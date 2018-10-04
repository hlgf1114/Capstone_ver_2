package eval.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eval.dao.EvalFinalDao;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import team.dao.TeamDao;

public class TerminateEvalplanService {
	
	EvalFinalDao evalFinalDao = new EvalFinalDao();
	TeamDao teamDao = new TeamDao();
	
	public boolean NotComplete(String evalNo, List<ShowTeam> st) {
		Connection conn = null;
		List<String> finallist = null;
		List<ShowTeam> restlist = new ArrayList<ShowTeam>();
		
		try {
			conn = ConnectionProvider.getConnection();
			
			finallist = evalFinalDao.selectNotCompleteFinal(conn, AllEvalStatusValue.togetStrYear());
			ShowTeam temp = null;
			String teamNo = null;
			for(String var : finallist) {
				teamNo = TrimTeamNo(var);
				temp = teamDao.selectShowTeam(conn, teamNo);
				restlist.add(temp);
			}
			st = restlist;
			if(finallist==null) {
				return false;
			}
			else {
				return true;	
			}
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	private String TrimTeamNo(String finalNo) {
		String temp =  finalNo.replace(("_"+AllEvalStatusValue.getDefaultFinalDocu()), "");
		System.out.println("팀 번호 : "+temp);
		return temp;
	}
}
