package app.tweet.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import app.tweet.entity.TmUser;

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
	 * 参照しているユーザのユーザID
	 */
	private int referUserId;
	
	/**
	 * 参照しているユーザのユーザ名
	 */
	private String referUserName;
	
	/**
	 * 参照中のユーザ情報
	 */
	private TmUser referUser;
	
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

	/**
	 * @return the referUserId
	 */
	public int getReferUserId() {
		return referUserId;
	}

	/**
	 * @param referUserId the referUserId to set
	 */
	public void setReferUserId(int referUserId) {
		this.referUserId = referUserId;
	}

	/**
	 * @return the referUserName
	 */
	public String getReferUserName() {
		return referUserName;
	}

	/**
	 * @param referUserName the referUserName to set
	 */
	public void setReferUserName(String referUserName) {
		this.referUserName = referUserName;
	}

	/**
	 * @return the referUser
	 */
	public TmUser getReferUser() {
		return referUser;
	}

	/**
	 * @param referUser the referUser to set
	 */
	public void setReferUser(TmUser referUser) {
		this.referUser = referUser;
	}

	
	
	
	
	
	
	
	

}
