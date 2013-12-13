package com.wprojectframework.jms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import com.wprojectframework.core.BeanHandler;

/**
 * JMS发送器测试用例
 * @author lenovo
 *
 */
public class TestJMSSender {
	
	/**
	 * 队列发送器
	 */
	Sender queueSender;
	
	/**
	 * 主题发送器
	 */
	Sender topicSender;
	
	/**
	 * before
	 */
	@Before
	public void before(){
		queueSender = BeanHandler.getBean("queueSender");
		topicSender = BeanHandler.getBean("topicSender");
	}
	
	/**
	 * 队列发送消息测试
	 */
//	@Test
	public void testQueueSender(){
		queueSender.send("dxh_tfels_queue", "first test message");
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("1", "hello");
		map.put("2", new ArrayList<String>(2));
//		queueSender.send("queue_2", map);
		
		User user = new User();
		user.setId(1);
		user.setUserName("dsms");
		user.setPassword("fjslkfja");
//		queueSender.send("queue_3", user);
		
	}
	
	/**
	 * 主题发送消息测试
	 */
	@Test
	public void testTopicSender(){
		topicSender.send("first test message");
//		topicSender.send("first test message");
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("1", "hello");
//		map.put("2", new ArrayList<String>(2));
//		topicSender.send("topic_2", map);
//		
//		User user = new User();
//		user.setId(1);
//		user.setUserName("dsms");
//		user.setPassword("fjslkfja");
//		topicSender.send("topic_3", user);
		
	}
	
//	/**
//	 * 
//	 */
//	@After
//	public void after(){
//		queueSender.closeSession();
//		topicSender.closeSession();
//		
//	}
	
//	@Test
	public void testWebsphereMQQueueSend(){
		queueSender.send("queue1","hello");
	}
	
//	@Test
	public void testWebsphereMQTopicSend(){
		topicSender.send("topic1", "hello topic1");
	}
	
}

class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4727557233703970529L;

	private int id;
	
	private String userName;
	
	private String password;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
