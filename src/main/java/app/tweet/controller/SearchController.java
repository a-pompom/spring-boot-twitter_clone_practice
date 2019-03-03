package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.tweet.form.SearchForm;
import app.tweet.security.CustomUser;
import app.tweet.service.MentionService;
import app.tweet.service.SearchService;

/**
 * 検索画面を管理するコントローラ
 * @author aoi
 *
 */
@RequestMapping(value = "/search")
@Controller
public class SearchController {
	
	/**
	 * 検索サービス
	 */
	@Autowired
	SearchService searchService;
	
	/**
	 * メンションサービス
	 */
	@Autowired
	MentionService mentionService;
	
	/**
	 * 初期化処理 検索文字列での検索結果を取得する。
	 * @param form 検索画面用フォーム
	 * @param attr リダイレクト用のModel
	 * @param customUser ログインユーザの情報
	 * @return 検索画面
	 */
	@RequestMapping(value = "/init")
	private String search(SearchForm form, @ModelAttribute("searchQueryString")String searchString,@AuthenticationPrincipal CustomUser customUser) {
		//検索クエリを発行するため、modelから検索用文字列を取得　
		//フォームへ検索結果・ログインユーザ情報をセット
		form.setPostDto(searchService.getSearchResults(searchString, customUser.getUserId()));
		form.setUserName(customUser.getUsername());
		
		return "search";
	}
	
	/**
	 * 検索ボタン押下時に呼ばれる処理
	 * 検索文字列をModelへ格納し、検索コントローラへリダイレクト
	 * @param form ホーム画面用のフォーム
	 * @param attr リダイレクトパラメータを利用するためのModel
	 * @return 検索画面の検索処理
	 */
	@RequestMapping(value = "/search")
	private String search(SearchForm form, RedirectAttributes attr) {
		//TODO 検索クエリ文字列のサニタイズ化
		String unsafeQueryString = form.getSearchQuery();
		attr.addFlashAttribute("searchQueryString", unsafeQueryString);
		return "redirect:/search/init";
		
	}
	
	/**
	 * 選択された投稿を共有する。
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @param postUserId 選択された投稿のID
	 * @return ホーム画面
	 */
	@RequestMapping(value = "/share/{postId}")
	private String share(@AuthenticationPrincipal CustomUser customUser, @PathVariable("postId")int postId) {
		mentionService.share(customUser.getUserId(), postId);
		
		return "redirect:/search/init";
	}
	
	/**
	 * 選択された投稿をお気に入りへ登録する。
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @param postUserId 選択された投稿のID
	 * @return ホーム画面　
	 */
	@RequestMapping(value = "/favorite/{postId}")
	private String favorite(@AuthenticationPrincipal CustomUser customUser, @PathVariable("postId")int postId) {
		mentionService.favorite(customUser.getUserId(), postId);
		
		return "redirect:/search/init";
	}
	
	
	
	/**
	 * コントローラ内で共通の検索フォームを生成。
	 * @return 生成されたフォームオブジェクト
	 */
	@ModelAttribute("searchForm")
	SearchForm searchForm() {
		return new SearchForm();
	}

}
