package app.tweet.dto;

import java.util.List;

import app.tweet.entity.TmPost;
import app.tweet.entity.TmUser;
import app.tweet.entity.ext.TmPostExt;

/**
 * ユーザ情報を管理するためのDto
 * @author aoi
 *
 */
public class UserDto {
	
	/**
	 * ユーザエンティティ
	 */
	private TmUser user;
	
	/**
	 * ログイン中のユーザ名
	 */
	private String userName;
	
	/**
	 * 投稿数
	 */
	private Integer postCount;
	
	/**
	 * フォロー数
	 */
	private Integer followCount;
	
	/**
	 * フォロワー数
	 */
	private Integer followerCount;
	
	/**
	 * お気に入り登録数
	 */
	private Integer favoriteCount;
	
	/**
	 * @return the user
	 */
	public TmUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(TmUser user) {
		this.user = user;
	}
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the postCount
	 */
	public Integer getPostCount() {
		return postCount;
	}

	/**
	 * @param postCount the postCount to set
	 */
	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}
	
	

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Integer getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	/**
	 * @return the favoriteCount
	 */
	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * @param favoriteCount the favoriteCount to set
	 */
	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
	
	

}
