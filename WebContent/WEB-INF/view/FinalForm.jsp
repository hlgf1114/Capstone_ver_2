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
</head>
<body class="flex-center-row">
<div class="center_box">
	<form action="EvaluateFinal.do" method="post" name="evalfinal">
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
                <span class="question_txt">교수 평가결과</span><br><br>
                <c:forEach var="List" items="${EvalResultList}" varStatus="status">
                	<span>${List.proName}&nbsp;&nbsp;${List.total}&nbsp;&nbsp;${List.result}</span><br>
                </c:forEach><br>
                <span class="question_txt">교수 평가 평균 점수 : <span>${efinal.avg}</span></span><br><br>
                <span class="question_txt">최종 평가</span>
                <textarea cols="30" rows="3" name="comment1">${efinal.comment}</textarea>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="rs_val" value="1" >합격(>44)</li>
                    <li class="radio_txt"><input type="radio" name="rs_val" value="2" >재심사(>34)</li>
                    <li class="radio_txt"><input type="radio" name="rs_val" value="3" >불합격</li>
                </ul>
            </div>
    	</div>
    </div>
    <div class="result_box">
        <div class="lastEval_box">
            <ul class="btn_box flex-space-row">
                <li class="radio_txt">
             	<button class="option-button" name="final_select" value="save">저장</button>
             	<input type="hidden" name="team_no" value="${team_no}">
             	</li>
                <li class="radio_txt">
                <button class="option-button" name="final_select" value="complete" >완료</button>
                </li> 
            </ul>
        </div>
    </div>
    </form>
</div>
</body>
</html>