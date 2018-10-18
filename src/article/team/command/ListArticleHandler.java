package article.team.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import article.team.model.TeamContent;
import article.team.service.ArticlePage;
import article.team.service.ListArticleService;
import auth.service.StudentUser;
import auth.service.User;
import eval.dao.EvalplanDao;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler {

	private ListArticleService listService = new ListArticleService();
	private EvalplanDao evalplanDao = new EvalplanDao();			//현재 평가가 진행중인지 아닌지를 읽어오기 위한 멤버
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {		
		/* 평가에 참여하는 선택된 교수 읽어오기 */	
		User user = (User)req.getSession(false).getAttribute("authProUser");
		
		String filetype = req.getParameter("filetype");
		String pageNoVal = req.getParameter("pageNo");	
		
		String state = req.getParameter("eval");
		
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		if(filetype == null) {
			filetype = "00";
		}
		
		ArticlePage articlePage = null;
		StudentUser team = (StudentUser)req.getSession(false).getAttribute("authStdUser");
		
		if(team == null) {
			String teamNo = req.getParameter("team_no");
			articlePage = listService.getArticlePage(pageNo, teamNo, filetype);
		} else {
			articlePage = listService.getArticlePage(pageNo, team.getTeamNo(), filetype);
		}
		
		req.setAttribute("articleTeamPage", articlePage);
		/* 평가가 진행중이고 교수님이 조회를 할 경우 */
		if(state!=null && state.equals("false")) {
			return "/index.jsp";	
		}
		if(state == null)
			return "/index.jsp";
		if(state != null & state.equals("true"))
			return "/WEB-INF/view/EvalTeamList.jsp";
		
		return "/WEB-INF/view/EvalTeamList.jsp";
	}
	
	public String getFileType(String filetype)
	{
		if(filetype.equals("a"))
			filetype = "회의록";
		else if(filetype.equals("b"))
			filetype = "제안서";
		else if(filetype.equals("c"))
			filetype = "요구분석서";
		else if(filetype.equals("d"))
			filetype = "설계서";
		else if(filetype.equals("e"))
			filetype = "구현서";
		else if(filetype.equals("f"))
			filetype = "형상관리서";
		else if(filetype.equals("g"))
			filetype = "메뉴얼";
		else if(filetype.equals("h"))
			filetype = "최종보고서";
		else
			filetype = "알수없음";
		
		return filetype;
	}
}
