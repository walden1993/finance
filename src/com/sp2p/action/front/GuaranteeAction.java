package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.sp2p.entity.User;
import com.sp2p.service.GuaranteeService;
import com.sp2p.service.UserService;

public class GuaranteeAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(GuaranteeAction.class);
	private GuaranteeService guaranteeService;
	protected UserService userService;

	public void setGuaranteeService(GuaranteeService guaranteeService) {
		this.guaranteeService = guaranteeService;
	}

	public String guaranteeaindexMethod() {
		return SUCCESS;
	}

	/**
	 * 点击投资人显示投资人的详细信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String userMegMethod() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> userMsg = null;
		Map<String,String> userVipPicture = null;
		Map<String,String> BorrowRecode = null;
		Map<String,String> inverseRecorde = null;
		Long userId = request.getLong("id", -1);
		userVipPicture = guaranteeService.queryUserVipPicture(userId);
		userMsg = guaranteeService.queryUserInformation(userId);
		BorrowRecode = guaranteeService.queryUserBorrowRecode(userId);
		inverseRecorde = guaranteeService.queryUserInerseRecode(userId);
		request().setAttribute("userMsg", userMsg);
		request().setAttribute("userVipPicture", userVipPicture);
		request().setAttribute("BorrowRecode", BorrowRecode);
		request().setAttribute("inverseRecorde", inverseRecorde);
		return SUCCESS;
	}

	/**
	 * 查看用户的信用详情
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String userCriditMethod() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> userMsg = null;
		/*Map<String, String> map = null;
		Map<String,String> criditMap = null;
		Map<String,String> userVipPicture = null;
		Map<String,String> BorrowRecode = null;
		Map<String,String> inverseRecorde = null;
		Map<String,String> UserBorrowmap1 = null;
		Map<String,String> UserBorrowmap2 = null;
		Map<String,String> UserBorrowmap3 = null;
		Map<String,String> UserBorrowmap4 = null;
		Map<String,String> UserBorrowmap5 = null;
		Map<String,String> UserBorrowmap6 = null;*/
		Long userId = request.getLong("id", -1);
		if (userId == null || userId == -1) {
			return LOGIN;
		}
		userMsg = guaranteeService.queryCreditStatic(userId);
//		criditMap = guaranteeService.queryUserCriditPicture(userId);
//		userMsg = guaranteeService.queryUserInformation(userId);
//		userVipPicture = guaranteeService.queryUserVipPicture(userId);
//		map = guaranteeService.queryPerUserCreditfornt(userId);
//		BorrowRecode = guaranteeService.queryUserBorrowRecode(userId);
//		inverseRecorde = guaranteeService.queryUserInerseRecode(userId);
//		//=====统计还款分数
//		UserBorrowmap1 = guaranteeService.queryUserBorrowAndInver15(userId);
//		UserBorrowmap2 = guaranteeService.queryUserBorrowAndInver16(userId);
//		UserBorrowmap3 = guaranteeService.queryUserBorrowAndInver10(userId);
//		UserBorrowmap4 = guaranteeService.queryUserBorrowAndInver30(userId);
//		UserBorrowmap5 = guaranteeService.queryUserBorrowAndInver90(userId);
//		UserBorrowmap6 = guaranteeService.queryUserBorrowAndInver90up(userId);
//		request().setAttribute("UserBorrowmap1", UserBorrowmap1);
//		request().setAttribute("UserBorrowmap2", UserBorrowmap2);
//		request().setAttribute("UserBorrowmap3", UserBorrowmap3);
//		request().setAttribute("UserBorrowmap4", UserBorrowmap4);
//		request().setAttribute("UserBorrowmap5", UserBorrowmap5);
//		request().setAttribute("UserBorrowmap6", UserBorrowmap6);
//		//=========
		request().setAttribute("userMsg", userMsg);
		request().setAttribute("userId", userId);
