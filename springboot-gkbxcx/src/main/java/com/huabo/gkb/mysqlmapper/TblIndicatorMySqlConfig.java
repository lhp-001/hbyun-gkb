package com.huabo.gkb.mysqlmapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;

public class TblIndicatorMySqlConfig {
	
	public String findByOrgid(String orgid, PageInfo<TblIndicator> pageInfo) {
		String sql="SELECT * FROM TBL_INDICATOR WHERE ORGID = "+orgid+" AND indicatordb = 0 ORDER BY createDate DESC, INDICATORID DESC LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
	     return sql;
	}
	
	public String findCountByOrgid(String orgid, PageInfo<TblIndicator> pageInfo) {
		String sql="SELECT COUNT(0) FROM TBL_INDICATOR WHERE ORGID = "+orgid+" AND indicatordb = 0";
	     return sql;
	}
}
