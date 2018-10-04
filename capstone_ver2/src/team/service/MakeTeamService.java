package team.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Calendar;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import team.dao.*;
import team.model.*;
import team.service.MakeTeamRequest;;


public class MakeTeamService {

   private TeamDao teamDao = new TeamDao();
   
   public void MakeTeam(MakeTeamRequest mtReq) {
      Connection conn = null;
      try {
    	 DecimalFormat df = new DecimalFormat("00"); // 연도 구하기위한 포맷 형식 지정
     	 Calendar currentCalendar = Calendar.getInstance();
     	 String strYear = null;
     	 strYear = Integer.toString(currentCalendar.get(Calendar.YEAR)); 
     	 strYear = strYear.substring(2,4);
     	 String tNo = (strYear + "_" + mtReq.getTeamNo() + "_" + mtReq.getGroupNo()); //팀 고유 번호 구하는 방식(생성연도 + 팀조번호 + 반)  
     	 
         conn = ConnectionProvider.getConnection();
         conn.setAutoCommit(false);
         System.out.println(tNo);
         Team team = teamDao.selectByteam(conn, tNo);
        
		 if (team != null) {
			JdbcUtil.rollback(conn);		
			throw new DuplicateTeamException();
		}
         teamDao.insert(conn, 
               new Team(
            		 mtReq.getTeamNo(), 
                     mtReq.getTeamName(), 
                     null,
                     mtReq.getAdvisor(),                     
                     mtReq.getGroupNo(),
                     true,
                     new Date())
               , mtReq.getId()
               , mtReq.getS_groupNo());
         conn.commit();
      } catch (SQLException e) {
         JdbcUtil.rollback(conn);
         throw new RuntimeException(e);
      } finally {
         JdbcUtil.close(conn);
      }
   }
   
   public boolean searchTeam(MakeTeamRequest stReq) {
	   Connection conn = null;
	      try {
	         conn = ConnectionProvider.getConnection();
	         conn.setAutoCommit(false);
	         Team team = teamDao.selectByteam(conn, stReq.getTeamNo());
			 if (team != null) {				
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
