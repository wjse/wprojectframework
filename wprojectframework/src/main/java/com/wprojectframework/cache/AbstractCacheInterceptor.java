package com.wprojectframework.cache;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import com.wprojectframework.cache.annotation.Cache;
import com.wprojectframework.util.AopUtil;
import com.wprojectframework.util.ArrayUtil;

/**
 * 
 * <pre>缓存拦截器抽象类</pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     CacheTemplate
 * @see     Cache
 * @see     ProceedingJoinPoint
 * @see     StringUtils
 * @see     AopUtil
 * @see     ArrayUtil
 * @since   JDK1.6
 */
public abstract class AbstractCacheInterceptor {
	
	/**
	 * logger
	 */
	protected Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 缓存模板
	 */
	protected CacheTemplate cacheTemplate;
	
	/**
	 * @return the cacheTemplate
	 */
	public CacheTemplate getCacheTemplate() {
		return cacheTemplate;
	}

	/**
	 * @param cacheTemplate the cacheTemplate to set
	 */
	public void setCacheTemplate(CacheTemplate cacheTemplate) {
		this.cacheTemplate = cacheTemplate;
	}

	/**
	 * 在缓存中查询数据
	 * @param key
	 * @return Object
	 */
	protected Object getObjectByCache(String key){
		Object obj = cacheTemplate.get(key);
		if(logger.isDebugEnabled()){
			logger.debug("   get data ["+key+":"+obj+"] from "+cacheTemplate.getCacheName());
		}
		return obj;
	}
	
	/**
	 * 继续执行方法本身
	 * @param point
	 * @return Object
	 */
	protected Object proceed(ProceedingJoinPoint point){
		Object obj = null;
		try {
			obj = point.proceed();
			if(logger.isDebugEnabled()){
				logger.debug("   execute point proceed");
			}
		} catch (Throwable e) {
			logger.error(e);
		}
		return obj;
	}
	
	/**
	 * 拼装缓存键值,为了确保查询数据可能存在带条件查询键值采用
	 * 注解配置的key()值与执行方法参数的值拼接而成
	 * 拼接形式：xxxx_xx_xx
	 * @param ann
	 * @param point
	 * @return
	 */
	protected String getCacheKey(Cache ann,ProceedingJoinPoint point){
		StringBuffer sb = new StringBuffer();
		String key = ann.key();
		if(StringUtils.isEmpty(key)){//如果元注解未配置缓存的key，默认使用当前执行方法名
			key = AopUtil.getCurrentMethodName(point);
		}
		sb.append(key);
		Object[] args = AopUtil.getArgs(point);
		if(ArrayUtil.isNotEmpty(args)){
			for (Object object : args) {
				sb.append("_");
				sb.append(object);
			}
		}
		return cacheTemplate.checkKey(sb.toString());
	}
	
	/**
	 * 获得Cache元注解
	 * @param point
	 * @return
	 */
	protected Cache getCacheAnnotation(ProceedingJoinPoint point){
		return AopUtil.getCurrentMethod(point).getAnnotation(Cache.class);
	}
	
	/**
	 * 环绕通知
	 * @param point
	 * @return
	 */
	public abstract Object proxy(ProceedingJoinPoint point);
}
