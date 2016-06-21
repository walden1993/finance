package com.sp2p.action.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.shove.Convert;
import com.shove.config.AlipayConfig;
import com.shove.config.XinfupayConfig;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.services.AlipayService;
import com.shove.util.AlipayNotify;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.shove.web.util.TdMerSign;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Response;
import com.sp2p.pay.llpay.client.config.PartnerConfig;
import com.sp2p.pay.llpay.client.config.ServerURLConfig;
import com.sp2p.pay.llpay.client.conn.HttpRequestSimple;
import com.sp2p.pay.llpay.client.utils.BaseHelper;
import com.sp2p.pay.llpay.client.utils.LLPayUtil;
import com.sp2p.pay.llpay.client.utils.Md5Algorithm;
import com.sp2p.pay.llpay.client.vo.PayDataBean;
import com.sp2p.pay.llpay.client.vo.PayOrder;
import com.sp2p.pay.llpay.client.vo.RetBean;
import com.sp2p.pay.llpay.client.vo.UserCardInfo;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RechargeDetailService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMessageService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.DateUtil;
import com.sun.org.apache.commons.collections.MapUtils;

public class OnlinePaymentAppAction extends BaseAppAction {

	private static Log log = LogFactory.getLog(OnlinePaymentAppAction.class);
	private RechargeService rechargeService;
	private String urlParam = "";// 接口拼接的参数
	private SelectedService selectedService;
	private SendMessageService sendMessageService;
	private UserService userService;
	private FundManagementService fundManagementService;
	private RechargeDetailService rechargeDetailService;
	private HomeInfoSettingService homeInfoSettingService;
	
	
	
	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public RechargeDetailService getRechargeDetailService() {
		return rechargeDetailService;
	}

	public void setRechargeDetailService(
			RechargeDetailService rechargeDetailService) {
		this.rechargeDetailService = rechargeDetailService;
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

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}

	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	// APP充值
	public String appPayment() throws IOException {
		return hrQuickPay();
		/*
		Response response = new Response();
		try {

			//JSONObject jsonMap = new JSONObject();

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

			// String card = infoMap.get("cardNo");
			String money = appInfoMap.get("recharge_amount");
			// String no_agree = infoMap.get("no_agree");

			// 生成商户订单

			if (StringUtils.isBlank(money)) {// 判断是否为空
				response.failure("请输入充值金额");
				JSONUtils.printObject(response);
				return null;
			}

			// 充值金额只能是整数

			if (money.indexOf(".") > 0) {
				response.failure("充值金额必须为正整");
				JSONUtils.printObject(response);
				return null;
			}

			// 通过卡bin查询卡号是否正确，暂时不考虑卡跟金额是否正确
			
			 * if (StringUtils.isBlank(no_agree)) { if
			 * (StringUtils.isBlank(card)) { jsonMap.put("error", 2);
			 * jsonMap.put("msg", "请输入银行卡号"); JSONUtils.printObject(jsonMap);
			 * return null; }
			 * 
			 * HttpRequestSimple request = HttpRequestSimple.getInstance();
			 * com.alibaba.fastjson.JSONObject reqObj = new
			 * com.alibaba.fastjson.JSONObject(); reqObj.put("oid_partner",
			 * PartnerConfig.OID_PARTNER); reqObj.put("card_no", card);
			 * reqObj.put("sign_type", PartnerConfig.SIGN_TYPE); String sign =
			 * LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
			 * PartnerConfig.MD5_KEY); reqObj.put("sign", sign); String reqJSON
			 * = reqObj.toString(); String resultJson =
			 * request.postSendHttp(ServerURLConfig.QUERY_BANKCARD_URL,
			 * reqJSON); log.info(resultJson); UserCardInfo retBean =
			 * JSON.parseObject(resultJson, UserCardInfo.class); if
			 * (retBean==null || !"0000".equals(retBean.getRet_code())) {
			 * jsonMap.put("error", 2); jsonMap.put("msg", "请输入正确的银行卡号");
			 * JSONUtils.printObject(jsonMap); return null; }
			 * paramMap.put("bankCode", retBean.getBank_name()); }
			 

			BigDecimal moneyDecimal;
			moneyDecimal = new BigDecimal(money);
			
			 * int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));//
			 * 最小金额为0.01元 if (temp < 0) { jsonMap.put("error", 3);
			 * jsonMap.put("msg", "最小金额为1元"); JSONUtils.printObject(jsonMap);
			 * return null; }
			 

			
			 * PayMessage payMessage = new PayMessage();
			 * payMessage.setMerchantID(“88888888”);
			 * payMessage.setMerchantName(“测试商户名”);
			 * payMessage.setUserID(“13800138000”);
			 * payMessage.setOrderNumber(“111111”);
			 * payMessage.setProductname(“测试商品名”);
			 * payMessage.setTransamt(“0.1”);
			 * payMessage.setOrderdate(“20150917105222”);
			 * payMessage.setNotifyUrl(“http://www.xxx.com”);
			 * payMessage.setMD5key(“123456”);
			 
			// 生成订单
			paramMap.put("rechargeMoney", moneyDecimal + "");
			paramMap.put("userId", userId + "");
			paramMap.put("result", "0");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sf.format(new Date());
			paramMap.put("addTime", date);
			// paramMap.put("bankCode", bankCode+"_"+typeCode);
			Map<String, String> result = rechargeService.addRecharge(paramMap,
					8);
			paramMap.clear();// 清空

			long nunber = Convert.strToInt(result.get("result"), -1);
			if (nunber != -1) {
				String Prdordno = rechargeDetailService.getIn_orderNo(nunber,
						userId); // 订单编号 orderNo = nowTime + "_" + recharId +"_"
									// + userId;
				// 生成订单
				//paramMap.put("transamt", moneyDecimal + "");
				// paramMap.put("md5key", XinfupayConfig.MD5key);
				paramMap.put("hr_md5_key", XinfupayConfig.MD5key);
				//paramMap.put("orderdate",
					//	DateUtil.YYYYMMDDHHMMSS.format(new Date()));
				// paramMap.put("merchantID", XinfupayConfig.MerNo);
				paramMap.put("hr_merchant_id", XinfupayConfig.MerNo);
				paramMap.put("merchant_name", XinfupayConfig.Prdordnam);
				//paramMap.put("userID", userId + "");
				paramMap.put("recharge_no", Prdordno);
				paramMap.put("product_name", XinfupayConfig.Prdordnam);
				paramMap.put("hr_notify_url", XinfupayConfig.Notify_url);
				response.success(paramMap);
			} else {
				response.failure("充值失败");
			}

			JSONUtils.printObject(response);

			// ip地址
			
			 * String ipAddress = ServletUtils.getRemortIp();
			 * paramMap.put("ipAddress", ipAddress);
			 * 
			 * Map<String, String> result =
			 * rechargeService.addRecharge(paramMap,9);// 调用存储过程,9表示连连支付
			 * 
			 * int recharId = Convert.strToInt(result.get("result"), -1); if
			 * (recharId != -1) { rechargeService.updateRechargeDetail(paramMap,
			 * recharId); //订单编号 orderNo = nowTime + "_" + recharId +"_" +
			 * userId; String Prdordno =
			 * rechargeDetailService.getIn_orderNo(recharId,userId); //商户的用户唯一编号
			 * StringBuffer user_id = new StringBuffer();
			 * user_id.append("S").append(uid); user_id.reverse(); Map<String,
			 * String> map = userService.queryPersonById(uid); String id_no="";
			 * String acct_name=""; if (map!=null) { id_no = map.get("idNo");
			 * acct_name = map.get("realName"); } //创建订单 String payOrder =
			 * createPayOrder
			 * (Prdordno,money,card,user_id.toString(),id_no,acct_name
			 * ,no_agree); log.info(payOrder); jsonMap.put("payOrder",
			 * payOrder); jsonMap.put("error", "-1"); jsonMap.put("msg", "成功");
			 * } else { jsonMap.put("error", 4); jsonMap.put("msg", "充值失败"); }
			 * 
			 * JSONUtils.printObject(jsonMap);
			 
		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;*/
	}
	
