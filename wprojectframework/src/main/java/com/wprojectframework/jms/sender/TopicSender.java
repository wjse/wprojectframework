package com.wprojectframework.jms.sender;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Topic;

/**
 * 
 * <pre>主题发送器</pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     JMSAbstractSender
 * @since   JDK1.6
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
		Topic topic = getSession().createTopic(this.destination);
		return getSession().createProducer(topic);
	}
}
