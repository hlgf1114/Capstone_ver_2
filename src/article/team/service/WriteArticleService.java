package article.team.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import article.team.dao.*;
import article.team.model.*;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteArticleService {

	private TeamArticleDao articleDao = new TeamArticleDao();
	private TeamContentDao contentDao = new TeamContentDao();
	
	public String write(WriteRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			TeamArticle article = toTeamArticle(req);
			TeamArticle savedArticle = articleDao.insert(conn, article);
			if (savedArticle == null) {
				throw new RuntimeException("fail to insert teamboard");
			}

			TeamContent content = new TeamContent(
					savedArticle.getFileNo(),
					req.getOrigin(),
					req.getStored(),
					req.getFileSize(),
					req.getFileExt());
			TeamContent savedContent = contentDao.insert(conn, content);
			if (savedContent == null) {
				throw new RuntimeException("fail to insert writeteam");
			}
			
			conn.commit();

			return savedArticle.getFileNo();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
	private TeamArticle toTeamArticle(WriteRequest req) {
		Date now = new Date();
		return new TeamArticle(toGetFileNo(req), req.getTitle(), req.getWriter(), now, now, 0);
	}
	//이거 구현해야함.
	
	private String toGetFileNo(WriteRequest req) {
		DecimalFormat df = new DecimalFormat("00"); // 연도 구하기위한 포맷 형식 지정
		Calendar currentCalendar = Calendar.getInstance();
		String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
		String strMin = Integer.toString(currentCalendar.get(Calendar.MINUTE));
		String strSecd = Integer.toString(currentCalendar.get(Calendar.SECOND));
		String strDate = strYear + strMin + strSecd;
		
		strDate = strDate.substring(2,7);
		
		String fileNo = req.getWriter().getTeamNo()+ "_" + strDate +"_"+req.getFiletype() ;
		return fileNo;
	}
}
