package com.milopong.monolink.schedulemanage.dataservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.friendmanage.common.Friend;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Repository
public class ScheduleDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Schedule selectByNo(int no) {
		Session session = sessionFactory.getCurrentSession();
		Schedule schedule = (Schedule) session.get(Schedule.class, new Integer(no));

		return schedule;
	}

	public void update(Schedule schedule) {
		Session session = sessionFactory.getCurrentSession();
		session.update(schedule);
	}

	public int regist(Schedule schedule) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(schedule);

	}

	public List<Schedule> selectByMember(Member member) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Schedule.class).add(Restrictions.eq("member", member));
		List<Schedule> schedules = criteria.list();
		return schedules;
	}

	public List<Schedule> selectByMemberToday(Member member) {
		Session session = sessionFactory.getCurrentSession();
		return session.createSQLQuery(
				"select * from schedule where date_format(startTime,\"%Y-%m-%d %H-%i\") > date_format(now(),\"%Y-%m-%d %H-%i\") && member_no ="
						+ member.getNo() + " Order by startTime;")
				.addEntity(Schedule.class).list();

	}

	public List<Schedule> selectByRootSchedule(int no) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Schedule.class).add(Restrictions.eq("rootSchedule", no));
		List<Schedule> schedules = criteria.list();
		return schedules;
	}

	public List<Schedule> selectByOpenSchedule(List<Friend> friends) {
		Session session = sessionFactory.getCurrentSession();
		List<Schedule> openSchedules = new ArrayList<Schedule>();
		List<Integer> oneWayFriends = new ArrayList<Integer>();
		String oneWayInput = " in(";
		String bilateralInput = " in(";

		List<Integer> bilateralFriends = new ArrayList<Integer>();
		for (Friend friend : friends) {
			if (friend.getStatus().equals("oneway")) {
				oneWayFriends.add(friend.getFriend().getNo());
			} else {
				bilateralFriends.add(friend.getFriend().getNo());
			}
		}

		for (int i = 0; i < oneWayFriends.size(); i++) {
			if (oneWayFriends.size() == 1) {
				oneWayInput = "=" + String.valueOf(oneWayFriends.get(i));
				break;
			} else if (i == oneWayFriends.size() - 1) {
				oneWayInput += oneWayFriends.get(i) + ")";
			}

			else {
				oneWayInput += oneWayFriends.get(i) + ",";
			}
		}

		openSchedules.addAll(session
				.createSQLQuery("select * from schedule where member_no" + oneWayInput
						+ "&& scope='public' && rootSchedule =0 && date_format(startTime,\"%Y-%m-%d %H-%i\") > date_format(now(),\"%Y-%m-%d %H-%i\")Order by startTime;")
				.addEntity(Schedule.class).list());

		for (int i = 0; i < bilateralFriends.size(); i++) {
			if (oneWayFriends.size() == 1) {
				bilateralInput = "=" + String.valueOf(bilateralFriends.get(i));
				break;
			} else if (i == bilateralFriends.size() - 1) {
				bilateralInput += bilateralFriends.get(i) + ")";
			}

			else {
				bilateralInput += bilateralFriends.get(i) + ",";
			}
		}
		openSchedules.addAll(session
				.createSQLQuery("select * from schedule where member_no" + bilateralInput
						+ "&& scope in('public','friend') && rootSchedule =0 && date_format(startTime,\"%Y-%m-%d %H-%i\") > date_format(now(),\"%Y-%m-%d %H-%i\")Order by startTime;")
				.addEntity(Schedule.class).list());

		return openSchedules;
	}

	public List<Schedule> selectByMemberNo(int memberNo) {

		Session session = sessionFactory.getCurrentSession();

		return session
				.createSQLQuery(
						"select a.* from schedule a left join memberschedule b on a.no=b.schedule_no where b.Member_no="
								+ memberNo + "union select a.* from schedule a where member_no=" + memberNo)
				.addEntity(Schedule.class).list();
	}

	public int delete(int no) {
		Session session = sessionFactory.getCurrentSession();
		Schedule schedule = (Schedule) session.get(Schedule.class, new Integer(no));

		session.delete(schedule);
		return 1;
	}

	public List<Schedule> selectFavoriteSchedule(Member member) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createSQLQuery(
						"select * from schedule where date_format(startTime,\"%Y-%m-%d %H-%i\") > date_format(now(),\"%Y-%m-%d %H-%i\") && member_no ="
								+ member.getNo() + " && bookmark = 'true' Order by startTime;")
				.addEntity(Schedule.class).list();

	}

	public List<Schedule> selectByFriendSchedule(Member member, String status) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = null;
		if (status.equals("bilateral")) {
			criteria = session.createCriteria(Schedule.class).add(Restrictions.eq("member", member))
					.add(Restrictions.in("scope", Arrays.asList("public", "friend")));
		} else if (status.equals("oneway")) {
			criteria = session.createCriteria(Schedule.class).add(Restrictions.eq("member", member))
					.add(Restrictions.eq("scope", "public"));
		}
		List<Schedule> schedules = criteria.list();

		return schedules;

	}

	public List<Schedule> selectByStartTime() {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createSQLQuery(
						"select * from schedule where date_format(startTime,\"%Y-%m-%d %H-%i\") > date_add(now(), INTERVAL +1 HOUR) && date_format(startTime,\"%Y-%m-%d %H-%i\") < date_add(now(), INTERVAL +65 minute) && notification ='yes';")
				.addEntity(Schedule.class).list();
	}
	
	public List<Schedule> selectByDepartureTime() {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createSQLQuery(
						"select * from schedule where date_format(departureTime,\"%Y-%m-%d %H-%i\") > now()  && date_format(departureTime,\"%Y-%m-%d %H-%i\") < date_add(now(), INTERVAL +5 minute) && notification ='doing';")
				.addEntity(Schedule.class).list();
	}

}
