package article.notice.service;

import java.sql.Connection;
import java.sql.SQLException;
import article.common.ArticleNotFoundException;
import article.common.PermissionDeniedException;
import article.notice.dao.NoticeContentDao;
import article.notice.dao.NoticeDao;
import article.notice.model.Notice;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ModifyArticleService {

	private NoticeDao noticeDao = new NoticeDao();
	private NoticeContentDao contentDao = new NoticeContentDao();

	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Notice notice = noticeDao.selectById(conn, 
					modReq.getPostNo());
			if (notice == null) {
				throw new ArticleNotFoundException();
			}
			if (!canModify(modReq.getWriter().getId(), notice)) {
				throw new PermissionDeniedException();
			}
			noticeDao.update(conn, 
					modReq.getPostNo(), modReq.getTitle());
			contentDao.update(conn, 
					modReq.getPostNo(), modReq.getContent(), modReq.getOrigin(),
					modReq.getStored(), modReq.getFileSize(), modReq.getFileExt());
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private boolean canModify(String modfyingUserId, Notice article) {
		String temp = article.getWriter().getId();
		return temp.equals(modfyingUserId);
	}
}
