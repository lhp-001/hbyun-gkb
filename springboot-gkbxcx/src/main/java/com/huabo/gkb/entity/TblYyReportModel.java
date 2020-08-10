package com.huabo.gkb.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;


/**
 * TblYyReportModel entity. @author MyEclipse Persistence Tools
 */
@Table(name = "TBL_YY_REPORT_MODEL")
@NameStyle(value=Style.normal)
public class TblYyReportModel implements java.io.Serializable {

	private static final long serialVersionUID = 5978966217462622850L;
	
	@Id
    @GeneratedValue(
    		  strategy = GenerationType.IDENTITY,
    		  generator = "select wxPay_sequence.nextval from dual")  
    @Column(name = "REPORTID")
	private BigDecimal reportid;
	
	@Column(name = "REPORTNAME")
	private String reportname;
	
	@Column(name = "REPORTURL")
	private String reporturl;
	
	@Column(name = "ANNUALPRICE")
	private Double annualprice;
	
	@Column(name = "REPORTPRICE")
	private Double reportprice;
	
	@Column(name = "ORGID")
	private BigDecimal orgId; // 隶属组织
	
	@Column(name = "STAFFID")
	private BigDecimal staffId;
	
	@Column(name = "PRICEID")
	private String priceid;
	
	
	public BigDecimal getReportid() {
		return reportid;
	}
	public String getReportname() {
		return reportname;
	}
	public String getReporturl() {
		return reporturl;
	}
	public Double getAnnualprice() {
		return annualprice;
	}
	public Double getReportprice() {
		return reportprice;
	}
	public BigDecimal getOrgId() {
		return orgId;
	}
	public BigDecimal getStaffId() {
		return staffId;
	}
	public String getPriceid() {
		return priceid;
	}
	public void setReportid(BigDecimal reportid) {
		this.reportid = reportid;
	}
	public void setReportname(String reportname) {
		this.reportname = reportname;
	}
	public void setReporturl(String reporturl) {
		this.reporturl = reporturl;
	}
	public void setAnnualprice(Double annualprice) {
		this.annualprice = annualprice;
	}
	public void setReportprice(Double reportprice) {
		this.reportprice = reportprice;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public void setStaffId(BigDecimal staffId) {
		this.staffId = staffId;
	}
	public void setPriceid(String priceid) {
		this.priceid = priceid;
	}
}