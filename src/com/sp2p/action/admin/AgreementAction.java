package com.sp2p.action.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.web.action.BasePageAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.PublicModelService;

/**
 * 协议Action
 * 
 * @author zhongchuiqing
 * 
 */
@SuppressWarnings("unchecked")
public class AgreementAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(AgreementAction.class);
	private PublicModelService agreementService;

	public PublicModelService getAgreementService() {
		return agreementService;
	}

	public void setAgreementService(PublicModelService agreementService) {
		this.agreementService = agreementService;
	}

	/**
	 * 更新初始化，根据Id获取信息管理信息详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String AgreementInit() throws Exception {
		Integer agreementType = request.getInt("agreementType", -1);
		Integer provisionType = request.getInt("provisionType", -1);
		int type = request.getInt("type", -1);
		try {
			paramMap = agreementService.getAgreementDetail(agreementType,
					provisionType);
			request().setAttribute("type", type);
			if (provisionType == 1) {
				return SUCCESS;
			} else {
				return "sercret";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 更新信息管理
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateAgreement() throws Exception {
		Integer agreementType = Convert.strToInt(paramMap.get("agreementType"),
				0);
		Integer provisionType = Convert.strToInt(paramMap.get("provisionType"),
				0);
		String title = paramMap.get("title");
		String content = paramMap.get("content");
		int type = request.getInt("type", -1);
		String message = "更新失败";
		try {

			long result = agreementService.updateAgreement(agreementType,
					provisionType, content, title);
			if (result > 0) {
				message = "更新成功";

			}
			request().setAttribute("type", type);
			request().setAttribute("message", message);
			if (provisionType == 1) {
				Admin admin = (Admin) session().getAttribute(
						IConstants.SESSION_ADMIN);
				operationLogService.addOperationLog("t_agreement", admin
						.getUserName(), IConstants.UPDATE, admin.getLastIP(),
						0, "更新协议内容", 2);
				return SUCCESS;
			} else {
				return "sercret";
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}
}
