package com.huabo.gkb.mapper;

public class TblReportOracleSqlConfig {
	
	public String findReport(String orgid) {
		String sql="SELECT PAGEID,PAGENAME,RQURL,URL FROM TBL_BI_REPORT_MENU WHERE 1 = 1 AND UNIT = 1 AND PAGEBODY= (SELECT PAGEID FROM TBL_BI_REPORT_MENU WHERE PAGENAME='小程序报表')";
	     return sql;
	}
}
