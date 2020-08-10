package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblyyTeam;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TblYyTeamMapper extends Mapper<TblyyTeam> {

	@SelectProvider(method = "selectAllListByStaffId" ,type = TblYyTeamSqlConfig.class)
	@Results({
		@Result(column="teamid",property="teamid"),
		@Result(column="teamName",property="teamName"),
	})
	List<TblyyTeam> selectAllListByStaffId(Integer staffId) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_YY_TEAM WHERE \"teamName\" = #{teamName} AND STAFFID = #{staffId}")
	Integer selectCountByName(@Param("teamName") String teamName, @Param("staffId")Integer staffId) throws Exception;

	@SelectProvider(method = "selectListByStaffId" ,type = TblYyTeamSqlConfig.class)
	@Results({
		@Result(column="teamid",property="teamid"),
		@Result(column="teamName",property="teamName"),
	})
	List<TblyyTeam> selectListByStaffId(Integer staffId, PageInfo<TblyyTeam> pageInfo) throws Exception;

	@Update("UPDATE TBL_YY_TEAM SET \"teamName\"= #{teamName} WHERE \"teamid\" = #{teamid}")
	void updateTeamName(@Param("teamName")String teamName,@Param("teamid") Integer teamid) throws Exception;

	@SelectProvider(method = "selectListInfoByPageInfo" ,type = TblYyTeamSqlConfig.class)
	@Results({
		@Result(column="teamid",property="teamid"),
		@Result(column="teamName",property="teamName"),
	})
	List<TblyyTeam> selectListInfoByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo);

	@SelectProvider(method = "selectCountByPageInfo" ,type = TblYyTeamSqlConfig.class)
	Integer selectCountByPageInfo(Integer staffId, PageInfo<TblyyTeam> pageInfo) throws Exception;

	@Select("SELECT COUNT(0) FROM TBL_YY_TEAM WHERE STAFFID = #{staffId}")
	Integer selectCountByStaffId(@Param("staffId")Integer staffId, PageInfo<TblyyTeam> pageInfo) throws Exception;


}
