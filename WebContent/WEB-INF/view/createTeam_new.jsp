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
    <title>Create Team</title>
</head>
<body class="flex-center-row">
<form action="createTeam.do" method="post">
<div class="center_box">
    <div class="title_box">
        <span class="_title">졸업작품 평가관리 시스템</span>
    </div>
    <div class="sign_box">
        <div class="list_box flex-center-row">
            <select class="custom-select" name="groupNo">
                <option value=01>월요일오전</option>
				<option value=02>월요일오후</option>
				<option value=03>화요일오전</option>
				<option value=04>화요일오후</option>
				<option value=05>수요일오전</option>
				<option value=06>수요일오후</option>
				<option value=07>목요일오전</option>
				<option value=08>목요일오후</option>
				<option value=09>금요일오전</option>
				<option value=10>금요일오후</option>
            </select>
        </div>
    </div>
    <div class="sign_box">
        <div class="list_box flex-center-row">
            <select class="custom-select" name="teamNo">
             	<option value=01>1조</option>
      	 	 	<option value=02>2조</option>
     		 	<option value=03>3조</option>
     		 	<option value=04>4조</option>
      		 	<option value=05>5조</option>
      		 	<option value=06>6조</option>
      	 	 	<option value=07>7조</option>
      		 	<option value=08>8조</option>
      		 	<option value=09>9조</option>
      		 	<option value=10>10조</option>
      		 	<option value=11>11조</option>
      		 	<option value=12>12조</option>
            </select>
            <c:if test="${errors.teamNo}">팀번호를 입력하세요.</c:if>
  		    
        </div>
    </div>
    <div class="join_box">
    <div class="list_box flex-center-row">
            <input type="text" name="teamName" value="${param.teamName}" maxlength="50" placeholder="팀이름 입력">
        </div>
    </div>
    <div class="sign_box">
    <div class="list_box flex-center-row">
       <select class="custom-select" name="advisor">
       		<c:forEach var="pro" items="${proList.list}" varStatus="status">
                 	<option value="${pro.proId}">${pro.proName}</option>
            </c:forEach>
       </select>    
    </div>
    <div class="button_box flex-center-column">
        <button type="submit">Create Team</button>
    </div>
    <p class="maketeam"><c:if test="${errors.teamName}">팀이름를 입력하세요.</c:if></p>
	<p class="maketeam"><c:if test="${errors.ExistTeam}">이미 사용중인 팀이름입니다.</c:if></p>
	<p class="maketeam"><c:if test="${errors.duplicateTeam}">이미 사용중인 팀번호입니다.</c:if></p>
</div>
</div>
</form>
</body>
</html>