package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TmUser;
import app.tweet.entity.ext.TmUserExt;
import app.tweet.util.QueryBuilder;

/**
 * ユーザDao
 * ユーザ情報を操作する
 * @author aoi
 *
 */
@Component
public class TmUserDao extends BaseDao<TmUser>{
	
	@PersistenceContext
	EntityManager em;
	
	public List<TmUser> findUserList() {
		QueryBuilder q = new QueryBuilder(em);
		q.append("select * from tm_user");
		
		return q.createQuery(TmUser.class).findResultList();
	}
	
	/**
	 * ユーザIDをキーにユーザを取得する。
	 * @param userID 主キー
	 * @return ユーザエンティティ
	 */
	public TmUser findByUserId(int userId) {
		QueryBuilder q = new QueryBuilder(em);
		
		q.append("select * from tm_user where user_id = :userId ");
		q.setParam("userId", userId);
		
		return q.createQuery(TmUser.class).findSingle();
	}
	
	/**
	 * ユーザ名をキーにユーザを取得する。
	 * @param userName フォームへ入力されたユーザ名
	 * @return ユーザエンティティ
	 */
	public TmUser findByUserName(String userName) {
		QueryBuilder q = new QueryBuilder(em);
		
		q.append("select * from tm_user where user_name = :userName ");
		q.setParam("userName", userName);
		
		return q.createQuery(TmUser.class).findSingle();
	}
	
	/**
	 * ユーザのプロフィール情報を取得する
	 * @param userId 取得対象となるユーザのユーザID
	 * @return ユーザのプロフィール情報を格納した拡張エンティティ
	 */
	public TmUserExt findUserProfile(int userId) {
		QueryBuilder q = new QueryBuilder(em);
		//ここではプロフィール情報としてユーザエンティティ、投稿数を取得
		//パスワード、削除フラグといった画面に表示しない情報は取得しない
		q.append("select u.user_id, u.user_name, u.user_nickname, u.bio, u.create_ts, u.birth_date, i.image_name image_path,");
		q.append(" coalesce(pc.post_count, 0) post_count, coalesce(fc.follow_count, 0) follow_count,");
		q.append(" coalesce(fwc.follower_count, 0) follower_count, coalesce(favc.favorite_count, 0) favorite_count");
		q.append(" from tm_user u");
		
		//画像
		q.append(" left join ts_image i");
		q.append(" on u.profile_image_id = i.image_id");
		
		//投稿数
		q.append(" left join (");
		q.append(" select p.post_user_id, count(*) post_count");
		q.append(" from tm_post p");
		q.append(" where p.post_user_id = :userId").setParam("userId", userId);
		q.append(" group by p.post_user_id");
		q.append(" ) pc");
		q.append(" on u.user_id = pc.post_user_id");
		
		//フォロー数
		q.append(" left join (");
		q.append(" select f.follow_user_id, count(*) follow_count");
		q.append(" from ts_follow f");
		q.append(" where f.follow_user_id = :userId").setParam("userId", userId);
		q.append(" group by f.follow_user_id");
		q.append(" ) fc");
		q.append(" on u.user_id = fc.follow_user_id");
		
		//フォロワー数
		q.append(" left join (");
		q.append(" select f.follower_user_id, count(*) follower_count");
		q.append(" from ts_follow f");
		q.append(" where f.follower_user_id = :userId").setParam("userId", userId);
		q.append(" group by f.follower_user_id");
		q.append(" ) fwc");
		q.append(" on u.user_id = fwc.follower_user_id");
		
		//お気に入り数
		q.append(" left join (");
		q.append(" select fa.favorite_user_id, count(*) favorite_count");
		q.append(" from ts_favorite fa");
		q.append(" where fa.favorite_user_id = :userId").setParam("userId", userId);
		q.append(" group by fa.favorite_user_id");
		q.append(" ) favc");
		q.append(" on u.user_id = favc.favorite_user_id");
		q.append(" where u.user_id = :userId").setParam("userId", userId);
		
		return q.createQuery(TmUserExt.class).findSingle();
	}
	
}
