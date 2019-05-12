package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TmPost;
import app.tweet.entity.ext.TmPostExt;
import app.tweet.util.QueryBuilder;

/**
 * 投稿情報を取得・登録するためのDao
 * @author aoi
 *
 */
@Component
public class TmPostDao extends BaseDao<TmPost>{
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * ユーザ画面で表示する指定ユーザの投稿+投稿者情報を取得する
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログインユーザのID
	 * @return ログインユーザの投稿・投稿者情報を格納した拡張エンティティ
	 */
	public List<TmPostExt> findTheUserExtPostList(int referUserId, int loginUserId){
		QueryBuilder q = new QueryBuilder(em);
		
		q.append("select p.*, i.image_name image_path, ");
		q.append(" case");
		q.append(" when fa.favorite_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_fav_flg,");
		q.append(" case");
		q.append(" when sh.share_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_share_flg");
		q.append(" from (");
		
		//ユーザの投稿＋ユーザの共有した投稿を取得
		q.append(" select p.*, u.user_name, u.user_nickname, u.profile_image_id");
		q.append(" from tm_post p");
		
		//投稿者情報
		q.append(" inner join tm_user u");
		q.append(" on p.post_user_id = u.user_id");
		q.append(" and u.user_id = :userId").setParam("userId", referUserId);
		q.append(" union");
		q.append(" select sp.*, su.user_name, su.user_nickname, su.profile_image_id");
		q.append(" from tm_post sp");
		q.append(" inner join ts_share sh");
		q.append(" on sh.share_user_id = :shareUserId").setParam("shareUserId", referUserId);
		q.append(" and sp.post_id = sh.share_post_id");
		q.append(" inner join tm_user su");
		q.append(" on sp.post_user_id = su.user_id");
		q.append(" ) as p");
		
		//画像
		q.append(" left join ts_image i");
		q.append(" on p.profile_image_id = i.image_id");
		
		//各々の投稿について、ログインユーザがお気に入りに登録しているか否かを管理するフラグを取得
		q.append(" left join ts_favorite fa");
		q.append(" on fa.favorite_user_id = :favoriteUserId").setParam("favoriteUserId", loginUserId);
		q.append(" and p.post_id = fa.favorite_post_id");
		
		//ログインユーザが共有しているか否かを管理するフラグを取得
		q.append(" left join ts_share sh");
		q.append(" on sh.share_user_id = :loginShareUserId").setParam("loginShareUserId", loginUserId);
		q.append(" and p.post_id = sh.share_post_id");
		q.append(" order by p.post_ts desc");
		
		return q.createQuery(TmPostExt.class).findResultList();
	}
	
	
	/**
	 * ホーム画面表示用にログインユーザの投稿+ ログインユーザがフォローしているユーザの投稿を取得する。
	 * @param loginUserId ログインユーザのユーザID
	 * @return ホーム画面へ表示する投稿情報のリスト
	 */
	public List<TmPostExt> findTheUserAndFollowExtPostList(int loginUserId) {
		QueryBuilder q = new QueryBuilder(em);
		q.append("select p.*, i.image_name image_path,");
		q.append(" case");
		q.append(" when fa.favorite_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_fav_flg,");
		q.append(" case");
		q.append(" when sh.share_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_share_flg");
		q.append(" from (");
		
		//ユーザの投稿＋ユーザの共有した投稿を取得
		q.append(" select p.*, u.user_name, u.user_nickname, u.profile_image_id");
		q.append(" from tm_post p");
		
		//指定のユーザIDがフォローしているユーザのID+自分自身のユーザID
		q.append(" inner join(");
		q.append(" select follower_user_id");
		q.append(" from ts_follow");
		q.append(" where follow_user_id = :followUserId").setParam("followUserId", loginUserId);
		
		//ログインユーザのユーザIDを取得
		//フォロー・フォロワーが0人の段階ではフォロー情報から自分自身を取得できないので、ユーザテーブルから取得
		q.append(" union");
		q.append(" select user_id");
		q.append(" from tm_user");
		q.append(" where user_id = :userId").setParam("userId", loginUserId);
		q.append(" ) f");
		
		//f.follower_user_idにfollow中のユーザID、自身のユーザIDが格納されているので、結合キーに指定し取得
		q.append(" on p.post_user_id = f.follower_user_id");
		
		//各々の投稿について投稿者情報を取得
		q.append(" inner join tm_user u");
		q.append(" on p.post_user_id = u.user_id");
		
		//共有情報をUNIONで縦結合
		q.append(" union");
		q.append(" select sp.*, su.user_name, su.user_nickname, su.profile_image_id");
		q.append(" from tm_post sp");
		q.append(" inner join ts_share sh");
		q.append(" on sh.share_user_id = :shareUserId").setParam("shareUserId", loginUserId);
		q.append(" and sp.post_id = sh.share_post_id");
		q.append(" inner join tm_user su");
		q.append(" on sp.post_user_id = su.user_id");
		q.append(" ) as p");
		
		//画像
		q.append(" left join ts_image i");
		q.append(" on p.profile_image_id = i.image_id");
		
		//各々の投稿について、ログインユーザがお気に入りに登録しているか否かを管理するフラグを取得
		q.append(" left join ts_favorite fa");
		q.append(" on fa.favorite_user_id = :loginFavoriteUserId").setParam("loginFavoriteUserId", loginUserId);
		q.append(" and p.post_id = fa.favorite_post_id");
		
		//ログインユーザが共有しているか否かを管理するフラグを取得
		q.append(" left join ts_share sh");
		q.append(" on sh.share_user_id = :loginShareUserId").setParam("loginShareUserId", loginUserId);
		q.append(" and p.post_id = sh.share_post_id");
		q.append(" order by p.post_ts desc");
		
		return q.createQuery(TmPostExt.class).findResultList();
	}
	
