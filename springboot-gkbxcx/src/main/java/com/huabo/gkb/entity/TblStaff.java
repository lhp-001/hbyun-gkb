package com.huabo.gkb.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;


@Table(name = "TBL_STAFF")
@NameStyle(value=Style.normal)
public class TblStaff {
	public final static Integer USER_DISABLE=0;//禁用
	public final static Integer USER_ENBLE=1;//启用
	
	public final static String REGISTERUSERPASSWORD = "12345678";
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select HIBERNATE_SEQUENCE.nextval from dual")  
    @Column(name = "staffid")
	private BigDecimal staffid;//主键ID,自动增长
	private String realname;//真实名字
	private String fixedphone;//固定电话
	private String address;//地址
	private String email;//邮箱
	private String miblephone;//手机号码
	private String memo;//备注
	private String username;//用户名（登录名）
	private String password;//密码
	private BigDecimal jobid;//岗位ID
	private Integer status;//状态（1启用，0弃用）
	private BigDecimal  roleid;
	//private TblOrganization tblOrganization;//组织ID
	private BigDecimal orgid;//组织Id
	private Integer outSideId; //标识用户来源 为null是本系统，1：蜂信，2,蜂信购买后的用户   3：华博云系统注册用户管理员  以后可能为2,3...来表示其它来源
	private String outSideOpenId; //外部同步企业来源Id
	private String orgName;
	private String jobName;
	private String orgFatherName;
	private String rn;
	private String checked;
	
	
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	public String getOrgFatherName() {
		return orgFatherName;
	}
	public void setOrgFatherName(String orgFatherName) {
		this.orgFatherName = orgFatherName;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public BigDecimal getStaffid() {
		return staffid;
	}
	public void setStaffid(BigDecimal staffid) {
		this.staffid = staffid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getFixedphone() {
		return fixedphone;
	}
	public void setFixedphone(String fixedphone) {
		this.fixedphone = fixedphone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMiblephone() {
		return miblephone;
	}
	public void setMiblephone(String miblephone) {
		this.miblephone = miblephone;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BigDecimal getJobid() {
		return jobid;
	}
	public void setJobid(BigDecimal jobid) {
		this.jobid = jobid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getOrgid() {
		return orgid;
	}
	public void setOrgid(BigDecimal orgid) {
		this.orgid = orgid;
	}
	public BigDecimal getRoleid() {
		return roleid;
	}
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
	public Integer getOutSideId() {
		return outSideId;
	}
	public void setOutSideId(Integer outSideId) {
		this.outSideId = outSideId;
	}
	public String getOutSideOpenId() {
		return outSideOpenId;
	}
	public void setOutSideOpenId(String outSideOpenId) {
		this.outSideOpenId = outSideOpenId;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
}
