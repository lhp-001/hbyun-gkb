package com.huabo.gkb.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblyyTeam;
import com.huabo.gkb.mapper.TblOrganizationMapper;
import com.huabo.gkb.mapper.TblYyTeamMapper;
import com.huabo.gkb.mysqlmapper.TblOrganizationMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyTeamMySqlMapper;
import com.huabo.gkb.service.TblYyTeamService;

@Service("tblYyTeamImpl")
public class TblYyTeamImpl extends BaseServiceImpl<TblyyTeam, Integer> implements TblYyTeamService {
	@Resource
	public TblYyTeamMapper tblYyTeamMapper;
	
	@Resource
	public TblOrganizationMapper tblOrganizationMapper;
	
	
	@Resource
	public TblYyTeamMySqlMapper tblYyTeamMySqlMapper;
	@Resource
	public TblOrganizationMySqlMapper tblOrganizationMySqlMapper;
	
	
	@Override
	public List<TblyyTeam> findAllListByStaffId(Integer staffId) throws Exception {
		return this.tblYyTeamMapper.selectAllListByStaffId(staffId);
	}

	@Override
	public Integer addTeam(String teamName, Integer staffId, String orgId) throws Exception {
		//验重
		Integer count = this.tblYyTeamMapper.selectCountByName(teamName,staffId);
		if(count > 0 || teamName.equals("全部")) {
			return -1;//小组名称重复
		}
		
		Integer oid = tblOrganizationMapper.findCompanyIdByDeptId(orgId);
		TblyyTeam team = new TblyyTeam();
		team.setCompay(oid);
		team.setCreateDate(new Date());
		team.setCreatePeson(staffId);
		team.setTeamName(teamName);
		this.tblYyTeamMapper.insert(team);
		return 0;
	}

	
	@Override
	public Integer addTeamMySql(String teamName, Integer staffId, String orgId) throws Exception {
		//验重
		Integer count = this.tblYyTeamMySqlMapper.selectCountByName(teamName,staffId);
		if(count > 0 || teamName.equals("全部")) {
			return -1;//小组名称重复
		}
		
		Integer oid = tblOrganizationMySqlMapper.findCompanyIdByDeptId(orgId);
		com.huabo.gkb.entitymysql.TblyyTeam team = new com.huabo.gkb.entitymysql.TblyyTeam();
		team.setCompay(oid);
		team.setCreateDate(new Date());
		team.setCreatePeson(staffId);
		team.setTeamName(teamName);
		this.tblYyTeamMySqlMapper.insert(team);
		return 0;
	}
	
	@Override
	public void findListByStaffId(Integer staffId, PageInfo<TblyyTeam> pageInfo) throws Exception {
		pageInfo.setTlist(this.tblYyTeamMapper.selectListByStaffId(staffId,pageInfo));
		pageInfo.setTotalRecord(this.tblYyTeamMapper.selectCountByStaffId(staffId,pageInfo));
	}
	
	@Override
	public void findListByStaffIdMySql(Integer staffId, PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblYyTeamMySqlMapper.selectListByStaffId(staffId,pageInfo));
		pageInfo.setTotalRecord(this.tblYyTeamMySqlMapper.selectCountByStaffId(staffId,pageInfo));
	}

	@Override
	public Integer modifyTeam(String teamName, Integer staffId, Integer teamid) throws Exception {
		//验重
		Integer count = this.tblYyTeamMapper.selectCountByName(teamName,staffId);
		if(count > 0 || teamName.equals("全部")) {
			return -1;//小组名称重复
		}
		this.tblYyTeamMapper.updateTeamName(teamName,teamid);
		return 0;
	}

	@Override
	public Integer modifyTeamMySql(String teamName, Integer staffId, Integer teamid) throws Exception {
		//验重
		Integer count = this.tblYyTeamMySqlMapper.selectCountByName(teamName,staffId);
		if(count > 0 || teamName.equals("全部")) {
			return -1;//小组名称重复
		}
		this.tblYyTeamMySqlMapper.updateTeamName(teamName,teamid);
		return 0;
	}
	
	@Override
	public void findTeamInfoByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo) throws Exception {
		pageInfo.setTlist(this.tblYyTeamMapper.selectListInfoByPageInfo(staffId,pageInfo));
		pageInfo.setTotalRecord(this.tblYyTeamMapper.selectCountByPageInfo(staffId,pageInfo));
	}

	@Override
	public void findTeamInfoByPageInfoMySql(Integer staffId, PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblYyTeamMySqlMapper.selectListInfoByPageInfo(staffId,pageInfo));
		pageInfo.setTotalRecord(this.tblYyTeamMySqlMapper.selectCountByPageInfo(staffId,pageInfo));
	}

}
