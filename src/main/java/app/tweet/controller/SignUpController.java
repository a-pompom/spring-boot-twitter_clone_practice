package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import app.tweet.dao.TsFollowDao;
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
	
	@Autowired
	SignUpService signUpService;
	
	@Autowired
	TsFollowDao daoFollow;
	
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
	 * @return
	 */
	@RequestMapping("/save")
	private String save(SignUpForm form) {
		signUpService.saveOrUpdate(form.getDto().getUser());
		return "sign-up";
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
