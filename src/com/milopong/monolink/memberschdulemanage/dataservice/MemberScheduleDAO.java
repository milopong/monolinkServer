package com.milopong.monolink.memberschdulemanage.dataservice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.common.MemberSchedule;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Repository
public class MemberScheduleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public int regist(MemberSchedule memberSchedule) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MemberSchedule.class)
				.add(Restrictions.eq("member", memberSchedule.getMember()))
				.add(Restrictions.eq("schedule", memberSchedule.getSchedule()));
		List<MemberSchedule> schedules = criteria.list();

		if (schedules.get(0) == null) {
			return (Integer) session.save(memberSchedule);
		} else {
			return 0;
		}

	}

	public List<MemberSchedule> selectByMember(Member member) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(MemberSchedule.class).add(Restrictions.eq("member", member));
		List<MemberSchedule> participants = criteria.list();
		return participants;
	}

	public List<MemberSchedule> selectBySchedule(Schedule schedule) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(MemberSchedule.class).add(Restrictions.eq("schedule", schedule));
		List<MemberSchedule> participants = criteria.list();
		return participants;
	}

	public int delete(int no) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery("delete from memberschedule where schedule_no=" + no).executeUpdate();
		return 1;
	}

	public int deleteMember(int scheduleNo, int memberNo) {
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(
				"delete from memberschedule where schedule_no=" + scheduleNo + "&& member_no=" + memberNo)
				.executeUpdate();
		return 1;
	}

}
