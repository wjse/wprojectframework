package com.wprojectframework.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import net.spy.memcached.KeyUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.MemcachedClientIF;

/**
 * 
 * @class MemcachedTemplate.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 缓存操作类,缓存服务器地址由spring注入,
 * 支持多地址集群配置
 * <pre>
 * 		<bean id="cacheTemplate" class="com.wprojectframework.cache.MemcachedTemplate">
 *	    	<property name="address">
 *	        	<list>
 *	            	<value>localhost:11211</value>
 *	        	</list>
 *	    	</property>
 *		</bean>
 * </pre>
 */
public class MemcachedTemplate implements CacheTemplate{
	
	/**
	 * 默认数据持续时间
	 */
	public static final int DEFAULT_LIFE = 600;
	
	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(MemcachedTemplate.class);
	
	/**
	 * 地址与端口号分隔符
	 */
	private static final String FIX = ":";
	
	/**
	 * Memcached地址,可传入多个地址用以集群
	 */
	private String[] address;
	
	/**
	 * 线程变量
	 */
	private ThreadLocal<MemcachedClient> cacheLocal = new ThreadLocal<MemcachedClient>();
	
	/**
	 * 初始化Memcached客户端,并将实例放入当前线程变量中
	 */
	protected void init(){
		MemcachedClient cacheClient = cacheLocal.get();
		if(null == cacheClient){
			try {
				cacheClient = initClient();
				setClient(cacheClient);
				if(logger.isDebugEnabled()){
					StringBuilder log = new StringBuilder();
					log.append("Connected Memcached success on ");
					log.append(ToStringBuilder.reflectionToString(address, ToStringStyle.SIMPLE_STYLE));
				}
			} catch (IOException e) {
				logger.error("Memcached server start error",e);
			}
		}
	}
	
	/**
	 * 实例化缓存客户端
	 * @return MemcachedClient
	 * @throws IOException
	 */
	private MemcachedClient initClient() throws IOException{
		return new MemcachedClient(getSocketAddresses());
	}
	
	/**
	 * 将配置的地址数值转换为InetSocketAddress对象数组
	 * @return
	 */
	private List<InetSocketAddress> getSocketAddresses(){
		Assert.notEmpty(address,"There is no addresses in spring config.");
		List<InetSocketAddress> _addresses = new ArrayList<InetSocketAddress>(address.length);
		for (String host : address) {
			String[] temp = host.split(FIX);
			_addresses.add(new InetSocketAddress(temp[0],Integer.parseInt(temp[1])));
		}
		return _addresses;
	}
	
	/**
	 * 获取当前线程客户端变量
	 * @return
	 */
	protected MemcachedClient getClient(){
		if(cacheLocal.get() == null){
			try {
				setClient(initClient());
			} catch (IOException e) {
				logger.error("Connected cache server error,please check the server is healthy",e);
			}
		}
		return cacheLocal.get();
	}
	
	/**
	 * 设置客户端变量到当前线程变量
	 * @param client
	 */
	protected void setClient(MemcachedClient client){
		cacheLocal.set(client);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.CacheTemplate#put(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void put(String key,Object value,int seconds){
		getClient().set(key, seconds, value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.CacheTemplate#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public void put(String key,Object value){
		put(key,value,DEFAULT_LIFE);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.CacheTemplate#get(java.lang.String)
	 */
	@Override
	public Object get(String key){
		return getClient().get(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.CacheTemplate#remove(java.lang.String)
	 */
	@Override
	public void remove(String key){
		getClient().delete(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.CacheTemplate#removeAll()
	 */
	@Override
	public void removeAll() {
		try {
			throw new NoSuchMethodException("There is no removeAll method in MemcachedTemplate.");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.CacheTemplate#getCacheName()
	 */
	@Override
	public String getCacheName() {
		return getClient().getName();
	}
	
	/**
	 * @return the address
	 */
	public String[] getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String[] address) {
		this.address = address;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.cache.CacheTemplate#validKey(java.lang.String)
	 */
	@Override
	public String checkKey(String key) {
		key = key.replaceAll(" ", "");
		byte[] keyBytes = KeyUtil.getKeyBytes(key);
		if(keyBytes.length > MemcachedClientIF.MAX_KEY_LENGTH){
			throw new IllegalArgumentException("Key is too long (maxlen = "+MemcachedClientIF.MAX_KEY_LENGTH+")");
		}
		if(keyBytes.length == 0){
			throw new IllegalArgumentException("Key must contain at least one character"); 
		}
		return key;
	}
}
