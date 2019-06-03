package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TsFollow;
import app.tweet.entity.ext.TmUserFollowExt;
import app.tweet.util.QueryBuilder;

/**
 * フォローに関する情報を操作するDao
 * @author aoi
 *
 */
@Component
public class TsFollowDao extends BaseDao<TsFollow> {
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * ログインユーザが参照中のユーザをフォローしているかDBを検索する。　
	 * @param referUserId　参照中のユーザID
	 * @param loginUserId ログインユーザID 
	 * @return フォロー中→Entity, フォロー中でない→null
	 */
	public TsFollow findByLoginAndReferUser(int referUserId, int loginUserId) {
		QueryBuilder q = new QueryBuilder(em);
		
		q.append("select * ");
		q.append(" from ts_follow");
		q.append(" where follow_user_id = :followUserId").setParam("followUserId", loginUserId);
		q.append(" and follower_user_id = :followerUserId").setParam("followerUserId", referUserId);
		
		return q.createQuery(TsFollow.class).findSingle();
	}
	
	/**
	 * 参照中のユーザがフォローしているユーザの一覧をユーザエンティティとして取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザID
	 * @return フォローしているユーザのエンティティのリスト
	 */
	public List<TmUserFollowExt> findFollowUserList(int referUserId, int loginUserId){
		QueryBuilder q = new QueryBuilder(em);
		
		//ユーザテーブルを参照中のユーザに関するフォロー情報で絞り込み
		q.append("select u.user_id, u.user_name, u.user_nickname, u.bio, i.image_name image_path, ");
		
		//left joinの結果がnullでない場合はログインユーザがフォロー中のユーザとなる
		q.append(" case");
		q.append("  when login_f.follower_user_id is null then false");
		q.append("  else true");
		q.append(" end as login_follow_flg");
		q.append(" from (");
		q.append("  select follower_user_id");
		q.append("  from ts_follow");
		q.append("  where follow_user_id = :followUserId").setParam("followUserId", referUserId);
		q.append("  ) f");
		q.append(" inner join tm_user u");
		q.append(" on f.follower_user_id = u.user_id");
		
		//ログインユーザがフォローしているか判別するためのフラグを取得
		q.append(" left join ts_follow login_f");
		q.append(" on login_f.follow_user_id = :loginFollowUserId").setParam("loginFollowUserId", loginUserId);
		q.append(" and login_f.follower_user_id = f.follower_user_id");
		
		//画像
		q.append(" left join ts_image i");
		q.append(" on u.profile_image_id = i.image_id");
		
		return q.createQuery(TmUserFollowExt.class).findResultList();
	}
	
	/**
	 * 参照中のユーザをフォローしているユーザの一覧をユーザエンティティとして取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザID
	 * @return 参照中のユーザをフォローしているユーザのエンティティのリスト
	 */
	public List<TmUserFollowExt> findFollowerUserList(int referUserId, int loginUserId){
		QueryBuilder q = new QueryBuilder(em);
		
		//ユーザテーブルを参照中のユーザに関するフォロー情報で絞り込み
		q.append("select u.user_id, u.user_name, u.user_nickname, u.bio, i.image_name image_path, ");
		
		//left joinの結果がnullでない場合はログインユーザがフォロー中のユーザとなる
		q.append(" case");
		q.append("  when login_f.follower_user_id is null then false");
		q.append("  else true");
		q.append(" end as login_follow_flg");
		q.append(" from (");
		q.append("  select follow_user_id");
		q.append("  from ts_follow");
		q.append("  where follower_user_id = :followerUserId").setParam("followerUserId", referUserId);
		q.append("  ) f");
		q.append(" inner join tm_user u");
		q.append(" on f.follow_user_id = u.user_id");
		
		//ログインユーザがフォローしているか判別するためのフラグを取得
		q.append(" left join ts_follow login_f");
		q.append(" on login_f.follow_user_id = :loginFollowerUserId").setParam("loginFollowerUserId", loginUserId);
		q.append(" and login_f.follower_user_id = f.follow_user_id");
		
		//画像
		q.append(" left join ts_image i");
		q.append(" on u.profile_image_id = i.image_id");
		
		return q.createQuery(TmUserFollowExt.class).findResultList();
	}

}
