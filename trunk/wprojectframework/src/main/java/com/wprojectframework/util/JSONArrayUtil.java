package com.wprojectframework.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @class JSONArrayUtil.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * JSONArray扩展工具类,解决JOSNObject返回JSONArray时
 * array length只有1个元素时默认不返回JSONArray对象
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
