package com.milopong.monolink.favoritemanager.dataservice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.favoritemanager.common.Favorite;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Repository
public class FavoriteDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public int regist(Favorite favorite) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(favorite);
	}

	public void unRegistBySchedule(Member member, Schedule schedule) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria =session.createCriteria(Favorite.class).add(Restrictions.eq("member", member))
				.add(Restrictions.eq("schedule", schedule));
		Favorite favorite = (Favorite)criteria.list().get(0);
		session.delete(favorite);
	}
	
	public void unRegistByEvent(Member member, Event event) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria =session.createCriteria(Favorite.class).add(Restrictions.eq("member", member))
				.add(Restrictions.eq("event", event));
		Favorite favorite = (Favorite)criteria.list().get(0);
		session.delete(favorite);
	}

	public List<Favorite> selectByMember(Member member) {
		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(
				"select * from favorite where date_format(startTime,\"%Y-%m-%d %H-%i\") > date_format(now(),\"%Y-%m-%d %H-%i\") && member_no ="
						+ member.getNo() + " Order by startTime;")
				.addEntity(Favorite.class).list();
	}
	
	public int delete(int no) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from favorite where schedule_no=" + no).executeUpdate();
		return 1;
	}

}
