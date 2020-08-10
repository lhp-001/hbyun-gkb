package com.huabo.gkb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyUserOrder;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TblYyUserOrderMapper extends Mapper<TblYyUserOrder> {

	@SelectProvider(method = "selectListByPageInfo" ,type = TblYyUserOrderMapperSqlConfig.class)
	@Results({
		@Result(column="ORDERCODE",property="ordercode"),
		@Result(column="ORDERID",property="orderid"),
		@Result(column="ORDERNO",property="orderno"),
		@Result(column="STATUS",property="status"),
		@Result(column="CREATEDATE",property="createdate"),
		@Result(column="PAYDATE",property="paydate"),
		@Result(column="ORGID",property="orgId"),
		@Result(column="REALNAME",property="staffName"),
		@Result(column="ORDERMONEY",property="ordermoney"),
	})
	List<TblYyUserOrder> selectListByPageInfo(PageInfo<TblYyUserOrder> pageInfo);

	@SelectProvider(method = "selectCountByPageInfo" ,type = TblYyUserOrderMapperSqlConfig.class)
	Integer selectCountByPageInfo(PageInfo<TblYyUserOrder> pageInfo);
	
	@Select("SELECT * FROM TBL_YY_USER_ORDER WHERE ORDERNO = #{out_trade_no}")
	TblYyUserOrder selectOrderByNo(@Param("out_trade_no")String out_trade_no);

	@Insert("INSERT INTO tbl_yy_user_order(ORDERNO,CREATEDATE,ORDERMONEY,`STATUS`,ORGID,CREATESTAFF) VALUE(#{orderno},#{createdate},#{ordermoney},#{status},#{orgId},#{staffid})")
	@Options(useGeneratedKeys=true, keyProperty="orderid", keyColumn="ORDERID")
	void insertSelectiveMySql(com.huabo.gkb.entitymysql.TblYyUserOrder tuo) throws Exception;
	
}
