package article.team.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import article.team.dao.TeamArticleDao;
import article.team.model.TeamArticle;
import article.team.model.TeamArticleWriter;
import jdbc.connection.ConnectionProvider;

public class ListArticleService {

	private TeamArticleDao articleDao = new TeamArticleDao();
	private int size = 100;

	public ArticlePage getArticlePage(int pageNum, String teamNo, String filetype) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			int total = articleDao.selectCount(conn, teamNo);
			List<TeamArticle> content = null;
			List<String> Name = new ArrayList<>();
			if(teamNo == null) {
				return null;
			}
			
			if(filetype.equals("00")) {
				content = articleDao.select(
						conn, (pageNum - 1) * size, size, teamNo);
			}
			else {
				content = articleDao.selectByFiletype(
						conn, (pageNum - 1) * size, size, teamNo, filetype);
			}
			
			for(int i = 0; i < content.size(); i++)
			{
				TeamArticleWriter articleWriter = content.get(i).getWriter();
				Name.add(getStuName(articleWriter.getWriterId()));
			}
			
		
			return new ArticlePage(total, pageNum, size, content, Name);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getStuName(String writeId) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			String stuName;
			
			if(writeId == null) {
				return null;
			}
			stuName = articleDao.selectNamebyWriterId(conn, writeId);			
			return stuName;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
