package app.tweet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmPostDao;
import app.tweet.dao.TsFavoriteDao;
import app.tweet.dao.TsShareDao;
import app.tweet.dto.PostDto;
import app.tweet.entity.TmPost;
import app.tweet.entity.TsFavorite;
import app.tweet.entity.TsFavoriteId;
import app.tweet.entity.TsShare;
import app.tweet.entity.TsShareId;
import app.tweet.entity.ext.TmPostExt;

/**
 * ホーム画面用のサービス
 * @author aoi
 *
 */
@Service
public class HomeService {
	
	/**
	 * 投稿情報用のDao
	 */
	@Autowired
	private TmPostDao tmPostDao;
	
	/**
	 * 共有管理用のDao
	 */
	@Autowired
	private TsShareDao tsShareDao;
	
	/**
	 * お気に入り管理用のDao
	 */
	@Autowired
	private TsFavoriteDao tsFavoriteDao;
	
	
	/**
	 * ログインユーザの投稿・投稿者情報+ログインユーザがフォローしているユーザの投稿情報を取得し、DTOへ格納する。
	 * @param userId ログインユーザのユーザID
	 * @return ホーム画面へ表示する投稿情報を格納したDTOのリスト
	 */
	public PostDto findTheUserAndFollowPostList(int userId) {
		List<TmPostExt> postList = tmPostDao.findTheUserAndFollowExtPostList(userId);
		return convertToDto(postList);
	}
	
	/**
	 * DBから取得した拡張エンティティをDtoへセットする。
	 * @param postList DBから取得した拡張投稿エンティティのリスト
	 * @return ホーム画面へ表示する投稿一覧を格納したDto
	 */
	private PostDto convertToDto(List<TmPostExt> postList){
		PostDto dto = new PostDto();
		dto.setPostList(postList);
		
		return dto;
	}
	
	
	/**
	 * フォームへ入力された投稿情報を格納したDTOを利用してDBへ登録。
	 * フォームには投稿者情報は含まれないのでセッションを利用して紐付け。
	 * @param dto 投稿情報を格納したDTO
	 */
	@Transactional
	public void save(TmPost post, int loginUserId) {
		post.setPostUserId(loginUserId);
		tmPostDao.saveOrUpdate(post);
	}

}
