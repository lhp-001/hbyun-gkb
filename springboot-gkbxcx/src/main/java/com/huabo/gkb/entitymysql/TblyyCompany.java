package com.huabo.gkb.entitymysql;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 分组表-公司表
 * @author tj
 *
 */
@Table(name = "TBL_YY_COMPANY")
@NameStyle(value=Style.normal)
public class TblyyCompany {

	@Id
    @Column(name = "companyid")
	private Integer companyid;
	@Column(name = "companyName")
	private String companyName;
	@Column(name = "teamid")
	private Integer teamId;//分组
	@Column(name = "REPORTID")
	private BigDecimal reportid;
	@Column(name = "fxtype")
	private String fxtype;
	@Column(name = "STAFFID")
	private Integer staffId; //创建人
	@Column(name = "createDate")
	private Date createDate;
	@Column(name = "ORGID")
	private Integer orgId;//创建公司
	
	@Column(name = "CSTATUS")
	private Integer cstatus;//0取消监控 1已监控
	
	public Integer getCompanyid() {
		return companyid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public Integer getTeamId() {
		return teamId;
	}
	public BigDecimal getReportid() {
		return reportid;
	}
	public String getFxtype() {
		return fxtype;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public void setReportid(BigDecimal reportid) {
		this.reportid = reportid;
	}
	public void setFxtype(String fxtype) {
		this.fxtype = fxtype;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getCstatus() {
		return cstatus;
	}
	public void setCstatus(Integer cstatus) {
		this.cstatus = cstatus;
	}
}
