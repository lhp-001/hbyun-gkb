package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorIndicatorResult;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblIndicatorResultMySqlMapper extends MySqlMapper<TblMonitorIndicatorResult> {

	@SelectProvider(type = TblIndicatorResultMySqlConfig.class,method = "findByIndicatorid")
	List<TblMonitorIndicatorResult> findByIndicatorid(@Param("indicatorid") String indicatorid, PageInfo<TblMonitorIndicatorResult> pageInfo);
	
}
