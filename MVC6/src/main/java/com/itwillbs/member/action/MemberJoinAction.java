package com.itwillbs.member.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;

/**
 *	MemberJoinAction
 *	회원가입에 대한 처리하는 클래스 (insertPro.jsp 동작을 대신 수행)
 * 	
 */
public class MemberJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : MemberJoinAction_execute() 실행 ");
		
		// insertPro.jsp 페이지에서 처리한 동작을 수행
		
		// 한글처리 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 전달정보(파라메터) 저장 => MemberDTO에 *저장*
		// String id = request.getParameter("id");
		MemberDTO mdto = new MemberDTO();
		
		mdto.setAge(Integer.parseInt(request.getParameter("age")));
		mdto.setEmail(request.getParameter("email"));
		mdto.setGender(request.getParameter("gender"));
		mdto.setId(request.getParameter("id"));
		mdto.setName(request.getParameter("name"));
		mdto.setPw(request.getParameter("pw"));
		mdto.setRegdate(new Timestamp(System.currentTimeMillis()));
		
		System.out.println(" M : 전달정보 "+mdto);
		
		// DB연결 - insert *처리*
		// DAO 객체 생성
		MemberDAO mdao = new MemberDAO();
		mdao.insertMember(mdto);
		System.out.println(" M : DAO처리 완료 ");
		
		// 로그인 페이지로 이동(X) => 페이지 이동 정보 생성
		ActionForward forward = new ActionForward();
		forward.setPath("./MemberLogin.me");
		forward.setRedirect(true);
		System.out.println(" M : "+forward);
		
		return forward;
	}
	
	
	
	

}
