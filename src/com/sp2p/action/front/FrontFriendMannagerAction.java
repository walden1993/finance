package com.sp2p.action.front;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.RecommendUserService;

/**
 * 好友管理
 * @author Administrator
 *
 */
public class FrontFriendMannagerAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontFriendMannagerAction.class);
	private static final long serialVersionUID = 1L;
	
	private RecommendUserService recommendUserService;
	
	//邀请好友列表
	private List<Map<String, Object>> recommendUserList;
	
	
	/**
	 * 加载邀请的好友列表
	 * @return
	 */
	public String FriendManagerInit(){
		//获取用户的信息
		try {
			User user = (User) session().getAttribute(IConstants.SESSION_USER);
			Long userId=user.getId();
			pageBean.setPageNum(request.getString("curPage"));
			recommendUserService.queryfindRecommendUserPage(pageBean, userId);
			List<Map<String,String>>  list=pageBean.getPage();
			DesSecurityUtil des = new DesSecurityUtil();
			String userI = des.encrypt(userId.toString());
		    String uri=getPath();
		    
		    //查询邀请好友奖励的金额
		    Map<String,String> friendMap = recommendUserService.queryFriendCost();
		    if(friendMap != null){
		    	request().setAttribute("friendCost", friendMap.get("costFee"));
		    }
		    
		    //查询是否有推荐人
		    Map m = recommendUserService.queryRecommendUserInfo(userId);
		    
		    //查询我发展的下线投资总额
		    Map sm = recommendUserService.sumInvestAmt(userId);
		    
			request().setAttribute("url", uri);
			request().setAttribute("userId", userI);
			if (null != m){
				request().setAttribute("recommendUserId", m.get("username"));
			}
			if (null != sm){
				request().setAttribute("reInvestAmount", "".equals(sm.get("investAmount")) ? 0: sm.get("investAmount"));
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (checkMobile()) {
			return "successmobile";
		}
		return SUCCESS;
	}
	/**
	 * 加载邀请的好友列表
	 * @return
	 */
	public String FriendManagerList(){
		//获取用户的信息
		try {
			User user = (User) session().getAttribute(IConstants.SESSION_USER);
			Long userId=user.getId();
			recommendUserService.queryfindRecommendUserPage(pageBean, userId);
			List<Map<String,String>>  list=pageBean.getPage();
			DesSecurityUtil des = new DesSecurityUtil();
			String userI = des.encrypt(userId.toString());
		    String uri=getPath();
		    
			request().setAttribute("url", uri);
			request().setAttribute("userId", userI);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 *功能：推荐人补录功能
	 * @return
	 */
	public String fillRecomment(){
		log.info("===========================fillRecomment....");
		String recommentName = paramMap.get("recommentName");
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId=user.getId();
		try {
			Map map = recommendUserService.fillRecomment(userId,recommentName);
			JSONUtils.printStr("" + map.get("ret"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *功能：校验推荐人
	 * @return
	 */
	public String checkRecomment(){
		log.info("==========check_Recomment_User");
		String retUserName = paramMap.get("retUserName");
		String userIdstr = paramMap.get("userId");
		
		log.info("==========retUserName:" + retUserName + userIdstr);
		Long userId = Long.parseLong(userIdstr);
		try {
			int ret = recommendUserService.checkRecomment(userId, retUserName);
			JSONUtils.printStr("" + ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *功能：理财师计划
	 * @return
	 */
	public String financialManager(){
		
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		Long userId=user.getId();
		Map map = new java.util.HashMap();
		map.put("userId", userId);
		int retStatus = 0;
		Map fimap = (Map) recommendUserService.queryOne("advert.queryFinancialRecord", map);
		if(null == fimap){
			retStatus = 0;
			log.info("======null");
			request().setAttribute("notFound", "1");
			fimap = new java.util.HashMap();
			fimap.put("myLevel", 0);
			request().setAttribute("fimap", fimap);
		} else {
			int appLevel = (Integer) fimap.get("appLevel");
			long leader = -1;
			if (null != fimap.get("leader")){
				leader = (Long) fimap.get("leader");
			}
			int myLevel = -1;
			if (null != fimap.get("myLevel")){
				myLevel = (Integer) fimap.get("myLevel");
			}
			
			//没有上线，正在申请中
			if (leader < 0L && 1 == appLevel){
				retStatus = 1;
			}
			//没有上线，可以申请
			else if (leader < 0L && 0 == appLevel){
				retStatus = 2;
			}
			//有上线，可以申请
			else if (leader > 0L && 0 == appLevel){
				retStatus = 3;
				if (myLevel == 3){
					retStatus = 5;
				}
			}
			//有上线，正在申请中
			else if (leader > 0L && 1 == appLevel){
				retStatus = 4;
			}
			
			request().setAttribute("notFound", "0");
			request().setAttribute("fimap", fimap);
		}
		
		request().setAttribute("retStatus", retStatus);
		return SUCCESS;
	}
	
	/**
	 *功能：申请理财师
	 * @return
	 */
	public String applyFinance(){
		try {
			User user = (User) session().getAttribute(IConstants.SESSION_USER);
			Long userId=user.getId();
			Map map = new java.util.HashMap();
			map.putAll(paramMap);
			map.put("userId", userId);
			JSONObject obj = new JSONObject();
			String tleader = paramMap.get("leader");
			String myLevel = paramMap.get("myLevel");
//			String nowLevel = paramMap.get("nowLevel");
			if (StringUtils.isEmpty(tleader) && StringUtils.isEmpty(myLevel)){
				obj.put("code", "1");
				obj.put("msg", "提交不能为空");
				JSONUtils.printObject(obj);
				return null;
			}
			if (StringUtils.isEmpty(tleader) && StringUtils.isNotEmpty(paramMap.get("nowLevel"))){
				String nowLevel = paramMap.get("nowLevel");
				if ("3".equals(nowLevel)){
					obj.put("code", "1");
					obj.put("msg", "已经是最高级了");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			
			//查找、校验上级理财师
			if (StringUtils.isNotEmpty(paramMap.get("leader"))){
				User redUser = (User) recommendUserService.queryOne("User.queryUeserByUserName", paramMap);
				if (null == redUser){
					obj.put("code", "1");
					obj.put("msg", "推荐理财师输入错误");
					JSONUtils.printObject(obj);
					return null;
				}
				Long leader = redUser.getId();
				if (leader >= userId){
					obj.put("code", "1");
					obj.put("msg", "推荐理财师输入不符合规则！");
					JSONUtils.printObject(obj);
					return null;
				}
				map.put("leader", leader);
			} else {
				map.put("leader", null);
			}
			String a = paramMap.get("myLevel");
			
			//申请级别，必传值校验
			
//			if (StringUtils.isEmpty(a)){
//				obj.put("code", "2");
//				obj.put("msg", "非法操作");
//				JSONUtils.printObject(obj);
//				return null;
//			}
			if (StringUtils.isNotEmpty(a)){
				String nowLevel = paramMap.get("nowLevel");
				if (Integer.parseInt(nowLevel)+1 != Integer.parseInt(a)){
					obj.put("code", "2");
					obj.put("msg", "非法操作");
					JSONUtils.printObject(obj);
					return null;
				}
			}
			
			Map map1 = new java.util.HashMap();
			map1.put("userId", userId);
			Map fimap = (Map) recommendUserService.queryOne("advert.queryFinancialRecord1", map1);
			if (null == fimap){
				recommendUserService.commonSave("advert.applyFinance", map);
			} else {
				String nowLevel = paramMap.get("nowLevel");
				map1.put("leader", map.get("leader"));
				map1.put("nowLevel", nowLevel);
				recommendUserService.commonUpdate("advert.appFinancialAudit", map1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 *功能：推荐人补录功能
	 * @return
	 */
	public String fillRecommentPage(){
		return SUCCESS;
	}
	public RecommendUserService getRecommendUserService() {
		return recommendUserService;
	}

	public void setRecommendUserService(RecommendUserService recommendUserService) {
		this.recommendUserService = recommendUserService;
	}

	public List<Map<String, Object>> getRecommendUserList() {
		return recommendUserList;
	}

	public void setRecommendUserList(List<Map<String, Object>> recommendUserList) {
		this.recommendUserList = recommendUserList;
	}



}
