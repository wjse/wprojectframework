package com.wprojectframework.jms.factory;

import org.apache.log4j.Logger;

/**
 * @class AbstractConnectionFactory.java
 * @author wujia
 * @date 2013年12月13日
 * @version v1.0
 * @todo
 * 自定义JMS链接工厂实现抽象类
 * 抽象定义了公共设置环境参数行为
 * 以及链接路径参数
 */
public abstract class AbstractConnectionFactory {
	
	/**
	 * host
	 */
	protected String host;
	
	/**
	 * port
	 */
	protected int port;
	
	/**
	 * logger
	 */
	Logger logger = Logger.getLogger(getClass());
	
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * 设置环境参数抽象方法
	 * 根据不同厂商实现子类自定义环境参数设置
	 */
	protected abstract void setProperties();
}
