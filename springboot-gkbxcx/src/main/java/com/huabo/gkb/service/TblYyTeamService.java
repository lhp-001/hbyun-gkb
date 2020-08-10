package com.huabo.gkb.service;

import java.util.List;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblyyTeam;

public interface TblYyTeamService {

	List<TblyyTeam> findAllListByStaffId(Integer staffId) throws Exception;

	Integer addTeam(String teamName, Integer staffId, String orgId) throws Exception;

	void findListByStaffId(Integer staffId, PageInfo<TblyyTeam> pageInfo) throws Exception;

	Integer modifyTeam(String teamName, Integer staffId, Integer teamid) throws Exception;

	void findTeamInfoByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo) throws Exception;

	void findListByStaffIdMySql(Integer staffId, PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo) throws Exception;

	void findTeamInfoByPageInfoMySql(Integer staffId, PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo) throws Exception;

	Integer modifyTeamMySql(String teamName, Integer staffId, Integer teamid) throws Exception;

	Integer addTeamMySql(String teamName, Integer staffId, String orgId) throws Exception;

}
