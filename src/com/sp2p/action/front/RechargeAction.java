package com.sp2p.action.front;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.ServletUtils;
import com.shove.util.UtilDate;
import com.shove.web.Utility;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_platform_cost;
import com.sp2p.database.Dao.Tables.t_sms;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.service.FinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.service.admin.RechargebankService;
import com.sp2p.util.DateUtil;

public class RechargeAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;
	private RechargeService rechargeService;
	private HomeInfoSettingService homeInfoSettingService;
	private UserService userService;
	private FinanceService financeService;
	private AdminService adminService;
	private RechargebankService rechargebankService;
	private FundManagementService fundManagementService;
	private SysparService sysparService;
	
	

	public SysparService getSysparService() {
        return sysparService;
    }

    public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	private List<Map<String, Object>> potypeList;
	private List<Map<String, Object>> banksList;

	public void setFundManagementService(
			FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	public void setSysparService(SysparService sysparService) {
        this.sysparService = sysparService;
    }

    /**
	 * 充值提现页面初始化
	 * 
	 * @return
	 */
	public String rechargePageInit() {
		return SUCCESS;
	}

	public String rechargeInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Map<String, String> m = new HashMap<String, String>();
		m = userService.queryUserById(user.getId());
		
		if (null != m) {
			if (m.get("password").equals(m.get("dealpwd"))) {
				getOut()
				.print(
						"<script>alert('*您的交易密码和登录密码相同，为了您的帐号安全，请您先修改交易密码! ');window.location.href='securityCent.do?isOn=2';</script>");
				return null;
			}
//			int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
//			if (isApplyPro == 1) {
//				getOut()
//						.print(
//								"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
//				return null;
//
//			}
		}

		boolean re = userService.checkSign(user.getId());
		if (!re) {
			request().getSession().removeAttribute("user");
			request().getSession().invalidate();
			getOut()
					.print(
							"<script>alert('*您的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
			return null;
		}
		String[] vals = moneyVal(user.getId());
        request().setAttribute("usableSum", vals[1]);
		request().setAttribute("realName", user.getRealName());
		request().setAttribute("email", user.getUserName());
		fundManagementService.queryAccountPaymentPage(pageBean);
		List<Map<String, Object>> list = pageBean.getPage();
		//modified by liuliling  for 缓存所有支付方式状态到request 8
		for (Map<String, Object> map : list) {
			if (map.get("nid").equals("gopay")) {
				request().setAttribute("gopay", map.get("status"));
			} else if (map.get("nid").equals("IPS")) {
				request().setAttribute("IPS", map.get("status"));
			} else if(map.get("nid").equals("ref")){
				request().setAttribute("ref", map.get("status"));
			} else if(map.get("nid").equals("xf")){
				request().setAttribute("xf", map.get("status"));
			}
		}
		
		
		try {
            List<Map<String, Object>> minWithDraw = sysparService.querySysparAllChild("*", " selectKey =\"POUNDAGE_MIN\" and deleted=1", "", -1, -1);
            if (minWithDraw!=null && minWithDraw.size()>0 && "POUNDAGE_MIN".equals(minWithDraw.get(0).get("selectKey"))) {
                request().setAttribute("min_withdraw", minWithDraw.get(0).get("selectValue"));// 未投标最低手续费
           }else {
               request().setAttribute("min_withdraw", null);
           }
       }
       catch (SQLException e1) {
           e1.printStackTrace();
       }
		
		
		//查询后台设置的提现比例并显示
		Double costFee = 0.5;
		Connection conn = MySQL.getConnection();
		
		
		try
		{
			Dao.Tables.t_platform_cost tpc = new Dao().new Tables().new t_platform_cost();
			DataSet  ds = tpc.open(conn, " costFee ", "alias='withdrawalFeeRate' and costMode='1'", "id", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			costFee = Convert.strToDouble(ds.tables.get(0).rows.get(0).get("costFee").toString(), 0);
		}
		catch (Exception e)
		{
			costFee=0.5;
		}
		finally
		{
			conn.close();
		}
		request().setAttribute("costFee", costFee);
		
		//上一次充的使用的银行
	    Map<String, String> map = fundManagementService.queryRechargeDedatilLast(user.getId());
	    if (map!=null && map.get("bankName")!=null && !"".equals(map.get("bankName"))) {
            String bankName = map.get("bankName");
            String [] arr =  bankName.split("_");
            request().setAttribute("lastBank", arr[0]);
            request().setAttribute("typeCode", arr[1]);
        }
	    
	    //记录系统是否正在升级中
	    request().setAttribute("paySystemUpgrade", map.get("paySystemUpgrade"));
	    request().setAttribute("upgradeNotes", map.get("upgradeNotes"));
	    
	    if (checkMobile()) {
			return "mobilesuccess";
		}
	    
		return SUCCESS;
	}

	
    /**
     * 提现信息加载 wap版本
     * 
     * @return
     * @throws Exception
     */
    public String withdrawLoadWap() throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 获取用户的信息
        User user = (User) session().getAttribute(IConstants.SESSION_USER);
        Long userId = user.getId();// 获得用户编号
        JSONObject wap = new JSONObject();
        try {

            Map<String, String> m = new HashMap<String, String>();
            m = userService.queryUserById(user.getId());
            if (null != m) {
                if (m.get("password").equals(m.get("dealpwd"))) {
                	wap.put("code", 1);
                	wap.put("msg", "*您的交易密码和登录密码相同，为了您的帐号安全，请您先修改交易密码!");
                	JSONUtils.printObject(wap);
                    return null;
                }
            }

            boolean re = userService.checkSign(userId);
            if (!re) {
                request().getSession().removeAttribute("user");
                request().getSession().invalidate();
                wap.put("code", 2);
            	wap.put("msg", "*你的账号出现异常，请速与管理员联系! ");
            	JSONUtils.printObject(wap);
                return null;
            }
            int authStep = user.getAuthStep();// 需要填写个人资料之后才能申请提现
            int userType = user.getUserType();
            if (authStep < 2) {
                if(userType == 1){
                    Map<String, String> person = userService.queryPersonById(userId);
                    String authCardName = person.get("authCardName");//0表示通过实名认证
                    String mobilePhone  = m.get("mobilePhone");
                    //判断是否已经实名制
                    if (!"0".equals(authCardName)) {//未实名认证
                    	wap.put("code", 3);
                    	wap.put("msg", "*您尚未实名认证，请先进行实名认证后在操作! ");
                    	JSONUtils.printObject(wap);
                        return null;
                    }
                    //判断是否已绑定手机号码
                    if(StringUtils.isBlank(mobilePhone)){
                    	wap.put("code", 4);
                    	wap.put("msg", "*您尚未绑定手机，请先绑定手机后在操作! ");
                    	JSONUtils.printObject(wap);
                        return null;
                    }

                }else{
                	wap.put("code", 5);
                	wap.put("msg", "*请完善企业详细信息! ");
                	JSONUtils.printObject(wap);
                    return null;
                }
            }
            List<Map<String, Object>> lists = rechargeService
                    .withdrawLoad(userId,user.getUserType());
            // 需要加载信息 真实姓名 手机号码 帐户余额 可用余额 冻结总额 提现银行
            String realName = user.getRealName();
            String bindingPhone = null;
            int status = -1;
            String[] vals = moneyVal(userId);

            wap.put("handleSum", vals[0]);
            wap.put("usableSum", vals[1]);
            wap.put("freezeSum", vals[2]);
            
            try {
                 List<Map<String, Object>> minWithDraw = sysparService.querySysparAllChild("*", " selectKey =\"POUNDAGE_MIN\" and deleted=1", "", -1, -1);
                 if (minWithDraw!=null && minWithDraw.size()>0 && "POUNDAGE_MIN".equals(minWithDraw.get(0).get("selectKey"))) {
                     wap.put("handleSum",minWithDraw.get(0).get("selectValue"));// 未投标最低手续费
                }else {
                    wap.put("min_withdraw", "");
                }
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            
            
            Map<String, String> map = sysparService.querySysparChild(" selectValue ", "WITHDRAW_MAX", "", -1, -1);
            if ( map!=null && map.size()>0) {
            	wap.put("min_withdraw", map.get("selectValue"));// 最大充值金额，超过之后要收取手续费
            }else {
            	wap.put("min_withdraw", IConstants.WITHDRAW_MAX);// 最大充值金额，超过之后要收取手续费
            }
            
            if (lists != null && lists.size() > 0) {
                if (lists.get(0).get("bindingPhone") != null) {
                    bindingPhone = lists.get(0).get("bindingPhone").toString();
                }
                if (lists.get(0).get("status") != null) {
                    status = Convert.strToInt(lists.get(0).get("status")
                            .toString(), -1);
                }

                // 如果设置的绑定号码为空，或者绑定的手机号码还未审核通过 则都使用用户注册时的手机号码
                if (bindingPhone == null
                        || status != IConstants.PHONE_BINDING_ON) {
                    bindingPhone = lists.get(0).get("cellPhone") + "";
                }
                // 绑定的银行卡信息单独查询
                List<Map<String, Object>> banks = homeInfoSettingService
                        .querySuccessBankInfoList(userId);
                wap.put("banks", banks);
                wap.put("bindingPhone", bindingPhone);
                
                
                //查询后台设置的提现比例并显示
                Double costFee = 0.5;
                Connection conn = MySQL.getConnection();
                try
                {
                    Dao.Tables.t_platform_cost tpc = new Dao().new Tables().new t_platform_cost();
                    DataSet  ds = tpc.open(conn, " costFee ", "alias='withdrawalFeeRate' and costMode='1'", "id", -1, -1);
                    ds.tables.get(0).rows.genRowsMap();
                    costFee = Convert.strToDouble(ds.tables.get(0).rows.get(0).get("costFee").toString(), 0);
                }
                catch (Exception e)
                {
                    costFee=0.5;
                }
                finally
                {
                    conn.close();
                }
                wap.put("costFee", costFee);
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
        JSONUtils.printObject(wap);
        return null;
    }
	
	
	/**
	 * 提现信息加载
	 * 
	 * @return
	 * @throws Exception
	 */
	public String withdrawLoad() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		//wap提现
		if (request("wap")!=null) {
			return withdrawLoadWap();
		}
		
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号

		try {

			Map<String, String> m = new HashMap<String, String>();
			m = userService.queryUserById(user.getId());
			if (null != m) {
				if (m.get("password").equals(m.get("dealpwd"))) {
					getOut()
					.print(
							"<script>alert('*您的交易密码和登录密码相同，为了您的帐号安全，请您先修改交易密码! ');window.location.href='securityCent.do?isOn=2';</script>");
					return null;
				}
//				int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
//				if (isApplyPro == 1) {
//					getOut()
//							.print(
//									"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
//					return null;
//				}
			}

			boolean re = userService.checkSign(userId);
			if (!re) {
				request().getSession().removeAttribute("user");
				request().getSession().invalidate();
				getOut()
						.print(
								"<script>alert('*你的账号出现异常，请速与管理员联系! ');window.location.href='login.do';</script>");
				return null;
			}
			int authStep = user.getAuthStep();// 需要填写个人资料之后才能申请提现
			int userType = user.getUserType();
			if (authStep < 2) {
				if(userType == 1){
				    Map<String, String> person = userService.queryPersonById(userId);
				    String authCardName = person.get("authCardName");//0表示通过实名认证
				    String mobilePhone  = m.get("mobilePhone");
				    //判断是否已经实名制
				    if (!"0".equals(authCardName)) {//未实名认证
				        getOut()
	                    .print(
	                            "<script>alert(' 您尚未实名认证，请先进行实名认证后在操作! ');window.location.href='securityCent.do?isOn=3';</script>");
				        return null;
                    }
				    //判断是否已绑定手机号码
				    if(StringUtils.isBlank(mobilePhone)){
				        getOut()
                        .print(
                                "<script>alert(' 您尚未绑定手机，请先绑定手机后在操作! ');window.location.href='securityCent.do?isOn=4';</script>");
				        return null;
				    }

				}else{
					getOut()
					.print(
							"<script>alert(' 请完善企业详细信息! ');window.location.href='owerInformationInit.do';</script>");
					return null;
				}
			}
			List<Map<String, Object>> lists = rechargeService
					.withdrawLoad(userId,user.getUserType());
			// 需要加载信息 真实姓名 手机号码 帐户余额 可用余额 冻结总额 提现银行
			String realName = user.getRealName();
			String bindingPhone = null;
			int status = -1;
			String[] vals = moneyVal(userId);

			request().setAttribute("handleSum", vals[0]);
			request().setAttribute("usableSum", vals[1]);
			request().setAttribute("freezeSum", vals[2]);
			
			
			try {
	             List<Map<String, Object>> minWithDraw = sysparService.querySysparAllChild("*", " selectKey =\"POUNDAGE_MIN\" and deleted=1", "", -1, -1);
	             if (minWithDraw!=null && minWithDraw.size()>0 && "POUNDAGE_MIN".equals(minWithDraw.get(0).get("selectKey"))) {
	                 request().setAttribute("min_withdraw", minWithDraw.get(0).get("selectValue"));// 未投标最低手续费
	            }else {
	                request().setAttribute("min_withdraw", null);
                }
	        }
	        catch (SQLException e1) {
	            e1.printStackTrace();
	        }
			
			
			Map<String, String> map = sysparService.querySysparChild(" selectValue ", "WITHDRAW_MAX", "", -1, -1);
			if ( map!=null && map.size()>0) {
			    request().setAttribute("max_withdraw", map.get("selectValue"));// 最大充值金额，超过之后要收取手续费
            }else {
                request().setAttribute("max_withdraw", IConstants.WITHDRAW_MAX);// 最大充值金额，超过之后要收取手续费
            }
			
			if (lists != null && lists.size() > 0) {
				if (lists.get(0).get("bindingPhone") != null) {
					bindingPhone = lists.get(0).get("bindingPhone").toString();
				}
				if (lists.get(0).get("status") != null) {
					status = Convert.strToInt(lists.get(0).get("status")
							.toString(), -1);
				}

				// 如果设置的绑定号码为空，或者绑定的手机号码还未审核通过 则都使用用户注册时的手机号码
				if (bindingPhone == null
						|| status != IConstants.PHONE_BINDING_ON) {
					bindingPhone = lists.get(0).get("cellPhone") + "";
				}
				String bindingPhoneSecret = "";
				if (null != bindingPhone && bindingPhone.length() > 10){
					//bindingPhoneSecret = bindingPhone.substring(0,4)+"****"+bindingPhone.substring(8);
					bindingPhoneSecret = bindingPhone.substring(7);
				}
				if (null != realName && realName.length() > 1){
					realName = realName.substring(0,1)+"**";
				}
				
				//默认绑定银行卡
				Integer defaultBcard = (Integer) lists.get(0).get("defaultBcard");
				request().setAttribute("defaultBcard", defaultBcard);
				
				// 绑定的银行卡信息单独查询
				List<Map<String, Object>> banks = homeInfoSettingService
						.querySuccessBankInfoList(userId);
				request().setAttribute("banks", banks);
				request().setAttribute("realName", realName);
				request().setAttribute("bindingPhone", bindingPhone);
				request().setAttribute("bindingPhoneSecret", bindingPhoneSecret);
				
				//查询后台设置的提现比例并显示
				Double costFee = 0.5;
				Connection conn = MySQL.getConnection();
				try
				{
					Dao.Tables.t_platform_cost tpc = new Dao().new Tables().new t_platform_cost();
					DataSet  ds = tpc.open(conn, " costFee ", "alias='withdrawalFeeRate' and costMode='1'", "id", -1, -1);
					ds.tables.get(0).rows.genRowsMap();
					costFee = Convert.strToDouble(ds.tables.get(0).rows.get(0).get("costFee").toString(), 0);
				}
				catch (Exception e)
				{
					costFee=0.5;
				}
				finally
				{
					conn.close();
				}
				request().setAttribute("costFee", costFee);
			}
			request().setAttribute("ISDEMO", IConstants.ISDEMO);
			
			//下一个工作日
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			Date bdate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(bdate);
			cal.add(cal.DATE, 1);
			for (int i = 0; i < 3; i++){
				cal.add(cal.DATE, 1);
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
						|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				} else {
//					System.out.print("no " + format1.format(cal.getTime()));
					request().setAttribute("nextWorkTime", format1.format(cal.getTime()));
					break;
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
		if (checkMobile()) {
			return "mobilesuccess";
		}
		return SUCCESS;
	}

	/**
	 * 加载提现记录信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryWithdrawList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 获取用户的信息
		User user = (User) session().getAttribute(IConstants.SESSION_USER);

		Long userId = user.getId();// 获得用户编号

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		try {
			rechargeService.queryWithdrawList(pageBean, userId, 0, null);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String addRechargeoutline() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> recharMap = new HashMap<String, String>();
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		long userid = user.getId();
		String rechargeMoneystr = paramMap.get("money") + "";
		String realname = paramMap.get("realname");
		Pattern pattern = Pattern.compile("^(-)?\\d+(\\.\\d+)?$");
		Matcher m = pattern.matcher(rechargeMoneystr);
		if (StringUtils.isBlank(realname)
				|| !realname.equals(user.getRealName())) {
			jsonMap.put("msg", " 请输入正确的真实姓名！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		if (!m.matches()) {
			jsonMap.put("msg", " 请输入正确的充值金额！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		double rechargeMoney = Convert.strToDouble(rechargeMoneystr, 0);
		if (rechargeMoney <= 0) {
			jsonMap.put("msg", "充值金额不能小于零！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		String bankName = paramMap.get("bankName");
		if (StringUtils.isBlank(bankName)) {
			jsonMap.put("msg", "请选定充值帐户！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		String rechargeNumber = paramMap.get("rechargeNumber") + "";
		String remark = paramMap.get("remark") + "";
		if (StringUtils.isBlank(rechargeNumber)) {
			jsonMap.put("msg", " 交易编号不能为空！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		if (StringUtils.isBlank(remark)) {
			jsonMap.put("msg", "线下充值备注不能为空！");
			JSONUtils.printObject(jsonMap);
			return null;
		}
		recharMap.put("rechargeMoney", rechargeMoney + "");
		recharMap.put("userId", userid + "");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recharMap.put("rechargeTime", df.format(new Date()) + "");
		recharMap.put("rechargeType", 4 + "");
		recharMap.put("result", 3 + "");// 3为审核中
		recharMap.put("rechargeNumber", rechargeNumber);
		recharMap.put("remark", remark);
		recharMap.put("bankName", bankName);
		long result = -1;
		result = rechargeService.addRechargeoutline(recharMap);

		if (result < 0) {
			jsonMap.put("msg", "提交失败");
			JSONUtils.printObject(jsonMap);
			return null;
		}

		jsonMap.put("msg", "提交成功");
		JSONUtils.printObject(jsonMap);
		return null;
	}

	public String addWithdraw() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		
		String header = request().getHeader("X-Requested-With");
        if (header == null || !"XMLHttpRequest".equals(header)) {
            obj.put("msg", "*请勿违禁操作。");
            JSONUtils.printObject(obj);
            return null;
        }
		
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Map<String, String> m = new HashMap<String, String>();
		m = userService.queryUserById(user.getId());
		if (null != m) {
			if (m.get("password").equals(m.get("dealpwd"))) {
				getOut()
				.print(
						"<script>alert('*您的交易密码和登录密码相同，为了您的帐号安全，请您先修改交易密码! ');window.location.href='securityCent.do?isOn=2';</script>");
				return null;
			}
//			int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
//			if (isApplyPro == 1) {
//				getOut()
//						.print(
//								"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
//				return null;
//			}
		}
		
		// ip地址
		String ipAddress = ServletUtils.getRemortIp();
		String dealpwd = paramMap.get("dealpwd");
		String money = paramMap.get("money");
		double moneyD = Convert.strToDouble(money, 0);
		String code = paramMap.get("code");
		String bankId = paramMap.get("bankId");
		long bankIdL = Convert.strToLong(bankId, -1);
		String type = paramMap.get("type");
		String phone = paramMap.get("cellPhone");
		long userId = user.getId();// 获得用户编号
		boolean re = userService.checkSign(userId);
		
		if (moneyD<=100) {
		    obj.put("msg", "*提现金额必须大于100元");
            JSONUtils.printObject(obj);
            return null;
        }
		
		if (!re) {
			obj.put("msg", "*你的账号出现异常，请速与管理员联系！");
			JSONUtils.printObject(obj);
			return null;
		}
		// 验证码
		if (!IConstants.ISDEMO.equals("1")) {
			if (StringUtils.isBlank(code)) {
				obj.put("msg", "请填写验证码");
				JSONUtils.printObject(obj);
				return null;
			}
			Object obje = session().getAttribute("randomCode");
			if (obje != null) {
				String randomCode = obje.toString();
				if (!randomCode.equals(code)) {
					obj.put("msg", "验证码错误");
					JSONUtils.printObject(obj);
					return null;
				}
				//验证码是否过期 
				boolean timeFlag = userService.querySendTime(phone);
				if (!timeFlag){
					log.info("过期了。");
					obj.put("msg", "验证码过期");
					session().removeAttribute("randomCode");
					JSONUtils.printObject(obj);
					return null;
				}
			} else {
				obj.put("msg", "验证码无效");
				JSONUtils.printObject(obj);
				return null;
			}
		}
		if (StringUtils.isBlank(bankId)) {
			obj.put("msg", "请先选择或者设置银行卡信息");
			JSONUtils.printObject(obj);
			return null;
		}
		if (moneyD <= 0) {
			obj.put("msg", "错误金额格式");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(dealpwd)) {
			obj.put("msg", "请填写交易密码");
			JSONUtils.printObject(obj);
			return null;
		}
		if ("1".equals(IConstants.ENABLED_PASS)) {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		} else {
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
					+ IConstants.PASS_KEY);
		}
		
		//验证银行卡是否填写了省、市
		long result = rechargebankService.queryBankIsProvinceOrCity(bankIdL);
		if (result>0) {//未填写省市
		    obj.put("msg", "2");
            JSONUtils.printObject(obj);
            return null;
        }
		
		Map<String, String> retMap = rechargeService.withdrawApply(userId,
				moneyD, dealpwd, bankIdL, type, ipAddress);
		userService.updateSign(userId);// 更换校验码
		long retVal = -1;
		retVal = Convert.strToLong(retMap.get("ret") + "", -1);
		session().removeAttribute("randomCode");
		if (retVal <= 0) {
			obj.put("msg", retMap.get("ret_desc"));
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	public static String getNeedTime(Date currDate) {
//		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			String currDateStr = DateUtil.YYYY_MM_DD.format(currDate);
			currDate = DateUtil.YYYY_MM_DD.parse(currDateStr);
			long currTime = currDate.parse(currDate.toString());
			long delTime = IConstants.WITHDRAW_TIME * 24 * 60 * 60 * 1000;
			long needTime = currTime - delTime;
			Date needDate = new Date();
			needDate.setTime(needTime);
			String needDateStr = DateUtil.YYYY_MM_DD.format(needDate);
			return needDateStr;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 删除提现记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteWithdraw() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		long wid = Convert.strToLong(paramMap.get("wId"), -1);
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		try {
			// 删除提现记录
			Map<String, String> resultMap = rechargeService.deleteWithdraw(
					userId, wid);
			long result = Convert.strToLong(resultMap.get("ret") + "", -1);

			userService.updateSign(userId);// 更换校验码
			if (result <= 0) {
				obj.put("msg", resultMap.get("ret_desc") + "");
				JSONUtils.printObject(obj);
				return null;
			} else {
				obj.put("msg", "1");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	public String queryFundrecordInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		String[] vals = fundVal(userId);
		
		String wap = request("wap");
		if (StringUtils.isNotBlank(wap)) {
            JSONObject object = new JSONObject();
            object.put("handleSum", vals[0]);
            object.put("usableSum", vals[1]);
            object.put("freezeSum", vals[2]);
            com.shove.util.JSONUtils.printObject(object);
            return null;
        }
		
		request().setAttribute("handleSum", vals[0]);
		request().setAttribute("usableSum", vals[1]);
		request().setAttribute("freezeSum", vals[2]);
		if (checkMobile()) {
			return "successmobile";
		}
		return SUCCESS;
	}

	private String[] fundVal(long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] val = new String[3];
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
				val[0] = String.format("%.2f", handleSum);
				val[1] = String.format("%.2f", usableSum);
				val[2] = String.format("%.2f", freezeSum);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return val;
	}

	public String[] moneyVal(long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
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

	public String queryFundrecordList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号

		String startTime = Convert.strToStr(paramMap.get("startTime"), null);
		String endTime = Convert.strToStr(paramMap.get("endTime"), null);
		String momeyType = Convert.strToStr(paramMap.get("momeyType"), "");
		String cycle = Convert.strToStr(paramMap.get("cycle"), null);//周期
		if (endTime != null) {
			String[] strs = endTime.split("-");
			// 结束日期往后移一天,否则某天0点以后的数据都不呈现
			Date date = new Date();// 取时间
			long time = date.UTC(Convert.strToInt(strs[0], -1) - 1900, Convert
					.strToInt(strs[1], -1) - 1, Convert.strToInt(strs[2], -1),
					0, 0, 0);
			date.setTime(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			endTime = formatter.format(date);
		}
		if (!StringUtil.isEmpty(cycle)){
			if ("3m".equals(cycle)){
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, -3);
				Date d = c.getTime();
				SimpleDateFormat fmt = new SimpleDateFormat(UtilDate._dtShort);
				cycle = fmt.format(d);
			} else if ("1m".equals(cycle)){
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, -1);
				Date d = c.getTime();
				SimpleDateFormat fmt = new SimpleDateFormat(UtilDate._dtShort);
				cycle = fmt.format(d);
			} else if ("1w".equals(cycle)){
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				Date d = c.getTime();
				SimpleDateFormat fmt = new SimpleDateFormat(UtilDate._dtShort);
				cycle = fmt.format(d);
			} else if ("today".equals(cycle)){
				SimpleDateFormat fmt = new SimpleDateFormat(UtilDate._dtShort);
				cycle = fmt.format(new Date());
			}
		}

		pageBean.setPageSize(IConstants.PAGE_SIZE_6);

		rechargeService.queryFundrecordList(pageBean, userId, startTime,
				endTime, momeyType,cycle);
		paramMap = rechargeService.queryFundrecordSum(userId, startTime,
				endTime, momeyType,cycle);
		String[] val = moneyVal(userId);
		paramMap.put("SumusableSum", val[1]);
		
		if (StringUtils.isNotBlank(request("wap"))) {
		    JSONObject object = new JSONObject();
		    object.put("pageBean", pageBean);
		    object.put("SumusableSum", val[1]);
		    JSONUtils.printObject(object);
		    return null;
        }
		
		int pageNums = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNums);
		
		if (checkMobile()) {
			return "successmobile";
		}
		
		return SUCCESS;
	}
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		Date d = c.getTime();
		SimpleDateFormat fmt = new SimpleDateFormat(UtilDate._dtShort);
		System.out.println(fmt.format(d));
	}
	
	

	/**
	 * 添加充值记录
	 * 
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */

	public String queryRechargeInfo() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId = user.getId();// 获得用户编号
		List<Map<String, Object>> lists = null;
		int type = -1, status = -1;
		try {

			pageBean.setPageSize(IConstants.PAGE_SIZE_6);

			rechargeService.queryRechargeInfo(pageBean, userId);
			lists = pageBean.getPage();
			if (lists != null) {
				for (Map<String, Object> map : lists) {
					map.put("userId", user.getUserName());
					type = Convert.strToInt(map.get("rechargeType").toString(),
							-1);
					status = Convert.strToInt(map.get("result").toString(), -1);
					if (status == IConstants.RECHARGE_SUCCESS) {
						map.put("status", IConstants.RECHARGE_SUCCESS_STR);
					} else {
						map.put("status", IConstants.RECHARGE_CHECKING_STR);
					}
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
	 * 发送手机验证码
	 * 
	 * @return
	 */
	public String sendPhoneCode() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Random rand = new Random();
		int num = rand.nextInt(9999);
		while (num < 1000) {
			num = rand.nextInt(9999);
		}
		System.out.println("num====>" + num);
		String type = request.getString("type");
		session().setAttribute(type + "_phoneCode", num);
		try {
			JSONUtils.printStr(num + "");
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 线下充值审核
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateRechargeDetailStatusById() throws Exception {

		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String award = paramMap.get("award");

		if (StringUtils.isBlank(award)) {
			award = "";
		} else {
			Pattern pattern = Pattern.compile("^(-)?\\d+(\\.\\d+)?$");
			Matcher m = pattern.matcher(award);
			if (!m.matches()) {
				this.addFieldError("paramMap.allerror", "输入奖励比例错误");
				return "input";
			}
		}
		long id = Convert.strToLong(paramMap.get("id"), -1);
		long userid = Convert.strToLong(paramMap.get("userId"), -1);
		if (id == -1) {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		if (userid == -1) {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		String typeStr = paramMap.get("type");
		long type = -1;
		if (typeStr.equals("a")) {// 审核成功
			type = 1;
		} else if (typeStr.equals("b")) {
			type = 0;
		} else {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		double rechargeMoney = Convert.strToDouble(paramMap
				.get("rechargeMoney")
				+ "", 0);
		if (rechargeMoney == 0) {
			this.addFieldError("paramMap.allerror", "审核失败");
			return "input";
		}
		long result = rechargeService.updateRechargeDetailStatusById(id,
				userid, type, rechargeMoney, award);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_recharge_detail", admin
				.getUserName(), IConstants.UPDATE, admin.getLastIP(),
				rechargeMoney, "线下审核", 2);
		userService.updateSign(userid);// 更换校验码
		if (result <= 0) {
			getOut()
					.print(
							"<script>alert('审核失败');window.location.href='queryxxRechargeInit.do';</script>");
			return null;
		}
		return SUCCESS;
	}
	
	/*
	 * 自动计算提现手续费
	 */
	public String withdrawCount() throws IOException, SQLException{
		log.info("---withdrawCount---");
	    User user = (User) session().getAttribute(IConstants.SESSION_USER);
	    double money = Convert.strToDouble(paramMap.get("money"), 0);
	    paramMap = rechargeService.withdrawCount(user.getId(), money);
	    JSONUtils.printObject(paramMap);
	    return null;
	}
	
	
	public String defaultFix()throws Exception{
		log.info("---defaultFix---");
	    User user = (User) session().getAttribute(IConstants.SESSION_USER);
	    int defaultBcard = Convert.strToInt(paramMap.get("defaultBcard"), 0);
	    userService.updatedDefaultBankcard(user.getId(),user.getUserType(), defaultBcard);
	    return null;
	}
	
	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public List<Map<String, Object>> getPotypeList() throws Exception {
		if (potypeList == null) {
			potypeList = rechargeService.queryTypeFund();
		}
		return potypeList;
	}

	public void setPotypeList(List<Map<String, Object>> potypeList) {
		this.potypeList = potypeList;
	}

	public List<Map<String, Object>> getBanksList() throws Exception {
		try {
			banksList = rechargebankService.queryrechargeBanklist();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return banksList;
	}

	public void setBanksList(List<Map<String, Object>> banksList) {
		this.banksList = banksList;
	}

	public RechargebankService getRechargebankService() {
		return rechargebankService;
	}

	public void setRechargebankService(RechargebankService rechargebankService) {
		this.rechargebankService = rechargebankService;
	}

}