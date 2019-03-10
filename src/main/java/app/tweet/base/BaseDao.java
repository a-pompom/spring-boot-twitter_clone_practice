package app.tweet.base;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Daoの基本的なメソッドを実装したクラス
 * 各Daoクラスはこれを継承して利用する
 * @author aoi
 * @param <T>
 *
 */
public class BaseDao<T> {
	
	/**
	 * クエリを実行するためのEntityManager
	 */
	@Autowired
	private EntityManager em;
	
	/**
	 * エンティティをDBへ登録する
	 * @param entity DBへ登録する対象となるエンティティ
	 */
	public <T>T saveOrUpdate(T entity) {
		return em.merge(entity);
	}
	

}
