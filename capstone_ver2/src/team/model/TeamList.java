package team.model;

public class TeamList {
	private String teamNo;
	private String teamName;
	
	public TeamList(){
		teamNo = null;
		teamName = null;
	}
	
	public TeamList(String tn, String tnn){
		teamNo = tn;
		teamName = tnn;
	}
	
	public String getTeamNo() {
		return teamNo;
	}
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
