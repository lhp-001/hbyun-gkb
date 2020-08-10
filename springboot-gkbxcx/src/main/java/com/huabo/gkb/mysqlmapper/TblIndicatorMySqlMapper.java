package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblIndicatorMySqlMapper extends MySqlMapper<TblIndicator> {

	@SelectProvider(type = TblIndicatorMySqlConfig.class,method = "findByOrgid")
	List<TblIndicator> findByOrgid(@Param("orgid") String orgid, PageInfo<TblIndicator> pageInfo);

	@SelectProvider(type = TblIndicatorMySqlConfig.class,method = "findCountByOrgid")
	Integer findCountByOrgid(String orgid, PageInfo<TblIndicator> pageInfo);
	
}
