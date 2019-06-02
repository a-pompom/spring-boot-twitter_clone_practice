package app.tweet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmPostDao;
import app.tweet.dao.TmUserDao;
import app.tweet.dao.TsFollowDao;
import app.tweet.dto.FollowDto;
import app.tweet.dto.PostDto;
import app.tweet.dto.UserDto;
import app.tweet.entity.TmUser;
import app.tweet.entity.TsFollow;
import app.tweet.entity.TsFollowId;
import app.tweet.entity.ext.TmPostExt;
import app.tweet.entity.ext.TmUserExt;
import app.tweet.entity.ext.TmUserFollowExt;
import app.tweet.util.DateUtil;

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
	
	/**
	 * 投稿情報Dao
	 */
	@Autowired
	private TmPostDao tmPostDao;
	
	/**
	 * フォロー情報Dao
	 */
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
	
	/**
	 * ログイン中のユーザが参照中のユーザをフォローしているか判定。
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログインユーザID
	 * @return フォロー中→true, フォローしていない→false
	 */
	public Boolean isLoggedInFollowing(int referUserId, int loginUserId) {
		if (tsFollowDao.findByLoginAndReferUser(referUserId, loginUserId) == null) {
			return false;
		}
		
		return true;
	}
	
	/*			ユーザ関連のDto取得処理			*/
	
	/**
	 * ユーザのプロフィール情報を取得する
	 * @param userId 参照中のユーザID
	 * @return ユーザのプロフィール情報
	 */
	public UserDto getUserProfile(int userId) {
		return convertToUserDto(tmUserDao.findUserProfile(userId));
	}
	
	/**
	 * ユーザの投稿情報を取得する
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログインユーザのユーザID
	 * @return ユーザの投稿情報
	 */
	public PostDto getUserPost(int referUserId, int loginUserId) {
		return convertToPostDto(tmPostDao.findTheUserExtPostList(referUserId, loginUserId));
	}
	
	/**
	 * 参照中のユーザのお気に入り情報を取得する。
	 * @param referUserId 参照中のユーザのユーザID
	 * @param loginUserId ログイン中のユーザID
	 * @return お気に入りの投稿情報のリスト　
	 */
	public PostDto getFavoritePost(int referUserId, int loginUserId) {
		return convertToPostDto(tmPostDao.findFavoritePostList(referUserId, loginUserId));
	}
	
	/**
	 * ユーザのフォロー情報を取得する
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザID
	 * @return フォローユーザ情報
	 */
	public FollowDto getUserFollowing(int referUserId, int loginUserId) {
		return convertToFollowDto(tsFollowDao.findFollowUserList(referUserId, loginUserId));
	}
	
	 /** ユーザのフォロー情報を取得する
	 * @param referUserId 参照中のユーザID
	 * @param loginUserId ログイン中のユーザID
	 * @return フォローユーザ情報
	 */
	public FollowDto getUserFollower(int referUserId, int loginUserId) {
		return convertToFollowDto(tsFollowDao.findFollowerUserList(referUserId, loginUserId));
	}
	
	/*			ユーザ関連のDto取得処理ここまで			*/
	
	
	/*			ユーザ関連のEntity→Dto整形処理			*/
	/**
	 * エンティティをDtoへ整形する。
	 * @param user ユーザのプロフィール情報を格納したエンティティ
	 * @param postList ユーザの投稿情報を格納したエンティティのリスト
	 * @return ユーザ画面で利用する情報を格納したDto
	 */
	private UserDto convertToUserDto(TmUserExt userExt) {
		UserDto dto = new UserDto();
		//ログインユーザ名→Dto
		dto.setUserName(userExt.getUserName());
		dto.setImagePath(userExt.getImagePath());
		//ユーザプロフィール情報→Dto
		dto.setPostCount(userExt.getPostCount());
		dto.setFollowCount(userExt.getFollowCount());
		dto.setFollowerCount(userExt.getFollowerCount());
		dto.setFavoriteCount(userExt.getFavoriteCount());
		//Extエンティティからユーザエンティティを生成
		TmUser user = new TmUser();
		user.setBio(userExt.getBio());
		user.setBirthDate(userExt.getBirthDate());
		user.setCreateTs(userExt.getCreateTs());
		user.setUserId(userExt.getUserId());
		user.setUserName(userExt.getUserName());
		user.setUserNickname(userExt.getUserNickname());
		
		dto.setUser(user);
		return dto;
	}
	
	/**
	 * エンティティをDtoへ整形する。
	 * @param postList ユーザの投稿情報を格納したエンティティのリスト
	 * @return ユーザ画面で利用する情報を格納したDto
	 */
	private PostDto convertToPostDto(List<TmPostExt> postList) {
		PostDto dto = new PostDto();
		dto.setPostList(postList);
		return dto;
	}
	
	/**
	 * エンティティをDtoへ整形する。
	 * @param postList ユーザの投稿情報を格納したエンティティのリスト
	 * @return ユーザ画面で利用する情報を格納したDto
	 */
	private FollowDto convertToFollowDto(List<TmUserFollowExt> followList) {
		FollowDto dto = new FollowDto();
		dto.setFollowList(followList);
		return dto;
	}
	
	/*			ユーザ関連のEntity→Dto整形処理ここまで			*/
	
	
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
	 * フォームの入力値を格納したDtoからユーザEntityを生成し、DBへ格納
	 * @param dto ユーザDto
	 */
	@Transactional
	public void editUser(UserDto dto, int profileImageId) {
		//既存のユーザEntityをフォームの入力値を格納したDtoで更新
		TmUser entity = tmUserDao.findByUserName(dto.getUser().getUserName());
		//カラム値セット処理
		entity.setUserName(dto.getUser().getUserName());
		entity.setUserNickname(dto.getUser().getUserNickname());
		entity.setBio(dto.getUser().getBio());
		entity.setProfileImageId(profileImageId);
		
		tmUserDao.saveOrUpdate(entity);
	}
	
	

}
