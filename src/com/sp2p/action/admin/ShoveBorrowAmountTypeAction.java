package com.sp2p.action.admin;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.ShoveBorrowAmountTypeService;

/**
 * 额度信息 action
 * 
 * @author C_J
 * 
 */
public class ShoveBorrowAmountTypeAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(MailBoxManagerAction.class);
	private static final long serialVersionUID = 1L;

	private ShoveBorrowAmountTypeService shoveBorrowAmountTypeService;

	/**
	 *额度信息初始化
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String queryBorrowAmountInit() {

		return SUCCESS;
	}

	/**
	 *查询所有额度信息
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String queryBorrowAmountInfo() {
		try {
			shoveBorrowAmountTypeService.queryBorrowAmountPageAll(pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	/**
	 * 修改额度信息初始化
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String updatgeBorrowAmountInit() {
		int id = request.getInt("id", -1);
		try {
			paramMap = shoveBorrowAmountTypeService.queryBorrowAmountById(id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
      
	}

	/**
	 * 修改额度信息
	 * 
	 * @return
	 * @throws Exception 
	 * @throws SQLException
	 */
	public String updatgeBorrowAmount() {
		
		int id = request.getInt("paramMap.id", -1);
		String title = request.getString("paramMap.title");
		String descriptionm = request.getString("paramMap.description");
		int status =
				request.getInt("paramMap_status", -1);
		String remark = request.getString("paramMap.remark");
		double init_credit = request.getDouble(
				"paramMap.init_credit", 0);
		long result = -1L;
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try {

			result = shoveBorrowAmountTypeService.updateBorrowAmount(id, title,
					descriptionm, status, remark, init_credit);
			if (result > 0) {
				// 添加操作日志
				operationLogService.addOperationLog("t_borrow_amount_type",
						admin.getUserName(), IConstants.UPDATE, admin
								.getLastIP(), 0, "修改借款额度类型", 2);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result > 0)
			return SUCCESS;
		else
			return INPUT;
       
	}

	public void setShoveBorrowAmountTypeService(
			ShoveBorrowAmountTypeService shoveBorrowAmountTypeService) {
		this.shoveBorrowAmountTypeService = shoveBorrowAmountTypeService;
	}

	public ShoveBorrowAmountTypeService getShoveBorrowAmountTypeService() {
		return shoveBorrowAmountTypeService;
	}

}
