package com.wprojectframework.core;

import java.util.Locale;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @class BeanHandler.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * spring IOC容器扩展类,方便某些地方获取容器中的内容
 */
public class BeanHandler implements ApplicationContextAware {
	
	/**
	 * IOC context
	 */
	private static ApplicationContext context;
	
	/**
	 * 配置文件classpath,方便单元测试，可通过IOC注入
	 */
	private static String _contextPath = "classpath*:/applicationContext.xml";
	
	/**
	 * 服务器本地语言环境key
	 */
	private static final String SERVER_LOCAL_KEY = "user.language";
	
	/**
	 * 私有构造
	 */
	private BeanHandler(){
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)throws BeansException {
		setApplicationContextStatic(arg0);
	}
	
	/**
	 * 获取bean,返回bean，bean通过泛型转型
	 * 调用方无需再转型
	 * @param String name
	 * @return E
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getBean(String name){
		initContext();
		return (E) context.getBean(name);
	}
	
	/**
	 * 获取bean map
	 * @param Class clz
	 * @return Map
	 */
	public static <E> Map<String,E> getBeans(Class<E> clz){
		initContext();
		return context.getBeansOfType(clz);
	}
	
	/**
	 * 根据传入Local获取国际化信息
	 * @param key
	 * @param locale
	 * @return
	 */
	public static String getMessage(String key,Locale locale){
		return context.getMessage(key, null, locale);
	}
	
	/**
	 * 根据服务器本地Local获取国际化信息
	 * @param key
	 * @return
	 */
	public static String getMessage(String key){
		return getMessage(key,new Locale(SERVER_LOCAL_KEY));
	}
	
	/**
	 * 设置容器静态方法
	 * @param ApplicationContext arg0
	 */
	private static void setApplicationContextStatic(ApplicationContext arg0){
		context = arg0;
	}

	/**
	 * 初始化IOC容器,此方法服务于单元测试通过classpath获取IOC
	 */
	private static void initContext(){
		if(null == context){
			context = new ClassPathXmlApplicationContext(_contextPath);
		}
	}
}
