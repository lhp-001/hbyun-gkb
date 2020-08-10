package com.huabo.gkb.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

/**
 * Created by taoyb on 2016-12-02.
 * 我的传阅类
 */
public class TblCirculation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7274575997012336661L;
	public final static String TYPE_JHSP = "计划审批";
    public final static String URL_JHSP = "/nbsj/jhgl/to_tjsp_info?formid=";//标识计划详情路径
    public final static String TYPE_XMSP = "项目审批";
    public final static String URL_XMSP = "/nbsj/xmgl/to_sp_info?spid=";//标识项目审批详情路径
    public final static String TYPE_TZGL = "投资管理";
    public final static String URL_TZGL = "/fxgl/tzfx/to_sptzgl_info?awid=";//标识投资管理审批详情路径
    public final static String TYPE_DGFH = "底稿复核";
    public final static String URL_DGFH = "/nbsj/sjss/to_sp_dggl?spid=";//标识项目审批详情路径
    public final static String TYPE_SSQRS = "事实确认书";
    public final static String URL_SSQRS = "/nbsj/sjss/to_sp_ssqrs?spid=";//标识事实确认书审批详情路径
    public final static String TYPE_SJTZS = "审计通知书";
    public final static String URL_SJTZS = "/nbsj/sjtzs/to_sp_sjtzs?spid=";//标识项目审批详情路径
    public final static String TYPE_SJBG = "审计报告";
    public final static String URL_SJBG = "/nbkz/sjbg/to_sp_sjbg?spid=";//标识项目审批详情路径
    public final static String TYPE_SJBGFH = "审计报告复核";
    public final static String URL_SJBGFH = "/nbkz/sjbg/to_sp_sjbg_fh?spid=";//标识审计报告复核
    public final static String TYPE_SJBGZQYJ = "审计报告征求意见";
    public final static String URL_SJBGZQYJ = "/nbkz/sjbg/to_sp_sjbg_zqyj?spid=";//标识审计报告征求意见
    

    public final static String STATE_FQ = "审批中";
    public final static String STATE_TZ = "需调整";
    public final static String STATE_ZZ = "已终止";
    public final static String STATE_TG = "已通过";
    public final static String STATE_ZD = "中断";
	@Column(name = "CYID")
    private BigDecimal cyId;//传阅id CYID
	@Column(name = "CYCODE")
    private String cyCode;//编号CYCODE
	@Column(name = "CYNAME")
    private String cyName;//名称CYNAME
	@Column(name = "CYDATE")
    private Date cyDate;//发布时间CYDATE
	@Column(name = "CYSTATE")
    private String cyState;//标记状态  如已发起 已结束。。。CYSTATE
	@Column(name = "CYTYPE")
    private String cyType;//类型   从哪发过来的CYTYPE
	@Column(name = "CYURL")
    private String cyUrl;//详情路径CYURL
	@Column(name = "CYSTAFFID")
    private String cyStaffid;//用户CYSTAFFID
	@Column(name = "TASKID")
    private String taskId;
	@Column(name = "BUSINESSKEY")
    private String businessKey;
	@Column(name = "DEFINITIONID")
    private String definitionId;
	@Column(name = "RN")
    private String rn;
    public String getBusinessKey() {
        return businessKey;
    }
    public TblCirculation setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }
    public String getDefinitionId() {
        return definitionId;
    }
    public TblCirculation setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
        return this;
    }
    public String getCyStaffid() {
        return cyStaffid;
    }
    public TblCirculation setCyStaffid(String cyStaffid) {
        this.cyStaffid = cyStaffid;
        return this;
    }
    public BigDecimal getCyId() {
        return cyId;
    }
    public TblCirculation setCyId(BigDecimal cyId) {
        this.cyId = cyId;
        return this;
    }
    public String getCyCode() {
        return cyCode;
    }
    public TblCirculation setCyCode(String cyCode) {
        this.cyCode = cyCode;
        return this;
    }
    public String getCyName() {
        return cyName;
    }
    public TblCirculation setCyName(String cyName) {
        this.cyName = cyName;
        return this;
    }
    public Date getCyDate() {
        return cyDate;
    }
    public TblCirculation setCyDate(Date cyDate) {
        this.cyDate = cyDate;
        return this;
    }
    public String getCyState() {
        return cyState;
    }
    public TblCirculation setCyState(String cyState) {
        this.cyState = cyState;
        return this;
    }
    public String getCyType() {
        return cyType;
    }
    public TblCirculation setCyType(String cyType) {
        this.cyType = cyType;
        return this;
    }
    public String getCyUrl() {
        return cyUrl;
    }
    public TblCirculation setCyUrl(String cyUrl) {
        this.cyUrl = cyUrl;
        return this;
    }
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	
}
