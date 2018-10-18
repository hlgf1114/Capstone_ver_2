package eval.service;

public class EpaperResult {
	
	private String epaperNo;
	private String evalstatus;
	private String proName;
	private String proId;
	private String result;
	private int total;
	
	public EpaperResult(String epno, int status, String proname, String proid, int total) {	
		epaperNo = epno;
		if(status==AllEvalStatusValue.getDefaultEvalPlanState()) {
			evalstatus="미시작";
		}else if(status == AllEvalStatusValue.getEvalPlanStarted()) {
			evalstatus="진행중";
		}else if(status == AllEvalStatusValue.getEvalPlanEnded()) {
			evalstatus="완료";
		}
		else {
			evalstatus="알수 없음";
		}
		proName=proname;
		proId = proid;
		this.total = total;
		if(total>AllEvalStatusValue.getPass()) {
			result = "합격";
		}else if(total>AllEvalStatusValue.getReTest()) {
			result = "재심사";
		}else {
			result = "불합격";
		}
	}

	public String getEpaperNo() {
		return epaperNo;
	}

	public void setEpaperNo(String epaperNo) {
		this.epaperNo = epaperNo;
	}

	public String getEvalstatus() {
		return evalstatus;
	}

	public void setEvalstatus(String evalstatus) {
		this.evalstatus = evalstatus;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
