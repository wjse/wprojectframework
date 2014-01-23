package com.wprojectframework.log;

import org.aspectj.lang.ProceedingJoinPoint;


/**
 * 
 * <pre>
 *  service层日志记录拦截器
 *  记录DEBUG级别日志
 * </pre>
 * @author  WuJ
 * @version v1.0
 * @date    2014年1月23日
 * @see     AbstractLoggerInterceptor
 * @since   JDK1.6
 */
public class ServiceLoggerInterceptor extends AbstractLoggerInterceptor{
	
    
    /*
     * (non-Javadoc)
     * @see com.wprojectframework.log.AbstractLoggerInterceptor#around(org.aspectj.lang.ProceedingJoinPoint)
     */
	@Override
    public Object around(ProceedingJoinPoint point){
		Object returnObj = null;
    	if(logger.isDebugEnabled()){
    		logClass(point);
    		logMethod(point);
    	}
    	try {
    		returnObj = point.proceed();
	    	if(logger.isDebugEnabled()){
	    		logReturn(returnObj);
	    	}
		} catch (Throwable e) {
			logger.error(e.toString(),e);
		}
    	return returnObj;
    }
}
