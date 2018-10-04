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
    <link rel="stylesheet" href="./css/evalForm/evaluationForm.css">
    <title>평가지</title>
</head>
<body class="flex-center-row">
<div class="center_box">
	<form action="EvaluateTeam.do" method="post" name="evalteam">
    <div class="title_box">
        <span class="_title">졸업작품 심사서</span>
    </div>
    <div class="evalForm_box">
        <div class="classInfo_box flex-space-row">
            <ul class="left_box left_txt">
                <li class="left_li">조 이름</li>
                <li class="left_li">팀원</li>
            </ul>
            <ul class="right_box">
                <li class="right_li">
                    <span>${team_name}</span>
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
                <span class="question_txt">1.작품의 내용과 제목이 적절하게 부합되었는가?</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_1" value="10"<c:if test = "${ '10' eq v1}">checked</c:if>>아주우수</li>
                    <li class="radio_txt"><input type="radio" name="val_1" value="8"<c:if test = "${ '8' eq v1}">checked</c:if>>우수</li>
                    <li class="radio_txt"><input type="radio" name="val_1" value="6"<c:if test = "${ '6' eq v1}">checked</c:if>>보통</li>
                    <li class="radio_txt"><input type="radio" name="val_1" value="4"<c:if test = "${ '4' eq v1}">checked</c:if>>불량</li>
                    <li class="radio_txt"><input type="radio" name="val_1" value="2"<c:if test = "${ '2' eq v1}">checked</c:if>>매우불량</li>
                </ul>
                <span class="opinion">의견작성</span>
                <textarea cols="30" rows="3" name="comment1">${c1}</textarea>
            </div>
            <div class="question_box">
                <span class="question_txt">2.작품의 내용이 독창적인가?</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_2" value="10"<c:if test = "${ '10' eq v2}">checked</c:if>>아주우수</li>
                    <li class="radio_txt"><input type="radio" name="val_2" value="8"<c:if test = "${ '8' eq v2}">checked</c:if>>우수</li>
                    <li class="radio_txt"><input type="radio" name="val_2" value="6"<c:if test = "${ '6' eq v2}">checked</c:if>>보통</li>
                    <li class="radio_txt"><input type="radio" name="val_2" value="4"<c:if test = "${ '4' eq v2}">checked</c:if>>불량</li>
                    <li class="radio_txt"><input type="radio" name="val_2" value="2"<c:if test = "${ '2' eq v2}">checked</c:if>>매우불량</li>
                </ul>
                <span class="opinion">의견작성</span>
                <textarea cols="30" rows="3" name="comment2">${c2}</textarea>
            </div>
            <div class="question_box">
                <span class="question_txt">3.작품의 구성은 적절한가(목표설정, 요구분석, 설계, 구현 등)?</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_3" value="10"<c:if test = "${ '10' eq v3}">checked</c:if>>아주우수</li>
                    <li class="radio_txt"><input type="radio" name="val_3" value="8"<c:if test = "${ '8' eq v3}">checked</c:if>>우수</li>
                    <li class="radio_txt"><input type="radio" name="val_3" value="6"<c:if test = "${ '6' eq v3}">checked</c:if>>보통</li>
                    <li class="radio_txt"><input type="radio" name="val_3" value="4"<c:if test = "${ '4' eq v3}">checked</c:if>>불량</li>
                    <li class="radio_txt"><input type="radio" name="val_3" value="2"<c:if test = "${ '2' eq v3}">checked</c:if>>매우불량</li>
                </ul>
                <span class="opinion">의견작성</span>
                <textarea cols="30" rows="3" name="comment3">${c3}</textarea>
            </div>
            <div class="question_box">
                <span class="question_txt">4.작품의 완성도는?</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_4" value="10"<c:if test = "${ '10' eq v4}">checked</c:if>>아주우수</li>
                    <li class="radio_txt"><input type="radio" name="val_4" value="8"<c:if test = "${ '8' eq v4}">checked</c:if>>우수</li>
                    <li class="radio_txt"><input type="radio" name="val_4" value="6"<c:if test = "${ '6' eq v4}">checked</c:if>>보통</li>
                    <li class="radio_txt"><input type="radio" name="val_4" value="4"<c:if test = "${ '4' eq v4}">checked</c:if>>불량</li>
                    <li class="radio_txt"><input type="radio" name="val_4" value="2"<c:if test = "${ '2' eq v4}">checked</c:if>>매우불량</li>
                </ul>
                <span class="opinion">의견작성</span>
                <textarea cols="30" rows="3" name="comment4">${c4}</textarea>
            </div>
            <div class="question_box">
                <span class="question_txt">5.작품에 대한 문서유지와 논문 구성은 적절한가?</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_5" value="10"<c:if test = "${ '10' eq v5}">checked</c:if>>아주우수</li>
                    <li class="radio_txt"><input type="radio" name="val_5" value="8"<c:if test = "${ '8' eq v5}">checked</c:if>>우수</li>
                    <li class="radio_txt"><input type="radio" name="val_5" value="6"<c:if test = "${ '6' eq v5}">checked</c:if>>보통</li>
                    <li class="radio_txt"><input type="radio" name="val_5" value="4"<c:if test = "${ '4' eq v5}">checked</c:if>>불량</li>
                    <li class="radio_txt"><input type="radio" name="val_5" value="2"<c:if test = "${ '2' eq v5}">checked</c:if>>매우불량</li>
                </ul>
                <span class="opinion">의견작성</span>
                <textarea cols="30" rows="3" name="comment5">${c5}</textarea>
            </div>
            <div class="question_box">
                <span class="question_txt">6.작품에 대한 소개문서는 적절한가?</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_6" value="10"<c:if test = "${ '10' eq v6}">checked</c:if>>아주우수</li>
                    <li class="radio_txt"><input type="radio" name="val_6" value="8"<c:if test = "${ '8' eq v6}">checked</c:if>>우수</li>
                    <li class="radio_txt"><input type="radio" name="val_6" value="6"<c:if test = "${ '6' eq v6}">checked</c:if>>보통</li>
                    <li class="radio_txt"><input type="radio" name="val_6" value="4"<c:if test = "${ '4' eq v6}">checked</c:if>>불량</li>
                    <li class="radio_txt"><input type="radio" name="val_6" value="2"<c:if test = "${ '2' eq v6}">checked</c:if>>매우불량</li>
                </ul>
                <span class="opinion">의견작성</span>
                <textarea cols="30" rows="3" name="comment6">${c6}</textarea>
            </div>
            <div class="question_box">
                <span class="question_txt">7.팀원간의 협력이 잘 이루어졌는가?</span>
                <ul class="btn_box flex-space-row">
                    <li class="radio_txt"><input type="radio" name="val_7" value="10"<c:if test = "${ '10' eq v7}">checked</c:if>>아주우수</li>
                    <li class="radio_txt"><input type="radio" name="val_7" value="8"<c:if test = "${ '8' eq v7}">checked</c:if>>우수</li>
                    <li class="radio_txt"><input type="radio" name="val_7" value="6"<c:if test = "${ '6' eq v7}">checked</c:if>>보통</li>
                    <li class="radio_txt"><input type="radio" name="val_7" value="4"<c:if test = "${ '4' eq v7}">checked</c:if>>불량</li>
                    <li class="radio_txt"><input type="radio" name="val_7" value="2"<c:if test = "${ '2' eq v7}">checked</c:if>>매우불량</li>
                </ul>
                <span class="opinion">의견작성</span>
                <textarea cols="30" rows="3" name="comment7">${c7}</textarea>
            </div>
        </div>
    </div>
    <div class="result_box">
        <div class="lastEval_box">
            <span class="eval-txt">평가점수 : </span><span class="eval-score" id="result">0점</span>
         </div>
            <ul class="btn_box flex-space-row">
                <li class="radio_txt">
             	<button class="option-button" name="select" value="save">저장</button>
             	<input type="hidden" name="team_no" value="${team_no}">
             	</li>
                <li class="radio_txt"><button class="option-button" name="select" value="complete" >완료</button></li>
            </ul>
        </div>
    </form>
</div>
<script src="src/evaluating-countScore.js"></script>
</body>
</html>