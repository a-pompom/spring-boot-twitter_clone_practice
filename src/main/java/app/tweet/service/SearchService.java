package app.tweet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.tweet.dao.TmPostDao;
import app.tweet.dto.PostDto;
import app.tweet.entity.ext.TmPostExt;

/**
 * 検索関連の機能を管理するサービス
 * @author aoi
 *
 */
@Service
public class SearchService {
	
	/**
	 * 投稿Dao
	 */
	@Autowired
	private TmPostDao tmPostDao;
	
	/**
	 * 
	 * @param searchQuery 検索文字列
	 * @param loginUserId ログインユーザのID
	 * @return 検索結果の投稿
	 */
	public PostDto getSearchResults(String searchQuery, int loginUserId) {
		return convertToPostDto(tmPostDao.findSearchResults(searchQuery, loginUserId));
	}
	
	/**
	 * 投稿用の拡張EntityをDtoへ変換する
	 * @param postList 検索結果の投稿のリスト
	 * @return 投稿Dto
	 */
	public PostDto convertToPostDto(List<TmPostExt> postList) {
		PostDto dto = new PostDto();
		dto.setPostList(postList);
		return dto;
	}

}
