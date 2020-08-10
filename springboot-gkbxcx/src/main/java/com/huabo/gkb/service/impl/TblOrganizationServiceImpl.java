package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblOrganization;
import com.huabo.gkb.mapper.TblOrganizationMapper;
import com.huabo.gkb.mysqlmapper.TblOrganizationMySqlMapper;
import com.huabo.gkb.service.TblOrganizationService;

@Service("tblOrganizationServiceImpl")
public class TblOrganizationServiceImpl extends BaseServiceImpl<TblOrganization, BigDecimal> implements TblOrganizationService {
	@Resource
	public TblOrganizationMapper tblOrganizationMapper;
	
	
	@Resource
	public TblOrganizationMySqlMapper tblOrganizationMySqlMapper;

	@Override
	public List<TblOrganization> findDeptNameIdByCompanyId(String orgId) throws Exception {
		return tblOrganizationMapper.selectDeptNameIdByCompanyId(orgId);
	}

	@Override
	public Integer findRegisterOrganizationCount(BigDecimal orgId, BigDecimal registerorgfatherid) throws Exception {
		return tblOrganizationMapper.selectRegisterOrganizationCount(orgId,registerorgfatherid);
	}

	@Override
	public Integer findCompanyIdByDeptId(String orgId) throws Exception {
		Integer orgid = 0;
		if(SystemStaticValue.DataType == 0) {
			orgid = tblOrganizationMySqlMapper.findCompanyIdByDeptId(orgId);
		}else {
			orgid = tblOrganizationMapper.findCompanyIdByDeptId(orgId);
		}
		return orgid;
	}

	@Override
	public Integer findRootCompanyIdByDeptId(String orgId, Integer registerorgtype) throws Exception {
		return tblOrganizationMapper.findCompanyIdByDeptId(orgId);
	}

	@Override
	public void findDeptInfoByOrgId(String orgId, PageInfo<TblOrganization> pageInfo) throws Exception {
		pageInfo.setTlist(tblOrganizationMapper.selectDeptInfoByFatherId(Integer.parseInt(orgId),pageInfo));
		pageInfo.setTotalRecord(tblOrganizationMapper.selectDeptCountByFatherId(Integer.parseInt(orgId),pageInfo));
	}

	@Override
	public String findOrgNameByOrgId(String orgId) throws Exception {
		return tblOrganizationMapper.selectOrgNameByOrgId(orgId);
	}

	@Override
	public Integer findSameDeptNoByOrgId(Integer orgId,String orgNo) throws Exception {
		Integer count = 0;
		if(SystemStaticValue.DataType == 0) {
			String deptId = tblOrganizationMySqlMapper.findDeptIdsByOrgId(orgId);
			count = tblOrganizationMySqlMapper.findSameDeptNoByOrgId(deptId,orgNo);
		}else {
			count = tblOrganizationMapper.findSameDeptNoByOrgId(orgId,orgNo);
		}
		return count;
	}

	@Override
	public void saveDeptInfo(TblOrganization org) throws Exception {
		if(SystemStaticValue.DataType == 0) {
			com.huabo.gkb.entitymysql.TblOrganization o = new com.huabo.gkb.entitymysql.TblOrganization();
			
			o.setOutsideId(org.getOutsideId());
			o.setFatherorgid(org.getFatherorgid());
			o.setOrgname(org.getOrgname());
			o.setOrgtype(org.getOrgtype());
			o.setOrgCreate(org.getOrgCreate());
			o.setOrgnumber(org.getOrgnumber());
			o.setMemo(org.getMemo());
			o.setOrgmeno(org.getOrgmeno());
			o.setStatus(org.getStatus());
			
			tblOrganizationMySqlMapper.insertDeptInfo(o);
		}else{
			tblOrganizationMapper.insertDeptInfo(org);
		}
	}

	@Override
	public List<TblOrganization> findHyOrgAllInfo() throws Exception {
		return tblOrganizationMapper.selectHyOrgInfo();
	}
	
	@Override
	public List<com.huabo.gkb.entitymysql.TblOrganization> findHyOrgAllInfoMySql() throws Exception {
		return tblOrganizationMySqlMapper.selectHyOrgInfo();
	}

	@Override
	public Integer selectCountByName(String companyName) throws Exception {
		return this.tblOrganizationMapper.selectCountByName(companyName);
	}

	@Override
	public BigDecimal findDeptInfoByorgName(String companyName) throws Exception {
		return this.tblOrganizationMapper.selectOrgIdByCompanyName(companyName);
	}

	@Override
	public void findDeptListByOrgId(Integer orgId, PageInfo<TblOrganization> pageInfo) throws Exception {
		pageInfo.setTlist(this.tblOrganizationMapper.selectDeptInfoByFather(orgId,pageInfo));
		pageInfo.setTotalRecord(this.tblOrganizationMapper.selectDeptCountByFather(orgId));
	}

	
	@Override
	public String findOrgNameByOrgIdMySql(String orgId) throws Exception {
		return tblOrganizationMySqlMapper.selectOrgNameByOrgId(orgId);
	}

	@Override
	public void findDeptInfoByOrgIdMySql(String orgId, PageInfo<com.huabo.gkb.entitymysql.TblOrganization> pageInfo)
			throws Exception {
		pageInfo.setTlist(tblOrganizationMySqlMapper.selectDeptInfoByFatherId(Integer.parseInt(orgId),pageInfo));
		pageInfo.setTotalRecord(tblOrganizationMySqlMapper.selectDeptCountByFatherId(Integer.parseInt(orgId),pageInfo));
	}

	@Override
	public void findDeptListByOrgIdMySql(Integer orgId, PageInfo<com.huabo.gkb.entitymysql.TblOrganization> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblOrganizationMySqlMapper.selectDeptInfoByFather(orgId,pageInfo));
		pageInfo.setTotalRecord(this.tblOrganizationMySqlMapper.selectDeptCountByFather(orgId));
	}

	@Override
	public Integer selectCountByNameMySql(String companyName) throws Exception {
		return this.tblOrganizationMySqlMapper.selectCountByName(companyName);
	}

	@Override
	public BigDecimal findDeptInfoByorgNameMySql(String companyName) throws Exception {
		return this.tblOrganizationMySqlMapper.selectOrgIdByCompanyName(companyName);
	}

	@Override
	public List<com.huabo.gkb.entitymysql.TblOrganization> findDeptNameIdByCompanyIdMySql(String oid) throws Exception {
		return tblOrganizationMySqlMapper.selectDeptNameIdByCompanyId(oid);
	}

	
}
