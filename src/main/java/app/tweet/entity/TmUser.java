package app.tweet.entity;
// Generated 2019/02/17 14:30:56 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TmUser generated by hbm2java
 */
@Entity
@Table(name = "tm_user")
public class TmUser implements java.io.Serializable {

	private int userId;
	private String userName;
	private String password;
	private String userNickname;
	private String bio;
	private Date lastLoginTs;
	private Date createTs;
	private Date birthDate;
	private Boolean deleteFlg;
	private Integer profileImageId;

	public TmUser() {
	}

	public TmUser(int userId, String userName, String password) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}

	public TmUser(int userId, String userName, String password, String userNickname, String bio, Date lastLoginTs,
			Date createTs, Date birthDate, Boolean deleteFlg, int profileImageId) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.userNickname = userNickname;
		this.bio = bio;
		this.lastLoginTs = lastLoginTs;
		this.createTs = createTs;
		this.birthDate = birthDate;
		this.deleteFlg = deleteFlg;
	}

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

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_ts", length = 29)
	public Date getLastLoginTs() {
		return this.lastLoginTs;
	}

	public void setLastLoginTs(Date lastLoginTs) {
		this.lastLoginTs = lastLoginTs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_ts", length = 29)
	public Date getCreateTs() {
		return this.createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date", length = 13)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "delete_flg")
	public Boolean getDeleteFlg() {
		return this.deleteFlg;
	}

	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	
	
	@Column(name = "profile_image_id")
	public Integer getProfileImageId() {
		return profileImageId;
	}

	
	public void setProfileImageId(Integer profileImageId) {
		this.profileImageId = profileImageId;
	}

	/**
	 * 永続化前に呼ばれるメソッド
	 * デフォルト値を設定する
	 */
	@PrePersist
	public void prePersist()
	{
		//削除フラグが未設定の場合falseをセット
	    if ( getDeleteFlg() == null )
	    {
	        setDeleteFlg(false);
	    }
	    
	    if (getProfileImageId() == null){
	    	setProfileImageId(0);
	    }
	}

}
