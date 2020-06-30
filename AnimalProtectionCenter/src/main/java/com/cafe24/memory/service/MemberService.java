package com.cafe24.memory.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.memory.domain.Member;
import com.cafe24.memory.mapper.MemberMapper;
import com.cafe24.memory.mapper.StaffMapper;

@Service
@Transactional
public class MemberService {
	
	private final static Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);
	
	@Autowired 
	private MemberMapper memberMapper;
	
	@Autowired 
	private StaffMapper staffMapper;

	@Value("${service.project.mode}")
	boolean projectMode;
	
	public void projectMode(HttpServletRequest request) {
		if(projectMode) {
			logger.info("개발 모드 {}", projectMode);
			Member member = memberMapper.getMemberList("id001");
			HttpSession session = request.getSession();
			session.setAttribute("SID", member.getMemberId());
			session.setAttribute("SNAME", member.getMemberName());
			session.setAttribute("SEMAIL", member.getMemberEmail());
			session.setAttribute("SLEVEL", member.getLevel().getLevelCode());
			session.setAttribute("STAFFCODE",staffMapper.selectStaffMember(member.getMemberId()).getStaffCode());
			session.setAttribute("STAFF",staffMapper.selectStaffMember(member.getMemberId()));
		}
	}
	
	public List<Member> getMemberList(){
	
		return memberMapper.getMemberList();
	}
	
	public Member getMemberList(String mId){
		
		return memberMapper.getMemberList(mId);
	}
	public int insertMember(Member m) {
		
		int result=memberMapper.insertMember(m);
		System.out.println(result+"<-Service insertMember결과값");
		return result;
	}
	public int deleteMember(Member m) {
		int result=memberMapper.deleteMember(m);
		return result;
	}
	public int updateMember(Member m) {
		int result=memberMapper.updateMember(m);
		return result;
	}
}
