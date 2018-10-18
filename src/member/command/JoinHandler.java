package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.Authority;
import auth.service.LoginFailException;
import member.service.DuplicateIdException;
import member.service.EncryptPassword;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;
import member.service.ClassifyMember;

public class JoinHandler implements CommandHandler {
	final private String TEAM_DEFAULT = "00000";
	
	private static final String FORM_VIEW = "/WEB-INF/view/Join_Need.jsp";
	private JoinService joinService = new JoinService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) {
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

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		JoinRequest joinReq = new JoinRequest();

//		String salt = EncryptPassword.generateSalt();		
//		String newPwd = EncryptPassword.getEncrypt(req.getParameter("password"), salt);
//		String newConPwd = EncryptPassword.getEncrypt(req.getParameter("confirmPassword"), salt);
		
		joinReq.setId(req.getParameter("id"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPassword(req.getParameter("password"));
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));
		joinReq.setPhoneNo(req.getParameter("phonenumber"));
		joinReq.setEmail(req.getParameter("email"));
		
		if(parseint(req.getParameter("groupnumber"))==ClassifyMember.getStu()){
			joinReq.setGroupNo(Authority.getStuNotTeam());
			joinReq.setTeamNo(TEAM_DEFAULT);
		}else {
			joinReq.setGroupNo(Authority.getProNotEval());
		}
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		joinReq.validate(errors);
		
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			if(joinReq.getGroupNo()==Authority.getProNotEval()){
				joinService.join_pro(joinReq);	
				return "/WEB-INF/view/joinSuccess.jsp";
			}
			else if(joinReq.getGroupNo()==Authority.getStuNotTeam()){
				joinService.join_stu(joinReq);
				return "/WEB-INF/view/joinSuccess.jsp";
			}
			else{
				errors.put("putValues", Boolean.TRUE);
				return FORM_VIEW;
			}
		} catch (DuplicateIdException e) {
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
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
