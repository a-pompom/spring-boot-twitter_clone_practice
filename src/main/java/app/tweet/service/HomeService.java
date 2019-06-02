package app.tweet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmPostDao;
import app.tweet.dto.PostDto;
import app.tweet.entity.TmPost;
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
	 * ログインユーザの投稿・投稿者情報+ログインユーザがフォローしているユーザの投稿情報を取得し、DTOへ格納する。
	 * @param userId ログインユーザのユーザID
	 * @return ホーム画面へ表示する投稿情報を格納したDTOのリスト
	 */
	public PostDto findTheUserAndFollowPostList(int userId) {
		List<TmPostExt> postList = tmPostDao.findTheUserAndFollowExtPostList(userId);
		return convertToDto(postList);
	}
	
	/**
	 * ユーザの新規投稿を取得する
	 * @param postId 新規投稿された投稿のID
	 * @param loginUserId ログインユーザのID
	 * @return 新規投稿を格納したDTO
	 */
	public PostDto findNewPost(int postId, int loginUserId) {
		return convertToDto(tmPostDao.findThePost(postId, loginUserId));
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
	 * DBから取得した拡張エンティティをDtoへセットする。
	 * @param thePost 新規追加された投稿
	 * @return ホーム画面へ追加する新規投稿を格納したDto
	 */
	private PostDto convertToDto(TmPostExt thePost){
		PostDto dto = new PostDto();
		List<TmPostExt> param = new ArrayList<TmPostExt>();
		param.add(thePost);
		dto.setPostList(param);
		
		return dto;
	}
	
	
	/**
	 * フォームへ入力された投稿情報を格納したDTOを利用してDBへ登録。
	 * フォームには投稿者情報は含まれないのでセッションを利用して紐付け。
	 * @param dto 投稿情報を格納したDTO
	 */
	@Transactional
	public TmPost save(TmPost post, int loginUserId) {
		post.setPostUserId(loginUserId);
		return tmPostDao.saveOrUpdate(post);
	}

}
