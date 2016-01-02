package com.milopong.monolink.schedulemanage.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.friendmanage.business.FriendManager;
import com.milopong.monolink.friendmanage.common.Friend;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.business.MemberScheduleManager;
import com.milopong.monolink.memberschdulemanage.common.MemberSchedule;
import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;

import net.sf.json.JSONObject;

@Controller
public class SelectFriendScheduleControl {
	
	@Autowired
	private ScheduleManager scheduleManager;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private FriendManager friendManager;
	@Autowired
	private MemberScheduleManager memberScheduleManager;
	
	
	private JSONObject json = null;
	private Member user = null;
	private Member friend = null;
	private Friend friendObject = null;
	private List<Schedule> schedules = null; 
	

	@RequestMapping("selectFriendSchedule.do")
	public @ResponseBody JSONObject selectFriendSchedule(String userEmail, String friendEmail){
		System.out.println("userEmail:"+userEmail);
		System.out.println("friendEmail:"+friendEmail);
		json = new JSONObject();
		
		user = memberManager.selectByEmail(userEmail);
		friend = memberManager.selectByEmail(friendEmail);
		friendObject =friendManager.selectByUserFriend(user, friend);
		
		String status = friendObject.getStatus();
		schedules = scheduleManager.selectByFriendSchedule(friend, status);
		
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
		json.put("schedules", schedules);
		
		
		return json;
		
		
		
	}
}
