package com.huabo.gkb.service;

import java.math.BigDecimal;
import java.util.List;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorRule;

public interface TblMonitorRuleService extends BaseService<TblMonitorRule, BigDecimal> {

public List<TblMonitorRule> selectList(String orgid, PageInfo<TblMonitorRule> pageInfo);

public String findBookByRuleid(String ruleid);
}
