package com.milopong.monolink.schedulemanage.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.favoritemanager.business.FavoriteManager;
import com.milopong.monolink.friendmanage.common.Friend;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.business.MemberScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;
import com.milopong.monolink.schedulemanage.dataservice.ScheduleDAO;
@Service
public class ScheduleManager {
	
	@Autowired
	ScheduleDAO scheduleDAO;
	
	@Autowired
	MemberScheduleManager memberScheduleManager;
	
	@Autowired
	FavoriteManager favoriteManager;
	
	@Transactional
	public Schedule selectByNo(int no){
		return scheduleDAO.selectByNo(no);
	}
	
	@Transactional
	public void update(Schedule schedule){
		scheduleDAO.update(schedule);
	}
	
	@Transactional
	public int regist(Schedule schedule){
		return scheduleDAO.regist(schedule);
	}
	
	@Transactional
	public List<Schedule> selectByMemberNo(int memberNo){
		
		return scheduleDAO.selectByMemberNo(memberNo);
	}
	@Transactional
	public List<Schedule> selectByRootSchedule(int scheduleNo){
		
		return scheduleDAO.selectByRootSchedule(scheduleNo);
	}
	
	@Transactional
	public List<Schedule> selectByMember(Member member){
		
		return scheduleDAO.selectByMember(member);
	}
	@Transactional
	public List<Schedule> selectByMemberToday(Member member){
		
		return scheduleDAO.selectByMemberToday(member);
	}
	
	@Transactional
	public List<Schedule> seleByOpenSchedule(List<Friend> friends){
		
		return scheduleDAO.selectByOpenSchedule(friends);
	}
	
	@Transactional
	public int delete(int no){
		
		memberScheduleManager.delete(no);
		favoriteManager.delete(no);
		
		return scheduleDAO.delete(no);
	}
	@Transactional
	public List<Schedule> selectFavoriteSchedule(Member member){

		return scheduleDAO.selectFavoriteSchedule(member);
	}
	
	@Transactional
	public List<Schedule> selectByFriendSchedule(Member member,String status){
		
		return scheduleDAO.selectByFriendSchedule(member,status);
	}
	
	@Transactional
	public List<Schedule> selectByStartTime(){
		
		return scheduleDAO.selectByStartTime();
	}
	
	@Transactional
	public List<Schedule> selectByDepartureTime(){
		
		return scheduleDAO.selectByDepartureTime();
	}

}
