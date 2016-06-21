package com.sp2p.action.admin;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.UserAdminService;


// l.x.z
@SuppressWarnings("serial")
public class UserAdminAction extends BaseFrontAction {
	public static Log log = LogFactory.getLog(UserAdminAction.class);
	private UserAdminService userAdminService;
	private RegionService regionService;
	Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);//添加操作日志用户获取

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setUserAdminService(UserAdminService userAdminService) {
		this.userAdminService = userAdminService;
	}

	public String updateUserBaseDataAdmin() throws Exception {//修改个人用户基本资料
		JSONObject json = new JSONObject();

		Long userId = Convert.strToLong(paramMap.get("ui"), -1L);
		if (userId == -1L) {
			json.put("msg", "用户为空");
			JSONUtils.printObject(json);
			return null;
		}
		String realName = Convert.strToStr(paramMap.get("realName"), "");// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请正确填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}

		String idNo = Convert.strToStr(paramMap.get("idNo"), "");// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请正确身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len) {
			if (18 == len) {
			} else {
				json.put("msg", "请正确身份证号码");
				JSONUtils.printObject(json);
				return null;
			}
		}

		String cellPhone = Convert.strToStr(paramMap.get("cellPhone"), "");// 手机号码
		if (StringUtils.isBlank(cellPhone)) {
			json.put("msg", "请正确填写手机号码");
			JSONUtils.printObject(json);
			return null;
		} else if (cellPhone.length() < 9 || cellPhone.length() > 15) {
			json.put("msg", "手机号码长度不对");
			JSONUtils.printObject(json);
			return null;
		}

		String sex = Convert.strToStr(paramMap.get("sex"), null);// 性别(男 女)
		if (StringUtils.isBlank(sex)) {
			json.put("msg", "请正确填写性别");
			JSONUtils.printObject(json);
			return null;
		}

		String birthday = Convert.strToStr(paramMap.get("birthday").toString(),
				null);// 出生日期
		if (StringUtils.isBlank(birthday)) {
			json.put("msg", "请正确填写出生日期");
			JSONUtils.printObject(json);
			return null;
		}

		String highestEdu = Convert.strToStr(paramMap.get("highestEdu"), null);// 最高学历
		if (StringUtils.isBlank(highestEdu)) {
			json.put("msg", "请正确填写最高学历");
			JSONUtils.printObject(json);
			return null;
		}

		String eduStartDay = Convert.strToStr(paramMap.get("eduStartDay")
				.toString(), null);// 入学年份
		if (StringUtils.isBlank(eduStartDay)) {
			json.put("msg", "请正确填写入学年份");
			JSONUtils.printObject(json);
			return null;
		}

		String school = Convert.strToStr(paramMap.get("school"), null);// 毕业院校
		if (StringUtils.isBlank(school)) {
			json.put("msg", "请正确填写入毕业院校");
			JSONUtils.printObject(json);
			return null;
		}

		String maritalStatus = Convert.strToStr(paramMap.get("maritalStatus"),
				null);// 婚姻状况(已婚 未婚)
		if (StringUtils.isBlank(maritalStatus)) {
			json.put("msg", "请正确填写入婚姻状况");
			JSONUtils.printObject(json);
			return null;
		}

		String hasChild = Convert.strToStr(paramMap.get("hasChild"), null);// 有无子女(有
																			// 无)

		if (StringUtils.isBlank(hasChild)) {
			json.put("msg", "请正确填写入有无子女");
			JSONUtils.printObject(json);
			return null;
		}
		String hasHourse = Convert.strToStr(paramMap.get("hasHourse"), null);// 是否有房(有
																				// 无)
		if (StringUtils.isBlank(hasHourse)) {
			json.put("msg", "请正确填写入是否有房");
			JSONUtils.printObject(json);
			return null;
		}

		String hasHousrseLoan = Convert.strToStr(
				paramMap.get("hasHousrseLoan"), null);// 有无房贷(有 无)
		if (StringUtils.isBlank(hasHousrseLoan)) {
			json.put("msg", "请正确填写入有无房贷");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCar = Convert.strToStr(paramMap.get("hasCar"), null);// 是否有车
																		// (有 无)
		if (StringUtils.isBlank(hasCar)) {
			json.put("msg", "请正确填写入是否有车");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCarLoan = Convert.strToStr(paramMap.get("hasCarLoan"), null);// 有无车贷
																				// (有
																				// 无)
		if (StringUtils.isBlank(hasCarLoan)) {
			json.put("msg", "请正确填写入有无车贷");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlacePro = Convert.strToLong(paramMap.get("nativePlacePro"),
				-1);// 籍贯省份(默认为-1)
		if (StringUtils.isBlank(nativePlacePro.toString())) {
			json.put("msg", "请正确填写入籍贯省份");
			JSONUtils.printObject(json);
			return null;
		}
		Long nativePlaceCity = Convert.strToLong(paramMap
				.get("nativePlaceCity"), -1);// 籍贯城市 (默认为-1)
		if (StringUtils.isBlank(nativePlaceCity.toString())) {
			json.put("msg", "请正确填写入籍贯城市");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlacePro = Convert.strToLong(paramMap
				.get("registedPlacePro"), -1);// 户口所在地省份(默认为-1)
		if (StringUtils.isBlank(registedPlacePro.toString())) {
			json.put("msg", "请正确填写入户口所在地省份");
			JSONUtils.printObject(json);
			return null;
		}

		Long registedPlaceCity = Convert.strToLong(paramMap
				.get("registedPlaceCity"), -1);// 户口所在地城市(默认为-1)

		if (StringUtils.isBlank(registedPlaceCity.toString())) {
			json.put("msg", "请正确填写入户口所在地城市");
			JSONUtils.printObject(json);
			return null;
		}

		String address = Convert.strToStr(paramMap.get("address"), "");// 所在地
		if (StringUtils.isBlank(address)) {
			json.put("msg", "请正确填写入所在地");
			JSONUtils.printObject(json);
			return null;
		}

		String telephone = Convert.strToStr(paramMap.get("telephone"), "");// 居住电话
		if (StringUtils.isBlank(telephone)) {
			json.put("msg", "请正确填写入你的家庭电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (!StringUtils.isBlank(telephone))
			if (telephone.trim().length() < 7 || telephone.trim().length() > 13) {
				json.put("msg", "你的家庭电话输入长度不对");
				JSONUtils.printObject(json);
				return null;
			}

		/* 用户头像 */
		String personalHead = paramMap.get("personalHead");// 个人头像 (默认系统头像)
		if (StringUtils.isBlank(personalHead)) {
			personalHead = null;
			json.put("msg", "请正确上传你的个人头像");
			JSONUtils.printObject(json);
			return null;
		}
		if(personalHead.contains("../")){
			personalHead = personalHead.substring(3, personalHead.length());
		}
		long personId = -1L;

		personId = userAdminService.updateUserBaseData(realName, cellPhone,
				sex, birthday, highestEdu, eduStartDay, school, maritalStatus,
				hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
				nativePlacePro, nativePlaceCity, registedPlacePro,
				registedPlaceCity, address, telephone, personalHead, userId,
				idNo);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);

		if (personId > 0) {
			json.put("msg", "保存成功");
			operationLogService.addOperationLog("t_person",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "管理员修改用户基础数据成功", 2);
			JSONUtils.printObject(json);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			operationLogService.addOperationLog("t_person",
					admin.getUserName(), IConstants.UPDATE, admin.getLastIP(),
					0, "管理员修改用户基础数据失败", 2);
			// 失败
			JSONUtils.printObject(json);
			return null;
		}

	}

	public String updateUserWorkDataAdmin() throws Exception {//修改个人用户工作信息
		JSONObject json = new JSONObject();
		Long userId = Convert.strToLong(paramMap.get("uid"), -1L);
		if (userId == -1L) {
			json.put("msg", "用户为空");
			JSONUtils.printObject(json);
			return null;
		}
		String orgName = paramMap.get("orgName");
		if (StringUtils.isBlank(orgName)) {
			json.put("msg", "请正确填写公司名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > orgName.length() || 50 < orgName.length()) {
			json.put("msg", "公司名字长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}

		String occStatus = paramMap.get("occStatus");
		if (StringUtils.isBlank(occStatus)) {
			json.put("msg", "请填写职业状态");
			JSONUtils.printObject(json);
			return null;
		}
		Long workPro = Convert.strToLong(paramMap.get("workPro"), -1L);
		if (workPro == null || workPro == -1L) {
			json.put("msg", "请填写工作城市省份");
			JSONUtils.printObject(json);
			return null;
		}
		Long workCity = Convert.strToLong(paramMap.get("workCity"), -1L);
		if (workCity == null || workCity == -1L) {
			json.put("msg", "请填写工作城市");
			JSONUtils.printObject(json);
			return null;
		}
		String companyType = paramMap.get("companyType");
		if (StringUtils.isBlank(companyType)) {
			json.put("msg", "请填写公司类别");
			JSONUtils.printObject(json);
			return null;
		}
		String companyLine = paramMap.get("companyLine");
		if (StringUtils.isBlank(companyLine)) {
			json.put("msg", "请填写公司行业");
			JSONUtils.printObject(json);
			return null;
		}
		String companyScale = paramMap.get("companyScale");
		if (StringUtils.isBlank(companyScale)) {
			json.put("msg", "请填写公司规模");
			JSONUtils.printObject(json);
			return null;
		}
		String job = paramMap.get("job");
		if (StringUtils.isBlank(job)) {
			json.put("msg", "请填写职位");
			JSONUtils.printObject(json);
			return null;
		}
		String monthlyIncome = paramMap.get("monthlyIncome");
		if (StringUtils.isBlank(monthlyIncome)) {
			json.put("msg", "请填写月收入");
			JSONUtils.printObject(json);
			return null;
		}
		String workYear = paramMap.get("workYear");
		if (StringUtils.isBlank(workYear)) {
			json.put("msg", "请填写现单位工作年限");
			JSONUtils.printObject(json);
			return null;
		}
		String companyTel = paramMap.get("companyTel");
		if (StringUtils.isBlank(companyTel)) {
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (companyTel.trim().length() < 7 || companyTel.trim().length() > 13) {
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}

		String workEmail = paramMap.get("workEmail");
		if (StringUtils.isBlank(workEmail)) {
			json.put("msg", "请填写工作邮箱");
			JSONUtils.printObject(json);
			return null;
		}
		String companyAddress = paramMap.get("companyAddress");
		if (StringUtils.isBlank(companyAddress)) {
			json.put("msg", "请填写公司地址");
			JSONUtils.printObject(json);
			return null;
		}
		String directedName = paramMap.get("directedName");
		if (StringUtils.isBlank(directedName)) {
			json.put("msg", "请填写直系人姓名");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > directedName.length() || 50 < directedName.length()) {
			json.put("msg", "直系人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}

		String directedRelation = paramMap.get("directedRelation");
		if (StringUtils.isBlank(directedRelation)) {
			json.put("msg", "请填写直系人关系");
			JSONUtils.printObject(json);
			return null;
		}
		String directedTel = paramMap.get("directedTel");
		if (StringUtils.isBlank(directedTel)) {
			json.put("msg", "请真确填写直系人电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (!StringUtils.isNumeric(directedTel)) {
			json.put("msg", "请真确填写直系人电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (directedTel.trim().length() != 11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}

		String otherName = paramMap.get("otherName");
		if (StringUtils.isBlank(workPro.toString())) {
			json.put("msg", "请填写其他人姓名");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > otherName.length() || 50 < otherName.length()) {
			json.put("msg", "其他人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}

		String otherRelation = paramMap.get("otherRelation");
		if (StringUtils.isBlank(otherRelation)) {
			json.put("msg", "请填写其他人关系");
			JSONUtils.printObject(json);
			return null;
		}
		String otherTel = paramMap.get("otherTel");
		if (StringUtils.isBlank(otherTel)) {
			json.put("msg", "请正确填写其他人联系电话");
			JSONUtils.printObject(json);
			return null;
		}

		if (!StringUtils.isNumeric(otherTel)) {
			json.put("msg", "请正确填写其他人联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (otherTel.trim().length() != 11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}

		String moredName = paramMap.get("moredName");
		if (StringUtils.isBlank(moredName)) {
			json.put("msg", "morename");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > moredName.length() || 50 < moredName.length()) {
			json.put("msg", "更多联系人姓名长度为不小于2和大于50");
			JSONUtils.printObject(json);
			return null;
		}
		String moredRelation = paramMap.get("moredRelation");
		if (StringUtils.isBlank(moredRelation)) {
			json.put("msg", "morereation");
			JSONUtils.printObject(json);
			return null;
		}
		String moredTel = paramMap.get("moredTel");
		if (StringUtils.isBlank(moredTel)) {
			json.put("msg", "moretel");
			JSONUtils.printObject(json);
			return null;
		}
		if (!StringUtils.isNumeric(moredTel)) {
			json.put("msg", "moretel");
			JSONUtils.printObject(json);
			return null;
		}
		if (moredTel.trim().length() != 11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		// 用户Id
		Long result = -1L;

		result = userAdminService.updateUserWorkData(orgName, occStatus,
				workPro, workCity, companyType, companyLine, companyScale, job,
				monthlyIncome, workYear, companyTel, workEmail, companyAddress,
				directedName, directedRelation, directedTel, otherName,
				otherRelation, otherTel, moredName, moredRelation, moredTel,
				userId);
		
		if (result > 0) {
			json.put("msg", "保存成功");
			operationLogService.addOperationLog("t_workauth", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"管理员修改用户工作资料成功", 2);
			JSONUtils.printObject(json);
			return null;
		} else {
			json.put("msg", "保存失败");
			operationLogService.addOperationLog("t_workauth", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"管理员修改用户工作资料失败", 2);
			JSONUtils.printObject(json);
			return null;
		}

	}
	
	/**
	 * 后台审核借款模块填写企业基本信息
	 * L.X.Z
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public String updateUserCompanyInfo() throws Exception {
		JSONObject json = new JSONObject();
		Long id = Convert.strToLong(paramMap.get("id"), -1);
		Long orgId = Convert.strToLong(paramMap.get("userId"), -1);
		String organizationName = paramMap.get("orgName");//企业全称
		String address = paramMap.get("address");
		Integer industory = Convert.strToInt(paramMap.get("industory"),-1);
		Integer companyType = Convert.strToInt(paramMap.get("companyType"), -1);
		Integer companyScale = Convert.strToInt(paramMap.get("companyScale"), -1);
		String foundDate = Convert.strToStr(paramMap.get("foundDate"), null);
		String legalPerson = paramMap.get("legalPerson");
		String regNum = Convert.strToStr(paramMap.get("regNum"), null);
		String organizationCode = Convert.strToStr(paramMap.get("organizationCode"), null);
		String registeredCapital = Convert.strToStr(paramMap.get("registeredCapital"), null);
		String bankName = Convert.strToStr(paramMap.get("bankName"), null);
		String accountName = Convert.strToStr(paramMap.get("accountName"), null);
		String publicBankaccount = Convert.strToStr(paramMap.get("publicBankaccount"), null);
		String website = Convert.strToStr(paramMap.get("website"), null);
		String phone = Convert.strToStr(paramMap.get("phone"), null);
		String email = Convert.strToStr(paramMap.get("email"), null);
		String linkmanName = Convert.strToStr(paramMap.get("linkmanName"), null);
		String linkmanMobile = paramMap.get("linkmanMobile");
		String companyDescription = paramMap.get("companyDescription");
		String headJpg = paramMap.get("headJpg");
		if (StringUtils.isBlank(address)) {
			json.put("msg", "请填写企业地址");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(foundDate)) {
			json.put("msg", "成立时间不能为空");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(legalPerson)) {
			json.put("msg", "请填写入法人代表");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(regNum)) {
			json.put("msg", "请填写工商注册号");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(organizationCode)) {
			json.put("msg", "请填写组织机构代码");
			JSONUtils.printObject(json);
			return null;
		}
//		注释部分可为空
		
//		if (StringUtils.isBlank(registeredCapital)) {
//			json.put("success", false);
//			json.put("msg", "请填写注册资本");
//			JSONUtils.printObject(json);
//			return null;
//		}
//		
//		if (StringUtils.isBlank(bankName)) {
//			json.put("success", false);
//			json.put("msg", "请填写企业开户行");
//			JSONUtils.printObject(json);
//			return null;
//		}
//		
//		if (StringUtils.isBlank(accountName)) {
//			json.put("success", false);
//			json.put("msg", "请填写企业开户名称");
//			JSONUtils.printObject(json);
//			return null;
//		}
//		
//		if (StringUtils.isBlank(publicBankaccount)) {
//			json.put("success", false);
//			json.put("msg", "请填写企业对公银行账户");
//			JSONUtils.printObject(json);
//			return null;
//		}
//		
//		if (StringUtils.isBlank(website)) {
//			json.put("success", false);
//			json.put("msg", "请填写企业网址");
//			JSONUtils.printObject(json);
//			return null;
//		}
		
		if (StringUtils.isBlank(phone)) {
			json.put("msg", "请填写联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(email)) {
			json.put("msg", "请填写邮件地址");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(linkmanName)) {
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
			json.put("msg", "请填写公司简介");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(headJpg)) {
			json.put("msg", "请上传公司头像");
			JSONUtils.printObject(json);
			return null;
		}
		
		long personId = -1L;
		//更新用户企业信息
		personId= userAdminService.updateUserCompanyData(id, orgId, organizationName, address, 
				industory, companyType, companyScale, foundDate, legalPerson, regNum, 
				organizationCode, registeredCapital, bankName, accountName, publicBankaccount,
				website, phone, email, linkmanName, linkmanMobile, companyDescription, headJpg);
		if (personId > 0) {
			json.put("msg", "保存成功");
			operationLogService.addOperationLog("t_orginfo", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"管理员修改企业用户基本资料成功", 2);
			JSONUtils.printObject(json);
			return null;
		
		}else{
			json.put("msg", "保存失败");
			operationLogService.addOperationLog("t_orginfo", admin
					.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
					"管理员修改企业用户基本资料失败", 2);
			JSONUtils.printObject(json);
			return null;
		}

	}
	

	public String ajaxqueryRegionadmin() throws Exception {
		Long parentId = request.getLong("parentId", -1);
		Integer regionType = request.getInt("regionType", -1);
		List<Map<String, Object>> list;
		try {
			list = regionService.queryRegionList(-1L, parentId, regionType);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		String jsonStr = JSONArray.toJSONString(list);
		JSONUtils.printStr(jsonStr);
		return null;
	}

}
