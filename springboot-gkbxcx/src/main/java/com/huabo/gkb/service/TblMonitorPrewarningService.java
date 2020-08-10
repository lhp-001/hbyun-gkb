package com.huabo.gkb.service;

import java.math.BigDecimal;
import java.util.List;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorPrewarning;

public interface TblMonitorPrewarningService extends BaseService<TblMonitorPrewarning, BigDecimal> {
/**
 * 指标结果列表
 * @param orgid
 * @param pageInfo 
 * @return
 */
public List<TblMonitorPrewarning> selectList(String ruleid, PageInfo<TblMonitorPrewarning> pageInfo);

public String findSignIdByRuleid(String ruleid);

public  List<Object> findRuleResult(String sql, PageInfo<Object> pageInfo);
}
