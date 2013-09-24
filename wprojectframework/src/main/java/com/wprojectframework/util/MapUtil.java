package com.wprojectframework.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sf.cglib.beans.BeanMap;
import org.apache.log4j.Logger;
import com.wprojectframework.core.Constants;




/**
 * Map自定义扩展支持工具类
 * @author WuJia
 * @date 2012-9-25
 * @version since v1.0
 * @todo 用于将Object属性以属性名=属性值的形式存入Map
 */
public class MapUtil implements Constants{
	
	private static Logger logger = Logger.getLogger(MapUtil.class);
	
	/**
	 * 私有构造
	 */
	private MapUtil(){
		
	}
	
	/**
	 * 将Object转换为Map
	 * Object中的属性将以key=value形式存在
	 * @param obj 要转换的Object
	 * @return Map<String,Object>
	 */
	public static Map<String,Object> convertByObject(Object obj){
		Map<String,Object> map = new HashMap<String, Object>();
		if(null == obj){
			return map;
		}
		Field[] fields = ClassTypeUtil.getDeclaredFields(obj);
		if(null != fields && 0 != fields.length){
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				f.setAccessible(true);
				try {
					if(null != f.get(obj) && !f.getName().contains(IGNORE_FIELD)){
						map.put(f.getName(), f.get(obj));
					}
				} catch (IllegalArgumentException e) {
					logger.error(e);
				} catch (IllegalAccessException e) {
					logger.error(e);
				}
			}
		}
		return map;
	}
	
	/**
	 * 将List里的对象转换成Map, map的key为list对象里pro属性的值,value为list里的value属性
	 * @param col 要转换的集合
	 * @param propertyName 集合里对象的某个属性
	 * @param valueName Map的value所装的属性
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public static <K,E> Map<K,E> convertByList(Collection<E> col,String propertyName, String valueName){
		Map<K,E> map = new HashMap<K, E>();
		if(null == col){
			return map;
		}
		for (E obj : col) {
			BeanMap bMap = BeanMap.create(obj);
			K value = (K) bMap.get(propertyName);
			map.put(value, (E) bMap.get(valueName));
		}
		return map;
	}
}
