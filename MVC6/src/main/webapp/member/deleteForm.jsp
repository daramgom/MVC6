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
	<c:if test="${empty id }">
		<c:redirect url="./MemberLogin.me" />
	</c:if>
	
	<h1>/member/deleteForm.jsp</h1>
	<h2><span style="color : red;">회</span><span style="color : orange;">원</span>
	<span style="color : yellow;">정</span><span style="color : green;">보</span> 
	<span style="color : blue;">삭</span><span style="color : indigo;">제</span>
	<span style="color : purple;">(탈퇴)</span></h2>
	
	<fieldset>
		<legend>회원 탈퇴</legend>
		<form action="./MemberDeleteAction.me" method="post">
			비밀번호 : <input type="password" name="pw"><br>
			<input type="submit"  value="탈퇴하기">
		</form>
	</fieldset>
	
	<h3><a href="./Main.me">메인페이지로 이동...</a></h3>
</body>
</html>