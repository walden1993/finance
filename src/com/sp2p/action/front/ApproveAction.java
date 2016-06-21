package com.sp2p.action.front;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.shove.Convert;
import com.shove.security.Encrypt;
import com.shove.util.ServletUtils;
import com.shove.util.SqlInfusion;
import com.shove.util.UtilDate;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.ApproveService;
import com.sp2p.service.SendMailService;
import com.sp2p.service.UserService;

public class ApproveAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(ApproveAction.class);
	private ApproveService approveService;
	protected SendMailService mailSendService;
	private UserService userService;
	
	 
	
	public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    public String searchDealPwdSetp1() throws Exception{
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        User user = (User) session().getAttribute(IConstants.SESSION_USER);
        Map<String, String> userMap = userService.queryUserById(user.getId());
        String email  = userMap.get("email");
        String phone = userMap.get("mobilePhone");
        if (StringUtils.isNotBlank(email)) {
            request().setAttribute("memail", com.shove.util.StringUtils.formatStr(email,4,8));
        }
        
        if (StringUtils.isNotBlank(phone)) {
            request().setAttribute("mphone",com.shove.util.StringUtils.formatStr(phone,4,8));
        }
        
        request().setAttribute("email", StringUtils.isBlank(email)?null:email);
        request().setAttribute("phone", StringUtils.isBlank(phone)?null:phone);
        return SUCCESS;
    }
    
    /**
	 * 跳转到输入改变交易密码page
	 * 
	 * @return
	 */
	public String forgetTradepassword() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		UserService userService=new UserService();
		User user=(User)session().getAttribute("user");
		try {
			Map<String,String> map=userService.queryEamilById(user.getId());
			int cc=Integer.parseInt(map.get("cccc"));
			if(cc!=0){
				String paraMsg="您还没有绑定邮箱，请先绑定邮箱";
				session().setAttribute("paraMsg",paraMsg);
				getOut().print("<script>window.location.href='securityCent.do?isOn=5'</script>");
				//return "emailManagerInit";
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		//跳转时判定用户是否绑定邮箱
		
		return SUCCESS;
	}

	/**
	 * 更新交易密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateTradepasswordM() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		User user = this.getUser();
		String emails = user.getEmail();
		Map<String, String> map = null;
		String username = null;
		Long userId = null;
		String email = paramMap.get("email");
		if (StringUtils.isBlank(email)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		} else if (!email.equals(emails)){
			  obj.put("mailAddress", "3");
			  JSONUtils.printObject(obj);
			  return null;
		} else {
			// ===截取emal后面地址
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}
			// ====
			map = approveService.querytrancePassword(email);
			if (map != null && map.size() > 0) {
				username = map.get("username");
				userId = Convert.strToLong(map.get("id"), -1L);
				DesSecurityUtil des = new DesSecurityUtil();
				String key1 = des.encrypt(userId.toString());
				String key2 = des.encrypt(new Date().getTime() + "");
				String url = getPath(); // request().getRequestURI();
				url = url.endsWith("/") ? url : url + "/";
				String VerificationUrl = url + "changeTrancePassword.do?key="
						+ key1 + "-" + key2;

				mailSendService.sendTrancepasswordLogin(VerificationUrl,
						username, email);
				obj.put("mailAddress", mailAddress);
				JSONUtils.printObject(obj);
				return null;

			} else {
				obj.put("mailAddress", "1");
				JSONUtils.printObject(obj);
				return null;
			}
		}
	}

	/**
	 * 验证邮箱有效和跳转到修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String udpateTrancePassword() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String key = SqlInfusion.FilteSqlInfusion(request("key").trim());
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
			String userId = Encrypt.MD5(key + IConstants.BBS_SES_KEY)
					.substring(0, 10)
					+ key;
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			//String dateTime_email = (String) session().getAttribute("dateTime_email");
			//有效性
			/*if (StringUtils.isNotBlank(dateTime_email) && dateTime_email.equals(dateTime)) {
			    msg = "连接失效,<strong>请从新填写你的绑定邮箱</strong>";
                ServletActionContext.getRequest().setAttribute("msg", msg);
                return "index";
            }*/
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {//时效性
				// 修改用户状态
				// Long result = userService.frontVerificationEmial(userId);
				/*
				 * if (result > 0) { msg =
				 * "恭喜您帐号激活成功！请点击<a href='login.do'>登录</a>";
				 * ServletActionContext.getRequest().setAttribute("msg", msg); }
				 * else { msg = "注册失败"; // 这里还要写一个用户删除账号和密码
				 * ServletActionContext.getRequest().setAttribute("msg", msg); }
				 */
			    session().setAttribute("dateTime_email", dateTime);
				ServletActionContext.getRequest()
						.setAttribute("userId", userId);
				return SUCCESS;
			} else {
				msg = "连接失效,<strong>请从新填写你的绑定邮箱</strong>";
				ServletActionContext.getRequest().setAttribute("msg", msg);
				return "index";
			}

		} else {
			return "index";
		}

	}
	
	/**
	 * 验证手机有效和跳转到修改页面--找回交易密码
	 * llz
	 * @return
	 * @throws Exception
	 */
	public String ajaxQueryUserByIDAndPhone() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String phone = SqlInfusion.FilteSqlInfusion(paramMap
				.get("phone"));
		User user=(User)session().getAttribute("user");
		Long userId=user.getId();
		if(phone!=null && phone!=""){
			
		}
		
		
