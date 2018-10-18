package eval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import article.common.ArticleNotFoundException;
import eval.model.Evalplan;
import eval.service.AllEvalStatusValue;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class EvalplanDao {
			/* 평가계획서 평가번호로 읽어오기 */
	   public Evalplan selectByEvalNo(Connection conn, String evalNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from eplan where planNo = ?");
	         pstmt.setString(1, evalNo);
	         rs = pstmt.executeQuery();
	         Evalplan evalplan = null;
	         String pNo = null;
	         if (rs.next()) {
	        	 pNo = rs.getString("planNo");
	        	 evalplan = new Evalplan(
	        			 pNo,
	        		  rs.getString("dean"),
	                  toDate(rs.getTimestamp("regDate")),
	                  toDate(rs.getTimestamp("endDate")),
	                  rs.getInt("state"));
	         }
	         //test
	         return evalplan;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
	   
	   public boolean DoesEvalPlanExist(Connection conn, String evalNo) throws SQLException {
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      
		      try {
		         pstmt = conn.prepareStatement(
		               "select * from eplan where planNo = ?");
		         pstmt.setString(1, evalNo);
		         rs = pstmt.executeQuery();
		        
		         //rs가 null일 때, 해당 계획서 존재하지 않음.
		         //rs.next가 null일 때, 해당 계획서 존재하지 않음.
		         if(rs==null||(!rs.next())) {
		        	 return false;
		         }
		         
		         return true;
		      } finally {
		         JdbcUtil.close(rs);
		         JdbcUtil.close(pstmt);
		      }
		   }
	   
	   private Date toDate(Timestamp date) {
		      return date == null ? null : new Date(date.getTime());
		   }
	   
	   /* 평가계획서 DB입력  */
	   public Evalplan insert(Connection conn, Evalplan eval) throws SQLException {
		   PreparedStatement pstmt = null;
		   try {
	    	  pstmt = conn.prepareStatement("insert into eplan (planNo,dean,stDate,endDate,state) values (?,?,?,null,?)");
	         pstmt.setString(1,  eval.getEvalNo());
	         pstmt.setString(2,  eval.getDean());
	         pstmt.setTimestamp(3, new Timestamp(eval.getRegDate().getTime()));
	         //pstmt.setTimestamp(4,  new Timestamp(eval.getEndDate().getTime()));
	         pstmt.setInt(4,  eval.getEvalState());
	         int insertedCount = pstmt.executeUpdate();
	         if(insertedCount>0) {
	        	 return selectByEvalNo(conn,eval.getEvalNo());
	         }
	         return null;
	      }
	      finally {
				JdbcUtil.close(pstmt);
	      }
	   }		  
	   
	   public String getEvalState() throws SQLException {
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   DecimalFormat df = new DecimalFormat("00"); // 연도 구하기위한 포맷 형식 지정
		   Calendar currentCalendar = Calendar.getInstance();
		   String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
		      try {
		    	 conn = ConnectionProvider.getConnection();
		    	 String evalNo = strYear+"-01";
		    	 pstmt = conn.prepareStatement(
		               "select state from eplan where planNo = ?");
		         pstmt.setString(1, evalNo);
		         rs = pstmt.executeQuery();
		         String state = null;
		         if (rs.next()) {
		        	 state = rs.getString("state");
		        	 return state; 
		         }
		         return null;
		      } finally {
		         JdbcUtil.close(rs);
		         JdbcUtil.close(pstmt);
		      }
	   }
	   
	   /* 평가 완료 변경 메소드 */
	   public void update_eval_complete(Connection conn, Evalplan eval) throws SQLException {
	      try (PreparedStatement pstmt = conn.prepareStatement(
	    		  "update evalplan set state = ?, endDate = ? where evalNo = ?")) {
	         pstmt.setInt(1, AllEvalStatusValue.getEvalPlanEnded());
	         pstmt.setTimestamp(2, new Timestamp(eval.getEndDate().getTime()));
	         pstmt.setString(3, eval.getEvalNo());
	         pstmt.executeUpdate();
	      }
	   }
	   /* 평가 진행중 변경 메소드 */
	   public void update_eval_ongoing(Connection conn, Evalplan eval) throws SQLException {
		      try (PreparedStatement pstmt = conn.prepareStatement(
		    		  "update evalplan set state = ? where evalNo = ?")) {
		         pstmt.setInt(1, AllEvalStatusValue.getEvalPlanStarted());
		         pstmt.setString(2, eval.getEvalNo());
		         pstmt.executeUpdate();
		      }
		   }
		   
		   /* 평가 삭제 메소드 */
	   public Evalplan deleteByEvalNo(Connection conn, Evalplan eval) throws SQLException {
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Evalplan rm_eval = selectByEvalNo(conn, eval.getEvalNo());
				if(rm_eval == null) {
					//평가가 찾아지지 않습니다. 라는 오류 함수로 넘어가게끔. 수정하자
					throw new ArticleNotFoundException();
				}
				
				pstmt = conn.prepareStatement("delete from evalplan where planNo = ? ");
				pstmt.setString(1, eval.getEvalNo());
				int insertedCount = pstmt.executeUpdate();		//리턴값은 성골했을 때 : 성공한 행의 개수, 실패시 : 0
				
				if(insertedCount>0) {
						return rm_eval;
				}
				return null;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
				JdbcUtil.close(pstmt);
		}
	}
}
