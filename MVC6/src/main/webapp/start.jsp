<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>start.jsp</h1>
	
	<%
		// MVC 프로젝트의 시작 지점
		// 원하는 주소로 페이지 이동
		// ** 절대로 jsp파일 실행X (단, start.jsp 제외)
		// ** 주소줄에도 .jsp주소는 표시X
		//    (주소에 .jsp 주소가 있을 경우 잘못된 코드)
		
		// response.sendRedirect("http://localhost:8088/MVC6/test.me");
		// response.sendRedirect("./test.me");
		// response.sendRedirect("./itwill.me");
		// response.sendRedirect("./MemberJoin.me");
		// response.sendRedirect("./MemberLogin.me");
		// response.sendRedirect("./Main.me");
		// response.sendRedirect("./BoardWrite.bo");
		response.sendRedirect("./BoardList.bo");
	
	%>
</body>
</html>