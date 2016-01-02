package com.milopong.monolink.friendmanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.friendmanage.business.FriendManager;
import com.milopong.monolink.friendmanage.common.Friend;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.notificationmanage.business.NotificationManager;
import com.milopong.monolink.notificationmanage.common.Notification;
import com.milopong.monolink.utils.Utility;

import net.sf.json.JSONObject;

@Controller
public class AddFriendControl {

	@Autowired
	MemberManager memberManager;
	@Autowired
	FriendManager friendManager;
	@Autowired
	NotificationManager notificationManager;
	
	JSONObject json;

	@RequestMapping("addFriend.do")
	public @ResponseBody JSONObject addFriend(String userEmail, String friendEmail) {

		Member user, friend;
		Friend friendObject = new Friend();
		user = memberManager.selectByEmail(userEmail);
		friend = memberManager.selectByEmail(friendEmail);
		json = new JSONObject();

		friendObject.setUser(user);
		friendObject.setFriend(friend);

		int status = friendManager.addFriend(friendObject);

		if(status ==0){
			json.put("status", "fail");
		}else{
			Utility.sendGcmMessage(friend.getGcmId(), user.getName() + "님이 회원님을 팔로우하기 시작했습니다.");
			
			Notification aNotification = new Notification();
			aNotification.setMember_sender(user);
			aNotification.setMember_reciever(friend);
			aNotification.setStatus("unRead");
			aNotification.setMessage("님이 회원님을 팔로우하기 시작했습니다.");
			notificationManager.regist(aNotification);
			json.put("status", "success");
		}

		return json;

	}

}
