<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>member/logout.jsp (MVC) </h1>
	<%
	session.invalidate();
	/* response.sendRedirect("loginForm.jsp"); */
	%>
	<script type="text/javascript">
		alert("정상적으로 로그아웃 되었습니다");
		location.href="./Main.me";
	</script>
</body>
</html>