//		request().setAttribute("map", map);
//		request().setAttribute("criditMap", criditMap);
//		request().setAttribute("userVipPicture", userVipPicture);
//		request().setAttribute("BorrowRecode", BorrowRecode);
//		request().setAttribute("inverseRecorde", inverseRecorde);
		return SUCCESS;
	}

	/**
	 * 查看用户的认证
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String userRenRenMethod() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String flag = request.getString("flag");
		request().setAttribute("flag", flag);//区别从什么地方访问的
		
		
		Long userId=((User)session().getAttribute("user")).getId();
		Map<String,String> map=userService.queryUserById(userId);
		Integer userType=Convert.strToInt(map.get("userType"), 0);
		request().setAttribute("userType", userType);
		
		return SUCCESS;
	}

	/**
	 * 查询用户的好友列表
	 * 
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String queryUserFriendList() throws SQLException, Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = paramMap.get("id");
		request().setAttribute("id", id);
		String attention = paramMap.get("attention");
		request().setAttribute("attention", attention);
		if (StringUtils.isNotBlank(id)) {
			Long userId = Convert.strToLong(id, -1L);
			guaranteeService.queryUserFriends(pageBean, userId);
		}
		if(StringUtils.isNotBlank(attention)){
			return "attention";
		}
		return SUCCESS;
	}
	
	/**
	 * 删除关注好友
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws Exception
	 */
	public String deleteUserFriend()throws SQLException,DataException,Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long userId=Convert.strToLong(paramMap.get("userId"), -1L);
		Long attentionUserId=Convert.strToLong(paramMap.get("attentionId"),-1L);
		guaranteeService.DeteleUserFriends(attentionUserId, userId);
		paramMap.put("attention", "attention");
		paramMap.put("id", userId.toString());
		return queryUserFriendList();
		
	}
	/**
	 * 查询用户的借款列表
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryUserBorrowLists() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String userIdStr = paramMap.get("id");
		if(StringUtils.isNotBlank(userIdStr)){
			request().setAttribute("id", userIdStr);
			Long userId = Convert.strToLong(userIdStr,-1L);
		//myborrowlist = guaranteeService.queryMyBorrowList(userId);
			guaranteeService.queryMyBorrowList(pageBean, userId);
		}
		return SUCCESS;
	}
	/**
	 * 查询用户的投资记录
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryMyborrowRecorde() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String userIdStr = paramMap.get("id");
		if(StringUtils.isNotBlank(userIdStr)){
			request().setAttribute("id", userIdStr);
			Long userId = Convert.strToLong(userIdStr,-1L);
		//myborrowlist = guaranteeService.queryMyBorrowList(userId);
			guaranteeService.queryMyborrowRecorde(pageBean, userId);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询用户动态
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryUserRecore() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
			Long userId = Convert.strToLong(paramMap.get("id"),-1L);
			request().setAttribute("id", userId);
			guaranteeService.queryUserRecore(pageBean, userId);
		return SUCCESS;
	}
	/**
	 * 查询用户好友的动态
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryfrendsRecore() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long userId = Convert.strToLong(paramMap.get("id"),-1L);
		if(userId!=null&&userId!=-1){
			request().setAttribute("id",userId);
		guaranteeService.queryfrendsRecore(pageBean, userId);
		}
		return SUCCESS;
	}
	
	/**
	 * 返回安全中心
	 * @return
	 * @throws IOException 
	 */
	public String securityCenter() throws IOException{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Long userId=((User)session().getAttribute("user")).getId();
		
		if (StringUtils.isNotBlank(request("wap"))) {
		    try {
	            Map <String, String> map = guaranteeService.queryUserStatus(userId);
                String mobilePhone = map.get("mobilePhone");
                if (!StringUtil.isEmpty(mobilePhone)){
                    mobilePhone = com.shove.util.StringUtils.formatStr(mobilePhone, 3, 8);
                }
                String email = map.get("email");
                if (!StringUtil.isEmpty(email)){
                    email = com.shove.util.StringUtils.formatStr(email, 4, 8);
                }
                String realName = map.get("realName");
                if (StringUtils.isNotBlank(realName)) {
                    realName = com.shove.util.StringUtils.formatStr(realName, 1, 6);
                }
                String idNo = map.get("idNo");
                if (StringUtils.isNotBlank(idNo)) {
                    idNo = com.shove.util.StringUtils.formatStr(idNo, 3);
                }
                JSONObject object = new JSONObject();
                object.put("mobilePhone", mobilePhone);
                object.put("email", email);
                object.put("authCardName", map.get("authCardName"));
                object.put("realName", realName);
                object.put("idNo", idNo);
                object.put("dealpwd", map.get("dealpwd")!=null);
	            JSONUtils.printObject(object);
	            return null;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            log.error("SQLException:", e);
	            JSONUtils.printObject(-1);
	            return null;
	        }
        }
		
		
		try {
			Map <String, String> map = guaranteeService.queryUserStatus(userId);
			if(map == null){
				map = guaranteeService.queryUserNoPersonInfo(userId);
				String mobilePhone = map.get("mobilePhone");
				if (!StringUtil.isEmpty(mobilePhone)){
					mobilePhone = mobilePhone.substring(0, 3) + "****" + mobilePhone.substring(7, 11);
				}
				request().setAttribute("mobilePhone", mobilePhone);
				String email = map.get("email");
				if (!StringUtil.isEmpty(email)){
					email = email.substring(0,4) + "****" + email.substring(email.lastIndexOf("."));
				}
				request().setAttribute("email", email);
				request().setAttribute("dealpwd", map.get("dealpwd"));
				request().setAttribute("password", map.get("password"));
				request().setAttribute("authCardName", 1);
				request().setAttribute("realName", null);
				request().setAttribute("idNo", null);
			}else{
				String mobilePhone = map.get("mobilePhone");
				if (!StringUtil.isEmpty(mobilePhone)){
					mobilePhone = mobilePhone.substring(0, 3) + "****" + mobilePhone.substring(7, 11);
				}
				request().setAttribute("mobilePhone", mobilePhone);
				String email = map.get("email");
				if (!StringUtil.isEmpty(email)){
					email = email.substring(0,4) + "****" + email.substring(email.lastIndexOf("."));
				}
				request().setAttribute("mobilePhone", mobilePhone);
				request().setAttribute("email", email);
				request().setAttribute("dealpwd", map.get("dealpwd"));
				request().setAttribute("password", map.get("password"));
				request().setAttribute("authCardName", map.get("authCardName"));
				request().setAttribute("realName", map.get("realName"));
				request().setAttribute("idNo", map.get("idNo"));
			}
			System.out.println("goto_securityCenter:" + map);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("SQLException:", e);
		}
		if (checkMobile()) {
			return "successmobile";
		}
		return SUCCESS;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
