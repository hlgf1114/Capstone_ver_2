package eval.service;

import java.util.Date;
import java.util.Map;

import eval.model.Evalpaper;
import eval.model.Questions;

public class EvaluateTeamRequest {
	
		private Evalpaper ep;
		
		public EvaluateTeamRequest(String pn, Questions q, Date reg, Date end) {
			ep = new Evalpaper(pn, q, reg, end, AllEvalStatusValue.getEpaperEvalStarted());
		}
		
		public EvaluateTeamRequest(Evalpaper e) {
			ep = e;
		}
		
		public void validate(Map<String, Boolean> errors) {
			String temps = null;
			String tempc = null;
			
			Questions qs = ep.getQs();
			
			for(int i = 0; i < AllEvalStatusValue.getDefaultQuestionNo(); i++) {
				temps = "score" + ((Integer)(i+1)).toString();
				tempc = "comment" + ((Integer)(i+1)).toString();
				
				checkEmpty(errors, qs.getQsItemScore(i), temps);
				checkEmpty(errors, qs.getQsItemComment(i), tempc);
			}
//			//이걸 체크가 가능한가?
//			checkEmpty(errors, total, "total");
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

		public Evalpaper getEp() {
			return ep;
		}

		public void setEp(Evalpaper ep) {
			this.ep = ep;
		}

}
