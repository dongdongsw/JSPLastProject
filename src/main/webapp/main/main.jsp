<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>JSPLastProject</title>
    <link rel="icon" href="../img/core-img/favicon.ico">
    <link href="../css/style.css" rel="stylesheet">
    <link href="../css/responsive/responsive.css" rel="stylesheet">
</head>

<body>
    <div id="preloader">
        <div class="yummy-load"></div>
    </div>
    <div id="pattern-switcher">
        실시간 상담
    </div>
    <div id="patter-close">
        <i class="fa fa-times" aria-hidden="true"></i>
    </div>
    
    <!-- 메뉴 -->
    <jsp:include page="header.jsp"/>
    
    <!--  주 화면 -->
    <jsp:include page="${main_jsp }"/>
    
    <!-- 쿠키 출력 -->
	<jsp:include page="cookies.jsp"/>

    <!-- Footer -->
	<jsp:include page="footer.jsp"/>
	
    <!-- Jquery-2.2.4 js --> 
    <script src="../js/jquery/jquery-2.2.4.min.js"></script>
    <!-- Popper js -->
    <script src="../js/bootstrap/popper.min.js"></script>
    <!-- Bootstrap-4 js -->
    <script src="../js/bootstrap/bootstrap.min.js"></script>
    <!-- All Plugins JS -->
    <script src="../js/others/plugins.js"></script>
    <!-- Active JS -->
    <script src="../js/active.js"></script>
</body>
</html>