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
import java.util.Date;
import java.util.List;

import eval.model.EvalTeam;
import eval.model.Evalplan;
import eval.service.ShowProf;
import jdbc.JdbcUtil;

public class EvalProfDao {

		public void insert(Connection conn, String evalNo, String proId) throws SQLException {
			PreparedStatement pstmt = null;
		   try {
			 pstmt = conn.prepareStatement("insert into epflist (evalNo, proId) values (?,?)");
			 pstmt.setString(1, evalNo);
	         pstmt.setString(2, proId);
	         /* 밑에 주석은 데이터가 잘 들어갔는지 확인하는 과정이지만 필요없을 듯함  */
	         int insertedCount = pstmt.executeUpdate();
	         if(insertedCount>0) {
	        	 System.out.println("데이터가 잘 들어감");
	         	}
	         }
	         finally {
		         JdbcUtil.close(pstmt);
		     }
	   }
	
	   public List<String> selectAllProf(Connection conn, String e) throws SQLException {
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      try {
		         pstmt = conn.prepareStatement(
		               "select * from epflist where evalNo = ?");
		         pstmt.setString(1, e);
		         rs = pstmt.executeQuery();
		         List<String> list = new ArrayList<String>(); 
		         while (rs.next()) {	      
		        	 list.add(rs.getString("proId"));
		         }
		         return list;
		      } finally {
		         JdbcUtil.close(rs);
		         JdbcUtil.close(pstmt);
		      }
	   }
	/* 평가 참여 교수 숫자 읽어오기  */
	   public int selectPfCount(Connection conn, String e) throws SQLException {
		   	PreparedStatement pstmt = null;		//Statement와 캐시 기능의 차이
		   	//Statement stmt = null;
		   	ResultSet rs = null;
			try {
				 pstmt = conn.prepareStatement(
			               "select count(*) from eprof when planNo = ?");	//평가 참여 교수 저장 티이블 : eprof
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
	   
	   /* */
	   public boolean IsPossibleToEval(Connection conn, String planNo, String proId) throws SQLException {
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      try {
		         pstmt = conn.prepareStatement(
		               "select * from epflist where evalNo = ? and proId = ?");
		         pstmt.setString(1, planNo);
		         pstmt.setString(2, proId);
		         rs = pstmt.executeQuery();
		         if (rs.next()) {	      
		        	 return true;
		         }
		         return false;
		      }catch(SQLException e)  {		    
		    	  return false;
		      }
		      finally {
		         JdbcUtil.close(rs);
		         JdbcUtil.close(pstmt);
		      }
	   }
	   

	   /* 평가 참여 교수 아이디 전부 가져오기 */
	   public ArrayList<String> selectByEvalNoFromEprof(Connection conn, String e) throws SQLException {
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      try {
		         pstmt = conn.prepareStatement(
		               "select * from eprof where planNo = ?");
		         pstmt.setString(1, e);
		         rs = pstmt.executeQuery();
		         ArrayList<String> pl = new ArrayList<String>();
		         
		         while (rs.next()) {	      
		        	 pl.add(rs.getString("proId"));
		         }
		         return pl;
		      } finally {
		         JdbcUtil.close(rs);
		         JdbcUtil.close(pstmt);
		      }
		   }

	   private Date toDate(Timestamp date) {
	      return date == null ? null : new Date(date.getTime());
	   }
	   
//	   
//	   /* 평가 참여 교수 목록 DB insert */
//	   public void insertToEprof(Connection conn, Evalplan e) throws SQLException {
//		      try (PreparedStatement pstmt =		         
//		    		  conn.prepareStatement("insert into eprof(planNo,proId) values(?,?)")) {
//		         final String evalno = e.getEvalNo();
//		         ArrayList<String> pl = e.getPflist();
//		    	  for(int i = 0; i<e.getProNum();i++) {
//		        	 pstmt.setString(1, evalno);
//			         pstmt.setString(2, pl.get(i));
//			         pstmt.executeUpdate();	 
//		         }
//		      }
//		   }
//	   
	   /* 평가 교수 전부 삭제 메소드 */
	   public ArrayList<String> deleteEvalProf(Connection conn, Evalplan e) throws SQLException {
		   PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;   

		   try {
		    	  ArrayList<String> plist = selectByEvalNoFromEprof(conn, e.getEvalNo());
		    	  
		    	  pstmt = conn.prepareStatement("delete from evalplan where planNo = ? ");
					pstmt.setString(1, e.getEvalNo());
					int insertedCount = pstmt.executeUpdate();		//리턴값은 성골했을 때 : 성공한 행의 개수, 실패시 : 0
					
					if(insertedCount>0) {
							return plist;
					}
					return null;
		   		} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
				JdbcUtil.close(pstmt);
		   		}
		   }
}
