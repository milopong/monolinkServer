package com.milopong.monolink.friendmanage.dataservice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.friendmanage.common.Friend;
import com.milopong.monolink.membermanage.common.Member;

@Repository
public class FriendDAO {

	@Autowired
	SessionFactory sessionFactory;

	public int addFriend(Friend friend) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Friend.class).add(Restrictions.eq("user", friend.getUser()))
				.add(Restrictions.eq("friend", friend.getFriend()));
		Criteria criteria1 = session.createCriteria(Friend.class).add(Restrictions.eq("user", friend.getFriend()))
				.add(Restrictions.eq("friend", friend.getUser()));
		if (criteria.list().size() == 0 && criteria1.list().size() == 0) {
			friend.setStatus("oneway");
			return (Integer) session.save(friend);
		} else if (criteria.list().size() == 0 && criteria1.list().size() == 1) {
			friend.setStatus("bilateral");

			Friend aFriend = (Friend) criteria1.list().get(0);
			aFriend.setStatus("bilateral");
			session.update(aFriend);

			return (Integer) session.save(friend);

		} else {
			return 0;
		}

	}

	public List<Friend> selectByUser(Member member) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Friend.class).add(Restrictions.eq("user", member));
		List<Friend> friends = criteria.list();
		return friends;
	}

	public Friend selectByUserFriend(Member user, Member friend) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Friend.class).add(Restrictions.eq("user", user))
				.add(Restrictions.eq("friend", friend));
		List<Friend> friends = criteria.list();
		return friends.get(0);

	}
}
