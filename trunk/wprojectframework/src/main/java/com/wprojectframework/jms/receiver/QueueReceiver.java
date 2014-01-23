package com.wprojectframework.jms.receiver;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;


/**
 * 
 * <pre>
 * 队列消息接收器
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     JMSAbstractReceiver
 * @see     Receiver
 * @since   JDK1.6
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
