package app.tweet.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * SpringSecurityの認証に用いるユーザをカスタムしたクラス
 * セッション上にDBの主キーのユーザIDを持たせられるようプロパティを追加
 * @author aoi
 *
 */
public class CustomUser extends User{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * セッションへ持たせるDBの主キー
	 */
	private int userId;
	
	/**
	 * コンストラクタ
	 * @param username DBから取得したユーザ名
	 * @param password DBから取得したパスワード
	 * @param authorities 権限情報のリスト
	 */
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
	
	

}
