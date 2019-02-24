package app.tweet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import app.tweet.base.BaseController;
import app.tweet.base.TweetConst.UserViewMethod;
import app.tweet.dto.FollowDto;
import app.tweet.entity.TmUser;
import app.tweet.form.UserForm;
import app.tweet.security.CustomUser;
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
	 * 初期処理 ユーザの投稿、ユーザ情報、ユーザ基本情報を取得し、フォームへセットする。
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/user/init")
	private String init(UserForm form, @AuthenticationPrincipal CustomUser user) {
		form.setDto(userService.getUserProfile(user.getUserId()));
		form.setLoggedInUser(true);
		return "user";
	}
	
	@RequestMapping(value = "/{regUserName:(?!^.$)|^[a-z0-9_]+$}/{regMethod:(?!^.$)|^[a-z]+$}")
	private String initUserWithMethod(HttpServletRequest request, UserForm form, Model model,
			@AuthenticationPrincipal CustomUser customUser) {
		return initUser(request, form, model, customUser);
	}
	
	/**
	 * ユーザ名でGETリクエストが送信されたときに呼ばれるメソッド
	 * 指定されたユーザ名と対応したユーザのユーザ画面を表示する。
	 * @param request リクエスト情報 ユーザ名、follow,favoriteのような処理情報をもつ
	 * @param form ユーザフォーム
	 * @return ユーザ画面
	 */
	@RequestMapping(value = "/{reg:(?!^.$)|^[a-z0-9_]+$}")
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
			
			form.setMethod("post");
			return "user";
		}
		//methodの値に応じてフォロー、フォロワー、お気に入りいずれの情報を取得するか分岐
		String method = userNameAndMethod[2].toUpperCase();
		UserViewMethod v;
		v = UserViewMethod.valueOf(method);
		switch (v) {
		case FAVORITE:
			return "user";
		case FOLLOWING:
			form.setDto(userService.getUserProfile(referUserId));
			//フォロー情報取得処理
			form.setFollowDto(new FollowDto());
			form.getFollowDto().setFollowList(userService.findFollowUserList(referUserId, loginUserId));
			form.setMethod("following");
			return "user";
		case FOLLOWER:
			//フォロー情報取得処理
			form.setFollowDto(new FollowDto());
			form.getFollowDto().setFollowList(userService.findFollowerUserList(referUserId, loginUserId));
			form.setMethod("follower");
			return "user";
		default:
			return "user";
		}
	}
	
	/**
	 * 参照中のユーザをフォローする。
	 * @param form ユーザフォーム
	 * @return 参照中のユーザ画面
	 */
	@RequestMapping(value = "/*/do_follow")
	private String doFollow(@AuthenticationPrincipal CustomUser user, @ModelAttribute("referUser")TmUser referUser) {
		
		userService.doFollow(user.getUserId(), referUser.getUserId());
		return "redirect:/" + referUser.getUserName();
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
