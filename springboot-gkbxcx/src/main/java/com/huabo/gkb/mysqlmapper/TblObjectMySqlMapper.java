package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface TblObjectMySqlMapper{


	@SelectProvider(type = TblObjectMySqlConfig.class,method = "findRuleResult")
	List<Object[]> findRuleResult(@Param("sql") String sql);
	
	@SelectProvider(type = TblObjectMySqlConfig.class,method = "findRuleResult")
	Integer findRuleResultCount(@Param("sql") String sql);
}
