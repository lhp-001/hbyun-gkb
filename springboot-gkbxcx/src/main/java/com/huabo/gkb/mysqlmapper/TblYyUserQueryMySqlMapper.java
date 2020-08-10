package com.huabo.gkb.mysqlmapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entitymysql.TblYyUserQuery;

import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface TblYyUserQueryMySqlMapper extends MySqlMapper<TblYyUserQuery> {
	
	@Insert("INSERT INTO TBL_YY_USER_QUERY(QUERYSTAFF,PAYMONEY,QUERYTIME,REPORTNAME,ORGID) VALUE (#{staffid},#{paymoney},#{querytime},#{reportName},#{orgid})")
	@Options(useGeneratedKeys=true, keyProperty="recordid", keyColumn="RECORDID")
	void insertSelective(TblYyUserQuery yuq);


}
