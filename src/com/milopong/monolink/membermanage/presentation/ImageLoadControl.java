package com.milopong.monolink.membermanage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;

import net.sf.json.JSONObject;

@Controller
public class ImageLoadControl {

	@Autowired
	MemberManager memberManager;

	Member member;

	JSONObject json;

	@RequestMapping("imageLoad.do")
	public @ResponseBody JSONObject imageLoad(String email) {
		System.out.println("okok");
		member = memberManager.selectByEmail(email);
		json = new JSONObject();
		json.put("photo", member.getPhoto());

		return json;
	}

}
