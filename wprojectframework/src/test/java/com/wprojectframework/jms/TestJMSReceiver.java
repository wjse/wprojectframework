package com.wprojectframework.jms;


import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.junit.Before;
import org.junit.Test;

import com.wprojectframework.core.BeanHandler;
import com.wprojectframework.jms.impl.JMSMessageListener;

/**
 * JMS接收器测试用例
 * @author lenovo
 *
 */
public class TestJMSReceiver {
	
	/**
	 * 队列接收器
	 */
	Receiver queueReceiver;
	
	/**
	 * 主题接收器
	 */
	Receiver topicReceiver;
	
	/**
	 * before
	 */
	@Before
	public void before(){
		queueReceiver = BeanHandler.getBean("queueReceiver");
		topicReceiver = BeanHandler.getBean("topicReceiver");
	}
	
	/**
	 * 队列接收测试
	 * @throws JMSException
	 * @throws InterruptedException
	 */
//	@Test
	public void testQueueReceiver() throws JMSException, InterruptedException{
		System.out.println("+++++++++++++QUEUE+++++++++++++");
		/*同步接收*/
		Message m2 = queueReceiver.receiveMessage("queue_2");
		System.out.println(m2);
		
		ActiveMQObjectMessage m3 = (ActiveMQObjectMessage) queueReceiver.receiveMessage("queue_3");
		System.out.println(m3);
		
		/*异步接收*/
		JMSMessageListener listener = new JMSMessageListener();
		queueReceiver.listenMessage("queue_1",listener);
		while(true){
			if(listener.getMessage() != null){
				System.out.println(listener.getMessage());
				listener.cleanMessage();
			}
			Thread.sleep(1000);
		}
	}
	
	/**
	 * 主题接收测试
	 * @throws InterruptedException
	 */
	@Test
	public void testTopicReceiver() throws InterruptedException{
//		System.out.println("+++++++++++++TOPIC+++++++++++++");
//		/*同步接收*/
//		Message m2 = topicReceiver.receiveMessage("topic_2");
//		System.out.println(m2);
		
		Message m3 = topicReceiver.receiveMessage("JMSServer-ST/SystemModule-ST!Topic-ST");
		System.out.println(m3);
		
		/*异步接收*/
		JMSMessageListener listener = new JMSMessageListener();
		topicReceiver.listenMessage(listener);
		while(true){
			if(listener.getMessage() != null){
				System.out.println(listener.getMessage());
				listener.cleanMessage();
			}
			Thread.sleep(1000);
		}
	}
	
}
