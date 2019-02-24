package app.tweet.base;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import app.tweet.security.CustomUser;

@Controller
public class BaseController {
	
	/**
	 * セッションに格納されているユーザIDを取得する。
	 * @return ログインユーザID
	 */
	protected int getUserBySecurityContext() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser user = (CustomUser)auth.getPrincipal();
		
		return user.getUserId();
	}

}
