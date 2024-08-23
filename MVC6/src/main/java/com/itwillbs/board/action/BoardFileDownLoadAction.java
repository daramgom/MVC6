package com.itwillbs.board.action;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;

public class BoardFileDownLoadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardFileDownLoadAction_execute() 호출 ");
		
		// 전달정보(파라메터) 저장
		String fileName = request.getParameter("fileName");
		
		// 다운로드할 파일의 이름(+경로)
		String savePath = "upload";
		
		ServletContext context = request.getServletContext();
		String downLoadPath = context.getRealPath(savePath);
		System.out.println(" M : downLoadPath : "+downLoadPath);
		
		// 최종 다운로드할 경로 + 다운로드할 파일명
		String dFilePath = downLoadPath + "\\" + fileName;
		System.out.println(" M : dFilePath : "+dFilePath);
		
		// 파일을 저장할 버퍼(배열)
		byte[] b = new byte[4096]; // 4096byte == 4KB
		
		// 파일 입출력
		FileInputStream fis = new FileInputStream(dFilePath);
		
		// 파일의 MIME 타입 확인
		String dMimeType = context.getMimeType(dFilePath);
		System.out.println(" M : dMimeType : "+dMimeType);
		
		if(dMimeType == null) {
			dMimeType = "application/octet-stream";
			// -> 알려지지 않은 이진 파일 형태로 설정
			// -> 브라우저가 자동실행 X, 확인창
		}
		
		// 응답 데이터 형태를 (다운로드 할) MIME타입으로 설정
		response.setContentType(dMimeType);
		
		// (ie)브라우저에 따른 인코딩 설정
		String agent = request.getHeader("User-Agent");
		boolean ieBrowser = agent.indexOf("MSIE") > -1 || agent.indexOf("Trident") > -1;
		// User-Agent 정보 안에 "MSIE"/"Trident" 정보가 포함되어있는지 확인
		
		if(ieBrowser) {// ie 브라우저일 때, 인코딩 UTF-8 설정, +문자(공백) -> %20 공백문자 변경
			fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20"); // java.net
		} else {// 나머지 브라우저 일 때
			fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		}
		
		// 다운로드 정보 출력
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		// => 모든 형태의 다운로드를 다운로드 창으로 표시
		
		// PrintWriter out = response.getWriter();
		// out.println("<html>");
		ServletOutputStream out2 = response.getOutputStream();
		
		int data = 0;
		while((data = fis.read(b,0,b.length)) != -1) {
			out2.write(b,0,data);
		}
		
		out2.flush(); // 버퍼에 남은 공간을 공백으로 채워서 전달
		out2.close();
		fis.close();
		
		
		
		
		return null;
	}

}
