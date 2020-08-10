package com.huabo.gkb.service;

import java.util.Map;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblyyCompany;

public interface TblYyCompanyService {

	void findCompanyInfoByPageInfo(PageInfo<TblyyCompany> pageInfo) throws Exception;

	Integer addCompanyList(String chooseName, Integer staffId, String orgId) throws Exception;

	void cancelCompany(String string) throws Exception;

	Integer removeTeamInfoCompany(String[] chooseids, Integer teamid) throws Exception;

	void addReportModel(Integer companyid, String companyName, String orgId, Integer staffId) throws Exception;

	Map<String, String> findReportInfo(Integer companyid, String orgId, Integer staffId) throws Exception;

	void modifyReportJkInfo(String priceIds, String reportId) throws Exception;

	void findCompanyInfoByPageInfoMySql(PageInfo<com.huabo.gkb.entitymysql.TblyyCompany> pageInfo) throws Exception;


}
