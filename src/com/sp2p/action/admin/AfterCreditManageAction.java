package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.FrontMyPaymentService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AfterCreditManageService;

/**
 * @ClassName: AfterCreditManageAction.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:22:02
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 贷后管理控制层
 */
public class AfterCreditManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(AfterCreditManageAction.class);
	private static final long serialVersionUID = 1L;

	private AfterCreditManageService afterCreditManageService;
	private SelectedService selectedService;

	private List<Map<String, Object>> borrowDeadlineList;
	private List<Map<String, Object>> userGroupList;
	
	private FrontMyPaymentService frontpayService;
	
	private UserService userService;
	
	
	
	public FrontMyPaymentService getFrontpayService() {
        return frontpayService;
    }

    public void setFrontpayService(FrontMyPaymentService frontpayService) {
        this.frontpayService = frontpayService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
	 * @MethodName: lastRepayMentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午10:26:42
	 * @Return:
	 * @Descb: 最近3天还款记录初始化
	 * @Throws:
	 */
	public String lastRepayMentInit() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}

	/**
	 * @MethodName: forPaymentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:00
	 * @Return:
	 * @Descb: 待收款记录初始化
	 * @Throws:
	 */
	public String forPaymentInit() {
		return "success";
	}

	/**
	 * @MethodName: forPaymentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:00
	 * @Return:
	 * @Descb: 待收款记录初始化
	 * @Throws:
	 */
	public String forPaymentDueInInit() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);// 得到前7
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(c.getTime());
		String strStart = mDateTime.substring(0, 16);//
		request().setAttribute("strStart", strStart);
		return "success";
	}

	/**
	 * @MethodName: forPaymentTotalInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:06:48
	 * @Return:
	 * @Descb: 代收款统计初始化
	 * @Throws:
	 */
	public String forPaymentTotalInit() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 7);// 得到前7
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(c.getTime());
		String strStart = mDateTime.substring(0, 16);//
		request().setAttribute("strStart", strStart);
		return "success";
	}

	/**
	 * @MethodName: lateRepayInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:22
	 * @Return:
	 * @Descb: 逾期的还款初始化
	 * @Throws:
	 */
	public String lateRepayInit() {
		return "success";
	}

	/**
	 * @MethodName: overduePaymentInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:13:58
	 * @Return:
	 * @Descb: 逾期垫付初始化
	 * @Throws:
	 */
	public String overduePaymentInit() {
		return "success";
	}

	/**
	 * @MethodName: hasRepayInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午10:14:29
	 * @Return:
	 * @Descb: 已还款初始化
	 * @Throws:
	 */
	public String hasRepayInit() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -7);// 得到前7
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(c.getTime());
		String strStart = mDateTime.substring(0, 16);//
		request().setAttribute("strStart", strStart);

		request().setAttribute("endStart", formatter.format(new Date()));
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: lastRepayMentList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午10:26:54
	 * @Return:
	 * @Descb: 最近3天还款记录
	 * @Throws:
	 */
	public String lastRepayMentList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap.get("userName");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap.get("borrowWay");
		String realName = paramMap.get("realName") == null ? "" : paramMap.get("realName");
		String title = paramMap.get("title") == null ? "" : paramMap.get("title");
		String status = paramMap.get("status") == null ? "" : paramMap.get("status");
		String type = paramMap.get("type") == null ? "" : paramMap.get("type");
		String orgName = paramMap.get("orgName") == null ? "" : paramMap.get("orgName");
		String userType = paramMap.get("userType") == null ? "" : paramMap.get("userType");
		int statusInt = Convert.strToInt(status, -1);
		int borrowWayInt = Convert.strToInt(borrowWay, -1);

		afterCreditManageService.queryLastRepayMentByCondition(userName,orgName,userType,
				borrowWayInt, realName, title, statusInt, type, pageBean);
		// 最近借款统计总额
		Map<String, String> repaymentMap = afterCreditManageService
				.queryRepaymentAmount(userName,orgName,userType, borrowWayInt, realName, title,
						statusInt, type);
		request().setAttribute("repaymentMap", repaymentMap);
		// 当前页金额统计
		double currentAmount = 0;
		List<Map<String, Object>> mapList = pageBean.getPage();
		if (mapList != null) {
			for (Map<String, Object> map : mapList) {
				currentAmount = currentAmount
						+ Convert.strToDouble(map.get("totalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("currentAmount", fmt.format(currentAmount));

		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出最近3天还款记录excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportlastRepayMent() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		String type = request.getString("type");

		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			// 用户名
			String userName = request.getString("userName") == null ? "": request.getString("userName");
			userName = URLDecoder.decode(userName, "UTF-8");// 中文乱码转换
			// 真实姓名
			String realName = request.getString("realName") == null ? "": request.getString("realName");
			realName = URLDecoder.decode(realName, "UTF-8");// 中文乱码转换
			// 借款标题
			String titles = request.getString("titles") == null ? "": request.getString("titles");
			titles = URLDecoder.decode(titles, "UTF-8");
			//企业全称
			String orgName = request.getString("orgName") == null ? "": request.getString("orgName");
			orgName = URLDecoder.decode(orgName, "UTF-8");// 中文乱码转换
			//用户类型
			String userType = request.getString("userType") == null ? "": request.getString("userType");
			userType = URLDecoder.decode(userType, "UTF-8");// 中文乱码转换
			// 借款类型
			int borrowWay = request.getInt("borrowWay", -1);
			// 还款状态
			int status = request.getInt("status", -1);
			// 待还款详情
			afterCreditManageService.queryLastRepayMentByCondition(userName,orgName,userType,
					borrowWay, realName, titles, status, type, pageBean);
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
			afterCreditManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = null;
			if (type.equals("") || type == null) {
				wb = ExcelUtils.exportExcel("今天到期还款", pageBean.getPage(),
						new String[] { "用户名", "真实姓名", "企业全称", "用户类型", "借款标题", "期数", "类型",
								"到期时间", "应还金额(￥)", "还款时间", "跟踪客服", "是否已还款" },
						new String[] { "username", "realName", "orgName", "userType",
								"borrowTitle", "repayPeriod", "borrowWay",
								"repayDate", "totalSum", "realRepayDate",
								"servier", "repayStatus" });
				// 添加操作日志
				operationLogService.addOperationLog("v_t_repayment_h", admin
						.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
						"导出今天到期还款", 2);
			} else if (type.equals("1")) {
				wb = ExcelUtils.exportExcel("明天到期还款", pageBean.getPage(),
						new String[] { "用户名", "真实姓名", "企业全称", "用户类型", "借款标题", "期数", "类型",
								"到期时间", "应还金额(￥)", "还款时间", "跟踪客服", "是否已还款" },
						new String[] { "username", "realName", "orgName", "userType",
								"borrowTitle", "repayPeriod", "borrowWay",
								"repayDate", "totalSum", "realRepayDate",
								"servier", "repayStatus" });
				// 添加操作日志
				operationLogService.addOperationLog("v_t_repayment_h", admin
						.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
						"导出明天到期还款", 2);
			} else if (type.equals("2")) {
				wb = ExcelUtils.exportExcel("后天到期还款", pageBean.getPage(),
						new String[] { "用户名", "真实姓名", "企业全称", "用户类型", "借款标题", "期数", "类型",
								"到期时间", "应还金额(￥)", "还款时间", "跟踪客服", "是否已还款" },
						new String[] { "username", "realName", "orgName", "userType",
								"borrowTitle", "repayPeriod", "borrowWay",
								"repayDate", "totalSum", "realRepayDate",
								"servier", "repayStatus" });
				// 添加操作日志
				operationLogService.addOperationLog("v_t_repayment_h", admin
						.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
						"导出后天到期还款", 2);
			}
			this.export(wb, new Date().getTime() + ".xls");
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 应收款款详情
	public String forPaymentList() throws Exception {
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String title = paramMap.get("title") == null ? "" : paramMap
				.get("title");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		int groupInt = Convert.strToInt(group, -1);
		// ----------add by houli 判断是否反选
		boolean inverse = paramMap.get("inverse") == null ? false : true;
		// --------------
		afterCreditManageService.queryForPaymentByCondition(investor,
				timeStart, timeEnd, title, borrowWayInt, groupInt, pageBean,
				inverse,userType);
		// 应收款款统计总额

		Map<String, String> repaymentMap = afterCreditManageService
				.queryForPaymentAmount(investor, timeStart, timeEnd, title,
						borrowWayInt, groupInt, inverse,userType);
		request().setAttribute("repaymentMap", repaymentMap);

		// 统计当前页应收款
		double receivableAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				receivableAmount = receivableAmount
						+ Convert.strToDouble(map.get("forTotalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request()
				.setAttribute("receivableAmount", fmt.format(receivableAmount));

		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出应收款详情
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportforPayment() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			// ---------add by houli 看是否是反选
			boolean inverse = request.getString("inverse").equals(
					"checked") ? true : false;
			// --------
			int group = request.getInt("groupId", -1);
			int borrowWay = request.getInt("borrowWayid", -1);
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			String title = request.getString("titles") == null ? ""
					: request.getString("titles");
			String userType = request.getString("userType") == null ? ""
					: request.getString("userType");
			// 转换中文乱码
			title = URLDecoder.decode(title, "UTF-8");
			investor = URLDecoder.decode(investor, "UTF-8");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			userType = URLDecoder.decode(userType, "UTF-8");
			// --------

			// 待还款详情
			afterCreditManageService.queryForPaymentByCondition(investor,
					timeStart, timeEnd, title, borrowWay, group, pageBean,
					inverse,userType);
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
			
			afterCreditManageService.changeNumToStr2(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("应收款详情", pageBean
					.getPage(), new String[] { "ID", "投资人", "姓名",// modify by houli
															// 添加用户组
					"用户类型", "用户组", "投资时间", "应收时间", "还款期数/总期数", "应收金额" }, new String[] {
					"id", "investor", "realName", "userType", "groupName", "investTime", "repayDate",
					"repayPeriod", "forTotalSum" });
			this.export(wb, new Date().getTime() + ".xls");

			operationLogService.addOperationLog("v_t_forpayment_h", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出应收款列表", 2);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 待还款详情
	public String forPaymentDueInList() throws Exception {
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String title = paramMap.get("title") == null ? "" : paramMap
				.get("title");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		int groupInt = Convert.strToInt(group, -1);
		// ----------add by houli 判断是否反选
		boolean inverse = paramMap.get("inverse") == null ? false : true;
		// --------------
		afterCreditManageService.queryForPaymentByDueIn(userType,investor, timeStart,
				timeEnd, title, borrowWayInt, groupInt, pageBean, inverse);
		// 待收款款统计总额

		Map<String, String> repaymentMap = afterCreditManageService
				.queryForPaymentAmount(investor, timeStart, timeEnd, title,
						borrowWayInt, groupInt, inverse,userType);
		request().setAttribute("repaymentMap", repaymentMap);
		System.out.println("34Record 436" + repaymentMap);
		// 得到当前页待收款金额统计
		double repayAmount = 0;
		List<Map<String, Object>> repayList = pageBean.getPage();

		if (repayList != null) {
			for (Map<String, Object> map : repayList) {
				repayAmount = repayAmount
						+ Convert.strToDouble(map.get("forTotalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("repayAmount", fmt.format(repayAmount));

		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出待还款详情
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportforPaymentDueIn() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			// ---------add by houli 看是否是反选
			boolean inverse = request.getString("inverse").equals(
					"checked") ? true : false;
			// --------
			int group = request.getInt("groupId", -1);
			int borrowWay = request.getInt(
					"borrowWayid", -1);
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			String title = request.getString("titles") == null ? ""
					: request.getString("titles");
			String userType = request.getString("userType") == null ? ""
					: request.getString("userType");
			// 转换中文乱码
			title = URLDecoder.decode(title, "UTF-8");
			investor = URLDecoder.decode(investor, "UTF-8");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			userType = URLDecoder.decode(userType, "UTF-8");
			// --------

			// 待还款详情
			afterCreditManageService.queryForPaymentByDueIn(userType,investor,
					timeStart, timeEnd, title, borrowWay, group, pageBean,
					inverse);
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
			afterCreditManageService.changeNumToStr2(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("待还款列表", pageBean
					.getPage(), new String[] { "借款人",
					"真实姓名", "企业全称", "用户类型",// modify by houli 添加用户组
					 "用户组", "借款时间", "标旳类型", "借款标题", "是否天标 ", "期数/总期数  ",
					"应还时间 ", "应还金额  " }, new String[] { "username",
					"realNames", "orgName", "userType", "groupName", "publishTime",
					"borrowWay", "borrowTitle", "isDayThe", "repayPeriod", "repayDate",
					"forTotalSum" });
			this.export(wb, new Date().getTime() + ".xls");
			operationLogService.addOperationLog("v_t_deuin_list", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出待还款列表", 2);

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: forPaymentTotalList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:07:25
	 * @Return:
	 * @Descb: 待收款统计记录
	 * @Throws:
	 */
	public String forPaymentTotalList() throws Exception {
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String deadline = paramMap.get("deadline") == null ? "" : paramMap
				.get("deadline");
		int deadlineWayInt = Convert.strToInt(deadline, -1);
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		String reffere = paramMap.get("reffere") == null ? "" : paramMap
				.get("reffere");
		int groupInt = Convert.strToInt(group, -1);
		// -------add by houli 添加反选条件
		boolean inverse = paramMap.get("inverse") == null ? false : true;
		//
		afterCreditManageService
				.queryForPaymentTotalByCondition(userType,investor, timeStart, timeEnd,
						deadlineWayInt, groupInt, pageBean, inverse,reffere);
		// 待收款款总计统计总额
		Map<String, String> repaymentMap = afterCreditManageService
				.queryForPaymentTotalAmount(investor, timeStart, timeEnd,
						deadlineWayInt, groupInt, inverse,userType);
		request().setAttribute("repaymentMap", repaymentMap);
		// 得到当前页待收款统计
		double payAmount = 0;
		List<Map<String, Object>> payList = pageBean.getPage();
		if (payList != null) {
			for (Map<String, Object> map : payList) {
				payAmount = payAmount
						+ Convert.strToDouble(map.get("forTotalSum") + "", 0);
			}
		}
		DecimalFormat fmt = new DecimalFormat("0.00");
		request().setAttribute("payAmount", fmt.format(payAmount));
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出待收款总计
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportPaymentTotal() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			// 查看是否是反转
			boolean inverse = request.getString("inverse").equals(
					"checked") ? true : false;
			// --------
			int group = request.getInt("groupId", -1);
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			String userType = request.getString("userType") == null ? ""
					: request.getString("userType");
			String reffere = request.getString("reffere") == null ? ""
					: request.getString("reffere");
			int deadline = request.getInt("deadline",
					-1);
			investor = URLDecoder.decode(investor, "UTF-8");// 中文乱码转换
			timeStart = URLDecoder.decode(timeStart, "UTF-8");// 中文乱码转换
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");// 中文乱码转换
			// 中文乱码转换
			investor = URLDecoder.decode(investor, "UTF-8");
			userType = URLDecoder.decode(userType, "UTF-8");
			// 待还款详情
			afterCreditManageService.queryForPaymentTotalByCondition(userType,investor,
					timeStart, timeEnd, deadline, group, pageBean, inverse,reffere);
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
			afterCreditManageService.changeNumToStr2(pageBean);
			// --------add by houli 添加 "用户组"
			HSSFWorkbook wb = ExcelUtils.exportExcel("待收款详情", pageBean
					.getPage(), new String[] { "投资人", "姓名", "用户类型", "用户组", "投资金额",
					"投资占比", "借款时间", "标旳总金额 ", "标旳类型 ", "是否天标", "期数/总期数",
					"应收时间", "应收金额 ","推荐人" }, new String[] { "investor", "realName", "userType",
					"groupName", "investAmount", "scale", "publishTime",
					"borrowAmount", "borrowWay", "isDayThe", "repayPeriod",
					"repayDate", "forTotalSum","reffere" });
			this.export(wb, new Date().getTime() + ".xls");
			// 添加操作日志
			operationLogService.addOperationLog("v_t_forpayment_h", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出待收款总计列表", 2);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: lateRepayList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:02:43
	 * @Return:
	 * @Descb: 逾期还款记录
	 * @Throws:
	 */
	public String lateRepayList() throws Exception {
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		String status = paramMap.get("status") == null ? "" : paramMap
				.get("status");
		int statusInt = Convert.strToInt(status, -1);
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		afterCreditManageService.queryLateRepayByCondition(userName,userType,
				borrowWayInt, statusInt, pageBean);
		// 逾期的借款统计总额
		Map<String, String> lateRepayMap = afterCreditManageService
				.queryLateRepayAmount(userName,userType, borrowWayInt, statusInt);
		request().setAttribute("lateRepayMap", lateRepayMap);
		// 得到当前页逾期借款金额
		double lateAmount = 0;
		double lateFI = 0;
		double totalSum = 0;
		List<Map<String, Object>> lateList = pageBean.getPage();
		if (lateList != null) {
			for (Map<String, Object> map : lateList) {
				lateAmount = lateAmount
						+ Convert.strToDouble(map.get("repaySum") + "", 0);
				lateFI = lateFI
						+ Convert.strToDouble(map.get("lateFI") + "", 0);
				totalSum = totalSum
						+ Convert.strToDouble(map.get("totalSum") + "", 0);
			}
		}
		request().setAttribute("lateAmount", lateAmount);
		request().setAttribute("lateFI", lateFI);
		request().setAttribute("totalSum", totalSum);
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出逾期还款记录,或逾期垫付
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportlateRepay() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Integer status = request.getInt("status", -1);
		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			// 用户名
			String userName = request.getString("userName") == null ? ""
					: request.getString("userName");
			
			String userType = request.getString("userType") == null ? ""
					: request.getString("userType");
			// 中文乱码转换
			userName = URLDecoder.decode(userName, "UTF-8");
			userType = URLDecoder.decode(userType, "UTF-8");
			// 借款类型
			int borrowWay = request.getInt("borrowWay", -1);
			if (status != -1) {
				// 逾期垫付
				afterCreditManageService.queryOverduePaymentByCondition(
						userName,userType, borrowWay, status, pageBean);
			} else {
				// 逾期的还款
				afterCreditManageService.queryLateRepayByCondition(userName,userType,
						borrowWay, status, pageBean);
			}
			// 待还款详情
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
			afterCreditManageService.changeNumToStr4(pageBean);
			HSSFWorkbook wb = null;
			if (status == -1) {
				wb = ExcelUtils.exportExcel("逾期的还款", pageBean.getPage(),
						new String[] { "用户名", "真实姓名", "企业全称", "用户类型", "借款标题", "期数", "类型",
								"应还时间", "逾期天数", "应还金额(￥)", "逾期金额(￥)", "总还款(￥)",
								"网站待还", "还款状态" }, new String[] {
								"username", "realName", "orgName", "userType", "borrowTitle",
								"repayPeriod", "borrowWay", "repayDate",
								"lateDay", "totalSum", "lateFI", "repaySum",
								"isWebRepay", "repayStatus" });
				// 操作日志
				operationLogService.addOperationLog("v_t_overduepayment_h",
						admin.getUserName(), IConstants.EXCEL, admin
								.getLastIP(), 0, "导出逾期的还款列表", 2);
			} else {
				wb = ExcelUtils.exportExcel("逾期垫付", pageBean.getPage(),
						new String[] { "用户名", "真实姓名", "企业全称", "用户类型", "借款标题", "期数", "类型",
								"应还时间", "逾期天数", "应还金额(￥)", "逾期金额(￥)", "总还款(￥)",
								"网站待还", "还款状态" }, new String[] {
								"username", "realName", "orgName", "userType", "borrowTitle",
								"repayPeriod", "borrowWay", "repayDate",
								"lateDay", "totalSum", "lateFI", "repaySum",
								"isWebRepay", "repayStatus" });
				// 操作日志
				operationLogService.addOperationLog("v_t_laterepay_h", admin
						.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
						"导出逾期垫付列表", 2);
			}

			this.export(wb, new Date().getTime() + ".xls");
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: overduePaymentList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:40:57
	 * @Return:
	 * @Descb: 逾期垫付记录
	 * @Throws:
	 */
	public String overduePaymentList() throws Exception {
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		String status = paramMap.get("status") == null ? "" : paramMap
				.get("status");
		int statusInt = Convert.strToInt(status, -1);
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		afterCreditManageService.queryOverduePaymentByCondition(userName,userType,
				borrowWayInt, statusInt, pageBean);
		// 逾期垫付的借款统计总额
		Map<String, String> overduePaymentMap = afterCreditManageService
				.queryOverduePaymentAmount(userName,userType, borrowWayInt, statusInt);
		request().setAttribute("overduePaymentMap", overduePaymentMap);
		// 得到当前页逾期还款总金额
		double overAmount = 0;
		double lateFI = 0;
		double totalSum = 0;
		List<Map<String, Object>> overList = pageBean.getPage();
		if (overList != null) {
			for (Map<String, Object> map : overList) {
				overAmount = overAmount
						+ Convert.strToDouble(map.get("repaySum") + "", 0);
				lateFI = lateFI
						+ Convert.strToDouble(map.get("lateFI") + "", 0);
				totalSum = totalSum
						+ Convert.strToDouble(map.get("totalSum") + "", 0);
			}
		}
		request().setAttribute("overAmount", overAmount);
		request().setAttribute("lateFI", lateFI);
		request().setAttribute("totalSum", totalSum);
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: overduePaymentRepaySubmit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-8 下午05:15:13
	 * @Return:
	 * @Descb: 逾期垫付还款提交
	 * @Throws:
	 */
	public String overduePaymentRepaySubmit() throws Exception {
		JSONObject obj = new JSONObject();
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1L);
		if (idLong == -1) {
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> map = afterCreditManageService
				.overduePaymentRepaySubmit(idLong, admin.getId(), getBasePath());
		String message = Convert.strToStr(map.get("ret_desc"), null) == null ? "操作成功"
				: map.get("ret_desc");
		if (StringUtils.isBlank(map.get("ret_desc"))) {
			// 添加操作日志
			operationLogService.addOperationLog("t_repayment", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"管理员进行逾期垫付操作", 2);
		}
		obj.put("msg", message);
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @Descb: 已还款查看详情
	 * @Throws:
	 */
	public String queryByrepayId() {
		int id = request.getInt("repayId", -1);
		try {
			afterCreditManageService.queryByrepayId(id, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Descb: 已还款查看详情
	 * @Throws:
	 */
	public String queryByrepayIdDueId() {
		int id = request.getInt("repayId", -1);
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		try {
			afterCreditManageService.queryByrepayIdDueId(id, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		int pageNums = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return SUCCESS;
	}

	/**
	 * @throws Exception
	 * @MethodName: hasRepayList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午02:24:40
	 * @Return:
	 * @Descb: 已还款列表
	 * @Throws:
	 */
	public String hasRepayList() throws Exception {
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap
				.get("userName");
		String realName = paramMap.get("realName") == null ? "" : paramMap
				.get("realName");
		String userType = paramMap.get("userType") == null ? "" : paramMap
				.get("userType");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String deadline = paramMap.get("deadline") == null ? "" : paramMap
				.get("deadline");
		int deadlineInt = Convert.strToInt(deadline, -1);
		String repayStatus = paramMap.get("status") == null ? "" : paramMap
				.get("status");
		int repayStatusInt = Convert.strToInt(repayStatus, -1);
		String timeStart1 = null;
		String timeEnd1 = null;
		String timeStart = null;
		String timeEnd = null;
			// -----------add by houli
			timeStart1 = paramMap.get("timeStart1") == null ? "" : paramMap
					.get("timeStart");
			timeEnd1 = paramMap.get("timeEnd1") == null ? "" : paramMap
					.get("timeEnd");
			// -------
		
			timeStart = paramMap.get("timeStart") == null ? "" : paramMap
					.get("timeStart");
			timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
					.get("timeEnd");
		
		afterCreditManageService.queryHasRepayByCondition(userName, realName,userType,
				timeStart, timeEnd, borrowWayInt, deadlineInt, repayStatusInt,
				pageBean,
				timeStart1, timeEnd1);
		// 已收款总额
		Map<String, String> hasRePayMap = afterCreditManageService
				.queryHasRePayAmount(userName, realName, userType ,timeStart, timeEnd,
						borrowWayInt, deadlineInt, repayStatusInt, timeStart1,
						timeEnd1);
		request().setAttribute("hasRePayMap", hasRePayMap);

		// 得到当前页已收款金额
		double hasAmount = 0;
		List<Map<String, Object>> hasList = pageBean.getPage();
		if (hasList != null) {
			for (Map<String, Object> map : hasList) {
				hasAmount = hasAmount
						+ Convert.strToDouble(map.get("hasPI") + "", 0);
			}
		}
		request().setAttribute("hasAmount", hasAmount);
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}

	/**
	 * 导出已还款列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exporthasRepay() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			// 得到页面传来的信息
			// 用户名
			String userName = request.getString("userName") == null ? ""
					: request.getString("userName");
			// 真实姓名
			String realName = "";
			// 还款日期
			String timeStart = request.getString("timeStart") == null ? ""
					:request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			// 到期日期
			String timeStart1 = request.getString("timeStart1") == null ? ""
					: request.getString("timeStart1");
			String timeEnd1 = request.getString("timeEnd1") == null ? ""
					: request.getString("timeEnd1");
			String userType = request.getString("userType") == null ? ""
					: request.getString("userType");
			// 借款期数
			int deadline = request.getInt("deadline",-1);
			// 借款类型
			int borrowWay = request.getInt("borrowWay",-1);
			// 中文乱码转换
			userName = URLDecoder.decode(userName, "UTF-8");
			realName = URLDecoder.decode(realName, "UTF-8");
			userType = URLDecoder.decode(userType, "UTF-8");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			timeStart1 = URLDecoder.decode(timeStart1, "UTF-8");
			timeEnd1 = URLDecoder.decode(timeEnd1, "UTF-8");
			// 已还款记录列表
			afterCreditManageService.queryHasRepayByCondition(userName,
					realName,userType, timeStart, timeEnd, borrowWay, deadline, -1,
					pageBean, timeStart1, timeEnd1);
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
			afterCreditManageService.changeNumToStr3(pageBean);

			HSSFWorkbook wb = ExcelUtils.exportExcel("已还款详情", pageBean
					.getPage(), new String[] { "借款人", "真实姓名", "企业全称", "用户类型", "用户组 ",
					"借款时间", "标旳类型", "借款标题", "是否天标", "期数/总期数 ", "应还时间 ",
					"还款时间 ", "应还金额  ", "还款状态" }, new String[] {
					"username", "realName", "orgName", "userType", "groupName", "publishTime",
					"borrowWay", "borrowTitle", "isDayThe", "repayPeriod",
					"repayDate", "realRepayDate", "hasPI", "repayStatus" });
			this.export(wb, new Date().getTime() + ".xls");

			operationLogService.addOperationLog("v_t_hasrepay_h", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出已还款列表", 2);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Descb: 待收款查看详情
	 * @Throws:
	 */
	public String queryByrepayIdDieIn() {
		int id = request.getInt("repayId", -1);
		try {
			afterCreditManageService.queryByrepayId(id, pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @MethodName: repaymentNoticeInit
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午07:24:07
	 * @Return:
	 * @Descb: 还款记录沟通初始化
	 * @Throws:
	 */
	public String repaymentNoticeInit() {
		String id = request.getString("id") == null ? "" : request.getString("id");
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: repayMentNoticeList
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午02:43:32
	 * @Return:
	 * @Descb: 还款沟通记录
	 * @Throws:
	 */
	public String repayMentNoticeList() throws Exception {
		String pageNum = request.getString("curPage") == null ? ""
				: request.getString("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToInt(id, -1);
		afterCreditManageService.queryRepayMentNoticeByCondition(idLong,
				pageBean);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: addRepayMentNotice
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午07:02:45
	 * @Return:
	 * @Descb: 添加还款沟通记录
	 * @Throws:
	 */
	public String addRepayMentNotice() throws Exception {
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String content = paramMap.get("content");
		long result = -1;
		result = afterCreditManageService.addRepayMentNotice(idLong, content);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (result <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_repayment_service", admin
					.getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
					"添加还款沟通记录失败", 2);
			return null;
		}
		// 前台跳转地址
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		operationLogService.addOperationLog("t_repayment_service", admin
				.getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
				"添加还款沟通记录成功", 2);
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: repaymentDetail
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:22:17
	 * @Return:
	 * @Descb: 还款记录详情
	 * @Throws:
	 */
	public String repaymentDetail() throws Exception {
		String frontUrl = ServletUtils.serverUrl();
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> map = afterCreditManageService
				.queryRepaymentDetail(idLong);
		List<Map<String, Object>> serviceList = afterCreditManageService
				.queryRepaymentService(idLong);
		List<Map<String, Object>> collectionList = afterCreditManageService
				.queryRepaymentCollection(idLong);
		request().setAttribute("map", map);
		request().setAttribute("serviceList", serviceList);
		request().setAttribute("collectionList", collectionList);
		request().setAttribute("frontUrl", frontUrl);
		return "success";
	}

	public String addCollection() throws Exception {
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1L);
		String colResult = paramMap.get("colResult") == null ? "" : paramMap
				.get("colResult");

		String remark = paramMap.get("remark") == null ? "" : paramMap
				.get("remark");
		long returnId = -1L;
		if (StringUtils.isBlank(colResult.trim())) {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(remark.trim())) {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = afterCreditManageService.addCollection(idLong, colResult,
				remark);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin
					.getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
					"添加催收记录失败", 2);
			return null;
		} else {
			obj.put("msg", IConstants.ACTION_SUCCESS);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin
					.getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
					"添加催收记录成功", 2);
			return null;
		}
	}

	/**
	 * @throws Exception
	 * @MethodName: delCollection
	 * @Param: AfterCreditManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:37:26
	 * @Return:
	 * @Descb: 删除催收记录
	 * @Throws:
	 */
	public String delCollection() throws Exception {
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1L);
		long returnId = -1L;
		returnId = afterCreditManageService.delCollection(idLong);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin
					.getUserName(), IConstants.DELETE, admin.getLastIP(), 0,
					"删除催收记录失败", 2);
			return null;
		} else {
			obj.put("msg", IConstants.ACTION_SUCCESS + ",请刷新");
			JSONUtils.printObject(obj);
			operationLogService.addOperationLog("t_collection", admin
					.getUserName(), IConstants.DELETE, admin.getLastIP(), 0,
					"删除催收记录成功", 2);
			return null;
		}
	}
	
	/**
	 * @MethodName: queryExperienceInit
	 * @Param: AfterCreditManageAction
	 * @Author: L.X.Z
	 * @Descb: 体验标初始化
	 */
	public String queryExperienceInit() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}
	/**
	 * @throws Exception
	 * @MethodName: queryExperienceList
	 * @Param: AfterCreditManageAction
	 * @Author: L.X.Z
	 * @Descb: 体验标
	 */
	public String queryExperienceList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String title = paramMap.get("title") == null ? "" : paramMap.get("title");
		String state = paramMap.get("state") == null ? "" : paramMap.get("state");
		String creationStart = paramMap.get("creationStart") == null ? "" : paramMap.get("creationStart");
		String creationEnd = paramMap.get("creationEnd") == null ? "" : paramMap.get("creationEnd");
		String waitingStart = paramMap.get("waitingStart") == null ? "" : paramMap.get("waitingStart");
		String waitingEnd = paramMap.get("waitingEnd") == null ? "" : paramMap.get("waitingEnd");

		
		afterCreditManageService.queryExperienceByCondition(title,state,creationStart,
				creationEnd, waitingStart, waitingEnd, pageBean);

		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		return "success";
	}
	
	/*
	 * 导出体验标列表
	 */
	public String exportExperienceList() throws Exception{
	    String title = paramMap.get("title") == null ? "" : paramMap.get("title");
        String state = paramMap.get("state") == null ? "" : paramMap.get("state");
        String creationStart = paramMap.get("creationStart") == null ? "" : paramMap.get("creationStart");
        String creationEnd = paramMap.get("creationEnd") == null ? "" : paramMap.get("creationEnd");
        String waitingStart = paramMap.get("waitingStart") == null ? "" : paramMap.get("waitingStart");
        String waitingEnd = paramMap.get("waitingEnd") == null ? "" : paramMap.get("waitingEnd");
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
        
        afterCreditManageService.queryExperienceByConditionExport(title,state,creationStart,
                creationEnd, waitingStart, waitingEnd, pageBean);
        try{
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        if (pageBean.getPage() == null) {
            getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
            return null;
        }
        if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
            getOut().print( "<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
            return null;
        }
        HSSFWorkbook wb = ExcelUtils.exportExcel("体验标的列表", pageBean
                .getPage(), new String[] { "标题", "体验总金额", "实投金额", "利率(%)", "期限(月)",
                "筹标期限(天)", "应还利息总额", "已还利息总额 ", "已还数","总期数", "发布时间",
                "状态","还款日期","期数" }, new String[] { "borrowTitle", "borrowAmount", "hasInvestAmount",
                "annualRate","deadline", "raiseTerm", "interestAmount", "repaymentedInterestAmount",
                "repaymentedCount", "deadline", "publishTime",
                "status", "repaymentTime","seq_id" });
        this.export(wb, new Date().getTime() + ".xls");
        // 添加操作日志
        operationLogService.addOperationLog("v_t_trial_borrow", admin
                .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                "导出体验标的列表", 2);
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
	}
	
	/**
	 * @MethodName: addExperience
	 * @Param: AfterCreditManageAction
	 * @Author: L.X.Z
	 * @Descb: 新增体验标
	 */
	public String addExperience() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}
	public String addExperienceInfo() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject json = new JSONObject();
		String title = Convert.strToStr(paramMap.get("title"), null);
		String amount = Convert.strToStr(paramMap.get("amount"), null);
		String annualRate = Convert.strToStr(paramMap.get("annualRate"), null);
		String deadline = Convert.strToStr(paramMap.get("deadline"), null);
		String raiseTerm = Convert.strToStr(paramMap.get("raiseTerm"), null);
		String paymentMode = Convert.strToStr(paramMap.get("paymentMode"), null);
		String minSum = Convert.strToStr(paramMap.get("minSum"), null);
		String maxSum = Convert.strToStr(paramMap.get("maxSum"), null);
		String detail = Convert.strToStr(paramMap.get("detail"), null);
		if (StringUtils.isBlank(title)) {
			json.put("msg", "请填写标题");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(amount)) {
			json.put("msg", "请填写总额");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(annualRate)) {
			json.put("msg", "请填写年利率");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(deadline)) {
			json.put("msg", "请填写期限");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(raiseTerm)) {
			json.put("msg", "请填写筹标期限");
			JSONUtils.printObject(json);
			return null;
		}
		Integer raiseTerm_1 = Convert.strToInt(raiseTerm, -1);
		if (StringUtils.isBlank(paymentMode)) {
			json.put("msg", "请填写还款方式");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(minSum)) {
			json.put("msg", "请填写最小额");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(maxSum)) {
			json.put("msg", "请填写最大额");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(detail)) {
			json.put("msg", "请填写描述");
			JSONUtils.printObject(json);
			return null;
		}
		
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		
		long batchId = -1L;
		batchId= afterCreditManageService.addExperienceInfo(title, amount, annualRate, deadline, raiseTerm_1, paymentMode, minSum,
				maxSum ,detail,admin==null?"":admin.getUserName());
		log.info("batchId:"+batchId);
		if (batchId > 0) {
		 // 添加操作日志
            operationLogService.addOperationLog("t_trial_borrow", admin
                    .getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
                    "新增体验标", 2);
			json.put("msg", "新增成功");
			JSONUtils.printObject(json); 
			return null;
		
		}else{
			json.put("msg", "新增失败");
			JSONUtils.printObject(json);
			return null;
		}
	}
	
	/**
	 * @throws Exception
	 * @MethodName: viewExperience
	 * @Param: AfterCreditManageAction
	 * @Author: L.X.Z
	 * @Descb: 体验标详情
	 */
	public String viewExperience() throws Exception {
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		Map<String, String> map = afterCreditManageService.viewExperienceInfo(idLong);
		log.info("map is:"+map);
		request().setAttribute("map", map);
		return "success";
	}
	
	
	/**
     * 提交还款记录
     * @return
     * @throws Exception 
     */
    public String submitPay() throws Exception{
        Thread.sleep(100);
        Admin user = (Admin)session().getAttribute(IConstants.SESSION_ADMIN);
        JSONObject obj = new JSONObject();
        String code = (String) session().getAttribute("invest_checkCode");
        String _code = paramMap.get("code") == null ? "" : paramMap
                .get("code");
        String id = paramMap.get("id") == null ? "" : paramMap
                .get("id");
        long idLong = Convert.strToLong(id, -1L);
        String pwd = paramMap.get("pwd") == null ? "" : paramMap
                .get("pwd");
        if (StringUtils.isBlank(pwd.trim())) {
            obj.put("msg", "密码不能为空");
            JSONUtils.printObject(obj);
            return null;
        }
        if (!code.equals(_code)) {
            obj.put("msg", "验证码错误");
            JSONUtils.printObject(obj);
            return null;
        }
        boolean re = userService.checkSign(user.getId());
        if(!re){
            obj.put("msg", "*您的账号出现异常，请速与管理员联系!");
            JSONUtils.printObject(obj);
            request().getSession().removeAttribute("user");
            request().getSession().invalidate();
            getOut().print(
            "<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
            return null;
        }
        Map<String, String> map =frontpayService.submitPay(idLong,user.getId(),pwd,getBasePath(),user.getUserName());
        String result =Convert.strToStr( map.get("ret_desc"),"");
        userService.updateSign(user.getId());//更换校验码
        obj.put("msg", result);
        JSONUtils.printObject(obj);
        return null;
    }
	
    /**
     * 还款初始化
     */
    public String experienceInvestInit() throws Exception{
        long id = request.getLong("id", -1);
        if (id==-1) {
            addActionMessage("参数丢失，请从新操作。");
            return INPUT;
        }
        Map<String, String> map = afterCreditManageService.viewExperienceInfo(id);
        setParamMap(map);
        afterCreditManageService.queryRepayment(id, pageBean);
        return SUCCESS;
    }
    
    /**
     * 还款
     * @throws Exception 
     */
    public String experienceInvestMethod() throws Exception{
        long repayment_id = Convert.strToLong(paramMap.get("repayment_id"), -1);
        long borrow_id = Convert.strToLong(paramMap.get("borrow_id"), -1);
        if (repayment_id==-1 || borrow_id==-1) {
            JSONUtils.printStr("2");
            return null;//参数丢失：
        }
        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        
        Map<String, Object> map = afterCreditManageService.investTrial(repayment_id, borrow_id);
        operationLogService.addOperationLog( "t_repayment",admin.getUserName() ,
                                        IConstants.UPDATE, ServletUtils.getIpAddress(request()), 0, "用户还款", 1);
        JSONUtils.printObject(map);
        return null;
    }
    
    /**
     * 自动还款
     * @throws Exception 
     */
    public String experienceInvestAutoMethod() throws Exception{
        afterCreditManageService.investTrialAuto();
        return null;
    }
    
    /**
     * 体验记录初始化
     * @throws SQLException 
     */
    public String experienceBorrowInitMethod() throws SQLException{
        request().setAttribute("id", request.getLong("id", -1L)+"");
        return SUCCESS;
    }
    
    /**
     * 投资记录列表
     * @throws SQLException 
     */
    public String experienceBorrowInfoMethod() throws SQLException{
        long borrowId = Convert.strToLong(paramMap.get("id"), -1L);
        String username  = paramMap.get("username");
        String ticketNo  = paramMap.get("ticketNo");
        String batchId  = paramMap.get("batchId");
        afterCreditManageService.queryExperienceBorrow(pageBean, username, ticketNo, batchId,borrowId);
        return SUCCESS;
    }
    
    /**
     * 导出投资记录列表
     * @throws Exception 
     */
    public String exportExperienceBorrowMethod() throws Exception{
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        long borrowId = Convert.strToLong(paramMap.get("id"), -1L);
        String username  = paramMap.get("username");
        String ticketNo  = paramMap.get("ticketNo");
        String batchId  = paramMap.get("batchId");
        afterCreditManageService.queryExperienceBorrow(pageBean, username, ticketNo, batchId,borrowId);
        try{
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        if (pageBean.getPage() == null) {
            getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
            return null;
        }
        if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
            getOut().print( "<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
            return null;
        }
        HSSFWorkbook wb = ExcelUtils.exportExcel("体验记录", pageBean
                .getPage(), new String[] { "用户名", "注册时间", "体验全批次号", "体验券号", "投标时间",
              /*  "投资占比", "借款时间", "标旳总金额 ", "标旳类型 ", "是否天标", "期数/总期数",
                "应收时间", "应收金额 "*/ }, new String[] { "username", "createTime", "batchId",
                "ticketNo", "useTime"/*, "scale", "publishTime",
                "borrowAmount", "borrowWay", "isDayThe", "repayPeriod",
                "repayDate", "forTotalSum" */});
        this.export(wb, new Date().getTime() + ".xls");
        // 添加操作日志
        operationLogService.addOperationLog("v_t_trial_ticket", admin
                .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                "导出体验记录列表", 2);
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return null;
    }
    
	public AfterCreditManageService getAfterCreditManageService() {
		return afterCreditManageService;
	}

	public void setAfterCreditManageService(
			AfterCreditManageService afterCreditManageService) {
		this.afterCreditManageService = afterCreditManageService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {
		if (borrowDeadlineList == null) {
			borrowDeadlineList = selectedService.borrowDeadline();
		}
		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getUserGroupList() throws Exception {
		if (userGroupList == null) {
			userGroupList = selectedService.userGroup();
		}
		return userGroupList;
	}
	
	
	
}