	/**
	 * 参照中のユーザがお気に入りに登録している投稿を取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログインユーザID
	 * @return お気に入りの投稿リスト
	 */
	public List<TmPostExt> findFavoritePostList(int referUserId, int loginUserId) {
		QueryBuilder q = new QueryBuilder(em);
		
		q.append("select p.*, u.user_name, u.user_nickname, i.image_name image_path,");
		q.append(" case");
		q.append(" when login_fa.favorite_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_fav_flg,");
		q.append(" case");
		q.append(" when sh.share_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_share_flg");
		q.append(" from tm_post p");
		
		//参照中のユーザIDでお気に入り情報を絞り込み
		q.append(" inner join ts_favorite fa");
		q.append(" on fa.favorite_user_id = :favoriteUserId").setParam("favoriteUserId", referUserId);
		q.append(" and p.post_id = fa.favorite_post_id");
		
		//投稿者情報を取得するためユーザテーブルと結合
		q.append(" inner join tm_user u");
		q.append(" on p.post_user_id = u.user_id");
		
		//画像
		q.append(" left join ts_image i");
		q.append(" on u.profile_image_id = i.image_id");
		
		//ログインユーザがお気に入りに登録しているか否かを管理するフラグを取得
		q.append(" left join ts_favorite login_fa");
		q.append(" on login_fa.favorite_user_id = :loginFavoriteUserId").setParam("loginFavoriteUserId", loginUserId);
		q.append(" and p.post_id = login_fa.favorite_post_id");
		
		//ログインユーザが共有しているか否かを管理するフラグを取得
		q.append(" left join ts_share sh");
		q.append(" on sh.share_user_id = :loginShareUserId").setParam("loginShareUserId", loginUserId);
		q.append(" and p.post_id = sh.share_post_id");
		q.append(" order by p.post_ts");
		
		return q.createQuery(TmPostExt.class).findResultList();
	}
	
	/*			 検索画面用			 */
	
	/**
	 * 検索文字列をもとに投稿を検索する。
	 * @param searchQuery 検索用文字列
	 * @param loginUserId ログインユーザのID
	 * @return 検索結果
	 */
	public List<TmPostExt> findSearchResults(String searchQuery, int loginUserId){
		QueryBuilder q = new QueryBuilder(em);
		
		q.append("select p.*, u.user_name, u.user_nickname, i.image_name image_path,");
		
		//ログインユーザ共有フラグ
		q.append(" case");
		q.append("  when sh.share_post_id is null then false");
		q.append("  else true");
		q.append(" end as login_share_flg,");
		
		//ログインユーザお気に入りフラグ
		q.append(" case");
		q.append("  when fav.favorite_post_id is null then false");
		q.append("  else true");
		q.append(" end as login_fav_flg");
		q.append(" from tm_post p");
		
		//投稿者情報
		q.append(" inner join tm_user u");
		q.append(" on p.post_user_id = u.user_id");
		
		//画像
		q.append(" left join ts_image i");
		q.append(" on u.profile_image_id = i.image_id");
		
		//フラグ取得
		q.append(" left join ts_share sh");
		q.append(" on sh.share_user_id = :loginShareUserId").setParam("loginShareUserId", loginUserId);
		q.append(" and p.post_id = sh.share_post_id");
		q.append(" left join ts_favorite fav");
		q.append(" on fav.favorite_user_id = :loginFavoriteUserId").setParam("loginFavoriteUserId", loginUserId);
		q.append(" and p.post_id = fav.favorite_post_id");
		
		//検索文字列による絞り込み　
		q.append(" where p.post like '%" + searchQuery + "%'");
		q.append(" order by p.post_ts");
		
		return q.createQuery(TmPostExt.class).findResultList();
	}

}
