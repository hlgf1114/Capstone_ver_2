package article.team.service;

import article.team.model.*;

public class TeamWriteData {

	private TeamArticle article;
	private TeamContent content;

	public TeamWriteData(TeamArticle article, TeamContent content) {
		this.article = article;
		this.content = content;
	}
	public TeamArticle getArticle() {
		return article;
	}
	public String getContent() {
		return content.getOrigin();
	}
	public String getOrigin() {
		return content.getOrigin();
	}
	public String getStored() {
		return content.getOrigin();
	}
	public long getFileSize() {
		return content.getFileSize();
	}
	public String getFileType() {
		return content.getFileExt();
	}
	
}
