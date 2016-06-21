package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.Convert;
import com.shove.JSONUtils;
import com.shove.data.DataException;
import com.shove.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.PayTreasureService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;


/**
 * 涨薪宝
 * @author hjh
 *
 */
public class FrontPayTreasureAction extends BaseFrontAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontPayTreasureAction.class);
	private PayTreasureService payTreasureService;
	private UserService userService;
	
	//详情
	public String paytreasuredetail() throws Exception{
		Map<String, Object> map = payTreasureService.paytreasuredetail(getUserId());
		
		//预计收益到账时间
		String date = DateUtil.YYYY_MM_DD.format(new Date())+" 15:00:00";
		Date proDate = DateUtil.strToDate(date);
		Date now = new Date();
		String showDate = "";
		if (now.after(proDate)) {
			now = DateUtil.dateAddDay(now, 1);
		}
		showDate = DateUtil.YYYYMMDDPoint.format(now);
		
		//可用余额
		if (getUserId()!=-1) {
			Map<String, String> usableSum= userService.queryPresent(getUserId());
			map.put("usableSum", usableSum);
			Map<String, String> yesterdayProfit =  payTreasureService.yesterdayProfit(getUserId());
			map.put("zxbprofit", yesterdayProfit);
		}
		
		map.put("showdate", showDate);
		request().setAttribute("map", map);
		
		if (checkMobile()) {
			return "mobile";
		}
		
		
		return SUCCESS;
	}
	
	//转入
	public String intoPayTreasure() throws Exception{
		long userId = getUserId();
		
		Map<String, Object> map = payTreasureService.paytreasuredetail(getUserId());
		Map<String, Object> paytreasure = (Map<String, Object>) map.get("paytreasure");
		if ("1".equals(paytreasure.get("isopen"))) {
			JSONUtils.printStr("11");
			return null;
		}
		
		String amountstr = paramMap.get("amount");
		String dealpassword = paramMap.get("dealpassword");
		if (StringUtils.isBlank(amountstr)) {
			JSONUtils.printStr("1");
			return null;
		}
		
		if (!NumberUtils.isNumber(amountstr)) {
			JSONUtils.printStr("2");
			return null;
		}else {
			
			int indexof = amountstr.indexOf(".");
			if (indexof!=-1) {//有小数
				int len = amountstr.substring(indexof+1).length();
				if (len>2) {
					JSONUtils.printStr("13");
					return null;
				}
			}
			
			double amountdouble = Convert.strToDouble(amountstr, 0);
			
			if (amountdouble<=0) {
				JSONUtils.printStr("12");
				return null;
			}
			
			if (map.get("payinvest")!=null) {
				Map<String, Object> payinvest = (Map<String, Object>) map.get("payinvest");
				double nowamount = MapUtils.getDoubleValue(payinvest, "investamount");
				if (amountdouble<1000-nowamount) {
					JSONUtils.printStr("12");
					return null;
				}
			}else {
				if (amountdouble<1000) {
					JSONUtils.printStr("12");
					return null;
				}
			}
			
		}
		
		if (StringUtils.isBlank(dealpassword)) {
			JSONUtils.printStr("3");
			return null;
		}
		
		Map<String, String> user= userService.queryUserById(getUserId());
		if (user==null) {
			JSONUtils.printStr("4");
			return null;
		}else {
			String dealpwd = user.get("dealpwd");
			String usableSumstr=user.get("usableSum");
			
			if ("1".equals(IConstants.ENABLED_PASS)) {
				dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim());
			} else {
				dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim()
						+ IConstants.PASS_KEY);
			}
			if (!dealpassword.equals(dealpwd)) {
				JSONUtils.printStr("5");
				return null;
			}
			double amount = Convert.strToDouble(amountstr, 0.0);
			double usableSum = Convert.strToDouble(usableSumstr, 0.0);
			if (amount>usableSum) {
				JSONUtils.printStr("6");
				return null;
			}
			paramMap.put("username", user.get("username"));
		}
		paramMap.put("ip", ServletUtils.getIpAddress(request()));
		paramMap.put("userid", userId+"");
		long result = payTreasureService.intoPayTreasure(paramMap);
		if (result>0) {
			JSONUtils.printStr("8");
		}else {
			JSONUtils.printStr("7");
		}
		return null;
	}
	
	//转出详情初始化
	public String outPayTreasureInit() throws Exception{
		//涨薪宝总金额
		Map<String, String> myPayInvest = payTreasureService.queryMyPayInvest(getUserId());
		//三好资本可用余额
		Map<String, String> usableSum= userService.queryPresent(getUserId());
		request().setAttribute("usableSum", usableSum);
		request().setAttribute("myPayInvest", myPayInvest);
		if (checkMobile()) {
			return "mobile";
		}
		return SUCCESS;
	}
	
	//转出
	public String outPayTreasure() throws Exception{
		long userId = getUserId();
		String amountstr = paramMap.get("amount");
		String dealpassword = paramMap.get("dealpassword");
		if (StringUtils.isBlank(amountstr)) {
			JSONUtils.printStr("1");
			return null;
		}
		
		if (!NumberUtils.isNumber(amountstr)) {
			JSONUtils.printStr("2");
			return null;
		}
		
		int indexof = amountstr.indexOf(".");
		if (indexof!=-1) {//有小数
			int len = amountstr.substring(indexof+1).length();
			if (len>2) {
				JSONUtils.printStr("12");
				return null;
			}
		}
		
		if (StringUtils.isBlank(dealpassword)) {
			JSONUtils.printStr("3");
			return null;
		}
		
		Map<String, String> user= userService.queryUserById(getUserId());
		
		boolean allOut = false;
		
		if (user==null) {
			JSONUtils.printStr("4");
			return null;
		}else {
			String dealpwd = user.get("dealpwd");
			
			Map<String, String> myPayInvest = payTreasureService.queryMyPayInvest(getUserId());
			if (myPayInvest==null || myPayInvest.size()<0) {
				JSONUtils.printStr("6");
				return null;
			}
			String usableSumstr=myPayInvest.get("investamount");
			
			if ("1".equals(IConstants.ENABLED_PASS)) {
				dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim());
			} else {
				dealpassword = com.shove.security.Encrypt.MD5(dealpassword.trim()
						+ IConstants.PASS_KEY);
			}
			if (!dealpassword.equals(dealpwd)) {
				JSONUtils.printStr("5");
				return null;
			}
			double amount = Convert.strToDouble(amountstr, 0.0);
			if (amount<=0) {
				JSONUtils.printStr("1");
				return null;
			}
			double usableSum = Convert.strToDouble(usableSumstr, 0.0);
			if (amount>usableSum) {
				JSONUtils.printStr("6");
				return null;
			}
			
			allOut = amount==usableSum; //如果是属于全部转出
			
			
			paramMap.put("username", user.get("username"));
		}
		paramMap.put("ip", ServletUtils.getIpAddress(request()));
		paramMap.put("userid", userId+"");
		long result = payTreasureService.outPayTreasure(paramMap,allOut);
		if (result>0) {
			JSONUtils.printStr("8");
		}else {
			JSONUtils.printStr("7");
		}
		return null;
	}
	
	
		//转出
		public String autoOutPayTreasure() throws Exception{
			long userId = Convert.strToLong(paramMap.get("userId"), -1L);
			String amountstr = paramMap.get("amount");
			String dealpassword = paramMap.get("password");
			if (StringUtils.isBlank(amountstr)) {
				JSONUtils.printStr("1");
				return null;
			}
			
			if (!NumberUtils.isNumber(amountstr)) {
				JSONUtils.printStr("2");
				return null;
			}
			
			int indexof = amountstr.indexOf(".");
			if (indexof!=-1) {//有小数
				int len = amountstr.substring(indexof+1).length();
				if (len>2) {
					JSONUtils.printStr("12");
					return null;
				}
			}
			
			if (StringUtils.isBlank(dealpassword)) {
				JSONUtils.printStr("3");
				return null;
			}
			
			Map<String, String> user= userService.queryUserById(userId);
			
			boolean allOut = false;
			
			if (user==null) {
				JSONUtils.printStr("4");
				return null;
			}else {
				
				Map<String, String> myPayInvest = payTreasureService.queryMyPayInvest(userId);
				if (myPayInvest==null || myPayInvest.size()<0) {
					JSONUtils.printStr("6");
					return null;
				}
				String usableSumstr=myPayInvest.get("investamount");
				
				
				if (!dealpassword.equals("123456")) {
					JSONUtils.printStr("5");
					return null;
				}
				double amount = Convert.strToDouble(amountstr, 0.0);
				if (amount<=0) {
					JSONUtils.printStr("1");
					return null;
				}
				double usableSum = Convert.strToDouble(usableSumstr, 0.0);
				if (amount>usableSum) {
					JSONUtils.printStr("6");
					return null;
				}
				
				allOut = amount==usableSum; //如果是属于全部转出
				
				
				paramMap.put("username", user.get("username"));
			}
			paramMap.put("ip", ServletUtils.getIpAddress(request()));
			paramMap.put("userid", userId+"");
			long result = payTreasureService.outPayTreasure(paramMap,allOut);
			if (result>0) {
				JSONUtils.printStr("ok");
			}else {
				JSONUtils.printStr("error");
			}
			return null;
		}
	
	//手机版收益记录初始化
	public String profitRecordInit() throws SQLException{
		Map<String, String> mypaytreasure = payTreasureService.queryMyPayInvest(getUserId());
		request().setAttribute("mypaytreasure", mypaytreasure);
		return SUCCESS;
	}
	
	//我的涨薪宝
	public String  mypaytreasure() throws Exception{
		Map<String, String> mypaytreasure = payTreasureService.queryMyPayInvest(getUserId());
		Map<String, String> usableSum= userService.queryPresent(getUserId());
		request().setAttribute("mypaytreasure", mypaytreasure);
		request().setAttribute("usableSum", usableSum);
		
		String publishTimeEnd = DateUtil.YYYY_MM_DD.format(new Date());//当前时间
		String publishTimeStart = DateUtil.YYYY_MM_DD.format(DateUtil.dateAddMonth(new Date(), -1));//减去一个月时间
		paramMap.put("publishTimeEnd", publishTimeEnd);
		paramMap.put("publishTimeStart", publishTimeStart);
		
		
		return SUCCESS;
	}
	
	//转入转出记录
	public String payInvestRecord() throws SQLException{
		payTreasureService.payInvestRecord(pageBean, getUserId(),paramMap);
		return SUCCESS;
	}
	
	//收益记录
	public String profitRecord() throws SQLException{
		payTreasureService.profitRecord(pageBean, getUserId(),paramMap);
		if (checkMobile()) {
			return "mobile";
		}
		return SUCCESS;
	}
	
	//自动转入
	public String autoIntoPayTreasure() throws SQLException{
		payTreasureService.autoIntoPayTreasure();
		return null;
	}
	
	//涨薪宝设置界面
	public String payTreasureSetting() throws SQLException{
		setParamMap(payTreasureService.querySetting(getUserId()));
		return SUCCESS;
	}
	
	//保存涨薪宝设置
	public String payTreasureSettingSave() throws SQLException{
		paramMap.put("userId", getUserId()+"");
		
		String open = paramMap.get("open");
		
		
		if ("0".equals(open)) {
			String amountstr = paramMap.get("minBalance");//保留金额
			
			if (StringUtils.isBlank(amountstr) || !NumberUtils.isNumber(amountstr)) {
				JSONUtils.printStr("1");
				return null;
			}
			//保留金额大于0
			int amount = Convert.strToInt(amountstr, 0);
			if (amount<=0) {
				JSONUtils.printStr("1");
				return null;
			}
			int indexof = amountstr.indexOf(".");
			if (indexof!=-1) {//有小数
				int len = amountstr.substring(indexof+1).length();
				if (len>2) {
					JSONUtils.printStr("2");
					return null;
				}
			}
		}
		
		
		long result = payTreasureService.saveSetting(paramMap, getUserId());
		if (result>0) {
			JSONUtils.printStr("3");
		}else {
			JSONUtils.printStr("4");
		}
		return null;
	}
	
	//涨薪宝协议
	public String payTreasureProtocol() throws Exception{
		Map<String, String> person = userService.queryPersonById(getUserId());
		Map<String, String> user =userService.queryUserById(getUserId());
		request().setAttribute("name", "涨薪宝-金秋1号");
		request().setAttribute("userMap", user);
		request().setAttribute("personMap", person);
		return SUCCESS;
	}
	
	//手机转入
	public String intoPayTreasureMobile() throws Exception{
		Map<String, String> usableSum= userService.queryPresent(getUserId());
		request().setAttribute("usableSum", usableSum);
		return SUCCESS;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}

}
