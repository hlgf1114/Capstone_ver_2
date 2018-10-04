package eval.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import eval.dao.EvalProfDao;
import eval.dao.EvalpaperDao;
import eval.model.Evalpaper;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.StudentDao;
import member.service.DuplicateIdException;
import team.dao.TeamDao;

public class EvaluateTeamService {
	
	private EvalpaperDao evalpaperdao = new EvalpaperDao();
	private StudentDao studentDao = new StudentDao();
	private EvalProfDao evalprofdao = new EvalProfDao();
	private TeamDao teamDao = new TeamDao();
	/* 개별 교수님 평가서 번호 */
	/* 평가 세션 값 가져와서 만들자 */
	public String makePaperNo(String tNo, String pId) {
		return tNo+"_"+pId;
	}
	
	public Evalpaper SelectEvalpaper(String epaperNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			Evalpaper evalpaper = evalpaperdao.selectEvalPaper(conn, epaperNo);
			/* 평가지 못찾았을 경우 에러 처리 */
//			if (evalpaper != null) {
//				JdbcUtil.rollback(conn);
//				throw new DuplicateIdException();
//			}
			/* 해당 팀에 대한 평가가 끝남 */
			
			if(evalpaper.getState() == AllEvalStatusValue.getEpaperEvalEnded()) {
				return null;
			}
			return evalpaper;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
			
	public Evalpaper SelectEvalResult(String epaperNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			
			Evalpaper evalpaper = evalpaperdao.selectEvalPaper(conn, epaperNo);
			/* 평가지 못찾았을 경우 에러 처리 */
//			if (evalpaper != null) {
//				JdbcUtil.rollback(conn);
//				throw new DuplicateIdException();
//			}
			return evalpaper;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public void EvaluateTeam(EvaluateTeamRequest evalReq) {
		Connection conn = null;
		
		Evalpaper evalpaper = evalReq.getEp();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			evalpaperdao.update(conn, evalpaper);
			
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public List<ShowTeamMember> SelectTeamMembers(String teamNo){
		Connection conn = null;
		
		List<ShowTeamMember> sl = null;
		try {
			conn = ConnectionProvider.getConnection();
			
			sl = studentDao.selectByTeamNo(conn, teamNo);
			return sl;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public void CompleteEval(String epaperNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			evalpaperdao.update_complete(conn, epaperNo);
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public boolean IsEvalCompleted(String paperNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int state = evalpaperdao.selectState(conn, paperNo);
			if(state == AllEvalStatusValue.getEpaperEvalEnded()) {
				return true; 
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	
	public boolean IsPossibleToEval(String proId) {
		Connection conn = null;
		String planNo = AllEvalStatusValue.togetStrYear() + AllEvalStatusValue.getEvalPlanDocuNo();
		try {
			conn = ConnectionProvider.getConnection();
			if(evalprofdao.IsPossibleToEval(conn, planNo, proId)) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
	public ShowTeam SelectShowTeam(String teamNo){
		Connection conn = null;
		
		ShowTeam st = null;
		try {
			conn = ConnectionProvider.getConnection();
			
			st = teamDao.selectShowTeam(conn, teamNo);
			return st;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
