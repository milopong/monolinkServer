package com.milopong.monolink.membermanage.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.milopong.monolink.membermanage.common.Member;
import com.milopong.monolink.membermanage.dataservice.MemberDAO;

@Service
public class MemberManager {
	
	@Autowired
	MemberDAO memberDAO;
	
	
	
	@Transactional
	public int regist(Member member) {
		// TODO Auto-generated method stub
		return memberDAO.regist(member);
	}
	
	@Transactional
	public List<Member> selectAll(){
		
		return memberDAO.selectAll();
	}
	
	@Transactional
	public Member selectByName(String nickName){
		
		return memberDAO.selectByName(nickName);
	}
	
	@Transactional
	public Member selectByEmail(String email){
		
		return memberDAO.selectByEmail(email);
	}
	
	@Transactional
	public Member selectByPhone(String phone){
		
		return memberDAO.selectByPhone(phone);
	}
	
	@Transactional
	public Member selectByEmailPassword(String email, String password){
		
		return memberDAO.selectByEmailPassword(email,password);
	}
	
	@Transactional
    public void memberUpdate(Member member ) {
    	memberDAO.update(member);
    }
	
}















