package com.huabo.gkb.mysqlmapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblCirculation;

public class TblCirculationMySqlConfig {
	
	public String findSpByStaffid(String staffid, PageInfo<TblCirculation> pageInfo) {
		String sql="select * from Tbl_Circulation where CYSTAFFID="+staffid+" limit "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
	     return sql;
	}
}
