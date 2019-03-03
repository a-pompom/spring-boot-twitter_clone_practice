package app.tweet.form;

import app.tweet.dto.PostDto;

public class SearchForm {
	
	/**
	 * ログインユーザ名
	 */
	private String userName;
	
	/**
	 *  検索文字列
	 */
	private String searchQuery;
	
	/**
	 * 投稿Dto
	 */
	private PostDto postDto;

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
	
	
	

}
