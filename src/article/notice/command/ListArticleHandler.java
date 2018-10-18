package article.notice.command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import article.notice.service.ArticlePage;
import article.notice.service.ListArticleService;
import auth.service.Authority;
import auth.service.StudentUser;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler {

	 private static final String FORM_VIEW = "/index.jsp";
	
   private ListArticleService listService = new ListArticleService();
   
   @Override
   public String process(HttpServletRequest req, HttpServletResponse res) 
         throws Exception {
      String pageNoVal = req.getParameter("pageNo");
      StudentUser stduser = (StudentUser)req.getSession(false).getAttribute("authStdUser");
      
      HttpSession session = req.getSession();
      
      int pageNo = 1;
      if (pageNoVal != null) {
         pageNo = Integer.parseInt(pageNoVal);
      }
      ArticlePage articlePage = listService.getArticlePage(pageNo);
      
      session.setAttribute("articlePage", articlePage);
      if(stduser != null) {
    	  if(stduser.getTeamNo() != null) {
      	  	RequestDispatcher dispatcher = req.getRequestDispatcher("authTeam.do");
      	  	dispatcher.forward(req,res);
      	  	return null;
        }
      }
      return FORM_VIEW;
   }
}
