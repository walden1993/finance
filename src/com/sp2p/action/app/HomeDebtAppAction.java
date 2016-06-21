package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;

/**
 * 债权转让
 */
public class HomeDebtAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(HomeDebtAppAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;

	private AuctionDebtService auctionDebtService;

	private SelectedService selectedService;

	private List<Map<String, Object>> auctionDaysList;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	/**
	 * 可以转让的债权
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryCanAssignmentDebt() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String borrowTitle = infoMap.get("borrowTitle");
			String borrowerName = infoMap.get("borrowerName");
			pageBean.setPageNum(infoMap.get("curPage"));
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			try {
				assignmentDebtService.queryCanAssignmentDebt(userId,
						borrowTitle, borrowerName, pageBean);
			} catch (SQLException e) {
				log.error(e);
				e.printStackTrace();
			}
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (IOException e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 结束竞拍
	 * 
	 * @return
	 * @throws IOException
	 */
	public String auctingDebtEnd() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			boolean re = userService.checkSign(userId);
			if(!re){
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String id = infoMap.get("debtId");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "债权ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long debtId = Convert.strToLong(id, -1);
			assignmentDebtService.auctingDebtSuccess(debtId, userId, 1);
			Map<String, String> map = assignmentDebtService.getAssignmentDebt(debtId);
			if(map != null){
				long alienatorId = Convert.strToLong(map.get("alienatorId"), -1);//转让人
				userService.updateSign(alienatorId);//更换校验码
			}
			List<Map<String, Object>> auctionerList = assignmentDebtService.queryAuctioner(debtId);
			if(auctionerList != null){
				for(Map<String, Object> auctioner: auctionerList){
					long auctionerId = Convert.strToLong(auctioner.get("userId")+"", -1);//竞拍者
					userService.updateSign(auctionerId);//更换校验码
				}
			}
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 查询竞拍中的债权
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryAuctingDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String borrowTitle = infoMap.get("borrowTitle");
			String borrowerName = infoMap.get("borrowerName");
			pageBean.setPageNum(infoMap.get("curPage"));
			assignmentDebtService.queryAuctingDebt(userId, borrowTitle,
					borrowerName, "2", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();

			if (list != null) {
				Date nowDate = new Date();
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("remainAuctionTime");
					map.put("remainDays", DateUtil.remainDateToString(nowDate,
							date));
				}
			}
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 查询竞拍结束的债权
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryAuctedDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String borrowTitle = infoMap.get("borrowTitle");
			String borrowerName = infoMap.get("borrowerName");
			pageBean.setPageNum(infoMap.get("curPage"));
			assignmentDebtService.queryAuctingDebt(userId, borrowTitle,
					borrowerName, "3", pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 添加债权转让
	 * 
	 * @return
	 * @throws IOException
	 */
	public String addAssignmentDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			double auctionBasePrice = Convert.strToDouble(infoMap
					.get("auctionBasePrice"), -1);
			double debtSum = Convert.strToDouble(infoMap.get("debtSum"), -1);
			double lowerPrice = debtSum * 0.5;
			
			String dealpwd = infoMap.get("dealpwd");
			dealpwd = com.shove.security.Encrypt.decrypt3DES(dealpwd, IConstants.PASS_KEY);
			if(StringUtils.isBlank(dealpwd)){
				jsonMap.put("error", "5");
				jsonMap.put("msg", "请输入交易密码");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			dealpwd = com.shove.security.Encrypt.MD5(dealpwd + IConstants.PASS_KEY);
			if(!dealpwd.equals(userService.queryUserById(userId).get("dealpwd"))){
				jsonMap.put("error", "6");
				jsonMap.put("msg", "交易密码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			boolean re = userService.checkSign(userId);//验证校验码
			if(!re){
				
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (auctionBasePrice < lowerPrice || auctionBasePrice > debtSum) {
				NumberFormat nf = new DecimalFormat("0.00");
				// this.addFieldError("paramMap.auctionBasePrice",
				// "竞拍底价范围为"+nf.format(lowerPrice)+"到"+debtSum+"元之间");
				jsonMap.put("error", "1");
				jsonMap.put("msg", "竞拍底价范围为" + nf.format(lowerPrice) + "到"
						+ debtSum + "元之间");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String investId = infoMap.get("investId");
			if (StringUtils.isBlank(investId)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "投资ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			paramMap.put("borrowId", infoMap.get("borrowId"));
			paramMap.put("auctionBasePrice", auctionBasePrice+"");
			paramMap.put("details", infoMap.get("details"));
			paramMap.put("auctionDays", infoMap.get("auctionDays"));
			paramMap.put("auctionMode", infoMap.get("auctionMode"));
			paramMap.put("debtLimit", infoMap.get("debtLimit"));
			paramMap.put("debtSum", debtSum+"");
			paramMap.put("investId", investId);
			paramMap.put("alienatorId", userId + "");
			paramMap.put("applyTime", DateUtil.dateToString(new Date()));
			long reslut = -1;
			reslut = assignmentDebtService.addAssignmentDebt(paramMap);
			userService.updateSign(userId);//更换校验码
			if (reslut != -1) {
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "成功");
				JSONUtils.printObject(jsonMap);

			} else {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "失败");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			paramMap.put("error", "4");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 查询失败的债权
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryAuctionFailDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String borrowTitle = infoMap.get("borrowTitle");
			String borrowerName = infoMap.get("borrowerName");
			pageBean.setPageNum(infoMap.get("curPage"));
			assignmentDebtService.queryAuctingDebt(userId, borrowTitle,
					borrowerName, "4,5,6,7", pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 取消申请债权转让
	 * 
	 * @return
	 * @throws IOException
	 */
	public String cancelApplyDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			Map<String, String> authMap = this.getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			boolean re = userService.checkSign(userId);
			if(!re){
				jsonMap.put("error", "11");
				jsonMap.put("msg", "*你的账号出现异常，请速与管理员联系！");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long debtId = Convert.strToLong(infoMap.get("debtId"), -1);
			assignmentDebtService.cancelAssignmentDebt(debtId, 5, userId, 1);
			Map<String, String> map = assignmentDebtService.getAssignmentDebt(debtId);
			if(map != null){
				long alienatorId = Convert.strToLong(map.get("alienatorId"), -1);//转让人
				userService.updateSign(alienatorId);//更换校验码
			}
			List<Map<String, Object>> auctionerList = assignmentDebtService.queryAuctioner(debtId);
			if(auctionerList != null){
				for(Map<String, Object> auctioner: auctionerList){
					long auctionerId = Convert.strToLong(auctioner.get("userId")+"", -1);//竞拍者
					userService.updateSign(auctionerId);//更换校验码
				}
			}
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 参与竞拍的债权
	 * 
	 * @return
	 * @throws IOException
	 */
	public String queryBuyingDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String borrowTitle = infoMap.get("borrowTitle");
			String startTime = infoMap.get("startTime");
			String endTime = infoMap.get("endTime");
			pageBean.setPageNum(infoMap.get("curPage"));
			auctionDebtService.queryAuctionDebt(borrowTitle, startTime,
					endTime, userId, "2", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();

			if (list != null) {
				Date nowDate = new Date();
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("remainAuctionTime");
					map.put("remainDays", DateUtil.remainDateToString(nowDate,
							date));
				}
			}
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 成功竞拍的债权
	 * 
	 * @return
	 * @throws IOException
	 */
	public String querySucessBuyedDebt() throws IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, String> authMap = getAppAuthMap();
		long userId = Convert.strToLong(authMap.get("uid"), -1);
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String borrowTitle = infoMap.get("borrowTitle");
			String startTime = infoMap.get("startTime");
			String endTime = infoMap.get("endTime");
			pageBean.setPageNum(infoMap.get("curPage"));
			auctionDebtService.querySuccessAuctionDebt(borrowTitle, startTime,
					endTime, userId, pageBean);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			paramMap.put("error", "1");
			paramMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public List<Map<String, Object>> getAuctionDaysList() throws Exception {
		auctionDaysList = selectedService.getDebtAuctionDays();
		return auctionDaysList;
	}

	public void setAuctionDaysList(List<Map<String, Object>> auctionDaysList) {
		this.auctionDaysList = auctionDaysList;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

}
