package com.milopong.monolink.eventmanage.dataservice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Repository
public class EventDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Event> selectAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(Event.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<Event> events = (List<Event>) cr.list();
		return events;
	}

	public Event selectByNo(int no) {
		Session session = sessionFactory.getCurrentSession();
		Event event = (Event) session.get(Event.class, new Integer(no));
		return event;
	}

}
