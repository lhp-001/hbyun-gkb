package com.huabo.gkb.entitymysql;

import javax.persistence.Column;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * TblYyQueryPrice entity. @author MyEclipse Persistence Tools
 * 用户查询记录 与 接口中间关系表
 */
@Table(name = "TBL_YY_QUERY_PRICE")
@NameStyle(value=Style.normal)
public class TblYyQueryPrice implements java.io.Serializable {

	private static final long serialVersionUID = 4973875856157572952L;
	@Column(name = "PRICEID")
	private Integer priceid;
	@Column(name = "RECORDID")
	private Integer recordid;
	@Column(name = "PRICEMONEY")
	private Double priceMoney;
	public Integer getPriceid() {
		return priceid;
	}
	public Integer getRecordid() {
		return recordid;
	}
	public Double getPriceMoney() {
		return priceMoney;
	}
	public void setPriceid(Integer priceid) {
		this.priceid = priceid;
	}
	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}
	public void setPriceMoney(Double priceMoney) {
		this.priceMoney = priceMoney;
	}
}