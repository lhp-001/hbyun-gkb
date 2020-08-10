package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;

import tk.mybatis.mapper.common.Mapper;

public interface TblIndicatorMapper extends Mapper<TblIndicator> {

	@SelectProvider(type = TblIndicatorOracleSqlConfig.class,method = "findByOrgid")
	List<TblIndicator> findByOrgid(@Param("orgid") String orgid, PageInfo<TblIndicator> pageInfo);

	@SelectProvider(type = TblIndicatorOracleSqlConfig.class,method = "findCountByOrgid")
	Integer findCountByOrgid(String orgid, PageInfo<TblIndicator> pageInfo);
	
}
