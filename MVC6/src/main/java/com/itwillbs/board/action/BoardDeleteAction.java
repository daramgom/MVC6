package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;
import com.itwillbs.util.JSFunction;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardDeleteAction_execute() 호출 ");
		// 한글처리 인코딩 web.xml(filter) 생략
		// 전달받은(파라메터) 정보 DTO & 변수 저장(bno,pass,pageNum)
		BoardDTO dto = new BoardDTO();
		dto.setBno(Integer.parseInt(request.getParameter("bno")));
		dto.setPass(request.getParameter("pass"));
		
		String pageNum = request.getParameter("pageNum");
		
		// BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		// 게시판 글 삭제하는 메서드 호출
		int result = dao.deleteBoard(dto);
		System.out.println(" M : result : "+result);
		
		// 결과(result)값에 따른 페이지 이동준비 (JS)
		if(result == 0) {
			JSFunction.alertAndBack(response, "삭제 X - 글 비밀번호 오류");
			return null;
		} else if(result == -1) {
			JSFunction.alertAndBack(response, "삭제 X - 글 정보 없음");
			return null;
		}
		
		JSFunction.alertAndLocation(response, "삭제 O - 글 삭제완료 ", "./BoardList.bo?pageNum="+pageNum);
		return null;
		
	}

}
