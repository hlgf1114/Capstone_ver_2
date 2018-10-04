package article.notice.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import jdbc.connection.ConnectionProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import member.model.Professor;
import member.dao.ProfessorDao;
import article.common.PermissionDeniedException;
import article.notice.model.Writer;
import article.notice.service.WriteArticleService;
import article.notice.service.WriteRequest;
import auth.service.User;
import mvc.command.CommandHandler;
import auth.service.Authority;
import auth.service.LoginFailException;

public class WriteArticleHandler implements CommandHandler {
   private static final String FORM_VIEW = "/WEB-INF/view/writeNotice.jsp";
   private WriteArticleService writeService = new WriteArticleService();
   private static final Integer defaut_PostId = 0;
   
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
	   User user = (User)req.getSession(false).getAttribute("authProUser");
	   if(user.getAccess()!=Authority.getProDean() && 
			   user.getAccess()!=Authority.getProEval() &&
			   		user.getAccess()!=Authority.getProNotEval()) {
		   throw new PermissionDeniedException();
	   }
	   return FORM_VIEW;
   }
   
   private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
      Map<String, Boolean> errors = new HashMap<>();
      req.setAttribute("errors", errors);

      User user = (User)req.getSession(false).getAttribute("authProUser");
      WriteRequest writeReq = createWriteRequest(user, req);
      writeReq.validate(errors);
      
      if (!errors.isEmpty()) {
         return FORM_VIEW;
      }
      
      int newArticleNo = writeService.write(writeReq);
      req.setAttribute("newArticleNo", newArticleNo);
      
      ListArticleHandler listarticlehandler = new ListArticleHandler();
		
      return listarticlehandler.process(req, res);
     //return "/WEB-INF/view/listNotice.jsp";
   }
   
   private WriteRequest createWriteRequest(User user, HttpServletRequest req) throws Exception {
	   ProfessorDao ProfessorDao = new ProfessorDao();
       Professor professor = null;
       try (Connection conn = ConnectionProvider.getConnection()) {
    	   professor = ProfessorDao.selectById(conn, user.getId());
           if (professor == null) {
        	   throw new LoginFailException();
           }
       } catch (SQLException e) {
       throw new RuntimeException(e);
       }
       MultipartRequest multi = null;
       int sizeLimit = 10 * 1024 * 1024 ; // 10메가입니다.

       String savePath = req.getSession().getServletContext().getRealPath("/upload/notice");    // 파일이 업로드될 실제 tomcat 폴더의 WebContent 기준

       try{
    	   multi=new MultipartRequest(req, savePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy()); 
       }catch (Exception e) {
    	   e.printStackTrace();
       } 
       String file = multi.getOriginalFileName("file");   

       if(file == null) {
    	   return new WriteRequest(defaut_PostId,
    		          new Writer(professor.getProId(), professor.getProname()),
    		          multi.getParameter("noticetitle"),
    		          multi.getParameter("content"));
       }
       
       file = new String(file.getBytes("euc-kr"),"KSC5601");
              
       /*여기서의 이름과 뷰.jsp 파일에서의 이름이 같아야함.*/
       /* 파일 시스템상의 이름을 구하는 방법을 알아보고 코드 다시 수정해야함. */
       return new WriteRequest(defaut_PostId,
          new Writer(professor.getProId(), professor.getProname()),
          multi.getParameter("noticetitle"),
          multi.getParameter("content"),
          multi.getOriginalFileName("file"),
          multi.getFilesystemName("file"),
          multi.getFile("file").length(),
          multi.getContentType("file")
       );
   }
}
