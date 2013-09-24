package com.wprojectframework.util;

/**
 * 数组扩展工具类
 * @author WuJia
 * @date 2012-9-27
 * @version since v1.0
 * @todo
 */
public class ArrayUtil {
	
	/**
	 * 私有构造
	 */
	private ArrayUtil(){}
	
	/**
	 * 数组中是否包含某一特定值
	 * @param obj 是否包含值
	 * @param array 是否包含数组
	 * @return boolean
	 */
	public static <T> boolean constains(T obj,T[] array){
		if(null == array || 0 == array.length){
			return false;
		}
		for (T t : array) {
			if(obj.equals(t)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 针对String[]是否包含的比较重载
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean constains(String str , String[] array){
		if(null == array || 0 == array.length){
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if(str.contains(array[i])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 针对String[]是否包含的比较重载
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean endWith(String str , String[] array){
		if(null == array || 0 == array.length){
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if(str.endsWith(array[i])){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotEmpty(Object[] array){
		if(null == array || array.length == 0){
			return false;
		}
		return true;
	}
	
	public static boolean isEmpty(Object[] array){
		if(null == array || array.length == 0){
			return true;
		}
		return false;
	}
}
