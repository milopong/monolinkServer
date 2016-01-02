package com.milopong.monolink.membermanage.presentation;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.milopong.monolink.membermanage.business.MemberManager;
import com.milopong.monolink.membermanage.common.Member;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.sf.json.JSONObject;

@Controller
public class ProfileUploadControl {
	
	@Autowired
	MemberManager memberManager;
	
	String uploadFile = null;
	Member member;
	String fileName = null;

	@RequestMapping("profileUpload.do")
	public @ResponseBody String imageUpload(HttpServletRequest request, HttpServletResponse response) {

		int maxSize = 1024 * 1024 * 10;
		ServletContext scontext = request.getSession().getServletContext();
		String savefile = "images\\profile";
		String savePath = scontext.getRealPath(savefile);

		try {

			MultipartRequest multi = new MultipartRequest(request, savePath, maxSize, "UTF-8",
					new DefaultFileRenamePolicy());

			// 파일업로드
			Enumeration files = multi.getFileNames();
			
			//파일 정보가 있다면
			if(files.hasMoreElements()) {
			     String name = (String)files.nextElement();
			     fileName = multi.getFilesystemName(name);
			}
			
			System.out.println("savePath:"+savePath);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@RequestMapping("profileSave.do")
	public @ResponseBody JSONObject imageSave(String email){
		JSONObject json = null;
		json = new JSONObject();
		
		member =memberManager.selectByEmail(email);
		member.setPhoto(fileName);
		memberManager.memberUpdate(member);
		json.put("status","success");
		return json;
		
	}
}
