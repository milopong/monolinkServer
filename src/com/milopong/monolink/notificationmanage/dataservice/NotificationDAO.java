package com.milopong.monolink.notificationmanage.dataservice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.notificationmanage.common.Notification;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Repository
public class NotificationDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	
	public int delete(int no) {
		Session session = sessionFactory.getCurrentSession();
		Notification notification = (Notification) session.get(Notification.class, new Integer(no));

		session.delete(notification);
		return 1;
	}

	public int regist(Notification notificaion) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(notificaion);

	}

	public List<Notification> selectByReciever(Member member) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Notification.class).add(Restrictions.eq("member_reciever", member));

		return criteria.list();

	}
}
