package com.sp2p.action.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.util.WebUtils;

import com.shove.Convert;
import com.shove.security.Encrypt;
import com.shove.util.CookieUtils;
import com.shove.util.SMSUtil;
import com.shove.util.SqlInfusion;
import com.shove.util.UtilDate;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.entity.Users;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.ExperienceVoucherService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.MailSendService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SendMailService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.RelationService;
import com.sp2p.util.DateUtil;
import com.sp2p.vioce.RestUtil;

/**
 * 用户注册
 * 
 * @author
 * 
 */
public class FrontLongRegisterAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(FrontLongRegisterAction.class);
	private static final long serialVersionUID = 1L;
	/**
	 */
	protected UserService userService;
	protected SendMailService sendMailService;
	private RecommendUserService recommendUserService;
	private RelationService relationService;
	private HomeInfoSettingService homeInfoSettingService;
	private MailSendService mailSendService;
	private BBSRegisterService bbsRegisterService;
	private AdminService adminService;
	private CellPhoneService cellPhoneService;
	private SelectedService selectedService;
	private ExperienceVoucherService experienceVoucherService;
	private UserIntegralService userIntegralService;
	private SysparService sysparService;
	
	private int invertal = 60;
	

	public SysparService getSysparService() {
        return sysparService;
    }

    public void setSysparService(SysparService sysparService) {
        this.sysparService = sysparService;
    }

    public void setUserIntegralService(UserIntegralService userIntegralService) {
        this.userIntegralService = userIntegralService;
    }

    public void setExperienceVoucherService(ExperienceVoucherService experienceVoucherService) {
        this.experienceVoucherService = experienceVoucherService;
    }

    public void setSelectedService(SelectedService selectedService) {
        this.selectedService = selectedService;
    }

    public void setMailSendService(MailSendService mailSendService) {
		this.mailSendService = mailSendService;
	}

	public String regInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		if (user != null) {
			// return "login";
		}
		String param = request.getString("param");
		String source = request.getString("source");
		request().setAttribute("source", source);
		
		if (StringUtils.isNotBlank(param)) {
			DesSecurityUtil des = new DesSecurityUtil();
			Long userId = Convert.strToLong(des.decrypt(param), -1);
			String userName;
			Map<String, String> map = new HashMap<String, String>();
			try {
				map = userService.queryUserById(userId);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
			userName = map.get("username");
			paramMap.put("refferee", userName);
		}
		paramMap.put("param", param);
		
		String type = request.getString("type");
		
		long time = new Date().getTime()-(invertal*1000);
		
		DesSecurityUtil entry = new DesSecurityUtil();
	    String key = entry.encrypt(time+"&"+0);
	    request().setAttribute("key", key);
	    if (session("key")!=null) {
            session().removeAttribute("key");
        }
	    CookieUtils.setKey(request(), response(), "key", key);
		if (StringUtils.isNotBlank(type)) {
            return "company";
        }
		
		return SUCCESS;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 忘记密码--邮箱修改
	 * 
	 * @return
	 */
	public String forget() {
		return SUCCESS;
	}
	/**
	 * 忘记密码--手机修改
	 * 
	 * @return
	 */
	public String forgetPhone() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return SUCCESS;
	}
	
	//找回密码,发送邮件  获取用户信息
	public String forgetSendEMl() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		Map<String, String> map = null;
		String username = null;
		Long userId = null;
		String email = paramMap.get("email");
		if (StringUtils.isBlank(email)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		} else {
			// ===截取emal后面地址
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}
			
			if (!com.shove.util.StringUtils.matchEmail(email)) {
			    obj.put("mailAddress", "1");
                JSONUtils.printObject(obj);
                return null;
            }
			
			// ====
			map = userService.queryPassword(email);
			
			if (map != null && map.size() > 0) {
				username = map.get("username");
				userId = Convert.strToLong(map.get("id"), -1L);
				DesSecurityUtil des = new DesSecurityUtil();
				String key1 = des.encrypt(userId.toString());
				String key2 = des.encrypt(new Date().getTime() + "");
				String url = getPath(); // request().getRequestURI();
				String VerificationUrl = url + "changePassword.mht?key=" + key1
						+ "-" + key2;
				
				//统计发送邮件次数，超过4次的不发送
				Object count =  session().getAttribute(email);
				if (count!=null) {
                    int countInt = NumberUtils.toInt(count.toString(), 0);
                    if (countInt>=4) {
                        obj.put("mailAddress", mailAddress);
                        JSONUtils.printObject(obj);
                        return null;
                    }else {
                        countInt++;
                        session().setAttribute("email", countInt+"");
                    }
                }
				
				sendMailService.sendRegisterVerificationEmailPassWordindex(
						VerificationUrl, username, email);
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
	 * 点击邮箱连接后
	 * 
	 * @return
	 * @throws Exception
	 */
	public String changePasswordfor() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String key = SqlInfusion.FilteSqlInfusion(request("key").trim());
		String msg = "邮箱验证失败";
		String[] keys = key.split("-");
		if (2 == keys.length) {
			DesSecurityUtil des = new DesSecurityUtil();
		    String userId = Encrypt.MD5(key+IConstants.BBS_SES_KEY).substring(0,10)+key;
			String dateTime = des.decrypt(keys[1].toString());
			long curTime = new Date().getTime();
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
				ServletActionContext.getRequest()
						.setAttribute("userId", userId);
				return SUCCESS;
			} else {
				msg = "连接失效,<strong>请从新填写你的注册邮箱</a></strong>";
				ServletActionContext.getRequest().setAttribute("msg", msg);
				return "index";
			}

		} else {
			return "index";
		}

	}

	/**
	 * 修改密码--邮箱
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatechangePasswordfor() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("newPassword"));
		String confirmpassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmpassword"));
		String key = SqlInfusion.FilteSqlInfusion(paramMap.get("userId"));
		Long userId = -1l;
		
		String mdKey = key.substring(0,10);
		String mdValue = key.substring(10,key.length());
		String mdCompare = Encrypt.MD5(mdValue+IConstants.BBS_SES_KEY).substring(0,10);
		if(!mdKey.equals(mdCompare)){
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
		if (StringUtils.isBlank(password)) {
			JSONUtils.printStr("3");
			return null;
		}

		if (!confirmpassword.equals(password)) {
			JSONUtils.printStr("5");
			return null;
		}
		// 验证密码的长度
		if (password.length() < 6 || password.length() > 20) {
			JSONUtils.printStr("6");
			return null;
		}
		if (userId < 0) {
			JSONUtils.printStr("4");
			return null;
		}
		Long result = -1L;
		if (password != null && password.trim() != "" && userId > 0) {
			result = userService.updateUserPassword(userId, password);
		}
		if (result==10) {//登录密码等于交易密码，修改失败
            JSONUtils.printStr("10");
            return null;
        }
		if (result > 0) {
			Map<String, String> userMap = userService.queryUserById(userId);
			if(userMap == null){
				userMap = new HashMap<String, String>();
			}
			String userName = userMap.get("username") + "";
			// 修改论坛的密码
			bbsRegisterService.doUpdatePwdByAsynchronousMode(userName,
					password, confirmpassword, 2);
			JSONUtils.printStr("1");
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}

	}
	//修改密码--手机
	public String updatechangePasswordforPhone() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String password = SqlInfusion.FilteSqlInfusion(paramMap.get("newPassword"));
		String confirmpassword = SqlInfusion.FilteSqlInfusion(paramMap.get("confirmpassword"));
		String phone= SqlInfusion.FilteSqlInfusion(paramMap.get("phone"));
		if(StringUtils.isBlank(phone)){
			JSONUtils.printStr("11");
			return null;
		}
		//根据电话号码查询用户信息
		Long userId = userService.queryUserIdByPhone(phone);//要跟前面步骤中确认手机是否存在的判断保持一致
		
		if (StringUtils.isBlank(password) || StringUtils.isBlank(confirmpassword)) {
			JSONUtils.printStr("3");
			return null;
		}
		
		

		if (!confirmpassword.equals(password)) {
			JSONUtils.printStr("5");
			return null;
		}
		// 验证密码的长度
		if (password.length() < 6 || password.length() > 20) {
			JSONUtils.printStr("6");
			return null;
		}
		if (userId < 0) {
			JSONUtils.printStr("4");
			return null;
		}
		Long result = -1L;
		if (password != null && password.trim() != "" && userId > 0) {
			result = userService.updateUserPassword(userId, password);
		}
		if (result==10) {//登录密码等于交易密码，修改失败
		    JSONUtils.printStr("10");
            return null;
        }
		if (result > 0) {
			Map<String, String> userMap = userService.queryUserById(userId);
			if(userMap == null){
				userMap = new HashMap<String, String>();
			}
			String userName = userMap.get("username") + "";
			// 修改的密码
			bbsRegisterService.doUpdatePwdByAsynchronousMode(userName,
					password, confirmpassword, 2);
			JSONUtils.printStr("1");
			return null;
		} else {
			JSONUtils.printStr("0");
			return null;
		}
//		return null;
	}

	// -------add by houli 将查询推荐人方法单独出来，注册填写推荐人的时候，用户要求失去焦点进行提示推荐人填写正确与否
	public String queryValidRecommer() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String refferee = paramMap.get("refferee");
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			if (refferee == null) {
				resultMap.put("error", "推荐人不存在");
				JSONUtils.printObject(resultMap);
				return null;
			}
			Map<String, String> userIdMap = userService.queryIdByUser(refferee);// 根据用户查询用户明细
			Map<String, Object> map = relationService.isPromoter(refferee);
			if (userIdMap == null && map == null) {
				resultMap.put("error", "推荐人不存在");
			}else {
				resultMap.put("ok", "");//验证成功
			}
			JSONUtils.printObject(resultMap);
			return null;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	public String registerTwoInit(){
		return SUCCESS;
	}
	
	//wap注册完毕
	public String registerTwoWap() throws Exception{
        
        String tradPassWord = paramMap.get("tradPassWord");//交易密码
        String reTradPassWord = paramMap.get("reTradPassWord");//确认交易密码
        String cellcode = paramMap.get("cellcode");//手机验证码
        Long userId = -1L;
        
        JSONObject obj = new JSONObject();
        
        if (StringUtils.isBlank(cellcode)) {
            obj.put("code", "1");
            obj.put("msg", "请输入手机验证码");
            JSONUtils.printObject(obj);
            return null;
        }
        
        if (StringUtils.isBlank(tradPassWord)) {
            obj.put("code", "1");
            obj.put("msg", "交易密码不能为空");
            JSONUtils.printObject(obj);
            return null;
        }
        
        if (StringUtils.isBlank(reTradPassWord)) {
            obj.put("code", "2");
            obj.put("msg", "请再次输入交易密码");
            JSONUtils.printObject(obj);
            return null;
        }
        
        if (!tradPassWord.equals(reTradPassWord)) {
            obj.put("code", "3");
            obj.put("msg", "两次交易密码不一致，请重新输入");
            JSONUtils.printObject(obj);
            return null;
        }
        
        //步骤2
        Users userEntity =(Users)session().getAttribute("LinUserEntity");//获取步骤1的数据
        
        if (null == userEntity) {
            obj.put("code", "4");
            obj.put("msg", "注册失败,请重新注册");
            JSONUtils.printObject(obj);
            return null;
        }
        
        String phone = userEntity.getMobilePhone();
        Object codephone =  session(cellcode);
        
        if (codephone==null || phone==null || !phone.equals(codephone)) {
            obj.put("code", "3");
            obj.put("msg", "手机验证码不正确");
            JSONUtils.printObject(obj);
            return null;
        }
        
        
        Map<String, Object> map=(Map<String, Object>)session().getAttribute("mapref");
        long recommendUserId=(Long)session().getAttribute("recommendUserIdref");
        
        String  md5Password=tradPassWord;
        
        if ("1".equals(IConstants.ENABLED_PASS)) {
            md5Password = com.shove.security.Encrypt
                    .MD5(md5Password.trim());
        } else {
            md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()
                    + IConstants.PASS_KEY);
        }
        if(md5Password.equals(userEntity.getPassword())){
            obj.put("code", "5");
            obj.put("msg", "交易密码不能与登录密码相同");
            JSONUtils.printObject(obj);
            return null;
        }
        

        //手机注册用户
//      if(smsMap.get("StutsCode").equals("Sucess")){
//          if(smsMap.get("randomCode").equals(cellcode)){
                userEntity.setEnable(1);//将用户激活
                //userId=userService.userRegister(userEntity, king);
                int typelen = -1;
                Map<String,String> lenMap = null;
                lenMap = userService.querymaterialsauthtypeCount(); //查询证件类型主表有多少种类型
                if(lenMap!=null&&lenMap.size()>0){
                    typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
                    if(typelen!=-1){
                        //个人用户
                        userEntity.setUserType(1);
                        userId=cellPhoneService.usercellRegister(userEntity.getMobilePhone(),userEntity.getUserName(),userEntity.getPassword(),
                                userEntity.getRefferee(),map,0,userEntity.getUserType(),userEntity.getOrgName());//注册用户 和  初始化图片资料
                    }
                }
                
        if (userId < 0) { 
            // 注册失败
            obj.put("code", "4");
            obj.put("msg", "注册失败");
            JSONUtils.printObject(obj);
            return null;
        } else {
            //注册成功，判断当前赠送体验券功能是否开启，开启则绑定体验券到此用户
            Map<String, String> syspar =  selectedService.getSyspar("TYQ");
            if (syspar!=null  && syspar.get("selectKey")!=null && !"".equals(syspar.get("selectKey"))) {
                    int deleted = Convert.strToInt(syspar.get("deleted"), -1);
                    if (deleted==1) {//表示启动此功能
                          int  batchId = Convert.strToInt(syspar.get("selectValue"), -1); //得到批次号
                          if (batchId!=-1) {
                              //获取对应批次号的一张未绑定的体验券
                             Map<String, String>  ticket =  experienceVoucherService.getTicket(batchId);
                             if (ticket!=null &&  ticket.get("ticketNo")!=null && !"".equals(ticket.get("ticketNo"))) {
                                  String ticketNo = ticket.get("ticketNo");
                                  Map<String, String> bindResult =  experienceVoucherService.activeTicket(userId.intValue(), ticketNo);
                                  log.info("绑定结果：================"+bindResult);
                            }
                        }
                    }
            }
            //修改交易密码
            homeInfoSettingService.updateUserPassword(userId, tradPassWord, "tradpwd");
            //手机注册
            userService.updateSign(userId);//修改校验码
            //添加通知默认方法
            homeInfoSettingService.addNotes(userId, true, false, false);
            homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
              //====
            obj.put("code", "6");
            obj.put("msg", "注册成功");
            JSONUtils.printObject(obj);
            
            //修改之前的推荐
            if(recommendUserId>0){//判断是否为空
                
                List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
                if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
                    return null;
                }
                recommendUserService.addRecommendUser(userId, recommendUserId);
                
                /*//推荐人送一次刮奖，加好币的方式实现 
    		    Map maward = sysparService.getAwardJudge("25");
    		    String a = (String) maward.get("introduce");
    		    if (!StringUtil.isEmpty(a) && a.equals("1")){
    		    	
    		    	//添加送好币记录
    		    	sysparService.addUserSore(recommendUserId);
    		    }*/
            }

			//注册送一次刮奖，加好币的方式实现 
            boolean active = sysparService.isOpen("OPEN", "25");
		    if (active){
		    	
		    	long result = userService.addPresrent(10, userId,0);//加赠送金额
		    	homeInfoSettingService.addAwardUser(userId);//记录
		    	
		    	/*Map map1 = userService.queryUserById(userId);//.queryPersonById(userId);
		    	log.info("------------- map:" + map1);
		    	SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		    	String d = (String) map1.get("createTime");
		    	Date redate = sf.parse(d);
		    	
		    	//活动起止时间   是改日期 TODO
		    	Date sdate = sf.parse("2015-11-03 10:00:00");//yyyy-MM-dd HH:mm:ss
		    	Date edate = sf.parse("2015-12-03 18:00:00");
		    	
		    	//添加送好币记录
		    	if (redate.after(sdate) && redate.before(edate)){
		    		sysparService.addUserSore(userId);
		    	}*/
		    }
		    
		    
            User user = new User();
            user.setUserName(userEntity.getUserName());
            user.setPassword(userEntity.getPassword());
            user.setEmail("default@163.com");
            //bbsRegisterService.doRegisterByAsynchronousMode(userEntity);
            //清空session
            request().getSession().invalidate();
            return null;
        }
    }
	
	// ------------end by houli

	public String registerTwo() throws Exception {
		
		//String tradPassWord = paramMap.get("tradPassWord");//交易密码
		//String reTradPassWord = paramMap.get("reTradPassWord");//确认交易密码
		Long userId = -1L;
		
		JSONObject obj = new JSONObject();
		
		/*if (StringUtils.isBlank(tradPassWord)) {
			obj.put("mailAddress", "1");
			JSONUtils.printObject(obj);
			return null;
		}
		
		if (StringUtils.isBlank(reTradPassWord)) {
			obj.put("mailAddress", "2");
			JSONUtils.printObject(obj);
			return null;
		}
		
		if (!tradPassWord.equals(reTradPassWord)) {
			obj.put("mailAddress", "3");
			JSONUtils.printObject(obj);
			return null;
		}*/
		
		
		
		
		//步骤2
		Users userEntity =(Users)session().getAttribute("LinUserEntity");//获取步骤1的数据
		String source = userEntity.getSource();
		Map<String, Object> map=(Map<String, Object>)session().getAttribute("mapref");
		long recommendUserId=(Long)session().getAttribute("recommendUserIdref");
		Integer type=(Integer)session().getAttribute("type");
		
		/*String  md5Password=tradPassWord;
		
		if ("1".equals(IConstants.ENABLED_PASS)) {
			md5Password = com.shove.security.Encrypt
					.MD5(md5Password.trim());
		} else {
			md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()
					+ IConstants.PASS_KEY);
		}
		if(md5Password.equals(userEntity.getPassword())){
			obj.put("mailAddress", "5");
			JSONUtils.printObject(obj);
			return null;
		}*/
		

		//手机注册用户
//		if(smsMap.get("StutsCode").equals("Sucess")){
//			if(smsMap.get("randomCode").equals(cellcode)){
				userEntity.setEnable(1);//将用户激活
				//userId=userService.userRegister(userEntity, king);
				int typelen = -1;
				Map<String,String> lenMap = null;
				lenMap = userService.querymaterialsauthtypeCount();	//查询证件类型主表有多少种类型
				if(lenMap!=null&&lenMap.size()>0){
					typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
					if(typelen!=-1){
						//个人用户
						if(type==2){
							userEntity.setUserType(1);
						}else{
							//企业用户
							userEntity.setUserType(2);
							
						}
						typelen = 0;//去除所有的认证
						Map m = new HashMap();
						m.put("mobilePhone", userEntity.getMobilePhone());
						m.put("userName", userEntity.getUserName());
						m.put("password", userEntity.getPassword());
						m.put("refferee", userEntity.getRefferee());
						m.put("typelen", typelen);
						m.put("userType", userEntity.getUserType());
						m.put("orgName", userEntity.getOrgName());
						m.put("source", userEntity.getSource());
						userId=cellPhoneService.usercellRegister2(map,m);//注册用户 和  初始化图片资料
		
					}
				}
				
		if (userId < 0) { 
			// 注册失败
			obj.put("mailAddress", "4");
			JSONUtils.printObject(obj);
			return null;
		} else {
			
		    //注册成功，判断当前赠送体验券功能是否开启，开启则绑定体验券到此用户
		    Map<String, String> syspar =  selectedService.getSyspar("TYQ");
		    if (syspar!=null  && syspar.get("selectKey")!=null && !"".equals(syspar.get("selectKey"))) {
                    int deleted = Convert.strToInt(syspar.get("deleted"), -1);
                    if (deleted==1) {//表示启动此功能
                          int  batchId = Convert.strToInt(syspar.get("selectValue"), -1); //得到批次号
                          if (batchId!=-1) {
                              //获取对应批次号的一张未绑定的体验券
                             Map<String, String>  ticket =  experienceVoucherService.getTicket(batchId);
                             if (ticket!=null &&  ticket.get("ticketNo")!=null && !"".equals(ticket.get("ticketNo"))) {
                                  String ticketNo = ticket.get("ticketNo");
                                  Map<String, String> bindResult =  experienceVoucherService.activeTicket(userId.intValue(), ticketNo);
                                  log.info("绑定结果：================"+bindResult);
                            }
                        }
                    }
            }
			//修改交易密码
			//homeInfoSettingService.updateUserPassword(userId, tradPassWord, "tradpwd");
			
			//如果邮箱不为空，则发生绑定邮箱
			if(StringUtils.isNotBlank(userEntity.getEmail())){
				// 组合加密验证链接
				DesSecurityUtil des = new DesSecurityUtil();
				String key1 = des.encrypt(userId.toString());
				String key2 = des.encrypt(new Date().getTime() + "");
				String key3=userEntity.getEmail();
				String url = getPath(); // request().getRequestURI();
				String VerificationUrl = url + "verificationEmial.mht?key="
						+ key1 + "-" + key2 + "-" + key3;;
				
				// 发送验证邮件
				mailSendService.sendRegEmail(userEntity.getUserName(), userEntity.getEmail(), VerificationUrl);
				// ===截取emal后面地址
				int dd = userEntity.getEmail().indexOf("@");
				String mailAddress = null;
				if (dd >= 0) {
					mailAddress = "mail." + userEntity.getEmail().substring(dd + 1);
				}

				obj.put("isMail", "1");//是否发送了邮件
				obj.put("mail", mailAddress);//是否发送了邮件
				session().setAttribute("mail", mailAddress);
				
				
			}else{
				obj.put("isMail", "0");//是否发送了邮件
			}
			
			
			//如果是企业注册，往企业基础表添加数据
			if(type==1){
				Map m = new HashMap();
				m.put("orgId", userId);
				m.put("organizationName", userEntity.getOrgName());
				m.put("source", source);
				//userService.addUserCompanyData(userId, userEntity.getOrgName(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				userService.addUserCompanyData2(m);
			}
			//手机注册
			userService.updateSign(userId);//修改校验码
			//添加通知默认方法
			homeInfoSettingService.addNotes(userId, true, false, false);
			homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
			  //====
			obj.put("mailAddress", "注册成功");//注册成功
			JSONUtils.printObject(obj);
			
			//修改之前的推荐
			if(recommendUserId>0){//判断是否为空
				
				List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
				if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
					return null;
				}
				recommendUserService.addRecommendUser(userId, recommendUserId);
				

                /*//推荐人送一次刮奖，加好币的方式实现 
    		    Map maward = sysparService.getAwardJudge("25");
    		    String a = (String) maward.get("introduce");
    		    if (!StringUtil.isEmpty(a) && a.equals("1")){
    		    	
    		    	//添加送好币记录
    		    	sysparService.addUserSore(recommendUserId);
    		    }*/
			}
			
			//注册送一次刮奖，加好币的方式实现 
			boolean active = sysparService.isOpen("OPEN", "25");
		    if (active){
		    	//送红包
		    	long result = userService.addPresrent(10, userId,0);
		    	homeInfoSettingService.addAwardUser(userId);
		    	/*Map map1 = userService.queryUserById(userId);//.queryPersonById(userId);
		    	log.info("------------- map:" + map1);
		    	SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		    	String d = (String) map1.get("createTime");
		    	Date redate = sf.parse(d);
		    	
		    	//活动起止时间   是改日期 TODO
		    	Date sdate = sf.parse("2015-04-13 10:00:00");//yyyy-MM-dd HH:mm:ss
		    	Date edate = sf.parse("2015-05-13 18:00:00");
		    	
		    	//添加送好币记录
		    	if (redate.after(sdate) && redate.before(edate)){
		    		sysparService.addUserSore(userId);
		    		//直接抽奖
		    		homeInfoSettingService.awardStart("4", userId, 2);
		    	}*/
		    }
		    
			User user = new User();
			user.setUserName(userEntity.getUserName());
			user.setPassword(userEntity.getPassword());
			user.setEmail("default@163.com");
			//bbsRegisterService.doRegisterByAsynchronousMode(userEntity);
			
			
			//实现注册成功登录
			String lastIP = ServletUtils.getRemortIp();
			String lastTime = DateUtil.dateToString(new Date());
			try {
				user = userService.userLogin1(userEntity.getUserName(), userEntity.getPassword(), lastIP, lastTime,"dm");
				session().setAttribute(IConstants.SESSION_USER, user);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return null;
		}
	}
	
	public String registerThirdInit(){
		Integer isMail=Convert.strToInt(request("isMail"), 0);
		paramMap.put("isMail", request("isMail"));
		if(isMail==1){
			paramMap.put("mail", request("mail"));
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * @throws Exception
	 *             用户注册
	 * @throws SQLException
	 * @return String
	 * @throws IOException
	 * @throws MessagingException
	 * @throws
	 */
	public String register() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		String pageId = paramMap.get("pageId"); // 验证码
		String email = paramMap.get("email"); // 电子邮箱
		String phone=paramMap.get("cellphone"); // 手机号码
		String cellcode=paramMap.get("cellcode"); // 手机验证码
		String kingA = paramMap.get("king");//用户类型（个人用户，企业用户）
		String refferee = paramMap.get("refferee");//推荐人
		Map<String, Object> map = null;
		long recommendUserId = -1;//推荐人userId
		String userName = paramMap.get("userName"); // 用户名
		// 企业全称
 		String orgName = paramMap.get("orgName");
		
		String buzhou = paramMap.get("buzhou");
		
		// 用户密码
		String password = paramMap.get("password"); 
		String md5Password = password;
		// 用户确认密码
		String confirmPassword = paramMap.get("confirmPassword"); 
		String param = paramMap.get("param"); // 邀请好友链接携带的参数
		Long userId = -1L;
		
		
		//用户数据处理
		Users userEntity =new Users();
		
		if (StringUtils.isBlank(buzhou)) {
              JSONUtils.printStr("1");
              return null;
        }
		
		
		//判断是否是第一步，如果是则获取页面信息
		if(buzhou.equals("1")){
		
		/*//此时注册用户的类型
		String kingA = paramMap.get("king");
		//此时注册用户的种类
		String typeA = paramMap.get("type");
		int king=1;  //默认邮件个人注册
		if(kingA.equals("gerenImg")){
			if(typeA.equals("e")){
				king=1;  //邮件个人注册
			}else{
				king=3;  //手机个人注册
			}
		}else{
			if(typeA.equals("e")){
				king=2;  //邮件企业注册
			}else{
				king=4;  //手机企业注册
			}			
		}
		*/
		//邮件或者电话号码验证
//		if(email.length()!=0 && (king==1||king==2)){
//			if (StringUtils.isBlank(email)) {
//				obj.put("mailAddress", "12");
//				JSONUtils.printObject(obj);
//				return null;
//			}
//		}else if(phone.length()!=0 && (king==3||king==4)){
			//手机
			if (StringUtils.isBlank(phone)) {
				obj.put("mailAddress", "54");
				JSONUtils.printObject(obj);
				return null;
			}
			if (StringUtils.isBlank(cellcode)) {
				obj.put("mailAddress", "55");
				JSONUtils.printObject(obj);
				return null;
			}
			
//		}
		
		
		//用户名不为空
		if (StringUtils.isBlank(userName)) {
			obj.put("mailAddress", "13");
			JSONUtils.printObject(obj);
			return null;
		}
			
		//用户名长度大于2且小于20
		if (userName.length() < 2 || userName.length() > 20) {
			obj.put("mailAddress", "18");
			JSONUtils.printObject(obj);
			return null;
		}
		
		// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
		if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
			obj.put("mailAddress", "20");
			JSONUtils.printObject(obj);
			return null;
		}
		
		// 判断第一个字符串不能使以下划线开头的
		String fristChar = userName.substring(0, 1);
		if (fristChar.equals("_")) {
			obj.put("mailAddress", "21");
			JSONUtils.printObject(obj);
			return null;
		}
		
		

		
 		
 		if(!kingA.equals("gerenImg")){
//		if(orgName.length()!=0 && (king==2||king==4)){			
 			if (StringUtils.isBlank(orgName)) {
				obj.put("mailAddress", "55");
				JSONUtils.printObject(obj);
				return null;
			}
 			if (orgName.length() < 2 || orgName.length() > 20) {
				obj.put("mailAddress", "56");
				JSONUtils.printObject(obj);
				return null;
			}
			// 验证企业名称不含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
	
			if (orgName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
				obj.put("mailAddress", "57");
				JSONUtils.printObject(obj);
				return null;
			}
			// 判断第一个字符串不能使以下划线开头的
			String fristCharOrg = orgName.substring(0, 1);
			if (fristCharOrg.equals("_")) {
				obj.put("mailAddress", "58");
				JSONUtils.printObject(obj);
				return null;
			}
		}
 	
		if (StringUtils.isBlank(password)) {
			obj.put("mailAddress", "14");
			JSONUtils.printObject(obj);
			return null;
		}
		
		if (StringUtils.isBlank(confirmPassword)) {
			obj.put("mailAddress", "15");
			JSONUtils.printObject(obj);
			return null;
		}
		
		
		
		
		/*
		String code = (String) session().getAttribute(pageId + "_checkCode");//获取生成的验证码
		String _code = paramMap.get("code").toString().trim();//获取用户输入的验证码
		//比较验证码是否一致
		if (code == null || !_code.equals(code)) {
			obj.put("mailAddress", "0");
			JSONUtils.printObject(obj);
			return null;
		}*/
		
		//推荐人
		
		if (StringUtils.isNotBlank(refferee)) {
			//根据用户名查询推荐人用户明细
			Map<String, String> userIdMap = userService.queryIdByUser(refferee);
			if (userIdMap != null) {
				recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
			}
			//判断推荐人是否为推广人
			map = relationService.isPromoter(refferee);
			if (map == null) {
				refferee = null;
			}
			if (userIdMap == null && map == null) {
				obj.put("mailAddress", "5");
				JSONUtils.printObject(obj);
				return null;
			}

		}
		
		// 判断密码是否一致
		if (!password.equals(confirmPassword)) {
			obj.put("mailAddress", "1");
			JSONUtils.printObject(obj);
			return null;
		}
		
		//用户名、email、手机号码、企业全称唯一认证
		try {
			Long result = userService.isExistEmailORUserName(null, userName,null,null);
			result = result +userService.isExistEmailORUserName(null, null,null,userName);
			if (result > 0) { // 用户名重复
				obj.put("mailAddress", "2");
				JSONUtils.printObject(obj);
				return null;
			}
			if(email.length()!=0){
				result = userService.isExistEmailORUserName(email, null,null,null);
				if (result > 0) { // email重复
					obj.put("mailAddress", "3");
					JSONUtils.printObject(obj);
					return null;
				}
			}else{
				result = userService.isExistEmailORUserName(null, null,null,phone);
				if (result > 0) { // 手机号码重复
					obj.put("mailAddress", "52");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			if(orgName.length()!=0){
				result = userService.isExistEmailORUserName(null, null,orgName,null);
				if (result > 0) { // 企业全称重复
					obj.put("mailAddress", "51");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			Thread.sleep(100);
			//IConstants.ENABLED_PASS是否启用加密
			if ("1".equals(IConstants.ENABLED_PASS)) {
				md5Password = com.shove.security.Encrypt
						.MD5(md5Password.trim());
			} else {
				md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()
						+ IConstants.PASS_KEY);
			}
			
			
			
			
			userEntity.setEmail(email);
			userEntity.setUserName(userName);
			userEntity.setOrgName(orgName);
			userEntity.setMobilePhone(phone);
			userEntity.setPassword(md5Password);
			userEntity.setRefferee(refferee);
			session().setAttribute("LinUserEntity", userEntity);
			session().setAttribute("mapref", map);//推荐人是否为推广人保存到session
			session().setAttribute("recommendUserIdref", recommendUserId);//推荐人userId
			return null;
			} catch (SQLException e) {
				obj.put("mailAddress", "16");
				JSONUtils.printObject(obj);
				e.printStackTrace();
				throw e;
	
			}
				
		}else{
			//步骤2
			userEntity =(Users)session().getAttribute("userEntity");//获取步骤1的数据
			map=(Map<String, Object>)session().getAttribute("mapref");
			recommendUserId=(Long)session().getAttribute("recommendUserIdref");
//调用添加用户service
			
			//邮件注册先注册再激活
			//手机注册，先判断验证码是否发送成功，在判断验证码是否正确，直接激活
			/*if(king==1 || king==2){
				userEntity.setEnable(2);
				int typelen = -1;
				//userId=userService.userRegister(userEntity, king);
				//userId = userService.userRegister1(email, userName, md5Password,
						//refferee, map, typelen, null);// 注册用户 和初始化图片资料
				
				userId = userService.userRegister1(email, userName, orgName, md5Password, refferee, map, typelen, null);
			}else */
			
//			if(king==3 || king==4){
				Map<String,String> smsMap=(Map<String,String>)session().getAttribute("smsMap");
				//手机注册用户
				if(smsMap.get("StutsCode").equals("Sucess")){
					if(smsMap.get("randomCode").equals(cellcode)){
						if(smsMap.get("phone").equals(phone)){
							userEntity.setEnable(1);
							//userId=userService.userRegister(userEntity, king);
							int typelen = -1;
							Map<String,String> lenMap = null;
							lenMap = userService.querymaterialsauthtypeCount();	//查询证件类型主表有多少种类型
							if(lenMap!=null&&lenMap.size()>0){
								typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
								if(typelen!=-1){
									//个人用户
									if(kingA.equals("gerenImg")){
										userEntity.setUserType(1);
									}else{
										//企业用户
										userEntity.setUserType(2);
										
									}
									
									userId=cellPhoneService.usercellRegister(userEntity.getMobilePhone(),userEntity.getUserName(),userEntity.getPassword(),
											userEntity.getRefferee(),map,typelen,userEntity.getUserType(),userEntity.getOrgName());//注册用户 和  初始化图片资料
								}
							}
						}else{
							obj.put("mailAddress", "71");
							JSONUtils.printObject(obj);
							return null;
						}
					}else{
						obj.put("mailAddress", "60");
						JSONUtils.printObject(obj);
						return null;
					}
				}else{
					obj.put("mailAddress", "61");
					JSONUtils.printObject(obj);
					return null;
				}
//			}
			
			
			if (userId < 0) { 
				// 注册失败
				obj.put("mailAddress", "4");
				JSONUtils.printObject(obj);
				return null;
			} else {
				
				//如果是企业注册，往企业基础表添加数据
				if(!kingA.equals("gerenImg")){
					userService.addUserCompanyData(userId, userEntity.getOrgName(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
				}
				
				/*//邮箱注册
				if(typeA.equals("e")){
					
					userService.updateSign(userId);//修改校验码
					// 添加论坛用户
					User user = new User();
					user.setUserName(userName);
					user.setPassword(password);
					user.setEmail(email);
					bbsRegisterService.doRegisterByAsynchronousMode(user);

					// 组合加密验证链接
					DesSecurityUtil des = new DesSecurityUtil();
					String key1 = des.encrypt(userId.toString());
					String key2 = des.encrypt(new Date().getTime() + "");

					String url = getPath(); // request().getRequestURI();
					String VerificationUrl = url + "verificationEmial.do?key="
							+ key1 + "-" + key2;
					// 发送验证邮件
					mailSendService.sendRegEmail(userName, email, VerificationUrl);
					// ===截取emal后面地址
					int dd = email.indexOf("@");
					String mailAddress = null;
					if (dd >= 0) {
						mailAddress = "mail." + email.substring(dd + 1);
					}
					session().setAttribute("mail", mailAddress);
					// 添加通知默认方法
					homeInfoSettingService.addNotes(userId, true, true, false);
					homeInfoSettingService.addNotesSetting(userId, true, true,
							true, true, true, true, true, false, false, false,
							false, false, false, false, false);
					// ====
					obj.put("mailAddress", mailAddress);
					JSONUtils.printObject(obj);
					
					
					
					JSONUtils.printStr(IConstants.USER_REGISTER_OK);
					// 注册成功后判断是否是推广注册的。
					// 修改之前的推荐
					try {
						if (recommendUserId > 0) {// 判断是否为空

							List<Map<String, Object>> list = recommendUserService
									.queryRecommendUser(null, userId, null);// 查询用户是否已经存在关系了。
							if (list != null && list.size() > 0) {// 判断之前是否已经有关系了。
								return null;
							}
							recommendUserService.addRecommendUser(userId, recommendUserId);
						}
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
					if (IConstants.ISDEMO.equals("1")) {
						obj.put("mailAddress", "99");
						JSONUtils.printObject(obj);
						return null;
					}
					return null;
				}else{*/
					//手机注册
					
					userService.updateSign(userId);//修改校验码
					//添加通知默认方法
					homeInfoSettingService.addNotes(userId, true, false, false);
					homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
					  //====
					obj.put("mailAddress", "注册成功");//注册成功
					JSONUtils.printObject(obj);
					
					//修改之前的推荐
					if(recommendUserId>0){//判断是否为空
						
						List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
						if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
							return null;
						}
						recommendUserService.addRecommendUser(userId, recommendUserId);
					}
					
					User user = new User();
					user.setUserName(userName);
					user.setPassword(password);
					user.setEmail("default@163.com");
					bbsRegisterService.doRegisterByAsynchronousMode(userEntity);
					
					return null;
					
					
					
//				}
			}
		}
			
			
			
		
		
	}
	
	
	public String registerOneTZ() throws Exception {
		return SUCCESS;
	
	}
	
	
	
	public String registerOneWap() throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        JSONObject obj = new JSONObject();
        String phone=paramMap.get("cellphone"); // 手机号码
        String cellcode=paramMap.get("code"); // 验证码
        Integer type = 1 ;//用户类型（1.个人用户 2.企业用户 ，）
        String refferee = paramMap.get("refferee");//推荐人
        Map<String, Object> map = null;
        long recommendUserId = -1;//推荐人userId
        String userName = paramMap.get("userName"); // 用户名
        String password = paramMap.get("password"); // 用户确认密码，加密了
        String confirmPassword = paramMap.get("confirmPassword"); //加密了
        
        //手机
        if (StringUtils.isBlank(phone)) {
            obj.put("code", "54");
            obj.put("msg", "请填写您的手机号码");
            JSONUtils.printObject(obj);
            return null;
        }
        //验证码为空
        if (StringUtils.isBlank(cellcode)) {
            obj.put("code", "55");
            obj.put("msg", "请填写验证码");
            JSONUtils.printObject(obj);
            return null;
        }
        //验证码不正确，请重新输入
        String code = (String) session().getAttribute("userreg_checkCode");
        if (StringUtils.isBlank(code) || !cellcode.equals(code)) {
            obj.put("code", "55");
            obj.put("msg", "验证码不正确，请重新输入");
            JSONUtils.printObject(obj);
            session().removeAttribute("userreg");
            return null;
        }
        
        //用户名不为空
        if (StringUtils.isBlank(userName)) {
            obj.put("code", "13");
            obj.put("msg", "请填写您的用户名");
            JSONUtils.printObject(obj);
            return null;
        }
        
        if (StringUtils.isBlank(password)) {
            obj.put("code", "14");
            obj.put("msg", "请填写密码");
            JSONUtils.printObject(obj);
            return null;
        }
        
        if (StringUtils.isBlank(confirmPassword)) {
            obj.put("code", "15");
            obj.put("msg", "请再次确认密码");
            JSONUtils.printObject(obj);
            return null;
        }
        
        //用户名长度大于2且小于20
        if (userName.length() < 2 || userName.length() > 20) {
            obj.put("code", "18");
            obj.put("msg", "用户名长度大于2且小于20");
            JSONUtils.printObject(obj);
            return null;
        }
        
        
        // 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
        if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
            obj.put("code", "20");
            obj.put("msg", "用户名不能包含特殊字符");
            JSONUtils.printObject(obj);
            return null;
        }
        
        // 判断第一个字符串不能使以下划线开头的
        String fristChar = userName.substring(0, 1);
        if (fristChar.equals("_")) {
            obj.put("code", "21");
            obj.put("msg", "用户名不能以下划线开头");
            JSONUtils.printObject(obj);
            return null;
        }

       
        //推荐人
        if (StringUtils.isNotBlank(refferee)) {
            //根据用户名查询推荐人用户明细
            Map<String, String> userIdMap = userService.queryIdByUser(refferee);
            if (userIdMap != null) {
                recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
            }
            //判断推荐人是否为推广人
            map = relationService.isPromoter(refferee);
            if (map == null) {
                refferee = null;
            }
            if (userIdMap == null && map == null) {
                obj.put("code", "5");
                obj.put("msg", "推荐人不存在，请重新填写");
                JSONUtils.printObject(obj);
                return null;
            }

        }
        
        // 判断密码是否一致
        if (!password.equals(confirmPassword)) {
            obj.put("code", "1");
            obj.put("msg", "两次密码不一致，请重新输入");
            JSONUtils.printObject(obj);
            return null;
        }
        
        //用户名、email、手机号码、企业全称唯一认证
        try {
            Long result = userService.isExistEmailORUserName(null, userName,null,null);
            result = result +userService.isExistEmailORUserName(null, null,null,userName);
            if (result > 0) { // 用户名重复
                obj.put("code", "2");
                obj.put("msg", "用户名已存在，请重新输入");
                JSONUtils.printObject(obj);
                return null;
            }

            result = userService.isExistEmailORUserName(null, null,null,phone);
            if (result > 0) { // 手机号码重复
                obj.put("code", "52");
                obj.put("msg", "手机号码已被使用，请重新输入");
                JSONUtils.printObject(obj);
                return null;
            }
            
            Thread.sleep(100);
            String md5Password = password;
            //IConstants.ENABLED_PASS是否启用加密
            if ("1".equals(IConstants.ENABLED_PASS)) {
                md5Password = com.shove.security.Encrypt
                        .MD5(md5Password.trim());
            } else {
                md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()
                        + IConstants.PASS_KEY);
            }
            //用户数据处理
            Users userEntity =new Users();
            userEntity.setUserName(userName);
            userEntity.setMobilePhone(phone);
            userEntity.setPassword(md5Password);
            userEntity.setRefferee(refferee);
            session().setAttribute("LinUserEntity", userEntity);
            session().setAttribute("mapref", map);//推荐人是否为推广人保存到session
            session().setAttribute("recommendUserIdref", recommendUserId);//推荐人userId
            session().setAttribute("type", type);
            obj.put("code", "99");
            
            Users users = new Users();
			users.setPassword(password);
			users.setUserName(userName);
			users.setMobilePhone(phone);
			//注册论坛
			bbsRegisterService.doRegisterByAsynchronousMode(users);
            
            JSONUtils.printObject(obj);
            } catch (SQLException e) {
                obj.put("code", "16");
                JSONUtils.printObject(obj);
                e.printStackTrace();
                throw e;
            }
        session().setAttribute("userreg_checkCode", "EF-E");//去除验证码
        return null;
    }
	
	public String registerOne() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		String email = paramMap.get("email"); // 电子邮箱
		String phone=paramMap.get("cellphone"); // 手机号码
		String cellcode=paramMap.get("cellcode"); // 手机验证码
		Integer type = Convert.strToInt(paramMap.get("type"),2) ;//用户类型（1.企业用户 2.个人用户，）
		String refferee = paramMap.get("refferee");//推荐人
		Map<String, Object> map = null;
		long recommendUserId = -1;//推荐人userId
		String userName = paramMap.get("userName"); // 用户名
 		String orgName = paramMap.get("orgName");// 企业全称
		String password = paramMap.get("password"); // 用户确认密码
		String md5Password = password;
		String confirmPassword = paramMap.get("confirmPassword"); 
		String source = paramMap.get("source"); // 用户名
		
		//用户数据处理
		Users userEntity =new Users();
		
		//手机
		if (StringUtils.isBlank(phone)) {
			obj.put("mailAddress", "54");
			JSONUtils.printObject(obj);
			return null;
		}
		//手机验证码为空
		if (StringUtils.isBlank(cellcode)) {
			obj.put("mailAddress", "55");
			JSONUtils.printObject(obj);
			return null;
		}
		

		//用户名不为空
		if (StringUtils.isBlank(userName)) {
			obj.put("mailAddress", "13");
			JSONUtils.printObject(obj);
			return null;
		}
		

		//用户名长度大于2且小于20
		if (userName.length() < 2 || userName.length() > 20) {
			obj.put("mailAddress", "18");
			JSONUtils.printObject(obj);
			return null;
		}
		
		// 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
		if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
			obj.put("mailAddress", "20");
			JSONUtils.printObject(obj);
			return null;
		}
		
		// 判断第一个字符串不能使以下划线开头的
		String fristChar = userName.substring(0, 1);
		if (fristChar.equals("_")) {
			obj.put("mailAddress", "21");
			JSONUtils.printObject(obj);
			return null;
		}
 		if(type==1){
			if (orgName.length() < 2 || orgName.length() > 20) {
				obj.put("mailAddress", "56");
				JSONUtils.printObject(obj);
				return null;
			}
			if (StringUtils.isBlank(orgName)) {
				obj.put("mailAddress", "55");
				JSONUtils.printObject(obj);
				return null;
			}
			// 验证企业名称不含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
			if (orgName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
				obj.put("mailAddress", "57");
				JSONUtils.printObject(obj);
				return null;
			}
			// 判断第一个字符串不能使以下划线开头的
			String fristCharOrg = orgName.substring(0, 1);
			if (fristCharOrg.equals("_")) {
				obj.put("mailAddress", "58");
				JSONUtils.printObject(obj);
				return null;
			}
		}
 	
		if (StringUtils.isBlank(password)) {
			obj.put("mailAddress", "14");
			JSONUtils.printObject(obj);
			return null;
		}
		
		if (StringUtils.isBlank(confirmPassword)) {
			obj.put("mailAddress", "15");
			JSONUtils.printObject(obj);
			return null;
		}
		//推荐人
		if (StringUtils.isNotBlank(refferee)) {
			//根据用户名查询推荐人用户明细
			Map<String, String> userIdMap = userService.queryIdByUser(refferee);
			if (userIdMap != null) {
				recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
			}
			//判断推荐人是否为推广人
			map = relationService.isPromoter(refferee);
			if (map == null) {
				refferee = null;
			}
			if (userIdMap == null && map == null) {
				obj.put("mailAddress", "5");
				JSONUtils.printObject(obj);
				return null;
			}

		}
		
		// 判断密码是否一致
		if (!password.equals(confirmPassword)) {
			obj.put("mailAddress", "1");
			JSONUtils.printObject(obj);
			return null;
		}
		
		//用户名、email、手机号码、企业全称唯一认证
		try {
			Long result = userService.isExistEmailORUserName(null, userName,null,null);
			if (result > 0) { // 用户名重复
				obj.put("mailAddress", "2");
				JSONUtils.printObject(obj);
				return null;
			}
			if(StringUtils.isNotBlank(email) && email.length()!=0){
				result = userService.isExistEmailORUserName(email, null,null,null);
				if (result > 0) { // email重复
					obj.put("mailAddress", "3");
					JSONUtils.printObject(obj);
					return null;
				}
			}else{
				result = userService.isExistEmailORUserName(null, null,null,phone);
				if (result > 0) { // 手机号码重复
					obj.put("mailAddress", "52");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			if(StringUtils.isNotBlank(orgName) && orgName.length()!=0){
				result = userService.isExistEmailORUserName(null, null,orgName,null);
				if (result > 0) { // 企业全称重复
					obj.put("mailAddress", "51");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			Thread.sleep(100);
			//IConstants.ENABLED_PASS是否启用加密
			if ("1".equals(IConstants.ENABLED_PASS)) {
				md5Password = com.shove.security.Encrypt
						.MD5(md5Password.trim());
			} else {
				md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()
						+ IConstants.PASS_KEY);
			}
			Map<String,String> smsMap=(Map<String,String>)session().getAttribute("smsMap");
			if(smsMap==null){
				obj.put("mailAddress", "70");
				JSONUtils.printObject(obj);
				return null;
			}else{
				if(!smsMap.get("StutsCode").equals("Sucess")){
					obj.put("mailAddress", "61");
					JSONUtils.printObject(obj);
					return null;
				}
				if(!smsMap.get("randomCode").equals(cellcode)){
					obj.put("mailAddress", "60");
					JSONUtils.printObject(obj);
					return null;
				}
				if(!smsMap.get("phone").equals(phone)){
					obj.put("mailAddress", "71");
					JSONUtils.printObject(obj);
					return null;
				}
						
			}
			
			userEntity.setEmail(email);
			userEntity.setUserName(userName);
			userEntity.setOrgName(orgName);
			userEntity.setMobilePhone(phone);
			userEntity.setPassword(md5Password);
			userEntity.setRefferee(refferee);
			userEntity.setSource(source);
			session().setAttribute("LinUserEntity", userEntity);
			session().setAttribute("mapref", map);//推荐人是否为推广人保存到session
			session().setAttribute("recommendUserIdref", recommendUserId);//推荐人userId
			session().setAttribute("type", type);
			obj.put("mailAddress", "99");
			
			Users users = new Users();
			users.setPassword(password);
			users.setUserName(userName);
			users.setMobilePhone(phone);
			//注册论坛
			bbsRegisterService.doRegisterByAsynchronousMode(users);
			
			
			
			JSONUtils.printObject(obj);
			return null;
			} catch (SQLException e) {
				obj.put("mailAddress", "16");
				JSONUtils.printObject(obj);
				e.printStackTrace();
				throw e;
	
			}
	}
	
	/*
	 * 注册短信发送
	 */
	public String ajaxSendSMS() throws Exception{
		log.info("IP:" + ServletUtils.getIpAddress(request()) + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String wap = request("wap");
		if (StringUtils.isNotBlank(wap)) {
		    Users user =  (Users) session().getAttribute("LinUserEntity");
		    Map<String,String> resultMap=new HashMap<String, String>();
		    if (user==null) {
		        resultMap.put("code", "1");
		        resultMap.put(" msg", "参数信息就丢失，请重新开始注册");
		        JSONUtils.printObject(resultMap);
		        return null;
            }else {
                String mobilePhone = user.getMobilePhone();
                int count  = 0;
                Object cObject = session(mobilePhone)==null?"":session(mobilePhone);
                count = Convert.strToInt(cObject.toString(), 0);
                if (count<=4) {
                    String code = RandomStringUtils.randomNumeric(4);
                    boolean result = false;
                    if (StringUtils.isNotBlank(request("vioce"))) {//语音
                        result = RestUtil.voiceCode(code, mobilePhone);
                        userService.addSendLog(mobilePhone,"注册短信,发送类型：语音",60L);
                    }else {//短信
                        String content="尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME + ",手机验证码为:";
                        String statu = SMSUtil.sendSMS("", "", content, mobilePhone, code);
                        if ("3".equals(statu)) {//短信发送已上限
                            resultMap.put("code", "2");
                            resultMap.put(" msg", "您今日的短信发送数量已上限，无法继续发送");
                            JSONUtils.printObject(resultMap);
                            return null;
                        }
                        result  = statu.equals("Sucess");
                        userService.addSendLog(mobilePhone,"注册短信,发送类型：短信",60L);
                    }
                    if (result) {
                        resultMap.put("code", "3");
                        resultMap.put(" msg", "验证码已发送到你的手机："+mobilePhone+"，请注意查看");
                        JSONUtils.printObject(resultMap);
                        session().setAttribute(code, mobilePhone);
                    }else {
                        resultMap.put("code", "3");
                        resultMap.put(" msg", "验证码发送失败，请联系客服");
                        JSONUtils.printObject(resultMap);
                    }
                    count++;
                    session().setAttribute(mobilePhone, count+"");
                    return null;
                }else {
                    resultMap.put("code", "2");
                    resultMap.put(" msg", "您今日的短信发送数量已上限，无法继续发送");
                    JSONUtils.printObject(resultMap);
                    return null;
                }
            }
        }
		
		String key=paramMap.get("key"); 
		String phone=paramMap.get("cellphone"); // 手机号码
		Map<String,String> resultMap=new HashMap<String, String>();
	      
        //手机
        if (StringUtils.isBlank(phone)) {
            resultMap.put("result", "-2");
            JSONUtils.printObject(resultMap);
            return null;
        }
        
        long results = userService.isExistEmailORUserName(null, null,null,phone);
        if (results > 0) { // 手机号码重复
            resultMap.put("result", "-3");
            JSONUtils.printObject(resultMap);
            return null;
        }else{
            Long re = userService.isExistPersonByPhone(phone);
            if(re>0){
                JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_PHONE);
                return null;
            }
        }
		
		/*boolean iswap = false;
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(phone)) {//专为wap站准备的
		    byte[] en_result = HexToBytes.HexString2Bytes(key);
	        byte[] de_result = RSAUtil.decrypt(RSAUtil.getKeyPair().getPrivate(),en_result);
	        StringBuffer sb = new StringBuffer();
            sb.append(new String(de_result));
	        String e=Escape.unescape(sb.toString());
	        if (phone.equals(e)) {
	            iswap = true;
            }
	        en_result = null;
	        de_result = null;
	        sb = null;
        }*/
		
		String header = request().getHeader("X-Requested-With");
        if (header == null || !"XMLHttpRequest".equals(header)) {//只能ajax请求
            return null;
        }
        
        //来源
        String retUrl = request().getHeader("Referer");//只能是从reg.do跳转过来的
        if (StringUtils.isBlank(retUrl) || !retUrl.contains("reg.")) {
            return null;
        }
		
		Object cookiekey =  CookieUtils.getKey(request(), "key");
		
		Object sessionkey = session("key");
		
		if (StringUtils.isBlank(key)) {//不做任何提示
		    resultMap.put("result", 1+"");
            JSONUtils.printObject(resultMap);
            return null;
        }else {
            if (sessionkey!=null) {//第二次做session验证
                if (!key.equals(sessionkey)) {
                    resultMap.put("result", 1+"");
                    JSONUtils.printObject(resultMap);
                    return null;
                }
            }else {
                if (cookiekey==null || !key.equals(cookiekey)) {//第一次做cookies验证
                    resultMap.put("result", 1+"");
                    JSONUtils.printObject(resultMap);
                    return null;
                }
            }
        }
		
		String ip = ServletUtils.getIpAddress(request());
		
		//统计IP访问次数
		String acount_str = (String) session().getAttribute(ip);
		int ipsmsacount = 5;
		if (acount_str!=null) {
			Map<String, String> map_count =  sysparService.querySysparBySql("SELECT t.selectValue AS smsacount FROM t_select t WHERE t.`selectKey` =\"IPSMSACOUNT\"");
            if(map_count!=null && StringUtils.isNotBlank(map_count.get("smsacount"))){
                ipsmsacount = Integer.parseInt(map_count.get("smsacount"));
            }
			int int_account = Integer.parseInt(acount_str);
			if (int_account>=ipsmsacount) {
				log.info("超过次数..~~~");
				resultMap.put("result", 1+"");
                JSONUtils.printObject(resultMap);
                return null;
			}else {
				int_account++;
				session().setAttribute(ip, int_account +"");
			}
		}else {
			session().setAttribute(ip, "1");
		}
		
		
		DesSecurityUtil entry = new DesSecurityUtil();
		key =entry.decrypt(key);
		try{
		        String [] keys = key.split("&");
                long time = Long.valueOf(keys[0]);
                long acount = Long.valueOf(keys[1]);
                if (keys.length>=3) {
                    long hasSession = Long.valueOf(keys[2]);
                    if (hasSession==0 && sessionkey==null) {//如果是已经是sessionkey了。session中却没有值
                        resultMap.put("result", 1+"");
                        JSONUtils.printObject(resultMap);
                        return null;
                    }
                }
                if (acount>=3) {
                    resultMap.put("result", "-5");
                    JSONUtils.printObject(resultMap);
                    return null;
                }
                long now = new Date().getTime();
                log.info("==============间隔时间："+(now-time));
                log.info("==============规定时间："+1000*invertal);
                if ((now-time)<(1000*invertal) || (now-time)-(1000*invertal)<1000) {//小于5秒钟点击.或者直接copy的。不发送
                    resultMap.put("result", "1");
                    key = entry.encrypt(now+"&"+acount+"&"+0);
                    resultMap.put("key", key);
                    JSONUtils.printObject(resultMap);
                    log.info("==============超时了。。。：");
                    return null;
                }else {
                    acount++;
                    if (sessionkey==null) {
                        key = entry.encrypt(now+"&"+acount+"&"+0);
                    }else {
                        key = entry.encrypt(now+"&"+acount);
                    }
                }
                session().setAttribute("key", key);
       } catch (Exception e) {
           e.printStackTrace();
           resultMap.put("result", "1");
           JSONUtils.printObject(resultMap);
           return null;
        }
		
		String userName="";
		String result ="-1";
		

		Pattern p=Pattern.compile("^\\d{11}$");//手机号码正则表达式
		Matcher m=p.matcher(phone);	//判断页面获取的手机号码是否与正则表达式匹配
		if (m.matches()){
			Random rand = new Random();
			//String randomCode = rand.nextInt(999999)+"";
//			randomCode="123123";
			String randomCode;// = rand.nextInt(999999)+"";
			DecimalFormat df = new DecimalFormat("0000");
			randomCode = df.format(rand.nextInt(9999));
			System.out.println("randomCode....."+randomCode);
//			randomCode="123123";
			String confirmPassword="";
			String content="尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME + ",手机验证码为:";
			
			try {
			    Map<String,String> smsMap=new HashMap<String, String>();
			    if (StringUtils.isNotBlank(paramMap.get("vioce"))) {//语音发送
                    if (RestUtil.voiceCode(randomCode, phone)) {
                        resultMap.put("result", 1+"");
                        smsMap.put("StutsCode", "Sucess");
                    }else {
                        resultMap.put("result", -1+"");
                        smsMap.put("StutsCode", "Fail");
                    }
                  //记录插入时间 
                    userService.addSendLog(phone,"注册短信,发送类型：语音",60L);
                }else {//短信发送
                    String statu = SMSUtil.sendSMS(userName, confirmPassword, content, phone, randomCode);
                    if ("3".equals(statu)) {//短信发送已上限
                        JSONUtils.printStr("3");
                        return null;
                    }
//                  String statu="Sucess";
                    result = statu.equals("Sucess") ? "1":"-1";
                    smsMap.put("StutsCode", statu);
                    resultMap.put("result", result+"");
                    //记录插入时间 
                    userService.addSendLog(phone,"注册短信,发送类型：短信",60L);
                }
			    smsMap.put("randomCode",randomCode );
                smsMap.put("phone", phone);
                session().setAttribute("smsMap", smsMap);
                resultMap.put("key", key);
				//记录IP
				Map<String,String> count = (Map<String, String>) session().getAttribute("acount");
				if (count!=null) {
                    try {
                        int ip_count = Integer.parseInt(count.get(ip));
                        
                        Map<String, String> map_email = sysparService.querySysparBySql("SELECT t.`selectName` AS email FROM  t_select t WHERE t.`selectKey` =\"IPEMAIL\" ");
                        String email = map_email!=null?map_email.get("email"):"hejiahua@sanhaodai.com";
                        if (ip_count>=ipsmsacount) {//超过5条发送警报信息
                            sendMailService.sendRegisterVerificationEmail("IP:"+ip+",发送短信已到达："+ip_count+"次，请立即去检查！", userName, email);
                        }
                        ip_count++;
                        count.put(ip, ip_count+"");
                        session().setAttribute("acount",count);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    count = new HashMap<String, String>();
                    count.put(ip, 1+"");
                    session().setAttribute("acount",count);
                }
				
				JSONUtils.printObject(resultMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JSONUtils.printStr("-2");
		}
		
		
		return null;
		
		
	}
	
	
	/*
	 * 用户通过手机找回密码发送验证码
	 */
	public String ajaxSendSMS1() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String phone=paramMap.get("cellphone"); // 手机号码
		String vioce=paramMap.get("vioce"); // 手机号码
		String userName="";
		String result ="-1";
		//手机
		if (StringUtils.isBlank(phone)) {
			JSONUtils.printStr("-2");
			return null;
		}
		
		long hasPhone = userService.isExistEmailORUserName(null, null,null,phone);
		if (hasPhone<=0) {//不存在
		    JSONUtils.printStr("-3");
		    return null;
        }

		//记录插入时间 
		userService.addSendLog(phone,"找回登陆密码",60L);
		
		//Pattern p=Pattern.compile("^\\d{11}$");//手机号码正则表达式
		//Matcher m=p.matcher(phone);	//判断页面获取的手机号码是否与正则表达式匹配
		//if (m.matches()){
			Random rand = new Random();
			String randomCode;// 
			DecimalFormat df = new DecimalFormat("0000");
			randomCode = df.format(rand.nextInt(9999));
			System.out.println("randomCode....."+randomCode);
			String confirmPassword="";
			String content="尊敬的客户您好,欢迎使用" + IConstants.PRO_GLOBLE_NAME + ",手机验证码为:";
			try {
			    String statu = "";
			    if (StringUtils.isNotBlank(vioce)) {//语音发送
                    if (RestUtil.voiceCode(randomCode, phone)) {
                        statu = "Sucess";
                    }else {
                        statu = "Fail";
                    }
                  //记录插入时间 
                    userService.addSendLog(phone,"注册短信,发送类型：语音",60L);
                }else {
                    statu = SMSUtil.sendSMS(userName, confirmPassword, content, phone, randomCode);
                    if ("3".equals(statu)) {//短信发送已上限
                        JSONUtils.printStr(statu);
                        return null;
                    }
                    userService.addSendLog(phone,"注册短信,发送类型：短信",60L);
                }
			    result = statu.equals("Sucess") ? "1":"-1";
				Map<String,String> smsMap=new HashMap<String, String>();
				smsMap.put("randomCode",randomCode );
				smsMap.put("StutsCode", statu);
				smsMap.put("phone", phone);
				session().setAttribute("smsMap", smsMap);
				JSONUtils.printStr(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		//}else{
		//	JSONUtils.printStr("-2");
		//}
		
		
		return null;
		
		
	}
	

	private long r_userId;

	public long getR_userId() {
		return r_userId;
	}

	public void setR_userId(long id) {
		r_userId = id;
	}

	// add by houli
	public String reActivateEmail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String email = request.getString("email") == null ? null : Convert.strToStr(
				SqlInfusion.FilteSqlInfusion(request.getString("email")), null);
		try {
			if (email == null) {
				JSONUtils.printStr("1");
				return INPUT;
			}
			Map<String, String> userm = userService.queryUserByName(email);
			if(userm == null){
				return INPUT;
			}
			email = userm.get("email");
			
			Pattern p = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
			Matcher matcher = p.matcher(email);
			if (!matcher.matches()) {
				return INPUT;
			}
			long id = -100;
			// 根据邮件查询用户信息
			Map<String, String> userMap = userService.queryPassword(email);
			if (userMap == null || userMap.isEmpty()) {
				// 按照用户名查找
				userMap = userService.queryIdByUser(email);
				if (userMap == null || userMap.isEmpty()) {
					JSONUtils.printStr("2");
					return INPUT;
				} else {
					id = userMap.get("id") == null ? -100 : Convert.strToLong(
							userMap.get("id"), -100);
				}
			} else {
				id = userMap.get("id") == null ? -100 : Convert.strToLong(
						userMap.get("id"), -100);
			}

			setR_userId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;
	}

	/**
	 * 登录BBS
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginBBS() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		String referer = this.request.getString("referer");
		if (referer == null) {
			referer = "";
		}
		if (referer.contains("tid")) {
			referer += "&highlight=";
		}
		if (user == null) {
			this.response().sendRedirect(IConstants.BBS_URL + referer);
			return null;
		}

		// 虚拟用户不能登录论坛
		if (user.getVirtual() == 1) {
			this.response().sendRedirect(IConstants.BBS_URL);
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();

		map.put("username", user.getUserName());
		map.put("password", user.getPassword());

		map.put("cookietime", "2592000");
		map.put("answer", "");
		map.put("formHash", "6a36c78f");
		map.put("loginfield", "username");
		map.put("loginmode", "");
		map.put("loginsubmit", "true");

		map.put("questionid", "0");

		map.put("referer", referer);
		map.put("styleid", "");

		map.put("k", Encrypt.encryptSES(IConstants.BBS_KEY,
				IConstants.BBS_SES_KEY));

		String strURL = IConstants.BBS_URL.endsWith("/") ? IConstants.BBS_URL
				+ "logging.jsp?action=login" : IConstants.BBS_URL
				+ "/logging.jsp?action=login";
		String html = buildForm(map, strURL, "post", "登录");
		this.response().setContentType("text/html");
		response().setCharacterEncoding("utf-8");
		PrintWriter out = response().getWriter();
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
		out.println(" <BODY>");
		out.print(html);
		out.println(" </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		return null;
	}

	private String buildForm(Map<String, String> sParaTemp, String gateway,
			String strMethod, String strButtonName) {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		log.info("BBS==gateway========>" + gateway);
		// 待请求参数数组
		List<String> keys = new ArrayList<String>(sParaTemp.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"loginForm\" name=\"loginForm\" action=\""
				+ gateway + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = keys.get(i);
			String value = sParaTemp.get(name);
			sbHtml.append("<input type=\"hidden\" name=\"" + name
					+ "\" value=\"" + value + "\"/>");
			log.info(name + "=============" + value);
		}

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName
				+ "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['loginForm'].submit();</script>");

		return sbHtml.toString();
	}

	/**
	 * 登录初始化
	 * 
	 * @return String
	 * @throws
	 */
	public String loginInit() {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		if (user != null) {
			return "zhanghao";
		} 
		String actionCallback = request().getHeader("referer");
		if (StringUtils.isNotBlank(actionCallback)) {
		    WebUtils.setSessionAttribute(request(), "afterLoginUrl", (actionCallback.contains("logout.mht")||actionCallback.contains("registerThirdInit.mht"))?"":actionCallback);
        }
		return SUCCESS;
	}
	
	/**
	 * 验证用户名、邮箱、企业全称 、手机号码的唯一性
	 * 2015-07-14 16:16:35
	 * hjh
	 * @return String
	 * @throws Exception
	 */
	public String ajaxNewCheckRegister() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			String email = paramMap.get("email"); // 电子邮箱
			String userName = paramMap.get("userName"); // 用户名
			String orgName = paramMap.get("orgName"); // 企业全称
			String phone=paramMap.get("cellphone");//手机号码
			String flag = paramMap.get("flag");
			String cellcode=paramMap.get("cellcode");//手机验证码
			
			Map<String, String> resultMap = new HashMap<String, String>();
			
			//验证码是否匹配判断
			if (StringUtils.isNotBlank(cellcode)) {
				//验证码是否过期 
				boolean timeFlag = userService.querySendTime(phone);
				if (!timeFlag){
					resultMap.put("error", "验证码已过期");
					JSONUtils.printObject(resultMap);
					return null;
				}
				
				Map<String, String> smsMap=(Map<String, String>)session().getAttribute("smsMap");
				if(smsMap!=null && StringUtils.isNotBlank(smsMap.get("randomCode"))  &&  cellcode.equals(smsMap.get("randomCode"))){
					resultMap.put("ok", "");//验证成功
				}else{
					resultMap.put("error", "验证码不正确");//验证失败
				}
				JSONUtils.printObject(resultMap);
				return null;
			}
			
			// 判断邮箱是否唯一
			Long result = -1L;
			if (StringUtils.isNotBlank(email) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(email, null,null,null);
				if (result > 0) {
					resultMap.put("error", "邮箱已被使用");
				} else {
					resultMap.put("ok", "");
				}
				JSONUtils.printObject(resultMap);
				return null;
			}

			// 判断用户名是否唯一
			if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, userName,null,null);
				result = result +userService.isExistEmailORUserName(null, null,null,userName);
				if (result > 0) {
					resultMap.put("error", "用户名已存在");
				} else {
					// ---add by houli 首先检查用户表中是否有重复的名字，如果没有则去t_admin表中
					Map<String, String> map = adminService
							.queryIdByUser(userName);
					if (map == null || map.size() <= 0) {
						resultMap.put("ok", "");
					} else {
						resultMap.put("error", "用户名已存在");
					}
				}
				JSONUtils.printObject(resultMap);
				return null;
			}
			// 判断企业全称是否唯一
			if (StringUtils.isNotBlank(orgName) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, null,orgName,null);
				if (result > 0) {
					resultMap.put("error", "");
				} else {
					resultMap.put("ok", "");
				}
				JSONUtils.printObject(resultMap);
				return null;
			}
			// 判断手机号码是否唯一
			if (StringUtils.isNotBlank(phone) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, null,null,phone);
				if (result > 0) {
					resultMap.put("error", "手机号码已被使用");
				} else {
					//判断person表中是否有该号码
					Long re = userService.isExistPersonByPhone(phone);
					if(re>0){
						resultMap.put("error", "手机号码已被使用");
					}else{
						resultMap.put("ok", "");
					}
				}
				JSONUtils.printObject(resultMap);
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
	 * 验证用户名、邮箱、企业全称 、手机号码的唯一性
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String ajaxCheckRegister() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			String email = paramMap.get("email"); // 电子邮箱
			String userName = paramMap.get("userName"); // 用户名
			String orgName = paramMap.get("orgName"); // 企业全称
			String phone=paramMap.get("cellphone");//手机号码
			String cellcode=paramMap.get("cellcode");//手机验证码
			String flag = paramMap.get("flag");
			
			
			
			//验证码是否匹配判断
			if (StringUtils.isNotBlank(cellcode)) {
				//验证码是否过期 
				boolean timeFlag = userService.querySendTime(phone);
				if (!timeFlag){
					log.info("过期了。");
					JSONUtils.printStr("35");//验证失败
					return null;
				}
				
				Map<String, String> smsMap=(Map<String, String>)session().getAttribute("smsMap");
				if(cellcode.equals(smsMap.get("randomCode"))){
					JSONUtils.printStr("33");//验证成功
				}else{
					JSONUtils.printStr("34");//验证失败
				}
				return null;
			}
			

			// 判断邮箱是否唯一
			Long result = -1L;
			if (StringUtils.isNotBlank(email) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(email, null,null,null);
				if (result > 0) {
					JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_EMAIL);
				} else {
					JSONUtils.printStr("0");
				}
				return null;
			}

			// 判断用户名是否唯一
			if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, userName,null,null);
				result = result +userService.isExistEmailORUserName(null, null,null,userName);
				if (result > 0) {
					JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_NAME);
				} else {
					// ---add by houli 首先检查用户表中是否有重复的名字，如果没有则去t_admin表中
					Map<String, String> map = adminService
							.queryIdByUser(userName);
					if (map == null || map.size() <= 0) {
						JSONUtils.printStr("0");
					} else {
						JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_NAME);
					}
				}
				return null;
			}
			// 判断企业全称是否唯一
			if (StringUtils.isNotBlank(orgName) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, null,orgName,null);
				if (result > 0) {
					JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_ORGNAME);
				} else {
					JSONUtils.printStr("0");
				}
				return null;
			}
			// 判断手机号码是否唯一
			if (StringUtils.isNotBlank(phone) && StringUtils.isBlank(flag)) {
				result = userService.isExistEmailORUserName(null, null,null,phone);
				if (result > 0) {
					JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_PHONE);
				} else {
					//判断person表中是否有该号码
					Long re = userService.isExistPersonByPhone(phone);
					if(re>0){
						JSONUtils.printStr(IConstants.USER_REGISTER_REPEAT_PHONE);
					}else{
						JSONUtils.printStr("0");
					}
				}
				return null;
			}

			// 判断邮箱与用户名是否对应存在
			if (StringUtils.isNotBlank(flag) && StringUtils.isNotBlank(email)
					&& StringUtils.isNotBlank(userName)) {
				result = userService.isExistEmailORUserName(email, userName,null,null);
				if (result > 0) {
					JSONUtils.printStr("1");

				} else {
					JSONUtils.printStr("0");
				}
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
	 * 用户登录时候的用户名和邮箱验证是否已将激活
	 * 
	 * @throws Exception
	 */
	public String ajaxChecklogin() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			String email = paramMap.get("email"); // 电子邮箱
			String userName = paramMap.get("userName"); // 用户名
			String flag = paramMap.get("flag");
			String cellphone = paramMap.get("cellphone"); // 用户名
			// 判断邮箱是否唯一
			Long result = -1L;
			Long vidResult = -1L;
			if (StringUtils.isNotBlank(email) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				result = userService.isUEjihuo(email, null);
				// 不检测enable 检测有没这个账号
				vidResult = userService.isUEjihuo_(email, null);
				if (vidResult < 0) {
					// 没有这个账号
					JSONUtils.printStr("0");
					return null;
					// 有邮箱 但是没有激活
				} else if (result > 0) {
					JSONUtils.printStr("1");
					return null;
				}
				JSONUtils.printStr("4");
				return null;
			}

			// 判断用户名是否唯一
			if (StringUtils.isNotBlank(userName) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				result = userService.isUEjihuo(null, userName);
				// 不检测enable 检测有没这个账号
				vidResult = userService.isUEjihuo_(null, userName);
				if (vidResult < 0) {
					// 没有这个账号
					JSONUtils.printStr("2");
					return null;
					// 有号 但是没有激活
				} else if (result > 0) {
					JSONUtils.printStr("3");
					return null;
				}
				JSONUtils.printStr("4");
				return null;
			}

			if (StringUtils.isNotBlank(cellphone) && StringUtils.isBlank(flag)) {
				// 检测enable 没有有账号激活的
				// 不检测enable 检测有没这个账号
				vidResult = userService.isPhoneExist(cellphone);
				long vidResult2 = userService.isUEjihuo_(null, cellphone);
				if (vidResult < 0&&vidResult2<0) {
					// 没有这个账号
					JSONUtils.printStr("5");
					return null;
				}
				JSONUtils.printStr("4");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return null;
	}
	
	public static int pv = 0;
    public static int uv = 0;
    public static int ipc = 0;
    public static Map<String,Integer> uMap = new HashMap<String,Integer>();
    public static Map<String,Integer> iMap = new HashMap<String,Integer>();
    private DateFormat dft = new SimpleDateFormat(UtilDate.dtShort);
    private String ptoday = dft.format(new Date());
    
    /**
     *功能：PV,UV统计
     * @return
     */
    public String pvuvStatistic(){
        DateFormat dft = new SimpleDateFormat(UtilDate.dtShort);
        String today = dft.format(new Date());
//      log.info("ptoday:" + ptoday + "----" + today);
        //  3->100 
        if (!ptoday.equals(today) || pv %50 == 0){
            ptoday = dft.format(new Date());
            try {
                //保存进数据库
                userService.addUvpLog( pv, uv, ipc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pv = 0;
            uv = 0;
            ipc = 0;
            if (!ptoday.equals(today)){
                uMap = new HashMap<String,Integer>();
                iMap = new HashMap<String,Integer>();
                ptoday = today;
            }
        }
        pv++;
        dft.format(new Date());
        String ip = request().getRemoteAddr();
        String mockClientIp = paramMap.get("mockClientIp");
//      log.info("----mockClientIp:" + mockClientIp);
//      log.info("----RemoteAddr:" + ip);
        if (null == mockClientIp){
            mockClientIp = ip;
            log.info("=====mockClientIp_null");
            if(null == mockClientIp){
                log.info("=====ip_null");
                return null;
            }
            
        }
        Integer counts = uMap.get(mockClientIp);
        if (null == counts){
            uMap.put(mockClientIp, 1);
            uv ++;
        } else {
            uMap.put(mockClientIp, counts + 1);
        }
        Integer icounts = iMap.get(ip);
        if (null == icounts){
            iMap.put(ip, 1);
            ipc ++;
        } else {
            iMap.put(ip, icounts + 1);
        }
//      if (pv % 3 == 0){
//          System.out.println("--uMap:" + uMap);
//          System.out.println("--iMap:" + iMap);
//      }
        try {
            JSONUtils.printStr("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	/**
	 * 用户登录
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String login() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String pageId = paramMap.get("pageId"); // 验证码
		String email = paramMap.get("email");
		String password = paramMap.get("password");
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		User user = (User) session().getAttribute("user");
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code");
		if (code == null || !_code.equals(code)) {
			log.info("notEqual_"+code +"--"+_code);
			JSONUtils.printStr("2");
			return null;
		}
		try {
			Thread.sleep(500);
			user = userService.userLogin1(email, password, lastIP, lastTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if (user == null) {
			JSONUtils.printStr("3");
			return null;
		}
		// 查找数据库对象中的enable属性
		if (user.getEnable() == 2) {
			JSONUtils.printStr("4");
			return null;
		}
		if (user.getIsLoginLimit() == 2) {
			JSONUtils.printStr("5");
			return null;
		}
		if (user.getLoginErrorCount() == 3) {
			JSONUtils.printStr("8");
			return null;
		}
		if (user.getLoginErrorCount() == 4) {
			JSONUtils.printStr("9");
			return null;
		}
		boolean re = userService.checkSign(user.getId());
		if(!re){
			JSONUtils.printStr("7");
			return null;
		}
		session().setAttribute("user", user);
		user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
		JSONUtils.printStr("1");
		
		//刷新token
		// 令牌
		String uuid = UUID.randomUUID().toString().replace("-", "");
		// 查询TOKEN表是否有数据
		Map<String, String> userTokenMap = userService
				.queryUserTokenById(user.getId());
		if (null == userTokenMap || userTokenMap.isEmpty()) {
			// 没有数据插入TOKEN数据
			userService.addToken(user.getId(), uuid, System.currentTimeMillis()+"");
		} else {
			// 有数据更新TOKEN
			userService.updateUserToken(user.getId(),uuid, System.currentTimeMillis()+"");
		}
		
		return null;
	}
	
	/**
	 * 首页登录
	 * <br/>警告。登录、注册、等通用入口只允许一个
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public String login1() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		DateFormat dateformat = new SimpleDateFormat(UtilDate.simple);
		String pageId = paramMap.get("pageId"); // 验证码
		String email = paramMap.get("email");
		String password = paramMap.get("password");
		String lastIP = ServletUtils.getRemortIp();
		String lastTime = dateformat.format(new Date());
		User user = (User) session().getAttribute("user");
		String code = (String) session().getAttribute(pageId + "_checkCode");
		String _code = paramMap.get("code");
		
		if (code == null || !_code.equals(code)) {
			JSONUtils.printStr("2");
			return null;
		}
		
		if (StringUtils.isBlank(password) || StringUtils.isBlank(email)) {
            JSONUtils.printStr("3");
            return null;
        }
		
		
		
		try {
			Thread.sleep(500);
			user = userService.userLogin1(email, password, lastIP, lastTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		if (user == null) {
			JSONUtils.printStr("3");
			return null;
		}
		// 查找数据库对象中的enable属性
		if (user.getEnable() == 2) {
			JSONUtils.printStr("4");
			return null;
		}
		if (user.getIsLoginLimit() == 2) {
			JSONUtils.printStr("5");
			return null;
		}
		
		if (user.getLoginErrorCount() == 3) {
			JSONUtils.printStr("8");
			return null;
		}
		if (user.getLoginErrorCount() == 4) {
			JSONUtils.printStr("9");
			return null;
		}
		
		boolean re = userService.checkSign(user.getId());
		if(!re){
			JSONUtils.printStr("7");
			return null;
		}
		session().setAttribute("user", user);
		user.setEncodeP(Encrypt.encryptSES(password, IConstants.PWD_SES_KEY));
		if (paramMap!=null && paramMap.get("wap")!=null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", "1");
            map.put("user_key", Encrypt.encryptSES(user.getId()+"", IConstants.PWD_SES_KEY));
            JSONUtils.printObject(map);
        }else {
            JSONUtils.printStr("1");
        }
		return null;
	}

	/**
	 * 验证邮箱
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public String verificationEmial() throws Exception {
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
			// 当用户点击注册时间小于10分钟
			if (curTime - Long.valueOf(dateTime) < 10 * 60 * 1000) {
				// 修改用户状态
//				Long result = userService.frontVerificationEmial(userId);
				long result = userService.updateEmalByid(userId, emial);
				if (result > 0) {
					msg = "恭喜您邮箱验证成功！请点击<a href='login.mht'>登录</a>";
					ServletActionContext.getRequest().setAttribute("msg", msg);
				} else {
					msg = "注册失败";
					// 这里还要写一个用户删除账号和密码
					ServletActionContext.getRequest().setAttribute("msg", msg);
				}
				
			} else {
				msg = "连接失效,<strong><a href='reSend.mht?id=" + userId
						+ "'>点击重新发送邮件</a></strong>";
				ServletActionContext.getRequest().setAttribute("msg", msg);
			}
		}
		return SUCCESS;

	}

	/**
	 * 重新发送邮件
	 * 
	 * @throws Exception
	 */
	public String reSendEmail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		DesSecurityUtil des = new DesSecurityUtil();
		String key1 = des.encrypt(ServletActionContext.getRequest().getParameter("id"));
	//	String key1=request.getString("id");		
		String key2 = des.encrypt(new Date().getTime() + "");
		String url = getPath(); // request().getRequestURI();
		String VerificationUrl = url + "verificationEmial.mht?key=" + key1 + "-"
				+ key2;
		//long userId = Convert.strToLong(ServletActionContext.getRequest().getParameter("id"), -1);
		long userId=request.getLong("id", -1);		
		// 获取用户email地址 和 userName
		Map<String, String> reMap = null;

		reMap = userService.queryUserById(userId);

		if (null != reMap && reMap.size() > 0) {
			String userName = reMap.get("username");
			String email = reMap.get("email");
			// 发送验证邮件
			sendMailService.sendRegisterVerificationEmail(VerificationUrl,
					userName, email);
			int dd = email.indexOf("@");
			String mailAddress = null;
			if (dd >= 0) {
				mailAddress = "mail." + email.substring(dd + 1);
			}
			session().setAttribute("mail", mailAddress);
		}
		return SUCCESS;
	}
	
	/*
	 * 检验是否登录
	 */
	public String hasLogin() throws IOException{
	    User user = (User) session().getAttribute(IConstants.SESSION_USER);
	    if (user!=null && user.getId()!=null) {
	        paramMap.put("error", "1");
            JSONUtils.printObject(paramMap);
        }else {
            paramMap.put("error", "0");
            JSONUtils.printObject(paramMap);
        }
	    return null;
	}
	
	/**
	 * 邮箱提示信息跳转
	 */
	public String tip() {
		return SUCCESS;
	}

	/**
	 * 用户登录后的页面
	 * 
	 * @return
	 */
	public String jumpUser() {
		return SUCCESS;
	}

	/**
	 * 虚拟用户登录时没有权限跳转页面
	 * 
	 * @return
	 */
	public String noPermission() {
		return SUCCESS;
	}

	/**
	 * @MethodName: logout
	 * @Param: FrontLongRegisterAction
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午11:04:19
	 * @Return:
	 * @Descb: 退出系统
	 * @Throws:
	 */
	public void logout() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		request().getSession().removeAttribute("user");
		request().getSession().invalidate();
		session().removeAttribute("bbs");
		bbsRegisterService.doExitByAsynchronousMode();
		getOut().print("<script>parent.location.href='login.mht';</script>");
	}
	
	public String getPhone() throws IOException{
	    Users user =  (Users) session().getAttribute("LinUserEntity");
        Map<String,String> resultMap=new HashMap<String, String>();
        if (user==null) {
            resultMap.put("msg", "参数信息就丢失，请重新开始注册");
        }else {
            resultMap.put("msg", user.getMobilePhone());
        }
        JSONUtils.printObject(resultMap);
        return null;
	}
	
	public void setSendMailService(SendMailService sendMailService) {
		this.sendMailService = sendMailService;
	}

	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(
			RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	public HomeInfoSettingService getHomeInfoSettingService() {
		return homeInfoSettingService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
		this.bbsRegisterService = bbsRegisterService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public void setCellPhoneService(CellPhoneService cellPhoneService) {
		this.cellPhoneService = cellPhoneService;
	}
}
