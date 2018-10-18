package eval.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import eval.service.EvalpaperListService;
import eval.service.ShowResultListService;
import mvc.command.CommandHandler;

public class ShowProResultHandler implements CommandHandler {
	
	ShowResultListService showResultListService = new ShowResultListService();
	EvalpaperListService evalpaperListService = new EvalpaperListService();
	
	private static final String EVAL_TEAM_VIEW = "/WEB-INF/view/EvalTeamList.jsp";
	private static final String STU_MAIN_VIEW = "/index.jsp";
	private static final String EVAL_VIEW = "/WEB-INF/view/FinalEval.jsp";
	private static final String PRO_RESULT_VIEW = "/WEB-INF/view/ShowFinalResult.jsp";
	
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception{
		//학과장님 판별할 필요 있음.
		User user = (User)req.getSession(false).getAttribute("authProUser");
		
		/* 학과장님이 아니면 안되게 해야함. */
//		애초에 학과장님 아니면 누를 수가 없음.
//		if(user.getAccess()==Authority.getProDean()) {
//		
//		}
		/**평가가 시작하지 않은 것을 거를 필요가 있음
		 * 
		 */
		
		
		/* 평가가 이미 종료되지 않았는지 점검할 필요가 있음 */
		String entirebtn = req.getParameter("entire");
		if((entirebtn!=null)&&entirebtn.equals("result")) {
			return PRO_RESULT_VIEW;
		}
		return null;
	}
}
