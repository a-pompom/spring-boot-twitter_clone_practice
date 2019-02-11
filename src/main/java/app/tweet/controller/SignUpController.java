package app.tweet.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.tweet.dto.SignUpDTO;
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
		SignUpDTO dto = new SignUpDTO();
		dto.setUser(new TmUser());
		
		//フォームへDTOをセット
		form.setDto(dto);
		
		return "sign-up";
	}
	
	/**
	 * フォームへ入力されたユーザ情報をDBへ登録する。
	 * @return 認証処理→ホーム画面へ遷移
	 */
	@RequestMapping("/save")
	private ModelAndView save(HttpServletRequest request, SignUpForm form) {
		signUpService.saveOrUpdate(form.getDto().getUser());
		
		return authWithAuthManager(request, form.getDto().getUser().getUserName(), form.getDto().getUser().getPassword());
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
