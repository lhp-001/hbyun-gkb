package com.huabo.gkb.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

/**
 * 规则结果
 * @author SongXiangYing
 * @date 2016年1月21日 上午10:55:21
 */

public class TblMonitorPrewarning implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "PREWARNINGID")
	private BigDecimal prewarningid;//规则结果id
	@Column(name = "SAVEDATETIME")
	private Date saveDateTime;//执行时间
	@Column(name = "STAFFID")
	private String staffid;//用户
	@Column(name = "RULEID")
	private String ruleid;//规则id
	@Column(name = "SIGNID")
	private String signId;
	@Column(name = "MEMO")
	private String memo;//备注
	@Column(name = "RESULTCOUNT")
	private Integer resultCount;
	@Column(name = "RN")
	private Integer rn;
	@Column(name = "REALNAME")
	private String realname;
	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public BigDecimal getPrewarningid() {
		return this.prewarningid;
	}

	public void setPrewarningid(BigDecimal prewarningid) {
		this.prewarningid = prewarningid;
	}


	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getSaveDateTime() {
		return saveDateTime;
	}

	public void setSaveDateTime(Date saveDateTime) {
		this.saveDateTime = saveDateTime;
	}

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public String getRuleid() {
		return ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	public Integer getRn() {
		return rn;
	}

	public void setRn(Integer rn) {
		this.rn = rn;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	

}