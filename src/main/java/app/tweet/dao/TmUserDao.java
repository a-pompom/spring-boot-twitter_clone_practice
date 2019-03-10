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
		//パスワード、削除フラグといった画面に表示しない情報は取得しない
		query += "select u.user_id, u.user_name, u.user_nickname, u.bio, u.create_ts, u.birth_date, i.image_name image_path,";
		query += " coalesce(pc.post_count, 0) post_count, coalesce(fc.follow_count, 0) follow_count,";
		query += " coalesce(fwc.follower_count, 0) follower_count, coalesce(favc.favorite_count, 0) favorite_count";
		query += " from tm_user u";
		//画像
		query += " left join ts_image i";
		query += " on u.profile_image_id = i.image_id";
		//投稿数
		query += " left join (";
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
		//お気に入り数
		query += " left join (";
		query += " select fa.favorite_user_id, count(*) favorite_count";
		query += " from ts_favorite fa";
		query += " where fa.favorite_user_id =" + userId;
		query += " group by fa.favorite_user_id";
		query += " ) favc";
		query += " on u.user_id = favc.favorite_user_id";
		query += " where u.user_id =" + userId;
		return (TmUserExt)em.createNativeQuery(query, TmUserExt.class).getSingleResult();
	}
	
	

}
