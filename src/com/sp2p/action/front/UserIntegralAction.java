package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.GuaranteeService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;

public class UserIntegralAction extends BaseFrontAction {
	
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(UserIntegralAction.class);
	private UserIntegralService userIntegralService;
	private GuaranteeService  guaranteeService;
	private MyHomeService myHomeService;
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}
	public void setGuaranteeService(GuaranteeService guaranteeService) {
		this.guaranteeService = guaranteeService;
	}
	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	} 
	/**
	 * 前台用户查询用户的vip积分记录
	 * @return
	 * @throws Exception 
	 */
	public String queryUserIntegral() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> userMsg = null;
		User user = (User) session().getAttribute("user");
		userMsg = guaranteeService.queryCreditStatic(user.getId());
		request().setAttribute("userMsg", userMsg);
		return SUCCESS;
	}
	
	/**
	 * 前台用户查询用户的企业认证
	 * @return
	 * @throws Exception 
	 */
	public String queryUserIntegralBycompany() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> userMsg = null;
		User user = (User) session().getAttribute("user");
		userMsg = guaranteeService.queryCreditStaticByCompany(user.getId());
		request().setAttribute("userMsg", userMsg);
		return SUCCESS;
	}
	public String queryUservip() throws SQLException, Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String>  userMap = null;
	    User user = (User)session().getAttribute(IConstants.SESSION_USER);
	    //分页
	    userIntegralService.queryUserIntegral(pageBean,user.getId(), IConstants.USER_INTERGRALTYPEVIP);
		Map<String, String> homeMap = myHomeService.queryHomeInfo(user.getId());
		request().setAttribute("homeMap", homeMap);
		userMap = userService.queryUserById(user.getId());
		request().setAttribute("userMap", userMap);
		return SUCCESS;
	}
	

}
