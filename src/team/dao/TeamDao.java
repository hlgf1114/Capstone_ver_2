package team.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import eval.service.ShowTeam;
import jdbc.JdbcUtil;
import team.model.Team;
import team.model.TeamList;
import member.dao.*;

public class TeamDao {
	
	private StudentDao studentDao = new StudentDao();
	
   public Team selectByteam(Connection conn, String teamNo) throws SQLException {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         pstmt = conn.prepareStatement(
               "select * from team where teamNo = ?");
         pstmt.setString(1, teamNo);
         rs = pstmt.executeQuery();
         Team team = null;
         
         if (rs.next()) {
        	 boolean tstate = true;
             if(rs.getString("state").equals("1"))
            	 tstate = true;
             else if(rs.getString("state").equals("0"))
            	 tstate = false;
            team = new Team(
            	  rs.getString("teamNo"),         //이거 db int -> str 수정해야함 
                  rs.getString("teamName"), 
                  rs.getString("teamSubject"),
                  rs.getString("advisor"),
                  rs.getString("groupNo"),
            	  tstate,                           
                  toDate(rs.getTimestamp("TeamJoinDate")));      //db 이름을 teamRegDate로 바꿧으면함.
            	  
            	
       }
         return team;
      } finally {
         JdbcUtil.close(rs);
         JdbcUtil.close(pstmt);
      }
   }
   
   /* 한 팀의 번호, 이름 가져오기 */
   public ShowTeam selectShowTeam(Connection conn, String teamNo) throws SQLException {
	   PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from team where teamNo = ?");
	         pstmt.setString(1, teamNo);
	         rs = pstmt.executeQuery();
	         ShowTeam team = null;
	         
	         if (rs.next()) {	        	 
	            team = new ShowTeam(
	            	  rs.getString("teamNo"),         //이거 db int -> str 수정해야함 
	                  rs.getString("teamName"));      //db 이름을 teamRegDate로 바꿧으면함.
	       }
	         return team;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
   /* 학과장님의 평가 시작 페이지에서 평가받아야할 팀의 이름들만 가져오는 select 함수 */
   public List<ShowTeam> selectAllTeam(Connection conn, String strYear) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String year = "'" + strYear.substring(2,2) + "%'";

	      try {
	         pstmt = conn.prepareStatement(
	               "select * from team where teamNo like "+ year);
	         rs = pstmt.executeQuery();
	         List<ShowTeam> result = new ArrayList<ShowTeam>();
	         ShowTeam eteam = null;
	         while (rs.next()) {
	        	 	eteam = new ShowTeam(
	        		rs.getString("teamNo"),         //이거 db int -> str 수정해야함 
                    rs.getString("teamName") 
	                );   
					result.add(eteam);
				}
				return result;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }	      
	   }
//  교수님메인 팀들 가져오기
   public List<TeamList> selectAllTeam_pro(Connection conn, String strYear) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String year = "'" + strYear.substring(2,2) + "%'";

	      try {
	         pstmt = conn.prepareStatement(
	               "select * from team where teamNo like "+ year);
	         rs = pstmt.executeQuery();
	         List<TeamList> result = new ArrayList<TeamList>();
	         TeamList eteam = null;
	         while (rs.next()) {
	        	 	eteam = new TeamList(
	        		rs.getString("teamNo"),         //이거 db int -> str 수정해야함 
                 rs.getString("teamName") 
	                );   
					result.add(eteam);
				}
				return result;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }	      
	   }
