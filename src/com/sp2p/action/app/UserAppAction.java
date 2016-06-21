package com.sp2p.action.app;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.util.SMSUtil;
import com.shove.util.SqlInfusion;
import com.shove.web.Utility;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.shove.web.util.UploadUtil;
import com.shove.web.util.VerifyTruePicture;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.entity.Response;
import com.sp2p.entity.User;
import com.sp2p.entity.Users;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.ExperienceVoucherService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.RelationService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.util.DateUtil;
import com.sp2p.util.PublicFunction;
import com.sp2p.vioce.RestUtil;

public class UserAppAction extends BaseAppAction {
	public static Log log = LogFactory.getLog(UserAppAction.class);
	private static final long serialVersionUID = -9011135946028616456L;

	private UserService userService;
	private UserIntegralService userIntegralService;
	private HomeInfoSettingService homeInfoSettingService;
	private SMSInterfaceService sMSInterfaceService;
	private BBSRegisterService bbsRegisterService;
	private RelationService relationService;
	private RecommendUserService recommendUserService;
	private CellPhoneService cellPhoneService;
	private MyHomeService myHomeService;
	private FinanceService financeService;
	private SelectedService selectedService;
	private ExperienceVoucherService experienceVoucherService;
	
	// private BBSRegisterService bbsRegisterService;

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public void setExperienceVoucherService(
			ExperienceVoucherService experienceVoucherService) {
		this.experienceVoucherService = experienceVoucherService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public void setCellPhoneService(CellPhoneService cellPhoneService) {
		this.cellPhoneService = cellPhoneService;
	}

	public void setRecommendUserService(
			RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
		this.bbsRegisterService = bbsRegisterService;
	}
	
	public String logout() throws IOException{
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登陆").toLogin();
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId == -1L) {
				response.failure("请重新登陆").toLogin();
				JSONUtils.printObject(response);
				return null;
			}
			userService.updateUserToken(userId, "", "");
			response.success();
			JSONUtils.printObject(response);
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			return null;
		}
		return null;
	}
	
	// 检查手机是否注册
	public String check_Mobile() throws IOException {
		Response response = new Response();
		try {
			Map<String, String> appinfoMap = getAppInfoMap();
			String mobile_tel = appinfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("手机号码不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			if (!com.shove.util.StringUtils.matchPhone(mobile_tel)) {
				response.failure("手机号码不正确");
				JSONUtils.printObject(response);
				return null;
			}
			long userid = userService.queryUserIdByPhone(mobile_tel);
			appinfoMap.clear();
			if (userid > 0) {
				appinfoMap.put("mobile_status", "1");
			} else {
				appinfoMap.put("mobile_status", "0");
			}
			response.success();
			response.setData(appinfoMap);
			JSONUtils.printObject(response);
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			return null;
		}
		return null;
	}

	public String autoLogin() throws Exception {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appAuthMap = getAppAuthMap();
			String uid = appAuthMap.get("uid");
			String token = appAuthMap.get("token");
			if (StringUtils.isNotBlank(uid)) {
				Map<String, String> tokens = userService
						.queryUserTokenById(Convert.strToLong(uid, -1));
				String t_token = tokens.get("token");
				String t_checkTime = tokens.get("checktime");
				String t_userid = tokens.get("userid");
				if (t_token == null || !t_token.equals(token)) {
					jsonMap.put("error", "-3");
					jsonMap.put("msg", "登录已失效，请重新登录");
					JSONUtils.printObject(jsonMap);
					return null;
				} else {
					Date startDate = new Date();// 当前时间
					Date endDate = DateUtil.YYYYMMDDHHMMSS.parse(t_checkTime);
					long days = DateUtil.diffDays(startDate, endDate);
					if (days > 3) {// 超过三天了登录失效，需要重新登录了
						jsonMap.put("error", "-3");
						jsonMap.put("msg", "登录已失效，请重新登录");
						JSONUtils.printObject(jsonMap);
						return null;
					} else {
						// 刷新登录令牌
						userService.updateUserToken(
								Convert.strToLong(t_userid, -1), t_token,
								DateUtil.YYYYMMDDMMHHSSSSS.format(startDate));
						// 更新用户登录数据(需要用户的密码跟用户名)
						return login();
					}
				}
			} else {
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "登录已失效，请重新登录");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "11");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			return null;
		}
	}

	public String login() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		// 验证签名
		Map<String, String> jsonMap = new HashMap<String, String>();

		// Map<String, String> authMap = getAppAuthMap();
		// String time = new Date().getTime()+"";
		Response response = new Response();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String name = appInfoMap.get("mobile_tel");
			String pwd = appInfoMap.get("login_pwd");
			// String step1 = appInfoMap.get("step1");
			String time = new Date().getTime() + "";

			if (StringUtils.isBlank(name)) {
				name = request.getString("username");
			}
			if (StringUtils.isBlank(pwd)) {
				pwd = request.getString("password");
			}

			// 查找数据库对象中的enable属性
			if (StringUtils.isBlank(name)) {
				jsonMap.put("error", "1");
				// jsonMap.put("msg", "用户名或手机号为空");
				jsonMap.put("msg", "用户名不能为空");
				response.failure("用户名不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			if (StringUtils.isBlank(pwd)) {
				// jsonMap.put("error", "2");
				// jsonMap.put("msg", "密码不能为空");
				response.failure("密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			/*
			 * String step2 = Encrypt.MD5(name+"cellPhone"); if
			 * (!step2.equals(step1)) { jsonMap.put("error", "3");
			 * jsonMap.put("msg", "参数异常"); JSONUtils.printObject(jsonMap);
			 * return null; }
			 */

			/*try {
				String autoLogin = appInfoMap.get("autoLogin");
				if ("true".equals(autoLogin)) {
					String uid = appInfoMap.get("uid");
					Map<String, String> map = userService.queryUserById(Convert
							.strToLong(uid, -1));
					// 自动登录
					name = map.get("username");
					pwd = map.get("password");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/

			String lastIP = ServletUtils.getRemortIp();
			String lastTime = DateUtil.dateToString(new Date());

			Thread.sleep(500);
			User userMap = userService.userLogin1(name, pwd, lastIP, lastTime);

			if (userMap == null) {
				//jsonMap.put("error", "4");
				//jsonMap.put("msg", "用户名或密码错误");
				response.failure("用户名或密码错误");
				JSONUtils.printObject(response);
				return null;
			}
			// 查找数据库对象中的enable属性
			if (userMap.getEnable() == 2) {
				//jsonMap.put("error", "5");
				//jsonMap.put("msg", "该用户已被限制登录，请于三小时以后登录！");
				response.failure("该用户已被锁定，请联系客服人员");
				JSONUtils.printObject(response);
				return null;
			}

			if (userMap.getIsLoginLimit() == 2) {
				response.failure("该用户已被限制登录，请于三小时以后登录！");
				JSONUtils.printObject(response);
				return null;
			}
			if (userMap.getLoginErrorCount() == 3) {
				response.failure("用户名或密码错误!还有2次机会");
				JSONUtils.printObject(response);
				return null;
			}
			if (userMap.getLoginErrorCount() == 4) {
				response.failure("用户名或密码错误!还有1次机会");
				JSONUtils.printObject(response);
				return null;
			}

			boolean re = userService.checkSign(userMap.getId());
			if (!re) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "*该用户账号出现异常，请速与管理员联系！");
				response.failure("*该用户账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(response);
				return null;
			}

			// 令牌
			String uuid = UUID.randomUUID().toString().replace("-", "");
			jsonMap.clear();
			jsonMap.put("token", uuid);

			jsonMap.putAll(userService.getUserInfoApp(userMap.getId()));

			response.setData(jsonMap);
			// 查询TOKEN表是否有数据
			Map<String, String> userTokenMap = userService
					.queryUserTokenById(userMap.getId());
			log.info("userId:" + userMap.getId() + ",uuid:" + uuid + ", time:"
					+ time);
			if (null == userTokenMap || userTokenMap.isEmpty()) {
				// 没有数据插入TOKEN数据
				userService.addToken(userMap.getId(), uuid, time);
			} else {
				// 有数据更新TOKEN
				userService.updateUserToken(userMap.getId(), uuid, time);
			}

			// 刷新登录计数
			userService.loginCountReFresh(userMap.getId());

			// 用户登录日志插入
			userIntegralService.addUserLoginLog(userMap.getId());

			// jsonMap.putAll(userMap);

			// 用户登录分数
			/*
			 * Map<String, String> logmap = null; Map<String, String> usermap =
			 * null; Integer preScore = null; int LongCount = 1; int score = 1;
			 * if (userMap.getId() > 0) { logmap =
			 * userIntegralService.queryUserLoginLong(userMap.getId()); usermap
			 * = userService.queryUserById(userMap.getId()); if (logmap.size() >
			 * 0 && logmap != null) { preScore =
			 * Convert.strToInt(usermap.get("rating"), 0); LongCount =
			 * Convert.strToInt(logmap.get("cl"), 0);
			 * userIntegralService.UpdateLoginRating(userMap.getId(), score,
			 * preScore, LongCount); jsonMap.put("userType",
			 * usermap.get("userType")); } }
			 */
			// jsonMap.put("realName", "");
			/*
			 * Connection conn = MySQL.getConnection(); Map<String, String>
			 * personMap = new HashMap<String, String>(); try { DataSet dataSet
			 * = Database.executeQuery(conn,
			 * "select * from v_app_t_person2 where userId="+userMap.getId());
			 * personMap = BeanMapUtils.dataSetToMap(dataSet); } catch
			 * (Exception e) { }finally{ conn.close(); } if (personMap != null
			 * && personMap.size() > 0) { jsonMap.put("cellPhone",
			 * personMap.get("cellPhone")); jsonMap.put("realName",
			 * personMap.get("realName")); }
			 */

			/*
			 * for (String key : personMap.keySet()) { jsonMap.put(key,
			 * personMap.get(key)); }
			 */

			// jsonMap.put("error", "-1");
			// jsonMap.put("msg", "登录成功");
			response.success().setMessage("登录成功");
			JSONUtils.printObject(response);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	public String register() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String time = new Date().getTime() + "";
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String name = appInfoMap.get("user_name");
			String password = appInfoMap.get("user_password");

			if (StringUtils.isBlank(name)) {
				response.failure("用户名不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			String dealpwd = appInfoMap.get("user_password");

			if (StringUtils.isBlank(password)) {
				response.failure("密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			String phone = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(phone)) {
				response.failure("手机号码不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			if (!com.shove.util.StringUtils.matchPhone(phone)) {
				response.failure("手机号码不正确");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (NumberUtils.isNumber(name)) {
				response.failure("用户名不能是纯数字");
				JSONUtils.printObject(response);
				return null;
			}
			
			
			long str = userService.queryUserIdByPhone(phone);
			if (str > 0) {
				response.failure("该手机号已注册");
				JSONUtils.printObject(response);
				return null;
			}
			String code = appInfoMap.get("identify_code");
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
			String md5 = Encrypt.MD5(phone + code);
			// String phone = session().getAttribute("phone") +"";
			if (!md5.equals(md5_code)) {
				response.failure("验证码不正确");
				JSONUtils.printObject(response);
				return null;
			}

			boolean expire = userService.querySendTime(phone, code);
			if (expire) {
				response.failure("验证码已过期");
				JSONUtils.printObject(response);
				return null;
			}

			/*
			 * String step2 = Encrypt.MD5(phone+"cellPhone"); if
			 * (!step2.equals(step1)) { jsonMap.put("error", "10");
			 * jsonMap.put("msg", "未知异常"); JSONUtils.printObject(jsonMap);
			 * return null; }
			 */

			Long userId = -1L;
			Integer userType = 1;// 用户类型（1.个人用户，2.企业用户 ）
			String md5Password_pwd = password;
			// 用户数据处理
			Users userEntity = new Users();
			// 用户名长度大于2且小于20
			if (name.length() < 2 || name.length() > 20) {
				// 用户名长度大于2且小于20
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "用户名长度大于2且小于20");
				response.failure("用户名长度大于2且小于20");
				JSONUtils.printObject(response);
				return null;
			}

			// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
			if (name.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
				// 用户名不能包含特殊字符
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "用户名不能包含特殊字符");
				response.failure("用户名不能包含特殊字符");
				JSONUtils.printObject(response);
				return null;
			}

			// 判断第一个字符串不能使以下划线开头的
			String fristChar = name.substring(0, 1);
			if (fristChar.equals("_")) {// 用户名不能以下划线开头
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "用户名不能以下划线开头");
				response.failure("用户名不能以下划线开头");
				JSONUtils.printObject(response);
				return null;
			}

			if (StringUtils.isBlank(password)) {
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "登录密码不能为空");
				response.failure("登录密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			// 用户名、email、手机号码、企业全称唯一认证
			Long result_ = userService.isExistEmailORUserName(null, name, null,
					null);
			if (result_ > 0) { // 用户名重复
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "用户名已被使用");
				response.failure("用户名已被使用");
				JSONUtils.printObject(response);
				return null;//
			}
			result_ = userService.isExistEmailORUserName(null, null, null,
					phone);
			if (result_ > 0) { // 手机号码重复
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "手机号码已被注册");
				response.failure("手机号码已被注册");
				JSONUtils.printObject(response);
				return null;//
			}
			// IConstants.ENABLED_PASS是否启用加密
			if ("1".equals(IConstants.ENABLED_PASS)) {
				md5Password_pwd = com.shove.security.Encrypt
						.MD5(md5Password_pwd.trim());
			} else {
				md5Password_pwd = com.shove.security.Encrypt
						.MD5(md5Password_pwd.trim() + IConstants.PASS_KEY);
			}
			userEntity.setUserName(name);
			userEntity.setOrgName(orgName);
			userEntity.setMobilePhone(phone);
			userEntity.setPassword(md5Password_pwd);
			String refferee = appInfoMap.get("referee_user_name");
			Map<String, Object> map = null;
			long recommendUserId = -1;// 推荐人userId
			// 推荐人
			if (StringUtils.isNotBlank(refferee)) {
				// 根据用户名查询推荐人用户明细
				Map<String, String> userIdMap = userService
						.queryIdByUser(refferee);
				if (userIdMap != null) {
					recommendUserId = Convert
							.strToLong(userIdMap.get("id"), -1);
				}
				// 判断推荐人是否为推广人
				map = relationService.isPromoter(refferee);
				if (map == null) {
					refferee = null;
				}
				if (userIdMap == null && map == null) {
					// jsonMap.put("error", "1");
					// jsonMap.put("msg", "推荐人不存在");
					response.failure("推荐人不存在");
					JSONUtils.printObject(response);
					return null;
				}
			}
			String email = "";
			if (StringUtils.isBlank(email)) {
				userEntity.setEmail("default@163.com");
			} else {
				long count = userService.validationIdNoAndRealName("email",
						email, "t_user");
				if (count > 0) {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "邮箱已被使用");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				userEntity.setEmail(email);
			}
			// 手机注册用户
			userEntity.setEnable(1);// 将用户激活
			int typelen = -1;
			Map<String, String> lenMap = null;
			lenMap = userService.querymaterialsauthtypeCount(); // 查询证件类型主表有多少种类型
			if (lenMap != null && lenMap.size() > 0) {
				typelen = Convert.strToInt(lenMap.get("cccc"), -1);
				if (typelen != -1) {
					// 个人用户
					if (userType == 1) {
						userEntity.setUserType(1);
					} else {
						// 企业用户
						userEntity.setUserType(2);
					}
					userId = cellPhoneService.usercellRegister(
							userEntity.getMobilePhone(),
							userEntity.getUserName(), userEntity.getPassword(),
							userEntity.getRefferee(), map, typelen,
							userEntity.getUserType(), userEntity.getOrgName());// 注册用户
																				// 和
																				// 初始化图片资料
				}
			}
			if (userId < 0) {
				// 注册失败
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "注册失败");
				response.failure("注册失败");
				JSONUtils.printObject(response);
				return null;
			} else {

				// 注册成功，判断当前赠送体验券功能是否开启，开启则绑定体验券到此用户
				Map<String, String> syspar = selectedService.getSyspar("TYQ");
				if (syspar != null && syspar.get("selectKey") != null
						&& !"".equals(syspar.get("selectKey"))) {
					int deleted = Convert.strToInt(syspar.get("deleted"), -1);
					if (deleted == 1) {// 表示启动此功能
						int batchId = Convert.strToInt(
								syspar.get("selectValue"), -1); // 得到批次号
						if (batchId != -1) {
							// 获取对应批次号的一张未绑定的体验券
							Map<String, String> ticket = experienceVoucherService
									.getTicket(batchId);
							if (ticket != null
									&& ticket.get("ticketNo") != null
									&& !"".equals(ticket.get("ticketNo"))) {
								String ticketNo = ticket.get("ticketNo");
								Map<String, String> bindResult = experienceVoucherService
										.activeTicket(userId.intValue(),
												ticketNo);
								log.info("绑定结果：================" + bindResult);
							}
						}
					}
				}

				// 修改交易密码
				homeInfoSettingService.updateUserPassword(userId, dealpwd,
						"tradpwd");
				// 如果是企业注册，往企业基础表添加数据
				if (userType == 2) {
					userService.addUserCompanyData(userId,
							userEntity.getOrgName(), null, null, null, null,
							null, null, null, null, null, null, null, null,
							null, null, null, null, null, null, null);
				}
				// 手机注册
				userService.updateSign(userId);// 修改校验码
				// 添加通知默认方法
				homeInfoSettingService.addNotes(userId, true, false, false);
				homeInfoSettingService.addNotesSetting(userId, true, true,
						true, true, true, false, false, false, false, false,
						false, false, false, false, false);
				// jsonMap.put("error", "-1");
				// jsonMap.put("msg", "注册成功");
				// jsonMap.put("uid", userId + "");
				// 令牌
				String uuid = UUID.randomUUID().toString().replace("-", "");
				jsonMap.put("token", uuid);
				jsonMap.putAll(userService.getUserInfoApp(userId));
				response.success(jsonMap, "注册成功");

				// 清空验证码
				userService.overSendLog(phone, "", code);

				JSONUtils.printObject(response);

				// 查询TOKEN表是否有数据
				Map<String, String> userTokenMap = userService
						.queryUserTokenById(userId);
				log.info("userId:" + userId + ",uuid:" + uuid + ", time:"
						+ time);
				if (null == userTokenMap || userTokenMap.isEmpty()) {
					// 没有数据插入TOKEN数据
					userService.addToken(userId, uuid, time);
				} else {
					// 有数据更新TOKEN
					userService.updateUserToken(userId, uuid, time);
				}
				Users user = new Users();
				user.setUserName(userEntity.getUserName());
				user.setPassword(password);
				user.setMobilePhone(phone);
				if (StringUtils.isBlank(email)) {
					user.setEmail("default@163.com");
				} else {
					user.setEmail(email);
				}
				// bbsRegisterService.doRegisterByAsynchronousMode(user);

				// 修改之前的推荐
				if (recommendUserId > 0) {// 判断是否为空

					List<Map<String, Object>> list = recommendUserService
							.queryRecommendUser(null, userId, null);// 查询用户是否已经存在关系了。
					if (list != null && list.size() > 0) {// 判断之前是否已经有关系了。
					}
					recommendUserService.addRecommendUser(userId,
							recommendUserId);
				}

				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// jsonMap.put("error", "8");
			// jsonMap.put("msg", "未知异常");
			response.failure("未知异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/**
	 * 登陆第一步
	 * 
	 * @return
	 * @throws IOException
	 */
	public String loginOrReg() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		// 验证签名
		Map<String, String> jsonMap = new HashMap<String, String>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String phone = appInfoMap.get("cellPhone");

			if (StringUtils.isBlank(phone)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long str = userService.queryUserIdByPhone(phone);
			String step1 = Encrypt.MD5(phone + "cellPhone");
			if (str > 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "true");
				jsonMap.put("step1", step1);
				JSONUtils.printObject(jsonMap);
			} else {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "false");
				jsonMap.put("step1", step1);
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "0");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
		}
		return null;
	}

	/**
	 * 发送短信(注册)
	 * 
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException
	 * @throws DocumentException
	 */
	public String sendSMS() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> jsonMap = new HashMap<String, String>();
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();

			String phone = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(phone)) {
				response.failure("手机号不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			if (!com.shove.util.StringUtils.matchPhone(phone)) {
				response.failure("手机号码不正确");
				JSONUtils.printObject(response);
				return null;
			}

			/*
			 * long str = userService.queryUserIdByPhone(phone); if (str > 0) {
			 * response.failure("该手机号码已注册！"); JSONUtils.printObject(response);
			 * return null; }
			 */

			// 随机产生4位数字
			String randomCode = RandomStringUtils.randomNumeric(4);
			// 发送短信
			log.debug("===========randomCode======" + randomCode);
			String content = "尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME
					+ ",手机验证码为:";
			// 获取短信接口url
			String retCode = SMSUtil
					.sendSMS("", "", content, phone, randomCode);
			// String retCode = "Sucess";

			// 发送短信
			if ("Sucess".equals(retCode)) {
				jsonMap.put("md5_code", Encrypt.MD5(phone + randomCode));
				response.success(jsonMap, "发送成功");
				JSONUtils.printObject(response);
				// 添加短信记录
				userService.addSendLog(phone, content + ",短信发送", 180L,
						randomCode);
				return null;
			} else if ("3".equals(retCode)) {
				jsonMap.put("md5_code", Encrypt.MD5(phone + randomCode));
				response.success(jsonMap, "发送成功");
				JSONUtils.printObject(response);
				// 添加短信记录
				userService.addSendLog(phone, content + ",短信发送", 180L,
						randomCode);
				return null;
			} else {
				response.failure("发送失败");
				JSONUtils.printObject(response);
				return null;
			}

		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			e.printStackTrace();
		}
		return null;

	}

	// public static void main(String[] args) {
	// System.out.println(Encrypt.MD5("test123"));
	// }
	/**
	 * 重置密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetLoginPwd() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		// Map<String, String> jsonMap = new HashMap<String, String>();
		Response response = new Response();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String password = appInfoMap.get("user_password");

			if (StringUtils.isBlank(password)) {
				response.failure("新密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}

			String cellPhone = appInfoMap.get("mobile_tel");
			String code = appInfoMap.get("identify_code");
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
			String md5 = Encrypt.MD5(cellPhone + code);
			// String phone = session().getAttribute("phone") +"";
			if (!md5.equals(md5_code)) {
				response.failure("验证码不正确");
				JSONUtils.printObject(response);
				return null;
			}

			boolean expire = userService.querySendTime(cellPhone, code);
			if (expire) {
				response.failure("验证码已过期");
				JSONUtils.printObject(response);
				return null;
			}
			//}

			long userId = userService.queryUserIdByPhone(cellPhone);

			if (userId == -1) {
				response.failure("手机号与绑定的手机号不一致");
				JSONUtils.printObject(response);
				return null;
			}
			Long result = -1L;

			result = userService.updateLoginPwd(userId, password);
			userService.overSendLog(cellPhone, "", code);
			if (result > 0) {
				// userService.queryUserById(userId);
				// 修改论坛的密码
				// bbsRegisterService.doUpdatePwdByAsynchronousMode(this.getUser()
				// .getUserName(), password, password, 2);
				response.success();
				JSONUtils.printObject(response);
				return null;
			} else {
				response.failure("修改失败");
				JSONUtils.printObject(response);
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/**
	 * 修改密码发送验证码
	 */
	public String sendUpdatePwdSms() {

		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String phone = appInfoMap.get("cellPhone");
			if (StringUtils.isBlank(phone)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long str = userService.queryUserIdByPhone(phone);
			if (str <= 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "此手机号码尚未注册，请重新填写！");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			// 随机产生4位数字
			int intCount = 0;
			intCount = (new Random()).nextInt(9999);// 最大值位9999
			if (intCount < 1000)
				intCount += 1000; // 最小值位1001

			String randomCode = intCount + "";
			// 发送短信
			Map<String, String> map = sMSInterfaceService.getSMSById(1);
			System.out.println(randomCode);
			String content = "尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME
					+ ",手机验证码为:";
			String retCode = SMSUtil.sendSMS(map.get("Account"),
					map.get("Password"), content, phone, randomCode);
			// 发送短信
			if ("Sucess".equals(retCode)) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "发送成功");
				jsonMap.put("randomCode", Encrypt.MD5(randomCode + phone));// 编码传过去
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "发送失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateLoginPwd() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> jsonMap = new HashMap<String, String>();
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			String password = appInfoMap.get("new_user_password");
			String code = appInfoMap.get("user_password");

			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId < 0) {
				response.failure("请重新登录");
				JSONUtils.printObject(response);
				return null;
			}

			Map<String, String> userMap = userService
					.queryUserByPhone(mobile_tel);
			if (userMap == null || userMap.size() == 0
					|| StringUtils.isBlank(userMap.get("id"))) {
				response.failure("您的手机号码尚未注册，请重新输入");
				JSONUtils.printObject(response);
				return null;
			}

			if (StringUtils.isBlank(password)) {
				response.failure("新密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}

			String oldPwd = appInfoMap.get("user_password");

			if (StringUtils.isBlank(oldPwd)) {
				response.failure("旧密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			if (password.equals(userMap.get("dealpwd"))) {
				response.failure("登录密码不能和交易密码一样");
				JSONUtils.printObject(response);
				return null;
			}

			boolean re = userService.checkSign(userId);
			if (!re) {
				response.failure("修改失败！你的账号出现异常，请速与管理员联系");
				JSONUtils.printObject(response);
				return null;
			}

			/*
			 * if
			 * (!oldPwd.toLowerCase().equalsIgnoreCase(userMap.get("password")))
			 * { jsonMap.put("error", "4"); jsonMap.put("msg", "旧密码输入错误");
			 * JSONUtils.printObject(jsonMap); return null; }
			 */
			Long result = -1L;

			result = userService.updateLoginPwd(userId, password.toLowerCase());

			if (result > 0) {
				// 修改论坛的密码
				// bbsRegisterService.doUpdatePwdByAsynchronousMode(this.getUser()
				// .getUserName(), password, password, 2);
				response.success();
				JSONUtils.printObject(response);
				return null;
			} else {
				response.failure("修改失败");
				JSONUtils.printObject(response);
				return null;
			}
		} catch (Exception e) {
			response.failure("服务器出现异常");
			JSONUtils.printObject(response);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改交易密码初始化
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateDeal() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			// Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();

			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			if (userId == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			boolean re = userService.checkSign(userId);
			if (!re) {
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (userMap.get("isApplyPro").equals("1")) {
				jsonMap.put("error", "-4");
				jsonMap.put("msg", "请先设置安全问题");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			if (userMap.get("isApplyPro").equals("2")) {
				jsonMap.put("error", "-3");
				jsonMap.put("msg", "请先回答安全问题");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改交易密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateDealPwd() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> jsonMap = new HashMap<String, String>();
		Response response = new Response();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			// Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd");
			if (StringUtils.isBlank(password)) {
				// jsonMap.put("error", "1");
				// jsonMap.put("msg", "新密码不能为空");
				response.failure("新密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			password = com.shove.security.Encrypt.decrypt3DES(password,
					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}

			String oldPwd = appInfoMap.get("oldPwd");
			oldPwd = com.shove.security.Encrypt.decrypt3DES(oldPwd,
					IConstants.PASS_KEY);
			//
			// if (StringUtils.isBlank(oldPwd)) {
			// jsonMap.put("error", "2");
			// jsonMap.put("msg", "旧密码不能为空");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }
			//
			// if ("1".equals(IConstants.ENABLED_PASS)) {
			// oldPwd = com.shove.security.Encrypt.MD5(oldPwd.trim());
			// } else {
			// oldPwd = com.shove.security.Encrypt.MD5(oldPwd.trim()
			// + IConstants.PASS_KEY);
			// }

			long userId = Convert.strToLong(appInfoMap.get("uid"), -1L);
			if (userId == -1L) {
				// jsonMap.put("error", "3");
				// jsonMap.put("msg", "请登录后再操作");
				response.failure("请登录后再操作");
				JSONUtils.printObject(response);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userId);
			if (userMap == null || userMap.size() == 0) {
				// jsonMap.put("error", "3");
				response.failure("请登录后再操作");
				JSONUtils.printObject(response);
				return null;
			}

			boolean re = userService.checkSign(userId);
			if (!re) {
				// jsonMap.put("error", "11");
				// jsonMap.put("msg", "*修改失败！你的账号出现异常，请速与管理员联系！");
				response.failure("*修改失败！你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(response);
				return null;
			}
			// 设置交易密码
			Long result = -1L;
			/*
			 * if (Encrypt.MD5("-2").equalsIgnoreCase(oldPwd)) {
			 * 
			 * result = userService.updateDealPwd(userId, password); if (result
			 * > 0) { jsonMap.put("error", "-1"); jsonMap.put("msg", "设置成功");
			 * JSONUtils.printObject(jsonMap); } else { jsonMap.put("error",
			 * "5"); jsonMap.put("msg", "设置失败"); JSONUtils.printObject(jsonMap);
			 * } return null; }
			 */

			// if (!oldPwd.equalsIgnoreCase(userMap.get("dealpwd"))) {
			// jsonMap.put("error", "4");
			// jsonMap.put("msg", "输入旧密码错误");
			// JSONUtils.printObject(jsonMap);
			// return null;
			// }

			if (password.equals(userMap.get("password"))) {
				// jsonMap.put("error", "15");
				// jsonMap.put("msg", "交易密码不能和登录密码一样！");
				response.failure("*交易密码不能和登录密码一样！");
				JSONUtils.printObject(response);
				return null;
			}
			result = userService.updateDealPwd(userId, password);
			if (result > 0) {
				// jsonMap.put("error", "-1");
				// jsonMap.put("msg", "修改成功");
				response.success();
				JSONUtils.printObject(response);
				return null;
			} else {
				// jsonMap.put("error", "5");
				// jsonMap.put("msg", "修改失败");
				response.failure("*修改失败");
				JSONUtils.printObject(response);
				return null;
			}
		} catch (Exception e) {
			// jsonMap.put("error", "3");
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 忘记并重置交易密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String resetDealPwd() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			String password = appInfoMap.get("newPwd").toLowerCase();

			if (StringUtils.isBlank(password)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "新密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String cellPhone = appInfoMap.get("cellPhone");
			userService.queryUserIdByPhone(cellPhone);
			if (StringUtils.isBlank(cellPhone)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String code = appInfoMap.get("code");
			if (StringUtils.isBlank(code)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String randomCode = appInfoMap.get("randomCode");
			if (StringUtils.isBlank(randomCode)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "你还没有获取手机验证码");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			randomCode = Encrypt.decryptSES(randomCode, AlipayConfig.ses_key);
			// String phone = session().getAttribute("phone") +"";
			if (!randomCode.equals(code)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			String recivePhone = appInfoMap.get("recivePhone");
			if (StringUtils.isBlank(recivePhone)) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", "接收验证码手机号不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			recivePhone = Encrypt.decryptSES(recivePhone, AlipayConfig.ses_key);
			if (StringUtils.isNotBlank(cellPhone)
					&& !cellPhone.equals(recivePhone)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "手机号跟接收验证码手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long userId = userService.queryUserIdByPhone(cellPhone);

			if (userId == -1) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "手机号与绑定的手机号不一致");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			Long uId = userService.queryUserIdByPhone(cellPhone);

			long userid = Convert.strToLong(authMap.get("uid"), -1L);
			if (userid == -1L) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> userMap = userService.queryUserById(userid);
			if (userMap == null || userMap.size() == 0) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "请登录后再操作");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (uId != userid) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "输入手机号错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			password = com.shove.security.Encrypt.decrypt3DES(password,
					IConstants.PASS_KEY);
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			Long result1 = userService.updateDealPwd(userid, password);
			if (result1 > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "修改成功");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "修改失败");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 审核基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateUserBaseDataCheck() throws SQLException, IOException,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long personId = -1L;
			int auditStatus = 1;// 默认不通过审核
			long userId = Convert.strToLong(authMap.get("uid"), -1l);
			long flag = -1L;
			if (StringUtils.isNotBlank(appInfoMap.get("flag"))) {
				flag = Long.parseLong(appInfoMap.get("flag"));
			}
			if (flag == 3) {
				auditStatus = 3;// 通过审核
			} else if (flag == 2) {
				auditStatus = 2;// 审核不通过
			} else {
				auditStatus = 1;// 等待审核
			}
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			long adminId = admin.getId();
			if (admin != null) {
				personId = userService.updateUserBaseDataCheck(userId,
						auditStatus, adminId);
			}
			// 测试---跳过
			if (IConstants.ISDEMO.equals("1")) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (personId > 0) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "保存成功");
				JSONUtils.printObject(jsonMap);
				return null;
				// 成功
			} else {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "保存失败");
				// 失败
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加认证图片
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addImage() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> Apcmap = null;// 五大基本资料的计数存放map
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long materAuthTypeId = Convert.strToLong(
					appInfoMap.get("materAuthTypeId").toString(), -1L);
			String imgPath = appInfoMap.get("userHeadImg");

			if (StringUtils.isBlank(imgPath)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "上传失败，请上传认证资料");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			long imageId = -1L;
			long userId = Convert.strToLong(authMap.get("uid"), -1l);
			User user = userService.jumpToWorkData(userId);
			// 认证状态
			if (null != user) {
				userId = user.getId();

				imageId = userService
						.addImage(materAuthTypeId, imgPath, userId);
				String id = userService.queryPitcturId(userId, materAuthTypeId)
						.get("id");
				String msg = addpastPicturdate(Convert.strToLong(id, -1l),
						materAuthTypeId, user, imgPath);
				if (msg == null) {
					jsonMap.put("error", "-1");// 不给跳转
					jsonMap.put("msg", "保存成功");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				jsonMap.put("error", "11");// 不给跳转
				jsonMap.put("msg", msg);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (imageId < 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "保存失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				Integer allcount = 0;
				Apcmap = userService.queryPicturStatuCount(userId);
				if (Apcmap != null && Apcmap.size() > 0) {
					allcount = Convert.strToInt(Apcmap.get("ccc"), 0);
				}
				if (allcount != 0 && allcount >= 5) {
					jsonMap.put("error", "3");
					jsonMap.put("msg", "请先填写基本信息和工作信息");
				} else {
					jsonMap.put("error", "-1");// 不给跳转
					jsonMap.put("msg", "成功");
				}
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	// 用户提交图片审核
	public String addpastPicturdate(Long tmid, Long materAuthTypeId, User user,
			String imgPath) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		// Map<String, String> jsonMap = new HashMap<String, String>();
		// ------add by houli
		// String btype = request("btype");
		// -----------
		List<Map<String, Object>> userPicDate = null;
		// Map<String, String> typmap = null;
		long userId = user.getId();
		int len = 1;// 上传图片个数
		if (tmid > 0) {
			request().setAttribute("tmid", tmid);
			userPicDate = userService.queryPerTyhpePicture(tmid);
			len = userPicDate.size() + 1;
		}

		Integer Listlen = 0;// 数据库的图片个数
		// Long tmids = Convert.strToLong(appInfoMap.get("tmidStr"), -1L);
		Long result = -1L;
		if (Listlen == -1) {
			return "没有图片";
		}
		if (len == -1) {
			return "没有图片";
		}
		Integer allPicturecount = len + Listlen;// 用户将要上传的图片和数据库图片的个数的总和
		if (materAuthTypeId == 1) {
			if (5 < allPicturecount) {
				return "身份证审核图片最多5张";
			}// 身份证
		}
		if (materAuthTypeId == 2) {
			if (10 < allPicturecount) {
				return "工作认证审核图片最多10张";
			}// 工作认证
		}
		if (materAuthTypeId == 3) {
			if (5 < allPicturecount) {
				return "居住认证审核图片最多5张";
			}// 居住认证
		}

		if (materAuthTypeId == 4) {
			if (30 < allPicturecount) {
				return "收入认证审核图片最多5张";
			}// 收入认证
		}
		if (materAuthTypeId == 5) {
			if (10 < allPicturecount) {
				return "信用报告审核图片最多10张";
			}// 信用报告
		}
		if (materAuthTypeId == 6) {
			if (10 < allPicturecount) {
				return "房产证审核图片最多10张";
			}// 房产
		}
		if (materAuthTypeId == 7) {
			if (10 < allPicturecount) {
				return "购车证审核图片最多10张";
			}// 购车
		}
		if (materAuthTypeId == 8) {
			if (5 < allPicturecount) {
				return "结婚证审核图片最多5张";
			}// 结婚
		}
		if (materAuthTypeId == 9) {
			if (5 < allPicturecount) {
				return "学历认证审核图片最多5张";
			}// 学历
		}
		if (materAuthTypeId == 10) {
			if (5 < allPicturecount) {
				return "技术认证审核图片最多5张";
			}// 技术
		}
		if (materAuthTypeId == 11) {
			if (5 < allPicturecount) {
				return "手机认证审核图片最多5张";
			}// 手机
		}
		if (materAuthTypeId == 12) {
			if (5 < allPicturecount) {
				return "微博认证审核图片最多5张";
			}// 微博
		}
		/*
		 * if(materAuthTypeId==13){ //视频
		 * if(5<allPicturecount){JSONUtils.printStr("13");return null;}//视频 }
		 */

		if (materAuthTypeId == 13) {
			if (10 < allPicturecount) {
				return "现场认证审核图片最多10张";
			}// 现场认证
		}
		if (materAuthTypeId == 14) {
			if (10 < allPicturecount) {
				return "抵押认证审核图片最多10张";
			}// 抵押认证
		}

		if (materAuthTypeId == 15) {
			if (10 < allPicturecount) {
				return "机构担保审核图片最多10张";
			}// 机构担保
		}

		if (materAuthTypeId == 16) {
			if (30 < allPicturecount) {
				return "其他资料审核图片最多30张";
			}// 其他资料
		}

		List<Long> lists = new ArrayList<Long>();// 已经上传的图片设置他们的可见性
		if (Listlen != -1 && user != null) {
			for (int i = 1; i <= Listlen; i++) {
				if (Convert.strToInt(paramMap.get("id" + i), -1) != -1) {
					lists.add(Convert.strToLong(paramMap.get("id" + i), -1));
				}
			}
		}

		List<String> imglistsy = new ArrayList<String>();
		List<String> imgListsn = new ArrayList<String>();
		if (len != -1 && user != null) {
			for (int i = 1; i <= len; i++) {// 将要上传图片图片先保存在一个数组里面
				imgListsn.add(imgPath);
				break;
			}
		}
		// Map<String, String> authMap = this.getAppAuthMap();
		// long userId = Convert.strToLong(authMap.get("uid"), -1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String uploadingTime = format.format(new Date());// 当前时间上传图片时间
		if (user != null && tmid != -1L && materAuthTypeId != -1L) {
			result = userService.addUserImage(1, uploadingTime, lists,
					imglistsy, imgListsn, tmid, userId, materAuthTypeId, -1l,
					len);// 遍历将image查到数据库中 1 表示向t_materialsauth 插入图片类型 表示等待审核
			if (result > 0) {
				// 更新User的状态
				try {

					Map<String, String> newstatusmap = null;
					newstatusmap = userService.querynewStatus(userId);// 查询放到session中去
					if (newstatusmap != null && newstatusmap.size() > 0) {
						user.setAuthStep(Convert.strToInt(
								newstatusmap.get("authStep"), -1));

						user.setEmail(Convert.strToStr(
								newstatusmap.get("email"), null));
						user.setPassword(Convert.strToStr(
								newstatusmap.get("password"), null));
						user.setId(Convert.strToLong(newstatusmap.get("id"),
								-1L));
						user.setRealName(Convert.strToStr(
								newstatusmap.get("realName"), null));
						user.setKefuname(Convert.strToStr(
								newstatusmap.get("kefuname"), null));
						user.setUserName(Convert.strToStr(
								newstatusmap.get("username"), null));
						user.setVipStatus(Convert.strToInt(
								newstatusmap.get("vipStatus"), -1));
						user.setKefuid(Convert.strToInt(
								newstatusmap.get("tukid"), -1));
						// session().setAttribute("user",
						// user);//跟新session中的user
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return "失败";
			}

		}
		return null;
	}

	/**
	 * 跳转到上传页面
	 * 
	 * @throws Exception
	 */
	public String jumpPassDatapage() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Map<String, Object>> basepictur = null;
		List<Map<String, Object>> selectpictur = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {

			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1L);
			User user = userService.jumpToWorkData(userId);
			String from = appInfoMap.get("from");
			String btype = appInfoMap.get("btype");
			// -------------
			if (user != null) {
				if (from == null || from.equals("")) {
					// 获取用户认证进行的步骤
					if (user.getAuthStep() == 1) {
						// 个人信息认证步骤
						jsonMap.put("error", "1");
						jsonMap.put("msg", "请先填写个人信息");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (user.getAuthStep() == 2) {
						// 工作信息认证步骤
						jsonMap.put("error", "2");
						jsonMap.put("msg", "请先填写工作信息");
						JSONUtils.printObject(jsonMap);
						return null;
					} else if (user.getAuthStep() == 3) {
						// VIP申请认证步骤
						jsonMap.put("error", "3");
						jsonMap.put("msg", "请先成为VIP");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					// ---------add by houli
					else if (user.getAuthStep() == 5
							&& (btype != null && !btype.equals(""))) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						JSONUtils.printObject(jsonMap);
						return null;
					}
				} else {// 净值借款跟秒还借款操作步骤
					// 获取用户认证进行的步骤
					if (user.getAuthStep() == 1) {
						// 个人信息认证步骤
						jsonMap.put("error", "1");
						jsonMap.put("msg", "请先填写个人信息");
						JSONUtils.printObject(jsonMap);
						return null;
					}

					if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
						jsonMap.put("error", "2");
						jsonMap.put("msg", "请先成为VIP");
						JSONUtils.printObject(jsonMap);
						return null;
					}

					// -------add by houli
					// return jumpToBorrow(btype);
					if (IConstants.BORROW_TYPE_NET_VALUE.equals(btype)) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						return null;
					} else if (IConstants.BORROW_TYPE_SECONDS.equals(btype)) {
						jsonMap.put("error", "-1");
						jsonMap.put("msg", "成功");
						JSONUtils.printObject(jsonMap);
						return null;
					}
					// -----------------
				}

				basepictur = userService.queryBasePicture(userId);// 五大基本
				selectpictur = userService.querySelectPicture(userId);// 可选
				jsonMap.put("basepictur", basepictur);
				jsonMap.put("selectpictur", selectpictur);
				jsonMap.put("error", "-1");// 不给跳转
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 所有密码安全提问的内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAllQuestionList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<Map<String, Object>> list = userService.queryAllQuestionList();
		if (null != list) {
			jsonMap.put("list", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "获取所有密保提问成功");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		jsonMap.put("error", "1");
		jsonMap.put("msg", "获取所有密保提问失败");
		JSONUtils.printObject(jsonMap);
		return null;
	}

	/**
	 * 检查用户回答密保问题是否正确
	 * 
	 * @return
	 * @throws Exception
	 */
	public String checkUserAnswer() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1L);
		String answerOne = appInfoMap.get("answerOne");
		String answerTwo = appInfoMap.get("answerTwo");
		String answerThree = appInfoMap.get("answerThree");
		Map<String, String> map = homeInfoSettingService
				.queryUserALLAnswer(userId);
		if (null != map) {
			if (!answerOne.equals(map.get("answerOne"))) {
				jsonMap.put("error", "1");// 问题一回答不正确
				jsonMap.put("msg", "问题一回答不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!answerTwo.equals(map.get("answerTwo"))) {
				jsonMap.put("error", "2");// 问题二回答不正确
				jsonMap.put("msg", "问题二回答不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!answerThree.equals(map.get("answerThree"))) {
				jsonMap.put("error", "3");// 问题三回答不正确
				jsonMap.put("msg", "问题三回答不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			jsonMap.put("error", "-1");// 问题回答都正确
			jsonMap.put("msg", "问题回答都正确");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		return null;
	}

	/**
	 * 获取用户设置的密保问题
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserQuestion() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1L);
		int isApplyPro = Convert.strToInt(userService.queryUserById(userId)
				.get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);

		try {
			Map<String, String> map = homeInfoSettingService
					.queryUserALLAnswer(userId);
			if (null != map) {

				jsonMap.put("questionOne", map.get("questionOne"));
				jsonMap.put("questionTwo", map.get("questionTwo"));
				jsonMap.put("questionThree", map.get("questionThree"));
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "获取成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 保存或修改用户密保答案及提问
	 * 
	 * @return
	 * @throws Exception
	 */

	public String saveOrUpdatePwdAnswer() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> appInfoMap = getAppInfoMap();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1L);

		int isApplyPro = Convert.strToInt(userService.queryUserById(userId)
				.get("isApplyPro"), 1);

		String questionOne = appInfoMap.get("questionOne");
		String questionTwo = appInfoMap.get("questionTwo");
		String questionThree = appInfoMap.get("questionThree");
		String answerOne = appInfoMap.get("answerOne");
		String answerTwo = appInfoMap.get("answerTwo");
		String answerThree = appInfoMap.get("answerThree");
		long result = -1;
		// 判定用户是否已经申请密保，没有申请就表示保存，有申请就表示修改
		if (isApplyPro == 1) {
			result = homeInfoSettingService.savePwdAnswer(userId, questionOne,
					questionTwo, questionThree, answerOne, answerTwo,
					answerThree);
			if (result < 0) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "设置密保失败");
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				userService.updatePwdProState(userId);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "设置密保成功");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		}
		result = homeInfoSettingService.updatePwdAnswer(userId, questionOne,
				questionTwo, questionThree, answerOne, answerTwo, answerThree);
		if (result < 0) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "修改密保失败");
			JSONUtils.printObject(jsonMap);
			return null;
		} else {
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "修改密保成功");
			JSONUtils.printObject(jsonMap);
			return null;
		}
	}

	/**
	 * 查询数据
	 * 
	 * @return
	 * @throws Exception
	 */

	public String getDbData() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String table = appInfoMap.get("table");// 表名
			String field = appInfoMap.get("field");// 字段名
			String order = appInfoMap.get("order");// 排序
			String condition = appInfoMap.get("condition");// 条件
			long id = Convert.strToLong(appInfoMap.get("userId"), -1);
			int pageSize = Convert.strToInt(appInfoMap.get("pageSize"), 1);// 每页条数
			int pageNo = Convert.strToInt(appInfoMap.get("pageNo"), 1);// 当前页

			System.out.println("SQL:select " + field + " from " + table
					+ " where 1 = 1  " + condition + order + "");
			List<Map<String, Object>> dbList = userService.getDbData(table,
					field, order, Utility.filteSqlInfusion(condition),
					pageSize, pageNo);
			// 查询用户的总金额
			Map<String, String> userMap = new HashMap<String, String>();
			if (dbList == null || dbList.size() == 0) {
				if (userMap != null) {
					// 投资融资情况
					Map<String, String> accmountStatisMap = myHomeService
							.queryAccountStatisInfo(id);
					userMap.putAll(accmountStatisMap);
					// 取总投资，总借款
					Map<String, String> m = financeService
							.queryInvestAmount(id);
					userMap.putAll(m);
					jsonMap.put("userMap", userMap);
				} else {
					jsonMap.put("error", "1");
					jsonMap.put("msg", "查询结果为空");
				}
			} else {
				// 投资融资情况
				Map<String, String> accmountStatisMap = myHomeService
						.queryAccountStatisInfo(id);
				userMap.putAll(accmountStatisMap);
				// 取总投资，总借款
				Map<String, String> m = financeService.queryInvestAmount(id);
				userMap.putAll(m);
				jsonMap.put("userMap", userMap);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "操作成功");
				jsonMap.put("dbList", dbList);
			}
		} catch (Exception e) {
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			log.error(e);
			e.printStackTrace();
		}
		JSONUtils.printObject(jsonMap);
		return null;
	}

	/**
	 * 移动端下载 downApp
	 * 
	 * @auth hejiahua
	 * @return String
	 * @exception
	 * @date:2014-11-5 上午11:20:35
	 * @since 1.0.0
	 */
	public String downApp() {
		int type = request.getInt("type", 2);
		java.io.File file = null;
		;
		if (type == 1) {
			// IOS尚无
		} else {
			file = new java.io.File("/upload/app/sanhaodai.apk");
		}
		try {
			// 文件名
			String filename = file.getName();
			// 设置response的Header
			response().addHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes()));
			response().addHeader("Content-Length", "" + file.length());
			response().setContentType("application/octet-stream");
			PrintWriter out = response().getWriter();
			out.print(file);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 需要登录的短信接口
	 * 
	 * @return
	 * @throws IOException
	 */
	public String sendSmsNeedLogin() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
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

			if (StringUtils.isBlank(mobile_tel)
					|| !com.shove.util.StringUtils.matchPhone(mobile_tel)) {
				response.failure("手机号码不正确");
				JSONUtils.printObject(response);
				return null;
			}

			String code_type = appInfoMap.get("code_type");
			String typeStr = "";
			if ("1".equals(code_type)) {
				typeStr = "提现";
			} else if ("2".equals(code_type)) {
				typeStr = "绑定新手机号码";
				mobile_tel = appInfoMap.get("new_mobile");
				if (StringUtils.isBlank(mobile_tel)
						|| !com.shove.util.StringUtils.matchPhone(mobile_tel)) {
					response.failure("新手机号码不正确");
					JSONUtils.printObject(response);
					return null;
				}

				if (getUserId(mobile_tel, userService) > 0) {
					response.failure("此手机号码已被注册");
					JSONUtils.printObject(response);
					return null;
				}
			} else if ("3".equals(code_type)) {
				typeStr = "找回登录密码";
			} else if ("4".equals(code_type)) {
				typeStr = "找回交易密码";
			} else if ("5".equals(code_type)) {
				typeStr = "三好资本充值";
			}else {
				typeStr = "短信发送";
			}
			String vioce = appInfoMap.get("vioce");
			// 随机产生4位数字
			String randomCode = RandomStringUtils.randomNumeric(4);
			log.info("=====:" + randomCode);

			// 发送短信
			String content = "尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME
					+ ",手机验证码为:";
			// //发送短信
			if (StringUtils.isBlank(vioce)) {// 短信
				String retCode = SMSUtil.sendSMS("", "", content, mobile_tel,
						randomCode);
				if ("Sucess".equals(retCode)) {
					log.info(randomCode);
					appInfoMap.clear();
					appInfoMap.put("md5_code",
							Encrypt.MD5(mobile_tel + randomCode));
					response.success(appInfoMap);
					JSONUtils.printObject(response);
					// 添加短信记录
					userService.addSendLog(mobile_tel, typeStr + ",短信发送", 180L,
							randomCode);
					JSONUtils.printObject(response);
					return null;
				} else if ("3".equals(retCode)) {
					appInfoMap.clear();
					appInfoMap.put("md5_code",
							Encrypt.MD5(mobile_tel + randomCode));
					response.success(appInfoMap, "发送成功");
					JSONUtils.printObject(response);
					// 添加短信记录
					userService.addSendLog(mobile_tel, content + ",短信发送", 180L,
							randomCode);
					return null;
				} else {
					response.failure("发送失败");
					return null;
				}
			} else {// 语音
				if (RestUtil.voiceCode(randomCode, mobile_tel)) {
					log.info(randomCode);
					appInfoMap.clear();
					appInfoMap.put("md5_code",
							Encrypt.MD5(randomCode + mobile_tel));
					response.success(appInfoMap);
					JSONUtils.printObject(response);
					// 添加短信记录
					userService.addSendLog(mobile_tel, typeStr + ",语音发送", 180L,
							randomCode);
					JSONUtils.printObject(response);
					return null;
				} else {
					response.failure("发送失败");
					JSONUtils.printObject(response);
					return null;
				}
			}

		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/**
	 * 修改app版本号标准初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchAppVersionInit() throws Exception {
		Map<String, String> appMap = new HashMap<String, String>();
		Response response = new Response();
		try {
			String content = PublicFunction.GetOption("app_upcontent");
			// 是否强制更新
			String appForce = PublicFunction.GetOption("app_force");
			String curNum = Convert.strToStr(
					PublicFunction.GetOption("app_version"), "");
			String url = PublicFunction.GetOption("app_url");
			String app_version_name = PublicFunction.GetOption("app_version_name");
			appMap.put("app_upcontent", content);//更新内容
			appMap.put("app_version", curNum);//版本
			appMap.put("app_force", appForce);//是否强制更新
			appMap.put("app_url", url);
			appMap.put("app_version_name", app_version_name);
			response.success(appMap);
			JSONUtils.printObject(response);
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 绑定新手机
	 * 
	 * @throws IOException
	 */
	public String bind_mobile() throws IOException {
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
			String new_mobile = appInfoMap.get("new_mobile");
			String identify_code = appInfoMap.get("identify_code");
			String business_pwd = appInfoMap.get("business_pwd");

			if (StringUtils.isBlank(new_mobile)
					|| StringUtils.isBlank(identify_code)
					|| StringUtils.isBlank(business_pwd)) {
				response.failure("缺少必需参数");
				JSONUtils.printObject(response);
				return null;
			}

			if (!com.shove.util.StringUtils.matchPhone(new_mobile)) {
				response.failure("手机号码不正确");
				JSONUtils.printObject(response);
				return null;
			}
			String md5_code = appInfoMap.get("md5_code");
			String md5 = Encrypt.MD5(new_mobile + identify_code);
			// String phone = session().getAttribute("phone") +"";
			if (!md5.equals(md5_code)) {
				response.failure("验证码不正确");
				JSONUtils.printObject(response);
				return null;
			}

			// 过期数据
			boolean timeFlag = userService.querySendTime(new_mobile,
					identify_code);
			if (timeFlag) {
				response.failure("验证码无效");
				JSONUtils.printObject(response);
				return null;
			}

			// 查看变更的手机号码是否别人绑定了
			Map<String, String> map = homeInfoSettingService
					.queryBindingMobile(new_mobile);
			//
			if (map != null) {
				response.failure("手机号码已被使用");
				JSONUtils.printObject(response);
				return null;
			}

			// 验证交易密码
			if ("1".equals(IConstants.ENABLED_PASS)) {
				business_pwd = com.shove.security.Encrypt.MD5(business_pwd
						.trim());
			} else {
				business_pwd = com.shove.security.Encrypt.MD5(business_pwd
						.trim() + IConstants.PASS_KEY);
			}

			User user = userService.queryUserDetailById(userId);

			// 如果交易密码不正确，则不准提交
			if (!business_pwd.equals(user.getDealpwd())) {
				response.failure("交易密码不正确");
				JSONUtils.printObject(response);
				return null;
			}

			// 进行手机变更（状态为审核通过）
			Long result = homeInfoSettingService.addBindingMobile(new_mobile,
					userId, IConstants.PHONE_BINDING_CHECK, business_pwd,
					mobile_tel);
			// 清空验证码
			userService.overSendLog(new_mobile, "", identify_code);
			if (result < 0) {// 手机变更失败
				response.failure("更改失败");
				JSONUtils.printObject(response);
				return null;
			}
			// 修改手机号码，UserType=1是个人用户，2是企业用户
			userService.updateSmsByid(userId, new_mobile, user.getUserType());

			operationLogService.addOperationLog("t_phone_binding_info",
					user.getUserName(), IConstants.INSERT, user.getLastIP(), 0,
					"发布手机变更请求", 1);
			response.success();
			JSONUtils.printObject(response);
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/*
	 * 修改登录密码
	 */
	public String revise_login_pwd() throws IOException {
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

			String user_password = Convert.strToStr(
					appInfoMap.get("user_password"), null);
			String new_user_password = Convert.strToStr(
					appInfoMap.get("new_user_password"), null);
			if (StringUtils.isBlank(user_password)
					|| StringUtils.isBlank(new_user_password)) {
				response.failure("缺少必需参数");
				JSONUtils.printObject(response);
				return null;
			}

			String temp_new_user_password = "";
			if ("1".equals(IConstants.ENABLED_PASS)) {
				user_password = com.shove.security.Encrypt.MD5(user_password);
				temp_new_user_password = com.shove.security.Encrypt.MD5(new_user_password);
			} else {
				user_password = com.shove.security.Encrypt.MD5(user_password
						+ IConstants.PASS_KEY);
				temp_new_user_password= com.shove.security.Encrypt.MD5(new_user_password
						+ IConstants.PASS_KEY);
			}
			
			


			Long id = getUserId(mobile_tel, userService);// 获得用户编号
			User user = userService.queryUserDetailById(userId);
			Map<String, String> map = homeInfoSettingService.getDealPwd(id);
			boolean re = userService.checkSign(id);
			if (!re) {
				response.failure("账号异常");
				JSONUtils.printObject(response);// 账号异常
				return null;
			}
			String password = user.getPassword();
			
			if (!user_password.endsWith(password)) {// 旧密码输入错误
				response.failure("旧密码输入错误");
				JSONUtils.printObject(response);
				return null;
			}
			

			if (user_password.equals(temp_new_user_password)) {
				response.failure("新密码不能与旧密码相同");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (new_user_password.equals(map.get("dealpwd"))) {
				response.failure("新密码不能和交易密码相同");
				JSONUtils.printObject(response);
				// JSONUtils.printStr("6");// 登录密码不能和交易密码一样
				return null;
			}
			
			
			homeInfoSettingService.updateUserPassword(id, new_user_password,
					"login");
			response.success();
			JSONUtils.printObject(response);
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/*
	 * 修改交易密码
	 */
	public String revise_business_pwd() throws IOException {
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

			String user_password = Convert.strToStr(
					appInfoMap.get("business_pwd"), null);
			String new_user_password = Convert.strToStr(
					appInfoMap.get("new_business_pwd"), null);
			if (StringUtils.isBlank(user_password)
					|| StringUtils.isBlank(new_user_password)) {
				response.failure("缺少必需参数");
				JSONUtils.printObject(response);
				return null;
			}

			if (user_password.equals(new_user_password)) {
				response.failure("新密码不能与旧密码相同");
				JSONUtils.printObject(response);
				return null;
			}

			Long id = getUserId(mobile_tel, userService);// 获得用户编号
			User user = userService.queryUserDetailById(userId);
			Map<String, String> map = homeInfoSettingService.getDealPwd(id);
			boolean re = userService.checkSign(id);
			if (!re) {
				response.failure("账号异常");
				JSONUtils.printObject(response);// 账号异常
				return null;
			}
			String password = map.get("dealpwd");// 交易密码默认为登录密码
			if (password == null || password.equals("")) {
				password = user.getPassword();
			}
			if (com.shove.security.Encrypt.MD5(
					new_user_password + IConstants.PASS_KEY).equals(
					user.getPassword())) {
				response.failure("交易密码不能和登录密码相同");
				JSONUtils.printObject(response);
				return null;
			}

			if ("1".equals(IConstants.ENABLED_PASS)) {
				user_password = com.shove.security.Encrypt.MD5(user_password);
			} else {
				user_password = com.shove.security.Encrypt.MD5(user_password
						+ IConstants.PASS_KEY);
			}
			if (!user_password.endsWith(password)) {// 旧密码输入错误
				response.failure("旧密码输入错误");
				JSONUtils.printObject(response);
				return null;
			}

			homeInfoSettingService.updateUserPassword(id, new_user_password,
					"deal");
			response.success();
			JSONUtils.printObject(response);
		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/*
	 * 找回交易密码
	 */
	public String find_business_pwd() throws IOException {
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

			String identify_code = Convert.strToStr(
					appInfoMap.get("identify_code"), null);
			String business_pwd = Convert.strToStr(
					appInfoMap.get("business_pwd"), null);
			if (StringUtils.isBlank(identify_code)
					|| StringUtils.isBlank(business_pwd)) {
				response.failure("缺少必需参数");
				JSONUtils.printObject(response);
				return null;
			}

			String code = appInfoMap.get("identify_code");
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

			Long id = getUserId(mobile_tel, userService);// 获得用户编号
			User user = userService.queryUserDetailById(userId);
			Map<String, String> map = homeInfoSettingService.getDealPwd(id);
			boolean re = userService.checkSign(id);
			if (!re) {
				response.failure("账号异常");
				JSONUtils.printObject(response);// 账号异常
				return null;
			}
			String password = map.get("dealpwd");// 交易密码默认为登录密码
			if (password == null || password.equals("")) {
				password = user.getPassword();
			}
			if (com.shove.security.Encrypt.MD5(
					business_pwd + IConstants.PASS_KEY).equals(
					user.getPassword())) {
				response.failure("交易密码不能和登录密码一样");
				JSONUtils.printObject(response);
				// 交易密码不能和登录密码一样
				return null;
			}

			homeInfoSettingService.updateUserPassword(id, business_pwd, "deal");

			// 清空验证码
			userService.overSendLog(mobile_tel, "", identify_code);

			response.success();
			JSONUtils.printObject(response);
		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/**
	 * 设置交易密码
	 * 
	 * @return
	 * @throws IOException
	 */
	public String set_business_pwd() throws IOException {
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
			String business_pwd = appInfoMap.get("business_pwd");

			if (StringUtils.isBlank(business_pwd)) {
				response.failure("交易密码不能为空");
				JSONUtils.printObject(response);
				return null;
			}

			User user = userService.queryUserDetailById(userId);
			boolean re = userService.checkSign(userId);
			if (!re) {
				response.failure("账号异常");
				JSONUtils.printObject(response);// 账号异常
				return null;
			}

			if (com.shove.security.Encrypt.MD5(
					business_pwd + IConstants.PASS_KEY).equals(
					user.getPassword())) {
				response.failure("交易密码不能和登录密码一样");
				JSONUtils.printObject(response);
				// 交易密码不能和登录密码一样
				return null;
			}

			homeInfoSettingService.updateUserPassword(userId, business_pwd,
					"deal");
			response.success();
			JSONUtils.printObject(response);
		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/**
	 * 上传头像
	 * 
	 * @return
	 * @throws Exception
	 */
	public String homeupload() throws Exception {
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
			String user_head = appInfoMap.get("user_head");

			byte[] bytes = Base64Utils.decode(user_head);

			String realPath = "";
			String fileName = "";
			realPath = ServletUtils.serverRootDirectory() + "/upload/touxiang/";
			String getExt = appInfoMap.get("head_img_type");
			if ("1".equals(getExt)) {
				getExt = "GIF";
			} else if ("2".equals(getExt)) {
				getExt = "JPG";
			} else if ("5".equals(getExt)) {
				getExt = "BMP";
			} else if ("4".equals(getExt)) {
				getExt = "PNG";
			} else {
				getExt = "JPEG";
			}
			// String
			// path=ServletActionContext.getServletContext().getRealPath("/testlw");
			String timetemp = DateUtil.YYYYMMDDMMHHSSSSS.format(new Date());
			File homefile = new File(new File(realPath), timetemp);
			byte2File(bytes, homefile);

			if (!homefile.getParentFile().exists()) {
				homefile.getParentFile().mkdirs();
			}

			if (!"GIF,JPG,JPEG,PNG,BMP".contains(getExt.toUpperCase())) {
				// return "文件类型不对!";
				response.failure("文件类型不正确");
				JSONUtils.printObject(response);
				return null;
			}

			/*
			 * String filetype2 = VerifyTruePicture.getFileByFile(homefile,
			 * "img");
			 * 
			 * if (filetype2 == null) { // return "上传文件类型错误!";
			 * response.failure("文件类型不正确"); JSONUtils.printObject(response);
			 * return null; }
			 */

			// 限制文件大小
			long getFileSize = 1 * 700 * 1024;
			if (homefile.length() > getFileSize) {
				// return "文件超过上传700KB限制!";
				response.failure("文件超过上传700KB限制");
				JSONUtils.printObject(response);
				return null;
			}
			fileName = com.shove.web.util.FileUtils.getFileName() + "."
					+ getExt;
			try {
				// 上传文件
				UploadUtil.uploadByFile(homefile, realPath, fileName);
			} catch (Exception e) {
				// return "上传路径不存在!";
				response.failure("上传路径不存在");
				JSONUtils.printObject(response);
				return null;
			}

			/**
			 * 同步上传头像到数据库中
			 */
			String realServicePath = "upload/touxiang/" + fileName;
			try {
				homeInfoSettingService.updatePersonImg(realServicePath, userId);
				response.success();
				JSONUtils.printObject(response);
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				response.failure("服务器异常");
				JSONUtils.printObject(response);
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	public static void byte2File(byte[] buf, File file) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setSMSInterfaceService(SMSInterfaceService interfaceService) {
		sMSInterfaceService = interfaceService;
	}

}
