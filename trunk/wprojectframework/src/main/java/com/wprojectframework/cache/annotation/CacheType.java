package com.wprojectframework.cache.annotation;

/**
 * 
 * <pre>缓存类型枚举</pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @since   JDK1.6
 */
public enum CacheType {
	
	/**
	 * 查询类型(用于查询)
	 */
	QUERY,
	
	/**
	 * 即时刷新类型(用于增删改)
	 */
	FLUSH
}
