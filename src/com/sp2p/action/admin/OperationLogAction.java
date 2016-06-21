package com.sp2p.action.admin;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.web.action.BasePageAction;
import com.sp2p.service.OperationLogService;

public class OperationLogAction extends BasePageAction {
	public static Log log = LogFactory.getLog(OperationLogAction.class);
	private OperationLogService operationLogService;

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	/**
	 * 查询操作初始化
	 * 
	 * @return
	 */
	public String queryRecordInit() {
		String operation_around = request.getString("operation_around").toString();
		request().setAttribute("operation_around", operation_around);
		return SUCCESS;
	}

	// 查询操作记录
	public String queryRecordInfo() throws Exception {
		try {
			String adminName = paramMap.get("adminName");
			String startTime = paramMap.get("startTime");
			String endTime =paramMap.get("endTime");
			String operation_around = request.getString("operation_around").toString();
			operationLogService.queryAdminRecordAll(pageBean, adminName,
					startTime,endTime, operation_around);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
}
