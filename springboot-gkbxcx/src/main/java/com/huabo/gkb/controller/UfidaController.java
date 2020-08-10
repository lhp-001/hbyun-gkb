package com.huabo.gkb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.service.TblOrganizationService;
import com.huabo.gkb.service.TblYyOrgDepositService;
import com.huabo.gkb.service.TblYyUserQueryService;
import com.huabo.gkb.service.TblyypriceService;
import com.huabo.gkb.service.TblyyuserService;
import com.huabo.gkb.util.YHttpclicent;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("unfida")
public class UfidaController {
	private static final Logger logger = LoggerFactory.getLogger(UfidaController.class);
	
	@Resource
	public TblyypriceService tblyypriceService;
	
	@Resource
	public TblOrganizationService tblOrganizationService;
	
	@Resource
	public TblyyuserService tblyyuserService;
	
	@Resource
	public TblYyOrgDepositService tblYyOrgDepositService;
	
	@Resource
	public TblYyUserQueryService tblYyUserQueryService;
	
	//
	
	@RequestMapping(value = "/judgeSelectCount")
	@ResponseBody
	public Integer judgeSelectCount(HttpServletRequest request,HttpServletResponse response,String orgId,String staffId,String reportName)throws Exception {
		Integer count = 0;
		response.addHeader("Access-Control-Allow-Origin", "*");
		if( SystemStaticValue.WBSJSTAFFID.indexOf(staffId) == -1 ) {
			Integer companyId = tblOrganizationService.findCompanyIdByDeptId(orgId);
			count = tblyyuserService.findByUserOne(companyId);
			//试用次数已用完  判断账户余额是否支持使用
			if(count >= 1) {
				//判断公司费用是否支持此次查询	 
	        	String surplusMoney = this.tblYyOrgDepositService.findUrplusMoney(companyId);
	        	if(surplusMoney == null){
	        		return -1;//账户余额不足，请充值
	        	}	 
	        	Double money = this.tblYyOrgDepositService.findTostMoney(reportName); 
	        	Double smoney = Double.valueOf(surplusMoney);	 
	        	if(smoney < money){
	        		return -1;//账户余额不足，请充值
	        	}
			}
		}
		return count;
	}
	
