package eval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import eval.model.EvalTeam;
import eval.model.Evalplan;
import jdbc.JdbcUtil;

public class EvalTeamDao {
	
	public void insert(Connection conn, String teamNo, String finalNo) throws SQLException {
		   try(PreparedStatement pstmt = conn.prepareStatement("insert into eteam (teamNo, finalNo) values (?,?)")) {
	         pstmt.setString(1,  teamNo);
	         pstmt.setString(2,  finalNo);
	         pstmt.executeUpdate();
	         /* 밑에 주석은 데이터가 잘 들어갔는지 확인하는 과정이지만 필요없을 듯함  */
//	         int insertedCount = pstmt.executeUpdate();
//	         if(insertedCount>0) {
//	        	 return select(conn,eval.getEvalNo());
//	         }
//	         return null;
	      }
	   }
	
	   public EvalTeam select(Connection conn, String t) throws SQLException {
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      try {
		         pstmt = conn.prepareStatement(
		               "select * from eteam where teamNo = ?");
		         pstmt.setString(1, t);
		         rs = pstmt.executeQuery();
		         EvalTeam evalteam = null; 
		         if (rs.next()) {	      
		        	 evalteam = new EvalTeam(rs.getString("teamNo"),rs.getString("finalNo"));
		         }
		         return evalteam;
		      } finally {
		         JdbcUtil.close(rs);
		         JdbcUtil.close(pstmt);
		      }
	   }
	
	
	 	/* 클래스 설계 다시  */
	   /* 평가 팀 수 읽어오기 */
	   public int selectTeamCount(Connection conn, String e) throws SQLException {
		   	PreparedStatement pstmt = null;		//Statement와 캐시 기능의 차이
		   	//Statement stmt = null;
		   	ResultSet rs = null;
			try {
				 pstmt = conn.prepareStatement(
			               "select count(*) from eteam when planNo = ?");	//평가 팀 저장 티이블 : eteam
		         pstmt.setString(1, e);
		         rs = pstmt.executeQuery();
			     if (rs.next()) {
					return rs.getInt(1);
			     }
			     return 0;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(pstmt);
			}
		}
	   /* 평가 팀 번호 전부 가져오기 */
	   public ArrayList<String> selectByEvalNoFromEteam(Connection conn, String e) throws SQLException {
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      try {
		         pstmt = conn.prepareStatement(
		               "select * from eteam where planNo = ?");
		         pstmt.setString(1, e);
		         rs = pstmt.executeQuery();
		         ArrayList<String> pl = new ArrayList<String>();
		         while (rs.next()) {	      
		        	 pl.add(rs.getString("teamNo"));
		         }
		         return pl;
		      } finally {
		         JdbcUtil.close(rs);
		         JdbcUtil.close(pstmt);
		      }
	   }
	
//	   /* 평가 팀 목록 DB insert */
//	   public void insertToEvalteam(Connection conn, Evalplan eval) throws SQLException {
//		      try (PreparedStatement pstmt =		         
//		    		  conn.prepareStatement("insert into eteam(planNo,teamNo,finalNo) values(?,?)")) {
//		         final String evalno = eval.getEvalNo();
//		         ArrayList<String> tl = eval.getTlist();
//		    	  for(int i = 0; i<eval.gettNo();i++) {
//		        	 pstmt.setString(1, evalno);
//			         pstmt.setString(2, tl.get(i));
//			         pstmt.executeUpdate();	 
//		         }
//		      }
//		   }
//	   
	   /* 평가 팀 전부 삭제 메소드 */
	   public ArrayList<String> deleteEvalTeam(Connection conn, Evalplan e) throws SQLException {
		   PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;   

		   try {
		    	  ArrayList<String> tlist = selectByEvalNoFromEteam(conn, e.getEvalNo());
		    	  
		    	  pstmt = conn.prepareStatement("delete from eteam where planNo = ? ");
					pstmt.setString(1, e.getEvalNo());
					int insertedCount = pstmt.executeUpdate();		//리턴값은 성골했을 때 : 성공한 행의 개수, 실패시 : 0
					
					if(insertedCount>0) {
							return tlist;
					}
					return null;
		   		} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
				JdbcUtil.close(pstmt);
		   		}
		   }
	   
	   /* 클래스 설계 다시  */
	
	public String selectByEvalNoToFindFinal(Connection conn, String teamNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from eteam where teamNo = ?");
	         pstmt.setString(1, teamNo);
	         rs = pstmt.executeQuery();   
	         return rs.getString("finalNo");
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	}
	/* 이거 제대로 동작하는지 궁금. */
	public List<EvalTeam> select(Connection conn, int startRow, int size) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      try {
	         pstmt = conn.prepareStatement("select * from eteam " +
	               "where teamNo like "+countYear()+"%"+
	        		 "order by teamNo desc limit ?, ?");
	         pstmt.setInt(1, startRow);
	         pstmt.setInt(2, size);
	         rs = pstmt.executeQuery();
	         List<EvalTeam> result = new ArrayList<>();
	         while (rs.next()) {
	            result.add(convertArticle(rs));
	         }
	         return result;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
	
	 public int selectCount(Connection conn) throws SQLException {
	      Statement stmt = null;
	      ResultSet rs = null;
	      try {
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery("select count(*) from eteam where teamno like "+ countYear()+"%");
	         if (rs.next()) {
	            return rs.getInt(1);
	         }
	         return 0;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(stmt);
	      }
	   }
	
	 private EvalTeam convertArticle(ResultSet rs) throws SQLException {
	      return new EvalTeam(rs.getString("teamNo"), rs.getString("finalNo"));
	 }
	 
	 private String countYear() {
		 DecimalFormat df = new DecimalFormat("00"); // 연도 구하기위한 포맷 형식 지정
	      Calendar currentCalendar = Calendar.getInstance();
	      String Year = Integer.toString(currentCalendar.get(Calendar.YEAR));
	      Year = Year.substring(2);
	      return Year;
	 }
	 
}
