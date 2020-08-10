package com.huabo.gkb.mysqlmapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorPrewarning;

public class TblMonitorPrewarningMySqlConfig {
	
	public String findByOrgid(String ruleid, PageInfo<TblMonitorPrewarning> pageInfo) {
		String sql="SELECT Tbl_Monitor_Prewarning.*,TS.REALNAME FROM Tbl_Monitor_Prewarning LEFT JOIN TBL_STAFF TS ON Tbl_Monitor_Prewarning.STAFFID=TS.STAFFID WHERE RULEID = "+ruleid+" LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
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
		String sql1=sql+" LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
	     return sql1;
	}
}
