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
	 * 投稿情報に投稿者情報を加えた拡張エンティティを取得する
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

}
