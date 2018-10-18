<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/success/success.css">
    <title>Delete Team</title>
</head>
<body class="flex-center-row">
<div class="center_box">
    <div class="title_box">
        <span class="_title">졸업작품 평가관리 시스템</span>
    </div>
    <div class="join_box">
        <span class="delete_h2">암호가 변경되었습니다. 재로그인해주세요.</span><br>
    </div>
    <%
    	session.invalidate();
	%>
    <div class="button_box flex-center-column">
        <a href="${ctxPath}/Capstone/index.jsp">
        <button>처음화면</button></a>
    </div>
</div>
</body>
</html>