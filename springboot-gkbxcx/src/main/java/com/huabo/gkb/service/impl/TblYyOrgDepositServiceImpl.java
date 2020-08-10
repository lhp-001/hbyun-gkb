package com.huabo.gkb.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyUserQuery;
import com.huabo.gkb.mapper.TblOrganizationMapper;
import com.huabo.gkb.mapper.TblYyOrgDepositMapper;
import com.huabo.gkb.mysqlmapper.TblOrganizationMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblYyOrgDepositMySqlMapper;
import com.huabo.gkb.service.TblYyOrgDepositService;


@Service("tblYyOrgDepositServiceImpl")
public class TblYyOrgDepositServiceImpl implements TblYyOrgDepositService {
	
	@Autowired
	private TblYyOrgDepositMapper tblYyOrgDepositMapper;
	
	@Resource
	private TblOrganizationMapper tblOrganizationMapper;
	
	
	@Autowired
	private TblYyOrgDepositMySqlMapper tblYyOrgDepositMySqlMapper;
	
	@Resource
	private TblOrganizationMySqlMapper tblOrganizationMySqlMapper;
	

	@Override
	public String findUrplusMoney(Integer orgid) throws Exception {
		String result ;
		if(SystemStaticValue.DataType == 0) {
			result = this.tblYyOrgDepositMySqlMapper.selectUrplusMoney(orgid);
		}else {
			result = this.tblYyOrgDepositMapper.selectUrplusMoney(orgid);
		}
		return result;
	}

	@Override
	public Double findTostMoney(String reportName) throws Exception {
		String result;
		if(SystemStaticValue.DataType == 0) {
			result = this.tblYyOrgDepositMySqlMapper.selectTotalTostMoney(reportName);
		}else {
			result = this.tblYyOrgDepositMapper.selectTotalTostMoney(reportName);
		}
		
		if(result != null){
			return Double.valueOf(result);
		}
		return 0.0;
	}

	@Override
	public void findCostPircePageInfo(PageInfo<TblYyUserQuery> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblYyOrgDepositMapper.selectListByPageInfo(pageInfo));
		pageInfo.setTotalRecord(this.tblYyOrgDepositMapper.selectCountByPageInfo(pageInfo));
		
	}

	@Override
	public Double calBalanceMoney(String orgId) throws Exception {
		String balance = null;
		Integer oid = 0;
		
		if(SystemStaticValue.DataType == 0) {
			oid = tblOrganizationMySqlMapper.findCompanyIdByDeptId(orgId);
			balance = this.tblYyOrgDepositMySqlMapper.selectUrplusMoney(oid);
		}else {
			oid = tblOrganizationMapper.findCompanyIdByDeptId(orgId);
			balance = this.tblYyOrgDepositMapper.selectUrplusMoney(oid);
		}
		
		if(balance == null){
    		return 0.0;//账户余额不足，请充值
    	}	 
		return Double.valueOf(balance);
	}

	@Override
	public void findPromotionPircePageInfo(PageInfo<TblYyUserQuery> pageInfo) throws Exception {
		pageInfo.setTlist(this.tblYyOrgDepositMapper.selectPromotionPriceList(pageInfo));
		pageInfo.setTotalRecord(this.tblYyOrgDepositMapper.selectCountPromotionPrice(pageInfo));
	}

	@Override
	public Double findPromotionAllMoney(Integer staffId) throws Exception {
		return this.tblYyOrgDepositMapper.selectPromotionAllMoney(staffId);
	}

	@Override
	public void findCostPircePageInfoMySql(PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblYyOrgDepositMySqlMapper.selectListByPageInfo(pageInfo));
		pageInfo.setTotalRecord(this.tblYyOrgDepositMySqlMapper.selectCountByPageInfo(pageInfo));
	}

	@Override
	public void findPromotionPircePageInfoMySql(PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery> pageInfo)
			throws Exception {
		pageInfo.setTlist(this.tblYyOrgDepositMySqlMapper.selectPromotionPriceList(pageInfo));
		pageInfo.setTotalRecord(this.tblYyOrgDepositMySqlMapper.selectCountPromotionPrice(pageInfo));
	}

	@Override
	public Double findPromotionAllMoneyMySql(Integer staffId) throws Exception {
		return this.tblYyOrgDepositMySqlMapper.selectPromotionAllMoney(staffId);
	}
}
