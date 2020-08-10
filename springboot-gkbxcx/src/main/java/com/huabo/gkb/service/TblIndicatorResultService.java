package com.huabo.gkb.service;

import java.math.BigDecimal;
import java.util.List;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblMonitorIndicatorResult;

public interface TblIndicatorResultService extends BaseService<TblMonitorIndicatorResult, BigDecimal> {
/**
 * 指标列表
 * @param orgid
 * @param pageInfo 
 * @return
 */
public List<TblMonitorIndicatorResult> selectList(String indicatorid, PageInfo<TblMonitorIndicatorResult> pageInfo);
}
