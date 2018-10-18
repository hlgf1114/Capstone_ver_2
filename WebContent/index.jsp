<%@ page contentType="text/html; charset=UTF-8"%>
<%-- <%@ page language="java" pageEncoding="EUC-KR" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"> 
    <link rel="stylesheet" href="./css/index/main.css">
    <title>졸업작품 평가관리 시스템</title>
</head>
<u:notLogin>
<body class="flex-center-row">
	<div class="center_box flex-center-column">	
    <form action="login.do" method="post">
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
            <div class="list_box flex-center-row">
                <!--<span class="list_head">아이디</span>-->
                <input type="text" maxlength="8" placeholder="아이디 입력" name="id" value="${param.id}">
            </div>
            <div class="list_box flex-center-row">
               <!--<span class="list_head">패스워드</span>-->
                <input type="password" placeholder="비밀번호 입력" name="password" >
            </div>
            <div class="guide_box">
            <c:if test="${errors.id}">ID를 입력하세요.</c:if>
            <c:if test="${errors.password}">암호를 입력하세요.</c:if>
            <c:if test="${errors.idOrPwNotMatch}">아이디와 암호가 일치하지 않습니다.</c:if>
            </div>
        </div>
        <div class="button_box flex-center-column">
            <button>Login</button>
	    </div>
 </form>
  		<div class="button_box flex-center-column">
			<button onclick="location.href='join.do';">Join</button>	
		</div> 	
	</div>  
</body>
</u:notLogin>
<u:professor>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/professor/professor_main.css">
    <link rel="stylesheet" href="./css/student/student_main.css">
    <title>Professor</title>
