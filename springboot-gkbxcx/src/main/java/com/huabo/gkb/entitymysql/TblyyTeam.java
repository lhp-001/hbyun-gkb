package com.huabo.gkb.entitymysql;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

/**
 * 分组表
 * @author tj
 *
 */
@Table(name = "TBL_YY_TEAM")
@NameStyle(value=Style.normal)
public class TblyyTeam {

	@Id
    @Column(name = "teamid")
	private Integer teamid;//小组ID
	 @Column(name = "teamName")
	private String teamName;//分组名称
	 @Column(name = "STAFFID")
	private Integer createPeson; //创建人
	 @Column(name = "createDate")
	private Date createDate;//创建时间
	 @Column(name = "COMPANYID")
	private Integer compay;//所属公司
	public Integer getTeamid() {
		return teamid;
	}
	public void setTeamid(Integer teamid) {
		this.teamid = teamid;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Integer getCreatePeson() {
		return createPeson;
	}
	public void setCreatePeson(Integer createPeson) {
		this.createPeson = createPeson;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCompay() {
		return compay;
	}
	public void setCompay(Integer compay) {
		this.compay = compay;
	}
	
	
}
