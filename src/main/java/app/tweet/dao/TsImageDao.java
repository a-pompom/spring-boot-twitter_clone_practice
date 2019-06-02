package app.tweet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TsImage;
import app.tweet.util.QueryBuilder;

/**
 * 画像データを扱うDao
 * @author aoi
 *
 */
@Component
public class TsImageDao extends BaseDao<TsImage>{

	@PersistenceContext
	EntityManager em;
	
	/**
	 * ログインユーザのアイコン情報を取得する
	 * @param loginUserId ログインユーザのID
	 * @return アイコン情報エンティティ
	 */
	public TsImage findLoginIconPath(int loginUserId) {
		QueryBuilder q = new QueryBuilder(em);
		
		//未登録の場合はデフォルト画像を表示
		q.append("select coalesce(i.image_id, 0) image_id, coalesce(i.image_name, '/images/default.jpg') image_name ");
		q.append("from ts_image i");
		q.append(" inner join tm_user u");
		q.append(" on u.user_id = :userId").setParam("userId", loginUserId);
		q.append(" and i.image_id = u.profile_image_id");
		
		return q.createQuery(TsImage.class).findSingle();
	}
}