	/**
	 * 华融快捷支付
	 * @return
	 * @throws IOException 
	 */
	public String hrQuickPay() throws IOException{
		Response response = new Response();
		try {
			//JSONObject jsonMap = new JSONObject();
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

			// String card = infoMap.get("cardNo");
			String money = appInfoMap.get("recharge_amount");//充值金额
			String identify_code = appInfoMap.get("identify_code");//验证码
			String card_id = appInfoMap.get("card_id");//卡ID
			String business_pwd = appInfoMap.get("business_pwd");//交易密码
			String md5_code = appInfoMap.get("md5_code");//短信md5_code
			// String no_agree = infoMap.get("no_agree");

			// 生成商户订单

			if (StringUtils.isBlank(money)) {// 判断是否为空
				response.failure("请输入充值金额");
				JSONUtils.printObject(response);
				return null;
			}else {
				if (!NumberUtils.isNumber(money)) {
					response.failure("充值金额只能是数字");
					JSONUtils.printObject(response);
					return null;
				}
			}
			
			if (StringUtils.isBlank(business_pwd)) {// 判断是否为空
				response.failure("请输入交易密码");
				JSONUtils.printObject(response);
				return null;
			}
			
			if ("1".equals(IConstants.ENABLED_PASS)) {
				business_pwd = com.shove.security.Encrypt.MD5(business_pwd.trim());
			} else {
				business_pwd = com.shove.security.Encrypt.MD5(business_pwd.trim()
						+ IConstants.PASS_KEY);
			}
			String dealpwd = userService.queryUserById(userId).get("dealpwd");
			if (!business_pwd.equals(dealpwd)) {
				response.failure("交易密码不正确");
				JSONUtils.printObject(response);
				return null;
			}
			
			
			if (StringUtils.isBlank(identify_code)) {// 判断是否为空
				response.failure("请输入验证码");
				JSONUtils.printObject(response);
				return null;
			}
			
			if (StringUtils.isBlank(md5_code) || StringUtils.isBlank(card_id)) {
				response.failure("参数异常，请重新操作");
				JSONUtils.printObject(response);
				return null;
			}
			
			//验证码是否正确,以及是否有效
			String md5 = Encrypt.MD5(mobile_tel + identify_code);
			// String phone = session().getAttribute("phone") +"";
			if (!md5.equals(md5_code)) {
				response.failure("验证码不正确");
				JSONUtils.printObject(response);
				return null;
			}

			boolean expire = userService.querySendTime(mobile_tel, identify_code);
			if (expire) {
				response.failure("验证码已过期");
				JSONUtils.printObject(response);
				return null;
			}
			
			//查询卡号是否存在
			Map<String, String> bankmap = userService.queryUserBankCard(userId, Convert.strToLong(card_id, -1L));
			String cardNo = "";
			if (bankmap==null || bankmap.isEmpty()) {
				response.failure("您尚未绑定银行卡，请绑定银行卡后再充值");
				JSONUtils.printObject(response);
				return null;
			}else {
				cardNo = MapUtils.getString(bankmap, "cardNo");
			}
			
			//查询是否实名
			boolean isAuthentication = userService.isAuthentication(userId);
			Map<String, String> person = userService.queryPersonById(userId);
			String realName = "";
			String idNo = "";
			if (!isAuthentication) {
				response.failure("您尚未实名，请实名后再充值");
				JSONUtils.printObject(response);
				return null;
			}else {
				realName = MapUtils.getString(person, "realName");
				idNo = MapUtils.getString(person, "idNo");
			}
			// 充值金额只能是整数

			/*if (money.indexOf(".") > 0) {
				response.failure("充值金额必须为正整");
				JSONUtils.printObject(response);
				return null;
			}*/

			// 通过卡bin查询卡号是否正确，暂时不考虑卡跟金额是否正确
			/*
			 * if (StringUtils.isBlank(no_agree)) { if
			 * (StringUtils.isBlank(card)) { jsonMap.put("error", 2);
			 * jsonMap.put("msg", "请输入银行卡号"); JSONUtils.printObject(jsonMap);
			 * return null; }
			 * 
			 * HttpRequestSimple request = HttpRequestSimple.getInstance();
			 * com.alibaba.fastjson.JSONObject reqObj = new
			 * com.alibaba.fastjson.JSONObject(); reqObj.put("oid_partner",
			 * PartnerConfig.OID_PARTNER); reqObj.put("card_no", card);
			 * reqObj.put("sign_type", PartnerConfig.SIGN_TYPE); String sign =
			 * LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
			 * PartnerConfig.MD5_KEY); reqObj.put("sign", sign); String reqJSON
			 * = reqObj.toString(); String resultJson =
			 * request.postSendHttp(ServerURLConfig.QUERY_BANKCARD_URL,
			 * reqJSON); log.info(resultJson); UserCardInfo retBean =
			 * JSON.parseObject(resultJson, UserCardInfo.class); if
			 * (retBean==null || !"0000".equals(retBean.getRet_code())) {
			 * jsonMap.put("error", 2); jsonMap.put("msg", "请输入正确的银行卡号");
			 * JSONUtils.printObject(jsonMap); return null; }
			 * paramMap.put("bankCode", retBean.getBank_name()); }
			 */

			/*BigDecimal moneyDecimal;
			moneyDecimal = new BigDecimal(money);*/
			/*
			 * int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));//
			 * 最小金额为0.01元 if (temp < 0) { jsonMap.put("error", 3);
			 * jsonMap.put("msg", "最小金额为1元"); JSONUtils.printObject(jsonMap);
			 * return null; }
			 */

			/*
			 * PayMessage payMessage = new PayMessage();
			 * payMessage.setMerchantID(“88888888”);
			 * payMessage.setMerchantName(“测试商户名”);
			 * payMessage.setUserID(“13800138000”);
			 * payMessage.setOrderNumber(“111111”);
			 * payMessage.setProductname(“测试商品名”);
			 * payMessage.setTransamt(“0.1”);
			 * payMessage.setOrderdate(“20150917105222”);
			 * payMessage.setNotifyUrl(“http://www.xxx.com”);
			 * payMessage.setMD5key(“123456”);
			 */
			// 生成订单
			paramMap.put("rechargeMoney", money + "");
			paramMap.put("userId", userId + "");
			paramMap.put("result", "0");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sf.format(new Date());
			paramMap.put("addTime", date);
			// paramMap.put("bankCode", bankCode+"_"+typeCode);
			Map<String, String> result = rechargeService.addRecharge(paramMap,
					8);
			paramMap.clear();// 清空

			long nunber = Convert.strToInt(result.get("result"), -1);
			if (nunber != -1) {
				String Prdordno = rechargeDetailService.getIn_orderNo(nunber,
						userId); // 订单编号 orderNo = nowTime + "_" + recharId +"_"
									// + userId;
				// 生成订单
				//paramMap.put("transamt", moneyDecimal + "");
				// paramMap.put("md5key", XinfupayConfig.MD5key);
				paramMap.put("hr_md5_key", XinfupayConfig.MD5key);
				//paramMap.put("orderdate",
					//	DateUtil.YYYYMMDDHHMMSS.format(new Date()));
				// paramMap.put("merchantID", XinfupayConfig.MerNo);
				paramMap.put("hr_merchant_id", XinfupayConfig.MerNo);
				paramMap.put("merchant_name", XinfupayConfig.Prdordnam);
				//paramMap.put("userID", userId + "");
				paramMap.put("recharge_no", Prdordno);
				paramMap.put("product_name", XinfupayConfig.Prdordnam);
				paramMap.put("hr_notify_url", XinfupayConfig.Notify_url);
				
				DecimalFormat decimalFormat = new DecimalFormat("#.00");
				
				String payInfo = quickPay(realName, cardNo, idNo, mobile_tel,decimalFormat.format(Double.valueOf(money)), Prdordno);
				JSONObject json= JSONObject.fromObject(payInfo);
				if ("1".equals(json.get("ordsts"))) {
					paramMap.clear();
					paramMap.put("reacharge_amount", money);
					response.success(paramMap);
				}else {
					response.failure(json.getString("message"));
				}
			} else {
				response.failure("充值失败");
			}
			JSONUtils.printObject(response);
			userService.overSendLog(mobile_tel, "5", identify_code);
			
			// ip地址
			/*
			 * String ipAddress = ServletUtils.getRemortIp();
			 * paramMap.put("ipAddress", ipAddress);
			 * 
			 * Map<String, String> result =
			 * rechargeService.addRecharge(paramMap,9);// 调用存储过程,9表示连连支付
			 * 
			 * int recharId = Convert.strToInt(result.get("result"), -1); if
			 * (recharId != -1) { rechargeService.updateRechargeDetail(paramMap,
			 * recharId); //订单编号 orderNo = nowTime + "_" + recharId +"_" +
			 * userId; String Prdordno =
			 * rechargeDetailService.getIn_orderNo(recharId,userId); //商户的用户唯一编号
			 * StringBuffer user_id = new StringBuffer();
			 * user_id.append("S").append(uid); user_id.reverse(); Map<String,
			 * String> map = userService.queryPersonById(uid); String id_no="";
			 * String acct_name=""; if (map!=null) { id_no = map.get("idNo");
			 * acct_name = map.get("realName"); } //创建订单 String payOrder =
			 * createPayOrder
			 * (Prdordno,money,card,user_id.toString(),id_no,acct_name
			 * ,no_agree); log.info(payOrder); jsonMap.put("payOrder",
			 * payOrder); jsonMap.put("error", "-1"); jsonMap.put("msg", "成功");
			 * } else { jsonMap.put("error", 4); jsonMap.put("msg", "充值失败"); }
			 * 
			 * JSONUtils.printObject(jsonMap);
			 */
		} catch (Exception e) {
			log.error(e);
			response.failure("服务器异常");
			JSONUtils.printObject(response);
		}
		return null;
	}

