package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huabo.gkb.entity.TblYyOrgDeposit;
import com.huabo.gkb.entity.TblYyQueryPrice;
import com.huabo.gkb.entity.TblYyUserQuery;
import com.huabo.gkb.entity.Tblyyprice;
import com.huabo.gkb.mapper.TblYyOrgDepositMapper;
import com.huabo.gkb.mapper.TblYyQueryPriceMapper;
import com.huabo.gkb.mapper.TblYyReportModelMapper;
import com.huabo.gkb.mapper.TblYyUserQueryMapper;
import com.huabo.gkb.mapper.TblyypriceMapper;
import com.huabo.gkb.mapper.TblyyuserMapper;
import com.huabo.gkb.mysqlmapper.TblYyOrgDepositMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyQueryPriceMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyReportModelMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyUserQueryMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblyypriceMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblyyuserMySqlMapper;
import com.huabo.gkb.service.TblYyUserQueryService;


@Service("tblYyUserQueryServiceImpl")
public class TblYyUserQueryServiceImpl implements TblYyUserQueryService {
	
	@Autowired
	private TblYyUserQueryMapper tblYyUserQueryMapper;
	
	@Autowired
	private TblYyReportModelMapper tblYyReportModelMapper;
	
	@Autowired
	private TblyypriceMapper  tblyypriceMapper;
	
	@Autowired
	private TblYyOrgDepositMapper tblYyOrgDepositMapper;

	@Autowired
	private TblYyQueryPriceMapper tblYyQueryPriceMapper;
	
	@Resource
	public TblyyuserMapper tblyyuserMapper;
	
	
	@Autowired
	private TblYyUserQueryMySqlMapper tblYyUserQueryMySqlMapper;
	
	@Autowired
	private TblYyReportModelMySqlMapper tblYyReportModelMySqlMapper;
	
	@Autowired
	private TblyypriceMySqlMapper  tblyypriceMySqlMapper;
	
	@Autowired
	private TblYyOrgDepositMySqlMapper tblYyOrgDepositMySqlMapper;

	@Autowired
	private TblYyQueryPriceMySqlMapper tblYyQueryPriceMySqlMapper;
	
	@Resource
	public TblyyuserMySqlMapper tblyyuserMySqlMapper;
	
	
	
	@Override
	@Transactional(value="oracleDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public void dealUserQuery(Integer companyId, String staffId, String reportName)
			throws Exception {
		//查询接口模板
		String priceIds = tblyypriceMapper.selectTblyyUserPriceByName(reportName);
		
		//查询调用的所有接口
		List<Tblyyprice> priceList = this.tblyypriceMapper.selectListByPriceIds(priceIds);
		
		//查询公司余额
		TblYyOrgDeposit yod = this.tblYyOrgDepositMapper.selectOrgDepositByOrgId(companyId);
		
		Integer count = tblyyuserMapper.selectByUserOne(companyId);
		
			
			//计算花费金额，准备查询记录的中间表数据
			List<TblYyQueryPrice> yqpList = new ArrayList<TblYyQueryPrice>(0);
			TblYyQueryPrice yqp = null;
			Double money = 0.0;
			for (Tblyyprice yp : priceList) {
				money = money + yp.getHbprice();
				yqp = new TblYyQueryPrice();
				yqp.setPriceid(yp.getPriceId());
				yqp.setPriceMoney(yp.getHbprice());
				yqpList.add(yqp);
			}
			TblYyUserQuery yuq = new TblYyUserQuery();
			
			yuq.setStaffid(new BigDecimal(staffId));
			yuq.setPaymoney(money);
			yuq.setQuerytime(new Date());
			yuq.setReportName(reportName);
			yuq.setOrgid(new BigDecimal(companyId.toString()));
			
			this.tblYyUserQueryMapper.insertSelective(yuq);
			
			
			for (TblYyQueryPrice tyqp : yqpList) {
				tyqp.setRecordid(yuq.getRecordid().intValue());
				this.tblYyQueryPriceMapper.insertSelective(tyqp);
			}
			
			if(count >= 1) {
				if(yod.getTotalpaymoney() != null){
					yod.setTotalpaymoney(yod.getTotalpaymoney()+money);
				}else{
					yod.setTotalpaymoney(money);
				}
				this.tblYyOrgDepositMapper.updateByPrimaryKeySelective(yod);
			}
			
			

	}



	@Override
	@Transactional(value="mysqlDataSourceTransactionManager",rollbackFor = Exception.class,timeout=36000)
	public void dealUserQueryMySql(Integer companyId, String staffId, String reportName) throws Exception {
		//查询接口模板
				String priceIds = tblyypriceMySqlMapper.selectTblyyUserPriceByName(reportName);
				
				//查询调用的所有接口
				List<com.huabo.gkb.entitymysql.Tblyyprice> priceList = this.tblyypriceMySqlMapper.selectListByPriceIds(priceIds);
				
				//查询公司余额
				com.huabo.gkb.entitymysql.TblYyOrgDeposit yod = this.tblYyOrgDepositMySqlMapper.selectOrgDepositByOrgId(companyId);
				
				Integer count = tblyyuserMapper.selectByUserOne(companyId);
				
					
					//计算花费金额，准备查询记录的中间表数据
					List<com.huabo.gkb.entitymysql.TblYyQueryPrice> yqpList = new ArrayList<com.huabo.gkb.entitymysql.TblYyQueryPrice>(0);
					com.huabo.gkb.entitymysql.TblYyQueryPrice yqp = null;
					Double money = 0.0;
					for (com.huabo.gkb.entitymysql.Tblyyprice yp : priceList) {
						money = money + yp.getHbprice();
						yqp = new com.huabo.gkb.entitymysql.TblYyQueryPrice();
						yqp.setPriceid(yp.getPriceId());
						yqp.setPriceMoney(yp.getHbprice());
						yqpList.add(yqp);
					}
					com.huabo.gkb.entitymysql.TblYyUserQuery yuq = new com.huabo.gkb.entitymysql.TblYyUserQuery();
					
					yuq.setStaffid(new BigDecimal(staffId));
					yuq.setPaymoney(money);
					yuq.setQuerytime(new Date());
					yuq.setReportName(reportName);
					yuq.setOrgid(new BigDecimal(companyId.toString()));
					
					this.tblYyUserQueryMySqlMapper.insertSelective(yuq);
					
					
					for (com.huabo.gkb.entitymysql.TblYyQueryPrice tyqp : yqpList) {
						tyqp.setRecordid(yuq.getRecordid().intValue());
						this.tblYyQueryPriceMySqlMapper.insertSelective(tyqp);
					}
					
					if(count >= 1) {
						if(yod.getTotalpaymoney() != null){
							yod.setTotalpaymoney(yod.getTotalpaymoney()+money);
						}else{
							yod.setTotalpaymoney(money);
						}
						this.tblYyOrgDepositMySqlMapper.updateTotalMoneyById(yod);
					}
		
	}
}