</head><body class="flex-center-row">
<div class="center_box flex-center-row">
    <div class="side_box">
        <div class="info_box flex-center-column">
            <div class="my_info_box flex-center-row">
                <div class="profile_photo">
                    <svg x="0px" y="0px"
                         viewBox="0 0 55 55">
                        <path d="M55,27.5C55,12.337,42.663,0,27.5,0S0,12.337,0,27.5c0,8.009,3.444,15.228,8.926,20.258l-0.026,0.023l0.892,0.752
	c0.058,0.049,0.121,0.089,0.179,0.137c0.474,0.393,0.965,0.766,1.465,1.127c0.162,0.117,0.324,0.234,0.489,0.348
	c0.534,0.368,1.082,0.717,1.642,1.048c0.122,0.072,0.245,0.142,0.368,0.212c0.613,0.349,1.239,0.678,1.88,0.98
	c0.047,0.022,0.095,0.042,0.142,0.064c2.089,0.971,4.319,1.684,6.651,2.105c0.061,0.011,0.122,0.022,0.184,0.033
	c0.724,0.125,1.456,0.225,2.197,0.292c0.09,0.008,0.18,0.013,0.271,0.021C25.998,54.961,26.744,55,27.5,55
	c0.749,0,1.488-0.039,2.222-0.098c0.093-0.008,0.186-0.013,0.279-0.021c0.735-0.067,1.461-0.164,2.178-0.287
	c0.062-0.011,0.125-0.022,0.187-0.034c2.297-0.412,4.495-1.109,6.557-2.055c0.076-0.035,0.153-0.068,0.229-0.104
	c0.617-0.29,1.22-0.603,1.811-0.936c0.147-0.083,0.293-0.167,0.439-0.253c0.538-0.317,1.067-0.648,1.581-1
	c0.185-0.126,0.366-0.259,0.549-0.391c0.439-0.316,0.87-0.642,1.289-0.983c0.093-0.075,0.193-0.14,0.284-0.217l0.915-0.764
	l-0.027-0.023C51.523,42.802,55,35.55,55,27.5z M2,27.5C2,13.439,13.439,2,27.5,2S53,13.439,53,27.5
	c0,7.577-3.325,14.389-8.589,19.063c-0.294-0.203-0.59-0.385-0.893-0.537l-8.467-4.233c-0.76-0.38-1.232-1.144-1.232-1.993v-2.957
	c0.196-0.242,0.403-0.516,0.617-0.817c1.096-1.548,1.975-3.27,2.616-5.123c1.267-0.602,2.085-1.864,2.085-3.289v-3.545
	c0-0.867-0.318-1.708-0.887-2.369v-4.667c0.052-0.519,0.236-3.448-1.883-5.864C34.524,9.065,31.541,8,27.5,8
	s-7.024,1.065-8.867,3.168c-2.119,2.416-1.935,5.345-1.883,5.864v4.667c-0.568,0.661-0.887,1.502-0.887,2.369v3.545
	c0,1.101,0.494,2.128,1.34,2.821c0.81,3.173,2.477,5.575,3.093,6.389v2.894c0,0.816-0.445,1.566-1.162,1.958l-7.907,4.313
	c-0.252,0.137-0.502,0.297-0.752,0.476C5.276,41.792,2,35.022,2,27.5z M42.459,48.132c-0.35,0.254-0.706,0.5-1.067,0.735
	c-0.166,0.108-0.331,0.216-0.5,0.321c-0.472,0.292-0.952,0.57-1.442,0.83c-0.108,0.057-0.217,0.111-0.326,0.167
	c-1.126,0.577-2.291,1.073-3.488,1.476c-0.042,0.014-0.084,0.029-0.127,0.043c-0.627,0.208-1.262,0.393-1.904,0.552
	c-0.002,0-0.004,0.001-0.006,0.001c-0.648,0.16-1.304,0.293-1.964,0.402c-0.018,0.003-0.036,0.007-0.054,0.01
	c-0.621,0.101-1.247,0.174-1.875,0.229c-0.111,0.01-0.222,0.017-0.334,0.025C28.751,52.97,28.127,53,27.5,53
	c-0.634,0-1.266-0.031-1.895-0.078c-0.109-0.008-0.218-0.015-0.326-0.025c-0.634-0.056-1.265-0.131-1.89-0.233
	c-0.028-0.005-0.056-0.01-0.084-0.015c-1.322-0.221-2.623-0.546-3.89-0.971c-0.039-0.013-0.079-0.027-0.118-0.04
	c-0.629-0.214-1.251-0.451-1.862-0.713c-0.004-0.002-0.009-0.004-0.013-0.006c-0.578-0.249-1.145-0.525-1.705-0.816
	c-0.073-0.038-0.147-0.074-0.219-0.113c-0.511-0.273-1.011-0.568-1.504-0.876c-0.146-0.092-0.291-0.185-0.435-0.279
	c-0.454-0.297-0.902-0.606-1.338-0.933c-0.045-0.034-0.088-0.07-0.133-0.104c0.032-0.018,0.064-0.036,0.096-0.054l7.907-4.313
	c1.36-0.742,2.205-2.165,2.205-3.714l-0.001-3.602l-0.23-0.278c-0.022-0.025-2.184-2.655-3.001-6.216l-0.091-0.396l-0.341-0.221
	c-0.481-0.311-0.769-0.831-0.769-1.392v-3.545c0-0.465,0.197-0.898,0.557-1.223l0.33-0.298v-5.57l-0.009-0.131
	c-0.003-0.024-0.298-2.429,1.396-4.36C21.583,10.837,24.061,10,27.5,10c3.426,0,5.896,0.83,7.346,2.466
	c1.692,1.911,1.415,4.361,1.413,4.381l-0.009,5.701l0.33,0.298c0.359,0.324,0.557,0.758,0.557,1.223v3.545
	c0,0.713-0.485,1.36-1.181,1.575l-0.497,0.153l-0.16,0.495c-0.59,1.833-1.43,3.526-2.496,5.032c-0.262,0.37-0.517,0.698-0.736,0.949
	l-0.248,0.283V39.8c0,1.612,0.896,3.062,2.338,3.782l8.467,4.233c0.054,0.027,0.107,0.055,0.16,0.083
	C42.677,47.979,42.567,48.054,42.459,48.132z"/>
                    </svg>
                </div>                
                <div class="profile_desc">
                    <span class="desc">${authProUser.name}교수님 반갑습니다.</span>
                </div>
            </div>
            <div class="button_box flex-space-row">
                <a href="changePwd.do"><button class="prof_btn">패스워드변경</button></a>
                <a href="logout.do"><button class="prof_btn">로그아웃</button></a>
            </div>
        </div>
        <div class="notice_box">
	                	<span class="title_sub">공지사항</span>
	               <ul class="lists">
	               		<c:forEach var="article" items="${articlePage.content}">
	                	<li><span class="txt"><a href="noticeread.do?postNo=${article.postNo}&pageNo=${articlePage.currentPage}">
	                	<c:out value="${article.title}"/></a></span></li>
	                	</c:forEach>
	               </ul>
	        <div class="btn_box flex-center-row">
	        <button class="eval_btn" onclick="location.href='noticewrite.do';">공지사항 업로드</button>
            </div>
        </div>
    </div>
    <div class="main_box">
        <span class="title_sub">팀 목록</span>
        <div class="board">
    	<form action="listTeam.do" method="post">
            <select class="board-select" name="groupNo">
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
            <select class="board-select" name="teamNo">
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
            <select class="board-select" name="date">
                <option value=2018>2018</option>
				<option value=2019>2019</option>
				<option value=2020>2020</option>
            </select> 
        <button type="submit" id="search" class="prof_btn">Search</button>  
    </form>
    <form action="teamlist.do" method="post" name="findFile">
       <input type="hidden" name="team_no" value="${listTno}">
       <input type="hidden" name="eval" value="false">
       <select class="board-select" name="filetype">
            <option value=00>전체보기</option>
            <option value=a>회의록</option>
            <option value=b>제안서</option>
            <option value=c>요구분석서</option>
            <option value=d>설계서</option>
            <option value=e>구현서</option>
            <option value=f>형상관리서</option>
            <option value=g>메뉴얼</option>
            <option value=h>최종보고서</option>            
            </select>
       <c:if test="${main_tName ne null}"><button class="btn_tname" type="submit">${main_tName}</button></c:if>
       </form>

        <div class="board-list-box">               
            <ul class="board-info">
            	<li class="file_num">작성자</li>
                <li class="file_title">제목</li>
                <li class="file_time">게시날짜</li> 
            </ul>             
            <c:forEach var="teamarticle_pro" items="${articleTeamPage.content}" varStatus="status">
            <div class="board-list flex-center-row">
            	<div class="file_num_box file_base">
            		<span class="text-data">${articleTeamPage.stuName[status.index]}</span>
            	</div>
            	<div class="file_title_box file_base">
                            <form action="downloadTeamFile.do" method="post" name="downTeamFile">
                            	<span class="text-data">
                            	<input class="submitLink" type="submit" name="filtitle" value='${teamarticle_pro.title}'>
                            	<input type="hidden" name="fileNo" value="${teamarticle_pro.fileNo}">
                            	<input type="hidden" name="team_no" value="${listTno}">      		
    							</span>
    						</form>
                </div>
                <div class="file_time_box  flex-center-column">
                            <span class="text-data">${teamarticle_pro.regDate}</span>
                </div>
                </div>
				</c:forEach>
				<c:if test="${errors.listTeamNotExist}">팀이 존재하지 않습니다.</c:if>
            	</div>
        	</div>
        	
        	<!-- <form action="makeEvalPlan.do" method="get" name="makeEvalPlan"> -->
        	<form action="EvalTeamList.do" method="get" name="EvalTeamList">
        		<div class="btn_box flex-center-row">
                    <button class="eval_btn" type="submit">전체 팀 보기</button>
             	</div>
            </form> 
        </div>     
    </div>
