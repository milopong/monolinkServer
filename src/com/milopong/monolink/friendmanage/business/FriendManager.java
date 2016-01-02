package com.milopong.monolink.friendmanage.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.friendmanage.common.Friend;
import com.milopong.monolink.friendmanage.dataservice.FriendDAO;
import com.milopong.monolink.membermanage.common.Member;

@Service
public class FriendManager {
	
	@Autowired
	FriendDAO friendDAO;
	
	@Transactional
	public int addFriend(Friend friend){
		
		return friendDAO.addFriend(friend);
	}
	
	@Transactional
	public List<Friend> selectByUser(Member member){
		
		return friendDAO.selectByUser(member);
	}
	
	@Transactional
	public Friend selectByUserFriend(Member user, Member friend){
		
		return friendDAO.selectByUserFriend(user,friend);
	}

}
