package com.huabo.gkb.controller;

import java.math.BigDecimal;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.TblOrganization;
import com.huabo.gkb.entity.TblStaff;
import com.huabo.gkb.entity.TblWxUserInfo;
import com.huabo.gkb.service.TblOrganizationService;
import com.huabo.gkb.service.TblStaffService;
import com.huabo.gkb.service.TblWxUserInfoService;
import com.huabo.gkb.tool.MessageTool;
import com.huabo.gkb.tool.MySessionContext;
import com.huabo.gkb.util.AesCbcUtil;
import com.huabo.gkb.util.HttpRequest;
import com.huabo.gkb.util.JsonBean;
import com.huabo.gkb.util.MD5Encrypt;
import com.huabo.gkb.util.ServiceProgramClass;

@Controller
@RequestMapping("wxLogin")
public class WxLoginController {
	private static final Logger logger = LoggerFactory.getLogger(WxLoginController.class);
	@Resource
	public TblWxUserInfoService tblWxUserInfoService;
	
	@Resource
	public TblOrganizationService tblOrganizationService;
	
	@Resource
	public TblStaffService tblStaffService;
	
	/**
	 * 获取微信用户unionId等敏感信息
	 * userInfoMap存储用户信息
	 */
	@RequestMapping("/getSensitiveInfo")
	@ResponseBody
	public Map<String,String> getSensitiveInfo(HttpServletRequest request,HttpServletResponse response,String encryptedData, String iv, String code){
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("访问路径 /wxLogin/getSensitiveInfo, 获取微信用户unioiId等信息，传入参数：encryptedData="+encryptedData+"，iv="+iv+"，code="+code);
		Map<String,String> userInfoMap = null;
		try {  
			userInfoMap = new HashMap<String, String>();
			if (code == null || code.length() == 0) {  
				userInfoMap.put("status", "0");  
			}else{
				//var appid = "";
	            // var secret = "";
				String wxspAppid = SystemStaticValue.WXSPAPPID;  
				//小程序的 app secret (在微信小程序管理后台获取)  
				String wxspSecret = SystemStaticValue.WXSPSECRET;  
				//授权（必填）  
				String grant_type = SystemStaticValue.GRANT_TYPE;  
				//////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////  
				//请求参数  
				String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;  
				//发送请求  
				logger.info("开始获取微信用户信息，访问地址https://api.weixin.qq.com/sns/jscode2session，传入参数："+params);
				String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);  
				logger.info("获取微信用户加密信息成功，返回结果"+sr);
				JSONObject json = JSONObject.parseObject(sr);  
				//获取会话密钥（session_key）  
				String session_key = json.get("session_key").toString();  
				userInfoMap.put("sessionkey", session_key);
		        String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");  
		        logger.info("微信用户信息解密成功，返回结果："+result);
		        if (null != result && result.length() > 0) {  
		            JSONObject userInfoJSON = JSONObject.parseObject(result);  
		            userInfoMap.put("openId", userInfoJSON.get("openId").toString());  
		            userInfoMap.put("unionId", userInfoJSON.get("unionId").toString());
		            
		            if(SystemStaticValue.DataType == 0) {
		            	com.huabo.gkb.entitymysql.TblWxUserInfo userInfo = tblWxUserInfoService.findWxUserInfoByOpenIdMySql(userInfoJSON.get("openId").toString());
			            if(userInfo == null){
			        	   userInfoMap.put("status", "1"); //没有用户信息前往注册页面 
			            }else{
			        	  
			        	   /*Integer orgFatherId = tblOrganizationService.findRegisterOrganizationCount(userInfo.getOrgId(),TblOrganization.REGISTERORGFATHERID);
			        	   logger.info("判断该用户的根级公司注册是否到期，1：未到期，0：已到期 返回结果："+orgFatherId);
			        	   if(orgFatherId == 1||SystemStaticValue.WBSJSTAFFID.indexOf(userInfo.getStaffId()) == -1 ) {*/
			        		   if(userInfoJSON.get("avatarUrl") != null && !userInfoJSON.get("avatarUrl").toString().equals(userInfo.getAvatarUrl())){
			        			   userInfo.setAvatarUrl(userInfoJSON.get("avatarUrl").toString());
			        			   tblWxUserInfoService.updateAvatarUrlMySql(userInfo);
			        		   }
			        		   
			        		   Integer companyId = this.tblOrganizationService.findCompanyIdByDeptId(userInfo.getOrgId().toString());
			        		   com.huabo.gkb.entitymysql.TblStaff staff = this.tblStaffService.selectWxappAdminMySql(new BigDecimal(companyId));
			        		   if(Integer.parseInt(userInfo.getStaffId()) ==  Integer.parseInt(staff.getStaffid().toString())) {
			        			   userInfo.setIsAdmin(0);//是管理员
			        		   }else {
			        			   userInfo.setIsAdmin(1);//不是管理员
			        		   }
			        		   
			        		   userInfoMap.put("status","2");//状态等于二 可以正常使用
			        		   userInfoMap.put("userInfo",JSON.toJSONString(userInfo));
			        	   /*}else {
			        		   userInfoMap.put("status", "0"); //注册公司已到期 无法使用小程序
			        	   }*/
			            }
		            }else {
		            	TblWxUserInfo userInfo = tblWxUserInfoService.findWxUserInfoByOpenId(userInfoJSON.get("openId").toString());
			            if(userInfo == null){
			        	   userInfoMap.put("status", "1"); //没有用户信息前往注册页面 
			            }else{
			        	  
			        	   /*Integer orgFatherId = tblOrganizationService.findRegisterOrganizationCount(userInfo.getOrgId(),TblOrganization.REGISTERORGFATHERID);
			        	   logger.info("判断该用户的根级公司注册是否到期，1：未到期，0：已到期 返回结果："+orgFatherId);
			        	   if(orgFatherId == 1||SystemStaticValue.WBSJSTAFFID.indexOf(userInfo.getStaffId()) == -1 ) {*/
			        		   if(userInfoJSON.get("avatarUrl") != null && !userInfoJSON.get("avatarUrl").toString().equals(userInfo.getAvatarUrl())){
			        			   userInfo.setAvatarUrl(userInfoJSON.get("avatarUrl").toString());
			        			   tblWxUserInfoService.updateAvatarUrl(userInfo);
			        		   }
			        		   
			        		   Integer companyId = this.tblOrganizationService.findCompanyIdByDeptId(userInfo.getOrgId().toString());
			        		   TblStaff staff = this.tblStaffService.selectWxappAdmin(new BigDecimal(companyId));
			        		   if(Integer.parseInt(userInfo.getStaffId()) ==  Integer.parseInt(staff.getStaffid().toString())) {
			        			   userInfo.setIsAdmin(0);//是管理员
			        		   }else {
			        			   userInfo.setIsAdmin(1);//不是管理员
			        		   }
			        		   
			        		   userInfoMap.put("status","2");//状态等于二 可以正常使用
			        		   userInfoMap.put("userInfo",JSON.toJSONString(userInfo));
			        	   /*}else {
			        		   userInfoMap.put("status", "0"); //注册公司已到期 无法使用小程序
			        	   }*/
			            }
		            }
		       }
			}
			logger.info("微信用户信息获取成功：返回参数对象："+userInfoMap);
	    } catch (Exception e) { 
	    	userInfoMap.put("status", "-1");//系统异常
	    	logger.error("错误信息", e);
	    }
	    return userInfoMap;
	}
	
	@RequestMapping(value = "/getWxuserPhone", produces = "application/json; charset=utf-8")
	public @ResponseBody Map<String,String> getUserPhone(HttpServletRequest request,HttpServletResponse response,String aesInfo) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,String> resultMap = new HashMap<String,String>(0);
		try {
			JSONObject info = JSONObject.parseObject(aesInfo);
			byte[] dataByte = Base64.decodeBase64(info.getString("encryptedData"));  
	        //加密秘钥  
	        byte[] keyByte = Base64.decodeBase64(info.getString("sessionkey"));  
	        //偏移量  
	        byte[] ivByte = Base64.decodeBase64(info.getString("ivcode")); 
	        
	        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivByte);  
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	        SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");  
	        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);  
	        //解析解密后的字符串  
	        String result =  new String(cipher.doFinal(dataByte),"UTF-8");
	        System.out.println(result);
	        JSONObject phone = JSONObject.parseObject(result); 
	        
	        resultMap.put("phone", phone.getString("phoneNumber"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return resultMap;
	}
	
	/**
	 * 注册
	 * 
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value = "/register", produces = "application/json; charset=utf-8")
	public @ResponseBody Map<String,String> register(String user,HttpServletRequest request,HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("访问路径 /wxLogin/register , 注册用户信息方法，传入参数：user:"+user);
		Map<String,String> resultMap = new HashMap<String, String>();
		try {
			JSONObject jsonUser = JSONObject.parseObject(user);
			
			if(SystemStaticValue.DataType == 0) {
				Integer count = tblWxUserInfoService.findCountByUserOpenIdMySql(jsonUser.getString("openId"));	
				logger.info("根据openId判断此微信用户是否已经注册，传入参数：openId:"+jsonUser.getString("openId")+",返回结果：count:"+count);
				if(count > 0) {
					resultMap.put("result", "此微信已经注册过"+SystemStaticValue.XCXNAME+"小程序！");
				}else {
					com.huabo.gkb.entitymysql.TblWxUserInfo userInfo = new com.huabo.gkb.entitymysql.TblWxUserInfo();
					userInfo.setAvatarUrl(jsonUser.getString("avatarUrl"));
					userInfo.setCity(jsonUser.getString("city"));
					userInfo.setGender(jsonUser.getString("gender"));
					userInfo.setLanguage(jsonUser.getString("language"));
					userInfo.setNickName(jsonUser.getString("nickName"));
					userInfo.setXcxOpenId(jsonUser.getString("openId"));
					userInfo.setProvince(jsonUser.getString("province"));
					userInfo.setUnionId(jsonUser.getString("unionId"));
					
					com.huabo.gkb.entitymysql.TblStaff staff = new com.huabo.gkb.entitymysql.TblStaff();
					staff.setRealname(jsonUser.getString("nickName"));
					staff.setMiblephone(jsonUser.getString("miblephone"));
					staff.setUsername(jsonUser.getString("miblephone"));
					staff.setPassword(MD5Encrypt.encrypByMd5(TblStaff.REGISTERUSERPASSWORD  ));
					staff.setStatus(1);
					staff.setAddress(jsonUser.getString("province"));
					staff.setOutSideId(3); 
					
					com.huabo.gkb.entitymysql.TblOrganization org = new com.huabo.gkb.entitymysql.TblOrganization();
					org.setOrgname(jsonUser.getString("companyName"));
					org.setOrgCreate(new Date());
					org.setStatus(0);
					org.setIsAutoNumber(0);
					org.setFatherorgid(TblOrganization.REGISTERORGFATHERID);
					org.setOrgtype(TblOrganization.REGISTERORGTYPE);
					org.setOutsideId(3);
					
					String staffId = jsonUser.getString("staffId");
					com.huabo.gkb.entitymysql.TblWxUserInfo registeUserInfo = tblWxUserInfoService.insertUserOrgStaffRightInfoMySql(userInfo,staff,org,staffId);
					resultMap.put("result", "0");
					resultMap.put("userInfo",JSON.toJSONString(registeUserInfo));
					resultMap.put("end", "注册成功！");
				}
			}else {
				/*HttpSession session = MySessionContext.getSession(jsonUser.getString("sessionid"));
				String messagecode=(String)session.getAttribute("messagecode");
				logger.info("获取存储当前用户的session,并从中获取到验证码。session:"+session+"，验证码:"+messagecode);
				if(messagecode==null){
					resultMap.put("result","验证码失效");
				}else if(!jsonUser.getString("code").trim().equals(messagecode)){
					resultMap.put("result","验证码错误");
				}else{*/
					/*List<Integer> countList = tblWxUserInfoService.findCountByUserOpenIdAndCompanyName(jsonUser.getString("openId"),jsonUser.getString("companyName"));
					logger.info("根据openId判断此微信用户是否已经注册，传入参数：openId:"+jsonUser.getString("openId")+"，companyName:"+jsonUser.getString("companyName")+",返回结果：count:"+countList);
					if(countList.get(0) > 0){
						resultMap.put("result", "此微信已经注册过"+SystemStaticValue.XCXNAME+"小程序！");
					}else if(countList.get(1) > 0){
						resultMap.put("result", "公司名重复无法注册");
					}else{*/
					Integer count = tblWxUserInfoService.findCountByUserOpenId(jsonUser.getString("openId"));	
					logger.info("根据openId判断此微信用户是否已经注册，传入参数：openId:"+jsonUser.getString("openId")+",返回结果：count:"+count);
					if(count > 0) {
						resultMap.put("result", "此微信已经注册过"+SystemStaticValue.XCXNAME+"小程序！");
					}else {
						TblWxUserInfo userInfo = new TblWxUserInfo();
						userInfo.setAvatarUrl(jsonUser.getString("avatarUrl"));
						userInfo.setCity(jsonUser.getString("city"));
						userInfo.setGender(jsonUser.getString("gender"));
						userInfo.setLanguage(jsonUser.getString("language"));
						userInfo.setNickName(jsonUser.getString("nickName"));
						userInfo.setXcxOpenId(jsonUser.getString("openId"));
						userInfo.setProvince(jsonUser.getString("province"));
						userInfo.setUnionId(jsonUser.getString("unionId"));
						
						TblStaff staff = new TblStaff();
						staff.setRealname(jsonUser.getString("nickName"));
						staff.setMiblephone(jsonUser.getString("miblephone"));
						staff.setUsername(jsonUser.getString("miblephone"));
						staff.setPassword(MD5Encrypt.encrypByMd5(TblStaff.REGISTERUSERPASSWORD  ));
						staff.setStatus(1);
						staff.setAddress(jsonUser.getString("province"));
						staff.setOutSideId(3); 
						
						TblOrganization org = new TblOrganization();
						org.setOrgname(jsonUser.getString("companyName"));
						org.setOrgCreate(new Date());
						org.setStatus(0);
						org.setIsAutoNumber(0);
						org.setFatherorgid(TblOrganization.REGISTERORGFATHERID);
						org.setOrgtype(TblOrganization.REGISTERORGTYPE);
						org.setOutsideId(3);
						
						String staffId = jsonUser.getString("staffId");
						TblWxUserInfo registeUserInfo = tblWxUserInfoService.insertUserOrgStaffRightInfo(userInfo,staff,org,staffId);
						resultMap.put("result", "0");
						resultMap.put("userInfo",JSON.toJSONString(registeUserInfo));
						resultMap.put("end", "注册成功！");
					}
				//}
			}
			logger.info("微信用户注册成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", ServiceProgramClass.ERRORMSG);
		}
		return resultMap;
	}
	
	/**
	 * 注册
	 * 
	 * @param orgid
	 * @return
	 */
	@RequestMapping(value = "/inviteregister", produces = "application/json; charset=utf-8")
	public @ResponseBody Map<String,String> inviteregister(String user,HttpServletRequest request,HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("访问路径 /wxLogin/register , 注册用户信息方法，传入参数：user:"+user);
		Map<String,String> resultMap = new HashMap<String, String>();
		try {
			JSONObject jsonUser = JSONObject.parseObject(user);
			
			if(SystemStaticValue.DataType == 0) {
				Integer count = tblWxUserInfoService.findCountByUserOpenIdMySql(jsonUser.getString("openId"));	
				logger.info("根据openId判断此微信用户是否已经注册，传入参数：openId:"+jsonUser.getString("openId")+",返回结果：count:"+count);
				if(count > 0) {
					resultMap.put("result", "此微信已经注册过"+SystemStaticValue.XCXNAME+"小程序！");
				}else {
					com.huabo.gkb.entitymysql.TblWxUserInfo userInfo = new com.huabo.gkb.entitymysql.TblWxUserInfo();
					userInfo.setAvatarUrl(jsonUser.getString("avatarUrl"));
					userInfo.setCity(jsonUser.getString("city"));
					userInfo.setGender(jsonUser.getString("gender"));
					userInfo.setLanguage(jsonUser.getString("language"));
					userInfo.setNickName(jsonUser.getString("nickName"));
					userInfo.setXcxOpenId(jsonUser.getString("openId"));
					userInfo.setProvince(jsonUser.getString("province"));
					userInfo.setUnionId(jsonUser.getString("unionId"));
					
					com.huabo.gkb.entitymysql.TblStaff staff = new com.huabo.gkb.entitymysql.TblStaff();
					staff.setRealname(jsonUser.getString("nickName"));
					staff.setMiblephone(jsonUser.getString("miblephone"));
					staff.setUsername(jsonUser.getString("miblephone"));
					staff.setPassword(MD5Encrypt.encrypByMd5(TblStaff.REGISTERUSERPASSWORD  ));
					staff.setStatus(1);
					staff.setAddress(jsonUser.getString("province"));
					staff.setOutSideId(3); 
					
					
					String orgId = jsonUser.getString("deptId");
					
					com.huabo.gkb.entitymysql.TblWxUserInfo registeUserInfo = tblWxUserInfoService.insertUserOrgStaffRightInfoMySql(userInfo,staff,orgId);
					resultMap.put("result", "0");
					resultMap.put("userInfo",JSON.toJSONString(registeUserInfo));
					resultMap.put("end", "注册成功！");
				}
			}else {
				/*HttpSession session = MySessionContext.getSession(jsonUser.getString("sessionid"));
				String messagecode=(String)session.getAttribute("messagecode");
				logger.info("获取存储当前用户的session,并从中获取到验证码。session:"+session+"，验证码:"+messagecode);
				if(messagecode==null){
					resultMap.put("result","验证码失效");
				}else if(!jsonUser.getString("code").trim().equals(messagecode)){
					resultMap.put("result","验证码错误");
				}else{*/
					Integer count = tblWxUserInfoService.findCountByUserOpenId(jsonUser.getString("openId"));	
					logger.info("根据openId判断此微信用户是否已经注册，传入参数：openId:"+jsonUser.getString("openId")+",返回结果：count:"+count);
					if(count > 0) {
						resultMap.put("result", "此微信已经注册过"+SystemStaticValue.XCXNAME+"小程序！");
					}else {
						TblWxUserInfo userInfo = new TblWxUserInfo();
						userInfo.setAvatarUrl(jsonUser.getString("avatarUrl"));
						userInfo.setCity(jsonUser.getString("city"));
						userInfo.setGender(jsonUser.getString("gender"));
						userInfo.setLanguage(jsonUser.getString("language"));
						userInfo.setNickName(jsonUser.getString("nickName"));
						userInfo.setXcxOpenId(jsonUser.getString("openId"));
						userInfo.setProvince(jsonUser.getString("province"));
						userInfo.setUnionId(jsonUser.getString("unionId"));
						
						TblStaff staff = new TblStaff();
						staff.setRealname(jsonUser.getString("nickName"));
						staff.setMiblephone(jsonUser.getString("miblephone"));
						staff.setUsername(jsonUser.getString("miblephone"));
						staff.setPassword(MD5Encrypt.encrypByMd5(TblStaff.REGISTERUSERPASSWORD  ));
						staff.setStatus(1);
						staff.setAddress(jsonUser.getString("province"));
						staff.setOutSideId(3); 
						
						
						String orgId = jsonUser.getString("deptId");
						
						TblWxUserInfo registeUserInfo = tblWxUserInfoService.insertUserOrgStaffRightInfo(userInfo,staff,orgId);
						resultMap.put("result", "0");
						resultMap.put("userInfo",JSON.toJSONString(registeUserInfo));
						resultMap.put("end", "注册成功！");
					}
				//}
			}
			logger.info("微信用户注册成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", ServiceProgramClass.ERRORMSG);
		}
		return resultMap;
	}
	
	@RequestMapping(value = "/findDeptByCompanyName", produces = "application/json; charset=utf-8")
	public @ResponseBody Map<String, Object> findDeptByCompanyName(String companyName,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("访问路径 /wxLogin/findDeptByCompanyName,注册用户根据公司名称获取部门信息，传入参数：companyName："+companyName);
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		
		try {
			Integer count = 0;
			String adminName = null;
			if(SystemStaticValue.DataType == 0) {
				count = this.tblOrganizationService.selectCountByNameMySql(companyName);
				//查找注册公司第一个注册的人 管理员
				if(count > 0) {
					BigDecimal orgId = this.tblOrganizationService.findDeptInfoByorgNameMySql(companyName);
					com.huabo.gkb.entitymysql.TblStaff staff = this.tblStaffService.selectWxappAdminMySql(orgId);
					adminName = staff.getRealname();
				}
			}else {
				count = this.tblOrganizationService.selectCountByName(companyName);
				//查找注册公司第一个注册的人 管理员
				if(count > 0) {
					BigDecimal orgId = this.tblOrganizationService.findDeptInfoByorgName(companyName);
					TblStaff staff = this.tblStaffService.selectWxappAdmin(orgId);
					adminName = staff.getRealname();
				}
			}
			
			resultMap.put("adminName",adminName);
			resultMap.put("result", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/findDeptByCompanyId", produces = "application/json; charset=utf-8")
	public @ResponseBody Map<String, Object> findDeptByCompanyId(String orgId,HttpServletRequest request,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("访问路径 /wxLogin/findDeptByCompanyName,注册用户根据公司名称获取部门信息，传入参数：orgId："+orgId);
		Map<String, Object> resultMap = new HashMap<String, Object>(0);
		
		try {
			if(orgId == null) {
				return null;
			}
			
			Integer oid = tblOrganizationService.findCompanyIdByDeptId(orgId.toString());
			Integer companyCount = 0;
			
			if(SystemStaticValue.DataType == 0) {
				
				List<com.huabo.gkb.entitymysql.TblOrganization> tblList = tblOrganizationService.findDeptNameIdByCompanyIdMySql(oid.toString());
				companyCount = Integer.parseInt(tblList.get(0).getOrgid().toString());
				if(companyCount == 0){
					resultMap.put("result", 0);
				}else if(tblList.size() <= 1) {
					resultMap.put("result", 1);
				}else {
					resultMap.put("result","success");
					resultMap.put("date", tblList);
				}
			}else {
				List<TblOrganization> tblList = tblOrganizationService.findDeptNameIdByCompanyId(oid.toString());
				companyCount = Integer.parseInt(tblList.get(0).getOrgid().toString());
				if(companyCount == 0){
					resultMap.put("result", 0);
				}else if(tblList.size() <= 1) {
					resultMap.put("result", 1);
				}else {
					resultMap.put("result","success");
					resultMap.put("date", tblList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	/**
	 * 注册发送验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/sendVerificationCode", produces = "application/json; charset=utf-8")
	public @ResponseBody String sendVerificationCode(String phone,String type,HttpServletRequest request,HttpServletResponse responses){
		responses.addHeader("Access-Control-Allow-Origin", "*");
		logger.info("访问路径 /wxLogin/sendVerificationCode, 微信小程序用户注册获取短息验证码，传入参数：phone="+phone+"，type="+type);
		/**
		 * 获取随机数验证码
		 */
		Integer flag = new Random().nextInt(99999);  
		Map<String, String> map=new HashMap<String,String>();
		/**
		 * 根据短信模板参数设置填写参数值
		 */
		String code="{\"code\":\""+flag+"\"}";
		/**
		 * 发送验证码
		 * "1"这是type值为1是小程序注册的短信验证模板，为0是pc端忘记密码短信验证的模板
		 */
		MessageTool messageTool=new  MessageTool(phone, code, type);
		   try {
			   /**
			    * 查询发送短信是否成功
			    */
			SendSmsResponse response = messageTool.sendSms();
			  Thread.sleep(3000L);
			  logger.info("短信接口返回的数据 ---------- Code="+response.getCode()+",Message="+response.getMessage()+",RequestId=" + response.getRequestId()+",BizId=" + response.getBizId());
			  if(response.getCode() != null && response.getCode().equals("OK")) { 
				  request.getSession().setAttribute("messagecode", flag.toString());
				  request.getSession().setMaxInactiveInterval(300);
				  map.put("result", "success");
				  map.put("sessionid", request.getSession().getId());
				  MySessionContext.AddSession(request.getSession());
				  return JsonBean.success(map);  
			  }
		} catch (Exception e) {
			logger.error("访问错误,返回值:err,错误信息", e);
			 map.put("result", "err");
			 return JsonBean.success(map);
		}
		return JsonBean.success("fail");
		
	}
	
	/**
	 * 获取到小程序用户访问的sessionid实现小程序与后台的session会话
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSessionId" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getInviteQrCode(HttpServletRequest request){
		System.out.println(request.getSession().getId());
		return request.getSession().getId();
	}
	
	//
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getQrCode" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public byte[] getQrCode(HttpServletRequest request,String orgId,HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
        byte[] result = null;
		try {
			String params = "appid=" + SystemStaticValue.WXSPAPPID + "&secret=" + SystemStaticValue.WXSPSECRET + "&grant_type=client_credential";  
			logger.info("开始获取access_token，访问地址https://api.weixin.qq.com/cgi-bin/token，传入参数："+params);
			String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);  
			logger.info("获取access_token，返回结果"+sr);
			JSONObject json = JSONObject.parseObject(sr);
			String accessToken = json.get("access_token").toString();
			
			Map<String,Object> map = new HashMap<String,Object>(0);
			map.put("width", 160);
			map.put("path", "pages/mine/inviteregister/inviteregister");
			map.put("scene",orgId+",3");//邀请码
			params = JSON.toJSONString(map);
			logger.info("开始获取二维码信息，访问地址https://api.weixin.qq.com/wxa/getwxacodeunlimit，传入参数："+params);
			String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			HttpEntity requestEntity = new HttpEntity(map, headers);
			ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
			logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
			result = entity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
        }
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getInviteQrCode" ,produces = "application/json; charset=utf-8")
	@ResponseBody
	public byte[] getSessionId(HttpServletRequest request,String staffId){
        byte[] result = null;
		try {
			String params = "appid=" + SystemStaticValue.WXSPAPPID + "&secret=" + SystemStaticValue.WXSPSECRET + "&grant_type=client_credential";  
			logger.info("开始获取access_token，访问地址https://api.weixin.qq.com/cgi-bin/token，传入参数："+params);
			String sr = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);  
			logger.info("获取access_token，返回结果"+sr);
			JSONObject json = JSONObject.parseObject(sr);
			String accessToken = json.get("access_token").toString();
			
			Map<String,Object> map = new HashMap<String,Object>(0);
			map.put("width", 160);
			map.put("path", "pages/wyj/wyj");
			map.put("scene",staffId+",1");//推广码
			params = JSON.toJSONString(map);
			logger.info("开始获取二维码信息，访问地址https://api.weixin.qq.com/wxa/getwxacodeunlimit，传入参数："+params);
			String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			HttpEntity requestEntity = new HttpEntity(map, headers);
			ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
			logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
			result = entity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
        }
		return result;
	}
}
