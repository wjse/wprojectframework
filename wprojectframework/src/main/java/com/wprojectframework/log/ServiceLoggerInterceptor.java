package com.wprojectframework.log;

import org.aspectj.lang.ProceedingJoinPoint;


/**
 * service层日志记录拦截器
 * 记录DEBUG级别日志
 * @author lenovo
 *
 */
public class ServiceLoggerInterceptor extends AbstractLoggerInterceptor{
	
    
    /**
     * arround
     * @param point
     * @return
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
