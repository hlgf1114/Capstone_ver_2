package eval.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eval.dao.EvalFinalDao;
import eval.model.EvalFinal;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ShowFinalResultService {
	
		EvalFinalDao evalplanDao = new EvalFinalDao();
		
		public EvalFinal selectEvalFinal(String teamNo){
			Connection conn = null;
			
			String finalNo = teamNo+"_"+AllEvalStatusValue.getDefaultFinalDocu();			
			
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
			
				EvalFinal ef = evalplanDao.selectEvalFinal(conn, finalNo);
				
				return ef;
			}catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				JdbcUtil.rollback(conn);
				throw e;
			} finally {
				JdbcUtil.close(conn);
			}
			
		}
		
		public boolean IsCompleted(String teamNo) {
			Connection conn = null;
			String finalNo = teamNo+"_"+AllEvalStatusValue.getDefaultFinalDocu();
			
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
			
				int state = evalplanDao.selectState(conn, finalNo);
				
				if(state == AllEvalStatusValue.getEfinalEvalEnded()) {
					return true;
				}
				return false;
			}catch (SQLException e) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException(e);
			} catch (RuntimeException e) {
				JdbcUtil.rollback(conn);
				throw e;
			} finally {
				JdbcUtil.close(conn);
			}
		}
		
}
