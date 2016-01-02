package com.milopong.monolink.membermanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

import net.sf.json.JSONObject;

@Controller
public class LogoutControl {
	private JSONObject json = null;
	@Autowired
	private MemberManager memberManager;
	private Member member = null;

	@RequestMapping("logout.do")
	public @ResponseBody JSONObject logout(String email) {
		json = new JSONObject();
		member = new Member();
		member = memberManager.selectByEmail(email);
		member.setGcmId("");
		memberManager.memberUpdate(member);
		json.put("status", "success");

		return json;

	}

}
