package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entitymysql.TblYyReportModel;

import tk.mybatis.mapper.common.MySqlMapper;


@Repository
public interface TblYyReportModelMySqlMapper extends MySqlMapper<TblYyReportModel> {

	@Update("UPDATE TBL_YY_REPORT_MODEL SET PRICEID = #{priceIds} WHERE REPORTID = #{reportId}")
	void updateJkInfoById(@Param("priceIds")String priceIds,@Param("reportId") String reportId) throws Exception;

	@Select("SELECT * FROM TBL_YY_REPORT_MODEL WHERE REPORTID = #{reportid}")
	TblYyReportModel selectByPrimaryKey(BigDecimal reportid) throws Exception;

	@Insert("INSERT INTO tbl_yy_report_model(REPORTNAME,STAFFID,ORGID) VALUE (#{reportname},#{staffId},#{orgId})")
	void insertSelective(TblYyReportModel model) throws Exception;

}
