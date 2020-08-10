package com.huabo.gkb.entitymysql;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;



/**
 * 指标
 * @author SongXiangYing
 *
 */
public class TblIndicator implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7195156239572150975L;
	/**是否行业库  0标识否*/
	public static final Integer IS_HY0 = 0;
	/**是否行业库  1标识是*/
	public static final Integer IS_HY1 = 1;
	/**是否行业库  2标识是通用行业*/
	public static final Integer IS_HY2 = 2;
	@Column(name = "INDICATORID")
	private BigDecimal indicatorid;
	@Column(name = "INDICATORCODE")
	private String indicatorcode;//编号
	@Column(name = "INDICATORNAME")
	private String indicatorname;//指标名
	@Column(name = "FORMULA")
	private String formula;//公式
	@Column(name = "INDICATORDES")
	private String indicatordes;//指标描述
	@Column(name = "DEPARTMENTINCHARGE")
	private String departmentincharge;//
	@Column(name = "INDICATORSTATUS")
	private String indicatorstatus;//指标状态
	@Column(name = "UNITTYPE")
	private String unittype;//类型
	@Column(name = "FORMULADES")
	private String formulades;//
	@Column(name = "FORLUMACHS")
	private String forlumachs;//公式
	@Column(name = "ORGID")
	private String orgid;//所属部门
	@Column(name = "RUNSTATUS")
	private BigDecimal runstatus;//0 未执行  1  执行中  2 完成
	@Column(name = "CREATEDATE")
	private Date createdate;//创建时间
	@Column(name = "STAFFID")
	private BigDecimal staffid;//创建人
	@Column(name = "INDICATORDB")
	private Integer indicatordb;//是否是行业
	@javax.persistence.Transient
	@Column(name = "RN")
	private Integer rn;
	public BigDecimal getIndicatorid() {
		return indicatorid;
	}
	public void setIndicatorid(BigDecimal indicatorid) {
		this.indicatorid = indicatorid;
	}

	public String getIndicatorcode() {
		return indicatorcode;
	}
	public void setIndicatorcode(String indicatorcode) {
		this.indicatorcode = indicatorcode;
	}
	public String getIndicatorname() {
		return indicatorname;
	}
	public void setIndicatorname(String indicatorname) {
		this.indicatorname = indicatorname;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getIndicatordes() {
		return indicatordes;
	}
	public void setIndicatordes(String indicatordes) {
		this.indicatordes = indicatordes;
	}
	public String getDepartmentincharge() {
		return departmentincharge;
	}
	public void setDepartmentincharge(String departmentincharge) {
		this.departmentincharge = departmentincharge;
	}
	public String getIndicatorstatus() {
		return indicatorstatus;
	}
	public void setIndicatorstatus(String indicatorstatus) {
		this.indicatorstatus = indicatorstatus;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getUnittype() {
		return unittype;
	}
	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}
	public String getFormulades() {
		return formulades;
	}
	public void setFormulades(String formulades) {
		this.formulades = formulades;
	}
	public BigDecimal getRunstatus() {
		return runstatus;
	}
	public void setRunstatus(BigDecimal runstatus) {
		this.runstatus = runstatus;
	}
	public BigDecimal getStaffid() {
		return staffid;
	}
	public void setStaffid(BigDecimal staffid) {
		this.staffid = staffid;
	}
	public Integer getIndicatordb() {
		return indicatordb;
	}
	public void setIndicatordb(Integer indicatordb) {
		this.indicatordb = indicatordb;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getForlumachs() {
		return forlumachs;
	}
	public void setForlumachs(String forlumachs) {
		this.forlumachs = forlumachs;
	}
	public Integer getRn() {
		return rn;
	}
	public void setRn(Integer rn) {
		this.rn = rn;
	}

	
}