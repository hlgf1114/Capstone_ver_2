package team.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import team.dao.TeamDao;
import team.model.TeamList;

public class ProListTeamService {
	
	private TeamDao teamdao = new TeamDao();
	
	public ProListTeamRequest SearchTeamList(){
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			DecimalFormat df = new DecimalFormat("00"); // 연도 구하기위한 포맷 형식 지정
	     	Calendar currentCalendar = Calendar.getInstance();
	     	String strYear = null;
	     	int countTeam = 0;
	     	
	     	strYear = Integer.toString(currentCalendar.get(Calendar.YEAR)); 
	     	strYear = strYear.substring(2,4);
	     	
			List<TeamList> teamlist = teamdao.selectAllTeam_pro(conn, strYear);
			countTeam = teamdao.CountTeam(conn);
			ProListTeamRequest ptReq = new ProListTeamRequest(countTeam, teamlist);
			
			if(teamlist == null)
			{
				JdbcUtil.rollback(conn);
				return null;
			}
			
			return ptReq;
			
		}catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
}
