package com.milopong.monolink.schedulemanage.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.business.MemberScheduleManager;
import com.milopong.monolink.notificationmanage.business.NotificationManager;
import com.milopong.monolink.notificationmanage.common.Notification;
import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;
import com.milopong.monolink.utils.Utility;

import net.sf.json.JSONObject;

@Controller
public class RegistScheduleControl {

	@Autowired
	ScheduleManager scheduleManager;
	@Autowired
	MemberManager memberManager;
	@Autowired
	MemberScheduleManager memberScheduleManager;
	@Autowired
	NotificationManager notificationManager;

	@RequestMapping("registSchedule.do")
	public @ResponseBody JSONObject registSchdule(Schedule schedule, String email, String members) {
		Member member;
		JSONObject json;
		List<Member> friends = null;
		Gson gson = null;
		int rootScheduleNo;

		gson = new Gson();
		json = new JSONObject();
		friends = new ArrayList<Member>();
		friends = gson.fromJson(members, new TypeToken<List<Member>>() {
		}.getType());
		member = memberManager.selectByEmail(email);
		schedule.setMember(member);
		schedule.setHost(member);
		schedule.setNotification("yes");
		schedule.setScope("public");
		schedule.setBookmark("false");
		rootScheduleNo = scheduleManager.regist(schedule);
		if (friends != null) {
			for (int i = 0; i < friends.size(); i++) {
				Utility.sendGcmMessage(friends.get(i).getGcmId(), member.getName() + "님이 회원님을 일정에 초대 했습니다.");

				Notification aNotification = new Notification();
				aNotification.setMember_sender(member);
				aNotification.setMember_reciever(friends.get(i));
				aNotification.setStatus("unRead");
				aNotification.setMessage("님이 회원님을 일정에 초대 했습니다.");
				aNotification.setScheduleNo(schedule.getNo());
				notificationManager.regist(aNotification);
				
			}

		}
		json.put("no", rootScheduleNo);
		json.put("status", "success");
		return json;

	}
}
