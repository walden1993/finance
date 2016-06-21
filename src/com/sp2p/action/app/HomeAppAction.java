package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Response;
import com.sp2p.entity.User;
import com.sp2p.service.HSFundService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.PublicFunction;

public class HomeAppAction extends BaseAppAction {
	private static final long serialVersionUID = -8705141732645392945L;
	public static Log log = LogFactory.getLog(HomeAppAction.class);

	private MyHomeService myHomeService;
	private HomeInfoSettingService homeInfoSettingService;
	private FundManagementService fundManagementService;
	private UserService userService;
	private HSFundService hsFundService;
	
	
	
	public void setHsFundService(HSFundService hsFundService) {
		this.hsFundService = hsFundService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(
			FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	public String getAppVersion() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String verNum = Convert.strToStr(infoMap.get("verNum"), "");
			String curNum = Convert.strToStr(
					PublicFunction.GetOption("app_version"), "");
			if (verNum.equals(curNum)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "当前版本已经是最新版本");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String content = PublicFunction.GetOption("app_upcontent");
			String url = IConstants.APP_URL;
			jsonMap.put("url", url);
			jsonMap.put("updMessage", content);
			jsonMap.put("curNum", curNum + "");
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public String queryHome() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
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
			Map<String, String> homeMap = myHomeService.queryHomeInfoApp(userId);
			Map<String, String> accmountStatisMap = myHomeService
					.queryAccountStatisInfoApp(userId);
			jsonMap.putAll(homeMap);
			jsonMap.putAll(accmountStatisMap);
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

	public String queryBank() throws IOException {
		// Map<String, Object> jsonMap = new HashMap<String, Object>();
		Response response = new Response();
		try {
			// Map<String, String> authMap = this.getAppAuthMap();
			// long userId = Convert.strToLong(authMap.get("uid"), -1);

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

			List<Map<String, Object>> lists = homeInfoSettingService
					.queryBankInfoListApp(userId);
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("bank_card_list", lists);
			response.success(json);
			JSONUtils.printObject(response);
		} catch (Exception e) {
			// log.error(e);
			// e.printStackTrace();
			// jsonMap.put("error", "2");
			// jsonMap.put("msg", "未知异常");
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}
	
	/**
	 * 卡bin查询
	 * @return
	 * @throws IOException
	 */
	public String queryBankBin() throws IOException {
		// Map<String, Object> jsonMap = new HashMap<String, Object>();
		Response response = new Response();
		try {
			// Map<String, String> authMap = this.getAppAuthMap();
			// long userId = Convert.strToLong(authMap.get("uid"), -1);

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
			String card_bin = appInfoMap.get("card_no");
			if (StringUtils.isBlank(card_bin)) {
				response.failure("请请输入卡bin信息");
				JSONUtils.printObject(response);
				return null;
			}
			Map<String, String> infoBin = homeInfoSettingService
					.queryBankBinInfoList(card_bin);
		    if (infoBin==null) {
				infoBin = new HashMap<String, String>();
				infoBin.put("bank_name",null);
				infoBin.put("card_bank_en",null);
				infoBin.put("card_type",null);
				response.failure("对不起，暂不支持该银行卡").setData(infoBin);
			}else {
				response.success(infoBin);
			}
			JSONUtils.printObject(response);
			
		} catch (Exception e) {
			// log.error(e);
			// e.printStackTrace();
			// jsonMap.put("error", "2");
			// jsonMap.put("msg", "未知异常");
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	public String addBank() throws IOException {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Response response = new Response();
		try {
			// Map<String, String> authMap = this.getAppAuthMap();
			// long userId = Convert.strToLong(authMap.get("uid"), -1);
			// Map<String, String> appInfoMap = this.getAppInfoMap();

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
			// 获取用户的信息
			User user = userService.queryUserDetailById(userId);
			String bankName = Convert.strToStr(appInfoMap.get("card_bank_name"), null);
			String subBankName = Convert.strToStr(appInfoMap.get("branch_bank_name"),
					null);
			String bankCard = Convert.strToStr(appInfoMap.get("card_no"), null);
			String dealpwd = Convert.strToStr(appInfoMap.get("business_pwd"), null);
			//0-借记卡
			//1-信用卡
			String cardMode = Convert.strToStr(appInfoMap.get("card_type"), "0");
			String card_bank_en =  Convert.strToStr(appInfoMap.get("card_bank_en"), null);
			String vc_branchbank = appInfoMap.get("bank_branch");//银行编号
			String card_mobile= appInfoMap.get("card_mobile");//预留手机号码
			String sign_id = appInfoMap.get("sign_id");//短信签约序号
			String local_sign_id = appInfoMap.get("local_sign_id");//服务器唯一序列号
			String valid_code = appInfoMap.get("valid_code");//验证码
			
			if (StringUtils.isBlank(bankName)
					|| StringUtils.isBlank(subBankName)
					|| StringUtils.isBlank(bankCard)
					|| StringUtils.isBlank(dealpwd)
					|| StringUtils.isBlank(card_bank_en)
					|| StringUtils.isBlank(vc_branchbank)
					|| StringUtils.isBlank(local_sign_id)
					|| StringUtils.isBlank(sign_id)) {
				response.failure("必输参数不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			if (StringUtils.isBlank(card_mobile)) {
				response.failure("请输入银行预留手机号");
				JSONUtils.printObject(response);
				return null;
			}
			if (StringUtils.isBlank(valid_code)) {
				response.failure("请输入验证码");
				JSONUtils.printObject(response);
				return null;
			}
			if (StringUtils.isBlank(bankCard) || !bankCard.startsWith("6")) {// 必须6开头
				response.failure("卡号必须是6开头");
				JSONUtils.printObject(response);
				return null;
			}

			if ("1".equals(IConstants.ENABLED_PASS)) {
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
			} else {
				dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
						+ IConstants.PASS_KEY);
			}
			
			String pwd = Convert.strToStr(
					userService.queryUserById(user.getId()).get("dealpwd"), "");
			if (!dealpwd.equals(pwd)) {
				response.failure("交易密码不正确");
				JSONUtils.printObject(response);
				return null;
			}
			Long id = user.getId();// 获得用户编号
			Map<String, String> map = homeInfoSettingService
					.queryCardStatus(id);
			int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);
			if (bindingCardNum >= 9) {// 已经绑定九张银行卡，不能再绑定了
				response.failure("您绑定的银行卡已超出限制(9张)");
				JSONUtils.printObject(response);
				return null;
			}
			// 查询某个账号是否已经被该用户绑定过了
			Map<String, String> bandingMap = homeInfoSettingService
					.queryBankCardisBanding(id, bankCard);
			int bindingCardisBanding = Convert.strToInt(
					bandingMap.get("count(*)"), 0);
			if (bindingCardisBanding > 0) {
				response.failure("此银行卡已被使用");
				JSONUtils.printObject(response);
				return null;
			}
			
			String realName = "";
			String idNo = "";
			String idType = "";
			//获取实名信息
			Map<String, String> idNoMap = userService.queryPersonById(userId);
			if (idNoMap != null) {
				System.out.println(idNoMap.get("authCardName"));
				if (!idNoMap.get("authCardName").equals("0")) {
					response.failure("您尚未实名，请实名后再操作");
					JSONUtils.printObject(response);
					return null;
				}else {
					realName = MapUtils.getString(idNoMap, "realName","");
					idType = MapUtils.getString(idNoMap, "idType","");
					idNo = MapUtils.getString(idNoMap, "idNo","");
				}
			}
			
			
			Map<String, String> data = new HashMap<String, String>();
			data.put("[name]", realName);
			data.put("[idNo]", idNo);
			data.put("[cardNo]",bankCard);
			data.put("[bankNo]", vc_branchbank);
			data.put("[signId]", sign_id);
			data.put("[localSignId]", local_sign_id);
			data.put("[smsCode]", valid_code);
			data.put("[mobile]", card_mobile);
			data.put("[idType]", idType);
			data.put("method", "hr.fund.sign.confirm");
			//调用远程接口
			String result = hsFundService.hr_fund(data, queryToken(userService,userId));
			result.replace("\"description\"", "\"message\"");
			JSONObject jsonObject = JSONObject.fromObject(result);
			if ("0".equals(jsonObject.get("code"))) {//成功
				// 新添加的提现银行卡信息状态为2，表示申请中
				homeInfoSettingService.addBankCardInfoApp(userId,"",bankName, subBankName, bankCard,
						IConstants.BANK_SUCCESS, card_bank_en,cardMode,-1,-1,vc_branchbank,card_mobile);
				operationLogService.addOperationLog("t_bankcard",
						user.getUserName(), IConstants.INSERT, user.getLastIP(), 0,
						"添加提现银行信息", 1);
				response.success();
				JSONUtils.printObject(response);
			}else {
				JSONUtils.printObject(result);
			}
			return null;
			
		} catch (Exception e) {
			log.error(e);
			response.failure("系统繁忙，请稍后再试...");
			JSONUtils.printObject(response);
			return null;
		}
	}
	
	
	//删除该条提现银行卡信息
		public String deleteMyBankInfo() throws Exception {
			log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
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
				
				Map<String, String> map = userService.queryUserById(userId);
				long id = Convert.strToLong(appInfoMap.get("card_id"), -1L);
				
				if (homeInfoSettingService.isFundDefault(id, userId)) {
					response.failure("该卡已关联基金账户不能删除");
					JSONUtils.printObject(response);
					return null;
				}
				
			    //2014-12-15 14:15:54修复。必须删除自己的银行卡
				long result = homeInfoSettingService.deleteMyBankInfo(id,userId);
				// 添加系统操作日志
				if(result>0){
				operationLogService.addOperationLog("t_bankcard",map.get("username"), IConstants.DELETE,map.get("lastIP"), 0,
						"删除已绑定的银行卡信息", 1);
				}
				response.success();
				JSONUtils.printObject(response);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				response.failure("服务器异常");
				JSONUtils.printObject(response);
			}
			return null;
		}

	public String updateBank() throws IOException {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			Map<String, String> appInfoMap = this.getAppInfoMap();

			// String code = appInfoMap.get("code");
			// if(StringUtils.isBlank(code)){
			// jsonMap.put("error", "1");
			// jsonMap.put("msg", "验证码不能为空");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			// String randomCode = appInfoMap.get("randomCode") + "";
			// randomCode = Encrypt.decryptSES(randomCode,
			// AlipayConfig.ses_key);
			// String phone = session().getAttribute("phone") +"";
			// if (!randomCode.equals(code)) {
			// jsonMap.put("error", "2");
			// jsonMap.put("msg", "验证码不正确");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			// String recivePhone = appInfoMap.get("recivePhone") + "";
			// recivePhone = Encrypt.decryptSES(recivePhone,
			// AlipayConfig.ses_key);
			// String cardUserName = appInfoMap.get("cardUserName");
			String bankName = appInfoMap.get("mBankName");
			if (StringUtils.isBlank(bankName)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "银行名称不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String subBankName = appInfoMap.get("mSubBankName");
			if (StringUtils.isBlank(subBankName)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "银行开户行不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String bankCard = appInfoMap.get("mBankCard");
			if (StringUtils.isBlank(bankCard)) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "银行卡号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String dealpwd = appInfoMap.get("dealpwd");
			dealpwd = com.shove.security.Encrypt.decrypt3DES(dealpwd,
					IConstants.PASS_KEY);
			if (StringUtils.isBlank(dealpwd)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "请输入交易密码");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd
					+ IConstants.PASS_KEY);
			if (!dealpwd.equals(userService.queryUserById(userId)
					.get("dealpwd"))) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "交易密码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long bankId = Convert.strToLong(appInfoMap.get("bankId"), -1);

			// Map<String, String> map = homeInfoSettingService
			// .queryCardStatus(userId);
			// int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);
			// 银行卡验证
			/*
			 * Pattern p = Pattern.compile("[0-9]{19}"); Matcher m =
			 * p.matcher(bankCard); if(!m.matches()){ JSONUtils.printStr("1");
			 * return null; }
			 */

			// 新添加的提现银行卡信息状态为2，表示申请中
			long result = -1;
			result = fundManagementService.updateChangeBank(bankId, bankName,
					subBankName, bankCard, IConstants.BANK_CHECK, new Date(),
					true);

			if (result > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "更变申请已提交");
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "8");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

}
