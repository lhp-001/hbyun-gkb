package com.huabo.gkb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.LunboTuInfo;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblIndicator;
import com.huabo.gkb.entity.TblMonitorIndicatorResult;
import com.huabo.gkb.entity.TblMonitorPrewarning;
import com.huabo.gkb.entity.TblMonitorRule;
import com.huabo.gkb.entity.TblReport;
import com.huabo.gkb.mapper.TblObjectMapper;
import com.huabo.gkb.mapper.TblReportMapper;
import com.huabo.gkb.mysqlmapper.TblObjectMySqlMapper;
import com.huabo.gkb.mysqlmapper.TblReportMySqlMapper;
import com.huabo.gkb.service.TblCirculationService;
import com.huabo.gkb.service.TblIndicatorResultService;
import com.huabo.gkb.service.TblIndicatorService;
import com.huabo.gkb.service.TblMonitorPrewarningService;
import com.huabo.gkb.service.TblMonitorRuleService;
import com.huabo.gkb.service.TblStaffService;
import com.huabo.gkb.util.EncryptUtil;

@Controller
@RequestMapping("znjk")
public class TblZnjkController {

	@Resource
	public TblStaffService tblStaffService;
	@Resource
	public TblIndicatorService tblIndicatorService;
	@Resource
	public TblIndicatorResultService tblIndicatorResultService;
	@Resource
	public TblMonitorRuleService tblMonitorRuleService;
	@Resource
	public TblMonitorPrewarningService tblMonitorPrewarningService;
	@Resource
	public TblObjectMapper tblObjectMapper;
	@Resource
	public TblReportMapper tblReportMapper;
	@Resource
	public TblCirculationService tblCirculationService;
	
	
	@Resource
	public TblObjectMySqlMapper tblObjectMySqlMapper;
	@Resource
	public TblReportMySqlMapper tblReportMySqlMapper;
	
	
	@RequestMapping("toPaiKeReportSheet")
	public ModelAndView toPaiKeReportSheet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ModelAndView mv = new ModelAndView("reportSheet");
		String token = EncryptUtil.getInstance().DESencode("fengkong1", "hbyun");
		mv.addObject("token", token);
		mv.addObject("url", "https://report.huabodashuju.com/browse/0/A98CB6AD58DC4633906BBBC477EDC118");
		//mv.addObject("url", "http://123.56.236.25:9001/browse/0/B1009EF82BBD4B35828FB8D17EEFBE20");
		return mv;
	}
	
	@RequestMapping("toPaiKeReportSheetInfo")
	@ResponseBody
	public Map<String,Object> toPaiKeReportSheetInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		String token = EncryptUtil.getInstance().DESencode("fengkong1", "hbyun");
		resultMap.put("pageurl","http://192.168.0.11:8007/static/reportSheet.jsp");
		resultMap.put("token", token);
		resultMap.put("url", "https://report.huabodashuju.com/browse/0/A98CB6AD58DC4633906BBBC477EDC118");
		//resultMap.put("url", "http://123.56.236.25:9001/browse/0/B1009EF82BBD4B35828FB8D17EEFBE20");
		return resultMap;
	}

	/**
	 * 指标列表
	 * 
	 * @param request
	 * @param orgid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/findIndicatorList")
	@ResponseBody
	public String findIndicatorList(HttpServletRequest request,HttpServletResponse response, String orgId, Integer currentPage) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PageInfo<TblIndicator> pageInfo = new PageInfo<TblIndicator>();
		pageInfo.setPageSize(10);
		pageInfo.setCurrentPage(currentPage);
		tblIndicatorService.selectList(orgId, pageInfo);
		String str = JSONObject.toJSONString(pageInfo);
		return str;
	}

	/**
	 * 指标结果
	 * 
	 * @param request
	 * @param indicatorid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/findIndicatorResultList")
	@ResponseBody
	public String findIndicatorResultList(HttpServletRequest request,HttpServletResponse response, String indicatorid, Integer currentPage) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PageInfo<TblMonitorIndicatorResult> pageInfo = new PageInfo<TblMonitorIndicatorResult>();
		pageInfo.setCurrentPage(currentPage);
		tblIndicatorResultService.selectList(indicatorid, pageInfo);
		String str = JSONObject.toJSONString(pageInfo);
		return str;
	}

	/**
	 * 规则列表
	 * 
	 * @param request
	 * @param orgid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/findTblMonitorRuleList")
	@ResponseBody
	public String findTblMonitorRuleList(HttpServletRequest request,HttpServletResponse response, String orgId, Integer currentPage) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PageInfo<TblMonitorRule> pageInfo = new PageInfo<TblMonitorRule>();
		pageInfo.setCurrentPage(currentPage);
		pageInfo.setPageSize(10);
		tblMonitorRuleService.selectList(orgId, pageInfo);
		String str = JSONObject.toJSONString(pageInfo);
		return str;
	}

	/**
	 * 规则结果列表
	 * 
	 * @param request
	 * @param indicatorid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/findTblMonitorPrewarningList")
	@ResponseBody
	public String findTblMonitorPrewarningList(HttpServletRequest request,HttpServletResponse response, String ruleid, Integer currentPage) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PageInfo<TblMonitorPrewarning> pageInfo = new PageInfo<TblMonitorPrewarning>();
		pageInfo.setCurrentPage(currentPage);
		tblMonitorPrewarningService.selectList(ruleid, pageInfo);
		String str = JSONObject.toJSONString(pageInfo.getTlist());
		return str;
	}

	/**
	 * 规则结果列表
	 * 
	 * @param request
	 * @param indicatorid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/findTblRuleResultList")
	@ResponseBody
	public String findTblRuleResultList(HttpServletRequest request,HttpServletResponse response, String ruleid, Integer currentPage) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		List<Object[]> list = null;
		Integer count = 0;
		// 账套模式名
		String acctid = tblMonitorRuleService.findBookByRuleid(ruleid);
		// 条件
		String signid = tblMonitorPrewarningService.findSignIdByRuleid(ruleid);
		// 获取规则结果
		String sql = "select * from " + acctid + "." + "ZNJK_GZ_" + ruleid + " where EXECTIME ='" + signid + "'";
		// 总数
		String sqlCount = "select count(*) from " + acctid + "." + "ZNJK_GZ_" + ruleid + " where EXECTIME ='" + signid
				+ "'";
		
		PageInfo<Object[]> pageInfo = new PageInfo<Object[]>();
		pageInfo.setCurrentPage(currentPage);
		
		if(SystemStaticValue.DataType == 0) {
			list = tblObjectMySqlMapper.findRuleResult(sql);
			count = tblObjectMySqlMapper.findRuleResultCount(sqlCount);
		}else {
			list = tblObjectMapper.findRuleResult(sql);
			count = tblObjectMapper.findRuleResultCount(sqlCount);
		}
		
		pageInfo.setTlist(list);
		pageInfo.setTotalRecord(count);
		String str = JSONObject.toJSONString(pageInfo);
		return str;
	}

	/**
	 * 报表展现
	 * 
	 * @param request
	 * @param indicatorid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/findReport")
	@ResponseBody
	public Map<String,String> findReport(HttpServletRequest request,HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> map = new HashMap<String, String>(0);
		
		String str = null;
		
		if(SystemStaticValue.DataType == 0) {
			List<com.huabo.gkb.entitymysql.TblReport> list = tblReportMySqlMapper.findReport();
			str = JSONObject.toJSONString(list);
		}else {
			List<TblReport> list = tblReportMapper.findReport();
			str = JSONObject.toJSONString(list);
		}
		
		
		List<LunboTuInfo> infoList = new ArrayList<LunboTuInfo>(0);
		LunboTuInfo  info = new LunboTuInfo("/static/image/backimg/wyj.png");
		infoList.add(info);
		info = new LunboTuInfo("/static/image/backimg/gkb.png");
		infoList.add(info);
		
		map.put("lbtList", JSONObject.toJSONString(infoList));
		map.put("reportList", str);
		map.put("roomId", "8089401");
		return map;
	}
	/*	*//**
			 * 规则结果列表
			 * 
			 * @param request
			 * @param indicatorid
			 * @param currentPage
			 * @return
			 *//*
				 * @RequestMapping("/findSpByStaffid")
				 * 
				 * @ResponseBody public String findSpByStaffid(HttpServletRequest request,String
				 * staffid,Integer currentPage){ PageInfo<TblCirculation> pageInfo = new
				 * PageInfo<TblCirculation>(); pageInfo.setCurrentPage(currentPage);
				 * tblCirculationService.findSpByStaffid(staffid, pageInfo); String str =
				 * JSONObject.toJSONString(pageInfo); return str; }
				 */
}
