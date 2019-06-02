package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.tweet.security.CustomUser;
import app.tweet.service.MentionService;

/**
 * メンションを管理するコントローラ
 * @author aoi
 *
 */
@RequestMapping("/mention")
@Controller
public class MentionController {
	
	/**
	 * メンションサービス
	 */
	@Autowired
	private MentionService mentionService;
	
	/**
	 * 選択された投稿を共有する。
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @param postUserId 選択された投稿のID
	 * @return ホーム画面
	 */
	@RequestMapping(value = "/share/{postId}")
	private String share(@AuthenticationPrincipal CustomUser customUser, @PathVariable("postId")int postId, Model model) {
		mentionService.share(customUser.getUserId(), postId);
		
		//レンダリングに必要な情報をリクエストスコープへセット
		model.addAttribute("mentionType", "share");
		model.addAttribute("mentionIcon", "sync");
		model.addAttribute("isLoginMentioned", true);
		model.addAttribute("postId", postId);
		return "post_mention_fragment :: mention";
	}
	
	/**
	 * 選択された投稿を共有する。
	 * @param customUser セッションに格納されているログインユーザ情報
	 * @param postUserId 選択された投稿のID
	 * @return ホーム画面
	 */
	@RequestMapping(value = "/favorite/{postId}")
	private String favorite(@AuthenticationPrincipal CustomUser customUser, @PathVariable("postId")int postId, Model model) {
		mentionService.favorite(customUser.getUserId(), postId);
		
		//レンダリングに必要な情報をリクエストスコープへセット
		model.addAttribute("mentionType", "favorite");
		model.addAttribute("mentionIcon", "star");
		model.addAttribute("isLoginMentioned", true);
		model.addAttribute("postId", postId);
		
		return "post_mention_fragment :: mention";
	}
}
