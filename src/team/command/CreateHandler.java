package team.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import auth.service.*;
import eval.service.EvalPlanList;
import eval.service.EvalProfList;
import eval.service.ShowProf;
import team.service.DuplicateTeamException;
import team.service.*;
import team.service.MakeTeamService;
import mvc.command.CommandHandler;

public class CreateHandler implements CommandHandler {
   
   private static final String FORM_VIEW = "/WEB-INF/view/createTeam_new.jsp";
   private MakeTeamService teamService = new MakeTeamService();
   private EvalPlanList evalplanlist = new EvalPlanList();
   
   @Override
   public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
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
		
		EvalProfList evalproflist = evalplanlist.getEvalProfList();
		
		session.setAttribute("proList", evalproflist);
		
		return FORM_VIEW;
   }
   
   private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
	  MakeTeamRequest mtReq = new MakeTeamRequest();
      StudentUser user = (StudentUser)req.getSession(false).getAttribute("authStdUser");
      EvalProfList plist = (EvalProfList)req.getSession(false).getAttribute("proList");
      
      String proValue = req.getParameter("advisor");
      
      ShowProf pl = selectedProfessor(plist, proValue);
      
      mtReq.SetId(user.getId()); //Student 테이블 TeamNo 값 update 하기 위함.
      
      
      mtReq.setTeamNo(req.getParameter("teamNo"));
      mtReq.setTeamName(req.getParameter("teamName"));
      mtReq.setAdvisor(pl.getProId());
      System.out.println(pl.getProId());
      mtReq.setGroupNo(req.getParameter("groupNo"));
      
      Map<String, Boolean> errors = new HashMap<>();
      req.setAttribute("errors", errors);
      
      mtReq.validate(errors);
      
      if (!errors.isEmpty()) {
         return FORM_VIEW;
      }
      
      try {
         if(teamService.searchTeam(mtReq) == true){
        	mtReq.setS_groupNo(Authority.getStuTeamMaker());
            teamService.MakeTeam(mtReq);         
            return "/WEB-INF/view/createTeamSuccess.jsp";
         }
         else{
            errors.put("duplicateTeam", Boolean.TRUE);
            return FORM_VIEW;
         }
      } catch (DuplicateTeamException e) {
         errors.put("duplicateTeam", Boolean.TRUE);
         return FORM_VIEW;
      }
   }
   private ShowProf selectedProfessor(EvalProfList pl, String val) {
		List<ShowProf> profl=pl.getList();
		ShowProf newlist = new ShowProf();
			for(ShowProf pro : profl) {
				if(val.equals(pro.getProId())) {
					newlist = pro;
					break;
				}
			}
		return newlist;
	}
}