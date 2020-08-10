package com.huabo.gkb.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyOrgDeposit;
import com.huabo.gkb.entity.TblYyUserQuery;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TblYyOrgDepositMapper extends Mapper<TblYyOrgDeposit> {

	@SelectProvider(method="selectListByPageInfo",type=TblYyOrgDepositMapperSqlConfig.class)
	@Results({
		@Result(column="RECORDID",property="recordid"),
		@Result(column="QUERYTIME",property="querytime"),
		@Result(column="PAYMONEY",property="paymoney"),
		@Result(column="REPORTNAME",property="reportName"),
		@Result(column="ORGID",property="orgid"),
		@Result(column="REALNAME",property="staffname"),
	})
	List<TblYyUserQuery> selectListByPageInfo(PageInfo<TblYyUserQuery> pageInfo);

	@SelectProvider(method="selectCountByPageInfo",type=TblYyOrgDepositMapperSqlConfig.class  )
	Integer selectCountByPageInfo(PageInfo<TblYyUserQuery> pageInfo);

	@Select("SELECT * FROM TBL_YY_ORG_DEPOSIT WHERE ORGID = #{companyId}")
	TblYyOrgDeposit selectOrgDepositByOrgId(@Param("companyId")Integer companyId);

	@Select("SELECT NVL((NVL(TOTALMONEY,0)- NVL(TOTALPAYMONEY,0)),0) AS MONEY FROM TBL_YY_ORG_DEPOSIT WHERE ORGID = #{orgId}")
	String selectUrplusMoney(@Param("orgId")Integer orgId);

	@Select("SELECT (SELECT NVL(SUM(HBPRICE),0) FROM TBL_YY_PRICE WHERE INSTR(','||TBL_YY_REPORT_MODEL.PRICEID||',',','||PRICEID||',') > 0) AS MONEY FROM TBL_YY_REPORT_MODEL WHERE REPORTNAME = #{reportName}")
	String selectTotalTostMoney(@Param("reportName")String reportName);

	@SelectProvider(method="selectPromotionPriceList",type=TblYyOrgDepositMapperSqlConfig.class  )
	@Results({
		@Result(column="QUERYTIME",property="querytime"),
		@Result(column="PAYMONEY",property="paymoney"),
		@Result(column="ORGNAME",property="orgname"),
	})
	List<TblYyUserQuery> selectPromotionPriceList(PageInfo<TblYyUserQuery> pageInfo) throws Exception;

	@SelectProvider(method="selectCountPromotionPrice",type=TblYyOrgDepositMapperSqlConfig.class  )
	Integer selectCountPromotionPrice(PageInfo<TblYyUserQuery> pageInfo) throws Exception;

	@Select("SELECT SUM(PAYMONEY) AS PAYMONEY FROM TBL_YY_USER_QUERY WHERE ORGID IN (SELECT ORGID FROM TBL_WX_POPULARIZE_STAFF_ORGID WHERE STAFFID = #{staffId})")
	Double selectPromotionAllMoney(@Param("staffId")Integer staffId) throws Exception;

	@Select("SELECT (SELECT NVL(SUM(HBPRICE),0) FROM TBL_YY_PRICE WHERE INSTR(','||TBL_YY_REPORT_MODEL.PRICEID||',',','||PRICEID||',') > 0) AS MONEY FROM TBL_YY_REPORT_MODEL WHERE REPORTID = #{reportid}")
	Double selectReportTostMoney(@Param("reportid")BigDecimal reportid) throws Exception;

}
