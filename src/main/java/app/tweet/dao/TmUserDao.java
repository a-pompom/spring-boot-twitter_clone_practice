package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TmUser;
import app.tweet.entity.ext.TmUserExt;
import app.tweet.entity.ext.TmUserFollowExt;

@Component
public class TmUserDao extends BaseDao<TmUser>{
	
	@Autowired
	EntityManager em;
	
	public List<TmUser> findUserList() {
		String query = "select * from tm_user";
		return em.createNativeQuery(query, TmUser.class).getResultList();
	}
	
	/**
	 * ユーザ名をキーにユーザを取得する。
	 * @param userName フォームへ入力されたユーザ名
	 * @return ユーザエンティティ
	 */
	public TmUser findByUserName(String userName) {
		String query = "select * from tm_user where user_name = '" + userName + "'";
		List<TmUser> userList = em.createNativeQuery(query, TmUser.class).getResultList();
		//結果セットは必ず一件以下となるが、GetSingleResultでは0件の場合エラーとなるので、リストで取得し、分岐
		if (userList.isEmpty()) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * ユーザのプロフィール情報を取得する
	 * @param userId 取得対象となるユーザのユーザID
	 * @return ユーザのプロフィール情報を格納した拡張エンティティ
	 */
	public TmUserExt findUserProfile(int userId) {
		String query = "";
		//ここではプロフィール情報としてユーザエンティティ、投稿数を取得
		//TODO お気に入り情報も今後は取得したい
		//パスワード、削除フラグといった画面に表示しない情報は取得しない
		query += "select u.user_id, u.user_name, u.user_nickname, u.bio, u.create_ts, u.birth_date, ";
		query += " pc.post_count, coalesce(fc.follow_count, 0) follow_count, coalesce(fwc.follower_count, 0) follower_count";
		query += " from tm_user u";
		//投稿数
		query += " inner join (";
		query += " select p.post_user_id, count(*) post_count";
		query += " from tm_post p";
		query += " where p.post_user_id = " + userId;
		query += " group by p.post_user_id";
		query += " ) pc";
		query += " on u.user_id = pc.post_user_id";
		//フォロー数
		query += " left join (";
		query += " select f.follow_user_id, count(*) follow_count";
		query += " from ts_follow f";
		query += " where f.follow_user_id =" + userId;
		query += " group by f.follow_user_id";
		query += " ) fc";
		query += " on u.user_id = fc.follow_user_id";
		//フォロワー数
		query += " left join (";
		query += " select f.follower_user_id, count(*) follower_count";
		query += " from ts_follow f";
		query += " where f.follower_user_id =" + userId;
		query += " group by f.follower_user_id";
		query += " ) fwc";
		query += " on u.user_id = fwc.follower_user_id";
		
		return (TmUserExt)em.createNativeQuery(query, TmUserExt.class).getSingleResult();
	}
	
	/**
	 * 参照中のユーザがフォローしているユーザの一覧をユーザエンティティとして取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザID
	 * @return フォローしているユーザのエンティティのリスト
	 */
	public List<TmUserFollowExt> findFollowUserList(int referUserId, int loginUserId){
		//ユーザテーブルを参照中のユーザに関するフォロー情報で絞り込み
		String query = "";
		query += "select u.user_id, u.user_name, u.user_nickname, u.bio,";
		//left joinの結果がnullでない場合はログインユーザがフォロー中のユーザとなる
		query += " case";
		query += "  when login_f.follower_user_id is null then true";
		query += "  else false";
		query += " end as login_follow_flg";
		query += " from (";
		query += "  select follower_user_id";
		query += "  from ts_follow";
		query += "  where follow_user_id = " + referUserId;
		query += "  ) f";
		query += " inner join tm_user u";
		query += " on f.follower_user_id = u.user_id";
		//ログインユーザがフォローしているか判別するためのフラグを取得
		query += " left join ts_follow login_f";
		query += " on login_f.follow_user_id =" + loginUserId;
		query += " and login_f.follower_user_id = f.follower_user_id";
		return em.createNativeQuery(query, TmUserFollowExt.class).getResultList();
	}
	
	/**
	 * 参照中のユーザをフォローしているユーザの一覧をユーザエンティティとして取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザID
	 * @return 参照中のユーザをフォローしているユーザのエンティティのリスト
	 */
	public List<TmUserFollowExt> findFollowerUserList(int referUserId, int loginUserId){
		//ユーザテーブルを参照中のユーザに関するフォロー情報で絞り込み
		String query = "";
		query += "select u.user_id, u.user_name, u.user_nickname, u.bio,";
		//left joinの結果がnullでない場合はログインユーザがフォロー中のユーザとなる
		query += " case";
		query += "  when login_f.follower_user_id is null then true";
		query += "  else false";
		query += " end as login_follow_flg";
		query += " from (";
		query += "  select follow_user_id";
		query += "  from ts_follow";
		query += "  where follower_user_id = " + referUserId;
		query += "  ) f";
		query += " inner join tm_user u";
		query += " on f.follow_user_id = u.user_id";
		//ログインユーザがフォローしているか判別するためのフラグを取得
		query += " left join ts_follow login_f";
		query += " on login_f.follow_user_id =" + loginUserId;
		query += " and login_f.follower_user_id = f.follow_user_id";
		return em.createNativeQuery(query, TmUserFollowExt.class).getResultList();
	}

}
