<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<title> 게시판 글목록 | 참여하기 | 아이티윌 </title>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="./css/common.css">
<script src="./js/jquery-1.11.3.min.js"></script>
<script src="./js/common.js"></script>  
<script src="./js/jquery.smooth-scroll.min.js"></script> 
<!--[if lte IE 9]>
    <script src="js/html5shiv.js"></script>
	<script src="js/placeholders.min.js"></script>
<![endif]-->
</head>

<body>
<ul class="skipnavi">
    <li><a href="#container">본문내용</a></li>
</ul>
<!-- wrap -->
<div id="wrap">

	<!-- 헤더 시작 -->
	<jsp:include page="../inc/header.jsp"/>
	<!-- 헤더 끝 -->
	
	<div id="container">
		<!-- location_area -->
		<div class="location_area customer">
			<div class="box_inner">
				<h2 class="tit_page">ITWILL <span class="in">back</span> END</h2>
				<p class="location">Board <span class="path">/</span> 게시판</p>
				<ul class="page_menu clear">
					<li><a href="#" class="on">게시판</a></li>
					<li><a href="#">문의하기</a></li>
				</ul>
			</div>
		</div>	
		<!-- //location_area -->

		<!-- bodytext_area -->
		<div class="bodytext_area box_inner">
			<form action="./BoardSearchListAction.bo" class="minisrch_form" method="post">
				<fieldset>
					<legend>검색</legend>
					<input type="hidden" name="pageNum" value="${pageNum }">
					<input type="text" name="search" class="tbox" title="검색어를 입력해주세요" placeholder="검색어를 입력해주세요">
					<!-- <a href="javascript:;" class="btn_srch">검색</a> -->
					<input type="submit" value="검색" class="btn_srch">
				</fieldset>
			</form>
			<table class="bbsListTbl" summary="번호,제목,조회수,작성일 등을 제공하는 표">
				<caption class="hdd">게시판 글목록</caption>
				<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">제목</th>
						<th scope="col">조회수</th>
						<th scope="col">작성일</th>
					</tr>
				</thead>
				<tbody>
				
				
				<c:forEach var="dto" items="${boardList }">
					<tr>
						<td>${dto.bno }</td>
						<td class="tit_notice">
							<a href="./BoardContent.bo?bno=${dto.bno }&pageNum=${pageNum }">
							
								<c:if test="${dto.re_lev > 0 }">
									<img src="./img/level.gif" width="${dto.re_lev * 10 }">
									<img src="./img/re.gif">
								</c:if>
								
								${dto.subject }
								<c:if test="${dto.file != null}">
									<img src="./img/save.png" width="20">
								</c:if>
							</a> 
						</td>
						<td>${dto.readcount }</td>
						<td>${dto.date }</td>
					</tr>
				</c:forEach>
				
				</tbody>
			</table>
			<!-- pagination -->
			<%-- ${requestScope } --%>
			<div class="pagination">
				<!-- <a href="javascript:;" class="firstpage  pbtn"><img src="./img/btn_firstpage.png" alt="첫 페이지로 이동"></a> -->
				
				<c:if test="${startPage > pageBlock }">
					<a href="./BoardList.bo?pageNum=${startPage-pageBlock }" class="prevpage  pbtn">
					<img src="./img/btn_prevpage.png" alt="이전 페이지로 이동">
					</a>
				</c:if>
				
				<c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
				<a href="./BoardList.bo?pageNum=${i }"><span class="pagenum ${pageNum == i? 'currentpage':'' }">${i }</span></a>
				<%-- <c:if test="${pageNum == i }">${'currentpage' }</c:if> --%>
				</c:forEach>
				<c:if test="${endPage < pageCount }">
					<a href="./BoardList.bo?pageNum=${startPage+pageBlock }" class="nextpage  pbtn">
					<img src="./img/btn_nextpage.png" alt="다음 페이지로 이동">
					</a>
				</c:if>
				<!-- <a href="javascript:;" class="lastpage  pbtn"><img src="./img/btn_lastpage.png" alt="마지막 페이지로 이동"></a> -->
			</div>
			<!-- //pagination -->
			
		</div>
		<!-- //bodytext_area -->

	</div>
	<!-- //container -->

	<footer>
		<div class="foot_area box_inner">
			<ul class="foot_list clear">
				<li><a href="javascript:;">이용약관</a></li>
				<li><a href="javascript:;">개인정보취급방침</a></li>
			</ul>
			<h2>아이티윌</h2>
            <p class="addr">부산광역시 부산진구 동천로 109 삼한골든게이트빌딩 7층(접수) <span class="gubun">/</span>        
				<span class="br_line">TEL <span class="space0">051-803-0909</span> <span class="gubun">/</span>        
					<span class="br_line">E-mail : <span class="space0"> class@itwillbs.co.kr </span></span>
				</span>
			</p>
			<p class="copy box_inner">Copyright(c) TouristInTour all right reserved</p>
			<ul class="snslink clear">
				<li><a href="javascript:;">blog</a></li>
				<li><a href="javascript:;">facebook</a></li>
				<li><a href="javascript:;">instargram</a></li>
			</ul>
		</div>
	</footer>

</div>
<!-- //wrap -->

<h2 class="hdd">빠른 링크 : 전화 문의,카카오톡,오시는 길,꼭대기로</h2>
<div class="quick_area">
	<ul class="quick_list">
		<li><a href="tel:010-7184-4403"><h3>전화 문의</h3><p>010-1234-5678</p></a></li>
		<li><a href="javascript:;"><h3>카카오톡 <em>상담</em></h3><p>1:1상담</p></a></li>
		<li><a href="javascript:;"><h3 class="to_contact">오시는 길</h3></a></li>
	</ul>
	<p class="to_top"><a href="#layout0" class="s_point">TOP</a></p>
</div>

</body>
</html>
