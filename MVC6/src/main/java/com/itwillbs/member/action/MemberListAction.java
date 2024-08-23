package com.itwillbs.member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;

public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberListAction_execute() 실행 ");
		// 한글 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 전달받은 정보 저장
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		// 로그인 체크 (로그인 O, 아이디 'admin' O)
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")) {
			// 로그인 X 또는 아이디 'admin' X
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// 회원정보 목록을 가져오는 메서드
		ArrayList<MemberDTO> memberList = dao.getMemberList();
		System.out.println(" M : "+memberList.size());
		
		// request 영역에 저장
		request.setAttribute("memberList", memberList);
		
		// 페이지 이동준비
		forward.setPath("./member/list.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
