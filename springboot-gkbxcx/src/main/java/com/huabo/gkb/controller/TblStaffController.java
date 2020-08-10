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

import com.alibaba.fastjson.JSONObject;
import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblStaff;
import com.huabo.gkb.service.TblOrganizationService;
import com.huabo.gkb.service.TblStaffService;

@Controller
@RequestMapping("staff")
public class TblStaffController {
	private static final Logger logger = LoggerFactory.getLogger(TblStaffController.class);
	
	@Resource
	public TblStaffService tblStaffService;
	
	@Resource
	public TblOrganizationService tblOrganizationService;
	
	@RequestMapping("/findStaffUserInfo")
	@ResponseBody
	public Map<String,String> findStaffUserInfo(HttpServletRequest request,HttpServletResponse response,String staffId){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			logger.info("访问路径：/staff/findStaffUserInfo,传入参数：staffId:"+staffId+",获取员工的部门，组织与岗位信息。");
			
			if(SystemStaticValue.DataType == 0) {
				com.huabo.gkb.entitymysql.TblStaff staff = tblStaffService.findStaffUserInfoMySql(staffId);
				resultMap.put("staff", JSONObject.toJSONString(staff));
			}else {
				TblStaff staff = tblStaffService.findStaffUserInfo(staffId);
				resultMap.put("staff", JSONObject.toJSONString(staff));
			}
			logger.info("查询成功，");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/findUserInfoByOrgId")
	@ResponseBody
	public Map<String,Object> findUserInfoByOrgId(HttpServletRequest request,HttpServletResponse response,String orgId,Integer pageNumber){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblStaff> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblStaff>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(10);
				this.tblStaffService.findUserInfoByOrgIdMySql(orgId,pageInfo);
				resultMap.put("list",pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}else {
				PageInfo<TblStaff> pageInfo = new PageInfo<TblStaff>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(10);
				this.tblStaffService.findUserInfoByOrgId(orgId,pageInfo);
				resultMap.put("list",pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/findUserInfoByDeptId")
	@ResponseBody
	public Map<String,Object> findUserInfoByDeptId(HttpServletRequest request,HttpServletResponse response,String orgId,Integer pageNumber){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String,Object>(0);
		try {
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblStaff> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblStaff>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(15);
				tblStaffService.findUserInfoByDeptIdIncludOrgMySql(orgId,pageInfo);
				resultMap.put("list",pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}else {
				PageInfo<TblStaff> pageInfo = new PageInfo<TblStaff>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(15);
				tblStaffService.findUserInfoByDeptIdIncludOrg(orgId,pageInfo);
				resultMap.put("list",pageInfo.getTlist());
				resultMap.put("currentPage", pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
	@RequestMapping("/changeDeptInfoStaff")
	@ResponseBody
	public Map<String,String> changeDeptInfoStaff(HttpServletRequest request,HttpServletResponse response,Integer orgid,String staffids){
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			staffids = staffids.substring(0,staffids.length()-1);
			this.tblStaffService.changeDeptInfoStaff(staffids,orgid);
			resultMap.put("result", "true");
		} catch (Exception e) {
			resultMap.put("result", "false");
			e.printStackTrace();
		}
		return resultMap;
	}
}
