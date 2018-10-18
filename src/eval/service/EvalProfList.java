package eval.service;

import java.util.List;

public class EvalProfList {
	private int total;
	private List<ShowProf> list;	//코드 재사용이 안됨.... 공지사항과 따로??

	public EvalProfList(int total, List<ShowProf> list) {
		this.total = total;
		this.list = list;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ShowProf> getList() {
		return list;
	}

	public void setList(List<ShowProf> list) {
		this.list = list;
	}
}
