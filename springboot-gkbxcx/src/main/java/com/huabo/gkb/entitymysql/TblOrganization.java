package com.huabo.gkb.entitymysql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;


/**
 * TblOrganization entity. @author MyEclipse Persistence Tools
 */
/**
 * 
* <p>Title:TblOrganization </p>
* <p>Description: 组织架构</p>
* <p>Company: 华博风控信息技术(北京)有限公司</p> 
* @author caochaochao
* @date 2018年4月25日 下午1:17:05
 */
@Table(name = "TBL_ORGANIZATION")
@NameStyle(value=Style.normal)
public class TblOrganization implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final  Integer DEL_YES =1; 
	public static final  Integer DEL_NO  =0; 
	public static final Integer AUDITTYPE=1;
	/**标识外聘专家库*/
	public static final String WPZJK="wpzjk";
	
	public static final BigDecimal REGISTERORGFATHERID = new BigDecimal("201047");
	public static final Integer REGISTERORGTYPE = 3;
	
	
    @Id
    @Column(name = "ORGID")
	private BigDecimal orgid;//主键ID,自动增长
    @Column(name = "ORGNAME")
	private String orgname;//组织（部门）名称
    @Column(name = "FATHERORGID")
	private BigDecimal fatherorgid;//父ID
    @Column(name = "ORGNUMBER")
	private String orgnumber;//机构编号
    @Column(name = "ORGMENO")
	private String orgmeno;//机构简介
    @Column(name = "MEMO")
	private String memo;//备注
    @Column(name = "ICODE")
	private String icode;//行业架构ID（在哪个行业下创建行业知识库/行业缺陷库/行业问题库/行业数据库/行业指标库/行业规则库/行业模型库，该字段为哪个行业ID）
    @Column(name = "STATUS")
	private Integer status;//状态（1弃用，0启用）
    @Column(name = "ISZY")
	private String iszy;//外聘专家库标识字段（部门为外聘专家库，则为wpzjk，否则为空）
    @Column(name = "HYZSKTYPE")
	private String hyzskType;//标识行业知识库的所属模块（风险管控为fxmanage，内部控制为nbkz，智能审计为znsj，智能监控为znjk）
    @Column(name = "ORDERID")
	private Integer orderid;//排序编号，用于在显示组织架构排序
    @Column(name = "ORGTYPE")
	private Integer orgtype;	//是否是公司（普通部门为0，一级公司为1，二级公司为2，三级公司为3，行业架构为100，行业问题库为101，行业缺陷库为102，行业规则库为103，行业指标库为104，行业模型库为105，行业知识库为106，审计经验库为107，行业数据库为108）
    @Column(name = "AUDITTYPE")
	private Integer auditType;  //是否是主责部门/审计部（1是，0否
    
    @Column(name = "OUTSIDEID")
    private Integer outsideId;
    @Column(name = "OUTSIDEOPENDID")
    private String outsideOpendId;
    @Column(name = "ISAUTONUMBER")
    private Integer isAutoNumber;
    @Column(name = "ORGCREATE")
    private Date orgCreate;
	@javax.persistence.Transient
    private List<TblOrganization> orgList = new ArrayList<TblOrganization>(0);
	@javax.persistence.Transient
	private List<TblStaff> staffList = new ArrayList<TblStaff>(0);
	@javax.persistence.Transient
	private Integer staffCount;
	
	
	public TblOrganization(BigDecimal orgid,String orgname) {
		this.orgid = orgid;
		this.orgname = orgname;
	}
	public List<TblOrganization> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<TblOrganization> orgList) {
		this.orgList = orgList;
	}

	public TblOrganization() {
	}
	public String getHyzskType() {
		return hyzskType;
	}
	
	public void setHyzskType(String hyzskType) {
		this.hyzskType = hyzskType;
	}
	
	public String getIcode() {
		return icode;
	}
	
	public void setIcode(String icode) {
		this.icode = icode;
	}
	public TblOrganization(String orgname, BigDecimal fatherorgid,
			String orgnumber, String orgmeno, String memo) {
		this.orgname = orgname;
		this.fatherorgid = fatherorgid;
		this.orgnumber = orgnumber;
		this.orgmeno = orgmeno;
		this.memo = memo;
		/*	this.tblProjects = tblProjects;
		this.tblStaffs = tblStaffs;
		this.tblMonitorRules = tblMonitorRules;*/
	}

	public BigDecimal getOrgid() {
		return this.orgid;
	}

	public void setOrgid(BigDecimal orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public BigDecimal getFatherorgid() {
		return this.fatherorgid;
	}

	public void setFatherorgid(BigDecimal fatherorgid) {
		this.fatherorgid = fatherorgid;
	}

	public String getOrgnumber() {
		return this.orgnumber;
	}

	public void setOrgnumber(String orgnumber) {
		this.orgnumber = orgnumber;
	}

	public String getOrgmeno() {
		return this.orgmeno;
	}

	public void setOrgmeno(String orgmeno) {
		this.orgmeno = orgmeno;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(Integer orgtype) {
		this.orgtype = orgtype;
	}

	

	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIszy() {
		return iszy;
	}

	public void setIszy(String iszy) {
		this.iszy = iszy;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getOutsideId() {
		return outsideId;
	}

	public String getOutsideOpendId() {
		return outsideOpendId;
	}

	public Integer getIsAutoNumber() {
		return isAutoNumber;
	}

	public Date getOrgCreate() {
		return orgCreate;
	}

	public void setOutsideId(Integer outsideId) {
		this.outsideId = outsideId;
	}

	public void setOutsideOpendId(String outsideOpendId) {
		this.outsideOpendId = outsideOpendId;
	}

	public void setIsAutoNumber(Integer isAutoNumber) {
		this.isAutoNumber = isAutoNumber;
	}

	public void setOrgCreate(Date orgCreate) {
		this.orgCreate = orgCreate;
	}
	public List<TblStaff> getStaffList() {
		return staffList;
	}
	public void setStaffList(List<TblStaff> staffList) {
		this.staffList = staffList;
	}
	public Integer getStaffCount() {
		return staffCount;
	}
	public void setStaffCount(Integer staffCount) {
		this.staffCount = staffCount;
	}
}