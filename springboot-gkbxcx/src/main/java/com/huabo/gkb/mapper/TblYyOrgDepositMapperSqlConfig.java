package com.huabo.gkb.mapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyUserQuery;

public class TblYyOrgDepositMapperSqlConfig {
	
	public String selectListByPageInfo(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = "SELECT RECORDID,REALNAME,ORGID,QUERYTIME,PAYMONEY,REPORTNAME FROM (" + 
				" SELECT T1.RECORDID,T1.REALNAME,T1.ORGID,T1.QUERYTIME,T1.PAYMONEY,T1.REPORTNAME,ROWNUM RW FROM (" + 
				" SELECT YUQ.RECORDID,TS.REALNAME,YUQ.ORGID,YUQ.QUERYTIME,YUQ.PAYMONEY,YUQ.REPORTNAME FROM TBL_YY_USER_QUERY YUQ" + 
				" LEFT JOIN TBL_STAFF TS ON TS.STAFFID = YUQ.QUERYSTAFF" + 
				" WHERE YUQ.ORGID = "+yuq.getOrgid()+ 
				" ORDER BY YUQ.QUERYTIME DESC ) T1 WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+") WHERE RW > "+pageInfo.getCurrentRecord();
		return sql;
	}
	
	public String selectCountByPageInfo(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = "SELECT COUNT(0) FROM TBL_YY_USER_QUERY WHERE ORGID = "+yuq.getOrgid();
		return sql;
	}
	
	public String  selectPromotionPriceList(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = "SELECT ORGNAME,PAYMONEY,QUERYTIME FROM (SELECT T1.ORGNAME,T1.PAYMONEY,T1.QUERYTIME,ROWNUM RW FROM ( " + 
				" SELECT TOG.ORGNAME,SUM(TUQ.PAYMONEY) AS PAYMONEY,MAX(QUERYTIME) AS QUERYTIME FROM TBL_YY_USER_QUERY TUQ LEFT JOIN TBL_ORGANIZATION TOG ON TUQ.ORGID = TOG.ORGID " + 
				" WHERE TUQ.ORGID IN (SELECT ORGID FROM TBL_WX_POPULARIZE_STAFF_ORGID WHERE STAFFID = "+yuq.getStaffid()
				+ ") GROUP BY TUQ.ORGID,TOG.ORGNAME ORDER BY TUQ.ORGID) T1 WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+" )WHERE RW > "+ pageInfo.getCurrentRecord();
		return sql;
	}
	
	public String selectCountPromotionPrice(PageInfo<TblYyUserQuery> pageInfo) {
		TblYyUserQuery yuq = pageInfo.getCondition();
		String sql = "SELECT COUNT(0) FROM TBL_YY_USER_QUERY TUQ " + 
				" LEFT JOIN TBL_ORGANIZATION TOG ON TUQ.ORGID = TOG.ORGID" + 
				" WHERE TUQ.ORGID IN (SELECT ORGID FROM TBL_WX_POPULARIZE_STAFF_ORGID WHERE STAFFID = " + yuq.getStaffid() +
				") GROUP BY TUQ.ORGID,TOG.ORGNAME";
		return sql;
	}
}
