package auth.service;

public class Member {
	
	private String teamNo;
	private boolean state;
	private String teamName;
	private String groupNo;
	
	public Member(String teamNo, boolean state, String teamName, String groupNo)
	{
		this.teamNo = teamNo;
		this.state = state;
		this.teamName = teamName;
		this.groupNo = groupNo;
	}
	
	public String getTeamNo() {
		return teamNo;
	}
	
	public boolean getState() {
		return state;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public String getGroupNo() {
		return groupNo;
	}
}
