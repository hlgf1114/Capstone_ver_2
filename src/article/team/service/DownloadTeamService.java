package article.team.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.team.dao.TeamContentDao;
import article.team.model.TeamContent;
import jdbc.connection.ConnectionProvider;



public class DownloadTeamService {

private TeamContentDao contentDao = new TeamContentDao();
	
	public TeamContent selectTeam(String fileNo) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			if(fileNo != null) {	
			TeamContent content = contentDao.selectById(conn, fileNo);
				if (content == null) {
					return null;
				}else {
					return content;
				}					
			}else {
				return null;
			}						
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
} 