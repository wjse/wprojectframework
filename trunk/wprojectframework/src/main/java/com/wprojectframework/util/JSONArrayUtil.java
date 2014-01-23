package com.wprojectframework.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <pre>
 * JSONArray扩展工具类,解决JOSNObject返回JSONArray时
 * array length只有1个元素时默认不返回JSONArray对象
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     JSONObject
 * @see     JSONArray
 * @since   JDK1.6
 */
public class JSONArrayUtil {
	
	private JSONArrayUtil(){
		
	}
	
	/**
	 * 获取JSONArray
	 * @param jobj JSONObject
	 * @param name String
	 * @return JSONArray
	 */
	public static JSONArray getArray(JSONObject jobj,String name){
		if(jobj == null || !jobj.has(name)){
			return new JSONArray();
		}
		Object obj = jobj.get(name);
		if(obj instanceof JSONObject){
			JSONArray array  = new JSONArray();
			array.add(obj);
			return array;
		}else{
			return JSONArray.fromObject(obj);
		}
	}
}
