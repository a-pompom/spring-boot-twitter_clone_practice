package app.tweet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import app.tweet.dto.FollowDto;
import app.tweet.entity.TmUser;
import app.tweet.form.UserForm;
import app.tweet.security.CustomUser;
import app.tweet.service.UserService;

/**
 * フォローユーザの一覧画面を管理するコントローラ
 * @author aoi
 *
 */
@Controller
public class FollowController {
	
	/**
	 * ユーザサービス
	 */
	@Autowired
	private UserService userService;
	
	
	private String initUserFollow(HttpServletRequest request, UserForm form, @AuthenticationPrincipal CustomUser customUser) {
		String unSafeUserName = request.getRequestURI();
		String userName = unSafeUserName.replace("/following", "").replace("/", "");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//ログイン中でない場合はログイン画面へ飛ばす∂
		if (auth.getName().equals("anonymousUser")) {
			return "login";
		}
		
		//リクエストパラメータのユーザ名をキーにDBを検索し、得られたユーザエンティティでプロフィール情報を取得
		TmUser user = userService.getByUserName(userName);
		
		if (user == null) {
			return "login";
		}
		form.setLoggedInUser(customUser.getUserId() == user.getUserId() ? true : false);
		form.setDto(userService.getUserProfile(user.getUserId()));
		
		//フォロー情報取得処理
		form.setFollowDto(new FollowDto());
		form.getFollowDto().setFollowList(userService.findFollowUserList(user.getUserId(), customUser.getUserId()));
		
		return "following";
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
