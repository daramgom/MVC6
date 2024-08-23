package com.itwillbs.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.util.Action;
import com.itwillbs.util.ActionForward;



/**
 * 	Controller
 * 
 * 	사용자의 요청을 가장 먼저 처리하는 객체(서블릿 형태).
 * 	
 */

public class MemberFrontController extends HttpServlet {
	// ***** 초기 설정 *****
	// package - build path - java build path - Libraries - add Libraries - Tomcat
	// build path - java compiler - version 11
	
	
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : MemberFrontController-doProcess() 호출");
		System.out.println(" C : GET/POST 방식 상관없이 모두 실행 \n\n");
		
		// 서버만 실행한 뒤 주소 ctrl + click 
		// 가상주소
		// http://localhost:8088/MVC6/test.me (URL)
		// 						/MVC6/test.me (URI)
		// jsp response.sendRedirect 이용
		
		
		System.out.println(" C : --------------------1. 가상주소 계산 - 시작 --------------------");
		// 가상 주소를 가져오기
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI : "+requestURI);
		// 프로젝트(컨텍스트 : Context)명을 가져오기
		String CTXPath = request.getContextPath();
		System.out.println(" C : CTXPath : "+CTXPath);
		// 가상주소(URI) - 프로젝트(컨텍스트)명
		String command = requestURI.substring(CTXPath.length());
		System.out.println(" C : command : "+command);
		System.out.println(" C : --------------------1. 가상주소 계산 - 끝 --------------------");
		
		
		
		
		System.out.println(" C : --------------------2. 가상주소 매핑 - 시작 --------------------");
		
		Action action = null;
		ActionForward forward = null; // 페이지 이동정보(티켓)
		
		// 회원가입 - 정보 입력
		if(command.equals("/MemberJoin.me")) {
			System.out.println(" C : /MemberJoin.me 매핑 ");
			System.out.println(" C : 패턴 1 - DB사용 X, view 페이지 이동 ");
			
			forward = new ActionForward();
			forward.setPath("./member/insertForm.jsp");
			forward.setRedirect(false);
			System.out.println(" C : "+forward.toString());
		} 
		else if(command.equals("/MemberJoinAction.me")) {
			System.out.println(" C : /MemberJoinAction.me 매핑 ");
			System.out.println(" C : 패턴 2 - DB사용 O, 페이지 이동 ");
			
			// MemberJoinAction객체 생성
			// MemberJoinAction mjAction = new MemberJoinAction(); --> 강한 결합
			// Action mjAction = new MemberJoinAction();
			action = new MemberJoinAction();
			// 인터페이스로 업캐스팅하는 이유 --> 약한 결합, 문제 발생시 다른 객체로 변환이 상대적으로 쉬움
			
			try { // mjAction.execute(request, response);
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		} 
		else if(command.equals("/MemberLogin.me")) {
			System.out.println(" C : /MemberLogin.me 매핑 ");
			System.out.println(" C : 패턴 1 - DB사용 X, view 페이지 이동 ");
			// ActionForward 객체 생성
			forward = new ActionForward();
			forward.setPath("./member/loginForm.jsp");
			forward.setRedirect(false);
			System.out.println(" C : "+forward.toString());
		} 
		else if(command.equals("/MemberLoginAction.me")) {
			System.out.println(" C : /MemberLoginAction.me 매핑 ");
			System.out.println(" C : 패턴 2 - DB사용 O, 페이지 이동 ");
			
			// MemberLoginAction() 객체 생성
			action = new MemberLoginAction();
			try {
				// 객체의 execute() 메서드 호출, forward 리턴
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward.toString());
		}
		else if(command.equals("/Main.me")) {
			System.out.println(" C : /Main.me 매핑 ");
			System.out.println(" C : 패턴 1 - DB사용 X, view 페이지 이동 ");
			// ActionForward 객체 생성
			forward = new ActionForward();
			forward.setPath("./member/main.jsp");
			forward.setRedirect(false); // 주소를 바꾸면 jsp 드러나니까 안바뀌는 방식인 forward
			System.out.println(" C : "+forward.toString());
		}
		else if(command.equals("/MemberLogout.me")) {
			System.out.println(" C : /MemberLogout.me 매핑 ");
//			System.out.println(" C : 패턴 1 - DB사용 X, view 페이지 이동 ");
			System.out.println(" C : 패턴 2 - 작업처리 O (DB사용 X), 페이지 이동 ");
			
			// MemberLogoutAction() 객체 생성
			action = new MemberLogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			//ActionForward 객체 생성
//			forward = new ActionForward();
//			forward.setPath("./member/logout.jsp");
//			forward.setRedirect(false);
//			System.out.println(" C : "+forward.toString());
		}
		else if(command.equals("/MemberInfo.me")) {
			System.out.println(" C : /MemberInfo.me 매핑 ");
			System.out.println(" C : 패턴 3 - DB사용 O, 페이지 출력 O ");
			
			// MemberInfoAction() 객체 생성
			action = new MemberInfoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/MemberUpdate.me")) {
			System.out.println(" C : /MemberUpdate.me 매핑 ");
			System.out.println(" C : 패턴 3 - DB사용 O, 페이지 출력 O ");
			
			// MemberUpdateAction() 객체 생성
			action = new MemberUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/MemberUpdateProAction.me")) {
			System.out.println(" C : /MemberUpdateProAction.me 매핑 ");
			System.out.println(" C : 패턴 2 - DB사용 O, 페이지 이동 ");
			
			// MemberUpdateProAction() 객체 생성
			action = new MemberUpdateProAction();
			try {
			// execute() 호출
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/MemberDelete.me")) {
			System.out.println(" C : /MemberDelete.me 매핑 ");
			System.out.println(" C : 패턴 1 - DB사용 X, 페이지 이동");
			forward = new ActionForward();
			forward.setPath("./member/deleteForm.jsp");
			forward.setRedirect(false);
			
			System.out.println(" C : "+forward);
		}
		else if(command.equals("/MemberDeleteAction.me")) {
			System.out.println(" C : /MemberDeleteAction.me 매핑 ");
			System.out.println(" C : 패턴 2 - DB사용 O, 페이지 이동 ");
			action = new MemberDeleteAction();
			// execute() 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else if(command.equals("/MemberList.me")) {
			System.out.println(" C : /MemberList.me 매핑");
			System.out.println(" C : 패턴 3 - DB사용O, 페이지 출력");
			// MemberListAction() 객체 생성
			action = new MemberListAction();
			// execute() 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println(" C : --------------------2. 가상주소 매핑 - 끝 --------------------");
		
		
		
		
		System.out.println(" C : --------------------3. 가상주소 주소 이동 - 시작 --------------------");
		if(forward != null) {
			// 이동정보 객체가 있다. (티켓이 있음)
			System.out.println(" C : "+forward.isRedirect()+"방식으로, "+forward.getPath()+" 이동 ");
			if(forward.isRedirect()) {
				// true
				response.sendRedirect(forward.getPath());
			}
			else {
				// false
				RequestDispatcher dis
					= request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}
			
		}
		System.out.println(" C : --------------------3. 가상주소 주소 이동 - 끝 -------------------- \n\n");
		
		
		
		
		System.out.println(" C : doProcess() 동작 끝 \n\n");
	}// doProcess() 
	

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : MemberFrontController-doGet() 호출");
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : MemberFrontController-doPost() 호출");
		doProcess(request, response);
	} 
	
	
	
}
