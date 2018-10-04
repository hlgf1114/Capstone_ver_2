package eval.service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import eval.model.Evalplan;
import member.dao.ProfessorDao;
import member.model.Professor;


/* 평가 계획서 */
public class MakeRequest {
	
	//final private String DEFAULT_EVAL_NO= "2018-01";			//2018년 5월 20일 4학년	
	
	private String evalNo;
	private String dean;
	private List<String> pflist; 		//평가 참여 교수 리스트
	//private ArrayList<Eteam> eteamlist;		//평가 참여 팀 리스트 - 참여 1팀당 팀 넘버, 교수님 개인 평가지 arraylist, 최종 평가지 번호
	private List<String> tlist;
	private List<String> epaperlist;
	private List<String> efinallist;
	
	public MakeRequest() {
		evalNo = null;
		dean = null;
		pflist = new ArrayList<String>();
		tlist = new ArrayList<String>();
		epaperlist = new ArrayList<String>();
		efinallist = new ArrayList<String>();
	}
	/* 평가 계획서, 평가 교수 리스트, 평가 팀 리스트 완성 생성자 */
	public MakeRequest(String d, List<ShowProf> pl,
			List<ShowTeam> tl) {
		evalNo = MakeEvalNo();
		dean = d;
		pflist = getProIdList(pl);
		tlist = getTeamList(tl);
		epaperlist = MakeEpaperList();
		efinallist = MakeFinalList();
//		regDate = new Date(date.getTime());
//		endDate = null;
	}
	
//	/* 이 생성자 굳이 필요할거 같지 않음 */
//	public MakeRequest(String e, String d, List<ShowProf> pl, List<ShowTeam> tl) {		
//		evalNo = e;
//		dean = d;
//		pflist = getProIdList(pl);
//		tlist = getTeamList(tl);
//	}
//	
	
	public void validate(Map<String, Boolean> errors) {	
		checkEmpty(errors, dean, "dean");
	}
	
	private void checkEmpty(Map<String, Boolean> errors, 
			String value, String fieldName) {
		if (value == null || value.isEmpty())
			errors.put(fieldName, Boolean.TRUE);
	}
	/* id, group의 입력값이 0인지 아닌지 확인 */
	private void checkEmpty(Map<String, Boolean> errors, 
			Integer id, String fieldName) {
		if (id == 0)
			errors.put(fieldName, Boolean.TRUE);
	}
	
	
	private List<String> getProIdList(List<ShowProf> pl){
		List<String> templist = new ArrayList<String>();
		for(ShowProf pro : pl) {
			templist.add(pro.getProId());
		}
		return templist;
	}
	
	private List<String> getTeamList(List<ShowTeam> tl){
		List<String> templist = new ArrayList<String>();
		for(ShowTeam team : tl) {
			templist.add(team.getTeamNo());
		}
		return templist;
	}
	
	private String MakeEvalNo() {
		Calendar currentCalendar = Calendar.getInstance();
		String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
		String evalNo = strYear+"-01";
		return evalNo;
	}
	
	/* 최종 평가서 번호 리스트 생성 */
	private List<String> MakeFinalList() {
		List<String> fl = new ArrayList<String>();
		/* 최종 평가서 번호 생성 */
		String finalNo = null;
		
		for(String var : tlist) {
			finalNo=var+"_"+AllEvalStatusValue.getDefaultFinalDocu();		//최종 평가서 문서 번호 : 팀번호_ff
			fl.add(finalNo);
		}
		return fl;
	}
	
	/* 개별 평가지 번호 리스트 생성 */
	private List<String> MakeEpaperList() {
		List<String> el = new ArrayList<String>();
		/* 개별 평가지 번호 */
		String epaperNo = null;
		
		for(String var1 : tlist) {
			for(String var2 : pflist) {
				epaperNo = var1+"_"+var2;			//개별 평가지 문서 번호 : 팀번호_교수번호
				el.add(epaperNo);
			}
		}
		return el;
	}
	
	public List<String> getTlist()  {
		/* try catch 로 바궈야하지 않을까 싶음
		if(tlist.size() == 0) {
			return null;
		}*/
		return tlist;
	}

	public void setTlist(List<String> tlist) {
		this.tlist = tlist;
	}
	
	public String getEvalNo() {
		return evalNo;
	}

	public void setEvalNo(String evalNo) {
		this.evalNo = evalNo;
	}

	public String getDean() {
		return dean;
	}

	public void setDean(String dean) {
		this.dean = dean;
	}

	public List<String> getPflist() {
		return pflist;
	}

	public void setPflist(List<String> pflist) {
		this.pflist = pflist;
	}
	public List<String> getEpaperlist() {
		return epaperlist;
	}
	public void setEpaperlist(List<String> epaperlist) {
		this.epaperlist = epaperlist;
	}
	public List<String> getEfinallist() {
		return efinallist;
	}
	public void setEfinallist(List<String> efinallist) {
		this.efinallist = efinallist;
	}
}