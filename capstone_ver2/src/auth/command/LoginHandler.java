package auth.command;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.*;
import mvc.command.CommandHandler;
import member.service.ClassifyMember;
import member.service.EncryptPassword;

public class LoginHandler implements CommandHandler {

	private static final String FORM_VIEW = "/index.jsp";
	private LoginService loginService = new LoginService();
	
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
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		String id = trim(req.getParameter("id"));
		String password = trim(req.getParameter("password"));
		String group = trim(req.getParameter("groupnumber"));
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		if (id == null || id.isEmpty())
			errors.put("id", Boolean.TRUE);
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);

		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			if(Integer.parseInt(group) == ClassifyMember.getPro()) {
				User prouser = loginService.ProfessorLogin(id, password, parseint(group));
				req.getSession().setAttribute("authProUser", prouser);
				RequestDispatcher dispatcher = req.getRequestDispatcher("noticelist.do");
		    	dispatcher.forward(req,res);
				return null;
			}
			else if(Integer.parseInt(group) == ClassifyMember.getStu()){
				StudentUser studentuser = loginService.StudentLogin(id, password, parseint(group));
				req.getSession().setAttribute("authStdUser", studentuser);
				res.sendRedirect("noticelist.do");
				return null;
			}
			else {
				return null;
			}
		} catch (LoginFailException e) {
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
	}


	//필요없을 수도 있음
	private String trim(String str) {
		return str == null ? null : str.trim();
	}
	
	//필요없을 수도 있음
	private int parseint(String str) {
		String temp = str;
		try{
			return Integer.parseInt(temp);
		} catch(NumberFormatException nfe){
			System.err.println(nfe);
			throw new LoginFailException();
		}
	}
}
