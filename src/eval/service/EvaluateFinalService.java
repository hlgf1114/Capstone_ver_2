package eval.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import eval.dao.EvalFinalDao;
import eval.dao.EvalTeamDao;
import eval.dao.EvalpaperDao;
import eval.model.EvalFinal;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import team.dao.TeamDao;

public class EvaluateFinalService {
		
		private EvalFinalDao evalfinaldao = new EvalFinalDao();
		private TeamDao TeamDao = new TeamDao();
		private EvalpaperDao evalpaperDao = new EvalpaperDao();
		/* 개별 교수님 평가서 번호 */
		/* 평가 세션 값 가져와서 만들자 */
		public String makeFinalNo(String tNo, String pId) {
			return tNo+"_"+pId;
		}
		/* 그냥 골라오기 - 결과를 보여줘야할 때도 쓸 수 있을듯. */
		public EvalFinal SelectEvalFinal(String finalNo) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
			
				EvalFinal evalfinal = evalfinaldao.selectEvalFinal(conn, finalNo);
				/* 평가지 못찾았을 경우 에러 처리 */
//				if (EvalFinal != null) {
//					JdbcUtil.rollback(conn);
//					throw new DuplicateIdException();
//				}
				/* 해당 팀에 대한 평가가 끝남 */
				return evalfinal;
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} finally {
				JdbcUtil.close(conn);
			}
		}
		
		public void EvaluateFinal(EvalFinal evalFinal) {
			Connection conn = null;
			
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				
				evalfinaldao.update(conn, evalFinal);
				conn.commit();
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} finally {
				JdbcUtil.close(conn);
			}
		}
		
		public List<EvalFinal> SelectAllFinal(){
			Connection conn = null;
			
			String year = AllEvalStatusValue.togetTwoDigitYear();
			
			List<EvalFinal> sl = null;
			try {
				conn = ConnectionProvider.getConnection();
				
				sl = evalfinaldao.selectAllFinal(conn, year);
				
				return sl;
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} finally {
				JdbcUtil.close(conn);
			}
		}
		
		public boolean IsFinalCompleted(String finalNo) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				int state = evalfinaldao.selectState(conn, finalNo);
				
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
		
		public ShowTeam selectEteam(String teamno) {
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
			
				ShowTeam eteam = TeamDao.selectShowTeam(conn, teamno);
				/* 평가지 못찾았을 경우 에러 처리 */
//				if (EvalFinal != null) {
//					JdbcUtil.rollback(conn);
//					throw new DuplicateIdException();
//				}
				/* 해당 팀에 대한 평가가 끝남 */
				return eteam;
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} finally {
				JdbcUtil.close(conn);
			}
		}
		public double CntAverage(String teamNo) {
			double average = 0.0;
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				average = evalpaperDao.CntEteamAverage(conn, teamNo);
				
				return average;
			} catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} finally {
				JdbcUtil.close(conn);
			}
		}
}
