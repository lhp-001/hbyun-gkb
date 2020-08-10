package com.huabo.gkb.service;

import java.math.BigDecimal;
import java.util.List;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;

public interface TblIndicatorService extends BaseService<TblIndicator, BigDecimal> {
/**
 * 指标列表
 * @param orgid
 * @param pageInfo 
 * @return
 */
public List<TblIndicator> selectList(String orgid, PageInfo<TblIndicator> pageInfo);
}
