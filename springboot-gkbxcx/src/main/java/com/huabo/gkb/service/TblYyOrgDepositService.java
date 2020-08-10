package com.huabo.gkb.service;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyUserQuery;



public interface TblYyOrgDepositService {

	String findUrplusMoney(Integer orgid) throws Exception;

	Double findTostMoney(String reportName) throws Exception;

	void findCostPircePageInfo(PageInfo<TblYyUserQuery> pageInfo) throws Exception;

	Double calBalanceMoney(String orgId) throws Exception;

	void findPromotionPircePageInfo(PageInfo<TblYyUserQuery> pageInfo) throws Exception;

	Double findPromotionAllMoney(Integer staffId) throws Exception ;

	void findCostPircePageInfoMySql(PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery> pageInfo) throws Exception;

	void findPromotionPircePageInfoMySql(PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery> pageInfo) throws Exception;

	Double findPromotionAllMoneyMySql(Integer staffId) throws Exception;
	
}
