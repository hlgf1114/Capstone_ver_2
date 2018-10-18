package team.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdbc.connection.ConnectionProvider;
import mvc.command.CommandHandler;
import team.service.ProListTeamRequest;
import team.service.ProListTeamService;

public class ProListTeamHandler implements CommandHandler {
	private static final String FORM_VIEW = "/index_old.jsp";	//수정과 같은 뷰면 될듯
	ProListTeamService prolistservice = new ProListTeamService();
	
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
		
		ProListTeamRequest plReq = prolistservice.SearchTeamList();
		
		req.setAttribute("ListTeamPage", plReq);
		//이 부분 평가 화면으로 넘겨야함.
		return FORM_VIEW;
	}
}
