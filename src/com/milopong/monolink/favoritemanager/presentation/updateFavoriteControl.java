package com.milopong.monolink.favoritemanager.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.eventmanage.business.EventManager;
import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.favoritemanager.business.FavoriteManager;
import com.milopong.monolink.favoritemanager.common.Favorite;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;

import net.sf.json.JSONObject;

@Controller
public class updateFavoriteControl {

	@Autowired
	MemberManager memberManager;
	@Autowired
	EventManager eventManager;
	@Autowired
	ScheduleManager scheduleManager;
	@Autowired
	FavoriteManager favoriteManager;

	@RequestMapping("updateFavorite.do")
	@ResponseBody
	public JSONObject updateFavorite(String email, String status, Integer scheduleNo, Integer eventNo) {

		JSONObject json = new JSONObject();
		Member member = new Member();
		member = memberManager.selectByEmail(email);
		int no = 0;
		if (scheduleNo != null && status.equals("regist")) {
			Schedule schedule = new Schedule();
			schedule = scheduleManager.selectByNo(scheduleNo);
			Favorite favorite = new Favorite();
			favorite.setMember(member);
			favorite.setSchedule(schedule);
			favorite.setStartTime(schedule.getStartTime());
			no = favoriteManager.regist(favorite);
			json.put("status", "regist");
		} else if (scheduleNo != null && status.equals("unregist")) {
			Schedule schedule = new Schedule();
			schedule = scheduleManager.selectByNo(scheduleNo);
			favoriteManager.unRegistBySchedule(member, schedule);
			json.put("status", "unRegist");
		} else if (scheduleNo == null && status.equals("regist")) {
			Favorite favorite = new Favorite();
			Event event = new Event();
			event = eventManager.selectByNo(eventNo);
			favorite.setMember(member);
			favorite.setEvent(event);
			favorite.setStartTime(event.getStartTime());
			no = favoriteManager.regist(favorite);
			json.put("status", "regist");
		} else if (scheduleNo == null && status.equals("unregist")) {
			Event event = new Event();
			event = eventManager.selectByNo(eventNo);
			favoriteManager.unRegistByEvent(member, event);
			json.put("status", "unRegist");
		}

		

		return json;

	}
}
