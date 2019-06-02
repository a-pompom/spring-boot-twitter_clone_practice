package app.tweet.dao.helper;

import app.tweet.util.QueryBuilder;

/**
 * 投稿Daoのクエリで共通部分を生成するためのヘルパークラス
 * @author aoi
 *
 */
public class TmPostDaoHelper {
	/**
	 * 投稿一覧画面でログインユーザが共有・お気に入りしたか否かによって
	 * アイコンの活性・非活性を切り替えるためのフラグを取得するクエリを生成する。
	 */
	public static void appendMentionConditional(QueryBuilder q) {
		// ログインユーザがお気に入りに登録しているか
		q.append(" case");
		q.append(" when fa.favorite_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_fav_flg,");
		
		// ログインユーザが共有しているか
		q.append(" case");
		q.append(" when sh.share_post_id is null then false");
		q.append(" else true");
		q.append(" end as login_share_flg");
	}
	
	/**
	 * 投稿一覧画面でログインユーザが共有・お気に入りしたか否かによって
	 * アイコンの活性・非活性を切り替えるためのフラグを取得するクエリを生成する。
	 * @param loginUserId ログインユーザのID
	 */
	public static void appendShareAndFavFlg(QueryBuilder q, int loginUserId) {
		q.append(" left join ts_favorite fa");
		q.append(" on fa.favorite_user_id = :loginFavoriteUserId").setParam("loginFavoriteUserId", loginUserId);
		q.append(" and p.post_id = fa.favorite_post_id");
		//ログインユーザが共有しているか否かを管理するフラグを取得
		q.append(" left join ts_share sh");
		q.append(" on sh.share_user_id = :loginShareUserId").setParam("loginShareUserId", loginUserId);
		q.append(" and p.post_id = sh.share_post_id");
	}
}
