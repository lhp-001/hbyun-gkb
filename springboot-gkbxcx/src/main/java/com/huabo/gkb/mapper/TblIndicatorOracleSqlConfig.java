package com.huabo.gkb.mapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;

public class TblIndicatorOracleSqlConfig {
	
	public String findByOrgid(String orgid, PageInfo<TblIndicator> pageInfo) {
		String sql="SELECT RN, T2.* FROM ( "
				+ "SELECT ROWNUM RN, T1.* FROM ( SELECT * FROM TBL_INDICATOR WHERE ORGID = "+orgid+" AND indicatordb = 0 ORDER BY createDate DESC, INDICATORID DESC ) T1 "
				+ "WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+" ) T2 WHERE RN > "+pageInfo.getCurrentRecord()+"";
	     return sql;
	}
	
	public String findCountByOrgid(String orgid, PageInfo<TblIndicator> pageInfo) {
		String sql="SELECT COUNT(0) FROM TBL_INDICATOR WHERE ORGID = "+orgid+" AND indicatordb = 0 ORDER BY createDate DESC, INDICATORID DESC ";
	     return sql;
	}
}
