package app.tweet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmPostDao;
import app.tweet.dto.HomeDto;
import app.tweet.entity.TmPost;
import app.tweet.entity.ext.TmPostExt;
import app.tweet.security.CustomUser;

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
	TmPostDao tmPostDao;
	
	/**
	 * フォームへ入力された投稿情報を格納したDTOを利用してDBへ登録。
	 * フォームには投稿者情報は含まれないのでセッションを利用して紐付け。
	 * @param dto 投稿情報を格納したDTO
	 */
	@Transactional
	public void save(HomeDto dto) {
		setUserID(dto);
		tmPostDao.saveOrUpdate(dto.getPost());
	}
	
	/**
	 * セッションを利用して投稿情報と投稿者情報を結びつける
	 * @param dto 投稿情報を格納したDTO
	 */
	private void setUserID(HomeDto dto) {
		//セッション上のユーザID情報を取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser user = (CustomUser)auth.getPrincipal();
		
		//投稿情報に投稿者情報を紐付け
		dto.getPost().setPostUserId(user.getUserId());
	}
	
	/**
	 * ログインユーザの投稿・投稿者情報をDBから取得し、DTOへ格納する。
	 * @return ログインユーザの投稿・投稿者情報を格納したDTOのリスト
	 */
	public List<HomeDto> findTheUserPostList(int userId){
		List<TmPostExt> postList = tmPostDao.findTheUserExtPostList(userId);
		return convertToDto(postList);
	}
	
	private List<HomeDto> convertToDto(List<TmPostExt> postList){
		List<HomeDto> dtoList = new ArrayList<HomeDto>();
		for (TmPostExt postExt : postList) {
			HomeDto dto = new HomeDto();
			//投稿情報
			dto.setPost(new TmPost());
			dto.getPost().setPostId(postExt.getPostId());
			dto.getPost().setPost(postExt.getPost());
			dto.getPost().setPostUserId(postExt.getPostUserId());
			dto.getPost().setPostTs(postExt.getPostTs());
			dto.getPost().setDeleteFlg(postExt.getDeleteFlg());
			
			//ユーザ情報
			dto.setUserName(postExt.getUserName());
			dto.setUserNickName(postExt.getUserNickname());
			
			dtoList.add(dto);
		}
		
		return dtoList;
	}

}
