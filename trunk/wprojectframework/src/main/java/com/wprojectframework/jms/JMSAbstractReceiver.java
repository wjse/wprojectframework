package com.wprojectframework.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

/**
 * 自定义JMS抽象接收器
 * 该类提供工公用行为方法
 * @author lenovo
 *
 */
public abstract class JMSAbstractReceiver extends JMSAbstractTemplate{
	
	/**
	 * 默认线程休眠1000ms
	 */
	public static final long DEFAULT_RECEIVE_TIMEOUT = 1000;
	
	/**
	 * 目标名称,由子类设置,如果为空则不适用该值。
	 * 说明目标由spring注入
	 */
	protected String destName;

	protected JMSAbstractReceiver(){
		
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
			consumer = jmsConnectionFactory.getSession().createConsumer(jmsConnectionFactory.getDestination());
		}
		Message message = consumer.receive(timeout);
		logger.info("received message success...");
		return message;
	}
	
	/**
	 * 监听消息
	 * @param listener
	 * @throws JMSException
	 */
	protected void listenMessage(MessageListener listener) throws JMSException{
		MessageConsumer consumer = getConsumer();
		consumer.setMessageListener(listener);
	}
}
