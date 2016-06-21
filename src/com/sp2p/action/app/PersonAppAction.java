package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.nciic.www.SimpleCheckByJson;
import cn.com.nciic.www.SimpleCheckByJsonResponse;
import cn.com.nciic.www.service.IdentifierServiceStub;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.security.Encrypt;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Response;
import com.sp2p.entity.User;
import com.sp2p.service.BeVipService;
import com.sp2p.service.HSFundService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.UserAdminService;

public class PersonAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(PersonAppAction.class);
	private static final long serialVersionUID = 7226324035784433720L;

	private BeVipService beVipService;
	private UserService userService;
	private SysparService sysparService;
	private UserAdminService userAdminService;
	private HomeInfoSettingService homeInfoSettingService;
	private HSFundService hsFundService;
	private OperationLogService operationLogService;
	
	

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public void setHsFundService(HSFundService hsFundService) {
		this.hsFundService = hsFundService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setSysparService(SysparService sysparService) {
		this.sysparService = sysparService;
	}

	public void setUserAdminService(UserAdminService userAdminService) {
		this.userAdminService = userAdminService;
	}

	// 实名认证
	public String identifier() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		//JSONObject json = new JSONObject();
		Response response = new Response();
		try {
			Map<String, String> infoMap = getAppInfoMap();


			String realName = infoMap.get("name");// 真实姓名
			
			String mobile_tel = infoMap.get("mobile_tel");
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
			
			if (StringUtils.isBlank(realName)) {
				//json.put("msg", "请输入您的真实名字");
				response.failure("请输入您的真实名字");
				JSONUtils.printObject(response);
				return null;
			}
			if (2 > realName.length() || 10 < realName.length()) {
				//json.put("msg", "真实姓名的长度为不小于2和大于30");
				response.failure("真实姓名的长度为不小于2和大于10");
				JSONUtils.printObject(response);
				return null;
			}
			Pattern p_realName = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");
			Matcher matcher_realName = p_realName.matcher(realName);
			if (!matcher_realName.matches()) {
				//json.put("msg", "真实姓名输入有误");
				response.failure("真实姓名输入有误");
				JSONUtils.printObject(response);
				return null;
			}
			String idNo = infoMap.get("id_no");// 身份证号码
			if (StringUtils.isBlank(idNo)) {
				//json.put("msg", "请输入您的身份证号码");
				response.failure("请输入您的身份证号码");
				JSONUtils.printObject(response);
				return null;
			}
			String id_type = infoMap.get("id_type");
			
			if (StringUtils.isBlank(id_type)) {
				response.failure("请选择您的证件类型");
				JSONUtils.printObject(response);
				return null;
			}
			
			
			if ("0".equals(id_type)) {//身份证校验
				// 身份证正则表达式(15位)
				Pattern p_idNo1 = Pattern
						.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
				// 身份证正则表达式(18位)
				Pattern p_idNo2 = Pattern
						.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}(x|X))$");
				Matcher matcher_idNo1 = p_idNo1.matcher(idNo);
				Matcher matcher_idNo2 = p_idNo2.matcher(idNo);
				long len = idNo.length();
				if (!(matcher_idNo1.matches() || matcher_idNo2.matches())) {
					//json.put("msg", "身份证号码不合法");
					response.failure("身份证号码不合法");
					JSONUtils.printObject(response);
					return null;
				}
				
				
			}
			String card_no = infoMap.get("card_no");//银行卡号
			
			String valid_code = infoMap.get("valid_code");//验证码
			
			String card_mobile= infoMap.get("card_mobile");//预留手机号码
			
			String vc_branchbank = infoMap.get("vc_branchbank");//银行编号
			
			String sign_id = infoMap.get("sign_id");//短信签约序号
			
			String local_sign_id = infoMap.get("local_sign_id");//服务器唯一序列号
			
			if (StringUtils.isBlank(sign_id) ||StringUtils.isBlank(local_sign_id) ) {
				response.failure("参数异常");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (StringUtils.isBlank(valid_code)) {
				response.failure("请输入验证码");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (StringUtils.isBlank(card_no)) {
				response.failure("请输入银行卡号");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (StringUtils.isBlank(valid_code)) {
				response.failure("请输入验证码");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (StringUtils.isBlank(card_mobile)) {
				response.failure("请输入银行预留手机号");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (StringUtils.isBlank(vc_branchbank)) {
				response.failure("请选择开户行所在支行");
				JSONUtils.printObject(response);
				return null;
			}
			
			String card_bank_en =  Convert.strToStr(infoMap.get("card_bank_en"), null);
			String bank_name =  Convert.strToStr(infoMap.get("card_bank_name"), null);
			String bank_branch_name =  Convert.strToStr(infoMap.get("branch_bank_name"), null);
			
			Map<String, String> data = new HashMap<String, String>();
			data.put("[name]", realName);
			data.put("[idNo]", idNo);
			data.put("[cardNo]",card_no);
			data.put("[bankNo]", vc_branchbank);
			data.put("[signId]", sign_id);
			data.put("[localSignId]", local_sign_id);
			data.put("[smsCode]", valid_code);
			data.put("[mobile]", card_mobile);
			data.put("[idType]", id_type);
			data.put("method", "hr.fund.sign.confirm");
			//调用远程接口
			String result = hsFundService.hr_fund(data, queryToken(userService,userId));
			result.replace("\"description\"", "\"message\"");
			JSONObject jsonObject = JSONObject.fromObject(result);
			if ("0".equals(jsonObject.get("code"))) {//成功
				User user = userService.queryUserDetailById(userId);
				//0-借记卡
				//1-信用卡
				String cardMode = Convert.strToStr(infoMap.get("card_type"), "0");
				//插入银行卡信息
				// 新添加的提现银行卡信息状态为1，表示审核通过
				homeInfoSettingService.addBankCardInfoApp(userId,"",bank_name, bank_branch_name, card_no,
						IConstants.BANK_SUCCESS, card_bank_en,cardMode,-1,-1,vc_branchbank,card_mobile);
				operationLogService.addOperationLog("t_bankcard",
						user.getUserName(), IConstants.INSERT, user.getLastIP(), 0,
						"添加提现银行信息", 1);
				
				//插入实名信息
				Map<String, String> idNoMap = beVipService.queryHasIDCard(idNo,
						realName);
				if (idNoMap != null) {
					System.out.println(idNoMap.get("authCardName"));
					if (idNoMap.get("authCardName").equals("0")) {
						response.failure("该身份证已认证过");
						JSONUtils.printObject(response);
						return null;
					}
				}
				Map<String, String> pMap = new HashMap<String, String>();
				pMap = beVipService.queryPersonUser(userId);
				if (pMap != null && !pMap.isEmpty()) {
					int authCardNameCount = Convert.strToInt(
							pMap.get("authCardName"), -1);
					if (authCardNameCount >= 4) {
						response.failure("您已三次认证");
						JSONUtils.printObject(response);
						return null;
					}
					long personId = userAdminService.updatePerson(realName,
							userId, idNo,id_type);
					if (personId > 0) {
						response.success();
						JSONUtils.printObject(response);
						return null;
					}
				}else {
					response.failure("系统繁忙，请稍后再试");
					JSONUtils.printObject(response);
					return null;
				}
				
			}else {
				JSONUtils.printObject(result);
			}
			return null;
		} catch (Exception e) {
			response.failure("系统繁忙，请稍后再试");
			JSONUtils.printObject(response);
			return null;
		}
	}
	
		// 实名认证发送短信
		public String authentication_sms() throws Exception {
			log.info("className:" + this.getClass().getName() + ";methodName:"
					+ Thread.currentThread().getStackTrace()[1].getMethodName());
			//JSONObject json = new JSONObject();
			Response response = new Response();
			try {
				Map<String, String> infoMap = getAppInfoMap();

				String realName = infoMap.get("name");// 真实姓名
				String idNo = infoMap.get("id_no");// 身份证号码
				String id_type = infoMap.get("id_type");
				
				String mobile_tel = infoMap.get("mobile_tel");
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
				
				if (StringUtils.isBlank(realName)||StringUtils.isBlank(idNo)||StringUtils.isBlank(id_type)) {
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
							id_type = MapUtils.getString(idNoMap, "idType","");
							idNo = MapUtils.getString(idNoMap, "idNo","");
						}
					}
				}
				
				
				
				if (StringUtils.isBlank(realName)) {
					//json.put("msg", "请输入您的真实名字");
					response.failure("请输入您的真实名字");
					JSONUtils.printObject(response);
					return null;
				}
				if (2 > realName.length() || 10 < realName.length()) {
					//json.put("msg", "真实姓名的长度为不小于2和大于30");
					response.failure("真实姓名的长度为不小于2和大于10");
					JSONUtils.printObject(response);
					return null;
				}
				Pattern p_realName = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");
				Matcher matcher_realName = p_realName.matcher(realName);
				if (!matcher_realName.matches()) {
					//json.put("msg", "真实姓名输入有误");
					response.failure("真实姓名输入有误");
					JSONUtils.printObject(response);
					return null;
				}
				
				if (StringUtils.isBlank(idNo)) {
					//json.put("msg", "请输入您的身份证号码");
					response.failure("请输入您的身份证号码");
					JSONUtils.printObject(response);
					return null;
				}
				
				
				if (StringUtils.isBlank(id_type)) {
					response.failure("请选择您的证件类型");
					JSONUtils.printObject(response);
					return null;
				}
				
				
				if ("0".equals(id_type)) {//身份证校验
					// 身份证正则表达式(15位)
					Pattern p_idNo1 = Pattern
							.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
					// 身份证正则表达式(18位)
					Pattern p_idNo2 = Pattern
							.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}(x|X))$");
					Matcher matcher_idNo1 = p_idNo1.matcher(idNo);
					Matcher matcher_idNo2 = p_idNo2.matcher(idNo);
					long len = idNo.length();
					if (!(matcher_idNo1.matches() || matcher_idNo2.matches())) {
						//json.put("msg", "身份证号码不合法");
						response.failure("身份证号码不合法");
						JSONUtils.printObject(response);
						return null;
					}
					
					
				}
				String card_no = infoMap.get("card_no");//银行卡号
				
				String card_mobile= infoMap.get("card_mobile");//预留手机号码
				
				String vc_branchbank = infoMap.get("vc_branchbank");//银行编号
				
				if (StringUtils.isBlank(card_no)) {
					response.failure("请输入银行卡号");
					JSONUtils.printObject(response);
					return null;
				}
				
				
				if (StringUtils.isBlank(card_mobile)) {
					response.failure("请输入银行预留手机号");
					JSONUtils.printObject(response);
					return null;
				}
				
				if (StringUtils.isBlank(vc_branchbank)) {
					response.failure("请选择开户行所在支行");
					JSONUtils.printObject(response);
					return null;
				}
				
				
				// 查询某个账号是否已经被该用户绑定过了
				Map<String, String> bandingMap = homeInfoSettingService
						.queryBankCardisBanding(userId, card_no);
				int bindingCardisBanding = Convert.strToInt(
						bandingMap.get("count(*)"), 0);
				if (bindingCardisBanding > 0) {
					response.failure("此银行卡已被使用");
					JSONUtils.printObject(response);
					return null;
				}
				
				
				
				Map<String, String> data = new HashMap<String, String>();
				data.put("[name]", realName);
				data.put("[idNo]", idNo);
				data.put("[cardNo]",card_no);
				data.put("[bankNo]", vc_branchbank);
				data.put("[mobile]", card_mobile);
				data.put("[idType]", id_type);
				data.put("method", "hr.fund.sign.sms");
				String result = hsFundService.hr_fund(data, queryToken(userService,userId));
				result.replace("\"description\"", "\"message\"");
				JSONUtils.printObject(result);//该接口为短信接口，暂时不做数据库的任何操作
				return null;
			} catch (Exception e) {
				response.failure("服务器异常");
				JSONUtils.printObject(response);
				return null;
			}
		}

	public String Identifier(String realName, String idNo) throws SQLException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			List<Map<String, Object>> lists = sysparService
					.querySysparAllChild("*",
							" selectKey =\"SMRZ\" and deleted=1", "", -1, -1);
			if (lists != null && lists.size() > 0
					&& "SMRZ".equals(lists.get(0).get("selectKey"))) {
				return "成功";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JSONObject json = null;
		String result = null;
		String result1 = "不成功";
		try {
			// 鹏元
			/*
			 * boolean pyopen = sysparService.isOpen("PYSM", "20"); if (pyopen)
			 * { Map<String, Object> py = pyRealName(realName, idNo); if
			 * (py!=null && py.get("code").equals("1")) { return "成功"; }else {
			 * return "不成功"; } }
			 */

			String req = String.format("{\"IDNumber\":\"%s\",\"Name\":\"%s\"}",
					idNo, realName);
			String cred = String.format(
					"{\"UserName\":\"%s\",\"Password\":\"%s\"}", "yft_admin",
					"SP7Z27PH");
			IdentifierServiceStub client = new IdentifierServiceStub();
			SimpleCheckByJson scbj = new SimpleCheckByJson();
			scbj.setCred(cred);
			scbj.setRequest(req);
			SimpleCheckByJsonResponse scbr = client.simpleCheckByJson(scbj);
			result = scbr.getSimpleCheckByJsonResult();
			System.out.println(result);
			JSONObject jsonObj = JSONObject.fromObject(result);

			System.out.println(jsonObj.get("ResponseText"));

			if ("成功".equals(jsonObj.get("ResponseText"))) {
				JSONObject info = jsonObj.getJSONObject("Identifier");
				if ("一致".equals(info.get("Result"))) {
					result1 = "成功";
				} else {
					result1 = "不成功";
				}
			} else {
				result1 = "不成功";
			}
			if ("不成功".equals(jsonObj.get("ResponseText"))) {
				result1 = "不成功";
			}
			if ("余额不足".equals(jsonObj.get("ResponseText"))) {
				result1 = "余额不足";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result1;
	}

	public String addOrUpdatePerson() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		Map<String, String> pMap = null;
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String realName = appInfoMap.get("realName");// 真实姓名
			if (StringUtils.isBlank(realName)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请正确填写真实名字");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (2 > realName.length() || 20 < realName.length()) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "真实姓名的长度为不小于2和大于20");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String idNo = appInfoMap.get("idNo");// 身份证号码
			if (StringUtils.isBlank(idNo)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请输入正确的身份证号码");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (15 != idNo.length() && 18 != idNo.length()) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "请输入正确的身份证号码");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 验证身份证
			int sortCode = 0;
			int MAN_SEX = 0;
			if (idNo.length() == 15) {
				sortCode = Integer.parseInt(idNo.substring(12, 15));
			} else {
				sortCode = Integer.parseInt(idNo.substring(14, 17));
			}
			if (sortCode % 2 == 0) {
				MAN_SEX = 1;// 女性身份证
			} else if (sortCode % 2 != 0) {
				MAN_SEX = 2;// 男性身份证
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "身份证不合法");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String iDresutl = "";
			iDresutl = IDCardValidateService.chekIdCard(MAN_SEX, idNo);
			if (iDresutl != "") {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "身份证不合法");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (IConstants.ISDEMO.equals("1")) {

			} else {
				// 判断身份证的唯一性
				if (!StringUtils.isBlank(idNo)) {
					Map<String, String> idNoMap = beVipService
							.queryIDCard(idNo);
					if (idNoMap != null && !idNoMap.isEmpty()) {

						if (Convert.strToLong(idNoMap.get("userId"), -1) != userId) {
							jsonMap.put("error", "6");
							jsonMap.put("msg", "身份证已注册");
							JSONUtils.printObject(jsonMap);
							return null;
						}
					}
				}

			}

			String cellPhone = appInfoMap.get("cellPhone");// 手机号码
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "请正确填写手机号码");
				JSONUtils.printObject(jsonMap);
				return null;
			} else if (cellPhone.length() < 9 || cellPhone.length() > 12) {
				jsonMap.put("error", "8");
				jsonMap.put("msg", "手机号码长度不对");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			/**
			 * 判定用户是否已存在记录
			 */
			if (userId == -1) {
				jsonMap.put("error", "9");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Map<String, String> personMap = userService.queryPersonById(userId);
			if (personMap != null) {
				if (personMap.get("auditStatus").equals("3")) {
					jsonMap.put("error", "30");
					jsonMap.put("msg", "您的信息已通过审核");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}
			// 验证手机的唯一性
			Map<String, String> phonemap = beVipService.queryIsPhone(cellPhone);
			pMap = beVipService.queryPUser(userId);
			if (pMap == null) {
				if (phonemap != null) {
					jsonMap.put("error", "10");
					jsonMap.put("msg", "手机号已存在");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			} else if (phonemap != null
					&& !cellPhone.trim().equals(pMap.get("cellphone"))) {
				jsonMap.put("error", "10");
				jsonMap.put("msg", "手机号已存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (IConstants.ISDEMO.equals("2")) {
				String phonecode = appInfoMap.get("recivePhone");
				if (StringUtils.isBlank(phonecode)) {
					jsonMap.put("error", "12");
					jsonMap.put("msg", "你还没有获取手机验证码");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				phonecode = Encrypt.decryptSES(phonecode, AlipayConfig.ses_key);
				if (StringUtils.isNotBlank(phonecode)) {
					if (!phonecode.trim().equals(cellPhone.trim())) {
						jsonMap.put("error", "12");
						jsonMap.put("msg", "与获取验证码手机号不一致");
						JSONUtils.printObject(jsonMap);
						return null;
					}

				}
				// 验证码
				String code = appInfoMap.get("code");
				if (StringUtils.isBlank(code)) {
					jsonMap.put("error", "13");
					jsonMap.put("msg", "请填写验证码");
					JSONUtils.printObject(jsonMap);
					return null;
				}

				String randomCode = appInfoMap.get("randomCode");
				if (StringUtils.isBlank(randomCode)) {
					jsonMap.put("error", "14");
					jsonMap.put("msg", "请输入正确的验证码");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				randomCode = Encrypt.decryptSES(randomCode,
						AlipayConfig.ses_key);
				if (!code.trim().equals(randomCode)) {
					jsonMap.put("error", "14");
					jsonMap.put("msg", "请输入正确的验证码");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			String sex = appInfoMap.get("sex");// 性别(男 女)
			if (StringUtils.isNotBlank(sex)) {
				if (sex.equals("男") && MAN_SEX == 1) {
					jsonMap.put("error", "12");
					jsonMap.put("msg", "请正确填写性别");
					JSONUtils.printObject(jsonMap);
					return null;
				} else if (sex.equals("女") && MAN_SEX == 2) {
					jsonMap.put("error", "12");
					jsonMap.put("msg", "请正确填写性别");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			String birthday = appInfoMap.get("birthday");// 出生日期

			if (StringUtils.isBlank(birthday)) {
				jsonMap.put("error", "13");
				jsonMap.put("msg", "请正确填写出生日期");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String highestEdu = appInfoMap.get("highestEdu");// 最高学历
			if (StringUtils.isBlank(highestEdu)) {
				jsonMap.put("error", "14");
				jsonMap.put("msg", "请正确填写最高学历");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String eduStartDay = appInfoMap.get("eduStartDay");// 入学年份
			if (StringUtils.isBlank(eduStartDay)) {
				jsonMap.put("error", "15");
				jsonMap.put("msg", "请正确填写入学年份");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String school = appInfoMap.get("school");// 毕业院校
			if (StringUtils.isBlank(school)) {
				jsonMap.put("error", "16");
				jsonMap.put("msg", "请正确填写入毕业院校");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String maritalStatus = appInfoMap.get("maritalStatus");// 婚姻状况(已婚
			// 未婚)
			if (StringUtils.isBlank(maritalStatus)) {
				jsonMap.put("error", "17");
				jsonMap.put("msg", "请正确填写入婚姻状况");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String hasChild = appInfoMap.get("hasChild");// 有无子女(有 无)

			if (StringUtils.isBlank(hasChild)) {
				jsonMap.put("error", "18");
				jsonMap.put("msg", "请正确填写入有无子女");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String hasHourse = appInfoMap.get("hasHourse");// 是否有房(有 无)
			if (StringUtils.isBlank(hasHourse)) {
				jsonMap.put("error", "19");
				jsonMap.put("msg", "请正确填写入是否有房");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String hasHousrseLoan = appInfoMap.get("hasHousrseLoan");// 有无房贷(有
			// 无)
			if (StringUtils.isBlank(hasHousrseLoan)) {
				jsonMap.put("error", "19");
				jsonMap.put("msg", "请正确填写入有无房贷");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String hasCar = appInfoMap.get("hasCar");// 是否有车 (有 无)
			if (StringUtils.isBlank(hasCar)) {
				jsonMap.put("error", "20");
				jsonMap.put("msg", "请正确填写入是否有车");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String hasCarLoan = appInfoMap.get("hasCarLoan");// 有无车贷 (有 无)
			if (StringUtils.isBlank(hasCarLoan)) {
				jsonMap.put("error", "21");
				jsonMap.put("msg", "请正确填写入有无车贷");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long nativePlacePro = Convert.strToLong(
					appInfoMap.get("nativePlacePro"), -1);// 籍贯省份(默认为-1)
			if (StringUtils.isBlank(nativePlacePro.toString())) {
				jsonMap.put("error", "22");
				jsonMap.put("msg", "请正确填写入籍贯省份");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Long nativePlaceCity = Convert.strToLong(
					appInfoMap.get("nativePlaceCity"), -1);// 籍贯城市 (默认为-1)
			if (StringUtils.isBlank(nativePlaceCity.toString())) {
				jsonMap.put("error", "23");
				jsonMap.put("msg", "请正确填写入籍贯城市");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Long registedPlacePro = Convert.strToLong(
					appInfoMap.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
			if (StringUtils.isBlank(registedPlacePro.toString())) {
				jsonMap.put("error", "24");
				jsonMap.put("msg", "请正确填写入户口所在地省份");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Long registedPlaceCity = Convert.strToLong(
					appInfoMap.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)

			if (StringUtils.isBlank(registedPlaceCity.toString())) {
				jsonMap.put("error", "25");
				jsonMap.put("msg", "请正确填写入户口所在地城市");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String address = appInfoMap.get("address");// 所在地
			if (StringUtils.isBlank(address)) {
				jsonMap.put("error", "26");
				jsonMap.put("msg", "请正确填写居住地址");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String telephone = appInfoMap.get("telephone");// 居住电话
			if (StringUtils.isNotBlank(telephone)) {

				if (!telephone.contains("-")) {
					jsonMap.put("error", "15");
					jsonMap.put("msg", "你的居住电话格式不正确");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				if (telephone.trim().length() < 7
						|| telephone.trim().length() > 13) {
					jsonMap.put("error", "16");
					jsonMap.put("msg", "你的居住电话输入长度不对");
					JSONUtils.printObject(jsonMap);
					return null;
				}
			}

			/* 用户头像 */
			String personalHead = appInfoMap.get("personalHead");// 个人头像
			// (默认系统头像)
			// if (StringUtils.isBlank(personalHead)) {
			// jsonMap.put("error", "30");
			// jsonMap.put("msg", "请正确上传你的个人头像");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			long personId = -1L;

			personId = userService.updateUserBaseData(realName, cellPhone, sex,
					birthday, highestEdu, eduStartDay, school, maritalStatus,
					hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
					nativePlacePro, nativePlaceCity, registedPlacePro,
					registedPlaceCity, address, telephone, personalHead,
					userId, idNo);
			if (personId > 0) {
				session().removeAttribute("randomCode");
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);

				// 成功
			} else {
				// 失败
				jsonMap.put("error", "17");
				jsonMap.put("msg", "保存失败");

				JSONUtils.printObject(jsonMap);

			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "18");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);

		}

		return null;
	}

	public String queryPersonByUserId() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> map = userService.queryPersonById(userId);
			if (map == null || map.isEmpty()) {
				jsonMap.put("error", "-2");
				jsonMap.put("msg", "用户没有填写个人基本信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			jsonMap.put("error", "-1");
			jsonMap.put("msg", "获取成功");
			jsonMap.putAll(map);
			JSONUtils.printObject(jsonMap);

		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
