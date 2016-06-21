package com.sp2p.action.front;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.shove.Convert;
import com.shove.util.SMSUtil;
import com.shove.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.pay.llpay.client.security.Md5Algorithm;
import com.sp2p.pay.llpay.client.utils.LLPayUtil;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.UserAdminService;
import com.sp2p.util.DateUtil;

public class MingAction extends BaseFrontAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(MingAction.class);
	private static final String md5_key = "dmjt&huarong!@#$%";
	
	private UserService userService;
	private CellPhoneService cellPhoneService;
	private HomeInfoSettingService homeInfoSettingService;
	private UserAdminService userAdminService;
	
	
	public void setHomeInfoSettingService(HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}



	public void setUserAdminService(UserAdminService userAdminService) {
		this.userAdminService = userAdminService;
	}



	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
	public void setCellPhoneService(CellPhoneService cellPhoneService) {
		this.cellPhoneService = cellPhoneService;
	}



	/**
	 * 验证第一步
	 * @return
	 * @throws IOException 
	 * @throws Exception 
	 */
	public String validateMingOne() throws IOException{
		String reqStr = LLPayUtil.readReqStr(request());
		try {
			if (LLPayUtil.isnull(reqStr)) {
				paramMap.put("ret_code", "1003");
				paramMap.put("ret_msg", "参数丢失");
			}else {
				JSONObject reqObj = JSON.parseObject(reqStr);
				String mobile = reqObj.getString("mobile");
				String realName = reqObj.getString("realName");
				String IDNumber = reqObj.getString("IDNumber");
				String tokenid = reqObj.getString("tokenid");
				String verifyCode = reqObj.getString("verifyCode");
				String validateStr = mobile+md5_key;
				
				//再次调用大明的用户验证，确保用户是大明过来的 start
				  //尚未完成，后期需要大明提供接口
				//end
				
				if (StringUtils.isNotBlank(validateStr) && verifyCode.equals(Md5Algorithm.getInstance().md5Digest(
	                    validateStr.getBytes("utf-8")))) {
					Map<String, String> user =  userService.queryUserByName(mobile);
					if (user==null || user.size()==0) {
						//用户不存在需要注册
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("mobilePhone",mobile);
						m.put("userName", mobile);
						DecimalFormat format = new DecimalFormat("000000");
						
						m.put("typelen", 0);
						m.put("userType", 1);
						m.put("source", "DM");
						Long userId=cellPhoneService.usercellRegister2(null,m);//注册用户 和  初始化图片资料
						String  tradPassWord = format.format(RandomUtils.nextInt(999999));
						String password = format.format(RandomUtils.nextInt(999999));
						m.put("password", password);
						
						
						String content="尊敬的客户您好,您的默认登录密码为："+password+"，默认交易密码为："+tradPassWord+",为了您的资金安全请及时登陆我们的三好资本平台修改您的密码信息。";
						
						if ("1".equals(IConstants.ENABLED_PASS)) {
							password = com.shove.security.Encrypt
									.MD5(password.trim());
						} else {
							password = com.shove.security.Encrypt.MD5(password.trim()
									+ IConstants.PASS_KEY);
						}
						
						
						
						if ("1".equals(IConstants.ENABLED_PASS)) {
							tradPassWord = com.shove.security.Encrypt
									.MD5(password.trim());
						} else {
							tradPassWord = com.shove.security.Encrypt.MD5(password.trim()
									+ IConstants.PASS_KEY);
						}
						//修改交易密码
						homeInfoSettingService.updateUserPassword(userId, tradPassWord, "tradpwd");
						//手机注册
						userService.updateSign(userId);//修改校验码
						//添加通知默认方法
						homeInfoSettingService.addNotes(userId, true, false, false);
						homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
						if (StringUtils.isNotBlank(realName) && StringUtils.isNotBlank(IDNumber)) {
							//实名
							userAdminService.addPerson(realName, userId, IDNumber);
						}
						
                        SMSUtil.sendSMS("", "", content, mobile, null);
                        tokenid = creatToken(String.valueOf(userId));
					}else {
						Map<String, String> map = userService.queryUserByPhone(mobile);
						String userId = map.get("id");
						tokenid = creatToken(String.valueOf(userId));
					}
					paramMap.put("ret_code", "1005");
					paramMap.put("ret_msg", "登录成功");
					paramMap.put("tokenid", tokenid);
				}else {
					paramMap.put("ret_code", "1002");
					paramMap.put("ret_msg", "验签失败");
				}
			}
		} catch (Exception e) {
			paramMap.put("ret_code", "1004");
			paramMap.put("ret_msg", "登录失败");
		}
		response().setCharacterEncoding("UTF-8");
		response().getWriter().print(JSON.toJSONString(paramMap));
		response().getWriter().flush();
		return null;
	}
	
	/**
	 * 验证第二步登录
	 * @return
	 * @throws Exception
	 */
	public String mingLogin() throws Exception{
		String reqStr = request("params");
		JSONObject jsonObject = JSON.parseObject(reqStr);
		String mobile = jsonObject.getString("mobile");
		String tokenid = jsonObject.getString("tokenid");
		if (StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(tokenid)) {
			Map<String, String> map = userService.queryUserByPhone(mobile);
			String userId = map.get("id");
			if (verifyToken(tokenid, userId)) {
				User user = userService.userLogin1(mobile, map.get("password"), ServletUtils.getRemortIp(), DateUtil.dateToString(new Date()),"dm");
				session().setAttribute(IConstants.SESSION_USER, user);
				return SUCCESS;
			}else {
				return ERROR;
			}
		}else {
			return ERROR;
		}
	}
}
