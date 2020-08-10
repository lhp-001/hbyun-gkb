package com.huabo.gkb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

/**
 * 指标实行结果
 * @author SongXiangYing
 * @date 2016年1月26日 上午1:18:36
 */
public class TblMonitorIndicatorResult implements Serializable{

	private static final long serialVersionUID = 1L;
	public static Integer YJYJ = 1;
	public static Integer ZJ=0;
	/**
	 * 监控执行
	 */
	public static Integer ZKZX=3;
	@Column(name = "RESULTID")
	private BigDecimal resultId;
	@Column(name = "SCORE")
	private Double score;
	@Column(name = "SAVETIME")
	private Date saveTime;
	@Column(name = "INDICATORID")
	private BigDecimal indicatorid;
	@Column(name = "TOLERANCE")
	private String tolerance;//tolerance字段：0-绿色；1-黄色；2-红色；其他数字-结果值不在阈值范围内
	@Column(name = "SOURCE")
	private Integer source;
	@Column(name = "SIGN")
	private String sign;
	@Column(name = "EXECUTEID")
	private String executeId;
	@Column(name = "RN")
	private String rn;
	@Column(name = "REALNAME")
	private String realname;
	public BigDecimal getResultId() {
		return resultId;
	}
	public void setResultId(BigDecimal resultId) {
		this.resultId = resultId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Date getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}
	public String getTolerance() {
		return tolerance;
	}
	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getExecuteId() {
		return executeId;
	}
	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}
	public BigDecimal getIndicatorid() {
		return indicatorid;
	}
	public void setIndicatorid(BigDecimal indicatorid) {
		this.indicatorid = indicatorid;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
}
