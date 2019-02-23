package app.tweet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.tweet.dao.TmPostDao;
import app.tweet.dao.TmUserDao;
import app.tweet.dto.UserDto;
import app.tweet.entity.TmUser;
import app.tweet.entity.ext.TmPostExt;
import app.tweet.entity.ext.TmUserExt;

/**
 * ユーザ画面を管理するサービス
 * @author aoi
 *
 */
@Service
public class UserService {
	
	/**
	 * ユーザDao
	 */
	@Autowired
	private TmUserDao tmUserDao;
	
	@Autowired
	private TmPostDao tmPostDao;
	//ユーザ投稿DTOを取得する処理
	
	
	//ユーザ情報を取得する処理
	public UserDto getUserProfile(int userId) {
		return convertToDto(tmUserDao.findUserProfile(userId), tmPostDao.findTheUserExtPostList(userId));
	}
	
	/**
	 * エンティティをDtoへ整形する。
	 * @param user ユーザのプロフィール情報を格納したエンティティ
	 * @param postList ユーザの投稿情報を格納したエンティティのリスト
	 * @return ユーザ画面で利用する情報を格納したDto
	 */
	private UserDto convertToDto(TmUserExt userExt, List<TmPostExt> postList) {
		UserDto dto = new UserDto();
		//ユーザプロフィール情報→Dto
		 dto.setPostNum(userExt.getPostCount());
		//Extエンティティからユーザエンティティを生成
		 TmUser user = new TmUser();
		 user.setBio(userExt.getBio());
		 user.setBirthDate(userExt.getBirthDate());
		 user.setCreateTs(userExt.getCreateTs());
		 user.setUserId(userExt.getUserId());
		 user.setUserName(userExt.getUserName());
		 user.setUserNickname(userExt.getUserNickname());
		
		 dto.setUser(user);
		//ユーザ投稿情報→Dto
		 dto.setUserPostExtList(postList);
		return dto;
	}
	
	

}
