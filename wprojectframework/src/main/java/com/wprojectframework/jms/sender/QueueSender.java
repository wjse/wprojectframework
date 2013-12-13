package com.wprojectframework.jms.sender;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;


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
	 * @see com.stee.dsms.jms.JMSAbstractSender#getProducer()
	 */
	@Override
	protected MessageProducer getProducer() throws JMSException {
		if(this.destination == null){
			return null;
		}
		Queue queue = getSession().createQueue(this.destination);
		return getSession().createProducer(queue);
	}
}