//   
//   private Team convertArticle(ResultSet rs) throws SQLException {
//		return new Team(
//          	  rs.getString("teamNo"),         //이거 db int -> str 수정해야함 
//              rs.getString("teamName"), 
//              rs.getString("teamSubject"),
//              rs.getString("advisor"),
//              rs.getString("groupNo"),
//              true,                           
//              toDate(rs.getTimestamp("TeamJoinDate")));
//	}
   
   private Date toDate(Timestamp date) {
      return date == null ? null : new Date(date.getTime());
   }
   
   
   
   public void insert(Connection conn, Team team, String stu_Id, int s_groupNo) throws SQLException {
      try (PreparedStatement pstmt = 
            conn.prepareStatement("insert into team(teamNo,teamName,teamSubject"
            		+ ",advisor,groupNo,state,TeamJoinDate) values(?,?,?,?,?,?,?)")) {
    	
    	 DecimalFormat df = new DecimalFormat("00"); // 연도 구하기위한 포맷 형식 지정
    	 Calendar currentCalendar = Calendar.getInstance();
    	 String strYear = null;
    	 strYear = Integer.toString(currentCalendar.get(Calendar.YEAR)); 
    	 strYear = strYear.substring(2,4);
    	 String tNo = (strYear + "_" + team.getTeamNo() + "_" + team.getGroupNo()); //팀 고유 번호 구하는 방식(생성연도 + 팀조번호 + 반)       
    	 pstmt.setString(1,  tNo);
         pstmt.setString(2,  team.getTeamName());
         pstmt.setString(3,  team.getTeamSubject());
         pstmt.setString(4,  team.getAdvisor());
         pstmt.setString(5,  team.getGroupNo());
         pstmt.setBoolean(6,  team.isState());            //boolean 값 db에 어떻게 저장하는지    
         pstmt.setTimestamp(7, new Timestamp(team.getTeamJoinDate().getTime()));
         pstmt.executeUpdate();
         
         studentDao.update_tNo(conn, tNo, stu_Id, s_groupNo);
      }
   }
   /* 정보 변경 메소드 */
   public void update_team(Connection conn, String stuId, String teamNo) throws SQLException {
      try (PreparedStatement pstmt = conn.prepareStatement(
            "update student set teamNo = ? where stuId = ?")) {
          pstmt.setString(1, teamNo);
    	  pstmt.setString(2, stuId);
          pstmt.executeUpdate();
      }
   }
   
   public void update_team_gNo(Connection conn, String stuId, int groupNo) throws SQLException {
	      try (PreparedStatement pstmt = conn.prepareStatement(
	            "update student set groupNo = ? where stuId = ?")) {
	          pstmt.setInt(1, groupNo);
	    	  pstmt.setString(2, stuId);
	          pstmt.executeUpdate();
	      }
	   }
   
   public void delete(Connection conn, Team team) throws SQLException {
	   try (PreparedStatement pstmt = conn.prepareStatement(
			   "delete from team where teamNo = ?")) {
		   pstmt.setString(1, team.getTeamNo());
		   pstmt.executeUpdate();				   
	   }
   }
   public void update_groupNo(Connection conn, String stuId) throws SQLException {
	   try (PreparedStatement pstmt = conn.prepareStatement(
			   "update student set groupNo = 30 where stuId = ?")){
		   pstmt.setString(1, stuId);		
		   pstmt.executeUpdate();
	   }
   }
   
   public void delete_teamNo(Connection conn, String stuId) throws SQLException {
	   try (PreparedStatement pstmt = conn.prepareStatement(
			   "update student set teamNo = null where stuId = ?")){
		   pstmt.setString(1, stuId);		
		   pstmt.executeUpdate();
	   }
   }
   
   public Team selectByteamName(Connection conn, String teamName) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from team where teamName = ?");
	         pstmt.setString(1, teamName);
	         rs = pstmt.executeQuery();
	         Team team = null;
	         
	         if (rs.next()) {
	        	 boolean tstate = true;
	             if(rs.getString("state").equals("1"))
	            	 tstate = true;
	             else if(rs.getString("state").equals("0"))
	            	 tstate = false;
	            team = new Team(
	            	  rs.getString("teamNo"),         //이거 db int -> str 수정해야함 
	                  rs.getString("teamName"), 
	                  rs.getString("teamSubject"),
	                  rs.getString("advisor"),
	                  rs.getString("groupNo"),
	            	  tstate,                           
	                  toDate(rs.getTimestamp("TeamJoinDate")));      //db 이름을 teamRegDate로 바꿧으면함.           	  
	            	
	       }
	         return team;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
   
   public int CountTeam(Connection conn) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	    	 int countTeam = 0;
	         pstmt = conn.prepareStatement(
	               "select count(*) count from team");
	         rs = pstmt.executeQuery();
	         
	         rs.next();
	         countTeam = rs.getInt("count");
	         return countTeam;

	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }

}
