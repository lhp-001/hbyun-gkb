package com.huabo.gkb.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblCirculation;
import com.huabo.gkb.mapper.TblCirculationMapper;
import com.huabo.gkb.mysqlmapper.TblCirculationMySqlMapper;
import com.huabo.gkb.service.TblCirculationService;

@Service("TblCirculationService")
public class TblCirculationServiceImpl extends BaseServiceImpl<TblCirculation, BigDecimal> implements TblCirculationService {
	@Resource
	public TblCirculationMapper tblCirculationServiceMapper;
	
	@Resource
	public TblCirculationMySqlMapper tblCirculationMySqlMapper;
	
	@Override
	public List<TblCirculation> findSpByStaffid(String staffid, PageInfo<TblCirculation> pageInfo) {
		List<TblCirculation> list = null;
		if(SystemStaticValue.DataType == 0) {
			list = tblCirculationMySqlMapper.findSpByStaffid(staffid, pageInfo);
		}else {
			list = tblCirculationServiceMapper.findSpByStaffid(staffid,pageInfo);
		}
		
		pageInfo.setTlist(list);
		pageInfo.setTotalRecord(10);
		return null;
	}


}
