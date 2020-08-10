package com.huabo.gkb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.entity.Tblyyprice;
import com.huabo.gkb.mapper.TblyypriceMapper;
import com.huabo.gkb.mysqlmapper.TblyypriceMySqlMapper;
import com.huabo.gkb.service.TblyypriceService;

@Service("tblyypriceServiceImpl")
public class TblyypriceServiceImpl extends BaseServiceImpl<Tblyyprice, Integer> implements TblyypriceService {
	@Resource
	public TblyypriceMapper tblyypriceMapper;
	
	@Resource
	public TblyypriceMySqlMapper tblyypriceMySqlMapper;

}
