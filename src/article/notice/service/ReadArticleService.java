package article.notice.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.common.ArticleNotFoundException;
import article.common.ArticleContentNotFoundException;
import article.notice.dao.NoticeContentDao;
import article.notice.dao.NoticeDao;
import article.notice.model.Notice;
import article.notice.model.NoticeContent;
import jdbc.connection.ConnectionProvider;

public class ReadArticleService {

	private NoticeDao noticeDao = new NoticeDao();
	private NoticeContentDao contentDao = new NoticeContentDao();
	
	public NoticeData getNotice(int postNo, boolean increaseReadCount) {
		try (Connection conn = ConnectionProvider.getConnection()){
			Notice notice = noticeDao.selectById(conn, postNo);
			if (notice == null) {
				throw new ArticleNotFoundException();
			}
			NoticeContent content = contentDao.selectById(conn, postNo);
			if (content == null) {
				throw new ArticleContentNotFoundException();
			}
			if (increaseReadCount) {
				noticeDao.increaseReadCount(conn, postNo);
			}
			return new NoticeData(notice, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
