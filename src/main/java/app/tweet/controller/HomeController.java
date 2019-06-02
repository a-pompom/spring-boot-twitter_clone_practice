package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.tweet.dto.PostDto;
import app.tweet.entity.TmPost;
import app.tweet.form.HomeForm;
import app.tweet.security.CustomUser;
import app.tweet.service.HomeService;
import app.tweet.service.ImageService;

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
	 * イメージサービス
	 */
	@Autowired
	private ImageService imageService;

	/**
	 * 初期化処理
	 * 投稿情報及びログイン中のユーザ情報を取得
	 * @param form ホーム画面用のフォーム
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @return ホーム画面
	 */
	@RequestMapping("/init")
	private String init(HomeForm form, @AuthenticationPrincipal CustomUser customUser) {
		//タイムライン情報として以下を取得 ※投稿には投稿者情報も含む
		//・自分自身の投稿
		//・フォローしているユーザの投稿
		//・フォローしているユーザが共有した投稿
		form.setPostDto(homeService.findTheUserAndFollowPostList(customUser.getUserId()));
		
		//ユーザ画面への遷移時に必要となるログインユーザ名をフォームへセット　
		form.setUserName(customUser.getUsername());
		form.setImagePath(imageService.getLoginIconPath(customUser.getUserId()));
		
		//ログインユーザの入力した投稿情報を格納するEntity
		form.setPost(new TmPost());
		
		return "home";
	}
	/**
	 * フォームの入力情報・セッションをもとに投稿情報を生成し、DBへ登録する。
	 * (非同期処理)
	 * リクエスト: 投稿内容
	 * レスポンス: 新規投稿をもとに生成された投稿のHTML
	 * @param form フォーム
	 * @param customUser  セッションへ格納されているログインユーザ情報
	 * @param model レンダリングに必要な情報をリクエストスコープに載せるためのモデル
	 * @return 投稿のHTMLをThymeleafでレンダリングでした結果が返る
	 */
	@RequestMapping(value= "/save")
	private String save(HomeForm form, @AuthenticationPrincipal CustomUser customUser, Model model) {
		//入力内容をDBへ登録し、返り値のエンティティをもとに表示情報をDBから取得
		TmPost newPost = homeService.save(form.getPost(), customUser.getUserId());
		PostDto dto = homeService.findNewPost(newPost.getPostId(), customUser.getUserId());
		
		//レンダリングに必要な情報をリクエストスコープへセット
		model.addAttribute("post", dto.getPostList().get(0));
		
		
		return "post_fragment :: thePost";
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
	 * コントローラ内で共通のホーム画面フォームを生成。
	 * @return 生成されたフォームオブジェクト
	 */
	@ModelAttribute("homeForm")
	HomeForm homeForm() {
		return new HomeForm();
	}
}
