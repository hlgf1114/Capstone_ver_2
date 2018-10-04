package eval.service;

import java.util.List;

public class EvalTeamList {

	private int total;					//평가 받는 팀 총 수
	private List<ShowTeam> list;	//코드 재사용이 안됨.... 공지사항과 따로??

	public EvalTeamList(int total, List<ShowTeam> list) {
		this.total = total;
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ShowTeam> getList() {
		return list;
	}

	public void setList(List<ShowTeam> list) {
		this.list = list;
	}
}