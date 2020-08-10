package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblStaff;

public class TblStaffMySqlConfig {
	
	public String selectUserInfoByDeptIdIncludOrg(String orgId,Integer comId, PageInfo<TblStaff> pageInfo) {
		StringBuffer sb = new StringBuffer("SELECT TSF.STAFFID,TSF.REALNAME,TSF.EMAIL,TOG.ORGNAME,'false' AS CHECKED FROM TBL_STAFF TSF LEFT JOIN TBL_ORGANIZATION TOG ON TSF.ORGID = TOG.ORGID WHERE TSF.ORGID IN (");
		sb.append("'"+orgId+"','"+comId+"') ORDER BY TSF.ORGID DESC LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize());
		return sb.toString();
	}
	
	
	public String selectUserInfoByOrgId(String orgId, PageInfo<TblStaff> pageInfo) {
		StringBuffer sb = new StringBuffer("SELECT REALNAME,MIBLEPHONE,EMAIL FROM TBL_STAFF WHERE ORGID = ");
		sb.append(orgId);
		sb.append(" ORDER BY STAFFID LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize());
		return sb.toString();
	}
	
	public String getRightForUser(@Param("staffid") BigDecimal staffid) {
		String sql="SELECT T1.RIGHTID,T1.RIGHTNAME,T1.RIGHTURL,T1.MEMO,T1.LEAF,T1.FATHERRIGHTID "
				+",T1.RIGHTCODE,T1.FUNCORDER,T1.CUSTOMPAGE,T1.INDICATORSTATUS,T1.RIGHHTDESC, "
				+"T1.RIGHTISBZ,T1.RIGHTCONTENT,T1.CSSCLASS"
				+"FROM TBL_MANAGE_RIGHT T1 "
				+"LEFT JOIN TBL_MANAGE_USER_RIGHT T2 ON T1.RIGHTID=T2.RIGHTID "
				+"WHERE T2.STAFFID ="+staffid+" AND T1.INDICATORSTATUS = 1 ";
	     return sql;
	}
	
	public String insertManagerRight(@Param("rightList") List<String> rightList,@Param("staffid") BigDecimal staffid) {
		 StringBuilder sb = new StringBuilder();
	     sb.append("INSERT INTO TBL_MANAGE_USER_RIGHT(RIGHTID,STAFFID) ");
	     for (int i = 0; i < rightList.size(); i++) {
	        sb.append(" SELECT '"+rightList.get(i)+"' AS RIGHTID ,'"+staffid+"' AS ORGID FROM DUAL ");
	        if(i < (rightList.size()-1)) {
	        	sb.append("UNION ALL");
	        }
		 }
	     return sb.toString();
	}
	
	public String insertStaffReturnStaffId(TblStaff staff) {
		StringBuffer columnsb = new StringBuffer("INSERT INTO TBL_STAFF(");
		StringBuffer valueSb = new StringBuffer(") VALUES (");
		
		if(staff.getRealname() != null && !"".equals(staff.getRealname())) {
			columnsb.append("REALNAME,");
			valueSb.append("'"+staff.getRealname()+"',");
		}
		if(staff.getEmail() != null && !"".equals(staff.getEmail())) {
			columnsb.append("EMAIL,");
			valueSb.append("'"+staff.getEmail()+"',");
		}
		if(staff.getMiblephone() != null && !"".equals(staff.getMiblephone())) {
			columnsb.append("MIBLEPHONE,");
			valueSb.append("'"+staff.getMiblephone()+"',");
		}
		if(staff.getOrgid() != null) {
			columnsb.append("ORGID,");
			valueSb.append(staff.getOrgid()+",");
		}
		if(staff.getUsername() != null && !"".equals(staff.getUsername())) {
			columnsb.append("USERNAME,");
			valueSb.append("'"+staff.getUsername()+"',");
		}
		if(staff.getPassword() != null && !"".equals(staff.getPassword())) {
			columnsb.append("`PASSWORD`,");
			valueSb.append("'"+staff.getPassword()+"',");
		}
		if(staff.getStatus() != null) {
			columnsb.append("`STATUS`,");
			valueSb.append(staff.getStatus()+",");
		}
		if(staff.getOutSideId() != null) {
			columnsb.append("OUTSIDEID,");
			valueSb.append(staff.getOutSideId()+",");
		}
		if(staff.getAddress() != null && !"".equals(staff.getAddress())){
			columnsb.append("ADDRESS,");
			valueSb.append("'"+staff.getAddress()+"',");
		}
		columnsb.deleteCharAt(columnsb.length()-1);
		valueSb.deleteCharAt(valueSb.length()-1);
		valueSb.append(")");
		String sql = columnsb.toString()+valueSb.toString();
		return sql;
	}
}
