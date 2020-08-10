package com.huabo.gkb.entity;

public class LunboTuInfo {
	
	public LunboTuInfo() {
		
	}
	public LunboTuInfo(String url) {
		this.url = url;
	}
	private String url;
	private String link;
	public String getUrl() {
		return url;
	}
	public String getLink() {
		return link;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
