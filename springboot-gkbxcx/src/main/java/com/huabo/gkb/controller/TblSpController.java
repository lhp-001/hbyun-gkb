package com.huabo.gkb.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huabo.gkb.util.HttpClient;

@Controller
public class TblSpController {
	/**
	 * 172.16.0.50
	 * 127.0.0.1
	 * 47.93.182.225
	 */


	/**
	 * 我的代办-列表页
	 * 
	 * @param request
	 * @param indicatorid
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/findSpByStaffid", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findSpByStaffid(HttpServletRequest request,HttpServletResponse response, String staffid,String orgid,String type) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String rwurl = "http://47.93.182.225:8080/home/wddb/wddb_xcx";
		HashMap<String, Object> fields3 = new HashMap<String, Object>();
		fields3.put("staffid", staffid);
		fields3.put("orgid", orgid);
		fields3.put("type", type);
		String result = HttpClient.request(rwurl, fields3, null);
		return result;
	}
	

   /**
    * 提交审批
    * @param request
    * @param cyId
    * @param tId
    * @param tfrom
    * @param transition
    * @param staffid
    * @param optState
    * @param formid
    * @return
    * @throws Exception
    */
	@RequestMapping(value= "/complete", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findSpByStaffid(HttpServletRequest request,HttpServletResponse response,String cyId, String tId,String tfrom, String transition,String staffid,String optState,BigDecimal formid) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String rwurl = "http://47.93.182.225:8080/home/complete";
		HashMap<String, Object> fields3 = new HashMap<String, Object>();
		fields3.put("cyId", cyId);//
		fields3.put("tId", tId);//“任务id”
		fields3.put("tfrom", tfrom);//审批归属：如"计划审批"
		fields3.put("transition", transition);//审批建议，长幅文字
		fields3.put("staffid", staffid);//用户id
		fields3.put("optState", optState);//审批状态
		fields3.put("formid", formid);//关联表单id
		String result = HttpClient.request(rwurl, fields3, null);
		return result;
	}
	
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:计划审批--详情
	  */
	@RequestMapping(value = "nbsj/jhgl/to_tjsp_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_tjsp_info(HttpServletRequest request,HttpServletResponse response,String formid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/jhgl/to_tjsp_info";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("formid", formid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:项目审批--详情
	  */
	@RequestMapping(value = "nbsj/xmgl/to_sp_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sp_dggl(HttpServletRequest request,HttpServletResponse response,BigDecimal orgid,String spid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/xmgl/to_sp_info";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("spid", spid);
			fields3.put("orgid", orgid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}

	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:底稿复核--详情
	  */
	@RequestMapping(value = "nbsj/sjss/to_sp_dggl", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sp_info(HttpServletRequest request,HttpServletResponse response,String spid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/sjss/to_sp_dggl";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("spid", spid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:事实确认书--详情
	  */
	@RequestMapping(value = "nbsj/sjss/to_sp_ssqrs", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sp_ssqrs(HttpServletRequest request,HttpServletResponse response,String spid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/sjss/to_sp_ssqrs";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("spid", spid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:审计通知书--详情
	  */
	@RequestMapping(value = "nbsj/sjtzs/to_sp_sjtzs", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sp_sjtzs(HttpServletRequest request,HttpServletResponse response,String spid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/sjtzs/to_sp_sjtzs";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("spid", spid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:审计报告--详情
	  */
	@RequestMapping(value = "nbkz/sjbg/to_sp_sjbg", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sp_sjbg(HttpServletRequest request,HttpServletResponse response,String spid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/sjbg/to_sp_sjbg";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("spid", spid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:审计报告复核--详情
	  */
	@RequestMapping(value = "nbkz/sjbg/to_sp_sjbg_fh", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sp_sjbg_fh(HttpServletRequest request,HttpServletResponse response,String spid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/sjbg/to_sp_sjbg_fh";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("spid", spid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:审计报告征求意见--详情
	  */
	@RequestMapping(value = "nbkz/sjbg/to_sp_sjbg_zqyj", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sp_sjbg_zqyj(HttpServletRequest reques,HttpServletResponse response,String spid,String tId,String cyId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/sjbg/to_sp_sjbg_zqyj";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("spid", spid);
			fields3.put("tId", tId);
			fields3.put("cyId", cyId);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	 * 我的审批-列表页
	 * 
	 * @param request
	 * @param indicatorid
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/wdsp/wdsp_xcx", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Map<String,Object> wdsp_xcx(HttpServletRequest request,HttpServletResponse response,String contractId,String staffid,BigDecimal orgid,String number) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		String rwurl = "http://47.93.182.225:8080/home/wdsp/wdsp_xcx";
		HashMap<String, Object> fields3 = new HashMap<String, Object>();
		fields3.put("contractId", contractId);
		fields3.put("staffid", staffid);
		fields3.put("orgid", orgid);
		fields3.put("number", number);
		String result = HttpClient.request(rwurl, fields3, null);
		resultMap.put("result", result);
		resultMap.put("judge", "0");
		return resultMap;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:商标申请--详情页
	  */
	@RequestMapping(value = "nbkz/cwgl/to_sptzgl_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sptzgl_info(HttpServletRequest request,HttpServletResponse response,String assetid,String staffid) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/cwgl/to_sptzgl_info";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("assetid", assetid);
			fields3.put("staffid", staffid);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:朝阳环卫项目支出预算申报表--详情页
	  */
	@RequestMapping(value = "cyhw/yszc/to_sptzgl_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sptzgl_infoyszc(HttpServletRequest request,HttpServletResponse response,String budgetId,String staffid) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/yszc/to_sptzgl_info";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("budgetId", budgetId);
			fields3.put("staffid", staffid);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:朝阳环卫中心经济合同联审单基层--详情页
	  */
	@RequestMapping(value = "cyhw/htlsjc/to_sptzgl_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sptzgl_infojc(HttpServletRequest request,HttpServletResponse response,String contractId,String staffid,String cyId,String tId) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/htlsjc/to_sptzgl_info";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("contractId", contractId);
			fields3.put("cyId", cyId);
			fields3.put("tId", tId);
			fields3.put("staffid", staffid);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:朝阳环卫中心经济合同联审单基层--详情页
	  */
	@RequestMapping(value = "cyhw/htlsjg/to_sptzgl_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sptzgl_infojg(HttpServletRequest request,String contractId,String staffid,String cyId,String tId) throws Exception {
		   String rwurl = "http://47.93.182.225:8080/home/htlsjg/to_sptzgl_info";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("contractId", contractId);
			fields3.put("cyId", cyId);
			fields3.put("tId", tId);
			fields3.put("staffid", staffid);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:朝阳环卫中心经济合同联审单基层--详情页
	  */
	@RequestMapping(value = "cyhw/zbjc/to_sptzgl_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sptzgl_infozbjc(HttpServletRequest request,HttpServletResponse response,String inspectionId,String staffid) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/zbjc/to_sptzgl_info";
			HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("inspectionId", inspectionId);
			fields3.put("staffid", staffid);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
	/**
	  * @throws Exception 
	  * @Author: ZXL
	  * @Des:朝阳环卫中心经济合同联审单基层--详情页
	  */
	@RequestMapping(value = "cyhw/zbjg/to_sptzgl_info", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String to_sptzgl_infozbjg(HttpServletRequest request,HttpServletResponse response,String inspectionId,String staffid) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		   String rwurl = "http://47.93.182.225:8080/home/zbjg/to_sptzgl_info";
		  HashMap<String, Object> fields3 = new HashMap<String, Object>();
			fields3.put("inspectionId", inspectionId);
			fields3.put("staffid", staffid);
			String result = HttpClient.request(rwurl, fields3, null);
	    return result;
	}
}
