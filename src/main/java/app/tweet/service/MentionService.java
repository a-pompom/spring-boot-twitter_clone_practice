package app.tweet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TsFavoriteDao;
import app.tweet.dao.TsShareDao;
import app.tweet.entity.TsFavorite;
import app.tweet.entity.TsFavoriteId;
import app.tweet.entity.TsShare;
import app.tweet.entity.TsShareId;

/**
 * メンション関連の機能を管理するサービス
 * @author aoi
 *
 */
@Service
public class MentionService {
	
	/**
	 * お気に入りDao
	 */
	@Autowired
	private TsFavoriteDao tsFavoriteDao;
	
	/**
	 * 共有Dao
	 */
	@Autowired
	private TsShareDao tsShareDao;
	
	
	/**
	 * 選択された投稿を共有する。
	 * @param loginUserId 共有するユーザのID
	 * @param postId 共有対象の投稿ID
	 */
	@Transactional
	public void share(int loginUserId, int postId) {
		//セッションのログインユーザ・選択された投稿情報をもとにEntityを生成
		TsShare entity = new TsShare();
		entity.setId(new TsShareId());
		entity.getId().setShareUserId(loginUserId);
		entity.getId().setSharePostId(postId);
		
		tsShareDao.saveOrUpdate(entity);
	}
	
	/**
	 * 選択された投稿をお気に入り登録する。
	 * @param loginUserId お気に入り登録するユーザのID
	 * @param postId 登録対象の投稿ID
	 */
	@Transactional
	public void favorite(int loginUserId, int postId) {
		TsFavorite entity = new TsFavorite();
		entity.setId(new TsFavoriteId());
		entity.getId().setFavoriteUserId(loginUserId);
		entity.getId().setFavoritePostId(postId);
		
		tsFavoriteDao.saveOrUpdate(entity);
	}
	

}
