package com.huabo.gkb.mysqlmapper;

import org.apache.ibatis.annotations.Select;

import com.huabo.gkb.entitymysql.Tblyyuser;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblyyuserMySqlMapper extends MySqlMapper<Tblyyuser> {
	
	@Select("SELECT COUNT(*) FROM TBL_YY_USER_QUERY WHERE QUERYSTAFF IN (SELECT STAFFID FROM TBL_STAFF WHERE FIND_IN_SET(ORGID,(SELECT findAllOrgUser(1))))")
	Integer selectByUserOne(Integer companyId) throws Exception;


}
