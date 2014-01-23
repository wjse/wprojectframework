package com.wprojectframework.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.log4j.Logger;

/**
 * 
 * <pre>
 * JMS链接工厂，该类使用javax.jms API，具体实现
 * 由spring注入各实现连接工厂
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     ConnectionFactory
 * @since   JDK1.6
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
     * JMS厂商类型
     */
    private JMSType jmsType;
    
    /**
     * @return jmsType
     */
    public JMSType getJmsType() {
		return jmsType;
	}

    /**
     * 
     * @param jmsType the jmsType to set
     */
	public void setJmsType(JMSType jmsType) {
		this.jmsType = jmsType;
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
				checkJmsType();
			} catch (JMSException e) {
				logger.error("JMSConnectionFactory init error...",e);
			}
    }
	
	/**
	 * 检查JMS厂商类型
	 */
	private void checkJmsType(){
		String clzName = connectionFactory.getClass().getName().toLowerCase();
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
	
	/**
	 * 厂商类型枚举
	 * @class JMSConnectionFactory.java
	 * @author wujia
	 * @date 2013年12月13日
	 * @version v1.0
	 * @todo
	 */
	public enum JMSType{
		ActiveMQ,
		Weblogic,
		WebsphereMQ,
		JBoss
	}
}

