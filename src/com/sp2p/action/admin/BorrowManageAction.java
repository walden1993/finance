package com.sp2p.action.admin;

import java.awt.print.Printable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.params.ConnConnectionParamBean;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.allinpay.ets.client.StringUtil;
import com.renren.api.client.utils.JsonUtils;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.entity.Admin;
import com.sp2p.service.DataApproveService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.DateUtil;
import com.sun.xml.bind.v2.model.core.ID;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的借款控制层
 */
public class BorrowManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(BorrowManageAction.class);
	private static final long serialVersionUID = 1L;

	private BorrowManageService borrowManageService;
	// ---add by houli
	private DataApproveService dataApproveService;
	// ---add by C_J
	private ShoveBorrowTypeService shoveBorrowTypeService;
	private FinanceService financeService;
	private SelectedService selectedService;
	private UserService userService;
    private SysparService sysparService;
    private ValidateService validateService;
    
	
	private Map<String, String> borrowMFADetail;
	private Map<String, String> borrowMTenderInDetail;
	private Map<String, String> borrowMFullScaleDetail;
	private Map<String, String> borrowMFlowMarkDetail;
	private Map<String, String> borrowMAllDetail;
	private Map<String, String> borrowCirculationDetail;
	private List<Map<String, Object>> cirList;
	private Object borrowId = "";

	// 下拉列表
	private List<Map<String, Object>> borrowPurposeList;
	private List<Map<String, Object>> borrowDeadlineList;
	private List<Map<String, Object>> borrowAmountList;
	private List<Map<String, Object>> borrowRaiseTermList;
	private List<Map<String, Object>> sysImageList;
	private List<Map<String, Object>> borrowTurnlineList;
	private List<Map<String, Object>> imgtype;
	
	private Long relate_borrowid;
	
	
	
	
	public void setValidateService(ValidateService validateService) {
        this.validateService = validateService;
    }

    public Long getRelate_borrowid() {
        return relate_borrowid;
    }

    public void setRelate_borrowid(Long relate_borrowid) {
        this.relate_borrowid = relate_borrowid;
    }

    public SysparService getSysparService() {
        return sysparService;
    }

    public List<Map<String, Object>> getImgtype() {
        return imgtype;
    }

    public void setSysparService(SysparService sysparService) {
        this.sysparService = sysparService;
    }

    /**
	 * @MethodName: borrowManageFistAuditInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 上午09:54:00
	 * @Return:
	 * @Descb: 后台借款管理初审初始化
	 * @Throws:
	 */
	public String borrowManageFistAuditInit() throws SQLException,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:58:57
	 * @Return:
	 * @Descb: 后台借款管理初审列表
	 * @Throws:
	 */
	public String borrowManageFistAuditList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay");
		
		String orgName = paramMap.get("orgName") == null ? "" : paramMap.get("orgName");
		String userType = paramMap.get("userType") == null ? "" : paramMap.get("userType");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);

		// 暂时性的修改 用于修改显示所有初审中的借款
		// 不用做判断处理~
		// borrowManageService.queryBorrowAllByCondition(userName, borrowWayInt,
		// 1,pageBean);
		// 做了判断的处理
		borrowManageService.queryBorrowFistAuditByCondition(userName,
				borrowWayInt,orgName,userType, pageBean);

		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowTotalFistAuditDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页应收款
		double fistAuditAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				fistAuditAmount = fistAuditAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("fistAuditAmount", fmt.format(fistAuditAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	public String borrowManageWaitingAuditList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowWaitingAuditByCondition(userName,
				borrowWayInt, pageBean);

		Map<String, String> waitTotalAmount = borrowManageService
				.queryBorrowTotalWait();
		request().setAttribute("waitTotalAmount", waitTotalAmount);
		// 统计当前页等待
		double waitingAuditAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				waitingAuditAmount = waitingAuditAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("waitingAuditAmount",
				fmt.format(waitingAuditAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理中的借款详情
	 * @Throws:
	 */
	public String borrowManageFistAuditDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMFADetail == null) {
			// 初审中的借款详情
			borrowMFADetail = borrowManageService
					.queryBorrowFistAuditDetailById(idLong);
			String nid_log = borrowMFADetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
			}
		}
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws IOException
	 * @throws Exception 
	 * @MethodName: updateBorrowF
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午03:58:28
	 * @Return:
	 * @Descb: 审核借款中的初审记录
	 * @Throws:
	 */
	public String updateBorrowF() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String reciver = paramMap.get("reciver");
		long reciverLong = Convert.strToLong(reciver, -1);
		String status = paramMap.get("status");
		int statusLong = Convert.strToInt(status, -1);
		String msg = paramMap.get("msg");
		String auditOpinion = paramMap.get("auditOpinion");
		String  detail = paramMap.get("detail");
		String displayTime = paramMap.get("displayTime");
		String foreknow = paramMap.get("foreknow");
		String rewardRate = paramMap.get("rewardRate");
		long result = -1;

		try {
		    //更改借款详情  hjh  2014-10-10 19:40:13
		    Connection conn = MySQL.getConnection();
		    Dao.Tables.t_borrow t = new Dao().new Tables().new t_borrow();
		    t.detail.setValue(detail);
		    t.firstAuditTime.setValue(new Date());
		    if (StringUtils.isNotBlank(rewardRate)) {
		    	t.rewardRate.setValue(rewardRate);
			}
		    t.update(conn, " id = "+idLong);
		    conn.commit();
		    conn.close();
			result = borrowManageService.updateBorrowFistAuditStatus(idLong,
					reciverLong, statusLong, msg, auditOpinion, admin.getId(),
					getBasePath(),displayTime,foreknow);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result <= 0) {
			// 操作失败提示
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
		
	}

	/**
	 * @MethodName: borrowManageTenderInInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理招标中初始化
	 * @Throws:
	 */
	public String borrowManageTenderInInit() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageTenderInList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款招标中的记录
	 * @Throws:
	 */
	public String borrowManageTenderInList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowTenderInByCondition(userName,userType,
				borrowWayInt, pageBean);

		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowTotalTenderDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页等待
		double tenderAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				tenderAmount = tenderAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("tenderAmount", fmt.format(tenderAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理招标中的借款详情
	 * @Throws:
	 */
	public String borrowManageTenderInDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception 
	 * @throws Exception
	 * @MethodName: updateBorrowF
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午03:58:28
	 * @Return:
	 * @Descb: 审核借款中的招标中记录
	 * @Throws:
	 */
	public String updateBorrowTenderIn() throws Exception  {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		String displayTime = paramMap.get("displayTime");
		long idLong = Convert.strToLong(id, -1);
		String auditOpinion = paramMap.get("auditOpinion");
		long result = -1;
		result = borrowManageService.updateBorrowTenderInStatus(idLong,
				auditOpinion,displayTime);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_borrow",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "审核借款中的招标中记录，操作失败", 2);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		operationLogService.addOperationLog("t_borrow", admin.getUserName(),
				IConstants.UPDATE, admin.getLastIP(), 0, "审核借款中的招标中记录，操作成功", 2);
		return null;
		
	}

	public String reBackBorrowFistAudit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		long result = -1;
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 调用撤消服务
		result = borrowManageService.reBackBorrowFistAudit(idLong, admin
				.getId(), getBasePath(), "", "");

		if (result <= 0) {
			// 操作失败
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 操作成功
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: reBackBorrowTenderIn
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午04:00:42
	 * @Return:
	 * @Descb: 撤消招标中的借款
	 * @Throws:
	 */
	public String reBackBorrowTenderIn() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		long result = -1;
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 调用撤消服务
		result = borrowManageService.reBackBorrow(idLong, admin.getId(),
				getBasePath());
		if (result < 0) {
			// 操作失败
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 操作成功
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @MethodName: borrowManageFullScaleInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理满标初始化
	 * @Throws:
	 */
	public String borrowManageFullScaleInit() throws SQLException,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFullScaleList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款满标的记录
	 * @Throws:
	 */
	public String borrowManageFullScaleList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowFullScaleByCondition(userName,
				borrowWayInt, pageBean);

		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowTotalFullScaleDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页应收款
		double fullScaleAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				fullScaleAmount = fullScaleAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("fistAuditAmount", fmt.format(fullScaleAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理满标的借款详情
	 * @Throws:
	 */
	public String borrowManageFullScaleDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: updateBorrowF
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午03:58:28
	 * @Return:
	 * @Descb: 审核借款中的满标记录
	 * @Throws:
	 */
	public String updateBorrowFullScale() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String userId = paramMap.get("userId");
		long userIdLong = Convert.strToLong(userId, -1);
		String status = paramMap.get("status");
		long statusLong = Convert.strToLong(status, -1);
		String auditOpinion = paramMap.get("auditOpinion");
		Map<String, String> retMap = borrowManageService
				.updateBorrowFullScaleStatus(idLong, statusLong, auditOpinion,
						admin.getId(), getBasePath());
		long retVal = -1;
		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
		session().removeAttribute("randomCode");
		if (retVal <= 0) {
			obj.put("msg", retMap.get("ret_desc"));
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			Long result = -1L;
			result = borrowManageService.updateUserBidStatus(userIdLong);
			if (result > 0) {
				return null;
			}
			return null;
		}
	}

	/**
	 * @MethodName: borrowManageFlowMarkInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理流标初始化
	 * @Throws:
	 */
	public String borrowManageFlowMarkInit() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFlowMarkList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款流标的记录
	 * @Throws:
	 */
	public String borrowManageFlowMarkList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNums = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNums)) {
			pageBean.setPageNum(pageNums);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowFlowMarkByCondition(userName,userType,
				borrowWayInt, pageBean);
		Map<String, String> repaymentMap = borrowManageService
				.queryBorrowFlowMarkDetail();
		request().setAttribute("repaymentMap", repaymentMap);
		// 统计当前页应收款
		double flowmarkAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				flowmarkAmount = flowmarkAmount
						+ Convert.strToDouble(map.get("borrowAmount") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("flowmarkAmount", fmt.format(flowmarkAmount));
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理流标的借款详情
	 * @Throws:
	 */
	public String borrowManageFlowMarkDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @MethodName: borrowManageAllInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:01
	 * @Return:
	 * @Descb: 后台借款管理初始化
	 * @Throws:
	 */
	public String borrowManageAllInit() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}
	
	/**
	 * @throws Exception
	 * @MethodName: borrowManageAllList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午11:36:32
	 * @Return:
	 * @Descb: 后台借款的记录
	 * @Throws:
	 */
	public String borrowManageAllList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap.get("userName");
		String borrowStatus = paramMap.get("borrowStatus") == null ? "" : paramMap.get("borrowStatus");
		int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay");
		String realName = paramMap.get("realName") == null ? "" : paramMap.get("realName");
		String orgName = paramMap.get("orgName") == null ? "" : paramMap.get("orgName");
		String userType = paramMap.get("userType") == null ? "" : paramMap.get("userType");
		String isNew = paramMap.get("isNew") == null ? "" : paramMap.get("isNew");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		borrowManageService.queryBorrowAllByCondition(userName, borrowWayInt, realName, orgName,userType,
				borrowStatusInt,isNew, pageBean);
		// ----add by houli 对等待资料的借款进行标记
		List<Map<String, Object>> lists = borrowManageService
				.queryAllWaitingBorrow();
		Vector<String> ids = new Vector<String>();
		if (lists != null && lists.size() > 0) {
			for (Map<String, Object> map : lists) {
				ids.add(map.get("id").toString());
			}
		}
		List<Map<String, Object>> lls = pageBean.getPage();
		if (lls != null && lls.size() > 0) {
			for (Map<String, Object> map : lls) {
				if (ids.contains(map.get("id").toString())) {
					map.put("flag", "0");
				} else {
					map.put("flag", "1");
				}
			}
		}
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}
	
	/*
	 * 置顶
	 */
	public String hasStickie() throws SQLException, IOException{
	    int id = Convert.strToInt(paramMap.get("id"), -1);
	    int stickie  = Convert.strToInt(paramMap.get("stickie"), -1);
	    long result = -1;
	    Connection conn = MySQL.getConnection();
	    try {
            Dao.Tables.t_borrow borrow = new Dao().new Tables().new t_borrow();
            borrow.stickie.setValue(stickie);
            result = borrow.update(conn, "id="+id);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        }finally{
            conn.close();
        }
	    
	    if (result>0) {//置顶成功
	        JSONUtils.printStr("1");
        }else {
            JSONUtils.printStr("2");//指定失败
        }
	    
	    return null;
	}
	
	/*
	 * 设置新手标
	 */
	public String setNewBorrow() throws SQLException, IOException{
	    int id = Convert.strToInt(paramMap.get("id"), -1);
	    int isNew  = Convert.strToInt(paramMap.get("isNew"), -1);
	    long result = -1;
	    Connection conn = MySQL.getConnection();
	    try {
            Dao.Tables.t_borrow borrow = new Dao().new Tables().new t_borrow();
            borrow.isNew.setValue(isNew);
            result = borrow.update(conn, "id="+id);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        }finally{
            conn.close();
        }
	    
	    if (result>0) {//置顶成功
	        JSONUtils.printStr("1");
        }else {
            JSONUtils.printStr("2");//指定失败
        }
	    
	    return null;
	}
	
	
	// 校验提交借款参数
	@SuppressWarnings("unchecked")
	public boolean isValidate(double amountDouble, String excitationType,
			double sumRateDouble, double annualRateDouble) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String t = (String) session().getAttribute("t");
		// 获取借款的范围
		Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
		tempBorrwBidMessage = shoveBorrowTypeService
				.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
		// 取得按借款金额的比例进行奖励
		double accountfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_first")
				+ "", 0);
		double accountend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_account_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "2".equals(excitationType)) {
				if (sumRateDouble < accountfirst || sumRateDouble > accountend) {
					this.addFieldError("paramMap['sum']", "固定总额奖励填写不正确");
					return false;
				}
			}
		}
		// 如果选择金额的话，则按此奖励的金额范围
		double scalefirst = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_first")
				+ "", 0);
		double scaleend = Convert.strToDouble(tempBorrwBidMessage
				.get("award_scale_end")
				+ "", 0);
		if (StringUtils.isNotBlank(excitationType)) {
			// 按借款金额比例奖励
			if (StringUtils.isNumericSpace(excitationType)
					&& "3".equals(excitationType)) {
				if (sumRateDouble < scalefirst || sumRateDouble > scaleend) {
					this.addFieldError("paramMap['sumRate']", "奖励比例填写不正确");
					return false;
				}
			}
		}
		// 借款额度
		double borrowMoneyfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_first")
				+ "", 0);
		double borrowMoneyend = Convert.strToDouble(tempBorrwBidMessage
				.get("amount_end")
				+ "", 0);
		if (borrowMoneyfirst > amountDouble || borrowMoneyend < amountDouble) {
			this.addFieldError("paramMap['amount']", "输入的借款总额不正确");
			return false;
		}
		// 借款额度倍数
		double accountmultiple = Convert.strToDouble(tempBorrwBidMessage
				.get("account_multiple")
				+ "", -1);
		if (accountmultiple != 0) {
			if (amountDouble % accountmultiple != 0) {
				this.addFieldError("paramMap['amount']", "输入的借款总额的倍数不正确");
				return false;
			}
		}
		// 年利率
		double aprfirst = Convert.strToDouble(tempBorrwBidMessage
				.get("apr_first")
				+ "", 0);
		double aprend = Convert.strToDouble(tempBorrwBidMessage.get("apr_end")
				+ "", 0);
		if (aprfirst > annualRateDouble || aprend < annualRateDouble) {
			this.addFieldError("paramMap['annualRate']", "输入的年利率不正确");
			return false;
		}
		return true;
	}

	/**
	 * @MethodName: circulationBorrowInit
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:33:18
	 * @Return:
	 * @Descb: 流转标借款初始化
	 * @Throws:
	 */
	public String circulationBorrowInit() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: circulationBorrowList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:35:50
	 * @Return:
	 * @Descb: 流转标借款
	 * @Throws:
	 */
	public String circulationBorrowList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String undertaker = paramMap.get("undertaker") == null ? "" : paramMap
				.get("undertaker");
		String borrowStatus = paramMap.get("borrowStatus") == null ? ""
				: paramMap.get("borrowStatus");
		int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
		borrowManageService.queryAllCirculationByCondition(userName, -1,
				borrowStatusInt, undertaker, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowCirculationDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午01:44:03
	 * @Return:
	 * @Descb: 流转标借款详情
	 * @Throws:
	 */
	public String borrowCirculationDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		if (borrowCirculationDetail == null) {
			// 初审中的借款详情
			borrowCirculationDetail = borrowManageService
					.queryBorrowCirculationDetailById(idLong);
		}
		return "success";
	}

	/**
	 * @MethodName: updateBorrowCirculation
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午03:23:42
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public String updateBorrowCirculation() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long borrowId = Convert.strToLong(id, -1);
		String reciver = paramMap.get("reciver");
		long reciverId = Convert.strToLong(reciver, -1);
		String status = paramMap.get("status");
		long statusLong = Convert.strToLong(status, -1);
		String auditOpinion = paramMap.get("auditOpinion");
		long result = -1;
		if (statusLong == -1) {
			obj.put("msg", "请选择审核状态");
			JSONUtils.printObject(obj);
			return null;
		}
		if (!StringUtils.isNotBlank(auditOpinion)) {
			obj.put("msg", "请填写风险控制措施");
			JSONUtils.printObject(obj);
			return null;
		} else if (auditOpinion.length() > 500) {
			obj.put("msg", "风险控制措施内容不能超过500字符");
			JSONUtils.printObject(obj);
			return null;
		}
		try {
			result = borrowManageService.updateBorrowCirculationStatus(
					borrowId, reciverId, statusLong, auditOpinion, admin
							.getId(), getBasePath(), getPlatformCost());
			operationLogService.addOperationLog("t_borrow",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "更新流转标的状态", 2);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result <= 0) {
			// 操作失败提示
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowManageFistAuditDetail
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午11:02:22
	 * @Return:
	 * @Descb: 后台借款管理的借款详情
	 * @Throws:
	 */
	public String borrowManageAllDetail() throws Exception {
		return "success";
	}

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public Map<String, String> getBorrowMFADetail() throws SQLException,
			DataException {
		return borrowMFADetail;
	}

	public Map<String, String> getBorrowMTenderInDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMTenderInDetail == null) {
			// 招标中的借款详情
			borrowMTenderInDetail = borrowManageService
					.queryBorrowTenderInDetailById(idLong);
			String nid_log = borrowMTenderInDetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
			}
		}
		// ---add by houli 屏蔽链接
		String mailContent = borrowMTenderInDetail.get("mailContent");
		String newStr = changeStr2Str(mailContent);
		borrowMTenderInDetail.put("mailContent", newStr);
		// ---------end
		return borrowMTenderInDetail;
	}

	public Map<String, String> getBorrowMFullScaleDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = request.getString("id") == null ? "" :request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMFullScaleDetail == null) {
			// 满标的借款详情
			borrowMFullScaleDetail = borrowManageService
					.queryBorrowFullScaleDetailById(idLong);
			String nid_log = borrowMFullScaleDetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
			}
		}
		// ---add by houli 屏蔽链接
		String mailContent = borrowMFullScaleDetail.get("mailContent");
		String newStr = changeStr2Str(mailContent);
		borrowMFullScaleDetail.put("mailContent", newStr);
		// ---------end
		return borrowMFullScaleDetail;
	}

	public Map<String, String> getBorrowMFlowMarkDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> TypeLogMap = null;
		if (borrowMFlowMarkDetail == null) {
			// 流标的借款详情
			borrowMFlowMarkDetail = borrowManageService
					.queryBorrowFlowMarkDetailById(idLong);
			String nid_log = borrowMFlowMarkDetail.get("nid_log");
			if (StringUtils.isNotBlank(nid_log)) {
				TypeLogMap = shoveBorrowTypeService
						.queryBorrowTypeLogByNid(nid_log.trim());
				int stauts = Convert.strToInt(TypeLogMap
						.get("subscribe_status"), -1);
				request().setAttribute("subscribes", stauts);
			}
		}
		return borrowMFlowMarkDetail;
	}

	public String circulationRepayRecordInit() throws SQLException,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: circulationBorrowList
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:35:50
	 * @Return:
	 * @Descb: 流转标借款
	 * @Throws:
	 */
	public String circulationRepayRecordList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String borrowTitle = paramMap.get("borrowTitle") == null ? ""
				: paramMap.get("borrowTitle");
		String borrowStatus = paramMap.get("borrowStatus") == null ? ""
				: paramMap.get("borrowStatus");
		int borrowStatusInt = Convert.strToInt(borrowStatus, -1);
		borrowManageService.queryAllCirculationRepayRecordByCondition(userName,
				borrowStatusInt, borrowTitle, pageBean);
		return "success";
	}

	/**
	 * @MethodName: circulationRepayForAdd
	 * @Param: BorrowManageAction
	 * @Return:
	 * @Descb: 流转标还款详情添加初始化
	 * @Throws:
	 */
	public String circulationRepayForAdd() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: circulationRepayAdd
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午03:48:45
	 * @Return:
	 * @Descb: 添加流转标详情
	 * @Throws:
	 */
	public String circulationRepayAdd() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Admin admin = (Admin) session().getAttribute("admin");
		String amount = paramMap.get("amount");
		double amountDouble = Convert.strToDouble(amount, -1);
		String remark = paramMap.get("remark");
		Object id = session().getAttribute("repayId");
		long repayId = Convert.strToLong(id + "", -1);
		if (repayId == -1) {
			this.addFieldError("paramMap['action']", "操作失败");
			return "input";
		}
		if (amountDouble == -1 || amountDouble <= 0) {
			this.addFieldError("paramMap['amount']", "金额格式错误");
			return "input";
		}
		if (remark.length() > 500) {
			this.addFieldError("paramMap['remark']", "备注不能超过500字符");
			return "input";
		}
		long returnId = -1;
		returnId = borrowManageService.addCirculationRepay(repayId,
				amountDouble, admin.getId(), remark);
		operationLogService.addOperationLog("t_borrow", admin.getUserName(),
				IConstants.INSERT, admin.getLastIP(), 0, "添加流转标还款记录", 2);

		if (returnId < 1) {
			this.addFieldError("paramMap['action']", "操作失败");
			return "input";
		}
		borrowId = session().getAttribute("borrowId");
		;
		return "success";
	}

	public Map<String, String> getBorrowMAllDetail() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		if (borrowMAllDetail == null) {
			// 所以的借款详情
			borrowMAllDetail = borrowManageService
					.queryBorrowAllDetailById(idLong);
		}
		return borrowMAllDetail;
	}

	/**
	 * 验证用户资料信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isNotUnderCoirculationBorrow() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long uId = request.getLong("i", -1);
		Map<String, String> userMap;
		try {
			userMap = userService.queryUserById(uId);
			if (Convert.strToInt(userMap.get("vipStatus"), 0) == IConstants.UNVIP_STATUS) {// 没有成为VIP
				JSONUtils.printStr("3");
				return null;
			}
			if (Convert.strToInt(userMap.get("authStep"), 0) == 1) {
				// 基本信息认证步骤
				JSONUtils.printStr("7");
				return null;
			} else if (Convert.strToInt(userMap.get("authStep"), 0) == 2) {
				// 工作信息认证步骤
				JSONUtils.printStr("4");
				return null;
			} else if (Convert.strToInt(userMap.get("authStep"), 0) == 3) {
				// VIP申请认证步骤
				JSONUtils.printStr("5");
				return null;
			} else if (Convert.strToInt(userMap.get("authStep"), 0) == 4) {
				// 上传资料认证步骤
				JSONUtils.printStr("6");
				return null;
			}
			Map<String, String> map = dataApproveService.querySauthId(uId,
					IConstants.FLOW_PHONE);
			if (map == null) {
				JSONUtils.printStr("2");// 手机认证
				return null;
			} else {
				Long sauthId = Convert.strToLong(map.get("id"), -1L);
				Long status = dataApproveService.queryApproveStatus(sauthId);
				if (status < 0) {
					JSONUtils.printStr("8");// 手机认证待审核
					return null;
				}
			}
			// 条件满足
			JSONUtils.printStr("1");
			return null;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @throws Exception
	 * @MethodName: underCirculationBorrow
	 * @Param: BorrowManageAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午04:38:02
	 * @Return:
	 * @Descb: 代发流转标
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String underCirculationBorrow() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String uId = request.getString("i") == null ? "" : request.getString("i");
		session().setAttribute("uId", uId);

		Map<String, String> tempBorrwBidMessage = new HashMap<String, String>();
		try {
			tempBorrwBidMessage = shoveBorrowTypeService
					.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
			// 取得按借款金额的比例进行奖励
			paramMap.put("scalefirst", tempBorrwBidMessage
					.get("award_scale_first")
					+ "");
			paramMap.put("scaleend", tempBorrwBidMessage.get("award_scale_end")
					+ "");
			// 如果选择金额的话，则按此奖励的金额范围
			paramMap.put("accountfirst", tempBorrwBidMessage
					.get("award_account_first")
					+ "");
			paramMap.put("accountend", tempBorrwBidMessage
					.get("award_account_end")
					+ "");
			// 借款额度
			paramMap.put("borrowMoneyfirst", tempBorrwBidMessage
					.get("amount_first")
					+ "");
			paramMap.put("borrowMoneyend", tempBorrwBidMessage
					.get("amount_end")
					+ "");
			// 借款额度倍数
			paramMap.put("accountmultiple", tempBorrwBidMessage
					.get("account_multiple")
					+ "");
			// 年利率
			paramMap.put("aprfirst", tempBorrwBidMessage.get("apr_first") + "");
			paramMap.put("aprend", tempBorrwBidMessage.get("apr_end") + "");
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}

		return "success";
	}

	public DataApproveService getDataApproveService() {
		return dataApproveService;
	}

	public void setDataApproveService(DataApproveService dataApproveService) {
		this.dataApproveService = dataApproveService;
	}

	private String changeStr2Str(String mailContent) {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (mailContent != null && !mailContent.equals("")) {
			int ind1 = mailContent.indexOf("<");
			int ind2 = mailContent.indexOf(">");
			if (ind1 < 0 || ind2 < 0 || ind2 <= ind1) {
				return mailContent;
			}
			String newStr = mailContent.substring(0, ind1)
					+ mailContent.substring(ind2 + 1);
			// 处理<a>链接的结束标签
			newStr = newStr.replace("</a>", "");
			return newStr;
		}
		return mailContent;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public List<Map<String, Object>> getCirList() {
		return cirList;
	}

	public void setCirList(List<Map<String, Object>> cirList) {
		this.cirList = cirList;
	}

	public Map<String, String> getBorrowCirculationDetail() {
		return borrowCirculationDetail;
	}

	public void setBorrowCirculationDetail(
			Map<String, String> borrowCirculationDetail) {
		this.borrowCirculationDetail = borrowCirculationDetail;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public Object getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Object borrowId) {
		this.borrowId = borrowId;
	}

	public static Log getLog() {
		return log;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws Exception {
		if (borrowPurposeList == null) {
			// 借款目的列表
			borrowPurposeList = selectedService.borrowPurpose();
		}
		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {
		if (borrowDeadlineList == null) {
			// 借款期限列表
			borrowDeadlineList = selectedService.borrowDeadline();
		}
		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getBorrowRaiseTermList() throws Exception {
		if (borrowRaiseTermList == null) {
			// 筹款期限列表
			borrowRaiseTermList = selectedService.borrowRaiseTerm();
		}
		return borrowRaiseTermList;
	}

	public List<Map<String, Object>> getSysImageList() throws Exception {
		if (sysImageList == null) {
			// 系统列表
			sysImageList = selectedService.sysImageList();
		}
		return sysImageList;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowAmountList() {
		return borrowAmountList;
	}

	public void setBorrowAmountList(List<Map<String, Object>> borrowAmountList) {
		this.borrowAmountList = borrowAmountList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ShoveBorrowTypeService getShoveBorrowTypeService() {
		return shoveBorrowTypeService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setBorrowMFADetail(Map<String, String> borrowMFADetail) {
		this.borrowMFADetail = borrowMFADetail;
	}

	public void setBorrowMTenderInDetail(
			Map<String, String> borrowMTenderInDetail) {
		this.borrowMTenderInDetail = borrowMTenderInDetail;
	}

	public void setBorrowMFullScaleDetail(
			Map<String, String> borrowMFullScaleDetail) {
		this.borrowMFullScaleDetail = borrowMFullScaleDetail;
	}

	public void setBorrowMFlowMarkDetail(
			Map<String, String> borrowMFlowMarkDetail) {
		this.borrowMFlowMarkDetail = borrowMFlowMarkDetail;
	}

	public void setBorrowMAllDetail(Map<String, String> borrowMAllDetail) {
		this.borrowMAllDetail = borrowMAllDetail;
	}

	public void setBorrowPurposeList(List<Map<String, Object>> borrowPurposeList) {
		this.borrowPurposeList = borrowPurposeList;
	}

	public void setBorrowDeadlineList(
			List<Map<String, Object>> borrowDeadlineList) {
		this.borrowDeadlineList = borrowDeadlineList;
	}

	public void setBorrowRaiseTermList(
			List<Map<String, Object>> borrowRaiseTermList) {
		this.borrowRaiseTermList = borrowRaiseTermList;
	}

	public void setSysImageList(List<Map<String, Object>> sysImageList) {
		this.sysImageList = sysImageList;
	}

	public List<Map<String, Object>> getBorrowTurnlineList() throws Exception {
		if (borrowTurnlineList == null) {
			// 借款期限列表
			// borrowDeadlineList = selectedService.borrowDeadline();
			// 获取的到相应的map
			Map<String, String> borrowTrunlineMap;
			try {
				borrowTrunlineMap = shoveBorrowTypeService
						.queryShoveBorrowTypeByNid(IConstants.BORROW_TYPE_FLOW);
				borrowTurnlineList = new ArrayList<Map<String, Object>>();
				if (borrowTrunlineMap != null && borrowTrunlineMap.size() > 0) {
					String trunmonth = Convert.strToStr(borrowTrunlineMap
							.get("period_month")
							+ "", "");
					String[] trunmonths = trunmonth.split(",");// 截取;符号
					// 放入String数组
					if (trunmonths != null) {
						String str = " 个月";
						for (String file : trunmonths) {// 遍历String数组
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("key", file.trim());
							map.put("value", file + str);
							borrowTurnlineList.add(map);
						}
					}

				}
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}

		}
		return borrowTurnlineList;
	}
	
	/*
	 * 相关材料初始化
	 */
	public String relatedInit() throws SQLException{
	    long id = request.getLong("id", -1);
	    request().setAttribute("id", id);
	    return SUCCESS;
	}
	
	/*
	 * 查询所有的相关材料
	 */
	public String relatedList() throws SQLException{
	    long id = request.getLong("id", -1);
        request().setAttribute("id", id);
        borrowManageService.queryRelated(id, pageBean);
	    return SUCCESS;
	}
	
	/*
	 * 新增相关材料初始化
	 */
	public String addRelatedImgInit() throws SQLException{
	    long id = request.getLong("id", -1);
        request().setAttribute("id", id);
	    imgtype =  sysparService.querySysparAllChild("*", "  typeId = 14", "id desc", -1, -1);
	    return SUCCESS;
	}
	
	/*
	 * 新增相关材料
	 */
	public String addRelatedImgInfo() throws SQLException{
	    relate_borrowid = request.getLong("id", -1);
        request().setAttribute("id", relate_borrowid);
        paramMap.put("id", relate_borrowid+"");
        long result = borrowManageService.addRelated(paramMap);
        if (result<0) {
            imgtype =  sysparService.querySysparAllChild("*", "  typeId = 14", "id desc", -1, -1);
            return INPUT;
        }
	    return SUCCESS;
	}
	
	/*
	 * 验证新增相关材料
	 */
	public String validateAddRelatedImgInfo() throws SQLException{
	    long id = request.getLong("id", -1);
        request().setAttribute("id", id);
        imgtype =  sysparService.querySysparAllChild("*", "  typeId = 14", "id desc", -1, -1);
        
        if (paramMap==null) {
            this.addFieldError("actionMsg", "保存信息已丢失，请重新输入");
        }
        
        if (id==-1) {
            this.addFieldError("actionMsg", "标的ID已丢失，请重新操作");
        }
        
        if (StringUtils.isBlank(paramMap.get("imgPath"))) {
            this.addFieldError("paramMap['imgPath']", "请上传图片");
        }
        
        if (StringUtils.isBlank(paramMap.get("type"))) {
            this.addFieldError("paramMap['type']", "请选择类型");
        }
        
        if (StringUtils.isBlank(paramMap.get("name"))) {
            this.addFieldError("paramMap['name']", "请输入名称");
        }
        
        if (StringUtils.isBlank(paramMap.get("isa"))) {
            this.addFieldError("paramMap['isa']", "请选择是否打码");
        }
        
        if (hasErrors()) {
            return INPUT;
        }
        
	    return SUCCESS;
	}
	
	
	/*
	 * 删除相关材料
	 */
	public String deleteRelatedImg() throws SQLException{
	    relate_borrowid = request.getLong("id", -1);
	    String commonId = request.getString("commonId");
	    borrowManageService.deleteRelated(commonId);
	    return SUCCESS;
	}
	
	/*
	 * 更新相关材料初始化
	 */
	public String updateRelatedImgInit() throws SQLException{
	    long id = request.getLong("id", -1);
	    long borrowId = request.getLong("borrowId", -1);
        request().setAttribute("id", borrowId);
        imgtype =  sysparService.querySysparAllChild("*", "  typeId = 14", "id desc", -1, -1);
        paramMap = borrowManageService.queryRelated(id);
	    return SUCCESS;
	}
	
	/*
	 * 更新相关材料
	 */
	public String updateRelatedImgInfo() throws SQLException{
	    relate_borrowid = request.getLong("id", -1);
        request().setAttribute("id", relate_borrowid);
        paramMap.put("borrowId", relate_borrowid+"");
        long result = borrowManageService.updateRelated(paramMap);
        if (result<0) {
            imgtype =  sysparService.querySysparAllChild("*", "  typeId = 14", "id desc", -1, -1);
            return INPUT;
        }
        return SUCCESS;
	}
	
	/*
     * 验证新增相关材料
     */
    public String validateUpdateRelatedImgInfo() throws SQLException{
        long id = request.getLong("id", -1);
        request().setAttribute("id", id);
        imgtype =  sysparService.querySysparAllChild("*", "  typeId = 14", "id desc", -1, -1);
        
        if (paramMap==null) {
            this.addFieldError("actionMsg", "保存信息已丢失，请重新输入");
        }
        
        if (id==-1) {
            this.addFieldError("actionMsg", "标的ID已丢失，请重新操作");
        }
        
        if (StringUtils.isBlank(paramMap.get("imgPath"))) {
            this.addFieldError("paramMap['imgPath']", "请上传图片");
        }
        
        if (StringUtils.isBlank(paramMap.get("type"))) {
            this.addFieldError("paramMap['type']", "请选择类型");
        }
        
        if (StringUtils.isBlank(paramMap.get("name"))) {
            this.addFieldError("paramMap['name']", "请输入名称");
        }
        
        if (StringUtils.isBlank(paramMap.get("isa"))) {
            this.addFieldError("paramMap['isa']", "请选择是否打码");
        }
        
        if (hasErrors()) {
            return INPUT;
        }
        
        return SUCCESS;
    }
	
    /*
     * 借款申请列表初始化
     */
    public String borrowAskMethod() throws SQLException{
        List<Map<String, Object>> syspar = sysparService.querySysparAllChild("selectKey,selectName", "typeId=17 and  deleted=1", "", -1   , -1);
        request().setAttribute("syspar", syspar);
        return SUCCESS;
    }
    
    /*
     * 借款申请列表查询
     */
    public String borrowAskListMethod() throws SQLException{
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String contact_name = paramMap.get("contact_name");
        String contact_phone = paramMap.get("contact_phone");
        String createTimeEnd =  paramMap.get("createTimeEnd");
        String createTimeStart = paramMap.get("createTimeStart");
        String borrowerType = paramMap.get("borrowerType");
        String borrowWay = paramMap.get("borrowWay");
        String hasDistribution = paramMap.get("hasDistribution");
        String hasMortgage = paramMap.get("hasMortgage");
        borrowManageService.queryBorrowAskList(pageBean,-1, contact_phone, contact_name, createTimeStart, createTimeEnd, borrowWay, borrowerType, hasMortgage, hasDistribution);
        return SUCCESS;
    }
    /*
     * 借款申请详情
     */
    public String borrowAskCheckMethod() throws SQLException{
        long id = request.getLong("id", -1);
        borrowManageService.queryBorrowAskList(pageBean,id, null, null, null, null, null, null, null, null);
        request().setAttribute("map", pageBean.getPage().get(0));
        return SUCCESS;
    }
    
    /*
     * 分配初始
     */
    public String borrowAskFenpeiInitMethod() throws Exception{
        long id = request.getLong("id", -1);
        List<Map<String, Object>> map = validateService.queryServiceNameByI();
        request().setAttribute("map", map);
        request().setAttribute("id", id);
        return SUCCESS;
    }
    /*
     * 分配
     */
    public String borrowAskFenpeiMethod() throws Exception{
        long id = Convert.strToLong(paramMap.get("id"), -1);
        long userId = Convert.strToLong(paramMap.get("userId"), -1);
        long result = borrowManageService.fenPei(id, userId);
        if (result>0) {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("t_borrow_new",
                                                admin.getUserName(), IConstants.INSERT, admin.getLastIP(),
                                                0, "分配借款申请管理人员", 2);
            JSONUtils.printStr("1");
        }else {
            JSONUtils.printStr("-1");
        }
        return null;
    }
    
    /*
     * 员工列表初始化
     */
    public String employeeInitMethod() throws SQLException{
        List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
         return SUCCESS;
    }
    /*
     * 员工列表
     */
    public String employeeInfoMethod() throws SQLException{
        String username = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String phone = paramMap.get("phone");
        String hasWork = paramMap.get("hasWork");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        borrowManageService.queryEmployee(pageBean,username,realName,department,phone,hasWork,createTimeStart,createTimeEnd);
        return SUCCESS;
    }
    
   /*
    * 导出员工列表
    */
    public String employeeInfoExportMethod() throws Exception{
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        String username = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String phone = paramMap.get("phone");
        String hasWork = paramMap.get("hasWork");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        borrowManageService.queryEmployee(pageBean,username,realName,department,phone,hasWork,createTimeStart,createTimeEnd);
        if (pageBean.getPage() == null) {
            getOut()
                    .print(
                            "<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
            return null;
        }
        if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
            getOut()
                    .print(
                            "<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
            return null;
        }
        
        try {
            HSSFWorkbook wb = ExcelUtils.exportExcel("公司员工",
                                                     pageBean.getPage(),
                                                     new String[] { "用户名", "真实姓名", "部门", "职位", "邮箱",
                                                             "手机号码", "注册时间", "入职时间" ,"是否离职" ,"离职时间" }, new String[] {
                                                             "username", "realName", "selectName",
                                                             "job", "email", "cellPhone",
                                                             "createTime", "entrytime", "hasWork" , "leavetime" });
                                             this.export(wb, new Date().getTime() + ".xls");
                                             Admin admin = (Admin) session().getAttribute(
                                                     IConstants.SESSION_ADMIN);
                                             operationLogService.addOperationLog("v_t_employee",
                                                     admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
                                                     0, "公司员工列表", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
           getOut()
           .print(
                   "<script>alert(' 导出记录出现异常! ');window.history.go(-1);</script>");
        }
        return null;
    }
    
    /*
     * 添加员工初始化
     */
    public String addEmployeeInitMethod() throws SQLException{
        List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
        //request().setAttribute("date", DateUtil.dateToString(new Date()));
        return SUCCESS;
    }
    
    /*
     * 添加员工
     */
    public String addEmployeeMethod() throws Exception{
        long userId = Convert.strToLong(paramMap.get("userId"), -1L);
        long queryResult = borrowManageService.queryEmployeeBindUser(userId);
        if (queryResult>0) {//员工已经跟平台用户绑定了
            JSONUtils.printStr("3");
            return null;
        }
        long result = borrowManageService.addEmployee(paramMap);
        if (result>0) {
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("t_employee",
                                                admin.getUserName(), IConstants.INSERT, admin.getLastIP(),
                                                0, "添加员工", 2);
            JSONUtils.printStr("1");
        }else {
            JSONUtils.printStr("2");
        }
        return null;
    }
    
    /*
     * 搜索注册用户
     */
    public String searchEmployeeMethod() throws Exception{
        String username  = paramMap.get("username");
        Map<String, String>  map  =   userService.queryUserByUsername(username);
        if (map!=null) {
            JSONUtils.printObject(map);
        }else {
            JSONUtils.printObject(null);
        }
        return null;
    }
    
    /*
     * 离职
     */
    public String leaveWork() throws Exception{
        long id = request.getLong("id", -1);
        if (id==-1) {
            JSONUtils.printStr("2");
        }else {
            long result = borrowManageService.leaveWork(id);
            if (result>0) {
                Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
                operationLogService.addOperationLog("t_employee",admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),0, "操作员工离职", 2);
                JSONUtils.printStr("1");
            }else {
                JSONUtils.printStr("2");
            }
        }
        return null;
    }
    
    /*
     * 更新员工信息
     */
    public  String updateEmployeeInitMethod() throws Exception{
        long id = request.getLong("id", -1L);
        if (id==-1) {
            getOut().print("<script>alert('参数丢失');window.history.go(-1);</script>");
            return null;
        }
        List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
        setParamMap(borrowManageService.queryEmployeeById(id));
        return SUCCESS;
    }
    
    /*
     * 更新员工信息
     */
    public String updateEmployeeMethod() throws Exception{
         long id = Convert.strToLong(paramMap.get("id"), -1L);
         String department = paramMap.get("department");
         String job = paramMap.get("job");
         String entryTime = paramMap.get("entrytime");
         String hasWork = paramMap.get("hasWork");
         String leavetime = paramMap.get("leavetime");
         long result = borrowManageService.updateEmployee(id, department, job, entryTime,hasWork,leavetime);
         if (result>0) {
             Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
             operationLogService.addOperationLog("t_employee",admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),0, "更新员工信息", 2);
            JSONUtils.printStr("1");
        }else {
            JSONUtils.printStr("2");
        }
         return null;
    }
    
    
    /**
	 * 克隆图片
	 */
	public String cloneImgInit() throws SQLException{
	    long id = request.getLong("id", -1);
	    request().setAttribute("id", id);
	    return SUCCESS;
	}
	
	/**
	 *功能：得到借款标题
	 * @return
	 */
	public String queryBorrowTitle(){
		try {
			JSONObject obj = new JSONObject();
			String idstr = request().getParameter("id");
			if (StringUtil.isEmpty(idstr)) {
				JSONUtils.printStr("请输入ID");
				return null;
			}
			Map map = borrowManageService.queryBorrowAllDetailById(Long.parseLong(idstr));
			if (null == map || null == map.get("borrowTitle")){
				obj.put("ret", "err");//输入错误，没有找到
				JSONUtils.printObject(obj);
//				JSONUtils.printStr("输入错误，没有找到");
				return null;
			}
			obj.put("ret", (String) map.get("borrowTitle"));
			JSONUtils.printObject(obj);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *功能：复制老标的材料
	 * @return
	 */
	public String copyBorrowImage(){
		try {
			String fromId = request().getParameter("fromId");
			String toId = request().getParameter("toId");
			if (StringUtil.isEmpty(toId)) {
				JSONUtils.printStr("请输入ID");
				return null;
			}
			borrowManageService.copyBorrowImage(Long.parseLong(fromId),Long.parseLong(toId));
//			if (null == map || null == map.get("borrowTitle")){
//				JSONUtils.printStr("输入错误，没有找到");
//				return null;
//			}
//			JSONObject obj = new JSONObject();
//			obj.put("ret", "操作成功");
//			JSONUtils.printObject(obj);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public void setBorrowTurnlineList(
			List<Map<String, Object>> borrowTurnlineList) {
		this.borrowTurnlineList = borrowTurnlineList;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}
}