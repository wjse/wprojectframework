package com.wprojectframework.jms.sender;
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

import com.wprojectframework.jms.JMSAbstractTemplate;

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
	 * 消息持久化,默认为非持久
	 * 可通过IOC设置起为持久消息
	 * 持久消息值为2
	 */
	private int messagePersistent = DeliveryMode.NON_PERSISTENT;
	
	/**
	 * @return the messagePersistent
	 */
	public int getMessagePersistent() {
		return messagePersistent;
	}

	/**
	 * @param messagePersistent the messagePersistent to set
	 */
	public void setMessagePersistent(int messagePersistent) {
		this.messagePersistent = messagePersistent;
	}

	/**
	 * 获取文本消息对象
	 * @return
	 * @throws JMSException
	 */
	protected TextMessage getTextMessage() {
		return getTextMessage(null);
	}
	
	/**
	 * 重载获取文本消息对象
	 * @param str
	 * @return
	 * @throws JMSException
	 */
	protected TextMessage getTextMessage(String str) {
		try {
			return getSession().createTextMessage(str);
		} catch (JMSException e) {
			logger.error("get Text message error.",e);
		}
		return null;
	}
	
	/**
	 * 获取序列化消息对象
	 * @return
	 * @throws JMSException
	 */
	protected ObjectMessage getObjectMessage() {
		return getObjectMessage(null);
	}
	
	/**
	 * 重载获取序列化消息对象
	 * @param arg
	 * @return
	 * @throws JMSException
	 */
	protected ObjectMessage getObjectMessage(Serializable arg) {
		try {
			return getSession().createObjectMessage(arg);
		} catch (JMSException e) {
			logger.error("get Object message error.",e);
		}
		return null;
	}
	
	/**
	 * 获取map消息对象
	 * @return
	 * @throws JMSException
	 */
	protected MapMessage getMapMessage() {
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
	protected MapMessage getMapMessage(Map map) {
		MapMessage message;
		try {
			message = getSession().createMapMessage();
			if(map != null){
				for (Object key : map.keySet()) {
					message.setObject(key.toString(), map.get(key));
				}
			}
			return message;
		} catch (JMSException e) {
			logger.error("get Map message error.", e);
		}
		return null;
	} 
	
	/**
	 * 生产者发送消息后关闭对象
	 * @param producer
	 * @param message
	 * @throws JMSException
	 */
	private void send(Message message){
		try {
			MessageProducer producer = getProducer();
			if(null == producer){
				logger.error("MessageProducer is null",new NullPointerException());
				return;
			}
			producer.setDeliveryMode(messagePersistent);
			if(producer instanceof TopicPublisher){
				TopicPublisher publisher = (TopicPublisher) producer;
				publisher.publish(message);
			}else{
				producer.send(message);
			}
			logger.info("Sent message success to "+producer.getDestination());
			if(logger.isDebugEnabled()){
				logger.debug("message is : " +message);
			}
			producer.close();
		} catch (JMSException e) {
			logger.error("Sending JMS message error.Session is rollback.",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String destination, String str) {
		this.destination = destination;
		send(getTextMessage(str));
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String, java.io.Serializable)
	 */
	@Override
	public void send(String destination, Serializable ser) {
		this.destination = destination;
		send(getObjectMessage(ser));
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void send(String destination, Map map) {
		this.destination = destination;
		send(getMapMessage(map));
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.lang.String)
	 */
	@Override
	public void send(String str) {
		send(getTextMessage(str));
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.io.Serializable)
	 */
	@Override
	public void send(Serializable ser) {
		send(getObjectMessage(ser));
	}

	/* (non-Javadoc)
	 * @see com.wprojectframework.jms.Sender#send(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void send(Map map) {
		send(getMapMessage(map));
	}
}
