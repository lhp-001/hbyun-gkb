package com.huabo.gkb.mapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorPrewarning;

public class TblMonitorPrewarningOracleSqlConfig {
	
	public String findByOrgid(String ruleid, PageInfo<TblMonitorPrewarning> pageInfo) {
		String sql="SELECT RN, T2.* FROM ( "
				+ "SELECT ROWNUM RN, T1.* FROM (SELECT Tbl_Monitor_Prewarning.*,TS.REALNAME FROM Tbl_Monitor_Prewarning LEFT JOIN TBL_STAFF TS ON Tbl_Monitor_Prewarning.STAFFID=TS.STAFFID WHERE RULEID = "+ruleid+") T1 "
				+ "WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+" ) T2 WHERE RN > "+pageInfo.getCurrentRecord()+"";
	     return sql;
	}
	
	public String findGzDetailCountByOrgid(String ruleid, PageInfo<TblMonitorPrewarning> pageInfo) {
		String sql="SELECT COUNT(0) FROM Tbl_Monitor_Prewarning LEFT JOIN TBL_STAFF TS ON Tbl_Monitor_Prewarning.STAFFID=TS.STAFFID WHERE RULEID = "+ruleid;
	     return sql;
	}
	
	
	public String findSignIdByRuleid(String ruleid) {
		String sql="SELECT SIGNID FROM Tbl_Monitor_Prewarning WHERE RULEID ="+ruleid+"  and SAVEDATETIME =(SELECT MAX(SAVEDATETIME)FROM Tbl_Monitor_Prewarning)";
	     return sql;
	}
	public String findRuleResult(String sql, PageInfo<Object> pageInfo) {
		String sql1="SELECT RN, T2.* FROM ( "
				+ "SELECT ROWNUM RN, T1.* FROM ("+sql+") T1 "
				+ "WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+" ) T2 WHERE RN > "+pageInfo.getCurrentRecord()+"";	
	     return sql1;
	}
}
