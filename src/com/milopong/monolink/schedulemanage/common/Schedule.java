package com.milopong.monolink.schedulemanage.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import com.milopong.monolink.membermanage.common.Member;

@Entity
public class Schedule implements Cloneable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no")
	private int no;

	@Generated(GenerationTime.ALWAYS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", insertable = false, updatable = false)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "member_no")
	Member member;

	@ManyToOne
	@JoinColumn(name = "host_no")
	Member host;

	@Column(name = "rootSchedule")
	int rootSchedule;

	@Column(name = "name")
	String name;

	@Column(name = "starttime")
	String startTime;

	@Column(name = "endtime")
	String endTime;

	@Column(name = "place")
	String place;

	@Column(name = "detailPlace")
	String detailPlace;

	@Column(name = "longitude")
	String longitude;

	@Column(name = "latitude")
	String latitude;

	@Column(name = "memo")
	String memo;

	@Column(name = "scope")
	String scope;

	@Column(name = "status")
	String status;

	@Column(name = "notification")
	String notification;
	
	@Column(name = "tag")
	String tag;
	
	@Column(name = "departuretime")
	String departureTime;

	@Column(name = "bookmark")
	String bookmark;
	
	@Column(name = "photo")
	String photo;
	
	public Member getHost() {
		return host;
	}

	public void setHost(Member host) {
		this.host = host;
	}

	public int getRootSchedule() {
		return rootSchedule;
	}

	public void setRootSchedule(int rootSchedule) {
		this.rootSchedule = rootSchedule;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDetailPlace() {
		return detailPlace;
	}

	public void setDetailPlace(String detailPlace) {
		this.detailPlace = detailPlace;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	

	public String getBookmark() {
		return bookmark;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Schedule clone() throws CloneNotSupportedException {
		Schedule schedule = (Schedule) super.clone();
		schedule.no = this.no;
		schedule.date = this.date;
		schedule.member = (Member) member.clone();
		schedule.rootSchedule = this.rootSchedule;
		schedule.name = this.name;
		schedule.startTime = this.startTime;
		schedule.endTime = this.endTime;
		schedule.place = this.place;
		schedule.longitude = this.longitude;
		schedule.latitude = this.latitude;
		schedule.memo = this.memo;
		schedule.scope = this.scope;
		schedule.status = this.status;
		schedule.notification = this.notification;
		schedule.tag = this.tag;
		schedule.departureTime = this.departureTime;
		schedule.bookmark = this.bookmark;
		schedule.photo = this.photo;
		return schedule;
	}

}
