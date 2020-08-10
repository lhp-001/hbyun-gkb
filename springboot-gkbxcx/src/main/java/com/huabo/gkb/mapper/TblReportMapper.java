package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.TblReport;

import tk.mybatis.mapper.common.Mapper;

public interface TblReportMapper extends Mapper<TblReport> {

	@SelectProvider(type = TblReportOracleSqlConfig.class,method = "findReport")
	List<TblReport> findReport();
	
}
