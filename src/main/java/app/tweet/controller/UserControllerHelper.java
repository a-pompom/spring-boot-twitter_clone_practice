package app.tweet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.service.UserService;

/**
 * ユーザ画面のうち、表示情報の取得処理を扱うためのヘルパークラス　
 * @author aoi
 *
 */
@Component
public class UserControllerHelper {
	
	@Autowired
	private UserService userService;

}
