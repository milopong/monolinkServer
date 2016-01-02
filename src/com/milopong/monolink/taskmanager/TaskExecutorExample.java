package com.milopong.monolink.taskmanager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import com.milopong.monolink.schedulemanage.business.ScheduleManager;
import com.milopong.monolink.schedulemanage.common.Schedule;
import com.milopong.monolink.utils.Utility;

public class TaskExecutorExample {

	@Autowired
	ScheduleManager scheduleManager;

	private class getLocation implements Runnable {

		public void run() {
			List<Schedule> urgentSchedules = new ArrayList<Schedule>();

			while (true) {
				try {
					Thread.sleep(1000*60);
					urgentSchedules = scheduleManager.selectByStartTime();
					for (Schedule schedule : urgentSchedules) {
						Utility.sendGcmMessage(schedule.getHost().getGcmId(), "lbs#" + schedule.getNo());
						schedule.setNotification("doing");
						scheduleManager.update(schedule);
					}

				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}

		}
	}

	private class pushToClient implements Runnable {

		public void run() {
			List<Schedule> urgentSchedules = new ArrayList<Schedule>();

			while (true) {
				try {
					Thread.sleep(1000*60);
					urgentSchedules = scheduleManager.selectByDepartureTime();
					for (Schedule schedule : urgentSchedules) {
						Utility.sendGcmMessage(schedule.getHost().getGcmId(), "지금 출발하셔야 합니다.");
						schedule.setNotification("done");
						scheduleManager.update(schedule);
					}

				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}

		}
	}

	private TaskExecutor taskExecutor;

	public TaskExecutorExample(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void printMessages() {
		taskExecutor.execute(new getLocation());
		taskExecutor.execute(new pushToClient());
	}
}