//		String key = SqlInfusion.FilteSqlInfusion(request("key").trim());
//		String msg = "邮箱验证失败";
//		String[] keys = key.split("-");
//		if (2 == keys.length) {
//			DesSecurityUtil des = new DesSecurityUtil();
//			String userId = Encrypt.MD5(key + IConstants.BBS_SES_KEY)
//					.substring(0, 10)
//					+ key;
//			String dateTime = des.decrypt(keys[1].toString());
//			long curTime = new Date().getTime();
//			// 当用户点击注册时间小于10分钟
//			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
//				// 修改用户状态
//				// Long result = userService.frontVerificationEmial(userId);
//				/*
//				 * if (result > 0) { msg =
//				 * "恭喜您帐号激活成功！请点击<a href='login.do'>登录</a>";
//				 * ServletActionContext.getRequest().setAttribute("msg", msg); }
//				 * else { msg = "注册失败"; // 这里还要写一个用户删除账号和密码
//				 * ServletActionContext.getRequest().setAttribute("msg", msg); }
//				 */
//				ServletActionContext.getRequest()
//						.setAttribute("userId", userId);
//				return SUCCESS;
//			} else {
//				msg = "连接失效,<strong>请从新填写你的注册邮箱</a></strong>";
//				ServletActionContext.getRequest().setAttribute("msg", msg);
//				return "index";
//			}
//
//		} else {
			return "index";
//		}

	}

	// 修改交易密码
	public String updateTrancePasswordfor() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String password = SqlInfusion.FilteSqlInfusion(paramMap
				.get("newPassword"));
		String confirmpassword = SqlInfusion.FilteSqlInfusion(paramMap
				.get("confirmpassword"));
		String key = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		Long userId = -1l;
		String type = paramMap.get("phone");
        if (!StringUtils.isBlank(type) && "phone".equals(type)) {//手机
            userId = Convert.strToLong(key, -1L);
        }else {//邮箱
            try {
                String mdKey = key.substring(0, 10);
                String mdValue = key.substring(10, key.length());
                String mdCompare = Encrypt.MD5(mdValue + IConstants.BBS_SES_KEY)
                        .substring(0, 10);
                if (!mdKey.equals(mdCompare)) {
                    JSONUtils.printStr("4");
                    return null;
                }
                String[] keys = mdValue.split("-");
                if (2 == keys.length) {
                    DesSecurityUtil des = new DesSecurityUtil();
                    userId = Convert.strToLong(des.decrypt(keys[0].toString()), -1);
                    String dateTime = des.decrypt(keys[1].toString());
                    long curTime = new Date().getTime();
                    // 当用户点击注册时间小于10分钟
                    if (curTime - Long.valueOf(dateTime) >= 10 * 60 * 1000) {
                        JSONUtils.printStr("4");
                        return null;
                    }
                } else {
                    JSONUtils.printStr("4");
                    return null;
                }
            }
            catch (Exception e) {
                JSONUtils.printStr("4");
                return null;
            }
        }
		
		
		if (StringUtils.isBlank(password)) {
			JSONUtils.printStr("3");
			return null;
		}
		if (!confirmpassword.equals(password)) {
			JSONUtils.printStr("5");
			return null;
		}
		// 判断长度必须是6到20个字符
		if (password.length() < 6 || password.length() > 20) {
			JSONUtils.printStr("6");
			return null;
		}
		if (userId == null || userId == -1L) {
			JSONUtils.printStr("4");
			return null;
		}
		Long result = -1L;
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		if (password != null && password.trim() != "" && userId != null
				&& userId != -1L) {
			result = approveService.updateUserTrancePassword(user.getId(), password);
		}
		if (result ==10) {
            JSONUtils.printStr("10");
            return null;
        }
		if (result > 0) {
			JSONUtils.printStr("1");
			if ("1".equals(IConstants.ENABLED_PASS)){
			    password = com.shove.security.Encrypt.MD5(password.trim());
	        }else{
	            password = com.shove.security.Encrypt.MD5(password.trim()+IConstants.PASS_KEY);
	        }
			user.setDealpwd(password);
			 session().setAttribute(IConstants.SESSION_USER, user);
			 if (!StringUtils.isBlank(type) && "phone".equals(type)) {//手机
			    session().removeAttribute("smsMap");
             }
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}

	}

	public void setApproveService(ApproveService approveService) {
		this.approveService = approveService;
	}

	public void setSendMailService(SendMailService mailSendService) {
		this.mailSendService = mailSendService;
	}

}
