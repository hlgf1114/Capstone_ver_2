package article.notice.service;

import article.notice.model.*;

public class NoticeData {

	private Notice notice;
	private NoticeContent noticecontent;
	private String content;
	private String file;
	
	
	public NoticeData(Notice notice, NoticeContent ncontent) {
		this.notice = notice;
		this.noticecontent = ncontent;
		this.content = ncontent.getContent();
		this.file = ncontent.getOrigin();
	}

	public Notice getNotice() {
		return notice;
	}
	public String getNoticeContent() {
		return noticecontent.getContent();
	}
	//이거랑 밑에꺼 왜 둘다 있는지 모르겠네...
	public String getOrigin() {
		return noticecontent.getOrigin();
	}
	public String getStored() {
		return noticecontent.getOrigin();
	}
	public long getFileSize() {
		return noticecontent.getFileSize();
	}
	public String getFileType() {
		return noticecontent.getFileExt();
	}
	public String getContent() {
		return this.content;
	}
	public String getFile() {
		return this.file;
	}

}
