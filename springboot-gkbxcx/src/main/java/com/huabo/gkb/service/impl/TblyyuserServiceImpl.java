package com.huabo.gkb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.Tblyyuser;
import com.huabo.gkb.mapper.TblyyuserMapper;
import com.huabo.gkb.mysqlmapper.TblyyuserMySqlMapper;
import com.huabo.gkb.service.TblyyuserService;

@Service("tblyyuserServiceImpl")
public class TblyyuserServiceImpl extends BaseServiceImpl<Tblyyuser, Integer> implements TblyyuserService {
	@Resource
	public TblyyuserMapper tblyyuserMapper;
	
	
	@Resource
	public TblyyuserMySqlMapper tblyyuserMySqlMapper;

	@Override
	public Integer findByUserOne(Integer companyId) throws Exception {
		Integer count = 0;
		if(SystemStaticValue.DataType == 0) {
			count = tblyyuserMySqlMapper.selectByUserOne(companyId);
		}else {
			count = tblyyuserMapper.selectByUserOne(companyId);
		}
		return count;
	}


}
