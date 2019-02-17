package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import app.tweet.dto.HomeDto;
import app.tweet.form.HomeForm;
import app.tweet.service.HomeService;

/**
 * ホーム画面を管理するコントローラ
 * @author aoi
 *
 */
@RequestMapping("/home")
@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeService;

	@RequestMapping("/init")
	private String init(HomeForm form) {
		
		//フォームに入力値が残っている場合があるので、初期化
		form.setDto(new HomeDto());
		
		return "home";
	}
	/**
	 * フォームの入力情報・セッションをもとに投稿情報を生成し、DBへ登録する。
	 * @param form フォーム
	 * @return ホーム画面へのパス
	 */
	@RequestMapping(value="save")
	private String save(HomeForm form) {
		homeService.save(form.getDto());
		
		return init(form);
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
