package com.huabo.gkb.mapper;

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
import com.huabo.gkb.entity.TblOrganization;

import tk.mybatis.mapper.common.Mapper;

public interface TblOrganizationMapper extends Mapper<TblOrganization>{

	@Results({
		@Result(column="ORGID",property="orgid"),
		@Result(column="ORGNAME",property="orgname")
	})
	@SelectProvider(type = TblOrganizationOracleSqlConfig.class,method = "selectDeptNameIdByCompanyId")
	List<TblOrganization> selectDeptNameIdByCompanyId(String orgId);

	@Insert("INSERT INTO TBL_ORGANIZATION(ORGID,ORGNAME,FATHERORGID,ORGTYPE,STATUS,ISAUTONUMBER,OUTSIDEID,ORGCREATE) VALUES (HIBERNATE_SEQUENCE.NEXTVAL,#{orgname},#{fatherorgid},#{orgtype},#{status},#{isAutoNumber},#{outsideId},#{orgCreate})")
	@Options(useGeneratedKeys=true, keyProperty="orgid", keyColumn="ORGID")
	void insertOrgReturnOrgId(TblOrganization org) throws Exception;

	@InsertProvider(type = TblOrganizationOracleSqlConfig.class,method = "insertOrgRight")
	void insertOrgRight(@Param("rightList")List<String> rightList,@Param("orgid") BigDecimal orgid) throws Exception;
	
	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION o WHERE o.ORGTYPE != 0 AND OUTSIDEID = 3 AND FATHERORGID = #{registerorgfatherid} AND (ORGCREATE+30) > SYSDATE START WITH o.ORGID = #{orgId} CONNECT by PRIOR o.FATHERORGID = o.ORGID order BY o.ORGTYPE desc")
	Integer selectRegisterOrganizationCount(@Param("orgId") BigDecimal orgId,@Param("registerorgfatherid") BigDecimal registerorgfatherid) throws Exception;

	@Select("SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGTYPE = ( SELECT MAX(ORGTYPE) FROM TBL_ORGANIZATION WHERE ORGTYPE != 0 AND ORGTYPE < 100  START WITH ORGID = #{orgId} CONNECT BY PRIOR FATHERORGID = ORGID ) AND  ORGTYPE != 0 AND ORGTYPE < 100  START WITH ORGID = #{orgId} CONNECT BY PRIOR FATHERORGID = ORGID ")
	Integer findCompanyIdByDeptId(@Param("orgId")String orgId) throws Exception;

	@Select("SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGTYPE = #{registerorgtype} AND  ORGTYPE != 0 AND ORGTYPE < 100  START WITH ORGID = #{orgId} CONNECT BY PRIOR FATHERORGID = ORGID ")
	Integer selectRootCompanyIdByDeptId(@Param("orgId")String orgId, @Param("registerorgtype")Integer registerorgtype);

	@SelectProvider(type = TblOrganizationOracleSqlConfig.class,method = "selectDeptInfoByFatherId")
	@Results({
		@Result(column="ORGID",property="orgid"),
		@Result(column="ORGNAME",property="orgname"),
		@Result(column="STAFFCOUNT",property="staffCount")
	})
	List<TblOrganization> selectDeptInfoByFatherId(Integer comId, PageInfo<TblOrganization> pageInfo);

	@Select("SELECT ORGNAME FROM TBL_ORGANIZATION WHERE ORGID = #{orgId}")
	String selectOrgNameByOrgId(@Param("orgId")String orgId);

	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION WHERE ORGTYPE = 0 AND ORGNUMBER = #{orgNo} START WITH FATHERORGID = #{orgId} AND ORGTYPE = 0 CONNECT BY PRIOR ORGID = FATHERORGID")
	Integer findSameDeptNoByOrgId(@Param("orgId")Integer orgId, @Param("orgNo")String orgNo) throws Exception;

	@Insert("INSERT INTO TBL_ORGANIZATION(ORGID,ORGNAME,FATHERORGID,ORGNUMBER,ORGMENO,MEMO,ORGTYPE,STATUS) VALUES (HIBERNATE_SEQUENCE.NEXTVAL,#{orgname},#{fatherorgid},#{orgnumber},#{orgmeno},#{memo},#{orgtype},#{status})")
	void insertDeptInfo(TblOrganization org) throws Exception;

	@Select("SELECT ORGID,ORGNAME FROM TBL_ORGANIZATION WHERE ORGTYPE = 100 and FATHERORGID = (SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGNAME = '行业' AND ORGTYPE>=100)  ORDER BY ORDERID ASC")
	List<TblOrganization> selectHyOrgInfo() throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION WHERE ORGNAME = #{companyName}")
	Integer selectCountByName(@Param("companyName")String companyName) throws Exception;

	@Select("SELECT * FROM TBL_ORGANIZATION WHERE ORGTYPE != 0 AND ORGTYPE < 100 and STATUS = 0  AND ORGTYPE = ( SELECT MAX(ORGTYPE) FROM TBL_ORGANIZATION WHERE ORGTYPE != 0 AND ORGTYPE < 100 and STATUS = 0 START WITH ORGID = #{orgid} CONNECT BY PRIOR FATHERORGID = ORGID ) START WITH ORGID = #{orgid} CONNECT BY PRIOR FATHERORGID = ORGID ")
	TblOrganization selectOrgInfoByChildren(@Param("orgid")BigDecimal orgid) throws Exception;

	@Select("SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGNAME = #{orgName}")
	BigDecimal selectOrgIdByCompanyName(@Param("orgName")String companyName);

	
	@SelectProvider(type = TblOrganizationOracleSqlConfig.class,method = "selectDeptInfoByFather")
	List<TblOrganization> selectDeptInfoByFather(Integer orgId, PageInfo<TblOrganization> pageInfo) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION TOG WHERE TOG.ORGTYPE = 0 AND TOG.FATHERORGID = #{orgId}")
	Integer selectDeptCountByFatherId(@Param("orgId")Integer orgId, PageInfo<TblOrganization> pageInfo) throws Exception;
	
	@Select("SELECT COUNT(0) FROM TBL_ORGANIZATION WHERE FATHERORGID = #{orgId} AND ORGTYPE = 0 AND STATUS = 0 ORDER BY ORGID")
	Integer selectDeptCountByFather(Integer orgId);
}
