package article.team.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import article.common.ArticleNotFoundException;
import article.common.PermissionDeniedException;
import article.team.model.TeamArticleWriter;
import article.team.service.ModifyArticleService;
import article.team.service.ModifyRequest;
import article.team.service.TeamWriteData;
import article.team.service.ReadArticleService;
import auth.service.Member;
import auth.service.StudentUser;
import auth.service.User;
import mvc.command.CommandHandler;

public class ModifyArticleHandler implements CommandHandler {
	private static final String FORM_VIEW = "/index.jsp";	//작성과 같은 뷰이면 될듯(팀게시판 메인 페이지)
	
	private ModifyArticleService modifyService = new ModifyArticleService();
	private ReadArticleService readService = new ReadArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		try {
			String fileNo = req.getParameter("select_file");
			System.out.print(fileNo);
			
			TeamWriteData articleData = readService.getArticle(fileNo, false);
			StudentUser authUser = (StudentUser) req.getSession().getAttribute("authStdUser");
			if (!canModify(authUser, articleData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			
			ModifyRequest modReq = new ModifyRequest(fileNo, articleData.getArticle().getTitle(), articleData.getOrigin(), articleData.getStored(),
					articleData.getArticle().getWriter(), articleData.getFileSize(), articleData.getFileType());

			req.setAttribute("modReq", modReq);
			return FORM_VIEW;
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	private boolean canModify(StudentUser authUser, TeamWriteData articleData) {
		String writerId = articleData.getArticle().getWriter().getWriterId();
		//정수 -> 문자열 변환함
		String temp = authUser.getId();
		return temp.equals(writerId);
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		StudentUser authUser = (StudentUser)req.getSession().getAttribute("authStdUser");
		Member authTeam = (Member)req.getSession().getAttribute("authTeam");
		
		String teamNo = authUser.getTeamNo();
		
		MultipartRequest multi = null;
		int sizeLimit = 10 * 1024 * 1024 ; // 10메가입니다.

		String savePath = req.getSession().getServletContext().getRealPath("/upload/") + teamNo;    // 파일이 업로드될 실제 tomcat 폴더의 WebContent 기준

		try{
		multi=new MultipartRequest(req, savePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy()); 
		}catch (Exception e) {
			e.printStackTrace();
		} 
		String origin_file = multi.getOriginalFileName("file");
		String store_file = multi.getFilesystemName("file");
		origin_file = new String(origin_file.getBytes("euc-kr"),"KSC5601");
		store_file = new String(store_file.getBytes("euc-kr"),"KSC5601");
		String fileNo = multi.getParameter("fileNo");
		ModifyRequest modReq = new ModifyRequest(fileNo,
				multi.getParameter("title"),
				origin_file,
				store_file,
				new TeamArticleWriter(teamNo, authUser.getId()),
				multi.getFile("file").length(),
				multi.getContentType("file"));
		
		
		req.setAttribute("modReq", modReq);

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		modReq.validate(errors);
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		try {
			modifyService.modify(modReq);
			return "/index.jsp";
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}
}
