package com.wprojectframework.jms.receiver;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Topic;

/**
 * 
 * <pre>
 * 主题持久化消息订阅者,subscriber可由IOC注入
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     JMSAbstractReceiver
 * @since   JDK1.6
 */
public class TopicDurableSubscriber extends JMSAbstractReceiver{
	
	/**
	 * 订阅名称
	 */
	private String subscriber;
	
	/**
	 * @return the subscriber
	 */
	public String getSubscriber() {
		return subscriber;
	}

	/**
	 * @param subscriber the subscriber to set
	 */
	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	/*
	 * (non-Javadoc)
	 * @see com.wprojectframework.jms.JMSAbstractReceiver#getConsumer()
	 */
	@Override
	protected MessageConsumer getConsumer() throws JMSException {
		if(this.destination == null){
			return null;
		}
		Topic topic = getSession().createTopic(this.destination);
		return getSession().createDurableSubscriber(topic, subscriber);
	}

}
