package com.shove.util;
import org.aspectj.lang.ProceedingJoinPoint;
/**   
*    
* @Description 切面
* @Author Yang Cheng
* @Date: Nov 22, 2013 9:35:51 PM   
* @Version    
*    
*/ 
public class ProjectAspect {
	/** 
	* @Description: 函数参数SQL注入过滤
	* @Author Yang Cheng
	* @Date: Nov 22, 2013 9:36:27 PM  
	* @param joinPoint
	* @return
	* @throws Throwable  
	* @return Object    
	*/ 
	public Object doFilteSqlInfusionAround(ProceedingJoinPoint joinPoint) throws Throwable{
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			if(null != args[i] && args[i] instanceof String){
				args[i] = com.shove.web.Utility.filteSqlInfusion(args[i].toString());
			}
		}
		return joinPoint.proceed(args);
	}
}
