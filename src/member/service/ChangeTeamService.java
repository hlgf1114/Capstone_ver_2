package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.*;
import member.model.*;

public class ChangeTeamService {
	
	private StudentDao studentDao = new StudentDao();
	
	public void changeTeam(String userId, String curPwd, String newTeam) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Student student = studentDao.selectById(conn, userId);
			if (student == null) {
				throw new MemberNotFoundException();
			}
			if (!student.matchPassword(curPwd)) {
				throw new InvalidPasswordException();
			}
			student.changeTeam(newTeam);
			studentDao.update_team(conn, student);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}