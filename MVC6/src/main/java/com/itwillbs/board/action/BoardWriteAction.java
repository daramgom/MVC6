package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;

public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardWriteAction_execute() 호출 ");
		// 한글처리 인코딩 => web.xml(filter 설정) - 생략
		// request.setCharacterEncoding("UTF-8");
		
		// 전달정보(파라메터) BoardDTO 저장
		// 작성자, 비밀번호, 제목, 내용
		BoardDTO dto = new BoardDTO();
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		// + IP 주소
		dto.setIp(request.getRemoteAddr());
		System.out.println(" M : "+dto);
		
		// BoardDAO 객체 - 글쓰기 메서드
		BoardDAO dao = new BoardDAO();
		dao.insertBoard(dto);
		
		// 페이지 이동준비
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
	}

}
