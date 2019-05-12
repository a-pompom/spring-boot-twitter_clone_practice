package app.tweet.entity.ext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import app.tweet.base.BaseEntity;

/**
 * 参照中のユーザのフォローユーザ情報、ログインユーザがフォローしているかのフラグを持つ拡張エンティティ
 * @author aoi
 *
 */
@Entity
public class TmUserFollowExt extends BaseEntity {
	
	//フォロー画面へ表示するユーザ情報
	private int userId;
	private String userName;
	private String userNickname;
	private String bio;
	
	//ログインユーザが対象ユーザをフォローしているかどうかの状態を表すフラグ
	private boolean loginFollowFlg;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", nullable = false, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_nickname", length = 30)
	public String getUserNickname() {
		return this.userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	@Column(name = "bio")
	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	@Column(name = "login_follow_flg")
	public boolean isLoginFollowFlg() {
		return loginFollowFlg;
	}

	public void setLoginFollowFlg(boolean loginFollowFlg) {
		this.loginFollowFlg = loginFollowFlg;
	}
	
	

}
