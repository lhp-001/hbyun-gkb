package com.huabo.gkb.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblStaff;
import com.huabo.gkb.mapper.TblOrganizationMapper;
import com.huabo.gkb.mapper.TblStaffMapper;
import com.huabo.gkb.mysqlmapper.TblOrganizationMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblStaffMySqlMapper;
import com.huabo.gkb.service.TblStaffService;

@Service("tblStaffServiceImpl")
public class TblStaffServiceImpl extends BaseServiceImpl<TblStaff, BigDecimal> implements TblStaffService {
	@Resource
	public TblStaffMapper tblStaffMapper;
	@Resource
	public TblOrganizationMapper tblOrganizationMapper;
	
	
	
	@Resource
	public TblStaffMySqlMapper tblStaffMySqlMapper;
	@Resource
	public TblOrganizationMySqlMapper tblOrganizationMySqlMapper;
	

	@Override
	public TblStaff findStaffUserInfo(String staffId) throws Exception {
		return tblStaffMapper.selectStaffUserInfo(staffId);
	}

	@Override
	public void findUserInfoByOrgId(String orgId, PageInfo<TblStaff> pageInfo) throws Exception {
		pageInfo.setTlist(tblStaffMapper.selectUserInfoByOrgId(orgId,pageInfo));
		pageInfo.setTotalRecord(tblStaffMapper.selectUserCountByOrgId(orgId));
	}

	@Override
	public TblStaff findStaff(String staffId) throws Exception {
		return tblStaffMapper.selectStaff(staffId);
	}

	@Override
	public TblStaff findStaffByMiblePhone(String miblePhone) throws Exception {
		return this.tblStaffMapper.selectByMiblePhone(miblePhone);
	}

	@Override
	public TblStaff selectWxappAdmin(BigDecimal orgId) throws Exception {
		return this.tblStaffMapper.selectWxappAdmin(orgId);
	}

	@Override
	public void findUserInfoByDeptIdIncludOrg(String orgId, PageInfo<TblStaff> pageInfo) throws Exception {
		Integer comId = tblOrganizationMapper.findCompanyIdByDeptId(orgId);
		pageInfo.setTlist(this.tblStaffMapper.selectUserInfoByDeptIdIncludOrg(orgId,comId,pageInfo));
		pageInfo.setTotalRecord(this.tblStaffMapper.selectUserCountByDeptIdIncludOrg(orgId,comId));
	}

	@Override
	public void changeDeptInfoStaff(String staffids, Integer orgid) throws Exception {
		if(SystemStaticValue.DataType == 0) {
			this.tblStaffMySqlMapper.updateDeptInfoStaff(staffids,orgid);
		}else {
			this.tblStaffMapper.updateDeptInfoStaff(staffids,orgid);
		}
	}

	@Override
	public com.huabo.gkb.entitymysql.TblStaff findStaffUserInfoMySql(String staffId) throws Exception {
		return tblStaffMySqlMapper.selectStaffUserInfo(staffId);
	}

	@Override
	public void findUserInfoByOrgIdMySql(String orgId, PageInfo<com.huabo.gkb.entitymysql.TblStaff> pageInfo)
			throws Exception {
		pageInfo.setTlist(tblStaffMySqlMapper.selectUserInfoByOrgId(orgId,pageInfo));
		pageInfo.setTotalRecord(tblStaffMySqlMapper.selectUserCountByOrgId(orgId));
	}

	@Override
	public void findUserInfoByDeptIdIncludOrgMySql(String orgId, PageInfo<com.huabo.gkb.entitymysql.TblStaff> pageInfo)
			throws Exception {
		Integer comId = tblOrganizationMySqlMapper.findCompanyIdByDeptId(orgId);
		pageInfo.setTlist(this.tblStaffMySqlMapper.selectUserInfoByDeptIdIncludOrg(orgId,comId,pageInfo));
		pageInfo.setTotalRecord(this.tblStaffMySqlMapper.selectUserCountByDeptIdIncludOrg(orgId,comId));
	}

	@Override
	public com.huabo.gkb.entitymysql.TblStaff selectWxappAdminMySql(BigDecimal orgId) throws Exception {
		return this.tblStaffMySqlMapper.selectWxappAdmin(orgId);
	}


}
