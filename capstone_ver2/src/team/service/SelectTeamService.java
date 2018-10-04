package team.service;

import java.sql.Connection;
import java.sql.SQLException;
import auth.service.LoginFailException;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import team.dao.*;
import team.model.*;


public class SelectTeamService {

private TeamDao teamDao = new TeamDao();
	
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