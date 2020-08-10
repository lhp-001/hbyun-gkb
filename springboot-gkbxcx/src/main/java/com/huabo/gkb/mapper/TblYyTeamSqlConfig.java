package com.huabo.gkb.mapper;

import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblyyTeam;

@Repository
public class TblYyTeamSqlConfig {
	
	
	public String selectAllListByStaffId(Integer staffId) {
		String sql = "SELECT -1 AS \"teamid\", '全部' AS \"teamName\" FROM DUAL UNION SELECT \"teamid\",\"teamName\" FROM TBL_YY_TEAM WHERE STAFFID = "+staffId+" ORDER BY \"teamid\"";
		return sql;
	}

	
	public String selectListInfoByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo) {
		String sql = "SELECT \"teamid\",\"teamName\" FROM (SELECT T1.\"teamid\",T1.\"teamName\",ROWNUM RN FROM (SELECT -1 AS \"teamid\", '全部' AS \"teamName\" FROM DUAL UNION SELECT \"teamid\",\"teamName\" FROM TBL_YY_TEAM WHERE STAFFID = "+staffId+" ORDER BY \"teamid\") T1 WHERE ROWNUM <= "+(pageInfo.getPageSize()*pageInfo.getCurrentPage())+") WHERE RN > "+(pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();
		return sql;
	}
	
	
	public String selectCountByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo) {
		String sql = "SELECT COUNT(0)+1 FROM TBL_YY_TEAM WHERE STAFFID = " + staffId;
		return sql;
	}
	
	
	public String selectListByStaffId(Integer staffId, PageInfo<TblyyTeam> pageInfo) {
		String sql = "SELECT \"teamid\",\"teamName\" FROM (SELECT T1.\"teamid\",T1.\"teamName\",ROWNUM RN FROM (SELECT \"teamid\",\"teamName\" FROM TBL_YY_TEAM WHERE STAFFID = "+staffId+" ORDER BY \"teamid\") T1 WHERE ROWNUM <= "+(pageInfo.getPageSize()*pageInfo.getCurrentPage())+") WHERE RN > "+(pageInfo.getCurrentPage()-1)*pageInfo.getPageSize();
		return sql;
	}
}
