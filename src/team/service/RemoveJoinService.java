package team.service;

import java.sql.Connection;
import java.sql.SQLException;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.service.MemberNotFoundException;
import team.dao.*;
import member.model.*;
import member.dao.*;

public class RemoveJoinService {

	private TeamDao teamDao = new TeamDao();
	private StudentDao studentDao = new StudentDao();
	
	public void delete_teamNo(String stuId) { 	//학생 테이블의 팀넘버 지우기
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			Student student = studentDao.selectById(conn, stuId);
			if(student == null) {
				throw new MemberNotFoundException();
			}
			teamDao.delete_teamNo(conn,  stuId);
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