</body>
</u:professor>

<u:hasTeam>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/student/student_main.css">
    <title>Student</title>
</head>
<body class="flex-center-row">
    <div class="toggle_box flex-space-column">
        <a href="deleteTeam.do">
            <button class="toggle_btn">
                	팀삭제
            </button>
        </a>
        <a href="deleteJoinTeam.do">
            <button class="toggle_btn">
                	팀탈퇴
            </button>
        </a>
    </div>
    <div class="center_box flex-center-row">
        <div class="side_box">
            <div class="info_box flex-center-column">
                <div class="my_info_box flex-center-row">
                    <div class="profile_photo">
                        <svg x="0px" y="0px"
                             viewBox="0 0 55 55">
                            <path d="M55,27.5C55,12.337,42.663,0,27.5,0S0,12.337,0,27.5c0,8.009,3.444,15.228,8.926,20.258l-0.026,0.023l0.892,0.752
        c0.058,0.049,0.121,0.089,0.179,0.137c0.474,0.393,0.965,0.766,1.465,1.127c0.162,0.117,0.324,0.234,0.489,0.348
        c0.534,0.368,1.082,0.717,1.642,1.048c0.122,0.072,0.245,0.142,0.368,0.212c0.613,0.349,1.239,0.678,1.88,0.98
        c0.047,0.022,0.095,0.042,0.142,0.064c2.089,0.971,4.319,1.684,6.651,2.105c0.061,0.011,0.122,0.022,0.184,0.033
        c0.724,0.125,1.456,0.225,2.197,0.292c0.09,0.008,0.18,0.013,0.271,0.021C25.998,54.961,26.744,55,27.5,55
        c0.749,0,1.488-0.039,2.222-0.098c0.093-0.008,0.186-0.013,0.279-0.021c0.735-0.067,1.461-0.164,2.178-0.287
        c0.062-0.011,0.125-0.022,0.187-0.034c2.297-0.412,4.495-1.109,6.557-2.055c0.076-0.035,0.153-0.068,0.229-0.104
        c0.617-0.29,1.22-0.603,1.811-0.936c0.147-0.083,0.293-0.167,0.439-0.253c0.538-0.317,1.067-0.648,1.581-1
        c0.185-0.126,0.366-0.259,0.549-0.391c0.439-0.316,0.87-0.642,1.289-0.983c0.093-0.075,0.193-0.14,0.284-0.217l0.915-0.764
        l-0.027-0.023C51.523,42.802,55,35.55,55,27.5z M2,27.5C2,13.439,13.439,2,27.5,2S53,13.439,53,27.5
        c0,7.577-3.325,14.389-8.589,19.063c-0.294-0.203-0.59-0.385-0.893-0.537l-8.467-4.233c-0.76-0.38-1.232-1.144-1.232-1.993v-2.957
        c0.196-0.242,0.403-0.516,0.617-0.817c1.096-1.548,1.975-3.27,2.616-5.123c1.267-0.602,2.085-1.864,2.085-3.289v-3.545
        c0-0.867-0.318-1.708-0.887-2.369v-4.667c0.052-0.519,0.236-3.448-1.883-5.864C34.524,9.065,31.541,8,27.5,8
        s-7.024,1.065-8.867,3.168c-2.119,2.416-1.935,5.345-1.883,5.864v4.667c-0.568,0.661-0.887,1.502-0.887,2.369v3.545
        c0,1.101,0.494,2.128,1.34,2.821c0.81,3.173,2.477,5.575,3.093,6.389v2.894c0,0.816-0.445,1.566-1.162,1.958l-7.907,4.313
        c-0.252,0.137-0.502,0.297-0.752,0.476C5.276,41.792,2,35.022,2,27.5z M42.459,48.132c-0.35,0.254-0.706,0.5-1.067,0.735
        c-0.166,0.108-0.331,0.216-0.5,0.321c-0.472,0.292-0.952,0.57-1.442,0.83c-0.108,0.057-0.217,0.111-0.326,0.167
        c-1.126,0.577-2.291,1.073-3.488,1.476c-0.042,0.014-0.084,0.029-0.127,0.043c-0.627,0.208-1.262,0.393-1.904,0.552
        c-0.002,0-0.004,0.001-0.006,0.001c-0.648,0.16-1.304,0.293-1.964,0.402c-0.018,0.003-0.036,0.007-0.054,0.01
        c-0.621,0.101-1.247,0.174-1.875,0.229c-0.111,0.01-0.222,0.017-0.334,0.025C28.751,52.97,28.127,53,27.5,53
        c-0.634,0-1.266-0.031-1.895-0.078c-0.109-0.008-0.218-0.015-0.326-0.025c-0.634-0.056-1.265-0.131-1.89-0.233
        c-0.028-0.005-0.056-0.01-0.084-0.015c-1.322-0.221-2.623-0.546-3.89-0.971c-0.039-0.013-0.079-0.027-0.118-0.04
        c-0.629-0.214-1.251-0.451-1.862-0.713c-0.004-0.002-0.009-0.004-0.013-0.006c-0.578-0.249-1.145-0.525-1.705-0.816
        c-0.073-0.038-0.147-0.074-0.219-0.113c-0.511-0.273-1.011-0.568-1.504-0.876c-0.146-0.092-0.291-0.185-0.435-0.279
        c-0.454-0.297-0.902-0.606-1.338-0.933c-0.045-0.034-0.088-0.07-0.133-0.104c0.032-0.018,0.064-0.036,0.096-0.054l7.907-4.313
        c1.36-0.742,2.205-2.165,2.205-3.714l-0.001-3.602l-0.23-0.278c-0.022-0.025-2.184-2.655-3.001-6.216l-0.091-0.396l-0.341-0.221
        c-0.481-0.311-0.769-0.831-0.769-1.392v-3.545c0-0.465,0.197-0.898,0.557-1.223l0.33-0.298v-5.57l-0.009-0.131
        c-0.003-0.024-0.298-2.429,1.396-4.36C21.583,10.837,24.061,10,27.5,10c3.426,0,5.896,0.83,7.346,2.466
        c1.692,1.911,1.415,4.361,1.413,4.381l-0.009,5.701l0.33,0.298c0.359,0.324,0.557,0.758,0.557,1.223v3.545
        c0,0.713-0.485,1.36-1.181,1.575l-0.497,0.153l-0.16,0.495c-0.59,1.833-1.43,3.526-2.496,5.032c-0.262,0.37-0.517,0.698-0.736,0.949
        l-0.248,0.283V39.8c0,1.612,0.896,3.062,2.338,3.782l8.467,4.233c0.054,0.027,0.107,0.055,0.16,0.083
        C42.677,47.979,42.567,48.054,42.459,48.132z"/>
                        </svg>
                    </div>
                    <div class="profile_desc">
                        <span class="desc">>${authStdUser.name}님 반갑습니다.</span>
                    </div>
                
                    <a href="changePwd.do"><button class="prof_btn">패스워드변경</button></a>
                    <a href="logout.do"><button class="prof_btn">로그아웃</button></a>
                </div>
            </div>
            <div class="notice_box">
	                	<span class="title_sub">공지사항</span>
	               <ul class="lists">
	               		<c:forEach var="article" items="${articlePage.content}">
	                	<li><a class="txt" href="noticeread.do?postNo=${article.postNo}&pageNo=${articlePage.currentPage}">
	                	<c:out value="${article.title}"/></a></li>
	                	</c:forEach>
            		</ul>
            </div>
        </div>
        <div class="main_box">
            <span class="title_sub">팀 게시판</span>
            <div class="board">
                <div class="search_box flex-center-row">
                <form action="teamlist.do" method="post">
                    <select class="board-select" name="filetype">
                    	<option value="00">전체보기</option>
                		<option value="a">회의록</option>
                		<option value="b">제안서</option>
                		<option value="c">요구분석서</option>
                		<option value="d">설계서</option>
                		<option value="e">구현서</option>
                		<option value="f">형상관리서</option>
                		<option value="g">메뉴얼</option>
                		<option value="h">최종보고서</option> 
                    </select>
                    <button type="submit" id="search">찾기</button>
                 </form>
                </div>
                <div class="board-list-box">
                    <ul class="board-info">
                        <li class="file_num">작성자</li>
                        <li class="file_title">제목</li>
                        <li class="file_time">게시날짜</li>           
                    </ul>
                    <c:forEach var="teamarticle1" items="${articleTeamPage.content}" varStatus="status">
                    <c:if test="${allowed == 'no'}" var="result">
                        		글 작성자가 아닙니다. <br>
                    </c:if>
                    <div class="board-list flex-center-row">
                        <div class="file_num_box file_base">
                     <span class="text-data">${articleTeamPage.stuName[status.index]}</span>
                        </div>
                        <div class="file_title_box file_base">
                            <form action="downloadTeamFile.do" method="post" name="downTeamFile">
                               <span class="text-data">
                               	<input class="submitLink" type="submit" name="filtitle" value='${teamarticle1.title}'>
                               		<input class = "mod_file_class" type="hidden" name="fileNo" value="${teamarticle1.fileNo}">           
                         	   </span>
                      		</form>
                        </div>
                        <div class="file_time_box  flex-center-column">
                            <span class="text-data_date">${teamarticle1.regDate}</span>
       
