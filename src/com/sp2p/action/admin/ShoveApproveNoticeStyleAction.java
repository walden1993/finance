package com.sp2p.action.admin;

import java.sql.SQLException;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.ShoveApproveNoticeStyleService;
import com.sp2p.service.admin.ShoveApproveNoticeTemplateService;
import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

/**
 * 提醒方式
 * 
 * @author C_J
 * 
 */
public class ShoveApproveNoticeStyleAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory
			.getLog(ShoveApproveNoticeStyleAction.class);

	private ShoveApproveNoticeStyleService shoveApproveNoticeStyleService;
	private ShoveApproveNoticeTemplateService shoveApproveNoticeTemplateService;

	/**
	 * 查询所有初始化
	 * 
	 * @return
	 */
	public String queryNoticeStyleAllInit() {
		int sid = request.getInt("sid", 0);
		request().setAttribute("sid", sid);
		return SUCCESS;
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryNoticeStyleAllList() throws Exception {
		int notice_style = request.getInt("sid", 0);
		if (notice_style == 0) {
			notice_style = 1;
		}
		try {
			shoveApproveNoticeStyleService.queryApproveNoticeStylePageAll(
					pageBean, notice_style);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * 修改初始化
	 * 
	 * @return
	 */
	public String updateNoticeStyleInit() {
		int id = request.getInt("id", 0);
		try {
			paramMap = shoveApproveNoticeStyleService
					.queryApproveNoticeStyleById(id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateNoticeStyleAllinfo() throws Exception {
		String title = request.getString("paramMap.title");
		int sort = request.getInt("paramMap.sort", 0);
		int id = request.getInt("paramMap.id", -1);
		int notice_style = request.getInt("notice_style", 0);
		long result = -1L;
		try {
			result = shoveApproveNoticeStyleService.updateApproveNoticeStyle(
					id, title, sort);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		request().setAttribute("sid", notice_style);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result > 0) {
			operationLogService.addOperationLog("t_approve_notice_style", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"修改提醒设置成功", 2);
			return SUCCESS;
		} else {
			operationLogService.addOperationLog("t_approve_notice_style", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"修改提醒设置失败", 2);
			return INPUT;
		}
	}

	/**
	 * 管理初始化
	 * 
	 * @return
	 */
	public String queryNoticeTemplateAllInit() {
		int id = request.getInt("id", 0);
		int notice_style = request.getInt("notice_style", -1);
		request().setAttribute("id", id);
		request().setAttribute("notice_style", notice_style);
		return SUCCESS;
	}

	/**
	 * 管理
	 * 
	 * @return
	 */
	public String queryNoticeTemplateAllList() {
		int notice_style = request.getInt("notice_style", 0);
		int id =request.getInt("id", 0); 
		try {
			shoveApproveNoticeTemplateService.queryOrderRechargeRecords(
					pageBean, notice_style, id, 0);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		request().setAttribute("id", id);
		return SUCCESS;
	}

	/**
	 * 提醒管理修改初始化
	 * 
	 * @return
	 */
	public String updateNoticeTemplateInit() {
		int id = request.getInt("id", 0);
		int notice_style =request.getInt("notice_style", 0); 
		try {
			shoveApproveNoticeTemplateService.queryOrderRechargeRecords(
					pageBean, notice_style, id, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 提醒管理修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateNoticeTemplateInfo() throws Exception {
		String name = request.getString("bean_name");
		String template = request.getString("bean_template");
		int sort = request.getInt("bean_sort", 0);
		int id = request.getInt("id", 0);
		int sid = request.getInt("sid", 0);
		int notice_id = request.getInt("notice_id", 0);
		int notice_style =request.getInt("notice_style", -1); 
		request().setAttribute("notice_style", notice_style);
		request().setAttribute("id", sid);
		request().setAttribute("ntid", notice_id);
		long result = -1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {
			result = shoveApproveNoticeTemplateService
					.updateApproveNoticeTemplate(id, notice_id, name, template,
							sort);
			// request().setAttribute("sid", notice_style);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result > 0) {
			operationLogService.addOperationLog("t_approve_notice_template",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "提醒管理修改成功", 2);
			return SUCCESS;
		} else {
			operationLogService.addOperationLog("t_approve_notice_template",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "提醒管理修改失败", 2);
			return INPUT;
		}
	}

	public void setShoveApproveNoticeStyleService(
			ShoveApproveNoticeStyleService shoveApproveNoticeStyleService) {
		this.shoveApproveNoticeStyleService = shoveApproveNoticeStyleService;
	}

	public void setShoveApproveNoticeTemplateService(
			ShoveApproveNoticeTemplateService shoveApproveNoticeTemplateService) {
		this.shoveApproveNoticeTemplateService = shoveApproveNoticeTemplateService;
	}

}
