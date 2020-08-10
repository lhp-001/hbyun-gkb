package com.huabo.gkb.mysqlmapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblYyUserQuery;

public class TblYyOrgDepositMySqlConfig {
	
	public String selectListByPageInfo(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = " SELECT YUQ.RECORDID,TS.REALNAME,YUQ.ORGID,YUQ.QUERYTIME,YUQ.PAYMONEY,YUQ.REPORTNAME FROM TBL_YY_USER_QUERY YUQ" + 
				" LEFT JOIN TBL_STAFF TS ON TS.STAFFID = YUQ.QUERYSTAFF" + 
				" WHERE YUQ.ORGID = "+yuq.getOrgid()+ 
				" ORDER BY YUQ.QUERYTIME DESC LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
		return sql;
	}
	
	public String selectCountByPageInfo(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = "SELECT COUNT(0) FROM TBL_YY_USER_QUERY WHERE ORGID = "+yuq.getOrgid();
		return sql;
	}
	
	public String  selectPromotionPriceList(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = "SELECT TOG.ORGNAME,SUM(TUQ.PAYMONEY) AS PAYMONEY,MAX(QUERYTIME) AS QUERYTIME FROM TBL_YY_USER_QUERY TUQ LEFT JOIN TBL_ORGANIZATION TOG ON TUQ.ORGID = TOG.ORGID " + 
				" WHERE TUQ.ORGID IN (SELECT ORGID FROM TBL_WX_POPULARIZE_STAFF_ORGID WHERE STAFFID = "+yuq.getStaffid()
				+ ") GROUP BY TUQ.ORGID,TOG.ORGNAME ORDER BY TUQ.ORGID LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
		return sql;
	}
	
	public String selectCountPromotionPrice(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = "SELECT count(0) from (SELECT TUQ.ORGID FROM TBL_YY_USER_QUERY TUQ " + 
				" LEFT JOIN TBL_ORGANIZATION TOG ON TUQ.ORGID = TOG.ORGID" + 
				" WHERE TUQ.ORGID IN (SELECT ORGID FROM TBL_WX_POPULARIZE_STAFF_ORGID WHERE STAFFID = " + yuq.getStaffid() +
				") GROUP BY TUQ.ORGID,TOG.ORGNAME) AS T1";
		return sql;
	}
}
