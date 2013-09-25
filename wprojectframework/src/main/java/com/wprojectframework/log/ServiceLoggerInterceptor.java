package com.wprojectframework.log;

import org.aspectj.lang.ProceedingJoinPoint;


/**
 * 
 * @class ServiceLoggerInterceptor.java
 * @author wujia
 * @date 2013-9-25
 * @version v1.0
 * @todo
 * service层日志记录拦截器
 * 记录DEBUG级别日志
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
