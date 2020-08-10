package com.huabo.gkb.mysqlmapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.huabo.gkb.entitymysql.TblWxUserInfo;

import tk.mybatis.mapper.common.MySqlMapper;

public interface TblWxUserInfoMySqlMapper extends MySqlMapper<TblWxUserInfo> {

	@Select("SELECT TWI.USERID,TWI.AVATARURL,TWI.GENDER,TWI.NICKNAME,TWI.XCXOPENID,TWI.UNIONID,TWI.STAFFID,TSF.REALNAME,TSF.EMAIL,TSF.MIBLEPHONE,TSF.ORGID FROM TBL_WXUSER_INFO TWI LEFT JOIN TBL_STAFF TSF ON TSF.STAFFID = TWI.STAFFID WHERE TWI.XCXOPENID = #{openId}")
	@Results({
		@Result(column="USERID",property="userId"),
		@Result(column="AVATARURL",property="avatarUrl"),
		@Result(column="GENDER",property="gender"),
		@Result(column="NICKNAME",property="nickName"),
		@Result(column="XCXOPENID",property="xcxOpenId"),
		@Result(column="UNIONID",property="unionId"),
		@Result(column="STAFFID",property="staffId"),
		@Result(column="REALNAME",property="realName"),
		@Result(column="EMAIL",property="email"),
		@Result(column="MIBLEPHONE",property="miblePhone"),
		@Result(column="ORGID",property="orgId"),
	})
	TblWxUserInfo selectWxUserInfoByOpenId(String openId) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_WXUSER_INFO WHERE XCXOPENID = #{openId}")
	Integer selectCountUserInfoByOpenId(String openId) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_WXUSER_INFO WHERE XCXOPENID = #{openId} UNION ALL SELECT COUNT(0) FROM TBL_ORGANIZATION WHERE ORGNAME = #{companyName}")
	List<Integer> selectCountByOpenIdAndCompanyName(String openId, String companyName) throws Exception;

	@InsertProvider(type = TblWxUserInfoMySqlConfig.class,method = "insertWxUserInfoReturnUserId")
	@Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="USERID")	
	void insertWxUserInfoReturnUserId(TblWxUserInfo userInfo) throws Exception;

	@Select("SELECT RIGHTID FROM TBL_ORG_RIGHT WHERE ORGID = #{registerorgfatherid}")
	List<String> selectRegisterRightByOrgId(BigDecimal registerorgfatherid) throws Exception;

	@Update("UPDATE TBL_WXUSER_INFO SET AVATARURL = #{avatarUrl} WHERE XCXOPENID = #{xcxOpenId}")
	void updateAvatarUrl(TblWxUserInfo userInfo) throws Exception;

}
