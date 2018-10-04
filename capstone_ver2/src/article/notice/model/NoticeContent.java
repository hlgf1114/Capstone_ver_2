package article.notice.model;

public class NoticeContent {

   private Integer postNo;
   private String content;
   private String origin;
   private String stored;
   private long fileSize;
   private String fileExt;

   public NoticeContent(Integer postNo, String content, String origin, String stored,
         long size, String ext) {
      this.postNo = postNo;
      this.content = content;
      this.origin = origin;
      this.stored = stored;
      this.fileSize = size;
      this.fileExt = ext;
   }

   public Integer getPostNo() {
      return postNo;
   }

   public String getContent() {
      return content;
   }

   public String getOrigin() {
      return origin;
   }

   public String getStored() {
      return stored;
   }

   public Long getFileSize() {
      return fileSize;
   }

   public String getFileExt() {
      return fileExt;
   }
}