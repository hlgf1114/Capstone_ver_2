//package team.service;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import jdbc.JdbcUtil;
//import jdbc.connection.ConnectionProvider;
//import member.model.Student;
//import member.dao.*;
//import member.service.InvalidPasswordException;
//import member.service.MemberNotFoundException;
//import team.dao.*;
//import team.model.*;
//import team.service.*;
//
//public class ChangeTeamService {
//
//	private TeamDao teamDao = new TeamDao();
//	private StudentDao studentDao = new StudentDao(); 
//	
//	public void changeTeam(String teamName, String newTeam, String curPwd, String userId) {
//		Connection conn = null;
//		try {
//			conn = ConnectionProvider.getConnection();
//			conn.setAutoCommit(false);
//			
//			Team team = teamDao.selectByteam(conn, teamName);
//			if (team == null) {
//				throw new TeamNotFoundException();
//			}
//			Student student = studentDao.selectById(conn, userId);
//			if (student == null) {
//				throw new MemberNotFoundException();
//			}
//			if (!student.matchPassword(curPwd)) {
//				throw new InvalidPasswordException();
//			}
//			student.changeTeam(newTeam);
//			studentDao.update(conn, student);
//			conn.commit();
//		} catch (SQLException e) {
//			JdbcUtil.rollback(conn);
//			throw new RuntimeException(e);
//		} finally {
//			JdbcUtil.close(conn);
//		}
//	}
//	
//	public void changePassword_Stu(String userId, String curPwd, String newPwd) {
//		Connection conn = null;
//		try {
//			conn = ConnectionProvider.getConnection();
//			conn.setAutoCommit(false);
//			
//
//			student.changePassword(newPwd);
//			studentDao.update(conn, student);
//			conn.commit();
//		} catch (SQLException e) {
//			JdbcUtil.rollback(conn);
//			throw new RuntimeException(e);
//		} finally {
//			JdbcUtil.close(conn);
//		}
//	}
//}