package article.team.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.common.ArticleContentNotFoundException;
import article.common.ArticleNotFoundException;
import article.team.service.DeleteArticleService;
import article.team.service.ReadArticleService;
import article.team.service.TeamWriteData;
import auth.service.StudentUser;
import auth.service.User;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {
	
	private DeleteArticleService deleteService = new DeleteArticleService();
	private ReadArticleService readService = new ReadArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		String fileNo = req.getParameter("fileNo");
		Map<String, Boolean> errors = new HashMap<>();
		
	    req.setAttribute("errors", errors);
	    
		try {
			String allowed = "no";
			TeamWriteData articleData = readService.getArticle(fileNo, false);
			StudentUser authUser = (StudentUser) req.getSession().getAttribute("authStdUser");
			if (!canDelete(authUser.getId(), articleData)) {
				req.setAttribute("allowed", allowed);
				return "/index.jsp";
			}
			
			deleteService.deleteArticle(fileNo);
			//TeamWriteData articleData = deleteService.deleteArticle(fileNo);
			//req.setAttribute("articleData", articleData);
			return "/index.jsp";
		} catch (ArticleNotFoundException | ArticleContentNotFoundException e) {
			req.getServletContext().log("no article", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	private boolean canDelete(String id, TeamWriteData articleData) {
		String writerId = articleData.getArticle().getWriter().getWriterId();
		String temp = id;
		return temp.equals(writerId);
	}
}
