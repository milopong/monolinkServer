package com.milopong.monolink.membermanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

import net.sf.json.JSONObject;

//친구찾기
@Controller
public class SearchFriendControl {

	@Autowired
	MemberManager memberManager;
	JSONObject json;
	Member memberByEmail, memberByPhone;

	@RequestMapping("searchFriend.do")
	public @ResponseBody JSONObject searchFriend(String input) {
		System.out.println("input:"+input);
		json = new JSONObject();
		memberByEmail = memberManager.selectByEmail(input);
		memberByPhone = memberManager.selectByPhone(input);
		
		if(memberByEmail == null && memberByPhone !=null){
			json.put("member", memberByPhone);
		}else if(memberByPhone == null && memberByEmail != null){
			json.put("member", memberByEmail);
		}else{
			return null;
		}
		return json;
	}

}
