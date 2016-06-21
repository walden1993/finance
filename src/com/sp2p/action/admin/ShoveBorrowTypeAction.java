package com.sp2p.action.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.Convert;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.EmalAndMessageService;
import com.sp2p.service.admin.ShoveBorrowAmountTypeService;
import com.sp2p.service.admin.ShoveBorrowStyleService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.DateUtil;

/**
 * 标种类型
 * 
 * @author C_J
 * 
 */
public class ShoveBorrowTypeAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(ShoveBorrowTypeService.class);
	private static final long serialVersionUID = 1L;

	private ShoveBorrowTypeService shoveBorrowTypeService;
	private ShoveBorrowStyleService shoveBorrowStyleService;
	private ShoveBorrowAmountTypeService shoveBorrowAmountTypeService;
	private EmalAndMessageService emalAndMessageService;
	private String paramMapStyles;
	private String paramMaplistcounter;
	private String paramMaplistIn;

	/**
	 * 查询所有初始化
	 * 
	 * @return
	 */
	public String shoveTypeAllInit() {

		return SUCCESS;
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public String shoveTypeAllList() {
		try {
			List<Map<String, Object>> mapTypeList = shoveBorrowAmountTypeService
					.queryBorrowAmountAll();
			shoveBorrowTypeService.queryShoveBorrowTypePageAll(pageBean);
			
			List<Map<String, Object>> list = pageBean.getPage();
			for (Map<String, Object> map : list) {
				int i = 0;
				String stytyles = map.get("styles") + "";
				if (stytyles.length() > 0) {
					List<Map<String, Object>> titleList = shoveBorrowStyleService
							.queryBorrowAmountByIds(map.get("styles") + "");
					if (titleList != null) {
						StringBuffer buf = new StringBuffer();
						for (Map<String, Object> titleMap : titleList) {
							buf.append(titleMap.get("title"));
							i++;
							if (i != titleList.size()) {
								buf.append("|");
							}
						}
						map.put("titles", buf.toString());
					}

				}
			}

			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

			request().setAttribute("mapTypeList", mapTypeList);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改初始化
	 * 
	 * @return
	 */
	public String updateShoveTypeInit() {
		int id = request.getInt("id", -1);
		try {
			List<Map<String, Object>> mapList = shoveBorrowStyleService
					.queryBorrowAll();
			List<Map<String, Object>> mapTypeList = shoveBorrowAmountTypeService
					.queryBorrowAmountAll();
			paramMap = shoveBorrowTypeService.queryShoveBorrowTypeById(id);
			String arr[] = paramMap.get("styles").split(",");
			request().setAttribute("mapList", mapList);
			request().setAttribute("mapTypeList", mapTypeList);
			request().setAttribute("arr", arr);
			if ("flow".equals(paramMap.get("nid"))) {
				String arrInsti[] = paramMap.get("institution").split(","); // 机构担保
				String arrCounter[] = paramMap.get("counter_guarantee").split(
						","); // 反担保方式
				// 得到担保机构
				List<Map<String, Object>> listInstitution = emalAndMessageService
						.queryinstitution();
				request().setAttribute("listInstitution", listInstitution);
				request().setAttribute("arrInsti", arrInsti);
				// 得到反担保方式
				List<Map<String, Object>> listCounter = emalAndMessageService
						.queryguarantee();
				request().setAttribute("listCounter", listCounter);
				request().setAttribute("arrCounter", arrCounter);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String updateShoveTypeInfo() {
		
		// id
		int id = request.getInt("paramMap.id", -1);
		// 名称
		String title = request.getString("paramMap.title");
		// 描述
		String description = request.getString("paramMap.description");
				
		// 状态
		int status = request.getInt("paramMap.status", -1);
		// 额度类型
		int amount_type =request.getInt("paramMap_amount_type", -1); 
		// 最低借款额度
		double amount_first = 
				request.getDouble("paramMap.amount_first", 0);
		// 最高的借款额度
		double amount_end = request.getDouble("paramMap.amount_end", 0);
		// 借款金额倍数
		double account_multiple = request.getDouble("paramMap.account_multiple", 0);
		// 开始年利率
		double apr_first =request.getDouble("paramMap.apr_first", 0); 
		// 结束年利率
		double apr_end = request.getDouble("paramMap.apr_end", 0); 
		// 借款期限 月
		String period_month = 
				request.getString("paramMap.period_month");
		// 借款期限 天
		String period_day = request.getString("paramMap.period_day");
		// 有效期
		String validate =request.getString("paramMap.validate"); 
		// 审核最短时间
		int check_first = request.getInt("paramMap.check_first", 0);
		// 审核最长时间
		int check_end =  request.getInt("paramMap.check_end", 0);
		// 最低投标金额
		String tender_account_min = request.getString("paramMap.tender_account_min");
		// 最高投标金额
		String tender_account_max = request.getString("paramMap.tender_account_max");
		// 是否启用 奖励
		int award_status =request.getInt("paramMap_award_status", 0); 
		// 是否启用投标密码
		int password_status =request.getInt("paramMap_password_status", 0);  
		// 奖励比例的最小值
		double award_scale_first = 
				request.getDouble("paramMap.award_scale_first", 0);
		// 奖励比例的最大值
		double award_scale_end = request.getDouble("paramMap.award_scale_end", 0);
		// 不能小于此奖励金额
		double award_account_first =  request.getDouble("paramMap.award_account_first", 0);
		// 不能高于此奖励金额
		double award_account_end = request.getDouble("paramMap.award_account_end", 0);
		// 初审自动通过
		// int verify_auto_status =
		// Convert.strToInt(request("paramMap_verify_auto_status"),0) ;
		// 初审自动通过的备注
		// String verify_auto_remark =
		// Convert.strToStr(request("paramMap.verify_auto_remark"),null) ;
		// VIP冻结保证金
		double vip_frost_scale = request.getDouble("paramMap.vip_frost_scale", 0);
		// 普通会员冻结保证金
		double all_frost_scale =  request.getDouble("paramMap.all_frost_scale", 0);
		// 垫付逾期的天数 月标
		int late_days_month = request.getInt("paramMap.late_days_month", 0);
				
		// 垫付逾期的天数 天标
		int late_days_day = request.getInt("paramMap.late_days_day", 0);
				
		// vip会员垫付本金比例：
		double vip_late_scale =request.getDouble("paramMap.vip_late_scale", 0); 
		// 普通会员垫付本金比例
		double all_late_scale =request.getDouble("paramMap.all_late_scale", 0); 
		// 还款方式
		String listStyle = this.getParamMapStyles();
		// 否开启认购模式
		int subscribe_status =  request.getInt("paramMap_subscribe_status", 0);
		// 借款费
		double locan_fee = request.getDouble("paramMap.locan_fee", 0); 
		// 超出限定期数
		int locan_month = request.getInt("paramMap.locan_month", 0);
		// 超出限定期数借款费
		double locan_fee_month = request.getDouble("paramMap.locan_fee_month", 0); 
		// 天标借款费
		double day_fee = request.getDouble("paramMap.day_fee", 0);
		long result = -1L;
		Map<String, String> map = null;
		int vorsion = 0;
		String nid = null;
		try {
			map = shoveBorrowTypeService.queryShoveBorrowTypeById(id);
			vorsion = Convert.strToInt(map.get("version"), -1) + 1;
			nid = Convert.strToStr(map.get("nid"), null);
			String listIn = null;
			String counter = null;
			// 转换时间
			long time = DateUtil.getTimeCur("yyyy-MM-dd HH:mm:ss", new Date());
			// 得到后台登陆的用户
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			if ("flow".equals(nid)) {
				listIn = this.getParamMaplistIn();// 得到担保机构
				counter = this.getParamMaplistcounter();// 反担保方式
			}
			nid = nid + "_" + id + "_" + vorsion;
			result = shoveBorrowTypeService.updateShoveBorrowType(id, status,
					title, description, account_multiple, password_status,
					amount_type, amount_first, amount_end, apr_first, apr_end,
					check_first, check_end, tender_account_min,
					tender_account_max, period_month, period_day, validate,
					award_status, award_scale_first, award_scale_end,
					award_account_first, award_account_end, subscribe_status,
					counter, listIn, listStyle, vip_frost_scale,
					late_days_month, late_days_day, vip_late_scale,
					all_late_scale, all_frost_scale, vorsion, nid, locan_fee,
					locan_month, locan_fee_month, day_fee);
			if (result > 0) {
				// 添加操作日志
				operationLogService.addOperationLog("t_borrow_type", admin
						.getUserName(), IConstants.UPDATE, admin.getLastIP(),
						0, "修改标的种类信息", 2);
			}
			result = shoveBorrowTypeService.addBorrowTypeLog(nid, status,
					title, title, description, account_multiple,
					password_status, amount_type, amount_first, amount_end,
					apr_first, apr_end, check_first, check_end,
					tender_account_min, tender_account_max, period_month,
					period_day, validate, award_status, award_scale_first,
					award_scale_end, award_account_first, award_account_end,
					subscribe_status, listIn, counter, listStyle,
					vip_frost_scale, late_days_month, late_days_day,
					vip_late_scale, all_late_scale, all_frost_scale, admin
							.getId(), time, admin.getLastIP(), nid, locan_fee,
					locan_month, locan_fee_month, day_fee);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result > 0)
			return SUCCESS;
		else
			return INPUT;
	}

	// 判断字符串是不是数字
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 标题种类 修改时验证
	 * 
	 * @return
	 * @throws IOException
	 */
	public String updateBorrowType() throws IOException {
		String[] arr = null;
		// 名称
		String title = request.getString("title");
		if (StringUtils.isBlank(title)) {
			JSONUtils.printStr("1");
			return null;
		}

		// 最低借款额度
		String amount_first =  request.getString("tb_amount_first");
		if (StringUtils.isBlank(amount_first)) {
			JSONUtils.printStr("2");
			return null;
		}
		if (isNum(amount_first) == false
				|| Double.parseDouble(amount_first) < 0) {
			JSONUtils.printStr("3");
			return null;
		}
		// 最高借款额度
		String amount_end =  request.getString("tb_amount_end");
		if (StringUtils.isBlank(amount_end)) {
			JSONUtils.printStr("4");
			return null;
		}
		if (isNum(amount_end) == false || Double.parseDouble(amount_end) < 0) {
			JSONUtils.printStr("5");
			return null;
		}
		if (Double.parseDouble(amount_first) > Double.parseDouble(amount_end)) {
			JSONUtils.printStr("6");
			return null;
		}
		// 额度倍数
		String account_multiple =  request.getString("tb_account_multiple");
		if (StringUtils.isBlank(account_multiple)) {
			JSONUtils.printStr("7");
			return null;
		}
		if (isNum(account_multiple) == false
				|| Double.parseDouble(account_multiple) < 0) {
			JSONUtils.printStr("8");
			return null;
		}
		// 最低年利率
		String apr_first =request.getString("tb_apr_first"); 
		if (StringUtils.isBlank(apr_first)) {
			JSONUtils.printStr("9");
			return null;
		}
		if (isNum(apr_first) == false || Double.parseDouble(apr_first) < 0) {
			JSONUtils.printStr("10");
			return null;
		}
		// 最高年利率
		String apr_end =request.getString("tb_apr_end"); 
		if (StringUtils.isBlank(apr_end)) {
			JSONUtils.printStr("11");
			return null;
		}
		if (isNum(apr_end) == false) {
			JSONUtils.printStr("12");
			return null;
		}
		if (Double.parseDouble(apr_first) > Double.parseDouble(apr_end)) {
			JSONUtils.printStr("13");
			return null;
		}
		if (Double.parseDouble(apr_first) < 0.01
				|| Double.parseDouble(apr_end) > 100) {
			JSONUtils.printStr("58");
			return null;
		}
		// 借款期限 月标
		String period_month =request.getString("tb_period_month"); 
		if (StringUtils.isBlank(period_month)) {
			JSONUtils.printStr("14");
			return null;
		}
		arr = period_month.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (isNum(arr[i]) == false) {
				JSONUtils.printStr("15");
				return null;
			}
			if (arr[i].indexOf(".") != -1) { // 判断是不是整数
				JSONUtils.printStr("15");
				return null;
			}
			if (Integer.parseInt(arr[i]) < 0 || Integer.parseInt(arr[i]) > 60) {
				JSONUtils.printStr("15");
				return null;
			}
		}
		// 借款期限 天标
		String period_day =request.getString("tb_period_day");  
		if (StringUtils.isBlank(period_day)) {
			JSONUtils.printStr("16");
			return null;
		}
		arr = period_day.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (isNum(arr[i]) == false) {
				JSONUtils.printStr("17");
				return null;
			}
			if (arr[i].indexOf(".") != -1) { // 判断是不是整数
				JSONUtils.printStr("17");
				return null;
			}
			if (Integer.parseInt(arr[i]) < 0 || Integer.parseInt(arr[i]) > 25) {
				JSONUtils.printStr("17");
				return null;
			}
		}
		// 有效期
		String validate =request.getString("tb_validate");  
		if (StringUtils.isBlank(validate)) {
			JSONUtils.printStr("18");
			return null;
		}
		arr = validate.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (isNum(arr[i]) == false) {
				JSONUtils.printStr("19");
				return null;
			}
			if (arr[i].indexOf(".") != -1) { // 判断是不是整数
				JSONUtils.printStr("19");
				return null;
			}
		}
		// 审核最短时间
		String check_first = request.getString("tb_check_first");
		if (StringUtils.isBlank(check_first)) {
			JSONUtils.printStr("20");
			return null;
		}
		if (isNum(check_first) == false || Double.parseDouble(check_first) < 0) {
			JSONUtils.printStr("21");
			return null;
		}
		if (check_first.indexOf(".") != -1) { // 判断是不是整数
			JSONUtils.printStr("21");
			return null;
		}

		// 审核最长时间
		String check_end =  request.getString("tb_check_end");
		if (StringUtils.isBlank(check_end)) {
			JSONUtils.printStr("22");
			return null;
		}
		if (isNum(check_end) == false || Double.parseDouble(check_end) < 0) {
			JSONUtils.printStr("23");
			return null;
		}
		if (Double.parseDouble(check_first) > Double.parseDouble(check_end)) {
			JSONUtils.printStr("24");
			return null;
		}
		if (check_end.indexOf(".") != -1) { // 判断是不是整数
			JSONUtils.printStr("23");
			return null;
		}
		/*
		 * if(Double.parseDouble(check_first)>31 ||
		 * Double.parseDouble(check_end)>31){ JSONUtils.printStr("25"); return
		 * null; }
		 */
		// 最低投标金额
		String tender_account_min = request.getString("tb_tender_account_min"); 
		if (StringUtils.isBlank(tender_account_min)) {
			JSONUtils.printStr("26");
			return null;
		}
		// 最高投标金额
		String account_max = request.getString("tb_account_max");
		if (StringUtils.isBlank(account_max)) {
			JSONUtils.printStr("27");
			return null;
		}
		arr = tender_account_min.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (isNum(arr[i]) == false) {
				JSONUtils.printStr("28");
				return null;
			}
			if (Double.parseDouble(arr[i]) < 0) {
				JSONUtils.printStr("28");
				return null;
			}
		}
		arr = account_max.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (isNum(arr[i]) == false) {
				JSONUtils.printStr("28");
				return null;
			}
			if (Double.parseDouble(arr[i]) < 0) {
				JSONUtils.printStr("28");
				return null;
			}
		}
		// 奖励比例最小值
		String award_scale_first =  request.getString("tb_award_scale_first");
		if (StringUtils.isBlank(award_scale_first)) {
			JSONUtils.printStr("30");
			return null;
		}
		// 奖励比例最大值
		String award_scale_end = request.getString("tb_award_scale_end");
		if (StringUtils.isBlank(award_scale_end)) {
			JSONUtils.printStr("31");
			return null;
		}
		if (isNum(award_scale_first) == false
				|| isNum(award_scale_end) == false
				|| Double.parseDouble(award_scale_first) < 0
				|| Double.parseDouble(award_scale_end) < 0) {
			JSONUtils.printStr("32");
			return null;
		}
		if (Double.parseDouble(award_scale_first) > Double
				.parseDouble(award_scale_end)) {
			JSONUtils.printStr("33");
			return null;
		}
		if (Double.parseDouble(award_scale_first) < 0
				|| Double.parseDouble(award_scale_end) > 100) {
			JSONUtils.printStr("34");
			return null;
		}
		// 奖励固定金额最小值
		String award_account_first = request.getString("tb_award_account_first");
		if (StringUtils.isBlank(award_account_first)) {
			JSONUtils.printStr("35");
			return null;
		}
		// 奖励固定金额最大值
		String award_account_end =request.getString("tb_award_account_end"); 
		if (StringUtils.isBlank(award_account_end)) {
			JSONUtils.printStr("36");
			return null;
		}
		if (isNum(award_account_first) == false
				|| isNum(award_account_end) == false
				|| Double.parseDouble(award_account_first) < 0
				|| Double.parseDouble(award_account_end) < 0) {
			JSONUtils.printStr("37");
			return null;
		}
		if (Double.parseDouble(award_account_first) > Double
				.parseDouble(award_account_end)) {
			JSONUtils.printStr("38");
			return null;
		}
		/*
		 * //初审自动通过备注 String verify_auto_remark =
		 * Convert.strToStr(request().getParameter("tb_verify_auto_remark"),
		 * ""); if(StringUtils.isBlank(verify_auto_remark)){
		 * JSONUtils.printStr("39"); return null; }
		 */
		// Vip冻结保证金
		String vip_frost_scale = request.getString("tb_vip_frost_scale");
		if (StringUtils.isBlank(vip_frost_scale)) {
			JSONUtils.printStr("40");
			return null;
		}
		if (isNum(vip_frost_scale) == false) {
			JSONUtils.printStr("41");
			return null;
		}
		if (Double.parseDouble(vip_frost_scale) < 0
				|| Double.parseDouble(vip_frost_scale) > 100) {
			JSONUtils.printStr("42");
			return null;
		}
		// 普通会员冻结保证金
		String all_frost_scale =request.getString("tb_all_frost_scale");
		if (StringUtils.isBlank(all_frost_scale)) {
			JSONUtils.printStr("43");
			return null;
		}
		if (isNum(all_frost_scale) == false) {
			JSONUtils.printStr("44");
			return null;
		}
		if (Double.parseDouble(all_frost_scale) < 0
				|| Double.parseDouble(all_frost_scale) > 100) {
			JSONUtils.printStr("45");
			return null;
		}
		// 垫付逾期天数 (月标)
		String late_days_month = request.getString("tb_late_days_month");
		if (StringUtils.isBlank(late_days_month)) {
			JSONUtils.printStr("46");
			return null;
		}
		if (Double.parseDouble(late_days_month) < 0
				|| isNum(late_days_month) == false) {
			JSONUtils.printStr("47");
			return null;
		}
		if (late_days_month.indexOf(".") != -1) { // 判断是不是整数
			JSONUtils.printStr("47");
			return null;
		}
		// 垫付逾期天数 (天标)
		String late_days_day = request.getString("tb_late_days_day");
		if (StringUtils.isBlank(late_days_day)) {
			JSONUtils.printStr("48");
			return null;
		}
		if (Double.parseDouble(late_days_day) < 0
				|| isNum(late_days_day) == false) {
			JSONUtils.printStr("49");
			return null;
		}
		if (late_days_day.indexOf(".") != -1) { // 判断是不是整数
			JSONUtils.printStr("49");
			return null;
		}
		// vip垫付利息比例
		String vip_late_scale = request.getString("tb_vip_late_scale");
		if (StringUtils.isBlank(vip_late_scale)) {
			JSONUtils.printStr("50");
			return null;
		}
		if (Double.parseDouble(vip_late_scale) < 0
				|| Double.parseDouble(vip_late_scale) > 100
				|| isNum(vip_late_scale) == false) {
			JSONUtils.printStr("51");
			return null;
		}
		// 普通会员垫付利息比例
		String all_late_scale =request.getString("tb_all_late_scale"); 
		if (StringUtils.isBlank(all_late_scale)) {
			JSONUtils.printStr("52");
			return null;
		}
		if (Double.parseDouble(all_late_scale) < 0
				|| Double.parseDouble(all_late_scale) > 100
				|| isNum(all_late_scale) == false) {
			JSONUtils.printStr("53");
			return null;
		}
		if ("flow".equals(request.getString("nid"))) {
			// 担保机构
			String paramMapInstitution = request.getString("paramMapInstitution");
			if (StringUtils.isBlank(paramMapInstitution)) {
				JSONUtils.printStr("57");
				return null;
			}
			// 反担保方式
			String paramMapcounter =request.getString("paramMapcounter"); 
			if (StringUtils.isBlank(paramMapcounter)) {
				JSONUtils.printStr("56");
				return null;
			}
		}
		// 还款方式
		String paramMapStyles =request.getString("paramMapStyles"); 
		if (StringUtils.isBlank(paramMapStyles)) {
			JSONUtils.printStr("54");
			return null;
		}
		// 月标借款费
		String locan_fee =request.getString("locan_fee"); // 借款费
		if (StringUtils.isBlank(locan_fee)) {
			JSONUtils.printStr("60");
			return null;
		}
		if (isNum(locan_fee) == false || Double.parseDouble(locan_fee) < 0
				|| Double.parseDouble(locan_fee) > 100) {
			JSONUtils.printStr("61");
			return null;
		}

		String locan_month = request.getString("locan_month");// 借款限定期数
		if (StringUtils.isBlank(locan_month)) {
			JSONUtils.printStr("62");
			return null;
		}
		if (Double.parseDouble(locan_month) < 0 || isNum(locan_month) == false
				|| Double.parseDouble(locan_month) > 100) {
			JSONUtils.printStr("63");
			return null;
		}
		if (locan_month.indexOf(".") != -1) { // 判断是不是整数
			JSONUtils.printStr("63");
			return null;
		}
		// 超出借款限定期数借款费
		String locan_fee_month =request.getString("locan_fee_month"); // 超出借款限定期数借款费
		if (StringUtils.isBlank(locan_fee_month)) {
			JSONUtils.printStr("64");
			return null;
		}
		if (isNum(locan_fee_month) == false
				|| Double.parseDouble(locan_fee_month) < 0
				|| Double.parseDouble(locan_fee_month) > 100) {
			JSONUtils.printStr("65");
			return null;
		}
		// 天标借款费
		String day_fee =request.getString("day_fee"); // 天标借款费
		if (StringUtils.isBlank(day_fee)) {
			JSONUtils.printStr("66");
			return null;
		}
		if (isNum(day_fee) == false || Double.parseDouble(day_fee) < 0
				|| Double.parseDouble(day_fee) > 100) {
			JSONUtils.printStr("67");
			return null;
		}

		else {
			JSONUtils.printStr("55");
			return null;
		}

	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setShoveBorrowStyleService(
			ShoveBorrowStyleService shoveBorrowStyleService) {
		this.shoveBorrowStyleService = shoveBorrowStyleService;
	}

	public void setShoveBorrowAmountTypeService(
			ShoveBorrowAmountTypeService shoveBorrowAmountTypeService) {
		this.shoveBorrowAmountTypeService = shoveBorrowAmountTypeService;
	}

	public String getParamMapStyles() {
		return paramMapStyles;
	}

	public void setParamMapStyles(String paramMapStyles) {
		this.paramMapStyles = paramMapStyles;
	}

	public void setEmalAndMessageService(
			EmalAndMessageService emalAndMessageService) {
		this.emalAndMessageService = emalAndMessageService;
	}

	public String getParamMaplistcounter() {
		return paramMaplistcounter;
	}

	public void setParamMaplistcounter(String paramMaplistcounter) {
		this.paramMaplistcounter = paramMaplistcounter;
	}

	public String getParamMaplistIn() {
		return paramMaplistIn;
	}

	public void setParamMaplistIn(String paramMaplistIn) {
		this.paramMaplistIn = paramMaplistIn;
	}

	public ShoveBorrowTypeService getShoveBorrowTypeService() {
		return shoveBorrowTypeService;
	}

	public ShoveBorrowStyleService getShoveBorrowStyleService() {
		return shoveBorrowStyleService;
	}

	public ShoveBorrowAmountTypeService getShoveBorrowAmountTypeService() {
		return shoveBorrowAmountTypeService;
	}

	public EmalAndMessageService getEmalAndMessageService() {
		return emalAndMessageService;
	}

}
