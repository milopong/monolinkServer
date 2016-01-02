package com.milopong.monolink.schedulemanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class JoinOpenScheduleControl {

	@Autowired
	MemberManager memberManager;
	@Autowired
	ScheduleManager scheduleManager;
	@Autowired
	MemberScheduleManager memberScheduleManager;
	@Autowired
	NotificationManager notificationManager;

	private JSONObject json = null;
	private Member member = null;
	private Schedule schedule = null;

	@RequestMapping("joinOpenSchedule.do")
	public @ResponseBody JSONObject joinOpenSchedule(String sNo, String email) {
		json = new JSONObject();
		member = memberManager.selectByEmail(email);

		int no = Integer.valueOf(sNo);
		schedule = scheduleManager.selectByNo(no);
		Utility.sendGcmMessage(schedule.getMember().getGcmId(), member.getName() + "님이 회원님의 오픈 스케쥴에 참가신청 하셨습니다.");
		
		Notification aNotification = new Notification();
		aNotification.setMember_sender(member);
		aNotification.setMember_reciever(schedule.getHost());
		aNotification.setStatus("unRead");
		aNotification.setMessage("님이 회원님의 오픈 스케쥴에 참가신청 하셨습니다.");
		aNotification.setScheduleNo(schedule.getNo());
		aNotification.setType("joinOpenSchedule");
		notificationManager.regist(aNotification);

		
		json.put("status","success");
		return json;
	}
}