<!--                                 <button class="modify_section" id = "modify">수정</button> -->
                         		<form action="teamarticledelete.do" method="post" name="deleteTeamFile">                                 
                                  <div class="edit_box">
                                     <button class="board_editBtn" id="teamWriteDelete" type="submit">삭제</button>
                                     <input type="hidden" name="fileNo" value="${teamarticle1.fileNo}">
                                     <c:if test="${teamarticle1.writer.writerId}">암호를 입력하세요.</c:if> 
                                  </div>              
                                </form>
                        </div>
                    </div>
                    </c:forEach>
                    <c:if test="${allowed == 'no'}" var="result">
						글 작성자가 아닙니다. <br>
					</c:if>
                </div>
            </div>   
                <div class="btn_box flex-space-row">
                    <a href="#"><button class="writing" id="write">글쓰기</button></a>
                    <form action="showResultList.do" method="post" name="showResultList">
                    <button class="writing" id="resultlist">결과보기</button>
                    <c:if test = "${evalstate eq 'aaaa' }"><script>alert('평가가 종료되지 않았습니다.');</script></c:if>
                    <c:if test = "${notstarted eq 'ddd' }"><script>alert('평가가 시작되지 않았습니다.');</script></c:if>
                    </form>
                </div>
        	</div>
        <div class="write_box" id="write_board">
          <form action="teamwrite.do" method="post" enctype="multipart/form-data">       
            <div class="w_title_box flex-center-row">
                <div class="list_box flex-center-row">
                    <select class="custom-select" name="filetype" id="teamFileType">
                        <option value="">분류선택</option>
            			<option value=a>회의록</option>
            			<option value=b>제안서</option>
            			<option value=c>요구분석서</option>
            			<option value=d>설계서</option>
            			<option value=e>구현서</option>
            			<option value=f>형상관리서</option>
            			<option value=g>메뉴얼</option>
            			<option value=h>최종보고서</option>
                    </select>
                </div>
            </div>
            
            <input type="hidden" name="fileNo" value="${teamarticle.fileNo}">
            <div class="w_title_box flex-center-row">
                <input type="text" name="title" id="fileTitle" placeholder="제목">
                <c:if test="${errors.title}">제목을 입력하세요.</c:if>
            </div>
            <div class="w_title_box flex-center-row">
                <input type="file" name="file" id="teamFile">
                ${param.file}
            </div>
            <div class="w_title_box flex-space-row">
                <button type="reset" id ="cancel">취소</button>
                <button type="submit" id="register">등록</button>
            </div>
            <c:if test="${errors.NotAllw}">잘못된 값입니다.</c:if>
          </form>        
        </div>    
         <div class="write_box" id="modify_board">
          <form action="teamarticlemodify.do" method="post" enctype="multipart/form-data">       
            <input id="select_file" type="hidden" name="select_file" value="">
            <div class="w_title_box flex-center-row">
                <input type="text" name="title" placeholder="제목">
                <c:if test="${errors.title}">제목을 입력하세요.</c:if>
            </div>
            <div class="w_title_box flex-center-row">
                <input type="file" name="file">
                ${param.file}
            </div>
            <div class="w_title_box flex-space-row">
                <button type="reset" id ="modify_cancel">취소</button>
                <button type="submit" id="mod_register" >수정</button>
            </div>
          </form>
        </div>   	
	</div>
