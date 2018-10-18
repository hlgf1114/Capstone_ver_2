package article.team.model;

import java.util.Date;

public class TeamArticle {

	private String fileNo;				//파일 고유 번호
	private String title;				//글 제목
	private TeamArticleWriter writer;	//작성자(팀번호, 학번)
	private Date regDate;				//등록일자
	private Date modifiedDate;			//수정일자
	private int downCount;				//조회수
	
	public TeamArticle(String fileNo, String title, TeamArticleWriter writer, 
			Date regDate, Date modifiedDate, int downCount) {
		this.fileNo = fileNo;
		this.title = title;
		this.writer = writer;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
		this.downCount = downCount;
	}

	public String getFileNo() {
		return fileNo;
	}
	
	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public TeamArticleWriter getWriter() {
		return writer;
	}
	

	public Date getRegDate() {
		return regDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public int getDownCount() {
		return downCount;
	}
}
