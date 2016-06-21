package com.sp2p.action.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.entity.Response;
import com.sp2p.service.FinanceToolsService;

/**
 * @ClassName: FinanceToolsAction.java
 * @Author: li.hou
 * @Descrb: 我要理财，工具箱
 */
public class FinanceToolsAppAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(FinanceToolsAppAction.class);
	private static final long serialVersionUID = 1L;

	private FinanceToolsService financeToolsService;

	/*
	 * public String toolsCalculate() throws Exception{
	 * 
	 * float borrowSum = Convert.strToInt(paramMap.get("borrowSum"), -1); float
	 * yearRate = Convert.strToFloat(paramMap.get("yearRate"), -1);//接收百分比 int
	 * borrowTime = Convert.strToInt(paramMap.get("borrowTime"), -1);//接收的数字是月
	 * int repayWay = Convert.strToInt(paramMap.get("repayWay"), -1);//还款方式
	 * float yearRateVal = yearRate/100;
	 * 
	 * double[] vals = null;//分别为 月利率，每月偿还，还款本息
	 * 
	 * DecimalFormat df = new DecimalFormat("#.00"); if(repayWay == 0){//按月还款
	 * (下拉框的下标从0开始) vals = financeToolsService.rateCalculate2Month(borrowSum,
	 * yearRateVal, borrowTime); // lists =
	 * financeToolsService.rateCalculate2Month(borrowSum, yearRateVal,
	 * borrowTime); }else{//先息后本 vals =
	 * financeToolsService.rateCalculate2Sum(borrowSum, yearRateVal,
	 * borrowTime); } float repay2mon =
	 * Convert.strToFloat(df.format(vals[1]),0); float rate2mon =
	 * Convert.strToFloat(df.format(vals[0]*100),0);//百分比 float repayAll =
	 * Convert.strToFloat(df.format(vals[2]),0); // // if(repayWay == 0){//按月还款
	 * (下拉框的下标从0开始) // str
	 * ="每个月将偿还："+repay2mon+"元 月利率："+rate2mon+"% 还款本息总额："+repayAll; //
	 * }else{//先息后本 //最后一个月偿还本金 // str
	 * ="每个月将偿还："+repay2mon+"元 月利率："+rate2mon+"% 还款本息总额："
	 * +repayAll+" 最后一个月还："+(repay2mon+borrowSum); // } if(repay2mon == 0 ||
	 * rate2mon ==0 || repayAll == 0){ getOut().print("请输入正确的数字进行计算"); return
	 * null; } // String str
	 * ="每个月将偿还："+repay2mon+"元 月利率："+rate2mon+"% 还款本息总额："+repayAll; String str =
	 * repay2mon+"|"+rate2mon+"|"+repayAll; getOut().print(str);
	 * 
	 * return null; }
	 */

	/**
	 * 天标计算
	 */
	public String toolsCalculateDay() throws Exception {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			double borrowSum = Convert
					.strToDouble(infoMap.get("borrowSum"), -1);
			double yearRate = Convert.strToFloat(infoMap.get("yearRate"), -1);// 接收百分比
			int borrowTime = Convert.strToInt(infoMap.get("borrowTime"), -1);// 接收的数字是月

			if (yearRate < 0.00001) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "年利率太低，请重新输入");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			double yearRateVal = yearRate * 1.0f / 100;

			Map<String, Object> map = financeToolsService.rateCalculateDay(
					borrowSum, yearRateVal, borrowTime);

			if (map == null) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "请填写正确信息");
				JSONUtils.printObject(jsonMap);
				return null;
			}

			jsonMap.put("map", map);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (IOException e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(paramMap);
			log.error(e);
		}
		return null;
	}

	/**
	 * 工具箱，利息计算器
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toolsCalculate() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Response response = new Response();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			double borrowSum = Convert
					.strToDouble(infoMap.get("borrow_sum"), -1);
			Double yearRate = Convert.strToDouble(infoMap.get("year_rate"), -1);// 接收百分比
			int borrowTime = Convert.strToInt(infoMap.get("borrow_time"), -1);// 接收的数字是月
			int repayWay = Convert.strToInt(infoMap.get("repayment_method"), -1);// 还款方式

			if (yearRate < 0.00001) {
				response.failure("年利率太低，请重新输入");
				JSONUtils.printObject(response);
				return null;
			}

			double yearRateVal = yearRate * 1.0f / 100;

			List<Map<String, Object>> lists = null;
			if (repayWay == 1) {// 按月还款 (下拉框的下标从0开始)
				lists = financeToolsService.rateCalculate2Month(borrowSum,
						yearRateVal, borrowTime);
			} else if (repayWay == 2) {// 一次性 (下拉框的下标从0开始)
				lists = financeToolsService.rateCalculate2SumOne_new(borrowSum,
						yearRateVal, borrowTime);
			} else {// 先息后本
				lists = financeToolsService.rateCalculate2Sum(borrowSum,
						yearRateVal, borrowTime);
			}
			if (lists == null) {
				response.failure("请填写正确信息");
				JSONUtils.printObject(response);
				return null;
			}
			DecimalFormat decimalFormat = new DecimalFormat("#0.00");
			double expected_revenue = MapUtils.getDoubleValue(lists.get(0), "allPay",0)-borrowSum;
			jsonMap.put("expected_revenue", decimalFormat.format(expected_revenue));
			response.success(jsonMap);
			JSONUtils.printObject(response);
		} catch (IOException e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			log.error(e);
		}
		return null;
	}

	/**
	 * 收益计算器
	 * 
	 * @return
	 * @throws Exception
	 */
	public String incomeCalculate() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Response response = new Response();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			double bidReward = Convert.strToDouble(infoMap.get("bidReward"), 0);
			double borrowSum = Convert
					.strToDouble(infoMap.get("borrow_sum"), -1);
			Double yearRate = Convert.strToDouble(infoMap.get("year_rate"), -1);// 接收百分比
			int borrowTime = Convert.strToInt(infoMap.get("borrow_time"), -1);// 接收的数字是月
			int repayWay = Convert.strToInt(infoMap.get("repayment_method"), -1);// 还款方式
			
			double bidRewardMoney = Convert.strToDouble(infoMap
					.get("bidRewardMoney"), 0);
			if (yearRate < 0.00001) {
				response.failure("年利率太低，请重新输");
				JSONUtils.printObject(response);
				return null;
			}

			double yearRateVal = yearRate * 1.0f / 100;

			List<Map<String, Object>> lists = null;
			if (repayWay == 0) {// 按月还款 (下拉框的下标从0开始)
				lists = financeToolsService.rateIncome2Month(borrowSum,
						yearRateVal, borrowTime, bidReward, bidRewardMoney);
			} else if (repayWay == 1) {// 先息后本
				lists = financeToolsService.rateIncome2Sum(borrowSum,
						yearRateVal, borrowTime, bidReward, bidRewardMoney);
			} else {// 一次性还款 13-07-25
				lists = financeToolsService.rateCalculate2SumOne(borrowSum,
						yearRateVal, borrowTime);
			}
			if (lists == null) {
				response.failure("请填写正确信息");
				JSONUtils.printObject(response);
				return null;
			}
			// String jsonStr = JSONArray.toJSONString(lists);
			DecimalFormat decimalFormat = new DecimalFormat("#0.00");
			double expected_revenue = MapUtils.getDoubleValue(lists.get(0), "allPay",0)-borrowSum;
			jsonMap.put("expected_revenue", decimalFormat.format(expected_revenue));
			response.success(jsonMap);
			JSONUtils.printObject(response);
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			log.error(e);
		}
		return null;
	}

	/**
	 * 手机归属地查询
	 */
	public String queryPhoneInfo() throws Exception {
		String address = "";
		try {
			String mobile = Convert.strToStr(paramMap.get("phoneNum"), null);
			if (mobile == null) {
				getOut().print("请输入手机号码进行查询");
				return null;
			}
			// 手机号码验证
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobile);
			if (!m.matches()) {
				getOut().print("请输入正确的手机号码进行查询");
				return null;
			}
			String key = "08d1e5b008381ad003c8dae75b9f084a";
			String url = "http://apis.juhe.cn/mobile/get?phone=" + mobile
					+ "&key=" + key;
			address = getLocation2(url);
		} catch (Exception e) {
			address = "未知";
			System.out.println("手机所属地查询失败====================");
		}
		getOut().print(address);
		return null;
	}

	/**
	 * 解析url返回的文件，获得值
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getLocation2(String url) throws Exception {
		String address = "";
		URLConnection connection = (URLConnection) new URL(url)
				.openConnection();
		connection.setDoOutput(true);
		InputStream os = connection.getInputStream();
		Thread.sleep(100);
		int length = os.available();
		byte[] buff = new byte[length];
		os.read(buff);
		String s = new String(buff, "utf-8");
		// 返回数据fYodaoCallBack(1,
		// {‘product’:'ip’,'ip’:’192.168.1.1′,’location’:'局域网 对方和您在同一内部网’} , ”);
		String addre[] = s.split("province");
		String addre2[] = addre[1].split(":");
		String addre3 = addre2[1].substring(1, 3);
		String addre4 = addre2[2].substring(1, 3);
		String addre5 = addre2[5].substring(3, 5);
		address = addre3 + addre4 + addre5;
		s = null;
		buff = null;
		os.close();
		connection = null;
		return address;
	}

	/**
	 * ip地址查询
	 */
	public String queryIPInfo() throws Exception {
		String address = "";
		try {
			String ipAddr = Convert.strToStr(SqlInfusion
					.FilteSqlInfusion(paramMap.get("ipAddress")), null);
			if (ipAddr == null) {
				getOut().print("请输入IP进行查询");
				return null;
			}
			// 手机号码验证
			Pattern p = Pattern
					.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
							+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
							+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher m = p.matcher(ipAddr);
			if (!m.matches()) {
				getOut().print("请输入正确的IP地址进行查询");
				return null;
			}
			address = getLocationIp(ipAddr);
		} catch (Exception e) {
			address = "未知";
			System.out.println("IP地址查询失败====================");
		}
		getOut().print(address);
		return null;
	}

	/**
	 * 解析url返回的文件，获得值
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private String getLocationIp(String ip) throws Exception {
		String address = "";
		String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip="
				+ ip;
		URLConnection connection = (URLConnection) new URL(url)
				.openConnection();
		connection.setDoOutput(true);
		InputStream os = connection.getInputStream();
		Thread.sleep(100);
		int length = os.available();
		byte[] buff = new byte[length];
		os.read(buff);
		String s = new String(buff, "gbk");
		Map<String, String> map2 = (Map<String, String>) JSONObject.toBean(
				JSONObject.fromObject(s.substring(21, s.length() - 1)),
				HashMap.class);
		// Map<String, String> map2 = (Map<String, String>) JSONObject.toBean(
		// JSONObject.fromObject(map.get("data")), HashMap.class);
		address = map2.get("country") + map2.get("province") + map2.get("city");
		s = null;
		buff = null;
		os.close();
		connection = null;
		return address;
	}

	/**
	 * ip地址查询
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * public String queryIPInfo() throws Exception { String ipAddr =
	 * Convert.strToStr(paramMap.get("ipAddress"), null); if(ipAddr == null){
	 * getOut().print("请输入IP进行查询|false"); return null; } //手机号码验证 Pattern p =
	 * Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
	 * "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
	 * + "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b"); Matcher m =
	 * p.matcher(ipAddr); if(!m.matches()){
	 * getOut().print("请输入正确的IP地址进行查询|false"); return null; }
	 * getOut().print("http://www.yodao.com/smartresult-xml/search.s?type=ip&q="
	 * +ipAddr+"|true");
	 * 
	 * return null; }
	 */

	public String howToFinanceInit() throws Exception {
		/**
		 * 初始化 从bt_config表中加载静态内容
		 */
		List<Map<String, Object>> map = financeToolsService.queryConfigList();
		if (map != null) {
			request().setAttribute("name", map.get(0).get("name"));
			request().setAttribute("var", map.get(0).get("var"));
		}
		return SUCCESS;
	}

	public FinanceToolsService getFinanceToolsService() {
		return financeToolsService;
	}

	public void setFinanceToolsService(FinanceToolsService financeToolsService) {
		this.financeToolsService = financeToolsService;
	}

}
