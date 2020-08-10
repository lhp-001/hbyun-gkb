package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorRule;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblMonitorRuleMySqlMapper extends MySqlMapper<TblMonitorRule> {

	@SelectProvider(type = TblMonitorRuleMySqlConfig.class,method = "findByOrgid")
	List<TblMonitorRule> findByOrgid(@Param("orgid") String orgid, PageInfo<TblMonitorRule> pageInfo);
	
	@SelectProvider(type = TblMonitorRuleMySqlConfig.class,method = "findBookByRuleid")
	String findBookByRuleid(@Param("ruleid") String ruleid);

	@SelectProvider(type = TblMonitorRuleMySqlConfig.class,method = "findCountByOrgid")
	Integer findCountByOrgid(String orgid, PageInfo<TblMonitorRule> pageInfo);
}
