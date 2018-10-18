<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/join/join.css">
    <title>Change Password</title>
</head>
<body class="flex-center-row">
<form action="changePwd.do" method="post">
<div class="center_box">
    <div class="title_box">
        <span class="_title">졸업작품 평가관리 시스템</span>
    </div>
    <div class="join_box">
        <div class="list_box flex-center-row">
            <input type="password" name="curPwd" placeholder="비밀번호를 입력하세요.">        
        </div>
        <c:if test="${errors.curPwd}">현재암호를 입력하세요.</c:if>
		<c:if test="${errors.badCurPwd}">현재암호와 확인이 일치하지 않습니다.</c:if>
		<div class="list_box flex-center-row">
			<input type="password" name="newPwd" placeholder="새 암호를 입력하세요.">
			<c:if test="${errors.newPwd}">새 암호를 입력하세요.</c:if>
		</div>
    </div>
    <div class="button_box flex-center-column">
        <button type="submit">Change Password</button>
    </div>
</div>
</form>
</body>
</html>