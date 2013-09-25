package com.wprojectframework.jms;

import org.apache.log4j.Logger;



/**
 * 
 * @class JMSAbstractTemplate.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * JMS抽象模板类，该类组合JMSConnectionFactory链接工厂，
 * 链接工厂由spring注入。
 * 该类提供生产者和消费者的简单操作，避免sender或receiver出现过多重复代码
 */
public abstract class JMSAbstractTemplate {
	
	protected JMSAbstractTemplate(){
		
	}
	
	/**
	 * 链接工厂
	 */
	protected JMSConnectionFactory jmsConnectionFactory;
	
	/**
	 * logger
	 */
	protected static final Logger logger = Logger.getLogger(JMSAbstractTemplate.class);

	/**
	 * @return the jmsConnectionFactory
	 */
	public JMSConnectionFactory getJmsConnectionFactory() {
		return jmsConnectionFactory;
	}

	/**
	 * @param jmsConnectionFactory
	 *            the jmsConnectionFactory to set
	 */
	public void setJmsConnectionFactory(JMSConnectionFactory jmsConnectionFactory) {
		this.jmsConnectionFactory = jmsConnectionFactory;
	}
	
	/**
	 * 关闭链接
	 */
	public void closeSession(){
		jmsConnectionFactory.close();
	}
	
	
}
