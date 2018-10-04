package article.notice.dao;

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
import article.notice.model.Notice;
import article.notice.model.Writer;
import jdbc.JdbcUtil;

public class NoticeDao {

   public Notice insert(Connection conn, Notice notice) throws SQLException {
      PreparedStatement pstmt = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
    	 //autoincrement는 안넣어줘도 되나봄
         pstmt = conn.prepareStatement("insert into notice "
               + "(postTitle, writeId, writeName, regDate, modDate, readCnt) "
               + "values (?,?,?,?,?,0)");
         pstmt.setString(1, notice.getTitle());
         pstmt.setString(2, notice.getWriter().getId());
         pstmt.setString(3, notice.getWriter().getName());
         pstmt.setTimestamp(4, toTimestamp(notice.getRegDate()));
         pstmt.setTimestamp(5, toTimestamp(notice.getModDate()));
         int insertedCount = pstmt.executeUpdate();

         if (insertedCount > 0) {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select last_insert_id() from notice");
            if (rs.next()) {
               Integer newNo = rs.getInt(1);
               return new Notice(newNo,
                     notice.getWriter(),
                     notice.getTitle(),
                     notice.getRegDate(),
                     notice.getModDate(),
                     0);
            }
         }
         return null;
      } finally {
         JdbcUtil.close(rs);
         JdbcUtil.close(stmt);
         JdbcUtil.close(pstmt);
      }
   }

   private Timestamp toTimestamp(Date date) {
      return new Timestamp(date.getTime());
   }

   public int selectCount(Connection conn) throws SQLException {
      Statement stmt = null;
      ResultSet rs = null;
      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery("select count(*) from notice");
         if (rs.next()) {
            return rs.getInt(1);
         }
         return 0;
      } finally {
         JdbcUtil.close(rs);
         JdbcUtil.close(stmt);
      }
   }

   public List<Notice> select(Connection conn, int startRow, int size) throws SQLException {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         pstmt = conn.prepareStatement("select * from notice " +
               "order by regDate desc limit ?, ?");
         pstmt.setInt(1, startRow);
         pstmt.setInt(2, size);
         rs = pstmt.executeQuery();
         List<Notice> result = new ArrayList<>();
         while (rs.next()) {
            result.add(convertArticle(rs));
         }
         return result;
      } finally {
         JdbcUtil.close(rs);
         JdbcUtil.close(pstmt);
      }
   }

   private Notice convertArticle(ResultSet rs) throws SQLException {
      return new Notice(rs.getInt("postNo"),
            new Writer(
                  rs.getString("writeId"),
                  rs.getString("writeName")),
            rs.getString("postTitle"),
            toDate(rs.getTimestamp("regDate")),
            toDate(rs.getTimestamp("modDate")),
            rs.getInt("readCnt"));
   }

   private Date toDate(Timestamp timestamp) {
      return new Date(timestamp.getTime());
   }
   
   public Notice selectById(Connection conn, int no) throws SQLException {
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         pstmt = conn.prepareStatement(
               "select * from notice where postNo = ?");
         pstmt.setInt(1, no);
         rs = pstmt.executeQuery();
         Notice article = null;
         if (rs.next()) {
            article = convertArticle(rs);
         }
         return article;
      } finally {
         JdbcUtil.close(rs);
         JdbcUtil.close(pstmt);
      }
   }
   
   public void increaseReadCount(Connection conn, int no) throws SQLException {
      try (PreparedStatement pstmt = 
            conn.prepareStatement(
                  "update notice set readCnt = readCnt + 1 "+
                  "where postNo = ?")) {
         pstmt.setInt(1, no);
         pstmt.executeUpdate();
      }
   }
   
   public int update(Connection conn, int no, String title) throws SQLException {
      try (PreparedStatement pstmt = 
            conn.prepareStatement(
                  "update notice set postTitle = ?, modDate = now() "+
                  "where postNo = ?")) {
         pstmt.setString(1, title);
         pstmt.setInt(2, no);
         return pstmt.executeUpdate();
      }
   }
   public Notice deleteByPostNo(Connection conn, int postNo) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//int PostNo = Integer.parseInt(postNo);
		
		try {
			Notice notice = selectById(conn, postNo);
			if(notice == null) {
				throw new ArticleNotFoundException();
			}
			
			pstmt = conn.prepareStatement("delete from notice where postNo = ? ");
			pstmt.setInt(1, postNo);
			int insertedCount = pstmt.executeUpdate();		//리턴값은 성골했을 때 : 성공한 행의 개수, 실패시 : 0
			
			if(insertedCount>0) {
					return notice;
			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}
   
}