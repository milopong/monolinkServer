package com.milopong.monolink.membermanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

import net.sf.json.JSONObject;

//회원 가입 
@Controller
public class RegistMemberControl {

	@Autowired
	MemberManager memberManager;
	JSONObject json;


	@RequestMapping("registMember.do")
	public @ResponseBody JSONObject registMember(Member member) {
		String status = null;
		json = new JSONObject();

		memberManager.regist(member);
		
		status = "success";
		
		json.put("status",status);
		return json;

	}
}
