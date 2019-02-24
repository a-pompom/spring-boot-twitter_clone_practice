package app.tweet.dto;

import java.util.List;

import app.tweet.entity.ext.TmUserFollowExt;

/**
 * フォロー情報を持つDto
 * @author aoi
 *
 */
public class FollowDto {
	
	/**
	 * フォローユーザの一覧
	 */
	private List<TmUserFollowExt> followList;

	public List<TmUserFollowExt> getFollowList() {
		return followList;
	}

	public void setFollowList(List<TmUserFollowExt> followList) {
		this.followList = followList;
	}
	
	
	
	
}
