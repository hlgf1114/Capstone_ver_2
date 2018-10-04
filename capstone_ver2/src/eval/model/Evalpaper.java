package eval.model;

import java.util.Date;

import eval.service.AllEvalStatusValue;

/* 팀별 개별 교수님 평가서 */
public class Evalpaper {

	private String paperNo;
	private Questions qs;					
	private Date regDate;
	private Date endDate;
	private int total;
	private int State;		//평가 미시작, 시작, 완료에 대한 상태 변수
	
	public Evalpaper() {
		paperNo = null;
		qs = new Questions();
		regDate = null;
		endDate = null;
		total = 0;
		State = AllEvalStatusValue.getDefaultEpaperState();
	}
	
	public Evalpaper(String pn, Questions q, Date reg, Date end, int tt, int r) {
		paperNo = pn;
		qs = q;
		regDate = reg;
		endDate = end;
		total = tt;
		State = r;
	}
	
	public Evalpaper(String pn, Questions q, Date reg, Date end, int st) {
		paperNo = pn;
		qs = q;
		regDate = reg;
		endDate = end;
		total = countTotal();
		State = st;
	}
	
	private int countTotal() {
		int total = 0;
		for(int i = 0; i < 7 ; i++) {
			total += qs.getQsItemScore(i);
		}
		return total;
	}
	
	/* 개별 교수님 평가서 번호 */
	public String makePaperNo(String eNo, String tNo, String pId) {
		return eNo+"-"+tNo+"-"+pId;
	}

	public String getPaperNo() {
		return paperNo;
	}

	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}

	public Questions getQs() {
		return qs;
	}

	public void setQs(Questions qs) {
		this.qs = qs;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}
}
