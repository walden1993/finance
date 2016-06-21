package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;

/**
 * 用户资金管理
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class UserFundRecordAction extends BaseFrontAction{

	public static Log log = LogFactory.getLog(LinksAction.class);
	private UserService userService;
	private List<Map<String,Object>> status;
	private List<Map<String,Object>> rechargeStatus;
	private List<Map<String,Object>> rechargeType;
	private AdminService adminService;
	private FundManagementService fundManagementService;
	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}
	/**
	 * 用户资金管理页面加载
	 * @return
	 */
	public String userFundInit(){
		return SUCCESS;
	}
	
	/**
	 * 查找用户资金列表信息
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundList() throws Exception{
		try {
			//加载银行卡信息
			String userName = Convert.strToStr(paramMap.get("userName"), null);
			String userType = Convert.strToStr(paramMap.get("userType"), null);
			String realName = Convert.strToStr(paramMap.get("realName"), null);
			fundManagementService.queryUserCashList(pageBean, userName,realName,userType);
			Map<String, String> map = fundManagementService.queryUserCashList(pageBean, userName,realName,userType);
			request().setAttribute("map", map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	public String userFundWithdrawInit(){
		String userId = request.getString("userId");
		paramMap.put("userId", userId);
		return SUCCESS;
	}
	
	/**
	 * 查询提现记录
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFundWithdrawList() throws Exception{
		
		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String endTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("endTime"), null));
		Double sum = paramMap.get("sum")==null?-100: Convert.strToDouble(paramMap.get("sum"), -100);
		Integer status = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		
		Long userId = Convert.strToLong(paramMap.get("userId"), -100);
		try{
			fundManagementService.queryUserFundWithdrawInfo(pageBean,userName,startTime,endTime,
					sum,status,userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserFundRechargeList() throws Exception{
		
		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String endTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("endTime"), null));

		Integer status = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		
		Integer rt = paramMap.get("rechargeType")==null?-100:
			Convert.strToInt(paramMap.get("rechargeType"), -100);
		
		Long userId = Convert.strToLong(paramMap.get("userId"), -100);

		try{
			fundManagementService.queryUserFundRechargeInfo(pageBean,startTime,endTime,
					status,userId,rt);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String exportUserFundRecharge() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Long userId = request.getLong("userId",-1L);
		try {
			Admin admin = (Admin)session().getAttribute(IConstants.SESSION_ADMIN);
			String applyTime =request.getString("applyTime");
			String endTime=request.getString("endTime");
			if(StringUtils.isBlank(endTime) &&StringUtils.isNotBlank(applyTime))
			{
				endTime = FrontMyPaymentAction.changeEndTime(applyTime);

			}
			Integer status = request.getInt("statss", -100);
			Integer rt = request.getInt("reType", -100);
			// 充值记录
			fundManagementService.queryUserFundRechargeInfo(pageBean,applyTime,endTime,
					status,userId,rt);
			if(pageBean.getPage()==null)
			{
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return  null;
			}
			if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
			{
			getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
			return  null;
			}
			HSSFWorkbook wb = ExcelUtils.exportExcel("充值记录", pageBean
					.getPage(), new String[] { "用户名", "充值类型", "充值金额", "手续费",
					"到账金额", "充值时间", "状态" }, new String[] { "username",
					"type", "rechargeMoney", "poundage", "realMoney","rechargeTime","result"
					});
			operationLogService.addOperationLog("v_t_user_rechargeall_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户充值记录", 2);
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
 * 导出查询提现记录
 * 
 * @return
 */
	@SuppressWarnings("unchecked")
	public String exportUserFundWithdraw() {
	pageBean.pageNum = 1;
	pageBean.setPageSize(100000);
	Long userId = request.getLong("userId",-1L);
	try {
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		//String applyTime=request().getParameter("applyTime")==null ? "" :request().getParameter("applyTime");
		String applyTime=request.getString("applyTime");
		//String  endTime=request().getParameter("endTime")==null ?  "" :request().getParameter("applyTime");
		String endTime=request.getString("endTime");
		if(StringUtils.isNotBlank(applyTime)&&StringUtils.isBlank(endTime))
		{
		 endTime = FrontMyPaymentAction.changeEndTime(applyTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime = FrontMyPaymentAction.changeEndTime(endTime);
		}
		//String userName=request().getParameter("userName")==null ? "" : request().getParameter("userName");
		String userName=request.getString("userName");
		if(StringUtils.isNotBlank(userName))
		{
			userName=URLDecoder.decode(userName, "UTF-8");
		}
		double sum=request.getDouble("sum", -1);
		int statss=request.getInt("statss",-1);
		// 提现记录
		fundManagementService.queryUserFundWithdrawInfo(pageBean,userName,applyTime,endTime,
				sum,statss,userId);
		if(pageBean.getPage()==null)
		{
			getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
			return  null;
		}
		if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
		{
		getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
		return  null;
		}
		HSSFWorkbook wb = ExcelUtils.exportExcel("提现记录", pageBean
				.getPage(), new String[] { "用户名", "真实姓名", "提现账号", "提现银行",
				"支行", "提现总额", "到账金额(￥)","手续费","提现时间" }, new String[] { "username",
				"realName", "acount", "bankName", "branchBankName","sum","realAccount","poundage","applyTime"
				});
		this.export(wb, new Date().getTime() + ".xls");
		
		operationLogService.addOperationLog("v_t_user_fundwithdraw_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出用户提现记录", 2);
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

	
	public String queryAllUserFundRecordInit(){
		return SUCCESS;
	}

	public String queryAllUserFundRecordList() throws Exception{
		try {
			String userName = paramMap.get("userName");
			String userType = paramMap.get("userType");
			fundManagementService.queryAllUserFundRecordList(pageBean, userName,userType);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
			request().setAttribute("totalNum", pageBean.getTotalNum());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	
	/**
	 * 导出资金明细
	 * @return
	 */
	public String exportAllUserFundList(){
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Integer status = request.getInt("statss", -1);
		try {
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			String userName = request.getString("userName") == null ? ""
					: request.getString("userName");
			String userType = request.getString("userType") == null ? ""
					: request.getString("userType");
			userName = URLDecoder.decode(userName, "UTF-8");// 中文乱码转换
			//资金明细
			fundManagementService.queryAllUserFundRecordList(pageBean, userName, userType);
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
			fundManagementService.changeNumToStr5(pageBean);
			//fundManagementService.changeTraderName(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("资金明细表", pageBean
					.getPage(), new String[] { "ID","用户名","用户类型", "类型", "收入","支出",
					"可用金额", "冻结金额", "待收金额",  "总金额", "记录时间" },
					new String[] { "id", "username","userType", "fundMode", "income","spending",
							"usableSum", "freezeSum", "dueinSum","totalSum",
							"recordTime",});
			operationLogService.addOperationLog("v_t_user_fundrecord_lists", admin.getUserName(), IConstants.EXCEL, admin.getLastIP(), 0, "导出资金明细列表", 2);
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
	 * 资金管理  资金记录
	 * @return
	 */
	public String userFundRecordInit(){
		String userName = request.getString("userName");
		String userId = request.getString("userId");
		paramMap.put("userName", userName);
		paramMap.put("userId", userId);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserFundRecordList() throws Exception{
		Long userId = paramMap.get("userId")==null?-100:
			Convert.strToLong(paramMap.get("userId"), -100);
		
		String userName = paramMap.get("userName");
		if(userName != null)
			request().setAttribute("userName", userName);
		try{
			fundManagementService.queryUserFundRecordList(pageBean,userId);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		return SUCCESS;
	}
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getRechargeStatus() {
		if(rechargeStatus == null){//#{0:'全部',2:'成功',5:'失败',1:'充值中'}"
			rechargeStatus = new ArrayList<Map<String,Object>>();
			Map<String,Object> mp = null;
			mp = new HashMap<String,Object>();
			mp.put("statusId",-100 );
			mp.put("statusValue", "全部");
			rechargeStatus.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",1 );
			mp.put("statusValue", "成功");
			rechargeStatus.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",0 );
			mp.put("statusValue", "失败");
			rechargeStatus.add(mp);
			
		}
		return rechargeStatus;
	}

	public void setRechargeStatus(List<Map<String, Object>> rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public List<Map<String, Object>> getRechargeType() {
		if (rechargeType == null) {
			rechargeType = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", -100);
			mp.put("typeValue", "全部");
			rechargeType.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("typeValue", "支付宝支付");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "环迅支付");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "国付宝");
			rechargeType.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("typeValue", "线下充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 5);
			mp.put("typeValue", "手工充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 6);
			mp.put("typeValue", "虚拟充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 7);
			mp.put("typeValue", "奖励充值");
			rechargeType.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 8);
			mp.put("typeValue", "融E付充值");
			rechargeType.add(mp);
		}
		return rechargeType;
	}

	public void setRechargeType(List<Map<String, Object>> rechargeType) {
		this.rechargeType = rechargeType;
	}

	public List<Map<String, Object>> getStatus() {
		if(status == null){//#{0:'全部',2:'成功',5:'失败',1:'充值中'}"
			status = new ArrayList<Map<String,Object>>();
			Map<String,Object> mp = null;
			mp = new HashMap<String,Object>();
			mp.put("statusId",0 );
			mp.put("statusValue", "全部");
			status.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",2 );
			mp.put("statusValue", "成功");
			status.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",5 );
			mp.put("statusValue", "失败");
			status.add(mp);
			
			mp = new HashMap<String,Object>();
			mp.put("statusId",4 );
			mp.put("statusValue", "充值中");
			status.add(mp);
		}
		return status;
	}

	public void setStatus(List<Map<String, Object>> status) {
		this.status = status;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	
}
