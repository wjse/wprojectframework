package com.wprojectframework.jms;


/**
 * 
 * @class JMSAbstractInterface.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * JMS公用接口,用于获得链接工厂
 */
public interface JMSAbstractInterface {
	
	/**
	 * 关闭链接,该方法已过时因为
	 * 不提倡主动关闭链接,
	 * JMS应该保持长连接存在
     */
	@Deprecated
    public void closeSession();
	
	/**
	 * 获得链接工厂
	 * @return
	 */
	public JMSConnectionFactory getJmsConnectionFactory();
}
