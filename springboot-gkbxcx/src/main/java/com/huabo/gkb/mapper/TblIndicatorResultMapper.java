package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorIndicatorResult;

import tk.mybatis.mapper.common.Mapper;

public interface TblIndicatorResultMapper extends Mapper<TblMonitorIndicatorResult> {

	@SelectProvider(type = TblIndicatorResultOracleSqlConfig.class,method = "findByIndicatorid")
	List<TblMonitorIndicatorResult> findByIndicatorid(@Param("indicatorid") String indicatorid, PageInfo<TblMonitorIndicatorResult> pageInfo);
	
}