	/**
	 * 创建移动订单
	 * 
	 * @return
	 */
	private String createPayOrder(String no_order, String money, String cardNo,
			String user_id, String id_no, String acct_name, String no_agree) {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String timeString = dataFormat.format(date);
		PayOrder order = new PayOrder();
		// TODO busi_partner 是指商户的业务类型，"101001"为虚拟商品销售，详情请参考接口说明书
		order.setBusi_partner("101001");
		order.setNo_order(no_order);
		// 订单时间
		order.setDt_order(timeString);
		order.setName_goods(PartnerConfig.GOODSNAME);
		order.setNotify_url(PartnerConfig.NOTIFY_URL);
		order.setSign_type(PayOrder.SIGN_TYPE_MD5);
		// 订单有效期，单位分钟，请参考文档填写
		order.setValid_order("30");
		order.setUser_id(user_id);
		// 身份证
		order.setId_no(id_no);
		// 真实姓名
		order.setAcct_name(acct_name);
		order.setMoney_order(money);
		// 银行卡卡号，该卡首次支付时必填
		if (StringUtils.isBlank(no_agree)) {
			order.setCard_no(cardNo);
		} else {
			// 银行卡历次支付时填写，可以查询得到，协议号匹配会进入SDK，可能后期会有用
			order.setNo_agree(no_agree);
		}
		// 修改标记非必须0-可以修改，默认为 0 1-不允许修改 与 id_type,id_no,acct_name
		// 配合使用，如果该用户在商户系统已经实名认证过了，则在绑定银行卡的输入信息不能修改， 否则可以修改银行卡号 card_no 否
		// 变(15-19) 银行卡号前置， 卡
		order.setFlag_modify("0");
		// 风险控制参数，没必要传入的，请不要设置这个条目，
		// order.setRisk_item(constructRiskItem());
		String sign = "";
		// 商户号
		order.setOid_partner(PartnerConfig.OID_PARTNER);
		// 对签名原串进行排序，并剔除不需要签名的串。
		String content = BaseHelper.sortParam(order);
		// MD5 签名方式, 签名方式包括两种，一种是MD5，一种是RSA 这个在商户站管理里有对验签方式和签名Key的配置。
		sign = Md5Algorithm.getInstance().sign(content, PartnerConfig.MD5_KEY);
		// RSA 签名方式
		// sign = Rsa.sign(content, PartnerConfig.TRADER_PRI_KEY);
		order.setSign(sign);
		return BaseHelper.toJSONString(order);
	}

