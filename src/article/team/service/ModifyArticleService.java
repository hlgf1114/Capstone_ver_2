package article.team.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.common.ArticleNotFoundException;
import article.common.PermissionDeniedException;
import article.team.dao.*;
import article.team.model.TeamArticle;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ModifyArticleService {

	private TeamArticleDao articleDao = new TeamArticleDao();
	private TeamContentDao contentDao = new TeamContentDao();
	
	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			/*파일 고유 이름으로 불러오기 -> 파일 번호 */
			TeamArticle article = articleDao.selectById(conn, 
					modReq.getFileNo());
			if (article == null) {
				throw new ArticleNotFoundException();
			}
			/* 이 부분 수정할 필요가 있음. 파일 수정 방식의 문제 */
			if (!canModify(modReq.getWriter().getWriterId(), article)) {
				throw new PermissionDeniedException();
			}
			articleDao.update(conn, 
					modReq.getFileNo(), modReq.getTitle());
			contentDao.update(conn, 
					modReq.getFileNo(), modReq.getOrigin(),modReq.getStored(), modReq.getFileSize(), modReq.getFileExt());
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

	private boolean canModify(String modfyingUserId, TeamArticle article) {
		String temp = article.getWriter().getWriterId();
		return temp.equals(modfyingUserId);
	}

}