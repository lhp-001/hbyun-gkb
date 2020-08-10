package com.huabo.gkb.tool;

import org.apache.commons.configuration.ConfigurationException;


/**
 * 获取短信接口配置信息
 * @author caofuqiao
 *
 */
public class MessageConfig {

	public static  String accessKeyId = "";
	public static  String accessKeySecret = "";
	public static  String registertemplateCode	 = "";
	public static  String updatePwdtemplateCode	 = "";
	public static  String signName	 = "";
	static{
		try {
			accessKeyId =  ConfigXmlUtils.FlagConfig("message", "accessKeyId");
			accessKeySecret =  ConfigXmlUtils.FlagConfig("message", "accessKeySecret");
			registertemplateCode  =  ConfigXmlUtils.FlagConfig("message", "registertemplateCode");
			updatePwdtemplateCode	  =  ConfigXmlUtils.FlagConfig("message", "updatePwdtemplateCode");
			signName  =  ConfigXmlUtils.FlagConfig("message", "signName");
		} catch (ConfigurationException e) {
		
			e.printStackTrace();
		}
	   
	}
}
