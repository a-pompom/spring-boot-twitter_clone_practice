package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TsFollow;
import app.tweet.entity.ext.TmUserFollowExt;

@Component
public class TsFollowDao extends BaseDao<TsFollow> {
	
	@Autowired
	EntityManager em;
	
	/**
	 * ログインユーザが参照中のユーザをフォローしているかDBを検索する。　
	 * @param referUserId　参照中のユーザID
	 * @param loginUserId ログインユーザID 
	 * @return フォロー中→Entity, フォロー中でない→null
	 */
	public TsFollow findByLoginAndReferUser(int referUserId, int loginUserId) {
		String query = "";
		query += "select * ";
		query += " from ts_follow";
		query += " where follow_user_id = " + loginUserId;
		query += " and follower_user_id = " + referUserId;
		
		//TODO BaseDaoでfindSingleメソッドをつくって共通化したい。
		List<TsFollow> followList = em.createNativeQuery(query, TsFollow.class).getResultList();
		
		if (followList.isEmpty()) {
			return null;
		}
		
		return followList.get(0);
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
		query += "  when login_f.follower_user_id is null then false";
		query += "  else true";
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
		query += "  when login_f.follower_user_id is null then false";
		query += "  else true";
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
