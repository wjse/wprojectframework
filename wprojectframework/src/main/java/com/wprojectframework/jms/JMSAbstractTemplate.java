package com.wprojectframework.jms;

import javax.jms.Session;
import org.apache.log4j.Logger;



/**
 * 
 * <pre>
 * JMS抽象模板类，该类组合JMSConnectionFactory链接工厂，
 * 链接工厂由spring注入。
 * 该类提供生产者和消费者的简单操作，避免sender或receiver出现过多重复代码
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     Session
 * @since   JDK1.6
 */
public abstract class JMSAbstractTemplate {
	
	protected JMSAbstractTemplate(){
		
	}
	
	/**
	 * 根据业务需求使用单一目的地,由IOC注入
	 */
	protected String destination;
	
	/**
	 * 链接工厂
	 */
	private JMSConnectionFactory jmsConnectionFactory;
	
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
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * 关闭链接
	 */
	public void closeSession(){
		jmsConnectionFactory.close();
	}
	
	/**
	 * 获取会话session
	 * @return
	 */
	public Session getSession(){
		return jmsConnectionFactory.getSession();
	}
}
