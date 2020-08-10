package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.huabo.gkb.entity.TblManageRight;

import tk.mybatis.mapper.common.Mapper;

public interface TblManageRightMapper extends Mapper<TblManageRight> {

	@SelectProvider(type = TblManageRightOracleSqlConfig.class,method = "getRightForUser")
	@Results({
		@Result(column="RIGHTID",property="rightid"),
		@Result(column="RIGHTNAME",property="rightname"),
		@Result(column="RIGHTURL",property="righturl"),
		@Result(column="FATHERRIGHTID",property="fatherrightid"),
		@Result(column="FUNCORDER",property="funcorder"),
		@Result(column="INDICATORSTATUS",property="indicatorstatus"),
	})
	List<TblManageRight> getRightForUser(@Param("staffid") String staffid) throws Exception;
	
}
