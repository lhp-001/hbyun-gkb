package com.huabo.gkb.entitymysql;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "TBL_WXUSER_INFO")
@NameStyle(value=Style.normal)
public class TblWxUserInfo implements Serializable {
	private static final long serialVersionUID = -2631142719454917536L;
	@Id
	@Column(name = "USERID") 
	private Integer userId;
	@Column(name = "AVATARURL")
	private String avatarUrl;
	@Column(name = "CITY")
	private String city;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "GENDER")
	private String gender;
	@Column(name = "LANGUAGE")
	private String language;
	@Column(name = "NICKNAME")
	private String nickName;
	@Column(name = "XCXOPENID")
	private String xcxOpenId;
	@Column(name = "WEBOPENID")
	private String webOpenId;
	@Column(name = "PROVINCE")
	private String province;
	@Column(name = "UNIONID")
	private String unionId;
	@Column(name = "STAFFID")
	private String staffId;
	
	private BigDecimal orgId;
	private String realName;
	private String email;
	private String miblePhone;
	private Integer registerChocie;
	private Integer isAdmin;//0 是微信管理员 1 不是
	
	
	@Transient
	public String getRealName() {
		return realName;
	}
	@Transient
	public String getEmail() {
		return email;
	}
	@Transient
	public String getMiblePhone() {
		return miblePhone;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setMiblePhone(String miblePhone) {
		this.miblePhone = miblePhone;
	}
	@Transient
	public BigDecimal getOrgId() {
		return orgId;
	}
	public void setOrgId(BigDecimal orgId) {
		this.orgId = orgId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getXcxOpenId() {
		return xcxOpenId;
	}
	public void setXcxOpenId(String xcxOpenId) {
		this.xcxOpenId = xcxOpenId;
	}
	public String getWebOpenId() {
		return webOpenId;
	}
	public void setWebOpenId(String webOpenId) {
		this.webOpenId = webOpenId;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	@Transient
	public Integer getRegisterChocie() {
		return registerChocie;
	}
	public void setRegisterChocie(Integer registerChocie) {
		this.registerChocie = registerChocie;
	}
	@Transient
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
}
