package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;
import com.huabo.gkb.mapper.TblIndicatorMapper;
import com.huabo.gkb.mysqlmapper.TblIndicatorMySqlMapper;
import com.huabo.gkb.service.TblIndicatorService;

@Service("tblIndicatorService")
public class TblIndicatorServiceImpl extends BaseServiceImpl<TblIndicator, BigDecimal> implements TblIndicatorService {
	@Resource
	public TblIndicatorMapper tblIndicatorMapper;
	
	@Resource
	public TblIndicatorMySqlMapper tblIndicatorMySqlMapper;
	
	@Override
	public List<TblIndicator> selectList(String orgid, PageInfo<TblIndicator> pageInfo) {
		List<TblIndicator> list = null;
		Integer count = 0;
		if(SystemStaticValue.DataType == 0) {
			list = tblIndicatorMySqlMapper.findByOrgid(orgid,pageInfo);
			count = tblIndicatorMySqlMapper.findCountByOrgid(orgid,pageInfo);
		}else {
			list = tblIndicatorMapper.findByOrgid(orgid,pageInfo);
			count = tblIndicatorMapper.findCountByOrgid(orgid,pageInfo);
		}
		pageInfo.setTlist(list);
		pageInfo.setTotalRecord(count);
		return null;
	}


}
