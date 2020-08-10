package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface TblObjectMapper{


	@SelectProvider(type = TblObjectOracleSqlConfig.class,method = "findRuleResult")
	List<Object[]> findRuleResult(@Param("sql") String sql);
	
	@SelectProvider(type = TblObjectOracleSqlConfig.class,method = "findRuleResult")
	Integer findRuleResultCount(@Param("sql") String sql);
}
