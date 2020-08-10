package com.huabo.gkb.mysqlmapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entitymysql.TblYyUserOrder;

import tk.mybatis.mapper.common.MySqlMapper;

@Repository
public interface TblYyUserOrderMySqlMapper extends MySqlMapper<TblYyUserOrder> {

	@SelectProvider(method = "selectListByPageInfo" ,type = TblYyUserOrderMySqlConfig.class)
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

	@SelectProvider(method = "selectCountByPageInfo" ,type = TblYyUserOrderMySqlConfig.class)
	Integer selectCountByPageInfo(PageInfo<TblYyUserOrder> pageInfo);
	
	@Select("SELECT * FROM TBL_YY_USER_ORDER WHERE ORDERNO = #{out_trade_no}")
	TblYyUserOrder selectOrderByNo(@Param("out_trade_no")String out_trade_no);

	@Update("UPDATE tbl_yy_user_order SET `STATUS` = #{status} ,PAYDATE = #{paydate} ,ORDERCODE = #{ordercode} WHERE ORDERID = #{orderid}")
	void updateByPrimaryKeySelective(TblYyUserOrder tuo);
	
}
