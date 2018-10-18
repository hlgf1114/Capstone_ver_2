package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.StudentUser;
import member.service.ChangeTeamService;
import member.service.InvalidPasswordException;
import member.service.MemberNotFoundException;
import mvc.command.CommandHandler;
import auth.service.Authority;


public class ChangeTeamHandler implements CommandHandler {
	
	
	private static final String FORM_VIEW = "/WEB-INF/view/changeTeamForm.jsp";
	private ChangeTeamService changeTeamSvc = new ChangeTeamService();
	
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
		StudentUser user = (StudentUser)req.getSession().getAttribute("authStdUser");
			
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		String curPwd = req.getParameter("curPwd");
		String newTeam = req.getParameter("newTeam");
		
		if (curPwd == null || curPwd.isEmpty()) {
			errors.put("curPwd", Boolean.TRUE);
		}
		if (curPwd == null || curPwd.isEmpty()) {
			errors.put("newTeam", Boolean.TRUE);
		}
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			int group = user.getAccess();
			if(group == Authority.getStuTeam()){
				changeTeamSvc.changeTeam(user.getId(), curPwd, newTeam);
				return "/WEB-INF/view/changeTeamSuccess.jsp";
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
