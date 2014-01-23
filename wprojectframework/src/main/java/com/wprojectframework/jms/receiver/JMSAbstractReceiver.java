package com.wprojectframework.jms.receiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import com.wprojectframework.jms.JMSAbstractTemplate;

/**
 * 
 * <pre>
 * JMS抽象接收器
 * 该类提供工公用行为方法
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     JMSAbstractTemplate
 * @see     Receiver
 * @since   JDK1.6
 */
public abstract class JMSAbstractReceiver extends JMSAbstractTemplate  implements Receiver{
	
	/**
	 * 默认线程休眠1000ms
	 */
	public static final long DEFAULT_RECEIVE_TIMEOUT = 1000;
	
	/**
	 * 线程休眠毫秒数,初始化为默认休眠时间
	 */
	private long timeout = DEFAULT_RECEIVE_TIMEOUT;
	
	/**
	 * @return the timeout
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	/**
	 * 获取消费者,由各子类重写实现，
	 * 再由抽象父类回调使用
	 * @param destName
	 * @return
	 * @throws JMSException
	 */
	protected abstract MessageConsumer getConsumer() throws JMSException;
	
	/**
	 * 接收消息 
	 * @param consumer
	 * @return
	 * @throws JMSException
	 */
	protected Message receive() throws JMSException{
		return receive(timeout);
	}
	
	/**
	 * 接收消息 
	 * @param consumer
	 * @return
	 * @throws JMSException
	 */
	protected Message receive(long time) throws JMSException{
		MessageConsumer consumer = getConsumer();
		if(consumer == null){
			logger.error("MessageConsumer is null",new NullPointerException());
			return null;
		}
		Message message = consumer.receive(time);
		logger.info("received message success...");
		if(logger.isDebugEnabled()){
			logger.debug("message is : " +message);
		}
		return message;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.Receiver#listenMessage(javax.jms.MessageListener)
	 */
	@Override
	public void listenMessage(MessageListener listener){
		try {
			MessageConsumer consumer = getConsumer();
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
