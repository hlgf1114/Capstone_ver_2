<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/lastEvalForm/lastEvalForm.css">
    <title>최종 심사서</title>
    <script>
	function goBack() {
	    history.go(-1);
	}
	</script>
</head>
<body class="flex-center-row">
<div class="center_box">
	<form action="showFinalResult.do" method="post" name="finalresult">
    <div class="title_box">
        <span class="_title">졸업작품 최종 심사서</span>
    </div>
    <div class="evalForm_box">
        <div class="classInfo_box flex-space-row">
            <ul class="left_box left_txt">
                <li class="left_li">조 이름</li>
                <li class="left_li">팀원</li>
            </ul>
            <ul class="right_box">
                <li class="right_li">
                    <span>${teamName}</span>
                </li>
                <c:forEach var="stu" items="${memberList}">
                	<li class="right_li flex-center-row">
                            <div class="id-box">
                                <span class="id">${stu.id}</span>
                            </div>
                            <div class="name-box">
                                <span class="name">${stu.name}</span>
                            </div>
                        </li>
	            </c:forEach>
            </ul>
        </div>
        <div class="check_box">
            <div class="question_box">
                <span class="question_txt">교수 평가 결과</span><br><br>
                <c:forEach var="List" items="${EvalResultList}" varStatus="status">
                	<span>${List.proName}&nbsp;&nbsp;${List.total}&nbsp;&nbsp;${List.result}</span><br>
                </c:forEach><br>
                <span class="question_txt">교수 평가 평균 점수 : <span>${evalfinal.avg}</span></span><br><br>
               <span class="question_txt">최종 평가</span>
                <span>${evalfinal.comment}</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_1" value="1"<c:if test = "${ '1' eq evalfinal.result}">checked</c:if>>합격(>44)</li>
                    <li class="radio_txt"><input type="radio" name="val_1" value="2"<c:if test = "${ '2' eq evalfinal.result}">checked</c:if>>재심사(>34)</li>
                    <li class="radio_txt"><input type="radio" name="val_1" value="3"<c:if test = "${ '3' eq evalfinal.result}">checked</c:if>>불합격</li>
                </ul>
            </div>
    	</div>
    </div>
    </form>
     <div class="result_box">
	        <div class="lastEval_box">
	            <ul class="btn_box flex-space-row">
	                <li class="radio_txt">
	             		<button class="option-button" name="com1" value="confirmed" onclick="goBack()">확인</button>
	             	</li>
	            </ul>
	        </div>
	    </div>
</div>
</body>
</html>