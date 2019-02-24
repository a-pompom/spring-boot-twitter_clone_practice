package app.tweet.form;

import app.tweet.dto.FollowDto;
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
	 * 表示情報の種類(follow, favorite...
	 */
	private String method;
	
	/**
	 * ログインユーザと参照中のユーザが一致するか
	 */
	private Boolean loggedInUser;
	
	
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
	
	

}
