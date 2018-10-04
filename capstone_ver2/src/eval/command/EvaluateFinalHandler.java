package eval.command;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.User;
import eval.dao.EvalTeamDao;
import eval.model.EvalFinal;
import eval.service.AllEvalStatusValue;
import eval.service.EpaperResult;
import eval.service.EvalPaperList;
import eval.service.EvalpaperListService;
import eval.service.EvaluateFinalService;
import eval.service.EvaluateTeamService;
import eval.service.ShowResultListService;
import eval.service.ShowTeam;
import eval.service.ShowTeamMember;
import mvc.command.CommandHandler;

public class EvaluateFinalHandler implements CommandHandler {
	
	private static final String EVAL_VIEW = "/WEB-INF/view/FinalList.jsp";
	private static final String FORM_VIEW = "/WEB-INF/view/FinalForm.jsp";
	EvaluateFinalService evaluateFinalService = new EvaluateFinalService();
	EvaluateTeamService evaluateTeamService = new EvaluateTeamService();
	ShowResultListService showResultListService = new ShowResultListService();
	EvalpaperListService evalpaperListService = new EvalpaperListService();
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		User user = (User)req.getSession(false).getAttribute("authProUser");
		
		String teamNo = req.getParameter("team_no");
		String finalNo = teamNo + "_"+AllEvalStatusValue.getDefaultFinalDocu();
		
		/* 최종 평가를 진행할 팀이 선택되지 않았을 경우 */
		if((teamNo==null)||(teamNo.equals(""))) {
			req.setAttribute("finalteam", "yes");
			return EVAL_VIEW;
		}
		else {
			req.setAttribute("finalteam", "no");
		}
		
		if(evaluateFinalService.IsFinalCompleted(finalNo)) {
			req.setAttribute("completed1", "yes");
			return EVAL_VIEW;
		}else {
			req.setAttribute("completed1", "no");
		}
		
		String finalbtn = req.getParameter("finalbtn");
		if(finalbtn!=null && finalbtn.equals("show")) {
			return processForm(req,res);
		}else{
			return processSubmit(req, res);
		}
	}
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();		
		
		String team_No = req.getParameter("team_no");	
		String finalNo = team_No + "_"+AllEvalStatusValue.getDefaultFinalDocu();
		
		EvalPaperList el = evalpaperListService.getEvalPaperList(team_No);
 		List<EpaperResult> eprl = showResultListService.MakeResultList(el.getList());
		
		EvalFinal evalfinal = evaluateFinalService.SelectEvalFinal(finalNo);
		List<ShowTeamMember> sl = evaluateTeamService.SelectTeamMembers(team_No);
		ShowTeam eteam = evaluateFinalService.selectEteam(team_No);
		if(evalfinal == null) {
			return EVAL_VIEW;
		}
		
		session.setAttribute("EvalResultList", eprl);
		session.setAttribute("team_no", team_No);
		session.setAttribute("average", evaluateFinalService.CntAverage(team_No));
		session.setAttribute("teamName", eteam.getTeamName());
		session.setAttribute("efinal", evalfinal);
		session.setAttribute("memberList", sl);
	
		return FORM_VIEW;
	}
	/* 평가 세션을 만들어야할 듯 한데 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		
		String select2 = req.getParameter("final_select");
		String teamNo = req.getParameter("team_no");
		String finalNo = teamNo+"_"+AllEvalStatusValue.getDefaultFinalDocu();
		int state = AllEvalStatusValue.getDefaultEfinalState();
		int resultstate = AllEvalStatusValue.getDefaultResult();
		if(select2.equals("complete")) {
			if ((req.getParameter("rs_val")==null)) {
				req.setAttribute("final_finished", "yes");
				return FORM_VIEW;
			}
			state=AllEvalStatusValue.getEfinalEvalEnded();
		}else {
			state=AllEvalStatusValue.getEfinalEvalStarted();
		}
		
		Date now = new Date();
		
		if ((req.getParameter("rs_val")!=null)) {
			resultstate = Integer.parseInt(req.getParameter("rs_val"));
		}
		
		EvalFinal evalfinal = new EvalFinal(
				finalNo,req.getParameter("comment1"),now,now, evaluateFinalService.CntAverage(teamNo),
				state,resultstate);
	
		evaluateFinalService.EvaluateFinal(evalfinal);
		
		return EVAL_VIEW;
		
	}
}
