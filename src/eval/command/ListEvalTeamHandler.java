package eval.command;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import article.team.command.ListArticleHandler;
import auth.service.Authority;
import auth.service.User;
import eval.dao.EvalplanDao;
import eval.service.EvalPlanList;
import eval.service.EvalTeamList;
import eval.service.MakeEvalplanService;
import eval.service.ShowTeam;
import mvc.command.CommandHandler;

public class ListEvalTeamHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/EvalTeamList.jsp";	//수정과 같은 뷰면 될듯
	private MakeEvalplanService makeService = new MakeEvalplanService();
	private EvalPlanList evalplanlist = new EvalPlanList();
	private ListArticleHandler listTeamArticlehandler = new ListArticleHandler();
	private EvalplanDao evalplanDao = new EvalplanDao();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		User user = (User)req.getSession(false).getAttribute("authProUser");
		/* 학과장님일 때, 뷰에 평가계획서, 최종평가 버튼을 나타나게하는 부분 */
		if(user.getAccess()==Authority.getProDean()) {
			req.setAttribute("dean", "yes");
		}else {
			req.setAttribute("dean", "no");
		}
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		/*이거 여기서 필요한건지 잘 모르겠음.*/
//		/* 아무 팀도 안선택한 세션값 설정 */
//		req.setAttribute("noselected", "No");
//		
//		/* 평가가 시작되지 않음 -> 초기화면 */
//		String state = evalplanDao.getEvalState();
//		if(state == null) {
//			return "/index";
//		}
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();
		
		EvalTeamList evalteamlist = evalplanlist.getEvalTeamList();
		
		session.setAttribute("teamList", evalteamlist);
		
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		/* 평가에 참여하는 선택된 교수 읽어오기 */
		String teamNo = req.getParameter("teamNo");	
		String teamName = req.getParameter("teamtitle");	
		User user = (User)req.getSession(false).getAttribute("authProUser");
		EvalTeamList tl = (EvalTeamList)req.getSession(false).getAttribute("teamList");
		
		/* 팀선택하면 해당 팀의 최종 평가지 문서 번호와 개별 교수 평가지 문서번호 생성 */
		String finalNo = teamNo+"_ff";				//이 값은 학과장만 사용하는 것?
		String epaperNo = teamNo+"_"+user.getId();	//이 값은 프론트로 넘겨줘야함.
		
		List<ShowTeam> sl = tl.getList();
		
		session.setAttribute("epaperNo", epaperNo);	//개별 평가지 번호 프론트로 넘기기
		session.setAttribute("team_No", teamNo);
		session.setAttribute("team_name", teamName);
	
		return FORM_VIEW;
	}
}
