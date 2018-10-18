package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.ChangePasswordService;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import mvc.command.CommandHandler;
import auth.service.Authority;
import auth.service.StudentUser;

public class ChangePasswordHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/changePwdForm.jsp";
	private ChangePasswordService changePwdSvc = new ChangePasswordService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}


	private String processSubmit(HttpServletRequest req, HttpServletResponse res)
	throws Exception {
		User user = (User)req.getSession().getAttribute("authUser");
		StudentUser stduser = (StudentUser)req.getSession().getAttribute("authStdUser");
			
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		String curPwd = req.getParameter("curPwd");
		String newPwd = req.getParameter("newPwd");
		
		if (curPwd == null || curPwd.isEmpty()) {
			errors.put("curPwd", Boolean.TRUE);
		}
		if (curPwd == null || curPwd.isEmpty()) {
			errors.put("newPwd", Boolean.TRUE);
		}
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		int group;
		try {
			if(user == null)
				group = stduser.getAccess();
			else
				group = user.getAccess();
			/* 권한 체크 */
			boolean value1 = ((group==Authority.getProDean())||(group==Authority.getProEval())
					||(group==Authority.getProNotEval()));
			boolean value2 = ((group == Authority.getStuTeam())
					||(group == Authority.getStuTeamMaker())
					||(group == Authority.getStuNotTeam()));
			
			if(value1){
				changePwdSvc.changePassword_Pro(user.getId(), curPwd, newPwd);
				return "/WEB-INF/view/changePwdSuccess.jsp";
			}
			else if(value2){
				changePwdSvc.changePassword_Stu(stduser.getId(), curPwd, newPwd);
				return "/WEB-INF/view/changePwdSuccess.jsp";
			}
			else{
				/* 그룹 선택 안했을 경우 */
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
			
		} catch (InvalidPasswordException e) {
			errors.put("badCurPwd", Boolean.TRUE);
			return FORM_VIEW;
		} catch (MemberNotFoundException e) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}

}
