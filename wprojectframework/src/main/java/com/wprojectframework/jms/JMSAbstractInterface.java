package com.wprojectframework.jms;


/**
 * 
 * <pre>JMS公用接口,用于获得链接工厂</pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @since   JDK1.6
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
