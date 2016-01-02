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
public class SelectOpenScheduleControl {

	@Autowired
	private ScheduleManager scheduleManager;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private FriendManager friendManager;
	@Autowired
	private MemberScheduleManager memberScheduleManager;

	private JSONObject json = null;
	private Member member = null;
	private List<Friend> friends = null;
	private List<Schedule> openSchedule = null;

	@RequestMapping("selectOpenSchedule.do")
	public @ResponseBody JSONObject selectOpenScheduleControl(String email) {
		json = new JSONObject();
		friends = new ArrayList<Friend>();
		openSchedule = new ArrayList<Schedule>();

		member = memberManager.selectByEmail(email);
		friends = friendManager.selectByUser(member);
		if (friends.size() != 0) {
			openSchedule = scheduleManager.seleByOpenSchedule(friends);

			for (int i = 0; i < openSchedule.size(); i++) {
				List<MemberSchedule> memberSchedule = new ArrayList<MemberSchedule>();
				memberSchedule = memberScheduleManager.selectBySchedule(openSchedule.get(i));

				List<Member> participants = new ArrayList<>();
				for (int j = 0; j < memberSchedule.size(); j++) {
					Member aMember = new Member();
					aMember = memberSchedule.get(j).getMember();
					participants.add(aMember);
				}
				int scheduleNo = openSchedule.get(i).getNo();

				json.put("participants" + scheduleNo, participants);
			}
		}
		json.put("openSchedules", openSchedule);

		return json;

	}

}
