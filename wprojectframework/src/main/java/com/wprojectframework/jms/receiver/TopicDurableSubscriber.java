package com.wprojectframework.jms.receiver;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Topic;

/**
 * 
 * @class TopicDurableSubscriber.java
 * @author wujia
 * @date 2013-10-15
 * @version v1.0
 * @todo
 * 主题持久化消息订阅者
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
