package com.wprojectframework.jms.sender;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;


/**
 * 
 * <pre>
 * 队列消息发送器
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     JMSAbstractSender
 * @see     Sender
 * @since   JDK1.6
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

