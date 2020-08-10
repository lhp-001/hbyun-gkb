package com.huabo.gkb.mysqlmapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;

public class TblIndicatorResultMySqlConfig {
	
	public String findByIndicatorid(String indicatorid, PageInfo<TblIndicator> pageInfo) {
		String sql="SELECT TBL_MONITOR_INDICATORRESULT.*,TS.REALNAME FROM TBL_MONITOR_INDICATORRESULT LEFT JOIN TBL_STAFF TS ON TBL_MONITOR_INDICATORRESULT.STAFFID=TS.STAFFID " + 
				" WHERE INDICATORID="+indicatorid+" AND EXECUTEID IS NULL ORDER BY SAVETIME DESC LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
	     return sql;
	} 
}
