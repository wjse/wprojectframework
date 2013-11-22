package com.wprojectframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.wprojectframework.core.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @class JSONToEntityUtil.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * JSONObject转实体工具类
 * 支持嵌套(级联)属性映射
 */
public class JSONToEntityUtil implements Constants{
	
	/**
	 * logger
	 */
	private static final Log log = LogFactory.getLog(JSONToEntityUtil.class);
	
	/**
	 * 私有构造
	 */
	private JSONToEntityUtil(){
		
	}
	
	/**
	 * 通过JSONObject获取实体对象,
	 * 其实是可以通过JSONObject.toBean()来获得,
	 * 但实体内部如有Collection存在,则实体无法自动转型为正确的泛型类型,
	 * 所以重新实现通过JSON获取实体对象。
	 * 
	 * 实体属性不支持char
	 * 实体中如果有Character或char类型,请用String代替
	 * 
	 * @param clazz 要获得的实体对象类模板
	 * @param result JSONObject
	 * @return 实体对象
	 */
	public static <T> T getEntity(Class<T> clazz,JSONObject result){
		T t = null;
		if(null == result || result.isNullObject()){
			return t;
		}
		try {
			t = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				if(IGNORE_FIELD.equals(f.getName())){//过滤掉序列化字符
					continue;
				}
				setField(f,result,t);//将JSON中对应属性的值通过属性set方法设置给t
			}
		} catch (Exception e) {
			log.error(e.toString(),e);
		} 
		return t;
	}
	
	/**
	 * 将JSONObject中对应属性的值通过属性set方法设置到实例中
	 * @param field 对应属性
	 * @param result JSON
	 * @param t 需要生产的实例类
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private static void setField(Field field,JSONObject result,Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String fName = field.getName();//属性名
		Class<?> clz = field.getType();//属性类型
		Method method = obj.getClass().getMethod(ClassTypeUtil.buildSetMethodName(fName), clz);//获取属性set方法
		Object value = getEntity(clz, result, fName);
		method.invoke(obj, value);
	}
	
	@SuppressWarnings({ "unchecked", "static-access", "rawtypes" })
	private static <T> T getEntity(Class<?> type,JSONObject result,String key){
		/*
		 * 设置具体类型属性值
		 */
		if(checkFieldName(type.getName(),STRING,result,key)){//String
			return (T) result.getString(key);
		}else if(checkFieldName(type.getName(),I,result,key)){//Integer||int
			return (T) Integer.valueOf(result.getInt(key));
		}else if(checkFieldName(type.getName(),L,result,key)){//Long||long
			return (T) Long.valueOf(result.getLong(key));
		}else if(checkFieldName(type.getName(),S,result,key)){//Short||short
			return (T) Short.valueOf(result.getString(key));
		}else if(checkFieldName(type.getName(),D,result,key)){//Double||double
			return (T) Double.valueOf(result.getDouble(key));
		}else if(checkFieldName(type.getName(),F,result,key)){//Float||float
			return (T) Float.valueOf(result.getString(key));
		}else if(checkFieldName(type.getName(),BL,result,key)){//Boolean||boolean
			return (T) Boolean.valueOf(result.getBoolean(key));
		}else if(checkFieldName(type.getName(),B,result,key)){//Byte||byte
			return (T) Byte.valueOf(result.getString(key));
		}else if(checkFieldName(type.getName(),DATE,result,key)){//java.util.Date
			return (T) result.toBean(result.getJSONObject(key), Date.class);
		}else if(checkFieldName(type.getName(),LIST,result,key)){//List
			return (T) JSONArray.toCollection(result.getJSONArray(key));
		}else if(checkFieldName(type.getName(),MAP,result,key)){//Map
			Map map = new HashMap();
			JSONObject jObj = result.getJSONObject(key);
			Set<String> keys = jObj.keySet();
			for (String s : keys) {
				map.put(s, jObj.get(s));
			}
			return (T) map;
		}else if(type.isArray()){
//			JSONArray jArray = result.getJSONArray(fName);
//			method.invoke(obj, jArray.toArray(jArray, clz.getComponentType()));
			return (T) JSONArray.toArray(result.getJSONArray(key), type.getComponentType());
		}else{//Object
			return (T) getEntity(type, result.getJSONObject(key));
		}
	}
	
	/**
	 * 检测属性类型
	 * @param type 属性类型类型
	 * @param fieldType 定义属性
	 * @param result JSON
	 * @param fName 属性名称
	 * @return Boolean
	 */
	private static boolean checkFieldName(String type,String fieldType,JSONObject result,String fName){
		if(INT.equals(type)){
			type = I;
		}
		if(type.contains(ARRAY_FIX)&& !fieldType.contains(ARRAY_FIX)){
			return false;
		}
		return type.toLowerCase().contains(fieldType.toLowerCase()) && result.containsKey(fName);
	}
}
