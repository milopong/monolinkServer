package com.milopong.monolink.schedulemanage.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.favoritemanager.business.FavoriteManager;
import com.milopong.monolink.favoritemanager.common.Favorite;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.business.MemberScheduleManager;
import com.milopong.monolink.memberschdulemanage.common.MemberSchedule;
import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;

import net.sf.json.JSONObject;

@Controller
public class SelectScheduleControl {

	@Autowired
	private ScheduleManager scheduleManager;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private MemberScheduleManager memberScheduleManager;
	@Autowired
	private FavoriteManager favoriteManager;
	private Member member = null;

	@RequestMapping("selectSchedule.do")
	@ResponseBody
	JSONObject selectSchedule(String email) {
		JSONObject json = new JSONObject();
		List<Schedule> schedules = new ArrayList<Schedule>();
		List<Favorite> favorites = new ArrayList<Favorite>();
		member = memberManager.selectByEmail(email);
		schedules = scheduleManager.selectByMember(member);

		for (int i = 0; i < schedules.size(); i++) {
			List<MemberSchedule> memberSchedule = new ArrayList<MemberSchedule>();
			memberSchedule = memberScheduleManager.selectBySchedule(schedules.get(i));
			List<Member> participants = new ArrayList<Member>();

			for (int j = 0; j < memberSchedule.size(); j++) {
				Member aMember = new Member();
				aMember = memberSchedule.get(j).getMember();
				participants.add(aMember);
			}
			int scheduleNo = schedules.get(i).getNo();
			favorites = favoriteManager.selectByMember(member);
			json.put("participants" + scheduleNo, participants);
		}

		json.put("schedules",  setBookmarkSchedule(favorites, schedules));

		return json;

	}

	@RequestMapping("selectScheduleByNo.do")
	public @ResponseBody JSONObject selectScheduleByNo(String sNo) {
		int no = Integer.valueOf(sNo);
		JSONObject json = new JSONObject();
		Schedule schedule = new Schedule();
		schedule = scheduleManager.selectByNo(no);
		List<MemberSchedule> memberSchedule = new ArrayList<MemberSchedule>();
		List<Member> participants = new ArrayList<Member>();

		for (int i = 0; i < memberSchedule.size(); i++) {
			Member aMember = new Member();
			aMember = memberSchedule.get(i).getMember();
			participants.add(aMember);
		}

		json.put("participants", participants);
		json.put("schedule", schedule);

		return json;
	}

	@RequestMapping("selectTodaySchedule.do")
	@ResponseBody
	JSONObject selectTodaySchedule(String email) {
		JSONObject json = new JSONObject();
		List<Schedule> schedules = new ArrayList<Schedule>();
		List<Favorite> favorites = new ArrayList<Favorite>();
		member = memberManager.selectByEmail(email);
		schedules = scheduleManager.selectByMemberToday(member);

		System.out.println("Schedules Size:" + schedules.size());

		for (int i = 0; i < schedules.size(); i++) {
			List<MemberSchedule> memberSchedule = new ArrayList<MemberSchedule>();
			memberSchedule = memberScheduleManager.selectBySchedule(schedules.get(i));
			List<Member> participants = new ArrayList<Member>();

			for (int j = 0; j < memberSchedule.size(); j++) {
				Member aMember = new Member();
				aMember = memberSchedule.get(j).getMember();
				participants.add(aMember);
			}
			int scheduleNo = schedules.get(i).getNo();
			json.put("participants" + scheduleNo, participants);
		}
		favorites = favoriteManager.selectByMember(member);

		json.put("schedules", setBookmarkSchedule(favorites, schedules));
		return json;

	}

	public List<Schedule> setBookmarkSchedule(List<Favorite> favorites, List<Schedule> schedules) {

		for (Favorite favorite : favorites) {
			if (favorite.getSchedule() != null) {
				for (Schedule schedule : schedules) {
					if (favorite.getSchedule().getNo() == schedule.getNo()) {
						schedule.setBookmark("true");
					}
				}
			}
		}
		return schedules;

	}
}
