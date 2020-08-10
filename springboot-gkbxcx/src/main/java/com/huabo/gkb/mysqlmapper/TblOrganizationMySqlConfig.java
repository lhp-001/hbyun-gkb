package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblOrganization;

public class TblOrganizationMySqlConfig {
	
	public String selectDeptInfoByFather(Integer orgId, PageInfo<TblOrganization> pageInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ORGID,ORGNAME FROM TBL_ORGANIZATION WHERE FATHERORGID = "+orgId+" AND ORGTYPE = 0 AND STATUS = 0 ORDER BY ORGID LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize());
		return sb.toString();
	}
	
	public String selectDeptNameIdByCompanyId(String orgId) {
		return "SELECT * FROM tbl_organization where STATUS = 0 AND FIND_IN_SET(ORGID,(SELECT getChildrenDeptList("+orgId+")))";
	}
	
	
	public String insertOrgRight(@Param("rightList") List<String> rightList,@Param("orgid") BigDecimal orgid) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TBL_ORG_RIGHT(RIGHTID,ORGID) ");
        for (int i = 0; i < rightList.size(); i++) {
        	sb.append(" SELECT '"+rightList.get(i)+"' AS RIGHTID ,'"+orgid+"' AS ORGID FROM DUAL ");
        	if(i < (rightList.size()-1)) {
        		sb.append("UNION ALL");
        	}
		}
        return sb.toString();
	}
	
	public String selectDeptInfoByFatherId(Integer comId, PageInfo<TblOrganization> pageInfo) {
		StringBuffer sb = new StringBuffer("SELECT ORGID,ORGNAME,(SELECT COUNT(0) FROM TBL_STAFF TS WHERE TS.ORGID = TOG.ORGID) AS STAFFCOUNT FROM TBL_ORGANIZATION TOG WHERE TOG.ORGTYPE = 0 AND TOG.FATHERORGID = ");
		sb.append(comId);
		sb.append(" ORDER BY TOG.ORGID LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize());
		return sb.toString();
	}
}
