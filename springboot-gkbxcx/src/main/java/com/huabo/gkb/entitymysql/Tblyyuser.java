package com.huabo.gkb.entitymysql;

import java.io.Serializable;
import java.util.Date;

public class Tblyyuser implements Serializable {

	private static final long serialVersionUID = -8171664913707865006L;
	private Integer id;
	private Integer orgId;
	private Integer staffId;//访问用户
	private Integer priceId;
	private Date createdate;
	
	public Integer getId() {
		return id;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public Integer getPriceId() {
		return priceId;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
}
