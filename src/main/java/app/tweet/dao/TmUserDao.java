package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TmUser;
import app.tweet.entity.ext.TmUserExt;

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
		return (TmUser)em.createNativeQuery(query, TmUser.class).getSingleResult();
	}
	
	/**
	 * ユーザのプロフィール情報を取得する
	 * @param userId 取得対象となるユーザのユーザID
	 * @return ユーザのプロフィール情報を格納した拡張エンティティ
	 */
	public TmUserExt findUserProfile(int userId) {
		String query = "";
		//ここではプロフィール情報としてユーザエンティティ、投稿数を取得
		//TODO フォロー、お気に入り情報も今後は取得したい
		//パスワード、削除フラグといった画面に表示しない情報は取得しない
		query += "select u.user_id, u.user_name, u.user_nickname, u.bio, u.create_ts, u.birth_date, pc.post_count";
		query += " from tm_user u";
		//投稿数取得部分
		query += " inner join (";
		query += " select p.post_user_id, count(*) post_count";
		query += " from tm_post p";
		query += " where p.post_user_id = " + userId;
		query += " group by p.post_user_id";
		query += " ) pc";
		query += " on u.user_id = pc.post_user_id";
		
		return (TmUserExt)em.createNativeQuery(query, TmUserExt.class).getSingleResult();
	}

}
