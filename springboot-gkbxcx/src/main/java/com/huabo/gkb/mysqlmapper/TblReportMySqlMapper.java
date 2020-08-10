package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entitymysql.TblReport;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblReportMySqlMapper extends MySqlMapper<TblReport> {

	@SelectProvider(type = TblReportMySqlConfig.class,method = "findReport")
	List<TblReport> findReport();
	
}
