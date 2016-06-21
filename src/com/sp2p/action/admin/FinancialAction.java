package com.sp2p.action.admin;import java.sql.SQLException;import java.util.List;import java.util.Map;import org.apache.commons.lang3.StringUtils;import com.shove.Convert;import com.shove.data.DataException;import com.shove.web.action.BasePageAction;import com.sp2p.service.UserService;import com.sp2p.service.ValidateService;@SuppressWarnings("unchecked")public class FinancialAction extends BasePageAction {	private static final long serialVersionUID = 1L;	private ValidateService validateService;	@SuppressWarnings("unused")	private UserService userService;	public void setUserService(UserService userService) {		this.userService = userService;	}	public void setValidateService(ValidateService validateService) {		this.validateService = validateService;	}	/**	 * 	 * 	 * @return	 * @throws DataException	 * @throws SQLException	 */	public String rechargeecordsInit() throws SQLException, DataException {		String types = request.getString("types");		request().setAttribute("types", types);		return SUCCESS;	}	/**	 * 	 * 	 * @return	 * @throws Exception	 */	public String rechargeecordsInfo() throws Exception {		// 资料审核状态		Integer auditStatus = Convert.strToInt(paramMap.get("auditStatus"), 1);		// 会员名称		String userName = Convert.strToStr(paramMap.get("userName"), null);		// 跟踪人		String serviceManName = Convert.strToStr(				paramMap.get("serviceManName"), null);		// 认证名称		String certificateName = Convert.strToStr(paramMap				.get("certificateName"), null);		// 真实名称		String realName = Convert.strToStr(paramMap.get("realName"), null);		validateService.queryBaseValidata(pageBean, userName, realName,				auditStatus, certificateName, serviceManName);		return SUCCESS;	}	/**	 * 	 * 	 * @return	 * @throws Exception	 */	public String querynewUserCheck() throws Exception {		// 会员名称		String userName = Convert.strToStr(paramMap.get("userName"), null);		String userType = Convert.strToStr(paramMap.get("userType"), null);		List<Map<String, Object>> map = null;		Map<String, String> distributemap = null;		map = validateService.queryServiceNameByI();		if (map != null && map.size() > 0) {			request().setAttribute("map", map);		}		validateService.querynewUserCheck(pageBean, userName,userType);// 显示所有新用户分配资料		// 统计有多少个新用户待分配		distributemap = validateService.querydistribute();// 统计总的统计新用户未分配的的个数		request().setAttribute("distributemap", distributemap);		return SUCCESS;	}		/**	 *功能：理财师审核-查找页	 * @return	 */	public String financeAudit(){				return SUCCESS;	}			/**	 *功能：理财师审核-查找审核数据	 * @return	 */	public String queryFinanceAudit(){//		Map map = new java.util.HashMap();//		validateService.queryFinanceAudit(map, pageBean);		List list = validateService.commonQuery(pageBean, paramMap, "advert.queryFinanceAudit");		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();		request().setAttribute("pageNum", pageNum);		return SUCCESS;	}		/**	 *功能：确认审核通过	 * @return	 */	public String auditFinancial(){		String id = paramMap.get("id");		if (StringUtils.isEmpty(id)){			return null;		}		String pass = paramMap.get("pass");		Map map = new java.util.HashMap();		map.put("id", Integer.parseInt(id));		if (pass.equals("1")){			validateService.commonUpdate("advert.auditFinancial", map);		} else if (pass.equals("0")){			validateService.commonUpdate("advert.auditFinancialNo", map);		} else if (pass.equals("3")){//降级			validateService.commonUpdate("advert.auditFinancialDegrade", map);		}				return SUCCESS;	}		/**	 * 升级条件查看	 */	public String queryAuditCondition(){		long userId = Convert.strToLong(request("id"), -1L);		String level = request("level");				request().setAttribute("level", level);		return SUCCESS;	}}