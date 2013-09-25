package com.wprojectframework.jms.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
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
	 * @see com.stee.dsms.jms.Receiver#receiveMessage(java.lang.String)
	 */
	@Override
	public Message receiveMessage(String destName) {
		this.destName = destName;
		Message message = null;
		try {
			message = receive();
		} catch (JMSException e) {
			logger.error("Receiving JMS queue message error...",e);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.Receiver#listenMessage(java.lang.String, javax.jms.MessageListener)
	 */
	@Override
	public void listenMessage(String destName,MessageListener listener) {
		try {
			this.destName = destName;
			listenMessage(listener);
		} catch (JMSException e) {
			logger.error("Listenning JMS topic message error...",e);
		} 
	}

	/*
	 * (non-Javadoc)
	 * @see com.stee.dsms.jms.JMSAbstractReceiver#getConsumer()
	 */
	@Override
	protected MessageConsumer getConsumer() throws JMSException {
		if(this.destName == null){
			return null;
		}
		Queue queue = jmsConnectionFactory.getSession().createQueue(this.destName);
		return jmsConnectionFactory.getSession().createConsumer(queue);
	}
}
