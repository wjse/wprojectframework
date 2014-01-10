package com.wprojectframework.core;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import com.thoughtworks.xstream.XStream;


/**
 * HTTP消息发送器 
 * @author wujia
 *
 */
public class HttpSender implements Constants{
	
	/**
	 * HttpClient
	 */
	private HttpClient client = new HttpClient();
	
	/**
	 * 成功返回状态码
	 */
	private static final int STATUS_SUCCESS = 200;
	
	
	/**
	 * 发送Http消息
	 * @param methodType Http方法类型
	 * @param serviceUrl 服务地址
	 * @param params 发送参数
	 * @return JSONObject
	 */
	public JSONObject sendMethod(MethodType methodType,String serviceUrl,Map<String,Object> params) throws HttpException, IOException{
		if(null == params){
			params = new HashMap<String, Object>(0);
		}
		switch (methodType) {
		case GET:
			return sendGetMethod(serviceUrl,params);
		case POST:
			return sendPostMethod(serviceUrl, params);
		case PUT:
			return sendPutMethod(serviceUrl, params);
		case DELETE:
			return sendDeleteMethod(serviceUrl, params);
		default:
		    break;
		}
		return null;
	}
	
	/**
	 * GET
	 */
	private JSONObject sendGetMethod(String serviceUrl,Map<String,Object> params) throws HttpException, IOException{
		return JSONObject.fromObject(invokeUrlMethod(serviceUrl, new GetMethod(), params));
	}
	/**
	 * POST
	 */
	private JSONObject sendPostMethod(String serviceUrl,Map<String,Object> params) throws HttpException, IOException{
		PostMethod method = new PostMethod(serviceUrl);
		String response = invoke(method, params);
		return JSONObject.fromObject(response);
	}
	
	/**
	 * PUT
	 */
	private JSONObject sendPutMethod(String serviceUrl,Map<String,Object> params) throws HttpException, IOException{
		PutMethod method = new PutMethod(serviceUrl);
		String response = invoke(method, params);
		return JSONObject.fromObject(response);
	}
	
	/**
	 * DELETE
	 */
	private JSONObject sendDeleteMethod(String serviceUrl,Map<String,Object> params) throws HttpException, IOException{
		return JSONObject.fromObject(invokeUrlMethod(serviceUrl, new DeleteMethod(), params));
	}
	
	/**
	 * HttpClient发送执行POST,PUT方法
	 * @param method HTTP方法
	 * @param params 请求参数
	 * @return String 返回字符串
	 */
	private String invoke(EntityEnclosingMethod method,Map<String,Object> params) throws HttpException, IOException{
		setMethodHeader(method);
		//构建XML报文
		StringBuilder builder = new StringBuilder();
		builder.append("<root>");//统一以root为根节点
		Set<String> keys = params.keySet();
		for (String key : keys) {
			XStream xs = new XStream();
			Object obj = params.get(key);
			
			/*
			 * XStream提供的Object转XML不包含java基本数据类型的包装对象
			 * 需要手动转换为基本类型类模板
			 */
			if(String.valueOf(obj.getClass()).endsWith(I)){
				xs.aliasType(key, int.class);
			}else if(String.valueOf(obj.getClass()).endsWith(L)){
				xs.aliasType(key, long.class);
			}else if(String.valueOf(obj.getClass()).endsWith(B)){
				xs.aliasType(key, byte.class);
			}else if(String.valueOf(obj.getClass()).endsWith(C)){
				xs.aliasType(key, char.class);
			}else if(String.valueOf(obj.getClass()).endsWith(S)){
				xs.aliasType(key, short.class);
			}else if(String.valueOf(obj.getClass()).endsWith(D)){
				xs.aliasType(key, double.class);
			}else if(String.valueOf(obj.getClass()).endsWith(F)){
				xs.aliasType(key, float.class);
			}else if(String.valueOf(obj.getClass()).endsWith(BL)){
				xs.aliasType(key, boolean.class);
			}else if(String.valueOf(obj.getClass()).endsWith("Date")){
				xs.aliasType(key, Date.class);
			}else{
				xs.aliasType(key, obj.getClass());
			}
			builder.append(xs.toXML(obj));
		}
		builder.append("</root>");
		String data = builder.toString();
		//以byte数组的形式设置HTTP请求参数
		method.setRequestEntity(new ByteArrayRequestEntity(data.getBytes(CHAR_ENCODING)));
		int status = client.executeMethod(method);
		if(STATUS_SUCCESS == status){
			return method.getResponseBodyAsString();
		}else{
			return "{error:"+status+"}";
		}
	}
	
	/**
	 * HttpClient发送执行GET,DELETE方法
	 * @param method HTTP方法
	 * @param params 请求参数
	 * @return String 返回字符串
	 */
	private String invokeUrlMethod(String serviceUrl,HttpMethodBase method,Map<String,Object> params) throws IOException{
		//构建请求路径
		StringBuilder builder = new StringBuilder();
		builder.append(serviceUrl);
		builder.append("/");
		Set<String> keys = params.keySet();
		int i = 0;
		for (String key : keys) {
			builder.append(params.get(key));
			if(i != keys.size()-1){
				builder.append("/");
				i++;
			}
		}
		method.setPath(builder.toString());
		int status = client.executeMethod(method);
		if(STATUS_SUCCESS == status){
			return method.getResponseBodyAsString();
		}else{
			return "{error:"+status+"}";
		}
	}
	
	/**
	 * 设置请求头信息
	 * @param method HTTP方法
	 */
	private void setMethodHeader(HttpMethod method){
		method.setRequestHeader("accept", "*/*");
		method.setRequestHeader("content-type", "application/xml");
		method.setRequestHeader("encoding", CHAR_ENCODING);
		client.getParams().setContentCharset(CHAR_ENCODING);
	}
	
	/**
	 * HTTP方法类型
	 * @author wujia
	 *
	 */
	public enum MethodType{
		GET,
		POST,
		PUT,
		DELETE
	}
}
