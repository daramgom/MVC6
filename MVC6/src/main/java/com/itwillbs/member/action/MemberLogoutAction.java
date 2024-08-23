package com.itwillbs.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;

public class MemberLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberLogoutAction_execute() 실행 ");
		System.out.println(" M : 로그아웃 처리 로직 수행 ");
		
		// 세션영역에 들어있는 아이디 정보를 제거
		HttpSession session = request.getSession();
		
		// 세션 객체 초기화
		session.invalidate();
		
		// Main 페이지로 이동 (JS)
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('안전하게 로그아웃 되었습니다');");
		out.println("location.href='./Main.me';");
		out.println("</script>");
		out.close();
		
		return null; // 컨트롤러에 이동정보 전달 X
	}
	
}
