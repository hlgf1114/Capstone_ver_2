package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import team.model.*;
import team.dao.*;

public class MakeTeamService {
	
	private TeamDao teamDao = new TeamDao();
	
	public Member MakeTeam(String teamNo, boolean state, String teamName, String groupNo) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			if(teamNo != null) {	
				Team team = teamDao.selectByteam(conn, teamNo);
			if (team == null) {
				throw new LoginFailException();
			}
				return new Member(team.getTeamNo(), team.isState(), team.getTeamName(), team.getGroupNo());
			}else {
				return null;
			}						
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}