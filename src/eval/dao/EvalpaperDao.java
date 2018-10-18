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
import eval.model.Evalpaper;
import jdbc.JdbcUtil;
import eval.model.Questions;
import eval.service.AllEvalStatusValue;
//import java.util.StringTokenizer;

public class EvalpaperDao {
	
	/* 개별 교수님 평가서 번호 */
	public String makeEpaperNo(String tNo, String pId) {
		return tNo+"_"+pId;
	}
	
	public Evalpaper selectEvalPaper(Connection conn, String paperNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from epaper where paperNo = ?");
	         pstmt.setString(1, paperNo);
	         rs = pstmt.executeQuery();
	         Evalpaper evalpaper = null;
	         
	         if(rs.next()) {
	        	 evalpaper = new Evalpaper(paperNo, selectQuestions(conn, paperNo),
		        		 toDate(rs.getTimestamp("regDate")),
	        			 toDate(rs.getTimestamp("endDate")),
	        			 rs.getInt("total"),
	        			 rs.getInt("state")); 
	         }
	         return evalpaper;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
	
	public int selectState(Connection conn, String paperNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	         pstmt = conn.prepareStatement(
	               "select state from epaper where paperNo = ?");
	         pstmt.setString(1, paperNo);
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
	
	public List<Evalpaper> selectEvalTeamAllPaper(Connection conn, String teamNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      List<Evalpaper> list = new ArrayList<Evalpaper>();
	      String str = "'" + teamNo + "%'";
	      
	      try {
	         pstmt = conn.prepareStatement(
	               "select * from epaper where paperNo like "+str);
	         rs = pstmt.executeQuery();
	         Evalpaper evalpaper = null;
	         String paperNo = null;
	         
	         while(rs.next()) {
	        	 paperNo = rs.getString("paperNo");
	        	 evalpaper = new Evalpaper(paperNo, selectQuestions(conn, paperNo),
		        		 toDate(rs.getTimestamp("regDate")),
	        			 toDate(rs.getTimestamp("endDate")),
	        			 rs.getInt("total"),
	        			 rs.getInt("state"));
	        	 list.add(evalpaper);
	         }
//	         /* 평가지 번호로 불러와 평가지 번호에서 평가 번호, 팀번호, 교수 번호 추출하기 */
//	         StringTokenizer st = new StringTokenizer(paperNo,"-");
//	         evalpaper.setEvalNo(st.nextToken());
//	         evalpaper.setTeam(st.nextToken());
//	         evalpaper.setPro(st.nextToken());
//	         return evalpaper;
	         return list;
	      } finally {
	         JdbcUtil.close(rs);
	         JdbcUtil.close(pstmt);
	      }
	   }
	
	
	public Questions selectQuestions(Connection conn, String paperNo) throws SQLException {
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      try {
	    	  Questions qlist = new Questions();
	    	  	
	    	  /* DB서버와 웹 서버 간의 통신량이 너무 많아 지지 않을까? */
	    	  for(int i = 0; i < Questions.DEFAULT_LIST_NO; i++) {
	    		 pstmt = conn.prepareStatement(
	   	               "select * from epf where paperNo  = ? and qNo = ?");
	   	         pstmt.setString(1, paperNo);
	   	         pstmt.setInt(2, i+1);
	   	         rs = pstmt.executeQuery();
	   	         if(rs.next()) {
	   	        	qlist.setQsItemScore(i, rs.getInt("score"));
	   	        	qlist.setQsItemComment(i, rs.getString("comment"));
	   	         }
	    	  }
	         return qlist;
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
	   
	   
	   public void insert(Connection conn, String paperNo) throws SQLException {
		   Date date = new Date();
		   try (
	    	 PreparedStatement pstmt = 
	         	conn.prepareStatement("insert into epaper(paperNo, state, regDate, endDate, total) values(?,?,?,null,?)")){ 
	         pstmt.setString(1,  paperNo);
	         pstmt.setInt(2,  AllEvalStatusValue.getDefaultEpaperState());
	         pstmt.setTimestamp(3,  toTimestamp(date));
	         pstmt.setInt(4,  0);
	         pstmt.executeUpdate();
	      }	
		   insertToEvalitem(conn,paperNo);
	   }

	   public void insert(Connection conn, Evalpaper evalpaper) throws SQLException {
		   try (
	    	 PreparedStatement pstmt = 
	         	conn.prepareStatement("insert into epaper(paperNo, state, regDate, endDate, total) values(?,?,?,null,?)")){ 
	         pstmt.setString(1,  evalpaper.getPaperNo());
	         pstmt.setInt(2,  evalpaper.getState());
	         pstmt.setTimestamp(3,  new Timestamp(evalpaper.getRegDate().getTime()));
	         pstmt.setTimestamp(4,  new Timestamp(evalpaper.getEndDate().getTime()));
	         pstmt.setInt(5,  evalpaper.getTotal());
	         pstmt.executeUpdate();
	      }	
		   insertToEvalitem(conn,evalpaper);
	   }
	   
	   public void insertToEvalitem(Connection conn, String paperNo) throws SQLException {
		   for(int i = 0; i < Questions.DEFAULT_LIST_NO; i++) {
		   try (PreparedStatement pstmt =		         
		    		  conn.prepareStatement("insert into epf(paperNo, qNo, score, comment) values(?,?,?,null)")) {
		        	 pstmt.setString(1, paperNo);
		        	 pstmt.setInt(2, i+1);
			         pstmt.setInt(3, 0);
			         pstmt.executeUpdate();	 
		     }
		  }
	   }
	   
	   public void insertToEvalitem(Connection conn, Evalpaper evalpaper) throws SQLException {
		   for(int i = 0; i < Questions.DEFAULT_LIST_NO; i++) {
		   try (PreparedStatement pstmt =		         
		    		  conn.prepareStatement("insert into epf(paperNo, qNo, score, comment) values(?,?,?,?)")) {
		        	 pstmt.setString(1, evalpaper.getPaperNo());
		        	 pstmt.setInt(2, i+1);
			         pstmt.setInt(3, evalpaper.getQs().getQsItemScore(i));
			         pstmt.setString(4, evalpaper.getQs().getQsItemComment(i));
			         pstmt.executeUpdate();	 
		     }
		  }
	   }
	   
	   /* 평가 변경 메소드 */
	   public void update(Connection conn, Evalpaper eval) throws SQLException {
		   update_evalitem(conn, eval.getQs(), eval.getPaperNo());
		   try (PreparedStatement pstmt = conn.prepareStatement(
	    		  "update epaper set state = ?, endDate = ?, total = ? where paperNo = ?")) {
	         pstmt.setInt(1, eval.getState());
	         pstmt.setTimestamp(2, new Timestamp(eval.getEndDate().getTime()));
	         pstmt.setInt(3, eval.getTotal());
	         pstmt.setString(4, eval.getPaperNo());
	         pstmt.executeUpdate();
	      }
	   }
	   
	   /* 평가 완료 메소드 */
	   public void update_complete(Connection conn, String paperNo) throws SQLException {
		   try (PreparedStatement pstmt = conn.prepareStatement(
	    		  "update epaper set state = ? where paperNo = ?")) {
	         pstmt.setInt(1, AllEvalStatusValue.getEpaperEvalEnded());
	         pstmt.setString(2, paperNo);
	         pstmt.executeUpdate();
	      }
	   }
	   
	   /* 평가 항목 변경 메소드 */
	   public void update_evalitem(Connection conn, Questions qs, String paperNo) throws SQLException {
		   String paperno = paperNo;
		   /* 이게 될지 안될지 모르겠네 */
		   try (PreparedStatement pstmt = conn.prepareStatement(
		    		  "update epf set score = ?, comment = ? where paperNo = ? and qNo = ?")) {
		       for(int i = 0 ; i < 7 ; i ++) {
		    	   pstmt.setInt(1, qs.getQsItemScore(i));
			         pstmt.setString(2, qs.getQsItemComment(i));
			         pstmt.setString(3, paperno);
			         pstmt.setInt(4, i+1);
			         pstmt.executeUpdate();   
		       }
		   }
	   }
	   /* 평가 삭제 메소드 */
	   public Evalpaper deleteBypaperNo(Connection conn, String paperNo) throws SQLException {
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Evalpaper rm_eval_paper = selectEvalPaper(conn, paperNo);
				if(rm_eval_paper == null) {
					//평가가 찾아지지 않습니다. 라는 오류 함수로 넘어가게끔. 수정하자
					throw new ArticleNotFoundException();
				}
				
				deleteQlistBypaperNo(conn, paperNo);
				
				pstmt = conn.prepareStatement("delete from epaper where paperNo = ? ");
				pstmt.setString(1, paperNo);
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
	   public Questions deleteQlistBypaperNo(Connection conn, String paperNo) throws SQLException {
			PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				Questions rm_qlist = selectQuestions(conn, paperNo);
				if(rm_qlist == null) {
					//평가가 찾아지지 않습니다. 라는 오류 함수로 넘어가게끔. 수정하자
					throw new ArticleNotFoundException();
				}
				
				pstmt = conn.prepareStatement("delete from epf where paperNo = ? ");
				pstmt.setString(1, paperNo);
				int insertedCount = pstmt.executeUpdate();		//리턴값은 성골했을 때 : 성공한 행의 개수, 실패시 : 0
				/* 이 부분 어떻게 될지 잘 모르겠음 */
				if(insertedCount>0) {
						return rm_qlist;
				}
				return null;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
				JdbcUtil.close(pstmt);
			}
		}
	   public double CntEteamAverage(Connection conn, String eteamNo) throws SQLException {
		   	PreparedStatement pstmt = null;
			Statement stmt = null;
			ResultSet rs = null;
			String likeword="'"+eteamNo+"%'";
			double average = 0.0;
			try {
			    pstmt = conn.prepareStatement("select avg(total) from epaper " +
			               "where paperNo like "+ likeword +
			        		 "and total != 0");
			    rs = pstmt.executeQuery();
				/* 이 부분 어떻게 될지 잘 모르겠음 */
				if(rs.next()) {
					average=rs.getDouble("avg(total)");
					return average;
				}
				return 0.0;
			} finally {
				JdbcUtil.close(rs);
				JdbcUtil.close(stmt);
				JdbcUtil.close(pstmt);
			}
	   }

}
