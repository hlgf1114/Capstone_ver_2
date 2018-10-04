package team.model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/* 평가 계획서 */
public class MakeTeamList {							
	
	private List<String> tlist;
	
	public MakeTeamList() {
		tlist = new ArrayList<String>();
	}
	/* 멤버 변수 전부 매개변수로 받는 생성자 */
	public MakeTeamList(String d, List<TeamList> tl) {
		tlist = getTeamList(tl);
//		regDate = new Date(date.getTime());
//		endDate = null;
	}
	
	/* 평가 계획서, 평가 교수 리스트, 평가 팀 리스트 완성 생성자 */
	public MakeTeamList(List<TeamList> tl) {				
		tlist = getTeamList(tl);
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
	
	
	private List<String> getTeamList(List<TeamList> tl){
		List<String> templist = new ArrayList<String>();
		for(TeamList team : tl) {
			templist.add(team.getTeamNo());
		}
		return templist;
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
}