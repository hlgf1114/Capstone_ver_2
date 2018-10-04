package article.team.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import auth.service.LoginFailException;
import eval.service.EvalTeamList;
import eval.service.ShowTeam;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import team.dao.*;
import team.model.*;


public class ListTeamService {

	private TeamDao teamDao = new TeamDao();
	
	private String togetStrYear() {
		Calendar currentCalendar = Calendar.getInstance();
		return Integer.toString(currentCalendar.get(Calendar.YEAR));
	}
	
	public EvalTeamList selectAllTeam() {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<ShowTeam> content = teamDao.selectAllTeam(conn, togetStrYear());
			
			EvalTeamList evalteamlist = new EvalTeamList(content.size(),content);
			return evalteamlist;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
	
	public Team selectTeam(String teamNo) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			if(teamNo != null) {	
				Team team = teamDao.selectByteam(conn, teamNo);
					if (team == null) {
						return null;
					}else {
						return team;
					}					
				}else {
					return null;
				}						
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean searchTeam(String teamNo) {
		   Connection conn = null;
		      try {
		         conn = ConnectionProvider.getConnection();
		         conn.setAutoCommit(false);
		         Team team = teamDao.selectByteam(conn, teamNo);
				 if (team == null) {				
					JdbcUtil.rollback(conn);
					return false;
				 }else {
						return true;
					}	         
		      } catch (SQLException e) {
		          JdbcUtil.rollback(conn);
		          throw new RuntimeException(e);
		      } finally {
		          JdbcUtil.close(conn);
		      }
	}
}