package com.milopong.monolink.notificationmanage.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.schedulemanage.common.Schedule;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	int no;

	@ManyToOne
	@JoinColumn(name = "member_sender")
	Member member_sender;

	@ManyToOne
	@JoinColumn(name = "member_reciever")
	Member member_reciever;

	@Column(name = "message")
	String message;

	@Column(name = "schedule_no")
	int scheduleNo;

	@Column(name = "status")
	String status;

	@Column(name = "type")
	String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Member getMember_sender() {
		return member_sender;
	}

	public void setMember_sender(Member member_sender) {
		this.member_sender = member_sender;
	}

	public Member getMember_reciever() {
		return member_reciever;
	}

	public void setMember_reciever(Member member_reciever) {
		this.member_reciever = member_reciever;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getScheduleNo() {
		return scheduleNo;
	}

	public void setScheduleNo(int scheduleNo) {
		this.scheduleNo = scheduleNo;
	}

}
