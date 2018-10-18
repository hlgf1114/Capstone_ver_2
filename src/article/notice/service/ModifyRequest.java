package article.notice.service;

import java.util.Map;

import article.notice.model.Writer;

public class ModifyRequest {

	private Writer writer;
	private int postNo;
	private String title;
	private String content;
	private String origin;
	private String stored;
	private long fileSize;
	private String fileExt;
	
    public ModifyRequest(int postNo, Writer writer, String title, String content,
         String origin, String stored, long fileSize, String fileExt) {
	  this.postNo = postNo;
	  this.writer = writer;
	  this.title = title;
      this.content = content;
      this.origin = origin;
      this.stored = stored;
      this.fileSize = fileSize;
      this.fileExt = fileExt;
    }

   public String getContent() {
      return content;
   }
   
   public Writer getWriter() {
      return writer;
   }

   public Integer getPostNo() {
      return postNo;
   }

   public String getOrigin() {
      return origin;
   }

   public String getStored() {
      return stored;
   }

   public long getFileSize() {
      return fileSize;
   }

   public String getFileExt() {
      return fileExt;
   }

   public String getTitle() {
      return title;
   }

   public void validate(Map<String, Boolean> errors) {
      if (title == null || title.trim().isEmpty()) {
         errors.put("title", Boolean.TRUE);
      }
   }
}
