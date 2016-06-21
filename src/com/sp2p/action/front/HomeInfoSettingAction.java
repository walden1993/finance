package com.sp2p.action.front;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONArray;

import cn.com.nciic.www.SimpleCheckByJson;
import cn.com.nciic.www.SimpleCheckByJsonResponse;
import cn.com.nciic.www.service.IdentifierServiceStub;

import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.pay.llpay.client.utils.Base64;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.BeVipService;
import com.sp2p.service.BecomeToFinanceService;
import com.sp2p.service.HSFundService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.OperationLogService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.SendMailService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.service.admin.UserAdminService;
import com.sp2p.util.WebUtil;

/**
 * 我的帐户 个人设置
 * 
 * @author Administrator
 * 
 */
public class HomeInfoSettingAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;

	private HomeInfoSettingService homeInfoSettingService;
	private UserService userService;
	private UserAdminService userAdminService;
	private BecomeToFinanceService becomeFinanceService;
	private AdminService adminService;
	private FundManagementService fundManagementService;
	private SysparService sysparService;
	private BeVipService beVipService;
	private BBSRegisterService bbsRegisterService;
	private SendMailService sendMailService;
	private OperationLogService operationLogService;
	private RechargeService rechargeService;
	
	
	
	public void setSysparService(SysparService sysparService) {
        this.sysparService = sysparService;
    }

    public RechargeService getRechargeService() {
		return rechargeService;
	}
	
	public void setUserAdminService(UserAdminService userAdminService) {
		this.userAdminService = userAdminService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}

	public String homeInfoSettingInit() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		// 加载用户真实姓名
		request().setAttribute("realName", user.getRealName());
		return SUCCESS;
	}

	/**
	 * @throws Exception
	 * @MethodName: renewalVIPInit
	 * @Param: HomeInfoSettingAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:37:37
	 * @Return:
	 * @Descb: 会员续费初始化
	 * @Throws:
	 */
	public String renewalVIPInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		try {
			Map<String, String> renewalVIPMap = homeInfoSettingService
					.queryRenewalVIP(user.getId());
			request().setAttribute("renewalVIPMap", renewalVIPMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: renewalVIPSubmit
	 * @Param: HomeInfoSettingAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午10:51:50
	 * @Return:
	 * @Descb: 提交会员续费
	 * @Throws:
	 */
	public String renewalVIPSubmit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("code_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		if (!code.equals(_code)) {
			obj.put("msg", "验证码错误");
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> renewalVIPMap = homeInfoSettingService
				.renewalVIPSubmit(user.getId(), getPlatformCost());
		String result = renewalVIPMap.get("result") == null ? ""
				: renewalVIPMap.get("result");
		userService.updateSign(user.getId());//更换校验码
		// 续费成功
		if ("1".equals(result)) {
			user.setVipStatus(IConstants.VIP_STATUS);
			session().setAttribute(IConstants.SESSION_USER, user);
			obj.put("msg", "VIP续费成功");
			JSONUtils.printObject(obj);
			return null;
		}
		obj.put("msg", result);
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * 修改个人头像的时候判断是否填写过个人信息
	 * 
	 * @return
	 */
	public String queryHeadImg() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		if (user.getRealName() == null || "".equals(user.getRealName())) {
			JSONUtils.printStr("1");
			return null;
		}
		Map<String, String> map = homeInfoSettingService.queryHeadImg(user
				.getRealName());
		if (map != null) {
			JSONUtils.printStr("2");
			return null;
		}
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: updatePersonImg
	 * @Param: HomeInfoSettingAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:08:12
	 * @Return:
	 * @Descb: 修改个人头像
	 * @Throws:
	 */
	public String updatePersonImg() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
//		String imgPath = paramMap.get("imgPath");
		String imgPath = request.getString("imgPath");
		JSONObject obj = new JSONObject();
		long returnId = -1;
		try {
			returnId = homeInfoSettingService.updatePersonImg(imgPath, user
					.getId());

			if (returnId <= 0) {
				obj.put("msg", IConstants.ACTION_FAILURE);
			} else {
				obj.put("msg", IConstants.ACTION_SUCCESS);
			}
			user.setPersonalHead(imgPath);
			session().setAttribute(IConstants.SESSION_USER, user);
			JSONUtils.printObject(obj);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 修改用户登录密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateLoginPass() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String oldPass = Convert.strToStr(paramMap.get("oldPassword"), null);
		String newPass = Convert.strToStr(paramMap.get("newPassword"), null);
		String confirmPass = Convert.strToStr(paramMap.get("confirmPassword"),
				null);
		String type = Convert.strToStr(paramMap.get("type"), null);// 用来标志修改的是登录密码还是交易密码
		// add by lw 判断交易面的长度 6 - 20
		if (newPass.length() < 6 || newPass.length() > 20) {
			JSONUtils.printStr("4");
			return null;
		}
		// end
		if (!newPass.endsWith(confirmPass)) {
			JSONUtils.printStr("1");
			return null;
		}

		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);

		try {

			Long id = user.getId();// 获得用户编号
			Map<String, String> map = homeInfoSettingService.getDealPwd(id);
			boolean re = userService.checkSign(id);
			if(!re){
				JSONUtils.printStr("5");//账号异常
				return null;
			}
			String password = null;
			if (type.endsWith("login")) {
				password = user.getPassword();
				if(com.shove.security.Encrypt.MD5(newPass
						+ IConstants.PASS_KEY).equals(map.get("dealpwd"))){
					JSONUtils.printStr("6");//登录密码不能和交易密码一样
					return null;
				}
			} else {
				// 获得交易密码
				password = map.get("dealpwd");//交易密码默认为登录密码
				if (password == null || password.equals("")) {
					password = user.getPassword();
				}
				if(com.shove.security.Encrypt.MD5(newPass
						+ IConstants.PASS_KEY).equals(user.getPassword())){
					JSONUtils.printStr("7");//交易密码不能和登录密码一样
					return null;
				}
				
			}
			if ("1".equals(IConstants.ENABLED_PASS)) {
				oldPass = com.shove.security.Encrypt.MD5(oldPass);
			} else {
				oldPass = com.shove.security.Encrypt.MD5(oldPass
						+ IConstants.PASS_KEY);
			}
			if (!oldPass.endsWith(password)) {// 旧密码输入错误
				JSONUtils.printStr("2");
				return null;
			}
			long result = homeInfoSettingService.updateUserPassword(id,
					newPass, type);

			if (result < 0) {
				JSONUtils.printStr("3");
			} else {
				bbsRegisterService.doUpdatePwdByAsynchronousMode(user
						.getUserName(), newPass, oldPass, 1);
				this.getUser().setEncodeP(
						Encrypt.encryptSES(newPass, IConstants.PWD_SES_KEY));
				session().removeAttribute("user");
				User user1 = new User();
				user1 = userService.queryUserDetailById(id);
				session().setAttribute("user", user1);
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return null;
	}

	/**
	 * 查询银行卡信息，以表格显示
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 * @throws IOException
	 */
	public String queryBankInfoInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);

		Long id = user.getId();// 获得用户编号
		try {
			List<Map<String, Object>> lists = homeInfoSettingService.queryBankInfoList(id);
			List<Map<String, Object>> listsCount = homeInfoSettingService.queryBankInfoListCount(id);
			request().setAttribute("lists", lists);
			request().setAttribute("count", listsCount.size());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		if (checkMobile()) {
			return "successmobile";
		}
		return SUCCESS;
	}

	/**
	 * 添加提现银行信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addBankInfo() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String cardUserName = Convert.strToStr(paramMap.get("cardUserName"),null);
		String bankName = Convert.strToStr(paramMap.get("bankName"), null);
		String subBankName = Convert.strToStr(paramMap.get("subBankName"), null);
		String bankCard = Convert.strToStr(paramMap.get("bankCard"), null);
		String dealpwd = Convert.strToStr(paramMap.get("dealpwd"), null);
		int province = Convert.strToInt(paramMap.get("province"), -1);
		int city = Convert.strToInt(paramMap.get("city"), -1);

		if(StringUtils.isBlank(bankName)|| StringUtils.isBlank(subBankName)||  StringUtils.isBlank(bankCard)||StringUtils.isBlank(cardUserName)||StringUtils.isBlank(dealpwd) || province==-1 || city==-1){
			JSONUtils.printStr("3");
			return null;
		}
		
		if (StringUtils.isNotBlank(bankCard) && !bankCard.startsWith("6")) {//必须6开头
		    JSONUtils.printStr("4");
            return null;
        }
		
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		} else {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
					+ IConstants.PASS_KEY);
		}
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String pwd=Convert.strToStr(userService.queryUserById(user.getId()).get("dealpwd"), "");
		if(!dealpwd.equals(pwd)){
			JSONUtils.printStr("5");
			return null;
		}
		Long id = user.getId();// 获得用户编号
		try {
			Map<String, String> map = homeInfoSettingService
					.queryCardStatus(id);
			int bindingCardNum = Convert.strToInt(map.get("count(*)"), 0);
			if (bindingCardNum >= 9) {// 已经绑定九张银行卡，不能再绑定了
				JSONUtils.printStr("2");
				return null;
			}
			//查询某个账号是否已经被该用户绑定过了
			Map<String, String> bandingMap = homeInfoSettingService
					.queryBankCardisBanding(id,bankCard);
			int bindingCardisBanding = Convert.strToInt(bandingMap.get("count(*)"), 0);
			if (bindingCardisBanding > 0) {
				JSONUtils.printStr("6");
				return null;
			}
			
			// 新添加的提现银行卡信息状态为2，表示申请中
			long result = homeInfoSettingService.addBankCardInfo(id,
					cardUserName, bankName, subBankName, bankCard,
					IConstants.BANK_CHECK,city,province);
			operationLogService.addOperationLog("t_bankcard", user
					.getUserName(), IConstants.INSERT, user.getLastIP(), 0,
					"添加提现银行信息", 1);

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 删除提现银行卡信息（这里删除未绑定的银行卡）
	 * 
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public String deleteBankInfo() throws Exception {
		/*log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long id = request.getLong("bankId", -1L);
		User user = (User) session().getAttribute("user");
		try {
			long result = homeInfoSettingService.deleteBankInfo(id);
			// 添加系统操作日志
			if(result>0){
			operationLogService.addOperationLog("t_bankcard", user
					.getUserName(), IConstants.DELETE, user.getLastIP(), 0,
					"删除未绑定的银行卡信息", 1);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;*/
	    return deleteMyBankInfo();
	}
	
	//删除该条提现银行卡信息
	public String deleteMyBankInfo() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long id = request.getLong("bankId", -1L);
		User user = (User) session().getAttribute("user");
		try {
		    //2014-12-15 14:15:54修复。必须删除自己的银行卡
			long result = homeInfoSettingService.deleteMyBankInfo(id,user.getId());
			// 添加系统操作日志
			if(result>0){
			operationLogService.addOperationLog("t_bankcard", user
					.getUserName(), IConstants.DELETE, user.getLastIP(), 0,
					"删除已绑定的银行卡信息", 1);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	

	/**
	 * 手机绑定页面加载
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bindingMobileInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		try {
			// 查询成功绑定的手机信息
			// Map<String,String> map = homeInfoSettingService.
			// querySucessBindingInfoByUserId(user.getId(),1);
			Map<String, String> map = homeInfoSettingService
					.querySucessBindingInfoByUserId(user.getId());
			JSONObject object = new JSONObject();
			if (map == null) {
				object.put("map", "");
			} else {
				object.put("map", map);
			}
			JSONUtils.printObject(object);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 添加手机号码绑定信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long addBindingMobile() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 为空在jsp页面已经验证
		String mobile = Convert.strToStr(paramMap.get("mobile"), null);
		String code = Convert.strToStr(paramMap.get("code"), null);
		String content = Convert.strToStr(paramMap.get("content"), "");

		// 手机号码验证
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		if (!m.matches()) {// 手机号码无效
			JSONUtils.printStr("1");
			return null;
		}

		// ..............................................
		// 手机号码与验证码号码匹配
		Object objcet = session().getAttribute("phone");
		if (objcet != null) {
			String phonecode = objcet.toString();
			if (!phonecode.trim().equals(mobile.trim())) {

				JSONUtils.printStr("10");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// 验证码

		if (StringUtils.isBlank(code)) {

			JSONUtils.printStr("12");
			return null;
		}
		Object obje = session().getAttribute("randomCode");
		if (obje != null) {
			String randomCode = obje.toString();
			if (!randomCode.trim().equals(code.trim())) {

				JSONUtils.printStr("13");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// ..........................................................

		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		try {
			// 首先查看该用户有没有设置手机绑定
			Map<String, String> mp = homeInfoSettingService
					.queryBindingInfoByUserId(id);
			if (mp != null) {// 如果该用户已经绑定了手机号码信息
				String status = Convert.strToStr(mp.get("status"), null);// 查看手机状态
				if (status != null) {
					if (status.equals(IConstants.PHONE_BINDING_ON + "")) {// 手机号码已经绑定，需要申请更换手机
						JSONUtils.printStr("7");
						return null;
					} else if (status.equals(IConstants.PHONE_BINDING_CHECK
							+ "")) {// 手机号码正在审核，请等待

						JSONUtils.printStr("8");
						return null;
					} else if (status.equals(IConstants.PHONE_BINDING_UNPASS
							+ "")) {// 手机审核不通过
						JSONUtils.printStr("9");
						return null;
					}
				}
			}

			// 查看填写的手机号码是不是已经被别人绑定或者在申请绑定
			Map<String, String> map = homeInfoSettingService
					.queryBindingMobile(mobile);

			if (map != null) {
				String status = Convert.strToStr(map.get("status"), null);
				if (status != null) {
					if (status.equals(IConstants.PHONE_BINDING_ON + "")) {// 手机号码已经绑定，需要申请更换手机
						JSONUtils.printStr("3");
						return null;
					} else if (status.equals(IConstants.PHONE_BINDING_CHECK
							+ "")) {// 手机号码正在审核，请等待
						session().removeAttribute("randomCode");
						JSONUtils.printStr("4");
						return null;
					}
				}
			}

			// add by lw 查询已经绑定的手机号码
			Map<String, String> phoneMap = null;
			String oldPhone = null;
			phoneMap = beVipService.queryPUser(id);
			if (phoneMap.size() > 0 && phoneMap != null) {
				oldPhone = phoneMap.get("cellphone");
			}
			// end

			// 添加手机绑定信息，手机绑定状态位2.2代表正在审核
			Long result = homeInfoSettingService.addBindingMobile(mobile, id,
					IConstants.PHONE_BINDING_CHECK, content, oldPhone);
			if (result < 0) {// 手机绑定失败
				JSONUtils.printStr("5");
				return null;
			}

		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public Long addChangeBindingMobile() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 为空在jsp页面已经验证
		String mobile = Convert.strToStr(paramMap.get("mobile"), null);
		String code = Convert.strToStr(paramMap.get("code"), null);
		String content = Convert.strToStr(paramMap.get("content"), "");

		// 手机号码验证
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(147))\\d{8}$");
		Matcher m = p.matcher(mobile);
		if (!m.matches()) {// 手机号码无效
			JSONUtils.printStr("1");
			return null;
		}
		
		// ..............................................
		// 手机号码与验证码号码匹配
		Object objcet = session().getAttribute("phone");
		// 测试--跳过验证码
		if (IConstants.ISDEMO.equals("2")) {
			if (objcet != null) {
				String phonecode = objcet.toString();
				if (!phonecode.trim().equals(mobile.trim())) {

					JSONUtils.printStr("10");
					return null;
				}
			} else {

				JSONUtils.printStr("11");
				return null;
			}
			// 验证码

			if (StringUtils.isBlank(code)) {

				JSONUtils.printStr("12");
				return null;
			}
			Object obje = session().getAttribute("randomCode");
			if (obje != null) {
				String randomCode = obje.toString();
				if (!randomCode.trim().equals(code.trim())) {

					JSONUtils.printStr("13");
					return null;
				}
			} else {
				JSONUtils.printStr("11");
				return null;
			}
		}
		
		//过期数据
		boolean timeFlag = userService.querySendTime(mobile);
		if (!timeFlag){
			log.info("过期了。");
			JSONUtils.printStr("35");//验证失败
			return null;
		}
		// ..........................................................

		try {
			// 获取用户的信息
			User user = (User) session().getAttribute(IConstants.SESSION_USER);
			Long id = user.getId();// 获得用户编号
			// 查看变更的手机号码是否别人绑定了
			Map<String, String> map = homeInfoSettingService
					.queryBindingMobile(mobile);
//
			if (map != null) {
				JSONUtils.printStr("6");
				return null;
			}
			// add by lw
			
			//验证交易密码
			if ("1".equals(IConstants.ENABLED_PASS)) {
				content = com.shove.security.Encrypt.MD5(content.trim());
			} else {
				content = com.shove.security.Encrypt.MD5(content.trim()+ IConstants.PASS_KEY);
			}
			//如果交易密码不正确，则不准提交
			if(!content.equals(user.getDealpwd())){
				JSONUtils.printStr("14");
				return null;
			}

			// add by lw 查询已经绑定的手机号码
			Map<String, String> phoneMap = null;
			String oldPhone = null;
			phoneMap = beVipService.queryPUser(id);
			if (phoneMap.size() > 0 && phoneMap != null) {
				oldPhone = phoneMap.get("cellphone");
			}
			// end
			// end
			// 进行手机变更（状态为审核通过）
			Long result = homeInfoSettingService.addBindingMobile(mobile, id,
					IConstants.PHONE_BINDING_CHECK, content, oldPhone);
			if (result < 0) {// 手机变更失败
				JSONUtils.printStr("5");
				return null;
			}
			
			//更新表的电话号码信息信息  
			userService.queryUserById(id);

			//修改手机号码，UserType=1是个人用户，2是企业用户
			userService.updateSmsByid(id, mobile,user.getUserType());
			
			
			
			operationLogService.addOperationLog("t_phone_binding_info", user
					.getUserName(), IConstants.INSERT, user.getLastIP(), 0,
					"发布手机变更请求", 1);
			session().removeAttribute("randomCode");
			
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 通知设置加载，加载的时候从数据库中读取已经设置的数据 查询两个地方
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryNotesSettingInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号
		try {
			Map<String, String> notes = homeInfoSettingService
					.queryNotesList(id);
			List<Map<String, Object>> lists = homeInfoSettingService
					.queryNotesSettingList(id);

			if (lists == null) {
				JSONUtils.printStr("1");
				return null;
			} else {
				if (notes == null && lists.size() <= 0) {// 没有值
					JSONUtils.printStr("1");
					return null;
				}
			}

			List<Map<String, Object>> values = changeList2List(notes, lists);
			String jsonStr = JSONArray.toJSONString(values);
			JSONUtils.printStr(jsonStr);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return null;
	}

	private List<Map<String, Object>> changeList2List(
			Map<String, String> notes, List<Map<String, Object>> lists) {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		boolean message = false, mail = false, note = false;
		if (notes.get("mailNoticeEnable").equals(IConstants.NOTICE_ON + "")) {// 2为开启状态
			message = true;
		}
		if (notes.get("emailNoticeEnable").equals(IConstants.NOTICE_ON + "")) {
			mail = true;
		}
		if (notes.get("noteNoticeEnable").equals(IConstants.NOTICE_ON + "")) {
			note = true;
		}
		Map<String, Object> val = null;
		if (lists != null && lists.size() > 0) {
			for (Map<String, Object> o : lists) {
				val = add(message, mail, note, o.get("noticeMode"), o
						.get("reciveRepayEnable"), o.get("showSucEnable"), o
						.get("loanSucEnable"), o.get("rechargeSucEnable"), o
						.get("capitalChangeEnable"));
				values.add(val);
			}
		}
		return values;
	}

	private Map<String, Object> add(boolean message, boolean mail,
			boolean note, Object noticeMode, Object reciveRepayEnable,
			Object showSucEnable, Object loanSucEnable,
			Object rechargeSucEnable, Object capitalChangeEnable) {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, Object> mg = new HashMap<String, Object>();
		mg.put("message", message);
		mg.put("mail", mail);
		mg.put("note", note);
		mg.put("noticeMode", noticeMode);// 通知方式(1 邮件 2 站内信 3 短信)
		mg.put("reciveRepayEnable", reciveRepayEnable);
		mg.put("showSucEnable", showSucEnable);
		mg.put("loanSucEnable", loanSucEnable);
		mg.put("rechargeSucEnable", rechargeSucEnable);
		mg.put("capitalChangeEnable", capitalChangeEnable);
		return mg;
	}

	/**
	 * 添加通知设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long addNotesSetting() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 站内信

		boolean message = paramMap.get("message") == null ? false : true;// Convert.strToBoolean(paramMap.get("message"),false);//站内信总复选框
		boolean messageReceive = paramMap.get("messageReceive") == null ? false
				: true;
		boolean messageDeposit = paramMap.get("messageDeposit") == null ? false
				: true;
		boolean messageBorrow = paramMap.get("messageBorrow") == null ? false
				: true;
		boolean messageRecharge = paramMap.get("messageRecharge") == null ? false
				: true;
		boolean messageChange = paramMap.get("messageChange") == null ? false
				: true;
		// 邮件
		boolean mail = paramMap.get("mail") == null ? false : true;
		boolean mailReceive = paramMap.get("mailReceive") == null ? false
				: true;
		boolean mailDeposit = paramMap.get("mailDeposit") == null ? false
				: true;
		boolean mailBorrow = paramMap.get("mailBorrow") == null ? false : true;
		boolean mailRecharge = paramMap.get("mailRecharge") == null ? false
				: true;
		boolean mailChange = paramMap.get("mailChange") == null ? false : true;
		// 短信
		boolean notes = paramMap.get("note") == null ? false : true;
		boolean noteReceive = paramMap.get("noteReceive") == null ? false
				: true;
		boolean noteDeposit = paramMap.get("noteDeposit") == null ? false
				: true;
		boolean noteBorrow = paramMap.get("noteBorrow") == null ? false : true;
		boolean noteRecharge = paramMap.get("noteRecharge") == null ? false
				: true;
		boolean noteChange = paramMap.get("noteChange") == null ? false : true;

		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号
		Map<String, String> map = userService.queryUserById(id);
		if (mail) {
			String ismail = Convert.strToStr(map.get("email"), "");
			if (StringUtils.isBlank(ismail)) {
				JSONUtils.printStr("3");
				return null;
			}
		}
		try {
			long result = homeInfoSettingService.addNotesSetting(id,
					messageReceive, messageDeposit, messageBorrow,
					messageRecharge, messageChange, mailReceive, mailDeposit,
					mailBorrow, mailRecharge, mailChange, noteReceive,
					noteDeposit, noteBorrow, noteRecharge, noteChange);

			long result2 = homeInfoSettingService.addNotes(id, message, mail,
					notes);
			// 添加操作日志
			long result3 = operationLogService.addOperationLog("t_noticecon",
					user.getUserName(), IConstants.UPDATE, user.getLastIP(), 0,
					"修改通知设置", 1);
			if (result < 0 || result2 < 0 || result3 < 0) {// 设置失败
				JSONUtils.printStr("1");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public String mailNoticeInit() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 加载邮件信息
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		request().setAttribute("userName", user.getUserName());
		return SUCCESS;
	}

	/**
	 * 判断收件人是否有效
	 * 
	 * @return
	 * @throws Exception
	 */
	public String judgeUserName() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String receiver = paramMap.get("receiver"); // 收件人
		try {
			// 检查用户名是否存在 t_user
			long result = homeInfoSettingService.getConcernList(user.getId(),
					receiver);
			if (result < 0) { // 用户名不存在
				// 到t_admin表中检查用户名
				List<Map<String, Object>> lists = adminService.queryAdminList(
						receiver, 1);
				if (lists == null || lists.size() <= 0) {
					JSONUtils.printStr("1");
					return null;
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 添加邮件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addMail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String receiver = Convert.strToStr(paramMap.get("receiver"), null);
		String title = Convert.strToStr(paramMap.get("title"), null);
		String content = Convert.strToStr(paramMap.get("content"), null);
		String pageId = paramMap.get("pageId"); // 验证码
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code").toString().trim();
		if (code == null || !_code.equals(code)) {
			JSONUtils.printStr(IConstants.USER_REGISTER_CODE_ERROR);
			return null;
		}
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Integer enable = user.getEnable();
		if (enable == 3) {
			JSONUtils.printStr("8");
			return null;
		}
		Long id = user.getId();// 获得用户编号
		try {
			// 前台页面进行了判断，这里名称不可能为空
			Map<String, String> map = userService.queryIdByUser(receiver);
			Long receiverId = -2L;
			if (map == null || map.size() < 0) {// 到t_admin表中查数据
				List<Map<String, Object>> lists = adminService.queryAdminList(
						receiver, 1);
				receiverId = Convert.strToLong(lists.get(0).get("id")
						.toString(), -1L);
			} else {
				receiverId = Convert.strToLong(map.get("id"), -1L);
			}

			long result = -1;
			/**
			 * 如果是发给admin，系统管理员，则该邮件为系统邮件(如果发件人或者收件人为admin,则为系统消息)
			 */
			if (receiver.equalsIgnoreCase(IConstants.MAIL_SYS)) {// 新发送的邮件默认为未读
																	// IConstants.MAIL_UN_READ
				result = homeInfoSettingService.addMail(id, receiverId, title,
						content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_SYS);
			} else if (user.getUserName().equalsIgnoreCase(IConstants.MAIL_SYS)) {
				result = homeInfoSettingService.addMail(id, receiverId, title,
						content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_SYS);
			} else {
				result = homeInfoSettingService.addMail(id, receiverId, title,
						content, IConstants.MAIL_UN_READ,
						IConstants.MALL_TYPE_COMMON);
			}
			if (result < 0) {
				JSONUtils.printStr("1");
				return null;
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 获得用户的收件箱信息(一般信息)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryReciveMails() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		int mailStatus = paramMap.get("mailStatus") == null ? -1 : Convert
				.strToInt(paramMap.get("mailStatus"), -1);
		try {

			homeInfoSettingService.queryReceiveMails(pageBean, id,
					IConstants.MALL_TYPE_COMMON, "", mailStatus);
			List<Map<String, Object>> lists = pageBean.getPage();
			if (lists != null)
				changeLists2Lists(lists, "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String querySendMailsInit() {
		return SUCCESS;
	}

	public String queryReceiveMailsInit() {
		return SUCCESS;
	}

	public String querySysMailsInit() {
		return SUCCESS;
	}

	/**
	 * 获得用户的发件箱信息(一般信息)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String querySendMails() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		try {
			homeInfoSettingService.querySendMails(pageBean, id);
			List<Map<String, Object>> lists = pageBean.getPage();
			if (lists != null)
				changeLists2Lists2(lists);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 获得用户系统信息
	 * 
	 * @throws Exception
	 */
	public String querySysMails() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long id = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		int mailStatus = paramMap.get("mailStatus") == null ? -1 : Convert
				.strToInt(paramMap.get("mailStatus"), -1);
		try {
			homeInfoSettingService.queryReceiveMails(pageBean, id,
					IConstants.MALL_TYPE_SYS, "sys", mailStatus);

			List<Map<String, Object>> lists = pageBean.getPage();
			if (lists != null) {
				changeLists2Lists(lists, "sys");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 更改lists里面的一些信息。这样前台直接显示。 将用户id改成用户名，信息状态更改中文显示
	 * 
	 * @throws Exception
	 */
	private void changeLists2Lists(List<Map<String, Object>> lists, String type)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String username = "";
		Map<String, String> mp = null;
		int status = -1;
		try {
			for (Map<String, Object> map : lists) {
				if (type.equalsIgnoreCase("sys")) {
					mp = adminService.queryAdminById(Convert.strToLong(map.get(
							"sender").toString(), -1));
					if (mp != null && mp.size() > 0) {
						username = Convert.strToStr(mp.get("userName"), "");
						map.put("sender", username);
					}
				} else {
					mp = userService.queryUserById(Convert.strToLong(map.get(
							"sender").toString(), -1));
					if (mp != null && mp.size() > 0) {
						username = Convert.strToStr(mp.get("username"), "");
						map.put("sender", username);
					}
				}
				status = Convert.strToInt(map.get("mailStatus").toString(), -1);
				if (status == IConstants.MAIL_READED) {
					map.put("mailStatus", "已读");
				} else if (status == IConstants.MAIL_UN_READ) {
					map.put("mailStatus", "未读");
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	private void changeLists2Lists2(List<Map<String, Object>> lists)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String username = "";
		for (Map<String, Object> map : lists) {
			username = this.getUserNameById(Convert.strToLong(map
					.get("reciver").toString(), -1));
			if (username.equals("")) {
				username = this.getAdminNameById(Convert.strToLong(map.get(
						"reciver").toString(), -1));
			}
			map.put("reciver", username);
		}
	}

	public String deleteMails() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String ids = request.getString("ids");
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String[] allIds = ids.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		homeInfoSettingService.deleteMails(ids, user.getId());
		return SUCCESS;
	}

	/**
	 * 更新邮件状态
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateMail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String ids = Convert.strToStr(paramMap.get("ids"), "");
		String type = Convert.strToStr(paramMap.get("type"), "");
		String[] allIds = ids.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		try {
			long result = -1;
			// 站内信状态(1 默认未读 2 删除 3 已读)
			if (type.equals("readed")) {// 标记为已读
				result = homeInfoSettingService.updateMails(ids,
						IConstants.MAIL_READED);
			} else if (type.equals("unread")) {// 标记为未读
				result = homeInfoSettingService.updateMails(ids,
						IConstants.MAIL_UN_READ);
			}
			if (result < 0)
				return null;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 根据用户id获得用户名
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private String getUserNameById(long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Map<String, String> mp = userService.queryUserById(userId);
			if (mp != null) {
				return Convert.strToStr(mp.get("username"), "");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "";
	}

	private String getAdminNameById(long adminId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Map<String, String> mp = adminService.queryAdminById(adminId);
			if (mp != null) {
				return Convert.strToStr(mp.get("userName"), "");
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "";
	}

	/**
	 * 查询邮件内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryEmailById() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long mailId = request.getLong("mailId", -1);
		int type = request.getInt("type", 0);
		int curPage = request.getString("curPage") == null ? 1 : request.getInt("curPage", 1);
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		try {
			Map<String, String> map = homeInfoSettingService
					.queryEmailById(mailId);
			if (map == null) {
				return null;
			}
			Long userId = user.getId();
			@SuppressWarnings("unused")
			long result = -1;
			if (type == 1) {// 如果是未读信息，则更新数据库，将状态改为已读
				// add by houli
				if (user.getVirtual() != 1) {// virtual=1 是后台虚拟用户登录，不用改变邮件状态
					result = homeInfoSettingService.updateMails(mailId + "",
							IConstants.MAIL_READED);
				}
			}
			String sender = "", receiver = "", title = "", date = "", content = "";

			int mt = 0;// 发件箱
			if (map.get("sender").equals(userId + "")) {
				sender = user.getUserName();
				mt = 100;// 标记发件箱
			} else {
				if (map.get("mailType").equals(IConstants.MAIL_SYS_ + "")) {
					sender = getAdminNameById(Convert.strToLong(map
							.get("sender"), -1));
				} else {
					sender = getUserNameById(Convert.strToLong(map
							.get("sender"), -1));

				}
			}

			if (map.get("reciver").equals(userId + "")) {
				receiver = user.getUserName();
			} else {
				if (map.get("mailType").equals(IConstants.MAIL_SYS_ + "")) {
					receiver = getAdminNameById(Convert.strToLong(map
							.get("reciver"), -1));
				} else {
					receiver = getUserNameById(Convert.strToLong(map
							.get("reciver"), -1));
				}
			}
			// 操作日志
			operationLogService.addOperationLog("t_mail", user.getUserName(),
					IConstants.UPDATE, user.getLastIP(), 0, "查看站内信", 1);
			title = map.get("mailTitle");
			date = map.get("sendTime");
			content = map.get("mailContent").replace("-1.00", "0");
			request().setAttribute("sender", sender);
			request().setAttribute("receiver", receiver);
			request().setAttribute("title", title);
			request().setAttribute("date", date);
			request().setAttribute("content", content);
			request().setAttribute("curPage", curPage);
			if (mt == 100) {
				request().setAttribute("mType", 100);
			} else {
				request().setAttribute("mType",
						Convert.strToInt(map.get("mailType") + "", 0));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 成为理财人页面初始化
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public String become2FinanceInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 成为理财人必须是在会员登录以后
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		try {
			Map<String, String> map = becomeFinanceService
					.queryFinancer(userId);
			if (map == null) {// 没有记录，非理财人
				return INPUT;
			} else {
				int status = Convert.strToInt(map.get("status"), 1);
				if (status == IConstants.FINANCE_NON) {// 如果已经是填写了理财人的信息，
					return "waiting";
				} else {
					return SUCCESS;
				}
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	public String queryOneBankInfo() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);

		Long bankId = request.getLong("bankId", -100);
		try {
			Map<String, String> map = fundManagementService
					.queryOneBank(bankId);
			request().setAttribute("bankCard", map.get("cardNo"));
			request().setAttribute("bankId", map.get("id"));
			request().setAttribute("realName", user.getRealName());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String updateBankInfo() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		Long bankId = paramMap.get("bankId") == null ? -100 : Convert
				.strToLong(paramMap.get("bankId"), -100);
		String mBankName = paramMap.get("mBankName") == null ? null : Convert
				.strToStr(paramMap.get("mBankName"), null);
		String mSubBankName = paramMap.get("mSubBankName") == null ? null
				: Convert.strToStr(paramMap.get("mSubBankName"), null);
		String mBankCard = paramMap.get("mBankCard") == null ? null : Convert
				.strToStr(paramMap.get("mBankCard"), null);
		String dealpwd = paramMap.get("dealpwd") == null ? null : Convert
				.strToStr(paramMap.get("dealpwd"), null);
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		} else {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
					+ IConstants.PASS_KEY);
		}
		 
		if(!dealpwd.equals(user.getDealpwd())){
			JSONUtils.printStr("1");
			return null;
		}

		try {
			Long result = fundManagementService.updateChangeBank(bankId,
					mBankName, mSubBankName, mBankCard, IConstants.BANK_CHECK,
					new Date(), true);
			if (result < 0) {
				JSONUtils.printStr("1");
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
	 * 取消银行卡变更
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bankChangeCancel() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long bankId = request.getLong("bankId", -100);
		User user = (User) session().getAttribute("user");
		try {
			Long result = fundManagementService.updateChangeBank(bankId, "",
					"", "", IConstants.BANK_SUCCESS, null, false);
			result = operationLogService.addOperationLog("t_bankcard", user
					.getUserName(), IConstants.UPDATE, user.getLastIP(), 0,
					"取消银行卡变更", 1);
			if (result < 0) {
				JSONUtils.printStr("1");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String financerWaiting() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return SUCCESS;
	}
	/**
	 * 检查用户回答密保问题是否正确
	 * @return
	 * @throws Exception
	 */
	public String checkUserAnswer() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		
		String answerOne = request.getString("answerOne");
		String answerTwo = request.getString("answerTwo");
		String answerThree = request.getString("answerThree");
		Map<String, String> map = homeInfoSettingService
		.queryUserALLAnswer(userId);
		if (null!=map ){
			request().setAttribute("questionOne", map.get("questionOne"));
			request().setAttribute("questionTwo", map.get("questionTwo"));
			request().setAttribute("questionThree", map.get("questionThree"));
			if (IConstants.ISDEMO.equals("1")) {
				return "success";
			}
			if((!answerOne.equals(map.get("answerOne")))||(!answerTwo.equals(map.get("answerTwo")))
					||(!answerThree.equals(map.get("answerThree")))){
				this.addFieldError("paramMap.code", "答案错误！");
				return INPUT;
			}else{
					return "success";
			}
		}
		return "404";
	}
	/**
	 * 检查用户回答密保问题是否正确
	 * @return
	 * @throws Exception
	 */
//	public String checkUserAnswera() throws Exception{
//		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
//		User user = (User) session().getAttribute(IConstants.SESSION_USER);
//		long userId = user.getId();
//		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
//		request().setAttribute("isApplyPro", isApplyPro);
//		List<Map<String, Object>> list = null;
//		list = userService.queryAllQuestionList();
//		if (list != null && list.size() > 0) {
//			request().setAttribute("list", list);
//		}
//		if (IConstants.ISDEMO.equals("1")) {
//			return "success";
//		}
//		String answerOne = request.getString("answerOne");
//		String answerTwo = request.getString("answerTwo");
//		String answerThree = request.getString("answerThree");
//		Map<String, String> map = homeInfoSettingService
//		.queryUserALLAnswer(userId);
//		if (null != map ) {
//			request().setAttribute("questionOne", map.get("questionOne"));
//			request().setAttribute("questionTwo", map.get("questionTwo"));
//			request().setAttribute("questionThree", map.get("questionThree"));
//			if((!answerOne.equals(map.get("answerOne")))||(!answerTwo.equals(map.get("answerTwo")))
//					||(!answerThree.equals(map.get("answerThree")))){
//				this.addFieldError("paramMap.code", "答案错误！");
//				return INPUT;
//			}else{
//				return "success";
//			}
//		}
//		return "404";
//	}
	
	
	/**
	 * 修改登录密码和交易密码页面跳转
	 * @return
	 * @throws Exception
	 */
	public String updatePasswordAndDealpwd() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session("user");
		int authStep = user.getAuthStep();
		if(authStep < 2){
			request().setAttribute("person", "0");
		}else{
			request().setAttribute("person", "1");
		}
		return "success";
	}
	
	public String setPwd() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		if(isApplyPro == 1){
			return "applyPro";
		}else{
			return "answerPro";
		}
	}
	
	/**
	 * 获取用户设置的密保问题
	 * @return
	 * @throws Exception
	 */
	public String getUserQuestion() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		if(isApplyPro == 1){
			return "index";
		}
		try {
			Map<String, String> map = homeInfoSettingService
					.queryUserALLAnswer(userId);
			if(null!=map){
				request().setAttribute("questionOne", map.get("questionOne"));
				request().setAttribute("questionTwo", map.get("questionTwo"));
				request().setAttribute("questionThree", map.get("questionThree"));
			}
			String bindingPhone = null;
			int status = -1;
			Map<String, String> m = userService.queryUserBindphone(userId);
			if (m != null && m.size() > 0) {
				if (m.get("bindingPhone") != null) {
					bindingPhone = m.get("bindingPhone").toString();
				}
				if (m.get("status") != null) {
					status = Convert.strToInt(m.get("status")
							.toString(), -1);
				}

				// 如果设置的绑定号码为空，或者绑定的手机号码还未审核通过 则都使用用户注册时的手机号码
				if (bindingPhone == null
						|| status != IConstants.PHONE_BINDING_ON || bindingPhone=="") {
					bindingPhone = m.get("cellPhone") + "";
				}
				if (bindingPhone == null || bindingPhone=="" ) {
					bindingPhone = m.get("mobilePhone").toString();
				}
			}
			request().setAttribute("bindingPhone", bindingPhone);
			request().setAttribute("ISDEMO", IConstants.ISDEMO);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return "success";
	}
	/** 保存或修改用户密保答案及提问
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String saveOrUpdatePwdAnswer() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		
		String questionOne = paramMap.get("questionOne").trim();
		String questionTwo = paramMap.get("questionTwo").trim();
		String questionThree = paramMap.get("questionThree").trim();
		String answerOne = paramMap.get("answerOne").trim();
		String answerTwo = paramMap.get("answerTwo").trim();
		String answerThree = paramMap.get("answerThree").trim();
		long result=-1;
		//判定用户是否已经申请密保，没有申请就表示保存，有申请就表示修改
		if(isApplyPro==1){
			 result = homeInfoSettingService.savePwdAnswer(userId, questionOne, questionTwo, 
					questionThree, answerOne, answerTwo, answerThree);
			 if (result < 0) {
					JSONUtils.printStr("1");
				     return null;
				}else{
					JSONUtils.printStr("2");
					userService.updatePwdProState(userId);
				     return null;
				}
		}
		 result = homeInfoSettingService.updatePwdAnswer(userId, questionOne, questionTwo,
				 questionThree, answerOne, answerTwo, answerThree);
		 if (result < 0) {
				JSONUtils.printStr("3");
			     return null;
			}else{
				JSONUtils.printStr("4");
				userService.updatePwdProState(userId);
			     return null;
			}
	}
	
	
	public String addBecomeFinance() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userId = user.getId();
		String realName = paramMap.get("realName");
		String cellPhone = paramMap.get("cellPhone");
		String idNo = paramMap.get("idNo");
		String code = paramMap.get("send_phoneCode");

		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(cellPhone);
		if (!m.matches()) {// 手机号码无效
			JSONUtils.printStr("7");
			return null;
		}
		if (StringUtils.isBlank(cellPhone)) {

			JSONUtils.printStr("8"); // 手机号为空
			return null;
		}

		// ..............................................
		// 手机号码与验证码号码匹配
		Object objcet = session().getAttribute("phone");
		if (objcet != null) {
			String phonecode = objcet.toString();
			if (!phonecode.trim().equals(cellPhone.trim())) {

				JSONUtils.printStr("10");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// 验证码

		if (StringUtils.isBlank(code)) {

			JSONUtils.printStr("12");
			return null;
		}
		Object obje = session().getAttribute("randomCode");
		if (obje != null) {
			String randomCode = obje.toString();
			if (!randomCode.trim().equals(code.trim())) {

				JSONUtils.printStr("13");
				return null;
			}
		} else {

			JSONUtils.printStr("11");
			return null;
		}
		// ..........................................................

		try {
			long result = becomeFinanceService.addBecomeFinancer(userId,
					realName, cellPhone, idNo, IConstants.FINANCE_NON);

			if (result < 0) {
				JSONUtils.printStr("1");
			}
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	/**
	 * 邮件回复
	 * 
	 * @return
	 * @throws Exception
	 */
	public String replayMail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long mailId = request.getLong("id", -1L);
		int type = request.getInt("type", 0);

		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();

		try {
			Map<String, String> map = homeInfoSettingService
					.queryEmailById(mailId);
			if (map == null) {
				return null;
			}
			long result = -1;
			if (type == 1) {// 如果是未读信息，则更新数据库，将状态改为已读
				result = homeInfoSettingService.updateMails(mailId + "",
						IConstants.MAIL_READED);
			}
			String sender = "", receiver = "", title = "", date = "", content = "";

			if (map.get("sender").equals(userId + "")) {
				sender = user.getUserName();
			} else {
				sender = getUserNameById(Convert.strToLong(map.get("sender"),
						-1));
			}

			if (map.get("reciver").equals(userId + "")) {
				receiver = user.getUserName();
			} else {
				receiver = getUserNameById(Convert.strToLong(
						map.get("reciver"), -1));
			}

			title = map.get("mailTitle");
			date = map.get("sendTime");
			content = map.get("mailContent");
			request().setAttribute("sender", sender);
			request().setAttribute("receiver", receiver);
			request().setAttribute("title", title);
			request().setAttribute("date", date);
			request().setAttribute("content", content);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;
	}

	/**
	 * 邮箱管理模块
	 * 
	 * @return
	 * @throws Exception
	 */
	public String emailManagerInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		paramMap = userService.queryUserById(user.getId());
		String email = paramMap.get("email") + "";
		String flag = "1";
		if (email.equals("")) {
			flag = "1";
		} else {
			session().setAttribute("paraMsg","");
			flag = "2";
		}
		paramMap.put("flag", flag);
		session().setAttribute("DEMO", IConstants.ISDEMO);
		return SUCCESS;
	}

	/**
	 * 账户设置 邮箱设定
	 * 
	 * @return
	 * @throws Exception
	 */
	public String SendUserEmailSet() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		String email = paramMap.get("email");
		if (StringUtils.isBlank(email)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		}
		long result1 = userService.isExistEmailORUserName(email, null);
		if (result1 > 0) { // email重复
			obj.put("mailAddress", "4");
			JSONUtils.printObject(obj);
			return null;
		}
		// ===截取emal后面地址
		int dd = email.indexOf("@");
		String mailAddress = null;
		if (dd >= 0) {
			mailAddress = "mail." + email.substring(dd + 1);
		}
		//60

		//记录插入时间 
		userService.addSendLog(email,"绑定email",360L);
		
		Random r = new Random();
		int x = r.nextInt(899999); 
		x += 100000;
		String xrandom = "" + x;
		session().setAttribute("emailSetRadom", xrandom);
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		if (user != null) {
			DesSecurityUtil des = new DesSecurityUtil();
			String key1 = des.encrypt(user.getId() + "");
			String key2 = des.encrypt(new Date().getTime() + "");
			String key3 = email;
			String Name = user.getUserName();
			String url = getPath(); // request().getRequestURI();
			String VerificationUrl = url + "bangdingemail.do?key=" + key1 + "-"
					+ key2 + "-" + key3;
			sendMailService
					.SendUserEmailSetInUser(VerificationUrl, Name, email,xrandom);
			obj.put("mailAddress", mailAddress);
			JSONUtils.printObject(obj);
			return null;
		}
		return null;

	}

	/**
	 * 邮箱绑定
	 * 
	 * @return
	 * @throws Exception
	 */
	public String bangdingemail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String key = request.getString("key").trim();
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (3 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			Long userId = Convert
					.strToLong(des.decrypt(keys[0].toString()), -1);
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			String emial = keys[2].toString();
			Pattern p = Pattern
					.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
			Matcher matcher = p.matcher(emial);
			if (!matcher.matches()) {
				paramMap.put("msg", "邮箱格式错误");
			} else {
				// 校验邮箱的唯一性
				long result1 = userService.isExistEmailORUserName(emial, null);
				if (result1 > 0) { // email重复
					paramMap.put("msg", "该邮箱已被绑定,请重新输入");
				} else {
					// 当用户点击注册时间小于10分钟
					if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
						long result = userService.updateEmalByid(userId, emial);
						if (result < 0) {
							paramMap.put("msg", "邮箱绑定失败");
						} else {
							paramMap.put("msg", "邮箱绑定成功");
							Map<String, String> map = userService
									.queEmailUser(userId);
							String username = map.get("username") + "";
							bbsRegisterService.doUpdateEmailByAsynchronousMode(
									username, emial);
							User user=(User)session().getAttribute("user");
							user.setEmail(emial);
							session().setAttribute("user", user);
						}
						return SUCCESS;
					} else {
						msg = "连接失效,<strong>请重新绑定</a></strong>";
						paramMap.put("msg", msg);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 绑定邮箱，要输入验证码
	 * @return
	 * @throws IOException 
	 */
	public String bangEmailByCode() throws IOException {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		String email = paramMap.get("mails");
		String checkCode = paramMap.get("checkCode");
		String dealpwd = paramMap.get("edealpwd");
		String scode = (String) session().getAttribute("emailSetRadom");
		if (StringUtil.isEmpty(checkCode) || StringUtil.isEmpty(scode)) {
			// 绑定失败  
			paramMap.put("msg", "数据错误");
			JSONUtils.printStr("1");
			log.info(email + "验证码为空，请仔细检查相关信息。");
			return SUCCESS;
		}

		if (!checkCode.equals(scode)) {
			// 校验失败 
			paramMap.put("msg", "验证码输入错误");
			JSONUtils.printStr("2");
			log.info(email + "验证码输入错误" + scode + checkCode);
			return SUCCESS;
		}
		
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		
		//加密交易密码
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		} else {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
					+ IConstants.PASS_KEY);
		}
		
		//验证交易密码  
		if (!dealpwd.equals(user.getDealpwd())){
			paramMap.put("msg", "交易密码错误,请重新输入。");
			JSONUtils.printStr("8");
			return SUCCESS;
		}
	
		// 成功则执行下面的代码

		Pattern p = Pattern
				.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
		Matcher matcher = p.matcher(email);
		if (!matcher.matches()) {
			paramMap.put("msg", "邮箱格式错误");
			JSONUtils.printStr("3");
		} else {
			// 校验邮箱的唯一性
			long result1 = 1;
			try {
				result1 = userService.isExistEmailORUserName(email, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result1 > 0) { // email重复
				paramMap.put("msg", "该邮箱已被绑定,请重新输入");
				JSONUtils.printStr("4");
			} else {
//				User user = (User) session().getAttribute(IConstants.SESSION_USER);
				
				boolean timeFlag = userService.querySendTime(email);
				if (!timeFlag){
					log.info("过期了。");
					paramMap.put("msg", "邮箱绑定超时");
					JSONUtils.printStr("5");
					return null;
				}
				Long userId = user.getId();
				try {
					long result = userService.updateEmalByid(userId, email);
					if (result < 0) {
						paramMap.put("msg", "邮箱绑定失败");
						JSONUtils.printStr("5");
					} else {
						log.debug(email + "邮箱绑定成功.");
						paramMap.put("msg", "邮箱绑定成功");
						session().removeAttribute("emailSetRadom");
						JSONUtils.printStr("7");
						Map<String, String> map = userService
								.queEmailUser(userId);
						String username = map.get("username") + "";
						bbsRegisterService.doUpdateEmailByAsynchronousMode(
								username, email);
						user.setEmail(email);
						session().setAttribute("user", user);
					}
				} catch (Exception e) {
					e.printStackTrace();
					JSONUtils.printStr("6");
					log.error(email+"邮箱绑定失败:", e);
				}
				return SUCCESS;
			
			}
		}
		return SUCCESS;
	}
	
	//实名认证
	public String identifier() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject json = new JSONObject();
		User user = (User) session("user");
		if (null == user){
			log.info("---identifier.error.request.--");
			json.put("msg", "非法请求。");
			JSONUtils.printObject(json);
			return null;
		}
		
		Long userId = user.getId();
		
		
		String realName = paramMap.get("realName");// 真实姓名
		
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请输入您的真实名字");
			JSONUtils.printObject(json);
			return null;
		} 
		if (2 > realName.length() || 30 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于30");
			JSONUtils.printObject(json);
			return null;
		} 
		Pattern p_realName = Pattern.compile("^[a-zA-Z\u4e00-\u9fa5]+$");
        Matcher matcher_realName = p_realName.matcher(realName);
		if(!matcher_realName.matches()){
			json.put("msg", "真实姓名输入有误");
			JSONUtils.printObject(json);
			return null;
		}
		String idNo = paramMap.get("idNo");// 身份证号码
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请输入您的身份证号码");
			JSONUtils.printObject(json);
			return null;
		}
		//身份证正则表达式(15位) 
        Pattern p_idNo1 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
        //身份证正则表达式(18位) 
        Pattern p_idNo2 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}(x|X))$");
        Matcher matcher_idNo1 = p_idNo1.matcher(idNo);
        Matcher matcher_idNo2 = p_idNo2.matcher(idNo);
        long len = idNo.length();
		if(!(matcher_idNo1.matches()||matcher_idNo2.matches())){
			json.put("msg", "身份证号码不合法");
			JSONUtils.printObject(json);
			return null;
		}
		Map<String, String> idNoMap = beVipService.queryHasIDCard(idNo,realName);
		if(idNoMap != null){
			System.out.println(idNoMap.get("authCardName"));
			if (idNoMap.get("authCardName").equals("0")) {
				json.put("msg", "该身份证已认证过");
				JSONUtils.printObject(json);
				return null;
			}
		}
		Map<String, String> pMap = new HashMap<String, String>();
		pMap = beVipService.queryPersonUser(user.getId());
		if(pMap!=null){
			int authCardNameCount = Convert.strToInt(pMap.get("authCardName"), -1);
			if(authCardNameCount>=4){
				json.put("msg", "您已三次认证");
				JSONUtils.printObject(json);
				return null;
			}
		}
//		Long userId = Convert.strToLong(pMap.get("userId"), -1);
		
		long personId = -1L;
		
		String result = Identifier(realName,idNo);
		if (result.equals("成功")) {
			
			//注册送好币    
		    /*boolean isActive = sysparService.isOpen("OPEN", "25");
		    if (isActive){
		    	Map map = userService.queryUserById(userId);//.queryPersonById(userId);
		    	log.info("------------- map:" + map);
		    	SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		    	String d = (String) map.get("createTime");
		    	Date redate = sf.parse(d);
		    	//活动起止时间   是改日期
		    	Date sdate = sf.parse("2015-09-25 10:00:00");//yyyy-MM-dd HH:mm:ss
		    	Date edate = sf.parse("2015-10-23 12:00:00");
		    	
		    	if (redate.after(sdate) && redate.before(edate)){
		    		userService.addPresrent(10, userId,0);//加赠送金额
			    	homeInfoSettingService.addAwardUser(userId);//记录
		    	}
		    }*/
			
			
			if (pMap != null) {
				try {
					personId = userAdminService.updatePerson(realName, userId, idNo);
					user.setRealName(realName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (personId > 0) {
					json.put("msg", "认证身份信息已更新至您的详细信息里");
					JSONUtils.printObject(json);
					return null;
					// 成功
				}
			}else{
				personId = userAdminService.addPerson(realName, userId, idNo);
				user.setRealName(realName);
				if (personId > 0) {
					json.put("msg", "认证信息已添加进您的详细信息里");
					JSONUtils.printObject(json);
					return null;
					// 成功
				}
			}
			session().setAttribute(IConstants.SESSION_USER, user);

		} 
		if (result.equals("不成功")){
			if (pMap != null) {
				int authCardName = Convert.strToInt(pMap.get("authCardName"), -1);
				try {
					personId = userAdminService.updatePersonCount(userId,authCardName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (personId > 0) {
					json.put("msg", "认证身份信息不通过");
					JSONUtils.printObject(json);
					return null;
				}
			}else{
				personId = userAdminService.addPersonCount(userId,0);
				if (personId > 0) {
					json.put("msg", "认证身份信息不通过");
					JSONUtils.printObject(json);
					return null;
				}
			}
		}
		if(result.equals("余额不足")){
			json.put("msg", "网络异常");
			JSONUtils.printObject(json);
			return null;
		}
		return SUCCESS;
	}
	
	
	public String Identifier(String realName,String idNo) throws SQLException{
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
             List<Map<String, Object>> lists = sysparService.querySysparAllChild("*", " selectKey =\"SMRZ\" and deleted=1", "", -1, -1);
             if (lists!=null && lists.size()>0 && "SMRZ".equals(lists.get(0).get("selectKey"))) {
                return "成功";
            }
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
		
		JSONObject json = null;
		String result = null;
		String result1 = "不成功";
		try {
			//鹏元
			boolean pyopen = sysparService.isOpen("PYSM", "20");
	        if (pyopen) {
	        	Map<String, Object> py = pyRealName(realName, idNo);
	    		if (py!=null && py.get("code").equals("1")) {
	    			return "成功";
	    		}else {
	    			return "不成功";
	    		}
	         }
			
			String req = String.format("{\"IDNumber\":\"%s\",\"Name\":\"%s\"}", idNo, realName);
			String cred = String.format("{\"UserName\":\"%s\",\"Password\":\"%s\"}", "yft_admin","SP7Z27PH");
			IdentifierServiceStub client = new IdentifierServiceStub();
			SimpleCheckByJson scbj = new SimpleCheckByJson();
			scbj.setCred(cred);
			scbj.setRequest(req);
			SimpleCheckByJsonResponse scbr = client.simpleCheckByJson(scbj);
			result = scbr.getSimpleCheckByJsonResult();
			System.out.println(result);
			JSONObject  jsonObj  = JSONObject.fromObject(result);
			
			System.out.println(jsonObj.get("ResponseText"));
			
			if("成功".equals(jsonObj.get("ResponseText"))){
				JSONObject  info  = jsonObj.getJSONObject("Identifier");
				if("一致".equals(info.get("Result"))){
					result1 = "成功";
				}else{
					result1 = "不成功";
				}
			}else{
				result1 = "不成功";
			}
			if("不成功".equals(jsonObj.get("ResponseText"))){
				result1 = "不成功";
			}
			if("余额不足".equals(jsonObj.get("ResponseText"))){
				result1 = "余额不足";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result1;
	}
	
	
	
	
	
	/**
	 * pyRealName:鹏元实名
	 * @author hjh
	 * @param name 姓名
	 * @param cardNo  身份证
	 * @return
	 * @since JDK 1.6
	 */
	public Map<String, Object> pyRealName(String name,String cardNo){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String file = WebUtil.getPackagePath("szhrrzs.jks", false, HomeInfoSettingAction.class.getResource(""));
			log.info(file);
			System.setProperty("javax.net.ssl.keyStore",file); 
	        System.setProperty("javax.net.ssl.keyStorePassword", "123456"); 
	        //System.setProperty("javax.net.ssl.trustStore", "test\\client.truststore"); 
	        //System.setProperty("javax.net.ssl.trustStorePassword", "123456"); 
	        //System.setProperty("javax.net.debug", "ssl"); 
	        //System.setProperty("https.protocols", "TLSv1"); 
	        //System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol"); 
	        //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());       
	        String endpoint = "https://www.pycredit.com:8443/services/WebServiceSingleQuery?wsdl"; 
	        //String endpoint = "http://www.pycredit.com:9001/services/WebServiceSingleQuery?wsdl";
			log.info("call---------start");
			Call call = null;
			Service service = new Service();
			call = (Call) service.createCall();
			call.setOperationName("queryReport");
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			String boay = "<?xml version=\"1.0\" encoding=\"GBK\"?><conditions><condition queryType=\"25160\"><item><name>name</name><value>"+name+"</value></item><item><name>documentNo</name><value>"+cardNo+"</value></item><item><name>subreportIDs</name><value>10602</value></item><item><name>refID</name><value>108</value></item></condition></conditions>";
			String xmlStr = (String) call.invoke(new Object[] { "hrrzwsquery",
					"{MD5}ZSpexsMyV9GyUlTVlYMuIg==", boay, "xml" });
			//String xmlStr = "<result><status>1</status><returnValue>UEsDBBQACAAIAH2NC0cAAAAAAAAAAAAAAAALAAAAcmVwb3J0cy54bWx9U01v0zAYPheJ/2D5vBEn7SCdnEyiDDQhhtSOH5CmbhsttYudFLZ/s26ncUCbhArrxCTQpG47tIC6TeXrwGFCCI5ckBDOR1u6Rrskrx+/fp73eV8bLzytuaBBuHAYNaB6A0FAqM1KDq0Y8N7t+xAsmNevYdsReVJn3BMAFC1vmRlQQ+oc0lVVRQhpc/otCHzqeMtWjRjw6NfHwXGvvd0ZdAZnxy82dzY/9LpfXj07eQ2B8IsPecWSaq2Lg/PdAwge+4SvPRKEL90xYJXz9ScihOKdHPOpJ2uDgBObOA2y4gQaQ32gonlNn9cQnChUJge/gHK60qLvuKVFWhozzSJ9dsylxtJ5YglGA45sNqw84l5ZqxMhK0I3kTajZjQku+ZxYnl5Inw3qHUGXU4vVGWUs+pe1OiN5tHP1udO93QLaBvNl787J62Lbr/97exT77DbBIHXcqCrIh2CqiUKa8IjtUXOGTdg2XIFgcARdzlbJ3QISP8pHLeMlpxASQRYCjvybBilMJUDMoMPVsIwQu2oMHOnvfv93fP+117/7R+sDNEop2G5PjH3zvfft7exEq1CdmVEPyVUYrZfI1RemCvlTrf2/+4dvvmRLJlJo4yuqdlsGmVVXQ4wnSyPlWnzuM5cxya5KrFXl2iZTU4lnuGlWeWY8EY7k3OVryMYwQMihFWRx6Oej/2HHoctGhnG4zYkmvlvO0znoZypYiWOQrReZR4zsRL9Q7+xsMQmXYbG5aNmNO+I1YJneYneo5ub5D3hTmtJ3qXylEwIj97h5EqY/wBQSwcILaFz9FICAAByBAAAUEsBAhQAFAAIAAgAfY0LRy2hc/RSAgAAcgQAAAsAAAAAAAAAAAAAAAAAAAAAAHJlcG9ydHMueG1sUEsFBgAAAAABAAEAOQAAAIsCAAAAAA==</returnValue></result>";
			log.info("call---------end");
			log.info(xmlStr);
			
			
			//返回字符串Document，解析处理Document内容
			Document document = DocumentHelper.parseText(xmlStr);
			Element element = document.getRootElement().element("returnValue");
	        String returnValue = element.getStringValue();
	        System.out.println(returnValue);
	        byte [] re= Base64.decode(returnValue); 
	        returnValue = decompress(re);
	        log.info(returnValue);
	        returnValue = xmltoJson(returnValue);
	        log.info(returnValue);
	        /*1：姓名和公民身份号码一致。
	        2：公民身份号码一致，姓名不一致。
	        3：库中无此号，请到户籍所在地进行核实。*/
	        JSONObject object = JSONUtils.toJSONObject(returnValue);
	        String ret = object.getJSONObject("cisReport").getJSONObject("policeCheckInfo").getJSONObject("item").getString("result");
	        
	        int intret = Convert.strToInt(ret, 4);
	       
	        switch (intret) {
			case 1:
				map.put("msg", "认证成功!");
				map.put("code", "1");
				//添加到本地数据库
				addTrealName(name,cardNo);
				break;
			case 2:
				map.put("msg", "公民身份号码一致，姓名不一致!");
				map.put("code", "2");
				break;
			case 3:
				map.put("msg", "库中无此号，请到户籍所在地进行核实!");
				map.put("code", "3");
				break;
			default:
				map.put("msg", "系统繁忙，请稍后再试!");
				map.put("code", "4");
				break;
			}
		} catch (Exception e) {
			map.put("msg", "系统繁忙，请稍后再试!");
			map.put("code", "4");
			log.error(e);
		}
		
		return map;
	}
	
	public static String xmltoJson(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		return xmlSerializer.read(xml).toString();
	}
	
	
	
	public static void main(String[] args) throws Exception{
		String xmlStr = "<result><status>1</status><returnValue>UEsDBBQACAAIAH2NC0cAAAAAAAAAAAAAAAALAAAAcmVwb3J0cy54bWx9U01v0zAYPheJ/2D5vBEn7SCdnEyiDDQhhtSOH5CmbhsttYudFLZ/s26ncUCbhArrxCTQpG47tIC6TeXrwGFCCI5ckBDOR1u6Rrskrx+/fp73eV8bLzytuaBBuHAYNaB6A0FAqM1KDq0Y8N7t+xAsmNevYdsReVJn3BMAFC1vmRlQQ+oc0lVVRQhpc/otCHzqeMtWjRjw6NfHwXGvvd0ZdAZnxy82dzY/9LpfXj07eQ2B8IsPecWSaq2Lg/PdAwge+4SvPRKEL90xYJXz9ScihOKdHPOpJ2uDgBObOA2y4gQaQ32gonlNn9cQnChUJge/gHK60qLvuKVFWhozzSJ9dsylxtJ5YglGA45sNqw84l5ZqxMhK0I3kTajZjQku+ZxYnl5Inw3qHUGXU4vVGWUs+pe1OiN5tHP1udO93QLaBvNl787J62Lbr/97exT77DbBIHXcqCrIh2CqiUKa8IjtUXOGTdg2XIFgcARdzlbJ3QISP8pHLeMlpxASQRYCjvybBilMJUDMoMPVsIwQu2oMHOnvfv93fP+117/7R+sDNEop2G5PjH3zvfft7exEq1CdmVEPyVUYrZfI1RemCvlTrf2/+4dvvmRLJlJo4yuqdlsGmVVXQ4wnSyPlWnzuM5cxya5KrFXl2iZTU4lnuGlWeWY8EY7k3OVryMYwQMihFWRx6Oej/2HHoctGhnG4zYkmvlvO0znoZypYiWOQrReZR4zsRL9Q7+xsMQmXYbG5aNmNO+I1YJneYneo5ub5D3hTmtJ3qXylEwIj97h5EqY/wBQSwcILaFz9FICAAByBAAAUEsBAhQAFAAIAAgAfY0LRy2hc/RSAgAAcgQAAAsAAAAAAAAAAAAAAAAAAAAAAHJlcG9ydHMueG1sUEsFBgAAAAABAAEAOQAAAIsCAAAAAA==</returnValue></result>";
		//返回字符串Document，解析处理Document内容
		Document document = DocumentHelper.parseText(xmlStr);
		Element element = document.getRootElement().element("returnValue");
        String returnValue = element.getStringValue();
        System.out.println(returnValue);
        byte [] re= Base64.decode(returnValue); 
        returnValue = decompress(re);
        log.info(returnValue);
        returnValue = xmltoJson(returnValue);
        log.info(returnValue);
        /*1：姓名和公民身份号码一致。
        2：公民身份号码一致，姓名不一致。
        3：库中无此号，请到户籍所在地进行核实。*/
        JSONObject object = JSONUtils.toJSONObject(returnValue);
        String ret = object.getJSONObject("cisReport").getJSONObject("policeCheckInfo").getJSONObject("item").getString("result");
        
        int intret = Convert.strToInt(ret, 4);
        Map<String, String> map = new HashMap<String, String>();
        switch (intret) {
		case 1:
			map.put("msg", "认证成功!");
			map.put("code", "1");
			break;
		case 2:
			map.put("msg", "公民身份号码一致，姓名不一致!");
			map.put("code", "2");
			break;
		case 3:
			map.put("msg", "库中无此号，请到户籍所在地进行核实!");
			map.put("code", "3");
			break;
		default:
			map.put("msg", "系统繁忙，请稍后再试!");
			map.put("code", "4");
			break;
		}
        
        log.info(ret);
	}
	
	public void addTrealName(String name,String cardNo){
		try {
			long result =homeInfoSettingService.queryTrealName(name, cardNo);
			if (result==0) {
				homeInfoSettingService.addTrealName(name,cardNo);
			}
		} catch (Exception e) {
			log.error(e);
		} 
	}
	
	/**
     * 解压缩
     * @param compressed
     * @return
	 * @throws IOException 
     */
    public static final String decompress(byte[] compressed) throws IOException {
        if (compressed == null)
            return null;
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        ZipInputStream zin = null;
        String decompressed;
        try {
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            zin = new ZipInputStream(in);
            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            //decompressed = out.toString();
            decompressed = out.toString("GBK");
        } catch (IOException e) {
            decompressed = null;
            throw new RuntimeException("解压缩字符串数据出错", e);
        } finally {
            if (zin != null) {
                try {
                    zin.close();
                } catch (IOException e) {
                    log.debug("关闭ZipInputStream", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("关闭ByteArrayInputStream", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.debug("关闭ByteArrayOutputStream", e);
                }
            }
        }
        return decompressed;
    }
	
	/**
	 * 功能 ：超时移除邮件验证码
	 * @return
	 */
	public String removeEmailTime(){
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		session().removeAttribute("emailSetRadom");
		log.info("清除邮件验证码。");
		return SUCCESS;
	}
	
	/**
	 * realNameInterface:实名接口
	 * @author hjh
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public String realNameInterface() throws Exception{
		String cardNo  = request("cardNo");
		String name = request("name");
		if (StringUtils.isBlank(cardNo) || StringUtils.isBlank(name)) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> idNoMap = beVipService.queryHasIDCard(cardNo,name);
		if(idNoMap != null){
			if (idNoMap.get("authCardName").equals("0")) {
				result.put("msg", "认证成功!");
				result.put("code", "1");
				JSONUtils.printObject(result);
				return null;
			}
		}
		
		long longresult = homeInfoSettingService.queryTrealName(name, cardNo);
		if (longresult!=0) {
			result.put("msg", "认证成功!");
			result.put("code", "1");
			JSONUtils.printObject(result);
			return null;
		}
		
		result = pyRealName(name,cardNo);
		JSONUtils.printObject(result);
		return null;
	}
	
	
	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public BecomeToFinanceService getBecomeFinanceService() {
		return becomeFinanceService;
	}

	public void setBecomeFinanceService(
			BecomeToFinanceService becomeFinanceService) {
		this.becomeFinanceService = becomeFinanceService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
		this.bbsRegisterService = bbsRegisterService;
	}

	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(
			FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}
	
	private Map<String, String> getJson() {
		InputStream inputStream = null;
		try {
			inputStream = request().getInputStream();
			byte[] buffer = new byte[512];
			int len = -1;
			StringBuffer stringBuffer = new StringBuffer();
			while ((len = inputStream.read(buffer)) != -1) {
				String s = new String(buffer, 0, len, "utf-8");
				stringBuffer.append(s);
				s = null;
			}
			JSONObject obj = JSONObject.fromObject(stringBuffer.toString());
			System.out.println("json===========" + obj);
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) JSONObject.toBean(
					obj, HashMap.class);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (inputStream!=null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	private HSFundService hsFundService;
	
	public void setHsFundService(HSFundService hsFundService) {
		this.hsFundService = hsFundService;
	}

	public String hr_fund() throws Exception{
		try {
			Map<String, String> data = getJson();
			String token="";
			if (data!=null && !data.isEmpty() && data.containsKey("ticket")) {//优先自己传入
				token=data.get("ticket");
			}
			if (StringUtils.isBlank(token)) {//会话获取
				token=queryToken(userService);
			}
			String result = hsFundService.hr_fund(data, token);
			JSONUtils.printObject(result);
		} catch (Exception e) {
			Map<String,String> error = new HashMap<String, String>();
			error.put("code", "01");
			error.put("description", "系统繁忙，请稍候再试...");
			error.put("data",null);
			JSONUtils.printObject(error);
		}
		return null;
	}
}
