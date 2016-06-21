package com.sp2p.action.front;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.entity.User;
import com.sp2p.service.BeVipService;
import com.sp2p.service.IDCardValidateService;
import com.sp2p.service.RegionService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;

public class FrontMyInformation extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyInformation.class);
	private static final long serialVersionUID = 1L;

	private UserService userService;

	private RegionService regionService;
	private ValidateService validateService;
	  private BeVipService beVipService;
	   private IDCardValidateService iDCardValidateService;
	private SysparService sysparService;

	private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> cityList;
	private List<Map<String, Object>> areaList;
	private List<Map<String, Object>> regcityList;

	private long workPro = -1L;// 初始化省份默认值
	private long cityId = -1L;// 初始化话默认城市
	private long regPro = -1L;// 户口区域默认值
	private long regCity = -1L;// 户口区域默认值
	
	
	
    
    public SysparService getSysparService() {
        return sysparService;
    }

    public void setSysparService(SysparService sysparService) {
        this.sysparService = sysparService;
    }

    /**
	 * 查询个详细信息
	 * 
	 * @return
	 * @throws Exception
     */
	public String queryBaseData() throws Exception {
		
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
		
		Map<String,String> maps=userService.queryUserById(userId);
		Integer userType=Convert.strToInt(maps.get("userType"), 0);
		if(userType==2){
			return "companyBase";
		}else{
			int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
			request().setAttribute("isApplyPro", isApplyPro);
			String  birth = null;
			String rxedate = null;
			if (user != null) {
				map = userService.queryPersonById(user.getId());
				if (map != null && map.size() > 0) {
					workPro = Convert.strToLong(map.get("nativePlacePro")
							.toString(), -1L);
					cityId = Convert.strToLong(map.get("nativePlaceCity")
							.toString(), -1L);
					regPro = Convert.strToLong(map.get("registedPlacePro")
							.toString(), -1L);
					regCity =Convert.strToLong(map.get("registedPlaceCity")
							.toString(), -1L);
					String birthd = map.get("birthday"); 
				birth = Convert.strToStr(map.get("birthday"), null);
				rxedate = Convert.strToStr(map.get("eduStartDay"), null);
				if(birth!=null){
					birth = birth.substring(0, 10);
				}
				if(rxedate!=null){
					rxedate = rxedate.substring(0, 10);
				}
		
				}
			}else{
				return "login";
			}
			//判断用户是否已经填写了基本信息
			String flag = "";
	        if(map!=null&&map.size()>0){//用户基本资料有数据但是不一定是已经填写了基本资料信息 还有可能是上传了个人头像
	        	if(!StringUtils.isBlank(map.get("realName"))){//不为空
	        	 flag = "1";
	        	}else{
	        	 flag = "2";
	        	}
	        }else{
	        	 flag = "2";
	        }
	        request().setAttribute("flag", flag);
			provinceList = regionService.queryRegionList(-1L, 1L, 1);
			cityList = regionService.queryRegionList(-1L, workPro, 2);
			regcityList = regionService.queryRegionList(-1L, regPro, 2);
			request().setAttribute("map", map);
			request().setAttribute("provinceList", provinceList);
			request().setAttribute("cityList", cityList);
			request().setAttribute("regcityList", regcityList);
			request().setAttribute("birth", birth);
			request().setAttribute("rxedate", rxedate);
			request().setAttribute("realName", user.getRealName());

			String tab_type = request.getString("tab_type")==null?null:request.getString("tab_type");
			if(tab_type != null){
				request().setAttribute("tab_type", tab_type);
			}
			
			String yy = request.getString("yy")==null?null:request.getString("yy");
			if(yy != null){
				request().setAttribute("yy", yy);
			}
			Map<String,String> pmap = userService.queryPersonById(user.getId());
			if(pmap != null &&  pmap.size() > 0){
				request().setAttribute("pass", pmap.get("auditStatus"));
			}
			int authStep = user.getAuthStep();
			if(authStep < 2){
				request().setAttribute("person", "0");
			}else{
				request().setAttribute("person", "1");
			}
			request().setAttribute("ISDEMO", IConstants.ISDEMO);
				return SUCCESS; //返回个人资料详情
			
		}
		
	}

	//验证手机的唯一性
	public String cellphoneOnly() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String cellPhone = request.getString("cellphone");;
		Map<String,String> phonemap = null;
		JSONObject json = new JSONObject();
		phonemap = beVipService.queryIsPhone(cellPhone);
		if(phonemap!=null){
			json.put("msg", "手机已存在");
			JSONUtils.printObject(json);
			return null;
		}
		return null;
	}
	
	//添加或更新个人信息
	public String updateUserBaseData() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
			User user = (User) session("user");
		
		JSONObject json = new JSONObject();
		String realName = paramMap.get("realName");// 真实姓名
		if (StringUtils.isBlank(realName)) {
			json.put("msg", "请正确填写真实名字");
			JSONUtils.printObject(json);
			return null;
		} else if (2 > realName.length() || 20 < realName.length()) {
			json.put("msg", "真实姓名的长度为不小于2和大于20");
			JSONUtils.printObject(json);
			return null;
		}
		
		String idNo = paramMap.get("idNo");// 身份证号码
		long len = idNo.length();
		if (StringUtils.isBlank(idNo)) {
			json.put("msg", "请正确输入身份证号码");
			JSONUtils.printObject(json);
			return null;
		} else if (15 != len) {
			if (18 == len) {
			} else {
				json.put("msg", "请正确输入身份证号码");
				JSONUtils.printObject(json);
				return null;
			}
		}
		//验证身份证
		String iDresutl = "";
	//	iDresutl  =	iDCardValidateService.IDCardValidate(idNo);
		if(iDresutl!=""){
			json.put("msg", "身份证不合法");
			JSONUtils.printObject(json);
			return null;
		}
		String cellPhone = paramMap.get("cellPhone");// 手机号码
		//判断是否是手机注册用户
		String iscellPhone = paramMap.get("iscellPhone");
		if (StringUtils.isBlank(iscellPhone)) {
			if (StringUtils.isBlank(cellPhone)) {
				json.put("msg", "请正确填写手机号码");
				JSONUtils.printObject(json);
				return null;
			} else if (cellPhone.length() < 9 || cellPhone.length() > 15) {
				json.put("msg", "手机号码长度不对");
				JSONUtils.printObject(json);
				return null;
			}

			/**
			 * 判定用户是否已存在记录
			 */
			Map<String,String> pMap = null;
			
			pMap = beVipService.queryPUser(user.getId());
			//验证手机的唯一性
			Map<String,String> phonemap = null;
			phonemap = beVipService.queryIsPhone(cellPhone);
			
			if(pMap==null){
			
				if(phonemap!=null){
					json.put("msg", "手机已存在");
					JSONUtils.printObject(json);
					return null;
				}	
				
				   if(phonemap==null){	
						String phonecode=null;
						try {
							Object obje=session().getAttribute("phone");
							if(obje!=null){
								phonecode=obje.toString();
							}else{
								json.put("msg", "请输入正确的验证码");
								JSONUtils.printObject(json);
								return null;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
				
						 
						if(phonecode!=null){
							if(!phonecode.trim().equals(cellPhone.trim())){
								json.put("msg", "与获取验证码手机号不一致");
								JSONUtils.printObject(json);
								return null;
							}
							
						}
						//验证码
						String vilidataNum = paramMap.get("vilidataNum");
				        if(StringUtils.isBlank(vilidataNum)){
				        	json.put("msg","请填写验证码");
				        	JSONUtils.printObject(json);
							return null;
				        }
				        
				        String randomCode=null;
						Object objec=session().getAttribute("randomCode");
						if(objec!=null){
							randomCode=objec.toString();
						}else{
							json.put("msg","请输入正确的验证码");
				        	JSONUtils.printObject(json);
							return null;
						}
						if(randomCode!=null){
							if(!randomCode.trim().equals(vilidataNum.trim())){
								
								json.put("msg","请输入正确的验证码");
					        	JSONUtils.printObject(json);
								return null;
							}
							
						}
				
			}
		
		   }
			
		}

		String sex = paramMap.get("sex");// 性别(男 女)
		if (StringUtils.isBlank(sex)) {
			json.put("msg", "请正确填写性别");
			JSONUtils.printObject(json);
			return null;
		}

		String birthday = paramMap.get("birthday");// 出生日期
		if (StringUtils.isBlank(birthday)) {
			json.put("msg", "请正确填写出生日期");
			JSONUtils.printObject(json);
			return null;
		}

		String highestEdu = paramMap.get("highestEdu");// 最高学历
		if (StringUtils.isBlank(highestEdu)) {
			json.put("msg", "请正确填写最高学历");
			JSONUtils.printObject(json);
			return null;
		}

		String eduStartDay = paramMap.get("eduStartDay");// 入学年份
		if (StringUtils.isBlank(eduStartDay)) {
			json.put("msg", "请正确填写入学年份");
			JSONUtils.printObject(json);
			return null;
		}

		String school = paramMap.get("school");// 毕业院校
		if (StringUtils.isBlank(school)) {
			json.put("msg", "请正确填写入毕业院校");
			JSONUtils.printObject(json);
			return null;
		}

		String maritalStatus = paramMap.get("maritalStatus");// 婚姻状况(已婚 未婚)
		if (StringUtils.isBlank(maritalStatus)) {
			json.put("msg", "请正确填写入婚姻状况");
			JSONUtils.printObject(json);
			return null;
		}

		String hasChild = paramMap.get("hasChild");// 有无子女(有 无)

		if (StringUtils.isBlank(hasChild)) {
			json.put("msg", "请正确填写入有无子女");
			JSONUtils.printObject(json);
			return null;
		}
		String hasHourse = paramMap.get("hasHourse");// 是否有房(有 无)
		if (StringUtils.isBlank(hasHourse)) {
			json.put("msg", "请正确填写入是否有房");
			JSONUtils.printObject(json);
			return null;
		}

		String hasHousrseLoan = paramMap.get("hasHousrseLoan");// 有无房贷(有 无)
		if (StringUtils.isBlank(hasHousrseLoan)) {
			json.put("msg", "请正确填写入有无房贷");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCar = paramMap.get("hasCar");// 是否有车 (有 无)
		if (StringUtils.isBlank(hasCar)) {
			json.put("msg", "请正确填写入是否有车");
			JSONUtils.printObject(json);
			return null;
		}

		String hasCarLoan = paramMap.get("hasCarLoan");// 有无车贷 (有 无)
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

		String address = paramMap.get("address");// 所在地
		if (StringUtils.isBlank(address)) {
			json.put("msg", "请正确填写入所在地");
			JSONUtils.printObject(json);
			return null;
		}

		String telephone = paramMap.get("telephone");// 居住电话
		if (StringUtils.isBlank(telephone)) {
			json.put("msg", "请正确填写入你的家庭电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (!StringUtils.isBlank(telephone)) {
			if (!StringUtils.isNumeric(telephone)) {
				json.put("msg", "你的家庭电话输入不正确");
				JSONUtils.printObject(json);
				return null;
			}
			if (telephone.trim().length()<7||telephone.trim().length()>11) {
				json.put("msg", "你的家庭电话输入长度不对");
				JSONUtils.printObject(json);
				return null;
			}
		}
		/* 用户头像 */
		String personalHead = paramMap.get("personalHead");// 个人头像 (默认系统头像)
		if (StringUtils.isBlank(personalHead)) {
			personalHead = null;
			json.put("msg", "请正确上传你的个人头像");
			JSONUtils.printObject(json);
			return null;
		}
		if(user==null){
			json.put("msg", "超时请重新登录");
			JSONUtils.printObject(json);
			return null;
		}

		long personId = -1L;
		if(iscellPhone!=null){
			cellPhone= iscellPhone;
		}

		personId = userService.updateUserBaseData(realName, cellPhone, sex,
				birthday, highestEdu, eduStartDay, school, maritalStatus,
				hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
				nativePlacePro, nativePlaceCity, registedPlacePro,
				registedPlaceCity, address, telephone, personalHead, user
						.getId(), idNo);
		if (personId > 0) {
			//==
			if(user.getAuthStep()==1){
				user.setAuthStep(2);
			}
			session().removeAttribute("randomCode");
			user.setPersonalHead(personalHead);//将个人头像放到session里面
			json.put("msg", "保存成功");
			JSONUtils.printObject(json);
			request().setAttribute("person", "1");
			user.setRealName(realName);
			session().setAttribute("user", user);
			return null;
			// 成功
		} else {
			json.put("msg", "保存失败");
			// 失败
			JSONUtils.printObject(json);
			return null;
		}
	}
	
	/**
	 *
	 * 初始化工作认证信息，
	 * @return
	 * @throws Exception
	 */
	public String queryWorkInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		//==============================
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> allworkmap = new HashMap<String, String>();
		User user = (User) session().getAttribute("user");
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		request().setAttribute("provinceList", provinceList);

		map  =  validateService.queryWorkDataById(user.getId());
		allworkmap = validateService.queryAllWorkStatus(user.getId());
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("workPro")
					.toString(), -1L);
			cityId = Convert.strToLong(map.get("workCity")
					.toString(), -1L);
		}
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		request().setAttribute("cityList", cityList);
		request().setAttribute("map", map);
		request().setAttribute("allworkmap", allworkmap);
		Map<String,String> pmap = userService.queryPersonById(user.getId());
		if(pmap != null &&  pmap.size() > 0){
			request().setAttribute("pass", pmap.get("auditStatus"));
		}
		//得都用户基本资料信息
		Map<String,String> mapList = userService.queryPersonById(user.getId());
		if(mapList == null){
			mapList = new HashMap<String, String>();
		}
		int authStep = user.getAuthStep();
		if(authStep < 2){
			request().setAttribute("person", "0");
		}else{
			request().setAttribute("person", "1");
		}
		request().setAttribute("realName", user.getRealName());
		
		return SUCCESS;
	   }
		
		
		//=============================
		
		
	
	/**
	 *
	 * 初始化基本资料信息，
	 * @return
	 * @throws Exception
	 */
	public String queryBasicInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) session().getAttribute("user");
		String  birth = null;
		String rxedate = null;
		if (user != null) {
			map = userService.queryPersonById(user.getId());
			if (map != null && map.size() > 0) {
				workPro = Convert.strToLong(map.get("nativePlacePro")
						.toString(), -1L);
				cityId = Convert.strToLong(map.get("nativePlaceCity")
						.toString(), -1L);
				regPro = Convert.strToLong(map.get("registedPlacePro")
						.toString(), -1L);
				regCity =Convert.strToLong(map.get("registedPlaceCity")
						.toString(), -1L);
			birth = Convert.strToStr(map.get("birthday"), null);
			rxedate = Convert.strToStr(map.get("eduStartDay"), null);
			if(birth!=null){
				birth = birth.substring(0, 10);
			}
			if(rxedate!=null){
				rxedate = rxedate.substring(0, 10);
			}
		   }
		}
		//判断用户是否已经填写了基本信息
		String flag = "";
        if(map!=null&&map.size()>0){//用户基本资料有数据但是不一定是已经填写了基本资料信息 还有可能是上传了个人头像
        	if(!StringUtils.isBlank(map.get("realName"))){//不为空
        	 flag = "1";
        	}else{
        	 flag = "2";
        	}
        }else{
        	 flag = "2";
        }
        request().setAttribute("flag", flag);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		cityList = regionService.queryRegionList(-1L, workPro, 2);
		regcityList = regionService.queryRegionList(-1L, regPro, 2);
		request().setAttribute("map", map);
		request().setAttribute("provinceList", provinceList);
		request().setAttribute("cityList", cityList);
		request().setAttribute("regcityList", regcityList);
		request().setAttribute("birth", birth);
		request().setAttribute("rxedate", rxedate);
		request().setAttribute("ISDEMO", IConstants.ISDEMO);
		return SUCCESS;
	}
	
	
	
	
	
	
	/**
	 * 更新的工作认证资料添加
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataException
	 */
	public String updateUserWorkData() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject json = new JSONObject();
		User user = (User) session().getAttribute("user");
		if (user.getAuthStep() == 1) {
			// 个人信息认证步骤
			json.put("msg", "querBaseData");
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
		if (workPro==null||workPro==-1L) {
			json.put("msg", "请填写工作城市省份");
			JSONUtils.printObject(json);
			return null;
		}	
		Long workCity = Convert.strToLong(paramMap.get("workCity"), -1L);
		if (workCity==null||workCity==-1L) {
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
		if(!StringUtils.isNumeric(companyTel)){
			json.put("msg", "请真确填写公司电话");
			JSONUtils.printObject(json);
			return null;
		}
		if(companyTel.trim().length()<7||companyTel.trim().length()>13){
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
		}else if (2 > directedName.length() || 50 < directedName.length()) {
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
		if(!StringUtils.isNumeric(directedTel)){
			json.put("msg", "请真确填写直系人电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (directedTel.trim().length()!=11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		
		
		String otherName = paramMap.get("otherName");
		if (StringUtils.isBlank(workPro.toString())) {
			json.put("msg", "请填写其他人姓名");
			JSONUtils.printObject(json);
			return null;
		}else if (2 > otherName.length() || 50 < otherName.length()) {
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
		
		if(!StringUtils.isNumeric(otherTel)){
			json.put("msg", "请正确填写其他人联系电话");
			JSONUtils.printObject(json);
			return null;
		}
		if (otherTel.trim().length()!=11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		
		
		String moredName = paramMap.get("moredName");
		if (StringUtils.isBlank(moredName)) {
			json.put("msg", "morename");
			JSONUtils.printObject(json);
			return null;
		}else if (2 > moredName.length() || 50 < moredName.length()) {
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
		if(!StringUtils.isNumeric(moredTel)){
			json.put("msg", "moretel");
			JSONUtils.printObject(json);
			return null;
		}
		if (moredTel.trim().length()!=11) {
			json.put("msg", "请真确填写直系人电话长度错误");
			JSONUtils.printObject(json);
			return null;
		}
		// 用户Id
		Long userId = user.getId();
		Long result = -1L;
		//判断用户是否已经是vip
		Map<String,String> vipMap  = null;
		vipMap = beVipService.queryUserById(user.getId());
		int vipStatus = 1;//1 为非vip 2 为vip 3 代扣费vip
	    int newutostept = -1;
		if(vipMap.size()>0&&vipMap!=null){
			vipStatus = Convert.strToInt(vipMap.get("vipStatus"), 1);
			newutostept = Convert.strToInt(vipMap.get("authStep"), -1);//用户此时的认证步骤状态
		}
		if(user!=null){
		result = userService.updateUserWorkData(orgName, occStatus, workPro,
				workCity, companyType, companyLine, companyScale, job,
				monthlyIncome, workYear, companyTel, workEmail, companyAddress,
				directedName, directedRelation, directedTel, otherName,
				otherRelation, otherTel, moredName, moredRelation, moredTel,
				userId,vipStatus,newutostept);
	  }

		if (result > 0) {
			// 保存成功更新认证步骤
			if (user.getAuthStep() == 2) {
				user.setAuthStep(3);
			}
				
			if(vipStatus!=1){//是vip会员
				//更新用户的session步骤和是更新user表中的认证步骤
				user.setAuthStep(4);
				json.put("msg", "vip保存成功");
				JSONUtils.printObject(json);
				return null;
			}else{
				json.put("msg", "保存成功");
				JSONUtils.printObject(json);
				return null;
			}
			
		} else {
			json.put("msg", "保存失败");
			JSONUtils.printObject(json);
			return null;
		}


	}

	public String updatePassword() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		if(isApplyPro==1){
			getOut().print("<script>alert('请您先设置密保问题')</script>");
			return "applyPro";
		}
		if(isApplyPro==2){
			getOut().print("<script>alert('请您回答密保问题')</script>");
			return "answerPro";
		}
		return "success";
	}
	
	public String szform() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		
		Double smsFee = 0.1;
		Connection conn = MySQL.getConnection();
		try
		{
			Dao.Tables.t_platform_cost tpc = new Dao().new Tables().new t_platform_cost();
			DataSet  ds = tpc.open(conn, " costFee ", "alias='smsFee' and costMode='2'", "id", -1, -1);
			ds.tables.get(0).rows.genRowsMap();
			smsFee = Convert.strToDouble(ds.tables.get(0).rows.get(0).get("costFee").toString(), 0);
		}
		catch (Exception e)
		{
			smsFee=0.1;
		}
		finally
		{
			conn.close();
		}
		request().setAttribute("smsFee", smsFee);
		
		int authStep = user.getAuthStep();
		if(authStep < 2){
			request().setAttribute("person", "0");
		}else{
			request().setAttribute("person", "1");
		}
		
		return "success";
	}
	
	
	
	/**
	 * (前台)个人设置中绑定手机
	 * @return
	 * @throws Exception 
	 */
	public String updatexgmm() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		session().setAttribute("DEMO", IConstants.ISDEMO);
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
//		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		String password=Convert.strToStr(userService.queryUserById(userId).get("password"), "");
		String dealpwd=Convert.strToStr(userService.queryUserById(userId).get("dealpwd"), "");
		if(password.equals(dealpwd)){
			getOut().print(
			"<script>alert('*您的账号还没有修改交易密码，为了您的帐号安全，请您修改交易密码! ');window.location.href='securityCent.do?isOn=2';</script>");
//			"<script>alert('*您的账号还没有修改交易密码，为了您的帐号安全，请您修改交易密码! ');</script>");
//			if(isApplyPro==1){
//				getOut().print(
//			"<script>alert('*请先设置安全问题! ');window.location.href='queryQuestion.do';</script>");
//			}
//			if(isApplyPro==2){
//				getOut().print(
//			"<script>alert('*请先回答安全问题! ');window.location.href='getQuestion.do';</script>");
//			}
	        getOut().flush();
		}
//		request().setAttribute("isApplyPro", isApplyPro);
		try {
			//查询用户类型
//			int userType=userService.queryUserTypeById(user.getId());
			User userA=(User)session().getAttribute("user");//从session获取user
			//判断当前用户是个人用户1还是企业用户2
			if(userA.getUserType()==2){
				Map<String,String> pmap = userService.queryOrgInfoById(user.getId());//获取企业用户信息
				
				if(pmap != null &&  pmap.size() > 0){
					orgName = pmap.get("organization_name");
					request().setAttribute("pass", pmap.get("auditStatus"));
					request().setAttribute("realName", orgName);
				}
				if(StringUtils.isBlank(orgName)){
					return "person";
				}
				
			}else{
				String realName = "";
				Map<String,String> pmap = userService.queryPersonById(user.getId());//获取个人用户信息
				
				if(pmap != null &&  pmap.size() > 0){
					request().setAttribute("pass", pmap.get("auditStatus"));
					realName = pmap.get("realName")+"";
				}
				if(StringUtils.isBlank(realName)){
					return "person";
				}
				request().setAttribute("realName", user.getRealName());
			}
			
//	 		int authStep = user.getAuthStep();
//	 		if(authStep < 2){
//	 			request().setAttribute("person", "0");
//	  		}else{
//				
//	 		}
			request().setAttribute("emailBound", user.getEmail());
//			request().setAttribute("realName", user.getRealName());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	//添加提现银行卡
	public String addtxBankInfo() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
		String password=Convert.strToStr(userService.queryUserById(userId).get("password"), "");
		String dealpwd=Convert.strToStr(userService.queryUserById(userId).get("dealpwd"), "");
		if(password.equals(dealpwd)){
			getOut().print(
			"<script>alert('*您的账号还没有修改交易密码，为了您的帐号安全，请您修改交易密码! ');window.parent.location.href='securityCent.do?isOn=2';</script>");
	        return null;
		}
		try {
			User userA=(User)session().getAttribute("user");//从session获取user
			if(userA.getUserType()==2){
				Map<String,String> pmap = userService.queryOrgInfoById(user.getId());//获取企业用户信息
				if(pmap != null &&  pmap.size() > 0){
					orgName = pmap.get("organization_name");
					request().setAttribute("pass", pmap.get("auditStatus"));
					request().setAttribute("realName", orgName);
				}
				if(StringUtils.isBlank(orgName)){
					return "person";
				}
			}else{
				String realName = "";
				Map<String,String> pmap = userService.queryPersonById(user.getId());//获取个人用户信息
				
				if(pmap != null &&  pmap.size() > 0){
					request().setAttribute("pass", pmap.get("auditStatus"));
					realName = pmap.get("realName")+"";
				}
				if(StringUtils.isBlank(realName)){
				    getOut().print(
				            "<script>alert('*您的账号还没有进行实名认证，请先实名认证后再操作! ');window.parent.location.href='securityCent.do?isOn=3';</script>");
				            getOut().flush();
					return null;
				}
				request().setAttribute("realName",realName);
			}
			request().setAttribute("emailBound", user.getEmail());
			
			provinceList = regionService.queryRegionList(-1L, 1L, 1);
            cityList = regionService.queryRegionList(-1L, workPro, 2);
            List<Map<String, Object>> bankList = this.sysparService.querySysparAllChild("selectName", "typeId=23 and deleted =1 ", " id ", -1, -1);
            request().setAttribute("bankList", bankList);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		if (checkMobile()) {
			return "successmobile";
		}
		return SUCCESS;
	}
	
	
	/**
	 * 所有密码安全提问的内容
	 * @return
	 * @throws Exception
	 */
	public String queryAllQuestionList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId=user.getId();
		int isApplyPro=Convert.strToInt(userService.queryUserById(userId).get("isApplyPro"), 1);
		request().setAttribute("isApplyPro", isApplyPro);
		if(isApplyPro ==2){
			return "index";
		}
		List<Map<String, Object>> list = null;
		list = userService.queryAllQuestionList();
		if (list != null && list.size() > 0) {
			request().setAttribute("list", list);
		}
		return "success";
	}
	/**
	 * 修改密保
	 * @return
	 */
	public String modifiPwdPro(){
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		return "success";
	}
	
	/**
	 * 修改银行卡省市初始化
	 * @throws Exception 
	 */
	public String updateBankPCInit() throws Exception{
	    log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
	    User user = (User) session().getAttribute(IConstants.SESSION_USER);
	    long bankId = request.getLong("bankId", -1L);
	    if (bankId == -1L || user == null) {
            getOut().print("<script>alert('参数信息丢失，请重新操作。');window.location.href.reload()</script>");
            return null;
        }
	    Map<String, String> map = validateService.queryBankCard(bankId, user.getId());
	    
	    if (map==null) {
	        getOut().print("<script>alert('银行卡不存在，请重新操作。');window.location.href.reload()</script>");
            return null;
        }else {
             String cardId = map.get("cardNo");
            if (StringUtils.isNotBlank(cardId)) {
                map.put("cardNo", com.shove.util.StringUtils.formatStr(cardId, 4, cardId.length()-5));
            }
        }
	    request().setAttribute("bank", map);
	    provinceList = regionService.queryRegionList(-1L, 1L, 1);
        cityList = regionService.queryRegionList(-1L, workPro, 2);
	    return SUCCESS;
	}
	
	/**
	 * 修改银行卡省市
	 * @throws Exception 
	 */
	public String updateBankPC() throws Exception{
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        long bankId = Convert.strToLong(paramMap.get("bankId"), -1);
        String dealpwd = Convert.strToStr(paramMap.get("dealpwd"), null);
        int province = Convert.strToInt(paramMap.get("province"), -1);
        int city = Convert.strToInt(paramMap.get("city"), -1);

        if(StringUtils.isBlank(dealpwd) || province==-1 || city==-1 || bankId== -1){
            JSONUtils.printStr("3");
            return null;
        }
        if ("1".equals(IConstants.ENABLED_PASS)) {
            dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
        } else {
            dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()
                    + IConstants.PASS_KEY);
        }
        // 获取用户的信息
        User user = (User) session().getAttribute(IConstants.SESSION_USER);
        String pwd=Convert.strToStr(userService.queryUserById(user.getId()).get("dealpwd"), "");
        if(!dealpwd.equals(pwd)){
            JSONUtils.printStr("5");
            return null;
        }
        long result = validateService.updateBankCardPC(bankId, user.getId(), province, city);
        if (result>0) {
            JSONUtils.printStr("1");
            operationLogService.addOperationLog("t_bankcard", user
                                                .getUserName(), IConstants.INSERT, user.getLastIP(), 0,
                                                "修改银行卡开户行所在地", 1);
        }else {
            JSONUtils.printStr("2");
        }
        return null;
    }

	public void setiDCardValidateService(IDCardValidateService iDCardValidateService) {
		this.iDCardValidateService = iDCardValidateService;
	}


	public UserService getUserService() {
		return userService;
	}

	public ValidateService getValidateService() {
		return validateService;
	}


	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public List<Map<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Map<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Map<String, Object>> getCityList() {
		return cityList;
	}

	public void setCityList(List<Map<String, Object>> cityList) {
		this.cityList = cityList;
	}

	public List<Map<String, Object>> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Map<String, Object>> areaList) {
		this.areaList = areaList;
	}

	public List<Map<String, Object>> getRegcityList() {
		return regcityList;
	}

	public void setRegcityList(List<Map<String, Object>> regcityList) {
		this.regcityList = regcityList;
	}

	public long getWorkPro() {
		return workPro;
	}

	public void setWorkPro(long workPro) {
		this.workPro = workPro;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getRegPro() {
		return regPro;
	}

	public void setRegPro(long regPro) {
		this.regPro = regPro;
	}

	public long getRegCity() {
		return regCity;
	}

	public void setRegCity(long regCity) {
		this.regCity = regCity;
	}


	public void setBeVipService(BeVipService beVipService) {
		this.beVipService = beVipService;
	}


	public void setIDCardValidateService(IDCardValidateService cardValidateService) {
		iDCardValidateService = cardValidateService;
	}

}
