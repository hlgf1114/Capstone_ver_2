<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./css/write-notice/write-notice.css">
    <title>공지사항작성</title>
</head>
<body class="flex-center-row">
   <form action="noticewrite.do" method="post" enctype="multipart/form-data">
    <div class="center_box">
        <h2>졸업작품 평가관리 시스템</h2>
        <div class="tit-area flex-center-row">
            <input type="text" name="noticetitle" class="notice-input-tit" placeholder="제목 입력" value="${param.title}">
        </div>
        <div class="txt-area flex-center-row">
            <textarea name="content" class="notice-input-txt" placeholder="내용 입력"><u:pre value='${param.content}'/></textarea>
        </div>
        <div class="file-area flex-center-row">
            <input name="file" type="file" placeholder="파일 선택">${param.file}
        </div>
        <div class="button-area flex-center-row">
            <button class="regist-button" type="submit">작성하기</button>
            <button class="regist-button" type="reset">재작성하기</button>
            <a href="/Capstone/index.jsp"><button class="regist-button">메인으로</button></a>
        </div>
    </div>
  </form>
</body>
</html>