package article.notice.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.common.ArticleContentNotFoundException;
import article.common.ArticleNotFoundException;

import article.notice.model.*;
import article.notice.dao.*;

import jdbc.connection.ConnectionProvider;

public class DeleteArticleService {
	
	private NoticeDao noticeDao = new NoticeDao();
	private NoticeContentDao contentDao = new NoticeContentDao();
	
	public NoticeData deleteArticle(int postNo) {
		try (Connection conn = ConnectionProvider.getConnection()){
			
			NoticeContent content = contentDao.deleteByPostNo(conn, postNo);
			if (content == null) {
				throw new ArticleContentNotFoundException();
			}
			
			Notice notice = noticeDao.deleteByPostNo(conn, postNo);
			if (notice == null) {
				throw new ArticleNotFoundException();
			}
			
			return new NoticeData(notice, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
