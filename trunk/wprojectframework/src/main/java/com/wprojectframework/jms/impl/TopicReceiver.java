package com.wprojectframework.jms.impl;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Topic;
import com.wprojectframework.jms.JMSAbstractReceiver;

/**
 * 
 * @class TopicReceiver.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 主题消息接收器
 */
public class TopicReceiver extends JMSAbstractReceiver{

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.JMSAbstractReceiver#getConsumer()
	 */
	@Override
	protected MessageConsumer getConsumer(){
		if(this.destination == null){
			return null;
		}
		try {
			Topic topic = jmsConnectionFactory.getSession().createTopic(this.destination);
			return jmsConnectionFactory.getSession().createConsumer(topic);
		} catch (JMSException e) {
			logger.error("createConsumer error : "+e);
		}
		return null;
	}
}
