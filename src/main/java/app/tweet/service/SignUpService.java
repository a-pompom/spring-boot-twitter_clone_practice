package app.tweet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.tweet.dao.TmUserDao;
import app.tweet.entity.TmUser;

/**
 * サインアップ機能を管理するサービス
 * @author aoi
 */
@Service
public class SignUpService {
	
	/**
	 * ユーザテーブルへアクセスするためのDaoクラス
	 */
	@Autowired
	TmUserDao dao;
	
	/**
	 * ユーザ情報エンティティをDBへ登録する。
	 * @param user フォームの入力値をもとに生成されたユーザエンティティ
	 */
	@Transactional
	public void saveOrUpdate(TmUser user) {
		dao.saveOrUpdate(user);
	}

}
