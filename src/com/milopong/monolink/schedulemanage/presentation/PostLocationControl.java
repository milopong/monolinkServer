package com.milopong.monolink.schedulemanage.presentation;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;
import com.milopong.monolink.utils.Utility;

import net.sf.json.JSONObject;

@Controller
public class PostLocationControl {

	@Autowired
	ScheduleManager scheduleManager;

	@RequestMapping("postLocation.do")
	@ResponseBody
	public JSONObject postLocation(String no, double latitude, double longitude) {
		JSONObject json = new JSONObject();
		org.json.JSONObject result = new org.json.JSONObject();
		Schedule schedule = new Schedule();
		schedule = scheduleManager.selectByNo(Integer.parseInt(no));
		String srcLocation = latitude + "," + longitude;
		String dstLocation = schedule.getLatitude() + "," + schedule.getLongitude();

		String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + srcLocation + "&destination="
				+ dstLocation + "&sensor=false&mode=transit";
		System.out.println(url);
		try {
			result = Utility.readJsonFromUrl(url);
			String duration = result.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0)
					.getJSONObject("duration").getString("text");

			System.out.println("duration:" + duration);
			StringTokenizer st = new StringTokenizer(duration);
			String[] data = new String[4];
			int mDuration;
			int i = 0;
			while (st.hasMoreTokens()) {
				data[i] = st.nextToken();
				i++;
			}

			if (data[1].equals("hour")) {
				mDuration = Integer.parseInt(data[0]) * 60 + Integer.parseInt(data[2]);
			} else {
				mDuration = Integer.parseInt(data[0]);
			}

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date date = sdf.parse(schedule.getStartTime());
			cal.setTime(date);
			cal.add(Calendar.MINUTE, -mDuration);
			date = cal.getTime();
			String departureTime = sdf.format(date);
			schedule.setDepartureTime(departureTime);
			scheduleManager.update(schedule);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		json.put("status", "success");

		return json;
	}
}
