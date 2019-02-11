package app.tweet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ViewNameに関する設定ファイル
 * パスとView名の対応づけを行う
 * @author aoi
 *
 */
@Configuration
public class MVCConfig implements WebMvcConfigurer {
	
	/**
	 * 「/login、ルートパス」からlogin.htmlを呼び出す
	 * @param registry
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/").setViewName("login");
	}

}
