package auth.service;

public class Authority {
	private final static int ADMIN = 100;			//관리자
	private final static int PRO_DEAN = 5;			//학과장
	private final static int PRO_NOT_EVAL=10;		//평가 참여 X 교수
	private final static int PRO_EVAL=20;			//평가 참여 O 교수
	private final static int STU_NOT_TEAM =30;		//팀 없는 학생
	private final static int STU_TEAM=40;			//팀 있는 학생
	private final static int STU_TEAM_MAKER = 50;	//팀장
	private final static int DEFAULT = 90;
	
	public static int getAdmin() {
		return ADMIN;
	}
	public static int getProDean() {
		return PRO_DEAN;
	}
	public static int getProNotEval() {
		return PRO_NOT_EVAL;
	}
	public static int getProEval() {
		return PRO_EVAL;
	}
	public static int getStuNotTeam() {
		return STU_NOT_TEAM;
	}
	public static int getStuTeam() {
		return STU_TEAM;
	}
	public static int getStuTeamMaker() {
		return STU_TEAM_MAKER;
	}
	public static int getDefault() {
		return DEFAULT;
	}
}