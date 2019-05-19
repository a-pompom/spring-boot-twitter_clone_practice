package app.tweet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.tweet.dto.SignUpDto;
import app.tweet.entity.TmUser;
import app.tweet.form.SignUpForm;
import app.tweet.service.SignUpService;

/**
 * ユーザ登録画面のコントローラ
 * @author aoi
 *
 */
@RequestMapping("/sign-up")
@Controller
public class SignUpController {
	
	/**
	 * ユーザ登録サービス
	 */
	@Autowired
	private SignUpService signUpService;
	
	/**
	 * ユーザ登録画面で入力された情報で認証を行うための機能
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	/**
	 * 初期処理 ユーザ情報を格納するためのDTO・ユーザエンティティを生成し、フォームへセット。
	 * @param form ユーザ登録フォーム
	 * @param model リクエストスコープ　
	 * @return ユーザ登録画面
	 */
	@RequestMapping("/init")
	private String init (SignUpForm form, Model model) {
		
		//フォームの入力情報を格納するためのDTO・ユーザエンティティを生成
		SignUpDto dto = new SignUpDto();
		
		//フォームへDTOをセット
		form.setDto(dto);
		
		return "sign-up";
	}
	
	/**
	 * フォームへ入力されたユーザ情報をDBへ登録する。
	 * @return 認証処理→ホーム画面へ遷移
	 */
	@RequestMapping("/save")
	private ModelAndView save(HttpServletRequest request,@Valid SignUpForm form, BindingResult result) {
		//TODO パスワードの一致チェックはVue.jsでやりたい
		//以下条件でバリデーション
		/*ユーザID
		 * ・空でない
		 * ・ユニーク
		 * ・半角英数及び「-_」のみで構成
		 * ・32文字以下
		 *パスワード
		 * ・空でない
		 * ・半角英数及び「-_」のみで構成
		 * ・32文字以下
		 */
		//パスワード、確認用パスワードが一致しているかサーバサイド側でも念のため確認
		if (!form.getDto().getPassword().equals(form.getConfirmPassword())) {
			FieldError error = new FieldError("confirmPassword", "confirmPassword", "error");
			result.addError(error);
		}
		
		if (result.hasErrors()) {
			return new ModelAndView("sign-up");
		}
		
		signUpService.saveOrUpdate(form.getDto());
		
		return authWithAuthManager(request, form.getDto().getUserId(), form.getDto().getPassword());
	}
	
	/**
	 * SpringSecurityの認証機能へ生成されたユーザエンティティのユーザ名・パスワードを渡し、認証する。
	 * @param request 
	 * @param username
	 * @param password
	 */
	public ModelAndView authWithAuthManager(HttpServletRequest request, String username, String password) {
		//ユーザ名・パスワードの組による認証を行うためのトークン
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		authToken.setDetails(new WebAuthenticationDetails(request));
		
		//生成したトークンを利用してSpringSecurityの認証処理を呼び出し
		Authentication authentication = authenticationManager.authenticate(authToken);
		//ログイン中のユーザ情報をエンティティのユーザ情報で上書き
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//Configを経由しない認証ではSuccessURLへ自動遷移しないので、ホーム画面へリダイレクト
		return new ModelAndView("redirect:/home/init");
	}
	
	/**
	 * コントローラ内で共通のサインアップフォームを生成。
	 * @return 生成されたフォームオブジェクト
	 */
	@ModelAttribute("signUpForm")
	SignUpForm signUpForm() {
		return new SignUpForm();
	}

}
