package eval.service;

public class ShowTeam {
	private String teamNo;
	private String teamName;
	
	public ShowTeam(){
		teamNo = null;
		teamName = null;
	}
	
	public ShowTeam(String tn, String tnn){
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
