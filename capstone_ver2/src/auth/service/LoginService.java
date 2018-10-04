package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.*;
import member.model.*;
import member.service.ClassifyMember;
import member.service.EncryptPassword;

public class LoginService {
	
	
	
	private ProfessorDao professorDao = new ProfessorDao();
	private StudentDao studentDao = new StudentDao();
	
	public User ProfessorLogin(String id, String password, int grade) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			if(grade == ClassifyMember.getPro()){				
				Professor professor = professorDao.selectById(conn, id);
				if (professor == null) {
					throw new LoginFailException();
				}				
//				String dbPwd = professor.getPassword();
//				String inputPwd = password;
//				String newPwd = EncryptPassword.getEncrypt(inputPwd, dbPwd);
				
				if (!professor.matchPassword(password)) {
					throw new LoginFailException();
				}
				
				return new User(professor.getProId(), professor.getProname(), 
						professor.getGroupNo());
			}
			//그룹 선택 안하면 로그인 못하게 해야함.
				return null;
			}catch (SQLException e) {
				throw new RuntimeException(e);
		}
	}
	
	public StudentUser StudentLogin(String id, String password, int grade) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			if(grade == ClassifyMember.getStu()) {
				Student student = studentDao.selectById(conn, id);
				if(student == null) {
					throw new LoginFailException();
				}				
//				String dbPwd = student.getPassword();
//				String inputPwd = password;
//				String newPwd = EncryptPassword.getEncrypt(inputPwd, dbPwd);
				
				if(!student.matchPassword(password)) {
					throw new LoginFailException();
				}
				return new StudentUser(student.getStuId(), student.getStuname(), 
						student.getTeamNo(), student.getGroupNo());		
			}
				return null;
		
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}