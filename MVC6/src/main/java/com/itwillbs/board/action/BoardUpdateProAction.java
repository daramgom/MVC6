package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;
import com.itwillbs.util.JSFunction;

public class BoardUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardUpdateProAction_execute() 호출 ");
		// 한글처리 인코딩 - web.xml(filter) 생략
		// 전달받은 정보(파라메터) 저장(pageNum)
		String pageNum = request.getParameter("pageNum");
		System.out.println(" M : pageNum : "+pageNum);
		
		// 전달받은 정보(파라메터) 저장(bno,name,pass,subject,content)
		// BoardDTO 에 저장
		BoardDTO dto = new BoardDTO();
		dto.setBno(Integer.parseInt(request.getParameter("bno")));
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		System.out.println(" M : dto : "+dto);
		
		// BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		// 글 내용을 수정하는 메서드 호출
		int result = dao.updateBoard(dto);
		System.out.println(" M : result : "+result);
		
		// 결과(result)값에 따른 페이지 이동준비 (JS)
		if(result == 0) {
			JSFunction.alertAndBack(response, "수정 X - 글 비밀번호 오류");
			return null;
		} else if(result == -1) {
			JSFunction.alertAndBack(response, "수정 X - 글 정보 없음");
			return null;
		}
		
		JSFunction.alertAndLocation(response, "수정 O - 글 수정완료 ", "./BoardList.bo?pageNum="+pageNum);
		return null;
	}

}
