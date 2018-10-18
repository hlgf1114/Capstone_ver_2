package eval.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import member.dao.ProfessorDao;
import member.model.Professor;
import team.dao.TeamDao;

public class EvalPlanList {

	private TeamDao teamDao = new TeamDao();
	private ProfessorDao professorDao = new ProfessorDao();
	private String strYear;
	
	public EvalPlanList(){
		togetStrYear();
	}
	
	private void togetStrYear() {
		Calendar currentCalendar = Calendar.getInstance();
		this.strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
	}
	
	public EvalTeamList getEvalTeamList() {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<ShowTeam> content = teamDao.selectAllTeam(conn, strYear);
			
			return new EvalTeamList(content.size(),content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public EvalProfList getEvalProfList() {
		try (Connection conn = ConnectionProvider.getConnection()) {
			List<ShowProf> content = null;
			content = professorDao.selectAllProfessor(conn);
			return new EvalProfList(content.size(), content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
