package com.huabo.gkb.mapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;

public class TblIndicatorResultOracleSqlConfig {
	
	public String findByIndicatorid(String indicatorid, PageInfo<TblIndicator> pageInfo) {
		String sql="SELECT RN, T2.* FROM ( "
				+ "SELECT ROWNUM RN, T1.* FROM (SELECT TBL_MONITOR_INDICATORRESULT.*,TS.REALNAME FROM TBL_MONITOR_INDICATORRESULT LEFT JOIN TBL_STAFF TS ON TBL_MONITOR_INDICATORRESULT.STAFFID=TS.STAFFID\r\n" + 
				" WHERE INDICATORID="+indicatorid+" AND EXECUTEID IS NULL ORDER BY SAVETIME DESC ) T1 "
				+ "WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+" ) T2 WHERE RN > "+pageInfo.getCurrentRecord();
	     return sql;
	} 
}
