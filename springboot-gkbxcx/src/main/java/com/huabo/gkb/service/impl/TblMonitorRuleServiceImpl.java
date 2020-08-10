package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorRule;
import com.huabo.gkb.mapper.TblMonitorRuleMapper;
import com.huabo.gkb.mysqlmapper.TblMonitorRuleMySqlMapper;
import com.huabo.gkb.service.TblMonitorRuleService;

@Service("tblMonitorRuleService")
public class TblMonitorRuleServiceImpl extends BaseServiceImpl<TblMonitorRule, BigDecimal> implements TblMonitorRuleService {
	@Resource
	public TblMonitorRuleMapper tblMonitorRuleMapper;
	
	
	@Resource
	public TblMonitorRuleMySqlMapper tblMonitorRuleMySqlMapper;
	
	@Override
	public List<TblMonitorRule> selectList(String orgid, PageInfo<TblMonitorRule> pageInfo) {
		List<TblMonitorRule> list = null;
		Integer count = 0;
		
		if(SystemStaticValue.DataType == 0) {
			list = tblMonitorRuleMySqlMapper.findByOrgid(orgid,pageInfo);
			count = tblMonitorRuleMySqlMapper.findCountByOrgid(orgid,pageInfo);
		}else {
			list = tblMonitorRuleMapper.findByOrgid(orgid,pageInfo);
			count = tblMonitorRuleMapper.findCountByOrgid(orgid,pageInfo);
		}
		
		pageInfo.setTlist(list);
		pageInfo.setTotalRecord(count);
		return null;
		
	}

	@Override
	public String findBookByRuleid(String ruleid) {
		String result = null;
		if(SystemStaticValue.DataType == 0) {
			result = tblMonitorRuleMySqlMapper.findBookByRuleid(ruleid);
		}else {
			result = tblMonitorRuleMapper.findBookByRuleid(ruleid);
		}
		return result;
	}



}
