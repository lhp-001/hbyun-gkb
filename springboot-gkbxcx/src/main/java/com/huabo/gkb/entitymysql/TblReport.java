package com.huabo.gkb.entitymysql;

import java.math.BigDecimal;

import javax.persistence.Column;


public class TblReport {
	
    @Column(name = "PAGEID")
	private BigDecimal pageid;
    @Column(name = "RQURL")
	private String rqurl;
    @Column(name = "PAGENAME")
	private String pagename;
    @Column(name="URL")
    private String url;
    
	public BigDecimal getPageid() {
		return pageid;
	}
	public void setPageid(BigDecimal pageid) {
		this.pageid = pageid;
	}
	public String getRqurl() {
		return rqurl;
	}
	public void setRqurl(String rqurl) {
		this.rqurl = rqurl;
	}
	public String getPagename() {
		return pagename;
	}
	public void setPagename(String pagename) {
		this.pagename = pagename;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    
}
