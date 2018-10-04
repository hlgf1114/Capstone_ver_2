package article.notice.command;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import mvc.command.CommandHandler;

public class DownloadArticleHandler implements CommandHandler {
   
   private static final String FORM_VIEW = "/WEB-INF/view/readNotice.jsp";

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
	  
	  String filename = req.getParameter("filename");
	  String Docname = URLEncoder.encode(filename,"UTF-8");
	  String path = req.getSession().getServletContext().getRealPath("/upload/notice/"); 
	  System.out.println("경로 = " + path + " file 이름 = " + filename);
	  
	  File file = new File(path, filename);
      Map<String, Boolean> errors = new HashMap<>();
      req.setAttribute("errors", errors);

     try {
         if(file.exists()){
        	 DownloadUtil.download(req, res, file);
        	 return FORM_VIEW;      
         }else{
        	errors.put("NotExistNoticeFile", Boolean.TRUE);
        	return FORM_VIEW;
         }
      } catch (DuplicateIdException e) {
         errors.put("duplicateId", Boolean.TRUE);
         return FORM_VIEW;
      }
   }

}