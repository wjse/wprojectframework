package com.wprojectframework.jms.impl;

import java.io.Serializable;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import com.wprojectframework.jms.JMSAbstractSender;
import com.wprojectframework.jms.Sender;


/**
 * 
 * @class QueueSender.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 队列消息发送器
 */
public class QueueSender extends JMSAbstractSender implements Sender{
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Sender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String destName,String str){
		try {
			this.destName = destName;
			send(getTextMessage(str));
		} catch (JMSException e) {
			logger.error("Sending JMS queue text message error...",e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Sender#send(java.lang.String, java.io.Serializable)
	 */
	@Override
	public void send(String destName,Serializable obj){
		try {
			this.destName = destName;
			send(getObjectMessage(obj));
		} catch (JMSException e) {
			logger.error("Sending JMS queue serializable message error...",e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Sender#send(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void send(String destName,Map map){
		try {
			this.destName = destName;
			send(getMapMessage(map));
		} catch (JMSException e) {
			logger.error("Sending JMS queue map message error...",e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Sender#send(java.lang.String)
	 */
	@Override
	public void send(String str) {
		try {
			send(getTextMessage(str));
		} catch (JMSException e) {
			logger.error("Sending JMS queue message error...",e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.JMSAbstractSender#getProducer()
	 */
	@Override
	protected MessageProducer getProducer() throws JMSException {
		Queue queue = jmsConnectionFactory.getSession().createQueue(destName);
		return jmsConnectionFactory.getSession().createProducer(queue);
	}
}

