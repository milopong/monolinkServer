package com.milopong.monolink.membermanage.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.utils.BCrypt;

import net.sf.json.JSONObject;

@Controller
public class LoginControl {

	@Autowired
	MemberManager memberManager;

	JSONObject json;
	Member member;

	@RequestMapping("login.do")
	@ResponseBody
	JSONObject login(String email, String password,String gcmId) {

		member = memberManager.selectByEmail(email);
		json = new JSONObject();

		if (member == null) {
			return null;
		} else {
			String hashPass = member.getPassword();

			if (BCrypt.checkpw(password, hashPass)) {
				json.put("member", member);
				member.setGcmId(gcmId);
				memberManager.memberUpdate(member);

				return json;

			} else {
				return null;
			}
		}
	}

}
