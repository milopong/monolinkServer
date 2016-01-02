package com.milopong.monolink.eventmanage.presentation;

import java.util.ArrayList;
import java.util.List;

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

import net.sf.json.JSONObject;

@Controller
public class SelectEventControl {

	@Autowired
	EventManager eventManager;
	@Autowired
	FavoriteManager favoriteManager;
	@Autowired
	MemberManager memberManager;

	JSONObject json;

	@RequestMapping("selectEvent.do")
	public @ResponseBody JSONObject selectEvent(String email) {
		List<Event> events = new ArrayList<Event>();
		List<Favorite> favorites = new ArrayList<Favorite>();
		List<Member> participants = new ArrayList<Member>();
		Member member = new Member();
		member = memberManager.selectByEmail(email);
		json = new JSONObject();
		
		events = eventManager.selectAll();
		favorites = favoriteManager.selectByMember(member);

		for (Event event : events) {
			json.put("participants" + event.getNo(), participants);
		}
		json.put("events", setBookmarkEvent(favorites,events));

		return json;

	}
	
	public List<Event> setBookmarkEvent(List<Favorite> favorites, List<Event> events) {
		for (Favorite favorite : favorites) {
			if (favorite.getEvent() != null) {
				for (Event event : events) {
					if (favorite.getEvent().getNo() == event.getNo()) {
						System.out.println("Event No:"+favorite.getEvent().getNo());
						event.setBookmark("true");
					}
				}
			}
		}
		return events;

	}

}
