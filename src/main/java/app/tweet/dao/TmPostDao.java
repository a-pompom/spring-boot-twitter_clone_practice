package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TmPost;
import app.tweet.entity.ext.TmPostExt;

/**
 * 投稿情報を取得・登録するためのDao
 * @author aoi
 *
 */
@Component
public class TmPostDao extends BaseDao<TmPost>{
	
	@Autowired
	EntityManager em;
	
	/**
	 * ユーザ画面で表示する指定ユーザの投稿+投稿者情報を取得する
	 * @param userId ログインユーザのユーザID
	 * @return ログインユーザの投稿・投稿者情報を格納した拡張エンティティ
	 */
	public List<TmPostExt> findTheUserExtPostList(int userId){
		String query = "";
		query += "select p.*, u.user_name, u.user_nickname";
		query += " from tm_post p";
		query += " inner join tm_user u";
		query += " on p.post_user_id = u.user_id";
		query += " and u.user_id = " + userId;
		query += " order by p.post_ts desc";
		
		return (List<TmPostExt>) em.createNativeQuery(query, TmPostExt.class).getResultList();
	}
	
	
	/**
	 * ホーム画面表示用にログインユーザの投稿+ ログインユーザがフォローしているユーザの投稿を取得する。
	 * @param userId ログインユーザのユーザID
	 * @return ホーム画面へ表示する投稿情報のリスト
	 */
	public List<TmPostExt> findTheUserAndFollowExtPostList(int userId) {
		String query = "";
		query += "select p.*, u.user_name, u.user_nickname";
		query += " from tm_post p";
		//指定のユーザIDがフォローしているユーザのID+自分自身のユーザID
		query += " inner join(";
		query += " select follower_user_id";
		query += " from ts_follow";
		query += " where follow_user_id = " + userId;
		//ログインユーザのユーザIDを取得
		//フォロー・フォロワーが0人の段階ではフォロー情報から自分自身を取得できないので、ユーザテーブルから取得
		query += " union";
		query += " select user_id";
		query += " from tm_user";
		query += " where user_id = " + userId;
		query += " ) f";
		//f.follower_user_idにfollow中のユーザID、自身のユーザIDが格納されているので、結合キーに指定し取得
		query += " on p.post_user_id = f.follower_user_id";
		//各々の投稿について投稿者情報を取得
		query += " inner join tm_user u";
		query += " on p.post_user_id = u.user_id";
		query += " order by p.post_ts desc";
		return (List<TmPostExt>) em.createNativeQuery(query, TmPostExt.class).getResultList();
	}

}
