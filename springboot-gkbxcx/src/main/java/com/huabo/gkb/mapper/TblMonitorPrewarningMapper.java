package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorPrewarning;

import tk.mybatis.mapper.common.Mapper;

public interface TblMonitorPrewarningMapper extends Mapper<TblMonitorPrewarning> {

	@SelectProvider(type = TblMonitorPrewarningOracleSqlConfig.class,method = "findByOrgid")
	List<TblMonitorPrewarning> findByOrgid(@Param("ruleid") String ruleid, PageInfo<TblMonitorPrewarning> pageInfo);
	
	@SelectProvider(type = TblMonitorPrewarningOracleSqlConfig.class,method = "findSignIdByRuleid")
	String findSignIdByRuleid(@Param("ruleid") String ruleid);
	
	@SelectProvider(type = TblMonitorPrewarningOracleSqlConfig.class,method = "findRuleResult")
	List<Object> findRuleResult(@Param("sql") String sql, PageInfo<Object> pageInfo);

	@SelectProvider(type = TblMonitorPrewarningOracleSqlConfig.class,method = "findGzDetailCountByOrgid")
	Integer findGzDetailCountByOrgid(String ruleid, PageInfo<TblMonitorPrewarning> pageInfo);
}
