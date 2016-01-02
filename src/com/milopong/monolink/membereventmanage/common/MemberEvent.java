package com.milopong.monolink.membereventmanage.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.milopong.monolink.eventmanage.common.Event;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Entity
public class MemberEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	int no;

	@ManyToOne
	@JoinColumn(name = "member_no")
	Member member;
	
	@ManyToOne
	@JoinColumn(name = "event_no")
	Event event;
	
	
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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}



}
