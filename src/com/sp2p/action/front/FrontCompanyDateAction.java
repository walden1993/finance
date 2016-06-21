package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.admin.UserAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.UserService;

@SuppressWarnings("unchecked")
public class FrontCompanyDateAction extends BasePageAction {

	public static Log log = LogFactory.getLog(UserAction.class);
	private static final long serialVersionUID = 1L;

	public UserService userService;
	
	//根据id查询企业用户信息
	public String queryCompayData() throws SQLException, DataException{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String from = getFrom();
		if (from == null) {
			from = request.getString("from");
		}
		String btype = getBtype();

		if (from != null) {
			request().setAttribute("from", from);
		}
		if (btype != null) {
			request().setAttribute("btype", btype);
		}
		User user = (User) session("user");
		paramMap=userService.queryCompayById(user.getId());
		Map<String,String> map=new HashMap<String, String>();
		String mobilePhone="";
		//根据id查询用户信息
		try {
			map=userService.queryUserById(user.getId());
			mobilePhone=map.get("mobilePhone").toString();
			String linkman_mobile= paramMap.get("linkman_mobile").toString();
			if(linkman_mobile==null || linkman_mobile.equals("")){
				paramMap.put("linkman_mobile", mobilePhone);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(paramMap==null){
			return "addCompany";
		}else{
			return "updateCompany";
		}
		
	}
	private String from;
	private String btype;
	public void setFrom(String from) {
		this.from = from;
	}


	public void setBtype(String btype) {
		this.btype = btype;
	}


	//根据id查询企业用户信息---企业中心
	public String queryCompayData1() throws SQLException, DataException{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
	
		// --------modify by houli
		String from = getFrom();
		if (from == null) {
			from = request.getString("from");
		}
		String btype = getBtype();

		if (from != null) {
			request().setAttribute("from", from);
		}
		if (btype != null) {
			request().setAttribute("btype", btype);
		}
			
		
		
		User user = (User) session("user");
		paramMap=userService.queryCompayById(user.getId());//查询企业用户详细信息
//		long userid=user.getId();
//		try {
//			int isApplyPro=Convert.strToInt(userService.queryUserById(userid).get("isApplyPro"), 1);
//			request().setAttribute("isApplyPro", isApplyPro);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//判断企业用户是否已经填写了企业基本信息
		String flag = "";
        if(paramMap!=null&&paramMap.size()>0){//用户基本资料有数据但是不一定是已经填写了基本资料信息 还有可能是上传了企业头像
        	if(!StringUtils.isBlank(paramMap.get("address"))){//不为空
        	 flag = "1";
        	}else{
        	 flag = "2";
        	}
        }else{
        	 flag = "2";
        }
        request().setAttribute("flag", flag);
		Map<String,String> map=new HashMap<String, String>();
		String mobilePhone="";
		//根据id查询用户信息
		try {
			map=userService.queryUserById(user.getId());
			mobilePhone=map.get("mobilePhone").toString();
			String linkman_mobile= paramMap.get("linkman_mobile").toString();
			if(linkman_mobile==null || linkman_mobile.equals("")){
				paramMap.put("linkman_mobile", mobilePhone);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(paramMap != null &&  paramMap.size() > 0){
			orgName = paramMap.get("organization_name");
			request().setAttribute("pass", paramMap.get("auditStatus"));
			request().setAttribute("realName", orgName);
		}
		int authStep = user.getAuthStep();
		if(authStep < 2){
			request().setAttribute("person", "0");
		}else{
			request().setAttribute("person", "1");
		}
		if(paramMap==null){
			return "addCompany1";
		}else{
			return "updateCompany1";
		}
	}


public String getFrom() {
		return from;
	}


	public String getBtype() {
		return btype;
	}


		/**
	 * 前台借款模块填写企业基本信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public String addUserCompanyData() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session("user");

		JSONObject json = new JSONObject();

		Long orgId = user.getId();
		String organizationName = paramMap.get("organizationName");//企业全称
		String address = paramMap.get("address");
		Integer industory = Convert.strToInt(paramMap.get("industory"),
				-1);
		Integer companyType = Convert.strToInt(paramMap.get("companyType"), -1);
		Integer companyScale = Convert.strToInt(paramMap.get("companyScale"),
				-1);
		String foundDate = Convert.strToStr(paramMap.get("foundDate"), null);

		String legalPerson = paramMap.get("legalPerson");
		String regNum = Convert.strToStr(paramMap.get("regNum"), null);
																		
		String organizationCode = Convert.strToStr(
				paramMap.get("organizationCode"), null);
		String registeredCapital = Convert.strToStr(
				paramMap.get("registeredCapital"), null);

		String bankName = Convert.strToStr(paramMap.get("bankName"), null);
		String accountName = Convert.strToStr(paramMap.get("accountName"), null);
		String publicBankaccount = Convert.strToStr(
				paramMap.get("publicBankaccount"), null);
		String website = Convert.strToStr(paramMap.get("website"), null);
																			
		String phone = Convert.strToStr(paramMap.get("phone"), null);
																		
		String email = Convert.strToStr(paramMap.get("email"), null);
																		
		String linkmanName = Convert.strToStr(paramMap.get("linkmanName"), null);
		String linkmanMobile = paramMap.get("linkmanMobile");
		String companyDescription = paramMap.get("companyDescription");
																		
		String headJpg = paramMap.get("headJpg");

		
		if (StringUtils.isBlank(organizationName)) {
			json.put("success", false);
			json.put("msg", "请填写企业全称");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(address)) {
			json.put("success", false);
			json.put("msg", "请填写企业地址");
			JSONUtils.printObject(json);
			return null;
		}

		if (StringUtils.isBlank(foundDate)) {
			json.put("success", false);
			json.put("msg", "成立时间不能为空");
			JSONUtils.printObject(json);
			return null;
		}

	
		if (StringUtils.isBlank(legalPerson)) {
			json.put("success", false);
			json.put("msg", "请填写入法人代表");
			JSONUtils.printObject(json);
			return null;
		}

		
		if (StringUtils.isBlank(regNum)) {
			json.put("success", false);
			json.put("msg", "请填写工商注册号");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(organizationCode)) {
			json.put("success", false);
			json.put("msg", "请填写组织机构代码");
			JSONUtils.printObject(json);
			return null;
		}

		
		if (StringUtils.isBlank(registeredCapital)) {
			json.put("success", false);
			json.put("msg", "请填写注册资本");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(bankName)) {
			json.put("success", false);
			json.put("msg", "请填写企业开户行");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(accountName)) {
			json.put("success", false);
			json.put("msg", "请填写企业开户名称");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(publicBankaccount)) {
			json.put("success", false);
			json.put("msg", "请填写企业对公银行账户");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(website)) {
			json.put("success", false);
			json.put("msg", "请填写企业网址");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(phone)) {
			json.put("success", false);
			json.put("msg", "请填写联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(email)) {
			json.put("success", false);
			json.put("msg", "请填写邮件地址");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(address)) {
			json.put("success", false);
			json.put("msg", "请填写入所在地");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(linkmanName)) {
			json.put("success", false);
			json.put("msg", "请填写联系人姓名");
			JSONUtils.printObject(json);
			return null;
		}

		
		if (StringUtils.isBlank(linkmanMobile)) {
			json.put("success", false);
			json.put("msg", "请填写联系人手机");
			JSONUtils.printObject(json);
			return null;
		}

		if (StringUtils.isBlank(companyDescription)) {
			json.put("success", false);
			json.put("msg", "请填写公司简介");
			JSONUtils.printObject(json);
			return null;
		}

		
		if (StringUtils.isBlank(headJpg)) {
			json.put("success", false);
			json.put("msg", "请上传公司头像");
			JSONUtils.printObject(json);
			return null;
		}

		

		long personId = -1L;
		// 判断是否是投资人填写个人资料

		personId= userService.addUserCompanyData(orgId, organizationName, address, industory, 
				companyType, companyScale, foundDate, legalPerson, regNum, organizationCode,
				registeredCapital, bankName, accountName, publicBankaccount, website, phone,
				email, linkmanName, linkmanMobile, companyDescription, headJpg);
		
	
		if (personId > 0) {
			json.put("success", true);
			JSONUtils.printObject(json);
			return null;
		
		}else{
			json.put("success", false);
			json.put("msg", "添加失败");
			JSONUtils.printObject(json);
			return null;
		}

	}
	
	
	/**
	 * 前台借款模块填写企业基本信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public String updateUserCompanyData() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session("user");

		JSONObject json = new JSONObject();

		Long orgId = user.getId();
		Long id = Convert.strToLong(paramMap.get("id"), -1);
		String organizationName = paramMap.get("organizationName");//企业全称
		String address = paramMap.get("address");
		Integer industory = Convert.strToInt(paramMap.get("industory"),
				-1);
		Integer companyType = Convert.strToInt(paramMap.get("companyType"), -1);
		Integer companyScale = Convert.strToInt(paramMap.get("companyScale"),
				-1);
		String foundDate = Convert.strToStr(paramMap.get("foundDate"), null);

		String legalPerson = paramMap.get("legalPerson");
		String regNum = Convert.strToStr(paramMap.get("regNum"), null);
																		
		String organizationCode = Convert.strToStr(
				paramMap.get("organizationCode"), null);
		String registeredCapital = Convert.strToStr(
				paramMap.get("registeredCapital"), null);

		String bankName = Convert.strToStr(paramMap.get("bankName"), null);
		String accountName = Convert
				.strToStr(paramMap.get("accountName"), null);
		String publicBankaccount = Convert.strToStr(
				paramMap.get("publicBankaccount"), null);
		String website = Convert.strToStr(paramMap.get("website"), null);
																			
		String phone = Convert.strToStr(paramMap.get("phone"), null);
																		
		String email = Convert.strToStr(paramMap.get("email"), null);
																		
		String linkmanName = Convert.strToStr(paramMap.get("linkmanName"), null);
		String linkmanMobile = paramMap.get("linkmanMobile");
		String companyDescription = paramMap.get("companyDescription");
																		
		String headJpg = paramMap.get("headJpg");

		if (StringUtils.isBlank(address)) {
			json.put("success", false);
			json.put("msg", "请填写企业地址");
			JSONUtils.printObject(json);
			return null;
		}

		if (StringUtils.isBlank(foundDate)) {
			json.put("success", false);
			json.put("msg", "成立时间不能为空");
			JSONUtils.printObject(json);
			return null;
		}

	
		if (StringUtils.isBlank(legalPerson)) {
			json.put("success", false);
			json.put("msg", "请填写入法人代表");
			JSONUtils.printObject(json);
			return null;
		}

		
		if (StringUtils.isBlank(regNum)) {
			json.put("success", false);
			json.put("msg", "请填写工商注册号");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(organizationCode)) {
			json.put("success", false);
			json.put("msg", "请填写组织机构代码");
			JSONUtils.printObject(json);
			return null;
		}

		/*
		if (StringUtils.isBlank(registeredCapital)) {
			json.put("success", false);
			json.put("msg", "请填写注册资本");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(bankName)) {
			json.put("success", false);
			json.put("msg", "请填写企业开户行");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(accountName)) {
			json.put("success", false);
			json.put("msg", "请填写企业开户名称");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(publicBankaccount)) {
			json.put("success", false);
			json.put("msg", "请填写企业对公银行账户");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(website)) {
			json.put("success", false);
			json.put("msg", "请填写企业网址");
			JSONUtils.printObject(json);
			return null;
		}
		*/
		if (StringUtils.isBlank(phone)) {
			json.put("success", false);
			json.put("msg", "请填写联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(email)) {
			json.put("success", false);
			json.put("msg", "请填写邮件地址");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(address)) {
			json.put("success", false);
			json.put("msg", "请填写入所在地");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(linkmanName)) {
			json.put("success", false);
			json.put("msg", "请填写联系人姓名");
			JSONUtils.printObject(json);
			return null;
		}

		
		if (StringUtils.isBlank(linkmanMobile)) {
			json.put("success", false);
			json.put("msg", "请填写联系人手机");
			JSONUtils.printObject(json);
			return null;
		}

		if (StringUtils.isBlank(companyDescription)) {
			json.put("success", false);
			json.put("msg", "请填写公司简介");
			JSONUtils.printObject(json);
			return null;
		}

		
		if (StringUtils.isBlank(headJpg)) {
			json.put("success", false);
			json.put("msg", "请上传公司头像");
			JSONUtils.printObject(json);
			return null;
		}

		

		long personId = -1L;
		
		//更新用户企业信息
		personId= userService.updateUserCompanyData(id, orgId, organizationName, address, 
				industory, companyType, companyScale, foundDate, legalPerson, regNum, 
				organizationCode, registeredCapital, bankName, accountName, publicBankaccount,
				website, phone, email, linkmanName, linkmanMobile, companyDescription, headJpg);
		
	
		if (personId > 0) {
			//添加成功后判断认证流程authStep
//			Map<String,String> map=userService.queryUserById(orgId);
			int authStep=user.getAuthStep();
//			System.out.println("user.getAuthStep()="+user.getAuthStep());
//			session().getAttribute("authStep");
//			System.out.println("session().getAttribute('authStep')="+session().getAttribute("authStep"));
			if(authStep<4){//如果流程小于等于1,则进入下一个流程
				authStep=4;//跳过第二步工作认证
				long aa = -1L;
				aa=userService.updateAuthStepByid(orgId,authStep);
				if(aa>0){
					user.setAuthStep(authStep);
					session().setAttribute("user", user);
				}
			}
			json.put("success", true);
			JSONUtils.printObject(json);
			return null;
		
		}else{
			json.put("success", false);
			json.put("msg", "添加失败");
			JSONUtils.printObject(json);
			return null;
		}

	}
	
	/**
	 * 跳转到上传页面
	 * @throws Exception 
	 */
	public String jumpPassDatapage() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		Long userId = -1l;
		 Map<String,String> pictruemap = null;
		List<Map<String, Object>> basepictur = null;
		List<Map<String, Object>> selectpictur = null;		String from = request.getString("from");
		String btype = request.getString("btype");
		// -------------
		if (user != null) {
			if (from == null || from.equals("")) {
				// 获取用户认证进行的步骤
				if (user.getAuthStep() == 1) {
					// 企业信息认证步骤
					return "queryCompayData";
				}else if (user.getAuthStep() == 3) {
					// VIP申请认证步骤
					return "quervipData";
				}
				// ---------add by houli
				else if (user.getAuthStep() == 5
						&& (btype != null && !btype.equals(""))) {
					return "jumpOther";
				}
			} else {// 净值借款跟秒还借款操作步骤
				// 获取用户认证进行的步骤
				if (user.getAuthStep() == 1) {
					// 企业信息认证步骤
					return "queryCompayData";
				}

				if (user.getVipStatus() == IConstants.UNVIP_STATUS) {
					return "quervipData";
				}
			}
			userId = user.getId();

			basepictur = userService.queryCompanyBasePicture(userId);// 五大基本
			selectpictur = userService.queryCompanySelectPicture(userId);// 可选
			request().setAttribute("basepictur", basepictur);
			request().setAttribute("selectpictur", selectpictur);

			return SUCCESS;
		}else{
			return LOGIN;
		}
		
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
