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
 * TblYyUserQuery entity. @author MyEclipse Persistence Tools
 * 用户 使用望远镜 查询记录表
 */
@Table(name = "TBL_YY_USER_QUERY")
@NameStyle(value=Style.normal)
public class TblYyUserQuery implements java.io.Serializable {
	private static final long serialVersionUID = 7530709132520051364L;
	@Id
    @Column(name = "RECORDID")
	private BigDecimal recordid; //查询记录
	
	@Column(name = "QUERYTIME")
	private Date querytime;  //查询时间
	
	@Column(name = "PAYMONEY")
	private Double paymoney;   	   //查询支付时间
	
	@Column(name = "REPORTNAME")
	private String reportName;
	
	@Column(name = "QUERYSTAFF")
	private BigDecimal staffid; // 查询用户
	
	@Column(name = "ORGID")
	private BigDecimal orgid; // 所属组织
	
	@Transient
	private String staffname;
	@Transient
	private String orgname;
	
	
	public BigDecimal getRecordid() {
		return recordid;
	}
	public Date getQuerytime() {
		return querytime;
	}
	public Double getPaymoney() {
		return paymoney;
	}
	public String getReportName() {
		return reportName;
	}
	public BigDecimal getStaffid() {
		return staffid;
	}
	public BigDecimal getOrgid() {
		return orgid;
	}
	public String getStaffname() {
		return staffname;
	}
	public void setRecordid(BigDecimal recordid) {
		this.recordid = recordid;
	}
	public void setQuerytime(Date querytime) {
		this.querytime = querytime;
	}
	public void setPaymoney(Double paymoney) {
		this.paymoney = paymoney;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public void setStaffid(BigDecimal staffid) {
		this.staffid = staffid;
	}
	public void setOrgid(BigDecimal orgid) {
		this.orgid = orgid;
	}
	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
}