<!-- <script src="src/dealert.js"></script> -->
<script src="src/modify.js"></script>
<script src="src/write.js"></script>
<script src="src/alert.js"></script>
<script src="src/mod_alert.js"></script>
</body>
</u:hasTeam>
<u:hasnTeam>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/student/student_main.css">
    <title>Student</title>
</head>
<body class="flex-center-row">
	<div class="toggle_box flex-space-column">
        <a href="createTeam.do">
            <button class="toggle_btn">
                	팀생성
            </button>
        </a>
        <a href="selectTeam.do"><button class="toggle_btn">
            	팀참가
        </button>
        </a>
    </div>
    <div class="center_box flex-center-row">
        <div class="side_box">
            <div class="info_box flex-center-column">
                <div class="my_info_box flex-center-row">
                    <div class="profile_photo">
                        <svg x="0px" y="0px"
	 viewBox="0 0 55 55">
<path d="M55,27.5C55,12.337,42.663,0,27.5,0S0,12.337,0,27.5c0,8.009,3.444,15.228,8.926,20.258l-0.026,0.023l0.892,0.752
	c0.058,0.049,0.121,0.089,0.179,0.137c0.474,0.393,0.965,0.766,1.465,1.127c0.162,0.117,0.324,0.234,0.489,0.348
	c0.534,0.368,1.082,0.717,1.642,1.048c0.122,0.072,0.245,0.142,0.368,0.212c0.613,0.349,1.239,0.678,1.88,0.98
	c0.047,0.022,0.095,0.042,0.142,0.064c2.089,0.971,4.319,1.684,6.651,2.105c0.061,0.011,0.122,0.022,0.184,0.033
	c0.724,0.125,1.456,0.225,2.197,0.292c0.09,0.008,0.18,0.013,0.271,0.021C25.998,54.961,26.744,55,27.5,55
	c0.749,0,1.488-0.039,2.222-0.098c0.093-0.008,0.186-0.013,0.279-0.021c0.735-0.067,1.461-0.164,2.178-0.287
	c0.062-0.011,0.125-0.022,0.187-0.034c2.297-0.412,4.495-1.109,6.557-2.055c0.076-0.035,0.153-0.068,0.229-0.104
	c0.617-0.29,1.22-0.603,1.811-0.936c0.147-0.083,0.293-0.167,0.439-0.253c0.538-0.317,1.067-0.648,1.581-1
	c0.185-0.126,0.366-0.259,0.549-0.391c0.439-0.316,0.87-0.642,1.289-0.983c0.093-0.075,0.193-0.14,0.284-0.217l0.915-0.764
	l-0.027-0.023C51.523,42.802,55,35.55,55,27.5z M2,27.5C2,13.439,13.439,2,27.5,2S53,13.439,53,27.5
	c0,7.577-3.325,14.389-8.589,19.063c-0.294-0.203-0.59-0.385-0.893-0.537l-8.467-4.233c-0.76-0.38-1.232-1.144-1.232-1.993v-2.957
	c0.196-0.242,0.403-0.516,0.617-0.817c1.096-1.548,1.975-3.27,2.616-5.123c1.267-0.602,2.085-1.864,2.085-3.289v-3.545
	c0-0.867-0.318-1.708-0.887-2.369v-4.667c0.052-0.519,0.236-3.448-1.883-5.864C34.524,9.065,31.541,8,27.5,8
	s-7.024,1.065-8.867,3.168c-2.119,2.416-1.935,5.345-1.883,5.864v4.667c-0.568,0.661-0.887,1.502-0.887,2.369v3.545
	c0,1.101,0.494,2.128,1.34,2.821c0.81,3.173,2.477,5.575,3.093,6.389v2.894c0,0.816-0.445,1.566-1.162,1.958l-7.907,4.313
	c-0.252,0.137-0.502,0.297-0.752,0.476C5.276,41.792,2,35.022,2,27.5z M42.459,48.132c-0.35,0.254-0.706,0.5-1.067,0.735
	c-0.166,0.108-0.331,0.216-0.5,0.321c-0.472,0.292-0.952,0.57-1.442,0.83c-0.108,0.057-0.217,0.111-0.326,0.167
	c-1.126,0.577-2.291,1.073-3.488,1.476c-0.042,0.014-0.084,0.029-0.127,0.043c-0.627,0.208-1.262,0.393-1.904,0.552
	c-0.002,0-0.004,0.001-0.006,0.001c-0.648,0.16-1.304,0.293-1.964,0.402c-0.018,0.003-0.036,0.007-0.054,0.01
	c-0.621,0.101-1.247,0.174-1.875,0.229c-0.111,0.01-0.222,0.017-0.334,0.025C28.751,52.97,28.127,53,27.5,53
	c-0.634,0-1.266-0.031-1.895-0.078c-0.109-0.008-0.218-0.015-0.326-0.025c-0.634-0.056-1.265-0.131-1.89-0.233
	c-0.028-0.005-0.056-0.01-0.084-0.015c-1.322-0.221-2.623-0.546-3.89-0.971c-0.039-0.013-0.079-0.027-0.118-0.04
	c-0.629-0.214-1.251-0.451-1.862-0.713c-0.004-0.002-0.009-0.004-0.013-0.006c-0.578-0.249-1.145-0.525-1.705-0.816
	c-0.073-0.038-0.147-0.074-0.219-0.113c-0.511-0.273-1.011-0.568-1.504-0.876c-0.146-0.092-0.291-0.185-0.435-0.279
	c-0.454-0.297-0.902-0.606-1.338-0.933c-0.045-0.034-0.088-0.07-0.133-0.104c0.032-0.018,0.064-0.036,0.096-0.054l7.907-4.313
	c1.36-0.742,2.205-2.165,2.205-3.714l-0.001-3.602l-0.23-0.278c-0.022-0.025-2.184-2.655-3.001-6.216l-0.091-0.396l-0.341-0.221
	c-0.481-0.311-0.769-0.831-0.769-1.392v-3.545c0-0.465,0.197-0.898,0.557-1.223l0.33-0.298v-5.57l-0.009-0.131
	c-0.003-0.024-0.298-2.429,1.396-4.36C21.583,10.837,24.061,10,27.5,10c3.426,0,5.896,0.83,7.346,2.466
	c1.692,1.911,1.415,4.361,1.413,4.381l-0.009,5.701l0.33,0.298c0.359,0.324,0.557,0.758,0.557,1.223v3.545
	c0,0.713-0.485,1.36-1.181,1.575l-0.497,0.153l-0.16,0.495c-0.59,1.833-1.43,3.526-2.496,5.032c-0.262,0.37-0.517,0.698-0.736,0.949
	l-0.248,0.283V39.8c0,1.612,0.896,3.062,2.338,3.782l8.467,4.233c0.054,0.027,0.107,0.055,0.16,0.083
	C42.677,47.979,42.567,48.054,42.459,48.132z"/>
</svg>
                    </div>
       <div class="profile_desc">
                        <span class="desc">${authStdUser.name}님 반갑습니다.</span>
                    </div>
                </div>
                <div class="button_box flex-space-row">
                    <a href="changePwd.do"><button class="prof_btn">패스워드변경</button></a>
                	<a href="logout.do"><button class="prof_btn">로그아웃</button></a>
                </div>
            </div>
            <div class="notice_box">
	                	<span class="title_sub">공지사항</span>
	               <ul class="lists">
	               		<c:forEach var="article" items="${articlePage.content}">
	                	<li><span class="txt"><a href="noticeread.do?postNo=${article.postNo}&pageNo=${articlePage.currentPage}">
	                	<c:out value="${article.title}"/></a></span></li>
	                	</c:forEach>
	            		</ul>
            </div>
        </div>
        <div class="main_box">
            <span class="title_sub">팀 게시판</span>
            <div class="options_box flex-space-row">
                <p> 팀 생성 및 참가를 해야 이용가능합니다.</p>
            </div>
        </div>
     </div>
</body>
</u:hasnTeam>
</body>
</html>
