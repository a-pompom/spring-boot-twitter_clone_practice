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
	 * 投稿数
	 */
	private Integer postNum;
	
	/**
	 * ユーザの投稿
	 */
	private List<TmPostExt> userPostExtList;
	
	
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

	/**
	 * @return the postNum
	 */
	public Integer getPostNum() {
		return postNum;
	}

	/**
	 * @param postNum the postNum to set
	 */
	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}

	/**
	 * @return the userPostList
	 */
	public List<TmPostExt> getUserPostExtList() {
		return userPostExtList;
	}

	/**
	 * @param userPostList the userPostList to set
	 */
	public void setUserPostExtList(List<TmPostExt> userPostExtList) {
		this.userPostExtList = userPostExtList;
	}
	
	
	

}
