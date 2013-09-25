package com.wprojectframework.jms.impl;

import java.io.Serializable;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import com.wprojectframework.jms.JMSAbstractSender;
import com.wprojectframework.jms.Sender;

/**
 * 
 * @class TopicSender.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 主题发送器
 */
public class TopicSender extends JMSAbstractSender implements Sender{
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Sender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String destName, String str) {
		try {
			this.destName = destName;
			send(getTextMessage(str));
		} catch (JMSException e) {
			logger.error("Sending JMS topic text message error...",e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Sender#send(java.lang.String, java.io.Serializable)
	 */
	@Override
	public void send(String destName, Serializable ser) {
		try {
			this.destName = destName;
			send(getObjectMessage(ser));
		} catch (JMSException e) {
			logger.error("Sending JMS topic serializable message error...",e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Sender#send(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void send(String destName, Map map) {
		try {
			this.destName = destName;
			send(getMapMessage(map));
		} catch (JMSException e) {
			logger.error("Sending JMS topic map message error...",e);
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
			logger.error("Sending JMS topic message error...",e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.JMSAbstractSender#getProducer()
	 */
	@Override
	protected MessageProducer getProducer() throws JMSException {
		if(this.destName == null){
			return null;
		}
		Topic topic = jmsConnectionFactory.getSession().createTopic(this.destName);
		return jmsConnectionFactory.getSession().createProducer(topic);
	}
}
