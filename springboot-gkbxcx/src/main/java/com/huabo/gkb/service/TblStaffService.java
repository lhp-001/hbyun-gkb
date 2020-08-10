package com.huabo.gkb.service;

import java.math.BigDecimal;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblStaff;

public interface TblStaffService {

	TblStaff findStaffUserInfo(String staffId) throws Exception;

	void findUserInfoByOrgId(String orgId, PageInfo<TblStaff> pageInfo) throws Exception;

	TblStaff findStaff(String staffId) throws Exception;

	TblStaff findStaffByMiblePhone(String miblePhone) throws Exception;

	TblStaff selectWxappAdmin(BigDecimal orgId) throws Exception;

	void findUserInfoByDeptIdIncludOrg(String orgId, PageInfo<TblStaff> pageInfo) throws Exception;

	void changeDeptInfoStaff(String staffids, Integer orgid) throws Exception;

	com.huabo.gkb.entitymysql.TblStaff findStaffUserInfoMySql(String staffId) throws Exception;

	void findUserInfoByOrgIdMySql(String orgId, PageInfo<com.huabo.gkb.entitymysql.TblStaff> pageInfo) throws Exception;

	void findUserInfoByDeptIdIncludOrgMySql(String orgId, PageInfo<com.huabo.gkb.entitymysql.TblStaff> pageInfo) throws Exception;

	com.huabo.gkb.entitymysql.TblStaff selectWxappAdminMySql(BigDecimal bigDecimal) throws Exception;


}
