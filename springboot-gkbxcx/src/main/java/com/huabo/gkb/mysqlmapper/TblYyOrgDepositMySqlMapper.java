package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblYyOrgDeposit;
import com.huabo.gkb.entitymysql.TblYyUserQuery;

import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface TblYyOrgDepositMySqlMapper extends MySqlMapper<TblYyOrgDeposit> {

	@SelectProvider(method="selectListByPageInfo",type=TblYyOrgDepositMySqlConfig.class)
	@Results({
		@Result(column="RECORDID",property="recordid"),
		@Result(column="QUERYTIME",property="querytime"),
		@Result(column="PAYMONEY",property="paymoney"),
		@Result(column="REPORTNAME",property="reportName"),
		@Result(column="ORGID",property="orgid"),
		@Result(column="REALNAME",property="staffname"),
	})
	List<TblYyUserQuery> selectListByPageInfo(PageInfo<TblYyUserQuery> pageInfo);

	@SelectProvider(method="selectCountByPageInfo",type=TblYyOrgDepositMySqlConfig.class  )
	Integer selectCountByPageInfo(PageInfo<TblYyUserQuery> pageInfo);

	@Select("SELECT * FROM TBL_YY_ORG_DEPOSIT WHERE ORGID = #{companyId}")
	TblYyOrgDeposit selectOrgDepositByOrgId(@Param("companyId")Integer companyId);

	@Select("SELECT IFNULL((IFNULL(TOTALMONEY,0)- IFNULL(TOTALPAYMONEY,0)),0) AS MONEY FROM TBL_YY_ORG_DEPOSIT WHERE ORGID = #{orgId}")
	String selectUrplusMoney(@Param("orgId")Integer orgId);

	@Select("SELECT IFNULL(SUM(HBPRICE),0) FROM TBL_YY_PRICE WHERE FIND_IN_SET(PRICEID,(SELECT PRICEID FROM TBL_YY_REPORT_MODEL WHERE REPORTNAME = #{reportName}))")
	String selectTotalTostMoney(@Param("reportName")String reportName);

	@SelectProvider(method="selectPromotionPriceList",type=TblYyOrgDepositMySqlConfig.class  )
	@Results({
		@Result(column="QUERYTIME",property="querytime"),
		@Result(column="PAYMONEY",property="paymoney"),
		@Result(column="ORGNAME",property="orgname"),
	})
	List<TblYyUserQuery> selectPromotionPriceList(PageInfo<TblYyUserQuery> pageInfo) throws Exception;

	@SelectProvider(method="selectCountPromotionPrice",type=TblYyOrgDepositMySqlConfig.class  )
	Integer selectCountPromotionPrice(PageInfo<TblYyUserQuery> pageInfo) throws Exception;

	@Select("SELECT IFNULL(SUM(PAYMONEY),0) AS PAYMONEY FROM TBL_YY_USER_QUERY WHERE ORGID IN (SELECT ORGID FROM TBL_WX_POPULARIZE_STAFF_ORGID WHERE STAFFID = #{staffId})")
	Double selectPromotionAllMoney(@Param("staffId")Integer staffId) throws Exception;

	@Select("SELECT IFNULL(SUM(HBPRICE),0) FROM TBL_YY_PRICE WHERE FIND_IN_SET(PRICEID,(SELECT PRICEID FROM TBL_YY_REPORT_MODEL WHERE REPORTID = #{reportid}))")
	Double selectReportTostMoney(@Param("reportid")BigDecimal reportid) throws Exception;

	
	@Update("UPDATE tbl_yy_org_deposit SET TOTALPAYMONEY = #{totalmoney} WHERE DEPOSITID = #{depositid}")
	void updateTotalMoneyById(TblYyOrgDeposit yod) throws Exception;

	@Insert("INSERT INTO tbl_yy_org_deposit(LASTDATE,ORGID,TOTALMONEY) VALUE(#{lastdate},#{orgId},#{totalmoney})")
	void insertSelective(TblYyOrgDeposit yod);

	@Update("UPDATE tbl_yy_org_deposit SET LASTDATE = #{lastdate} , TOTALMONEY = #{totalmoney} WHERE DEPOSITID = #{depositid}")
	void updateByPrimaryKeySelective(TblYyOrgDeposit yod);

}
