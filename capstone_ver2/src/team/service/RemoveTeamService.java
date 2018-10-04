package team.service;

import java.sql.Connection;
import java.sql.SQLException;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.service.MemberNotFoundException;
import team.dao.*;
import team.model.*;
import member.model.*;
import member.dao.*;

public class RemoveTeamService {

	private TeamDao teamDao = new TeamDao();
	
	public void delete_team(String curPwd, String teamNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			Team team = teamDao.selectByteam(conn, teamNo);
			if(team == null) {
				throw new MemberNotFoundException();
			}
			teamDao.delete(conn, team);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public void update_groupNo(String stuId) { 	//학생 테이블의 팀넘버 지우기
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			teamDao.update_groupNo(conn,  stuId);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
