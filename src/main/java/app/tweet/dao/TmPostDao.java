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
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログインユーザのID
	 * @return ログインユーザの投稿・投稿者情報を格納した拡張エンティティ
	 */
	public List<TmPostExt> findTheUserExtPostList(int referUserId, int loginUserId){
		String query = "";
		query += "select p.*, i.image_name image_path, ";
		query += " case";
		query += " when fa.favorite_post_id is null then false";
		query += " else true";
		query += " end as login_fav_flg,";
		query += " case";
		query += " when sh.share_post_id is null then false";
		query += " else true";
		query += " end as login_share_flg";
		query += " from (";
		//ユーザの投稿＋ユーザの共有した投稿を取得
		query += " select p.*, u.user_name, u.user_nickname, u.profile_image_id";
		query += " from tm_post p";
		//投稿者情報
		query += " inner join tm_user u";
		query += " on p.post_user_id = u.user_id";
		query += " and u.user_id = " + referUserId;
		query += " union";
		query += " select sp.*, su.user_name, su.user_nickname, su.profile_image_id";
		query += " from tm_post sp";
		query += " inner join ts_share sh";
		query += " on sh.share_user_id = " + referUserId;
		query += " and sp.post_id = sh.share_post_id";
		query += " inner join tm_user su";
		query += " on sp.post_user_id = su.user_id";
		query += " ) as p";
		//画像
		query += " left join ts_image i";
		query += " on p.profile_image_id = i.image_id";
		//各々の投稿について、ログインユーザがお気に入りに登録しているか否かを管理するフラグを取得
		query += " left join ts_favorite fa";
		query += " on fa.favorite_user_id =" + loginUserId;
		query += " and p.post_id = fa.favorite_post_id";
		//ログインユーザが共有しているか否かを管理するフラグを取得
		query += " left join ts_share sh";
		query += " on sh.share_user_id =" + loginUserId;
		query += " and p.post_id = sh.share_post_id";
		query += " order by p.post_ts desc";
		
		return (List<TmPostExt>) em.createNativeQuery(query, TmPostExt.class).getResultList();
	}
	
	
	/**
	 * ホーム画面表示用にログインユーザの投稿+ ログインユーザがフォローしているユーザの投稿を取得する。
	 * @param loginUserId ログインユーザのユーザID
	 * @return ホーム画面へ表示する投稿情報のリスト
	 */
	public List<TmPostExt> findTheUserAndFollowExtPostList(int loginUserId) {
		String query = "";
		query += "select p.*, i.image_name image_path,";
		query += " case";
		query += " when fa.favorite_post_id is null then false";
		query += " else true";
		query += " end as login_fav_flg,";
		query += " case";
		query += " when sh.share_post_id is null then false";
		query += " else true";
		query += " end as login_share_flg";
		query += " from (";
		//ユーザの投稿＋ユーザの共有した投稿を取得
		query += " select p.*, u.user_name, u.user_nickname, u.profile_image_id";
		query += " from tm_post p";
		//指定のユーザIDがフォローしているユーザのID+自分自身のユーザID
		query += " inner join(";
		query += " select follower_user_id";
		query += " from ts_follow";
		query += " where follow_user_id = " + loginUserId;
		//ログインユーザのユーザIDを取得
		//フォロー・フォロワーが0人の段階ではフォロー情報から自分自身を取得できないので、ユーザテーブルから取得
		query += " union";
		query += " select user_id";
		query += " from tm_user";
		query += " where user_id = " + loginUserId;
		query += " ) f";
		//f.follower_user_idにfollow中のユーザID、自身のユーザIDが格納されているので、結合キーに指定し取得
		query += " on p.post_user_id = f.follower_user_id";
		//各々の投稿について投稿者情報を取得
		query += " inner join tm_user u";
		query += " on p.post_user_id = u.user_id";
		//共有情報をUNIONで縦結合
		query += " union";
		query += " select sp.*, su.user_name, su.user_nickname, su.profile_image_id";
		query += " from tm_post sp";
		query += " inner join ts_share sh";
		query += " on sh.share_user_id = " + loginUserId;
		query += " and sp.post_id = sh.share_post_id";
		query += " inner join tm_user su";
		query += " on sp.post_user_id = su.user_id";
		query += " ) as p";
		//画像
		query += " left join ts_image i";
		query += " on p.profile_image_id = i.image_id";
		//各々の投稿について、ログインユーザがお気に入りに登録しているか否かを管理するフラグを取得
		query += " left join ts_favorite fa";
		query += " on fa.favorite_user_id =" + loginUserId;
		query += " and p.post_id = fa.favorite_post_id";
		//ログインユーザが共有しているか否かを管理するフラグを取得
		query += " left join ts_share sh";
		query += " on sh.share_user_id =" + loginUserId;
		query += " and p.post_id = sh.share_post_id";
		query += " order by p.post_ts desc";
		return (List<TmPostExt>) em.createNativeQuery(query, TmPostExt.class).getResultList();
	}
	
	/**
	 * 参照中のユーザがお気に入りに登録している投稿を取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログインユーザID
	 * @return お気に入りの投稿リスト
	 */
	public List<TmPostExt> findFavoritePostList(int referUserId, int loginUserId) {
		String query = "";
		query += "select p.*, u.user_name, u.user_nickname, i.image_name image_path,";
		query += " case";
		query += " when login_fa.favorite_post_id is null then false";
		query += " else true";
		query += " end as login_fav_flg,";
		query += " case";
		query += " when sh.share_post_id is null then false";
		query += " else true";
		query += " end as login_share_flg";
		query += " from tm_post p";
		//参照中のユーザIDでお気に入り情報を絞り込み
		query += " inner join ts_favorite fa";
		query += " on fa.favorite_user_id = " + referUserId;
		query += " and p.post_id = fa.favorite_post_id";
		//投稿者情報を取得するためユーザテーブルと結合
		query += " inner join tm_user u";
		query += " on p.post_user_id = u.user_id";
		//画像
		query += " left join ts_image i";
		query += " on u.profile_image_id = i.image_id";
		//ログインユーザがお気に入りに登録しているか否かを管理するフラグを取得
		query += " left join ts_favorite login_fa";
		query += " on login_fa.favorite_user_id = " + loginUserId;
		query += " and p.post_id = login_fa.favorite_post_id";
		//ログインユーザが共有しているか否かを管理するフラグを取得
		query += " left join ts_share sh";
		query += " on sh.share_user_id =" + loginUserId;
		query += " and p.post_id = sh.share_post_id";
		query += " order by p.post_ts";
		
		return (List<TmPostExt>) em.createNativeQuery(query, TmPostExt.class).getResultList();
	}
	
	/*			 検索画面用			 */
	
	/**
	 * 検索文字列をもとに投稿を検索する。
	 * @param searchQuery 検索用文字列
	 * @param loginUserId ログインユーザのID
	 * @return 検索結果
	 */
	public List<TmPostExt> findSearchResults(String searchQuery, int loginUserId){
		String query = "";
		query += "select p.*, u.user_name, u.user_nickname, i.image_name image_path,";
		//ログインユーザ共有フラグ
		query += " case";
		query += "  when sh.share_post_id is null then false";
		query += "  else true";
		query += " end as login_share_flg,";
		//ログインユーザお気に入りフラグ
		query += " case";
		query += "  when fav.favorite_post_id is null then false";
		query += "  else true";
		query += " end as login_fav_flg";
		query += " from tm_post p";
		//投稿者情報
		query += " inner join tm_user u";
		query += " on p.post_user_id = u.user_id";
		//画像
		query += " left join ts_image i";
		query += " on u.profile_image_id = i.image_id";
		//フラグ取得
		query += " left join ts_share sh";
		query += " on sh.share_user_id =" + loginUserId;
		query += " and p.post_id = sh.share_post_id";
		query += " left join ts_favorite fav";
		query += " on fav.favorite_user_id =" + loginUserId;
		query += " and p.post_id = fav.favorite_post_id";
		//検索文字列による絞り込み　
		query += " where p.post like '%" + searchQuery + "%'";
		query += " order by p.post_ts";
		
		return (List<TmPostExt>) em.createNativeQuery(query, TmPostExt.class).getResultList();
	}

}
