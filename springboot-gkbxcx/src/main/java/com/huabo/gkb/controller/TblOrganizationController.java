package com.huabo.gkb.controller;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblOrganization;
import com.huabo.gkb.service.TblOrganizationService;
import com.huabo.gkb.service.TblStaffService;


@Controller
@RequestMapping("orgInfo")
public class TblOrganizationController {
	
	@Resource
	public TblOrganizationService tblOrganizationService;
	
	@Resource
	public TblStaffService tblStaffService;
	
	@RequestMapping("/findHyOrgAllInfo")
	@ResponseBody
	public Map<String,String> findHyOrgAllInfo(HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			if(SystemStaticValue.DataType == 0) {
				List<com.huabo.gkb.entitymysql.TblOrganization> orgList = tblOrganizationService.findHyOrgAllInfoMySql();
				resultMap.put("data",JSONObject.toJSONString(orgList));
			}else {
				List<TblOrganization> orgList = tblOrganizationService.findHyOrgAllInfo();
				resultMap.put("data",JSONObject.toJSONString(orgList));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/findDeptInfoByOrgId")
	@ResponseBody
	public Map<String,Object> findDeptInfoByOrgId(HttpServletRequest request,HttpServletResponse response,String orgId,Integer pageNumber,Integer pageSize){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			Integer comId = tblOrganizationService.findCompanyIdByDeptId(orgId);
			String orgName = null;
			
			if(SystemStaticValue.DataType == 0) {
				orgName = tblOrganizationService.findOrgNameByOrgIdMySql(comId.toString());
				PageInfo<com.huabo.gkb.entitymysql.TblOrganization> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblOrganization>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(pageSize);
				this.tblOrganizationService.findDeptInfoByOrgIdMySql(comId.toString(),pageInfo);
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}else {
				orgName = tblOrganizationService.findOrgNameByOrgId(comId.toString());
				PageInfo<TblOrganization> pageInfo = new PageInfo<TblOrganization>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(pageSize);
				this.tblOrganizationService.findDeptInfoByOrgId(comId.toString(),pageInfo);
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}
			resultMap.put("orgname", orgName);
			resultMap.put("orgid", comId.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/findDeptInfoByFahterId")
	@ResponseBody
	public Map<String,Object> findDeptInfoByFahterId(HttpServletRequest request,HttpServletResponse response,String orgId,Integer pageNumber,Integer pageSize){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			String orgName = null;
			
			if(SystemStaticValue.DataType == 0) {
				orgName = tblOrganizationService.findOrgNameByOrgIdMySql(orgId);
				PageInfo<com.huabo.gkb.entitymysql.TblOrganization> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblOrganization>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(pageSize);
				this.tblOrganizationService.findDeptInfoByOrgIdMySql(orgId,pageInfo);
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}else {
				orgName = tblOrganizationService.findOrgNameByOrgId(orgId);
				PageInfo<TblOrganization> pageInfo = new PageInfo<TblOrganization>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(pageSize);
				this.tblOrganizationService.findDeptInfoByOrgId(orgId,pageInfo);
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}
			resultMap.put("orgname", orgName);
			resultMap.put("orgid", orgId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/saveDept")
	@ResponseBody
	public Map<String,String> saveDept(HttpServletRequest request,HttpServletResponse response,String dept){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			JSONObject jsonDept = JSONObject.parseObject(dept);
			Integer orgId = tblOrganizationService.findCompanyIdByDeptId(jsonDept.getString("orgId"));
			Integer countDeptNo = tblOrganizationService.findSameDeptNoByOrgId(orgId,jsonDept.getString("deptNo"));
			if(countDeptNo == 0) {
				TblOrganization org = new TblOrganization();
				org.setOutsideId(3);
				org.setFatherorgid(new BigDecimal(jsonDept.getString("orgId")));
				org.setOrgname(jsonDept.getString("deptName"));
				org.setOrgtype(0);
				org.setOrgCreate(new Date());
				org.setOrgnumber(jsonDept.getString("deptNo"));
				if(jsonDept.getString("deptMemo") != null && !"".equals(jsonDept.getString("deptMemo"))) {
					org.setMemo(jsonDept.getString("deptMemo"));
				}else {
					org.setMemo("");
				}
				if(jsonDept.getString("deptOrgMemo") != null && !"".equals(jsonDept.getString("deptOrgMemo"))) {
					org.setOrgmeno(jsonDept.getString("deptOrgMemo"));
				}else {
					org.setOrgmeno("");
				}
				org.setStatus(0);
				tblOrganizationService.saveDeptInfo(org);
				resultMap.put("result", "1");
			}else {
				resultMap.put("result", "0");
			}
		} catch (Exception e) {
			resultMap.put("result", "-1");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	
	@RequestMapping("/findDeptListById")
	@ResponseBody
	public Map<String,Object> findDeptListById(HttpServletRequest request,HttpServletResponse response,Integer orgId,Integer judge,Integer pageNumber){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			if(judge == 1) {
				orgId = tblOrganizationService.findCompanyIdByDeptId(orgId.toString());
			}
			resultMap.put("orgid", orgId.toString());
			
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblOrganization> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblOrganization>();
				pageInfo.setPageSize(15);
				pageInfo.setCurrentPage(pageNumber);
				this.tblOrganizationService.findDeptListByOrgIdMySql(orgId,pageInfo);
				resultMap.put("orgList", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}else {
				PageInfo<TblOrganization> pageInfo = new PageInfo<TblOrganization>();
				pageInfo.setPageSize(15);
				pageInfo.setCurrentPage(pageNumber);
				this.tblOrganizationService.findDeptListByOrgId(orgId,pageInfo);
				resultMap.put("orgList", pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
}
