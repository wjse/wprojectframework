package com.wprojectframework.jms;
import java.io.Serializable;
import java.util.Map;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.TopicPublisher;

/**
 * 
 * @class JMSAbstractSender.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * JMS抽象发送器
 * 该类提供工公用行为方法
 */
public abstract class JMSAbstractSender extends JMSAbstractTemplate implements Sender{
	
	
	/**
	 * 获取文本消息对象
	 * @return
	 * @throws JMSException
	 */
	protected TextMessage getTextMessage() throws JMSException{
		return getTextMessage(null);
	}
	
	/**
	 * 重载获取文本消息对象
	 * @param str
	 * @return
	 * @throws JMSException
	 */
	protected TextMessage getTextMessage(String str) throws JMSException{
		return jmsConnectionFactory.getSession().createTextMessage(str);
	}
	
	/**
	 * 获取序列化消息对象
	 * @return
	 * @throws JMSException
	 */
	protected ObjectMessage getObjectMessage() throws JMSException{
		return getObjectMessage(null);
	}
	
	/**
	 * 重载获取序列化消息对象
	 * @param arg
	 * @return
	 * @throws JMSException
	 */
	protected ObjectMessage getObjectMessage(Serializable arg) throws JMSException{
		return jmsConnectionFactory.getSession().createObjectMessage(arg);
	}
	
	/**
	 * 获取map消息对象
	 * @return
	 * @throws JMSException
	 */
	protected MapMessage getMapMessage() throws JMSException{
		return getMapMessage(null);
	}
	
	/**
	 * 获取生产者对象，由各子类重写实现，
	 * 再由抽象父类回调使用
	 * @return
	 * @throws JMSException
	 */
	protected abstract MessageProducer getProducer() throws JMSException;
	
	/**
	 * 重载获取map消息对象
	 * @param map
	 * @return
	 * @throws JMSException
	 */
	@SuppressWarnings("rawtypes")
	protected MapMessage getMapMessage(Map map) throws JMSException{
		MapMessage message = jmsConnectionFactory.getSession().createMapMessage();
		if(map != null){
			for (Object key : map.keySet()) {
				message.setObject(key.toString(), map.get(key));
			}
		}
		return message;
	} 
	
	/**
	 * 生产者发送消息后关闭对象
	 * @param producer
	 * @param message
	 * @throws JMSException
	 */
	protected void send(Message message) throws JMSException{
		MessageProducer producer = getProducer();
		if(null == producer){
			logger.error("MessageProducer is null",new NullPointerException());
			return;
		}
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//设置为非持久化消息模式
		if(producer instanceof TopicPublisher){
			TopicPublisher publisher = (TopicPublisher) producer;
			publisher.publish(message);
		}else{
			producer.send(message);
		}
		logger.info("Sent message success...");
		if(logger.isDebugEnabled()){
			StringBuilder log = new StringBuilder();
			log.append("Sent message to the destination : ");
			log.append(producer.getDestination());
			logger.debug(log.toString());
		}
		producer.close();
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String destination, String str) {
		this.destination = destination;
		try {
			send(getTextMessage(str));
		} catch (JMSException e) {
			logger.error("Sending JMS text message error...",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String, java.io.Serializable)
	 */
	@Override
	public void send(String destination, Serializable ser) {
		this.destination = destination;
		try {
			send(getObjectMessage(ser));
		} catch (JMSException e) {
			logger.error("Sending JMS object message error...",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void send(String destination, Map map) {
		this.destination = destination;
		try {
			send(getMapMessage(map));
		} catch (JMSException e) {
			logger.error("Sending JMS map message error...",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String)
	 */
	@Override
	public void send(String str) {
		try {
			send(getTextMessage(str));
		} catch (JMSException e) {
			logger.error("Sending JMS text message error...",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.io.Serializable)
	 */
	@Override
	public void send(Serializable ser) {
		try {
			send(getObjectMessage(ser));
		} catch (JMSException e) {
			logger.error("Sending JMS object message error...",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void send(Map map) {
		try {
			send(getMapMessage(map));
		} catch (JMSException e) {
			logger.error("Sending JMS map message error...",e);
		}
	}
}
