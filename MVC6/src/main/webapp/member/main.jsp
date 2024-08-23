<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1> /member/main.jsp (MVC) </h1>
	
	
	<!-- 로그인을 성공한 사용자만 페이지 사용가능 (JSTL/EL) -->
	
<%-- 	<% --%>
<!-- // 		String id = (String)session.getAttribute("id"); -->
<!-- // 		if(id == null) { -->
<!-- // 			response.sendRedirect("./MemberLogin.me"); -->
<!-- // 		} -->
	
<%-- 	%> --%>

	<!-- 세션 영역에 저장된 아이디 저장 -->
<%-- 	<c:set var="" value=""/> --%>

	<!-- 제어문 if 처리 로직 -->
<%-- 	<c:if test="${empty sessionScope.id }"> --%>
	<c:if test="${empty id }">
		<c:redirect url="./MemberLogin.me" />
	</c:if>
	
	
	<!-- ㅇㅇㅇ님 로그인 성공 / 안녕하세요 -->
	<h2>${sessionScope.id }님 로그인 성공</h2>
	
	<input type="button" value="로그아웃" onclick="location.href='./MemberLogout.me';">
	
	<hr>
	
	<h3><a href="./MemberInfo.me">회원정보 조회(info)</a></h3>
	
	<h3><a href="./MemberUpdate.me">회원정보 수정(update)</a></h3>
	
	<h3><a href="./MemberDelete.me">회원정보 삭제(delete)</a></h3>
	
	<h3><a href="./BoardList.bo">게시판 보기</a></h3>
	
	<c:if test="${!empty id && id.equals('admin') }">
		<!-- 관리자 전용 메뉴 -->
		<h3>관리자 전용 메뉴</h3>
		<h3><a href="./MemberList.me">회원정보 목록(list)</a></h3>
	</c:if>
	
</body>
</html>