package app.tweet.form;

import app.tweet.dto.PostDto;
import app.tweet.entity.TmPost;

/**
 * ホーム画面へ表示する情報を格納したフォーム
 * @author aoi
 *
 */
public class HomeForm {
	
	/**
	 *ログインユーザの入力した投稿を格納するためのEntity 
	 */
	private TmPost post;
	
	/**
	 * 投稿一覧情報を管理するDto
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
	 * 検索文字列
	 */
	private String searchQuery;
	
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
	 * @return the postDto
	 */
	public PostDto getPostDto() {
		return postDto;
	}

	/**
	 * @param postDto the postDto to set
	 */
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

	
	
	

}
