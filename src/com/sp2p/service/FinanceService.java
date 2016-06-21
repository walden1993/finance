package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.sp2p.dao.InvestDao;
import com.sp2p.dao.RepayMentDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.dao.FrontMyPaymentDao;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.entity.User;

/**
 * @ClassName: FinanceService.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:14:21
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 理财业务处理层
 */
public class FinanceService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);

	private FinanceDao financeDao;
	private AwardService awardService;
	private SelectedService selectedService;
	private UserDao userDao;
	private OperationLogDao operationLogDao;
	private RepayMentDao repayMentDao;
	private InvestDao investDao;
	private BorrowDao borrowDao;
	private FundManagementService fundManagementService;
	
	
	/**
	 * @MethodName: queryBorrowByCondition
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午05:01:31
	 * @Return:
	 * @Descb: 根据条件查询借款信息
	 * @Throws:
	 */
	public void queryBorrowByCondition(String borrowStatus, String borrowWay,
			String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order,
			PageBean pageBean) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		title = Utility.filteSqlInfusion(title);
		paymentMode = Utility.filteSqlInfusion(paymentMode);
		purpose = Utility.filteSqlInfusion(purpose);
		deadline = Utility.filteSqlInfusion(deadline);
		reward = Utility.filteSqlInfusion(reward);
		arStart = Utility.filteSqlInfusion(arStart);
		arEnd = Utility.filteSqlInfusion(arEnd);
		order = Utility.filteSqlInfusion(order);
		
		String resultFeilds = "rewardRate, remainTimeEnd,paymentMode,id,borrowShow,foreknow,displayTime,ShowTime,purpose,imgPath,borrowWay,investNum,borrowTitle,username,vipStatus,credit,creditrating,borrowAmount,annualRate,deadline,excitationType,excitationSum,borrowStatus,schedules,vip,hasPWD,isDayThe,auditStatus ";
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0 ");
		//condition.append(" and stickie != 0");
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus in"
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay in"
					+ StringEscapeUtils.escapeSql(borrowWay));
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(paymentMode)
				&& StringUtils.isNumericSpace(paymentMode)) {
			condition.append(" and paymentMode ="
					+ StringEscapeUtils.escapeSql(paymentMode));
		}
		if (StringUtils.isNotBlank(purpose)
				&& StringUtils.isNumericSpace(purpose)) {
			condition.append(" and purpose ="
					+ StringEscapeUtils.escapeSql(purpose));
		}
		if (StringUtils.isNotBlank(deadline)
				&& StringUtils.isNumericSpace(deadline)) {
			condition.append(" and deadline ="
					+ StringEscapeUtils.escapeSql(deadline));
		}
		if (StringUtils.isNotBlank(reward)
				&& StringUtils.isNumericSpace(reward)) {
			if ("1".equals(reward)) {
				condition.append(" and excitationType ="
						+ StringEscapeUtils.escapeSql(reward));
			} else {
				condition.append(" and excitationType > 1 ");
			}
		}
		if (StringUtils.isNotBlank(arStart)
				&& StringUtils.isNumericSpace(arStart)) {
			condition.append(" and amount >= "
					+ StringEscapeUtils.escapeSql(arStart));//2014-08-25 14:43:23  hjh  添加等于号
		}
		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd)) {
			condition.append(" and amount <="
					+ StringEscapeUtils.escapeSql(arEnd));//2014-08-25 14:43:23  hjh  添加等于号
		} 
		try {
			dataPage(conn, pageBean, " v_t_borrow_list", resultFeilds,
					"  order by sorts desc, id desc,schedule asc", condition
							.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * @MethodName: queryBorrowByCondition
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午05:01:31
	 * @Return:
	 * @Descb: 根据条件查询借款信息
	 * @Throws:
	 */
	public void queryBorrowByConditionApp(String borrowStatus, String borrowWay,
			String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order,
			PageBean pageBean) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		title = Utility.filteSqlInfusion(title);
		paymentMode = Utility.filteSqlInfusion(paymentMode);
		purpose = Utility.filteSqlInfusion(purpose);
		deadline = Utility.filteSqlInfusion(deadline);
		reward = Utility.filteSqlInfusion(reward);
		arStart = Utility.filteSqlInfusion(arStart);
		arEnd = Utility.filteSqlInfusion(arEnd);
		order = Utility.filteSqlInfusion(order);
		
		String resultFeilds = "borrowtitle as debtor_name,id as object_id,paymentMode as repayment_method,borrowStatus as object_status,borrowAmount as borrowing_balance,annualRate as annual_interest_rate,deadline as trade_period, borrowWay as object_type,investNum as available_amount,schedules as financing_progress";
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0 ");
		//condition.append(" and stickie != 0");
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus in"
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay in"
					+ StringEscapeUtils.escapeSql(borrowWay));
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(paymentMode)
				&& StringUtils.isNumericSpace(paymentMode)) {
			condition.append(" and paymentMode ="
					+ StringEscapeUtils.escapeSql(paymentMode));
		}
		if (StringUtils.isNotBlank(purpose)
				&& StringUtils.isNumericSpace(purpose)) {
			condition.append(" and purpose ="
					+ StringEscapeUtils.escapeSql(purpose));
		}
		if (StringUtils.isNotBlank(deadline)
				&& StringUtils.isNumericSpace(deadline)) {
			condition.append(" and deadline ="
					+ StringEscapeUtils.escapeSql(deadline));
		}
		if (StringUtils.isNotBlank(reward)
				&& StringUtils.isNumericSpace(reward)) {
			if ("1".equals(reward)) {
				condition.append(" and excitationType ="
						+ StringEscapeUtils.escapeSql(reward));
			} else {
				condition.append(" and excitationType > 1 ");
			}
		}
		if (StringUtils.isNotBlank(arStart)
				&& StringUtils.isNumericSpace(arStart)) {
			condition.append(" and amount >= "
					+ StringEscapeUtils.escapeSql(arStart));//2014-08-25 14:43:23  hjh  添加等于号
		}
		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd)) {
			condition.append(" and amount <="
					+ StringEscapeUtils.escapeSql(arEnd));//2014-08-25 14:43:23  hjh  添加等于号
		} 
		try {
			dataPage(conn, pageBean, " v_t_borrow_list", resultFeilds,
					"  order by sorts desc, id desc,schedule asc", condition
							.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 查询理财排名
	 * @param pageBean
	 * @throws SQLException 
	 */
	public void queryInvestRanking(PageBean pageBean,String no_uid) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			//排除不显示的用户
			String table = " (select f_formatAmount(money) as money,username from  (SELECT SUM(t.`investAmount`) AS money ,f_formatting_username(tu.`username`) AS username FROM t_invest t ,t_user tu WHERE t.`investor` = tu.`id` and tu.`id` not in "+no_uid+" GROUP BY t.`investor` ORDER BY money DESC LIMIT 0,10) tb) tb";
			dataPage(conn, pageBean, table, "*", "", "");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally{
			conn.close();
		}
	}
	
	//查询全部借款列表
	public void queryAllBorrowByCondition(String borrowStatus, String borrowWay,
			String title, String proStatus, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order,
			PageBean pageBean) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		title = Utility.filteSqlInfusion(title);
		proStatus = Utility.filteSqlInfusion(proStatus);
		purpose = Utility.filteSqlInfusion(purpose);
		deadline = Utility.filteSqlInfusion(deadline);
		reward = Utility.filteSqlInfusion(reward);
		arStart = Utility.filteSqlInfusion(arStart);
		arEnd = Utility.filteSqlInfusion(arEnd);
		order = Utility.filteSqlInfusion(order);
		
		String resultFeilds = " rewardRate,paymentMode,remainTimeEnd,id,borrowShow,foreknow,displayTime,ShowTime,purpose,imgPath,borrowWay,investNum,borrowTitle,username,vipStatus,credit,creditrating,borrowAmount,annualRate,deadline,excitationType,excitationSum,borrowStatus,schedules,vip,hasPWD,isDayThe,auditStatus ";
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0 ");
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus in"
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay in"
					+ StringEscapeUtils.escapeSql(borrowWay));
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(proStatus)
				&& StringUtils.isNumericSpace(proStatus)) {
			if ("1".equals(proStatus)) {//预告标
			    //是否预告0否，1是
                condition.append(" and (foreknow =1 or '"+DateUtil.dateToString(new Date())+"'<displayTime)");
            }else {//标的状态判断
                condition.append(" and borrowStatus ="
                        + StringEscapeUtils.escapeSql(proStatus));
            }
		}
		if (StringUtils.isNotBlank(purpose)
				&& StringUtils.isNumericSpace(purpose)) {
			condition.append(" and purpose ="
					+ StringEscapeUtils.escapeSql(purpose));
		}
		if (StringUtils.isNotBlank(deadline)
				&& StringUtils.isNumericSpace(deadline)) {
			condition.append(" and deadline ="
					+ StringEscapeUtils.escapeSql(deadline));
		}
		if (StringUtils.isNotBlank(reward)
				&& StringUtils.isNumericSpace(reward)) {
			if ("1".equals(reward)) {
				condition.append(" and excitationType ="
						+ StringEscapeUtils.escapeSql(reward));
			} else {
				condition.append(" and excitationType > 1 ");
			}
		}
		if (StringUtils.isNotBlank(arStart)
				&& StringUtils.isNumericSpace(arStart)) {
			condition.append(" and amount >= "
					+ StringEscapeUtils.escapeSql(arStart));//2014-08-25 14:43:23  hjh  添加等于号
		}
		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd)) {
			condition.append(" and amount <="
					+ StringEscapeUtils.escapeSql(arEnd));//2014-08-25 14:43:23  hjh  添加等于号
		}
		try {
			dataPage(conn, pageBean, " v_t_borrow_list", resultFeilds,
					" order by borrowStatus asc,id desc ,id desc ", condition
							.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	/**
	 * @MethodName: queryBorrowByCondition
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午05:01:31
	 * @Return:
	 * @Descb: 根据条件查询借款信息
	 * @Throws:
	 */
	public void queryBorrowRecomment(String borrowStatus, String borrowWay,
			String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order,
			PageBean pageBean) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		title = Utility.filteSqlInfusion(title);
		paymentMode = Utility.filteSqlInfusion(paymentMode);
		purpose = Utility.filteSqlInfusion(purpose);
		deadline = Utility.filteSqlInfusion(deadline);
		reward = Utility.filteSqlInfusion(reward);
		arStart = Utility.filteSqlInfusion(arStart);
		arEnd = Utility.filteSqlInfusion(arEnd);
		order = Utility.filteSqlInfusion(order);
		
		String resultFeilds = " id,borrowShow,foreknow,displayTime,purpose,imgPath,borrowWay,investNum,borrowTitle,username,vipStatus,credit,creditrating,borrowAmount,annualRate,deadline,excitationType,excitationSum,borrowStatus,schedules,vip,hasPWD,isDayThe,auditStatus,stickie ";
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0");
		condition.append(" and stickie= 0");
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus in"
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay in"
					+ StringEscapeUtils.escapeSql(borrowWay));
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(paymentMode)
				&& StringUtils.isNumericSpace(paymentMode)) {
			condition.append(" and paymentMode ="
					+ StringEscapeUtils.escapeSql(paymentMode));
		}
		if (StringUtils.isNotBlank(purpose)
				&& StringUtils.isNumericSpace(purpose)) {
			condition.append(" and purpose ="
					+ StringEscapeUtils.escapeSql(purpose));
		}
		if (StringUtils.isNotBlank(deadline)
				&& StringUtils.isNumericSpace(deadline)) {
			condition.append(" and deadline ="
					+ StringEscapeUtils.escapeSql(deadline));
		}
		if (StringUtils.isNotBlank(reward)
				&& StringUtils.isNumericSpace(reward)) {
			if ("1".equals(reward)) {
				condition.append(" and excitationType ="
						+ StringEscapeUtils.escapeSql(reward));
			} else {
				condition.append(" and excitationType > 1 ");
			}
		}
		if (StringUtils.isNotBlank(arStart)
				&& StringUtils.isNumericSpace(arStart)) {
			condition.append(" and amount >= "
					+ StringEscapeUtils.escapeSql(arStart));//2014-08-25 14:43:23  hjh  添加等于号
		}
		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd)) {
			condition.append(" and amount <="
					+ StringEscapeUtils.escapeSql(arEnd));//2014-08-25 14:43:23  hjh  添加等于号
		}
		try {
			dataPage(conn, pageBean, " v_t_borrow_list", resultFeilds,
					" order by sorts desc,schedules asc ,id desc ", condition
							.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	//查询优选标
	public void queryBestBorrow(String borrowStatus, String borrowWay,
			String title, String paymentMode, String purpose, String deadline,
			String reward, String arStart, String arEnd, String order,
			PageBean pageBean) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		title = Utility.filteSqlInfusion(title);
		paymentMode = Utility.filteSqlInfusion(paymentMode);
		purpose = Utility.filteSqlInfusion(purpose);
		deadline = Utility.filteSqlInfusion(deadline);
		reward = Utility.filteSqlInfusion(reward);
		arStart = Utility.filteSqlInfusion(arStart);
		arEnd = Utility.filteSqlInfusion(arEnd);
		order = Utility.filteSqlInfusion(order);
		
		String resultFeilds = "id,paymentMode,displayTime,foreknow,borrowShow,purpose,imgPath,borrowWay,investNum,borrowTitle,username,vipStatus,credit,creditrating,borrowAmount,annualRate,deadline,excitationType,excitationSum,borrowStatus,schedules,vip,hasPWD,isDayThe,auditStatus ";
		StringBuffer condition = new StringBuffer();
		condition.append(" and sorts!= 0 ");
		condition.append(" and stickie = 0 "); 
		Connection conn = MySQL.getConnection();
		if (StringUtils.isNotBlank(borrowStatus)) {
			condition.append(" and borrowStatus in"
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay in"
					+ StringEscapeUtils.escapeSql(borrowWay));
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(paymentMode)
				&& StringUtils.isNumericSpace(paymentMode)) {
			condition.append(" and paymentMode ="
					+ StringEscapeUtils.escapeSql(paymentMode));
		}
		if (StringUtils.isNotBlank(purpose)
				&& StringUtils.isNumericSpace(purpose)) {
			condition.append(" and purpose ="
					+ StringEscapeUtils.escapeSql(purpose));
		}
		if (StringUtils.isNotBlank(deadline)
				&& StringUtils.isNumericSpace(deadline)) {
			condition.append(" and deadline ="
					+ StringEscapeUtils.escapeSql(deadline));
		}
		if (StringUtils.isNotBlank(reward)
				&& StringUtils.isNumericSpace(reward)) {
			if ("1".equals(reward)) {
				condition.append(" and excitationType ="
						+ StringEscapeUtils.escapeSql(reward));
			} else {
				condition.append(" and excitationType > 1 ");
			}
		}
		if (StringUtils.isNotBlank(arStart)
				&& StringUtils.isNumericSpace(arStart)) {
			condition.append(" and amount >= "
					+ StringEscapeUtils.escapeSql(arStart));//2014-08-25 14:43:23  hjh  添加等于号
		}
		if (StringUtils.isNotBlank(arEnd) && StringUtils.isNumericSpace(arEnd)) {
			condition.append(" and amount <="
					+ StringEscapeUtils.escapeSql(arEnd));//2014-08-25 14:43:23  hjh  添加等于号
		}
		try {
			dataPage(conn, pageBean, " v_t_borrow_list", resultFeilds,
					" order by sorts desc,schedules asc ,id desc ", condition
							.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 新手标
	 */
	public Map<String, String> queryNewBorrow() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = financeDao.queryNewBorrow(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryBorrowDetailById
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:18:19
	 * @Return:
	 * @Descb: 根据ID查询借款详细信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetailById(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowDetailById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	/**
	 * @MethodName: queryBorrowDetailById
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:18:19
	 * @Return:
	 * @Descb: 根据ID查询借款详细信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetailByIdApp(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowDetailByIdApp(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * @MethodName: queryUserInfoById
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午06:04:54
	 * @Return:
	 * @Descb: 根据ID查询借款信息发布者个人信息
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoById(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserInfoById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	/**
	 * @MethodName: queryUserInfoById
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午06:04:54
	 * @Return:
	 * @Descb: 根据ID查询借款信息发布者个人信息
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoByIdApp(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserInfoByIdApp(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	/**
	 * @MethodName: queryUserInfoById2
	 * @Param: FinanceDao
	 * @Author: L.X.Z
	 * @Date: 2013-3-6 下午06:04:54
	 * @Return:
	 * @Descb: 根据ID查询融资信息发布者信息
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoById2(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserInfoById2(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	/**
	 * @MethodName: queryUserInfoById2
	 * @Param: FinanceDao
	 * @Author: L.X.Z
	 * @Date: 2013-3-6 下午06:04:54
	 * @Return:
	 * @Descb: 通过id查询融资用户的名称
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoById3(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserInfoById3(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryUserIdentifiedByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByid(long id)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserIdentifiedByid(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}
	
	/**
	 * @MethodName: queryUserIdentifiedByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByidApp(long id)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserIdentifiedByidApp(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}
	
	/**
	 * @MethodName: queryUserIdentifiedByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByid2(long id)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserIdentifiedByid2(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}
	/**
	 * @MethodName: queryUserIdentifiedByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByid2App(long id)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserIdentifiedByid2App(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}
	/**
	 * @MethodName: queryUserImageByid
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:01:28
	 * @Return:
	 * @Descb: 查询用户认证图片
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserImageByid(long typeId, long userId)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryUserImageByid(conn, typeId, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}

	/**
	 * @MethodName: queryPaymentByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:03:01
	 * @Return:
	 * @Descb: 根据ID查询本期还款信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRePayByid(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryRePayByid(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}
	
	/**
	 * @MethodName: queryPaymentByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:03:01
	 * @Return:
	 * @Descb: 根据ID查询本期还款信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRePayByidApp(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryRePayByidApp(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}

	/**
	 * @MethodName: queryCollectionByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:04:28
	 * @Return:
	 * @Descb: 根据ID查询本期催收信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryCollectionByid(long id)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryCollectionByid(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return list;
	}

	/**
	 * @MethodName: queryInvestByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:06:00
	 * @Return:
	 * @Descb: 根据ID查询投资记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryInvestByid(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryInvestByid(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	/**
	 * @MethodName: queryInvestByid
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:06:00
	 * @Return:
	 * @Descb: 根据ID查询投资记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryInvestByidApp(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryInvestByidApp(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	/**
	 * 根据ID和用户ID查询投资记录
	 * queryInvestByidAndInvestor
	 * @auth hejiahua
	 * @param id  标的id
	 * @param investor  投资用户
	 * @return
	 * @throws Exception 
	 * List<Map<String,Object>>
	 * @exception 
	 * @date:2014-10-12 下午9:17:14
	 * @since  1.0.0
	 */
    public int queryInvestByidAndInvestor(long id,long investor) throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection conn = MySQL.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = financeDao.queryInvestByidAndInvestor(conn, id,investor);
            if (list!=null) {
                return list.size();
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            conn.close();
        }
        return 0;
    }
	
	/**
	 * @MethodName: queryInvestExperienceByid
	 * @Param: FinanceDao
	 * @Author: 李艳华
	 * @Date: 
	 * @Return:
	 * @Descb: 根据ID查询体验标投资记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryInvestExperienceByid(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryInvestExperienceByid(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	/**
	 * 查询用户是否已经体验了此标
	 * queryInvestExperienceByUser(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception 
	 * List<Map<String,Object>>
	 * @exception 
	 * @date:2014-8-28 下午7:03:42
	 * @since  1.0.0
	 */
	public List<Map<String, Object>> queryInvestExperienceByUser(long id,long userId) throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection conn = MySQL.getConnection();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = financeDao.queryInvestExperienceByUser(conn, id,userId);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();

            throw e;
        } finally {
            conn.close();
        }
        return list;
    }
	
	/**
	 * @MethodName: queryInvestByid
	 * @Param: FinanceDao
	 * @Author: 李艳华
	 * @Date: 2014-8-22
	 * @Return:
	 * @Descb: 根据ID查询体验券记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryExperienceByid(long id,double startMoney,double endMoney) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryExperienceByid(conn, id,startMoney,endMoney);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: queryBorrowMSGBord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午08:30:26
	 * @Return:
	 * @Descb: 根据ID查询留言板信息
	 * @Throws:
	 */
	public void queryBorrowMSGBord(long id, PageBean pageBean) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_msgbord", " * ",
					" order by id desc ", " and modeId=" + id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: addBrowseCount
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:54:02
	 * @Return:
	 * @Descb: 添加浏览量处理
	 * @Throws:
	 */
	public void addBrowseCount(Long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long returnId = -1L;
		try {
			returnId = financeDao.addBrowseCount(conn, id);
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
	 * @throws DataException
	 * @MethodName: addBorrowMSG
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午08:16:45
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public long addBorrowMSG(long id, long userId, String msgContent)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long returnId = -1;
		try {
			returnId = financeDao.addBorrowMSG(conn, id, userId, msgContent);
			if (returnId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_msgboard", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "添加借款留言", 1);
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

		return returnId;
	}

	/**
	 * @MethodName: getInvestStatus
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午06:46:17
	 * @Return:
	 * @Descb: 获取借款的投标状态,条件是正在招标中
	 * @Throws:
	 */
	public Map<String, String> getInvestStatus(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.getInvestStatus(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * @MethodName: valideInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:59
	 * @Return:
	 * @Descb: 验证投资人是否符合本次投标
	 * @Throws:
	 */
	public String valideInvest(long id, long userId, double amount)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		String result = "";
		try {
			result = financeDao.valideInvest(conn, id, userId, amount);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * @MethodName: valideTradePassWord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:43
	 * @Return:
	 * @Descb: 验证交易密码
	 * @Throws:
	 */
	public String valideTradePassWord(long userId, String pwd) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		String result = "";
		try {
			result = financeDao.valideTradePassWord(conn, userId, pwd);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return result;
	}

	/**
	 * @MethodName: queryBorrowInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午07:30:26
	 * @Return:
	 * @Descb: 根据ID获取投资的借款信息
	 * @Throws:
	 */
	public Map<String, String> queryBorrowInvest(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryBorrowInvest(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * @MethodName: queryUserMonney
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午08:48:43
	 * @Return:
	 * @Descb: 查询用户的金额
	 * @Throws:
	 */
	public Map<String, String> queryUserMonney(long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryUserMonney(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	/**
	 * 将赠送金额转换成可用金额(不能使用的方法)
	 * @param investAmount
	 * @return
	 */
	@Deprecated
	public long convertPresrentToUseable(Connection conn,double investAmount,long userId,String addIP,String username){
		long result1  = -1L;
		try {
			Map<String, String> map = userDao.queryPresrent(conn,userId);
			double presrent = Convert.strToDouble(map.get("presrent"), 0);
			double convertMoney = (investAmount-presrent>0)?presrent:investAmount;
			result1 = fundManagementService.addBackR(userId, userId,
					7, convertMoney, "投标赠送金额抵用可用金额", new Date(),
					"自动充值", addIP, username,  "投标赠送金额抵用可用金额");
			if (result1>0) {
				result1 = userDao.updatePresrent(conn, userId, convertMoney);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return result1;
	}
	
	/**
	 * @throws Exception
	 * @MethodName: addBorrowInvest
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午05:48:20
	 * @Return:
	 * @Descb: 添加借款投资记录
	 * @Throws:
	 */
	public Map<String, String> addBorrowInvest(long id, long userId,
			String dealPWD, double investAmount, String basePath,
			String username, int status, int num) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		dealPWD = Utility.filteSqlInfusion(dealPWD);
		basePath = Utility.filteSqlInfusion(basePath);
		username = Utility.filteSqlInfusion(username);
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = MySQL.getConnection();

		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_borrow_join(conn, ds, outParameterValues, id, userId,
					basePath, new BigDecimal(investAmount), new Date(), status,
					num, 0, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret <= 0) {
				conn.rollback();
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_invest", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), investAmount, "用户投标借款", 1);
				// 得到当前用户最新的投资ID
				Map<String, String> maps = investDao.queryInvestId(conn, id,
						userId);
				// 得到借款当前借款信息
				Map<String, String> borrowMap = borrowDao.queryBorroeById(conn,
						id);
				if (borrowMap != null) {
					long borrowWay = Convert.strToLong(borrowMap
							.get("borrowWay"), -1);
					if (borrowWay == 6) {
						// 提成奖励
						ret = awardService.updateMoneyNew(conn, userId,
								new BigDecimal(investAmount),
								IConstants.MONEY_TYPE_1, Convert.strToLong(maps
										.get("investId"), -1));
					}
				}
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

		return map;
	}

	/**
	 * 投标
	 * 
	 * @param conn
	 * @param investAmount
	 * @param id
	 * @param userId
	 * @param basePath
	 * @param username
	 * @param status
	 * @param num
	 * @return
	 */
	private Map<String, String> validateInvest(Connection conn,
			double investAmount, long id, long userId, String basePath,
			String username, int status, int num) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		basePath = Utility.filteSqlInfusion(basePath);
		username = Utility.filteSqlInfusion(username);
		
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_borrow_join(conn, ds, outParameterValues, id, userId,
					basePath, new BigDecimal(investAmount), new Date(), status,
					num, 0, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret <= 0) {
				conn.rollback();
				return map;
			}
			// 添加操作日志
			userMap = userDao.queryUserById(conn, userId);
			operationLogDao.addOperationLog(conn, "t_invest", Convert.strToStr(
					userMap.get("username"), ""), IConstants.INSERT, Convert
					.strToStr(userMap.get("lastIP"), ""), investAmount, "用户投标借款", 1);
			// 得到当前用户最新的投资ID
			Map<String, String> maps = investDao
					.queryInvestId(conn, id, userId);
			// 得到借款当前借款信息
			Map<String, String> borrowMap = borrowDao.queryBorroeById(conn, id);
			if (borrowMap != null) {
				long borrowWay = Convert.strToLong(borrowMap.get("borrowWay"),
						-1);
				if (borrowWay == 6) {
					// 提成奖励
					ret = awardService.updateMoneyNew(conn, userId,
							new BigDecimal(investAmount),
							IConstants.MONEY_TYPE_1, Convert.strToLong(maps
									.get("investId"), -1));
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		}

		return map;
	}

	/**
	 * @throws DataException
	 * @MethodName: addFocusOn
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:00:49
	 * @Return:
	 * @Descb: 添加关注
	 * @Throws:
	 */
	public Long addFocusOn(long id, long userId, int modeType) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addFocusOn(conn, id, userId, modeType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				if (modeType == 1) {
					operationLogDao.addOperationLog(conn, "t_concern", Convert
							.strToStr(userMap.get("username"), ""),
							IConstants.INSERT, Convert.strToStr(userMap
									.get("lastIP"), ""), 0, "添加关注用户", 1);
				} else {
					operationLogDao.addOperationLog(conn, "t_concern", Convert
							.strToStr(userMap.get("username"), ""),
							IConstants.INSERT, Convert.strToStr(userMap
									.get("lastIP"), ""), 0, "添加关注借款", 1);
				}
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
	 * @throws DataException
	 * @MethodName: hasFocusOn
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午11:04:13
	 * @Return:
	 * @Descb: 查询用户是否已经有关注
	 * @Throws:
	 */
	public Map<String, String> hasFocusOn(long id, long userId, int moduleType)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.hasFocusOn(conn, id, userId, moduleType);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * @throws DataException
	 * @MethodName: addUserMail
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:13:57
	 * @Return:
	 * @Descb: 添加用户站内信
	 * @Throws:
	 */
	public long addUserMail(long reciver, Long userId, String title,
			String content, int mailType) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addUserMail(conn, reciver, userId, title,
					content, mailType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_concern", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "发送站内信", 1);
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
	 * @throws DataException
	 * @MethodName: addUserReport
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:15:05
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public long addUserReport(long reporter, Long userId, String title,
			String content) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = financeDao.addUserReport(conn, reporter, userId, title,
					content);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_report", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "添加用户举报", 1);
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
	 * @MethodName: queryLastestBorrow
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:28:00
	 * @Return:
	 * @Descb: 查询最新的借款前10条记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrow() throws Exception,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = financeDao.queryLastestBorrow(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return list;
	}
	
	/**
	 * @MethodName: queryLastestExperience
	 * @Param: FinanceService
	 * @Author: 李艳华
	 * @Date: 2014-8-21 上午10:52:00
	 * @Return:
	 * @Descb: 查询最新一期投资体验标的详情
	 * @Throws:
	 */
	public Map<String, String> queryLastestExperience() throws Exception,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryLastestExperience(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	
	public Map<String, String> queryExperienceDetail(int borrowId) throws Exception,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryExperienceDetail(conn,borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	

	/**
	 * @MethodName: investRank
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:11:37
	 * @param type 0 表示全部；1 表示年投资排行； 2 表示季度投资排行 3 表示月投资排行；4 表示周投资排行；5 表示日投资排行
	 * count 数量
	 * @Return:
	 * @Descb: 投资排名前20条记录
	 * @Throws:
	 */
	public List<Map<String, Object>> investRank(int type, int count)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		// List<Map<String, Object>> list = new ArrayList<Map<String,
		// Object>>();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			// list = financeDao.investRank(conn, starTime, endTime);
			Procedures.p_get_topinvestment(conn, ds, outParameterValues, type,
					count);

			conn.commit();

			ds.tables.get(0).rows.genRowsMap();

			return ds.tables.get(0).rows.rowsMap;
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
	 * @MethodName: queryTotalRisk
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:36:01
	 * @Return:
	 * @Descb: 查询风险保障金总额
	 * @Throws:
	 */
	public Map<String, String> queryTotalRisk() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryTotalRisk(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryCurrentRisk
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:36:14
	 * @Return:
	 * @Descb: 查询当日风险保障金收支金额
	 * @Throws:
	 */
	public Map<String, String> queryCurrentRisk() throws Exception,
			DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryCurrentRisk(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	/**
	 * 安全强度
	 * queryUserSafe: <br/>
	 *
	 * @author Administrator
	 * @return
	 * @throws Exception
	 * @throws DataException
	 * @since JDK 1.6
	 */
	public Map<String, String> queryUserSafe(long userId) throws Exception,
			DataException {
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			Procedures.p_progressSafe(conn, ds,
					outParameterValues, userId);
			map = BeanMapUtils.dataSetToMap(ds);
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
	 * @throws DataException
	 * @throws Exception
	 * @MethodName: queryBorrowRecord
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午11:03:17
	 * @Return:
	 * @Descb: 查询借款记录统计
	 * @Throws:
	 */
	public Map<String, String> queryBorrowRecord(Long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getBorrowRecord(conn, ds, outParameterValues, id,
					new Date());
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * @MethodName: subscribeSubmit
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午10:30:15
	 * @Return:
	 * @Descb: 认购提交
	 * @Throws:
	 */
	public String subscribeSubmit(long id, int copies, Long userId,
			String basePath, String username,
			Map<String, Object> platformCostMap) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		String msg = "";
		long returnId = -1;
		try {
			Map<String, String> borrowTenderInMap = financeDao
					.queryBorrowTenderIn(conn, id);
			// 认购中的借款
			if (borrowTenderInMap != null && borrowTenderInMap.size() > 0) {
				long remainCirculationNumber = Convert.strToLong(
						borrowTenderInMap.get("remainCirculationNumber") + "",
						0);
				double smallestFlowUnit = Convert.strToDouble(borrowTenderInMap
						.get("smallestFlowUnit")
						+ "", 0);
				if (copies > remainCirculationNumber) {
					// 校验认购份数是否满足
					msg = "只剩下【" + remainCirculationNumber + "】份可认购,请重新选择!";
				} else {
					// 提交的认购总金额
					double investAmount = smallestFlowUnit * copies;
					// 查询账户上的金额是否满足认购的份数
					Map<String, String> usableSumMap = financeDao
							.queryUserUsableSum(conn, userId, investAmount);
					if (usableSumMap != null && usableSumMap.size() > 0) {
						double usableSum = Convert.strToDouble(usableSumMap
								.get("usableSum")
								+ "", 0);
						long minCirculationNumber = 0;
						double needSum = 0;
						if (usableSum < smallestFlowUnit) {
							msg = "您的可用余额少于￥" + smallestFlowUnit + "元，认购失败!";
						} else {
							// 计算向下取数满足最小的认购份数
							for (long n = remainCirculationNumber; n > 0; n--) {
								needSum = smallestFlowUnit * n;
								if (usableSum >= needSum) {
									minCirculationNumber = n;
									break;
								}
							}
							msg = "您的可用余额可认购【" + minCirculationNumber
									+ "】份,请重新选择!";
						}
					} else {
						Map<String, String> map = validateInvest(conn,
								investAmount, id, userId, basePath, username,
								1, copies);
						returnId = Convert.strToLong(map.get("ret"), -1);
						if (returnId <= 0) {
							conn.rollback();
							msg = Convert.strToStr(map.get("ret_desc"), "");
						} else {
							msg = "1";
						}
					}
				}
			} else {
				// 认购已满,更新状态为回购中
				financeDao.updateRepo(conn, id);
				msg = "无效借款投标";
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

		return msg;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

	/**
	 * @throws Exception
	 * @MethodName: getInvestPWD
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:35:00
	 * @Return:
	 * @Descb: 获取投标密码是否正确
	 * @Throws:
	 */
	public Map<String, String> getInvestPWD(Long idLong, String investPWD)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			investPWD = Encrypt.MD5(investPWD);
			map = financeDao.getInvestPWD(conn, idLong, investPWD);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	/**
	 * @throws Exception
	 * @MethodName: getInvestPWD
	 * @Param: FinanceService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:35:00
	 * @Return:
	 * @Descb: 获取交易密码是否正确
	 * @Throws:
	 */
	public Map<String, String> getDealPWD(Long idLong, String dealPWD)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			dealPWD = Encrypt.MD5(dealPWD);
			map = financeDao.getDealPWD(conn, idLong, dealPWD);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	

	/**
	 * 根据借款Id查询还款记录
	 * 
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryRepayment(long borrowId)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());

		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = repayMentDao.queryHasPIAndStillPi(conn, borrowId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}


		return map;
	}

	/**
	 * 查找投资人信息 add by houli
	 * 
	 * @return
	 * @throws DataException
	 * @throws Exception
	 */
	public Map<String, String> queryInvestorById(long investorId)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = financeDao.queryInvestorById(conn, investorId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	  /**
     * 体验标
     * 
     * @auth hejiahua
     * @return String
     * @param id
     *            标id ticketNo 体验券号 user 用户
     * @throws DataException 
     * @throws SQLException 
     * @exception
     * @date:2014-8-26 上午11:22:17
     * @since 1.0.0 condition 每次投资只能使用一张体验券，每个用户每个体验标，只能投资一次
     */
    public int validateTicket(int id, String ticketNo, User user)throws Exception {
        log.info("className:"+ this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        ticketNo = Utility.filteSqlInfusion(ticketNo);
        Connection conn = MySQL.getConnection();
        DataSet ds = new DataSet();
        List<Object> outParameterValues = new ArrayList<Object>();
        int result = 0;
        try {
            Procedures.p_trial_buy_borrow(conn,ds, outParameterValues,id,  user.getId().intValue(),ticketNo,0,"");
            result = Convert.strToInt(outParameterValues.get(0).toString(), 0);
            if (result!=-1) {//执行不成功回滚
                conn.rollback();
            }
            conn.commit();
        }
        catch (Exception  e) {
            e.printStackTrace();
            conn.rollback();
        }finally{
            conn.close();
        }
        return result;
    }

    /**
     *功能：查询总投资，总借款
     * @return
     * @throws DataException 
     * @throws SQLException 
     */
    public Map <String, String> queryInvestAmount(long userId) throws DataException, SQLException{
    	Connection conn = MySQL.getConnection();
    	Map <String, String>retMap = new HashMap<String, String>();
    	try {
    	    retMap = investDao.queryInvestSum(conn, userId);
            retMap.putAll(investDao.queryBorrowSum(conn, userId));
        }
        catch (Exception e) {
                e.printStackTrace();
                log.error(e);
        }finally{
            conn.close();
        }
    	return retMap;
    }
    
	
    /**
     * queryRelated  查询相关材料
     * @auth hejiahua
     * @param id  标的id
     * @param isa  是否打码 
     * void
     * @throws SQLException 
     * @exception 
     * @date:2014-10-11 下午3:20:20
     * @since  1.0.0
     */
    public List<Map<String, Object>> queryRelated(long id,int isa) throws SQLException {
            Connection conn = MySQL.getConnection();
            try {
                Dao.Views.v_t_related_materials related_materials  = new Dao().new Views().new v_t_related_materials();
                DataSet ds= related_materials.open(conn, "", " borrowId ="+id+" and isa ="+isa, " id desc", -1 , -1);
                ds.tables.get(0).rows.genRowsMap();
                return ds.tables.get(0).rows.rowsMap;
            }
            catch (Exception e) {
                e.printStackTrace();
                log.error(e);
                return null;
            }finally{
                conn.close();
            }
    }
    
    /**
     * 查询我和我的用户累计总投资
     * queryMyAndFriendInvest
     * @auth hejiahua
     * @param userId
     * @return 
     * Map<String,Object>
     * @throws SQLException 
     * @exception 
     * @date:2014-11-5 下午3:02:46
     * @since  1.0.0
     */
    public List<Map<String, String>> queryMyAndFriendInvest(long userId) throws SQLException{
          List<Map<String, String>> list = null;
          Connection conn = MySQL.getConnection();
          try {
                  String command="SELECT my,sum(myinvestAmount+myfriendAmount)as amount ,f_formatting_username(username) as username from (SELECT  mx.*,IFNULL(sum((SELECT sum(t.investAmount) from t_invest t where t.investor=tu.userId and t.investTime > '2014-11-06 00:00:00'   AND t.investTime < '2014-12-06 00:00:00')),0) as myfriendAmount from(  SELECT  sum(t.investAmount) AS myinvestAmount,   t.investor AS my     FROM    t_invest t   WHERE   t.investTime > '2014-11-06 00:00:00'  AND t.investTime < '2014-12-06 00:00:00' GROUP BY  t.investor  ) mx LEFT OUTER JOIN t_recommend_user tu ON tu.recommendUserId = mx.my GROUP BY mx.my) x,t_user t where x.my = t.id GROUP BY my,username ORDER BY amount desc LIMIT 0,10";
                  if (userId!=-1) {
                      command="SELECT my,sum(myinvestAmount+myfriendAmount)as amount ,f_formatting_username(username) as username from (SELECT  mx.*,IFNULL(sum((SELECT sum(t.investAmount) from t_invest t where t.investor=tu.userId and t.investTime > '2014-11-06 00:00:00'   AND t.investTime < '2014-12-06 00:00:00')),0) as myfriendAmount from(  SELECT  sum(t.investAmount) AS myinvestAmount,   t.investor AS my     FROM    t_invest t   WHERE   t.investTime > '2014-11-06 00:00:00'  AND t.investTime < '2014-12-06 00:00:00' GROUP BY  t.investor  ) mx LEFT OUTER JOIN t_recommend_user tu ON tu.recommendUserId = mx.my GROUP BY mx.my) x,t_user t where x.my = t.id and x.my="+userId+" GROUP BY my,username ORDER BY amount desc LIMIT 0,10";
                  }
                  DataSet ds =  Database.executeQuery(conn, command);
                  ds.tables.get(0).rows.genRowsMap();
                  list = ds.tables.get(0).rows.rowsMap;
            }
            catch (Exception e) {
               e.printStackTrace();
               log.error(e);
            }finally{
                conn.close();
            }
          return list;
    }
    
    /**
     * 查询用户的有效邀请人数
     * queryMyAndFriendValid
     * @auth hejiahua
     * @param userId
     * @return 
     * Map<String,Object>
     * @throws SQLException 
     * @exception 
     * @date:2014-11-5 下午3:02:46
     * @since  1.0.0
     */
    public List<Map<String, String>> queryMyAndFriendValid(long userId) throws SQLException{
           List<Map<String, String>> list = null;
          Connection conn = MySQL.getConnection();
          try {
//                  String command="SELECT  COUNT(*) AS sort,  tus.recommendUserId,   f_formatting_username (tus.username) AS username  FROM   (   SELECT DISTINCT   investor   FROM  t_invest where investTime>='2014-11-06 00:00:00' and investTime<='2014-12-08 18:47:59' ) ti  INNER JOIN (   SELECT    tru.`userId`,  tru.`recommendUserId`,    tu1.username    FROM   t_recommend_user tru,   t_user tu,   t_user tu1   WHERE    tru.`userId` = tu.`id`  AND tu1.id = tru.`recommendUserId`   AND tu.`createTime` > '2014-11-06 00:00:00' and tu.`createTime` < '2014-12-06 00:00:00') tus ON ti.investor = tus.userId  GROUP BY    tus.recommendUserId,   tus.username ORDER BY  sort DESC LIMIT 0, 10";
//                    if (userId!=-1) {
//                         command="SELECT  COUNT(*) AS sort,  tus.recommendUserId,   f_formatting_username (tus.username) AS username  FROM   (   SELECT DISTINCT   investor   FROM  t_invest where  investTime>='2014-11-06 00:00:00' and investTime<='2014-12-08 18:47:59'  ) ti  INNER JOIN (   SELECT    tru.`userId`,  tru.`recommendUserId`,    tu1.username    FROM   t_recommend_user tru,   t_user tu,   t_user tu1   WHERE    tru.`userId` = tu.`id`  AND tu1.id = tru.`recommendUserId` and tru.recommendUserId="+userId+"   AND tu.`createTime` > '2014-11-06 00:00:00' and tu.`createTime` < '2014-12-06 00:00:00') tus ON ti.investor = tus.userId  GROUP BY    tus.recommendUserId,   tus.username ORDER BY  sort DESC LIMIT 0, 10";
//                    }
        	  
        	  StringBuffer command = new StringBuffer();
        	  command.append("SELECT a.id,f_formatting_username(a.username) username,COUNT(b.userId) ct FROM t_user a ,t_recommend_user  b ")
        	  .append("WHERE a.id=b.recommendUserId AND ")
        	  .append("EXISTS ( ")
        	  .append("SELECT 1 FROM t_invest c WHERE c.investor=b.userId AND  c.investTime > '2014-12-25' AND c.investTime<'2015-01-10' ")
        	  .append(") AND EXISTS(SELECT 1 FROM t_user d WHERE d.id = b.userId AND d.createTime > '2014-12-25' AND d.createTime<'2015-01-10') AND NOT EXISTS(SELECT * FROM t_employee e WHERE e.userId=a.id) GROUP BY a.id ")
        	  .append("UNION ALL ")
        	  .append("SELECT a.id,f_formatting_username(a.username) username,a.dataCount1 FROM t_virtual_person a WHERE a.typeValue=1 ")
        	  .append("ORDER BY ct DESC  LIMIT 0,10");
              DataSet ds =  Database.executeQuery(conn, command.toString());
              ds.tables.get(0).rows.genRowsMap();
              list = ds.tables.get(0).rows.rowsMap;
          }
            catch (Exception e) {
               e.printStackTrace();
               log.error(e);
            }finally{
                conn.close();
            }
          return list;
    }
    
    public Map queryNextRepay(long userId) throws SQLException{
    	Connection conn = MySQL.getConnection();
    	Map <String, String>retMap = new HashMap<String, String>();
    	try {
    	    retMap = investDao.queryNextRepay(conn, userId);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        } finally{
            conn.close();
        }
    	return retMap;
    }
    
    public List<Map <String, String>> queryNextRepayList(long userId,String date) throws SQLException{
    	Connection conn = MySQL.getConnection();
    	List<Map <String, String>> retMap = new ArrayList<Map<String,String>>();
    	try {
    	    retMap = investDao.queryNextRepayList(conn, userId, date);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        } finally{
            conn.close();
        }
    	return retMap;
    }
    
    public Map <String, String> queryNextRepayTotal(long userId,String date) throws SQLException{
    	Connection conn = MySQL.getConnection();
    	Map <String, String> retMap = new HashMap<String, String>();
    	try {
    	    retMap = investDao.queryNextRepayTotal(conn, userId, date);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        } finally{
            conn.close();
        }
    	return retMap;
    }
    
    public List<Map <String, String>> queryNextRepayDetail(long userId,String date) throws SQLException{
    	Connection conn = MySQL.getConnection();
    	List<Map <String, String>> retMap = new ArrayList<Map<String,String>>();
    	try {
    	    retMap = investDao.queryNextRepayDetail(conn, userId, date);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        } finally{
            conn.close();
        }
    	return retMap;
    }
    
    public List<Map<String, String>> queryMyFriendValid(long userId) throws SQLException{
        List<Map<String, String>> list = null;
       Connection conn = MySQL.getConnection();
       try {
     	  
     	  StringBuffer command = new StringBuffer();
     	  command.append("SELECT IFNULL(COUNT(b.userId),0) myCt FROM t_user a ,t_recommend_user b ")
     	  .append("WHERE a.id=b.recommendUserId AND ")
     	  .append("EXISTS ( ")
     	  .append("SELECT 1 FROM t_invest c WHERE c.investor=b.userId AND  c.investTime > '2014-12-25' AND c.investTime<'2015-01-10' ")
     	  .append(") AND EXISTS(SELECT 1 FROM t_user d WHERE d.id = b.userId AND d.createTime > '2014-12-25' AND d.createTime<'2015-01-10') AND a.id = ")
     	  .append(userId);
           DataSet ds =  Database.executeQuery(conn, command.toString());
           ds.tables.get(0).rows.genRowsMap();
           list = ds.tables.get(0).rows.rowsMap;
       }
         catch (Exception e) {
            e.printStackTrace();
            log.error(e);
         }finally{
             conn.close();
         }
       return list;
 }
    public Map queryNextDebt(long userId) throws SQLException{
    	Connection conn = MySQL.getConnection();
    	Map <String, String>retMap = new HashMap<String, String>();
    	try {
    	    retMap = investDao.queryNextDebt(conn, userId);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        } finally{
            conn.close();
        }
    	return retMap;
    }
    
	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public RepayMentDao getRepayMentDao() {
		return repayMentDao;
	}

	public void setRepayMentDao(RepayMentDao repayMentDao) {
		this.repayMentDao = repayMentDao;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}


	public FundManagementService getFundManagementService() {
		return fundManagementService;
	}


	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}
	
	public Map<String, String> queryTmail(long userid) throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			return financeDao.queryTmail(conn, userid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw e;
		} finally {
			conn.close();
		}
	}
	
}
