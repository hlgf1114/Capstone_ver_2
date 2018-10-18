package team.service;

import java.util.List;

import team.model.TeamList;

public class ProListTeamRequest {

	private int total;
	private List<TeamList> list;	//코드 재사용이 안됨.... 공지사항과 따로??

	public ProListTeamRequest(int total, List<TeamList> list) {
		this.total = total;
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<TeamList> getList() {
		return list;
	}

	public void setList(List<TeamList> list) {
		this.list = list;
	}
	
}