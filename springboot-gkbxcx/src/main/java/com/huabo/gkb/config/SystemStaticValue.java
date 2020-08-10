package com.huabo.gkb.config;


public class SystemStaticValue {
	
	/**
	 * 数据类型 
	 * 0  ----------- MySql
	 * 1  ----------- Oracle
	 * 2  ----------- SqlServer
	 */
	public static final Integer DataType = 1;
	
	public static final String WXSPAPPID = "wxb81b0c7f1d45d9b5"; //小程序APPID
	public static final String WXSPSECRET = "26231aa209a9819db2a25e1503e4c32a"; //小程序秘钥
	public static final String GRANT_TYPE = "authorization_code";
	public static final String XCXNAME = "管控宝"; //小程序名称
	
	public final static String MCH_ID = "1561973281";					//商户号
	public final static String API_KEY = "hbyunweixinshanghumiyao12345hbyw";					//商户api秘钥
	public final static String ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder"; //统一下单地址
	public final static String NOTIFY_URL = "https://gkb.huabodashuju.com/wxPay/wxPay_callback";				//微信支付回调接口
	//public final static String NOTIFY_URL = "http://zkk5eb.natappfree.cc/wxPay/wxPay_callback";				//微信支付回调接口
	//外部数据查询忽略的用户
	//public static final String WBSJSTAFFID = "521750,521959,246900,127822";
	public static final String WBSJSTAFFID = "";
	
	/**
	 * 
	 * 	{
	"avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/c9xpiakQ3OC3v1ibWrr5qpibKEOgxVnibsvicQwap2ia3KF9XvfVv4ugPCH4eRlIbNDoGXyDtdrKtoHoDb02FPSmV92Q/132",
	"email": "1421869153@qq.com",
	"gender": "1",
	"miblePhone": "17610245700",
	"nickName": "祈樱",
	"orgId": 246899,
	"realName": "李鸿鹏",
	"staffId": "246900",
	"unionId": "oKihF0jM51Y7QDYJbzliglwCHwmA",
	"userId": 246901,
	"xcxOpenId": "ojiL50D9heBUoQzmXPQzg84QbmEg"
}
	 */ 
}
