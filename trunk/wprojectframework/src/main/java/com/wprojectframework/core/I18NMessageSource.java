package com.wprojectframework.core;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 
 * @class I18NMessageSource.java
 * @author wujia
 * @date 2013-6-28
 * @version v1.0
 * @todo  
 * 国际化资源文件配置源扩展类
 * 该类对于org.springframework.context.support.ResourceBundleMessageSource
 * 起支撑作用，解决了spring国际化扫描规则单一的短处
 * 可支持带'*'通配符的匹配扫描;
 * IOC中的配置类似如下：
 * <pre>
 *    <bean id="messageSource" class="com.wprojectframework.core.I18NMessageSource" init-method="init">
 *       <property name="scanPath">
 *          <list>
 *              <value>i18n/*_message</value>
 *          </list>
 *       </property>
 *    </bean>
 * </pre>
 * 或者:
 * <pre>
 *    <bean id="messageSource" class="com.wprojectframework.core.I18NMessageSource" init-method="init">
 *       <property name="scanPackage" value="i18n">
 *       </property>
 *    </bean>
 * </pre>
 */
public class I18NMessageSource extends ResourceBundleMessageSource{
	
	/**
	 * I18N资源文件扫描路径集
	 */
	private String[] scanPath;
	
	/**
	 * I18N资源文件包路径
	 */
	private String scanPackage;
	
	/**
	 * 语言环境集
	 */
	private static final Locale[] DEFAULT_LOCALS = DateFormat.getAvailableLocales();
	
	/**
	 * @return the scanPackage
	 */
	public String getScanPackage() {
		return scanPackage;
	}

	/**
	 * @param scanPackage the scanPackage to set
	 */
	public void setScanPackage(String scanPackage) {
		this.scanPackage = scanPackage;
	}

	/**
	 *  set scanPath
	 */
	public void setScanPath(String[] scanPath) {
		this.scanPath = scanPath;
	}
	
	/**
	 * IOC容器加载时进行初始化。
	 * 初始化主要作将scanPath中带通配符的路径进行实际路径匹配
	 * 并将实际路径设置给ResourceBundleMessageSource
	 * @throws IOException
	 */
	public void init() throws IOException{
		if(null != scanPath){
			/*
			 * 配置形式为<properties name="scanPath">
			 *             <list>
			 *                 <value>i18n/*_message</value>
			 *             </list>
			 *        </properties>
			 */
			doPathScan();
		}else if(null == scanPath && null != scanPackage){//配置形式为<properties name="scanPackage" value="i18n"/>
			setScanPathFromScanPackage();
			doPathScan();
		}else{//未配置资源文件
			super.setBasenames(scanPath);
		}
	}
	
	/**
	 * 检索路劲
	 * @throws IOException
	 */
	public void doPathScan() throws IOException{
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		for (int i = 0; i < scanPath.length; i++) {
			//路劲拼装为classpath*:xxxx*.properties
			StringBuilder sb = new StringBuilder();
			sb.append(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX);
			sb.append(scanPath[i]);
			sb.append("*.properties");
			//获取资源
			Resource[] rs = resolver.getResources(sb.toString());
			//设置资源到spring容器
			setMessageSource(getFolderName(scanPath[i]), rs);
		}
	}
	
	/**
	 * 将解析匹配后的实际路径设置给ResourceBundleMessageSource
	 * @param folderName 目录
	 * @param rs 资源集
	 * @param defaultLocales 所有国家Locale集
	 */
	private void setMessageSource(String folderName,Resource[] rs){
		if(null == rs || 0 == rs.length){
			logger.info("no resources be found in scanPath");
			return;
		}
		String[] basenames = new String[rs.length];
		for (int i = 0; i < rs.length; i++) {
			String basename = folderName+getI18NName(getResourceFileName(rs[i]));
			basenames[i] = basename;
		}
		logger.info("load message source "+ToStringBuilder.reflectionToString(basenames, ToStringStyle.SIMPLE_STYLE)+" success");
		super.setBasenames(basenames);
	}
	
	/**
	 * 获取资源文件名
	 * @param rs 资源
	 * @return
	 */
	private String getResourceFileName(Resource rs){
		return rs.getFilename();
	}
	
	/**
	 * 拆分I18N资源文件名
	 * 将国家语言符号与前文件名进行拆分
	 * @param fileName
	 * @param locales
	 * @return
	 */
	private String getI18NName(String fileName){
		if(null == fileName){
			return null;
		}
		//拼接国家地区后缀
		for (int i = 0; i < DEFAULT_LOCALS.length; i++) {
			String language = "_"+DEFAULT_LOCALS[i].getLanguage();
			if(fileName.contains(language)){
				return fileName.substring(0,fileName.indexOf(language));
			}
		}
		return fileName;
	}
	
	/**
	 * 获取目录名
	 * @param path
	 * @return
	 */
	private String getFolderName(String path){
		return path.substring(0, path.lastIndexOf("/")+1);
	}
	
	/**
	 * 将包扫描路径添加/*后设置给扫描路径
	 */
	private void setScanPathFromScanPackage(){
		scanPath = new String[]{scanPackage+"/*"};
	}
}
