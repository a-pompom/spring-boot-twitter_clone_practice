package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TsImage;

/**
 * 画像データを扱うDao
 * @author aoi
 *
 */
@Component
public class TsImageDao extends BaseDao<TsImage>{

	@Autowired
	EntityManager em;
	
	/**
	 * ログインユーザのアイコン情報を取得する
	 * @param loginUserId ログインユーザのID
	 * @return アイコン情報エンティティ
	 */
	public TsImage findLoginIconPath(int loginUserId) {
		String query = "";
		query += "select coalesce(i.image_id, 0) image_id, coalesce(i.image_name, '/images/default.jpg') image_name ";
		query += " from ts_image i";
		query += " inner join tm_user u";
		query += " on u.user_id = " + loginUserId;
		query += " and i.image_id = u.profile_image_id";
		
		List<TsImage> tempList = (List<TsImage>) em.createNativeQuery(query, TsImage.class).getResultList();
		return tempList.get(0);
	}
}
