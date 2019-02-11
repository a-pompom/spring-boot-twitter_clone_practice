package app.tweet.entity;
// Generated 2019/02/10 10:45:15 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TsFollow generated by hbm2java
 */
@Entity
@Table(name = "ts_follow", schema = "public")
public class TsFollow implements java.io.Serializable {

	private TsFollowId id;
	private Date followTs;

	public TsFollow() {
	}

	public TsFollow(TsFollowId id) {
		this.id = id;
	}

	public TsFollow(TsFollowId id, Date followTs) {
		this.id = id;
		this.followTs = followTs;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "followUserId", column = @Column(name = "follow_user_id", nullable = false)),
			@AttributeOverride(name = "followerUserId", column = @Column(name = "follower_user_id", nullable = false)) })
	public TsFollowId getId() {
		return this.id;
	}

	public void setId(TsFollowId id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "follow_ts", length = 29)
	public Date getFollowTs() {
		return this.followTs;
	}

	public void setFollowTs(Date followTs) {
		this.followTs = followTs;
	}

}
