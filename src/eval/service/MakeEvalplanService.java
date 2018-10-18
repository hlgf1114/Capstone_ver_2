package eval.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.model.Professor;
import eval.dao.EvalFinalDao;
import eval.dao.EvalProfDao;
import eval.dao.EvalTeamDao;
import eval.dao.EvalpaperDao;
import eval.dao.EvalplanDao;
import eval.model.EvalProf;
import eval.model.EvalTeam;
import eval.model.Evalplan;

public class MakeEvalplanService {
	
	EvalplanDao evalplanDao = new EvalplanDao();
	EvalTeamDao evalteamDao = new EvalTeamDao();
	EvalProfDao evalprofDao = new EvalProfDao();
	EvalpaperDao evalpaperDao = new EvalpaperDao();
	EvalFinalDao evalfinalDao = new EvalFinalDao();
	
	public void Make(MakeRequest req){
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			List<String> teamlist = req.getTlist();
			List<String> proflist = req.getPflist();
			List<String> epaperlist = req.getEpaperlist();
			List<String> efinallist = req.getEfinallist();
			
			
			String evalNo = req.getEvalNo();
			String finalNo = null;
			
			Evalplan evalplan = toEvalplan(req, evalNo);
			
			Evalplan savedEvalplan = evalplanDao.insert(conn, evalplan);
			if (savedEvalplan == null) {
				throw new RuntimeException("fail to insert eplan");
			}
			
			/* 평가 받는 팀의 평가번호, 팀번호, 최종평가서 번호 DB넣기 */
			for(String var : teamlist) {
				finalNo = toGetFinalNo(var);
				evalteamDao.insert(conn, var, finalNo);
			}
			
			for(String var2 : proflist) {
				evalprofDao.insert(conn, evalNo, var2);
			}
			
			for(String var3 : epaperlist) {
				evalpaperDao.insert(conn, var3);
			}
			
			for(String var4 : efinallist) {
				evalfinalDao.insert(conn, var4);
			}
			conn.commit();
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
	
	public boolean DoesEvalPlanExist(String planNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			if(evalplanDao.DoesEvalPlanExist(conn, planNo)) {
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
	
	private Evalplan toEvalplan(MakeRequest req, String evalno) {
		Date now = new Date();
		return new Evalplan(toGetEvalNo(), req.getDean(), now, now, AllEvalStatusValue.getDefaultEvalPlanState());
	}
	
	private String toGetEvalNo() {
		Calendar c = Calendar.getInstance();
		String evalNo = Integer.toString(c.get(Calendar.YEAR))+"-01";
		return evalNo;
	}
	
	/*각 팀당 최종 평가지 번호 생성 코드 = 년도 + 팀번호 + 디폴트 최종 평가지 문서번호*/
	private String toGetFinalNo(String teamNo) {
		String evalNo = teamNo+"_"+AllEvalStatusValue.getDefaultFinalDocu();
		return evalNo;
	}	
}
