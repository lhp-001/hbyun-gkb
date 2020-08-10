package com.huabo.gkb.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;



public class MessageTool {

	
	
/**
 * 手机号码
 */
private String phone;

/**
 * 判断模板使用，1为小程序注册，0为修改密码
 * 2为小程序登录，3为小程序修改密码或手机号使用
 */
private String type;
/**
 * 替换Json串
 */
private String templateParam;


public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public MessageTool(String phone,String templateParam,String type){
	
	this.phone=phone;
	this.templateParam=templateParam;
this.type=type;
}

public MessageTool(){
	
}


public String getPhone() {
	return phone;
}


public void setPhone(String phone) {
	this.phone = phone;
}


public String getTemplateParam() {
	return templateParam;
}


public void setTemplateParam(String templateParam) {
	this.templateParam = templateParam;
}



//产品名称:云通信短信API产品,开发者无需替换
static final String product = "Dysmsapi";
//产品域名,开发者无需替换
static final String domain = "dysmsapi.aliyuncs.com";

// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
static final String accessKeyId = MessageConfig.accessKeyId;
static final String accessKeySecret = MessageConfig.accessKeySecret;

public  SendSmsResponse sendSms() throws ClientException {

    //可自助调整超时时间
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");	

    //初始化acsClient,暂不支持region化
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    IAcsClient acsClient = new DefaultAcsClient(profile);

    //组装请求对象-具体描述见控制台-文档部分内容
    SendSmsRequest request = new SendSmsRequest();
    request.setMethod(MethodType.POST);
    //必填:待+发送手机号
    request.setPhoneNumbers(phone);
    //必填:短信签名-可在短信控制台中找到
    request.setSignName(MessageConfig.signName);
    //必填:短信模板-可在短信控制台中找到
    if ("1".equals(type)) {
    	   request.setTemplateCode(MessageConfig.registertemplateCode);
	}else if("2".equals(type)){
		request.setTemplateCode(MessageConfig.registertemplateCode);
	}else if("3".equals(type)){
		request.setTemplateCode(MessageConfig.registertemplateCode);
	}else {
		request.setTemplateCode(MessageConfig.updatePwdtemplateCode);
	}   
 
    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    request.setTemplateParam(templateParam);

    //"{\"name\":\"Tom\", \"code\":\"123\"}"
    //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
    //request.setSmsUpExtendCode("90997");

    //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
   // request.setOutId("yourOutId");

    //hint 此处可能会抛出异常，注意catch
    SendSmsResponse sendSmsResponse=new  SendSmsResponse();
	try {
		sendSmsResponse = acsClient.getAcsResponse(request);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    return sendSmsResponse;
}


public  QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

    //可自助调整超时时间
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");

    //初始化acsClient,暂不支持region化
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    IAcsClient acsClient = new DefaultAcsClient(profile);

    //组装请求对象
    QuerySendDetailsRequest request = new QuerySendDetailsRequest();
    //必填-号码
    request.setPhoneNumber(phone);
    //可选-流水号
    request.setBizId(bizId);
    //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    request.setSendDate(ft.format(new Date()));
    //必填-页大小
    request.setPageSize(10L);
    //必填-当前页码从1开始计数
    request.setCurrentPage(1L);

    //hint 此处可能会抛出异常，注意catch
    QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

    return querySendDetailsResponse;
}

public static void main(String[] args) throws ClientException, InterruptedException {
	

MessageTool messageTool =new  MessageTool("14747419503",  "{\"code\":\"7896\"}","1");
	
    //发短信
    SendSmsResponse response = messageTool.sendSms();
    System.out.println("短信接口返回的数据----------------");
    System.out.println("Code=" + response.getCode());
    System.out.println("Message=" + response.getMessage());
    System.out.println("RequestId=" + response.getRequestId());
    System.out.println("BizId=" + response.getBizId());

    Thread.sleep(3000L);

    //查明细
    if(response.getCode() != null && response.getCode().equals("OK")) {
        QuerySendDetailsResponse querySendDetailsResponse = messageTool.querySendDetails(response.getBizId());
        System.out.println("短信明细查询接口返回数据----------------");
        System.out.println("Code=" + querySendDetailsResponse.getCode());
        System.out.println("Message=" + querySendDetailsResponse.getMessage());
        int i = 0;
        for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
        {
            System.out.println("SmsSendDetailDTO["+i+"]:");
            System.out.println("Content=" + smsSendDetailDTO.getContent());
            System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
            System.out.println("OutId=" + smsSendDetailDTO.getOutId());
            System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
            System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
            System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
            System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
            System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
        }
        System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
        System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
    }

}
	
}
