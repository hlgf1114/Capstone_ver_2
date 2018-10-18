package team.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.service.DuplicateIdException;
import team.model.Team;
import team.service.*;
import mvc.command.CommandHandler;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class SearchTeamHandler implements CommandHandler {
   
   private static final String FORM_VIEW = "/WEB-INF/view/teamSearchForm.jsp";
   private SelectTeamService searchService = new SelectTeamService();
     
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

	  HttpSession session = req.getSession();
      
      String groupNo;
      String teamNo;
      String teamName;
      String advisor;
      String date;
      String error = "존재하지 않습니다.";
      
      teamNo = req.getParameter("teamNo");
      groupNo = req.getParameter("groupNo");
      date = req.getParameter("date");
      Map<String, Boolean> errors = new HashMap<>();
      req.setAttribute("errors", errors);
      
      String subYear = date.substring(2,4);
      String tNo = (subYear + "_" + teamNo + "_" + groupNo);
      
      if (!errors.isEmpty()) {
			return FORM_VIEW;
      }
      
      
     try {
         if(searchService.searchTeam(tNo) == true){
        	 Team team = searchService.selectTeam(tNo);
         	 teamName = team.getTeamName();
             advisor = team.getAdvisor();                        
             advisor = selectPro(advisor);
                        
        	session.setAttribute("tno", tNo);
            session.setAttribute("subdate", date);
        	session.setAttribute("tName", teamName);
            session.setAttribute("advisor", advisor);
            return FORM_VIEW;
         }
         else{
        	 session.setAttribute("advisor", null);
        	 session.setAttribute("tName", null);
        	 errors.put("NotTeam1", Boolean.TRUE);
        	 return FORM_VIEW;		
         }
      } catch (DuplicateIdException e) {
    	 req.setAttribute("error", error);
         return "/index.jsp";
      }
   }
   
   public String selectPro(String advisor)
   {
	   if(advisor.equals("10000"))
       	  advisor = "김점구";
         else if(advisor.equals("10001"))
       	  advisor = "정지문";      
         else if(advisor.equals("10002"))
       	  advisor = "송은지";
         else if(advisor.equals("10003"))
       	  advisor = "나상엽";
         else if(advisor.equals("10004"))
       	  advisor = "김현철";
         else if(advisor.equals("10005"))
       	  advisor = "김정길";
         else if(advisor.equals("10006"))
       	  advisor = "문송철";
         else if(advisor.equals("10007"))
       	  advisor = "황정희";
         else if(advisor.equals("10008"))
       	  advisor = "기창진";
         else
       	  advisor = "알수 없음";
	   
	   return advisor;
   }
}