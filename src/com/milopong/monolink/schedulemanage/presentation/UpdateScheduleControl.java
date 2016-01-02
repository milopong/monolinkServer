package com.milopong.monolink.schedulemanage.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.memberschdulemanage.business.MemberScheduleManager;
import com.milopong.monolink.memberschdulemanage.common.MemberSchedule;
import com.milopong.monolink.notificationmanage.business.NotificationManager;
import com.milopong.monolink.notificationmanage.common.Notification;
import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;
import com.milopong.monolink.utils.Utility;

import net.sf.json.JSONObject;

@Controller
public class UpdateScheduleControl {
	@Autowired
	ScheduleManager scheduleManager;
	@Autowired
	MemberManager memberManager;
	@Autowired
	MemberScheduleManager memberScheduleManager;
	@Autowired
	NotificationManager notificationManager;

	private JSONObject json = null;
	private Schedule schedule = null;
	private Member member = null;
	private List<Schedule> schedules = null;
	private List<MemberSchedule> memberSchedules = null;
	private List<Member> participants = null;

	@RequestMapping("joinSchedule.do")
	public @ResponseBody JSONObject joinSchedule(String sNo, String email) {
		int no = Integer.parseInt(sNo);
		schedule = new Schedule();
		schedule = scheduleManager.selectByNo(no);

		member = new Member();
		member = memberManager.selectByEmail(email);

		MemberSchedule aMemberSchedule = new MemberSchedule();
		aMemberSchedule.setSchedule(schedule);
		aMemberSchedule.setMember(member);

		int status = memberScheduleManager.regist(aMemberSchedule);

		if (status != 0) {
			schedules = new ArrayList<Schedule>();

			schedules = scheduleManager.selectByRootSchedule(schedule.getNo());

			for (int i = 0; i < schedules.size(); i++) {

				MemberSchedule aMemberSchedule1 = new MemberSchedule();
				aMemberSchedule1.setSchedule(schedules.get(i));
				aMemberSchedule1.setMember(member);
				memberScheduleManager.regist(aMemberSchedule1);
			}

			Schedule newSchedule = new Schedule();
			try {
				newSchedule = schedule.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newSchedule.setRootSchedule(schedule.getNo());
			newSchedule.setStatus("guest");
			newSchedule.setNotification("no");
			newSchedule.setMember(member);
			newSchedule.setScope("no");
			newSchedule.setHost(schedule.getMember());
			scheduleManager.regist(newSchedule);

			memberSchedules = new ArrayList<MemberSchedule>();
			memberSchedules = memberScheduleManager.selectBySchedule(schedule);

			participants = new ArrayList<Member>();
			for (int i = 0; i < memberSchedules.size(); i++) {
				participants.add(memberSchedules.get(i).getMember());
			}
			participants.add(schedule.getMember());
			for (int i = 0; i < participants.size(); i++) {
				if (participants.get(i).getEmail().equals(member.getEmail())) {
					participants.remove(i);
				}
			}

			for (int i = 0; i < participants.size(); i++) {
				MemberSchedule aMemberSchedule2 = new MemberSchedule();
				aMemberSchedule2.setMember(participants.get(i));
				aMemberSchedule2.setSchedule(newSchedule);
				memberScheduleManager.regist(aMemberSchedule2);
			}
		}

		json = new JSONObject();
		json.put("status", status);
		return json;

	}

	@RequestMapping("rejectSchedule.do")
	public @ResponseBody JSONObject rejectSchedule(String sNo,String nNo, String email) {
		json = new JSONObject();
		int no = Integer.parseInt(sNo);
		int no1 = Integer.parseInt(nNo);
		schedule = new Schedule();
		schedule = scheduleManager.selectByNo(no);
		member = new Member();
		member = memberManager.selectByEmail(email);
		
		Utility.sendGcmMessage(schedule.getHost().getGcmId(), member.getName() + "님이 회원님의 참여요청을 거절하셨습니다.");
		
		Notification aNotification = new Notification();
		aNotification.setMember_sender(member);
		aNotification.setMember_reciever(schedule.getHost());
		aNotification.setStatus("unRead");
		aNotification.setMessage("님이 회원님의 참여요청을 거절하셨습니다.");
		aNotification.setScheduleNo(schedule.getNo());
		notificationManager.regist(aNotification);
		notificationManager.delete(no1);
		
		json.put("status", "success");
		return json;

	}

}
