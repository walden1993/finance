package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.shove.Convert;
import com.shove.config.GopayConfig;
import com.shove.config.IPayConfig;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.FundManagementService;

public class AccountPaymentAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(AfterCreditManageAction.class);
	/* private AccountPaymentService accountPaymentService; */
	private FundManagementService fundManagementService;

	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}

	public void setFundManagementService(
			FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	/**
	 * 查询初始化
	 * 
	 * @return
	 */
	public String queryAccountPayInit() {

		return SUCCESS;
	}

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String queryAccountPayInfo() {
		try {
			fundManagementService.queryAccountPaymentPage(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);

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
	@SuppressWarnings("unchecked")
	public String updateAccountPayInit() {
		String nid = Convert.strToStr(request.getString("nid"), "");
		try {
			paramMap = fundManagementService.queryAccountPaymentById(nid);
			String config = paramMap.get("config");
			if (StringUtils.isNotBlank(config)) {
				Map<String, String> map = (Map<String, String>) JSONObject
						.toBean(JSONObject.fromObject(config), HashMap.class);
				if (map != null) {
					paramMap.putAll(map);
				}
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
	 * @throws Exception
	 */
	public String updateAccountPay() throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String name = Convert.strToStr(paramMap.get("name"), "");
			String litpic = Convert.strToStr(paramMap.get("litpic"), "");
			int orders = Convert.strToInt(paramMap.get("orders"), -1);
			String description = Convert.strToStr(request.getString("paramMap.description")+ "", "");
			long id = Convert.strToLong(paramMap.get("id"), -1L);
			String merchantID = Convert
					.strToStr(paramMap.get("merchantID"), "");
			String virCardNoIn = Convert.strToStr(paramMap.get("virCardNoIn"),
					"");
			String VerficationCode = Convert.strToStr(paramMap
					.get("VerficationCode"), "");
			String privatekey = Convert
					.strToStr(paramMap.get("privatekey"), "");
			String customerID = Convert
					.strToStr(paramMap.get("customerID"), "");
			String nid = Convert.strToStr(paramMap.get("nid"), "");
			if ("IPS".equals(nid)) {
				map.put("customerID", customerID);
				map.put("privatekey", privatekey);
			}
			if ("gopay".equals(nid)) {
				map.put("virCardNoIn", virCardNoIn);
				map.put("VerficationCode", VerficationCode);
				map.put("merchantID", merchantID);
			}
			String json = JSONObject.fromObject(map).toString();
			long result = fundManagementService.updateAccountPaymentPage(id,
					name, litpic, json, description, orders);
			if (result > 0) {
				if ("IPS".equals(nid)) {
					IPayConfig.ipay_mer_code = customerID;
					IPayConfig.ipay_certificate = privatekey;
				}
				if ("gopay".equals(nid)) {
					GopayConfig.gopay_virCardNoIn = virCardNoIn;
					GopayConfig.gopay_verficationCode = VerficationCode;
					GopayConfig.gopay_merchantID = merchantID;
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_account_payment", admin
				.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
				"修改支付类型", 2);
		return SUCCESS;
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delteAccountPay() throws Exception {
		long id = Convert.strToLong(request.getString("id"), -1);
		long status = Convert.strToLong(request.getString("status"), 1);
		long result = -1L;
		try {
			result = fundManagementService.deleteAccountPaymentPage(id, status);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		if (result > 0) {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_account_payment", admin
					.getUserName(), IConstants.DELETE, admin.getLastIP(), 0,
					"删除id为" + id + "的支付类型", 2);
			return SUCCESS;
		} else
			return INPUT;
	}

}
