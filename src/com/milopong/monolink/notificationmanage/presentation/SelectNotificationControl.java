package com.milopong.monolink.notificationmanage.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.notificationmanage.business.NotificationManager;
import com.milopong.monolink.notificationmanage.common.Notification;

import net.sf.json.JSONObject;

@Controller
public class SelectNotificationControl {
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private NotificationManager notificationManager;
	
	private JSONObject json =null;
	private Member member =null;
	private List<Notification> notifications = null;
	
	@RequestMapping("selectNotification.do")
	public @ResponseBody JSONObject selectNotification(String email){
		json = new JSONObject();
		member = new Member();
		notifications = new ArrayList<Notification>();
		
		member = memberManager.selectByEmail(email);
		notifications = notificationManager.selectByReciever(member);
		
		System.out.println("notificationSize"+notifications.size());
		
		json.put("notifications", notifications);
		
		return json;
		
		
	}

}
