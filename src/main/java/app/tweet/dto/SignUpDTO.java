package app.tweet.dto;

import app.tweet.entity.TmUser;

/**
 * ユーザ登録画面での入力情報を保持するためのDTO
 * @author aoi
 *
 */
public class SignUpDTO {
	
	/**
	 * ユーザ情報を格納したユーザエンティティ
	 */
	private TmUser user;

	/**ユーザ情報を取得する。
	 * @return the user
	 */
	public TmUser getUser() {
		return user;
	}

	/**ユーザ情報をセットする。
	 * @param user the user to set
	 */
	public void setUser(TmUser user) {
		this.user = user;
	}
	
	

}
