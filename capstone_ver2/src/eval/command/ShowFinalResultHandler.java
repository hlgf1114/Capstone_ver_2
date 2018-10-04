package eval.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.Member;
import eval.model.EvalFinal;
import eval.service.EpaperResult;
import eval.service.EvalPaperList;
import eval.service.EvalpaperListService;
import eval.service.EvaluateFinalService;
import eval.service.EvaluateTeamService;
import eval.service.ShowFinalResultService;
import eval.service.ShowResultListService;
import eval.service.ShowTeam;
import eval.service.ShowTeamMember;
import mvc.command.CommandHandler;

public class ShowFinalResultHandler implements CommandHandler {
	   
		ShowResultListService showResultListService = new ShowResultListService();
		EvalpaperListService evalpaperListService = new EvalpaperListService();
	
      private static final String FINAL_EVAL_LIST_VIEW = "/WEB-INF/view/FinalList.jsp";
      private static final String FINAL_RESULT_VIEW = "/WEB-INF/view/FinalResult.jsp";
      private ShowFinalResultService showFinalResultService = new ShowFinalResultService();
      private EvaluateTeamService evaluateTeamService = new EvaluateTeamService();
      private EvaluateFinalService evaluateFinalService = new EvaluateFinalService();
      
      public String process(HttpServletRequest req, HttpServletResponse res) throws Exception{
    	  Member team = (Member)req.getSession(false).getAttribute("authTeam");
    	  if(team!=null) {
    		  return process_stu(req,res);
    	  }
    	  
    	  HttpSession session = req.getSession();      
         String result1 = req.getParameter("com1");
         String teamNo = req.getParameter("team_no");
        
 		EvalPaperList el = evalpaperListService.getEvalPaperList(teamNo);
 		List<EpaperResult> eprl = showResultListService.MakeResultList(el.getList());
 		
         ShowTeam eteam = evaluateFinalService.selectEteam(teamNo);
         List<ShowTeamMember> sl = evaluateTeamService.SelectTeamMembers(teamNo);
         session.setAttribute("memberList", sl);
         /* 평가가 끝났는지 안끝났는지 점검할 필요 있음 */
         EvalFinal ef = showFinalResultService.selectEvalFinal(teamNo);
         
         /* 결과 확인 버튼 눌렀을 때 리스트로 돌아가게 하는 조건 */
         if((result1!=null)&&(result1.equals("confirmed"))) {
        	 session.setAttribute("final_comfirmed", "no");
            return FINAL_EVAL_LIST_VIEW;
         }
         
         if(ef==null) {
            req.setAttribute("final_noselected", "yes");
            return FINAL_EVAL_LIST_VIEW;
         } else {
            req.setAttribute("final_noselected", "no");
         }
         
         if(!showFinalResultService.IsCompleted(teamNo)) {
            req.setAttribute("final_finished", "no");
            return FINAL_EVAL_LIST_VIEW;
         }else {
            req.setAttribute("final_finished", "yes");
         }
         
         session.setAttribute("EvalResultList", eprl);
         session.setAttribute("evalfinal", ef);
         String result = req.getParameter("finalbtn");
         if((result!=null) && (result.equals("result"))) {
        	session.setAttribute("teamName", eteam.getTeamName());
            return FINAL_RESULT_VIEW;
         }
         return FINAL_EVAL_LIST_VIEW;
	}
      
      public String process_stu(HttpServletRequest req, HttpServletResponse res) throws Exception{
          HttpSession session = req.getSession();      
         
          Member team = (Member)req.getSession(false).getAttribute("authTeam");
          String teamNo = team.getTeamNo();
          
	  		EvalPaperList el = evalpaperListService.getEvalPaperList(teamNo);
	  		List<EpaperResult> eprl = showResultListService.MakeResultList(el.getList());
  		
          ShowTeam eteam = evaluateFinalService.selectEteam(teamNo);
          List<ShowTeamMember> sl = evaluateTeamService.SelectTeamMembers(teamNo);
          session.setAttribute("memberList", sl);
          /* 평가가 끝났는지 안끝났는지 점검할 필요 있음 */
          EvalFinal ef = showFinalResultService.selectEvalFinal(teamNo);
          
          if(ef==null) {
        	  System.out.println("이거 ? ?");
             req.setAttribute("final_noselected", "yes");
             return FINAL_EVAL_LIST_VIEW;
          } else {
             req.setAttribute("final_noselected", "no");
          }
          
          if(!showFinalResultService.IsCompleted(teamNo)) {
        	  System.out.println("이거 ?112 ?");
        	  req.setAttribute("final_finished", "no");
             return FINAL_EVAL_LIST_VIEW;
          }else {
             req.setAttribute("final_finished", "yes");
          }
          
          session.setAttribute("EvalResultList", eprl);
          session.setAttribute("evalfinal", ef);
          String result = req.getParameter("finalbtn");
          if((result!=null) && (result.equals("result"))) {
         	session.setAttribute("teamName", eteam.getTeamName());
             return FINAL_RESULT_VIEW;
          }
          
          return FINAL_EVAL_LIST_VIEW;
 	}
}
