package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FrontMyPaymentDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.admin.AfterCreditManageDao;
import com.sp2p.dao.admin.SendSMSDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_user;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.AwardService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.SMSUtil;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;

/**
 * @ClassName: AfterCreditManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:18:35
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 贷后管理业务处理层
 */
public class AfterCreditManageService extends BaseService {

	public static Log log = LogFactory.getLog(AfterCreditManageService.class);

	private AfterCreditManageDao afterCreditManageDao;
	private SelectedService selectedService;
	private AwardService awardService;
	private AssignmentDebtService assignmentDebtService;
	private FrontMyPaymentDao frontpayDao;
	private InvestDao investDao;
	private BorrowDao borrowDao;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @MethodName: queryAfterCreditAuditByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午10:20:22
	 * @Return:
	 * @Descb: 贷后管理最近3天还款记录
	 * @Throws:
	 */
	@SuppressWarnings( { "static-access", "unchecked" })
	public void queryLastRepayMentByCondition(String userName,String orgName,String userType, int borrowWay,
			String realName, String title, int status, String type,
			PageBean pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		title = Utility.filteSqlInfusion(title);
		type = Utility.filteSqlInfusion(type);
		orgName = Utility.filteSqlInfusion(orgName);
		userType = Utility.filteSqlInfusion(userType);
		
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		Calendar calendar = Calendar.getInstance();
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(orgName)) {
			condition.append(" and orgName  like '%"
					+ StringEscapeUtils.escapeSql(orgName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType  = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and  borrowTitle   LIKE '%"
					+ StringEscapeUtils.escapeSql(title.trim()) + "%'");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and  borrowWay  =" + borrowWay);
		}
		if (IConstants.DEFAULT_NUMERIC != status) {
			condition.append(" and  repayStatus  =" + status);
		}
		if ("".equals(type)) {
			Date date = calendar.getTime();
			condition.append(" and repayDate ='" + sf.format(date) + "'");
		} else if ("1".equals(type)) {
			calendar.add(calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			condition.append(" and repayDate ='" + sf.format(date) + "'");
		} else if ("2".equals(type)) {
			calendar.add(calendar.DAY_OF_YEAR, 2);
			Date date = calendar.getTime();
			condition.append(" and repayDate ='" + sf.format(date) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_repayment_h", resultFeilds,
					" order by  id  desc", condition + "");
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
	 * @MethodName: queryRepaymentAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午11:32:00
	 * @Return:
	 * @Descb: 根据条件统计最近还款总额
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentAmount(String userName,String orgName,String userType,
			int borrowWay, String realName, String title, int status,
			String type) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryRepaymentAmount(conn, userName,orgName,userType,
					borrowWay, realName, title, status, type);
			conn.commit();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public AfterCreditManageDao getAfterCreditManageDao() {
		return afterCreditManageDao;
	}

	public void setAfterCreditManageDao(
			AfterCreditManageDao afterCreditManageDao) {
		this.afterCreditManageDao = afterCreditManageDao;
	}

	/**
	 * @throws DataException
	 * @MethodName: queryRepayMentNoticeByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午02:53:11
	 * @Return:
	 * @Descb: 查询还款的沟通记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryRepayMentNoticeByCondition(long idLong, PageBean pageBean)
			throws Exception {
		String resultFeilds = " id,serviceContent,date_format(serviceTime,'%Y-%c-%d %T') as serviceTime ";
		StringBuffer condition = new StringBuffer();
		condition.append(" and  repayId  = " + idLong);
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " t_repayment_service", resultFeilds,
					" order by  id desc", condition.toString());
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
	 * @MethodName: addRepayMentNotice
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 下午07:02:10
	 * @Return:
	 * @Descb: 添加还款沟通记录
	 * @Throws:
	 */
	public long addRepayMentNotice(long idLong, String content)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = afterCreditManageDao.addRepayMentNotice(conn, idLong,
					content);
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

	/**
	 * @MethodName: queryForPaymentByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午06:32:51
	 * @Return:
	 * @Descb: 根据条件查询待收款记录
	 * @Throws: modify by houli 添加反选标志 inverse
	 */
	@SuppressWarnings("unchecked")
	public void queryForPaymentByCondition(String investor, String timeStart,
			String timeEnd, String title, int borrowWayInt, int groupInt,
			PageBean pageBean, boolean inverse ,String userType) throws Exception {
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		title = Utility.filteSqlInfusion(title);
		userType = Utility.filteSqlInfusion(userType);
		String resultFeilds = " orgName,userType,investor,realName ,groupName,  investTime  ,repayPeriod,id,borrowTitle,borrowWay,repayDate,isDayThe,round(forTotalSum,2) forTotalSum,username ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and  investor  like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and  userType  = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" AND  repayDate  >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" AND  repayDate  <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(title.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" AND  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {// 如果是反选
				condition.append(" AND (  groupId  !=" + groupInt
						+ "  or  groupId  is null ) ");
			} else {
				condition.append(" AND  groupId  =" + groupInt);
			}
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_forpayment_h ", resultFeilds, "",
					condition.toString());
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		}finally {
			conn.close();
		}

	}

	/**
	 * @MethodName: queryForPaymentByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午06:32:51
	 * @Return:
	 * @Descb: 根据条件查询待还款记录
	 * @Throws: modify by houli 添加反选标志 inverse
	 */
	@SuppressWarnings("unchecked")
	public void queryForPaymentByDueIn(String userType,String investor, String timeStart,
			String timeEnd, String title, int borrowWayInt, int groupInt,
			PageBean pageBean, boolean inverse) throws Exception{
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		title = Utility.filteSqlInfusion(title);
		userType = Utility.filteSqlInfusion(userType);
		String resultFeilds = "   *  ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and  investor  like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and  userType  = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" AND  repayDate  >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" AND  repayDate  <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(title.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" AND  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {// 如果是反选
				condition.append(" AND (  groupId  !=" + groupInt
						+ "  or  groupId  is null ) ");
			} else {
				condition.append(" AND  groupId  =" + groupInt);
			}
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_deuin_list ", resultFeilds,
					" order by id desc", condition.toString());
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
	 * @MethodName: queryForPaymentAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午06:33:16
	 * @Return:
	 * @Descb: 根据条件查询待收款统计
	 * @Throws:
	 */
	public Map<String, String> queryForPaymentAmount(String investor,
			String timeStart, String timeEnd, String title, int borrowWayInt,
			int groupInt, boolean inverse,String userType) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryForPaymentAmount(conn, investor,
					timeStart, timeEnd, title, borrowWayInt, groupInt, inverse,userType);
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
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryForPaymentTotalByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:09:16
	 * @Return:
	 * @Descb: 代收款总计记录
	 * @Throws: add by houli 2014-04-26 添加反选条件 inverse
	 */
	@SuppressWarnings("unchecked")
	public void queryForPaymentTotalByCondition(String userType,String investor,
			String timeStart, String timeEnd, int deadlineInt, int groupInt,
			PageBean pageBean, boolean inverse,String reffere) throws Exception,
			DataException {
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		userType = Utility.filteSqlInfusion(userType);
		
		String resultFeilds = " orgName,userType,investor , realName,groupName,round(investAmount,2) as investAmount ,scale , publishTime , round(borrowAmount,2) as borrowAmount ,borrowWay,isDayThe,repayPeriod,repayDate,round(forTotalSum,2) as forTotalSum,reffere";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and  investor  like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and  userType  = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and  repayDate  >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and  repayDate  <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (IConstants.DEFAULT_NUMERIC != deadlineInt) {
			condition.append(" and  deadline  =" + deadlineInt);
		}
		if (StringUtils.isNotBlank(reffere)) {
			condition.append(" and  reffere  like '%" + reffere+"%'");
		}
		if (IConstants.DEFAULT_NUMERIC != groupInt) {
			if (inverse) {
				condition.append(" and (  groupId  !=" + groupInt
						+ "  or  groupId  is null )");
			} else {
				condition.append(" and  groupId  =" + groupInt);
			}
		}

		StringBuffer sql = new StringBuffer();
		sql.append(" (SELECT *,(SELECT tp.`realName` FROM t_user tu,t_person tp,t_recommend_user tru WHERE tu.`id` = tru.`userId` AND tru.`recommendUserId` = tp.`userId` AND tu.username = v.`investor`) AS reffere FROM v_t_forpayment_h v) tb ");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, sql.toString(), resultFeilds, "",
					condition.toString());
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
	 * @throws DataException
	 * @MethodName: queryForPaymentTotalAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-20 下午10:08:58
	 * @Return:
	 * @Descb: 代收款总计统计
	 * @Throws:
	 */
	public Map<String, String> queryForPaymentTotalAmount(String investor,
			String timeStart, String timeEnd, int deadlineInt, int groupInt,
			boolean inverse,String userType) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryForPaymentTotalAmount(conn,
					investor, timeStart, timeEnd, deadlineInt, groupInt,
					inverse,userType);
			conn.commit();
		} finally {
			conn.close();
		}
		return map;
	}

	/**
	 * @MethodName: queryHasRepayByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午02:37:15
	 * @Return:
	 * @Descb: 查询已收款列表
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryHasRepayByCondition(String userName, String realName,String userType,
			String timeStart, String timeEnd, int borrowWayInt,
			int deadlineInt, int repayStatusInt, PageBean pageBean,
			String timeStart1, String timeEnd1)// add by houli 添加两个时间变量
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		userType = Utility.filteSqlInfusion(userType);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		timeStart1 = Utility.filteSqlInfusion(timeStart1);
		timeEnd1 = Utility.filteSqlInfusion(timeEnd1);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username   like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName   like '%"
					+ StringEscapeUtils.escapeSql(realName) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType   = '"
					+ StringEscapeUtils.escapeSql(userType) + "' ");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and realRepayDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and realRepayDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" and borrowWay =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != deadlineInt) {
			condition.append(" and deadline =" + deadlineInt);
		}
		if (IConstants.DEFAULT_NUMERIC != repayStatusInt) {
			condition.append(" and repayStatus =" + repayStatusInt);
		}
		// -------add by houli
		if (StringUtils.isNotBlank(timeStart1)) {
			condition.append(" and repayDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart1) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd1)) {
			condition.append(" and repayDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd1) + "'");
		}
		// ---------------
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_hasrepay_h ", resultFeilds,
					" order by  id desc", condition.toString());
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
	 * @MethodName: queryHasRePayAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午02:37:02
	 * @Return:
	 * @Descb: 已收款统计
	 * @Throws:
	 */
	public Map<String, String> queryHasRePayAmount(String userName,
			String realName,String userType, String timeStart, String timeEnd,
			int borrowWayInt, int deadlineInt, int repayStatusInt,
			String timeStart1, String timeEnd1) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryHasRePayAmount(conn, userName,
					realName,userType, timeStart, timeEnd, borrowWayInt, deadlineInt,
					repayStatusInt,
					// add by houli
					timeStart1, timeEnd1);
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
	 * @MethodName: queryLateRepayByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:16:54
	 * @Return:
	 * @Descb: 逾期的借款记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryLateRepayByCondition(String userName,String userType, int borrowWayInt,
			int statusInt, PageBean pageBean) throws Exception{
		userName = Utility.filteSqlInfusion(userName);
		userType = Utility.filteSqlInfusion(userType);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and userName  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType  = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" and  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != statusInt) {
			condition.append(" and  repayStatus  =" + statusInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_laterepay_h", resultFeilds, "",
					condition.toString());
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
	 * @MethodName: queryLateRepayAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:17:01
	 * @Return:
	 * @Descb: 逾期的借款统计
	 * @Throws:
	 */
	public Map<String, String> queryLateRepayAmount(String userName,String userType,
			int borrowWayInt, int statusInt) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryLateRepayAmount(conn, userName,userType,
					borrowWayInt, statusInt);
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
	 * 根据还款ID 查询还款详情
	 * 
	 * @throws DataException
	 * @throws Exception
	 */
	public void queryByrepayId(int id, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			StringBuffer table = new StringBuffer();
			table
					.append(" t_invest_repayment a	LEFT JOIN t_invest b ON a.invest_id = b.id	LEFT JOIN t_user c ON b.investor = c.id	left join (select username as borrowName ,t.id from t_borrow t LEFT JOIN t_user u  on  t.publisher = u.id) e	on e.id = b. borrowId ");
			StringBuffer filed = new StringBuffer();
			filed
					.append(" c.username , a.repayPeriod,a.lateDay, a.repayId ,a.realRepayDate ,e.borrowName ,FORMAT(a.recivedInterest,2) as recivedInterest , FORMAT(a.hasPrincipal,2) as hasPrincipal,FORMAT(a.hasInterest,2) as hasInterest,a.isWebRepay,a.isLate,FORMAT( a.recivedFI ,2) as recivedFI  ");
			dataPage(conn, pageBean, table.toString(), filed.toString(), "",
					" and  a.repayStatus = 2 and a.repayId = " + id);
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
	 * 根据还款ID 查询还款详情
	 * 
	 * @throws DataException
	 * @throws Exception
	 */
	public void queryByrepayIdDueId(int id,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			StringBuffer table = new StringBuffer();
			table.append("  t_invest_repayment a LEFT JOIN  t_repayment s  on a.repayId = s.id	LEFT JOIN t_invest b ON a.invest_id = b.id	LEFT JOIN t_user c ON b.investor = c.id	left join (select username as borrowName ,t.id from t_borrow t LEFT JOIN t_user u  on  t.publisher = u.id) e	on e.id = b. borrowId ");
			StringBuffer filed = new StringBuffer();
			filed.append(" c.username , a.repayPeriod,s.lateDay,FORMAT(a.recivedInterest,2) as recivedInterest , FORMAT(a.recivedPrincipal,2) as recivedPrincipal,FORMAT(a.hasInterest,2) as hasInterest,a.isLate,FORMAT( a.recivedFI ,2) as recivedFI ,s.id ,date_format(s.repayDate, '%Y-%m-%d') as repayDate,e.borrowName ,a.isWebRepay ");
			dataPage(conn, pageBean, table.toString(), filed.toString(), "",
					" and  a.repayStatus = 1 and a.repayId = " + id);
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
	 * @throws Exception
	 * @MethodName: queryOverduePaymentByCondition
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:42:31
	 * @Return:
	 * @Descb: 逾期垫付的借款记录
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public void queryOverduePaymentByCondition(String userName,String userType,
			int borrowWayInt, int statusInt, PageBean pageBean)
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		userType = Utility.filteSqlInfusion(userType);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and userName  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType  = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWayInt) {
			condition.append(" and  borrowWay  =" + borrowWayInt);
		}
		if (IConstants.DEFAULT_NUMERIC != statusInt) {
			condition.append(" and  repayStatus  =" + statusInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_overduepayment_h", resultFeilds, "",
					condition.toString());
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
	 * @MethodName: queryOverduePaymentAmount
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-21 下午05:43:05
	 * @Return:
	 * @Descb: 逾期垫付的借款统计
	 * @Throws:
	 */
	public Map<String, String> queryOverduePaymentAmount(String userName,String userType,
			int borrowWayInt, int statusInt) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryOverduePaymentAmount(conn,
					userName,userType, borrowWayInt, statusInt);
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
	 * @MethodName: queryRepaymentDetail
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:12:38
	 * @Return:
	 * @Descb: 还款记录详情
	 * @Throws:
	 */
	public Map<String, String> queryRepaymentDetail(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.queryRepaymentDetail(conn, id);
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
	 * @MethodName: queryRepaymentService
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:12:08
	 * @Return:
	 * @Descb: 借款客服沟通记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRepaymentService(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = afterCreditManageDao.queryRepaymentService(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @MethodName: queryRepaymentColectoin
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午02:12:27
	 * @Return:
	 * @Descb: 借款催收记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryRepaymentCollection(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = afterCreditManageDao.queryRepaymentCollection(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * @throws Exception
	 * @MethodName: addCollection
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:12:57
	 * @Return:
	 * @Descb: 添加催款记录
	 * @Throws:
	 */
	public long addCollection(long idLong, String colResult, String remark)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = afterCreditManageDao.addCollection(conn, idLong,
					colResult, remark);
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

	/**
	 * @throws Exception
	 * @MethodName: delCollection
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-2 下午04:35:41
	 * @Return:
	 * @Descb: 删除催收记录
	 * @Throws:
	 */
	public long delCollection(long idLong) throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = afterCreditManageDao.delCollection(conn, idLong);
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

	/**
	 * @throws Exception
	 * @throws Exception
	 * @MethodName: overduePaymentRepaySubmit
	 * @Param: AfterCreditManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-8 下午10:02:42
	 * @Return:
	 * @Descb: 逾期垫付还款
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> overduePaymentRepaySubmit(long idLong,
			Long adminId, String basePath) throws Exception {
		basePath = Utility.filteSqlInfusion(basePath);
		
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> borrowUserMap = new HashMap<String, String>();
		List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
		
		
		long ret = -1;
		try {

			// 查询借款信息得到借款时插入的平台收费标准
			Map<String, String> mapacc = borrowDao.queryBorrowCostByPayId(conn,
					idLong);
			String feelog = Convert.strToStr(mapacc.get("feelog"), "");
			Map<String, Double> feeMap = (Map<String, Double>) JSONObject
					.toBean(JSONObject.fromObject(feelog), HashMap.class);
			double investFeeRate = Convert.strToDouble(feeMap
					.get(IAmountConstants.INVEST_FEE_RATE)
					+ "", 0);

			Procedures.p_borrow_repayment_overdue(conn, ds, outParameterValues,
					idLong, adminId, basePath, new Date(), new BigDecimal(
							investFeeRate), 0, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			borrowUserMap = borrowDao.queryBorrowerById(conn, idLong);
			if(borrowUserMap != null){
				long userId = Convert.strToLong(borrowUserMap.get("publisher"), -1);
				userService.updateSign(conn, userId);//更换校验码
			}
			investorList = borrowDao.queryInvesterById(conn, idLong);
			if(investorList != null){
				for(Map<String, Object> investorMap : investorList){
					long userId = Convert.strToLong(investorMap.get("investor")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
					investorMap = null;
				}
			}
			if (ret <= 0) {
				conn.rollback();
			} else {
				// // 还款成功修改奖励机制
				// if ("1".equals(ret)) {
				// DataSet ds1 = MySQL.executeQuery(
				// conn,
				// " select a.id,a.investor userId,c.publisher from t_invest a left join t_repayment b on a.borrowId = b.borrowId  left join t_borrow c on a.borrowId = c.id where b.id ="
				// + idLong);
				// ds1.tables.get(0).rows.genRowsMap();
				// List<Map<String, Object>> list =
				// ds1.tables.get(0).rows.rowsMap;
				// for (Map<String, Object> map2 : list) {
				// long uId = Convert.strToLong(map2.get("userId") + "",
				// -1);
				// Object obj = map2.get("principal");
				// BigDecimal amounts = BigDecimal.ZERO;
				// if (obj != null) {
				// amounts = new BigDecimal(obj + "");
				// }
				// awardService.updateMoney(conn, uId, amounts,
				// IConstants.MONEY_TYPE_2, idLong);
				// assignmentDebtService.preRepayment(conn, idLong);
				// }
				// }
				conn.commit();
				map.put("msg", outParameterValues.get(1) + "");
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

	public void changeNumToStr(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if (list != null) {
				for (Map<String, Object> map : list) {
				if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_SECONDS)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_GENERAL)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				} else if (map.get("borrowWay").toString().equals(
						IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay",IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}
				if (map.get("repayStatus").toString().equals("1")) {
					map.put("repayStatus", "未偿还");
				} else if (map.get("repayStatus").toString().equals("2")) {
					map.put("repayStatus", "已偿还");
				} else if (map.get("repayStatus").toString().equals("3")) {
					map.put("repayStatus", "偿还中");
				}
				if (map.get("servier") == null || map.get("servier") == "") {
					map.put("servier", "未分配");
				}
				if (map.get("userType").toString().equals("1")) {
					map.put("userType", "个人");
				} else if (map.get("userType").toString().equals("2")) {
					map.put("userType", "企业");
				}
				if (map.get("orgName") == null || map.get("orgName") == "") {
					map.put("orgName", "");
				}
				if (map.get("realRepayDate") == null || map.get("realRepayDate") == "") {
					map.put("realRepayDate", "");
				}
				System.out.println(map);
			}
		}
	}
	public void changeNumToStr2(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if (list != null) {

			for (Map<String, Object> map : list) {
				if (Convert.strToStr(map.get("borrowWay")+"", "").equals(
						IConstants.BORROW_TYPE_NET_VALUE)) {
					map.put("borrowWay",
							IConstants.BORROW_TYPE_NET_VALUE_STR);
				} else if (Convert.strToStr(map.get("borrowWay")+"", "").equals(
						IConstants.BORROW_TYPE_SECONDS)) {
					map
							.put("borrowWay",
									IConstants.BORROW_TYPE_SECONDS_STR);
				} else if (Convert.strToStr(map.get("borrowWay")+"", "").equals(
						IConstants.BORROW_TYPE_GENERAL)) {
					map
							.put("borrowWay",
									IConstants.BORROW_TYPE_GENERAL_STR);
				} else if (Convert.strToStr(map.get("borrowWay")+"", "").equals(
						IConstants.BORROW_TYPE_FIELD_VISIT)) {
					map.put("borrowWay",
							IConstants.BORROW_TYPE_FIELD_VISIT_STR);
				} else if (Convert.strToStr(map.get("borrowWay")+"", "").equals(
						IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
					map
							.put(
									"borrowWay",
									IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
				} else if (Convert.strToStr(map.get("borrowWay")+"", "").equals(
						IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
					map.put("borrowWay",
							IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
				}
				if (map.get("groupName") == null || map.get("groupName") == "") {
					map.put("groupName", "未分配");
				}
				if (map.get("realNames") == null || map.get("realNames") == "") {
					map.put("realNames", "");
				}
				if (map.get("orgName") == null || map.get("orgName") == "") {
					map.put("orgName", "");
				}
				if (map.get("userType").toString().equals("1")) {
					map.put("userType", "个人");
				} else if (map.get("userType").toString().equals("2")) {
					map.put("userType", "企业");
				}
				if (Convert.strToLong(map.get("isDayThe") + "", -1L) == 1) {
					map.put("isDayThe", "否");
				} else {
					map.put("isDayThe", "是");
				}
			}
		}
	}
	public void changeNumToStr3(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		for (Map<String, Object> map : list) {
			if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_NET_VALUE)) {
				map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_SECONDS)) {
				map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_GENERAL)) {
				map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_FIELD_VISIT)) {
				map
						.put("borrowWay",
								IConstants.BORROW_TYPE_FIELD_VISIT_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
				map.put("borrowWay",
						IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
				map.put("borrowWay",
						IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
			}

			if (map.get("repayStatus").toString().equals("1")) {
				map.put("repayStatus", "未偿还");
			} else if (map.get("repayStatus").toString().equals("2")) {
				map.put("repayStatus", "已偿还");
			} else if (map.get("repayStatus").toString().equals("3")) {
				map.put("repayStatus", "偿还中");
			}
			if (map.get("groupName") == null || map.get("groupName") == "") {
				map.put("groupName", "未分配");
			}
			if (map.get("userType").toString().equals("1")) {
				map.put("userType", "个人");
			} else if (map.get("userType").toString().equals("2")) {
				map.put("userType", "企业");
			}
			if (Convert.strToLong(map.get("isDayThe") + "", -1L) == 1) {
				map.put("isDayThe", "否");
			} else {
				map.put("isDayThe", "是");
			}
			if (map.get("realName") == null || map.get("realName") == "") {
				map.put("realName", "");
			}
			if (map.get("orgName") == null || map.get("orgName") == "") {
				map.put("orgName", "");
			}
			
		}
	}
  
	public void changeNumToStr4(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		for (Map<String, Object> map : list) {
			if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_NET_VALUE)) {
				map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_SECONDS)) {
				map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_GENERAL)) {
				map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_FIELD_VISIT)) {
				map
						.put("borrowWay",
								IConstants.BORROW_TYPE_FIELD_VISIT_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
				map.put("borrowWay",
						IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
			} else if (map.get("borrowWay").toString().equals(
					IConstants.BORROW_TYPE_INSTITUTION_FLOW)) {
				map.put("borrowWay",
						IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
			}

			if (map.get("isWebRepay").toString().equals("1")) {
				map.put("isWebRepay", "否");
			} else if (map.get("isWebRepay").toString().equals("2")) {
				map.put("isWebRepay", "是");
			}

			if (map.get("repayStatus").toString().equals("1")) {
				map.put("repayStatus", "未偿还");
			} else if (map.get("repayStatus").toString().equals("2")) {
				map.put("repayStatus", "已偿还");
			} else if (map.get("repayStatus").toString().equals("3")) {
				map.put("repayStatus", "偿还中");
			}
			
			if (map.get("userType").toString().equals("1")) {
				map.put("userType", "个人");
			} else if (map.get("userType").toString().equals("2")) {
				map.put("userType", "企业");
			}
			
			if (map.get("realName") == null || map.get("realName") == "") {
				map.put("realName", "");
			}
			if (map.get("orgName") == null || map.get("orgName") == "") {
				map.put("orgName", "");
			}

		}

	}
	
	/**
	 * @MethodName: queryExperienceByCondition
	 * @Param: AfterCreditManageService
	 * @Author: L.X.Z
	 * @Date: 
	 * @Return:
	 * @Descb: 根据条件查询体验标记录
	 */
	@SuppressWarnings("unchecked")
	public void queryExperienceByCondition(String title,String state, String creationStart,
			String creationEnd, String waitingStart, String waitingEnd, PageBean pageBean) throws Exception{
		title = Utility.filteSqlInfusion(title);
		state = Utility.filteSqlInfusion(state);
		creationStart = Utility.filteSqlInfusion(creationStart);
		creationEnd = Utility.filteSqlInfusion(creationEnd);
		waitingStart = Utility.filteSqlInfusion(waitingStart);
		waitingEnd = Utility.filteSqlInfusion(waitingEnd);
		String resultFeilds = "   *  ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(title.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(state) && !"-1".equals(state)) {
			condition.append(" and  status  = '"
					+ StringEscapeUtils.escapeSql(state.trim()) + "' ");
		}
		if (StringUtils.isNotBlank(creationStart)) {
			condition.append(" AND  publishTime  >= '"
					+ StringEscapeUtils.escapeSql(creationStart) + "'");
		}
		if (StringUtils.isNotBlank(creationEnd)) {
			condition.append(" AND  publishTime  <= '"
					+ StringEscapeUtils.escapeSql(creationEnd) + "'");
		}
		if (StringUtils.isNotBlank(waitingStart)) {
			condition.append(" AND  nextRepaymentDate  >= '"
					+ StringEscapeUtils.escapeSql(waitingStart) + "'");
		}
		if (StringUtils.isNotBlank(waitingEnd)) {
			condition.append(" AND  nextRepaymentDate  <= '"
					+ StringEscapeUtils.escapeSql(waitingEnd) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_trial_borrow ", resultFeilds,
					" order by id desc", condition.toString());
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
     * @MethodName: queryExperienceByConditionExport
     * @Param: AfterCreditManageService
     * @Author:HJH
     * @Date: 
     * @Return:
     * @Descb: 根据条件查询体验标记录
     */
    @SuppressWarnings("unchecked")
    public void queryExperienceByConditionExport(String title,String state, String creationStart,
            String creationEnd, String waitingStart, String waitingEnd, PageBean pageBean) throws Exception{
        title = Utility.filteSqlInfusion(title);
        state = Utility.filteSqlInfusion(state);
        creationStart = Utility.filteSqlInfusion(creationStart);
        creationEnd = Utility.filteSqlInfusion(creationEnd);
        waitingStart = Utility.filteSqlInfusion(waitingStart);
        waitingEnd = Utility.filteSqlInfusion(waitingEnd);
        String resultFeilds = "   *  ";
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(title)) {
            condition.append(" and  borrowTitle  like '%"
                    + StringEscapeUtils.escapeSql(title.trim()) + "%' ");
        }
        if (StringUtils.isNotBlank(state) && !"-1".equals(state)) {
            condition.append(" and  status  = '"
                    + StringEscapeUtils.escapeSql(state.trim()) + "' ");
        }
        if (StringUtils.isNotBlank(creationStart)) {
            condition.append(" AND  publishTime  >= '"
                    + StringEscapeUtils.escapeSql(creationStart) + "'");
        }
        if (StringUtils.isNotBlank(creationEnd)) {
            condition.append(" AND  publishTime  <= '"
                    + StringEscapeUtils.escapeSql(creationEnd) + "'");
        }
        if (StringUtils.isNotBlank(waitingStart)) {
            condition.append(" AND  nextRepaymentDate  >= '"
                    + StringEscapeUtils.escapeSql(waitingStart) + "'");
        }
        if (StringUtils.isNotBlank(waitingEnd)) {
            condition.append(" AND  nextRepaymentDate  <= '"
                    + StringEscapeUtils.escapeSql(waitingEnd) + "'");
        }
        Connection conn = MySQL.getConnection();
        try {
            dataPage(conn, pageBean, " v_t_trial_borrow_export ", resultFeilds,
                    " order by id desc", condition.toString());
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
	 * 新增体验标
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addExperienceInfo(String title, String amount, String annualRate, String deadline, int raiseTerm, String paymentMode,String minSum,
			String maxSum ,String detail,String publisherName)
			throws Exception {
		Connection conn = MySQL.getConnection();
		;
		try {
			return frontpayDao.addExperienceInfo(conn, title, amount, annualRate, deadline, raiseTerm, paymentMode, minSum,
					maxSum ,detail,publisherName);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		}finally{
			conn.close();
		}
	}
	
	
	/**
	 * @Param: AfterCreditManageService
	 * @Return:
	 * @Descb: 体验标详情
	 * @Throws:
	 */
	public Map<String, String> viewExperienceInfo(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = afterCreditManageDao.viewExperienceInfo(conn, id);
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
	
	//查询要还的借款
	public void queryRepayment(long id,PageBean pageBean) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
            dataPage(conn, pageBean, "t_trial_repayment", "id,borrowId,planRepaymentDate,repaymentTime,repaymentAmount,seq_id", " order by seq_id asc", "  and  borrowId="+id);
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            conn.close();
        }
	}
	
	/**
	 * 还款
	 * investTrial(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @param repayment_id
	 * @param borrow_id
	 * @return
	 * @throws SQLException 
	 * Map<String,Object>
	 * @exception 
	 * @date:2014-9-10 下午4:53:05
	 * @since  1.0.0
	 */
	public Map<String, Object> investTrial(long repayment_id,long borrow_id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Map<String, Object> map = new HashMap<String, Object>();
	    List<Object> outParameterValues = new ArrayList<Object>();
	    DataSet ds = new DataSet();
	    try {
            Procedures.p_invest_trial(conn, ds, outParameterValues, repayment_id, borrow_id, -1 , "");
            int ret = Convert.strToInt(outParameterValues.get(0).toString(), -1);
            map.put("ret", outParameterValues.get(0));
            map.put("ret_msg", outParameterValues.get(1));
            if (ret!=1) {//失败
                conn.rollback();
                return map;
            }
            
            //查询出所有的投资人id
            List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
            investorList = borrowDao.queryInvesterTrialById(conn, borrow_id);
            if(investorList != null){
                for(Map<String, Object> investorMap : investorList){
                    if(investorMap!=null){
                        long uId = Convert.strToLong(investorMap.get("userId")+"", -1);
                        userService.updateSign(conn, uId);//更换校验码
                    }
                    investorMap = null;
                }
            }
            conn.commit();
        }
        catch (Exception e) {
             log.error(e);
             e.printStackTrace();
             conn.rollback();
        }finally{
            conn.close();
        }
	    return map;
	}
	
	/**
     * 自动还款
     * investTrialAuto
     * @auth hejiahua
     * @return
     * @throws SQLException 
     * Map<String,Object>
     * @exception 
     * @date:2014-9-10 下午4:53:05
     * @since  1.0.0
     */
    public boolean investTrialAuto() throws SQLException{
        Connection conn = MySQL.getConnection();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //查询是否开启体验标自动还款
            String sql_select = "SELECT COUNT(id) as c FROM  t_select t WHERE t.`selectKey` = 'AUTOTRAIL' AND deleted =1";
            DataSet ds_select = Database.executeQuery(conn, sql_select);
            Map<String, String> map_select = BeanMapUtils.dataSetToMap(ds_select);
            if (map_select!=null) {
                int open = Convert.strToInt(map_select.get("c"), -1);
                if (open<=0) {//没有开启，结束自动体验标自动还款
                    return true;
                }
            }
            
            Integer repayment_id = -1;//还款期数
            Integer borrow_id = -1;//还款标的
            String investTime  = DateUtil.dateToString(new Date());
            String sql = "SELECT id,borrowId,planRepaymentDate,repaymentTime,repaymentAmount,seq_id FROM t_trial_repayment t WHERE t.`planRepaymentDate`<='"+investTime+"' AND t.`repaymentTime` IS  NULL  AND  EXISTS (SELECT id FROM t_trial_borrow tb WHERE tb.`status` = 1)  ORDER BY borrowId ASC,seq_id ASC";
            DataSet ds_invest = Database.executeQuery(conn, sql);
            ds_invest.tables.get(0).rows.genRowsMap();
            List<Map<String, Object>> investList = ds_invest.tables.get(0).rows.rowsMap;
            if (investList==null  || investList.size()==0) {
                log.info("~~~~~~~~~~~~~~~~~~~~~~~~体验标自动还款,没有要还的借款~~~~~~~~~~~~~~~~~~`");
                return true;
            }
            for (Map<String, Object> invest : investList) {
                List<Object> outParameterValues = new ArrayList<Object>();
                DataSet ds = new DataSet();
                repayment_id = (Integer) invest.get("id");
                borrow_id =  (Integer) invest.get("borrowId");
                Procedures.p_invest_trial(conn, ds, outParameterValues, repayment_id, borrow_id, -1 , "");
                int ret = Convert.strToInt(outParameterValues.get(0).toString(), -1);
                map.put("ret", outParameterValues.get(0));
                map.put("ret_msg", outParameterValues.get(1));
                if (ret!=1) {//失败
                    conn.rollback();
                }else {
                  //查询出所有的投资人id
                    List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
                    investorList = borrowDao.queryInvesterTrialById(conn, borrow_id);
                    if(investorList != null){
                        for(Map<String, Object> investorMap : investorList){
                            if(investorMap!=null){
                                long uId = Convert.strToLong(investorMap.get("userId")+"", -1);
                                userService.updateSign(uId);//更换校验码
                            }
                            investorMap = null;
                        }
                    }
                }
                log.info("~~~~~~~~~~~~~~~~~~~~~~~~体验标自动还款,还款id:"+borrow_id+",期数id："+repayment_id+"，还款时间："+investTime+"~~~~~~~~~~~~~~~~~~`"+map.toString());
                conn.commit();
            }
        }
        catch (Exception e) {
             log.error(e);
             e.printStackTrace();
             conn.rollback();
        }finally{
            conn.close();
        }
        return true;
    }
	
	/**
	 * 体验记录
	 * queryExperienceBorrow
	 * @auth hejiahua
	 * @param pageBean
	 * @param username
	 * @param ticketNo
	 * @param batchId 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-21 下午3:29:31
	 * @since  1.0.0
	 */
	public void queryExperienceBorrow(PageBean pageBean,String username,String ticketNo,String batchId,long id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    StringBuffer condition = new StringBuffer();
	    condition.append(" and id="+id);
	    if (StringUtils.isNotBlank(username)) {
	        username = Utility.filteSqlInfusion(username);
	        condition.append("  and username like '%"+username+"%'");
	    }
	    if (StringUtils.isNotBlank(ticketNo)) {
	        ticketNo = Utility.filteSqlInfusion(ticketNo);
	        condition.append("  and ticketNo like '%"+ticketNo+"%'");
	    }
	    if (StringUtils.isNotBlank(batchId)) {
            condition.append("  and batchId = "+batchId+"");
        }
        
	    try {
            dataPage(conn, pageBean, "v_t_trial_ticket", "*", " order by id desc", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
           conn.close();
        }
	}
	
	/**
	 * 查询今日未还款
	 * queryExperienceBorrow
	 * @auth bao
	 * @param pageBean
	 * @param username
	 * @param ticketNo
	 * @param batchId 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-21 下午3:29:31
	 * @since  1.0.0
	 */
	public void queryTodayRepay() throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
	    	Date date = new Date();
			DateFormat df = new SimpleDateFormat(UtilDate._dtShort);
			String repayDate  = df.format(date);
			
	    	String sql = "SELECT count(a.id) ct FROM t_repayment a WHERE a.repayDate='" + repayDate + "' AND a.repayStatus=1";
            DataSet ds = Database.executeQuery(conn, sql);
            Map rmap = BeanMapUtils.dataSetToMap(ds);
            String atoday = (String) rmap.get("ct");
            
            
            sql = "SELECT COUNT(a.id) ct FROM t_repayment a WHERE a.`isLate`=2 AND a.`repayStatus` = 1";
            ds = Database.executeQuery(conn, sql);
            Map rmap1 = BeanMapUtils.dataSetToMap(ds);
            String lateRepay = (String) rmap1.get("ct");
            
//            ds.tables.get(0).rows.genRowsMap();
//    		sql = null;
//			List list = ds.tables.get(0).rows.rowsMap;
			//如果有数据，则发送数据
			if (Integer.parseInt(atoday) > 0 || Integer.parseInt(lateRepay) > 0){
				log.info("---------t_invest_repayment:atoday:" + atoday);
				sql = "SELECT a.bakValue FROM t_param a WHERE a.bigType = 1";
	            ds = Database.executeQuery(conn, sql);
	            Map map = BeanMapUtils.dataSetToMap(ds);
	    		sql = null;
	    		StringBuffer sb = new StringBuffer("您好，今天有");
				if (Integer.parseInt(atoday) > 0) {
					sb.append(atoday).
					append("笔还款，");
				}
	    		if (Integer.parseInt(lateRepay) > 0){
	    			sb.append("逾期还款").
		    		append(lateRepay).
		    		append("笔，");
	    		}
	    		sb.append("请及时处理。");
	    		log.info("sendsms_content:"+sb.toString());
	    		
	    		
	    		String bakValue = (String) map.get("bakValue");
	    		String [] phone = bakValue.split(",");
	    		for (int i = 0,j = phone.length; i < j; i++){
	    			SMSUtil.sendHrSms(sb.toString(), phone[i], null, null);
	    		}
	    		
			} else{
				log.info("---------t_invest_repayment:nodata." );
			}
			conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
           conn.close();
        }
	}
	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public AwardService getAwardService() {
		return awardService;
	}

	public void setAwardService(AwardService awardService) {
		this.awardService = awardService;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public FrontMyPaymentDao getFrontpayDao() {
		return frontpayDao;
	}

	public void setFrontpayDao(FrontMyPaymentDao frontpayDao) {
		this.frontpayDao = frontpayDao;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

}
