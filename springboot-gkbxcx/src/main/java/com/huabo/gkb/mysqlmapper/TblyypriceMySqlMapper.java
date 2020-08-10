package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.huabo.gkb.entitymysql.Tblyyprice;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblyypriceMySqlMapper extends MySqlMapper<Tblyyprice>{

	@Select("SELECT PRICEID FROM TBL_YY_REPORT_MODEL WHERE REPORTNAME = #{reportName}")
	String selectTblyyUserPriceByName(@Param("reportName")String reportName) throws Exception;
	
	@Select("SELECT PRICEID,INTERFACENAME,ACCOUNTRULE,PRICE,ANNUALPRICE,COMPANYID,HBPRICE FROM TBL_YY_PRICE WHERE PRICEID IN (${priceIds})")
	List<Tblyyprice> selectListByPriceIds(@Param("priceIds")String priceIds) throws Exception;

	@Select("SELECT * FROM TBL_YY_PRICE WHERE PRICEID NOT IN(50,1,3,10,11,13,21,26,27,29,31,35,39,7,37)")
	List<Tblyyprice> selecAllList() throws Exception;

	@Select("SELECT * FROM TBL_YY_PRICE WHERE FIND_IN_SET(PRICEID,(SELECT PRICEID FROM TBL_YY_REPORT_MODEL WHERE REPORTID = #{reportid}))")
	List<Tblyyprice> selectOwenerList(@Param("reportid")BigDecimal reportid) throws Exception;
}
