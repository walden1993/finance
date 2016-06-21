package com.sp2p.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

/**
 * 涨薪宝自动转入
 * @ClassName: JobTaskPayTreasure 
 * @author hjh
 * @date 2015-9-30 上午10:50:37
 */
public class JobTaskPayTreasureAuto extends QuartzJobBean {
	
	private static Log log = LogFactory.getLog(JobTaskPayTreasureAuto.class);
	private static boolean isRunning = false;
	
	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		long start = System.currentTimeMillis();    
	    
	    JobTaskService jobTaskService =  (JobTaskService) getBean("jobTaskService");
		
	    try {
			if(!isRunning){
				isRunning = true;
				jobTaskService.payTreasureInto();
				isRunning = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒"
				+"SystemMemery:freeMemory"+Runtime.getRuntime().freeMemory()+"-------maxMemory"+Runtime.getRuntime().maxMemory()+"-------totalMemory"+Runtime.getRuntime().totalMemory());
	}
}
