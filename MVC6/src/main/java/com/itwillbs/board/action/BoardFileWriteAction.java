package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardFileWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardFileWriteAction_execute() 호출 ");
		// 한글처리 인코딩 => web.xml(filter 설정) - 생략
		// request.setCharacterEncoding("UTF-8");
		
		// 파일업로드
		
		// 1) 파일이 저장될 장소(webapp/upload 폴더)를 지정 (show in -> system explorer)
		
		// 실제 작업파일 위치(여기에 파일이 저장되면 해킹임)
		// C:\\backend\\workspace_jsp6\\MVC6\\src\\main\\webapp\\upload
		
		// realPath(서버에 배포된 작업파일 위치)
		// C:\\backend\\workspace_jsp6\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\MVC6\\upload
		
		// server clean 말고 project clean
		
		String realPath = request.getRealPath("/upload");
		System.out.println(" M : realPath : "+realPath);
		
		
		// 2) 업로드할 파일의 크기 제한(5MB)
		// bit > 8bit -> 1byte > 1024byte -> 1kb > 1024kb -> 1MB > 1024MB -> 1GB > 1024GB -> 1TB
		int maxSize = 5*1024*1024;
		// int maxSize = 5242880;
		
		
		// 3) 파일 업로드
		MultipartRequest multi 
					= new MultipartRequest(
					request,
					realPath,
					maxSize,
					"UTF-8",
					new DefaultFileRenamePolicy()
					);
		// request : request 영역객체 정보를 전달(+parameter)
		// realPath : 서버에 배포된 업로드 폴더의 위치
		// maxSize : 업로드시 파일크기 제한
		// "UTF-8" : 인코딩 정보 설정
		// new DefaultFileRenamePolicy() : 중복파일 이름 처리
		
		System.out.println(" M : 파일 업로드 성공 ");
		
		// 전달 정보(파라메터) 저장
		// BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		// dto.setName(request.getParameter("name")); (X) : form태그 enctype:multipart/form-data -> 사용불가
		
		dto.setName(multi.getParameter("name"));
		dto.setPass(multi.getParameter("pass"));
		dto.setSubject(multi.getParameter("subject"));
		dto.setContent(multi.getParameter("content"));
		dto.setIp(request.getRemoteAddr());
		dto.setFile(multi.getFilesystemName("file"));
		System.out.println(" M : dto : "+dto);
		System.out.println(" M : oFile : "+multi.getOriginalFileName("file"));
		
		// BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		// 글정보 + 파일이름 저장하는 메서드
		dao.insertBoard(dto);
		
		// 페이지 이동준비
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setRedirect(true);
		return forward;
	}

}
