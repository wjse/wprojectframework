package com.wprojectframework.jms.factory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

/**
 * 
 * <pre>
 * IBM Websphere MQ链接工厂
 * 整合IBM com.ibm.msg.client.jms.JmsConnectionFactory;
 * 根据IBM API需要设置队列管理器名称,通道名称.
 * 客户端ID为可选参数
 * 默认链接到Websphere MQ为客户端链接模式,默认设置其字符编码集为UTF-8(1381) 
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     AbstractConnectionFactory
 * @see     ConnectionFactory
 * @see     JmsConnectionFactory
 * @see     WMQConstants
 * @since   JDK1.6
 */
public class WebsphereMQConnectionFactory extends AbstractConnectionFactory implements ConnectionFactory{
	
	/**
	 * IBM 链接工厂实现
	 */
	private JmsConnectionFactory cf;
	
	/**
	 * 队列管理器名称
	 */
	private String queueManagerName;
	
	/**
	 * 服务器链接通道名称
	 */
	private String channelName;
	
	/**
	 * 客户端ID
	 */
	private String clientId;
	
	/**
	 * 字符编码代码,默认UTF-8(1381)
	 */
	private int ccsid = 1381;
	
	
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * @return the queueManagerName
	 */
	public String getQueueManagerName() {
		return queueManagerName;
	}

	/**
	 * @param queueManagerName the queueManagerName to set
	 */
	public void setQueueManagerName(String queueManagerName) {
		this.queueManagerName = queueManagerName;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the ccsid
	 */
	public int getCcsid() {
		return ccsid;
	}

	/**
	 * @param ccsid the ccsid to set
	 */
	public void setCcsid(int ccsid) {
		this.ccsid = ccsid;
	}

	/**
	 * 构造时初始化IBM链接工厂
	 */
	public WebsphereMQConnectionFactory(){
		try {
			JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
			cf = ff.createConnectionFactory();
		} catch (JMSException e) {
			logger.error("create websphere MQ JmsConnectionFactory error.",e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.jms.ConnectionFactory#createConnection()
	 */
	@Override
	public Connection createConnection() throws JMSException {
		setProperties();
		return cf.createConnection();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.jms.ConnectionFactory#createConnection(java.lang.String, java.lang.String)
	 */
	@Override
	public Connection createConnection(String arg0, String arg1)
			throws JMSException {
		setProperties();
		return cf.createConnection(arg0, arg1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.factory.AbstractConnectionFactory#setProperties()
	 */
	@Override
	protected void setProperties(){
		try {
			cf.setStringProperty(WMQConstants.CLIENT_ID, clientId);
			cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, host);
			cf.setIntProperty(WMQConstants.WMQ_PORT, port);
			cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManagerName);
			cf.setStringProperty(WMQConstants.WMQ_CHANNEL, channelName);
			cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
			cf.setIntProperty(WMQConstants.WMQ_CCSID, ccsid);
		} catch (JMSException e) {
			logger.error("set websphere MQ properties error.",e);
		}
	}

}
