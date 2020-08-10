package com.huabo.gkb.service;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyUserOrder;

public interface TblYyUserOrderService {

	void save(TblYyUserOrder tuo) throws Exception;

	void dealPaySuccess(String out_trade_no, String tno) throws Exception;

	void selectPageInfoList(PageInfo<TblYyUserOrder> pageInfo) throws Exception;

	void selectPageInfoListMySql(PageInfo<com.huabo.gkb.entitymysql.TblYyUserOrder> pageInfo) throws Exception;

	void saveTuoMySql(com.huabo.gkb.entitymysql.TblYyUserOrder tuo) throws Exception;

	void dealPaySuccessMySql(String out_trade_no, String string) throws Exception;
	
}
