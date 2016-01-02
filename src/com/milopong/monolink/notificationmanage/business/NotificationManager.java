package com.milopong.monolink.notificationmanage.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.notificationmanage.common.Notification;
import com.milopong.monolink.notificationmanage.dataservice.NotificationDAO;

@Service
public class NotificationManager {
	@Autowired
	private NotificationDAO notificationDAO;

	@Transactional
	public int regist(Notification notification){
		
		return notificationDAO.regist(notification);
	}
	
	@Transactional
	public List<Notification> selectByReciever(Member member){
		
		return notificationDAO.selectByReciever(member);
	}
	
	@Transactional
	public int delete(int no){
		
		return notificationDAO.delete(no);
	}
}
