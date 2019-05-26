package app.tweet.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.tweet.base.BaseController;
import app.tweet.base.TweetConst;
import app.tweet.base.TweetConst.UserViewMethod;
import app.tweet.entity.TmUser;
import app.tweet.form.UserForm;
import app.tweet.security.CustomUser;
import app.tweet.service.ImageService;
import app.tweet.service.MentionService;
import app.tweet.service.UserService;

/**
 * ユーザ画面を管理するためのコントローラ
 * @author aoi
 *
 */
@Controller
@SessionAttributes(names = "referUser")
public class UserController extends BaseController{
	
	/**
	 * ユーザサービス
	 */
	@Autowired
	UserService userService;
	
	/**
	 * メンションサービス
	 */
	@Autowired
	MentionService mentionService;
	
	/**
	 * イメージサービス
	 */
	@Autowired
	ImageService imageService;
	
	/**
	 * 「ユーザID/メソッド名」で遷移したときに呼ばれるメソッド
	 * 「.css」など静的リソースも拾ってしまうので、正規表現で除外
	 * @param request URI
	 * @param form ユーザフォーム
	 * @param model セッションへ参照中のユーザを格納するためのモデル
	 * @param customUser ログイン中のユーザ情報
	 * @return ユーザ画面
	 */
	@RequestMapping(value = "/{regUserName:(?!^.$)|^[a-zA-Z0-9_-]+$}/{regMethod:(?!^[.]+$)|^[a-z]+$}", method = RequestMethod.GET)
	private String initUserWithMethod(HttpServletRequest request, UserForm form, Model model,
			@AuthenticationPrincipal CustomUser customUser) {
		return initUser(request, form, model, customUser);
	}
	
	/**
	 * ユーザIDでGETリクエストが送信されたときに呼ばれるメソッド
	 * 指定されたユーザ名と対応したユーザのユーザ画面を表示する。
	 * @param request リクエスト情報 ユーザ名、follow,favoriteのような処理情報をもつ
	 * @param form ユーザフォーム
	 * @return ユーザ画面
	 */
	@RequestMapping(value = "/{reg:(?!^[.]+$)|^[a-zA-Z0-9_-]+$}", method = RequestMethod.GET)
	private String initUser(HttpServletRequest request, UserForm form, Model model,
							@AuthenticationPrincipal CustomUser customUser) {
		//TODO ユーザ名のサニタイズメソッドの実装
		String unSafeUserName = request.getRequestURI();
		//URIからユーザ名部分、処理部分を取得
		String[] userNameAndMethod = unSafeUserName.split("/");
		String userName = userNameAndMethod[1];
		
		//ログイン中でない場合はログイン画面へ飛ばす
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated()) {
			return "login";
		}
		
		//リクエストパラメータのユーザ名をキーにDBを検索し、得られたユーザエンティティでプロフィール情報を取得
		TmUser user = userService.getByUserName(userName);
		//DBに該当するユーザが存在しない場合はログイン画面へ戻す
		if (user == null) {
			return "login";
		}
		
		//参照中のユーザ情報をセッションへ格納
		model.addAttribute("referUser", user);
		form.setUserName(customUser.getUsername());
		form.setLoggedInFollowflg(userService.isLoggedInFollowing(user.getUserId(), customUser.getUserId()));
		form.setImagePath(imageService.getLoginIconPath(customUser.getUserId()));
		
