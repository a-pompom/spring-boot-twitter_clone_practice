package app.tweet.entity;
// Generated 2019/02/10 10:45:15 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TsFavoriteId generated by hbm2java
 */
@Embeddable
public class TsFavoriteId implements java.io.Serializable {

	private int favoriteUserId;
	private int favoritePostId;

	public TsFavoriteId() {
	}

	public TsFavoriteId(int favoriteUserId, int favoritePostId) {
		this.favoriteUserId = favoriteUserId;
		this.favoritePostId = favoritePostId;
	}

	@Column(name = "favorite_user_id", nullable = false)
	public int getFavoriteUserId() {
		return this.favoriteUserId;
	}

	public void setFavoriteUserId(int favoriteUserId) {
		this.favoriteUserId = favoriteUserId;
	}

	@Column(name = "favorite_post_id", nullable = false)
	public int getFavoritePostId() {
		return this.favoritePostId;
	}

	public void setFavoritePostId(int favoritePostId) {
		this.favoritePostId = favoritePostId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TsFavoriteId))
			return false;
		TsFavoriteId castOther = (TsFavoriteId) other;

		return (this.getFavoriteUserId() == castOther.getFavoriteUserId())
				&& (this.getFavoritePostId() == castOther.getFavoritePostId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getFavoriteUserId();
		result = 37 * result + this.getFavoritePostId();
		return result;
	}

}
