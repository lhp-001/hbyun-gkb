package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblCirculation;

import tk.mybatis.mapper.common.Mapper;

public interface TblCirculationMapper extends Mapper<TblCirculation> {

	@SelectProvider(type = TblCirculationOracleSqlConfig.class,method = "findSpByStaffid")
	List<TblCirculation> findSpByStaffid(@Param("staffid") String staffid, PageInfo<TblCirculation> pageInfo);
	
}
