package com.wprojectframework.jms.receiver;

import javax.jms.Message;
import javax.jms.MessageListener;

import com.wprojectframework.jms.JMSAbstractInterface;

/**
 * 
 * @class Receiver.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 接收消息接口
 * 该接口定义接收行为，提供同步接收以及异步接收两种接收机制
 */
public interface Receiver extends JMSAbstractInterface{
	
	/**
	 * 同步接收消息
	 * @param destination 
	 * @return
	 */
	public Message receiveMessage(String destination);
	
	/**
	 * 同步接收消息
	 * 目的地通过IOC注入
	 * @return
	 */
	public Message receiveMessage();
	
	/**
	 * 异步接收消息
	 * @param destination
	 * @param listener @see com.stee.dsms.jms.impl.JMSMessageListener
	 */
	public void listenMessage(String destination,MessageListener listener);
	
	/**
	 * 异步接收消息
	 * 目的地通过IOC注入
	 * @param listener
	 */
	public void listenMessage(MessageListener listener);
}
