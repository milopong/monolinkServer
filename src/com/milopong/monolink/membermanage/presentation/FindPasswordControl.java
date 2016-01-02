package com.milopong.monolink.membermanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

@Controller
public class FindPasswordControl {

	@Autowired
	MemberManager memberManager;

	Member member;

	@RequestMapping("findPassword.do")
	public @ResponseBody String findPassword(String email) {

		String status = null;
		member = memberManager.selectByEmail(email);

		if (member == null) {
			status = "success";
		} else {
			status = "fail";
		}

		return status;
	}
}
