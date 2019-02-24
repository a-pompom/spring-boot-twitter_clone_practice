package app.tweet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmPostDao;
import app.tweet.dao.TmUserDao;
import app.tweet.dao.TsFollowDao;
import app.tweet.dto.UserDto;
import app.tweet.entity.TmUser;
import app.tweet.entity.TsFollow;
import app.tweet.entity.TsFollowId;
import app.tweet.entity.ext.TmPostExt;
import app.tweet.entity.ext.TmUserExt;
import app.tweet.entity.ext.TmUserFollowExt;

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
	
	@Autowired
	private TsFollowDao tsFollowDao;
	
	//ユーザ投稿DTOを取得する処理
	
	/**
	 * ユーザ名をキーにユーザエンティティを取得する。
	 * @param userName ユーザ名
	 * @return ユーザ名をキーに取得された単一のユーザエンティティ
	 */
	public TmUser getByUserName(String userName) {
		return tmUserDao.findByUserName(userName);
	}
	
	
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
		dto.setPostCount(userExt.getPostCount());
		dto.setFollowCount(userExt.getFollowCount());
		dto.setFollowerCount(userExt.getFollowerCount());
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
	
	/**
	 * 参照中のユーザをフォローする。
	 * @param loginUserId ログイン中のユーザID
	 * @param followerUserId フォロー対象のユーザID
	 */
	@Transactional
	public void doFollow(int loginUserId, int followerUserId) {
		//画面のユーザIDのペアからフォロー情報エンティティを生成し、DBへ格納
		TsFollow follow = new TsFollow();
		follow.setId(new TsFollowId());
		follow.getId().setFollowUserId(loginUserId);
		follow.getId().setFollowerUserId(followerUserId);
		
		tsFollowDao.saveOrUpdate(follow);
	}
	
	/**
	 * 参照中のユーザがフォローしているユーザの一覧を取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザのユーザID
	 * @return フォローユーザのエンティティのリスト
	 */
	public List<TmUserFollowExt> findFollowUserList(int referUserId, int loginUserId) {
		return tmUserDao.findFollowUserList(referUserId, loginUserId);
	}
	
	/**
	 * 参照中のユーザがフォローしているユーザの一覧を取得する。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザのユーザID
	 * @return フォローユーザのエンティティのリスト
	 */
	public List<TmUserFollowExt> findFollowerUserList(int referUserId, int loginUserId) {
		return tmUserDao.findFollowerUserList(referUserId, loginUserId);
	}
	
	

}
