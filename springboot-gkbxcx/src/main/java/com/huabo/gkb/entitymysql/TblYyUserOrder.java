package com.huabo.gkb.entitymysql;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;


/**
 * TblYyUserOrder entity. @author MyEclipse Persistence Tools
 * 用户下单表
 */
@Table(name = "TBL_YY_USER_ORDER")
@NameStyle(value=Style.normal)
public class TblYyUserOrder implements java.io.Serializable {

	private static final long serialVersionUID = -8615838947882117315L;
	@Id
    @Column(name = "ORDERID")
	private BigDecimal orderid; //订单主键ID
	@Column(name = "ORGID")
	private BigDecimal orgId; // 隶属组织
	@Column(name = "ORDERCODE")
	private String ordercode;  // 订单随机码
	@Column(name = "ORDERNO")
	private String orderno;    // 订单编号
	@Column(name = "STATUS")
	private Integer status;    // 订单状态  1. 已创建，未付款   2. 已付款  3. 已到账，存入数据库  4.已完成，
	@Column(name = "ORDERMONEY")
	private Double ordermoney; // 交易金额
	@Column(name = "PAYDATE")
	private Date paydate;      // 支付到账时间
	@Column(name = "CREATEDATE")
	private Date createdate;   // 创建时间
	@Column(name = "CREATESTAFF")
	private BigDecimal staffid; //下单用户
	@Transient
	private String staffName;
	public BigDecimal getOrderid() {
		return orderid;
	}
	public BigDecimal getOrgId() {
		return orgId;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public String getOrderno() {
		return orderno;
	}
	public Integer getStatus() {
		return status;
	}
	public Double getOrdermoney() {
		return ordermoney;
	}
	public Date getPaydate() {
		return paydate;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public BigDecimal getStaffid() {
		return staffid;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setOrderid(BigDecimal orderid) {
		this.orderid = orderid;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setOrdermoney(Double ordermoney) {
		this.ordermoney = ordermoney;
	}
	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public void setStaffid(BigDecimal staffid) {
		this.staffid = staffid;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
}