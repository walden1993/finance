package com.sp2p.service.admin;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.springframework.web.context.ContextLoader;

import com.opensymphony.xwork2.util.finder.ClassFinder.Info;
import com.shove.Convert;
import com.shove.Xml;
import com.shove.base.BaseService;
import com.shove.config.XinfupayConfig;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.shove.web.util.FileUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.admin.AccountPaymentDao;
import com.sp2p.dao.admin.FIManageDao;
import com.sp2p.dao.admin.RiskManageDao;
import com.sp2p.dao.admin.UserBankManagerDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.FinanceService;
import com.sp2p.service.SelectedService;
import com.sp2p.util.DateUtil;
import com.sp2p.util.HttpClientHelp;

public class FundManagementService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);

	private FIManageDao fiManageDao;
	private FundRecordDao fundRecordDao;
	private AccountUsersDao accountUsersDao;
	private SelectedService selectedService;
	private UserBankManagerDao userBankDao;
	private RiskManageDao riskManageDao;
	private AccountPaymentDao accountPaymentDao;
	private OperationLogDao operationLogDao;
	private List<Map<String, Object>> rechargeTypes;
	private List<Map<String, Object>> results;
	private List<Map<String,Object>> checkers;
	private AdminService adminService;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public FIManageDao getFiManageDao() {
		return fiManageDao;
	}

	public void setFiManageDao(FIManageDao fiManageDao) {
		this.fiManageDao = fiManageDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public UserBankManagerDao getUserBankDao() {
		return userBankDao;
	}

	public void setUserBankDao(UserBankManagerDao userBankDao) {
		this.userBankDao = userBankDao;
	}

	public RiskManageDao getRiskManageDao() {
		return riskManageDao;
	}

	public void setRiskManageDao(RiskManageDao riskManageDao) {
		this.riskManageDao = riskManageDao;
	}

	public AccountPaymentDao getAccountPaymentDao() {
		return accountPaymentDao;
	}

	public void setAccountPaymentDao(AccountPaymentDao accountPaymentDao) {
		this.accountPaymentDao = accountPaymentDao;
	}

	/**
	 * 财务管理 充值记录查询
	 * 
	 * @return
	 */
	public Map<String, String> queryRechargeRecordList(PageBean<Map<String, Object>> pageBean,
			String userName, String userType,String reStartTime, String reEndTime,
			int rechargeType, Integer result,String orderId) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		reStartTime = Utility.filteSqlInfusion(reStartTime);
		reEndTime = Utility.filteSqlInfusion(reEndTime);
		userType = Utility.filteSqlInfusion(userType);
		
		Connection conn = MySQL.getConnection();
		// 手机变更状态不为空
		String command = "";
		if (userName != null&&!userName.equals("")) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (userType != null&&!userType.equals("") && !"-1".equals(userType)) {
			command += " and userType = '"
					+ StringEscapeUtils.escapeSql(userType) + "' ";
		}
		if (rechargeType != -100) {//
			command += " and rechargeType =" + rechargeType;
		}
		if (reStartTime != null&&!reStartTime.equals("")) {
			command += " and rechargeTime >='"
				+ StringEscapeUtils.escapeSql(reStartTime) + "'";
		}
		if (reEndTime != null&&!reEndTime.equals("")) {
			command += " and rechargeTime <='"
				+ StringEscapeUtils.escapeSql(reEndTime) + "'";
		}
		if (result >= 0) {
			command += " and result =" + result;
		}
		if (StringUtils.isNotBlank(orderId)) {
            String [] arrays = orderId.split("_");
            for (int i = 0; i < arrays.length; i++) {
                if (i==0) {//日期
                    String date = arrays[i];
                }else if(i==1){//订单号
                    String id = arrays[i];
                    command+=" and  reId="+id;
                } else if(i==2){//用户
                    String userid = arrays[i];
                    command+=" and userId="+userid;
                }
            }
        }
		try {
			dataPage(conn, pageBean, "v_t_user_rechargedetails_list", "*",
					" order by id desc ", command);
			DataSet ds = MySQL.executeQuery(conn, "select sum(rechargeMoney) as rechargeMoneys," +
					" sum(realMoney) as realMoneys from v_t_user_rechargedetails_list where 1=1 " + command.toString());
			conn.commit();
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 最后一次充值记录
	 * queryRechargeDedatilLast
	 * @auth hejiahua
	 * @param userId
	 * @return 
	 * Map<String,String>
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-25 下午1:55:34
	 * @since  1.0.0
	 */
	public Map<String, String> queryRechargeDedatilLast(Long userId) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Map<String, String> map = new HashMap<String, String>();
	    try {
            DataSet ds = Database.executeQuery(conn, "SELECT * FROM t_recharge_detail WHERE rechargeTime = (SELECT MAX(trd.`rechargeTime`) FROM t_recharge_detail trd WHERE trd.`userId` = "+userId+" AND trd.`result`=1 AND trd.`rechargeType`=8)");
            map = BeanMapUtils.dataSetToMap(ds);
            //查询支付系统是否升级
            ds = Database.executeQuery(conn, "SELECT deleted,introduce FROM t_select a WHERE a.typeid=26 and selectKey='ZFR'");
            Map m = BeanMapUtils.dataSetToMap(ds);
            if (null != m && null!=m.get("deleted")){
            	if (null == map){
            		map = new HashMap<String, String>();
            	}
            	map.put("paySystemUpgrade", ""+m.get("deleted"));
            	map.put("upgradeNotes", ""+m.get("introduce"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	    return map;
	}
	
	public void changeNumToStr5(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		for (Map<String, Object> map : list) {
			
			
			
			if (map.get("userType").toString().equals("1")) {
				map.put("userType", "个人");
			} else if (map.get("userType").toString().equals("2")) {
				map.put("userType", "企业");
			}
			
			if (map.get("status")!=null) {
				if (map.get("status").toString().equals("1")) {
					map.put("status", "审核中");
				} else if (map.get("status").toString().equals("2")) {
					map.put("status", "已提现");
				} else if (map.get("status").toString().equals("3")) {
					map.put("status", "取消");
				} else if (map.get("status").toString().equals("4")) {
					map.put("status", "转账中");
				} else if (map.get("status").toString().equals("5")) {
					map.put("status", "失败");
				}
			}
			

		}

	}
	
	   public void changeNumToStr6(PageBean pageBean){
	        List<Map<String, String>> list = pageBean.getPage();
	        if (list != null) {
	            DecimalFormat format = new DecimalFormat("000000");
	            for (Map<String, String> map : list) {
	                String data = DateUtil.YYYYMMDD.format(new Date());
	                String number = format.format(map.get("id"));
	                map.put("name", "T"+data+number);
	                map.put("market", "无");
	                map.put("fapiao", "1");
	                
	                if (StringUtils.isNotBlank(map.get("province")) && !"内蒙古宁夏新疆西藏广西".contains(map.get("province"))) {
	                    map.put("province", StringUtils.isNotBlank(map.get("province"))?(map.get("province")+((StringUtils.isNotBlank(map.get("province")) &&  "北京上海天津重庆".contains(map.get("province"))?"市":"省"))):"");
                    }
	                
	                map.put("city", StringUtils.isNotBlank(map.get("city"))?(map.get("city")+"市"):"");
	            }
	        }
	    }

	public void queryRechargeFirstList(PageBean<Map<String, Object>> pageBean,
			String userName,String userType, String reStartTime, String reEndTime,
			int rechargeType) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		reStartTime = Utility.filteSqlInfusion(reStartTime);
		reEndTime = Utility.filteSqlInfusion(reEndTime);
		userType = Utility.filteSqlInfusion(userType);
		
		Connection conn = MySQL.getConnection();
		// 手机变更状态不为空
		String command = "";
		if (userName != null) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (userType != null && !"-1".equals(userType)) {
			command += " and userType = '"
					+ StringEscapeUtils.escapeSql(userType) + "' ";
		}
		if (rechargeType != -100) {//
			command += " and rechargeType =" + rechargeType;
		}
		if (reStartTime != null) {
			command += " and first_recharge >='"
					+ StringEscapeUtils.escapeSql(reStartTime) + "'";
		}
		if (reEndTime != null) {
			command += " and first_recharge <='"
					+ StringEscapeUtils.escapeSql(reEndTime) + "'";
		}
		try {
			dataPage(conn, pageBean, "v_t_user_rechargefirst_lists", "*", "",
					command);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneFirstChargeDetails(Long rechargeId,
			boolean first) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			if (first) {
				return fiManageDao.queryOneFirstChargeDetails(conn, rechargeId);
			} else {
				return fiManageDao.queryOneChargeDetails(conn, rechargeId);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryAllWithdrawList(PageBean<Map<String, Object>> pageBean,
			String userName,String realName,String userType, String startTime, String endTime, Integer status)
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		realName = Utility.filteSqlInfusion(realName);
		userType = Utility.filteSqlInfusion(userType);
		Connection conn = MySQL.getConnection();
		// 手机变更状态不为空
		String command = "";
		if (StringUtils.isNotBlank(userName)) {
			command += " and name like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (StringUtils.isNotBlank(realName)) {
			command += " and realName like '%"
					+ StringEscapeUtils.escapeSql(realName) + "%' ";
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			command += " and userType ='"
					+ StringEscapeUtils.escapeSql(userType) + "'";
		}
		if (status > 0) {//
			command += " and status =" + status;
		}
		if (StringUtils.isNotBlank(startTime)) {
			command += " and applyTime >='"
					+ StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			command += " and applyTime <='"
					+ StringEscapeUtils.escapeSql(endTime) + "'";
		}
		try {
			dataPage(conn, pageBean, "v_t_user_withdraw_lists", "*", " order by applyTime desc",
					command);
			DataSet ds = MySQL.executeQuery(conn, "select sum(sum) as sums,sum(realMoney) as realMoneys,sum(poundage) as poundages from v_t_user_withdraw_lists where  1=1 " + command.toString());
			conn.commit();
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
				
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public Map<String, String> queryAllWithdrawList(PageBean<Map<String, Object>> pageBean,
			Map map)
			throws Exception {
		String userName = Utility.filteSqlInfusion((String)map.get("userName"));
		String startTime = Utility.filteSqlInfusion((String)map.get("startTime"));
		String endTime = Utility.filteSqlInfusion((String)map.get("endTime"));
		String realName = Utility.filteSqlInfusion((String)map.get("realName"));
		String userType = Utility.filteSqlInfusion((String)map.get("userType"));
		String firstAudits = Utility.filteSqlInfusion((String)map.get("firstAudits"));
		String firstAudite = Utility.filteSqlInfusion((String)map.get("firstAudite"));
		String checkAudits = Utility.filteSqlInfusion((String)map.get("checkAudits"));
		String checkAudite = Utility.filteSqlInfusion((String)map.get("checkAudite"));
		
		int status = (Integer)map.get("status");
		Connection conn = MySQL.getConnection();
		// 手机变更状态不为空
		String command = "";
		if (StringUtils.isNotBlank(userName)) {
			command += " and name like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (StringUtils.isNotBlank(realName)) {
			command += " and realName like '%"
					+ StringEscapeUtils.escapeSql(realName) + "%' ";
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			command += " and userType ='"
					+ StringEscapeUtils.escapeSql(userType) + "'";
		}
		if (status > 0) {//
			command += " and status =" + status;
		}
		if (StringUtils.isNotBlank(startTime)) {
			command += " and applyTime >='"
					+ StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			command += " and applyTime <='"
					+ StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (StringUtils.isNotBlank(firstAudits)) {
			command += " and firstCheck >='"
					+ StringEscapeUtils.escapeSql(firstAudits) + "'";
		}
		if (StringUtils.isNotBlank(firstAudite)) {
			command += " and firstCheck <='"
					+ StringEscapeUtils.escapeSql(firstAudite) + " 23:59:59'";
		}
		if (StringUtils.isNotBlank(checkAudits)) {
			command += " and checkTime >='"
					+ StringEscapeUtils.escapeSql(checkAudits) + "'";
		}
		if (StringUtils.isNotBlank(checkAudite)) {
			command += " and checkTime <='"
					+ StringEscapeUtils.escapeSql(checkAudite) + " 23:59:59'";
		}
		
		try {
			dataPage(conn, pageBean, "v_t_user_withdraw_lists", "*", " order by applyTime desc",
					command);
			DataSet ds = MySQL.executeQuery(conn, "select sum(sum) as sums,sum(realMoney) as realMoneys,sum(poundage) as poundages from v_t_user_withdraw_lists where  1=1 " + command.toString());
			conn.commit();
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
				
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneWithdraw(Long wid) throws Exception,
			DataException {
		Connection conn = MySQL.getConnection();
		// 手机变更状态不为空
		try {
			return fiManageDao.queryOneWithdraw(conn, wid);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryUserCashList(PageBean<Map<String, Object>> pageBean,
			String userName,String realName,String userType) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		userType = Utility.filteSqlInfusion(userType);
		Connection conn = MySQL.getConnection();
		String command = "";
		String command2 = " where 1=1  ";
		if (userName != null) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'";
			command2 += " and u.username like '%"
				+ StringEscapeUtils.escapeSql(userName.trim()) + "%'";
		}
		
		if (realName != null) {
			command += " and realname like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'";
			command2 += " and realname like '%"
				+ StringEscapeUtils.escapeSql(realName.trim()) + "%'";
		}
		
		if(userName == null){
			
			if (userType != null && !"-1".equals(userType)) {
				command += " and userType ='"
						+ StringEscapeUtils.escapeSql(userType.trim()) + "'";
				command2 += " and u.userType ='"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "'";
			}
			
		}else{
			
			if (userType != null && !"-1".equals(userType)) {
				command += " and userType ='"
						+ StringEscapeUtils.escapeSql(userType.trim()) + "'";
				command2 += " and u.userType ='"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "'";
			}
			
		}
		
		StringBuffer cmd = new StringBuffer();
		cmd
				.append("(select a.id as userId,a.username as username,a.userType as userType,a.orgName as orgName,IFNULL(f.forRePaySum,0) as dueoutSum,a.usableSum,a.freezeSum,");
		cmd
				.append("round(sum(IFNULL(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest,0)),2) as dueinSum,d.realName realName from t_user a left join t_invest b on a.id = b.investor ");
		cmd.append(" left join t_person d on d.userId=a.id left join ");
		cmd
				.append("(select forRePaySum,publisher from (select sum(IFNULL((c.stillPrincipal+c.stillInterest-c.hasPI+c.lateFI-c.hasFI),0)) as forRePaySum,d.publisher  from t_repayment c left join t_borrow d on c.borrowId = d.id where c.repayStatus = 1 GROUP BY d.publisher) t) f");
		cmd
				.append(" on f.publisher = a.id  group by userId,a.usableSum,a.freezeSum,f.forRePaySum,d.realName,username,userType) u");
		try {
			dataPage(conn, pageBean, cmd.toString(), "*", "", command);
			DataSet ds = MySQL.executeQuery(conn, "select sum(u.dueoutSum) as dueoutSums, sum(u.usableSum) as usableSums," +
					" sum(u.freezeSum) as freezeSums, sum(u.dueinSum) as dueinSums from "+cmd.toString()+command2);
			conn.commit();
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryBackCashList(PageBean<Map<String, Object>> pageBean,
			Long userId, String checkUser, String startTime, String endTime,
			Integer type, String uname,String userType) throws Exception {
		checkUser = Utility.filteSqlInfusion(checkUser);
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		uname = Utility.filteSqlInfusion(uname);
		userType = Utility.filteSqlInfusion(userType);
		
		Connection conn = MySQL.getConnection();

		try {
			String condition = " ";
			if (userId != -100) {
				condition += " and userId=" + userId;
			}
			if (StringUtils.isNotBlank(checkUser)) {
				condition += " and userName like '%"
						+ StringEscapeUtils.escapeSql(checkUser) + "%'";
			}
			if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
				condition += " and userType = '"
						+ StringEscapeUtils.escapeSql(userType) + "'";
			}
			if (StringUtils.isNotBlank(startTime)) {
				condition += " and checkTime >= '"
						+ StringEscapeUtils.escapeSql(startTime) + "'";
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition += " and checkTime <= '"
						+ StringEscapeUtils.escapeSql(endTime) + "'";
			}
			if (type > 0) {
				condition += " and type = " + type;
			}
			if (StringUtils.isNotBlank(uname)) {
				condition += " and uname like '%"
						+ StringEscapeUtils.escapeSql(uname) + "%'";
			}
			dataPage(conn, pageBean, "v_t_user_backrw_lists", "*",
					" order by checkTime desc", condition);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long addBackR(Long userId, Long adminId, Integer type, double money,
			String remark, Date date, String fundMode, String addIP,
			String userName, String remarks) throws Exception {
		remark = Utility.filteSqlInfusion(remark);
		fundMode = Utility.filteSqlInfusion(fundMode);
		addIP = Utility.filteSqlInfusion(addIP);
		userName = Utility.filteSqlInfusion(userName);
		remarks = Utility.filteSqlInfusion(remarks);
		
		Connection conn = MySQL.getConnection();
		Long ret = -1L;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_useraddmoneymanual(conn, ds, outParameterValues,
					userId, type, money, remarks,addIP, adminId, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret > 0) {
				// 发送通知，通过通知模板
				Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader
						.getCurrentWebApplicationContext()
						.getServletContext()
						.getAttribute(
								IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

				Map<String, String> noticeMap = new HashMap<String, String>();
				// 消息模版
				// 站内信
				String informTemplate = informTemplateMap.get(
						IInformTemplateConstants.HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {// 
					informTemplate = informTemplateMap.get(
							IInformTemplateConstants.HAND_WITHDRAW).toString();
				}
				informTemplate = informTemplate.replace("date", DateUtil
						.dateToString((new Date())));
				Double double1 = new Double(22);
				informTemplate = informTemplate.replace("money", money + "");
				informTemplate = informTemplate.replace("remark", remark);
				noticeMap.put("mail", informTemplate);

				// 邮件
				String e_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.E_HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {// 
					e_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.E_HAND_WITHDRAW)
							.toString();
				}
				e_informTemplate = e_informTemplate.replace("date", DateUtil
						.dateToString((new Date())));
				e_informTemplate = e_informTemplate.replace("money", money + "");
				e_informTemplate = e_informTemplate.replace("remark", remark);
				e_informTemplate = e_informTemplate.replace("userName", userName);
				noticeMap.put("email", e_informTemplate);

				// 短信
				String s_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.S_HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {// 
					s_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.S_HAND_WITHDRAW)
							.toString();
				}
				s_informTemplate = s_informTemplate.replace("userName",
						userName);
				s_informTemplate = s_informTemplate.replace("date", DateUtil
						.dateToString((new Date())));
				s_informTemplate = s_informTemplate
						.replace("money", money + "");
				s_informTemplate = s_informTemplate.replace("remark", remark);
				noticeMap.put("note", e_informTemplate);

				selectedService.sendNoticeMSG(conn, userId, "充值成功", noticeMap,
						IConstants.NOTICE_MODE_5);
				conn.commit();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return ret;
	}
	
	/**
	 * addBackR:活动程序调用. <br/>
	 * TODO 程序连接调用.<br/>
	 * TODO a>b>c<br/>
	 * TODO 在一个数据库连接.<br/>
	 * TODO 非财务操作，谨慎使用.<br/>
	 *
	 * @author hjh
	 * @param conn 数据库连接
	 * @param userId 用户ID
	 * @param adminId 操作员ID
	 * @param type 充值类型
	 * @param money 充值金额
	 * @param remark 备注
	 * @param date 日期
	 * @param fundMode 充值方式
	 * @param addIP IP
	 * @param userName 用户名
	 * @param remarks 备注
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public Long addBackR(Connection conn,Long userId, Long adminId, Integer type, double money,
			String remark, Date date, String fundMode, String addIP,
			String userName, String remarks) throws Exception {
		remark = Utility.filteSqlInfusion(remark);
		fundMode = Utility.filteSqlInfusion(fundMode);
		addIP = Utility.filteSqlInfusion(addIP);
		userName = Utility.filteSqlInfusion(userName);
		remarks = Utility.filteSqlInfusion(remarks);
		
		Long ret = -1L;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_useraddmoneymanual(conn, ds, outParameterValues,
					userId, type, money, remarks,addIP, adminId, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret > 0) {
				// 发送通知，通过通知模板
				Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader
						.getCurrentWebApplicationContext()
						.getServletContext()
						.getAttribute(
								IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

				Map<String, String> noticeMap = new HashMap<String, String>();
				// 消息模版
				// 站内信
				String informTemplate = informTemplateMap.get(
						IInformTemplateConstants.HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {// 
					informTemplate = informTemplateMap.get(
							IInformTemplateConstants.HAND_WITHDRAW).toString();
				}
				informTemplate = informTemplate.replace("date", DateUtil
						.dateToString((new Date())));
				Double double1 = new Double(22);
				informTemplate = informTemplate.replace("money", money + "");
				informTemplate = informTemplate.replace("remark", remark);
				noticeMap.put("mail", informTemplate);

				// 邮件
				String e_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.E_HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {// 
					e_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.E_HAND_WITHDRAW)
							.toString();
				}
				e_informTemplate = e_informTemplate.replace("date", DateUtil
						.dateToString((new Date())));
				e_informTemplate = e_informTemplate.replace("money", money + "");
				e_informTemplate = e_informTemplate.replace("remark", remark);
				e_informTemplate = e_informTemplate.replace("userName", userName);
				noticeMap.put("email", e_informTemplate);

				// 短信
				String s_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.S_HAND_RECHARGE).toString();
				if (type == IConstants.WITHDRAW) {// 
					s_informTemplate = informTemplateMap.get(
							IInformTemplateConstants.S_HAND_WITHDRAW)
							.toString();
				}
				s_informTemplate = s_informTemplate.replace("userName",
						userName);
				s_informTemplate = s_informTemplate.replace("date", DateUtil
						.dateToString((new Date())));
				s_informTemplate = s_informTemplate
						.replace("money", money + "");
				s_informTemplate = s_informTemplate.replace("remark", remark);
				noticeMap.put("note", e_informTemplate);

				/*selectedService.sendNoticeMSG(conn, userId, "充值成功", noticeMap,
						IConstants.NOTICE_MODE_5);*/
				conn.commit();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} 
		return ret;
	}

	public Long addBackW(Long userId, Long adminId, Integer type, double money,
			String remark, Date date, String fundMode, String addIP,
			String userName, String remarks) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			// 更新用户金额
			long result2 = fiManageDao.updateFundrecord(conn, userId, money,
					type);
			if (result2 <= 0) {
				conn.rollback();
				return result2;
			}

			result = fiManageDao.addBackR_W(conn, userId, adminId, type, money,
					remark, date);
			if (result <= 0) {
				conn.rollback();
				return result;
			}

			// 查询待收金额
			Map<String, String> dueinMap = fiManageDao.queryDueInSum(conn,
					userId);
			double dueinSum = 0d;
			if (dueinMap != null && dueinMap.size() > 0) {
				dueinSum = Convert.strToDouble(dueinMap.get("forPI"), 0);
			}
			long result3 = -1;
			result3 = fundRecordDao.addFundRecord(conn, userId, fundMode,
					money, Convert.strToDouble(dueinMap.get("usableSum"), 0d),
					Convert.strToDouble(dueinMap.get("freezeSum"), 0d),
					dueinSum, -1L, remarks, 0.0, money, -1, -1, 502, 0.0);

			if (result3 <= 0) {
				conn.rollback();
				return result3;
			}
			// 发送通知，通过通知模板
			Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

			Map<String, String> noticeMap = new HashMap<String, String>();

			// 消息模版
			// 站内信
			String informTemplate = informTemplateMap.get(
					IInformTemplateConstants.HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				informTemplate = informTemplateMap.get(
						IInformTemplateConstants.HAND_WITHDRAW).toString();
			}
			informTemplate = informTemplate.replace("date", DateUtil
					.dateToString((new Date())));
			informTemplate = informTemplate.replace("money", money + "");
			informTemplate = informTemplate.replace("remark", remark);
			noticeMap.put("mail", informTemplate);

			// 邮件
			String e_informTemplate = informTemplateMap.get(
					IInformTemplateConstants.E_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				e_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.E_HAND_WITHDRAW).toString();
			}
			e_informTemplate = e_informTemplate.replace("date", DateUtil
					.dateToString((new Date())));
			e_informTemplate = e_informTemplate.replace("money", money + "");
			e_informTemplate = e_informTemplate.replace("remark", remark);
			noticeMap.put("email", e_informTemplate);

			// 短信
			String s_informTemplate = informTemplateMap.get(
					IInformTemplateConstants.S_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				s_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.S_HAND_WITHDRAW).toString();
			}
			s_informTemplate = s_informTemplate.replace("userName", userName);
			s_informTemplate = s_informTemplate.replace("date", DateUtil
					.dateToString((new Date())));
			s_informTemplate = s_informTemplate.replace("money", money + "");
			s_informTemplate = s_informTemplate.replace("remark", remark);
			noticeMap.put("note", e_informTemplate);

			selectedService.sendNoticeMSG(conn, userId, "扣费成功", noticeMap,
					IConstants.NOTICE_MODE_5);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Long addBackW(Connection conn,Long userId, Long adminId, Integer type, double money,
			String remark, Date date, String fundMode, String addIP,
			String userName, String remarks) throws Exception {
		Long result = -1L;
		try {
			// 更新用户金额
			long result2 = fiManageDao.updateFundrecord(conn, userId, money,
					type);
			if (result2 <= 0) {
				conn.rollback();
				return result2;
			}

			result = fiManageDao.addBackR_W(conn, userId, adminId, type, money,
					remark, date);
			if (result <= 0) {
				conn.rollback();
				return result;
			}

			// 查询待收金额
			Map<String, String> dueinMap = fiManageDao.queryDueInSum(conn,
					userId);
			double dueinSum = 0d;
			if (dueinMap != null && dueinMap.size() > 0) {
				dueinSum = Convert.strToDouble(dueinMap.get("forPI"), 0);
			}
			long result3 = -1;
			result3 = fundRecordDao.addFundRecord(conn, userId, fundMode,
					money, Convert.strToDouble(dueinMap.get("usableSum"), 0d),
					Convert.strToDouble(dueinMap.get("freezeSum"), 0d),
					dueinSum, -1L, remarks, 0.0, money, -1, -1, 502, 0.0);

			if (result3 <= 0) {
				conn.rollback();
				return result3;
			}
			// 发送通知，通过通知模板
			Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

			Map<String, String> noticeMap = new HashMap<String, String>();

			// 消息模版
			// 站内信
			String informTemplate = informTemplateMap.get(
					IInformTemplateConstants.HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				informTemplate = informTemplateMap.get(
						IInformTemplateConstants.HAND_WITHDRAW).toString();
			}
			informTemplate = informTemplate.replace("date", DateUtil
					.dateToString((new Date())));
			informTemplate = informTemplate.replace("money", money + "");
			informTemplate = informTemplate.replace("remark", remark);
			noticeMap.put("mail", informTemplate);

			// 邮件
			String e_informTemplate = informTemplateMap.get(
					IInformTemplateConstants.E_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				e_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.E_HAND_WITHDRAW).toString();
			}
			e_informTemplate = e_informTemplate.replace("date", DateUtil
					.dateToString((new Date())));
			e_informTemplate = e_informTemplate.replace("money", money + "");
			e_informTemplate = e_informTemplate.replace("remark", remark);
			noticeMap.put("email", e_informTemplate);

			// 短信
			String s_informTemplate = informTemplateMap.get(
					IInformTemplateConstants.S_HAND_RECHARGE).toString();
			if (type == IConstants.WITHDRAW) {// 扣费
				s_informTemplate = informTemplateMap.get(
						IInformTemplateConstants.S_HAND_WITHDRAW).toString();
			}
			s_informTemplate = s_informTemplate.replace("userName", userName);
			s_informTemplate = s_informTemplate.replace("date", DateUtil
					.dateToString((new Date())));
			s_informTemplate = s_informTemplate.replace("money", money + "");
			s_informTemplate = s_informTemplate.replace("remark", remark);
			noticeMap.put("note", e_informTemplate);

			/*selectedService.sendNoticeMSG(conn, userId, "扣费成功", noticeMap,
					IConstants.NOTICE_MODE_5);*/

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return result;
	}

	public Map<String, String> queryR_WInfo(Long rwId) throws Exception,
			DataException {
		Connection conn = MySQL.getConnection();

		try {
			return fiManageDao.queryR_WInfo(conn, rwId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 更新资金流动信息表
	 * 
	 * @param userId
	 * @param handleSum
	 * @param usableSum
	 * @return
	 */
	public Long updateFundrecord(long userId, double money, int type)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = fiManageDao.updateFundrecord(conn, userId, money, type);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public void queryUserFundRecordList(PageBean<Map<String, Object>> pageBean,
			Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			String command = " and userId=" + userId;
			dataPage(conn, pageBean, "v_t_user_fundrecord_lists", "*",
					" order by id desc", command);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryAllUserFundRecordList(
			PageBean<Map<String, Object>> pageBean, String userName,String userType)
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		userType = Utility.filteSqlInfusion(userType);
		Connection conn = MySQL.getConnection();
		StringBuffer cmd = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			cmd.append(" and b.username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%'");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			cmd.append(" and b.userType = '"
					+ StringEscapeUtils.escapeSql(userType) + "' ");
		}
		try {
			String fields = "a.id as id,a.userId,b.userType as userType,a.fundMode,a.handleSum,a.usableSum,a.freezeSum,a.dueinSum,a.trader,a.recordTime as recordTime,a.dueoutSum,a.totalSum,a.traderName,b.username  as username,a.spending  as spending ,a.income as income ";
			dataPage(
					conn,
					pageBean,
					"v_t_user_fundrecord_lists a LEFT JOIN t_user b on a.userId = b.id",
					fields, " order by recordTime desc ", cmd + "");
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 后台 提现列表
	 * 
	 * @param pageBean
	 * @param userName
	 * @throws Exception
	 * @throws Exception
	 */
	public void queryUserFundWithdrawInfo(
			PageBean<Map<String, Object>> pageBean, String userName,
			String startTime, String endTime, double sum, int status,
			long userId) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		
		Connection conn = MySQL.getConnection();
		// 手机变更状态为空
		String command = " and userId=" + userId;
		if (StringUtils.isNotBlank(userName)) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (sum > 0) {
			command += " and sum =" + sum;
		}
		if (StringUtils.isNotBlank(startTime)) {
			command += " and applyTime >='"
					+ StringEscapeUtils.escapeSql(startTime) + "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			command += " and applyTime <='"
					+ StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (status > 0) {// (1 默认审核中 2 已提现 3 取消 4转账中 5 失败)
			command += " and status =" + status;
		}
		try {
			dataPage(conn, pageBean, "v_t_user_fundwithdraw_lists", "*", "",
					command);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryUserFundRechargeInfo(
			PageBean<Map<String, Object>> pageBean, String startTime,
			String endTime, int status, long userId, int rechargeType)
			throws Exception {
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		
		Connection conn = MySQL.getConnection();
		// 手机变更状态为空
		String command = " and userId=" + userId;
		if (StringUtils.isNotBlank(startTime)) {
			command += " and rechargeTime >='"
					+ StringEscapeUtils.escapeSql(startTime)+ "'";
		}
		if (StringUtils.isNotBlank(endTime)) {
			command += " and rechargeTime <='"
					+ StringEscapeUtils.escapeSql(endTime) + "'";
		}
		if (status >= 0) {// (0 失败 1 成功)
			command += " and result =" + status;
		}
		if (rechargeType > 0) {// (1 失败 2 成功)
			command += " and type =" + rechargeType;
		}
		try {
			dataPage(conn, pageBean, "v_t_user_rechargeall_lists", "*", "",
					command);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 获得充值提现审核中的审核信息
	 * 
	 * @param userId
	 * @param money
	 * @param type
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public Map<String, String> queryUserRWInfo(long userId, int rs, int ws)
			throws Exception {
		Connection conn = MySQL.getConnection();

		try {
			Map<String, String> map = fiManageDao.querySuccessRecharge(conn,
					userId, rs);
			Map<String, String> mp = fiManageDao.querySuccessBid(conn, userId);

			Map<String, String> _map = fiManageDao.querySuccessWithdraw(conn,
					userId, ws);
			if (_map == null || _map.size() <= 0) {
				map = new HashMap<String, String>();
				map.put("w_total", "0.00");
			}

			if (map == null || map.size() <= 0) {
				_map.put("r_total", "0.00");

			} else {
				_map.put("r_total", map.get("r_total"));
			}
			if (mp == null || mp.size() <= 0) {
				_map.put("real_Amount", "0.00");

			} else {
				_map.put("real_Amount", mp.get("realAmount"));
			}
			conn.commit();
			return _map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 添加数据到资金记录表（转账成功的情况下）
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long addFundRecord(Long userId, Map<String, String> map)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = fundRecordDao.addFundRecord(conn, userId, map
					.get("fundMode"), Convert.strToDouble(map.get("handleSum"),
					0d), Convert.strToDouble(map.get("usableSum"), 0d), Convert
					.strToDouble(map.get("freezeSum"), 0d), Convert
					.strToDouble(map.get("dueinSum"), 0d), -1, map
					.get("remark"), 0.0, Convert.strToDouble(map
					.get("handleSum"), 0d), -1, -1, 801, Convert.strToDouble(
					map.get("dueOutSum"), 0d));
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public Map<String, String> queryTransStatus(Long wid) throws Exception {
		Connection conn = MySQL.getConnection();

		try {
			return fiManageDao.queryTransStatus(conn, wid);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> updateWithdraw(Long wid, double money,
			double poundage, int status, String remark, long adminId,
			Long userId, String ipAddress) throws Exception {
		remark = Utility.filteSqlInfusion(remark);
		ipAddress = Utility.filteSqlInfusion(ipAddress);
		
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_amount_withdraw_auth(conn, ds, outParameterValues,
					userId, adminId, wid, new BigDecimal(money),
					new BigDecimal(poundage), status, ipAddress, remark, "",
					-1, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", outParameterValues.get(0) + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (result <= 0) {
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
			conn = null;
			ds = null;
			outParameterValues = null;
		}
		return map;
	}

	/**
	 * 查询待收金额
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public Map<String, String> queryDueInSum(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();

		try {
			return fiManageDao.queryDueInSum(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> queryLastRecord() throws Exception {
		Connection conn = MySQL.getConnection();

		try {
			return fiManageDao.queryLastRecord(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> updateWithdrawTransfer(Long wid, Integer status,
			Long adminId, String ipAddress) throws Exception {
		ipAddress = Utility.filteSqlInfusion(ipAddress);
		
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_amount_withdraw_transfer(conn, ds, outParameterValues,
					adminId, wid, status, ipAddress, "", -1, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", outParameterValues.get(0) + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (result <= 0) {
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
			conn = null;
			ds = null;
			outParameterValues = null;
		}
		return map;
	}

	// userBank
	/**
	 * 查询银行卡信息
	 * 
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param checkUser
	 * @param moStartTime
	 * @param moEndTime
	 * @param checkStartTime
	 * @param checkEndTime
	 * @throws Exception
	 * @throws Exception
	 */
	public void queryBankCardInfos(PageBean<Map<String, Object>> pageBean,
			String userName, String realName,String userType, long checkUser,
			String moStartTime, String moEndTime, String checkStartTime,
			String checkEndTime,String cardStatus,String isemployee,String hasWork) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		moStartTime = Utility.filteSqlInfusion(moStartTime);
		moEndTime = Utility.filteSqlInfusion(moEndTime);
		checkStartTime = Utility.filteSqlInfusion(checkStartTime);
		checkEndTime = Utility.filteSqlInfusion(checkEndTime);
		userType = Utility.filteSqlInfusion(userType);
		Connection conn = MySQL.getConnection();
		// 手机变更状态为空
		String command = " and modifiedCardStatus is null  and isDelete = 2  ";
		if (StringUtils.isNotBlank(userName)) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (StringUtils.isNotBlank(realName)) {
			command += " and realName like '%"
					+ StringEscapeUtils.escapeSql(realName) + "%'";
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			command += " and userType ='"
					+ StringEscapeUtils.escapeSql(userType) + "'";
		}
		
		if (StringUtils.isNotBlank(isemployee) && !"-1".equals(isemployee)) {
			if ("1".equals(isemployee)) {
				command += " and isemployee is not null ";
			}else {
				command += " and isemployee is  null ";
			}
		}
		
		if (StringUtils.isNotBlank(hasWork) && !"-1".equals(hasWork)) {
			command += " and hasWork ='"
					+ StringEscapeUtils.escapeSql(hasWork) + "'";
		}
		
		if (checkUser != -100) {// admin的用户id目前为 -1
			command += " and checkUser =" + checkUser;
		}
		if (StringUtils.isNotBlank(moStartTime)) {
			command += " and commitTime >='"
					+ StringEscapeUtils.escapeSql(moStartTime) + "'";
		}
		if (StringUtils.isNotBlank(moEndTime)) {
			command += " and commitTime <='"
					+ StringEscapeUtils.escapeSql(moEndTime) + "'";
		}
		if (StringUtils.isNotBlank(checkStartTime)) {
			command += " and checkTime >='"
					+ StringEscapeUtils.escapeSql(checkStartTime) + "'";
		}
		if (StringUtils.isNotBlank(checkEndTime)) {
			command += " and checkTime <='"
					+ StringEscapeUtils.escapeSql(checkEndTime) + "'";
		}
		if (StringUtils.isNotBlank(cardStatus)) {
			command += " and cardStatus ='"
					+ StringEscapeUtils.escapeSql(cardStatus) + "'";
		}
		try {
			dataPage(conn, pageBean, "t_bankcard_lists", "*", " order by commitTime desc", command);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询修改银行卡信息
	 * 
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param checkUser
	 * @param cStartTime
	 * @param cEndTime
	 * @param cardStatus
	 * @throws Exception
	 * @throws Exception
	 */
	public void queryModifyBankCardInfos(
			PageBean<Map<String, Object>> pageBean, String userName,
			String realName, long checkUser, String cStartTime,
			String cEndTime, int cardStatus) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		cStartTime = Utility.filteSqlInfusion(cStartTime);
		cEndTime = Utility.filteSqlInfusion(cEndTime);
		
		
		Connection conn = MySQL.getConnection();
		// 手机变更状态不为空
		String command = " and modifiedCardStatus is not null";
		if (userName != null) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		if (realName != null) {
			command += " and realName like '%"
					+ StringEscapeUtils.escapeSql(realName) + "%'";
		}
		if (checkUser != -100) {// admin的用户id目前为 -1
			command += " and checkUser =" + checkUser;
		}
		if (cStartTime != null) {
			command += " and modifiedTime >='"
					+ StringEscapeUtils.escapeSql(cStartTime) + "'";
		}
		if (cEndTime != null) {
			command += " and modifiedTime <='"
					+ StringEscapeUtils.escapeSql(cEndTime) + "'";
		}
		if (cardStatus > -1) {
			command += " and modifiedCardStatus = " + cardStatus;
		}
		try {
			dataPage(conn, pageBean, "t_bankcard_lists", "*", " order by commitTime desc", command);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryOneBankCardInfo(Long bankId)
			throws Exception {
		Connection conn = MySQL.getConnection();

		try {
			return userBankDao.queryBankCardInfos(conn, bankId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	   /**
     * 审核 银行卡
     * 
     * @param checkUserId
     * @param bankId
     * @param remark
     * @param result
     * @param username
     * @param lastIP
     * @return
     * @throws Exception
     * @throws Exception
     */
    public Long updateBankInfo(Long checkUserId, Long bankId, String remark,
            Integer result, String username, String lastIP,int provinceId,int cityId) throws Exception {
        Connection conn = MySQL.getConnection();
        long resultId = -1L;
        try {

            resultId = userBankDao.updateBankInfo(conn, checkUserId, bankId,
                    remark, result,provinceId,cityId);
            if (resultId > 0) {
                operationLogDao.addOperationLog(conn, "t_bankcard", username,
                        IConstants.UPDATE, lastIP, 0, "银行卡审核", 2);
            }
            conn.commit();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();

            throw e;
        } finally {
            conn.close();
        }
        return resultId;
    }
	
	/**
	 * 审核 银行卡
	 * 
	 * @param checkUserId
	 * @param bankId
	 * @param remark
	 * @param result
	 * @param username
	 * @param lastIP
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public Long updateBankInfo(Long checkUserId, Long bankId, String remark,
			Integer result, String username, String lastIP) throws Exception {
		Connection conn = MySQL.getConnection();
		long resultId = -1L;
		try {

			resultId = userBankDao.updateBankInfo(conn, checkUserId, bankId,
					remark, result);
			if (resultId > 0) {
				operationLogDao.addOperationLog(conn, "t_bankcard", username,
						IConstants.UPDATE, lastIP, 0, "银行卡审核", 2);
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return resultId;
	}

	public Long updateModifyBankInfo(Long checkUserId, Long bankId,
			String remark, Integer result, String bankName,
			String branchBankName, String bankCardNo, String date,
			boolean success) throws Exception {
		Connection conn = MySQL.getConnection();
		Long _result = -1L;
		try {

			_result = userBankDao.updateModifyBankInfo(conn, checkUserId,
					bankId, remark, result, bankName, branchBankName,
					bankCardNo, date, success);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return _result;
	}

	public Map<String, String> queryOneBank(Long bankId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {

			return userBankDao.queryOneBankInfo(conn, bankId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long updateChangeBank(Long bankId, String bankName,
			String subBankName, String bankCard, int status, Date date,
			boolean modify) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {

			result = userBankDao.updateChangeBankInfo(conn, bankId, bankName,
					subBankName, bankCard, status, date, modify);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	// riskManage 风险保证金
	/**
	 * @MethodName: queryRiskByCondition
	 * @Param: RiskManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-7 上午11:22:29
	 * @Return:
	 * @Descb: 查询风险保障金列表
	 * @Throws:
	 */
	public void queryRiskByCondition(String resource, String timeStart,
			String timeEnd, String riskType, String userType,PageBean<Map<String, Object>> pageBean)
			throws Exception {
		resource = Utility.filteSqlInfusion(resource);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		riskType = Utility.filteSqlInfusion(riskType);
		userType = Utility.filteSqlInfusion(userType);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(resource)) {
			// condition.append(" and resource ='"+StringEscapeUtils.escapeSql(resource.trim())+"'");
			condition.append(" and resource  like '%"
					+ StringEscapeUtils.escapeSql(resource.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType  = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + " ' ");
		}
		if (StringUtils.isNotBlank(riskType)) {
			condition.append(" and riskType ='"
					+ StringEscapeUtils.escapeSql(riskType.trim()) + "'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and riskDate >='"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and riskDate <='"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		try {
			dataPage(conn, pageBean, "v_t_risk_list_h", "*", " ", condition
					.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @throws Exception
	 * @throws Exception
	 * @MethodName: queryRiskDetailById
	 * @Param: RechargeService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午11:10:26
	 * @Return:
	 * @Descb: 查询风险保证金详情
	 * @Throws:
	 */
	public Map<String, String> queryRiskDetailById(long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = riskManageDao.queryRiskDetailById(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: addRisk
	 * @Param: RiskManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:03:45
	 * @Return:
	 * @Descb: 手动添加风险保障金
	 * @Throws:
	 */
	public long addRisk(double amountDouble, long adminId, String remark)
			throws Exception {
		Connection conn = MySQL.getConnection();

		long result = -1L;
		try {
			Map<String, String> map = riskManageDao.queryRiskBalance(conn);
			String riskBalance = map.get("riskBalance");
			double riskBalanceDouble = Convert.strToDouble(
					riskBalance, 0);
			Date riskDate = new Date();
			String riskType = "收入";
			String resource = "手动添加风险保障金";
			result = riskManageDao.addRisk(conn, amountDouble, adminId, remark,
					riskBalanceDouble, riskDate, riskType, resource);
			if (result <= 0) {
				conn.rollback();
				return -1;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * @MethodName: deductedRisk
	 * @Param: RiskManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-7 下午03:03:33
	 * @Return:
	 * @Descb: 手动扣除风险保障金
	 * @Throws:
	 */
	public long deductedRisk(double amountDouble, long adminId, String remark)
			throws Exception {
		Connection conn = MySQL.getConnection();

		long result = -1L;
		try {
			Map<String, String> map = riskManageDao.queryRiskBalance(conn);
			String riskBalance = map.get("riskBalance");
			double riskBalanceDouble = Convert.strToDouble(
					riskBalance, 0);
			Date riskDate = new Date();
			String riskType = "支出";
			String resource = "手动扣除风险保障金";
			result = riskManageDao.deductedRisk(conn, amountDouble, adminId,
					remark, riskBalanceDouble, riskDate, riskType, resource);
			if (result <= 0) {
				conn.rollback();
				return -1;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	// AccountPayment 支付方式

	/**
	 * 增加支付方式
	 * 
	 * @param conn
	 * @param name
	 * @param nid
	 * @param status
	 * @param litpic
	 * @param style
	 * @param config
	 * @param description
	 * @param order
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public long addAccountPayment(String name, String nid, long status,
			String litpic, int style, String config, String description,
			int order) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = accountPaymentDao.addAccountPayment(conn, name, nid,
					status, litpic, style, config, description, order);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return result;

	}

	/**
	 * 分页查询所有
	 * 
	 * @param conn
	 * @param pageBean
	 * @throws Exception
	 * @throws Exception
	 */
	public void queryAccountPaymentPage(PageBean<Map<String, Object>> pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			accountPaymentDao.queryAccountPaymentPage(conn, pageBean);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 修改
	 * 
	 * @param conn
	 * @param id
	 * @param name
	 * @param nid
	 * @param status
	 * @param litpic
	 * @param style
	 * @param config
	 * @param description
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public long updateAccountPaymentPage(long id, String name, String litpic,
			String config, String description, int order) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = accountPaymentDao.updateAccountPaymentPage(conn, id, name,
					litpic, config, description, order);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public long deleteAccountPaymentPage(long id, long status) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = accountPaymentDao.deleteAccountPaymentPage(conn, id,
					status);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * 根据ID 查询
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public Map<String, String> queryAccountPaymentById(String nid)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = accountPaymentDao.queryAccountPaymentById(conn, nid);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 查询所有支付信息
	 * 
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAccountPaymentList() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			mapList = accountPaymentDao.queryAccountPaymentList(conn);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return mapList;
	}

	public void changeFigure(PageBean<Map<String, Object>> pageBean) {
		List<Map<String, Object>> ll = pageBean.getPage();
		if (ll != null && ll.size() > 0) {// result rechargeType 中文显示
			for (Map<String, Object> mp : ll) {
				if (mp.get("rechargeType") != null) {
					String typeId = mp.get("rechargeType").toString();
					for (Map<String, Object> cc : this.getRechargeTypes()) {
						if (cc.get("typeId").toString().equals(typeId)) {
							mp.put("rechargeType", cc.get("typeValue"));
							break;
						}
					}
				}
				if (mp.get("result") != null) {
					String resultId = mp.get("result").toString();
					for (Map<String, Object> cc : this.getResults()) {
						if (cc.get("resultId").toString().equals(resultId)) {
							if (resultId.equals(0 + "")) {// 失败
								mp.put("realMoney", "0.00");
							}
							mp.put("result", cc.get("resultValue"));
							break;
						}
					}
				}
			}
		}
	}

	public void changeFigure2(PageBean<Map<String, Object>> pageBean) {
		List<Map<String, Object>> list = pageBean.getPage();
		if (list != null) {

			for (Map<String, Object> map : list) {

				if (map.get("type").toString().equals("1")) {
					map.put("type", "手动充值");
				} else if(map.get("type").toString().equals("10")) {
					map.put("type", "涨薪宝转出");
				}else if(map.get("type").toString().equals("11")) {
					map.put("type", "涨薪宝转入");
				}else {
					map.put("type", "手动扣费");
				}
			}
		}
	}

	public void changeTraderName(PageBean<Map<String, Object>> pageBean) {
		List<Map<String, Object>> lists = pageBean.getPage();
		if (lists != null) {
			for (Map<String, Object> map : lists) {
				// 从后台管理员表中查询用户信息
				if (map.get("traderName") == null) {
					map.put("traderName", IConstants.OPERATOR_ONLINE);
				}
			}
		}
	}
	
	public void changeNumToName(PageBean<Map<String, Object>> pageBean) throws Exception {
	  checkers=getCheckers();
		List<Map<String,Object>> ll = pageBean.getPage();
		if(ll != null && ll.size() > 0){
			for(Map<String,Object> mp : ll){
				if(mp.get("checkUser") != null){
					String chechUser = mp.get("checkUser").toString();
					for(Map<String,Object> cc : checkers){
						if(cc.get("id").toString().equals(chechUser)){
							mp.put("checkUser", cc.get("userName"));
							break;
						}
					}
				}
			}
		}
	}
	
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

	public List<Map<String, Object>> getRechargeTypes() {
		if (rechargeTypes == null) {
			rechargeTypes = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("typeValue", "支付宝支付");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "环迅支付");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "国付宝");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("typeValue", "线下充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 5);
			mp.put("typeValue", "手工充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 6);
			mp.put("typeValue", "虚拟充值");
			rechargeTypes.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 7);
			mp.put("typeValue", "奖励充值");
			rechargeTypes.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 8);
			mp.put("typeValue", "融E付充值");
			rechargeTypes.add(mp);
			
			mp = new HashMap<String, Object>();
			mp.put("typeId", 10);
			mp.put("typeValue", "涨薪宝转出");
			rechargeTypes.add(mp);
		}
		return rechargeTypes;
	}

	public void setRechargeTypes(List<Map<String, Object>> rechargeTypes) {
		this.rechargeTypes = rechargeTypes;
	}

	public List<Map<String, Object>> getResults() {
		if (results == null) {
			results = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("resultId", -100);
			mp.put("resultValue", "全部");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 1);
			mp.put("resultValue", "成功");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 0);
			mp.put("resultValue", "失败");
			results.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("resultId", 2);
			mp.put("resultValue", "审核中");
			results.add(mp);
		}
		return results;
	}
	
	public long queryUserIdByWId(Long wid) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map= null;
		try {
			map = fundRecordDao.queryUserIdByWId(conn, wid);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return Convert.strToLong(map.get("userId"), -1l);
	}
	
	public void changeNumToStr(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		for (Map<String, Object> map : list) {
			if (map.get("userType") == null || map.get("userType") == "") {
				map.put("userType", "");
			}else if(map.get("userType").toString().equals("1")) {
				map.put("userType", "个人");
			}else {
				map.put("userType", "企业");
			}
			if (map.get("realName") == null || map.get("realName") == "") {
				map.put("realName", "");
				System.out.println(map.get("realName").getClass());
			}
			if (map.get("orgName") == null || map.get("orgName") == "") {
				map.put("orgName", "");
			}
		}
	}
	
	/**
	 * 查询线上订单状态
	 * getREFstatus
	 * @auth hejiahua
	 * @param orid
	 * @return 
	 * Map<String,Object>
	 * @throws DocumentException 
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-9-15 下午5:31:49
	 * @since  1.0.0
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String>  getREFstatus(String orid) throws DocumentException, SQLException{
	    
	    String [] arrays = orid.split("_");//0 订单日期  1 支付流水号 2用户
	    if (arrays.length!=3) {
            return null;
        }
	    
	    Connection conn = MySQL.getConnection();
	    Map<String, String> map = new HashMap<String, String>();
	    try {
            Dao.Tables.t_recharge_detail detail = new Dao().new Tables().new t_recharge_detail();
            DataSet ds = detail.open(conn, "result", "id='"+Utility.filteSqlInfusion(arrays[1])+"' and userId="+arrays[2], "" , -1, -1);
            Map<String, String> result = BeanMapUtils.dataSetToMap(ds);
            if (result==null ||StringUtils.isBlank(result.get("result"))  ) {
                return null;
            }
            map.putAll(BeanMapUtils.dataSetToMap(ds));
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            conn.close();
        }
	    
        HttpClientHelp help = new HttpClientHelp();
        //地址
        String url =XinfupayConfig.xinfupay_query_url_test;
        //参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //服务版本号 必输  3   
        NameValuePair versionId = new BasicNameValuePair("versionId", "3");
        params.add(versionId);
        //特约商户编号    必输      
        NameValuePair merchantId = new BasicNameValuePair("merchantId", XinfupayConfig.MerNo);
        params.add(merchantId);
        //查询方式  必输  1：单笔查询2：多笔查询
        NameValuePair queryType = new BasicNameValuePair("queryType", "1");
        params.add(queryType);
        //商品订单号 可输  queryType=1时，必输
        NameValuePair orderId = new BasicNameValuePair("orderId", orid);
        params.add(orderId);
        //查询交易起始日期  可输  queryType=2时，为空查询当天交易YYYY-MM-DD
        NameValuePair orderDateStart = new BasicNameValuePair("orderDateStart", "");
        params.add(orderDateStart);
        //查询交易结束日期      YYYY-MM-DD
        NameValuePair orderDateEnd = new BasicNameValuePair("orderDateEnd", "");
        params.add(orderDateEnd);
        //商品订单状态        queryType=2时，可输
        NameValuePair prdOrdStatus = new BasicNameValuePair("prdOrdStatus", "");
        params.add(prdOrdStatus);
        //签名方式  必输  MD5 CFCA  ZJCA
        NameValuePair signType = new BasicNameValuePair("signType", XinfupayConfig.Signtype);
        params.add(signType);
        //签名信息  必输  版本3使用此字段按照签名方式进行校验，校验源串为本接口除本字段外的所有字段。
        NameValuePair signature = new BasicNameValuePair("signature",XinfupayConfig.MD5key);
        params.add(signature);
        
        String result_xml =  help.byPostMethodToHttpEntity(url, params, "UTF-8");
        log.info(result_xml);
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(result_xml));
        Element element =  document.getRootElement();
        
        Iterator<Element> iterator = element.elementIterator();
       
         while (iterator.hasNext()) {
             Element item = iterator.next();
             List<DefaultAttribute> attr =  item.attributes();
             for (DefaultAttribute defaultAttribute : attr) {
                 log.info(defaultAttribute.getName()+":"+defaultAttribute.getStringValue());
                /* 
                 输出项    与后台对应字段 输出项名称   属性  注释  长度
                 retCode     交易返回码       0001：成功0002：失败  
                 retMsg      交易返回信息          
                 seqId       本条交易信息序列号           
                 orderId prdOrdNo    商品订单号           
                 amount  ordAmt  订单金额            
                 orderDate   ordDate 订单日期        YYYY-MM-DD如：（2010-01-01）    
                 completeDate        此交易完成时间         
                 status  ordStatus   交易订单状态标识            
                 statusDes       交易订单状态描述        根据status翻译  
                 settleDate      对账日期        与completeDate相同
                 */
                map.put(defaultAttribute.getName(), defaultAttribute.getStringValue());
            }
        }
         return map;
    }
	
	/**
	 *功能：修改银行卡信息，
	 * @param map
	 */
	public void updateBankInfoForPay(Map <String,String>map){
		Connection conn = MySQL.getConnection();
		try {
			
			userBankDao.updateBankInfoForPay(conn, map);
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws DocumentException {

        HttpClientHelp help = new HttpClientHelp();
        //地址
        String url =XinfupayConfig.xinfupay_query_url;
        //参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //服务版本号 必输  3   
        NameValuePair versionId = new BasicNameValuePair("versionId", "3");
        params.add(versionId);
        //特约商户编号    必输      
        NameValuePair merchantId = new BasicNameValuePair("merchantId", XinfupayConfig.MerNo);
        params.add(merchantId);
        //查询方式  必输  1：单笔查询2：多笔查询
        NameValuePair queryType = new BasicNameValuePair("queryType", "1");
        params.add(queryType);
        //商品订单号 可输  queryType=1时，必输
        NameValuePair orderId = new BasicNameValuePair("orderId", "201409142151_1816_153");
        params.add(orderId);
        //查询交易起始日期  可输  queryType=2时，为空查询当天交易YYYY-MM-DD
        NameValuePair orderDateStart = new BasicNameValuePair("orderDateStart", "");
        params.add(orderDateStart);
        //查询交易结束日期      YYYY-MM-DD
        NameValuePair orderDateEnd = new BasicNameValuePair("orderDateEnd", "");
        params.add(orderDateEnd);
        //商品订单状态        queryType=2时，可输
        NameValuePair prdOrdStatus = new BasicNameValuePair("prdOrdStatus", "");
        params.add(prdOrdStatus);
        //签名方式  必输  MD5 CFCA  ZJCA
        NameValuePair signType = new BasicNameValuePair("signType", XinfupayConfig.Signtype);
        params.add(signType);
        //签名信息  必输  版本3使用此字段按照签名方式进行校验，校验源串为本接口除本字段外的所有字段。
        NameValuePair signature = new BasicNameValuePair("signature",XinfupayConfig.MD5key);
        params.add(signature);
        
        String result_xml =  help.byPostMethodToHttpEntity(url, params, "UTF-8");
        log.info(result_xml);
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(result_xml));
        Element element =  document.getRootElement();
        
        Iterator<Element> iterator = element.elementIterator();
        Map<String, Object> map = new HashMap<String, Object>();
         while (iterator.hasNext()) {
             Element item = iterator.next();
             List<DefaultAttribute> attr =  item.attributes();
             for (DefaultAttribute defaultAttribute : attr) {
                 log.info(defaultAttribute.getName()+":"+defaultAttribute.getStringValue());
                /* 
                 输出项    与后台对应字段 输出项名称   属性  注释  长度
                 retCode     交易返回码       0001：成功0002：失败  
                 retMsg      交易返回信息          
                 seqId       本条交易信息序列号           
                 orderId prdOrdNo    商品订单号           
                 amount  ordAmt  订单金额            
                 orderDate   ordDate 订单日期        YYYY-MM-DD如：（2010-01-01）    
                 completeDate        此交易完成时间         
                 status  ordStatus   交易订单状态标识            
                 statusDes       交易订单状态描述        根据status翻译  
                 settleDate      对账日期        与completeDate相同
                 */
                map.put(defaultAttribute.getName(), defaultAttribute.getStringValue());
            }
        }
    }
	
}
