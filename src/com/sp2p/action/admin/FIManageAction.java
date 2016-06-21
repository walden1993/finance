package com.sp2p.action.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.DocumentException;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.ServletUtils;
import com.shove.vo.Files;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.FrontMyFinanceAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.PayTreasureService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMessageService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;

/**
 * 后台财务管理
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class FIManageAction extends BasePageAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private UserService userService;
	private AdminService adminService;
	private SelectedService selectedService;
	private SendMessageService sendMessageService;
	private FundManagementService fundManagementService;
	private PayTreasureService payTreasureService;
	
	
	private Files files;//要批量上传的文件
	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}
	
	
	
	
	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}




	public Files getFiles() {
        return files;
    }



    public void setFiles(Files files) {
        this.files = files;
    }



    public void setFundManagementService(
			FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	/**
	 * 充值扣费
	 */

	private long userId;
	/**
	 * 财务管理 充值提现审核 全部提现状态
	 */
	private List<Map<String, Object>> operateType;
	private List<Map<String, Object>> status;
	private List<Map<String, Object>> results;
	/**
	 * 财务管理 充值记录管理
	 */
	private List<Map<String, Object>> rechargeTypes;

	public String queryRechargeRecordInit() {
		return SUCCESS;
	}

	/**
	 * 财务管理 充值记录查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryRechargeRecordList() throws Exception {

		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String userType = Convert.strToStr(paramMap.get("userType"), null);
		String startTime = Convert.strToStr(paramMap.get("startTime"),
				null);
		String orderId = Convert.strToStr(paramMap.get("orderId"), null);
		String endTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("endTime"), null));
		int rechargeType = Convert.strToInt(paramMap.get("rechargeType"), -100);
		/*String reEndTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("rechargeTime"), null));*/
		Integer result = paramMap.get("status") == null ? -100 : Convert
				.strToInt(paramMap.get("status"), -100);
		try {
		    //修正  2014-09-15 09:43:55 hjh
			Map<String, String> map = fundManagementService.queryRechargeRecordList(pageBean, userName,userType,
					startTime, endTime, rechargeType, result,orderId);
			List<Map<String, Object>> list = pageBean.getPage();
            if (list!=null&&list.size()>0) {
                for (Map<String, Object> itemMap : list) {
                    if (Convert.strToInt(itemMap.get("rechargeType").toString(), -1)==8) {
                        DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                        String orderid = format.format(itemMap.get("rechargeTime"))+"_"+itemMap.get("reId").toString()+"_"+itemMap.get("userId").toString();
                        itemMap.put("orderId", orderid);
                    }
                }
            }
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
			request().setAttribute("map", map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 导出充值记录查询
	 * 
	 * @return
	 */
	public String exportRechargeRecord() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			String userName = request.getString("userName");
			String userType = request.getString("userType");
			userName = URLDecoder.decode(userName, "UTF-8");
			String rechargeTime =request.getString("rechargeTime");
			String reEndTime =request.getString("reEndTime"); 
			
			reEndTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(reEndTime, null));
//			if (StringUtils.isBlank(reEndTime)&&StringUtils.isNotBlank(rechargeTime)) {
//				reEndTime = FrontMyPaymentAction.changeEndTime(rechargeTime);
//			}
			int rechargeType = request.getInt("rechargeType",-1);
			int statss =request.getInt("statss",-1);
			// 待还款详情
			fundManagementService.queryRechargeRecordList(pageBean, userName,userType,
					rechargeTime, reEndTime, rechargeType, statss,null);
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
			fundManagementService.changeFigure(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值记录",
					pageBean.getPage(), new String[] { "用户名", "充值类型", "充值金额",
							"费率", "到账金额", "充值时间", "状态" }, new String[] {
							"username", "rechargeType", "rechargeMoney",
							"cost", "realMoney", "rechargeTime", "result" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog(
					"v_t_user_rechargedetails_list", admin.getUserName(),
					IConstants.EXCEL, admin.getLastIP(), 0, "导出充值记录列表", 2);
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
	 * 财务管理 充值记录 第一次充值查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryRechargeFirstList() throws Exception {

		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String userType = Convert.strToStr(paramMap.get("userType"), null);
		String reStartTime = Convert
				.strToStr(paramMap.get("reStartTime"), null);
		int rechargeType = Convert.strToInt(paramMap.get("rechargeType"), -100);
		String reEndTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("reEndTime"), null));
		try {

			fundManagementService.queryRechargeFirstList(pageBean, userName,userType,
					reStartTime, reEndTime, rechargeType);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 导出充值记录 第一次充值查询
	 * 
	 * @return
	 */
	public String exportRechargeFirst() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			String userName = request.getString("userName");
			String userType = request.getString("userType");
			if (StringUtils.isNotBlank(userName)) {
				userName = URLDecoder.decode(userName, "UTF-8");
			}
			String reStartTime =request.getString("reStartTime"); 
			int rechargeType = Convert.strToInt(paramMap.get("rechargeType"),
					-100);
			String reEndTime = FrontMyPaymentAction.changeEndTime(request.getString("reEndTime"));

			// 待还款详情
			fundManagementService.queryRechargeFirstList(pageBean, userName,userType,
					reStartTime, reEndTime, rechargeType);
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
			fundManagementService.changeFigure(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值记录",
					pageBean.getPage(), new String[] { "用户名", "充值类型", "充值金额",
							"费率", "到账金额", "充值时间", "状态" }, new String[] {
							"username", "rechargeType", "rechargeMoney",
							"cost", "realMoney", "rechargeTime", "result" });
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_user_rechargefirst_lists",
					admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
					0, "导出充值记录第一次充值查询", 2);
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
	 * 查看第一次充值数据
	 * 
	 * @throws Exception
	 */
	public String queryOneFirstChargeDetails() throws Exception {
		Long rechargeId = request.getString("rechargeId") == null ? -100 : request.getLong("rechargeId", -1);
		try {
			paramMap = fundManagementService.queryOneFirstChargeDetails(
					rechargeId, false);
			if (paramMap != null) {
				String resultId = paramMap.get("result").toString();
				if (resultId.equals(0 + "")) {// 失败
					paramMap.put("realMoney", "0.00");
				}
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 财务管理 充值提现审核 全部提现
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAllWithdrawList() throws Exception {

		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String endTime = Convert.strToStr(paramMap.get("endTime"), null);
		endTime = FrontMyPaymentAction.changeEndTime(endTime);
		String realName = Convert.strToStr(paramMap.get("realName"), null);
		String userType = Convert.strToStr(paramMap.get("userType"), null);
		String firstAudits = Convert.strToStr(paramMap.get("firstAudits"), null);
		String firstAudite = Convert.strToStr(paramMap.get("firstAudite"), null);
		String checkAudits = Convert.strToStr(paramMap.get("checkAudits"), null);
		String checkAudite = Convert.strToStr(paramMap.get("checkAudite"), null);
		
		Integer status = paramMap.get("status") == null ? -100 : Convert
				.strToInt(paramMap.get("status"), -100);

		try {
            Map map1 = new HashMap();
            map1.put("userName", userName);
            map1.put("realName", realName);
            map1.put("userType", userType);
            map1.put("startTime", startTime);
            map1.put("endTime", endTime);
            map1.put("status", status);
            map1.put("firstAudits", firstAudits);
            map1.put("firstAudite", firstAudite);
            map1.put("checkAudits", checkAudits);
            map1.put("checkAudite", checkAudite);
            
			Map<String, String> map = fundManagementService.queryAllWithdrawList(pageBean, map1);
			int pageNum = (int) (pageBean.getPageNum() - 1)
                    * pageBean.getPageSize();
			request().setAttribute("map", map);
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("status", status);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 提现 --- 导出
	 * 
	 * @return
	 */
	public String exportAllWithdraw() {
		log.info("=========exportAllWithdraw=========");
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Integer status = request.getInt("statss", -1);
		String exporName = "";
		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			String userName = request.getString("userName") == null ? ""
					: request.getString("userName");
			String realName = request.getString("realName") == null ? ""
					: request.getString("userName");
			String userType = request.getString("userType") == null ? ""
					: request.getString("userName");
			String startTime = request.getString("startTime") == null ? ""
					: request.getString("startTime");
			String endTime = request.getString("endTime") == null ? ""
					: request.getString("endTime");
			String firstAudits = request.getString("firstAudits") == null ? ""
					: request.getString("firstAudits");
			String firstAudite = request.getString("firstAudite") == null ? ""
					: request.getString("firstAudite");
			String checkAudits = request.getString("checkAudits") == null ? ""
					: request.getString("checkAudits");
			String checkAudite = request.getString("checkAudite") == null ? ""
					: request.getString("checkAudite");
			String type = request.getString("type");
			userName = URLDecoder.decode(userName, "UTF-8");// 中文乱码转换
			startTime = URLDecoder.decode(startTime, "UTF-8");
			endTime = URLDecoder.decode(endTime, "UTF-8");
			if (StringUtils.isNotBlank(endTime)) {
				endTime = FrontMyPaymentAction.changeEndTime(endTime);
			}
			
			if ("1".equals(type)) {
				Map map1 = new HashMap();
				map1.put("userName", userName);
				map1.put("realName", realName);
				map1.put("userType", userType);
				map1.put("startTime", startTime);
				map1.put("endTime", endTime);
				map1.put("status", status);
				map1.put("firstAudits", firstAudits);
				map1.put("firstAudite", firstAudite);
				map1.put("checkAudits", checkAudits);
				map1.put("checkAudite", checkAudite);
			  // 支付
	            fundManagementService.queryAllWithdrawList(pageBean, map1);
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

	            if (status == 0) {
	                exporName = "全部提现列表";
	            } else if (status == 1) {
	                exporName = "待审核提现列表";
	            } else if (status == 4) {
	                exporName = "转账中的提现列表";
	            } else if (status == 2) {
	                exporName = "成功的提现列表";
	            } else if (status == 5) {
	                exporName = "失败的提现列表";
	            }
	            
	            fundManagementService.changeNumToStr6(pageBean);
	            
	            HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean
	                                                     .getPage(), new String[] { "商户财务流水号", "收款人真实姓名", "开户行名称", "支行名称", "开户行所在省",
	                                                     "开户行所在城市", "银行帐号", "代付金额", "摘要", "发票(预留 0不需要 1需要)" },
	                                                     new String[] { "name", "realName", "bankName", "branchBankName", "province",
	                                                             "city", "acount", "realMoney", "market",
	                                                             "fapiao" });
	            this.export(wb, new Date().getTime() + ".xls");
	            // 系统操作日志
	            operationLogService.addOperationLog("v_t_user_withdraw_lists",
	                    admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
	                    0, "导出" + exporName, 2);
            }else {
             // 提现详情
//                fundManagementService.queryAllWithdrawList(pageBean, userName,realName,userType,
//                        startTime, endTime, status);
            	Map map1 = new HashMap();
				map1.put("userName", userName);
				map1.put("realName", realName);
				map1.put("userType", userType);
				map1.put("startTime", startTime);
				map1.put("endTime", endTime);
				map1.put("status", status);
				map1.put("firstAudits", firstAudits);
				map1.put("firstAudite", firstAudite);
				map1.put("checkAudits", checkAudits);
				map1.put("checkAudite", checkAudite);
			  // 支付
	            fundManagementService.queryAllWithdrawList(pageBean, map1);
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

                if (status == 0) {
                    exporName = "全部提现列表";
                } else if (status == 1) {
                    exporName = "待审核提现列表";
                } else if (status == 4) {
                    exporName = "转账中的提现列表";
                } else if (status == 2) {
                    exporName = "成功的提现列表";
                } else if (status == 5) {
                    exporName = "失败的提现列表";
                }
                
                fundManagementService.changeNumToStr5(pageBean);

//                String checkTitle = "初审时间";
//                if (1==status){
//                	checkTitle = "初审时间";
//                } else if (2==status){
//                	checkTitle = "复审时间";
//                } else if (3==status){
//                	checkTitle = "审核时间";
//                } else if (4==status){
//                	checkTitle = "初审时间";
//                } else if (5==status){
//                	checkTitle = "审核时间";
//                } else {
//                	checkTitle = "审核时间";
//                }
                
                String firstCheck = "";
                String secondCheck = "";
                if (1==status){
                	firstCheck = "初审时间";
                } else if (2==status){
                	secondCheck = "复审时间";
                } else if (3==status){
                	firstCheck = "初审时间";
                    secondCheck = "复审时间";
                } else if (4==status){
                	firstCheck = "初审时间";
                } else if (5==status){
                	firstCheck = "初审时间";
                    secondCheck = "复审时间";
                } else  {
                	firstCheck = "初审时间";
                    secondCheck = "复审时间";
                }
                
                HSSFWorkbook wb = ExcelUtils.exportExcel(exporName, pageBean
                        .getPage(), new String[] { "用户名", "真实姓名", "用户类型", "提现账号", "提现银行",
                        "支行", "提现总额", "到账金额(￥)", "手续费", "提现时间", firstCheck,secondCheck,"状态","推荐人" },
                        new String[] { "name", "realName", "userType", "acount", "bankName",
                                "branchBankName", "sum", "realMoney", "poundage",
                                "applyTime","firstCheck","checkTime", "status","redName" });
                this.export(wb, new Date().getTime() + ".xls");
                // 系统操作日志
                operationLogService.addOperationLog("v_t_user_withdraw_lists",
                        admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
                        0, "导出" + exporName, 2);
            }
			
			
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
	 * 财务管理 充值记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryUserCashList() throws Exception {
		String userName = paramMap.get("userName") == null ? null : Convert
				.strToStr(paramMap.get("userName"), null);
		String realName = paramMap.get("realName") == null ? null : Convert
				.strToStr(paramMap.get("realName"), null);
		String userType = paramMap.get("userType") == null ? null : Convert
				.strToStr(paramMap.get("userType"), null);
		try {
			Map<String, String> map = fundManagementService.queryUserCashList(pageBean, userName,realName,userType);
			fundManagementService.queryUserCashList(pageBean, userName,realName,userType);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("map", map);
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 财务管理 充值管理 充值/扣费
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	public String queryBackCashInit() {
		String userId = request.getString("userId");
		if (userId != null)
			paramMap.put("userId", userId);

		return SUCCESS;
	}

	public String queryBackCashList() throws Exception {
		String userId = paramMap.get("userId");
		Long uid = -100L;
		if (userId != null) {
			uid = Convert.strToLong(userId, -100);
		}
		String checkUser = paramMap.get("checkUser") == null ? null : Convert
				.strToStr(paramMap.get("checkUser"), null);// 操作人员
		String startTime = paramMap.get("startTime") == null ? null : Convert
				.strToStr(paramMap.get("startTime"), null);
		String endTime = paramMap.get("endTime") == null ? null : Convert
				.strToStr(paramMap.get("endTime"), null);
		endTime = FrontMyPaymentAction.changeEndTime(endTime);
		Integer type = paramMap.get("type") == null ? -100 : Convert.strToInt(
				paramMap.get("type"), -100);// 操作类型
		String uname = paramMap.get("uname") == null ? null : Convert.strToStr(
				paramMap.get("uname"), null);// 用户名
		String userType = paramMap.get("userType") == null ? null : Convert.strToStr(
				paramMap.get("userType"), null);//用户类型

		try {
			fundManagementService.queryBackCashList(pageBean, uid, checkUser,
					startTime, endTime, type, uname,userType);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 导出充值明细
	 * 
	 * @return
	 */
	public String exportBackCash() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		String userId = request.getString("userId");
		Long uid = -100L;
		if (userId != null) {
			uid = Convert.strToLong(userId, -100);
		}

		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			String checkUser =request.getString("checkUser") == null ? ""
					: request.getString("checkUser");
			String startTime =request.getString("startTime") == null ? ""
					: request.getString("startTime");
			String endTime = request.getString("endTime") == null ? ""
					: request.getString("endTime");
			String userType = paramMap.get("userType") == null ? null : Convert.strToStr(
					paramMap.get("userType"), null);//用户类型
			if (StringUtils.isNotBlank(endTime)) {
				endTime = FrontMyPaymentAction.changeEndTime(endTime);
			}
			Integer type = request.getInt("type",-1);
			String uname = request.getString("userName") == null ? ""
					: request.getString("userName");
			// 中文乱码转换
			checkUser = URLDecoder.decode(checkUser, "UTF-8");
			uname = URLDecoder.decode(uname, "UTF-8");
			// 待还款详情
			fundManagementService.queryBackCashList(pageBean, uid, checkUser,
					startTime, endTime, type, uname,userType);
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
			fundManagementService.changeFigure2(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值明细",
					pageBean.getPage(), new String[] { "用户名", "真实姓名", "类型",
							"操作金额", "备注", "操作人员", "操作时间" }, new String[] {
							"uname", "realName", "type", "dealMoney", "remark",
							"userName", "checkTime" });
			this.export(wb, new Date().getTime() + ".xls");
			// 添加系统操作日志
			operationLogService.addOperationLog("v_t_user_backrw_lists", admin
					.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
					"导出充值明细", 2);
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

	public String queryR_WShow() throws Exception {
		Long rwId = request.getString("rwId") == null ? -100 : request.getLong("rwId", -1);

		try {
			paramMap = fundManagementService.queryR_WInfo(rwId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String addBackRechargeInit() throws Exception {
		String userId = request.getString("userId");

		try {
			if (userId != null) {
				Map<String, String> map = userService.queryUserById(Convert
						.strToLong(userId, -100));
				if (map != null) {
					paramMap.put("userName", map.get("username"));
					paramMap.put("userId", userId);
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String addBackWithdrawInit() throws Exception {
		String userId = request.getString("userId");
		try {
			if (userId != null) {
				Map<String, String> map = userService.queryUserById(Convert
						.strToLong(userId, -100));
				if (map != null) {
					paramMap.put("userName", map.get("username"));// request().setAttribute("userName",
					// map.get("username"));//
					paramMap.put("userId", userId);
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 添加扣费
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addBackWithdraw() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		Long adminId = admin.getId();
		String userIdParam = paramMap.get("userId");
		long userId = Convert.strToLong(userIdParam, -1);
		String dealMoneyParam = paramMap.get("dealMoney");
		double dealMoney = Convert.strToDouble(dealMoneyParam, 0);
		String remark = paramMap.get("remark");

		String _code = Convert.strToStr(paramMap.get("code"), null);
		String pageId = paramMap.get("pageId"); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		if (code == null || !_code.equals(code)) {
			return null;
		}
		if (dealMoney <= 0) {
			this.addFieldError("paramMap['dealMoney']", "操作金额错误");
			return "input";
		}
		try {
			Map<String, String> userMap = userService.queryUserById(userId);
			String addIP = ServletUtils.getRemortIp();
			fundManagementService.addBackW(userId, adminId,
					IConstants.WITHDRAW, dealMoney, remark, new Date(),
					IConstants.FUNDMODE_WITHDRAW_HANDLE, addIP, userMap
							.get("username"), "手动扣费，备注：" + remark);
			this.setUserId(userId);
			operationLogService.addOperationLog("t_recharge_withdraw_info",
					admin.getUserName(), IConstants.INSERT, admin.getLastIP(),
					dealMoney, " 进行充值扣费信息添加处理", 2);
			userService.updateSign(userId);//更换校验码
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;
	}

	public String addBackRecharge() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		Long adminId = admin.getId();
		Long userId = paramMap.get("userId") == null ? -100 : Convert
				.strToLong(paramMap.get("userId"), -100);
		Double dealMoney = paramMap.get("dealMoney") == null ? 0 : Convert
				.strToDouble(paramMap.get("dealMoney"), 0);
		String remark = paramMap.get("remark") == null ? null : Convert
				.strToStr(paramMap.get("remark"), null);
		String type = paramMap.get("recharType");
		if (StringUtils.isBlank(type)) {
			this.addFieldError("paramMap['recharType']", "充值类型错误");
			return "input";
		}
		int recharType = Convert.strToInt(type, -1);
		if (dealMoney <= 0) {
			this.addFieldError("paramMap['dealMoney']", "操作金额错误");
			return "input";
		}
		try {

			Map<String, String> userMap = userService.queryUserById(userId);

			String addIP = ServletUtils.getRemortIp();

			long result1 = fundManagementService.addBackR(userId, adminId,
					recharType, dealMoney, remark, new Date(),
					IConstants.FUNDMODE_RECHARGE_HANDLE, addIP, userMap
							.get("username"), remark);
			if (result1 < 0) {
				userService.updateSign(userId);//更换校验码
				this.addFieldError("paramMap['dealMoney']", "操作失败");
				return "input";
			}
			operationLogService.addOperationLog("t_recharge_withdraw_info",
					admin.getUserName(), IConstants.INSERT, admin.getLastIP(),
					dealMoney, "手动充值,对象" + userMap.get("username"), 2);
			this.setUserId(userId);
			userService.updateSign(userId);//更换校验码
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	/*
	 * 批量充值模版
	 */
	public String downBatchChargeModel() throws IOException{
        HSSFWorkbook book = ExcelUtils.exportExcel("批量充值模版",null, new String[]{"用户名","真实姓名","充值金额","充值类型(5表示手工充值，7表示奖励充值)","备注","涨薪宝"}   , null);
        this.export(book,new Date().getTime()+".xls");
        return null;
    }
	
	
	
	/*
	 * 批量充值
	 */
	public String batchCharge(){
	    Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        Long adminId = admin.getId();
        double in_sumMoney = Convert.strToDouble(paramMap.get("sumMoney"),0.0);
	    if (files==null) {
            addActionError("请选择文件");
            return INPUT;
        }
        String filename = files.getFilesFileName().substring(files.getFilesFileName().lastIndexOf("."));
        if (!".xls".equalsIgnoreCase(filename)) {
            addActionError("文件格式不正确，请重新选择文件");
            return INPUT;
        }
        try {
            String [][] data = com.shove.util.ExcelUtils.getData(files.getFiles(), 1);
            List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
            double sumMoney = 0.0;
            
            for (int i = 0;i<data.length;i++) {
                double dealMoney = Convert.strToDouble(data[i][2], -1);//充值金额
                sumMoney= AmountUtil.mathRound(sumMoney+dealMoney);
                if (dealMoney==-1) {
                    addActionError("充值失败，充值金额异常。");
                    return INPUT;
                }else {
                	String str1 = data[i][2],str2 =data[i][5];
                	int indexof = str1.indexOf(".");
            		if (indexof!=-1) {//有小数
            			int len = str1.substring(indexof+1).length();
            			if (len>2) {
            				addActionError("充值失败，充值清单中包含小数点超过三位的金额");
                            return INPUT;
            			}
            		}
            		indexof = str2.indexOf(".");
            		if (indexof!=-1) {//有小数
            			int len = str2.substring(indexof+1).length();
            			if (len>2) {
            				addActionError("充值失败，充值清单中包含小数点超过三位的金额");
                            return INPUT;
            			}
            		}
				}
            }
            if (in_sumMoney!=sumMoney) {
            	addActionError("充值失败，总金额不一致。");
                return INPUT;
			}
            for (int i = 0;i<data.length;i++) {
                Map<String, Object> result = new HashMap<String, Object>();
                String username = data[i][0];//用户名
                double dealMoney = Convert.strToDouble(data[i][2], -1);//充值金额
                double zxbMoney = Convert.strToDouble(data[i][5], 0);//涨薪宝金额
                if (dealMoney<0) {
                    result.put("result", "充值失败，充值金额异常。");
                    result.put("username", username);
                    results.add(result);
                    continue;
                }
                
                if (zxbMoney<0) {
                	result.put("result", "充值失败，涨薪宝金额异常。");
                    result.put("username", username);
                    results.add(result);
                    continue;
				}
                
                if (dealMoney<zxbMoney) {
                	result.put("result", "充值失败，充值金额不能小于涨薪宝金额。");
                    result.put("username", username);
                    results.add(result);
                    continue;
				}
                
                Integer recharType = Convert.strToInt(data[i][3], -2);//充值类型
                if (recharType==-2) {
                    result.put("result", "充值失败，充值类型异常。");
                    result.put("username", username);
                    results.add(result);
                    continue;
                }
                
                Map<String, String> userMap = userService.queryUserByUsername(username);
                if (userMap==null || "".equals(userMap.get("username"))) {
                    result.put("result", "充值失败，充值用户不存在。");
                    result.put("username", username);
                    results.add(result);
                    continue;
                }else {
                    userId = Convert.strToLong(userMap.get("id"), -1);
                    String realName = userMap.get("realName");
                    String in_realName = data[i][1];
                    
                    
                    if (StringUtils.isNotBlank(in_realName)) {//填写了真实姓名
                        if (StringUtils.isBlank(realName) ) {
                            result.put("result", "充值失败，用户尚未实名认证");
                            result.put("username", username);
                            results.add(result);
                            continue;
                        }
                        if (!data[i][1].equals(realName)) {
                            result.put("result", "充值失败，真实姓名不正确");
                            result.put("username", username);
                            results.add(result);
                            continue;
                        }
                        
                    }else {
                    	String userType = userMap.get("userType");
                    	 if (!"2".equals(userType)) {//非企业用户
                    		 result.put("result", "充值失败，请输入真实姓名");
                             result.put("username", username);
                             results.add(result);
                             continue;
						 }
					}
                }
                
                String remark = data[i][4];//备注
                String addIP = ServletUtils.getRemortIp();

                long result1 = fundManagementService.addBackR(userId, adminId,
                        recharType, dealMoney, remark, new Date(),
                        IConstants.FUNDMODE_RECHARGE_HANDLE, addIP, userMap
                                .get("username"), remark);
                if (result1 < 0) {
                    result.put("username", username);
                    result.put("result", "充值失败！");
                }else {//充值成功
                	operationLogService.addOperationLog("t_recharge_withdraw_info",
                            admin.getUserName(), IConstants.INSERT, admin.getLastIP(),
                            dealMoney, "手动充值,对象" + userMap.get("username"), 2);
                    this.setUserId(userId);
                    userService.updateSign(userId);//更换校验码
                    result.put("username", username);
                    result.put("result", "充值成功！");
                    
                    if (zxbMoney>0) {
                    	//涨薪宝是否转入
                        result1 = payTreasureService.autoIntoPayTreasureOne(userId, zxbMoney, username, addIP,"1");
                        if (result1 < 0) {
                            result.put("zxb", "涨薪宝转入失败!");
                        }else {
                        	result.put("zxb", "涨薪宝转入成功!");
						}
					}
				}
                results.add(result);
            }

            HSSFWorkbook book = ExcelUtils.exportExcel("批量充值结果",results, new String[]{"用户名","充值结果","涨薪宝结果"}   , new String[]{"username","result","zxb"});
            this.export(book, new Date().getTime()+".xls");
            addActionMessage("充值完毕，请查看充值结果");
            response().getOutputStream().print("<script type='text/javascript'>window.location.href.reload();</script>");
            return null;
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            addActionError("出现异常，请联系管理员。");
            return INPUT;
        }
	}
	
	/*
	 * 批量扣费模版
	 */
	public String downBatchChargeBackModel() throws IOException{
        HSSFWorkbook book = ExcelUtils.exportExcel("批量扣费模版",pageBean.getPage(), new String[]{"用户名","真实姓名","扣费金额","备注"}   , null);
        this.export(book,new Date().getTime()+".xls");
        return null;
    }
	
	/*
	 * 批量扣费
	 */
	public String batchChargeBack(){
	    Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        Long adminId = admin.getId();
	    double in_sumMoney = Convert.strToDouble(paramMap.get("sumMoney"),0.0);
	    if (files==null) {
            addActionError("请选择文件");
            return INPUT;
        }
        String filename = files.getFilesFileName().substring(files.getFilesFileName().lastIndexOf("."));
        if (!".xls".equalsIgnoreCase(filename)) {
            addActionError("文件格式不正确，请重新选择文件");
            return INPUT;
        }
        try {
            String [][] data = com.shove.util.ExcelUtils.getData(files.getFiles(), 1);
            List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
            double sumMoney = 0.0;
            for (int i = 0;i<data.length;i++) {
                double dealMoney = Convert.strToDouble(data[i][2], -1);//充值金额
                sumMoney+=dealMoney;
                if (dealMoney==-1) {
                    addActionError("扣费失败，扣费金额异常。");
                    return INPUT;
                }else {
                	String str1 = data[i][2];
                	int indexof = str1.indexOf(".");
            		if (indexof!=-1) {//有小数
            			int len = str1.substring(indexof+1).length();
            			if (len>2) {
            				addActionError("充值失败，充值清单中包含小数点超过三位的金额");
                            return INPUT;
            			}
            		}
				}
            }
            if (in_sumMoney!=sumMoney) {
            	addActionError("扣费失败，总金额不一致。");
                return INPUT;
			}
            
            for (int i = 0;i<data.length;i++) {
                Map<String, Object> result = new HashMap<String, Object>();
                String username = data[i][0];//用户名
                double dealMoney = Convert.strToDouble(data[i][2], -1);//充值金额
                if (dealMoney<0) {
                    result.put("result", "扣费失败，扣费金额异常。");
                    result.put("username", username);
                    results.add(result);
                    continue;
                }
                
                Map<String, String> userMap = userService.queryUserByUsername(username);
                if (userMap==null || "".equals(userMap.get("username"))) {
                    result.put("result", "扣费失败，扣费用户不存在。");
                    result.put("username", username);
                    results.add(result);
                    continue;
                }else {
                    userId = Convert.strToLong(userMap.get("id"), -1);
                    String realName = userMap.get("realName");
                    String in_realName = data[i][1];
                    if (StringUtils.isNotBlank(in_realName)) {//填写了真实姓名
                        if (StringUtils.isBlank(realName) ) {
                            result.put("result", "扣费失败，用户尚未实名认证");
                            result.put("username", username);
                            results.add(result);
                            continue;
                        }
                        if (!data[i][1].equals(realName)) {
                            result.put("result", "扣费失败，真实姓名不正确");
                            result.put("username", username);
                            results.add(result);
                            continue;
                        }
                    }else {
                    	String userType = userMap.get("userType");
                    	if (!"2".equals(userType)) {//非企业用户
                    		result.put("result", "扣费失败，请输入真实姓名");
                    		result.put("username", username);
                    		results.add(result);
                    		continue;
						 }
                    }
                }
                
                String remark = data[i][4];//备注
                String addIP = ServletUtils.getRemortIp();

                long result1 = fundManagementService.addBackW(userId, adminId,
                		IConstants.WITHDRAW, dealMoney, remark, new Date(),
                        IConstants.FUNDMODE_WITHDRAW_HANDLE, addIP, userMap
                                .get("username"), remark);
                if (result1 < 0) {
                    result.put("username", username);
                    result.put("result", "扣费失败！");
                    continue;
                }
                operationLogService.addOperationLog("t_recharge_withdraw_info",
                        admin.getUserName(), IConstants.INSERT, admin.getLastIP(),
                        dealMoney, "手动扣费,对象" + userMap.get("username"), 2);
                this.setUserId(userId);
                userService.updateSign(userId);//更换校验码
                result.put("username", username);
                result.put("result", "扣费成功！");
                results.add(result);
            }

            HSSFWorkbook book = ExcelUtils.exportExcel("批量扣费结果",results, new String[]{"用户名","扣费结果"}   , new String[]{"username","result"});
            this.export(book, new Date().getTime()+".xls");
            addActionMessage("扣费完毕，请查看扣费结果");
            response().getOutputStream().print("<script type='text/javascript'>window.location.href.reload();</script>");
            return null;
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            addActionError("出现异常，请联系管理员。");
            return INPUT;
        }
	}
	
	/**
	 *功能：批量处理转账中的提现
	 * @return
	 * @throws Exception
	 */
	public String updateWithdraws() throws Exception {
		log.info("updateWithdraws....");
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String ipAddress = ServletUtils.getRemortIp();
		JSONObject obj = new JSONObject();
		Long adminId = admin.getId();
		Integer status = 2;
//		String wids = request.getString("wids");
		String wids = paramMap.get("wids");//${bean.id };${bean.sum};${bean.poundage };${bean.userId }
		String[] allIds = wids.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length <= 0) {
			return INPUT;
		}
		String statusType = "成功";
		String[] strs = null;
		double mm, sum;
		float poundage;
		Long wid = 0L, userId = -100L;

		for (int i = 0, n = allIds.length; i < n; i++) {
			strs = allIds[i].split(";");
			wid = Convert.strToLong(strs[0], -1);
			sum = Convert.strToDouble(strs[1], 0);
			poundage = Convert.strToFloat(strs[2], 0);
			userId = Convert.strToLong(strs[3], -1);
			if (poundage > 0) {// 有手续费的时候，操作金额为减掉手续费的值
				mm = sum - poundage;
			}
			
			Map<String, String> retMap = fundManagementService.updateWithdrawTransfer(wid, status, adminId, ipAddress);
			session().removeAttribute("randomCode");
			long uid = fundManagementService.queryUserIdByWId(wid);
			userService.updateSign(uid);//更换校验码
			operationLogService.addOperationLog("t_withdraw", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"审核转账中的提现,审核状态" + statusType, 2);
			
			
		}
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
	}

	public List<Map<String, Object>> getRechargeTypes() {
		if (rechargeTypes == null) {
			rechargeTypes = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
//			mp = new HashMap<String, Object>();
//			mp.put("typeId", 1);
//			mp.put("typeValue", "支付宝支付");
//			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "环迅支付");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "国付宝");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 8);
			mp.put("typeValue", "融E付");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("typeValue", "线下充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 5);
			mp.put("typeValue", "手工充值");
			rechargeTypes.add(mp);

//			mp = new HashMap<String, Object>();
//			mp.put("typeId", 6);
//			mp.put("typeValue", "虚拟充值");
//			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 7);
			mp.put("typeValue", "奖励充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 8);
			mp.put("typeValue", "融E付充值");
			rechargeTypes.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 10);
			mp.put("typeValue", "涨薪宝转出");
			rechargeTypes.add(mp);
		}
		return rechargeTypes;
	}

	public void setRechargeTypes(List<Map<String, Object>> rechargeTypes) {
		this.rechargeTypes = rechargeTypes;
	}

	public List<Map<String, Object>> getResults() {
		if (results == null) {
			results = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("resultId", -100);
			mp.put("resultValue", "全部");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 1);
			mp.put("resultValue", "成功");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 0);
			mp.put("resultValue", "失败");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 2);
			mp.put("resultValue", "审核中");
			results.add(mp);
		}
		return results;
	}

	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}

	public List<Map<String, Object>> getStatus() {
		if (status == null) {
			status = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("statusId", 0);
			mp.put("statusValue", "全部");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 1);
			mp.put("statusValue", "审核中");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 2);
			mp.put("statusValue", "成功");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 3);
			mp.put("statusValue", "取消");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 4);
			mp.put("statusValue", "转账中");
			status.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("statusId", 5);
			mp.put("statusValue", "失败");
			status.add(mp);
		}
		return status;
	}

	public void setStatus(List<Map<String, Object>> status) {
		this.status = status;
	}

	public List<Map<String, Object>> getOperateType() {
		if (operateType == null) {
			operateType = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("tvalue", "手动充值");
			operateType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("tvalue", "手动扣费");
			operateType.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 7);
			mp.put("tvalue", "奖励充值");
			operateType.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 10);
			mp.put("tvalue", "涨薪宝转出");
			operateType.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 11);
			mp.put("tvalue", "涨薪宝转入");
			operateType.add(mp);
		}
		return operateType;
	}

	public void setOperateType(List<Map<String, Object>> operateType) {
		this.operateType = operateType;
	}

	public String queryWithdrawInfo() throws Exception {
		Long wid = request.getString("wid") == null ? -100 : request.getLong("wid", -1);

		try {
			getWithdrawInfo(wid, true);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String queryWithdrawTransInfo() throws Exception {
		Long wid = request.getString("wid") == null ? -100 : request.getLong("wid", -1);

		try {
			getWithdrawInfo(wid, false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	private void getWithdrawInfo(Long wid, boolean check) throws Exception {
		Map<String, String> b_map = fundManagementService.queryOneWithdraw(wid);
		request().setAttribute("wid", wid);

		String checkId = "", checkTime = "", remark = "";
		if (b_map != null) {
			request().setAttribute("realName", b_map.get("realName"));
			request().setAttribute("branchBankName",
					b_map.get("bankName") + " " + b_map.get("branchBankName"));
			request().setAttribute("cardNo", b_map.get("acount"));
			request().setAttribute("sum", b_map.get("sum"));
			request().setAttribute("realMoney", b_map.get("realMoney"));
			request().setAttribute("poundage", b_map.get("poundage"));
			request().setAttribute("applyTime", b_map.get("applyTime"));
			request().setAttribute("ipAddress", b_map.get("ipAddress"));
			request().setAttribute("userId", b_map.get("userId"));
			String status = b_map.get("status");
			if (status.equals(IConstants.WITHDRAW_CHECK + "")) {
				request().setAttribute("status", IConstants.WITHDRAW_CHECK_STR);
			} else if (status.equals(IConstants.WITHDRAW_SUCCESS + "")) {
				request().setAttribute("status",
						IConstants.WITHDRAW_SUCCESS_STR);
			} else if (status.equals(IConstants.WITHDRAW_TRANS + "")) {
				request().setAttribute("status", IConstants.WITHDRAW_TRANS_STR);
			} else if (status.equals(IConstants.WITHDRAW_FAIL + "")) {
				request().setAttribute("status", IConstants.WITHDRAW_FAIL_STR);
			} else if(status.equals(IConstants.WITHDRAW_CANCEL + "")){
				request().setAttribute("status", IConstants.WITHDRAW_CANCEL_STR);
			}
			userId = Convert.strToLong(b_map.get("userId"), -100);
			checkId = b_map.get("checkId");
			checkTime = b_map.get("checkTime");
			remark = b_map.get("remark");
		}
		String defaultValue = "0.00";
		Map<String, String> rw_map = fundManagementService.queryUserRWInfo(
				userId, IConstants.RECHARGE_SUCCESS,
				IConstants.WITHDRAW_SUCCESS);
		if (rw_map != null) {
			if (rw_map.get("r_total").equals(""))
				request().setAttribute("r_total", defaultValue);// 充值成功总额
			else
				request().setAttribute("r_total", rw_map.get("r_total"));// 充值成功总额

			if (rw_map.get("w_total").equals(""))
				request().setAttribute("w_total", defaultValue);// 提现成功总额
			else
				request().setAttribute("w_total", rw_map.get("w_total"));// 提现成功总额

			if (rw_map.get("real_Amount").equals(""))
				request().setAttribute("real_Amount", defaultValue);// 投标成功总额
			else
				request()
						.setAttribute("real_Amount", rw_map.get("real_Amount"));// 投标成功总额

			request().setAttribute("withdraw_max", IConstants.WITHDRAW_MAX);
		}

		Map<String, String> u_map = userService.queryUserById(userId);
		if (u_map != null) {
			request().setAttribute("username", u_map.get("username"));
			request().setAttribute("usableSum", u_map.get("usableSum"));
		}

		
		
		if (!check) {// 查看跟审核的时候，还要查询审核信息
			Map<String, String> ms = adminService.queryAdminById(Convert
					.strToLong(checkId, -100));
			String username = null;
			if(ms==null){
				username="";
			}else
				username = ms.get("userName");
			
			String rk = "审核人:" + username + ",   审核时间:" + checkTime
					+ ",   审核备注:" + remark;
			request().setAttribute("rk", rk);
		}
	}

	/**
	 * 转账中的提现
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateWithdrawTransfer() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String ipAddress = ServletUtils.getRemortIp();
		JSONObject obj = new JSONObject();
		Long adminId = admin.getId();
		String statusType = "";
		Integer status = paramMap.get("status") == null ? -1 : Convert
				.strToInt(paramMap.get("status"), -1);
		Long wid = paramMap.get("wid") == null ? -1 : Convert.strToLong(
				paramMap.get("wid"), -1);
		Map<String, String> retMap = fundManagementService
				.updateWithdrawTransfer(wid, status, adminId, ipAddress);
		long retVal = -1;
		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
		session().removeAttribute("randomCode");
		long uid = fundManagementService.queryUserIdByWId(wid);
		userService.updateSign(uid);//更换校验码
		if (retVal <= 0) {
			obj.put("msg", retMap.get("ret_desc"));
			JSONUtils.printObject(obj);
			return null;
		} else {
			if (status == 2) {
				statusType = "成功";
			} else {
				statusType = "失败";
			}
			operationLogService.addOperationLog("t_withdraw", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"审核转账中的提现,审核状态" + statusType, 2);
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 转账中的提现
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateWithdrawCheck() throws Exception {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		Integer status = paramMap.get("status") == null ? -1 : Convert
				.strToInt(paramMap.get("status"), -1);
		Double money = paramMap.get("money") == null ? 0 : Convert.strToDouble(
				paramMap.get("money"), 0);
		float poundage = paramMap.get("poundage") == null ? 0 : Convert
				.strToFloat(paramMap.get("poundage"), 0);
		String remark = paramMap.get("remark") == null ? "" : paramMap
				.get("remark");
		Long wid = paramMap.get("wid") == null ? -1 : Convert.strToLong(
				paramMap.get("wid"), -1);
		Long adminId = admin.getId();
		Long userId = paramMap.get("userId") == null ? -1 : Convert.strToLong(
				paramMap.get("userId"), -1);
		String ipAddress = ServletUtils.getRemortIp();

		if (money <= 0) {
			obj.put("msg", "到账金额格式错误");
			JSONUtils.printObject(obj);
			return null;
		}

		if (poundage < 0) {
			obj.put("msg", "手续费格式错误");
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> retMap = fundManagementService.updateWithdraw(wid,
				money, poundage, status, remark, adminId, userId, ipAddress);
		long retVal = -1;
		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
		session().removeAttribute("randomCode");
		
		userService.updateSign(userId);//更换校验码
		
		if (retVal <= 0) {
			obj.put("msg", retMap.get("ret_desc"));
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加操作日志
			operationLogService.addOperationLog("t_withdraw", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), money,
					"提现审核", 2);
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 根据用户通知，发送站内信、邮件、手机短信提醒
	 * 
	 * @throws Exception
	 */
	private void sendMessage(String title, String content, Long userId,
			String mode, boolean flag) throws Exception {
		try {
			// String title = "资金变动提醒";
			// 查找通知类型的通知状态
			List<Map<String, Object>> lists = selectedService.queryNoticeMode(
					userId, mode);
			if (lists != null && lists.size() > 0) {

				// [通知方式(1 邮件 2 站内信 3 短信]
				if (lists.get(0).get("flag").toString().equals(
						String.valueOf(IConstants.NOTICE_ON))) {
					sendMessageService
							.emailSendTemplate(title, content, userId);
				}
				if (lists.get(1).get("flag").toString().equals(
						String.valueOf(IConstants.NOTICE_ON))) {
					sendMessageService.mailSend(title, content, userId);
				}
				if (lists.get(2).get("flag").toString().equals(
						String.valueOf(IConstants.NOTICE_ON))) {
					if (flag) {// 有余额可扣的清空下，才进行短信的发送
						String dateStr = DateUtil.dateToString(new Date());
						Map<String, String> u_map = userService
								.queryUserById(userId);
						String userName = "";
						if (u_map != null && u_map.size() > 0) {
							userName = u_map.get("username");
						}
						String newContent = "尊敬的" + userName + ":[" + dateStr
								+ "]" + content;
						@SuppressWarnings("unused")
						Long result = sendMessageService.noteSend(newContent,
								userId);
					}
				}
			}
			userService.updateSign(userId);//更换校验码
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}
	
	/*
	 * 融E付查询init
	 */
	public String queryREFInitMethod(){
	    return SUCCESS;
	}
	
	/*
	 * 融E付查询
	 */
	public String queryREFMethod() throws DocumentException, IOException, SQLException{
	    String orderId = paramMap.get("orderId");
        Map<String, String> map = this.fundManagementService.getREFstatus(orderId);
        JSONUtils.printObject(map);
	    return SUCCESS;
	}
	
	/**
	 *功能：批量审核，待审核的提现
	 * @return
	 * @throws IOException 
	 */
	public String updatesWithdrawAudit() throws Exception{
		log.info("updatesWithdrawAudit...");
		
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		JSONObject obj = new JSONObject();
		Long adminId = admin.getId();
		String ipAddress = ServletUtils.getRemortIp();
		String ids = paramMap.get("ids");
		String[] idArr = ids.split(",");
		Integer status = 4;//审核通过
		for (int i = 0; i < idArr.length; i++){
			Long wid = Long.parseLong(idArr[i]);
			Map<String, String> b_map = fundManagementService.queryOneWithdraw(wid);
			String realMoney = b_map.get("realMoney");
			String poundage = b_map.get("poundage");
			long userId = Long.parseLong(b_map.get("userId"));
			Map<String, String> retMap = fundManagementService.updateWithdraw(wid, Double.parseDouble(realMoney), 
					Double.parseDouble(poundage), status, "批量审核提现成功", adminId, userId, ipAddress);
			long retVal = -1;
			retVal = Convert.strToLong(retMap.get("ret") + "", -1);
			session().removeAttribute("randomCode");
			userService.updateSign(userId);//更换校验码
			
			if (retVal <= 0) {
				log.info("some error occur..");
//				obj.put("msg", retMap.get("ret_desc"));
//				JSONUtils.printObject(obj);
//				return null;
			} else {
				// 添加操作日志
				operationLogService.addOperationLog("t_withdraw", admin
						.getUserName(), IConstants.UPDATE, admin.getLastIP(), Double.parseDouble(realMoney),
						"批量提现审核", 2);
			}
			
		}
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		return null;
		
//		Integer status = paramMap.get("status") == null ? -1 : Convert
//				.strToInt(paramMap.get("status"), -1);
//		Double money = paramMap.get("money") == null ? 0 : Convert.strToDouble(
//				paramMap.get("money"), 0);
//		float poundage = paramMap.get("poundage") == null ? 0 : Convert
//				.strToFloat(paramMap.get("poundage"), 0);
//		String remark = paramMap.get("remark") == null ? "" : paramMap
//				.get("remark");
//		Long wid = paramMap.get("wid") == null ? -1 : Convert.strToLong(
//				paramMap.get("wid"), -1);
//		
//		Long userId = paramMap.get("userId") == null ? -1 : Convert.strToLong(
//				paramMap.get("userId"), -1);
//		String ipAddress = ServletUtils.getRemortIp();
//
//		if (money <= 0) {
//			obj.put("msg", "到账金额格式错误");
//			JSONUtils.printObject(obj);
//			return null;
//		}
//
//		if (poundage < 0) {
//			obj.put("msg", "手续费格式错误");
//			JSONUtils.printObject(obj);
//			return null;
//		}
//		Map<String, String> retMap = fundManagementService.updateWithdraw(wid,
//				money, poundage, status, remark, adminId, userId, ipAddress);
//		long retVal = -1;
//		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
//		session().removeAttribute("randomCode");
//		
//		userService.updateSign(userId);//更换校验码
//		
//		if (retVal <= 0) {
//			obj.put("msg", retMap.get("ret_desc"));
//			JSONUtils.printObject(obj);
//			return null;
//		} else {
//			// 添加操作日志
//			operationLogService.addOperationLog("t_withdraw", admin
//					.getUserName(), IConstants.UPDATE, admin.getLastIP(), money,
//					"提现审核", 2);
//			obj.put("msg", "1");
//			JSONUtils.printObject(obj);
//			return null;
//		}
	
		//return SUCCESS;
	}
	
	
	/*
	 * 融E付修改状态
	 */
	public String updateREFMethod(){
	    return SUCCESS;
	}
	
	public String queryBackCashDetailsInit() {
		return SUCCESS;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public SendMessageService getSendMessageService() {
		return sendMessageService;
	}

	public void setSendMessageService(SendMessageService sendMessageService) {
		this.sendMessageService = sendMessageService;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

}
