package com.milopong.monolink.favoritemanager.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.favoritemanager.common.Favorite;
import com.milopong.monolink.favoritemanager.dataservice.FavoriteDAO;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Service
public class FavoriteManager {

	@Autowired
	FavoriteDAO favoriteDAO;

	@Transactional
	public List<Favorite> selectByMember(Member member) {
		return favoriteDAO.selectByMember(member);
	}

	@Transactional
	public int regist(Favorite favorite) {

		return favoriteDAO.regist(favorite);
	}

	@Transactional
	public void unRegistBySchedule(Member member, Schedule schedule) {

		favoriteDAO.unRegistBySchedule(member, schedule);
	}
	
	@Transactional
	public void unRegistByEvent(Member member, Event event) {

		favoriteDAO.unRegistByEvent(member, event);
	}
	
	@Transactional
	public int delete(int no) {

		return favoriteDAO.delete(no);
	}
	

}
