package com.huabo.gkb.service;

import java.math.BigDecimal;
import java.util.List;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblOrganization;

public interface TblOrganizationService {

	List<TblOrganization> findDeptNameIdByCompanyId(String orgname) throws Exception;

	Integer findRegisterOrganizationCount(BigDecimal orgId, BigDecimal registerorgfatherid) throws Exception;
	/**
	 * 根据部门公司
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	Integer findCompanyIdByDeptId(String orgId) throws Exception;
	/**
	 * 根据部门id及注册组织类型，查询根公司
	 * @param orgId
	 * @param registerorgtype
	 * @return
	 * @throws Exception
	 */
	Integer findRootCompanyIdByDeptId(String orgId, Integer registerorgtype) throws Exception;

	void findDeptInfoByOrgId(String orgId, PageInfo<TblOrganization> pageInfo) throws Exception;

	String findOrgNameByOrgId(String orgId) throws Exception;

	Integer findSameDeptNoByOrgId(Integer orgId, String orgNo) throws Exception;

	void saveDeptInfo(TblOrganization org) throws Exception;

	List<TblOrganization> findHyOrgAllInfo() throws Exception;
	
	List<com.huabo.gkb.entitymysql.TblOrganization> findHyOrgAllInfoMySql() throws Exception;

	Integer selectCountByName(String companyName) throws Exception;

	BigDecimal findDeptInfoByorgName(String companyName) throws Exception;

	void findDeptListByOrgId(Integer orgId, PageInfo<TblOrganization> pageInfo) throws Exception;

	String findOrgNameByOrgIdMySql(String string) throws Exception;

	void findDeptInfoByOrgIdMySql(String string, PageInfo<com.huabo.gkb.entitymysql.TblOrganization> pageInfo) throws Exception;

	void findDeptListByOrgIdMySql(Integer orgId, PageInfo<com.huabo.gkb.entitymysql.TblOrganization> pageInfo) throws Exception;

	Integer selectCountByNameMySql(String companyName) throws Exception;

	BigDecimal findDeptInfoByorgNameMySql(String companyName) throws Exception;

	List<com.huabo.gkb.entitymysql.TblOrganization> findDeptNameIdByCompanyIdMySql(String oid) throws Exception;

	
}
