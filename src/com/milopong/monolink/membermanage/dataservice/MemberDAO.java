package com.milopong.monolink.membermanage.dataservice;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.milopong.monolink.membermanage.common.Member;

@Repository
public class MemberDAO {
	public MemberDAO() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private SessionFactory sessionFactory;

	List<Member> memberList;

	public int regist(Member member) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(member);

	}
	
	public List<Member> selectAll(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Member.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);		
		List<Member> Members = (List<Member>) criteria.list();
		return Members;
		
	}

	public Member selectByName(String name) {
		
		System.out.println("name:"+name);

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Member.class);
		criteria.add(Restrictions.eq("name", name));

		memberList = criteria.list();

		if (memberList.size() == 0) {
			return null;
		} else {
			return memberList.get(0);
		}
	}

	public Member selectByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Member.class);
		criteria.add(Restrictions.eq("email", email));

		memberList = criteria.list();

		if (memberList.size() == 0) {
			return null;
		} else {
			return memberList.get(0);
		}
	}

	public Member selectByPhone(String phone) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Member.class);
		criteria.add(Restrictions.eq("phone", phone));

		memberList = criteria.list();

		if (memberList.size() == 0) {
			return null;
		} else {
			return memberList.get(0);
		}
	}

	public Member selectByEmailPassword(String email, String password) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Member.class);
		criteria.add(Restrictions.eq("email", email)).add(Restrictions.eq("password", password));

		memberList = criteria.list();

		if (memberList.size() == 0) {
			return null;
		} else {
			return memberList.get(0);
		}

	}
	
	public void update(Member member) {
		Session session = sessionFactory.getCurrentSession();
		session.update(member);
	}

}
