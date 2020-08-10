package com.huabo.gkb.mapper;

public class TblManageRightOracleSqlConfig {
	
	public String getRightForUser(String staffid) {
		String sql="SELECT T1.RIGHTID,T1.RIGHTNAME,T1.RIGHTURL,T1.MEMO,T1.LEAF,T1.FATHERRIGHTID "
				+",T1.RIGHTCODE,T1.FUNCORDER,T1.CUSTOMPAGE,T1.INDICATORSTATUS,T1.RIGHTDESC, "
				+"T1.RIGHTISBZ,T1.RIGHTCONTENT,T1.CSSCLASS "
				+"FROM TBL_MANAGE_RIGHT T1 "
				+"LEFT JOIN TBL_MANAGE_USER_RIGHT T2 ON T1.RIGHTID=T2.RIGHTID "
				+"WHERE T2.STAFFID ="+staffid+" AND T1.INDICATORSTATUS = 1 ";
	     return sql;
	}
}
