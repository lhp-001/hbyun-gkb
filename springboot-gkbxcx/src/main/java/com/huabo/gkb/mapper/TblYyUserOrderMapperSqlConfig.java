package com.huabo.gkb.mapper;

import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyUserOrder;

@Repository
public class TblYyUserOrderMapperSqlConfig{

	public String selectListByPageInfo(PageInfo<TblYyUserOrder> pageInfo) {
		TblYyUserOrder yuo = pageInfo.getCondition();
		String sql ="SELECT ORDERCODE,ORDERID,ORDERNO,STATUS,ORDERMONEY,CREATEDATE,PAYDATE,ORGID,REALNAME FROM (SELECT T1.ORDERCODE,T1.ORDERID,T1.ORDERNO,T1.STATUS,T1.ORDERMONEY,T1.CREATEDATE,T1.PAYDATE,T1.ORGID,T1.REALNAME,ROWNUM AS RN FROM("
				+ "SELECT YUO.ORDERCODE,YUO.ORDERID,YUO.ORDERNO,YUO.STATUS,YUO.ORDERMONEY,YUO.CREATEDATE,YUO.PAYDATE,YUO.ORGID,TS.REALNAME FROM TBL_YY_USER_ORDER YUO" + 
				" LEFT JOIN TBL_STAFF TS ON TS.STAFFID = YUO.CREATESTAFF" + 
				" WHERE YUO.ORGID = "+yuo.getOrgId();
		if(yuo.getStatus() != 0 ) {
			if(yuo.getStatus() == 1) {
				sql += " AND YUO.\"STATUS\" = 1";
			}else if(yuo.getStatus() == 2) {
				sql += " AND (YUO.\"STATUS\" != 1 AND YUO.STATUS != 3)";
			}else if(yuo.getStatus() == 3) {
				sql += " AND YUO.\"STATUS\" = 3";
			}
		}				
		sql += " ORDER BY YUO.CREATEDATE DESC ) T1 WHERE ROWNUM <= "+pageInfo.getCurrentPage()*pageInfo.getPageSize()+" ) WHERE RN > "+(pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();
		return sql;
	}

	public String selectCountByPageInfo(PageInfo<TblYyUserOrder> pageInfo) {
		TblYyUserOrder yuo = pageInfo.getCondition();
		String sql = "SELECT COUNT(0) FROM TBL_YY_USER_ORDER WHERE ORGID = "+yuo.getOrgId();
		if(yuo.getStatus() != 0 ) {
			if(yuo.getStatus() == 1) {
				sql += " AND \"STATUS\" = 1";
			}else if(yuo.getStatus() == 2) {
				sql += " AND (\"STATUS\" != 1 AND \"STATUS\" != 3)";
			}else if(yuo.getStatus() == 3) {
				sql += " AND \"STATUS\" = 3";
			}
		}	
		return sql;
	}
	
	
}
