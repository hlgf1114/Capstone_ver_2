package eval.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eval.dao.EvalProfDao;
import eval.dao.EvalplanDao;
import eval.model.Evalpaper;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.ProfessorDao;

public class ShowResultListService {
	
	EvalplanDao evalplanDao = new EvalplanDao();
	EvalProfDao evalprofDao = new EvalProfDao();
	EvalPlanList evalplanlist = new EvalPlanList();
	ProfessorDao professorDao = new ProfessorDao();
	
	public List<EpaperResult> MakeResultList(List<Evalpaper> eplist){
		
		List<ShowProf> pflist = selectEvalProf();
		
		String proid=null;
		String epaperNo=null;
		
		List<EpaperResult> erlist = new ArrayList<EpaperResult>();
		EpaperResult er = null;
		System.out.println("이거는 사이즈 ??"+pflist.size());
		for(ShowProf var1 : pflist) {
			proid=var1.getProId();
			System.out.println("이거는 ??"+proid);
			int i = 0;
			for(Evalpaper var2 : eplist) {
				System.out.println(i++);
				epaperNo = var2.getPaperNo();
				if(epaperNo.contains(proid)) { //해당 평가지 교수를 찾음
					er = new EpaperResult(epaperNo, var2.getState(),var1.getProName(),proid,var2.getTotal());
					erlist.add(er);
					break;
				}
			}
		}
		return erlist;
	}
	
	private List<ShowProf> selectEvalProf(){
		Connection conn = null;
		
		String planNo = AllEvalStatusValue.togetStrYear()+AllEvalStatusValue.getEvalPlanDocuNo();
		
		List<ShowProf> pl = new ArrayList<ShowProf>();
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
		
			List<String> plist = evalprofDao.selectAllProf(conn, planNo);
			
			ShowProf pf = null;
			
			for(String var : plist) {
				pf = professorDao.selectAsShowProf(conn, var);
				pl.add(pf);
			}
			
			return pl;
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
