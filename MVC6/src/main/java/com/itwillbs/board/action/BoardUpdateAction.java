package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;

public class BoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardUpdateAction_execute() 호출 ");
		
		// 수정 페이지에 기존 사용자의 정보를 출력
		
		// 전달받은 정보(파라메터) 저장(bno,pageNum) **무조건 1번
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		System.out.println(" M : bno = "+bno+", pageNum = "+pageNum);
		
		// BoardDAO BoardDTO 객체 생성
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		
		// 특정 bno 해당하는 글정보를 가져오는 메서드 호출
		dto = dao.getBoard(bno);
		
		// 글 정보 확인(출력)
		System.out.println(" M : dto : "+dto);
		
		// request 영역에 저장
		request.setAttribute("oldDto", dto);
		request.setAttribute("pageNum", pageNum);
		
		// 페이지 이동 준비 (./board/boardUpdate.jsp)
		ActionForward forward = new ActionForward();
		forward.setPath("./board/boardUpdate.jsp");
		forward.setRedirect(false);
		return forward;
	}

}// class BoardUpdateAction
