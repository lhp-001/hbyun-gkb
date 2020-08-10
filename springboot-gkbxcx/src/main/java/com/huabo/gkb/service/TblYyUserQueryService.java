package com.huabo.gkb.service;

public interface TblYyUserQueryService {

	void dealUserQuery(Integer companyId, String orgId, String reportName) throws Exception;

	void dealUserQueryMySql(Integer companyId, String staffId, String priceName) throws Exception;

}
