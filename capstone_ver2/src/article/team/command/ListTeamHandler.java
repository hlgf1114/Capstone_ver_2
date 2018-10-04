package article.team.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import article.team.service.ListTeamService;
import eval.service.EvalTeamList;
import member.service.DuplicateIdException;
import team.dao.TeamDao;
import team.model.Team;
import mvc.command.CommandHandler;

import java.util.HashMap;
import java.util.Map;


public class ListTeamHandler implements CommandHandler {
   
   private static final String FORM_VIEW = "/index.jsp";
   private ListTeamService searchService = new ListTeamService();
   private TeamDao teamDao = new TeamDao();
   
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
	   /* 팀 목록 전체 뷰로 넘기기 위해서 만듬 */
	   HttpSession session = req.getSession();
	   /* db에서 모든 팀이름과 번호를 읽어와 리스트에 저장하고 그 리스트와 전체 팀수를 가지고 있는 EvalTeamList 클래스 변수에 넣기 */
	   EvalTeamList evalteamlist = searchService.selectAllTeam();
	   /* 뷰로 넘기기 */
	   session.setAttribute("teamList", evalteamlist);
      return FORM_VIEW;
   }

   private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {	  

	  HttpSession session = req.getSession();
	   
      String groupNo;
      String teamNo;
      String teamName;
      String date;
      String filetype;
      
      teamNo = req.getParameter("teamNo");
      groupNo = req.getParameter("groupNo");
      date = req.getParameter("date");
      filetype = req.getParameter("filetype");
      String subYear = date.substring(2,4);
      String tNo = (subYear + "_" + teamNo + "_" + groupNo);
      
      Map<String, Boolean> errors = new HashMap<>();
      req.setAttribute("errors", errors);
      
      try {
         if(searchService.searchTeam(tNo) == true){        	
        	Team team = searchService.selectTeam(tNo);        	
        	teamName = team.getTeamName();
        	session.setAttribute("main_tName", teamName);
        	session.setAttribute("listTno", tNo);
        	session.setAttribute("fileType", filetype);

            return FORM_VIEW;
         }
         else {
        	 session.setAttribute("main_tName", null);
        	 errors.put("listTeamNotExist", Boolean.TRUE);
        	 return FORM_VIEW;		// 에러처리 다시해야됌
         }
      } catch (DuplicateIdException e) {
    	 req.setAttribute("error", errors);
         return null;
      }
   }
}
