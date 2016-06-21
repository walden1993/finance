package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.ServletUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Response;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.UserService;

public class RechargeWithdrawAppAction extends BaseAppAction {
	private static final long serialVersionUID = -676064526203960258L;
	public static Log log = LogFactory.getLog(RechargeWithdrawAppAction.class);

	private RechargeService rechargeService;
	private HomeInfoSettingService homeInfoSettingService;
	private UserService userService;
	
	
	public String withdraw_init() throws IOException{
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
			// 绑定的银行卡信息单独查询
	        List<Map<String, Object>> banks = homeInfoSettingService
	                .querySuccessBankInfoListApp(userId);
	        Map<String, Object> json = new HashMap<String, Object>();
	        json.put("bank_list", banks);
	        response.setData(json);
	        JSONUtils.printObject(json);
		} catch (Exception e) {
			e.printStackTrace();
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}
	
	/*
	 * 提现手续费计算
	 */
	public String get_withdraw_poundage() throws IOException{
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
			
			
			double money = Convert.strToDouble(appInfoMap.get("withdraw_amount"), 0);
		    paramMap = rechargeService.withdrawCount(userId, money);
		    appInfoMap.clear();
		    appInfoMap.put("withdraw_poundage", paramMap.get("ret_desc"));
		    response.success(appInfoMap);
		    JSONUtils.printObject(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}
	
	public String queryWithdraw() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Map<String, String> pmap = userService.queryPersonById(userId);
			if (pmap == null) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户的个人信息未填写,无法申请提现");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			int authStep = Convert.strToInt(pmap.get("auditStatus"), -1);// 需要填写个人资料之后才能申请提现
			 if (authStep < 2) {
			 jsonMap.put("error", "2");
			 jsonMap.put("msg", "用户的个人信息未填写,无法申请提现");
			 JSONUtils.printObject(jsonMap);
			 return null;
			 }

			// 需要加载信息 真实姓名 手机号码 帐户余额 可用余额 冻结总额 提现银行
			String realName = pmap.get("realName");

			String[] vals = moneyVal(userId);

			String bindingPhone = this.getBidingPhone(userId);
			if (StringUtils.isBlank(bindingPhone)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "手机号未设定");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 绑定的银行卡信息单独查询
			List<Map<String, Object>> bankList = homeInfoSettingService
					.querySuccessBankInfoList(userId);
			if (bankList == null || bankList.size() == 0) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "未添加银行卡信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			boolean re = userService.checkSign(userId);
			if(!re){
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			jsonMap.put("handleSum", vals[0]);
			jsonMap.put("usableSum", vals[1]);
			jsonMap.put("freezeSum", vals[2]);
			jsonMap.put("max_withdraw", IConstants.WITHDRAW_MAX);// 最大充值金额，超过之后要收取手续费
			jsonMap.put("bankList", bankList);
			jsonMap.put("realName", realName);
			jsonMap.put("bindingPhone", bindingPhone);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("msg", "5");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	private String getBidingPhone(long userId) throws Exception {
		List<Map<String, Object>> lists = rechargeService.withdrawLoad(userId);

		String bindingPhone = null;
		int status = -1;
		
		if (lists != null && lists.size() > 0) {
			if (lists.get(0).get("bindingPhone") != null) {
				bindingPhone = lists.get(0).get("bindingPhone").toString();
			}
			if (lists.get(0).get("status") != null) {
				status = Convert.strToInt(
						lists.get(0).get("status").toString(), -1);
			}

			// 如果设置的绑定号码为空，或者绑定的手机号码还未审核通过 则都使用用户注册时的手机号码
			if (bindingPhone == null || status != IConstants.PHONE_BINDING_ON) {
				bindingPhone = lists.get(0).get("cellPhone").toString();
			}
		}
		return bindingPhone;
	}

	/**
	 * 加载提现记录信息
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String queryWithdrawList() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
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
			
			String starting_page = appInfoMap.get("starting_page");//起始页
			String page_number = appInfoMap.get("page_number");//每页条数
			
			if (NumberUtils.isNumber(starting_page)) {
				pageBean.setPageNum(starting_page);
			}
			if (NumberUtils.isNumber(page_number)) {
				pageBean.setPageSize(Integer.valueOf(page_number));
			}
			
			rechargeService.queryWithdrawListApp(pageBean, userId);
			jsonMap.put("withdraw_record_list", pageBean.getPage());
			response.success(jsonMap);
			JSONUtils.printObject(response);

		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);

		}
		return null;
	}
	
	/*
	 * 提现
	 */
	public String addWithdraw() throws IOException {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
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
			if (userId == -1L) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			
			Map<String, String> m = new HashMap<String, String>();
			m = userService.queryUserById(userId);
			if (null != m) {
				
				String pwd=m.get("password");
				String del=m.get("dealpwd");
				if(pwd.equals(del)){
					response.failure("请您修改交易密码");
					JSONUtils.printObject(response);
					return null;
				}
			}
			String dealpwd = appInfoMap.get("business_pwd");
			
			if (StringUtils.isBlank(dealpwd)) {
				response.failure("提现密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			String code = appInfoMap.get("identify_code");

			if (StringUtils.isBlank(code)) {
				response.failure("验证码不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			//if (IConstants.ISDEMO.equals("2")) {
				if (StringUtils.isBlank(code)) {
					response.failure("验证码不能为空");
					JSONUtils.printObject(response);
					return null;
				}
				String md5_code = appInfoMap.get("md5_code");
				if (StringUtils.isBlank(md5_code)) {
					response.failure("验证码不能为空");
					JSONUtils.printObject(response);
					return null;
				}
				String md5 = Encrypt.MD5(mobile_tel + code);
				// String phone = session().getAttribute("phone") +"";
				if (!md5.equals(md5_code)) {
					response.failure("验证码不正确");
					JSONUtils.printObject(response);
					return null;
				}

				boolean expire = userService.querySendTime(mobile_tel, code);
				if (expire) {
					response.failure("验证码已过期");
					JSONUtils.printObject(response);
					return null;
				}
			//}


			double money = Convert.strToDouble(appInfoMap.get("withdraw_amount"), 0.0);
			
			if (money<=100) {
			    response.failure("提现金额必须大于100元");
				JSONUtils.printObject(response);
	            return null;
	        }

			long bankId = Convert.strToLong(appInfoMap.get("card_id"), -1);
			if (bankId < 0) {
				response.failure("提现银行卡不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			if ("1".equals(IConstants.ENABLED_PASS)) {
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
			} else {
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
						+ IConstants.PASS_KEY);
			}
			Map<String, String> map = userService.queryUserById(userId);
			String userDealPwd = map.get("dealpwd");
			if (userDealPwd == null) {// 如果用户没有设置交易密码，则默认为登录密码
				userDealPwd = map.get("password");
			}
			if (!dealpwd.equals(userDealPwd)) {// 交易密码错误
				response.failure("交易密码错误");
				JSONUtils.printObject(response);
				return null;
			}
			boolean re = userService.checkSign(userId);
			if(!re){
				response.failure("您的账号出现异常，请速与管理员联系");
				JSONUtils.printObject(response);
				return null;
			}
			// ------modify 2013-05-02 判断该用户所属的组是否能申请提现
			Long toWithdraw = homeInfoSettingService.queryUserCashStatus(
					userId, IConstants.CASH_STATUS_OFF);
			if (toWithdraw <= 0) {// 该用户所属组的提现权利被限制
				response.failure("您的帐号已被限制提现，请联系客服人员");
				JSONUtils.printObject(response);
				return null;
			}
			
			long result = -1L;
			// ip地址
			String ipAddress = ServletUtils.getRemortIp();

			Map<String, String> map1 = rechargeService.withdrawApply(userId, money,
					userDealPwd, bankId, "1", ipAddress);
			result = Convert.strToLong(map1.get("ret"), -1l);
			
			userService.updateSign(userId);//更换校验码
			
			//清空验证码
			userService.overSendLog(mobile_tel, "", code);
			
			if (result < 0) {// 提现失败
				response.failure("添加提现失败");
				JSONUtils.printObject(response);
			} else {
				response.success();
				JSONUtils.printObject(response);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/**
	 * 取消提现
	 * @return
	 * @throws Exception
	 */
	public String deleteWithdraw() throws Exception {
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
			if (userId == -1L) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			long withDrawId = Convert.strToLong(appInfoMap.get("withdraw_id"),
					-1);
			if (withDrawId < 0) {
				response.failure("提现记录不存在");
				JSONUtils.printObject(response);
				return null;
			}
			// 修改提现记录状态
			Map<String, String> resultMap = rechargeService.deleteWithdraw(
					userId, withDrawId);
			long result = -1;
			result = Convert.strToLong(resultMap.get("ret"), -1);
			
			userService.updateSign(userId);//更换校验码
			if (result < 0) {
				response.failure("删除失败");
				JSONUtils.printObject(response);
				return null;
			} else {
				response.success();
				JSONUtils.printObject(response);
				return null;
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}

		return null;
	}

	public String queryFundsRecodeList() throws SQLException, DataException,
	IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
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
			String startTime = "";
			/*int days = Convert.strToInt(appInfoMap.get("days"), 0);
			if (days != 0) {
				Date endDate = DateUtil.dateAddDay(new Date(), days);
				startTime = DateUtil.dateToString(endDate);
			}*/
			String starting_page = appInfoMap.get("starting_page");//起始页
			String page_number = appInfoMap.get("page_number ");//每页条数
			String type = appInfoMap.get("type ");//每页条数
			if (NumberUtils.isNumber(starting_page)) {
				pageBean.setPageNum(starting_page);
			}
			if (NumberUtils.isNumber(page_number)) {
				pageBean.setPageSize(Integer.valueOf(page_number));
			}
			rechargeService.queryFundrecordsListApp(pageBean, userId, startTime,
					"");
		
			String[] vals = fundVal(userId);
			//DecimalFormat df = new DecimalFormat("#0.00");
			//jsonMap.put("sum", df.format(Double.parseDouble(vals[0])));
			//jsonMap.put("handleSum", df.format(Double.parseDouble(vals[0])));
			jsonMap.put("available_balance", vals[1]);
			jsonMap.put("blocked_balances", vals[2]);
			//jsonMap.put("totalSum", df.format(Double.parseDouble(vals[3])));
			jsonMap.put("fund_record_list", pageBean.getPage());
			response.success(jsonMap);
			JSONUtils.printObject(response);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
}


	private String[] fundVal(long userId) throws Exception {
		String[] val = new String[4];
		for (int i = 0; i < val.length; i++) {
			val[i] = "0";
		}
		try {
			Map<String, String> map = rechargeService.queryFund(userId);
			if (map != null) {
				double usableSum = Convert.strToDouble(map.get("usableSum"), 0);
				double freezeSum = Convert.strToDouble(map.get("freezeSum"), 0);
				double dueinSum = Convert.strToDouble(map.get("forPI"), 0);
				double handleSum = usableSum + freezeSum + dueinSum;
				double totalSum = usableSum + freezeSum;
				val[0] = String.format("%.2f", handleSum);
				val[1] = String.format("%.2f", usableSum);
				val[2] = String.format("%.2f", freezeSum);
				val[3] = String.format("%.2f", totalSum);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return val;
	}

	private String[] moneyVal(long userId) throws Exception {
		String[] val = new String[3];
		for (int i = 0; i < val.length; i++) {
			val[i] = "0";
		}
		try {
			Map<String, String> map = userService.queryUserById(userId);
			if (map != null) {
				double usableSum = Convert.strToDouble(map.get("usableSum"), 0);
				double freezeSum = Convert.strToDouble(map.get("freezeSum"), 0);
				double handleSum = usableSum + freezeSum;
				val[0] = String.format("%.2f", handleSum);// df.format(handleSum);
				val[1] = String.format("%.2f", usableSum);// df.format(usableSum);
				val[2] = String.format("%.2f", freezeSum);// df.format(freezeSum);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return val;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
