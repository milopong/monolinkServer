package com.milopong.monolink.membereventmanage.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.membereventmanage.common.MemberEvent;
import com.milopong.monolink.membereventmanage.dataservice.MemberEventDAO;

@Service
public class MemberEventManager {
	
	@Autowired
	MemberEventDAO memberEventDAO;
	
	
	@Transactional
	public List<MemberEvent> selectByEvent(Event event){
		
		return memberEventDAO.selectBySchedule(event);
	}
	
	
	
}
