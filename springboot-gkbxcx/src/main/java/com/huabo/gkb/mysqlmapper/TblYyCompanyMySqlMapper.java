package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblyyCompany;

import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface TblYyCompanyMySqlMapper extends MySqlMapper<TblyyCompany> {

	@SelectProvider(method="selectCompanyListByPageInfo",type = TblYyCompanyMySqlConfig.class)
	List<TblyyCompany> selectCompanyListByPageInfo(PageInfo<TblyyCompany> pageInfo) throws Exception;

	@SelectProvider(method="selectCompanyCountByPageInfo",type = TblYyCompanyMySqlConfig.class)
	Integer selectCompanyCountByPageInfo(PageInfo<TblyyCompany> pageInfo) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_YY_COMPANY WHERE teamid IS NULL AND STAFFID = #{staffId} AND companyName IN ${name} AND CSTATUS = 1 ")
	Integer checkNameByList(@Param("staffId") Integer staffId,@Param("name") String name) throws Exception;

	@Select("SELECT * FROM TBL_YY_COMPANY WHERE teamid IS NULL AND STAFFID = #{staffId} AND companyName IN ${name} AND CSTATUS = 0 ")
	List<TblyyCompany> selectNoUserComByComName(@Param("staffId") Integer staffId,@Param("name")  String name) throws Exception;

	@Update("UPDATE TBL_YY_COMPANY SET CSTATUS = #{cstatus} WHERE companyName = #{companyName} AND teamid IS NULL AND STAFFID = #{staffId} AND CSTATUS = 0 ")
	void startCompanyByInfo(@Param("companyName")String companyName, @Param("cstatus")Integer cstatus, @Param("staffId")Integer staffId) throws Exception;

	@Update("UPDATE TBL_YY_COMPANY SET CSTATUS = 0 WHERE companyid IN ${ids}")
	void cancelCompany(@Param("ids")String ids) throws Exception;

	@Select("SELECT * FROM TBL_YY_COMPANY WHERE companyid IN ${ids}")
	List<TblyyCompany> selectListByIds(@Param("ids")String ids) throws Exception;

	@SelectProvider(method="checkNameByRemoveCom",type = TblYyCompanyMySqlConfig.class)
	Integer checkNameByRemoveCom(@Param("companyName")String companyName,@Param("teamid") Integer teamid,@Param("staffId") Integer staffId) throws Exception;

	@SelectProvider(method="checkNAddOrUpdateComPany",type = TblYyCompanyMySqlConfig.class)
	TblyyCompany checkNAddOrUpdateComPany(String companyName, Integer teamid, Integer staffId) throws Exception;

	@Update("UPDATE TBL_YY_COMPANY SET CSTATUS = 1 WHERE companyid = #{companyid}")
	void startCompanyById(@Param("companyid")Integer companyid) throws Exception;

	@Update("UPDATE TBL_YY_COMPANY SET teamid = #{teamid} WHERE companyid = #{companyid}")
	void updateTeamInfoById(@Param("teamid")Integer teamid,@Param("companyid") Integer companyid);

	@Update("UPDATE TBL_YY_COMPANY SET REPORTID = #{reportid} WHERE companyid = #{companyid}")
	void bindReportIdBycompanyid(@Param("companyid")Integer companyid,@Param("reportid") BigDecimal reportid) throws Exception;

	@Select("SELECT companyid,companyName,teamid,createDate,STAFFID,ORGID,REPORTID,fxtype FROM Tbl_yy_Company")
	TblyyCompany selectByPrimaryKey(Integer companyid) throws Exception;

	@Insert("INSERT INTO  tbl_yy_company(STAFFID,ORGID,companyName,createDate,CSTATUS) VALUE(#{staffId},#{orgId},#{companyName},#{createDate},#{cstatus})")
	void insertSelective(TblyyCompany com) throws Exception;


}
