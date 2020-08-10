package com.huabo.gkb.entity;


public class WxAuthPhone {
	private String encryptedData;
    private String sessionkey;
    private String ivcode;
    
	public String getEncryptedData() {
		return encryptedData;
	}
	public String getSessionkey() {
		return sessionkey;
	}
	public String getIvcode() {
		return ivcode;
	}
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}
	public void setIvcode(String ivcode) {
		this.ivcode = ivcode;
	}
}
