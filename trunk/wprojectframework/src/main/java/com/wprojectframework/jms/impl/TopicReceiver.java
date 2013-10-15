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
	protected MessageConsumer getConsumer() throws JMSException{
		if(this.destination == null){
			return null;
		}
		Topic topic = getSession().createTopic(this.destination);
		return getSession().createConsumer(topic);
	}
}
