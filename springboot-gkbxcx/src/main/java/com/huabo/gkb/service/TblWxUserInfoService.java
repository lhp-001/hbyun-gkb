package com.huabo.gkb.service;

import java.util.List;

import com.huabo.gkb.entity.TblOrganization;
import com.huabo.gkb.entity.TblStaff;
import com.huabo.gkb.entity.TblWxUserInfo;

public interface TblWxUserInfoService extends BaseService<TblWxUserInfo, Integer> {

	TblWxUserInfo findWxUserInfoByOpenId(String openId) throws Exception;

	Integer findCountByUserOpenId(String string) throws Exception;

	List<Integer> findCountByUserOpenIdAndCompanyName(String openId, String companyName) throws Exception;

	TblWxUserInfo insertUserOrgStaffRightInfo(TblWxUserInfo userInfo, TblStaff staff, TblOrganization org, String staffId) throws Exception;

	void updateAvatarUrl(TblWxUserInfo userInfo) throws Exception;

	TblWxUserInfo insertUserOrgStaffRightInfo(TblWxUserInfo userInfo, TblStaff staff, String orgId) throws Exception;

	com.huabo.gkb.entitymysql.TblWxUserInfo findWxUserInfoByOpenIdMySql(String openId) throws Exception;

	void updateAvatarUrlMySql(com.huabo.gkb.entitymysql.TblWxUserInfo userInfo) throws Exception;

	Integer findCountByUserOpenIdMySql(String openId) throws Exception;

	com.huabo.gkb.entitymysql.TblWxUserInfo insertUserOrgStaffRightInfoMySql(
			com.huabo.gkb.entitymysql.TblWxUserInfo userInfo, com.huabo.gkb.entitymysql.TblStaff staff,
			com.huabo.gkb.entitymysql.TblOrganization org, String staffId) throws Exception;

	com.huabo.gkb.entitymysql.TblWxUserInfo insertUserOrgStaffRightInfoMySql(
			com.huabo.gkb.entitymysql.TblWxUserInfo userInfo, com.huabo.gkb.entitymysql.TblStaff staff, String orgId) throws Exception;

}
