package com.wprojectframework.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.log4j.Logger;
import org.springframework.jms.connection.SingleConnectionFactory;

/**
 * 
 * @class JMSConnectionFactory.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * JMS链接工厂，该类使用javax.jms API，具体实现
 * 由spring注入，目前版本是基于activemq，后续如果使用其他厂商实现
 * 该类可能会作修改
 */
public class JMSConnectionFactory {
	
	/**
	 * JMS 链接工厂
	 */
	private ConnectionFactory connectionFactory;
    
	/**
	 * 链接对象
	 */
	private Connection connection;
    
    /**
     * 会话对象
     */
    private Session session;
    
    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(JMSConnectionFactory.class);
    
    /**
     * 目标，目前版本只支持单目标于测试
     */
    private Destination destination;
    
    private JMSType jmsType;
    
    public JMSType getJmsType() {
		return jmsType;
	}

	public void setJmsType(JMSType jmsType) {
		this.jmsType = jmsType;
	}

	/**
	 * @return the destination
	 */
	public Destination getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	/**
	 * @return the connectionFactory
	 */
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/**
	 * @param connectionFactory the connectionFactory to set
	 */
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	/**
	 * 初始化connection
	 * 初始化session
	 * session默认为非事务性的
	 */
	public void init(){
    		try {
				connection = connectionFactory.createConnection();
				connection.start();
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				logger.info("JMS connect success...");
				initJmsType();
			} catch (JMSException e) {
				logger.error("JMSConnectionFactory init error...",e);
			}
    }
	
	private void initJmsType(){
		String clzName = connectionFactory.getClass().getName().toLowerCase();
		if(connectionFactory instanceof SingleConnectionFactory){
			SingleConnectionFactory sFactory = (SingleConnectionFactory) connectionFactory;
			clzName = sFactory.getTargetConnectionFactory().getClass().getName().toLowerCase();
		}else{
			clzName = connectionFactory.getClass().getName().toLowerCase();
		}
		if(clzName.contains(JMSType.ActiveMQ.toString().toLowerCase())){
			this.jmsType = JMSType.ActiveMQ;
		}else if(clzName.contains(JMSType.Weblogic.toString().toLowerCase())){
			this.jmsType = JMSType.Weblogic;
		}else if(clzName.contains(JMSType.WebsphereMQ.toString().toLowerCase())){
			this.jmsType = JMSType.WebsphereMQ;
		}else if(clzName.contains(JMSType.JBoss.toString().toLowerCase())){
			this.jmsType = JMSType.JBoss;
		}
		logger.info("the jms connection factory type is " +jmsType.toString());
	}
	
	/**
	 * 对外获取session
	 * @return
	 */
	public Session getSession(){
		if(session == null){
			init();
		}
		return session;
	}
	
	/**
	 * 关闭链接
	 */
	public void close(){
		try {
			if(session != null){
				session.close();
			}
			if(connection != null){
				connection.stop();
				connection.close();
			}
			logger.info("JMS connect closed...");
		} catch (JMSException e) {
			logger.error(e);
		}finally{
			session = null;
			connection = null;
		}
	}
	
	public enum JMSType{
		ActiveMQ,
		Weblogic,
		WebsphereMQ,
		JBoss
	}
}

