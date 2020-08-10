package com.huabo.gkb.mysqlmapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entitymysql.TblYyQueryPrice;

import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface TblYyQueryPriceMySqlMapper extends MySqlMapper<TblYyQueryPrice> {

	@Insert("INSERT INTO TBL_YY_QUERY_PRICE(PRICEID,RECORDID,PRICEMONEY) VALUE(#{priceid},#{recordid},#{priceMoney})")
	void insertSelective(TblYyQueryPrice tyqp) throws Exception;

}
