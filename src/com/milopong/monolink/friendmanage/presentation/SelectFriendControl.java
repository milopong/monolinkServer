package com.milopong.monolink.friendmanage.presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.milopong.monolink.friendmanage.business.FriendManager;
import com.milopong.monolink.friendmanage.common.Friend;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

import net.sf.json.JSONObject;

@Controller
public class SelectFriendControl {

	private JSONObject json = null;
	private Member member = null;
	private List<Friend> friendsObject = null;
	@Autowired
	MemberManager memberManager;
	@Autowired
	FriendManager friendManager;

	@RequestMapping("selectFriend.do")
	public @ResponseBody JSONObject selectFriend(String members, String email) {
		Gson gson = null;
		List<Member> monoMembers = null;
		List<Member> contacts = null;
		List<Member> friends = null;
		json = new JSONObject();
		member = memberManager.selectByEmail(email);
		friendsObject = friendManager.selectByUser(member);

		gson = new Gson();
		monoMembers = new ArrayList<Member>();

		contacts = new ArrayList<Member>();
		friends = new ArrayList<Member>();
		monoMembers = memberManager.selectAll();

		// 친구 List
		for (int i = 0; i < friendsObject.size(); i++) {
			Member friend = new Member();
			friend = friendsObject.get(i).getFriend();
			friends.add(friend);
		}
		Collections.sort(friends, new NameAscCompare());
		json.put("friends", friends);
		if (members != null) {
			// 내 연락처
			contacts = gson.fromJson(members, new TypeToken<List<Member>>() {
			}.getType());

			List<Member> test = new ArrayList<Member>();
			test = cmp(monoMembers, contacts, friends);
			json.put("recommend", test);
		}

		return json;
	}

	static class NameAscCompare implements Comparator<Member> {

		/**
		 * 오름차순(ASC)
		 */
		@Override
		public int compare(Member arg0, Member arg1) {
			// TODO Auto-generated method stub
			return arg0.getName().compareTo(arg1.getName());
		}

	}

	private List<Member> cmp(List<Member> monoMembers, List<Member> contacts, List<Member> friends) {

		ArrayList<Member> recommend = new ArrayList<Member>();

		for (int i = 0; i < contacts.size(); i++) {
			for (int j = 0; j < monoMembers.size(); j++) {
				if (contacts.get(i).getPhone().replaceAll("-", "").equals(monoMembers.get(j).getPhone())) {
					if (monoMembers.get(j).getNo() != member.getNo()) {
						recommend.add(monoMembers.get(j));
					}

				}
			}
		}
		int recommendSize = recommend.size();
		for (int i = 0; i < recommendSize; i++) {
			for (int j = 0; j < friends.size(); j++) {
				if (recommend.get(i).getPhone().equals(friends.get(j).getPhone())) {
					recommend.remove(i);
					if (recommendSize != 1) {
						recommendSize--;
						i = -1;
					}

					break;
				}
			}
		}

		return recommend;

	}

}
