package com.milopong.monolink.memberschdulemanage.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.common.MemberSchedule;
import com.milopong.monolink.memberschdulemanage.dataservice.MemberScheduleDAO;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Service
public class MemberScheduleManager {
	
	@Autowired
	MemberScheduleDAO memberScheduleDAO;
	
	@Transactional
	public int regist(MemberSchedule memberSchedule){
		
		return memberScheduleDAO.regist(memberSchedule);
	}
	
	@Transactional
	public List<MemberSchedule> selectBySchedule(Schedule schedule){
		
		return memberScheduleDAO.selectBySchedule(schedule);
	}
	
	@Transactional
	public List<MemberSchedule> selectByMember(Member member){
		
		return memberScheduleDAO.selectByMember(member);
	}
	
	@Transactional
	public int delete(int no){
		
		return memberScheduleDAO.delete(no);
	}
	
	@Transactional
	public int deleteMember(int scheduleNo, int memberNo){
		
		return memberScheduleDAO.deleteMember(scheduleNo, memberNo);
	}
	
}
