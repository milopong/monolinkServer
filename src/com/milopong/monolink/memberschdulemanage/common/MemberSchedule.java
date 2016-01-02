package com.milopong.monolink.memberschdulemanage.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Entity
public class MemberSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	int no;
	@ManyToOne(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.REMOVE)
	@JoinColumn(name = "member_no")
	Member member;

	@ManyToOne(fetch = FetchType.EAGER, cascade = javax.persistence.CascadeType.REMOVE)
	@JoinColumn(name = "schedule_no")
	Schedule schedule;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
}
