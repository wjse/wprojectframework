package com.wprojectframework.cache;

/**
 * 缓存模板接口
 * @class CacheTemplate.java
 * @author wujia
 * @date 2013-9-18
 * @version v1.0
 * @todo
 */
public interface CacheTemplate {
	
	/**
	 * 获取缓存名称
	 * @return
	 */
	public String getCacheName();
	
	/**
	 * 将数据存放缓存中
	 * @param key
	 * @param value
	 */
	public void put(String key,Object value);
	
	/**
	 * 将数据存放缓存中，并设置有效期，单位秒
	 * @param key
	 * @param value
	 * @param secnds
	 */
	public void put(String key,Object value,int secnds);
	
	/**
	 * 从缓存中获取数据
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 从缓存中删除数据
	 * @param key
	 */
	public void remove(String key);
	
	/**
	 * 删除缓存中所有数据 
	 */
	public void removeAll();
	
	/**
	 * 检查将要使用的缓存键是否符合
	 * 缓存服务器标准
	 * @param key
	 */
	public String checkKey(String key);
	
}
