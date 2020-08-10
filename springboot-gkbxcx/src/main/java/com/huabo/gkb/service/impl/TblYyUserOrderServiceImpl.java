package com.huabo.gkb.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyOrgDeposit;
import com.huabo.gkb.entity.TblYyUserOrder;
import com.huabo.gkb.mapper.TblYyOrgDepositMapper;
import com.huabo.gkb.mapper.TblYyUserOrderMapper;
import com.huabo.gkb.mysqlmapper.TblYyOrgDepositMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyUserOrderMySqlMapper;
import com.huabo.gkb.service.TblYyUserOrderService;


@Service("tblYyUserOrderServiceImpl")
public class TblYyUserOrderServiceImpl implements TblYyUserOrderService {
	
	@Autowired
	private TblYyUserOrderMapper tblYyUserOrderMapper;
	@Autowired
	private TblYyOrgDepositMapper tblYyOrgDepositMapper;
	
	
	
	@Autowired
	private TblYyUserOrderMySqlMapper tblYyUserOrderMySqlMapper;
	@Autowired
	private TblYyOrgDepositMySqlMapper tblYyOrgDepositMySqlMapper;
	
	

	@Override
	public void save(TblYyUserOrder tuo) throws Exception {
		this.tblYyUserOrderMapper.insertSelective(tuo);
	}

	@Override
	@Transactional(value="oracleDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public void dealPaySuccess(String out_trade_no, String tno) throws Exception {
		TblYyUserOrder tuo = this.tblYyUserOrderMapper.selectOrderByNo(out_trade_no);
		if(tuo != null){
			if(tuo.getStatus() == 1){
				tuo.setStatus(2);
				TblYyOrgDeposit yod = this.tblYyOrgDepositMapper.selectOrgDepositByOrgId(tuo.getOrgId().intValue());
				if(yod == null){
					yod = new TblYyOrgDeposit();
					yod.setLastdate(new Date());
					yod.setOrgId(tuo.getOrgId());
					yod.setTotalmoney(tuo.getOrdermoney());
					this.tblYyOrgDepositMapper.insertSelective(yod);
				}else{
					yod.setLastdate(new Date());
					yod.setTotalmoney(yod.getTotalmoney()+tuo.getOrdermoney());
					this.tblYyOrgDepositMapper.updateByPrimaryKeySelective(yod);
				}
				tuo.setStatus(3);
				tuo.setPaydate(new Date());
				tuo.setOrdercode(tno);
				this.tblYyUserOrderMapper .updateByPrimaryKeySelective(tuo);
			}
		}
	}

	@Override
	public void selectPageInfoList(PageInfo<TblYyUserOrder> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblYyUserOrderMapper.selectListByPageInfo(pageInfo));
		pageInfo.setTotalRecord(this.tblYyUserOrderMapper.selectCountByPageInfo(pageInfo));
		
	}

	@Override
	public void selectPageInfoListMySql(PageInfo<com.huabo.gkb.entitymysql.TblYyUserOrder> pageInfo) throws Exception {
		pageInfo.setTlist(this.tblYyUserOrderMySqlMapper.selectListByPageInfo(pageInfo));
		pageInfo.setTotalRecord(this.tblYyUserOrderMySqlMapper.selectCountByPageInfo(pageInfo));
	}

	@Override
	public void saveTuoMySql(com.huabo.gkb.entitymysql.TblYyUserOrder tuo) throws Exception {
		this.tblYyUserOrderMapper.insertSelectiveMySql(tuo);
	}

	@Override
	@Transactional(value="mysqlDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public void dealPaySuccessMySql(String out_trade_no, String tno) throws Exception {
		com.huabo.gkb.entitymysql.TblYyUserOrder tuo = this.tblYyUserOrderMySqlMapper.selectOrderByNo(out_trade_no);
		if(tuo != null){
			if(tuo.getStatus() == 1){
				tuo.setStatus(2);
				com.huabo.gkb.entitymysql.TblYyOrgDeposit yod = this.tblYyOrgDepositMySqlMapper.selectOrgDepositByOrgId(tuo.getOrgId().intValue());
				if(yod == null){
					yod = new com.huabo.gkb.entitymysql.TblYyOrgDeposit();
					yod.setLastdate(new Date());
					yod.setOrgId(tuo.getOrgId());
					yod.setTotalmoney(tuo.getOrdermoney());
					this.tblYyOrgDepositMySqlMapper.insertSelective(yod);
				}else{
					yod.setLastdate(new Date());
					yod.setTotalmoney(yod.getTotalmoney()+tuo.getOrdermoney());
					this.tblYyOrgDepositMySqlMapper.updateByPrimaryKeySelective(yod);
				}
				tuo.setStatus(3);
				tuo.setPaydate(new Date());
				tuo.setOrdercode(tno);
				this.tblYyUserOrderMySqlMapper.updateByPrimaryKeySelective(tuo);
			}
		}
	}
}
