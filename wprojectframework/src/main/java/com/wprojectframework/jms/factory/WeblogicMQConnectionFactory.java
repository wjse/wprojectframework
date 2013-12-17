package com.wprojectframework.jms.factory;

import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.NamingException;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

/**
 * 
 * @class WeblogicMQConnectionFactory.java
 * @author wujia
 * @date 2013年12月13日
 * @version v1.0
 * @todo
 * Weblogic JMS链接工厂,weblogic通过jndi链接JMS服务器
 * 所以主要聚合了spring jndi模板类进行链接
 */
public class WeblogicMQConnectionFactory extends AbstractConnectionFactory implements ConnectionFactory{
	
	/**
	 * jndi 链接工厂类名,默认weblogic链接工厂
	 */
	private String namingFactory = "weblogic.jndi.WLInitialContextFactory";
	
	/**
	 * weblogic 服务器用户名 
	 */
	private String username;
	
	/**
	 * weblogic 服务器密码
	 */
	private String password;
	
	/**
	 * weblogic jms链接工厂jndi名称
	 */
	private String jndiName;
	
	/**
	 * spring jndiTemplate
	 */
	private JndiTemplate jndiTemplate;
	
	/**
	 * spring jndiObjectFactory
	 */
	private JndiObjectFactoryBean jndiObjectFactory;
	
	/**
	 * 链接类名jndi key
	 */
	private static final String NAMING_FACTORY = "java.naming.factory.initial";
	
	/**
	 * url jndi key
	 */
	private static final String URL = "java.naming.provider.url";
	
	/**
	 * 用户名 jndi key
	 */
	private static final String USERNAME = "java.naming.security.principal";
	
	/**
	 * 密码 jndi key
	 */
	private static final String PASSWORD = "java.naming.security.credentials";
	
	
	
	/**
	 * @return the jndiName
	 */
	public String getJndiName() {
		return jndiName;
	}
	/**
	 * @param jndiName the jndiName to set
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}
	/**
	 * @return the namingFactory
	 */
	public String getNamingFactory() {
		return namingFactory;
	}
	/**
	 * @param namingFactory the namingFactory to set
	 */
	public void setNamingFactory(String namingFactory) {
		this.namingFactory = namingFactory;
	}
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.jms.ConnectionFactory#createConnection()
	 */
	@Override
	public Connection createConnection() throws JMSException {
		setProperties();
		ConnectionFactory cf = (ConnectionFactory) jndiObjectFactory.getObject();
		return cf.createConnection();
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.jms.ConnectionFactory#createConnection(java.lang.String, java.lang.String)
	 */
	@Override
	public Connection createConnection(String arg0, String arg1)
			throws JMSException {
		throw new javax.jms.IllegalStateException(
				"WeblogicMQConnectionFactory does not support custom username and password");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.factory.AbstractConnectionFactory#setProperties()
	 */
	@Override
	protected void setProperties(){
		setJndiProperties();
		setJndiObjectFactoryProperties();
	}
	
	/**
	 * 将环境参数设置到spring jndiTemplate
	 */
	private void setJndiProperties(){
		jndiTemplate = new JndiTemplate();
		Properties prop = new Properties();
		prop.put(NAMING_FACTORY, namingFactory);
		prop.put(URL, getUrl());
		prop.put(USERNAME, username);
		prop.put(PASSWORD, password);
		jndiTemplate.setEnvironment(prop);
	}
	
	/**
	 * 初始化spring jndiObjectFactory
	 */
	private void setJndiObjectFactoryProperties(){
		jndiObjectFactory = new JndiObjectFactoryBean();
		jndiObjectFactory.setJndiName(jndiName);
		jndiObjectFactory.setJndiTemplate(jndiTemplate);
		try {
			jndiObjectFactory.afterPropertiesSet();
		} catch (IllegalArgumentException e) {
			logger.error(e);
		} catch (NamingException e) {
			logger.error(e);
		}
	}
	
	/**
	 * 拼装链接路径，需要带有tcpip协议
	 * @return
	 */
	private String getUrl(){
		if(!host.startsWith("t3://")){
			host = "t3://"+host;
		}
		return host+":"+port;
	}
}
