package com.wprojectframework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * @class DateUtil.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * 日期工具类
 */
public class DateUtil {
	
	/**
	 * 对外默认日期格式
	 */
	public static final String PATTERN_DEFAULT = "yyyy-MM-dd";
	
	/**
	 * 私有默认日期格式 
	 */
	private static final String DEFAULT = "yyyy-MM-dd hh:mm:ss";
	
	/**
	 * 私有构造
	 */
	private DateUtil(){}
	
	/**
	 * 获取当前日期字符串
	 * @param pattern 日期转换格式，如果为空则日期格式显示为默认值:yyyy-MM-dd hh:mm:ss
	 * @return String
	 */
	public static String getCurDateStr(String pattern){
		String dateStr = DateFormatUtils.format(new Date(), StringUtils.isNotBlank(pattern)?pattern:DEFAULT);
		return dateStr;
	}
	
	/**
	 * 日期字符串转换为日期
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return Date
	 * @throws ParseException
	 */
	public static Date getDateByStr(String dateStr,String pattern) throws ParseException{
		if(StringUtils.isNotBlank(dateStr)){
			dateStr = dateStr.replace("T", " ");
			if(dateStr.indexOf("+") >= 0){
				dateStr = dateStr.split("\\+")[0];
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isNotBlank(pattern)?pattern:DEFAULT);
		return sdf.parse(dateStr);
	}
	
	/**
	 * 日期转换为日期字符串
	 * @param date 日期
	 * @param pattern 日期格式
	 * @return String
	 */
	public static String getStringByDate(Date date , String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isNotBlank(pattern)?pattern:DEFAULT);
		return sdf.format(date);
	}
}
