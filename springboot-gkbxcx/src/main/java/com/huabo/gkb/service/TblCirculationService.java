package com.huabo.gkb.service;

import java.math.BigDecimal;
import java.util.List;

import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblCirculation;

public interface TblCirculationService extends BaseService<TblCirculation, BigDecimal> {
/**
 * 审批列表
 * @param orgid
 * @param pageInfo 
 * @return
 */
public List<TblCirculation> findSpByStaffid(String staffid, PageInfo<TblCirculation> pageInfo);
}
