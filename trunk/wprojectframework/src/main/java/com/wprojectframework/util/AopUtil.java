package com.wprojectframework.util;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.support.AopUtils;

/**
 * AOPUtil扩展工具类
 * @class AopUtil.java
 * @author wujia
 * @date 2013-9-24
 * @version v1.0
 * @todo
 */
public class AopUtil extends AopUtils{
	
	/**
	 * 获取代理对象当前参数列表
	 * @param point
	 * @return
	 */
	public static Object[] getArgs(ProceedingJoinPoint point){
		return point.getArgs();
	}
	
	/**
	 * 获取代理对象所有方法数组
	 * @param point
	 * @return Method[]
	 */
	public static Method[] getMethods(ProceedingJoinPoint point){
		return point.getTarget().getClass().getMethods();
	}
	
	/**
	 * 获取代理对象当前执行方法名称
	 * @param point
	 * @return String
	 */
	public static String getCurrentMethodName(ProceedingJoinPoint point){
		return point.getSignature().getName();
	}
	
	/**
	 * 获取代理当前执行方法
	 * @param point
	 * @return
	 */
	public static Method getCurrentMethod(ProceedingJoinPoint point){
		for (Method method : getMethods(point)) {
			if(getCurrentMethodName(point).equals(method.getName())){
				return method;
			}
		}
		return null;
	}
}
