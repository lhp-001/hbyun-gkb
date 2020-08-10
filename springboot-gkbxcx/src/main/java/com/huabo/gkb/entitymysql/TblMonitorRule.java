package com.huabo.gkb.entitymysql;

import java.math.BigDecimal;

import javax.persistence.Column;

/**
 * 规则管理
 * @author SongXiangYing
 * @date 2016年1月18日 下午5:58:19
 */
public class TblMonitorRule implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**是否行业库  0标识否*/
	public static final Integer IS_HY0 = 0;
	/**是否行业库  1标识是*/
	public static final Integer IS_HY1 = 1;
	@Column(name = "RULEID")
	private BigDecimal ruleid;
	@Column(name = "ORGID")
	private String orgid;
	@Column(name = "RULENAME")
	private String rulename;
	@Column(name = "RULEDESCROPTION")
	private String ruledescription;
	@Column(name = "RULECODE")
	private String rulecode;
	@Column(name = "RULEPRIORITY")
	private String rulepriority;
	@Column(name = "RISKLEVEL")
	private String risklevel;
	@Column(name = "SATUS")
	private String satus;
	@Column(name = "EXEITERNAL")
	private String exeinterval;
	@Column(name = "UNIT")
	private String unit;
	@Column(name = "REGEXP")
	private String regexp;
	@Column(name = "RN")
	private String rn;
	public BigDecimal getRuleid() {
		return ruleid;
	}
	public void setRuleid(BigDecimal ruleid) {
		this.ruleid = ruleid;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getRulename() {
		return rulename;
	}
	public void setRulename(String rulename) {
		this.rulename = rulename;
	}
	public String getRuledescription() {
		return ruledescription;
	}
	public void setRuledescription(String ruledescription) {
		this.ruledescription = ruledescription;
	}
	public String getRulecode() {
		return rulecode;
	}
	public void setRulecode(String rulecode) {
		this.rulecode = rulecode;
	}
	public String getRulepriority() {
		return rulepriority;
	}
	public void setRulepriority(String rulepriority) {
		this.rulepriority = rulepriority;
	}
	public String getRisklevel() {
		return risklevel;
	}
	public void setRisklevel(String risklevel) {
		this.risklevel = risklevel;
	}
	public String getSatus() {
		return satus;
	}
	public void setSatus(String satus) {
		this.satus = satus;
	}
	public String getExeinterval() {
		return exeinterval;
	}
	public void setExeinterval(String exeinterval) {
		this.exeinterval = exeinterval;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRegexp() {
		return regexp;
	}
	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	
}