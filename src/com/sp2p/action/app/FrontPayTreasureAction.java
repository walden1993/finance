package com.sp2p.action.app;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.JSONUtils;
import com.shove.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Response;
import com.sp2p.service.PayTreasureService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;


/**
 * 涨薪宝
 * @author hjh
 *
 */
public class FrontPayTreasureAction extends BaseAppAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontPayTreasureAction.class);
	private PayTreasureService payTreasureService;
	private UserService userService;
	
	//详情
	public String paytreasuredetail() throws Exception{
		Map<String, Object> map = payTreasureService.paytreasuredetail(getUserId());
		
		//预计收益到账时间
		String date = DateUtil.YYYY_MM_DD.format(new Date())+" 15:00:00";
		Date proDate = DateUtil.strToDate(date);
		Date now = new Date();
		String showDate = "";
		if (now.after(proDate)) {
			now = DateUtil.dateAddDay(now, 1);
		}
		showDate = DateUtil.YYYYMMDDPoint.format(now);
		
		//可用余额
		if (getUserId()!=-1) {
			Map<String, String> usableSum= userService.queryPresent(getUserId());
			map.put("usableSum", usableSum);
			Map<String, String> yesterdayProfit =  payTreasureService.yesterdayProfit(getUserId());
			map.put("zxbprofit", yesterdayProfit);
		}
		
		map.put("showdate", showDate);
		request().setAttribute("map", map);
		
		if (checkMobile()) {
			return "mobile";
		}
		
		
		return SUCCESS;
	}
	
	//转入
	public String intoPayTreasure() throws Exception{
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId==-1L) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}

			
			Map<String, Object> map = payTreasureService.paytreasuredetail(userId);
			Map<String, Object> paytreasure = (Map<String, Object>) map.get("paytreasure");
			if ("1".equals(paytreasure.get("isopen"))) {
				//JSONUtils.printStr("11");
				response.failure("尚未募集");
				JSONUtils.printObject(response);
				return null;
			}
			
			String amountstr = appInfoMap.get("zxb_in_amount");
			String dealpassword = appInfoMap.get("business_pwd");
			if (StringUtils.isBlank(amountstr)) {
				//JSONUtils.printStr("1");
				response.failure("转入金额不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (!NumberUtils.isNumber(amountstr)) {
				//JSONUtils.printStr("2");
				response.failure("转入金额必须是数字");
				JSONUtils.printObject(response);
				return null;
			}else {
				
				int indexof = amountstr.indexOf(".");
				if (indexof!=-1) {//有小数
					int len = amountstr.substring(indexof+1).length();
					if (len>2) {
						//JSONUtils.printStr("13");
						response.failure("转入金额小数位不能超过2位");
						JSONUtils.printObject(response);
						return null;
					}
				}
				
				double amountdouble = Convert.strToDouble(amountstr, 0);
				
				if (amountdouble<=0) {
					//JSONUtils.printStr("12");
					response.failure("转入金额不能小于0");
					JSONUtils.printObject(response);
					return null;
				}
				
				if (map.get("payinvest")!=null) {
					Map<String, Object> payinvest = (Map<String, Object>) map.get("payinvest");
					double nowamount = MapUtils.getDoubleValue(payinvest, "investamount");
					if (amountdouble<1000-nowamount) {
						//JSONUtils.printStr("12");
						response.failure("首次转入金额不能小于1000");
						JSONUtils.printObject(response);
						return null;
					}
				}else {
					if (amountdouble<1000) {
						//JSONUtils.printStr("12");
						response.failure("首次转入金额不能小于1000");
						JSONUtils.printObject(response);
						return null;
					}
				}
				
			}
			
			if (StringUtils.isBlank(dealpassword)) {
				//JSONUtils.printStr("3");
				response.failure("交易密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			
			Map<String, String> user= userService.queryUserById(userId);
			if (user==null) {
				//JSONUtils.printStr("4");
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}else {
				String dealpwd = user.get("dealpwd");
				String usableSumstr=user.get("usableSum");
				
				if ("1".equals(IConstants.ENABLED_PASS)) {
					dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim());
				} else {
					dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim()
							+ IConstants.PASS_KEY);
				}
				if (!dealpassword.equals(dealpwd)) {
					response.failure("交易密码不正确");
					JSONUtils.printObject(response);
					//JSONUtils.printStr("5");
					return null;
				}
				double amount = Convert.strToDouble(amountstr, 0.0);
				double usableSum = Convert.strToDouble(usableSumstr, 0.0);
				if (amount>usableSum) {
					response.failure("转入金额超过三好资本可用余额");
					JSONUtils.printObject(response);
					//JSONUtils.printStr("6");
					return null;
				}
				paramMap.put("username", user.get("username"));
			}
			paramMap.put("amount", amountstr);
			paramMap.put("ip", ServletUtils.getIpAddress(request()));
			paramMap.put("userid", userId+"");
			long result = payTreasureService.intoPayTreasure(paramMap);
			if (result>0) {
				Map<String, String> mypaytreasure = payTreasureService.queryMyPayInvest(userId);
				Map<String, String> usableSum= userService.queryPresent(userId);
				mypaytreasure.putAll(usableSum);
				appInfoMap.clear();
				appInfoMap.put("zxb_accum",MapUtils.getString(mypaytreasure, "investamount", "0.00"));
				appInfoMap.put("available_balance",MapUtils.getString(mypaytreasure, "usableSum", "0.00"));
				response.success(appInfoMap);
				JSONUtils.printObject(response);
				//JSONUtils.printStr("8");
			}else {
				response.failure("转入失败");
				JSONUtils.printObject(response);
				//JSONUtils.printStr("7");
			}
			return null;
		
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}
	
	//转出详情初始化
	public String outPayTreasureInit() throws Exception{
		//涨薪宝总金额
		Map<String, String> myPayInvest = payTreasureService.queryMyPayInvest(getUserId());
		//三好资本可用余额
		Map<String, String> usableSum= userService.queryPresent(getUserId());
		request().setAttribute("usableSum", usableSum);
		request().setAttribute("myPayInvest", myPayInvest);
		if (checkMobile()) {
			return "mobile";
		}
		return SUCCESS;
	}
	
	//转出
	public String outPayTreasure() throws Exception{
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId==-1L) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			String amountstr = appInfoMap.get("zxb_out_amount");
			String dealpassword = appInfoMap.get("business_pwd");
			if (StringUtils.isBlank(amountstr)) {
				response.failure("请输入转出金额");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (!NumberUtils.isNumber(amountstr)) {
				response.failure("金额只能是数字");
				JSONUtils.printObject(response);
				return null;
			}
			
			int indexof = amountstr.indexOf(".");
			if (indexof!=-1) {//有小数
				int len = amountstr.substring(indexof+1).length();
				if (len>2) {
					response.failure("金额小数位最多2位");
					JSONUtils.printObject(response);
					return null;
				}
			}
			
			if (StringUtils.isBlank(dealpassword)) {
				response.failure("请输入交易密码");
				JSONUtils.printObject(response);
				return null;
			}
			
			Map<String, String> user= userService.queryUserById(userId);
			
			boolean allOut = false;
			
			if (user==null) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}else {
				String dealpwd = user.get("dealpwd");
				
				Map<String, String> myPayInvest = payTreasureService.queryMyPayInvest(userId);
				if (myPayInvest==null || myPayInvest.size()<0) {
					response.failure("无可转出金额");
					JSONUtils.printObject(response);
					return null;
				}
				String usableSumstr=myPayInvest.get("investamount");
				
				if ("1".equals(IConstants.ENABLED_PASS)) {
					dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim());
				} else {
					dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim()
							+ IConstants.PASS_KEY);
				}
				if (!dealpassword.equals(dealpwd)) {
					response.failure("交易密码不正确");
					JSONUtils.printObject(response);
					return null;
				}
				double amount = Convert.strToDouble(amountstr, 0.0);
				if (amount<=0) {
					response.failure("转出金额不能小于0");
					JSONUtils.printObject(response);
					return null;
				}
				double usableSum = Convert.strToDouble(usableSumstr, 0.0);
				if (amount>usableSum) {
					response.failure("转出金额超出可转出范围，请重新输入");
					JSONUtils.printObject(response);
					return null;
				}
				
				allOut = amount==usableSum; //如果是属于全部转出
				
				
				paramMap.put("username", user.get("username"));
			}
			paramMap.put("amount", amountstr);
			paramMap.put("ip", ServletUtils.getIpAddress(request()));
			paramMap.put("userid", userId+"");
			long result = payTreasureService.outPayTreasure(paramMap,allOut);
			if (result>0) {
				Map<String, String> mypaytreasure = payTreasureService.queryMyPayInvest(userId);
				Map<String, String> usableSum= userService.queryPresent(userId);
				mypaytreasure.putAll(usableSum);
				appInfoMap.clear();
				appInfoMap.put("zxb_accum",MapUtils.getString(mypaytreasure, "investamount", "0.00"));
				appInfoMap.put("available_balance",MapUtils.getString(mypaytreasure, "usableSum", "0.00"));
				response.success(appInfoMap);
				JSONUtils.printObject(response);
				return null;
			}else {
				response.failure("转出失败");
				JSONUtils.printObject(response);
				return null;
			}
		
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}
	
	//手机版收益记录初始化
	public String profitRecordInit() throws SQLException{
		Response response = new Response();
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String uid = appInfoMap.get("uid");
			if (StringUtils.isBlank(uid)) {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = Convert.strToLong(uid, -1);
			if (userId>-1) {
				Map<String, String> mypaytreasure = payTreasureService.queryMyPayInvestApp(getUserId());
				//data.put("mypaytreasure", mypaytreasure);
				response.success(mypaytreasure);
			}else {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			return null;
		}
		return null;
	}
	
	//我的涨薪宝
	public String  mypaytreasure() throws Exception{

		Response response = new Response();
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId==-1L) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			if (userId>0) {
				Map<String, String> mypaytreasure = payTreasureService.queryMyPayInvestApp(userId);
				Map<String, String> userAmount = userService.queryUserAmount(userId);
				
				data.put("zxb_yesterday_income",MapUtils.getString(mypaytreasure, "zxb_yesterday_income","0.00"));
				data.put("zxb_accum_income",MapUtils.getString(mypaytreasure, "zxb_accum_income", "0.00"));
				data.put("zxb_accum",MapUtils.getString(mypaytreasure, "zxb_accum", "0.00"));
				data.put("available_balance",MapUtils.getString(userAmount, "usableSum", "0.00"));
				
				response.success(data);
				JSONUtils.printObject(response);
			}else {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			return null;
		}
		return null;
	
	}
	
	//转入转出记录
	public String payInvestRecord() throws SQLException{

		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId<0) {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
			String starting_page = appInfoMap.get("starting_page");//起始页
			String page_number = appInfoMap.get("page_number");//每页条数
			String type = appInfoMap.get("type ");//每页条数
			if (NumberUtils.isNumber(starting_page)) {
				pageBean.setPageNum(starting_page);
			}
			if (NumberUtils.isNumber(page_number)) {
				pageBean.setPageSize(Integer.valueOf(page_number));
			}
			paramMap.put("type", type);
			payTreasureService.payInvestRecordApp(pageBean, userId,paramMap);
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("in_out_list", pageBean.getPage());
			response.success(json);
			JSONUtils.printObject(response);
			return null;
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			return null;
		}
	
	}
	
	//收益记录
	public String profitRecord() throws SQLException{
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId<0) {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
			String starting_page = appInfoMap.get("starting_page");//起始页
			String page_number = appInfoMap.get("page_number");//每页条数
			
			if (NumberUtils.isNumber(starting_page)) {
				pageBean.setPageNum(starting_page);
			}
			if (NumberUtils.isNumber(page_number)) {
				pageBean.setPageSize(Integer.valueOf(page_number));
			}
			
			payTreasureService.profitRecordApp(pageBean, userId,paramMap);
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("income_list", pageBean.getPage());
			response.success(json);
			JSONUtils.printObject(response);
			return null;
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			return null;
		}
	}
	
	//自动转入
	public String autoIntoPayTreasure() throws SQLException{
		payTreasureService.autoIntoPayTreasure();
		return null;
	}
	
	//涨薪宝设置界面
	public String payTreasureSetting() throws SQLException{
		setParamMap(payTreasureService.querySetting(getUserId()));
		return SUCCESS;
	}
	
	//保存涨薪宝设置
	public String payTreasureSettingSave() throws SQLException{
		paramMap.put("userId", getUserId()+"");
		
		String open = paramMap.get("open");
		
		
		if ("0".equals(open)) {
			String amountstr = paramMap.get("minBalance");//保留金额
			
			if (StringUtils.isBlank(amountstr) || !NumberUtils.isNumber(amountstr)) {
				JSONUtils.printStr("1");
				return null;
			}
			//保留金额大于0
			int amount = Convert.strToInt(amountstr, 0);
			if (amount<=0) {
				JSONUtils.printStr("1");
				return null;
			}
			int indexof = amountstr.indexOf(".");
			if (indexof!=-1) {//有小数
				int len = amountstr.substring(indexof+1).length();
				if (len>2) {
					JSONUtils.printStr("2");
					return null;
				}
			}
		}
		
		
		long result = payTreasureService.saveSetting(paramMap, getUserId());
		if (result>0) {
			JSONUtils.printStr("3");
		}else {
			JSONUtils.printStr("4");
		}
		return null;
	}
	
	//涨薪宝协议
	public String payTreasureProtocol() throws Exception{
		Map<String, String> person = userService.queryPersonById(getUserId());
		Map<String, String> user =userService.queryUserById(getUserId());
		request().setAttribute("name", "涨薪宝-金秋1号");
		request().setAttribute("userMap", user);
		request().setAttribute("personMap", person);
		return SUCCESS;
	}
	
	//手机转入
	public String intoPayTreasureMobile() throws Exception{
		Map<String, String> usableSum= userService.queryPresent(getUserId());
		request().setAttribute("usableSum", usableSum);
		return SUCCESS;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}

}
