package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorPrewarning;
import com.huabo.gkb.mapper.TblMonitorPrewarningMapper;
import com.huabo.gkb.mysqlmapper.TblMonitorPrewarningMySqlMapper;
import com.huabo.gkb.service.TblMonitorPrewarningService;

@Service("tblMonitorPrewarningService")
public class TblMonitorPrewarningServiceImpl extends BaseServiceImpl<TblMonitorPrewarning, BigDecimal> implements TblMonitorPrewarningService {
	@Resource
	public TblMonitorPrewarningMapper tblMonitorPrewarningMapper;
	
	@Resource
	public TblMonitorPrewarningMySqlMapper tblMonitorPrewarningMySqlMapper;
	
	@Override
	public List<TblMonitorPrewarning> selectList(String ruleid, PageInfo<TblMonitorPrewarning> pageInfo) {
		pageInfo.setPageSize(10);
		List<TblMonitorPrewarning> list = null;
		Integer count = 0;
		
		if(SystemStaticValue.DataType == 0) {
			list = tblMonitorPrewarningMySqlMapper.findByOrgid(ruleid,pageInfo);
			count = tblMonitorPrewarningMySqlMapper.findGzDetailCountByOrgid(ruleid,pageInfo);
		}else {
			list = tblMonitorPrewarningMapper.findByOrgid(ruleid,pageInfo);
			count = tblMonitorPrewarningMapper.findGzDetailCountByOrgid(ruleid,pageInfo);
		}
		pageInfo.setTlist(list);
		pageInfo.setTotalRecord(count);
		return null;
	}
	public String findSignIdByRuleid(String ruleid) {
		String result = null;
		if(SystemStaticValue.DataType == 0) {
			result = tblMonitorPrewarningMySqlMapper.findSignIdByRuleid(ruleid);
		}else {
			result = tblMonitorPrewarningMapper.findSignIdByRuleid(ruleid);
		}
		return result;
	}
	@Override
	public List<Object> findRuleResult(String sql, PageInfo<Object> pageInfo) {
		List<Object> list = null;
		
		if(SystemStaticValue.DataType == 0) {
			list = tblMonitorPrewarningMySqlMapper.findRuleResult(sql,pageInfo);
		}else {
			list = tblMonitorPrewarningMapper.findRuleResult(sql,pageInfo);
		}
		
		pageInfo.setTlist(list);
		pageInfo.setTotalRecord(10);
		return null;
	}

}
