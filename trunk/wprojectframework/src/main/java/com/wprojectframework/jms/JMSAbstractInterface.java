package com.wprojectframework.jms;


/**
 * 自定义JMS公用接口
 * @author lenovo
 *
 */
public interface JMSAbstractInterface {
	
	/**
	 * 关闭链接,该方法已过时因为
	 * 不提倡主动关闭链接,
	 * JMS应该保持长连接存在
     */
	@Deprecated
    public void closeSession();
	
	public JMSConnectionFactory getJmsConnectionFactory();
}
