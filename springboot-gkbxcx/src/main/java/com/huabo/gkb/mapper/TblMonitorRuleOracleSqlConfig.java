package com.huabo.gkb.mapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorRule;

public class TblMonitorRuleOracleSqlConfig {
	
	
	public String findByOrgid(String orgid, PageInfo<TblMonitorRule> pageInfo) {
		String sql="SELECT RN, T2.* FROM ( "
				+ "SELECT ROWNUM RN, T1.* FROM (SELECT T .* FROM TBL_MONITOR_RULE T WHERE T .INRULEDB = 0 AND orgid = "+orgid+" ORDER BY T .RULEID DESC) T1 "
				+ "WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+" ) T2 WHERE RN > "+pageInfo.getCurrentRecord();
	     return sql;
	}
	
	public String findCountByOrgid(String orgid, PageInfo<TblMonitorRule> pageInfo) {
		String sql="SELECT COUNT(0) FROM TBL_MONITOR_RULE T WHERE T .INRULEDB = 0 AND orgid = "+orgid+" ORDER BY T .RULEID DESC ";
	     return sql;
	}
	
	
	public String findBookByRuleid(String ruleid) {
		String sql="SELECT ACCTID FROM TBL_ACCBOOK where BOOKID =(SELECT CONNECTIONSTRINGS FROM TBL_MONITOR_RULE WHERE RULEID ="+ruleid+")";
	     return sql;
	}
}
