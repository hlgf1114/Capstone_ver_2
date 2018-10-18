<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!doctype html>
<html lang="en">
<u:student>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/view-notice/view-notice.css">
    <title>공지사항보기</title>
</head>
<body class="flex-center-row">
<div class="center_box">
    <h2>공지사항 보기</h2>
    <div class="tit-area flex-center-row">
        <span class="notice-input-tit">
            <c:out value='${noticeData.notice.title}' />
        </span>
    </div>
    <div class="txt-area flex-center-row">
        <span class="notice-input-txt" >
            <c:out value='${noticeData.content}'/>
        </span>
    </div>
    <div class="file-area flex-center-row">
    <form action="downloadFile.do" method="post" name="downFile">
        <span class="file-name">
        	<a><input type="submit" class="submitLink" name="filename" value='${noticeData.file}'></a>	
        </span>  
    </form>    
    </div>
    <div class="button-area flex-center-row">
        <a href="/Capstone/index.jsp"><button class="back-button">뒤로가기</button></a>
    </div>
</div>
</body>
</u:student>
<u:professor>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/view-notice/view-notice.css">
    <title>공지사항보기</title>
</head>
<body class="flex-center-row">
<div class="center_box">
    <h2>공지사항 보기</h2>
    <div class="tit-area flex-center-row">
        <span class="notice-input-tit">
            <c:out value='${noticeData.notice.title}' />
        </span>
    </div>
    <div class="txt-area flex-center-row">
        <span class="notice-input-txt" >
           	<c:out value='${noticeData.content}'/>
        </span>
    </div>
    <div class="file-area flex-center-row">
    <form action="downloadFile.do" method="post" name="downFile">
        <span class="file-name">
        	<a><input type="submit" class="submitLink" name="filename" value='${noticeData.file}'></a>	
        </span>  
    </form>    
    </div>
    <div class="button-area flex-center-row">
        <a href="/Capstone/index.jsp"><button class="back-button">뒤로가기</button></a>
        <a href="noticemodify.do?no=${noticeData.notice.postNo}"><button class="back-button">게시글수정</button></a>
		<a href="noticedelete.do?no=${noticeData.notice.postNo}"><button class="back-button">게시글삭제</button></a>
		<c:if test="${errors.NotExistNoticeFile}">파일이 존재하지 않습니다.</c:if>
		<c:if test="${authUser.id == noticeData.notice.writer.id}">
		</c:if>
    </div>
</div>
</body>
</u:professor>
</html>