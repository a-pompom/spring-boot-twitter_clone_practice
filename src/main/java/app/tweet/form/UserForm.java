package app.tweet.form;

import org.springframework.web.multipart.MultipartFile;

import app.tweet.dto.FollowDto;
import app.tweet.dto.PostDto;
import app.tweet.dto.UserDto;

/**
 * ユーザ画面の情報を管理するフォーム
 * @author aoi
 *
 */
public class UserForm {
	
	/**
	 * ユーザDto
	 */
	private UserDto dto;
	
	/**
	 * フォローDto
	 */
	private FollowDto followDto;
	
	/**
	 * 投稿Dto
	 */
	private PostDto postDto;
	
	/**
	 * ログインユーザ名
	 */
	private String userName;
	
	/**
	 * ログインユーザアイコンパス
	 */
	private String imagePath;
	
	/**
	 * 表示情報の種類(follow, favorite...
	 */
	private String method;
	
	/**
	 * プロフィール画像
	 */
	private MultipartFile profileImage;
	
	/**
	 * 検索用文字列
	 */
	private String searchQuery;
	
	/**
	 * ログインユーザと参照中のユーザが一致するか
	 */
	private Boolean loggedInUser;
	
	/**
	 * 参照中のユーザをフォローしているか
	 */
	private Boolean loggedInFollowflg;
	
	
	/**
	 * @return the dto
	 */
	public UserDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(UserDto dto) {
		this.dto = dto;
	}
	
	

	/**
	 * @return the followDto
	 */
	public FollowDto getFollowDto() {
		return followDto;
	}

	/**
	 * @param followDto the followDto to set
	 */
	public void setFollowDto(FollowDto followDto) {
		this.followDto = followDto;
	}
	
	
	

	public PostDto getPostDto() {
		return postDto;
	}

	public void setPostDto(PostDto postDto) {
		this.postDto = postDto;
	}
	
	

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Boolean loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	
	
	
	/**
	 * @return the profileImage
	 */
	public MultipartFile getProfileImage() {
		return profileImage;
	}

	/**
	 * @param profileImage the profileImage to set
	 */
	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the searchQuery
	 */
	public String getSearchQuery() {
		return searchQuery;
	}

	/**
	 * @param searchQuery the searchQuery to set
	 */
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return the loggedInFollowflg
	 */
	public Boolean getLoggedInFollowflg() {
		return loggedInFollowflg;
	}

	/**
	 * @param loggedInFollowflg the loggedInFollowflg to set
	 */
	public void setLoggedInFollowflg(Boolean loggedInFollowflg) {
		this.loggedInFollowflg = loggedInFollowflg;
	}
	
	

}
