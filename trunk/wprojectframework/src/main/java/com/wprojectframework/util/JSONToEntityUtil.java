package com.wprojectframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
		if(null == result || result.isEmpty()){
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
	@SuppressWarnings({ "static-access"})
	private static void setField(Field field,JSONObject result,Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String fName = field.getName();//属性名
		Class<?> clz = field.getType();//属性类型
		Method method = obj.getClass().getMethod(ClassTypeUtil.buildMethodName(fName), clz);//获取属性set方法
		String type = ClassTypeUtil.getClassType(clz).toLowerCase();
		
		/*
		 * 设置具体类型属性值
		 */
		if(checkFieldName(type,STRING,result,fName)){//String
			
			method.invoke(obj, result.getString(fName));
			
		}else if(checkFieldName(type,I,result,fName)){//Integer||int
			
			method.invoke(obj, result.getInt(fName));
			
		}else if(checkFieldName(type,L,result,fName)){//Long||long
			
			method.invoke(obj, result.getLong(fName));
			
		}else if(checkFieldName(type,S,result,fName)){//Short||short
			
			method.invoke(obj, Short.valueOf(result.getString(fName)));
			
		}else if(checkFieldName(type,D,result,fName)){//Double||double
			
			method.invoke(obj, result.getDouble(fName));
			
		}else if(checkFieldName(type,F,result,fName)){//Float||float
			
			method.invoke(obj,Float.valueOf(result.getString(fName)));
			
		}else if(checkFieldName(type,BL,result,fName)){//Boolean||boolean
			
			method.invoke(obj, result.getBoolean(fName));
			
		}else if(checkFieldName(type,B,result,fName)){//Byte||byte
			
			method.invoke(obj, Byte.valueOf(result.getString(fName)));
			
		}else if(checkFieldName(type,DATE,result,fName)){//java.util.Date
			
			method.invoke(obj, result.toBean(result.getJSONObject(fName), Date.class));
			
		}else if(checkFieldName(type,LIST,result,fName)){//List
			
			setList(result, method, fName, obj);
			
		}else if(checkFieldName(type,MAP,result,fName)){//Map
			
			setMap(result, method, fName, obj);
			
		}else if(field.getType().isArray()){//Array
			
			setArray(result, method, fName, clz, obj);
			
		}else if(result.containsKey(fName)){//Object
			
			setObject(result, method, fName, clz, obj);
			
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
		return fieldType.toLowerCase().contains(type)&& result.containsKey(fName);
	}
	
	/**
	 * 设置Map
	 * @param result JSON
	 * @param method setMethod
	 * @param fName fieldName
	 * @param obj Entity
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	private static void setMap(JSONObject result,Method method,String fName,Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		JSONObject jObj = result.getJSONObject(fName);
		if(jObj.isNullObject()){
			return;
		}
		Class<?> _clz = ClassTypeUtil.getGenericSuperclass(method,0,1);
		Iterator<String> it = jObj.keys();
		Map<String,Object> map = new HashMap<String, Object>();
		while(it.hasNext()){
			String key = it.next();
			map.put(it.next(), jObj.toBean(jObj.getJSONObject(key), _clz));
		}
		method.invoke(obj, map);
	}
	
	/**
	 * 设置Object
	 * @param result JSON
	 * @param method setMethod
	 * @param fName fieldName
	 * @param clz fieldType
	 * @param obj Entity
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static void setObject(JSONObject result,Method method,String fName,Class<?> clz,Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Object temp = getEntity(clz, result.getJSONObject(fName));
		method.invoke(obj, temp);
	}
	
	/**
	 * 设置Array
	 * @param result JSON
	 * @param method setMethod
	 * @param fName fieldName
	 * @param clz fieldType
	 * @param obj Entity
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("static-access")
	private static void setArray(JSONObject result,Method method,String fName,Class<?> clz,Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		JSONArray jArray = result.getJSONArray(fName);
		method.invoke(obj, jArray.toArray(jArray, clz.getComponentType()));
	}
	
	/**
	 * 设置List
	 * @param result JSON
	 * @param method setMethod
	 * @param fName fieldName
	 * @param obj Entity
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "static-access"})
	private static void setList(JSONObject result,Method method,String fName,Object obj) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> _clz = ClassTypeUtil.getGenericSuperclass(method,0,0);
		JSONArray jArray = result.getJSONArray(fName);
		method.invoke(obj, jArray.toCollection(jArray, _clz));
	}
}
