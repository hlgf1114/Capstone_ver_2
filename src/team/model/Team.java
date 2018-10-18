package team.model;

import java.util.Date;

/* 팀  */
public class Team {
   private String teamNo;            //팀번호???-> 그룹번호랑 무슨 상관?
   private String teamName;         //팀이름
   private String teamSubject;         //TeamSubject????
   private String advisor;            //지도교수
   private boolean state;            //팀상태
   private String groupNo;         //요일,오전?오후반
   private Date teamJoinDate;         //팀생성일
   
   
   /*디폴트 생성자*/
   public Team() {
	  this.groupNo = null;
	  this.teamNo = null;
      this.teamName = null;
      this.teamSubject = null;
      this.advisor = null;
      this.state = true;      
      this.teamJoinDate = null;
   }
   
   /*생성자*/
   public Team(String teamNum, String teamName, String TeamSubject, String proId, String GroupNumber, 
          boolean state,  Date teamJoinDate) {
	  this.groupNo = GroupNumber;
	  this.teamNo = teamNum;
      this.teamName = teamName;
      this.teamSubject = TeamSubject;
      this.advisor = proId;
      this.state = state;      
      this.teamJoinDate = teamJoinDate;
   }
   
   public String getGroupNo() {
	      return groupNo;
   }
   
   public String getTeamNo() {
      return teamNo;
   }

   public String getTeamName() {
      return teamName;
   }

   public String getTeamSubject() {
      return teamSubject;
   }

   public String getAdvisor() {
      return advisor;
   }

   public boolean isState() {
      return state;
   }
 

   public Date getTeamJoinDate() {
      return teamJoinDate;
   }
   
}