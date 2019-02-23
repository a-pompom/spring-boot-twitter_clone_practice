package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import app.tweet.form.UserForm;
import app.tweet.security.CustomUser;
import app.tweet.service.UserService;

/**
 * ユーザ画面を管理するためのコントローラ
 * @author aoi
 *
 */
@RequestMapping(value = "/user")
@Controller
public class UserController {
	
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
	@RequestMapping(value = "/init")
	private String init(UserForm form) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser user = (CustomUser)auth.getPrincipal();
		
		form.setDto(userService.getUserProfile(user.getUserId()));
		
		return "user";
	}
	
	
	/**
	 * コントローラ内で共通のサインアップフォームを生成。
	 * @return 生成されたフォームオブジェクト
	 */
	@ModelAttribute("userForm")
	UserForm userForm() {
		return new UserForm();
	}

}
