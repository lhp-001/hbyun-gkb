package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblOrganization;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblOrganizationMySqlMapper extends MySqlMapper<TblOrganization>{

	@Results({
		@Result(column="ORGID",property="orgid"),
		@Result(column="ORGNAME",property="orgname")
	})
	@SelectProvider(type = TblOrganizationMySqlConfig.class,method = "selectDeptNameIdByCompanyId")
	List<TblOrganization> selectDeptNameIdByCompanyId(String orgId);

	@Insert("INSERT INTO TBL_ORGANIZATION(ORGNAME,FATHERORGID,ORGTYPE,STATUS,ISAUTONUMBER,OUTSIDEID,ORGCREATE) VALUES (#{orgname},#{fatherorgid},#{orgtype},#{status},#{isAutoNumber},#{outsideId},#{orgCreate})")
	@Options(useGeneratedKeys=true, keyProperty="orgid", keyColumn="ORGID")
	void insertOrgReturnOrgId(TblOrganization org) throws Exception;

	@InsertProvider(type = TblOrganizationMySqlConfig.class,method = "insertOrgRight")
	void insertOrgRight(@Param("rightList")List<String> rightList,@Param("orgid") BigDecimal orgid) throws Exception;
	
	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION o WHERE o.ORGTYPE != 0 AND OUTSIDEID = 3 AND FATHERORGID = #{registerorgfatherid} AND (ORGCREATE+30) > SYSDATE START WITH o.ORGID = #{orgId} CONNECT by PRIOR o.FATHERORGID = o.ORGID order BY o.ORGTYPE desc")
	Integer selectRegisterOrganizationCount(@Param("orgId") BigDecimal orgId,@Param("registerorgfatherid") BigDecimal registerorgfatherid) throws Exception;

	@Select({"select getParentOrgList(${orgId})"})
	Integer findCompanyIdByDeptId(@Param("orgId")String orgId) throws Exception;

	@Select("SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGTYPE = #{registerorgtype} AND  ORGTYPE != 0 AND ORGTYPE < 100  START WITH ORGID = #{orgId} CONNECT BY PRIOR FATHERORGID = ORGID ")
	Integer selectRootCompanyIdByDeptId(@Param("orgId")String orgId, @Param("registerorgtype")Integer registerorgtype);

	@SelectProvider(type = TblOrganizationMySqlConfig.class,method = "selectDeptInfoByFatherId")
	@Results({
		@Result(column="ORGID",property="orgid"),
		@Result(column="ORGNAME",property="orgname"),
		@Result(column="STAFFCOUNT",property="staffCount")
	})
	List<TblOrganization> selectDeptInfoByFatherId(Integer comId, PageInfo<TblOrganization> pageInfo);

	@Select("SELECT ORGNAME FROM TBL_ORGANIZATION WHERE ORGID = #{orgId}")
	String selectOrgNameByOrgId(@Param("orgId")String orgId);

	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION WHERE ORGNUMBER = #{orgNo} AND ORGID IN (${deptId})")
	Integer findSameDeptNoByOrgId(@Param("deptId")String deptId, @Param("orgNo")String orgNo) throws Exception;

	@Insert("INSERT INTO TBL_ORGANIZATION(ORGNAME,FATHERORGID,ORGNUMBER,ORGMENO,MEMO,ORGTYPE,STATUS) VALUES (#{orgname},#{fatherorgid},#{orgnumber},#{orgmeno},#{memo},#{orgtype},#{status})")
	void insertDeptInfo(TblOrganization org) throws Exception;

	@Select("SELECT ORGID,ORGNAME FROM TBL_ORGANIZATION WHERE ORGTYPE = 100 and FATHERORGID = (SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGNAME = '行业' AND ORGTYPE>=100)  ORDER BY ORDERID ASC")
	List<TblOrganization> selectHyOrgInfo() throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION WHERE ORGNAME = #{companyName}")
	Integer selectCountByName(@Param("companyName")String companyName) throws Exception;

	@Select("SELECT * FROM TBL_ORGANIZATION WHERE ORGID = (SELECT getParentOrgList(${orgid}))")
	TblOrganization selectOrgInfoByChildren(@Param("orgid")BigDecimal orgid) throws Exception;

	@Select("SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGNAME = #{orgName}")
	BigDecimal selectOrgIdByCompanyName(@Param("orgName")String companyName);

	
	@SelectProvider(type = TblOrganizationMySqlConfig.class,method = "selectDeptInfoByFather")
	List<TblOrganization> selectDeptInfoByFather(Integer orgId, PageInfo<TblOrganization> pageInfo) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION TOG WHERE TOG.ORGTYPE = 0 AND TOG.FATHERORGID = #{orgId}")
	Integer selectDeptCountByFatherId(@Param("orgId")Integer orgId, PageInfo<TblOrganization> pageInfo) throws Exception;
	
	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION WHERE FATHERORGID = #{orgId} AND ORGTYPE = 0 AND STATUS = 0 ORDER BY ORGID")
	Integer selectDeptCountByFather(Integer orgId);

	@Select({"select getChildrenDeptList(${orgId)"})
	String findDeptIdsByOrgId(Integer orgId) throws Exception;
}
