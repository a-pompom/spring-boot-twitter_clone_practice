package app.tweet.dto;

import java.util.List;

import app.tweet.entity.ext.TmPostExt;

/**
 * 投稿情報を格納するDto
 * @author aoi
 *
 */
public class PostDto {
	
	/**
	 * 投稿リスト
	 */
	private List<TmPostExt> postList;

	public List<TmPostExt> getPostList() {
		return postList;
	}

	public void setPostList(List<TmPostExt> postList) {
		this.postList = postList;
	}
	
	
	
	

}
