package com.huabo.gkb.mapper;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblCirculation;

public class TblCirculationOracleSqlConfig {
	
	public String findSpByStaffid(String staffid, PageInfo<TblCirculation> pageInfo) {
		String sql="SELECT RN, T2.* FROM ( "
				+ "SELECT ROWNUM RN, T1.* FROM (select * from Tbl_Circulation where CYSTAFFID="+staffid+" ) T1 "
				+ "WHERE ROWNUM <= "+(pageInfo.getCurrentRecord()+pageInfo.getPageSize())+" ) T2 WHERE RN > "+pageInfo.getCurrentRecord()+"";
	     return sql;
	}
}
