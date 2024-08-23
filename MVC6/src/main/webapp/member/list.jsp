<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>/member/list.jsp</h1>
	
	<h2> 관리자 메뉴 - 회원목록 조회 </h2>
	
<%-- 	${memberList } --%>
	
	<table border="1">
		<tr>
			<td>아이디</td>
			<td>비밀번호</td>
			<td>이름</td>
			<td>이메일</td>
			<td>나이</td>
			<td>성별</td>
			<td>회원가입일</td>
		</tr>

		<c:forEach items="${memberList }" var="m">
		<tr>
			<td>${m.id }</td>
			<td>${m.pw }</td>
			<td>${m.name }</td>
			<td>${m.email }</td>
			<td>${m.age }</td>
			<td>${m.gender }</td>
			<td><fmt:formatDate value="${m.regdate }" type="both"/></td>
		</tr>
		</c:forEach>
	</table>
	
	<h2><a href="./Main.me">메인페이지로...</a></h2>
</body>
</html>