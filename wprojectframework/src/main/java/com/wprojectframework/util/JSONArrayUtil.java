package com.wprojectframework.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * JSONArray扩展工具类
 * @author lenovo
 *
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
