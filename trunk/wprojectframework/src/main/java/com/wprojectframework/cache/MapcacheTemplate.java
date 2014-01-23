package com.wprojectframework.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <pre>内存缓存模板</pre>
 * 使用HashMap作为缓存载体
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     CacheTemplate
 * @see     HashMap
 * @since   JDK1.6
 */
public class MapcacheTemplate implements CacheTemplate{
	
	/**
	 * Map cache
	 */
	private static Map<String,Object> cache = new HashMap<String, Object>();

	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.cache.CacheTemplate#getCacheName()
	 */
	@Override
	public String getCacheName() {
		return "MapcacheTemplate:"+cache.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.cache.CacheTemplate#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public void put(String key, Object value) {
		cache.put(key, value);
	}

	@Override
	public void put(String key, Object value, int secnds) {
		put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.cache.CacheTemplate#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return cache.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.cache.CacheTemplate#remove(java.lang.String)
	 */
	@Override
	public void remove(String key) {
		cache.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.cache.CacheTemplate#removeAll()
	 */
	@Override
	public void removeAll() {
		Set<String> keys = cache.keySet();
		for(String key : keys){
			remove(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.cache.CacheTemplate#checkKey(java.lang.String)
	 */
	@Override
	public String checkKey(String key) {
		return key.replaceAll(" ", "");
	}
}
