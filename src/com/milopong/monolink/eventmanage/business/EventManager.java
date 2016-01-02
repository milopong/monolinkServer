package com.milopong.monolink.eventmanage.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.eventmanage.dataservice.EventDAO;

@Service
public class EventManager {
	
	@Autowired
	EventDAO eventDAO;
	
	@Transactional
	public List<Event> selectAll(){
		return eventDAO.selectAll();
	}
	
	@Transactional
	public Event selectByNo(int no){
		return eventDAO.selectByNo(no);
	}

}
