package eval.model;


public class EvalTeam {
	
	private String teamNo;
	private String finalNo;
	
	public EvalTeam() {
		teamNo = null;
		finalNo = null;
	}
	public EvalTeam(String t, String f) {
		teamNo = t;
		finalNo = f;
	}
	
	public String getTeamNo() {
		return teamNo;
	}
	
	public void setTeamNo(String t) {
		this.teamNo = t;
	}
	public String getFinalNo() {
		return finalNo;
	}
	
	public void setFinalNo(String f) {
		this.finalNo = f;
	}
}