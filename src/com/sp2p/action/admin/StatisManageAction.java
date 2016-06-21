package com.sp2p.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.RequestUtils;

import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.UtilDate;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.PayTreasureService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SysparService;
import com.sp2p.service.admin.StatisManageService;

/**
 * @ClassName: StatisManageAction.java
 * @Author: gang.lv
 * @Date: 2013-4-4 上午09:16:19
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb:统计管理控制层
 */
public class StatisManageAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(StatisManageAction.class);
	private static final long serialVersionUID = 1L;

	private StatisManageService statisManageService;
	private SelectedService selectedService;
	
	private List<Map<String, Object>> userGroupList;

	
	private SysparService sysparService;
	
	private PayTreasureService payTreasureService;
	
	

	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}

	public SysparService getSysparService() {
		return sysparService;
	}

	public void setSysparService(SysparService sysparService) {
		this.sysparService = sysparService;
	}

	public StatisManageService getStatisManageService() {

		return statisManageService;
	}

	public void setStatisManageService(StatisManageService statisManageService) {
		this.statisManageService = statisManageService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getUserGroupList() throws Exception {
		if (userGroupList == null) {
			userGroupList = selectedService.userGroup();
		}
		return userGroupList;
	}

	/**
	 * @throws Exception
	 * @MethodName: webStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:16:36
	 * @Return:
	 * @Descb: 网站统计初始化
	 * @Throws:
	 */
	public String webStatisInit() throws Exception {
		Map<String, String> webMap = statisManageService.queryWebStatis();
		request().setAttribute("webMap", webMap);
		return "success";
	}

	/**
	 * 导出网站统计 excel
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String exportwebStatis() throws Exception {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 投标排名查询
			Map<String, String> webMap = statisManageService.queryWebStatis();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("covarianceName", "网站会员总金额");
			map1.put("covarianceNum", webMap.get("webUserAmount"));
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("covarianceName", "网站会员总冻结金额");
			map2.put("covarianceNum", webMap.get("webUserFreezeAmount"));
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("covarianceName", "网站会员总待收金额");
			map3.put("covarianceNum", webMap.get("webUserForPI"));
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("covarianceName", "网站收入总金额");
			map4.put("covarianceNum", webMap.get("webComeInAmount"));
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("covarianceName", "网站总VIP金额");
			map5.put("covarianceNum", webMap.get("webVIPAmount"));
			Map<String, Object> map8 = new HashMap<String, Object>();
			map8.put("covarianceName", "网站总借款管理费金额");
			map8.put("covarianceNum", webMap.get("borrowManageFee"));
			Map<String, Object> map9 = new HashMap<String, Object>();
			map9.put("covarianceName", "网站总借款逾期罚息金额");
			map9.put("covarianceNum", webMap.get("borrowFI"));
			Map<String, Object> map11 = new HashMap<String, Object>();
			map11.put("covarianceName", "后台手动添加费用");
			map11.put("covarianceNum", webMap.get("backAddAmount"));
			Map<String, Object> map12 = new HashMap<String, Object>();
			map12.put("covarianceName", "后台手动扣除费用");
			map12.put("covarianceNum", webMap.get("backDelAmount"));
			Map<String, Object> map13 = new HashMap<String, Object>();
			map13.put("covarianceName", "网站成功充值总额");
			map13.put("covarianceNum", webMap.get("webSucPrepaid"));
			Map<String, Object> map14 = new HashMap<String, Object>();
			map14.put("covarianceName", "网站线上充值总额");
			map14.put("covarianceNum", webMap.get("onlinePrepaid"));
			Map<String, Object> map15 = new HashMap<String, Object>();
			map15.put("covarianceName", "网站线下充值总额");
			map15.put("covarianceNum", webMap.get("downlinePrepaid"));
			Map<String, Object> map16 = new HashMap<String, Object>();
			map16.put("covarianceName", "网站提现总额");
			map16.put("covarianceNum", webMap.get("cashWith"));
			Map<String, Object> map17 = new HashMap<String, Object>();
			map17.put("covarianceName", "网站提现手续费总额");
			map17.put("covarianceNum", webMap.get("cashWithFee"));
			Map<String, Object> map22 = new HashMap<String, Object>();
			map22.put("covarianceName", "所有借款未还总额");
			map22.put("covarianceNum", webMap.get("borrowForPI"));
			Map<String, Object> map23 = new HashMap<String, Object>();
			map23.put("covarianceName", "所有逾期网站垫付未还款金额");
			map23.put("covarianceNum", webMap.get("webAdvinceForP"));
			Map<String, Object> map24 = new HashMap<String, Object>();
			map24.put("covarianceName", "借款逾期网站未垫付未还款金额");
			map24.put("covarianceNum", webMap.get("borrowForAmount"));
			Map<String, Object> map25 = new HashMap<String, Object>();
			map25.put("covarianceName", "所有借款已还款总额");
			map25.put("covarianceNum", webMap.get("borrowHasAmount"));
			Map<String, Object> map26 = new HashMap<String, Object>();
			map26.put("covarianceName", "所有借款正常还款总额");
			map26.put("covarianceNum", webMap.get("borrowNomalRepayAmount"));
			Map<String, Object> map27 = new HashMap<String, Object>();
			map27.put("covarianceName", "借款逾期网站垫付后已还款总额");
			map27.put("covarianceNum", webMap.get("webAdvinceHasP"));
			Map<String, Object> map28 = new HashMap<String, Object>();
			map28.put("covarianceName", "借款逾期的网站未垫付已还款总额");
			map28.put("covarianceNum", webMap.get("webNoAdvinceHasP"));
			Map<String, Object> map29 = new HashMap<String, Object>();
			map29.put("covarianceName", "借款逾期网站垫付总额");
			map29.put("covarianceNum", webMap.get("webAdviceAmount"));

			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			list.add(map5);
			list.add(map8);
			list.add(map9);
			list.add(map11);
			list.add(map12);
			list.add(map13);
			list.add(map14);
			list.add(map15);
			list.add(map16);
			list.add(map17);
			list.add(map22);
			list.add(map23);
			list.add(map24);
			list.add(map25);
			list.add(map26);
			list.add(map27);
			list.add(map28);
			list.add(map29);

			HSSFWorkbook wb = ExcelUtils.exportExcel("网站统计", list,
					new String[] { "统计项", "金额" }, new String[] {
							"covarianceName", "covarianceNum" });
			this.export(wb, new Date().getTime() + ".xls");

			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("pr_getWebStatis", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出网站统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: loginStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:45:13
	 * @Return:
	 * @Descb: 登录统计列表
	 * @Throws:
	 */
	public String loginStatisList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap .get("userName");
		String realName = paramMap.get("realName") == null ? "" : paramMap .get("realName");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap .get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap .get("timeEnd");
		String count = paramMap.get("count") == null ? "" : paramMap.get("count");
		String countEnd = paramMap.get("countEnd") == null ? "" : paramMap.get("countEnd");
		String ltimeStart = paramMap.get("ltimeStart") == null ? "" : paramMap.get("ltimeStart");
		String ltimeEnd = paramMap.get("ltimeEnd") == null ? "" : paramMap.get("ltimeEnd");
		String mobilePhone = paramMap.get("mobilePhone") == null ? "" : paramMap.get("mobilePhone");
		
		int countInt = Convert.strToInt(count, -1);
		int countEndi = Convert.strToInt(countEnd, -1);
		statisManageService.queryLoginStatisByCondition(userName, realName,
				timeStart, timeEnd,countEndi,ltimeStart,ltimeEnd,mobilePhone,  countInt, pageBean);

		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return "success";
	}

	
	public String loginStatisExcel() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			// 得到页面传来的值
			// 用户名
			String userName = request.getString("userName") == null ? "": request.getString("userName");
			userName = URLDecoder.decode(userName, "UTF-8");
			//真名
			String realName = request.getString("realName") == null ? "": request.getString("realName");
			realName = URLDecoder.decode(realName, "UTF-8");
			
			// 时间
			String timeStart = request.getString("timeStart") == null ? "" : request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? "" : request.getString("timeEnd");
			String ltimeStart = request.getString("ltimeStart") == null ? "" : request.getString("ltimeStart");
			String ltimeEnd = request.getString("ltimeEnd") == null ? "" : request.getString("ltimeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			String mobilePhone = request.getString("mobilePhone") == null ? "" : request.getString("mobilePhone");
			mobilePhone = URLDecoder.decode(mobilePhone, "UTF-8");
			
			int count = Convert.strToInt(request.getString("count"), -1);
			int countEnd = Convert.strToInt(request.getString("countEnd"), -1);

			// 已还款记录列表
			statisManageService.queryLoginStatisByCondition(userName, realName,
					timeStart, timeEnd,countEnd,ltimeStart,ltimeEnd,mobilePhone,  count, pageBean);
			
			
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
			//用户名  会员真实姓名  会员手机号  会员邮箱  邀请人用户名 邀请人真实姓名  注册时间  最后登录时间  最后登录IP  登录次数 
			statisManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("登录统计",
					pageBean.getPage(),
					new String[] { "用户名", "会员真实姓名", "会员手机号", " 会员邮箱", "邀请人用户名",
							"邀请人真实姓名", "注册时间", "最后登录时间", "最后登录IP",  "登录次数" }, new String[] {
							"username", "realName", "mobilePhone",
							"email", "refUserName", "refName",
							"createTime", "lastDate","lastIP","loginCount" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出登录统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * @MethodName: loginStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:16:44
	 * @Return:
	 * @Descb: 登录统计初始化
	 * @Throws:
	 */
	public String loginStatisInit() {
		return "success";
	}

	/**
	 * @MethodName: financeStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:16:57
	 * @Return:
	 * @Descb: 投资统计初始化
	 * @Throws:
	 */
	public String financeStatisInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: financeStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午02:51:14
	 * @Return:
	 * @Descb: 投资统计列表
	 * @Throws:
	 */
	public String financeStatisList() throws Exception {
		String radio = paramMap.get("radio") == null ? "" : paramMap
				.get("radio");
		int radioInt = Convert.strToInt(radio, -1);
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		SimpleDateFormat sfYear = new SimpleDateFormat(UtilDate.year);
		Date date = new Date();
		if (radioInt == -1) {
			// 没有日期限制
			timeStart = "";
			timeEnd = "";
		} else if (radioInt == 1) {
			// d当日
			timeStart = sf.format(date) + " 00:00:00";
			timeEnd = sf.format(date) + " 23:59:59";
		} else if (radioInt == 2) {
			// 当月
			timeStart = UtilDate.getMonthFirstDay();
			timeEnd = UtilDate.getMonthLastDay();
		} else if (radioInt == 3) {
			// 当年
			timeStart = sfYear.format(date) + "-01-01 00:00:00";
			timeEnd = sfYear.format(date) + "-12-31 23:59:59";
		}
		Map<String, String> financeEarnMap = statisManageService
				.queryFinanceEarnStatis(timeStart, timeEnd);
		request().setAttribute("financeEarnMap", financeEarnMap);
		return "success";
	}

	/**
	 * 导出投资统计列表 excel
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String exportfinanceStatis() throws Exception {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		String radio = request.getString("radio") == null ? "" : request.getString("radio");
		int radioInt = Convert.strToInt(radio, -1);
		String timeStart = request.getString("timeStart") == null ? ""
				: request.getString("timeStart");
		String timeEnd = request.getString("timeEnd") == null ? "" : request.getString("timeEnd");
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		SimpleDateFormat sfYear = new SimpleDateFormat(UtilDate.year);
		Date date = new Date();
		if (radioInt == -1) {
			// 没有日期限制
			timeStart = "";
			timeEnd = "";
		} else if (radioInt == 1) {
			// d当日
			timeStart = sf.format(date) + " 00:00:00";
			timeEnd = sf.format(date) + " 23:59:59";
		} else if (radioInt == 2) {
			// 当月
			timeStart = UtilDate.getMonthFirstDay();
			timeEnd = UtilDate.getMonthLastDay();
		} else if (radioInt == 3) {
			// 当年
			timeStart = sfYear.format(date) + "-01-01 00:00:00";
			timeEnd = sfYear.format(date) + "-12-31 23:59:59";
		}

		try {

			// 投标排名查询
			Map<String, String> financeEarnMap = statisManageService
					.queryFinanceEarnStatis(timeStart, timeEnd);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("covarianceName", "投资成功待收金额");
			map1.put("covarianceNum", financeEarnMap.get("investForAmount"));
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("covarianceName", "投资奖励金额");
			map2.put("covarianceNum", financeEarnMap.get("investRewardAmount"));
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("covarianceName", "借款人逾期罚金金额");
			map3.put("covarianceNum", financeEarnMap.get("borrowLateFAmount"));
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("covarianceName", "用户邀请好友金额");
			map4.put("covarianceNum", financeEarnMap.get("inviteReward"));
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("covarianceName", "借款成功总额");
			map5.put("covarianceNum", financeEarnMap.get("borrowAmount"));
			Map<String, Object> map6 = new HashMap<String, Object>();
			map6.put("covarianceName", "借款管理费总额");
			map6.put("covarianceNum", financeEarnMap.get("borrowManageFee"));
			Map<String, Object> map7 = new HashMap<String, Object>();
			map7.put("covarianceName", "借款利息总额");
			map7.put("covarianceNum", financeEarnMap
					.get("borrowInterestAmount"));
			Map<String, Object> map8 = new HashMap<String, Object>();
			map8.put("covarianceName", "借款奖励总额");
			map8.put("covarianceNum", financeEarnMap.get("borrowRewardAmount"));
			Map<String, Object> map9 = new HashMap<String, Object>();
			map9.put("covarianceName", "借款逾期罚息总额");
			map9.put("covarianceNum", financeEarnMap.get("borrowLateFI"));

			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			list.add(map5);
			list.add(map6);
			list.add(map7);
			list.add(map8);
			list.add(map9);

			HSSFWorkbook wb = ExcelUtils.exportExcel("投资盈利统计", list,
					new String[] { "统计项", "金额" }, new String[] {
							"covarianceName", "covarianceNum" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("pr_getFinanceEarnStatis",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投资盈利统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws SQLException
	 * @MethodName: investStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:17:07
	 * @Return:
	 * @Descb: 投标统计初始化
	 * @Throws:
	 */
	public String investStatisInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: investStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午09:13:15
	 * @Return:
	 * @Descb: 投标统计列表
	 * @Throws:
	 */
	public String investStatisList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String bTitle = paramMap.get("bTitle") == null ? "" : paramMap
				.get("bTitle");
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		String isAutoBid = paramMap.get("isAutoBid") == null ? "" : paramMap
				.get("isAutoBid");
		int isAutoBidInt = Convert.strToInt(isAutoBid, -1);
		String borrowStatus = paramMap.get("borrowStatus") == null ? ""
				: paramMap.get("borrowStatus");
		if ("1".equals(borrowStatus)) {
			borrowStatus = "(6)";
		} else if ("2".equals(borrowStatus)) {
			borrowStatus = "(1,2,3,4,5)";
		}
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		
		String recommendName = paramMap.get("recommendName") == null ? "" : paramMap.get("recommendName");
		String deadlineType = paramMap.get("deadlineType") == null ? "" : paramMap.get("deadlineType");
		String fromLine = paramMap.get("fromLine") == null ? "" : paramMap.get("fromLine");
		String toLine = paramMap.get("toLine") == null ? "" : paramMap.get("toLine");
		String realName = paramMap.get("realName") == null ? "" : paramMap.get("realName");
		
		
		
		int groupInt = Convert.strToInt(group, -1);
		Map pMap = new HashMap();
		pMap.put("bTitle", bTitle);
		pMap.put("investor", investor);
		pMap.put("timeStart", timeStart);
		pMap.put("timeEnd", timeEnd);
		pMap.put("borrowWayInt", borrowWayInt);
		pMap.put("isAutoBidInt", isAutoBidInt);
		pMap.put("borrowStatus", borrowStatus);
		pMap.put("groupInt", groupInt);
		pMap.put("recommendName", recommendName);
		pMap.put("deadlineType", deadlineType);
		pMap.put("fromLine", fromLine);
		pMap.put("toLine", toLine);
		pMap.put("realName", realName);

		com.shove.vo.PageBean pageBean1 = statisManageService.queryInvestStatisByCondition(pMap, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("totalNum", pageBean.getTotalNum());
		
		//sum(realAmount)
		List <Map>list = pageBean1.getPage();
		BigDecimal sumAmount = new BigDecimal(0);
		if (null != list){
			for (Map map : list){
				sumAmount = (BigDecimal)map.get("sumAmount");
			}
		}
		
		log.info("---------------sum:" + sumAmount);
		request().setAttribute("sumAmt", sumAmount);
		
		return "success";
	}

	/**
	 * 导出投标统计列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportinvestStatis() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 得到页面传来的值
			// 借款标题
			String bTitle = request.getString("bTitle") == null ? ""
					: request.getString("bTitle");
			bTitle = URLDecoder.decode(bTitle, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 用户名
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 借款类型
			int borrowWay = Convert.strToInt(request
					.getString("borrowWay"), -1);
			// 是否自动投标
			int isAutoBid = Convert.strToInt(request.getString("isAutoBid"), -1);

			// 是否投标成功
			String borrowStatus = request.getString("borrowStatus") == null ? ""
					: request.getString("borrowStatus");
			if ("1".equals(borrowStatus)) {
				borrowStatus = "(6)";
			} else if ("2".equals(borrowStatus)) {
				borrowStatus = "(1,2,3,4,5)";
			}
			// 用户组
			int group = Convert.strToInt(request.getString("group"), -1);
			String recommendName = request.getString("recommendName") == null ? "" : request.getString("recommendName");
			String deadlineType = request.getString("deadlineType") == null ? "" : request.getString("deadlineType");
			String fromLine = request.getString("fromLine") == null ? "" : request.getString("fromLine");
			String toLine = request.getString("toLine") == null ? "" : request.getString("toLine");
			String realName0 = request.getString("realName") == null ? "" : request.getString("realName");
			
			Map pMap = new HashMap();
			pMap.put("bTitle", bTitle);
			pMap.put("investor", investor);
			pMap.put("timeStart", timeStart);
			pMap.put("timeEnd", timeEnd);
			pMap.put("borrowWayInt", borrowWay);
			pMap.put("isAutoBidInt", isAutoBid);
			pMap.put("borrowStatus", borrowStatus);
			pMap.put("groupInt", group);
			pMap.put("recommendName", recommendName);
			pMap.put("deadlineType", deadlineType);
			pMap.put("fromLine", fromLine);
			pMap.put("toLine", toLine);
			pMap.put("realName", realName0);

			// 已还款记录列表
			statisManageService.queryInvestStatisByCondition(pMap, pageBean);
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
			
			statisManageService.changeNumToStr(pageBean);
			
			List list = pageBean.getPage();
			for (int i = 0, j = list.size(); i < j; i++){
				Map map = (Map) list.get(i);
				
				String realName = (String) map.get("realName");
				String organization_name = (String) map.get("organization_name");
				Integer deadline = (Integer) map.get("deadline");
				String isDayThe = (String) map.get("isDayThe");
				Integer intborr = (Integer) map.get("borrowWay");
				String borrowWay1 = "";
				Integer intAutoBid = (Integer) map.get("isAutoBid");
				Integer intbstatus = (Integer) map.get("borrowStatus");

				if (intbstatus.intValue() == 6){
					map.put("borrowStatus", "否");
				} else {
					map.put("borrowStatus", "是");
				}
				
				if (intAutoBid.intValue() == 1){
					map.put("isAutoBid", "否");
				} else {
					map.put("isAutoBid", "是");
				}
				
				
				
				if (StringUtils.isEmpty(organization_name)){
					map.put("realName", realName);
				} else {
					map.put("realName", organization_name);
				}
				
				isDayThe = deadline + isDayThe;
				map.put("isDayThe", isDayThe);
				if (intborr.intValue() == 1){
					borrowWay1 = "净值借款";
				} else if(intborr.intValue() == 2){
					borrowWay1 = "秒还借款";
				} else if(intborr.intValue() == 3){
					borrowWay1 = "信用借款";
				} else if(intborr.intValue() == 4){
					borrowWay1 = "实地考察借款";
				} else if(intborr.intValue() == 5){
					borrowWay1 = "机构担保借款";
				} else if(intborr.intValue() == 6){
					borrowWay1 = "流转标借款";
				} 
				map.put("borrowWay", borrowWay1);
				
			}
			
			
			HSSFWorkbook wb = ExcelUtils.exportExcel("投标记录",
					pageBean.getPage(),
					new String[] { "用户名","真实姓名/企业全称", "投标扣除金额(￥)", "交易对方", "借款标题", "借款类型",
							"是否自动投标", "是否投标成功", "投标时间","邀请人用户名","借款期限" }, new String[] {
							"investor", "realName","realAmount", "borrower",
							"borrowTitle", "borrowWay", "isAutoBid",
							"borrowStatus", "investTime","redName","isDayThe" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投标统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: investStatisRankInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午10:34:18
	 * @Return:
	 * @Descb: 投资排名
	 * @Throws:
	 */
	public String investStatisRankInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: investStatisRankList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午10:34:26
	 * @Return:
	 * @Descb: 投表排名查询
	 * @Throws:
	 */
	public String investStatisRankList() throws Exception {

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		log.info("-----pageBean.pageNum="+pageBean.pageNum);
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String group = paramMap.get("group") == null ? "" : paramMap
				.get("group");
		int groupInt = Convert.strToInt(group, -1);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		statisManageService.queryInvestStatisRankByCondition(paramMap, pageBean);

		return "success";
	}

	/**
	 * 导出投表排名查询 excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportinvestStatisRank() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {
			// 用户名
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 用户组
			int group = Convert.strToInt(request.getString("group"), -1);
			paramMap.put("investor", investor);
			paramMap.put("timeStart", timeStart);
			paramMap.put("timeEnd", timeEnd);
			paramMap.put("group", ""+group);
//			paramMap.put("", );
//			paramMap.put("", );
//			paramMap.put("", );
			
			// 投标排名查询
			statisManageService.queryInvestStatisRankByCondition(paramMap, pageBean);
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
			List<Map<String, Object>> list = pageBean.getPage();
			Long num = 1L;
			if (list != null) {
				for (Map<String, Object> map : list) {
					map.put("count", num);
					num += 1L;

				}
			}

//			HSSFWorkbook wb = ExcelUtils.exportExcel("投标排名",
//					pageBean.getPage(), new String[] { "排名", "用户名", "真是姓名",
//							"期间成功投标金额", "期间投标金额总计", "账号总额型", "可以金额", "待收金额",
//							"会员积分", "信用积分" }, new String[] { "count",
//							"username", "realName", "realAmount", "realSum",
//							"totalSum", "usableSum", "forPI", "rating",
//							"creditrating" });
			HSSFWorkbook wb = ExcelUtils.exportExcel("投标排名",
					pageBean.getPage(), new String[] { "排名", "用户名", "真实姓名",
							 "期间投标金额总计", "可用金额"}, new String[] { "count",
							"username", "realName",  "investSum", "usableSum" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投标排名列表", 2);

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
	 * @MethodName: borrowStatisInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:17:15
	 * @Return:
	 * @Descb: 借款统计初始化
	 * @Throws:
	 */
	public String borrowStatisInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowStatisList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午10:25:18
	 * @Return:
	 * @Descb: 借款投资统计
	 * @Throws:
	 */
	public String borrowStatisList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String borrowTitle = paramMap.get("borrowTitle") == null ? ""
				: paramMap.get("borrowTitle");
		String borrower = paramMap.get("borrower") == null ? "" : paramMap
				.get("borrower");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		int borrowWayInt = Convert.strToInt(borrowWay, -1);
		statisManageService.queryBorrowStatisByCondition(borrowTitle, borrower,
				timeStart, timeEnd, borrowWayInt, pageBean);
		// 借款管理费统计总额
		Map<String, String> feeMap = statisManageService
				.queryBorrowStatisAmount(borrowTitle, borrower, timeStart,
						timeEnd, borrowWayInt);

		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		request().setAttribute("feeMap", feeMap);
		return "success";
	}

	/**
	 * 导出借款投资统计 excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportborrowStatis() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 用户名
			String investor = request.getString("borrower") == null ? ""
					: request.getString("borrower");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 标题
			String borrowTitle = request.getString("borrowTitle") == null ? ""
					: request.getString("borrowTitle");
			borrowTitle = URLDecoder.decode(borrowTitle, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 借款类型
			int borroWer = Convert.strToInt(
					request.getString("borrowWay"), -1);
			statisManageService.queryBorrowStatisByCondition(borrowTitle,
					investor, timeStart, timeEnd, borroWer, pageBean);
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
			statisManageService.changeNumToStr(pageBean);

			HSSFWorkbook wb = ExcelUtils.exportExcel("借款管理费统计", pageBean
					.getPage(), new String[] { "借款用户名", "借款标题", "借款金额(￥)",
					"借款类型", "借款期限", "借款管理费", "复审成功时间" }, new String[] {
					"borrower", "borrowTitle", "borrowAmount", "borrowWay",
					"deadline", "manageFee", "auditTime", });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_borrow",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出借款管理费统计列表", 2);

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
	 * @MethodName: borrowStatisInterestInit
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午11:36:46
	 * @Return:
	 * @Descb: 投资利息统计初始化
	 * @Throws:
	 */
	public String borrowStatisInterestInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: borrowStatisInterestList
	 * @Param: StatisManageAction
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午11:36:57
	 * @Return:
	 * @Descb: 投资利息查询
	 * @Throws:
	 */
	public String borrowStatisInterestList() throws Exception {
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String investor = paramMap.get("investor") == null ? "" : paramMap
				.get("investor");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap
				.get("timeStart");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap
				.get("timeEnd");
		statisManageService.queryborrowStatisInterestByCondition(investor,
				timeStart, timeEnd, pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return "success";
	}

	/**
	 * 导出借款投资统计 excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportborrowStatisInterest() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			// 得到页面传来的值
			// 用户名
			String investor = request.getString("investor") == null ? ""
					: request.getString("investor");
			investor = URLDecoder.decode(investor, "UTF-8");
			// 时间
			String timeStart = request.getString("timeStart") == null ? ""
					: request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? ""
					: request.getString("timeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			// 借款投资统计
			statisManageService.queryborrowStatisInterestByCondition(investor,
					timeStart, timeEnd, pageBean);

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

			HSSFWorkbook wb = ExcelUtils.exportExcel("投资利息管理费统计", pageBean
					.getPage(), new String[] { "用户名", "真实姓名", "期间投资管理费利息总额",
					"期间收到还款总额", "已赚利息总额 ","利息管理费", "待收利息总额", "待收总额", "投资时间" }, new String[] {
					"investor", "realName", "manageFI", "hasPI", "hasInterest","manageFee",
					"forInterest", "forPI","investTime" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_invest",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出投资利息管理费统计", 2);

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
	 * 用户组统计查询初始化
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryborrowStatisUserGroupInit() throws SQLException,
			DataException {
		return SUCCESS;
	}

	/**
	 * 用户组统计查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryborrowStatisUserGroupPage() throws Exception {
		String groupName = paramMap.get("groupName") == null ? "" : paramMap
				.get("groupName");
		try {
			statisManageService.queryborrowStatisUserGroupByCondition(
					groupName, pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return SUCCESS;

	}

	/**
	 * 导出用户组统计
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public String exportUserGroup() throws SQLException, DataException {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		try {
			String groupName = request.getString("groupName") == null ? ""
					: request.getString("groupName");
			groupName = URLDecoder.decode(groupName, "UTF-8");
			// 导出
			statisManageService.queryborrowStatisUserGroupByCondition(
					groupName, pageBean);
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
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户组统计", pageBean
					.getPage(), new String[] { "序号", "组名", "总金额(元)", "冻结金额(元)",
					"待收金额(元) ", "借款管理费金额", "待收利息总额(元)", "VIP总金额", "已还款总额",
					"投资总额" }, new String[] { "groupId", "groupName",
					"totalSum", "freezeSum", "forPI", "manageFee",
					"forInterest", "vipFee", "hasPI", "realAmount" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_group_user_amount", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出用户组统计", 2);

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
	
	/*
	 * 员工资金统计初始化
	 */
	public String employeeFundInitMethod() throws SQLException{
	    List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
	    return SUCCESS;
	}
	/*
     * 员工资金统计
     */
	public String employeeFundInfoMethod() throws SQLException{
        String userName = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String phone = paramMap.get("phone");
        String acountBalanceTime = paramMap.get("acountBalanceTime");
        String investTimeStart = paramMap.get("investTimeStart");
        String investTimeEnd = paramMap.get("investTimeEnd");
        String rechargeTimeStart = paramMap.get("rechargeTimeStart");
        String rechargeTimeEnd = paramMap.get("rechargeTimeEnd");
        statisManageService.queryemployeeFund(pageBean,userName,realName,department,phone,acountBalanceTime,investTimeStart,investTimeEnd,rechargeTimeStart,rechargeTimeEnd);
	    return SUCCESS;
	}
	/*
	 * 导出员工资金统计
	 */
	public String exportEmployeeFundInfo() throws SQLException{
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
	    String userName = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String phone = paramMap.get("phone");
        String acountBalanceTime = paramMap.get("acountBalanceTime");
        String investTimeStart = paramMap.get("investTimeStart");
        String investTimeEnd = paramMap.get("investTimeEnd");
        String rechargeTimeStart = paramMap.get("rechargeTimeStart");
        String rechargeTimeEnd = paramMap.get("rechargeTimeEnd");
        statisManageService.queryemployeeFund(pageBean,userName,realName,department,phone,acountBalanceTime,investTimeStart,investTimeEnd,rechargeTimeStart,rechargeTimeEnd);
	    
        try {
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
            HSSFWorkbook wb = ExcelUtils.exportExcel("员工资金统计", pageBean
                    .getPage(), new String[] { "序号", "用户名", "真实姓名", "部门",
                    "职位", "注册时间", "账户余额", "充值金额", "投资金额"}, new String[] { "id", "username",
                    "realName", "selectName", "job", "createTime",
                    "acoumtMoney", "rechargeMoney", "investMoney" });
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("v_t_employee", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出员工资金统计", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        
        return null;
	}
	
	/*
	 * 部门资金统计初始化
	 */
	public String departmentFundInitMethod() throws SQLException{
	    List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
        return SUCCESS;
	}
	/*
	 * 部门资金统计
	 */
	public String departmentFundInfoMethod() throws SQLException{
        String department = paramMap.get("department");
        String acountBalanceTime = paramMap.get("acountBalanceTime");
        String investTimeStart = paramMap.get("investTimeStart");
        String investTimeEnd = paramMap.get("investTimeEnd");
        String rechargeTimeStart = paramMap.get("rechargeTimeStart");
        String rechargeTimeEnd = paramMap.get("rechargeTimeEnd");
        pageBean.setPage(statisManageService.querydepartmentFund(department, acountBalanceTime, investTimeStart, investTimeEnd, rechargeTimeStart, rechargeTimeEnd));
        return SUCCESS;
	}
	/*
	 * 导出部门资金统计
	 */
	public String exportDepartmentFundInfo() throws SQLException{
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
	    String department = paramMap.get("department");
        String acountBalanceTime = paramMap.get("acountBalanceTime");
        String investTimeStart = paramMap.get("investTimeStart");
        String investTimeEnd = paramMap.get("investTimeEnd");
        String rechargeTimeStart = paramMap.get("rechargeTimeStart");
        String rechargeTimeEnd = paramMap.get("rechargeTimeEnd");
        pageBean.setPage(statisManageService.querydepartmentFund(department, acountBalanceTime, investTimeStart, investTimeEnd, rechargeTimeStart, rechargeTimeEnd));
       
        try {
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
            HSSFWorkbook wb = ExcelUtils.exportExcel("部门资金统计", pageBean
                    .getPage(), new String[] { "部门",
                    "人数", "账户余额", "充值金额", "投资金额"}, new String[] { "a",
                    "e", "b", "c", "d"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("v_t_employee", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出部门资金统计", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }    
        
        return null;
	}
	
	/*
	 * 员工推荐明细初始
	 */
	public String employeeRefInitMethod() throws SQLException{
	    List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
        return SUCCESS;
	}
	/*
	 * 员工推荐明细
	 */
	public String employeeRefInfoMethod() throws SQLException{
	    String userName = paramMap.get("userName");
        String department = paramMap.get("department");
        String hasRecharge = paramMap.get("hasRecharge");
        String hasInvest = paramMap.get("hasInvest");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        statisManageService.queryemployeeRefInfo(pageBean, userName, department, hasRecharge, hasInvest, createTimeStart, createTimeEnd);
        return SUCCESS;
	}
	
	/**
	 * 推荐有效客户投资明细
	 * @return
	 * @throws SQLException 
	 */
	public String recommendAwardDetialInfo() throws SQLException{
		//有效客户等级
		String level = request("level");
		String id = request("id");
		String startInvestTime = request("startInvestTime");
		String endInvestTime = request("endInvestTime");
		statisManageService.recommendAwardDetialInfo(pageBean, id, level,startInvestTime,endInvestTime);
		return SUCCESS;
	}
	
	/**
	 * 推荐有效客户投资明细导出
	 * @return
	 * @throws SQLException 
	 */
	public String recommendAwardDetailExcel() throws SQLException{
		//有效客户等级
		String level = request("level");
		String id = request("id");
		String startInvestTime = request("startInvestTime");
		String endInvestTime = request("endInvestTime");
		pageBean.setPageSize(IConstants.EXCEL_MAX);
		statisManageService.recommendAwardDetialInfo(pageBean, id, level,startInvestTime,endInvestTime);
		try {
            if (pageBean.getPage() == null) {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }
            HSSFWorkbook wb = ExcelUtils.exportExcel("推荐投资明细统计", pageBean
                    .getPage(), new String[] { "用户名",
                    "真实姓名", "投标金额", "交易对方", "借款标题","借款类型","借款期限","","投标时间"}, new String[] { 
                    "username", "realName", "investAmount", "publisher","borrowTitle","borrowWay","deadline","isDayThe","investTime"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出员工推荐明细", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }    
        
        return null;
	}
	
	/*
	 * 导出员工推荐明细
	 */
	public String exportEmployeeRefInfo() throws SQLException{
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
	    String userName = paramMap.get("userName");
        String department = paramMap.get("department");
        String hasRecharge = paramMap.get("hasRecharge");
        String hasInvest = paramMap.get("hasInvest");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        statisManageService.queryemployeeRefInfo(pageBean, userName, department, hasRecharge, hasInvest, createTimeStart, createTimeEnd);
        try {
            if (pageBean.getPage() == null) {
                getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }
            HSSFWorkbook wb = ExcelUtils.exportExcel("员工推荐明细统计", pageBean
                    .getPage(), new String[] { "会员用户名",
                    "会员真实姓名", "会员注册时间", "是否有充值", "是否有投资","推荐人用户名","推荐人姓名","推荐人部门"}, new String[] { 
                    "username", "realname", "createTime", "hasRecharge","hasInvest","recommendUserName","recommendUserRealName","selectName"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出员工推荐明细", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }    
        
        return null;
	}
	/*
	 * 员工推荐活跃度初始化
	 */
	public String employeeLivInitMethod() throws SQLException{
	    List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
        return SUCCESS;
	}
	/*
	 * 员工推荐活跃度
	 */
	public String employeeLivInfoMethod() throws SQLException{
	    String userName = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String loginTimeStart = paramMap.get("loginTimeStart");
        String loginTimeEnd = paramMap.get("loginTimeEnd");
        String logincount = paramMap.get("logincount");
        statisManageService.queryemployeeLivInfo(pageBean, userName, realName, department, loginTimeStart, loginTimeEnd, logincount);
        if (StringUtils.isNotBlank(logincount)) {
            request().setAttribute("logincountI_in", logincount);
        }
	    return SUCCESS;
	}
	/*
	 * 导出员工推荐活跃度
	 */
	public String exportEmployeeLivInfo() throws SQLException{
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
	    String userName = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String loginTimeStart = paramMap.get("loginTimeStart");
        String loginTimeEnd = paramMap.get("loginTimeEnd");
        String logincount = paramMap.get("logincount");
        statisManageService.queryemployeeLivInfo(pageBean, userName, realName, department, loginTimeStart, loginTimeEnd, logincount);
        try {
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
            HSSFWorkbook wb = ExcelUtils.exportExcel("员工推荐活跃度统计", pageBean
                    .getPage(), new String[] { "员工用户名",
                    "员工真实姓名", "部门", "总推荐人数", "充值人数","充值活跃度","投标人数","投标活跃度","无动作人数","静止度","某时段登陆10次以上的人数","登陆活跃度"}, new String[] { 
                    "username", "realName", "selectName", "rcdcount","rechargecount","rechargeActive","investcount","investActive","staticscount","statics","logincount","loginActive"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出员工推荐活跃度", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
	    return null;
	}
	/*
	 * 部门推荐活跃度初始化
	 */
	public String departmentLivInitMethod() throws SQLException{
	    List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
        return SUCCESS;
	}
	/*
	 * 部门推荐活跃度
	 */
	public String departmentLivInfoMethod() throws SQLException{
        String department = paramMap.get("department");
        String loginTimeStart = paramMap.get("loginTimeStart");
        String loginTimeEnd = paramMap.get("loginTimeEnd");
        String logincount = paramMap.get("logincount");
        statisManageService.querydepartmentLivInfo(pageBean, department, loginTimeStart, loginTimeEnd, logincount);
        if (StringUtils.isNotBlank(logincount)) {
            request().setAttribute("logincountI_in", logincount);
        }
        return SUCCESS;
	}
	/*
	 * 导出部门推荐活跃度
	 */
	public String exportDepartmentLivInfo() throws SQLException{
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
        String department = paramMap.get("department");
        String loginTimeStart = paramMap.get("loginTimeStart");
        String loginTimeEnd = paramMap.get("loginTimeEnd");
        String logincount = paramMap.get("logincount");
        statisManageService.querydepartmentLivInfo(pageBean, department, loginTimeStart, loginTimeEnd, logincount);
        try {
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
            HSSFWorkbook wb = ExcelUtils.exportExcel("部门推荐活跃度", pageBean
                    .getPage(), new String[] { "部门","员工人数", "总推荐人数", "充值人数","充值活跃度","投标人数","投标活跃度","无动作人数","静止度","某时段登陆10次以上的人数","登陆活跃度"}, new String[] { 
                     "selectName","countnumber", "rcdcount","rechargecount","rechargeActive","investcount","investActive","staticscount","statics","logincount","loginActive"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出部门推荐活跃度", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
	/*
	 * 员工绩效初始
	 */
	public String employeePerformanceInitMethod() throws SQLException{
	    List<Map<String, Object>> department =  sysparService.querySysparAllChild("selectKey,selectName", " typeId=21 and deleted=1", "", -1, -1);
        request().setAttribute("departments", department);
        return SUCCESS;
	}
	/*
	 * 员工绩效
	 */
	public String employeePerformanceInfoMethod() throws SQLException{
	    String userName = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String timeStart = paramMap.get("timeStart");
        String investStartTime = paramMap.get("investStartTime");
        String investEndTime = paramMap.get("investEndTime");
        statisManageService.queryemployeePerformanceInfo(pageBean, userName, realName, department, timeStart,investStartTime,investEndTime);
        return SUCCESS;
    }
	/*
	 * 导出员工绩效
	 */
	public String exportEmployeePerformanceInfo() throws SQLException{
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
	    String userName = paramMap.get("userName");
        String realName = paramMap.get("realName");
        String department = paramMap.get("department");
        String timeStart = paramMap.get("timeStart");
        String investStartTime = paramMap.get("investStartTime");
        String investEndTime = paramMap.get("investEndTime");
        statisManageService.queryemployeePerformanceInfo(pageBean, userName, realName, department, timeStart,investStartTime,investEndTime);
        try {
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
            HSSFWorkbook wb = ExcelUtils.exportExcel("员工绩效统计", pageBean
                    .getPage(), new String[] { "员工用户名","员工真实姓名", "部门", "账户可用余额", "账户冻结金额","账户总额","投资总额","被推荐人账户冻结余额","被推荐人账户可用余额","被推荐人账户总额","被推荐人投资总额","总推荐人数","充值人数","充值活跃度","投标人数","投标活跃度","无动作人数","静止度"}, new String[] { 
                     "username","realName", "selectName","myAmount","myFreeAmount","mySumAmount","myInvestAmount","friendFreezeAmount","friendAmount","friendSumAmount","friendInvestAmount","rcdcount","rechargecount","rechargeActive","investcount","investActive","staticscount","statics"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出员工绩效统计", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
	    return null;
	}
	
	/**
	 *功能：用户统计 
	 * @return
	 * @throws Exception
	 */
	public String userstatics() throws Exception {
		log.info("className:" + this.getClass().getName() + ";-methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());

		return SUCCESS;
	}
	
	/**
	 *功能：查询用户信息页面
	 * @return
	 * @throws Exception 
	 */
	public String userstaticsList() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String userName = paramMap.get("userName") == null ? "" : paramMap .get("userName");
		String realName = paramMap.get("realName") == null ? "" : paramMap .get("realName");
		String timeStart = paramMap.get("timeStart") == null ? "" : paramMap .get("timeStart");
		String isEmployee = paramMap.get("isEmployee") == null ? "" : paramMap .get("isEmployee");
		String timeEnd = paramMap.get("timeEnd") == null ? "" : paramMap .get("timeEnd");
		String ltimeStart = paramMap.get("ltimeStart") == null ? "" : paramMap.get("ltimeStart");
		String ltimeEnd = paramMap.get("ltimeEnd") == null ? "" : paramMap.get("ltimeEnd");
		String rtimeStart = paramMap.get("ltimeStart") == null ? "" : paramMap.get("rtimeStart");
		String rtimeEnd = paramMap.get("ltimeEnd") == null ? "" : paramMap.get("rtimeEnd");
		String mobilePhone = paramMap.get("mobilePhone") == null ? "" : paramMap.get("mobilePhone");
		String usableAmts = paramMap.get("usableAmts") == null ? "" : paramMap.get("usableAmts");
		String usableAmte = paramMap.get("usableAmte") == null ? "" : paramMap.get("usableAmte");
		String usableTimeStart = paramMap.get("usableTimeStart") == null ? "" : paramMap.get("usableTimeStart");
		String usableTimeEnd = paramMap.get("usableTimeEnd") == null ? "" : paramMap.get("usableTimeEnd");
		
		Map <String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("realName", realName);
		map.put("timeStart", timeStart);
		map.put("timeEnd", timeEnd);
		map.put("isEmployee", isEmployee);
		map.put("ltimeStart", ltimeStart);
		map.put("ltimeEnd", ltimeEnd);
		map.put("rtimeStart", rtimeStart);
		map.put("rtimeEnd", rtimeEnd);
		map.put("mobilePhone", mobilePhone);
		map.put("usableAmts", usableAmts);
		map.put("usableAmte", usableAmte);
		map.put("usableTimeStart", usableTimeStart);
		map.put("usableTimeEnd", usableTimeEnd);
		map.put("userType", paramMap.get("userType"));
		
		statisManageService.userstaticsList(map,pageBean);

		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("rechargeMoneyAll", map.get("rechargeMoney"));
		request().setAttribute("investAmtAll", map.get("investAmt"));
		request().setAttribute("usableSumAll", map.get("usableSum"));
		return SUCCESS;
	}
	
	/**
	 *功能：导出用户数据
	 * @return
	 */
	public String userstaticsExcel() {
		pageBean.setPageSize(100000);
		try {
			// 得到页面传来的值
			// 用户名
			String userName = request.getString("userName") == null ? "": request.getString("userName");
			userName = URLDecoder.decode(userName, "UTF-8");
			//真名
			String realName = request.getString("realName") == null ? "": request.getString("realName");
			realName = URLDecoder.decode(realName, "UTF-8");
			
			// 时间
			String timeStart = request.getString("timeStart") == null ? "" : request.getString("timeStart");
			String timeEnd = request.getString("timeEnd") == null ? "" : request.getString("timeEnd");
			String ltimeStart = request.getString("ltimeStart") == null ? "" : request.getString("ltimeStart");
			String ltimeEnd = request.getString("ltimeEnd") == null ? "" : request.getString("ltimeEnd");
			timeStart = URLDecoder.decode(timeStart, "UTF-8");
			timeEnd = URLDecoder.decode(timeEnd, "UTF-8");
			
			String rtimeStart = request.getString("rtimeStart") == null ? "" : request.getString("rtimeStart");
			String rtimeEnd = request.getString("rtimeEnd") == null ? "" : request.getString("rtimeEnd");
			
			String mobilePhone = request.getString("mobilePhone") == null ? "" : request.getString("mobilePhone");
			mobilePhone = URLDecoder.decode(mobilePhone, "UTF-8");
			String isEmployee = request.getString("isEmployee") == null ? "" : request.getString("isEmployee");
			isEmployee = URLDecoder.decode(isEmployee, "UTF-8");
			
			String usableAmts = request.getString("usableAmts") == null ? "" : request.getString("usableAmts");
			String usableAmte = request.getString("usableAmte") == null ? "" : request.getString("usableAmte");
			
			String usableTimeStart = request.getString("usableTimeStart") == null ? "" : request.getString("usableTimeStart");

			// 已还款记录列表
			Map <String, String> map = new HashMap<String, String>();
			map.put("userName", userName);
			map.put("realName", realName);
			map.put("timeStart", timeStart);
			map.put("timeEnd", timeEnd);
			map.put("isEmployee", isEmployee);
			map.put("ltimeStart", ltimeStart);
			map.put("ltimeEnd", ltimeEnd);
			map.put("rtimeStart", rtimeStart);
			map.put("rtimeEnd", rtimeEnd);
			map.put("mobilePhone", mobilePhone);
			map.put("usableAmts", usableAmts);
			map.put("usableAmte", usableAmte);
			map.put("usableTimeStart", usableTimeStart);
			statisManageService.userstaticsList(map, pageBean);
			
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
			statisManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("登录统计",
					pageBean.getPage(),
					new String[] { "会员用户名", "会员真实姓名", "会员手机号", "会员邮箱", "是否公司员工",
							"注册时间", "投资金额", "充值金额", "可用余额","时间点可用余额","推荐人"}, new String[] {
							"username", "realName", "mobilePhone",
							"email", "isEmployee", "createTime",
							"investAmt", "rechargeMoney","usableSum","usableSumTime","redName" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出用户统计列表", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 借款信息初始化
	 */
	public String borrowInfoDetailInitMethod(){
	    return SUCCESS;
	}
	/*
	 * 借款信息列表
	 */
	public String borrowInfoDeatilMethod() throws SQLException{
        String borrowTitle = paramMap.get("borrowTitle");
        String username = paramMap.get("username");
        String borrowWay = paramMap.get("borrowWay");
        String timeStart = paramMap.get("timeStart");
        String timeEnd = paramMap.get("timeEnd");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        Map<String, String> map = statisManageService.queryborrowInfoDeatil(pageBean, borrowTitle, username, borrowWay, timeStart, timeEnd, createTimeStart, createTimeEnd);
	    request().setAttribute("map", map);
        return SUCCESS;
	}
	/*
	 * 导出借款信息
	 */
	public String exportBorrowInfoDeatilMethod() throws SQLException{
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
	    String borrowTitle = paramMap.get("borrowTitle");
        String username = paramMap.get("username");
        String borrowWay = paramMap.get("borrowWay");
        String timeStart = paramMap.get("timeStart");
        String timeEnd = paramMap.get("timeEnd");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        statisManageService.queryborrowInfoDeatil(pageBean, borrowTitle, username, borrowWay, timeStart, timeEnd, createTimeStart, createTimeEnd);
        try {
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
            HSSFWorkbook wb = ExcelUtils.exportExcel("借款信息统计", pageBean
                    .getPage(), new String[] { "标的名称","借款人用户名", "借款人真实姓名", "借款类型","借款金额","年利率","借款期限","发标日期","起息日期","标的到期日","管理费","应收回款","应付利息","已还本金","已还利息","未还本金","未还利息"}, new String[] { 
                     "a2", "a3","a4","a5","a7","a8","a9","a10","a11","a12","a13","a14","a15","a16","a17","a18","a19"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出借款信息统计", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
	    return null;
	}
	/*
	 * 还款信息初始化
	 */
	public String investInfoDetailInitMethod(){
	    return SUCCESS;
	}
	/*
	 * 还款信息列表
	 */
	public String investInfoDetailMethod() throws SQLException{
	    String borrowTitle = paramMap.get("borrowTitle");
        String username = paramMap.get("username");
        String borrowWay = paramMap.get("borrowWay");
        String timeStart = paramMap.get("timeStart");
        String timeEnd = paramMap.get("timeEnd");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        String investTimeStart = paramMap.get("investTimeStart");
        String investTimeEnd = paramMap.get("investTimeEnd");
        String reffere = paramMap.get("reffere");
        Map<String, String> map = statisManageService.queryinvestInfoDetail(pageBean, borrowTitle, username, borrowWay, timeStart, timeEnd, createTimeStart, createTimeEnd,investTimeStart,investTimeEnd,reffere);
        request().setAttribute("map", map);
        return SUCCESS;
	}
	/*
	 * 导出还款信息
	 */
	public String exportInvestInfoDetailMethod() throws SQLException{
	    String borrowTitle = paramMap.get("borrowTitle");
        String username = paramMap.get("username");
        String borrowWay = paramMap.get("borrowWay");
        String timeStart = paramMap.get("timeStart");
        String timeEnd = paramMap.get("timeEnd");
        String createTimeStart = paramMap.get("createTimeStart");
        String createTimeEnd = paramMap.get("createTimeEnd");
        String investTimeStart = paramMap.get("investTimeStart");
        String investTimeEnd = paramMap.get("investTimeEnd");
        String reffere = paramMap.get("reffere");
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);
        statisManageService.queryinvestInfoDetail(pageBean, borrowTitle, username, borrowWay, timeStart, timeEnd, createTimeStart, createTimeEnd,investTimeStart,investTimeEnd,reffere);
        try {
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
            HSSFWorkbook wb = ExcelUtils.exportExcel("还款信息统计", pageBean
                    .getPage(), new String[] { "标的名称","期数/总期数","借款人用户名", "借款人真实姓名", "借款类型","借款金额","年利率","借款期限","发标日期","起息日期","标的到期日","应还款日","应还本息","应还本金","应还利息","推荐人"}, new String[] { 
                     "a1","a12","a2", "a3","a6","a4","a5","a7","a8","a9","a10","a11","a13","a14","a15","reffere"});
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出还款信息统计", 2);
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return null;
	}
	
	/**
	 *功能：平台统计
	 * @return
	 * @throws Exception 
	 */
	public String allUserStatics() throws Exception{
		log.info("==allUserStatics==");
//		String smonth = request.getString("smonth");
		Map m = statisManageService.allUserStatics();
		request().setAttribute("all_user", m.get("all_user"));
		request().setAttribute("recommendUser", m.get("recommendUser"));
		request().setAttribute("justaUser", m.get("justaUser"));
		request().setAttribute("hasRecharge", m.get("hasRecharge"));
		request().setAttribute("hasInvest", m.get("hasInvest"));
		request().setAttribute("publishBorrowNo", m.get("publishBorrowNo"));
		request().setAttribute("borrowAmt", m.get("borrowAmt"));
		request().setAttribute("borrowAvg", m.get("borrowAvg"));
		request().setAttribute("borrowTimeMax", m.get("borrowTimeMax"));
		request().setAttribute("borrowItemMax", m.get("borrowItemMax"));
		request().setAttribute("borrowTimeMin", m.get("borrowTimeMin"));
		request().setAttribute("borrowItemMin", m.get("borrowItemMin"));
		request().setAttribute("borrowTimeAvg", m.get("borrowTimeAvg"));
		
		request().setAttribute("allRecharge", m.get("allRecharge"));
		request().setAttribute("onlineRecharge", m.get("onlineRecharge"));
		request().setAttribute("onlineCostFee", m.get("onlineCostFee"));
		request().setAttribute("backerAmt", m.get("backerAmt"));
		request().setAttribute("cashToBank", m.get("cashToBank"));
		request().setAttribute("cashToBankFee", m.get("cashToBankFee"));
		request().setAttribute("manageFee", m.get("manageFee"));
		request().setAttribute("investAmt", m.get("investAmt"));
		request().setAttribute("intrestAmt", m.get("intrestAmt"));
		request().setAttribute("macketFee", m.get("macketFee"));
		return SUCCESS;
	}
	
	/**
	 *功能：平台信息统计
	 * @return
	 * @throws Exception 
	 */
	public String allUserStaticsExcel() throws Exception{
		log.info("==allUserStaticsExcel==");
		Map m = statisManageService.allUserStatics();
		request().setAttribute("all_user", m.get("all_user"));
		request().setAttribute("recommendUser", m.get("recommendUser"));
		request().setAttribute("justaUser", m.get("justaUser"));
		request().setAttribute("hasRecharge", m.get("hasRecharge"));
		request().setAttribute("hasInvest", m.get("hasInvest"));
		request().setAttribute("publishBorrowNo", m.get("publishBorrowNo"));
		request().setAttribute("borrowAmt", m.get("borrowAmt"));
		request().setAttribute("borrowAvg", m.get("borrowAvg"));
		request().setAttribute("borrowTimeMax", m.get("borrowTimeMax"));
		request().setAttribute("borrowItemMax", m.get("borrowItemMax"));
		request().setAttribute("borrowTimeMin", m.get("borrowTimeMin"));
		request().setAttribute("borrowItemMin", m.get("borrowItemMin"));
		request().setAttribute("borrowTimeAvg", m.get("borrowTimeAvg"));
		
		request().setAttribute("allRecharge", m.get("allRecharge"));
		request().setAttribute("onlineRecharge", m.get("onlineRecharge"));
		request().setAttribute("onlineCostFee", m.get("onlineCostFee"));
		request().setAttribute("backerAmt", m.get("backerAmt"));
		request().setAttribute("cashToBank", m.get("cashToBank"));
		request().setAttribute("cashToBankFee", m.get("cashToBankFee"));
		
		request().setAttribute("manageFee", m.get("manageFee"));
		request().setAttribute("investAmt", m.get("investAmt"));
		request().setAttribute("intrestAmt", m.get("intrestAmt"));
		request().setAttribute("macketFee", m.get("macketFee"));
        try {
        	//会员统计 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet =  wb.createSheet("平台信息统计");
			HSSFRow topRow0 = sheet.createRow(0);
			String [] title0 = {"累计会员数","有推荐人会员数","无推荐人的会员数","有充值记录的会员数","有投标记录的会员数"};
			for(int i = 0; i < title0.length; i++){
				ExcelUtils.setCellGBKValue(topRow0.createCell((short) i), title0[i]);
			}
			
			HSSFRow topRow1 = sheet.createRow(1);
			String [] title1 = {""+m.get("all_user"),""+m.get("recommendUser"),""+m.get("justaUser"),""+m.get("hasRecharge"),""+m.get("hasInvest")};
			for(int i = 0; i < title1.length; i++){
				ExcelUtils.setCellGBKValue(topRow1.createCell((short) i), title1[i]);
			}
			
			
			//借款统计 
			HSSFRow topRow3 = sheet.createRow(3);
			String [] title3 = {"发布借款数","借款金额 ","平均金额","最长满标时间","对应借款","最短满标时间","对应借款 ","平均满标时间 "};
			for(int i = 0; i < title3.length; i++){
				ExcelUtils.setCellGBKValue(topRow3.createCell((short) i), title3[i]);
			}
			HSSFRow topRow4 = sheet.createRow(4);
			String [] title4 = {""+m.get("publishBorrowNo"),""+m.get("borrowAmt"),""+m.get("borrowAvg"),""+m.get("borrowTimeMax"),
					""+m.get("borrowItemMax"),""+m.get("borrowTimeMin"),""+m.get("borrowItemMin"),""+m.get("borrowTimeAvg")};
			for(int i = 0; i < title4.length; i++){
				ExcelUtils.setCellGBKValue(topRow4.createCell((short) i), title4[i]);
			}
			

			//充值、提现统计
			HSSFRow topRow6 = sheet.createRow(6);
			String [] title6 = {"累计充值金额 ","累计线上充值金额 ","线上充值手续费 ","累计线下充值金额","累计提现金额","累计提现手续费 "};
			for(int i = 0; i < title6.length; i++){
				ExcelUtils.setCellGBKValue(topRow6.createCell((short) i), title6[i]);
			}
			HSSFRow topRow7 = sheet.createRow(7);
			String [] title7 = {""+m.get("allRecharge"),""+m.get("onlineRecharge"),""+m.get("onlineCostFee"),""+m.get("backerAmt"),""+m.get("cashToBank"),""+m.get("cashToBankFee")};
			for(int i = 0; i < title7.length; i++){
				ExcelUtils.setCellGBKValue(topRow7.createCell((short) i), title7[i]);
			}

			//平台统计 
			HSSFRow topRow8 = sheet.createRow(9);
			String [] title8 = {"累计收取管理费、服务费  ","累计投资金额  ","累计发放利息 ","累计营销支出（奖励充值、体验券利息）   "};
			for(int i = 0; i < title8.length; i++){
				ExcelUtils.setCellGBKValue(topRow8.createCell((short) i), title8[i]);
			}
			HSSFRow topRow9 = sheet.createRow(10);
			String [] title9 = {""+m.get("manageFee"),""+m.get("investAmt"),""+m.get("intrestAmt"),""+m.get("macketFee")};
			for(int i = 0; i < title9.length; i++){
				ExcelUtils.setCellGBKValue(topRow9.createCell((short) i), title9[i]);
			}
			
			
			
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
			        IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("", admin
			        .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
			        "平台信息统计", 2);
		} catch (Exception e) {
			log.error("error:", e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 *功能：月统计
	 * @return
	 * @throws Exception 
	 */
	public String monthUserStatics() throws Exception{
		log.info("==monthUserStatics==");
		String smonth = request.getString("smonth");
		if(null == smonth){
			java.util.Calendar c = java.util.Calendar.getInstance();
			smonth = new java.text.SimpleDateFormat("yyyyMM").format(c.getTime());
		}
		
		Map m = statisManageService.monthUserStatics(smonth);
		request().setAttribute("all_user", m.get("all_user"));
		request().setAttribute("recommendUser", m.get("recommendUser"));
		request().setAttribute("justaUser", m.get("justaUser"));
		request().setAttribute("hasRecharge", m.get("hasRecharge"));
		request().setAttribute("hasInvest", m.get("hasInvest"));
		request().setAttribute("publishBorrowNo", m.get("publishBorrowNo"));
		request().setAttribute("borrowAmt", m.get("borrowAmt"));
		request().setAttribute("borrowAvg", m.get("borrowAvg"));
		request().setAttribute("borrowTimeMax", m.get("borrowTimeMax"));
		request().setAttribute("borrowItemMax", m.get("borrowItemMax"));
		request().setAttribute("borrowTimeMin", m.get("borrowTimeMin"));
		request().setAttribute("borrowItemMin", m.get("borrowItemMin"));
		request().setAttribute("borrowTimeAvg", m.get("borrowTimeAvg"));
		
		request().setAttribute("allRecharge", m.get("allRecharge"));
		request().setAttribute("onlineRecharge", m.get("onlineRecharge"));
		request().setAttribute("onlineCostFee", m.get("onlineCostFee"));
		request().setAttribute("backerAmt", m.get("backerAmt"));
		request().setAttribute("cashToBank", m.get("cashToBank"));
		request().setAttribute("cashToBankFee", m.get("cashToBankFee"));
		request().setAttribute("manageFee", m.get("manageFee"));
		request().setAttribute("investAmt", m.get("investAmt"));
		request().setAttribute("intrestAmt", m.get("intrestAmt"));
		request().setAttribute("macketFee", m.get("macketFee"));

		request().setAttribute("smonth", smonth);
		return SUCCESS;
	}
	
	public String amonthStaticsExcel()throws Exception{
		log.info("==amonthStaticsExcel==");
		String smonth = request.getString("smonth");
		Map m = statisManageService.monthUserStatics(smonth);
		request().setAttribute("all_user", m.get("all_user"));
		request().setAttribute("recommendUser", m.get("recommendUser"));
		request().setAttribute("justaUser", m.get("justaUser"));
		request().setAttribute("hasRecharge", m.get("hasRecharge"));
		request().setAttribute("hasInvest", m.get("hasInvest"));
		request().setAttribute("publishBorrowNo", m.get("publishBorrowNo"));
		request().setAttribute("borrowAmt", m.get("borrowAmt"));
		request().setAttribute("borrowAvg", m.get("borrowAvg"));
		request().setAttribute("borrowTimeMax", m.get("borrowTimeMax"));
		request().setAttribute("borrowItemMax", m.get("borrowItemMax"));
		request().setAttribute("borrowTimeMin", m.get("borrowTimeMin"));
		request().setAttribute("borrowItemMin", m.get("borrowItemMin"));
		request().setAttribute("borrowTimeAvg", m.get("borrowTimeAvg"));
		
		request().setAttribute("allRecharge", m.get("allRecharge"));
		request().setAttribute("onlineRecharge", m.get("onlineRecharge"));
		request().setAttribute("onlineCostFee", m.get("onlineCostFee"));
		request().setAttribute("backerAmt", m.get("backerAmt"));
		request().setAttribute("cashToBank", m.get("cashToBank"));
		request().setAttribute("cashToBankFee", m.get("cashToBankFee"));
		
		request().setAttribute("manageFee", m.get("manageFee"));
		request().setAttribute("investAmt", m.get("investAmt"));
		request().setAttribute("intrestAmt", m.get("intrestAmt"));
		request().setAttribute("macketFee", m.get("macketFee"));
        try {
        	//会员统计 
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet =  wb.createSheet("平台信息统计");
			HSSFRow topRow0 = sheet.createRow(0);
			String [] title0 = {"累计会员数","有推荐人会员数","无推荐人的会员数"/*,"有充值记录的会员数","有投标记录的会员数"*/};
			for(int i = 0; i < title0.length; i++){
				ExcelUtils.setCellGBKValue(topRow0.createCell((short) i), title0[i]);
			}
			
			HSSFRow topRow1 = sheet.createRow(1);
			String [] title1 = {""+m.get("all_user"),""+m.get("recommendUser"),""+m.get("justaUser")/*,""+m.get("hasRecharge"),""+m.get("hasInvest")*/};
			for(int i = 0; i < title1.length; i++){
				ExcelUtils.setCellGBKValue(topRow1.createCell((short) i), title1[i]);
			}
			
			
			//借款统计 
			HSSFRow topRow3 = sheet.createRow(3);
			String [] title3 = {"发布借款数","借款金额 ","平均金额","最长满标时间","对应借款","最短满标时间","对应借款 ","平均满标时间 "};
			for(int i = 0; i < title3.length; i++){
				ExcelUtils.setCellGBKValue(topRow3.createCell((short) i), title3[i]);
			}
			HSSFRow topRow4 = sheet.createRow(4);
			String [] title4 = {""+m.get("publishBorrowNo"),""+m.get("borrowAmt"),""+m.get("borrowAvg"),""+m.get("borrowTimeMax"),
					""+m.get("borrowItemMax"),""+m.get("borrowTimeMin"),""+m.get("borrowItemMin"),""+m.get("borrowTimeAvg")};
			for(int i = 0; i < title4.length; i++){
				ExcelUtils.setCellGBKValue(topRow4.createCell((short) i), title4[i]);
			}
			

			//充值、提现统计
			HSSFRow topRow6 = sheet.createRow(6);
			String [] title6 = {"累计充值金额 ","累计线上充值金额 ","线上充值手续费 ","累计线下充值金额","累计提现金额","累计提现手续费 "};
			for(int i = 0; i < title6.length; i++){
				ExcelUtils.setCellGBKValue(topRow6.createCell((short) i), title6[i]);
			}
			HSSFRow topRow7 = sheet.createRow(7);
			String [] title7 = {""+m.get("allRecharge"),""+m.get("onlineRecharge"),""+m.get("onlineCostFee"),""+m.get("backerAmt"),""+m.get("cashToBank"),""+m.get("cashToBankFee")};
			for(int i = 0; i < title7.length; i++){
				ExcelUtils.setCellGBKValue(topRow7.createCell((short) i), title7[i]);
			}

			//平台统计 
			HSSFRow topRow8 = sheet.createRow(9);
			String [] title8 = {"累计投资金额  "/*,"累计收取管理费、服务费  ","累计发放利息 ","累计营销支出（奖励充值、体验券利息）   "*/};
			for(int i = 0; i < title8.length; i++){
				ExcelUtils.setCellGBKValue(topRow8.createCell((short) i), title8[i]);
			}
			HSSFRow topRow9 = sheet.createRow(10);
			String [] title9 = {""+m.get("investAmt")/*,""+m.get("manageFee"),""+m.get("intrestAmt"),""+m.get("macketFee")*/};
			for(int i = 0; i < title9.length; i++){
				ExcelUtils.setCellGBKValue(topRow9.createCell((short) i), title9[i]);
			}
			
			
			
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
			        IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("", admin
			        .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
			        "月信息统计", 2);
		} catch (Exception e) {
			log.error("error:", e);
			e.printStackTrace();
		}
		return null;
	
	}
	
	/**
	 *功能：奖励统计页面
	 * @return
	 */
	public String recommendAward(){
		return SUCCESS;
	}
	public String redInvestAward(){
		return SUCCESS;
	}
	
	
	/**
	 *功能：奖励注册
	 * @return
	 */
	public String recommendAwardList(){
		log.info("=====recommendAwardList==");
		String sversion = paramMap.get("sversion");
        pageBean.setPageSize(10);
		try {
			Map map = statisManageService.regeditStatics(paramMap,pageBean);
			request().setAttribute("sumAward", map.get("sumAward"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		if ("v2".equals(sversion)){
			return "successv2";
		}
		return SUCCESS;
	}
	
	
	public String retInvestAwardList(){

		log.info("=====retInvestAwardList==");
		String sversion = paramMap.get("sversion");
        pageBean.setPageSize(10);
		try {
			Map map = statisManageService.retInvestAwardList(paramMap,pageBean);
			request().setAttribute("sumAward", map.get("sumAward"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		if ("v2".equals(sversion)){
			return "successv2";
		}
		return SUCCESS;
	
	}
	
	
	/**
	 * 
	 *功能：投资奖励导出
	 * @return
	 * @throws Exception
	 */
	public String redInvestAwardExcel()throws Exception{
		log.info("==redInvestAwardExcel==");
		
		String sversion = request.getString("sversion");
		String userName = request.getString("userName");
		String realName = request.getString("realName");
		String isEmployee = request.getString("isEmployee");
		String awardFrom = request.getString("awardFrom");
		String awardTo = request.getString("awardTo");
		String memberTimes = request.getString("memberTimes");
		String memberTimee = request.getString("memberTimee");
		Map <String, String> tmap = new HashMap<String, String>();
		tmap.put("sversion", sversion);
		tmap.put("userName", userName);
		tmap.put("realName", realName);
		tmap.put("isEmployee", isEmployee);
		tmap.put("awardFrom", awardFrom);
		tmap.put("awardTo", awardTo);
		tmap.put("memberTimes", memberTimes);
		tmap.put("memberTimee", memberTimee);
		pageBean.setPageNum(1);
        pageBean.setPageSize(5000);
		try {
			Map map = statisManageService.retInvestAwardList(tmap,pageBean);
			request().setAttribute("sumAward", map.get("sumAward"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
        	List list = pageBean.getPage();
        	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
        	for (int i=0,j=list.size(); i < j; i++){
        		Map m = (Map) list.get(i);
        		if (null == m.get("isEmployee")){
        			m.put("isEmployee", "外部会员");
        		}else {
        			m.put("isEmployee", "内部员工");
        		}
        		if("v1".equals(sversion)){
        			if(Double.parseDouble(((BigDecimal)m.get("investAmount1")).toString())==0){
            			m.put("investAmount1", "0.00");
            		} else {
            			BigDecimal b = (BigDecimal) m.get("investAmount1");
            			m.put("investAmount1",df.format(b));
            		}
            		if(Double.parseDouble(((BigDecimal)m.get("investAmount2")).toString())==0){
            			m.put("investAmount2", "0.00");
            		} else {
            			BigDecimal b = (BigDecimal) m.get("investAmount2");
            			m.put("investAmount2",df.format(b));
            		}
            		if(Double.parseDouble(((BigDecimal)m.get("mylevel1")).toString())==0){
            			m.put("mylevel1", "0.00");
            		}else {
            			BigDecimal b = (BigDecimal) m.get("mylevel1");
            			m.put("mylevel1",df.format(b));
            		}
            		if(Double.parseDouble(((BigDecimal)m.get("mylevel2")).toString())==0){
            			m.put("mylevel2", "0.00");
            		}else {
            			BigDecimal b = (BigDecimal) m.get("mylevel2");
            			m.put("mylevel2",df.format(b));
            		}
        		}
        		
        		if(Double.parseDouble(((BigDecimal)m.get("sumAll")).toString())==0){
        			m.put("sumAll", "0.00");
        		}else {
        			BigDecimal b = (BigDecimal) m.get("sumAll");
        			m.put("sumAll",df.format(b));
        		}
        		
        	}
        	if ("v1".equals(sversion)){
        		HSSFWorkbook wb = ExcelUtils.exportExcel("推荐好友投资奖励v1", pageBean
                        .getPage(), new String[] { "用户名","真实姓名", "用户归属", "一级投资金额","一级奖励","二级投资金额","二级奖励","奖励共计"}, new String[] { 
                         "username","realName", "isEmployee","investAmount1","mylevel1","investAmount2","mylevel2","sumAll"});
                this.export(wb, new Date().getTime() + ".xls");
        	}
        	if ("v2".equals(sversion)){
        		HSSFWorkbook wb = ExcelUtils.exportExcel("推荐好友投资奖励v2", pageBean
                        .getPage(), new String[] { "用户名","真实姓名", "用户归属", "一级投资金额","一级奖励","奖励共计"}, new String[] { 
                         "username","realName", "isEmployee","investAmount","sumAll","sumAll"});
                this.export(wb, new Date().getTime() + ".xls");
        	}
        	Admin admin = (Admin) session().getAttribute(
                     IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "推荐好友投资奖励", 2);
		} catch (Exception e) {
			log.error("error:", e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *功能：注册奖励导出
	 * @return
	 * @throws Exception
	 */
	public String redregAwardExcel()throws Exception{
		log.info("==redregAwardExcel==");
		
		String sversion = request.getString("sversion");
		String userName = request.getString("userName");
		String realName = request.getString("realName");
		String isEmployee = request.getString("isEmployee");
		String awardFrom = request.getString("awardFrom");
		String awardTo = request.getString("awardTo");
		String memberTimes = request.getString("memberTimes");
		String memberTimee = request.getString("memberTimee");
		Map <String, String> tmap = new HashMap<String, String>();
		tmap.put("sversion", sversion);
		tmap.put("userName", userName);
		tmap.put("realName", realName);
		tmap.put("isEmployee", isEmployee);
		tmap.put("awardFrom", awardFrom);
		tmap.put("awardTo", awardTo);
		tmap.put("memberTimes", memberTimes);
		tmap.put("memberTimee", memberTimee);
		pageBean.setPageNum(1);
        pageBean.setPageSize(5000);
		try {
			Map map = statisManageService.regeditStatics(tmap,pageBean);
			request().setAttribute("sumAward", map.get("sumAward"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
        	List list = pageBean.getPage();
        	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
        	for (int i=0,j=list.size(); i < j; i++){
        		Map m = (Map) list.get(i);
        		if (null == m.get("isEmployee")){
        			m.put("isEmployee", "外部会员");
        		}else {
        			m.put("isEmployee", "内部员工");
        		}
        		
        		if ("v1".equals(sversion)){
        			if(Double.parseDouble(((BigDecimal)m.get("fmon")).toString())==0){
            			m.put("fmon", "0.00");
            		} else {
            			BigDecimal b = (BigDecimal) m.get("fmon");
            			m.put("fmon",df.format(b));
            		}
            		if((Long)m.get("level2")==0){
            			m.put("level2", "0");
            		}
            		if(Double.parseDouble(((BigDecimal)m.get("smon")).toString())==0){
            			m.put("smon", "0.00");
            		}else {
            			BigDecimal b = (BigDecimal) m.get("smon");
            			m.put("smon",df.format(b));
            		}
        		}
        		if((Long)m.get("level1")==0){
        			m.put("level1", "0");
        		} 
        		if(Double.parseDouble(((BigDecimal)m.get("sumAll")).toString())==0){
        			m.put("sumAll", "0.00");
        		}else {
        			BigDecimal b = (BigDecimal) m.get("sumAll");
        			m.put("sumAll",df.format(b));
        		}
        		
        	}
        	if ("v1".equals(sversion)){
        		HSSFWorkbook wb = ExcelUtils.exportExcel("推荐好友注册奖励v1", pageBean
                        .getPage(), new String[] { "用户名","真实姓名", "用户归属", "一级有效会员数","一级奖励","二级有效会员数","二级奖励","奖励共计"}, new String[] { 
                         "userName","realName", "isEmployee","level1","fmon","level2","smon","sumAll"});
                this.export(wb, new Date().getTime() + ".xls");
        	}else{
        		HSSFWorkbook wb = ExcelUtils.exportExcel("推荐好友注册奖励v2", pageBean
                        .getPage(), new String[] { "用户名","真实姓名", "用户归属", "一级有效会员数","一级奖励","奖励共计"}, new String[] { 
                         "userName","realName", "isEmployee","level1","fmon","sumAll"});
                this.export(wb, new Date().getTime() + ".xls");
        	}
        	Admin admin = (Admin) session().getAttribute(
                     IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog("", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "推荐好友注册奖励", 2);
		} catch (Exception e) {
			log.error("error:", e);
			e.printStackTrace();
		}
		return null;
	}	
	
	
	/**
	 *功能：平台日统计查询页
	 * @return
	 */
	public String alldayStatics(){
		return SUCCESS;
	}
	
	/**
	 *功能：平台日统计列表
	 * @return
	 */
	public String alldayStaticsList(){
		log.info("----alldayStaticsList---");
		
		//参数校验，封装
		Map<String, String> map = alldayParam(paramMap);
		if (null == map){
			try {
				JSONUtils.printStr("1");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return null;
		}
		try {
			Map retMap = statisManageService.alldayStaticsList(pageBean,map);
			request().setAttribute("retMap", retMap);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}

	/**
	 *功能：封装参数
	 * @return
	 */
	private Map<String, String> alldayParam(Map <String, String>paramMap) {
		String month = paramMap.get("monthsel");
		String dateStart ="";
		String dateEnd ="";
		if (!StringUtil.isEmpty(month)){
			dateStart = month.substring(0,4)+"-"+month.substring(4);
			dateStart = dateStart + "-01";
			dateEnd = getInvoiceMonth(month);
		}
		if (!StringUtil.isEmpty(month) && !StringUtil.isEmpty(paramMap.get("dateStart"))){
			String ds = paramMap.get("dateStart");
			String de = paramMap.get("dateEnd");
			if (StringUtil.isEmpty(paramMap.get("dateEnd"))&& StringUtil.isEmpty(dateEnd)){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				dateEnd = sf.format(new Date());
			}
			String month1 = month.substring(4);
			String month2 = ds.substring(5, 7);
			if (!StringUtil.isEmpty(de)){
				String month3 = de.substring(5, 7);
				if (!month1.equals(month3)){
					log.info("input error.");
					return null;
				}
			}
			
			//输入起止日期错误
			if (!month1.equals(month2)){
				log.info("input error.");
				return null;
			}
			
		}
		if (!StringUtil.isEmpty(paramMap.get("dateEnd"))){
			dateEnd = paramMap.get("dateEnd");
		}
		if (!StringUtil.isEmpty(paramMap.get("dateStart"))){
			dateStart = paramMap.get("dateStart");
		}
		
		if (StringUtil.isEmpty(paramMap.get("dateEnd"))&& StringUtil.isEmpty(dateEnd)){
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			dateEnd = sf.format(new Date());
		}
		
		String pvs = paramMap.get("pvs");
		String pve = paramMap.get("pve");
		String uvs = paramMap.get("uvs");
		String uve = paramMap.get("uve");
		
		if (StringUtil.isEmpty(pvs)){
			pvs = "-1";
		}
		if (StringUtil.isEmpty(pve)){
			pve = "-1";
		}
		if (StringUtil.isEmpty(uvs)){
			uvs = "-1";
		}
		if (StringUtil.isEmpty(uve)){
			uve = "-1";
		}
		
		if (StringUtil.isEmpty(dateStart)){
			dateStart="2014-07-07";
		}
		if (StringUtil.isEmpty(dateEnd)){
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			dateEnd = sf.format(new Date());
		}
		Map <String,String>map = new HashMap<String,String>();
		map.put("monthsel", paramMap.get("monthsel"));
		map.put("dateStart", dateStart);
		map.put("dateEnd", dateEnd);
		map.put("pvs", pvs);
		map.put("pve", pve);
		map.put("uvs", uvs);
		map.put("uve", uve);
        pageBean.setPageSize(31);
        pageBean.setPageNum(1);
		return map;
	}
	/**
	 * 
	 *功能：得到一个月的最后一天。
	 * @param invoiceMonth
	 * @return
	 */
	private static String getInvoiceMonth(String invoiceMonth) {// invoiceMonth // 的格式为 201108
		String year = invoiceMonth.substring(0, 4);
		String month = invoiceMonth.substring(4, invoiceMonth.length());

		Calendar date = Calendar.getInstance();
		int yeari = Integer.parseInt(year);
		int monthi = Integer.parseInt(month);
		date.set(yeari, monthi - 1, 1);
		int maxDayOfMonth = date.getActualMaximum(Calendar.DAY_OF_MONTH);
		String dater = year + "-" + month + "-" + maxDayOfMonth;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String today = formatter.format(new Date());
		if (dater.compareTo(today) > 0){
			
			return formatter.format(new Date());
		}
		
		return dater;
	}
	
	
	/**
	 *功能：导出用户数据
	 * @return
	 */
	public String alldayStaticsExcel() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(50000);
		try {
			// 得到页面传来的值
			String monthsel = request.getString("monthsel") == null ? "": request.getString("monthsel");
			monthsel = URLDecoder.decode(monthsel, "UTF-8");
			//真名
			String dateStart = request.getString("dateStart") == null ? "": request.getString("dateStart");
			dateStart = URLDecoder.decode(dateStart, "UTF-8");
			
			// 时间
			String dateEnd = request.getString("dateEnd") == null ? "" : request.getString("dateEnd");
			String pvs = request.getString("pvs") == null ? "" : request.getString("pvs");
			String pve = request.getString("pve") == null ? "" : request.getString("pve");
			String uvs = request.getString("uvs") == null ? "" : request.getString("uvs");
			String uve = request.getString("uve") == null ? "" : request.getString("uve");

			// 已还款记录列表
			Map <String, String> map = new HashMap<String, String>();
			map.put("monthsel", monthsel);
			map.put("dateStart", dateStart);
			map.put("dateEnd", dateEnd);
			map.put("pvs", pvs);
			map.put("pve", pve);
			map.put("uvs", uvs);
			map.put("uve", uve);
			
			
			
			Map<String, String> map1 = alldayParam(map);
			if (null == map1){
				try {
					JSONUtils.printStr("1");
				} catch (IOException e) {
					e.printStackTrace();
				} 
				return null;
			}
			try {
				statisManageService.alldayStaticsList(pageBean,map1);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
			statisManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("平台日统计",
					pageBean.getPage(),
					new String[] { "日期", "IP", "UV", "PV", "新注册用户数", "累计注册用户数",  "老用户访问人数", 
									"新用户充值人数","有效客户数 ", "新用户充值金额", "当日充值金融","新用户投资金额 ", "投标金额", "提现金额", 
									"提现手续费", "发标金额", "访问注册转化率", "新用户投资转化率", "新用户充值转化率"
							}, new String[] {
							"theDate", "ip", "uv", "pv", "todayRegNum", "regNum","oldVisit", "todayregRecharge",
							"effectReg", "newUserRecharge", "todayregMoney","newInvestSum", "allInvest",
							"allWithdraw", "poundage", "borrowAmount", "uvRate", "investRate","rechargeRate" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "平台日统计", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	//涨薪宝查询初始化
	public String paytreasureInit() throws SQLException{
		Map<String, String> sumPayInvest = payTreasureService.querySumPayInvest();
		request().setAttribute("sumPayInvest", sumPayInvest);
		return SUCCESS;
	}
	
	//涨薪宝查询
	public String paytreasureInfo() throws SQLException{
		payTreasureService.queryAllPayInvest(pageBean, paramMap);
		return SUCCESS;
	}
	
	//导出数据
	public String paytreasureExcel() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(50000);
		try {
			payTreasureService.queryAllPayInvest(pageBean, paramMap);
			
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
			statisManageService.changeNumToStr(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("平台日统计",
					pageBean.getPage(),
					new String[] { "用户名", "真实姓名", "手机号码", "涨薪宝余额", "涨薪宝总收益"
							}, new String[] {
							"username", "realName","cellPhone", "investamount","allprofit" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_user",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "涨薪宝", 2);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	//资金操作记录
	public String payInvestRecord() throws SQLException{
		long userId = Convert.strToLong(request("id"), -1L);
		payTreasureService.payInvestRecord(pageBean, userId,paramMap);
		request().setAttribute("id", userId);
		return SUCCESS;
	}
	
	
	
	//收益记录
	public String profitRecord() throws SQLException{
		long userId = Convert.strToLong(request("id"), -1L);
		payTreasureService.profitRecord(pageBean, userId,paramMap);
		request().setAttribute("id", userId);
		return SUCCESS;
	}
	
	
		//资金操作记录导出
		public String payInvestRecordExcel() throws SQLException{
			pageBean.pageNum = 1;
			pageBean.setPageSize(50000);
			try {
				long userId = Convert.strToLong(request("id"), -1L);
				payTreasureService.payInvestRecord(pageBean, userId,paramMap);
				
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
				HSSFWorkbook wb = ExcelUtils.exportExcel("平台日统计",
						pageBean.getPage(),
						new String[] { "操作金额","涨薪宝总金额", "操作时间", "操作类型(1转入，2转出)"
								}, new String[] {
								"handamount","investamount", "time","type"});
				this.export(wb, new Date().getTime() + ".xls");
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_payinvesth",
						admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
						0, "涨薪宝", 2);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		}
		
		
		
		//收益记录导出
		public String profitRecordExcel() throws SQLException{
			pageBean.pageNum = 1;
			pageBean.setPageSize(50000);
			try {
				long userId = Convert.strToLong(request("id"), -1L);
				payTreasureService.profitRecord(pageBean, userId,paramMap);
				
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
				statisManageService.changeNumToStr(pageBean);
				HSSFWorkbook wb = ExcelUtils.exportExcel("平台日统计",
						pageBean.getPage(),
						new String[] { "收益金额", "收益时间"
								}, new String[] {
								"profitamount", "time"});
				this.export(wb, new Date().getTime() + ".xls");
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_payprofit",
						admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
						0, "涨薪宝", 2);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		}
	
		
		//所有的转入转出记录
		public String payInvestRecordAllList() throws SQLException{
			List<Map<String, Object>> list= payTreasureService.payInvestRecord(pageBean, paramMap);
			request().setAttribute("list", list);
			return SUCCESS;
		}
		
		
		//所有的收益记录
		public String profitRecordAllList() throws SQLException{
			Map<String, String> map = payTreasureService.profitRecord(pageBean, paramMap);
			request().setAttribute("map", map);
			return SUCCESS;
		}
		
		//导出所有的转入转出记录
		public String payInvestRecordAllExcel(){
			pageBean.pageNum = 1;
			pageBean.setPageSize(50000);
			try {
				payTreasureService.payInvestRecord(pageBean, paramMap);
				
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
				statisManageService.changeNumToStr(pageBean);
				HSSFWorkbook wb = ExcelUtils.exportExcel("平台日统计",
						pageBean.getPage(),
						new String[] { "用户名","真实姓名","操作金额", "操作时间", "操作类型(1转入，2转出)"
								}, new String[] {"username","realName",
								"handamount", "time","type"});
				this.export(wb, new Date().getTime() + ".xls");
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_payinvesth",
						admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
						0, "涨薪宝", 2);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		}
		
		//导出所有的收益记录
		public String profitRecordAllExcel(){
			pageBean.pageNum = 1;
			pageBean.setPageSize(50000);
			try {
				payTreasureService.profitRecord(pageBean, paramMap);
				
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
				statisManageService.changeNumToStr(pageBean);
				HSSFWorkbook wb = ExcelUtils.exportExcel("平台日统计",
						pageBean.getPage(),
						new String[] {"用户名","真实姓名","收益金额", "收益时间"
								}, new String[] { "username","realName",
								"profitamount", "time"});
				this.export(wb, new Date().getTime() + ".xls");
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_payprofit",
						admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
						0, "涨薪宝", 2);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		}
		
		//每日记录
		public String paydailyrecordList() throws SQLException{
			payTreasureService.paydailyrecordList(pageBean, paramMap);
			return SUCCESS;
		}
		
		//每日记录导出
		public String paydailyrecordExcel(){
			pageBean.pageNum = 1;
			pageBean.setPageSize(50000);
			try {
				payTreasureService.paydailyrecordList(pageBean, paramMap);
				
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
				statisManageService.changeNumToStr(pageBean);
				HSSFWorkbook wb = ExcelUtils.exportExcel("平台日统计",
						pageBean.getPage(),
						new String[] {"日期","当日转入总金额", "当日转出总金额","当日发放收益","当日总额" 
								}, new String[] { "time","intoamount", "outamount","allprofit", "investamount"});
				this.export(wb, new Date().getTime() + ".xls");
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_paydailyrecord",
						admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
						0, "涨薪宝", 2);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		}
}
