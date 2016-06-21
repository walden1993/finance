package com.sp2p.action.admin;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.web.util.ExcelUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.RegionService;
import com.sp2p.service.admin.AdminService;
import com.sp2p.service.admin.FundManagementService;

@SuppressWarnings("serial")
public class UserBankManagerAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(LinksAction.class);
	private AdminService adminService;
	private List<Map<String,Object>> checkers;
	 private FundManagementService fundManagementService;
	 private RegionService regionService;
	 
	 
	 
		public RegionService getRegionService() {
        return regionService;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

        public FundManagementService getFundManagementService() {
			return fundManagementService;
		}

		public void setFundManagementService(FundManagementService fundManagementService) {
			this.fundManagementService = fundManagementService;
		}
		
	public String queryUserBankInit() throws SQLException, DataException{
		String types = request.getString("types");
		request().setAttribute("types", types);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserBankList() throws Exception{
		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String realName = Convert.strToStr(paramMap.get("realName"), null);
		String userType = Convert.strToStr(paramMap.get("userType"), null);
		//
		String checkUser = Convert.strToStr(paramMap.get("checkUser"), null);
		String moStartTime = Convert.strToStr(paramMap.get("modifiedTimeStart"), null);
		String moEndTime = Convert.strToStr(paramMap.get("modifiedTimeEnd"), null);
		String checkStartTime = Convert.strToStr(paramMap.get("checkTimeStart"), null);
		String checkTimeEnd = Convert.strToStr(paramMap.get("checkTimeEnd"), null);
		String cardStatus = Convert.strToStr(paramMap.get("cardStatus"), null);
		String isemployee = Convert.strToStr(paramMap.get("isemployee"), null);
		String hasWork = Convert.strToStr(paramMap.get("hasWork"), null);
		
		moEndTime = FrontMyPaymentAction.changeEndTime(moEndTime);
		checkTimeEnd = FrontMyPaymentAction.changeEndTime(checkTimeEnd);
		
		Long checkUserId = -100L;
		if(checkUser!=null){
			checkUserId = Convert.strToLong(checkUser, -100L);
		}
		try {
			//加载银行卡信息
			fundManagementService.queryBankCardInfos(pageBean,userName,realName,userType,checkUserId,moStartTime,moEndTime,
					checkStartTime,checkTimeEnd,cardStatus,isemployee,hasWork);
			fundManagementService.changeNumToName(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	public String exportUserBankList(){
		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String realName = Convert.strToStr(paramMap.get("realName"), null);
		String userType = Convert.strToStr(paramMap.get("userType"), null);
		//
		String checkUser = Convert.strToStr(paramMap.get("checkUser"), null);
		String moStartTime = Convert.strToStr(paramMap.get("modifiedTimeStart"), null);
		String moEndTime = Convert.strToStr(paramMap.get("modifiedTimeEnd"), null);
		String checkStartTime = Convert.strToStr(paramMap.get("checkTimeStart"), null);
		String checkTimeEnd = Convert.strToStr(paramMap.get("checkTimeEnd"), null);
		String cardStatus = Convert.strToStr(paramMap.get("cardStatus"), null);
		String isemployee = Convert.strToStr(paramMap.get("isemployee"), null);
		String hasWork = Convert.strToStr(paramMap.get("hasWork"), null);
		
		moEndTime = FrontMyPaymentAction.changeEndTime(moEndTime);
		checkTimeEnd = FrontMyPaymentAction.changeEndTime(checkTimeEnd);
		
		Long checkUserId = -100L;
		if(checkUser!=null){
			checkUserId = Convert.strToLong(checkUser, -100L);
		}
		try {
			Admin admin = (Admin) session().getAttribute(
					IConstants.SESSION_ADMIN);
			//加载银行卡信息
			pageBean.pageNum = 1;
			pageBean.setPageSize(50000);
			fundManagementService.queryBankCardInfos(pageBean,userName,realName,userType,checkUserId,moStartTime,moEndTime,
					checkStartTime,checkTimeEnd,cardStatus,isemployee,hasWork);
			fundManagementService.changeNumToName(pageBean);
            if (pageBean.getPage() == null) {
                getOut()
                        .print(
                                "<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut()
                        .print(
                                "<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }

            
            HSSFWorkbook wb = ExcelUtils.exportExcel("银行卡信息", pageBean
                                                     .getPage(), new String[] { "用户名", "用户类型（1:个人，2:企业）", "开户名称", "开户行", "支行",
                                                     "卡号", "省","市","审核人", "提交时间", "审核时间", "内部员工", "状态(1:成功,2:审核中,3:失败)" },
                                                     new String[] { "username", "userType", "realName", "bankName", "branchBankName",
                                                             "cardNo", "province", "city", "checkUser", "commitTime", "checkTime",
                                                             "isemployee" , "cardStatus"});
            this.export(wb, new Date().getTime() + ".xls");
            // 系统操作日志
            operationLogService.addOperationLog("t_bankcard_lists",
                    admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
                    0, "导出银行卡信息", 2);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String queryUserModifiyBankList() throws Exception{
		String userName = Convert.strToStr(paramMap.get("userName"), null);
		String realName = Convert.strToStr(paramMap.get("realName"), null);
		//username  需要转换成 id 去搜条件
		String checkUser = Convert.strToStr(paramMap.get("checkUser"), null);
		String cStartTime = Convert.strToStr(paramMap.get("commitTimeStart"), null);
		String cEndTime = Convert.strToStr(paramMap.get("commitTimeEnd"), null);
		int cardStatus = Convert.strToInt(paramMap.get("cardStatus"), -1);
		
		cEndTime = FrontMyPaymentAction.changeEndTime(cEndTime);
		
		Long checkUserId = -100L;
		if(checkUser!=null){
			checkUserId = Convert.strToLong(checkUser, -100L);
		}
		try {
			//加载银行卡信息
			fundManagementService.queryModifyBankCardInfos(pageBean,userName,realName,checkUserId,
					cStartTime,cEndTime,cardStatus);
			
			fundManagementService.changeNumToName(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	
	
	public String queryOneBankCardInfo() throws Exception{
		Long bankId = request.getString("bankId")==null?-1:request.getLong("bankId", -1);
		try {
		    List<Map<String, Object>>  provinceList = regionService.queryRegionList(-1L, 1L, 1);
		    session().setAttribute("provinceList", provinceList);
		    List<Map<String, Object>>  cityList = regionService.queryRegionList(-1L, -1L, 2);
		    session().setAttribute("cityList", cityList);
			//加载银行卡信息
			paramMap = fundManagementService.queryOneBankCardInfo( bankId);
			if(paramMap != null && paramMap.size() > 0){
				if(paramMap.get("mobilePhone") == null ||
						paramMap.get("mobilePhone").trim().equals("")){
					paramMap.put("mobilePhone", paramMap.get("cellPhone"));
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
		
	}
	
	/**
	 * 银行卡变更
	 * @return
	 * @throws Exception 
	 */
	public String queryModifyBankInfo() throws Exception{
		Long bankId = request.getString("bankId")==null?-1:request.getLong("bankId", -100);
		
		try {
			paramMap = fundManagementService.queryOneBankCardInfo( bankId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		request().setAttribute("bankId", bankId);
		return SUCCESS;
	}
	
	
	   // ======地区列表
    public String ajaxqueryRegion() throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        // Long regionId = Convert.strToLong(paramMap.get("regionId"), -1);
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
	
	/**
	 * 银行卡审核
	 * @return
	 * @throws Exception 
	 */
	public String updateUserBankInfo() throws Exception{
		Long checkUserId = paramMap.get("userName")==null?
				null:Convert.strToLong(paramMap.get("userName"), -100);
		String remark = paramMap.get("remark")==null?null:Convert.strToStr(paramMap.get("remark"), null);
		Integer check_result = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		Long bankId = paramMap.get("bankId")==null?null:Convert.strToLong(paramMap.get("bankId"), -1);	
		
		 int province = Convert.strToInt(paramMap.get("provinceId"), -1);
	     int city = Convert.strToInt(paramMap.get("cityId"), -1);
		
		Admin  admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		try{
		    Long result = fundManagementService.updateBankInfo(admin.getId(), bankId, remark, check_result,admin.getUserName(),admin.getLastIP(),province,city);
			if(result < 0){
				JSONUtils.printStr("4");
				return null;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		
		return SUCCESS;
	}
	
	/**
	 * 批量审核银行卡
	 * @auth hejiahua
	 * @return 
	 * String
	 * @throws Exception 
	 * @exception 
	 * @date:2014-12-4 下午2:12:26
	 * @since  1.0.0
	 */
	public String batchUpdateUserBankInfo() throws Exception{
	    String linksIds = request.getString("commonId");
        String[] allIds = linksIds.split(",");// 进行全选审核的时候获得多个id值
        String remark = request.getString("remark");
        Admin  admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
        List<Map<String, Object>>  map = new ArrayList<Map<String,Object>>();
       for (int i = 0; i < allIds.length; i++) {
           long bankId = Convert.strToLong(allIds[i], -1L);
           Map<String, Object> editResult = new HashMap<String, Object>();
           Long result = fundManagementService.updateBankInfo(admin.getId(),bankId ,StringUtils.isBlank(remark)? "审核通过":remark, 1,admin.getUserName(),admin.getLastIP());
           if (result>0) {//成功
                   editResult.put("result", "第"+(i+1)+"条数据审核成功,对应ID："+bankId);
            }else {
                editResult.put("result", "第"+(i+1)+"条数据审核失败,对应ID："+bankId);
            }
           map.add(editResult);
       }
       log.info(map.toString());
       /*try {
           HSSFWorkbook wb = ExcelUtils.exportExcel("批量审核银行卡",
                                                    map,
                                                    new String[] { "审核结果" }, new String[] {"result"});
                                            this.export(wb, "审核结果："+new Date().getTime() + ".xls");
                                            operationLogService.addOperationLog("v_t_employee",
                                                    admin.getUserName(), IConstants.EXCEL, admin.getLastIP(),
                                                    0, "公司员工列表", 2);
       }
       catch (Exception e) {
          e.printStackTrace();
          getOut()
          .print(
                  "<script>alert(' 导出记录出现异常! ');window.history.go(-1);</script>");
       }*/
       return null;
	}
	
	/**
	 * 银行卡变更审核
	 * @return
	 * @throws Exception 
	 */
	public String updateUserModifyBank() throws Exception{
		Long checkUserId = paramMap.get("userName")==null?
				null:Convert.strToLong(paramMap.get("userName"), -100);
		String remark = paramMap.get("remark")==null?null:Convert.strToStr(paramMap.get("remark"), null);
		Integer check_result = paramMap.get("status")==null?-100:
			Convert.strToInt(paramMap.get("status"), -100);
		Long bankId = paramMap.get("bankId")==null?null:Convert.strToLong(paramMap.get("bankId"), -1);		
		try{
		 Admin admin  = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			@SuppressWarnings("unused")
			Long result = -1L;
			if(check_result == 1){//审核成功
				result = fundManagementService.updateModifyBankInfo(checkUserId, 
						bankId, remark, check_result, paramMap.get("modifiedBankName"), 
						paramMap.get("modifiedBranchBankName"), paramMap.get("modifiedCardNo"),
						paramMap.get("modifiedTime"), true);
				//添加操作日志
				operationLogService.addOperationLog("t_bankcard", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "银行卡变更审核成功", 2);
			}else{//审核失败
				result = fundManagementService.updateModifyBankInfo(checkUserId, 
						bankId, remark, check_result, paramMap.get("modifiedBankName"), 
						paramMap.get("modifiedBranchBankName"), paramMap.get("modifiedCardNo"),
						paramMap.get("modifiedTime"), false);
				//添加操作日志
				operationLogService.addOperationLog("t_bankcard", admin.getUserName(), IConstants.UPDATE, admin.getLastIP(), 0, "银行卡变更审核失败", 2);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}
	
	/**
	 *功能：修改银行卡信息
	 * @return
	 */
	public String updateBankInfo(){
		String bankName = paramMap.get("bankName");
		String branchBankName = paramMap.get("branchBankName");
		String bankId = paramMap.get("bankId");
		Map <String,String>map = new HashMap<String,String>();
		
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String adminName = admin.getUserName();
		map.put("bankName", bankName);
		map.put("branchBankName", branchBankName);
		map.put("adminName", adminName);
		map.put("id", bankId);
		try{
		    fundManagementService.updateBankInfoForPay(map);
			JSONUtils.printStr("4");
			return null;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		
		return SUCCESS;
	}
	
	
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

/*	public UserBankManagerService getUserBankService() {
		return userBankService;
	}

	public void setUserBankService(UserBankManagerService userBankService) {
		this.userBankService = userBankService;
	}*/

	public List<Map<String, Object>> getCheckers() throws Exception {
		if(checkers == null){
			//加载审核人员列表
			checkers = adminService.queryAdministors(IConstants.ADMIN_ENABLE);
		}
		return checkers;
	}

	public void setCheckers(List<Map<String, Object>> checkers) {
		this.checkers = checkers;
	}
	
}
