package com.huabo.gkb.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpClient {
	//521959
	private static String charset = "UTF-8";
	private static final Log logger = LogFactory.getLog(HttpClient.class);
	
	private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
	
	// 此方法是POST请求上传的参数中包含本地图片信息File类型
	public static String request(String url, HashMap<String, Object> fields, HashMap<String, File> files) throws Exception {

	
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = null;
		// HttpClient请求的相关设置，可以不用配置，用默认的参数，这里设置连接和超时时长(毫秒)
		//RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
		try {
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
			if(fields!=null){
				addFields(entityBuilder, fields);
			}
			if(files!=null){
				addFiles(entityBuilder, files);
			}
	
	
	
			HttpPost httpPost = new HttpPost(url);
			HttpEntity reqEntity = entityBuilder.build();
			httpPost.setEntity(reqEntity);
			//httpPost.setConfig(config);
			// 执行网络请求并返回结果
			response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
		if (resEntity != null) {
			result = streamToString(resEntity.getContent(), charset);
		}
			EntityUtils.consume(resEntity);
		} finally {
			response.close();
			httpClient.close();
		}
		// 得到的是JSON类型的数据需要第三方解析JSON的jar包来解析
		return result;
	}


	private static void addFiles(MultipartEntityBuilder entityBuilder, HashMap<String, File> files) {
	
		if (files == null) {
			return;
		}
		for (String name : files.keySet()) {
			File file = files.get(name);
			FileBody fileBody = new FileBody(file);
			entityBuilder.addPart(name, fileBody);
		}
	}


	private static void addFields(MultipartEntityBuilder entityBuilder, HashMap<String, Object> fields) {
		for (String name : fields.keySet()) {
			if(fields.get(name)!=null){
				String value = fields.get(name).toString();
				ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
				StringBody StringBody = new StringBody(value,contentType);
				entityBuilder.addPart(name, StringBody);
			}
		}
	}

	
	

	// 此方法是把传进的字节流转化为相应的字符串并返回，此方法一般在网络请求中用到
	private static String streamToString(InputStream inputStream, String charset) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset)) {
			try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
				String line = null;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line).append("\r\n");
				}
			}
		}
		return stringBuilder.toString();
	}

	
	/**
	 * 
	 * @param filepath 文件路径 
	 * @param filename 文件名称
	 * @param httpurl http的访问地址
	 * @return
	 */
	public static String returnResult(String filepath,String filename,String httpurl){
		String result="";
		HashMap<String, Object> fields = new HashMap<String, Object>();
		//上传参数
		fields.put("module",filename);
		//上传文件
		File file = new File(filepath);
		HashMap<String, File> files = new HashMap<String, File>();
		files.put("file", file);
		try {
			result= request(httpurl, fields, files);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public static boolean httpPostWithJson(JSONObject jsonObj,String url,String appid){
	    boolean isSuccess = false;
	    
	    HttpPost post = null;
	    try {
	        DefaultHttpClient httpClient = new DefaultHttpClient();

	        // 设置超时时间
	        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
	        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
	            
	        post = new HttpPost(url);
	        // 构造消息头
	        post.setHeader("Content-type", "application/json; charset=utf-8");
	        post.setHeader("Connection", "Close");
	        String sessionId = getSessionId();
	        post.setHeader("SessionId", sessionId);
	        post.setHeader("appid", appid);
	                    
	        // 构建消息实体
	        StringEntity entity = new StringEntity(jsonObj.toString(), Charset.forName("UTF-8"));
	        entity.setContentEncoding("UTF-8");
	        // 发送Json格式的数据请求
	        entity.setContentType("application/json");
	        post.setEntity(entity);
	            
	        HttpResponse response = httpClient.execute(post);
	            
	        // 检验返回码
	        int statusCode = response.getStatusLine().getStatusCode();
	        if(statusCode != HttpStatus.SC_OK){
	        	System.out.println("请求出错: "+statusCode);
	            isSuccess = false;
	        }else{
	            int retCode = 0;
	            String sessendId = "";
	            // 返回码中包含retCode及会话Id
	            for(Header header : response.getAllHeaders()){
	                if(header.getName().equals("retcode")){
	                    retCode = Integer.parseInt(header.getValue());
	                }
	                if(header.getName().equals("SessionId")){
	                    sessendId = header.getValue();
	                }
	            }
	            System.out.println(" sessionId: "+sessendId+"\t"+"retCode: "+retCode);
	            
	           /* if(ErrorCodeHelper.IAS_SUCCESS != retCode ){
	                // 日志打印
	            	System.out.println("error return code,  sessionId: "sessendId"\t"+"retCode: "+retCode);
	                isSuccess = false;
	            }else{
	                isSuccess = true;
	            }*/
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        isSuccess = false;
	    }finally{
	        if(post != null){
	            try {
	                post.releaseConnection();
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return isSuccess;
	}

	// 构建唯一会话Id
	public static String getSessionId(){
	    UUID uuid = UUID.randomUUID();
	    String str = uuid.toString();
	    return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
	}

	
	
	/**
	     * post请求，参数为json字符串
	     * @param url 请求地址
	     * @param jsonString json字符串
	     * @return 响应
	     */
	    public String postJson(String url,String jsonString)
	    {
	        String result = null;
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost post = new HttpPost(url);
	        CloseableHttpResponse response = null;
	        try {
	            post.setEntity(new ByteArrayEntity(jsonString.getBytes("UTF-8")));
	            response = httpClient.execute(post);
	            if(response != null && response.getStatusLine().getStatusCode() == 200)
	            {
	                HttpEntity entity = response.getEntity();
	                result = entityToString(entity);
	            }
	            return result;
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            try {
	                httpClient.close();
	                if(response != null)
	                {
	                    response.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	    }
	
	    
	   private String entityToString(HttpEntity entity) throws IOException {
	       String result = null;
	       if(entity != null)
	       {
	           long lenth = entity.getContentLength();
	           if(lenth != -1 && lenth < 2048)
	           {
	               result = EntityUtils.toString(entity,"UTF-8");
	           }else {
	               InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
	               CharArrayBuffer buffer = new CharArrayBuffer(2048);
	               char[] tmp = new char[1024];
	               int l;
	               while((l = reader1.read(tmp)) != -1) {
	                   buffer.append(tmp, 0, l);
	               }
	               result = buffer.toString();
	           }
	       }
	       return result;
	   }
	   
	   
	   public static Map<String, Object> JsonToMap (String json){
		   
		   JSONObject jsonObject = JSONObject.fromObject(json);
	         
	        Map<String, Object> mapJson = JSONObject.fromObject(jsonObject);
	         
	        for(Entry<String,Object> entry : mapJson.entrySet()){
	            Object strval1 = entry.getValue();
	            JSONObject jsonObjectStrval1 = JSONObject.fromObject(strval1);
	            Map<String, Object> mapJsonObjectStrval1 = JSONObject.fromObject(jsonObjectStrval1);
	            
	            System.out.println("KEY:"+entry.getKey()+"  -->  Value:"+entry.getValue()+"\n");
	            
	            for(Entry<String, Object> entry1:mapJsonObjectStrval1.entrySet()){
	                System.out.println("KEY:"+entry1.getKey()+"  -->  Value:"+entry1.getValue()+"\n");
	            }
	             
	        }
		   return mapJson;
	   }
	   
	   
	   public static Map JsonToMapOne(String jsonArrayData){
		   JSONArray jsonArray = JSONArray.fromObject(jsonArrayData);
		   
	        List<Map<String,Object>> mapListJson = (List)jsonArray;
	        for (int i = 0; i < mapListJson.size(); i++) {
	            Map<String,Object> obj=mapListJson.get(i);
	             
	            for(Entry<String,Object> entry : obj.entrySet()){
	                String strkey1 = entry.getKey();
	                Object strval1 = entry.getValue();
	                System.out.println("KEY:"+strkey1+"  -->  Value:"+strval1+"\n");
	            }
	        }
	        return null;
		   
	   }
	   
	   
	   
	   public static boolean  updateProcess(File file){
		   boolean result=false;
		   HashMap<String, Object> fields = new HashMap<String, Object>();
		   fields.put("module", file.getName());
		   HashMap<String, File> files = new HashMap<String, File>();
		   files.put("file", file);
		   try {
			String resultjson = request("", fields, files);
			Map<String,Object>  map = JsonToMap(resultjson);
			 for(Entry<String,Object> entry : map.entrySet()){
				if(entry.getKey()!=null && entry.getKey().equals("result")){
					result=(boolean) entry.getValue();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		   return result;
	   }
	   
	
	    
	    
	    
	public static void main(String[] args) throws Exception {

		/* String lcdyurl="http://47.104.170.65:8081/workFlow/process/queryProcessDefinition";
	     HashMap<String, Object> fields6 = new HashMap<String, Object>();
	     fields6.put("processDefinitionKey", "Registrationprocess");
	     //fields6.put("processDefinitionId", "Registrationprocess");
	     fields6.put("start", "2");
	     fields6.put("pageSize", "5");
		 String result = request(lcdyurl, fields6, null);
		 System.out.println(result);
		 JSONObject jsonObject = JSONObject.fromObject(result);
		  String recordListjson=jsonObject.getString("data");
		   JSONObject jsonobject = JSONObject.fromObject(recordListjson);
		    //将json格式的字符串转换成JSONObject 对象
		    JSONArray array = jsonobject.getJSONArray("recordList");
	     for (int i = 0; i < array.size(); i++) {
	         JSONObject object = (JSONObject) array.get(i);
	         //将array中的数据进行逐条转换
	         ProcessView rule = (ProcessView) JSONObject.toBean(object, ProcessView.class);  //通过JSONObject.toBean()方法进行对象间的转换
	     }*/
		
		// 流程定义
				// http:/47.104.170.65:8081/workFlow/process/queryProcessDefinition
				// String processDefinitionKey,String processDefinitionId
				/*String lcdyurl = "http://47.104.170.65:8081/workFlow/process/queryProcessDefinition";
				HashMap<String, Object> fields6 = new HashMap<String, Object>();
				fields6.put("processDefinitionKey", "SJ_JHTZD");
				//fields6.put("processDefinitionId", "Registrationprocess");30024
				fields6.put("start", "1");
				fields6.put("pageSize", "5");
				String result = request(lcdyurl, fields6, null);
				System.out.println(result);*/
				
				/*String sc = "http://47.104.170.65:8081/workFlow/process/deleteProcessDefinition";
				HashMap<String, Object> fields6 = new HashMap<String, Object>();
				fields6.put("deploymentId", "30024");
				fields6.put("cascade", false);
				fields6.put("start", "1");
				fields6.put("pageSize", "5");
				String result = request(sc, fields6, null);
				System.out.println(result);*/
				// http://47.104.170.65:8081/workFlow/process/history/processInstance
		// 查询代办任务
				// 参数 userId:启动流程用户的id groupId:流程审批组的id start:分页起始数据 pageSize:分页显示的条数
				String rwurl = "http:192.168.0.22:8080/home/wddb/wddb_xcx";
				HashMap<String, Object> fields3 = new HashMap<String, Object>();
				fields3.put("staffid", "30");
				fields3.put("orgid", "1");
				String result = request(rwurl, fields3, null);
				System.out.println(result);
				// JsonToMap(result);
				
				// 流程走向
				

	}

	public static String postData(String urlStr, String data, String contentType) {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlStr);
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        conn.setConnectTimeout(CONNECT_TIMEOUT);
	        conn.setReadTimeout(CONNECT_TIMEOUT);
	        if(contentType!=null && !"".equals(contentType))
	            conn.setRequestProperty("content-type", contentType);
	        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), charset);
	        if(data == null)
	            data = "";
	        writer.write(data); 
	        writer.flush();
	        writer.close();  
	
	        reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	            sb.append("\r\n");
	        }
	        return sb.toString();
	    } catch (IOException e) {
	    	logger.error("Error connecting to " + urlStr + ": " + e.getMessage());
	    } finally {
	        try {
	            if (reader != null)
	                reader.close();
	        } catch (IOException e) {
	        }
	    }
	    return null;
	}

}
