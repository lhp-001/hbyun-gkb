package com.huabo.gkb.mysqlmapper;

import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblyyCompany;

@Repository
public class TblYyCompanyMySqlConfig {
	
	public String checkNAddOrUpdateComPany(String companyName, Integer teamid, Integer staffId){
		String sql ="SELECT * FROM TBL_YY_COMPANY WHERE companyName = '"+companyName+"' AND CSTATUS = 0 AND STAFFID = "+staffId;
		if(teamid == -1) {
			sql += " AND teamid IS NULL";
		}else {
			sql += " AND teamid ="+teamid;
		}
		
		return sql;
	}
	
	public String checkNameByRemoveCom(String companyName,Integer teamid,Integer staffId) {
		String sql ="SELECT COUNT(0) FROM TBL_YY_COMPANY WHERE companyName = '"+companyName+"' AND CSTATUS = 1 AND STAFFID = "+staffId;
		if(teamid == -1) {
			sql += " AND teamid IS NULL";
		}else {
			sql += " AND teamid ="+teamid;
		}
		return sql;
	}
	
	public String selectCompanyListByPageInfo(PageInfo<TblyyCompany> pageInfo) {
		TblyyCompany company = pageInfo.getCondition();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT companyid,companyName,teamid,createDate,STAFFID,ORGID,REPORTID,fxtype,CSTATUS FROM TBL_YY_COMPANY WHERE CSTATUS = 1");
		if(company.getTeamId() != null) {
			if(company.getTeamId() == -1) {
				sbSql.append(" AND (teamid IN (SELECT teamid FROM TBL_YY_TEAM WHERE STAFFID = "+company.getStaffId()+") OR (teamid IS NULL AND STAFFID = "+company.getStaffId()+" ))");
			}else {
				sbSql.append(" AND teamid ="+company.getTeamId());
			}
		}
		if(company.getCompanyName()!= null && !"".equals(company.getCompanyName())) {
			sbSql.append(" AND companyName LIKE '%"+company.getCompanyName()+"%' ");
		}else {
			sbSql.append(" AND companyName LIKE '%%' ");
		}
		
		sbSql.append(" ORDER BY companyid limit "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize());
		return sbSql.toString();
	}
	
	public String  selectCompanyCountByPageInfo(PageInfo<TblyyCompany> pageInfo) {
		TblyyCompany company = pageInfo.getCondition();
		StringBuffer sql = new StringBuffer("SELECT COUNT(0) FROM TBL_YY_COMPANY WHERE CSTATUS = 1 ");
		if(company.getTeamId() != null) {
			if(company.getTeamId() == -1) {
				sql.append(" AND (teamid IN (SELECT teamid FROM TBL_YY_TEAM WHERE STAFFID = "+company.getStaffId()+") OR (teamid IS NULL AND STAFFID = "+company.getStaffId()+" ))");
			}else {
				sql.append(" AND teamid ="+company.getTeamId());
			}
		}
		if(company.getCompanyName()!= null && !"".equals(company.getCompanyName())) {
			sql.append(" AND companyName LIKE '%"+company.getCompanyName()+"%'");
		}else {
			sql.append(" AND companyName LIKE '%%' ");
		}
		return sql.toString();
	}
}
