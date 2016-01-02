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
import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;

import net.sf.json.JSONObject;

@Controller
public class DeleteScheduleControl {

	@Autowired
	private ScheduleManager scheduleManager;
	@Autowired
	private MemberManager memberManager;
	@Autowired
	private MemberScheduleManager memberScheduleManager;

	private JSONObject json = null;
	private List<Schedule> schedules = null;
	private Member member = null;

	@RequestMapping("deleteHostSchedule.do")
	@ResponseBody
	JSONObject deleteRootSchedule(String sNo) {

		json = new JSONObject();
		schedules = new ArrayList<Schedule>();
		member = new Member();

		int no = Integer.parseInt(sNo);
		scheduleManager.delete(no);

		schedules = scheduleManager.selectByRootSchedule(no);

		if (schedules.size() != 0) {
			for (int i = 0; i < schedules.size(); i++) {
				scheduleManager.delete(schedules.get(i).getNo());
			}
		}
		json.put("status", "success");
		return json;
	}

}
