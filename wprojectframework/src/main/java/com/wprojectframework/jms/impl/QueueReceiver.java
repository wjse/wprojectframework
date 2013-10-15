package com.wprojectframework.jms.impl;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import com.wprojectframework.jms.JMSAbstractReceiver;
import com.wprojectframework.jms.Receiver;


/**
 * 
 * @class QueueReceiver.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 队列消息接收器
 */
public class QueueReceiver extends JMSAbstractReceiver implements Receiver{

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.JMSAbstractReceiver#getConsumer()
	 */
	@Override
	protected MessageConsumer getConsumer() throws JMSException {
		if(this.destination == null){
			return null;
		}
		Queue queue = getSession().createQueue(this.destination);
		return getSession().createConsumer(queue);
	}
}
