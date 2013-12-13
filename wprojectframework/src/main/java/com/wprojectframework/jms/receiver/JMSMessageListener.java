package com.wprojectframework.jms.receiver;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 
 * @class JMSMessageListener.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 自定义消息监听器
 * 在调用方获取非空消息实体后，请一定要手动cleanMessage
 * 否则易出错
 */
public class JMSMessageListener implements MessageListener{
	
	/**
	 * message
	 */
	private Message message;
	
	/*
	 * (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message arg0) {
		message = arg0;
	}
	
	/**
	 * 暴露对外获取消息
	 * @return
	 */
	public Message getMessage(){
		return message;
	}
	
	/**
	 * 清除消息变量
	 */
	public void cleanMessage(){
		message = null;
	}
}
