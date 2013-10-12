package com.wprojectframework.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

/**
 * 
 * @class JMSAbstractReceiver.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * JMS抽象接收器
 * 该类提供工公用行为方法
 */
public abstract class JMSAbstractReceiver extends JMSAbstractTemplate  implements Receiver{
	
	/**
	 * 默认线程休眠1000ms
	 */
	public static final long DEFAULT_RECEIVE_TIMEOUT = 1000;

	/**
	 * 获取消费者,由各子类重写实现，
	 * 再由抽象父类回调使用
	 * @param destName
	 * @return
	 * @throws JMSException
	 */
	protected abstract MessageConsumer getConsumer();
	
	/**
	 * 接收消息 
	 * @param consumer
	 * @return
	 * @throws JMSException
	 */
	protected Message receive() throws JMSException{
		return receive(DEFAULT_RECEIVE_TIMEOUT);
	}
	
	/**
	 * 接收消息 
	 * @param consumer
	 * @return
	 * @throws JMSException
	 */
	protected Message receive(long timeout) throws JMSException{
		MessageConsumer consumer = getConsumer();
		if(consumer == null){
			logger.error("MessageConsumer is null",new NullPointerException());
			return null;
		}
		Message message = consumer.receive(timeout);
		logger.info("received message success...");
		return message;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.Receiver#listenMessage(javax.jms.MessageListener)
	 */
	@Override
	public void listenMessage(MessageListener listener){
		MessageConsumer consumer = getConsumer();
		try {
			consumer.setMessageListener(listener);
		} catch (JMSException e) {
			logger.error("consumer set MessageListener error:"+e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.Receiver#receiveMessage()
	 */
	@Override
	public Message receiveMessage() {
		Message message = null;
		try {
		    message = receive();
		} catch (JMSException e) {
			logger.error("Receiving JMS topic message error...",e);
		}
		return message;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.Receiver#receiveMessage(String destination)
	 */
	@Override
	public Message receiveMessage(String destination){
		this.destination = destination;
		return receiveMessage();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.Receiver#listenMessage(String destination,MessageListener listener)
	 */
	@Override
	public void listenMessage(String destination,MessageListener listener){
		this.destination = destination;
		listenMessage(listener);
	}
}
