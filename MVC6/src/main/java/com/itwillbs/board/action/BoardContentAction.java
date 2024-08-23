package com.itwillbs.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;
import com.itwillbs.util.JSFunction;

public class BoardContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardContentAction_execute() 호출 ");
		
		// 작성된 글의 내용을 화면에 출력
		
		// 전달된 정보(파라메터) 저장(bno,pageNum)
		if(request.getParameter("bno").equals("")) {
			JSFunction.alertAndBack(response, "잘못된 접근입니다");
		}
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		
		System.out.println(" M : bno : "+bno+", pageNum : "+pageNum);
		
		// BoardDAO 객체
		BoardDAO dao = new BoardDAO();
		
		// 조회수를 증가 가능한 상태인지 체크
		HttpSession session = request.getSession();
		boolean isUpdate = (boolean) session.getAttribute("isUpdate");
		
		if(isUpdate) {
			// DAO 객체 - 조회수 1증가(update)
			dao.updatereadcount(bno);
			System.out.println(" M : 조회수 1증가 완료 ");
			session.setAttribute("isUpdate", false);
		}
		
		// DAO 객체 - 작성된 글의 정보를 가져오기(select)
		if(dao.getBoard(bno) == null) {
			JSFunction.alertAndBack(response, "잘못된 접근입니다");
		}
		BoardDTO dto = dao.getBoard(bno);
		System.out.println(" M : dto : "+dto);
		
		
		//------------------------------------------------------------------------
		// 다음글 제목 조회
		String nextTitle = dao.nextSubject(bno);
		System.out.println(" M : nextTitle : "+nextTitle);
		
		// 이전글 제목 조회
		String prevTitle = dao.prevSubject(bno); // 변수명과 함수명은 다르게하는 게 좋다
		System.out.println(" M : prevTitle : "+prevTitle);
		
		//------------------------------------------------------------------------
		
		
		// request 영역에 저장
		request.setAttribute("dto", dto);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("nextTitle", nextTitle);
		request.setAttribute("prevTitle", prevTitle);
		
		// 페이지 이동준비
		ActionForward forward = new ActionForward();
		forward.setPath("./board/boardContent.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}// class BoardContentAction
