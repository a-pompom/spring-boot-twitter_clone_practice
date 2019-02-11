package app.tweet.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ホーム画面を管理するコントローラ
 * @author aoi
 *
 */
@RequestMapping("/home")
@Controller
public class HomeController {

	@RequestMapping("/init")
	private String init() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		System.out.println(userName);
		
		return "home";
	}
}
