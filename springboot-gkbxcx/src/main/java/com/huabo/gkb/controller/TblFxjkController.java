package com.huabo.gkb.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblyyCompany;
import com.huabo.gkb.entity.TblyyTeam;
import com.huabo.gkb.service.TblOrganizationService;
import com.huabo.gkb.service.TblStaffService;
import com.huabo.gkb.service.TblYyCompanyService;
import com.huabo.gkb.service.TblYyOrgDepositService;
import com.huabo.gkb.service.TblYyTeamService;
import com.huabo.gkb.service.TblYyUserQueryService;
import com.huabo.gkb.util.YHttpclicent;


@RestController
@RequestMapping("fxjk")
public class TblFxjkController {

	@Resource
	public TblStaffService tblStaffService;
	
	@Resource
	public TblYyTeamService tblYyTeamService;
	
	@Resource
	public TblYyCompanyService tblYyCompanyService;
	
	@Resource
	public TblOrganizationService tblOrganizationService;
	
	@Resource
	public TblYyOrgDepositService tblYyOrgDepositService;
	
	@Resource
	public TblYyUserQueryService tblYyUserQueryService;
	
	@RequestMapping("modifyReportJkInfo")
	public Map<String,String> modifyReportJkInfo(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value="priceIds",required=true)String priceIds,
			@RequestParam(value="reportId",required=true)String reportId){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			this.tblYyCompanyService.modifyReportJkInfo(priceIds,reportId);
			resultMap.put("result", "0");
		} catch (Exception e) {
			resultMap.put("result", "-1");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/findReportInfo")
	public Map<String,String> findReportInfo(HttpServletRequest request,HttpServletResponse response, 
			@RequestParam(value="companyid",required=true)Integer companyid,
			@RequestParam(value="orgId",required=true)String orgId,
			@RequestParam(value="staffId",required=true)Integer staffId){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = null;
		try {
			resultMap = this.tblYyCompanyService.findReportInfo(companyid,orgId,staffId);
		} catch (Exception e) {
			resultMap.put("result", "-1");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/addRpoertModel")
	public Map<String,String> addRpoertModel(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="companyid",required=true)Integer companyid,
			@RequestParam(value="companyName",required=true)String companyName,
			@RequestParam(value="orgId",required=true)String orgId,
			@RequestParam(value="staffId",required=true)Integer staffId){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			this.tblYyCompanyService.addReportModel(companyid,companyName,orgId,staffId);
		} catch (Exception e) {
			resultMap.put("result", "-1");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/moveCompanyTeam")
	public Map<String,String> moveCompanyTeam(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="chooseids",required=true)String[] chooseids,
			@RequestParam(value="teamid",required=true)Integer teamid){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			Integer result = this.tblYyCompanyService.removeTeamInfoCompany(chooseids,teamid);
			resultMap.put("result", result.toString());
		} catch (Exception e) {
			resultMap.put("result", "-1");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/cancelCompanyjk")
	public Map<String,String> cancelCompanyjk(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="chooseids",required=true)String[] chooseids){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			StringBuffer idSb = new StringBuffer("(");
			for (int i = 0; i < chooseids.length; i++) {
				idSb.append(chooseids[i]+",");
			}
			idSb.deleteCharAt(idSb.length()-1);
			idSb.append(")");
			this.tblYyCompanyService.cancelCompany(idSb.toString());
			resultMap.put("result", "0");
		} catch (Exception e) {
			resultMap.put("result", "-1");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/addCompanyjk")
	public Map<String,String> addCompanyjk(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="names",required=true)String names,
			@RequestParam(value="staffId",required=true)Integer staffId,
			@RequestParam(value="orgId",required=true)String orgId){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			Integer result = this.tblYyCompanyService.addCompanyList(names,staffId,orgId);
			resultMap.put("result",result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/searchCompany")
	public Map<String,String> searchCompany(HttpServletRequest request,HttpServletResponse response,String companyName,
			@RequestParam(value="orgId",required=false) String orgId,
			@RequestParam(value="staffId",required=false) String staffId)throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		if( SystemStaticValue.WBSJSTAFFID.indexOf(staffId) == -1 ) {
			Integer companyId = tblOrganizationService.findCompanyIdByDeptId(orgId);
				//判断公司费用是否支持此次查询	 
	        	String surplusMoney = this.tblYyOrgDepositService.findUrplusMoney(companyId);
	        	if(surplusMoney == null){
	        		resultMap.put("record", "-1");//账户余额不足，请充值
	        		return resultMap;
	        	}	 
	        	Double money = this.tblYyOrgDepositService.findTostMoney("公司列表"); 
	        	Double smoney = Double.valueOf(surplusMoney);	 
	        	if(smoney < money){
	        		resultMap.put("record", "-1");//账户余额不足，请充值
	        		return resultMap;
	        	}
		}
		
		String result = YHttpclicent.findSerch(companyName);
		if( SystemStaticValue.WBSJSTAFFID.indexOf(staffId) == -1 ) {
			saveprice("公司列表",staffId,orgId);//搜索接口
		}
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(result);
        String reason = jsonObject.getString("reason");
        if(reason!=null && reason.equals("ok")){
	        String resultall = jsonObject.getString("result");
	        net.sf.json.JSONObject resultobj = net.sf.json.JSONObject.fromObject(resultall);
	        String total=resultobj.getString("total");
	        String items=resultobj.getString("items").replace("<em>","").replace("<\\/em>","");
	        resultMap.put("record", total);
	        resultMap.put("data", items);
        }else{
        	resultMap.put("record", "0");
        }
        
        
		return resultMap;
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
	
	@RequestMapping("/findTeamInfoByStaffId")
	public Map<String,Object> findTeamInfoByStaffId(HttpServletRequest request,HttpServletResponse response,Integer staffId,Integer pageNumber){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblyyTeam>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(25);
				this.tblYyTeamService.findListByStaffIdMySql(staffId,pageInfo);
				resultMap.put("teamList",pageInfo.getTlist());
				resultMap.put("pageNumber", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
        	}else {
        		PageInfo<TblyyTeam> pageInfo = new PageInfo<TblyyTeam>();
    			pageInfo.setCurrentPage(pageNumber);
    			pageInfo.setPageSize(25);
    			this.tblYyTeamService.findListByStaffId(staffId,pageInfo);
    			resultMap.put("teamList",pageInfo.getTlist());
    			resultMap.put("pageNumber", pageInfo.getCurrentPage());
    			resultMap.put("totalPage", pageInfo.getTotalPage());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	@RequestMapping("/findChooseTeamInfoByStaffId")
	public Map<String,Object> findChooseTeamInfoByStaffId(HttpServletRequest request,HttpServletResponse response,Integer staffId,Integer pageNumber){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			if(pageNumber == null) {
				pageNumber = 1;
			}
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblyyTeam>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(25);
				this.tblYyTeamService.findTeamInfoByPageInfoMySql(staffId,pageInfo);
				resultMap.put("teamList", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}else {
				PageInfo<TblyyTeam> pageInfo = new PageInfo<TblyyTeam>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(25);
				this.tblYyTeamService.findTeamInfoByPageInfo(staffId,pageInfo);
				resultMap.put("teamList", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	@RequestMapping("/modifyYyTeam")
	public Map<String,Object> modifyYyTeam(HttpServletRequest request,HttpServletResponse response,String teamName,Integer staffId,Integer teamid)
	throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			if(SystemStaticValue.DataType == 0) {
				Integer result = this.tblYyTeamService.modifyTeamMySql(teamName,staffId,teamid);
				if(result == 0) {
					PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblyyTeam>();
					pageInfo.setCurrentPage(1);
					pageInfo.setPageSize(25);
					this.tblYyTeamService.findListByStaffIdMySql(staffId,pageInfo);
					resultMap.put("teamList",pageInfo.getTlist());
					resultMap.put("pageNumber", pageInfo.getCurrentPage());
					resultMap.put("totalPage", pageInfo.getTotalPage());
				}
				resultMap.put("result", result.toString());
			}else {
				Integer result = this.tblYyTeamService.modifyTeam(teamName,staffId,teamid);
				if(result == 0) {
					PageInfo<TblyyTeam> pageInfo = new PageInfo<TblyyTeam>();
					pageInfo.setCurrentPage(1);
					pageInfo.setPageSize(25);
					this.tblYyTeamService.findListByStaffId(staffId,pageInfo);
					resultMap.put("teamList",pageInfo.getTlist());
					resultMap.put("pageNumber", pageInfo.getCurrentPage());
					resultMap.put("totalPage", pageInfo.getTotalPage());
				}
				resultMap.put("result", result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/addYyTeam")
	public Map<String,Object> addYyTeam(HttpServletRequest request,HttpServletResponse response,String teamName,Integer staffId,String orgId)
	throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			if(SystemStaticValue.DataType == 0) {
				Integer result = this.tblYyTeamService.addTeamMySql(teamName,staffId,orgId);
				if(result == 0) {
					PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblyyTeam>();
					pageInfo.setCurrentPage(1);
					pageInfo.setPageSize(5);
					this.tblYyTeamService.findTeamInfoByPageInfoMySql(staffId,pageInfo);
					resultMap.put("teamList", pageInfo.getTlist());
					resultMap.put("currentPage", pageInfo.getCurrentPage());
					resultMap.put("totalPage", pageInfo.getTotalPage());
				}
				resultMap.put("result", result.toString());
			}else {
				Integer result = this.tblYyTeamService.addTeam(teamName,staffId,orgId);
				if(result == 0) {
					PageInfo<TblyyTeam> pageInfo = new PageInfo<TblyyTeam>();
					pageInfo.setCurrentPage(1);
					pageInfo.setPageSize(5);
					this.tblYyTeamService.findTeamInfoByPageInfo(staffId,pageInfo);
					resultMap.put("teamList", pageInfo.getTlist());
					resultMap.put("currentPage", pageInfo.getCurrentPage());
					resultMap.put("totalPage", pageInfo.getTotalPage());
				}
				resultMap.put("result", result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/findFkxjInfoAll")
	public Map<String,String> findStaffUserInfo(HttpServletRequest request,HttpServletResponse response,Integer staffId,Integer orgId,Integer currentPage,Integer teamId,String companyName)
	throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			if(currentPage == null) {
				currentPage = 1;
			}
			
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblyyCompany> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblyyCompany>();
				pageInfo.setCurrentPage(currentPage);
				pageInfo.setPageSize(10);
				com.huabo.gkb.entitymysql.TblyyCompany company = new com.huabo.gkb.entitymysql.TblyyCompany();
				company.setTeamId(teamId);
				company.setCompanyName(companyName);
				company.setStaffId(staffId);
				pageInfo.setCondition(company);
				this.tblYyCompanyService.findCompanyInfoByPageInfoMySql(pageInfo);
				resultMap.put("companyList", JSONObject.toJSONString(pageInfo.getTlist()));
				resultMap.put("currentPage", pageInfo.getCurrentPage().toString());
				resultMap.put("totalPage", pageInfo.getTotalPage().toString());
				resultMap.put("totalRecord", pageInfo.getTotalRecord().toString());
			}else {
				PageInfo<TblyyCompany> pageInfo = new PageInfo<TblyyCompany>();
				pageInfo.setCurrentPage(currentPage);
				pageInfo.setPageSize(10);
				TblyyCompany company = new TblyyCompany();
				company.setTeamId(teamId);
				company.setCompanyName(companyName);
				company.setStaffId(staffId);
				pageInfo.setCondition(company);
				this.tblYyCompanyService.findCompanyInfoByPageInfo(pageInfo);
				resultMap.put("companyList", JSONObject.toJSONString(pageInfo.getTlist()));
				resultMap.put("currentPage", pageInfo.getCurrentPage().toString());
				resultMap.put("totalPage", pageInfo.getTotalPage().toString());
				resultMap.put("totalRecord", pageInfo.getTotalRecord().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("findTeamListInfo")
	public Map<String,Object> findTeamListInfo(HttpServletRequest request,HttpServletResponse response,Integer staffId,Integer pageNumber)
			throws Exception{
				response.addHeader("Access-Control-Allow-Origin", "*");
				Map<String,Object> resultMap = new HashMap<String,Object>(0);
				try {
					if(pageNumber == null) {
						pageNumber = 1;
					}
					
					if(SystemStaticValue.DataType == 0) {
						PageInfo<com.huabo.gkb.entitymysql.TblyyTeam> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblyyTeam>();
						pageInfo.setCurrentPage(pageNumber);
						pageInfo.setPageSize(5);
						this.tblYyTeamService.findTeamInfoByPageInfoMySql(staffId,pageInfo);
						resultMap.put("teamList", pageInfo.getTlist());
						resultMap.put("currentPage", pageInfo.getCurrentPage());
						resultMap.put("totalPage", pageInfo.getTotalPage());
					}else {
						PageInfo<TblyyTeam> pageInfo = new PageInfo<TblyyTeam>();
						pageInfo.setCurrentPage(pageNumber);
						pageInfo.setPageSize(5);
						this.tblYyTeamService.findTeamInfoByPageInfo(staffId,pageInfo);
						resultMap.put("teamList", pageInfo.getTlist());
						resultMap.put("currentPage", pageInfo.getCurrentPage());
						resultMap.put("totalPage", pageInfo.getTotalPage());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return resultMap;
			}
	
}
