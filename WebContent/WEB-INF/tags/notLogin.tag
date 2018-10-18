<%@ tag body-content="scriptless" pageEncoding="utf-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%
	HttpSession httpSession = request.getSession(false);
	if ((httpSession == null || httpSession.getAttribute("authStdUser") == null)
			&& (httpSession == null || httpSession.getAttribute("authProUser") == null)){
%>
<jsp:doBody />
<%
	}
%>