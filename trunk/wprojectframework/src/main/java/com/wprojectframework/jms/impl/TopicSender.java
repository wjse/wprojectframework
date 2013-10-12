package com.wprojectframework.jms.impl;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import com.wprojectframework.jms.JMSAbstractSender;

/**
 * 
 * @class TopicSender.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 主题发送器
 */
public class TopicSender extends JMSAbstractSender{
	
	
	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.JMSAbstractSender#getProducer()
	 */
	@Override
	protected MessageProducer getProducer() throws JMSException {
		if(this.destination == null){
			return null;
		}
		Topic topic = jmsConnectionFactory.getSession().createTopic(this.destination);
		return jmsConnectionFactory.getSession().createProducer(topic);
	}
}
