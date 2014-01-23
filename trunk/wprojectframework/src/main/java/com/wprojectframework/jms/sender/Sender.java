package com.wprojectframework.jms.sender;

import java.io.Serializable;
import java.util.Map;

import com.wprojectframework.jms.JMSAbstractInterface;


/**
 * 
 * <pre>
 * 发送消息接口
 * 该接口定义发送行为，并且多行为重载
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     JMSAbstractInterface
 * @since   JDK1.6
 */
public interface Sender extends JMSAbstractInterface{
	
	/**
	 * 发送字符串
	 * @param destination
	 * @param str
	 */
    public void send(String destination,String str);
    
    /**
     * 发送对象
     * @param destination
     * @param ser
     */
    public void send(String destination,Serializable ser);
    
    /**
     * 发送map
     * @param destination
     * @param map
     */
	@SuppressWarnings("rawtypes")
	public void send(String destination,Map map);
    
    /**
     * 发送消息，目标由spring配置注入给链接工厂
     * @see com.stee.dsms.JMSConnectionFactory
     * @param str
     */
    public void send(String str);
    
    /**
     * 发送对象
     * @param ser
     */
    public void send(Serializable ser);
    
    /**
     * 发送map
     * @param map
     */
	@SuppressWarnings("rawtypes")
	public void send(Map map);
}

