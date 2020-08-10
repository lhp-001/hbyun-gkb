package com.huabo.gkb.mysqlmapper;

import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblyyTeam;

@Repository
public class TblYyTeamMySqlConfig {
	
	
	public String selectAllListByStaffId(Integer staffId) {
		String sql = "SELECT -1 AS teamid, '全部' AS teamName FROM DUAL UNION SELECT teamid,teamName FROM TBL_YY_TEAM WHERE STAFFID = "+staffId+" ORDER BY teamid";
		return sql;
	}

	
	public String selectListInfoByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo) {
		String sql = "SELECT T1.teamid,T1.teamName FROM (SELECT -1 AS teamid, '全部' AS teamName FROM DUAL UNION SELECT teamid,teamName FROM TBL_YY_TEAM WHERE STAFFID = "+staffId+" ORDER BY teamid) T1 LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
		return sql;
	}
	
	
	public String selectCountByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo) {
		String sql = "SELECT COUNT(0)+1 FROM TBL_YY_TEAM WHERE STAFFID = " + staffId;
		return sql;
	}
	
	
	public String selectListByStaffId(Integer staffId, PageInfo<TblyyTeam> pageInfo) {
String sql = "SELECT teamid,teamName FROM TBL_YY_TEAM WHERE STAFFID = "+staffId+" ORDER BY teamid  LIMIT "+pageInfo.getCurrentRecord()+","+pageInfo.getPageSize();
		return sql;
	}
}
