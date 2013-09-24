package com.wprojectframework.cache.annotation;

/**
 * 缓存类型
 * @class CacheType.java
 * @author wujia
 * @date 2013-9-24
 * @version v1.0
 * @todo
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
