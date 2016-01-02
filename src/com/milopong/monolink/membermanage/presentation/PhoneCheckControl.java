package com.milopong.monolink.membermanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

import net.sf.json.JSONObject;

//phone 중복체크
@Controller
public class PhoneCheckControl {

	@Autowired
	MemberManager memberManager;

	Member member;

	JSONObject json;

	@RequestMapping("phoneCheck.do")
	public @ResponseBody JSONObject phoneCheck(String phone) {

		json = new JSONObject();
		String status = null;
		member = memberManager.selectByPhone(phone);

		if (member == null) {

			int authKey = (int) (Math.random() * 1000000);
			// 휴대폰에 인증번호 보내는 코드 삽입

			System.out.println("authKey:" + authKey);
			status = "success#" + String.valueOf(authKey);

		} else {
			status = "duplication#1";
		}
		json.put("status", status);

		return json;
	}

}
