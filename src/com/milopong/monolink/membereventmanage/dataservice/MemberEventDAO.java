package com.milopong.monolink.membereventmanage.dataservice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.membereventmanage.common.MemberEvent;

@Repository
public class MemberEventDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public List<MemberEvent> selectBySchedule(Event event) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(MemberEvent.class).add(Restrictions.eq("event", event));
		List<MemberEvent> participants = criteria.list();
		return participants;
	}
}