		//フォームへユーザ情報・ユーザ投稿情報をセット
		form.setLoggedInUser(customUser.getUserId() == user.getUserId() ? true : false);
		form.setDto(userService.getUserProfile(user.getUserId()));
		return setViewInfoByMethodURI(form, userNameAndMethod, user.getUserId(), customUser.getUserId());
	}
	
	/**
	 * URIの値に応じて画面の表示情報を切り替える
	 * @param form
	 * @param userNameAndMethod
	 * @param referUserId
	 * @param loginUserId
	 */
	private String setViewInfoByMethodURI(UserForm form, String[] userNameAndMethod, int referUserId, int loginUserId){
		//userNameAndMethodのMethod部分が存在しない場合、投稿情報
		if (userNameAndMethod.length < 3) {
			form.setPostDto(userService.getUserPost(referUserId, loginUserId));
			form.setMethod("post");
			return "user";
		}
		//methodの値に応じてフォロー、フォロワー、お気に入りいずれの情報を取得するか分岐
		String method = userNameAndMethod[2].toUpperCase();
		UserViewMethod v;
		v = UserViewMethod.valueOf(method);
		
		switch (v) {
		case POST:
			//TODO 画像を取得する際に
			form.setPostDto(userService.getUserPost(referUserId, loginUserId));
			form.setMethod("post");
			return "user";
		case FAVORITE:
			form.setPostDto(userService.getFavoritePost(referUserId, loginUserId));
			form.setMethod("favorite");
			return "user";
		case FOLLOWING:
			form.setDto(userService.getUserProfile(referUserId));
			//フォロー情報取得処理
			form.setFollowDto(userService.getUserFollowing(referUserId, loginUserId));
			form.setMethod("following");
			return "user";
		case FOLLOWER:
			//フォロワーー情報取得処理
			form.setFollowDto(userService.getUserFollower(referUserId, loginUserId));
			form.setMethod("follower");
			return "user";
		default:
			return "user";
		}
	}
	
	
	
	/**
	 *  フォームの入力値でユーザ情報を編集する
	 * @param form ユーザフォーム
	 * @param user ログインユーザ
	 * @param method 参照中の画面
	 * @return 参照中の画面
	 */
	@RequestMapping(value = "/user/editUser/{method}", method = RequestMethod.POST)
	private ResponseEntity<TmUser>  editUser(UserForm form, @AuthenticationPrincipal CustomUser user, 
			@PathVariable("method")String method, Model model) {
		
		model.addAttribute("userForm.dto.user.bio", form.getDto().getUser().getBio());
		
		
//		if (form.getProfileImage().isEmpty()) {
//			//参照中の画面へ戻る
//			return "redirect:/" + user.getUsername() + "/" + method;
//		}
		
//		try {
//			byte[] bytes = form.getProfileImage().getBytes();
//			int extLocation = form.getProfileImage().getOriginalFilename().lastIndexOf(".");
//			String imageName = TweetConst.IMAGE_FOLDER + user.getUsername() + form.getProfileImage().getOriginalFilename().substring(extLocation);
//			File tempFile = new File(imageName);
//		    tempFile.createNewFile();
//			Path path = Paths.get(TweetConst.IMAGE_FOLDER + user.getUsername() + form.getProfileImage().getOriginalFilename().substring(extLocation));
//			Files.write(path, bytes);
//			String imagePath = imageName.replace("static", "");
//			int profileImageId = imageService.saveImage(imagePath);
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		userService.editUser(form.getDto(), 0);
		return ResponseEntity.ok(form.getDto().getUser());
//		//参照中の画面へ戻る
//		return "redirect:/" + user.getUsername() + "/" + method;		
		
	}
	
	
	/**
	 * 参照中のユーザをフォローする。
	 * @param user ログインユーザ
	 * @param referUser 参照中の画面のユーザEntity
	 * @param followUserId フォロー対象のユーザID
	 * @return 参照中のユーザ画面
	 */
	@RequestMapping(value = "/user/{followUserId}/do_follow/{method}")
	private String doFollow(@AuthenticationPrincipal CustomUser user,
			@ModelAttribute("referUser")TmUser referUser, 
			@PathVariable("followUserId")int followUserId, @PathVariable("method")String method) {
		if (method == null) {
			userService.doFollow(user.getUserId(), referUser.getUserId());
			return "redirect:/" + referUser.getUserName();
		}
		
		userService.doFollow(user.getUserId(), followUserId);
		return "redirect:/" + referUser.getUserName() + "/" + method;
	}
	
	/**
	 * 選択された投稿を共有する。
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @param postUserId 選択された投稿のID
	 * @param referUser 参照中のユーザEntity
	 * @param method どの画面から遷移してきたかを保持する
	 * @return ユーザ画面
	 */
	@RequestMapping(value = "/user/share/{postId}/{method}")
	private String share(@AuthenticationPrincipal CustomUser customUser,
			@PathVariable("postId")int postId, @ModelAttribute("referUser")TmUser referUser,
			@PathVariable("method")String method) {
		mentionService.share(customUser.getUserId(), postId);
		
		return "redirect:/" + referUser.getUserName() + "/" + method;
	}
	
	/**
	 * 選択された投稿をお気に入りへ登録する。
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @param postUserId 選択された投稿のID
	 * @return
	 */
	@RequestMapping(value = "/user/favorite/{postId}/{method}")
	private String favorite(@AuthenticationPrincipal CustomUser customUser,
			@PathVariable("postId")int postId, @PathVariable("method")String method,
			@ModelAttribute("referUser")TmUser referUser) {
		mentionService.favorite(customUser.getUserId(), postId);
		
		return "redirect:/" + referUser.getUserName() + "/" + method;
	}
	
	/**
	 * 検索ボタン押下時に呼ばれる処理
	 * 検索文字列をModelへ格納し、検索コントローラへリダイレクト
	 * @param form ユーザ画面用のフォーム
	 * @param attr リダイレクトパラメータを利用するためのModel
	 * @return 検索画面の検索処理
	 */
	@RequestMapping(value = "/user/search", method = RequestMethod.POST)
	private String search(UserForm form, RedirectAttributes attr) {
		//TODO 検索クエリ文字列のサニタイズ化
		String unsafeQueryString = form.getSearchQuery();
		attr.addFlashAttribute("searchQueryString", unsafeQueryString);
		return "redirect:/search/init";
		
	}
	
	
	
	/**
	 * コントローラ内で共通のユーザフォームを生成。
	 * @return 生成されたフォームオブジェクト
	 */
	@ModelAttribute("userForm")
	UserForm userForm() {
		return new UserForm();
	}

}