	@RequestMapping(value = "/search")
	@ResponseBody
	public String enterpriselist(HttpServletRequest request,HttpServletResponse response,String staffId,String orgId,String companyName,String reportName)throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("根据企业名称查询企业信息");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		String result = YHttpclicent.findSerch(companyName);
		JSONObject jsonObject = JSONObject.fromObject(result);
        String reason = jsonObject.getString("reason");
        saveprice(reportName,staffId,orgId);//搜索接口
        if(reason!=null && reason.equals("ok")){
	        String resultall = jsonObject.getString("result");
	        JSONObject resultobj = JSONObject.fromObject(resultall);
	        String total=resultobj.getString("total");
	        String items=resultobj.getString("items").replace("<em>","").replace("<\\/em>","");
	        resultMap.put("record", total);
	        resultMap.put("data", items);
        }else{
        	resultMap.put("record", "0");
        }
		resultMap.put("record", "1");
		return JSONObject.fromObject(resultMap).toString();
	}
	

	/**
	 * 公司背景
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/companybackground")
	@ResponseBody
	public String companybackground(HttpServletRequest request,HttpServletResponse response,String companyName,String companyId,String staffId,String orgId,String reportName) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("根据企业名称查询企业信息");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			//========1===公司背景=================
			//1.1基本信息
			String business = findbyJbxx(companyId,staffId,orgId);
			resultMap.put("business", business);
			//1.2个人信息
			String keypersonnel = findbyZyrr(companyId,staffId,orgId);
			resultMap.put("keypersonnel", keypersonnel);
			//1.3股东信息
			String shareholder = findbyGdxx(companyId,staffId,orgId);
			resultMap.put("shareholder", shareholder);
			//1.4对外投资
			String outbounds = findbyDwtz(companyId,staffId,orgId);
			resultMap.put("outbounds", outbounds);
			//1.6分支机构
			String branchs = findbyFzjg(companyId,staffId,orgId);
			resultMap.put("branchs", branchs);
			saveprice(reportName,staffId,orgId);//搜索接口
		}catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 经营状况
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/managementcondition")
	@ResponseBody
	public String managementcondition(HttpServletRequest request,HttpServletResponse response,String companyName,String companyId,String staffId,String orgId,String reportName){
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("根据企业名称查询企业信息");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			//2.1招投标
			String managementcondition = findbyManage(companyId,staffId,orgId);
			resultMap.put("managementcondition", managementcondition);
			//2.2债券信息
			String bond = findbyZqxx(companyName,staffId,orgId);
			resultMap.put("bond", bond);
			//2.3招聘
			String recruit = findbyZP(companyName,staffId,orgId);
			resultMap.put("recruit", recruit);
			//2.4税务评级
			String taxation = findbySwpj(companyId,staffId,orgId);
			resultMap.put("taxation", taxation);
			//2.5抽查检查
			String spotcheck = findbyCcjc(companyName,staffId,orgId);
			resultMap.put("spotcheck", spotcheck);
			saveprice(reportName,staffId,orgId);//搜索接口
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}
	
	
	/**
	 * 司法风险
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/judicial_risk")
	@ResponseBody
	public String judicial_risk(HttpServletRequest request,HttpServletResponse response,String companyName,String companyId,String staffId,String orgId,String reportName){
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("根据企业名称查询企业信息");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			//========4===司法风险=================
			//4.1法律诉讼
			String legal = findbyFlss(companyName,staffId,orgId);
			resultMap.put("legal", legal);
			//4.2法院公告
			String judicialrisk = findbyJudirisk(companyName,staffId,orgId);
			resultMap.put("judicialrisk", judicialrisk);
			//4.3失信人
			String dishonest = findbySxr(companyName,staffId,orgId);
			resultMap.put("dishonest", dishonest);
			//4.4被执行人
			String executor = findbyBzxr(companyId,staffId,orgId);
			resultMap.put("executor", executor);
			//4.5开庭公告
			/*String noticecourt = findbyKtgg(companyId,staffId,orgId);
			resultMap.put("noticecourt", noticecourt);*/
			saveprice(reportName,staffId,orgId);//搜索接口
		}catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}
	
	private void saveprice(String priceName,String staffId,String orgId) throws Exception{
		if( SystemStaticValue.WBSJSTAFFID.indexOf(staffId) == -1 ) {
	        Integer companyId = tblOrganizationService.findCompanyIdByDeptId(orgId);
	        try {
	        	if(SystemStaticValue.DataType == 0) {
	        		this.tblYyUserQueryService.dealUserQueryMySql(companyId,staffId,priceName);
	        	}else {
	        		this.tblYyUserQueryService.dealUserQuery(companyId,staffId,priceName);
	        	}
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
	}
	
	/**
	 * 招投标
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyManage(String companyid,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findmanage(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 items = resultobj.getString("items");
         }
         return items;
	}
	/**
	 * 债券信息
	 * @param companyName
	 * @return
	 */
	@SuppressWarnings("unused")
	private String findbyZqxx(String companyName,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findzqxx("",companyName,1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason != null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 items = resultobj.getString("items");
         }
         return items;
	}
	/**
	 * 招聘
	 * @param companyName
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyZP(String companyName,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findzp("",companyName,1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 items = resultobj.getString("items");
    	     
         }
         return items;
	}
	/**
	 * 税务评级
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbySwpj(String companyid,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findswpj(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 items = resultobj.getString("items");
    	     
         }
         return items;
	}
	
	/**
	 * 抽查检查
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyCcjc(String companyName,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findccjc("",companyName,1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 items = resultobj.getString("items");
         }
         return items;
	}
	
	/**
	 * 基本信息
	 * @param companyid
	 * @return
	 */
	private String findbyJbxx(String companyid,String staffId,String orgId) throws Exception{
		String result = YHttpclicent.findGSXX(companyid,"");
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String context = null;
         if(reason!=null && reason.equals("ok")){
	        String resultall = jsonObject.getString("result");
	        JSONObject resultobj = JSONObject.fromObject(resultall);
	        JsonConfig jsonC = new JsonConfig();
		    jsonC.setExcludes(new String[] { "staffList" });
		    context = JSONObject.fromObject(resultobj,jsonC).toString();
         }
         return context;
	}
	
	/**
	 * 主要人员
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyZyrr(String companyid,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findzyrr(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 items = resultobj.getString("items");
         }
         return items;
	}
	/**
	 * 股东信息
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyGdxx(String companyid,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findgdxx(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String context = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 context = resultobj.getString("items");
         }
         return context;
	}
	/**
	 * 对外投资
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyDwtz(String companyid,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findDwtz(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 items = resultobj.getString("items");
         }
         return items;
	}
	/**
	 * 变更信息
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyBgxx(String companyid,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findchange(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 items=resultobj.getString("items");
         }
         return items;
	}
	/**
	 * 分支机构
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyFzjg(String companyid,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findfzjg(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 items = resultobj.getString("items");
   	        
         }
         return items;
	}
	/**
	 * 法律诉讼
	 * @param companyName
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyFlss(String companyName,String staffId,String orgId) throws Exception{
		 String result = YHttpclicent.findfvss("",companyName,1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 items=resultobj.getString("items");
         }
        
         return items;
	}
	  
	/**
	 * 法院公告
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyJudirisk(String companyName,String staffId,String orgId)throws Exception{
		 String result = YHttpclicent.findJudicialrisk("",companyName,1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 items = resultobj.getString("items");
         }
         return items;
	}
	
	/**
	 * 失信人
	 * @param companyName
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbySxr(String companyName,String staffId,String orgId)throws Exception{
		 String result = YHttpclicent.findsxr("",companyName,1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 items=resultobj.getString("items");
         }
         //saveprice("失信人",staffId,orgId);
         return items;
	}
	
	/**
	 * 被执行人
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyBzxr(String companyid,String staffId,String orgId)throws Exception{
		 String result = YHttpclicent.findbzxr(companyid,"",1);
		 JSONObject jsonObject = JSONObject.fromObject(result);
         String reason = jsonObject.getString("reason");
         String items = null;
         if(reason!=null && reason.equals("ok")){
        	 String resultall = jsonObject.getString("result");
        	 JSONObject resultobj = JSONObject.fromObject(resultall);
        	 String total=resultobj.getString("total");
        	 items=resultobj.getString("items");
         }
         return items;
	}
	
	/**
	 * 开庭公告
	 * @param companyid
	 * @return
	 */
	@SuppressWarnings("unused")
	private String  findbyKtgg(String companyid,String staffId,String orgId) throws Exception{
		String result = YHttpclicent.findktgg(companyid,"",1);
		JSONObject jsonObject = JSONObject.fromObject(result);
        String reason = jsonObject.getString("reason");
        String array = null;
        if(reason!=null && reason.equals("ok")){
	       	 String resultall = jsonObject.getString("result");
	       	 JSONObject resultobj = JSONObject.fromObject(resultall);
	       	 String total = resultobj.getString("total");
	       	 array = resultobj.getString("items");
        }
        return array;
	}
}
