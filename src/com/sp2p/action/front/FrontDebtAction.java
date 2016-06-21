package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.security.Encrypt;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AuctionDebtService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;

/**
 * 债权转让
 */
public class FrontDebtAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontDebtAction.class);
	private static final long serialVersionUID = 1L;

	private AssignmentDebtService assignmentDebtService;

	private AuctionDebtService auctionDebtService;
	private SelectedService selectedService;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 查询前台的债权转让
	 * 
	 * @return
	 */
	public String queryFrontAllDebt()  throws Exception{
		pageBean.setPageNum(request.getInt("curPage", -1));
		long debtSum = request.getLong("debtSum", -1);
		long auctionBasePrice = request.getLong("auctionBasePrice", -1);
		long auctionMode = request.getLong("auctionMode", -1);
		long isLate = request.getLong("isLate", -1);
		long publishDays = request.getLong("publishDays", -1);
		String borrowTitle = paramMap.get("borrowTitle");
		try {

			assignmentDebtService.queryAllDebt(borrowTitle,debtSum, auctionBasePrice,
					auctionMode, isLate, publishDays, "2,3", pageBean);
			List<Map<String, Object>> list = pageBean.getPage();
			Date nowDate = new Date();
			if (list != null) {
				for (Map<String, Object> map : list) {
					Date date = (Date) map.get("actionTime");
					String remainDays = DateUtil.remainDateToString(nowDate,
							date);
					map.put("remainDays", remainDays);
				}
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		//将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询前台的债权转让
	 * 
	 * @return
	 */
	public String queryFrontSuccessDebt() throws Exception {
		pageBean.setPageNum(request.getInt("curPage", -1));
		long debtSum = request.getLong("debtSum", -1);
		long auctionBasePrice = request.getLong("auctionBasePrice", -1);
		long auctionMode = request.getLong("auctionMode", -1);
		long isLate = request.getLong("isLate", -1);
		long publishDays = request.getLong("publishDays", -1);
		String borrowTitle = request.getString("borrowTitle");
		
		try {
			assignmentDebtService.queryAllDebt(borrowTitle,debtSum, auctionBasePrice,
					auctionMode, isLate, publishDays, "3", pageBean);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		//将参数设置到paramMap
		setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * 查询债权债权详情
	 * 
	 * @return
	 */
	public String queryDebtDetail() throws Exception {
		long id = request.getLong("id", -1);
		try {

			Map<String, String> map = assignmentDebtService
					.getAssignmentDebt(id);
			if (map != null) {
				long viewCount = Convert.strToLong(map.get("viewCount"), 0);
				viewCount++;
				paramMap.putAll(map);
				long borrowId = Convert.strToLong(map.get("borrowId"), -1);
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				Map<String, String> mapth = auctionDebtService.queryBorrowerImgpath(borrowId);
				String imgPath = mapth.get("imgPath");
				paramMap.put("imgPath", imgPath+"");
				paramMap.put("borrowerId", borrowerId+"");
				paramMap.put("viewCount", viewCount + "");
				map = new HashMap<String, String>();
				map.put("viewCount", viewCount + "");
				assignmentDebtService.updateAssignmentDebt(id,-1, map);
				String publishTime = paramMap.get("publishTime");
				long auctionDays = Convert.strToLong(paramMap
						.get("auctionDays"), 0);
				if(StringUtils.isNotBlank(publishTime)){
					String remainDays = DateUtil.remainDateToString(new Date(),
							DateUtil.dateAddDay(DateUtil.strToDate(publishTime),
									(int) auctionDays));
					paramMap.put("remainDays", remainDays);
				}
				long debtId = Convert.strToLong(paramMap.get("id"), -1);
				paramMap.put("debtId", paramMap.get("id"));
				paramMap.putAll(auctionDebtService
						.queryAuctionMaxPriceAndCount(debtId));
				long alienatorId = Convert.strToLong(paramMap
						.get("alienatorId"), -1);
				Map<String, String> userMap = auctionDebtService
						.getUserAddressById(alienatorId);
				Map<String, String> userOrgMap = auctionDebtService
				.getOrgAddressById(alienatorId);
				request().setAttribute("userMap", userMap);
				request().setAttribute("userOrgMap", userOrgMap);
			}

		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 添加留言
	 * @throws DataException 
	 * 
	 */
	public String addDebtMSG() throws Exception {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("msg_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String msgContent = paramMap.get("msg") == null ? "" : paramMap
				.get("msg");
		long returnId = -1;
		returnId = assignmentDebtService.addDebtMsg(idLong, user.getId(),
				msgContent);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * 留言初始化
	 * 
	 */
	public String debtMSGInit() throws Exception{
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String pageNum = paramMap.get("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		if (idLong == -1) {
			return "404";
		}
		assignmentDebtService.queryDebtMSGBord(idLong, pageBean);
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * 竞拍初始化
	 * 
	 * @return
	 */
	public String auctingDebtInit() throws Exception {
		long userId = this.getUserId();
		try {
			paramMap.put("debtId", request.getString("debtId"));
			Map<String, String> map = auctionDebtService.getUserById(userId);
			if (map != null) {
				paramMap.put("usableSum", map.get("usableSum"));
				paramMap.put("totalSum", String.format("%.2f", Convert
						.strToDouble(map.get("freezeSum"), 0.0)
						+ Convert.strToDouble(map.get("usableSum"), 0.0)));
			}
			Map<String,String> debtMap =  assignmentDebtService.getAssignmentDebt(request.getLong("debtId", -1));
			if(debtMap != null){
				paramMap.putAll(debtMap);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 参与竞拍
	 * 
	 * @return
	 */
	public String addAuctingDebt() throws Exception {
		Thread.sleep(100);
		long debtId = Convert.strToLong(paramMap.get("debtId"), -1);
		long userId = this.getUserId();
		
		try {
			
			
			String pwd = paramMap.get("pwd");
			if ("1".equals(IConstants.ENABLED_PASS)){ //1为默认启用
				pwd =  Encrypt.MD5(pwd.trim());
			}else{
				pwd =  Encrypt.MD5(pwd.trim()+IConstants.PASS_KEY);
			}
			double auctionPrice = Convert.strToDouble(paramMap.get("auctionPrice"), 0.0);
			String code = paramMap.get("code");
			String sessionCode = (String) session().getAttribute(
					"auction_checkCode");
			if (sessionCode == null || !sessionCode.equals(code)) {
				JSONUtils.printStr("-1"); // 验证码错误
				return null;
			}
			Map<String, String> debtMap = assignmentDebtService
					.getAssignmentDebt(debtId);
			Map<String, String> userMap = auctionDebtService
					.getUserById(userId);
			
			if (debtMap != null && userMap != null) {
				if (debtMap.get("alienatorId").equals(userId + "")) {
					JSONUtils.printStr("-2"); // 不能投自己转让的的债权
					return null;
				}
				long borrowId = Convert.strToLong(debtMap.get("borrowId"), -1);
			
				
			
				if (!pwd.equals(userMap.get("dealpwd"))) {
					JSONUtils.printStr("-3"); // 交易密码不对
					return null;
				}
				Map<String,String> aucctionMap = auctionDebtService.getAuctionDebt(debtId,userId);
				double oldAuctionPrice = 0.0;
				if(aucctionMap != null){
					oldAuctionPrice = Convert.strToDouble(aucctionMap.get("auctionPrice"),0.0);
				}
				
				double usableSum = Convert.strToDouble(userMap.get("usableSum"), 0.0);
				if (usableSum < (auctionPrice-oldAuctionPrice)) {
					JSONUtils.printStr("-4"); // 可用余额不足
					return null;
				}
				double debtSum = Convert.strToDouble(debtMap.get("debtSum"),
						0.0);
				if (debtSum < auctionPrice) {
					JSONUtils.printStr("-5"); // 大于债权金额
					return null;
				}

				double auctionBasePrice = Convert.strToDouble(debtMap
						.get("auctionBasePrice"), 0.0);
				if (auctionBasePrice > auctionPrice) {
					JSONUtils.printStr("-6"); // 小于最小竞拍金额
					return null;
				}
				
				long actionTimes = auctionDebtService.queryAuctionUserTimes(debtId, userId);
				if (actionTimes >= 2) {
					JSONUtils.printStr("-8"); // 该债权您只能竞拍两次
					return null;
				}
				
				long borrowerId = auctionDebtService.queryBorrowerByBorrowId(borrowId);
				if(borrowerId==userId){
					JSONUtils.printStr("-9"); // 借款者不能竞拍该债权
					return null;
				}
				
				if(oldAuctionPrice >= auctionPrice){
					JSONUtils.printStr("-10"); // 第二次竞拍金额应大于第一次竞拍金额
					return null;
				}
				double auctionHighPrice = Convert.strToDouble(debtMap.get("auctionHighPrice"), -1);
				if(auctionHighPrice != -1 && auctionHighPrice >= auctionPrice){
					JSONUtils.printStr("-11"); // 竞拍金额要大于最高竞拍金额
					return null;
				}
				if(!"2".equals(debtMap.get("debtStatus"))){
					JSONUtils.printStr("-7"); //竞拍失败
					return null;
				}
				
				Map<String,String> pro_map = auctionDebtService.procedure_Debts(debtId, userId, auctionPrice, pwd, this.getBasePath());
				
				userService.updateSign(userId);//更换校验码
				long result = -1;
				result =Convert.strToLong( pro_map.get("ret"),-1);
				if(result == 1){
					JSONUtils.printStr("1");//竞拍成功
				}else{
					JSONUtils.printStr(result+"");//竞拍失败
				}
				
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询竞拍记录
	 * 
	 * @return
	 */
	public String queryAcutionRecordInfo() throws Exception {
		long id = Convert.strToLong(paramMap.get("id"), -1);
		try {
			List<Map<String, Object>> list = auctionDebtService
					.queryAuctionDebtByDebtId(id);
			request().setAttribute("list", list);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setAuctionDebtService(AuctionDebtService auctionDebtService) {
		this.auctionDebtService = auctionDebtService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

}
