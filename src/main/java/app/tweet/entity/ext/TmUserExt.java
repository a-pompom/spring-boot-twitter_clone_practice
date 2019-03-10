package app.tweet.entity.ext;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ユーザ情報、投稿数を格納した拡張エンティティ
 * @author aoi
 *
 */
@Entity
public class TmUserExt {
	
	//ユーザユーザプロフィール
	private int userId;
	private String userName;
	private String userNickname;
	private String bio;
	private Date createTs;
	private Date birthDate;
	private String imagePath;
	
	//ユーザ基本情報
	private Integer postCount; //ユーザの投稿数
	private Integer followCount; //ユーザのフォロー数
	private Integer followerCount; //ユーザのフォロワー数
	private Integer favoriteCount; //ユーザのお気に入り数

	public TmUserExt() {
		
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
	
	@Column(name = "image_path")
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	/*			 ユーザプロフィールここまで 			*/
	
	/*			 ユーザ基本情報	 			*/
	@Column(name = "post_count")
	public Integer getPostCount() {
		return this.postCount;
	}

	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}

	@Column(name = "follow_count")
	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	@Column(name = "follower_count")
	public Integer getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	/**
	 * @return the favoriteCount
	 */
	@Column(name = "favorite_count")
	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	/**
	 * @param favoriteCount the favoriteCount to set
	 */
	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
	
	
	
	
}
