package eval.command;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eval.service.AllEvalStatusValue;
import eval.service.EvalPlanList;
import eval.service.EvalProfList;
import eval.service.EvalTeamList;
import eval.service.MakeEvalplanService;
import eval.service.MakeRequest;
import eval.service.ShowProf;
import jdbc.connection.ConnectionProvider;
import member.dao.ProfessorDao;
import auth.service.Authority;
import auth.service.User;
import mvc.command.CommandHandler;

public class MakeEvalplanHandler implements CommandHandler {
	private static final String FORM_VIEW = "/WEB-INF/view/makeEvalPlanForm.jsp";	//수정과 같은 뷰면 될듯
	private static final String PRESENET_VIEW = "/WEB-INF/view/EvalTeamList.jsp";
	private MakeEvalplanService makeService = new MakeEvalplanService();
	private EvalPlanList evalplanlist = new EvalPlanList();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String plan = (String)req.getParameter("plan");
		
		if ((plan!=null)&&(plan.equals("make"))) {
			return processForm(req, res);
		} else {
			return processSubmit(req, res);
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		
		String planNo = AllEvalStatusValue.togetStrYear()+AllEvalStatusValue.getEvalPlanDocuNo();
		
		/*평가가 이미 시작된 경우*/
		if(makeService.DoesEvalPlanExist(planNo)) {
			req.setAttribute("already", "yyyyyyyy");
			//평가가 이미 시작되었습니다를 알려주는 경고창이 있어야하는데...
			return PRESENET_VIEW;
		}
		
		HttpSession session = req.getSession();
		
		EvalTeamList evalteamlist = evalplanlist.getEvalTeamList();
		EvalProfList evalproflist = evalplanlist.getEvalProfList();
		
		session.setAttribute("teamList", evalteamlist);
		session.setAttribute("proList", evalproflist);
		
		
		req.setAttribute("already", "nooo");
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		/* 평가에 참여하는 선택된 교수 읽어오기 */
		String value[] = req.getParameterValues("selectprof");
		User user = (User)req.getSession(false).getAttribute("authProUser");
		EvalTeamList tl = (EvalTeamList)req.getSession(false).getAttribute("teamList");
		/* 세션에서 전체 교수 목록만 가져오기 */
		EvalProfList plist = (EvalProfList)req.getSession(false).getAttribute("proList");
		
		
		for(String val : value) {
			System.out.println("val : "+val);
		}
		/* 전체 교수 중에서 선택된 교수만 리스트에 넣기 */
		List<ShowProf> pl = selectedProfessor(plist,value);
		
		/* 아무 교수도 선택안하고 넘기면 오류 처리해야함. */
		if(value == null) {
			return FORM_VIEW;
		}
		//이 생성자 부터 완성하자.
		MakeRequest meq = new MakeRequest(user.getId(),pl, tl.getList());
		makeService.Make(meq);
		/* 평가 참여 교수 권한 변경 */
		changeProfAuthority(value);
		//이 부분 평가 화면으로 넘겨야함.
		return FORM_VIEW;
	}
	
	/* 평가 참여 교수 권한 변경 */
	private void changeProfAuthority(String value[]) {
		ProfessorDao professorDao = new ProfessorDao();
		try (Connection conn = ConnectionProvider.getConnection()) {
			for(String var : value) {
				System.out.print(var);
				/* 학과장 제외하고 평가 교수들만 권한 변경하기 */
				professorDao.updateAuthority(conn, var, Authority.getProEval(), Authority.getProDean());
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<ShowProf> selectedProfessor(EvalProfList pl, String[] val) {
		List<ShowProf> profl=pl.getList();
		List<ShowProf> newlist = new ArrayList<ShowProf>();
		for(String value : val) {
			for(ShowProf pro : profl) {
				if(value.equals(pro.getProId())) {
					newlist.add(pro);
					break;
				}
			}
		}
		return newlist;
	}
}
