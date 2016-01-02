package com.milopong.monolink.favoritemanager.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.favoritemanager.business.FavoriteManager;
import com.milopong.monolink.favoritemanager.common.Favorite;
import com.milopong.monolink.membereventmanage.business.MemberEventManager;
import com.milopong.monolink.membereventmanage.common.MemberEvent;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.business.MemberScheduleManager;
import com.milopong.monolink.memberschdulemanage.common.MemberSchedule;
import com.milopong.monolink.schedulemanage.common.Schedule;

import net.sf.json.JSONObject;

@Controller
public class SelectFavoriteScheduleControl {

	@Autowired
	FavoriteManager favoriteManager;
	@Autowired
	MemberManager memeberManager;
	@Autowired
	MemberScheduleManager memberScheduleManager;
	@Autowired
	MemberEventManager memberEventManager;

	@RequestMapping("selectFavoriteSchedule")
	public @ResponseBody JSONObject selectFavoriteSchedule(String email) {

		JSONObject json = new JSONObject();
		Member member = new Member();
		List<Favorite> favorites = new ArrayList<Favorite>();
		List<Schedule> schedules = new ArrayList<Schedule>();
		List<Event> events = new ArrayList<Event>();
		member = memeberManager.selectByEmail(email);
		favorites = favoriteManager.selectByMember(member);

		for (Favorite favorite : favorites) {
			if (favorite.getSchedule() != null) {
				schedules.add(favorite.getSchedule());
				List<MemberSchedule> participants = new ArrayList<MemberSchedule>();
				participants = memberScheduleManager.selectBySchedule(favorite.getSchedule());
				json.put("participants" + favorite.getSchedule().getNo(), participants);
			} else {
				events.add(favorite.getEvent());
				List<MemberEvent> participants = new ArrayList<MemberEvent>();
				participants = memberEventManager.selectByEvent(favorite.getEvent());
				json.put("participants" + favorite.getEvent().getNo(), participants);
			}
		}

		json.put("schedules", schedules);
		json.put("events", events);
		return json;

	}

}
