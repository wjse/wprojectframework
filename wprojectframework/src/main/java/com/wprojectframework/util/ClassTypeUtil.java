package com.wprojectframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.util.Assert;

/**
 * 类模板工具类
 * @author lenovo
 *
 */
public class ClassTypeUtil {
	
	/**
	 * 私有构造
	 */
    private ClassTypeUtil(){
    	
    }
    
    /**
     * 获取子类泛型类模板
     * @param <E>
     * @param clazz 类模板
     * @param i 泛型下标
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <E> Class<E> getGenericSuperclass(Class<?> clazz,int i){
    	Type type = clazz.getGenericSuperclass();
		if(type instanceof ParameterizedType){
			Type[] p = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<E>) p[0];
		}
		return null;
    }
    
    /**
     * 获取方法参数泛型类型
     * @param <E>
     * @param method 方法
     * @param argNum 参数下标
     * @param i 参数泛型下标
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <E> Class<E> getGenericSuperclass(Method method,int argNum,int i){
    	Type type = method.getGenericParameterTypes()[argNum];
		if(type instanceof ParameterizedType){
			Type[] p = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<E>) p[i];
		}
		return null;
    }
    
	/**
	 * 根据属性名构建set方法
	 * @param fieldName
	 * @return
	 */
	public static String buildMethodName(String fieldName){
		StringBuilder builder = new StringBuilder();
		builder.append("set");
		builder.append(fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)), String.valueOf(fieldName.charAt(0)).toUpperCase()));
		return builder.toString();
	}
	
	/**
	 * 获取Declared属性数组
	 * @param obj
	 * @return
	 */
	public static Field[] getDeclaredFields(Object obj){
		Assert.notNull(obj, "The Object is cannot be null.");
		return obj.getClass().getDeclaredFields();
	}
	
	/**
	 * 获取属性数组
	 * @param obj
	 * @return
	 */
	public static Field[] getFields(Object obj){
		Assert.notNull(obj, "The Object is cannot be null.");
		return obj.getClass().getFields();
	}
	
	/**
	 * 获取类模板类型名称
	 * 这里主要是获取截取后的属性(Field)的简单类型名称
	 * 截取规则是截取类全名最后一个"."以后的字符串
	 * @param clz
	 * @return
	 */
	public static String getClassType(Class<?> clz){
		String type = String.valueOf(clz);
		if(type.contains(".")){
			type = type.substring(type.lastIndexOf(".")+1);
		}
		return type;
	}
}
