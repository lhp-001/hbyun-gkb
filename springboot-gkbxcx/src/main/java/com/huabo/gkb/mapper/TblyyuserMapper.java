package com.huabo.gkb.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.huabo.gkb.entity.Tblyyuser;

import tk.mybatis.mapper.common.Mapper;

public interface TblyyuserMapper extends Mapper<Tblyyuser> {
	
	@Insert("INSERT INTO TBL_YY_USER_NUMBER(NUMBERID,PRICEID,STAFFID,COMPANYID,CREATEDATE) VALUES(HIBERNATE_SEQUENCE.NEXTVAL,#{priceId},#{staffId},#{orgId},#{createdate})")
	void insertTblyyUserAll(Tblyyuser user) throws Exception;
	
	@Select("SELECT COUNT(*) FROM TBL_YY_USER_QUERY WHERE QUERYSTAFF IN (SELECT STAFFID FROM TBL_STAFF WHERE FIND_IN_SET(ORGID,(SELECT getChildrenDeptList(#{companyId}))))")
	Integer selectByUserOne(Integer companyId) throws Exception;


}
