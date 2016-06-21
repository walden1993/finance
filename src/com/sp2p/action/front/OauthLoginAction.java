package com.sp2p.action.front;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.shove.Convert;
import com.shove.util.JSONUtils;
import com.shove.util.SMSUtil;
import com.shove.web.Utility;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.entity.Users;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;

/**
 * 第三方登录
 * @author hejiahua(940417907@qq.com)
 */
public class OauthLoginAction extends BaseFrontAction{

	private static final long serialVersionUID = 4903050675682236407L;
	
	static Log log = LogFactory.getLog(OauthLoginAction.class);
	
	private UserService userService;
	
	private CellPhoneService cellPhoneService;
	
	private HomeInfoSettingService homeInfoSettingService;
	
	private RecommendUserService recommendUserService;
	
	private BBSRegisterService bbsRegisterService;
	
	private SysparService sysparService;
	
	
	public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
		this.bbsRegisterService = bbsRegisterService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public void setCellPhoneService(CellPhoneService cellPhoneService) {
		this.cellPhoneService = cellPhoneService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	public void setSysparService(SysparService sysparService) {
		this.sysparService = sysparService;
	}


	private String openId;
	
	
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	/*
	 * QQ登录第一步
	 */
	public void qqlogin() throws IOException{
		try {
			response().sendRedirect(new Oauth().getAuthorizeURL(request()));
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
	}
	
	public void sinalogin(){
		
	}
	
	public String autoRegister(){
		if (session("openID")==null) {
			addActionError("授权已过期，请重新授权");
            return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * @throws Exception 
	 * 注册
	 * @Title: reg 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param mobile 手机
	 * @param @param password 密码
	 * @param @param source  来源QQ OR WEIBO
	 * @param @param ref 推荐人
	 * @return void 返回类型 
	 * @author hjh
	 * @throws
	 */
	public String reg() throws Exception{
		
		String mobile = paramMap.get("username");//用户名/手机
		String password = paramMap.get("password"); //密码
		String confirmPassword = paramMap.get("confirmPassword"); //密码
		String source = (String) session("type");//QQ or weibo
		String ref = paramMap.get("ref");//推荐人
		String code = paramMap.get("code");//验证码
		String _checkCode = (String) session().getAttribute("otherreg_checkCode");
		
		int error = 10 ;
		if (!com.shove.util.StringUtils.matchPhone(mobile)) {
			error=11;
		}else {
			Long result = userService.isExistEmailORUserName(null, mobile,null,null);
			if (result > 0) { // 用户名重复
				error=19;
			}else {
				result = userService.isExistEmailORUserName(null, null,null,mobile);
				if (result > 0) { // 手机重复
					error=19;
				}
			}
		}
		
		
		if (StringUtils.isBlank(password) || password.length()>26) {
			error=12;
		}else {
			if (StringUtils.isBlank(confirmPassword) || confirmPassword.length()>26) {
				error=13;
			}else {
				if (!confirmPassword.equals(password)) {
					error=14;
				}
			}
		}
		
		if (StringUtils.isBlank(source)) {
			error=15;
		}
		if (StringUtils.isBlank(code) || StringUtils.isBlank(_checkCode) || !code.equals(_checkCode)) {
			error=16;
		}
		
		//推荐人
		if (StringUtils.isNotBlank(ref)) {//判断推荐用户在不在
			//根据用户名查询推荐人用户明细
			Map<String, String> userIdMap = userService.queryIdByUser(ref);
			if (userIdMap == null || userIdMap.size()<=0) {
				error=18;
			}
	
		}
		
		if (error>10) {
			JSONUtils.printObject(""+error);
			return null;
		}
		
		Users user = new Users();//论坛
		user.setUserName(mobile);
		user.setPassword(password);
		user.setEmail("default@163.com");
		
		//用户不存在需要注册
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobilePhone",mobile);
		m.put("userName", mobile);
		m.put("typelen", 0);
		m.put("userType", 1);
		m.put("source", source);
		if ("1".equals(IConstants.ENABLED_PASS)) {
			password = com.shove.security.Encrypt
					.MD5(password.trim());
		} else {
			password = com.shove.security.Encrypt.MD5(password.trim()
					+ IConstants.PASS_KEY);
		}
		m.put("password", password);
		Long userId=cellPhoneService.usercellRegister2(null,m);//注册用户 和  初始化图片资料
		
		if (userId<=0) {
			JSONUtils.printObject("17");
			return null;
		}else {
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
		}
		
		
		//修改交易密码/与登录密码首次相同
		homeInfoSettingService.updateUserPassword(userId, password, "tradpwd");
		//手机注册
		userService.updateSign(userId);//修改校验码
		//添加通知默认方法
		homeInfoSettingService.addNotes(userId, true, false, false);
		homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
		
		if (StringUtils.isNotBlank(ref)) {
			//根据用户名查询推荐人用户明细
			Map<String, String> userIdMap = userService.queryIdByUser(ref);
			if (userIdMap != null) {
				long recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
				//修改之前的推荐
		        if(recommendUserId>0){//判断是否为空
		            List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
		            if(list==null || list.size()==0){//判断之前是否已经有关系了。
		            	recommendUserService.addRecommendUser(userId, recommendUserId);
		            }
		        }
			}
		}
		
		
		bbsRegisterService.doRegisterByAsynchronousMode(user);
		
		try {
			//实现注册成功登录
			String lastIP = ServletUtils.getRemortIp();
			String lastTime = DateUtil.dateToString(new Date());
			User u = userService.userLogin1(mobile, password, lastIP, lastTime,"dm");
			session().setAttribute(IConstants.SESSION_USER, u);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return relationAccount();
	}
	
	public String weibo(String code) throws Exception{
		weibo4j.Oauth oauth = new weibo4j.Oauth();
		try {
			//AccessToken [accessToken=2.00xVs1GE6yc1lDa436788c96Sel1_D, expireIn=157679999, refreshToken=,uid=3758165461]
			weibo4j.http.AccessToken accessToken = oauth.getAccessTokenByCode(code);
			String openID = accessToken.getUid();
			//通过 openid查询用户是否已经存在
            Map<String, String> usermap = userService.queryOauth(openID);
            weibo4j.Users um = new weibo4j.Users(accessToken.getAccessToken());
            weibo4j.model.User user = um.showUserById(accessToken.getUid());
			return oauthNext(accessToken.getAccessToken(), openID, Convert.strToLong(accessToken.getExpireIn(), 0l), usermap,"weibo",user.getScreenName());
		} catch (WeiboException e) {
			e.printStackTrace();
			addActionError("登录异常");
			return ERROR;
		}
	} 
	
	/*
	 * 登录回调
	 */
	public String afterLoginRedirect() throws Exception{
		//如果是QQ登录
    	String forcelogin =request("state");
    	if (StringUtils.isBlank(forcelogin)) {
			return weibo(request("code"));
		}else {
			return qq();
		}
        
    }
	
	private String qq() throws Exception{
		try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request());

            String accessToken   = null,
                   openID        = null;
            long tokenExpireIn = 0L;
            if (accessTokenObj.getAccessToken().equals("")) {
//                我们的网站被CSRF攻击了或者用户取消了授权
//                做一些数据统计工作
            	addActionError("登录失败！请重新登录");
                return ERROR;
            } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                //通过 openid查询用户是否已经存在
                Map<String, String> user = userService.queryOauth(openID);
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
    		    UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                //存在
                return oauthNext(accessToken, openID, tokenExpireIn, user,"QQ",userInfoBean.getNickname());
                // 利用获取到的accessToken 去获取当前用户的openid --------- end

               /* out.println("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- start </p>");
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                out.println("<br/>");
                if (userInfoBean.getRet() == 0) {
                    out.println(userInfoBean.getNickname() + "<br/>");
                    out.println(userInfoBean.getGender() + "<br/>");
                    out.println("黄钻等级： " + userInfoBean.getLevel() + "<br/>");
                    out.println("会员 : " + userInfoBean.isVip() + "<br/>");
                    out.println("黄钻会员： " + userInfoBean.isYellowYearVip() + "<br/>");
                    out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL30() + "/><br/>");
                    out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL50() + "/><br/>");
                    out.println("<image src=" + userInfoBean.getAvatar().getAvatarURL100() + "/><br/>");
                } else {
                    out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                }
                out.println("<p> end -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- end </p>");
                out.println("<p> start ----------------------------------- 验证当前用户是否为认证空间的粉丝------------------------------------------------ start <p>");
                PageFans pageFansObj = new PageFans(accessToken, openID);
                PageFansBean pageFansBean = pageFansObj.checkPageFans("97700000");
                if (pageFansBean.getRet() == 0) {
                    out.println("<p>验证您" + (pageFansBean.isFans() ? "是" : "不是")  + "QQ空间97700000官方认证空间的粉丝</p>");
                } else {
                    out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + pageFansBean.getMsg());
                }
                out.println("<p> end ----------------------------------- 验证当前用户是否为认证空间的粉丝------------------------------------------------ end <p>");
                out.println("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在微博的昵称等信息 ---------------------------- start </p>");
                com.qq.connect.api.weibo.UserInfo weiboUserInfo = new com.qq.connect.api.weibo.UserInfo(accessToken, openID);
                com.qq.connect.javabeans.weibo.UserInfoBean weiboUserInfoBean = weiboUserInfo.getUserInfo();
                if (weiboUserInfoBean.getRet() == 0) {
                    //获取用户的微博头像----------------------start
                    out.println("<image src=" + weiboUserInfoBean.getAvatar().getAvatarURL30() + "/><br/>");
                    out.println("<image src=" + weiboUserInfoBean.getAvatar().getAvatarURL50() + "/><br/>");
                    out.println("<image src=" + weiboUserInfoBean.getAvatar().getAvatarURL100() + "/><br/>");
                    //获取用户的微博头像 ---------------------end

                    //获取用户的生日信息 --------------------start
                    out.println("<p>尊敬的用户，你的生日是： " + weiboUserInfoBean.getBirthday().getYear()
                                +  "年" + weiboUserInfoBean.getBirthday().getMonth() + "月" +
                                weiboUserInfoBean.getBirthday().getDay() + "日");
                    //获取用户的生日信息 --------------------end

                    StringBuffer sb = new StringBuffer();
                    sb.append("<p>所在地:" + weiboUserInfoBean.getCountryCode() + "-" + weiboUserInfoBean.getProvinceCode() + "-" + weiboUserInfoBean.getCityCode()
                             + weiboUserInfoBean.getLocation());

                    //获取用户的公司信息---------------------------start
                    ArrayList<Company> companies = weiboUserInfoBean.getCompanies();
                    if (companies.size() > 0) {
                        //有公司信息
                        for (int i=0, j=companies.size(); i<j; i++) {
                            sb.append("<p>曾服役过的公司：公司ID-" + companies.get(i).getID() + " 名称-" +
                            companies.get(i).getCompanyName() + " 部门名称-" + companies.get(i).getDepartmentName() + " 开始工作年-" +
                            companies.get(i).getBeginYear() + " 结束工作年-" + companies.get(i).getEndYear());
                        }
                    } else {
                        //没有公司信息
                    }
                    //获取用户的公司信息---------------------------end
                    out.println(sb.toString());
                } else {
                    out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + weiboUserInfoBean.getMsg());
                }
                out.println("<p> end -----------------------------------利用获取到的accessToken,openid 去获取用户在微博的昵称等信息 ---------------------------- end </p>");
            */}
        } catch (QQConnectException e) {
        	addActionError("登录异常");
        	return ERROR;
        }
	}
	
	private String oauthNext(String accessToken, String openID,
			long tokenExpireIn, Map<String, String> user,String type,String nickname) throws Exception,
			QQConnectException {
		if (user!=null && StringUtils.isNotBlank(user.get("userId"))) {
			user = userService.queryUserById(Convert.strToLong(user.get("userId"), -1));
			if (user == null || StringUtils.isBlank(user.get("id"))) {
				//登录失败
				addActionError("登录失败");
				return ERROR;
			}else {
				User sessionUser = userService.userLogin1(user.get("username"), user.get("password"), ServletUtils.getRemortIp(),DateUtil.dateToString(new Date()),"dm");						
				if (sessionUser == null) {
					addActionError("用户不存在，请重新登录。");
					return ERROR;
				}
				// 查找数据库对象中的enable属性
				if (sessionUser.getEnable() == 2) {//被禁用
					addActionError("该用户已被禁用！");
				}
				if (sessionUser.getIsLoginLimit() == 2) {//错误次数上线
					addActionError("该用户已被限制登录，请于三小时以后登录");
				}
				boolean re = userService.checkSign(sessionUser.getId());
				if(!re){//用户被锁定
					addActionError("该用户账号出现异常，请速与客服人员联系！");
				}
				if (hasActionErrors()) {
					return ERROR;
				}else {
					user.clear();
					user.put("token_expirein", tokenExpireIn+"");
					user.put("openid", openID);
					user.put("access_token", accessToken);
					//更新token信息，为了后期能够调用用户的第三方接口的使用权限
					userService.updateOauth(user);
					session().setAttribute(IConstants.SESSION_USER, sessionUser);
					return "home";
				}
			}
		}else {//不存在,去注册页面
			if (null == user) {//第一次进来
				user = new HashMap<String, String>();
				user.put("token_expirein", tokenExpireIn+"");
				user.put("openid", openID);
				user.put("access_token", accessToken);
				user.put("type_code", type);
				userService.insertOauth(user);
			}
			setOpenId(openID);
			
		    session().setAttribute("type", type);
		    session().setAttribute("nickname", nickname);
		    session().setAttribute("openID", openID);
			return "reg";
		}
	}
	
	
	/**
	 * relationAccount:关联帐号. <br/>
	 *
	 * @author hjh
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public String relationAccount() throws Exception{
		
		String username = paramMap.get("username");
		String password = paramMap.get("password");
		String openId = (String) session("openID");
		if (StringUtils.isBlank(openId) || StringUtils.isBlank(username) || StringUtils.isBlank(password) ) {
			JSONUtils.printStr("3");
			return null;
		}
		User sessionUser = userService.userLogin1(Utility.filteSqlInfusion(username), Utility.filteSqlInfusion(password), ServletUtils.getRemortIp(),DateUtil.dateToString(new Date()));
		if (null!=sessionUser) {
			Map<String, String> oauth = new HashMap<String, String>();
			oauth.put("openid", openId);
			oauth.put("userId", sessionUser.getId()+"");
			userService.updateOauth(oauth);
			session().setAttribute(IConstants.SESSION_USER, sessionUser);
			JSONUtils.printStr("1");
		}else {
			JSONUtils.printStr("2");
		}
		return null;
	}
	
	
	
}
