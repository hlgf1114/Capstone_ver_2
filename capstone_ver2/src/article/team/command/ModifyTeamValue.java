package article.team.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.service.DuplicateIdException;
import mvc.command.CommandHandler;

import java.util.HashMap;
import java.util.Map;


public class ModifyTeamValue implements CommandHandler {
   
   private static final String FORM_VIEW = "/index.jsp";
     
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
	   
      String fileNo;
      
      fileNo = req.getParameter("file_No");
      
      Map<String, Boolean> errors = new HashMap<>();
      req.setAttribute("errors", errors);
      
      try {
         if(fileNo != null){        	
        	session.setAttribute("m_fileNo", fileNo);
            return FORM_VIEW;
         }
         else {
        	 session.setAttribute("m_fileNo", null);
        	 errors.put("m_fileNoNotExist", Boolean.TRUE);
        	 return FORM_VIEW;		// 에러처리 다시해야됌
         }
      } catch (DuplicateIdException e) {
    	 req.setAttribute("error", errors);
         return null;
      }
   }
}
