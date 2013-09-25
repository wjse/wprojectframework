package com.wprojectframework.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.wprojectframework.util.ArrayUtil;

/**
 * 
 * @class AbstractLoggerInterceptor.java
 * @author wujia
 * @date 2013-9-4
 * @version v1.0
 * @todo
 * 日志拦截器抽象定义，
 * 该类主要定义了环绕通知以及日志打印
 */
public abstract class AbstractLoggerInterceptor {
	
	/**
	 * logger
	 */
    static final Logger logger = Logger.getLogger(AbstractLoggerInterceptor.class);
	
    /**
     * AspectJ around advice
     * @param point
     * @return
     */
	public abstract Object around(ProceedingJoinPoint point);
	
	/**
     * log class name
     * @param point
     */
    protected void logClass(ProceedingJoinPoint point){
		StringBuffer log = new StringBuffer();
		log.append(" invoke class [");
		log.append(point.getTarget().getClass().getName()+"]");
		logger.debug(log.toString());
    }
    
    /**
     * log method name 
     * @param point
     */
    protected void logMethod(ProceedingJoinPoint point){
    	StringBuffer log = new StringBuffer();
    	log.append(" invoke method [");
    	log.append(point.getSignature().getName());
    	log.append("(");
    	Object[] args = point.getArgs();
    	if(ArrayUtil.isNotEmpty(args)){
    		for (int i = 0; i < args.length; i++) {
    			if(args[i] != null){
    				log.append(args[i].getClass().getName());
    			}else{
    				log.append("null");
    			}
    			if(i != args.length-1){
    				log.append(",");
    			}
			}
    	}
    	log.append(")]");
    	logger.debug(log.toString());
    	logArgs(args);
    }
    
    /**
     * log arg list
     * @param point
     */
    protected void logArgs(Object[] args){
    	if(ArrayUtil.isEmpty(args)){
    		return;
    	}
    	StringBuffer log = new StringBuffer();
    	log.append(" args [{");
		for (int i = 0; i < args.length; i++) {
			if(args[i] != null){
				log.append(args[i]);
			}else{
				log.append("null");
			}
			if(i != args.length-1){
				log.append(",");
			}
		}
		log.append("}]" );
		logger.debug(log.toString());
    }
    
    /**
     * log return Object
     * @param obj
     */
    protected void logReturn(Object obj){
    	logger.debug(" return Object ["+obj+"]");
    }
}
