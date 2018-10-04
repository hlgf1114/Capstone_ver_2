package article.notice.command;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import article.notice.service.DeleteArticleService;
import article.notice.service.ReadArticleService;
import article.notice.service.NoticeData;
import article.common.ArticleContentNotFoundException;
import article.common.ArticleNotFoundException;

import auth.service.User;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {
	
	private DeleteArticleService deleteService = new DeleteArticleService();
	private ReadArticleService readService = new ReadArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		String noVal = req.getParameter("no");
		int postNo = Integer.parseInt(noVal);
		try {
			
			NoticeData noticeData = readService.getNotice(postNo, false);
			User authUser = (User) req.getSession().getAttribute("authProUser");
			if (!canDelete(authUser, noticeData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			
			deleteService.deleteArticle(postNo);
			//TeamWriteData articleData = deleteService.deleteArticle(fileNo);
			//req.setAttribute("articleData", articleData);
			RequestDispatcher dispatcher = req.getRequestDispatcher("noticelist.do");
      	  	dispatcher.forward(req,res);
      	  	return null;
		} catch (ArticleNotFoundException | ArticleContentNotFoundException e) {
			req.getServletContext().log("no article", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	private boolean canDelete(User authUser, NoticeData articleData) {
		String writerId = articleData.getNotice().getWriter().getId();
		//정수 -> 문자열 변환함
		String temp = authUser.getId();
		return temp.equals(writerId);
	}
}