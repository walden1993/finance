package com.sp2p.task;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoader;

import com.sp2p.service.admin.AfterCreditManageService;

/**
 * 功能 ：查询今日还款，如果有，则提醒相关同事
 * @author bao
 */
public class JobDoubleTask extends QuartzJobBean {

	private static Log log = LogFactory.getLog(JobHourTask.class);

	private Object getBean(String beanName) {
		return ContextLoader.getCurrentWebApplicationContext()
				.getBean(beanName);
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		AfterCreditManageService afterCreditManageService = (AfterCreditManageService) getBean("afterCreditManageService");
		try {
				log.info("---JobDoubleTask开始查询今日还款");
				afterCreditManageService.queryTodayRepay();
				log.info("---JobDoubleTask结束查询今日还款");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}
}
