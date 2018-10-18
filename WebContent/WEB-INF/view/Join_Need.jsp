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
    <title>Join</title>
</head>
<body class="flex-center-row">
<form action="join.do" method="post">
<div class="center_box">
    <div class="title_box">
        <span class="_title">졸업작품 평가관리 시스템</span>
    </div>
    <div class="sign_box">
        <div class="list_box flex-center-row">
            <select class="custom-select" name="groupnumber">
                <option value="2">학생</option>
                <option value="1">교수</option>
            </select>
        </div>
    </div>
    <div class="join_box">
    <div class="list_box flex-center-row">
            <input type="text" name="id" value="${param.id}" maxlength="8" placeholder="학번 입력">
            <c:if test="${errors.id}">학번를 입력하세요.</c:if>
    </div>
    <p class="jointeam"><c:if test="${errors.duplicateId}">이미 사용중인 학번입니다.</c:if></p>
			
        <div class="list_box flex-center-row">
            <input type="text" name="name" maxlength="8"  value="${param.name}" placeholder="이름 입력">
            <c:if test="${errors.name}">이름을 입력하세요.</c:if>
        </div>
        <div class="list_box flex-center-row">
            <input type="password" name="password" placeholder="비밀번호 입력" >
            <c:if test="${errors.password}">암호르 입력하세요.</c:if>
        </div>
        <div class="list_box flex-center-row">
            <input type="password" name="confirmPassword" placeholder="비밀번호 확인" >
            <c:if test="${errors.confirmPassword}">확인을 입력하세요.</c:if>
			<c:if test="${errors.notMatch}">암호와 확인이 일치하지 않습니다.</c:if>
        </div>
        <div class="list_box flex-center-row">
            <input type="text" name="phonenumber" maxlength="12" placeholder="휴대폰 번호">
            <c:if test="${errors.phonenumber}">핸드폰 번호를 입력하세요.</c:if>
        </div>
        <div class="list_box flex-center-row">
            <input type="text" name="email" maxlength="30" placeholder="이메일입력  ex) abcd@abcd.efg">
            <c:if test="${errors.email}">이메일을 입력하세요.</c:if>
        </div>
    </div>
    <div class="button_box flex-center-column">
        <button type="submit">Join</button>
        <c:if test="${errors.putValues}">값을 입력하세요.</c:if>
    </div>
</div>
</form>
</body>
</html>