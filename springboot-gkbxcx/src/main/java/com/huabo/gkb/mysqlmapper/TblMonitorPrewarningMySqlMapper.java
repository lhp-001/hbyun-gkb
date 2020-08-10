package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorPrewarning;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblMonitorPrewarningMySqlMapper extends MySqlMapper<TblMonitorPrewarning> {

	@SelectProvider(type = TblMonitorPrewarningMySqlConfig.class,method = "findByOrgid")
	List<TblMonitorPrewarning> findByOrgid(@Param("ruleid") String ruleid, PageInfo<TblMonitorPrewarning> pageInfo);
	
	@SelectProvider(type = TblMonitorPrewarningMySqlConfig.class,method = "findSignIdByRuleid")
	String findSignIdByRuleid(@Param("ruleid") String ruleid);
	
	@SelectProvider(type = TblMonitorPrewarningMySqlConfig.class,method = "findRuleResult")
	List<Object> findRuleResult(@Param("sql") String sql, PageInfo<Object> pageInfo);

	@SelectProvider(type = TblMonitorPrewarningMySqlConfig.class,method = "findGzDetailCountByOrgid")
	Integer findGzDetailCountByOrgid(String ruleid, PageInfo<TblMonitorPrewarning> pageInfo);
}
