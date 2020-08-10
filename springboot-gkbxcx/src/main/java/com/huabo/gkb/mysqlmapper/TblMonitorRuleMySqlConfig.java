package com.huabo.gkb.mysqlmapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorRule;

public class TblMonitorRuleMySqlConfig {
	
	
	public String findByOrgid(String orgid, PageInfo<TblMonitorRule> pageInfo) {
		String sql="SELECT T .* FROM TBL_MONITOR_RULE T WHERE T .INRULEDB = 0 AND orgid = "+orgid+" ORDER BY T .RULEID DESC LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
	     return sql;
	}
	
	public String findCountByOrgid(String orgid, PageInfo<TblMonitorRule> pageInfo) {
		String sql="SELECT COUNT(0) FROM TBL_MONITOR_RULE T WHERE T .INRULEDB = 0 AND orgid = "+orgid;
	     return sql;
	}
	
	
	public String findBookByRuleid(String ruleid) {
		String sql="SELECT ACCTID FROM TBL_ACCBOOK where BOOKID =(SELECT CONNECTIONSTRINGS FROM TBL_MONITOR_RULE WHERE RULEID ="+ruleid+")";
	     return sql;
	}
}
