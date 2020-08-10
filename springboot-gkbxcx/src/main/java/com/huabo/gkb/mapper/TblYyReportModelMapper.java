package com.huabo.gkb.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.TblYyReportModel;

import tk.mybatis.mapper.common.Mapper;


@Repository
public interface TblYyReportModelMapper extends Mapper<TblYyReportModel> {

	@Update("UPDATE TBL_YY_REPORT_MODEL SET PRICEID = #{priceIds} WHERE REPORTID = #{reportId}")
	void updateJkInfoById(@Param("priceIds")String priceIds,@Param("reportId") String reportId) throws Exception;

}
