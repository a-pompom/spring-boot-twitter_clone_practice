package app.tweet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmPostDao;
import app.tweet.dto.HomeDto;
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

}
