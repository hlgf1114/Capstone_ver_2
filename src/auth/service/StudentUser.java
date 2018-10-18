package auth.service;

public class StudentUser {

	private String id;
	private String name;
	private String teamNo;
	private int access;
	
	public StudentUser(String id, String name, String teamNo, int access) {
		this.id = id;
		this.name = name;
		this.teamNo = teamNo;
		this.access = access;

	}
	
	public StudentUser() {
		this.id = null;
		this.name = null;
		this.teamNo = null;
		this.access = Authority.getDefault();

	}	

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getTeamNo() {
		return teamNo;
	}
	
	public int getAccess() {
		return access;
	}
	
}
