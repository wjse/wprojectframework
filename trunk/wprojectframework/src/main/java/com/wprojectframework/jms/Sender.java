package com.wprojectframework.jms;

import java.io.Serializable;
import java.util.Map;


/**
 * 发送消息接口
 * 该接口定义发送行为，并且多行为重载
 * @author lenovo
 *
 */
public interface Sender extends JMSAbstractInterface{
	
	/**
	 * 发送字符串
	 * @param destName
	 * @param str
	 */
    public void send(String destName,String str);
    
    /**
     * 发送对象
     * @param destName
     * @param ser
     */
    public void send(String destName,Serializable ser);
    
    /**
     * 发送map
     * @param destName
     * @param map
     */
	@SuppressWarnings("rawtypes")
	public void send(String destName,Map map);
    
    /**
     * 发送消息，目标由spring配置注入给链接工厂
     * @see com.stee.dsms.JMSConnectionFactory
     * @param str
     */
    public void send(String str);
}

