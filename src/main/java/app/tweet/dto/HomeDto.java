package app.tweet.dto;

import app.tweet.entity.TmPost;

/**
 * ホーム画面に関連したオブジェクトを保持するDTO
 * @author aoi
 *
 */
public class HomeDto {
	
	/**
	 * 投稿情報
	 */
	private TmPost post;
	
	/**
	 * 画面へ表示される投稿者のユーザID
	 */
	private String userName;
	
	/**
	 * 画面へ表示される投稿者名
	 */
	private String userNickName;

	/**
	 * @return the post
	 */
	public TmPost getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(TmPost post) {
		this.post = post;
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

	/**
	 * @return the userNickName
	 */
	public String getUserNickName() {
		return userNickName;
	}

	/**
	 * @param userNickName the userNickName to set
	 */
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	
	
	
	

}
