package eval.service;

import java.util.ArrayList;
import java.util.List;

import eval.model.Evalpaper;

/* 개별 평가지 7개 */
public class EvalPaperList {
	
	private String teamNo;
	private List<Evalpaper> list;
	private int totalscore;
	private double avg;
	
	public EvalPaperList() {
		teamNo= null;
		list = new ArrayList<Evalpaper>();
		totalscore = 0;
		avg = 0.0;
	}
	
	public EvalPaperList(String tn, List<Evalpaper> el) {
		teamNo = tn;
		list = el;
		countTotalAndAverage();
	}
	
	private void countTotalAndAverage() {
		int cnt = 0;
		for(Evalpaper var:list) {
			if(var.getState()==AllEvalStatusValue.getEpaperEvalEnded()) {
				totalscore += var.getTotal();
				cnt++;
			}
		}
		avg = (double)(totalscore/(double)cnt);
	}

	public List<Evalpaper> getList() {
		return list;
	}

	public void setList(List<Evalpaper> list) {
		this.list = list;
	}

	public int getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(int totalscore) {
		this.totalscore = totalscore;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public String getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}	
}
