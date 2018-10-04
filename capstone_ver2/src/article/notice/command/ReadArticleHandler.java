package article.notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import article.common.*;
import article.notice.service.ReadArticleService;
import article.notice.service.NoticeData;

import mvc.command.CommandHandler;

public class ReadArticleHandler implements CommandHandler {

	private ReadArticleService readService = new ReadArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		HttpSession session = req.getSession();
		String noVal = req.getParameter("postNo");
		int articleNum = Integer.parseInt(noVal);
		try {
			NoticeData noticeData = readService.getNotice(articleNum, true);
			session.setAttribute("noticeData", noticeData);
			return "/WEB-INF/view/readNotice.jsp";
		} catch (ArticleNotFoundException | ArticleContentNotFoundException e) {
			req.getServletContext().log("no notice", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}