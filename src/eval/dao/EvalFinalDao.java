package eval.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.common.ArticleNotFoundException;
import eval.model.EvalFinal;
import eval.service.AllEvalStatusValue;
import jdbc.JdbcUtil;

public class EvalFinalDao {
	/* 개별 교수님 평가서 번호 */
	public String makeFinalNo(String tNo, String pId) {
		return tNo+"_"+AllEvalStatusValue.getDefaultFinalDocu();
	}
	
	public EvalFinal selectEvalFinal(Connection conn, String finalNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from efinal where finalNo = ?");
	         pstmt.setString(1, finalNo);
	         rs = pstmt.executeQuery();
	         EvalFinal evalfinal = null;
	         
	         if(rs.next()) {
	        	 evalfinal = new EvalFinal(finalNo, rs.getString("comment"),
		        		 toDate(rs.getTimestamp("regDate")),
	        			 toDate(rs.getTimestamp("endDate")),
	        			 rs.getDouble("average"),
	        			 rs.getInt("state"),rs.getInt("result")); 
	         }
	         return evalfinal;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
	
	public int selectState(Connection conn, String finalNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select state from efinal where finalNo = ?");
	         pstmt.setString(1, finalNo);
	         rs = pstmt.executeQuery();
	         
	         int state = 50;			//아무값
	         
	         if(rs.next()) {
	        	 state = rs.getInt("state");
	         }
	         return state;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	}
	
	public int selectResult(Connection conn, String finalNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select result from efinal where finalNo = ?");
	         pstmt.setString(1, finalNo);
	         rs = pstmt.executeQuery();
	         
	         int state = 50;			//아무값
	         
	         if(rs.next()) {
	        	 state = rs.getInt("result");
	         }
	         return state;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	}
	
	/* 전체 팀 최종 평가서 가져오기 -> 보류 */
	public List<EvalFinal> selectAllFinal(Connection conn, String year) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<EvalFinal> list = new ArrayList<EvalFinal>();
	      String str = "'" + year + "%'";
	      
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from efinal where finalNo like "+str);
	         rs = pstmt.executeQuery();
	         EvalFinal EvalFinal = null;
	         String finalNo = null;
	         
	         while(rs.next()) {
	        	 finalNo = rs.getString("finalNo");
	        	 EvalFinal = new EvalFinal(finalNo, rs.getString("comment"),
		        		 toDate(rs.getTimestamp("regDate")),
	        			 toDate(rs.getTimestamp("endDate")),
	        			 rs.getDouble("average"),
	        			 rs.getInt("state"), rs.getInt("result"));
	        	 list.add(EvalFinal);
	         }
	         return list;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
	public List<String> selectNotCompleteFinal(Connection conn, String year) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<String> list = new ArrayList<String>();
	      String str = "'" + year + "%'";
	      
	      try {
	         pstmt = conn.prepareStatement(
	               "select finalNo from efinal where finalNo like "+str +" and state = ?");
	         pstmt.setInt(1, AllEvalStatusValue.getEfinalEvalEnded());
	         rs = pstmt.executeQuery();
	         String finalNo = null;
	         
	         while(rs.next()) {
	        	 finalNo = rs.getString("finalNo");
	        	 list.add(finalNo);
	         }
	         return list;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
	
	   private Date toDate(Timestamp date) {
	      return date == null ? null : new Date(date.getTime());
	   }
	   
	   private Timestamp toTimestamp(Date date) {
			return new Timestamp(date.getTime());
		}
	   
	   
	   public void insert(Connection conn, String finalNo) throws SQLException {
		   Date date = new Date();
		   try (
	    	 PreparedStatement pstmt = 
	         	conn.prepareStatement("insert into efinal(finalNo, comment, regDate, endDate, average, state, result) values(?,?,?,null,?,?,?)")){ 
	         pstmt.setString(1,  finalNo);
	         pstmt.setString(2,  AllEvalStatusValue.getDefaultComment());
	         pstmt.setTimestamp(3, toTimestamp(date));	//이거 맞는지 모르겠네
	         //pstmt.setTimestamp(4,  toTimestamp(date));
	         pstmt.setDouble(4,  AllEvalStatusValue.getDefaultAvg());
	         pstmt.setInt(5,  AllEvalStatusValue.getDefaultEfinalState());
	         pstmt.setInt(6,  AllEvalStatusValue.getDefaultResult());
	         pstmt.executeUpdate();
	      }	
	   }

	   public void insert(Connection conn, EvalFinal EvalFinal) throws SQLException {
		   try (
	    	 PreparedStatement pstmt = 
	         	conn.prepareStatement("insert into efinal(finalNo, comment, regDate, endDate, average, state, result) values(?,?,?,null,?,?,?)")){ 
	         pstmt.setString(1,  EvalFinal.getFinalNo());
	         pstmt.setString(2,  EvalFinal.getComment());
	         pstmt.setTimestamp(3,  new Timestamp(EvalFinal.getRegDate().getTime()));
	         //pstmt.setTimestamp(4,  new Timestamp(EvalFinal.getEndDate().getTime()));
	         pstmt.setDouble(4,  EvalFinal.getAvg());
	         pstmt.setInt(5,  EvalFinal.getState());
	         pstmt.setInt(6,  EvalFinal.getResult());
	         pstmt.executeUpdate();
	      }	
	   }
	 
	   /* 평가 변경 메소드 */
	   public void update(Connection conn, EvalFinal evalfinal) throws SQLException {
		   try (PreparedStatement pstmt = conn.prepareStatement(
	    		  "update efinal set state = ?, endDate = ?, average = ?, result = ?, comment = ? where finalNo = ?")) {
	         pstmt.setInt(1, evalfinal.getState());
	         pstmt.setTimestamp(2, new Timestamp(evalfinal.getEndDate().getTime()));
	         pstmt.setDouble(3, evalfinal.getAvg());
	         pstmt.setInt(4, evalfinal.getResult());
	         pstmt.setString(5, evalfinal.getComment());
	         pstmt.setString(6, evalfinal.getFinalNo());
	         pstmt.executeUpdate();
	      }
	   }
	  
	   /* 평가 삭제 메소드 */
	   public EvalFinal deleteByfinalNo(Connection conn, String finalNo) throws SQLException {
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				EvalFinal rm_eval_paper = selectEvalFinal(conn, finalNo);
				if(rm_eval_paper == null) {
					//평가가 찾아지지 않습니다. 라는 오류 함수로 넘어가게끔. 수정하자
					throw new ArticleNotFoundException();
				}
				
				pstmt = conn.prepareStatement("delete from efinal where finalNo = ? ");
				pstmt.setString(1, finalNo);
				int insertedCount = pstmt.executeUpdate();		//리턴값은 성골했을 때 : 성공한 행의 개수, 실패시 : 0
				
				if(insertedCount>0) {
						return rm_eval_paper;
				}
				return null;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
				JdbcUtil.close(pstmt);
			}
		}
}
