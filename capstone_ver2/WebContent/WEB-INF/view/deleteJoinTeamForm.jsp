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
<form action="deleteJoinTeam.do" method="post">
<div class="center_box">
    <div class="title_box">
        <span class="_title">졸업작품 평가관리 시스템</span>
    </div>
    <div class="join_box">
        <div class="list_box flex-center-row">
            <input type="password" name="curPwd" placeholder="비밀번호를 입력하세요.">        
        </div>
        <c:if test="${errors.curPwd}">암호를 입력하세요.</c:if>
		<c:if test="${errors.badCurPwd}">암호와 확인이 일치하지 않습니다.</c:if>
    </div>
    <div class="button_box flex-center-column">
        <button type="submit">Delete Team</button>
        <c:if test="${errors.NotAccessCommand}">팀장은 탈퇴할 수 없습니다.</c:if>
    </div>
</div>
</form>
</body>
</html>