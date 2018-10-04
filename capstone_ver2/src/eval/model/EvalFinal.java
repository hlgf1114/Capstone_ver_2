package eval.model;

import java.util.Date;

import eval.service.AllEvalStatusValue;

public class EvalFinal {
	
	private String finalNo;
	private String comment;
	private Date regDate;
	private Date endDate;
	private double avg;
	private int state;
	private int result;
	
	public EvalFinal() {
		finalNo = null;
		comment = null;
		regDate = null;
		endDate = null;
		avg = AllEvalStatusValue.getDefaultAvg();
		state = AllEvalStatusValue.getDefaultEfinalState();
		result = AllEvalStatusValue.getDefaultResult();
	}
	
	/* 모든 멤버변수 받는 생성자 */
	public EvalFinal(String fno, String c, Date r, Date e, double t, int s, int rs) {
		finalNo = fno;
		comment = c;
		regDate = r;
		endDate = e;
		avg = t;
		state = s;
		result = rs;
	}
	
	/* 평가 계획시 생성되는 최종 평가서 */
	public EvalFinal(String fno) {
		finalNo = fno;
		comment = null;
		regDate = null;
		endDate = null;
		avg = AllEvalStatusValue.getDefaultAvg();
		state = AllEvalStatusValue.getDefaultEfinalState();
		result = AllEvalStatusValue.getDefaultResult();
	}

	public String getFinalNo() {
		return finalNo;
	}

	public void setFinalNo(String finalNo) {
		this.finalNo = finalNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
}
