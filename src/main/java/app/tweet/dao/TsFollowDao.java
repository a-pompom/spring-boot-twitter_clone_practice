package app.tweet.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.tweet.base.BaseDao;
import app.tweet.entity.TsFollow;

@Component
public class TsFollowDao extends BaseDao<TsFollow> {
	
	@Autowired
	EntityManager em;
	
	public List<TsFollow> findFollowList(){
		String query = "select * from ts_follow";
		return em.createNativeQuery(query, TsFollow.class).getResultList();
	}

}
