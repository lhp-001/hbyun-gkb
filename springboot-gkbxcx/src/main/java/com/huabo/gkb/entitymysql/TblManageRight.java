package com.huabo.gkb.entitymysql;

import java.io.Serializable;
import java.math.BigDecimal;

public class TblManageRight implements Serializable {

	private static final long serialVersionUID = 6049173270204095809L;
	
	private BigDecimal rightid;
	private String rightname;
	private String righturl;
	private BigDecimal fatherrightid;
	private BigDecimal funcorder;
	private String indicatorstatus;
	public BigDecimal getRightid() {
		return rightid;
	}
	public String getRightname() {
		return rightname;
	}
	public String getRighturl() {
		return righturl;
	}
	public BigDecimal getFatherrightid() {
		return fatherrightid;
	}
	public BigDecimal getFuncorder() {
		return funcorder;
	}
	public String getIndicatorstatus() {
		return indicatorstatus;
	}
	public void setRightid(BigDecimal rightid) {
		this.rightid = rightid;
	}
	public void setRightname(String rightname) {
		this.rightname = rightname;
	}
	public void setRighturl(String righturl) {
		this.righturl = righturl;
	}
	public void setFatherrightid(BigDecimal fatherrightid) {
		this.fatherrightid = fatherrightid;
	}
	public void setFuncorder(BigDecimal funcorder) {
		this.funcorder = funcorder;
	}
	public void setIndicatorstatus(String indicatorstatus) {
		this.indicatorstatus = indicatorstatus;
	}
}
