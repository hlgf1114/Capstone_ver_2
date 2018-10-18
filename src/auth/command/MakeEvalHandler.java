package auth.command;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.connection.ConnectionProvider;


import mvc.command.CommandHandler;
import auth.service.*;
import auth.service.Member;
import team.dao.*;
import team.model.*;

public class MakeEvalHandler implements CommandHandler {

	private static final String FORM_VIEW = "/index.jsp";
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processSubmit(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processForm(req, res);
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
		
		StudentUser user = (StudentUser)req.getSession(false).getAttribute("authStdUser");		
		Member member = createMember(user, req);
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		String teamNo = member.getTeamNo();
		String teamName = member.getTeamName();
		String groupNo = member.getGroupNo();

		if (teamNo == null || teamNo.isEmpty())
			errors.put("teamNo", Boolean.TRUE);
		if (teamName == null || teamName.isEmpty())
			errors.put("teamName", Boolean.TRUE);
		if (groupNo == null || groupNo.isEmpty())
			errors.put("groupNo", Boolean.TRUE);

		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			req.getSession().setAttribute("authTeam", member);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
    	  	dispatcher.forward(req,res);
			return null;
		} catch (LoginFailException e) {
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
	
	private Member createMember(StudentUser stduser, HttpServletRequest req)
	{
		TeamDao teamdao = new TeamDao();
		Team team;
		try(Connection conn = ConnectionProvider.getConnection()){
			team = teamdao.selectByteam(conn, stduser.getTeamNo());
			if(team == null) {
				throw new LoginFailException();	//team Fail로 만들어서 바꿔야함.
			}
		}catch (SQLException e) {
			throw new LoginFailException();
		}
		return new Member(team.getTeamNo(), team.isState(), team.getTeamName(), 
				team.getGroupNo());
	}
}
