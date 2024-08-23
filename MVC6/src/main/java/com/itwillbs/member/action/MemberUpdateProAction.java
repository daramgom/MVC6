package com.itwillbs.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;
import com.itwillbs.util.JSFunction;

public class MemberUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateProAction_execute() 실행 ");
		
		// 세션 정보 확인(로그인 여부 체크)
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			// response.sendRedirect("./MemberLogin.me");
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// 한글 처리 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 수정 정보(전달된 파라메터) MemberDTO 저장
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(request.getParameter("pw"));
		dto.setEmail(request.getParameter("email"));
		dto.setName(request.getParameter("name"));
		dto.setGender(request.getParameter("gender"));
		dto.setAge(Integer.parseInt(request.getParameter("age")));
		
		System.out.println(" M : 수정할 내용 "+dto);
		
		// MemberDAO 객채생성
		MemberDAO dao = new MemberDAO();
		
		// 회원정보를 수정하는 메서드(동작) 실행
		int result = dao.updateMember(dto);
		System.out.println(" M : result : "+result);
		
		// 수정 결과에 따른 페이지 이동(JS)
		if(result == -1) {
			JSFunction.alertAndBack(response, "수정X - 아이디 정보 없음");
			return null;
		} else if(result == 0) {
			JSFunction.alertAndBack(response, "수정X - 비밀번호 오류");
			return null;
		} else {
			JSFunction.alertAndLocation(response, "수정O - 정상처리", "./Main.me");
		}
		
		return null;
	}

}
