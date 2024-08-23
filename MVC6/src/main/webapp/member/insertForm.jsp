<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	
	function check(){
		if(document.fr.id.value == ""){
// 			alert(" 아이디를 입력하세요! ");		
		}
	}

</script>
</head>
<body>
	<h1>member/insertForm.jsp (MVC) </h1>
	
	<h2> 회원가입 페이지 </h2>
	
	<fieldset>
		<legend> 회원가입 정보 </legend>
		<form action="./MemberJoinAction.me" method="post" name="fr" onsubmit="check();">
			아이디: <input type="text" name="id"> <br>
			비밀번호: <input type="password" name="pw"> <br>
			이메일: <input type="email" name="email"> <br>
			이름: <input type="text" name="name"> <br>
			성별: <input type="radio" name="gender" value="남">남
				  <input type="radio" name="gender" value="여">여 <br>
			나이: <input type="number" name="age"> <br>
			
			<hr>
			<input type="submit" value="제출">
		</form>
	</fieldset>
	
	
	
	
	
</body>
</html>