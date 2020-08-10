package com.huabo.gkb.util;

import com.alibaba.fastjson.JSONObject;

public class JsonBean {

	private String code;
	private Object result;
	public static String success(Object result){
		JsonBean jsonBean = new JsonBean();
		jsonBean.setCode("0");
		jsonBean.setResult(result);
		return  JSONObject.toJSONString(jsonBean);
	}
	public static String res(String code){
		JsonBean jsonBean = new JsonBean();
		jsonBean.setCode(code);
		return  JSONObject.toJSONString(jsonBean);
	}
	public static String success(){
		JsonBean jsonBean = new JsonBean();
		jsonBean.setCode("0");
		return  JSONObject.toJSONString(jsonBean);
	}
	public static String  error(Object result){
		JsonBean jsonBean = new JsonBean();
		jsonBean.setCode("1");
		jsonBean.setResult(result);
		return  JSONObject.toJSONString(jsonBean);
	}
	public static String  error(){
		JsonBean jsonBean = new JsonBean();
		jsonBean.setCode("1");
		return  JSONObject.toJSONString(jsonBean);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
