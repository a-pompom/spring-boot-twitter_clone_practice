package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.tweet.entity.TmPost;
import app.tweet.form.HomeForm;
import app.tweet.security.CustomUser;
import app.tweet.service.HomeService;
import app.tweet.service.MentionService;

/**
 * ホーム画面を管理するコントローラ
 * @author aoi
 *
 */
@RequestMapping("/home")
@Controller
public class HomeController {
	
	/**
	 * ホームサービス
	 */
	@Autowired
	private HomeService homeService;
	
	/**
	 * メンションサービス
	 */
	@Autowired
	private MentionService mentionService;

	/**
	 * 初期化処理
	 * 投稿情報及びログイン中のユーザ情報を取得
	 * @param form ホーム画面用のフォーム
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @return ホーム画面
	 */
	@RequestMapping("/init")
	private String init(HomeForm form, @AuthenticationPrincipal CustomUser customUser) {
		form.setPostDto(homeService.findTheUserAndFollowPostList(customUser.getUserId()));
		//ユーザ画面への遷移時に必要となるログインユーザ名をフォームへセット　
		form.setUserName(customUser.getUsername());
		//ログインユーザの入力した投稿情報を格納するEntity
		form.setPost(new TmPost());
		
		return "home";
	}
	/**
	 * フォームの入力情報・セッションをもとに投稿情報を生成し、DBへ登録する。
	 * @param form フォーム
	 * @param customUser  セッションへ格納されているログインユーザ情報
	 * @return ホーム画面へのパス
	 */
	@RequestMapping(value= "/save")
	private String save(HomeForm form, @AuthenticationPrincipal CustomUser customUser) {
		homeService.save(form.getPost(), customUser.getUserId());
		
		return "redirect:/home/init";
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
		
		return "redirect:/home/init";
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
		
		return "redirect:/home/init";
	}
	
	/**
	 * 検索ボタン押下時に呼ばれる処理
	 * 検索文字列をModelへ格納し、検索コントローラへリダイレクト
	 * @param form ホーム画面用のフォーム
	 * @param attr リダイレクトパラメータを利用するためのModel
	 * @return 検索画面の検索処理
	 */
	@RequestMapping("/search")
	private String search(HomeForm form, RedirectAttributes attr) {
		//TODO 検索クエリ文字列のサニタイズ化
		String unsafeQueryString = form.getSearchQuery();
		attr.addFlashAttribute("searchQueryString", unsafeQueryString);
		return "redirect:/search/init";
		
	}
	
	/**
	 * コントローラ内で共通のサインアップフォームを生成。
	 * @return 生成されたフォームオブジェクト
	 */
	@ModelAttribute("homeForm")
	HomeForm homeForm() {
		return new HomeForm();
	}
}