	// 在线充值
	public String alipayPayment() throws Exception {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String money = appInfoMap.get("money");

			if (StringUtils.isBlank(money)) {// 判断是否为空
				return INPUT;
			}
			BigDecimal moneyDecimal;
			moneyDecimal = new BigDecimal(money);
			int temp = moneyDecimal.compareTo(new BigDecimal("0.01"));// 最小金额为0.01元
			if (temp < 0) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "最小金额为0.01元");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 生成订单
			paramMap.put("rechargeMoney", moneyDecimal + "");
			paramMap.put("userId", userId + "");
			paramMap.put("result", "0");
			paramMap.put("addTime", DateUtil.dateToString(new Date()));

			// ip地址
			String ipAddress = ServletUtils.getRemortIp();
			paramMap.put("ipAddress", ipAddress);

			Map<String, String> result = rechargeService.addRecharge(paramMap,
					2);// 调用存储过程

			int nunber = Convert.strToInt(result.get("result"), -1);
			if (nunber != -1) {
				Map<String, String> map = rechargeService
						.getRechargeDetail(nunber);

				String html = createUrl(map.get("rechargeNumber"), "在线充值",
						result + "_" + userId, moneyDecimal);// paymentId_orderId_userId:支付类型(在线支付/在线充值)_订单编号/_用户编号
				jsonMap.put("html", html);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
			} else {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "冲值失败");
			}

			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "4");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);

		}
		return null;
	}

	private String createUrl(String out_trade_no, String body,
			String extraCommonParam, BigDecimal money) throws Exception {
		log.info("12");
		// 组装接口参数，并进行加密
		Map<String, String> sPara = new HashMap<String, String>();
		// sPara.put("body","body");//订单编号
		sPara.put("_input_charset", AlipayConfig.input_charset);
		sPara.put("subject", IConstants.PRO_GLOBLE_NAME + "充值编号:"
				+ out_trade_no);
		sPara.put("total_fee", money.toString() + "");
		sPara.put("service", "create_direct_pay_by_user");
		sPara.put("notify_url", AlipayConfig.notify_url);
		sPara.put("partner", AlipayConfig.partner);
		sPara.put("seller_email", AlipayConfig.seller_email);
		sPara.put("out_trade_no", out_trade_no);
		sPara.put("payment_type", "1");
		sPara.put("return_url", AlipayConfig.return_url);
		extraCommonParam = com.shove.security.Encrypt.encryptSES(
				extraCommonParam, AlipayConfig.ses_key);
		extraCommonParam = URLEncoder.encode(extraCommonParam, "utf-8");
		sPara.put("extra_common_param", extraCommonParam);
		String html = AlipayService.create_direct_pay_by_user(sPara);
		return html;
	}

	// 回调方法：明

	public String alipayReceive() throws Exception {
		log.info("alipayReceive");
		Map<String, String> params = new HashMap<String, String>();//
		Map<String, String> jsonMap = new HashMap<String, String>();

		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			// trade_no订单流水号
			// notify_time支付回调时间
			Map requestParams = (Map<String, String>) JSONObject.toBean(
					JSONObject.fromObject(appInfoMap.get("params")),
					HashMap.class);
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			boolean verify_result = AlipayNotify.verify(params);// 验证参数是否是支付宝那边返回过来的。
			if (!verify_result) {
				jsonMap.put("error", "1");
				jsonMap.put("url", "index.do");
				jsonMap.put("msg", "支付失败");
				JSONUtils.printObject(jsonMap);
				return null;
				// createHelpMessage("支付失败！", "返回首页", "index.do");
			}
			String extra_common_param = appInfoMap.get("extra_common_param");// 获得参数信息
			// 支付类型_订单编号/金钱_用户编号
			if (StringUtils.isBlank(extra_common_param)) {
				jsonMap.put("error", "2");
				jsonMap.put("url", "index.do");
				jsonMap.put("msg", "支付失败");
				JSONUtils.printObject(jsonMap);
				return null;
				// createHelpMessage("支付失败！", "返回首页", "index.do");
			}
			extra_common_param = URLDecoder.decode(extra_common_param, "utf-8");
			extra_common_param = com.shove.security.Encrypt.decryptSES(
					extra_common_param, AlipayConfig.ses_key);
			String[] extraCommonParam = extra_common_param.split("_");
			if (extraCommonParam == null || extraCommonParam.length != 2) {
				// 通过"_"进行截取，判断是否符合规范
				jsonMap.put("error", "3");
				jsonMap.put("url", "index.do");
				jsonMap.put("msg", "支付失败");
				JSONUtils.printObject(jsonMap);
				return null;
				// createHelpMessage("支付失败！", "返回首页", "index.do");
			}
			String sellerEmail = appInfoMap.get("seller_email");// 商户邮箱
			if (!sellerEmail.equals(AlipayConfig.seller_email)) {// 比较商户邮箱看是否符合
				jsonMap.put("error", "4");
				jsonMap.put("url", "index.do");
				jsonMap.put("msg", "支付失败");
				JSONUtils.printObject(jsonMap);
				return null;
				// createHelpMessage("支付失败！", "返回首页", "index.do");
			}
			String paynumber = URLDecoder.decode(appInfoMap.get("trade_no"),
					"utf-8");
			// 支付宝编号
			String notify_time = URLDecoder.decode(
					appInfoMap.get("notify_time"), "utf-8");// 支付宝编号
			// String paybank = URLDecoder.decode(request(""), "utf-8");//支付银行
			String paybank = null;// 支付银行
			if (StringUtils.isBlank(paybank)) {// 如果没有银行编号说明是支付宝直接支付的
				paybank = "支付宝余额支付";
			}
			String buyer_email = URLDecoder.decode(
					appInfoMap.get("buyer_email"), "utf-8");// 支付银行
			String buyer_id = URLDecoder.decode(appInfoMap.get("buyer_id"),
					"utf-8");// 支付银行
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("extraCommonParam", extraCommonParam);
			map.put("total_fee", new BigDecimal(appInfoMap.get("total_fee")));
			map.put("paynumber", paynumber);
			map.put("bankName", paybank);
			map.put("buyer_email", buyer_email);
			map.put("buyer_id", buyer_id);
			map.put("notify_time", notify_time);
			int returnId = -1;
			try {
				returnId = rechargeService.userPay(map);
				String[] extraCommon = (String[]) map.get("extraCommonParam");
				Long userId = Convert.strToLong(extraCommon[1], -1);// 获得用户编号
				userService.updateSign(userId);// 更换校验码
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			} catch (DataException e) {
				log.error(e);
				e.printStackTrace();
				throw e;
			}
			HttpServletResponse httpServletResponse = ServletActionContext
					.getResponse();
			httpServletResponse.setCharacterEncoding("utf-8");
			String msg = "";
			if (returnId < 0) {
				if (returnId == -1) {
					msg = "用户不存在";
				} else if (returnId == -2) {
					msg = "支付号错误";
				} else if (returnId == -3) {
					msg = "此笔支付记录已经被处理过";
				} else if (returnId == -4) {
					msg = "充值金额与本地记录中的金额不一致";
				} else if (returnId == -5) {
					msg = "充值金额错误";
				} else if (returnId == -6) {
					msg = "支付详细不存在";
				} else if (returnId == -7) {
					msg = "订单支付明细，状态修改失败。";
				} else {
					msg = "操作错误！";
				}
				jsonMap.put("error", "5");
				jsonMap.put("url", "index.do");
				jsonMap.put("msg", msg);
				JSONUtils.printObject(jsonMap);
				return null;
				// createHelpMessage(msg, "返回首页", "index.do");
			} else {
				// 根据用户的通知设置，进行邮件、短信、站内信的通知
				Long userId = Convert.strToLong(extraCommonParam[1], -1);// 获得用户编号
				sendMessage(userId,
						Convert.strToDouble(request("total_fee"), 0), 0);
				// ------------------

			}

			// createHelpMessage(msg + "", "返回首页", "index.do");
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			jsonMap.put("error", "6");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);

		}
		return null;
	}

	private void sendMessage(Long userId, double total, double money)
			throws Exception {
		try {
			String title = "资金变动提醒";
			// 查找通知类型的通知状态
			List<Map<String, Object>> lists = selectedService.queryNoticeMode(
					userId, IConstants.NOTICE_MODE_4);
			if (lists != null && lists.size() > 0) {
				String content = "你已成功从" + IConstants.PRO_GLOBLE_NAME + "充值￥"
						+ total + "元，扣除手续费后到账金额为￥" + money + "元，请注意查收!";
				// [通知方式(1 邮件 2 站内信 3 短信]
				if (lists.get(0).get("flag").toString()
						.equals(String.valueOf(IConstants.NOTICE_ON))) {
					sendMessageService.emailSend(title, content, userId);
				}
				if (lists.get(1).get("flag").toString()
						.equals(String.valueOf(IConstants.NOTICE_ON))) {
					sendMessageService.mailSend(title, content, userId);
				}
				if (lists.get(2).get("flag").toString()
						.equals(String.valueOf(IConstants.NOTICE_ON))) {
					Map<String, String> userMap = userService
							.queryUserById(userId);
					if (userMap != null) {
						Long result = sendMessageService.noteSend(content,
								userId);

					} else {
						if (money > IConstants.NOTE_CHARGE) {
							Long result = sendMessageService.noteSend(content,
									userId);
							if (result > 0) {// 信息发送成功，更新资金记录表
								Long result2 = fundManagementService
										.updateFundrecord(userId,
												IConstants.NOTE_CHARGE,
												IConstants.WITHDRAW);
								if (result2 > 0) {
									Map<String, String> uMap = userService
											.queryUserById(userId);
									Map<String, String> map = new HashMap<String, String>();
									map.put("handleSum", String
											.valueOf(IConstants.NOTE_CHARGE));
									map.put("usableSum", uMap.get("usableSum"));
									map.put("freezeSum", uMap.get("freezeSum"));
									map.put("dueinSum", uMap.get("dueinSum"));
									map.put("dueOutSum", uMap.get("dueOutSum"));
									map.put("fundMode", "扣除短信服务费");
									map.put("remarks", "扣除短信服务费");

									fundManagementService.addFundRecord(userId,
											map);
								}
							}
						}
					}
				}
			}
			userService.updateSign(userId);// 更换校验码
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	// 回调方法：暗
	public String alipayNotify() throws Exception {
		log.info("alipayNotify");
		return alipayReceive();

	}

	// 连连支付异步回调
	public String llpayNotify() throws Exception {
		log.info("llpayNotify");
		response().setCharacterEncoding("UTF-8");
		System.out.println("进入支付异步通知数据接收处理");
		RetBean retBean = new RetBean();
		String reqStr = LLPayUtil.readReqStr(request());
		if (LLPayUtil.isnull(reqStr)) {
			retBean.setRet_code("9999");
			retBean.setRet_msg("交易失败");
			response().getWriter().write(JSON.toJSONString(retBean));
			response().getWriter().flush();
			return null;
		}
		System.out.println("接收支付异步通知数据：【" + reqStr + "】");
		log.info("---------" + reqStr + "--------------");

		String paybank = "";//
		if (StringUtils.isBlank(paybank)) {// 无法查询那家银行支付
			// paybank = "新付支付充值";
			paybank = "融E付";
		}

		JSONObject object = JSONObject.fromObject(reqStr);

		if ("1".equals(object.getString("ordsts"))) {
			double ordamtDouble = Convert.strToDouble(
					object.getString("merActAmt"), 0) / 100;// 金额
			String arrays[] = object.getString("merOrderId").split("_");
			long userId = Convert.strToLong(arrays[2], 0);
			log.info(userId);
			Map<String, String> resultMap = rechargeService.addUseraddmoney(
					userId, ordamtDouble, arrays[1], paybank);
			String result = resultMap.get("result");
			String description = resultMap.get("description");

			userService.updateSign(userId);// 更换校验码
			if (!"0".endsWith(result)) {
				log.info("6--result:" + result + ";description:" + description);
			}
		}

		return null;

		/*
		 * try { if (!LLPayUtil.checkSign(reqStr,null,PartnerConfig.MD5_KEY)) {
		 * retBean.setRet_code("9999"); retBean.setRet_msg("交易失败");
		 * response().getWriter().write(JSON.toJSONString(retBean));
		 * response().getWriter().flush(); System.out.println("支付异步通知验签失败");
		 * return null; } } catch (Exception e) {
		 * System.out.println("异步通知报文解析异常：" + e); retBean.setRet_code("9999");
		 * retBean.setRet_msg("交易失败");
		 * response().getWriter().write(JSON.toJSONString(retBean));
		 * response().getWriter().flush(); return null; }
		 * 
		 * 
		 * 
		 * // 解析异步通知对象 PayDataBean payDataBean = JSON.parseObject(reqStr,
		 * PayDataBean.class); // TODO:更新订单，后续处理 String [] on_order =
		 * payDataBean.getNo_order().split("_"); int returnId = -1; try {
		 * returnId = rechargeService.userPay(payDataBean); Long userId =
		 * Convert.strToLong(on_order[2], -1);// 获得用户编号
		 * userService.updateSign(userId);//更换校验码 } catch (SQLException e) {
		 * log.error(e); e.printStackTrace(); throw e; } catch (DataException e)
		 * { log.error(e); e.printStackTrace(); throw e; } HttpServletResponse
		 * httpServletResponse = ServletActionContext .getResponse();
		 * httpServletResponse.setCharacterEncoding("utf-8"); String msg = "";
		 * if (returnId < 0) { if (returnId == -1) { msg = "用户不存在"; } else if
		 * (returnId == -2) { msg = "支付号错误"; } else if (returnId == -3) { msg =
		 * "此笔支付记录已经被处理过"; } else if (returnId == -4) { msg =
		 * "充值金额与本地记录中的金额不一致"; } else if (returnId == -5) { msg = "充值金额错误"; }
		 * else if (returnId == -6) { msg = "支付详细不存在"; } else if (returnId ==
		 * -7) { msg = "订单支付明细，状态修改失败。"; } else { msg = "操作错误！"; }
		 * log.info(msg); return null; } else { 在储存过程中已完成了短信发送 //
		 * 根据用户的通知设置，进行邮件、短信、站内信的通知 Long userId =Convert.strToLong(on_order[2],
		 * -1);// 获得用户编号 double money =
		 * Convert.strToDouble(payDataBean.getMoney_order(), 0);
		 * sendMessage(userId,money , money); // ------------------ }
		 * 
		 * //交易成功了。可是否出商户用户信息保存 //首先更新卡协议号查询数据库是否已经存在这种卡了 //商户的用户唯一编号
		 * StringBuffer user_id = new StringBuffer();
		 * user_id.append("S").append(Convert.strToLong(on_order[2], -1));
		 * user_id.reverse(); long count =
		 * rechargeService.queryCardByProtocol(payDataBean
		 * .getNo_agree(),user_id.toString());
		 * log.info("=========================count============"+count);
		 * if(count<=0){ try { HttpRequestSimple request =
		 * HttpRequestSimple.getInstance(); com.alibaba.fastjson.JSONObject
		 * reqObj = new com.alibaba.fastjson.JSONObject();
		 * reqObj.put("oid_partner", PartnerConfig.OID_PARTNER);
		 * reqObj.put("user_id", user_id); reqObj.put("offset", "0");
		 * reqObj.put("no_agree", payDataBean.getNo_agree());
		 * reqObj.put("sign_type", PartnerConfig.SIGN_TYPE); String sign =
		 * LLPayUtil.addSign(reqObj, PartnerConfig.TRADER_PRI_KEY,
		 * PartnerConfig.MD5_KEY); reqObj.put("sign", sign); String reqJSON =
		 * reqObj.toString(); String resultJson =
		 * request.postSendHttp(ServerURLConfig.QUERY_BANKCARD_IN_PROTOCOL,
		 * reqJSON); if (StringUtils.isNotBlank(resultJson)) { JSONObject object
		 * = JSONObject.fromObject(resultJson); String agreement_list =
		 * object.getString("agreement_list");
		 * rechargeService.addCardProtocol(JSON.parseObject(agreement_list,
		 * UserCardInfo.class),user_id.toString()); }else { Map<String, String>
		 * rechargeDeatail =
		 * rechargeDetailService.getRechargeDetail(Convert.strToLong
		 * (on_order[1], -1)); if (rechargeDeatail!=null) { //必填项 UserCardInfo
		 * info = new UserCardInfo();
		 * info.setBank_name(rechargeDeatail.get("bankName")); if
		 * (rechargeDeatail.get("buyerEmail")!=null &&
		 * rechargeDeatail.get("buyerEmail").length()>5) {
		 * info.setCard_no(rechargeDeatail
		 * .get("buyerEmail").substring(rechargeDeatail
		 * .get("buyerEmail").length()-4)); }
		 * info.setNo_agree(payDataBean.getNo_agree());
		 * info.setBank_code(payDataBean.getBank_code()); if
		 * (StringUtils.isNotBlank(info.getNo_agree()) &&
		 * StringUtils.isNotBlank(info.getCard_no())) {
		 * rechargeService.addCardProtocol(info, user_id.toString()); } } } }
		 * catch (Exception e) { e.printStackTrace(); log.error(e); } }
		 * 
		 * retBean.setRet_code("0000"); retBean.setRet_msg("交易成功");
		 * response().getWriter().write(JSON.toJSONString(retBean));
		 * response().getWriter().flush(); System.out.println("支付异步通知数据接收处理成功");
		 * 
		 * return null;
		 */
	}

	/**
	 * @Title: queryCardList
	 * @Description:获取银行卡列表
	 * @return String
	 * @author he
	 * @throws IOException
	 * @date 2015-2-3
	 */
	public String queryCardList() throws IOException {
		try {
			Map<String, String> authMap = getAppAuthMap();
			Long uid = Convert.strToLong(authMap.get("uid"), -1L);
			StringBuffer sbv = new StringBuffer();
			sbv.append("S").append(uid);
			if (uid == -1) {
				paramMap.put("error", "-2");
				paramMap.put("msg", "请重新登录");
			} else {
				List<Map<String, String>> cardList = rechargeService
						.queryCardByCardNo(null, sbv.reverse().toString());
				JSONObject object = new JSONObject();
				object.put("error", "-1");
				object.put("msg", "成功");
				object.put("cardList", cardList);
				JSONUtils.printObject(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
		}
		JSONUtils.printObject(paramMap);
		return null;
	}
	
	
	public  String quickPay(String realName,String cardNo,String idNo,String mobile,String reachargeamount,String orderId) throws Exception {
		HttpRequestSimple request = HttpRequestSimple.getInstance();
		//获取短信验证码
		//String orderId = DateUtil.YYYYMMDDMMHHSSSSS.format(new Date())+"_108";
		System.out.println(orderId);
		//MerchantID&PhoneNumber&OrderNumber&Transamt &I6V8VSSfJlee
		StringBuffer sb_sign = new StringBuffer();
		sb_sign.append(XinfupayConfig.MerNo).append("&").append(mobile).append("&").append(orderId).append("&").append(reachargeamount).append("&").append(XinfupayConfig.MD5key);
		String sign = TdMerSign.MD5Sign(sb_sign.toString()).toUpperCase();		
		List<NameValuePair> list = new LinkedList<NameValuePair>();
		list.add(new BasicNameValuePair("Code", "SendCode"));
		list.add(new BasicNameValuePair("MerchantID", XinfupayConfig.MerNo));
		list.add(new BasicNameValuePair("PhoneNumber", mobile));
		list.add(new BasicNameValuePair("OrderNumber", orderId));
		list.add(new BasicNameValuePair("Transamt", reachargeamount));
		list.add(new BasicNameValuePair("sign", sign));
		list.add(new BasicNameValuePair("Productname", XinfupayConfig.Prdordnam));
		list.add(new BasicNameValuePair("MerchantName", XinfupayConfig.MERCHANT_NAME));
		String responsejson =request.postPramaList(list, XinfupayConfig.xinfupay_quick_sms);
		log.info("responsejson1---"+responsejson);
		
		String Verifycode = JSONObject.fromObject(responsejson).getString("Verifycode");
		
		list.clear();
		//支付请求
		list.add(new BasicNameValuePair("code", "MerQuickPay"));
		list.add(new BasicNameValuePair("mercNo", XinfupayConfig.MerNo));
		list.add(new BasicNameValuePair("merNoType", "0"));
		list.add(new BasicNameValuePair("merOrderId", orderId));
		list.add(new BasicNameValuePair("signType", "MD5"));
		//cardNo&cardID&mobile&MD5key
		StringBuffer sb_signInfo = new StringBuffer();
		sb_signInfo.append(cardNo).append("&").append(idNo).append("&").append(mobile).append("&").append(XinfupayConfig.MD5key);
		String singInfo = TdMerSign.MD5Sign(sb_signInfo.toString()).toUpperCase();
		list.add(new BasicNameValuePair("signInfo", singInfo));
		list.add(new BasicNameValuePair("Verifycode", Verifycode));
		list.add(new BasicNameValuePair("merOrderAmt", reachargeamount));
		list.add(new BasicNameValuePair("merOrderName", XinfupayConfig.MERCHANT_NAME));
		list.add(new BasicNameValuePair("notifyUrl", XinfupayConfig.Notify_url));
		list.add(new BasicNameValuePair("orderTime", DateUtil.YYYYMMDDHHMMSS.format(new Date())));
		list.add(new BasicNameValuePair("cardNo", cardNo));
		list.add(new BasicNameValuePair("cardName", realName));
		list.add(new BasicNameValuePair("cardID", idNo));
		list.add(new BasicNameValuePair("mobile", mobile));
		list.add(new BasicNameValuePair("cardType", "0"));//储蓄卡
		responsejson =request.postPramaList(list, XinfupayConfig.xinfupay_quick_pay);
		log.info("responsejson2---"+responsejson);
		return responsejson;
	}
	
	
	public String alipayNotify_back() throws Exception {
		log.info("alipayNotify_back");
		return alipayNotify_back();

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

}
