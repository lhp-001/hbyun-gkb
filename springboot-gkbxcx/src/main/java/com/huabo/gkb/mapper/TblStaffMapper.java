package com.huabo.gkb.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblStaff;

import tk.mybatis.mapper.common.Mapper;

public interface TblStaffMapper extends Mapper<TblStaff> {

	@InsertProvider(type = TblStaffOracleSqlConfig.class,method = "insertStaffReturnStaffId")
	@Options(useGeneratedKeys=true, keyProperty="staffid", keyColumn="STAFFID")
	void insertStaffReturnStaffId(TblStaff staff) throws Exception;

	@InsertProvider(type = TblStaffOracleSqlConfig.class,method = "insertManagerRight")
	void insertManagerRight(@Param("rightList") List<String> rightList,@Param("staffid") BigDecimal staffid);
	
	@Select("SELECT TSF.EMAIL,TSF.ADDRESS,TSF.MIBLEPHONE,TOG.ORGNAME,TJ.JOBNAME,(SELECT ORGNAME FROM TBL_ORGANIZATION WHERE ORGTYPE <= 100 AND ORGTYPE != 0 AND ORGTYPE = (SELECT MAX(ORGTYPE) FROM TBL_ORGANIZATION WHERE ORGTYPE <= 100 AND ORGTYPE != 0 START WITH ORGID = TSF.ORGID CONNECT BY PRIOR FATHERORGID = ORGID ) START WITH ORGID = TSF.ORGID CONNECT BY PRIOR FATHERORGID = ORGID ) AS ORGFANAME FROM TBL_STAFF TSF LEFT JOIN TBL_JOB TJ ON TJ.JOBID = TSF.JOBID LEFT JOIN TBL_ORGANIZATION TOG ON TSF.ORGID = TOG.ORGID WHERE TSF.STAFFID = #{staffId}")
	@Results({
		@Result(column="ORGNAME",property="orgName"),
		@Result(column="JOBNAME",property="jobName"),
		@Result(column="ORGFANAME",property="orgFatherName"),
		@Result(column="EMAIL",property="email"),
		@Result(column="ADDRESS",property="address"),
		@Result(column="MIBLEPHONE",property="miblephone"),
	})
	TblStaff selectStaffUserInfo(@Param("staffId")String staffId);

	@SelectProvider(type = TblStaffOracleSqlConfig.class,method = "selectUserInfoByOrgId")
	@Results({
		@Result(column="REALNAME",property="realname"),
		@Result(column="MIBLEPHONE",property="miblephone"),
		@Result(column="EMAIL",property="email"),
	})
	List<TblStaff> selectUserInfoByOrgId(String orgId, PageInfo<TblStaff> pageInfo) throws Exception;

	@Select("SELECT STAFFID,REALNAME,ORGID,USERNAME FROM TBL_STAFF WHERE STAFFID = #{staffId}")
	@Results({
		@Result(column="STAFFID",property="staffid"),
		@Result(column="REALNAME",property="realname"),
		@Result(column="ORGID",property="orgid"),
		@Result(column="USERNAME",property="username"),
	})
	TblStaff selectStaff(@Param("staffId") String staffId) throws Exception;

	@Select("SELECT * FROM TBL_STAFF WHERE MIBLEPHONE = #{miblePhone}")
	TblStaff selectByMiblePhone(@Param("miblePhone")String miblePhone) throws Exception;

	@Insert("INSERT INTO TBL_WX_POPULARIZE_STAFF_ORGID(STAFFID,ORGID) VALUES(#{staffId},#{orgId})")
	void insertPupularizeStaffOrgRelation(@Param("staffId")Integer sid,@Param("orgId") BigDecimal oid) throws Exception;

	@Select("SELECT * FROM TBL_STAFF WHERE CREATETIME = ("
			+ " SELECT MIN(CREATETIME) FROM TBL_STAFF WHERE ORGID IN (SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGID = #{orgId}"
			+ " UNION ALL SELECT ORGID FROM TBL_ORGANIZATION WHERE STATUS = 0 START WITH FATHERORGID = #{orgId} AND ORGTYPE = 0 CONNECT BY PRIOR ORGID = FATHERORGID" 
			+ " )) AND ORGID IN (SELECT ORGID FROM TBL_ORGANIZATION WHERE ORGID = #{orgId}" 
			+ " UNION ALL SELECT ORGID FROM TBL_ORGANIZATION WHERE STATUS = 0 START WITH FATHERORGID = #{orgId} AND ORGTYPE = 0 CONNECT BY PRIOR ORGID = FATHERORGID) AND ROWNUM <= 1 ORDER BY CREATETIME ASC")
	TblStaff selectWxappAdmin(@Param("orgId")BigDecimal orgId) throws Exception;

	@SelectProvider(type = TblStaffOracleSqlConfig.class,method = "selectUserInfoByDeptIdIncludOrg")
	@Results({
		@Result(column="STAFFID",property="staffid"),
		@Result(column="REALNAME",property="realname"),
		@Result(column="ORGNAME",property="orgName"),
		@Result(column="EMAIL",property="email"),
		@Result(column="CHECKED",property="checked")
	})
	List<TblStaff> selectUserInfoByDeptIdIncludOrg(String orgId,Integer comId, PageInfo<TblStaff> pageInfo) throws Exception;

	@Update("UPDATE TBL_STAFF SET ORGID = #{orgid} WHERE STAFFID IN (${staffids})")
	void updateDeptInfoStaff(String staffids, Integer orgid) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_STAFF WHERE ORGID = #{orgId}")
	Integer selectUserCountByOrgId(@Param("orgId")String orgId);

	@Select("SELECT COUNT(0) FROM TBL_STAFF WHERE ORGID IN (#{orgId},#{comId})")
	Integer selectUserCountByDeptIdIncludOrg(@Param("orgId")String orgId,@Param("comId")Integer comId);
	
}
