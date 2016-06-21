package com.sp2p.task;

import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;
import com.sp2p.service.admin.AfterCreditManageService;

public class JobHourTask extends QuartzJobBean {

	private static Log log = LogFactory.getLog(JobHourTask.class);
	private static boolean isRunning = false;

	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext()
				.getBean(beanName);
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		long start = System.currentTimeMillis();

		AfterCreditManageService afterCreditManageService = (AfterCreditManageService) getBean("afterCreditManageService");
		try {
			if (!isRunning) {
				isRunning = true;
				log.info("开始体验标自动还款");
				afterCreditManageService.investTrialAuto();
				log.info("结束体验标自动还款");
				isRunning = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		log.info("用时 : " + (System.currentTimeMillis() - start) + "毫秒"
				+ "SystemMemery:freeMemory" + Runtime.getRuntime().freeMemory()
				+ "-------maxMemory" + Runtime.getRuntime().maxMemory()
				+ "-------totalMemory" + Runtime.getRuntime().totalMemory());
	}
}
