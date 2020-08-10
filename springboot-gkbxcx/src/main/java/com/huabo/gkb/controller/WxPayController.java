package com.huabo.gkb.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;
import com.huabo.gkb.config.SystemStaticValue;
import com.huabo.gkb.entity.PageInfo;
import com.huabo.gkb.entity.TblYyUserOrder;
import com.huabo.gkb.entity.TblYyUserQuery;
import com.huabo.gkb.service.TblOrganizationService;
import com.huabo.gkb.service.TblYyOrgDepositService;
import com.huabo.gkb.service.TblYyUserOrderService;
import com.huabo.gkb.util.DateUtil;
import com.huabo.gkb.util.HttpClient;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("wxPay")
public class WxPayController {
	@Resource
	public TblOrganizationService tblOrganizationService;
	
	@Resource
	public TblYyUserOrderService tblYyUserOrderService;
	
	@Resource
	public TblYyOrgDepositService tblYyOrgDepositService;
	
	
	/**
	 * 查询公司消费记录
	 */
	@RequestMapping(value = "/price_company")
	@ResponseBody
	public String cost_priceCompany(HttpServletRequest request,HttpServletResponse response,TblYyUserQuery yuq,
			@RequestParam(value="pageNumber",required=false)Integer pageNumber,@RequestParam(value="staffId",required=true)Integer staffId,
			@RequestParam(value="orgId",required=true)String orgId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String, Object>(0);
		try {
			if(pageNumber == null){
				pageNumber = 1;
			}
			Integer companyId = tblOrganizationService.findCompanyIdByDeptId(orgId);
			
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(50);
				com.huabo.gkb.entitymysql.TblYyUserQuery mqlYuq = new com.huabo.gkb.entitymysql.TblYyUserQuery();
				mqlYuq.setOrgid(new BigDecimal(companyId));
				pageInfo.setCondition(mqlYuq);
				this.tblYyOrgDepositService.findCostPircePageInfoMySql(pageInfo);
				resultMap.put("pageNumber", pageInfo.getCurrentPage());
				resultMap.put("pageSize", pageInfo.getPageSize());
				resultMap.put("totalPage", pageInfo.getTotalPage());
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("totalRecord", pageInfo.getTotalRecord());
			}else {
				PageInfo<TblYyUserQuery> pageInfo = new PageInfo<TblYyUserQuery>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(50);
				yuq.setOrgid(new BigDecimal(companyId));
				pageInfo.setCondition(yuq);
				this.tblYyOrgDepositService.findCostPircePageInfo(pageInfo);
				resultMap.put("pageNumber", pageInfo.getCurrentPage());
				resultMap.put("pageSize", pageInfo.getPageSize());
				resultMap.put("totalPage", pageInfo.getTotalPage());
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("totalRecord", pageInfo.getTotalRecord());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 查询公司余额
	 */
	@RequestMapping(value = "/balance_money")
	@ResponseBody
	public String balance_money(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="staffId",required=true)Integer staffId,
			@RequestParam(value="orgId",required=true)String orgId) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String, Object>(0);
		try {
			Double balance = this.tblYyOrgDepositService.calBalanceMoney(orgId);
			resultMap.put("balance", balance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}
	
	/**
	 * 查询公司充值录
	 */
	@RequestMapping(value = "/depositMoney_redcord")
	@ResponseBody
	public String depositMoney_redcord(HttpServletRequest request,HttpServletResponse response,TblYyUserOrder yuo,
			@RequestParam(value="teamid",required=false)Integer teamid,@RequestParam(value="staffId",required=true)Integer staffId,
			@RequestParam(value="orgId",required=true)String orgId,@RequestParam(value="pageNumber",required=true)Integer pageNumber
			) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String, Object>(0);
		try {
			Integer companyId = tblOrganizationService.findCompanyIdByDeptId(orgId);
			
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblYyUserOrder> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblYyUserOrder>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(10);
				com.huabo.gkb.entitymysql.TblYyUserOrder mqlYuo= new com.huabo.gkb.entitymysql.TblYyUserOrder();
				mqlYuo.setOrgId(new BigDecimal(companyId));
				mqlYuo.setStatus(teamid);
				pageInfo.setCondition(mqlYuo);
				this.tblYyUserOrderService.selectPageInfoListMySql(pageInfo);
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("currentPage",pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}else {
				PageInfo<TblYyUserOrder> pageInfo = new PageInfo<TblYyUserOrder>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(10);
				yuo.setOrgId(new BigDecimal(companyId));
				yuo.setStatus(teamid);
				pageInfo.setCondition(yuo);
				this.tblYyUserOrderService.selectPageInfoList(pageInfo);
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("currentPage",pageInfo.getCurrentPage());
				resultMap.put("totalPage", pageInfo.getTotalPage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}
	
	
	/**
	 * 微信支付下单；
	 * @param request
	 * @param reponse
	 * @param totalFee
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/payUrl")
	@ResponseBody
	public Map<String,String> payUrl(HttpServletRequest request,HttpServletResponse response,HttpServletResponse reponse,
			@RequestParam(value="money",required = true)String money,@RequestParam(value="staffId",required = true)String staffId,
			@RequestParam(value="orgId",required = true)String orgId,@RequestParam(value="openid",required = true)String openid) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		long shopNo = DateUtil.dateToUnixTimestamp();
		/*String dateStr = DateUtil.getIntDateStr(new Date(),DateUtil.DATE_KEY_ALLSTR);
		String str = null;*/
		String no = String.valueOf(shopNo);
		Integer total_fee = (int) (Float.valueOf(money) * 100);
		Map<String,String> dataMap = new HashMap<String,String>(0);
		dataMap.put("appid", SystemStaticValue.WXSPAPPID);
		dataMap.put("mch_id", SystemStaticValue.MCH_ID);
		String nonec_str = WXPayUtil.generateNonceStr();
		dataMap.put("nonce_str", nonec_str);
		dataMap.put("body", "望远镜功能充值");
		dataMap.put("out_trade_no", no);
		dataMap.put("total_fee",String.valueOf(total_fee));
		dataMap.put("spbill_create_ip",this.localIp());
		dataMap.put("notify_url", SystemStaticValue.NOTIFY_URL);
		dataMap.put("trade_type", "JSAPI");
		dataMap.put("openid",openid);
		String sign = WXPayUtil.generateSignature(dataMap, SystemStaticValue.API_KEY);
		dataMap.put("sign",sign );
		String xml = WXPayUtil.mapToXml(dataMap);
		
		String resXml = HttpClient.postData(SystemStaticValue.ORDER_URL, xml, null);
		Map<String,String> resultMap = WXPayUtil.xmlToMap(resXml);
		if("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("result_code"))){
			Integer companyId = tblOrganizationService.findCompanyIdByDeptId(orgId);
			
			if(SystemStaticValue.DataType == 0) {
				com.huabo.gkb.entitymysql.TblYyUserOrder tuo = new com.huabo.gkb.entitymysql.TblYyUserOrder();
				tuo.setOrderno(no);
				tuo.setCreatedate(new Date());
				tuo.setOrdermoney(Double.valueOf(money));
				tuo.setStatus(1);
				tuo.setOrgId(new BigDecimal(companyId));
				tuo.setStaffid(new BigDecimal(staffId));
				this.tblYyUserOrderService.saveTuoMySql(tuo);
				resultMap.put("orderId", tuo.getOrderid().toString());
				resultMap.put("out_trade_no", no);
			}else {
				TblYyUserOrder tuo = new TblYyUserOrder();
				tuo.setOrderno(no);
				tuo.setCreatedate(new Date());
				tuo.setOrdermoney(Double.valueOf(money));
				tuo.setStatus(1);
				tuo.setOrgId(new BigDecimal(companyId));
				tuo.setStaffid(new BigDecimal(staffId));
				this.tblYyUserOrderService.save(tuo);
				resultMap.put("orderId", tuo.getOrderid().toString());
				resultMap.put("out_trade_no", no);
			}
			
			Map<String,String> requestMap = new HashMap<String,String>(0);
			String ncStr = WXPayUtil.generateNonceStr();
			requestMap.put("nonceStr",ncStr);
			resultMap.put("wxnonceStr", ncStr);
			shopNo = DateUtil.dateToUnixTimestamp();
			requestMap.put("timeStamp",String.valueOf(shopNo));
			resultMap.put("wxtimeStamp", String.valueOf(shopNo));
			requestMap.put("package", "prepay_id="+resultMap.get("prepay_id"));
			requestMap.put("signType",WXPayConstants.MD5);
			requestMap.put("appId",resultMap.get("appid"));
			sign = WXPayUtil.generateSignature(requestMap, SystemStaticValue.API_KEY,SignType.MD5);
			resultMap.put("wxsign", sign);
		}
		System.out.println(resultMap.toString());
		return resultMap;
	}
	
	/**
	 * 微信平台发起的回调方法，
	 * 调用我们这个系统的这个方法接口，将扫描支付的处理结果告知我们系统
	 * @throws JDOMException
	 * @throws Exception
	 */
	@RequestMapping(value = "/wxPay_callback",method = RequestMethod.POST)
	public String weixinNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
	       //读取参数  
		response.addHeader("Access-Control-Allow-Origin", "*");
		 	String resXml = "";  
	       InputStream inputStream ;  
	       StringBuffer sb = new StringBuffer();  
	       inputStream = request.getInputStream();  
	       String s ;  
	       BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
	       while ((s = in.readLine()) != null){  
	           sb.append(s);
	       }
	       in.close();
	       inputStream.close();
	  
	       //解析xml成map  
	       Map<String, String> m = WXPayUtil.xmlToMap(sb.toString());  
	        
	       //过滤空 设置 TreeMap  
	       Map<String,String> packageParams = new HashMap<String,String>(0);        
	       Iterator it = m.keySet().iterator();  
	       while (it.hasNext()) {  
	           String parameter = (String) it.next();
	           String parameterValue = m.get(parameter);
	            
	           String v = "";  
	           if(null != parameterValue) {
	               v = parameterValue.trim();  
	           }  
	           packageParams.put(parameter, v);  
	       }  
	          
	       // 账号信息  
	  
	       Map<String,String> dataMap = new HashMap<String,String>(0);
		    dataMap.put("appid", (String)packageParams.get("appid"));
			dataMap.put("mch_id", (String)packageParams.get("mch_id"));
			dataMap.put("nonce_str", (String)packageParams.get("nonce_str"));
			dataMap.put("body", "望远镜功能充值");
			dataMap.put("out_trade_no", (String)packageParams.get("out_trade_no"));
			dataMap.put("total_fee",(String)packageParams.get("total_fee"));
			dataMap.put("spbill_create_ip",this.localIp());
			dataMap.put("notify_url", SystemStaticValue.NOTIFY_URL);
			dataMap.put("trade_type", (String)packageParams.get("trade_type"));
			dataMap.put("openid",SystemStaticValue.WXSPAPPID);
			String sign = WXPayUtil.generateSignature(dataMap, SystemStaticValue.API_KEY);
			dataMap.put("sign",sign);
	       //判断签名是否正确 
	       if(WXPayUtil.isSignatureValid(dataMap,SystemStaticValue.API_KEY)) {  
	           //------------------------------  
	           //处理业务开始  
	           //------------------------------  
	          
	           if("SUCCESS".equals((String)packageParams.get("return_code"))){  
	               // 这里是支付成功  
	               //////////执行自己的业务逻辑////////////////  
	               String out_trade_no = (String)packageParams.get("out_trade_no");  
	               //////////执行自己的业务逻辑//////////////// 
	               //暂时使用最简单的业务逻辑来处理：只是将业务处理结果保存到session中
	               //（根据自己的实际业务逻辑来调整，很多时候，我们会操作业务表，将返回成功的状态保留下来）
	               //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.  
	               resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
	                       + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
	               
	               if(SystemStaticValue.DataType == 0) {
	            	   this.tblYyUserOrderService.dealPaySuccessMySql(out_trade_no,(String)packageParams.get("transaction_id")); 
	               }else {
	            	   this.tblYyUserOrderService.dealPaySuccess(out_trade_no,(String)packageParams.get("transaction_id")); 
	               }
	           } else {
	               resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
	                       + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";  
	           }
	           //------------------------------  
	           //处理业务完毕  
	           //------------------------------  
	          
	       } else{  
	         System.out.println("通知签名验证失败");
	         resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                     + "<return_msg><![CDATA[签名校验失败]]></return_msg>" + "</xml> ";  
	       }
	       BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
           out.write(resXml.getBytes());
           out.flush();
           out.close();
	       return null;    
	}
	
	/**
	 * 查询推广公司的消费记录
	 */
	@RequestMapping(value = "/promotion_monay")
	@ResponseBody
	public String promotion_monay(HttpServletRequest request,HttpServletResponse response,TblYyUserQuery yuq,
			@RequestParam(value="pageNumber",required=false)Integer pageNumber,@RequestParam(value="staffId",required=true)Integer staffId
			) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> resultMap = new HashMap<String, Object>(0);
		try {
			if(pageNumber == null){
				pageNumber = 1;
			}
			Double payMoney = 0.0;
			
			if(SystemStaticValue.DataType == 0) {
				PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery> pageInfo = new PageInfo<com.huabo.gkb.entitymysql.TblYyUserQuery>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(10);
				
				com.huabo.gkb.entitymysql.TblYyUserQuery mqlYuq = new com.huabo.gkb.entitymysql.TblYyUserQuery();
				mqlYuq.setStaffid(new BigDecimal(staffId));
				pageInfo.setCondition(mqlYuq);
				this.tblYyOrgDepositService.findPromotionPircePageInfoMySql(pageInfo);
				payMoney = this.tblYyOrgDepositService.findPromotionAllMoneyMySql(staffId);
				resultMap.put("pageNumber", pageInfo.getCurrentPage());
				resultMap.put("pageSize", pageInfo.getPageSize());
				resultMap.put("totalPage", pageInfo.getTotalPage());
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("totalRecord", pageInfo.getTotalRecord());
			}else {
				PageInfo<TblYyUserQuery> pageInfo = new PageInfo<TblYyUserQuery>();
				pageInfo.setCurrentPage(pageNumber);
				pageInfo.setPageSize(10);
				yuq.setStaffid(new BigDecimal(staffId));
				pageInfo.setCondition(yuq);
				this.tblYyOrgDepositService.findPromotionPircePageInfo(pageInfo);
				payMoney = this.tblYyOrgDepositService.findPromotionAllMoney(staffId);
				resultMap.put("pageNumber", pageInfo.getCurrentPage());
				resultMap.put("pageSize", pageInfo.getPageSize());
				resultMap.put("totalPage", pageInfo.getTotalPage());
				resultMap.put("tlist", pageInfo.getTlist());
				resultMap.put("totalRecord", pageInfo.getTotalRecord());
			}
			resultMap.put("totalMoney", payMoney);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(resultMap).toString();
	}
	
	
	/**
	 * 获取访问本机Ip地址
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String localIp(){
	    String ip = null;
		Enumeration allNetInterfaces;
	    try {
	        allNetInterfaces = NetworkInterface.getNetworkInterfaces();            
	        while (allNetInterfaces.hasMoreElements()) {
	            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
	            List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
	            for (InterfaceAddress add : InterfaceAddress) {
	                InetAddress Ip = add.getAddress();
	                if (Ip != null && Ip instanceof Inet4Address) {
	                    ip = Ip.getHostAddress();
	                }
	            }
	        }
	    } catch (SocketException e) {
	    	e.printStackTrace();
	    }
	    return ip;
	}
	
}
