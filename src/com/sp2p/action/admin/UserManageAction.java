package com.sp2p.action.admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.allinpay.ets.client.StringUtil;
import com.renren.api.client.utils.JsonUtils;
import com.shove.Convert;
import com.shove.Xml;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;
import com.shove.io.File;
import com.shove.vo.Files;
import com.shove.web.util.ExcelUtils;
import com.shove.web.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.entity.Admin;
import com.sp2p.entity.User;
import com.sp2p.entity.Users;
import com.sp2p.service.BBSRegisterService;
import com.sp2p.service.CellPhoneService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.RecommendUserService;
import com.sp2p.service.RegionService;
import com.sp2p.service.UserService;
import com.sp2p.service.ValidateService;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.service.admin.RelationService;
import com.sp2p.service.admin.UserManageServic;
/**
 * 后台用户管理
 * @author lw
 *
 */
public class UserManageAction extends BaseFrontAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(UserManageAction.class);
	private UserManageServic userManageServic;
	private RegionService regionService;
	private UserService userService;
	private ValidateService validateService;
	
	private HomeInfoSettingService homeInfoSettingService;
	private CellPhoneService cellPhoneService;
	private BBSRegisterService bbsRegisterService;
	private RelationService relationService;
	
    private List<Map<String, Object>> provinceList;
	private List<Map<String, Object>> cityList;
	private List<Map<String, Object>> regcityList;
	private long workPro = -1L;//初始化省份默认值
	private long cityId =-1L;//初始化话默认城市
	private long regPro =-1L;//户口区域默认值
	private long regCity =-1L;//户口区域默认值
	private Files files;//要批量上传的文件
	private RecommendUserService recommendUserService;
	
	
	
	public void setRecommendUserService(RecommendUserService recommendUserService) {
        this.recommendUserService = recommendUserService;
    }
    public void setRelationService(RelationService relationService) {
        this.relationService = relationService;
    }
    public Files getFiles() {
        return files;
    }
    public void setFiles(Files files) {
        this.files = files;
    }
    public void setHomeInfoSettingService(HomeInfoSettingService homeInfoSettingService) {
        this.homeInfoSettingService = homeInfoSettingService;
    }
    public void setCellPhoneService(CellPhoneService cellPhoneService) {
        this.cellPhoneService = cellPhoneService;
    }
    
    public void setBbsRegisterService(BBSRegisterService bbsRegisterService) {
        this.bbsRegisterService = bbsRegisterService;
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
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}
	public void setUserManageServic(UserManageServic userManageServic) {
		this.userManageServic = userManageServic;
	}
	/**
	 * 跳转到用户基本信息管理初始化
	 * @return
	 */
	public String  queryUserManageBaseInfoindex(){
		return SUCCESS;
	}
	/**
	 *  跳转到用户基本信息管理详细信息
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserManageBaseInfo() throws Exception{
		String userName = paramMap.get("userName");
		String realName = paramMap.get("realName");
		String orgName = paramMap.get("orgName");
		String userType = paramMap.get("userType");
		userManageServic.queryUserManageBaseInfo(pageBean,userName,realName,orgName,userType);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

	    
		return SUCCESS;
	}
	/**
	 * 用户基本信息管理初始化
	 * @return
	 */
	public String  queryUserManageInfoIndex(){
		return SUCCESS;
	}
	
	
	@SuppressWarnings("unchecked")
	public String queryUserManageInfo() throws Exception{
		String userName = paramMap.get("userName");
		String realName = paramMap.get("realName");
		String orgName = paramMap.get("orgName");
		String userType = paramMap.get("userType");
		String lastLoginTimeStart = paramMap.get("lastLoginTimeStart");
        String createTimeStart = paramMap.get("createTimeStart");
        String refereName = paramMap.get("refereName");
        String lastLoginTimeEnd = paramMap.get("lastLoginTimeEnd");
        String createTimeEnd = paramMap.get("createTimeEnd");
        String sms = paramMap.get("sms");
	    userManageServic.queryUserManageInfo(pageBean,userName,realName,orgName,userType,createTimeStart , 
	    		lastLoginTimeStart, createTimeEnd,lastLoginTimeEnd,refereName,sms);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

	    
		return SUCCESS;
	}
	
	/**
	 * 解除登录限制
	 * @throws Exception 
	 */
	public String isLoginLimit() throws Exception{
	    long id = request.getLong("id", -1);
	    long result = userService.updateIsLoginLimit(id);
	    if (result>0) {
	        Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
	        operationLogService.addOperationLog("t_user", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新用户登录限制", 2);
           getOut().print("<script>alert('已解除登录限制');window.location.href='queryUserManageInfoIndex.do'</script>");
        }else {
            getOut().print("<script>alert('解除登录限制失败');window.location.href='queryUserManageInfoIndex.do'</script>");
        }
	    return null;
	}
	
	//用户列表中 查看个人信息
	@SuppressWarnings("unchecked")
	public String queryUserInfo() throws Exception {
		long  id = request.getLong("id",-1);	
		Map<String, String> map = userManageServic.queryUserInfo(id); 
		request().setAttribute("username", map.get("username"));
		request().setAttribute("realName", map.get("realName"));
		request().setAttribute("rating", map.get("rating"));
		request().setAttribute("creditrating", map.get("creditrating"));
		request().setAttribute("createTime", map.get("createTime"));
		request().setAttribute("lastIP", map.get("lastIP"));
		request().setAttribute("email", map.get("email"));
		request().setAttribute("cellPhone", map.get("cellPhone"));
		request().setAttribute("qq", map.get("qq"));
		request().setAttribute("userId", map.get("id"));
		request().setAttribute("idNo", map.get("idNo"));
		request().setAttribute("orgName", map.get("orgName"));
		request().setAttribute("userType", map.get("userType"));
		request().setAttribute("retUser", request.getString("retUser"));
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String updateUserqq() throws Exception{
		JSONObject obj = new JSONObject();
		String retUserName = paramMap.get("retUserName");
		String loginPwdHid = paramMap.get("loginPwdHid");
		String tranPwdHid = paramMap.get("tranPwdHid");
		String retId = paramMap.get("retId");
//		String qq = paramMap.get("qq");
		long userId=Integer.parseInt(paramMap.get("userId"));
		
		Map map = new HashMap();
		map.put("retUserName", retUserName);
		
		map.put("userId", userId);
		map.put("retId", retId);
		
		if ("1".equals(IConstants.ENABLED_PASS)) {
			if (!StringUtil.isEmpty(loginPwdHid)){
				loginPwdHid = com.shove.security.Encrypt.MD5(loginPwdHid.trim());
			}
			if (!StringUtil.isEmpty(tranPwdHid)){
				tranPwdHid = com.shove.security.Encrypt.MD5(tranPwdHid.trim());
			}
		} else {
			if (!StringUtil.isEmpty(loginPwdHid)){
				loginPwdHid = com.shove.security.Encrypt.MD5(loginPwdHid.trim()+ IConstants.PASS_KEY);
			}
			if (!StringUtil.isEmpty(tranPwdHid)){
				tranPwdHid = com.shove.security.Encrypt.MD5(tranPwdHid.trim()+ IConstants.PASS_KEY);
			}
		}
		map.put("loginPwdHid", loginPwdHid);
		map.put("tranPwdHid", tranPwdHid);
		
		long result = -1L;
		result = userManageServic.updateUserInfo(map);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		
		try {
			if(result>=1){
				operationLogService.addOperationLog("t_person", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新用户基本信息（密码-推荐人）", 2);
			obj.put("msg","1");
			JSONUtils.printObject(obj);
			return null;
			}else{
				obj.put("msg","操作失败");
				JSONUtils.printObject(obj);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 导出用户列表信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportusermanageinfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			//用户名
		    String userName = paramMap.get("userName");
	        String realName = paramMap.get("realName");
	        String orgName = paramMap.get("orgName");
	        String userType = paramMap.get("userType");
	        String lastLoginTimeStart = paramMap.get("lastLoginTimeStart");
	        String createTimeStart = paramMap.get("createTimeStart");
	        String refereName = paramMap.get("refereName");
	        String lastLoginTimeEnd = paramMap.get("lastLoginTimeEnd");
	        String createTimeEnd = paramMap.get("createTimeEnd");
	        String sms = paramMap.get("sms");
	        // 待还款详情
	        userManageServic.queryUserManageInfo(pageBean,userName,realName,orgName,userType,createTimeStart , lastLoginTimeStart, createTimeEnd,lastLoginTimeEnd,refereName,sms);
			
			if(pageBean.getPage()==null)
			{
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return  null;
			}
			if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
			{
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return  null;
			}
		
			userManageServic.changeNumToStr1(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户列表", pageBean
					.getPage(), new String[] { "用户名", "用户类型","手机号码", "真实姓名", "企业全称", "邮箱", "QQ号码",
					"手机号码","注册时间","最后登录IP","最后登录时间","邀请人用户名","邀请人真实姓名",""}, new String[] { "username", "userType","cellPhone",
					"realName", "orgName", "email", "qq", "cellPhone","createTime","lastIP","lastDate","refferee","ref_realname","ref_orgname"
					});
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("t_person", admin.getUserName(),IConstants.EXCEL, admin.getLastIP(), 0, "导出用户列表基本信息", 2);
			
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 跳转到用户信用积分管理
	 * @return
	 */
	public String  queryUserManageintegralindex(){
		return SUCCESS;
	}
	/**
	 *  跳转到用户信用积分管理详细
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserManageintegralinfo() throws Exception{
		String username = paramMap.get("username");
		int viprecode = Convert.strToInt(paramMap.get("viprecode"),-1);
		int creditcode = Convert.strToInt(paramMap.get("creditcode"),-1);
		String userType = paramMap.get("userType");
	    userManageServic.queryUserManageintegralinfo(pageBean, username, viprecode, creditcode,userType);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
	    
		return SUCCESS;
	}
	
	/**
	 * 导出用户积分信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportintegralinfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			//用户名
			String username=request.getString("username")==null? "" : request.getString("username");
			username = URLDecoder.decode(username,"UTF-8");
			//会员积分
			int viprecode = request.getInt("viprecode",-1);
			//用户积分排序
			int creditcode = request.getInt("creditcode",-1);
			//用户类型
			String userType=request.getString("userType")==null? "" : request.getString("userType");
			userType = URLDecoder.decode(userType,"UTF-8");
			// 待还款详情
			 userManageServic.queryUserManageintegralinfo(pageBean, username, viprecode, creditcode,userType);
			 if(pageBean.getPage()==null)
				{
					getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
					return  null;
				}
				if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
				{
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return  null;
				}
		
			userManageServic.changeNumToStr1(pageBean);
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户积分", pageBean
					.getPage(), new String[] { "用户名", "用户类型", "真实姓名", "企业全称", "信用积分", "会员积分",
					"最后调整时间"}, new String[] { "username", "userType",
					"realName", "orgName", "creditrating", "rating", "repayDate",
					});
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_usermanage_integralinfo", admin.getUserName(),IConstants.EXCEL, admin.getLastIP(), 0, "导出用户积分信息列表", 2);
			
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * vip记录表初始化
	 * @return
	 */
	public String  queryUservipRecoderindex(){
		return SUCCESS;
	}
	/**
	 * vip记录表详细内容
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String  queryUservipRecoderinfo() throws Exception{
		String username = paramMap.get("username");
		String userType = paramMap.get("userType");
		String appstarttime = paramMap.get("appstarttime");
		String appendTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("appendTime"), null));
		String laststarttime = paramMap.get("laststarttime");
		String lastendTime = FrontMyPaymentAction.changeEndTime(Convert.strToStr(
				paramMap.get("lastendTime"), null));
		userManageServic.queryUservipRecoderinfo(pageBean, username,userType, appstarttime, laststarttime,appendTime,lastendTime);
		
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	/**
	 *用户基本信息管理详细信息里面的详细
	 * @return
	 * @throws Exception 
	 */
	public String queryUserManageBaseInfoinner() throws Exception{
		Long uerId = -1L;
		Map<String, String> map = null;
		Map<String, String> UserMsgmap = null;//
		String birth = null;//出生日期
		String rxedate = null;//入学年份
		String foundDate = null;//成立时间
		if (StringUtils.isNotBlank(request.getString("i"))) {
			uerId = request.getLong("i",-1);
		}
		String userType = request.getString("userType");
		request().setAttribute("userType", userType);
		if(StringUtils.isNotBlank(userType) && "1".equals(userType)){
			map = userService.queryPersonById(uerId);
			UserMsgmap = userManageServic.queryUserManageInnerMsg(uerId);//用户基本信息里面的查看用户的基本信息
			if (map != null && map.size() > 0) {
				workPro = Convert.strToLong(map.get("nativePlacePro")
						+"", -1L);
				cityId = Convert.strToLong(map.get("nativePlaceCity")
						+"", -1L);
				regPro = Convert.strToLong(map.get("registedPlacePro")
						+"", -1L);
				regCity =Convert.strToLong(map.get("registedPlaceCity")
						+"", -1L);
				birth = Convert.strToStr(map.get("birthday"), null);
				rxedate = Convert.strToStr(map.get("eduStartDay"), null);
				if(birth!=null){
					birth = birth.substring(0, 10);
				}
				if(rxedate!=null){
					rxedate = rxedate.substring(0, 10);
				}
			}
			provinceList = regionService.queryRegionList(-1L, 1L, 1);
			cityList = regionService.queryRegionList(-1L, workPro, 2);
			regcityList = regionService.queryRegionList(-1L, regPro, 2);
			request().setAttribute("map", map);
			request().setAttribute("provinceList", provinceList);
			request().setAttribute("cityList", cityList);
			request().setAttribute("regcityList", regcityList);
			request().setAttribute("birth", birth);
			request().setAttribute("rxedate", rxedate);
			request().setAttribute("UserMsgmap", UserMsgmap);
		
			return "SUCCESS1";

		}else if(StringUtils.isNotBlank(userType) && "2".equals(userType)){
			map = userService.queryOrgById(uerId);
			if (map != null && map.size() > 0) {
				foundDate = Convert.strToStr(map.get("foundDate"), null);
				map.put("userType", userType);
				if (foundDate != null) {
					foundDate = foundDate.substring(0, 10);
				}
				request().setAttribute("map", map);
				if (map == null) {// 如果map是空的话 那么用企业没有填写基本信息
					request().setAttribute("flag", "1");
				}
				request().setAttribute("foundDate", foundDate);
			}
			UserMsgmap = userManageServic.queryOrgManageInnerMsg(uerId);//用户基本信息里面的查看用户的基本信息
			
			request().setAttribute("UserMsgmap", UserMsgmap);

			return "SUCCESS2";

		}else{
			log.info("用户类型错误！");
			return "404";
		}
	}
	
	
	/**
	 * 查询用户管理模块中的基本信息管理中的工作信息
	 * @return
	 * @throws Exception 
	 */
	public String queryUserMangework() throws Exception{
		long id = request.getLong("uid", -1L);
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> UserMsgmap = null;//
		map = validateService.queryWorkDataById(id);
		UserMsgmap = userManageServic.queryUserManageInnerMsg(id);//用户基本信息里面的查看用户的基本信息
		String userType = request.getString("userType");
		request().setAttribute("userType", userType);
		if (map != null && map.size() > 0) {
			workPro = Convert.strToLong(map.get("workPro")+"", -1L);
			cityId = Convert.strToLong(map.get("workCity")+"", -1L);
		}
		request().setAttribute("id", id);
		provinceList = regionService.queryRegionList(-1L, 1L, 1);
		
			cityList = regionService.queryRegionList(-1L, workPro, 2);
		
		if (cityId == 0) {
			request().setAttribute("map", map);
			request().setAttribute("provinceList", provinceList);
			cityList = regionService.queryRegionList(-1L, 1L, 1);
			request().setAttribute("cityList", cityList);
		} else {
 			request().setAttribute("map", map);
			request().setAttribute("provinceList", provinceList);
			request().setAttribute("cityList", cityList);
		}
		request().setAttribute("UserMsgmap", UserMsgmap);
		return SUCCESS;
	}
	
	/**
	 * 跳转到投资信息明细
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String queryUserManageInvest() throws Exception{
		Long uerId = request.getLong("i", -1L);
		String createtimeStart = paramMap.get("createtimeStart");
		String createtimeEnd = paramMap.get("createtimeEnd");
		userManageServic.queryUserManageInvest(pageBean, uerId,createtimeStart,createtimeEnd);
		Map<String, String> UserMsgmap = null;
		UserMsgmap = userManageServic.queryUserManageInnerMsg(uerId);
		String userType = request.getString("userType");
		request().setAttribute("userType", userType);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("UserMsgmap", UserMsgmap);
		request().setAttribute("userId", uerId);
		return SUCCESS;
	}
	
	
	/**
	 * 跳转到企业的投资信息明细
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryOrgManageInvest() throws Exception{
		Long uerId = request.getLong("i", -1L);
		String userType = request.getString("userType");
		request().setAttribute("userType", userType);
		String createtimeStart = paramMap.get("createtimeStart");
		String createtimeEnd = paramMap.get("createtimeEnd");
		userManageServic.queryUserManageInvest(pageBean, uerId,createtimeStart,createtimeEnd);
		Map<String, String> UserMsgmap = null;
		UserMsgmap = userManageServic.queryOrgManageInnerMsg(uerId);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		request().setAttribute("UserMsgmap", UserMsgmap);
		request().setAttribute("userId", uerId);
		return SUCCESS;
	}
	
	/**
	 * 导出用户列表信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportuserInvestInfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);

		try {

			//用户名
			Long uerId = request.getLong("i", -1L);
			System.out.println(uerId);
			String createtimeStart = paramMap.get("createtimeStart");
			String createtimeEnd = paramMap.get("createtimeEnd");
			// 待还款详情
			userManageServic.queryUserManageInvest(pageBean, uerId,createtimeStart,createtimeEnd);
			 if(pageBean.getPage()==null)
				{
					getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
					return  null;
				}
				if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
				{
				getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
				return  null;
				}
		
				userManageServic.changeFigure(pageBean);
			 HSSFWorkbook wb = ExcelUtils.exportExcel("用户投资信息列表", pageBean
					.getPage(), new String[] { "用户名", "真时姓名", "手机号码", "投资日期",
					"还款方式","投资期限","投资标题","投资金额"}, new String[] { "username",
					"realName", "cellPhone", "investTime", "paymentMode","deadline","borrowTitle","investAmount",
					});
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_userManage_invest", admin.getUserName(),IConstants.EXCEL, admin.getLastIP(), 0, "导出用户投资信息列表", 2);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 跳转到信用分明细
	 * @return
	 */
	public String userintegralcreditindex(){
		String userId = request.getString("id");
		String type = request.getString("y");
        request().setAttribute("i", userId);
        request().setAttribute("y", type);
		return SUCCESS;
	}
	/**
	 * 跳转到会员分明细info
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public String userintegralcreditinfo() throws Exception{
		Long userId = Convert.strToLong(paramMap.get("userId"), -1L);
		Integer type = Convert.strToInt(paramMap.get("type"), -1);
		request().setAttribute("userId",userId);
		request().setAttribute("type",type);
		userManageServic.userintegralcreditinfo(pageBean, userId,type);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		
		return SUCCESS;
	}
	
	/**
	 * 导出用户积分明细信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportuserintegralcreditinfo() {
		pageBean.pageNum = 1;
		pageBean.setPageSize(100000);
		Long userId = request.getLong("userId", -1L);
		Integer type = request.getInt("type", -1);

		try {

			// 待还款详情
			userManageServic.userintegralcreditinfo(pageBean, userId,type);
			if(pageBean.getPage()==null)
			{
				getOut().print("<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
				return  null;
			}
			if(pageBean.getPage().size()>IConstants.EXCEL_MAX)
			{
			getOut().print("<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
			return  null;
			}
			
			HSSFWorkbook wb = ExcelUtils.exportExcel("用户积分明细", pageBean
					.getPage(), new String[] { "用户名", "真实姓名", "积分类型", "备注",
					"变动类型","变动分值","操作时间"}, new String[] { "username",
					"realName", "intergraltype", "remark", "changetype","changerecore","changtime"
					});
			this.export(wb, new Date().getTime() + ".xls");
			Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			operationLogService.addOperationLog("v_t_userManage_integralinner", admin.getUserName(),IConstants.EXCEL, admin.getLastIP(), 0, "导出用户积分明细", 2);
	
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (DataException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 弹出框初始化
	 * @return
	 * @throws Exception 
	 */
	public String addintegralindex() throws Exception{
		Map<String,String> popmap = null;
		Long id = request.getLong("id",-1L);
		popmap = userManageServic.queryUserManageaddInteral(id);
		request().setAttribute("popmap", popmap);
		return SUCCESS;
	}
	/**
	 * 弹出框初始添加信用积分
	 * @return
	 * @throws Exception 
	 */
	public String addintegralreal() throws Exception{
		Long userId = Convert.strToLong(paramMap.get("id"), -1L);
		Integer type = Convert.strToInt(paramMap.get("type"), -1);
		if(type==-1){
			JSONUtils.printStr("0");
			return null;
		}
		String scoreStr = paramMap.get("s");
		if(StringUtils.isBlank(scoreStr)){
			JSONUtils.printStr("1");
			return null;
		}
		if(!StringUtils.isNumeric(scoreStr)){
			JSONUtils.printStr("5");
			return null;
		}
		String remark = paramMap.get("remark");
		if(StringUtils.isBlank(remark)){
			JSONUtils.printStr("2");
			return null;
		}
		String typeStr = null;
		Long result = -1L;
		if(type==1){
			typeStr = "手动添加信用积分";
		}
		if(type==2){
			typeStr = "手动添加会员积分";
		}
/*		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(new Date());*/
		String changetype = "增加";//先设置为增加类型
		Integer score = Convert.strToInt(scoreStr, -1);
		result =	userManageServic.addIntervalDelt(userId, score, type, typeStr, remark,changetype);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_user/t_userintegraldetail", admin.getUserName(),IConstants.INSERT, admin.getLastIP(), 0, "添加信用积分及其明细", 2);
		if(result>0){
			JSONUtils.printStr("3");
			return null;
		}else{
			JSONUtils.printStr("4");
			return null;
		}
	}
	
	/**
	 * add by houli  获得用户资金信息
	 * @return
	 * @throws Exception 
	 */
	public String queryUserCashInfo() throws Exception{
		try{
			Long userId =request.getLong("userId", -100);
			Map<String,String> map =  userManageServic.queryUserCashInfo(userId);
			if(map != null){
				//request().setAttribute("map_", map);
				JSONObject obj = new JSONObject();
				obj.put("map_", map);
				
				JSONUtils.printObject(obj);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 代金券批次列表初始化
	 * @return
	 */
	public String  queryVouchersInfoindex(){
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryVouchersBaseInfo() throws Exception{
		String investor = paramMap.get("investor");
		String type = paramMap.get("type");
		String state = paramMap.get("state");
		String timeStart = paramMap.get("timeStart");
		String timeEnd = paramMap.get("timeEnd");
		
	    userManageServic.queryVouchersInfo(pageBean,type,state,investor,timeStart,timeEnd);
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

	    
		return SUCCESS;
	}
	/**
	 * 代金券新增列表初始化
	 * @return
	 */
	public String  addVouchers(){
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String userName = admin.getUserName();
		request().setAttribute("userName",userName);
		return SUCCESS;
	}
	/**
	 * 代金券新增
	 * @return
	 */
	public String  addVouchersList() throws Exception{
		JSONObject json = new JSONObject();
		String title = Convert.strToStr(paramMap.get("title"), null);
		String type = Convert.strToStr(paramMap.get("type"),null);
		String amount = Convert.strToStr(paramMap.get("amount"), null);
		String number = Convert.strToStr(paramMap.get("number"), null);
		String timeStart = Convert.strToStr(paramMap.get("timeStart"), null);
		String timeEnd = Convert.strToStr(paramMap.get("timeEnd"), null);
		String applicant = Convert.strToStr(paramMap.get("applicant"), null);
		String departments = Convert.strToStr(paramMap.get("departments"), null);
		String operation = Convert.strToStr(paramMap.get("operation"), null);
		if (StringUtils.isBlank(title)) {
			json.put("msg", "请填写标题");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(type)) {
			json.put("msg", "请选择类型");
			JSONUtils.printObject(json);
			return null;
		}
		Integer type_1 = Convert.strToInt(type, -1);
		if (StringUtils.isBlank(amount)) {
			json.put("msg", "请填写金额");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(number)) {
			json.put("msg", "请填写数量");
			JSONUtils.printObject(json);
			return null;
		}
		Integer number_1 = Convert.strToInt(number, -1);
		if (StringUtils.isBlank(timeStart)) {
			json.put("msg", "请填写开始时间");
			JSONUtils.printObject(json);
			return null;
		}
		
		if (StringUtils.isBlank(timeEnd)) {
			json.put("msg", "请填写失效时间");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(applicant)) {
			json.put("msg", "请填写申请人");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(departments)) {
			json.put("msg", "请填写申请时间");
			JSONUtils.printObject(json);
			return null;
		}
		if (StringUtils.isBlank(operation)) {
			json.put("msg", "请填写操作人");
			JSONUtils.printObject(json);
			return null;
		}
		
		long batchId = -1L;
		batchId= userManageServic.addVouchersInfo(title, type_1, amount, number_1, timeStart, timeEnd, applicant, departments, operation);
		if (batchId > 0) {
			Map<String, String> djj = userManageServic.queryTrialGenerateTicket((int)batchId);  
			if(djj != null ){
			    Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			   // 添加操作日志
	            operationLogService.addOperationLog("t_repayment", admin
	                    .getUserName(), IConstants.UPDATE, admin.getLastIP(), 0,
	                    "管理员进行逾期垫付操作", 2);
				json.put("msg", "新增成功");
				JSONUtils.printObject(json);
				return null;
			}else{
				json.put("msg", "新增失败");
				JSONUtils.printObject(json); 
				return null;
			}
		}else{
			json.put("msg", "新增失败");
			JSONUtils.printObject(json);
			return null;
		}
	}
	
	/**
	 * 体验券/批次冻结、解冻
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public String experienceUp() throws IOException, SQLException{
	   int status = Convert.strToInt(paramMap.get("status"), -1);
	   Long id = Convert.strToLong(paramMap.get("id"), -1L);
	   String hasBatch = paramMap.get("batch");
	    if (hasBatch!=null && !"".equals(hasBatch)) {
	        if (status==-1  || id==-1) {
	            JSONUtils.printStr("-1");//参数丢失
	            return null;
	        }
	        Long result = userManageServic.experienceUp(id, status);
	        if (result>0) {
	            JSONUtils.printStr(""+status);
	        }else {
	            JSONUtils.printStr("-2");
	        }
        }else {
            if (status==-1  || id==-1) {
                JSONUtils.printStr("-1");//参数丢失
                return null;
            }
            Long result = userManageServic.experienceUp(id, status,null);
            if (result>0) {
                JSONUtils.printStr(""+status);
            }else {
                JSONUtils.printStr("-2");
            }
        }
	    return null;
	}
	
	
	/**
	 * 代金券列表初始化
	 * @return
	 */
	public String  experienceInit(){
	    request().setAttribute("batch", request.getString("batch")==null?"":request.getString("batch"));
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String experienceList() throws Exception{
		String batch = paramMap.get("batch");
		String number = paramMap.get("number");
		String name = paramMap.get("name");
		String state = paramMap.get("state");
		String project = paramMap.get("project");
		String timeStart = paramMap.get("timeStart");
		String timeEnd = paramMap.get("timeEnd");
		pageBean.setPageSize(IConstants.PAGE_SIZE_20);
	    userManageServic.queryExperienceInfo(pageBean,batch==null?"-1":batch,number,name,state,project,timeStart,timeEnd);
		return SUCCESS;
	}
	
	public String experienceBindInit() throws SQLException{
	        String idstr = paramMap.get("id");
	        Long id = Convert.strToLong(idstr, -1L);
	        setParamMap(userManageServic.queryTicketById(id));
	        return SUCCESS;
	}
	
	public String SeachUser() throws Exception{
	    String username = paramMap.get("username");
	    pageBean.setPageSize(IConstants.PAGE_SIZE_10);
	    userService.queryUserByNickName(username,pageBean);
	   JSONUtils.printArray(pageBean.getPage());
	    return null;
	}
	
	/**
	 * 体验券绑定用户
	 * @throws Exception 
	 */
	public String experienceBindMethod() throws Exception{
	    String username =paramMap.get("username");
	    String  userId = paramMap.get("userId");
	    userService.queryUserByName(username);
	    
	    if (Convert.strToInt(userId, -1)==-1) {
            Map<String, String> uid = userService.queryIdByUser(username);
            if (uid!=null&&uid.containsKey("id")) {
                userId = uid.get("id");
                if (Convert.strToInt(uid.get("id"), -1)==-1) {
                    JSONUtils.printStr("11");
                    return null;
                }
            }else {
                JSONUtils.printStr("11");
                return null;
            }
        }
	    
	    String ticketNo = paramMap.get("ticketNo");
	    Map<String, Object> map= userManageServic.updateMyTicket(userId, ticketNo);
	    JSONUtils.printObject(map);
	    return null;
	}
	
	/**
	 * 新增用户初始化
	 */
	public String addUserSystemInitMethod(){
	    return SUCCESS;
	}
	
	/**
	 * 新增用户
	 * @throws Exception 
	 */
	public String addUserSystemMethod() throws Exception{
        String phone=paramMap.get("mobilePhone"); // 手机号码
        Integer userType = Convert.strToInt(paramMap.get("userType"),-1) ;//用户类型（1.个人用户，2.企业用户 ）
        String userName = paramMap.get("username"); // 用户名
        String orgName = paramMap.get("orgName");// 企业全称
        String password = paramMap.get("password"); // 用户密码
        String comfirpwd = paramMap.get("comfirpassword");//用户确认密码
        String idNo = paramMap.get("idNo");//身份证
        String realName = paramMap.get("realName");//真实姓名
        String refferee = paramMap.get("refferee");//推荐人
        String email = paramMap.get("email");//邮箱
        
        String md5Password_pwd = password;
        Long userId = -1L;
        
        
        //用户数据处理
        Users userEntity =new Users();
        
        //手机
        if (StringUtils.isBlank(phone)) {
            //手机号不能为空
            JSONUtils.printStr("1");
            return null;
        }
        
        //用户名不为空
        if (StringUtils.isBlank(userName)) {
            //用户名不能为空
            JSONUtils.printStr("2");
            return null;
        }
        
        //用户名长度大于2且小于20
        if (userName.length() < 2 || userName.length() > 20) {
            //用户名长度大于2且小于20
            JSONUtils.printStr("3");
            return null;
        }
        
        
        
        // 验证用户名木含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
        if (userName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
            //用户名不能包含特殊字符
            JSONUtils.printStr("4");
            return null;
        }
        
        // 判断第一个字符串不能使以下划线开头的
        String fristChar = userName.substring(0, 1);
        if (fristChar.equals("_")) {//用户名不能以下划线开头
            JSONUtils.printStr("5");
            return null;
        }
        if(userType==2){
            if (StringUtils.isBlank(orgName)) {//企业名称不能为空
                JSONUtils.printStr("6");
                return null;
            }
            
            if (orgName.length() < 2 || orgName.length() > 20) {
                //企业名称长度大于2且小于20
                JSONUtils.printStr("7");
                return null;
            }
            
            // 验证企业名称不含有特殊字符串处理第一个字符不可以是下划线开始 ^[^@\/\'\\\"#$%&\^\*]+$
            if (orgName.replaceAll("^[\u4E00-\u9FA5A-Za-z0-9_]+$", "").length() != 0) {
                JSONUtils.printStr("8");
                return null;
            }
            // 判断第一个字符串不能使以下划线开头的
            String fristCharOrg = orgName.substring(0, 1);
            if (fristCharOrg.equals("_")) {
                JSONUtils.printStr("9");
                return null;
            }
        }
    
        if (StringUtils.isBlank(password)) {
            JSONUtils.printStr("10");
            return null;
        }
        
        if (StringUtils.isBlank(comfirpwd)) {
            JSONUtils.printStr("21");//请再次输入密码
            return null;
        }else {
            if (!password.equals(comfirpwd)) {
                JSONUtils.printStr("22");//两次密码不一致
                return null;
            }
        }
        
        
        //用户名、email、手机号码、企业全称唯一认证
       
            Long result = userService.isExistEmailORUserName(null, userName,null,null);
            if (result > 0) { // 用户名重复
                JSONUtils.printStr("11");
                return null;
            }
                result = userService.isExistEmailORUserName(null, null,null,phone);
                if (result > 0) { // 手机号码重复
                    JSONUtils.printStr("13");
                    return null;
                }
            if(orgName.length()!=0){
                result = userService.isExistEmailORUserName(null, null,orgName,null);
                if (result > 0) { // 企业全称重复
                    JSONUtils.printStr("14");
                    return null;
                }
            }
            //IConstants.ENABLED_PASS是否启用加密
            if ("1".equals(IConstants.ENABLED_PASS)) {
                md5Password_pwd = com.shove.security.Encrypt
                        .MD5(md5Password_pwd.trim());
            } else {
                md5Password_pwd = com.shove.security.Encrypt.MD5(md5Password_pwd.trim()
                        + IConstants.PASS_KEY);
            }
            userEntity.setUserName(userName);
            userEntity.setOrgName(orgName);
            userEntity.setMobilePhone(phone);
            userEntity.setPassword(md5Password_pwd);
            String tradPassWord = paramMap.get("dealpwd");//交易密码
            String comfirdealpwd = paramMap.get("comfirdealpwd");//确认交易密码
           if (StringUtils.isBlank(tradPassWord)) {
              JSONUtils.printStr("15");
              return null;
          }
           if (StringUtils.isBlank(comfirdealpwd)) {
               JSONUtils.printStr("19");//请再次输入交易密码
               return null;
        }else {
            if (!tradPassWord.equals(comfirdealpwd)) {
                JSONUtils.printStr("20");//两次密码不一致
                return null;
            }
        }
           Map<String, Object> map = null;
           long recommendUserId = -1;//推荐人userId
     //推荐人
       if (StringUtils.isNotBlank(refferee)) {

           //根据用户名查询推荐人用户明细
           Map<String, String> userIdMap = userService.queryIdByUser(refferee);
           if (userIdMap != null) {
               recommendUserId = Convert.strToLong(userIdMap.get("id"), -1);
           }
         //判断推荐人是否为推广人
           map = relationService.isPromoter(refferee);
           if (map == null) {
               refferee = null;
           }
           if (userIdMap == null && map == null) {
               JSONUtils.printStr("23");
               return null;
           }
       }
           
        //步骤2
        String  md5Password=tradPassWord;
        if ("1".equals(IConstants.ENABLED_PASS)) {
            md5Password = com.shove.security.Encrypt
                    .MD5(md5Password.trim());
        } else {
            md5Password = com.shove.security.Encrypt.MD5(md5Password.trim()
                    + IConstants.PASS_KEY);
        }
        if(md5Password.equals(userEntity.getPassword())){
            JSONUtils.printStr("16");
            return null;
        }
        if (StringUtils.isBlank(email)) {
            userEntity.setEmail("default@163.com");
        }else {
            long count  = userService.validationIdNoAndRealName("email", email, "t_user");
            if (count>0) {
                JSONUtils.printStr("25");
                return null;
            }
            userEntity.setEmail(email);
        }
        
        //验证身份证
        if (StringUtils.isNotBlank(idNo)) {
            long count = userService.validationIdNoAndRealName("idNo",idNo,"t_person");
            if (count>0) {
                JSONUtils.printStr("24");
                return null;
            }
        }
        
        
        //手机注册用户
        userEntity.setEnable(1);//将用户激活
        int typelen = -1;
        Map<String,String> lenMap = null;
        lenMap = userService.querymaterialsauthtypeCount(); //查询证件类型主表有多少种类型
        if(lenMap!=null&&lenMap.size()>0){
            typelen =  Convert.strToInt(lenMap.get("cccc"), -1);
            if(typelen!=-1){
                //个人用户
                if(userType==1){
                    userEntity.setUserType(1);
                }else{
                    //企业用户
                    userEntity.setUserType(2);
                }
                userId=cellPhoneService.usercellRegister(userEntity.getMobilePhone(),userEntity.getUserName(),userEntity.getPassword(),
                        userEntity.getRefferee(),map,typelen,userEntity.getUserType(),userEntity.getOrgName(),idNo,realName);//注册用户 和  初始化图片资料
            }
        }
                
        if (userId < 0) { 
            // 注册失败
            JSONUtils.printStr("17");
            return null;
        } else {
            //修改交易密码
            homeInfoSettingService.updateUserPassword(userId, tradPassWord, "tradpwd");
            //如果是企业注册，往企业基础表添加数据
            if(userType==2){
                userService.addUserCompanyData(userId, userEntity.getOrgName(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
            }
            //手机注册
            userService.updateSign(userId);//修改校验码
            //添加通知默认方法
           // homeInfoSettingService.addNotes(userId, true, false, false);
            //homeInfoSettingService.addNotesSetting(userId, true, true, true, true,  true, false, false, false, false, false, false, false, false, false, false);
              //====
            Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
         // 添加操作日志
            operationLogService.addOperationLog("t_user", admin
                    .getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
                    "后台手动添加用户", 2);
            JSONUtils.printStr("18");//注册成功
            Users user = new Users();
            user.setUserName(userEntity.getUserName());
            user.setPassword(password);
            user.setMobilePhone(phone);
            if (StringUtils.isBlank(email)) {
                user.setEmail("default@163.com");
            }else {
                user.setEmail(email);
            }
            bbsRegisterService.doRegisterByAsynchronousMode(user);
            
          //修改之前的推荐
            if(recommendUserId>0){//判断是否为空
                
                List<Map<String,Object>> list = recommendUserService.queryRecommendUser(null, userId, null);//查询用户是否已经存在关系了。
                if(list!=null&&list.size()>0){//判断之前是否已经有关系了。
                    return null;
                }
                recommendUserService.addRecommendUser(userId, recommendUserId);
            }
            
            return null;
        }
    
	}
	public static void main(String[] args) {
		String password = "1c7e7709ed460926d14570aa0cb5c8ad";
		password = com.shove.security.Encrypt.MD5(password.trim()+IConstants.PASS_KEY);
		System.out.println(password);
	}
	
	
	/**
	 * 导出excel
	 */
	public String excelImportExperience(){

        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        pageBean.setPageNum(1);
        pageBean.setPageSize(50000);//最大查询5W条
        try {
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            String batch = paramMap.get("batch");
            String number = paramMap.get("number");
            String name = paramMap.get("name");
            String state = paramMap.get("state");
            String project = paramMap.get("project");
            String timeStart = paramMap.get("timeStart");
            String timeEnd = paramMap.get("timeEnd");
            userManageServic.queryExperienceInfo(pageBean,batch==null?"-1":batch,number,name,state,project,timeStart,timeEnd);

            if (pageBean.getPage()== null) {
               getOut().print("<script>alert('没有数据可导出。');window.history.go(-1);</script>");
               return null;
            }
           
            for (int i = 0; i < pageBean.getPage().size(); i++) {
                Map<String, Object> map= (Map<String, Object>) pageBean.getPage().get(i);
                int status = (Integer) map.get("ticketStatus");
                if (status==0) {//未绑定
                    map.put("ticketStatusStr", "未绑定");
                }else if(status==1){//冻结
                    map.put("ticketStatusStr", "冻结");
                }else if (status==2) {//已使用
                    map.put("ticketStatusStr", "已使用");
                }else if (status==3) {//已绑定
                    map.put("ticketStatusStr", "已绑定");
                }else {
                    map.put("ticketStatusStr", "状态异常");
                }
            }
            
            HSSFWorkbook wb = ExcelUtils.exportExcel("体验券列表", pageBean
                                                     .getPage(), new String[] {"编号" , "批次号", "券号", "金额", "用户名", "使用项目", "使用时间","绑定时间", "状态" }, 
                                                     new String[] { "ticketId", "batchId", "ticketNo", "amount", "username",
                                                     "borrowTitle", "useTime", "bindingTime", "ticketStatusStr" });
           
            this.export(wb, new Date().getTime() + ".xls");
            
            operationLogService.addOperationLog("v_t_trial_ticket", admin
                    .getUserName(), IConstants.EXCEL, admin.getLastIP(), 0,
                    "导出体验券列表", 2);
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    
	}
	
    public String downExperienceModel() throws IOException, SQLException, DataException{
        int batchId = Convert.strToInt(session().getAttribute("batchId")==null?"-1":session().getAttribute("batchId").toString(), -1);
        if (batchId==-1) {
            addActionError("模版下载异常。");
            return null;
        }
        pageBean.setPageSize(50000);
        userManageServic.downExperienceModel(batchId, pageBean);
        HSSFWorkbook book = ExcelUtils.exportExcel("批量绑定模版",pageBean.getPage(), new String[]{"体验券号","用户ID"}   , new String[]{"ticketNo","userId"});
        this.export(book,new Date().getTime()+".xls");
        return null;
    }
	
	public String experienceBindManyInit() throws SQLException{
	  try {
	       int batchId = request.getInt("batchId",-1);
	       long unbind = userManageServic.queryTicketUnbind(batchId);
	        session().setAttribute("unbind", unbind);
	        session().setAttribute("batchId", batchId);
    }
    catch (Exception e) {
       log.error(e);
       e.printStackTrace();
       addActionMessage(e.getMessage());
    }
	    return SUCCESS;
	}
	
	/**
	 * 批量绑定
	 * @throws Exception 
	 */
	public String experienceBindManyMethod() throws Exception{
	    if (files==null) {
            addActionError("请选择文件");
            return INPUT;
        }
	    String filename = files.getFilesFileName().substring(files.getFilesFileName().lastIndexOf("."));
	    if (!".xls".equalsIgnoreCase(filename)) {
	        addActionError("文件格式不正确，请重新选择文件");
            return INPUT;
        }
	    try {
            String [][] data = com.shove.util.ExcelUtils.getData(files.getFiles(), 1);
            
            List<Map<String,Object>> result = userManageServic.bindExperienceMany(data);
            HSSFWorkbook book = ExcelUtils.exportExcel("批量绑定结果",result, new String[]{"用户ID","体验券号","绑定结果"}   , new String[]{"userId","ticketNo","ret_desc"});
            this.export(book, new Date().getTime()+".xls");
            addActionMessage("绑定完毕，请查看绑定结果");
            response().getOutputStream().print("<script type='text/javascript'>window.parent.history.go(-1);window.parent.jBox.close();</script>");
            return null;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            log.error(e);
            e.printStackTrace();
            addActionError("出现异常，请联系管理员。");
            return INPUT;
        }
	    return SUCCESS;
	}
	
	/**
	 * 异常用户index
	 */
	public String abnormalUserIndex(){
	    return SUCCESS;
	}
	/**
	 * 查询异常用户
	 */
	public String queryUserManageBnormalInfo() throws SQLException{
	    List<Map<String, Object>> list = userService.queryUserManageBnormalInfo();
	    pageBean.setPage(list);
	    return SUCCESS;
	}
	
	/**
	 * 解决异常
	 */
	public String updateSign() throws Exception{
	    long userId = Convert.strToLong(paramMap.get("userId"), -1);
	    long result = userService.updateSign(userId);
	    if (result >0) {
	        Admin admin = (Admin) session().getAttribute(
	                                                     IConstants.SESSION_ADMIN);
	        operationLogService.addOperationLog("t_user", admin
	                                            .getUserName(), IConstants.INSERT, admin.getLastIP(), 0,
	                                            "解决用户异常状态", 2);
            //解除异常成功。
            getOut().print("<script>alert('解除异常成功!');window.location.href='abnormalUserIndex.do';</script>");
            return null;
        }else {
            getOut().print("<script>alert('解除异常失败!');window.location.href='abnormalUserIndex.do';</script>");
            return null;
        }
	}
	
	/**
	 *功能：密码初使化
	 * @return
	 * @throws Exception
	 */
	public String passwordDefault() throws Exception {
		// getOut().print("<script>alert('解除异常失败!');window.location.href='abnormalUserIndex.do';</script>");

		String loginPwdHid = paramMap.get("loginPwdHid");
		String tranPwdHid = paramMap.get("tranPwdHid");
		long userId = Integer.parseInt(paramMap.get("userId"));
		Map map = new HashMap();

		map.put("userId", userId);

		if ("1".equals(IConstants.ENABLED_PASS)) {
			if (!StringUtil.isEmpty(loginPwdHid)) {
				loginPwdHid = com.shove.security.Encrypt
						.MD5(loginPwdHid.trim());
			}
			if (!StringUtil.isEmpty(tranPwdHid)) {
				tranPwdHid = com.shove.security.Encrypt.MD5(tranPwdHid.trim());
			}
		} else {
			if (!StringUtil.isEmpty(loginPwdHid)) {
				loginPwdHid = com.shove.security.Encrypt.MD5(loginPwdHid.trim()
						+ IConstants.PASS_KEY);
			}
			if (!StringUtil.isEmpty(tranPwdHid)) {
				tranPwdHid = com.shove.security.Encrypt.MD5(tranPwdHid.trim()
						+ IConstants.PASS_KEY);
			}
		}
		map.put("loginPwdHid", loginPwdHid);
		map.put("tranPwdHid", tranPwdHid);

		long result = -1L;
		result = userManageServic.updateUserInfo(map);
		return null;
	}
	
	/**
	 *功能：修改真名，身份证号，且实名认证通过
	 * @return
	 * @throws Exception
	 */
	public String updateAuthInfo() throws Exception {

		String realName = paramMap.get("realName");
		String idNo = paramMap.get("idNo");
		long userId = Integer.parseInt(paramMap.get("userId"));
		Map <String,Object>map = new HashMap<String,Object>();

		map.put("userId", userId);
		map.put("idNo", idNo);
		map.put("realName", realName);

		userManageServic.updatePersonAuth(map);
		JSONUtils.printStr("0");
		return null;
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
	public List<Map<String, Object>> getRegcityList() {
		return regcityList;
	}
	public void setRegcityList(List<Map<String, Object>> regcityList) {
		this.regcityList = regcityList;
	}
	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}
	
	
	
	
}
