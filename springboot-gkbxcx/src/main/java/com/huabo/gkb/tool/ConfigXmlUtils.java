package com.huabo.gkb.tool;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * 读取配置文件要素
 * @author haoyuge
 *
 */
public class ConfigXmlUtils {
	
	public static String FlagConfig(String key ,String value) throws ConfigurationException{
		Configuration config = new XMLConfiguration("config.xml");
		return config.getString(key+"."+value);
	}

}
