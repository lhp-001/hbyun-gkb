package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorIndicatorResult;
import com.huabo.gkb.mapper.TblIndicatorResultMapper;
import com.huabo.gkb.mysqlmapper.TblIndicatorResultMySqlMapper;
import com.huabo.gkb.service.TblIndicatorResultService;

@Service("tblIndicatorResultService")
public class TblIndicatorResultServiceImpl extends BaseServiceImpl<TblMonitorIndicatorResult, BigDecimal> implements TblIndicatorResultService {
	@Resource
	public TblIndicatorResultMapper tblIndicatorResultMapper;
	
	@Resource
	public TblIndicatorResultMySqlMapper tblIndicatorResultMySqlMapper;
	
	@Override
	public List<TblMonitorIndicatorResult> selectList(String indicatorid, PageInfo<TblMonitorIndicatorResult> pageInfo) {
		List<TblMonitorIndicatorResult> list = null;
		if(SystemStaticValue.DataType == 0) {
			list = tblIndicatorResultMySqlMapper.findByIndicatorid(indicatorid,pageInfo);
		}else {
			list = tblIndicatorResultMapper.findByIndicatorid(indicatorid,pageInfo);
		}
		
		
		pageInfo.setTlist(list);
		pageInfo.setTotalRecord(10);
		return null;
	}


}
