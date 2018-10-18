package article.team.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Util;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import article.team.model.TeamArticleWriter;
import article.team.service.WriteArticleService;
import article.team.service.WriteRequest;
import auth.service.LoginFailException;
import auth.service.StudentUser;
import auth.service.User;
import jdbc.connection.ConnectionProvider;
import member.model.Student;
import member.dao.StudentDao;
import mvc.command.CommandHandler;
import team.dao.TeamDao;
import team.model.Team;

public class WriteArticleHandler implements CommandHandler {
	private static final String FORM_VIEW = "/index.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	
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
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		StudentUser user = (StudentUser)req.getSession(false).getAttribute("authStdUser");
		
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		WriteRequest writeReq = createWriteRequest(user, req);
		if(writeReq == null)
		{
			errors.put("NoFileType", Boolean.TRUE);
			return FORM_VIEW;
		}
		else {
			String newFileNo = writeService.write(writeReq);
			req.setAttribute("newArticleNo", newFileNo);
		}
		
		return FORM_VIEW;
	}

	private WriteRequest createWriteRequest(StudentUser user, HttpServletRequest req) throws Exception {
		StudentDao studentDao = new StudentDao();
		Student student;
		
		DecimalFormat df = new DecimalFormat("00"); // 연도 구하기위한 포맷 형식 지정
		Calendar currentCalendar = Calendar.getInstance();
		String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
		String strMin = Integer.toString(currentCalendar.get(Calendar.MINUTE));
		String strSecd = Integer.toString(currentCalendar.get(Calendar.SECOND));
		String strDate = strYear + strMin + strSecd;
		
		strDate = strDate.substring(2,7);
		
		try (Connection conn = ConnectionProvider.getConnection()) {
			student = studentDao.selectById(conn, user.getId());
			
			if (student == null) {
				throw new LoginFailException();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		MultipartRequest multi = null;
		int sizeLimit = 10 * 1024 * 1024 ; // 10메가입니다.

		String savePath = req.getSession().getServletContext().getRealPath("/upload");    // 파일이 업로드될 실제 tomcat 폴더의 WebContent 기준
		
		savePath = makeDirectory(savePath, student.getTeamNo());
		
		try{
			multi=new MultipartRequest(req, savePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy()); 	//utf-8로 해야됨
		}catch (Exception e) {	
			return null;
		}
		
		String fileTitle = multi.getParameter("title");
		String fileType = multi.getParameter("filetype");
		String file = multi.getOriginalFileName("file");
		
		if(file == null || fileTitle.equals(null)|| fileType.equals("")) {
			return null;
		}
		
		
		
		
		String origin_file = multi.getOriginalFileName("file");
		String store_file = multi.getFilesystemName("file");
		origin_file = new String(origin_file.getBytes("euc-kr"),"KSC5601");	//인코딩 문제
		store_file = new String(store_file.getBytes("euc-kr"),"KSC5601");	//인코딩 문제
//		store_file = new String(store_file.getBytes("euc-kr"),"KSC5601");
//		System.out.println(new String(origin_file.getBytes("KSC5601"), "utf-8"));
//		System.out.println(new String(origin_file.getBytes("utf-8"), "KSC5601"));
//		System.out.println(new String(origin_file.getBytes("KSC5601"), "euc-kr"));
//		System.out.println(new String(origin_file.getBytes("euc-kr"), "KSC5601"));
//		System.out.println(new String(origin_file.getBytes("8859_1"), "KSC5601"));
//		System.out.println(new String(origin_file.getBytes("8859_1"), "euc-kr"));
//		System.out.println(new String(origin_file.getBytes("8859_1"), "utf-8"));
//		System.out.println(new String(origin_file.getBytes("utf-8"), "8859_1"));

//		store_file = new String(store_file.getBytes("euc-kr"),"KSC5601");
		/*여기서의 이름과 뷰.jsp 파일에서의 이름이 같아야함.*/
//		/* 파일 시스템상의 이름을 구하는 방법을 알아보고 코드 다시 수정해야함. */
		int i = -1;
		
		i = store_file.lastIndexOf(".");
		String realName = strDate + store_file.substring(i, store_file.length());
		
		File oldFile = new File(savePath + "/" + store_file);
		File newFile = new File(savePath + "/" + realName);
		
		oldFile.renameTo(newFile);
		
		if(oldFile.renameTo(newFile)){

		      System.out.print("이름변경성공");

		    }else{

		       System.out.print("이름변경실패");

		   }
		
		return new WriteRequest(null,
				multi.getParameter("title"),
				origin_file,
				realName,
				new TeamArticleWriter(user.getTeamNo(), user.getId()),	//분,초 + 문서번호
				multi.getFile("file").length(),
				multi.getContentType("file"),
				multi.getParameter("filetype"));
	}
	/* 팀폴더 생성 함수 */
	public String makeDirectory(String path, String teamNo) {
		String savePath = path + "/" + teamNo;
		File file = new File(path);
        //upload 폴더가 있는지 점검하는 것 -> 필요한지는 모르겠음
		/*
		if(!file.mkdirs()) {
        	return savePath;
        }*/
        file = new File(savePath);
        if(!file.mkdirs()){
        	return savePath;
        }
		return savePath;
	}
}
