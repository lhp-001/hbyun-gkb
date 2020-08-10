package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblCirculation;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblCirculationMySqlMapper extends MySqlMapper<TblCirculation> {

	@SelectProvider(type = TblCirculationMySqlConfig.class,method = "findSpByStaffid")
	List<TblCirculation> findSpByStaffid(@Param("staffid") String staffid, PageInfo<TblCirculation> pageInfo);
	
}
