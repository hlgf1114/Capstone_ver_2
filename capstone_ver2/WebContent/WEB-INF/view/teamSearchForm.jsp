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
    <title>Join Team</title>
   
</head>
<body class="flex-center-row">
<div class="center_box">

<form action="selectTeam.do" method="post">
<%-- <script>
	function join(){
		var tno = <%=session.getAttribute("tno")%>;
		document.joinForm.joinTeam.value = tno;
		document.joinForm.submit();
	}
</script>--%>
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
        </div>
    </div>
    <div class="sign_box">
        <div class="list_box flex-center-row">
            <select class="custom-select" name="date">
                <option value=2018>2018</option>
				<option value=2019>2019</option>
				<option value=2020>2020</option>				
            </select>
        </div>
    </div> 	
    	<div class="button_box flex-center-column">
        	<button type="submit" id="search">Search</button>   
    	</div>
</form>
    	팀이름 : ${tName}<br>
    	지도교수님 : ${advisor}<br>
        <c:if test="${errors.NotTeam1}">팀이 존재하지 않습니다.</c:if>
    <form action="joinTeam.do" method="post" name="joinForm">
    	<div class="button_box flex-center-column">
    	<input type="hidden" name="team_no" value="${tno}">
        	<button type="submit">Join Team</button>   
        	<c:if test="${errors.NotExistTeams}">팀을 참가할 수 없습니다.</c:if>
    	</div>
    </form>
    	<div class="button_box flex-center-column">
        	<a href="/Capstone/index.jsp"><button>메인으로</button></a>   
    	</div>
</div>
</body>
</html>