package app.tweet.entity.ext;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

/**
 * 投稿者情報を含めた投稿の拡張エンティティ
 * @author aoi
 *
 */
@Entity
public class TmPostExt {

	private int postId;
	private String post;
	private Integer postUserId;
	private Date postTs;
	private Boolean deleteFlg;
	private String userName;
	private String userNickname;
	private String imagePath;
	//ログインユーザが投稿をお気に入り登録しているかを管理するフラグ
	private Boolean loginUserFavFlg;
	//ログインユーザが投稿を共有しているかを管理するフラグ
	private Boolean loginUserShareFlg;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "post_id", unique = true, nullable = false)
	public int getPostId() {
		return this.postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Column(name = "post", nullable = false)
	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	

	
	@Column(name = "image_path")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "post_user_id")
	public Integer getPostUserId() {
		return this.postUserId;
	}

	public void setPostUserId(Integer postUserId) {
		this.postUserId = postUserId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "post_ts", length = 29)
	public Date getPostTs() {
		return this.postTs;
	}

	public void setPostTs(Date postTs) {
		this.postTs = postTs;
	}

	@Column(name = "delete_flg")
	public Boolean getDeleteFlg() {
		return this.deleteFlg;
	}

	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	
	/*			投稿情報ここまで			*/
	
	/*			投稿者情報			*/
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
	/*			投稿者情報ここまで			*/
	/*			 投稿のお気に入りフラグ			*/
	/**
	 * @return the loginUserFavFlg
	 */
	@Column(name = "login_fav_flg")
	public Boolean getLoginUserFavFlg() {
		return loginUserFavFlg;
	}

	/**
	 * @param loginUserFavFlg the loginUserFavFlg to set
	 */
	public void setLoginUserFavFlg(Boolean loginUserFavFlg) {
		this.loginUserFavFlg = loginUserFavFlg;
	}

	/**
	 * @return the loginUserShareFlg
	 */
	@Column(name = "login_share_flg")
	public Boolean getLoginUserShareFlg() {
		return loginUserShareFlg;
	}

	/**
	 * @param loginUserShareFlg the loginUserShareFlg to set
	 */
	public void setLoginUserShareFlg(Boolean loginUserShareFlg) {
		this.loginUserShareFlg = loginUserShareFlg;
	}
	
	
	
	
	
}
