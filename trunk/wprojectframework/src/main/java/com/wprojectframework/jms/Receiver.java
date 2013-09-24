package com.wprojectframework.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 接收消息接口
 * 该接口定义接收行为，提供同步接收以及异步接收两种接收机制
 * @author lenovo
 *
 */
public interface Receiver extends JMSAbstractInterface{
	
	/**
	 * 同步接收消息
	 * @param destName 
	 * @return
	 */
	public Message receiveMessage(String destName);
	
	/**
	 * 异步接收消息
	 * @param destName
	 * @param listener @see com.stee.dsms.jms.impl.JMSMessageListener
	 */
	public void listenMessage(String destName,MessageListener listener);
}
