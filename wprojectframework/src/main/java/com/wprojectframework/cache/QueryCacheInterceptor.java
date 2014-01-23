package com.wprojectframework.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import com.wprojectframework.cache.annotation.Cache;
import com.wprojectframework.cache.annotation.CacheType;

/**
 * 
 * <pre>
 * 查询数据缓存拦截器，基于AOP通过代理模式处理数据是从缓存中取出还是从存储中取出,
 * 该拦截器扫描方法是否存在@QueryCache注解，
 * 如存在在通过@QueryCache中的key值去Cache取得数据
 * 如果缓存里数据不存在，则执行方法本身
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     AbstractCacheInterceptor
 * @see     ProceedingJoinPoint
 * @see     Cache
 * @see     CacheType
 * @since   JDK1.6
 */
public class QueryCacheInterceptor extends AbstractCacheInterceptor{
	
	/**
	 * 缓存为查询类型
	 */
	private static final CacheType CACHE_TYPE = CacheType.QUERY;
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.AbstractCacheInterceptor#proxy(org.aspectj.lang.ProceedingJoinPoint)
	 */
	@Override
	public Object proxy(ProceedingJoinPoint point){
		Cache ann = getCacheAnnotation(point);
		if(null == ann || ann.type() != CACHE_TYPE){//如果Cache不存在，或Cache不是查询类型，直接执行方法本身
			return proceed(point);
		}
		try {
			String key = getCacheKey(ann, point);//获取注解key()
			Object obj = getObjectByCache(key);//从缓存中获取数据
			if(null == obj){//如果从缓存中获取的数据不存在则执行方法本身并将执行后的结果放入缓存中
				int cacheTime = ann.time();//获取缓存持续时间
				obj = proceedAndCache(point,key,cacheTime);
			}
			return obj;
		} catch (Exception e) {
			logger.error(e);
			return proceed(point);//出现异常一般都是缓存服务器连接异常，所以让方法直接继续执行其本身
		}
	}
	
	/**
	 * 执行方法本身并将执行后的结果放入缓存中
	 * @param point
	 * @param key
	 * @param time
	 * @return Object
	 */
	private Object proceedAndCache(ProceedingJoinPoint point,String key,int time){
		Object obj = proceed(point);
		if(null == obj){
			return obj;
		}
		synchronized (cacheTemplate) {
			if(0 != time){
				cacheTemplate.put(key, obj, time);
			}else{//如果持续时间为0，则数据放入缓存的持续时间采用cacheTemplate默认的配置时间
				cacheTemplate.put(key, obj);
			}
			if(logger.isDebugEnabled()){
				logger.debug("   put data ["+key+":"+obj+"] in "+cacheTemplate.getCacheName());
			}
		}
		return obj;
	}
}
