package team.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.*;
import member.service.DuplicateIdException;
import team.service.*;
import team.service.JoinTeamService;
import mvc.command.CommandHandler;

public class JoinTeamHandler implements CommandHandler {
   
   private static final String FORM_VIEW = "/WEB-INF/view/teamSearchForm.jsp";
   private JoinTeamService jointeamService = new JoinTeamService();

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
      return FORM_VIEW;
   }

   private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
	  MakeTeamRequest mtReq = new MakeTeamRequest();
      StudentUser user = (StudentUser)req.getSession(false).getAttribute("authStdUser");
      String teamNo = req.getParameter("team_no");
      String stuId = user.getId();
      
      
      Map<String, Boolean> errors = new HashMap<>();
      req.setAttribute("errors", errors);
      
      mtReq.validate(errors);
      /*
      if (!errors.isEmpty()) {
         return "/index.jsp";
      }
      */
     try {
         if(jointeamService.searchTeam(teamNo) == true){
            jointeamService.JoinTeam(teamNo, stuId, Authority.getStuTeam());         
            return "/WEB-INF/view/createTeamSuccess.jsp";
         }
         else{
            errors.put("NotExistTeams", Boolean.TRUE);
            return FORM_VIEW;
         }
      } catch (DuplicateIdException e) {
         errors.put("duplicateId", Boolean.TRUE);
         return FORM_VIEW;
      }
   }
}