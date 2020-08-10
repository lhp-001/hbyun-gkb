package com.huabo.gkb.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;


/**
 * TblYyOrgDeposit entity. @author MyEclipse Persistence Tools
 * 用户购买望远镜存款表
 */
@Table(name = "TBL_YY_ORG_DEPOSIT")
@NameStyle(value=Style.normal)
public class TblYyOrgDeposit implements java.io.Serializable {
	private static final long serialVersionUID = -4693586244089473222L;
	
	@Id
    @GeneratedValue(
    		  strategy = GenerationType.IDENTITY,
    		  generator = "select wxPay_sequence.nextval from dual")  
    @Column(name = "DEPOSITID")
	private BigDecimal depositid; //存款信息主键
	@Column(name = "ORGID")
	private BigDecimal orgId; //隶属组织
	@Column(name = "TOTALMONEY")
	private Double totalmoney; //总金额
	@Column(name = "TOTALPAYMONEY")
	private Double totalpaymoney; // 总花费金额
	@Column(name = "LASTDATE")
	private Date lastdate; //最后一次充值到账时间
	
	
	public BigDecimal getDepositid() {
		return depositid;
	}
	public BigDecimal getOrgId() {
		return orgId;
	}
	public Double getTotalmoney() {
		return totalmoney;
	}
	public Double getTotalpaymoney() {
		return totalpaymoney;
	}
	public Date getLastdate() {
		return lastdate;
	}
	public void setDepositid(BigDecimal depositid) {
		this.depositid = depositid;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}
	public void setTotalpaymoney(Double totalpaymoney) {
		this.totalpaymoney = totalpaymoney;
	}
	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
}