package eval.command;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.LoginFailException;
import auth.service.User;
import eval.model.Evalpaper;
import eval.model.Questions;
import eval.service.EvalTeamList;
import eval.service.EvaluateTeamRequest;
import eval.service.EvaluateTeamService;
import eval.service.ShowTeamMember;
import member.dao.StudentDao;
import mvc.command.CommandHandler;


public class EvaluateTeamHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/evaluateForm.jsp";
	private static final String EVAL_VIEW = "/WEB-INF/view/EvalTeamList.jsp";
	private static final String RESULT_VIEW = "/WEB-INF/view/EvalResult.jsp";
	private EvaluateTeamService evaluateTeamService = new EvaluateTeamService();
	
	
	final public static int DEFAULT_LIST_NO = 7;	
	
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		User user = (User)req.getSession(false).getAttribute("authProUser");
		/* 평가할 수 있는 교수인지 아닌지 체크해야함 */
	
		if(!evaluateTeamService.IsPossibleToEval(user.getId())){
			req.setAttribute("noeval", "yes");
			return EVAL_VIEW;
		}else {
			req.setAttribute("noeval", "no");
		}
	
		
		/* 평가가 끝났는지 안끝났는지 점검할 필요 있음 */
		String ep = (String)req.getSession(false).getAttribute("epaperNo");
		
		if(ep==null) {
			req.setAttribute("noselected", "yes");
			return EVAL_VIEW;
		}
		else {
			req.setAttribute("noselected", "no");
		}
		
		if(evaluateTeamService.IsEvalCompleted(ep)) {
			req.setAttribute("completed", "yes");
			return EVAL_VIEW;
		}else {
			req.setAttribute("completed", "no");
		}
		
		String eval = req.getParameter("eval");
		
		if(eval!=null && eval.equals("true")) {
			return processForm(req,res);
		}else {
			return processSubmit(req, res);
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session = req.getSession();		
		
		String team_No = req.getParameter("team_no");
		String ep = (String)req.getSession(false).getAttribute("epaperNo");
		
		Evalpaper evalpaper = evaluateTeamService.SelectEvalpaper(ep);
		List<ShowTeamMember> sl = evaluateTeamService.SelectTeamMembers(team_No);
		
		if(evalpaper == null) {
			return EVAL_VIEW;
		}
		Questions qs = evalpaper.getQs();
		
		SetItems(req,res,qs);
		
		session.setAttribute("memberList", sl);
	
		return FORM_VIEW;
	}
	/* 평가 세션을 만들어야할 듯 한데 */
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		
		Questions qs = GetItems(req,res);
		
		String select = req.getParameter("select");
		
		String epaperNo = (String)req.getSession(false).getAttribute("epaperNo");

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		Date now = new Date();
		EvaluateTeamRequest evalReq = new EvaluateTeamRequest(
				epaperNo,qs,now,now);
		
		/* 평가 종료를 선택했을때만 빈칸 체크를 해야함 */
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
	
		evaluateTeamService.EvaluateTeam(evalReq);
		if(select.equals("complete")) {
			evaluateTeamService.CompleteEval(epaperNo);
		}
		
		return EVAL_VIEW;
		
//		try {
//				evaluateTeamService.EvaluateTeam(evalReq);
//				return "/index.jsp";	
//		} catch (DuplicateIdException e) {		//이미 평가 했는지 에러 처리 필요
//			errors.put("duplicateId", Boolean.TRUE);
//			return FORM_VIEW;
//		}
	}

	//필요있네
	private int parseint(String str) {
		String temp = str;
		try{
			return Integer.parseInt(temp);
		} catch(NumberFormatException nfe){
			System.err.println(nfe);
			//점수 미선택 오류 지정
			throw new LoginFailException(); 
		}
	}
	
	private void SetItems(HttpServletRequest req, HttpServletResponse res, Questions qs) {
		String comment = "c";
		String point = "v";
		
		String setvalue1 = null;
		String setvalue2 = null;
		
		for(int i=0;i<7;i++) {
			setvalue1 = comment + Integer.toString(i+1);
			setvalue2 = point + Integer.toString(i+1);
			req.setAttribute(setvalue1, qs.getQsItemComment(i));
			req.setAttribute(setvalue2, qs.getQsItemScore(i));
		}
	}
	
	private Questions GetItems(HttpServletRequest req, HttpServletResponse res) {
		String comment = "comment";
		String point = "val_";
		Questions qs = new Questions();
		String getvalue1 = null;
		String getvalue2 = null;
		
		int value = 0;
		
		for(int i=0;i<7;i++) {
			getvalue1 = comment + Integer.toString(i+1);
			getvalue2 = point + Integer.toString(i+1);
			
			if(req.getParameter(getvalue2) == null) {
				qs.setQsItemScore(i, value);
			}else {
				qs.setQsItemScore(i, Integer.parseInt(req.getParameter(getvalue2)));
			}
			qs.setQsItemComment(i, req.getParameter(getvalue1));
		}
		return qs;
	}
}

