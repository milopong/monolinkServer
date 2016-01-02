package com.milopong.monolink.membermanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

import net.sf.json.JSONObject;

//Email 중복체크
@Controller
public class EmailCheckControl {

	@Autowired
	MemberManager memberManager;
	Member member;
	JSONObject json;

	@RequestMapping("emailCheck.do")
	public @ResponseBody JSONObject emailCheck(@RequestParam("email") String email) {

		String status = null;
		json = new JSONObject();

		member = memberManager.selectByEmail(email);

		if (member == null) {
			status = "success";
		}
		else{
			status = "duplication";
		}
		json.put("status", status);

		return json;
	}
}