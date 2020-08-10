package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorRule;

import tk.mybatis.mapper.common.Mapper;

public interface TblMonitorRuleMapper extends Mapper<TblMonitorRule> {

	@SelectProvider(type = TblMonitorRuleOracleSqlConfig.class,method = "findByOrgid")
	List<TblMonitorRule> findByOrgid(@Param("orgid") String orgid, PageInfo<TblMonitorRule> pageInfo);
	
	@SelectProvider(type = TblMonitorRuleOracleSqlConfig.class,method = "findBookByRuleid")
	String findBookByRuleid(@Param("ruleid") String ruleid);

	@SelectProvider(type = TblMonitorRuleOracleSqlConfig.class,method = "findCountByOrgid")
	Integer findCountByOrgid(String orgid, PageInfo<TblMonitorRule> pageInfo);
}
