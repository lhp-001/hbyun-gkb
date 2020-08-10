package com.huabo.gkb.entitymysql;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

@Table(name = "TBL_YY_PRICE")
@NameStyle(value=Style.normal)
public class Tblyyprice implements Serializable {
	
	private static final long serialVersionUID = -8331036685562712852L;
	@Id
    @Column(name = "PRICEID")
	private Integer priceId;

	@Column(name = "INTERFACENAME")
	private String interfacename;//接口名称
	
	@Column(name = "ACCOUNTRULE")
	private String accountrule;//计费规则
	
	@Column(name = "PRICE")
	private Double price;//单价
	
	@Column(name = "HBPRICE")
	private Double hbprice;//收款单价
	
	@Column(name = "ANNUALPRICE")
	private String annualprice;//包年价格
	
	@Column(name = "COMPANYID")
	private Integer companyId;

	public Integer getPriceId() {
		return priceId;
	}

	public String getInterfacename() {
		return interfacename;
	}

	public String getAccountrule() {
		return accountrule;
	}

	public Double getPrice() {
		return price;
	}

	public Double getHbprice() {
		return hbprice;
	}

	public String getAnnualprice() {
		return annualprice;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}

	public void setInterfacename(String interfacename) {
		this.interfacename = interfacename;
	}

	public void setAccountrule(String accountrule) {
		this.accountrule = accountrule;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setHbprice(Double hbprice) {
		this.hbprice = hbprice;
	}

	public void setAnnualprice(String annualprice) {
		this.annualprice = annualprice;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
