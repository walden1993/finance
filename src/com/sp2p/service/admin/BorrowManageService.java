package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
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

import com.google.zxing.Result;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.NoticeTaskDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RepaymentRecordDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.UserDao;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.ServletUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.action.admin.FIManageAction;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.dao.RepamentDao;
import com.sp2p.dao.admin.AdminDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.database.Dao.Tables.t_borrow_new;
import com.sp2p.service.AwardService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.util.SpringUtil;

/**
 * @ClassName: BorrowManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-10 下午10:07:28
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 后台借款管理业务处理
 */
public class BorrowManageService extends BaseService {

	public static Log log = LogFactory.getLog(BorrowManageService.class);

	private BorrowManageDao borrowManageDao;
	private SelectedService selectedService;
	private AwardService awardService;
	private InvestDao investDao;
	private AccountUsersDao accountUsersDao;
	private RepamentDao repamentDao;
	private UserDao userDao;
	private FundRecordDao fundRecordDao;
	private RepaymentRecordDao repaymentRecordDao;
	private AdminDao adminDao;
	private BorrowDao borrowDao;
	private PlatformCostService platformCostService;
	private NoticeTaskDao noticeTaskDao;
	private OperationLogDao operationLogDao;
	private UserService userService;
	private SysparService sysparService;
	
	

	public void setSysparService(SysparService sysparService) {
		this.sysparService = sysparService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @MethodName: queryAllCirculationRepayRecordByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-23 下午11:24:18
	 * @Return:
	 * @Descb: 根据条件查询流转标还款记录
	 * @Throws:
	 */
	public void queryAllCirculationRepayRecordByCondition(String userName,
			int borrowStatus, String borrowTitle,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		
		String resultFeilds = " a.*";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and a.username  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and a.borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(borrowTitle.trim())
					+ "','%')");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and borrowStatus =" + borrowStatus);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_circulation_repayrecord a",
					resultFeilds, " order by a.id asc", condition.toString());
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
	 * @MethodName: updateBorrowCirculationStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午03:21:21
	 * @Return:
	 * @Descb: 更新流转标状态
	 * @Throws:
	 */
	public long updateBorrowCirculationStatus(long borrowId, long reciverId,
			long statusLong, String auditOpinion, Long adminId,
			String basePath, Map<String, Object> platformCostMap)
			throws Exception {
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		basePath = Utility.filteSqlInfusion(basePath);
		
		Connection conn = MySQL.getConnection();
		int circulationStatus = -1;
		double borrowSum = 0;
		double yearRate = 0;
		int deadline = 0;
		int isDayThe = 1;
		Long result = -1L;
		try {
			Map<String, String> borrowMap = borrowManageDao.queryBorrowInfo(
					conn, borrowId);
			if (borrowMap == null)
				borrowMap = new HashMap<String, String>();
			borrowSum = Convert.strToDouble(borrowMap.get("borrowAmount") + "",
					0);
			yearRate = Convert.strToDouble(borrowMap.get("annualRate") + "", 0);
			deadline = Convert.strToInt(borrowMap.get("deadline") + "", 0);
			isDayThe = Convert.strToInt(borrowMap.get("isDayThe") + "", 1);
			// 处理流转标
			result = borrowManageDao.updateBorrowCirculationStatus(conn,
					borrowId, statusLong, auditOpinion, circulationStatus, 10);
			if (result > 0) {
				// 审核通过添加还款记录
				if (statusLong == 2) {
					AmountUtil au = new AmountUtil();
					List<Map<String, Object>> rateCalculateOneList = au
							.rateCalculateOne(borrowSum, yearRate, deadline,
									isDayThe);
					for (Map<String, Object> oneMap : rateCalculateOneList) {
						String repayPeriod = oneMap.get("repayPeriod") + "";
						String repayDate = oneMap.get("repayDate") + "";
						// 添加还款记录,还款金额和利息在投资时进行累加
						result = borrowManageDao.addRepayRecord(conn,
								repayPeriod, 0, 0, borrowId, 0, 0, repayDate);
					}
				}
			}
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
	 * @MethodName: queryBorrowCirculationDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午02:52:55
	 * @Return:
	 * @Descb: 查询流转标详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowCirculationDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowCirculationDetailById(conn, id);
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
	 * @MethodName: queryCirculationRepayRecord
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午07:37:39
	 * @Return:
	 * @Descb: 查询流转标还款记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryCirculationRepayRecord(long borrowId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = null;
		try {
			list = borrowManageDao.queryCirculationRepayRecord(conn, borrowId);
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
	 * @MethodName: queryAllCirculationByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-5-20 上午11:37:27
	 * @Return:
	 * @Descb: 查询流转标借款
	 * @Throws:
	 */
	public void queryAllCirculationByCondition(String userName, int borrowWay,
			int borrowStatus, String undertaker,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		undertaker = Utility.filteSqlInfusion(undertaker);
		
		String resultFeilds = " a.*,b.userid,b.counts,c.userName as undertakerName ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and a.username  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(undertaker)) {
			condition
					.append(" and c.userName  LIKE CONCAT('%','"
							+ StringEscapeUtils.escapeSql(undertaker.trim())
							+ "','%')");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and a.borrowStatus =" + borrowStatus);
		}
		condition.append(" and a.borrowShow=2");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_circulation a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid"
							+ " left join t_admin c on a.undertaker=c.id",
					resultFeilds, " order by a.borrowStatus asc,a.id desc",
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
	 * @MethodName: queryBorrowFistAuditByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的初审借款记录
	 * @Throws:
	 */
	public void queryBorrowFistAuditByCondition(String userName, int borrowWay,String orgName,String userType,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		orgName = Utility.filteSqlInfusion(orgName);
		userType = Utility.filteSqlInfusion(userType);
		
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
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}

		String filed = " a.*,b.counts";

		String table = "(select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userId=b.userid "
				+ " INNER JOIN v_t_user_approve_lists c on a.userId = c.uid where a.borrowWay >2  "
				+ "UNION ALL SELECT  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth "
				+ "where auditStatus = 3  GROUP BY userid) b ON a.userId=b.userid   "
				+ "INNER JOIN v_t_user_base_approve_lists d  on a.userId=d.uuid where a.borrowWay <3 and d.auditStatus=3) t ";
		// ----
		Connection conn = MySQL.getConnection();
		try {
			// 秒还净值个人资料通过审核即可 其它借款需要个人资料+工作认证+5项基本认证
			dataPage(conn, pageBean, table,
			// ---
					resultFeilds, " order by id desc ", condition.toString());
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
	 * add by houli 查询等待资料审核的数据
	 * 
	 * @param userName
	 * @param borrowWay
	 * @param pageBean
	 * @throws Exception
	 * @throws DataException
	 */
	public void queryBorrowWaitingAuditByCondition(String userName,
			int borrowWay, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();

		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		String filed = "a.id,a.userId,a.username,a.realName,b.counts as counts,a.borrowWay,a.borrowTitle,"
				+ "a.borrowAmount,a.annualRate,a.deadline,a.raiseTerm,a.isDayThe ,a.borrowShow,a.orgName,a.userType";
		String table = " (select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userId=b.userid "

				+ " inner join v_t_user_un_approve_lists c on a.userid = c.uid where a.borrowway >2 "
				+ "union all select  "
				+ filed
				+ " from v_t_borrow_h_firstaudit a left join (select userid,count(1) as counts from t_materialsauth "
				+ "where auditstatus = 3  group by userid) b on a.userid=b.userid   "
				+ "inner join v_t_user_base_approve_lists d  on a.userid=d.uuid where a.borrowway <3 and d.auditstatus!=3) t";

		Connection conn = MySQL.getConnection();
		try {
			// 秒还净值个人资料通过审核即可 其它借款需要个人资料+工作认证+5项基本认证
			dataPage(conn, pageBean, table, resultFeilds, "order by  id desc ",
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
	 * @MethodName: queryBorrowFistAuditDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款初审中的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFistAuditDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFistAuditDetailById(conn, id);
			map.put("mailContent", "");
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
	 * @MethodName: queryBorrowTenderInByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的招标中借款记录
	 * @Throws:
	 */
	public void queryBorrowTenderInByCondition(String userName,String userType, int borrowWay,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		userType = Utility.filteSqlInfusion(userType);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "'");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_tenderin a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowTenderInDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款招标中的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTenderInDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapNotick = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTenderInDetailById(conn, id);
			if (map != null) {
				long userId = Convert.strToLong(map.get("userId"), -1L);
				mapNotick = noticeTaskDao.queryNoticeTask(conn, userId, id);
				if (mapNotick == null) {
					Map<String, String> maps = noticeTaskDao
							.queryNoticeTasklog(conn, userId, id);
					map.put("mailContent", Convert.strToStr(maps
							.get("mail_info")
							+ "", ""));
				} else {
					map.put("mailContent", Convert.strToStr(mapNotick
							.get("mail_info")
							+ "", ""));
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
		return map;
	}

	/**
	 * @MethodName: queryBorrowFullScaleByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的满标借款记录
	 * @Throws:
	 */
	public void queryBorrowFullScaleByCondition(String userName, int borrowWay,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_fullscale a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowFullScaleDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款满标的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFullScaleDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapNotick = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFullScaleDetailById(conn, id);
			if (map != null) {
				long userId = Convert.strToLong(map.get("userId"), -1L);
				mapNotick = noticeTaskDao.queryNoticeTask(conn, userId, id);
				if (mapNotick == null) {
					Map<String, String> maps = noticeTaskDao
							.queryNoticeTasklog(conn, userId, id);
					map.put("mailContent", Convert.strToStr(maps
							.get("mail_info")
							+ "", ""));
				} else {
					map.put("mailContent", Convert.strToStr(mapNotick
							.get("mail_info")
							+ "", ""));
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
		return map;
	}

	/**
	 * @MethodName: queryBorrowFlowMarkByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的流标借款记录
	 * @Throws:
	 */
	public void queryBorrowFlowMarkByCondition(String userName,String userType, int borrowWay,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		userType = Utility.filteSqlInfusion(userName);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h_flowmark a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * add by houli 获得所有等待资料审核的借款
	 * 
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAllWaitingBorrow() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>();
		try {
			returnMap = borrowManageDao.queryAllWaitingBorrow(conn);
			conn.commit();
			return returnMap;
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
	 * @MethodName: queryBorrowFlowMarkDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款流标的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowFlowMarkDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFlowMarkDetailById(conn, id);
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
	 * @MethodName: queryBorrowAllByCondition
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:09:16
	 * @Return:
	 * @Descb: 查询后台借款管理中的借款记录
	 * @Throws:
	 */
	public void queryBorrowAllByCondition(String userName, int borrowWay, String realName, String orgName,String userType,
			int borrowStatus,String isNew, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		orgName = Utility.filteSqlInfusion(orgName);
		userType = Utility.filteSqlInfusion(userType);
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(isNew)) {
			condition.append(" and isNew  = "
					+ StringEscapeUtils.escapeSql(isNew.trim()) + " ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(orgName)) {
			condition.append(" and orgName  like '%"
					+ StringEscapeUtils.escapeSql(orgName.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType = '"
					+ StringEscapeUtils.escapeSql(userType.trim()) + "' ");
		}
		if (IConstants.DEFAULT_NUMERIC != borrowWay) {
			condition.append(" and borrowWay =" + borrowWay);
		}
		if (IConstants.DEFAULT_NUMERIC != borrowStatus) {
			condition.append(" and borrowStatus =" + borrowStatus);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(
					conn,
					pageBean,
					" v_t_borrow_h a LEFT JOIN (SELECT userid,COUNT(1) AS counts FROM t_materialsauth where auditStatus = 3 GROUP BY userid) b ON a.userid=b.userid ",
					resultFeilds, " order by id desc", condition.toString());
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
	 * @MethodName: queryBorrowAllDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款的借款详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowAllDetailById(long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowAllDetailById(conn, id);
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
	 * @throws DataException
	 * @MethodName: updateBorrowFistAuditStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:19:48
	 * @Return:
	 * @Descb: 更新初审中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowFistAuditStatus(long id, long reciver, int status,
			String msg, String auditOpinion, long sender, String basePath,String displayTime,String foreknow)
			throws Exception {
		//String displayTime
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dTime = df.parse(displayTime);
		
		msg = Utility.filteSqlInfusion(msg);
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		basePath = Utility.filteSqlInfusion(basePath);
		
		int iforeknow = Integer.parseInt(foreknow);
		
		Long result = -1L;
		long userId = -1;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		Connection conn = MySQL.getConnection();
		// 得到管理员信息
		adminMap = adminDao.queryAdminById(conn, sender);
		if (status == IConstants.BORROW_STATUS_2) {

			try {
				Procedures.p_borrow_audit(conn, ds, outParameterValues, id,
						sender, status, msg, auditOpinion, basePath,
						new Date(),dTime/*displayTime*/, iforeknow, 0, "");
				map_ret.put("out_ret", outParameterValues.get(0) + "");
				map_ret.put("out_desc", outParameterValues.get(1) + "");
				result = Convert.strToLong(outParameterValues.get(0) + "", -1);
				map = borrowManageDao.queryBorrowerById(conn, id);
				if(map != null){
					userId = Convert.strToLong(map.get("publisher"), -1);
					userService.updateSign(conn, userId);//更换校验码
				}
				if (result < 1) {
					conn.rollback();
					return -1L;
				}else{
				// 添加操作日志
				operationLogDao.addOperationLog(conn, "t_borrow", Convert
						.strToStr(adminMap.get("userName"), ""),
						IConstants.UPDATE, Convert.strToStr(adminMap
								.get("lastIP"), ""), 0, "初审通过", 2);
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
		} else {
			conn.close();
			// 撤消初审中的借款
			result = reBackBorrowFistAudit(id, sender, basePath, msg,
					auditOpinion);
			// Procedures.p_borrow_cancel(conn, ds, outParameterValues, id,
			// sender, status, auditOpinion, basePath, 0, "");
			// map_ret.put("out_ret", outParameterValues.get(0)+"");
			// map_ret.put("out_desc", outParameterValues.get(1)+"");
			// 添加操作日志
			// operationLogDao.addOperationLog(conn, "t_borrow",
			// Convert.strToStr(adminMap.get("userName"), ""),
			// IConstants.UPDATE, Convert.strToStr(adminMap.get("lastIP"), ""),
			// 0, "管理员撤销借款", 2);
		}
		return result;
	}
	

	/**
	 * @throws DataException
	 * @MethodName: updateBorrowTenderInStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:20:27
	 * @Return:
	 * @Descb: 更新招标中的借款状态
	 * @Throws:
	 */
	public Long updateBorrowTenderInStatus(long id, String auditOpinion,String displayTime)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = borrowManageDao.updateBorrowTenderInStatus(conn, id,
					auditOpinion,displayTime);
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
	 * @throws DataException
	 * @throws Exception
	 * @MethodName: reBackBorrowTenderIn
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午08:40:22
	 * @Return:
	 * @Descb: 撤消借款
	 * @Throws:
	 */
	public long reBackBorrow(long id, long aId, String basePath)
			throws Exception {
		basePath = Utility.filteSqlInfusion(basePath);
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		ContextLoader.getCurrentWebApplicationContext().getServletContext()
				.getAttribute(
						IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		Map<String, String> borrowUserMap = new HashMap<String, String>();
		List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
		try {
			Procedures.p_borrow_cancel(conn, ds, outParameterValues, id, aId,
					6, "", basePath, -1, "");
			map_ret.put("out_ret", outParameterValues.get(0) + "");
			map_ret.put("out_desc", outParameterValues.get(1) + "");
			returnId = Convert.strToLong(outParameterValues.get(0) + "", -1);
			
			borrowUserMap = borrowManageDao.queryBorrowerById(conn, id);
			if(borrowUserMap != null){
				long userId = Convert.strToLong(borrowUserMap.get("publisher"), -1);
				userService.updateSign(conn, userId);//更换校验码
			}
			investorList = borrowManageDao.queryInvesterById(conn, id);
			if(investorList != null){
				for(Map<String, Object> map : investorList){
					long userId = Convert.strToLong(map.get("investor")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
					map = null;
				}
			}
			
			if (returnId < 1) {
				conn.rollback();
				return -1L;
			}
			
			// 添加操作日志
			adminMap = adminDao.queryAdminById(conn, aId);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
					adminMap.get("userName"), ""), IConstants.UPDATE, Convert
					.strToStr(adminMap.get("lastIP"), ""), 0, "管理员撤销借款", 2);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			return -1L;
		} finally {
			conn.close();
		}
		return returnId;
	}

	//关闭满标借款复审通过后的自动投标状态         liyanhua
	public Long updateUserBidStatus(Long id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long userId = -1;
		try {
			userId = BorrowManageDao.updateUserBidStatus(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return userId;
	}
	
	/**
	 * @throws Exception
	 * @MethodName: updateBorrowFullScaleStatus
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-13 下午01:21:09
	 * @Return:
	 * @Descb: 更新满标的借款状态
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> updateBorrowFullScaleStatus(long id,
			long status, String auditOpinion, long adminId, String basePath)
			throws Exception {
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		basePath = Utility.filteSqlInfusion(basePath);
		
		Connection conn = MySQL.getConnection();
		double investFeeRate = 0;// 投资管理费
		double borrowFeeRateOne = 0;// 秒还借款管理费
		double borrowInmonthFeeRateOne = 0;// 净值借款2个月内管理费率
		double borrowOutmonthFeeRateOne = 0;// 净值借款2个月外管理费率
		double borrowDayFeeRateOne = 0;// 净值借款天标管理费率
		double borrowInmonthFeeRateTwo = 0;// 信用借款2个月内管理费率
		double borrowOutmonthFeeRateTwo = 0;// 信用借款2个月外管理费率
		double borrowDayFeeRateTwo = 0;// 信用借款天标管理费率
		double borrowInmonthFeeRateThree = 0;// 机构担保借款2个月内管理费率
		double borrowOutmonthFeeRateThree = 0;// 机构担保借款2个月外管理费率
		double borrowDayFeeRateThree = 0;// 机构担保借款天标管理费率
		double borrowInmonthFeeRateFour = 0;// 实地考察借款2个月内管理费率
		double borrowOutmonthFeeRateFour = 0;// 实地考察借款2个月外管理费率
		double borrowDayFeeRateFour = 0;// 实地考察借款天标管理费率
		String identify = id + "_" + System.currentTimeMillis() + "";
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		List<Object> outParameters = new ArrayList<Object>();
		Map<String, String> borrowUserMap = new HashMap<String, String>();
		List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
		
		try {
			// 满标审核前判断处理
			Procedures.p_borrow_auth_fullscale(conn, ds, outParameterValues,
					id, status, -1, "", new BigDecimal(0.00), new BigDecimal(
							0.00), 0, 0, 0);
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (ret <= 0) {
				map.put("ret", ret + "");
				map.put("ret_desc", outParameterValues.get(1) + "");
				conn.rollback();
				return map;
			}
			// 审核通过才生成还款记录
			if (ret == 4) {
				double borrowAmount = Convert.strToDouble(outParameterValues
						.get(2)
						+ "", 0);
				double annualRate = Convert.strToDouble(outParameterValues
						.get(3)
						+ "", 0);
				int deadline = Convert.strToInt(outParameterValues.get(4) + "",
						0);
				int isDayThe = Convert.strToInt(outParameterValues.get(5) + "",
						1);
				int paymentMode = Convert.strToInt(outParameterValues.get(6)
						+ "", 1);

				// 生成还款记录
				List<Map<String, Object>> repayMapList = null;
				AmountUtil au = new AmountUtil();
				if (paymentMode == 1) {
					// 按月等额还款
					repayMapList = au.rateCalculateMonth(borrowAmount,
							annualRate, deadline, isDayThe);
				} else if (paymentMode == 2) {
					// 先息后本还款
					repayMapList = au.rateCalculateSum(borrowAmount,
							annualRate, deadline, isDayThe);
				} else if (paymentMode == 3) {
					// 秒还还款
					repayMapList = au.rateSecondsSum(borrowAmount, annualRate,
							deadline);
				}// add by c_j 13.07.25增加一次性还款
				else if (paymentMode == 4) {
					repayMapList = au.rateCalculateOne(borrowAmount,
							annualRate, deadline, isDayThe);
				}
				String repayPeriod = ""; // 还款期数
				double stillPrincipal = 0; // 应还本金
				double stillInterest = 0; // 应还利息
				double principalBalance = 0; // 剩余本金
				double interestBalance = 0; // 剩余利息
				double totalSum = 0; // 本息余额
				double totalAmount = 0; // 还款总额
				double mRate = 0; // 月利率
				String repayDate = "";
				int count = 1;
				for (Map<String, Object> paymentMap : repayMapList) {
					repayPeriod = paymentMap.get("repayPeriod") + "";
					stillPrincipal = Convert.strToDouble(paymentMap
							.get("stillPrincipal")
							+ "", 0);
					stillInterest = Convert.strToDouble(paymentMap
							.get("stillInterest")
							+ "", 0);
					principalBalance = Convert.strToDouble(paymentMap
							.get("principalBalance")
							+ "", 0);
					interestBalance = Convert.strToDouble(paymentMap
							.get("interestBalance")
							+ "", 0);
					totalSum = Convert.strToDouble(paymentMap.get("totalSum")
							+ "", 0);
					totalAmount = Convert.strToDouble(paymentMap
							.get("totalAmount")
							+ "", 0);
					repayDate = paymentMap.get("repayDate") + "";
					mRate = Convert
							.strToDouble(paymentMap.get("mRate") + "", 0);
					// 添加预还款记录
					ret = repamentDao.addPreRepament(conn, id, identify,
							repayPeriod, stillPrincipal, stillInterest,
							principalBalance, interestBalance, totalSum,
							totalAmount, mRate, repayDate, count);
					count++;
					if (ret <= 0) {
						break;
					}
				}

				if (ret <= 0) {
					map.put("ret", ret + "");
					map.put("ret_desc", "执行失败");
					conn.rollback();
					return map;
				}
				// 查询借款信息得到借款时插入的平台收费标准
				Map<String, String> mapacc = borrowDao
						.queryBorrowCost(conn, id);
				String feelog = Convert.strToStr(mapacc.get("feelog"), "");
				Map<String, Double> feeMap = (Map<String, Double>) JSONObject
						.toBean(JSONObject.fromObject(feelog), HashMap.class);
				investFeeRate = Convert.strToDouble(feeMap
						.get(IAmountConstants.INVEST_FEE_RATE)
						+ "", 0);
				borrowFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_FEE_RATE_1)
						+ "", 0);
				borrowInmonthFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_1)
						+ "", 0);
				borrowOutmonthFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_1)
						+ "", 0);
				borrowDayFeeRateOne = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_1)
						+ "", 0);
				borrowInmonthFeeRateTwo = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_2)
						+ "", 0);
				borrowOutmonthFeeRateTwo = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_2)
						+ "", 0);
				borrowDayFeeRateTwo = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_2)
						+ "", 0);
				borrowInmonthFeeRateThree = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_3)
						+ "", 0);
				borrowOutmonthFeeRateThree = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_3)
						+ "", 0);
				borrowDayFeeRateThree = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_3)
						+ "", 0);
				borrowInmonthFeeRateFour = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_INMONTH_FEE_RATE_4)
						+ "", 0);
				borrowOutmonthFeeRateFour = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_OUTMONTH_FEE_RATE_4)
						+ "", 0);
				borrowDayFeeRateFour = Convert.strToDouble(feeMap
						.get(IAmountConstants.BORROW_DAY_FEE_RATE_4)
						+ "", 0);
			}

			// 满标审核处理
			Procedures.p_borrow_deal_fullscale(conn, ds, outParameters, id,
					adminId, status, new Date(), auditOpinion, identify,
					basePath, new BigDecimal(investFeeRate), new BigDecimal(
							borrowFeeRateOne), new BigDecimal(
							borrowInmonthFeeRateOne), new BigDecimal(
							borrowOutmonthFeeRateOne), new BigDecimal(
							borrowDayFeeRateOne), new BigDecimal(
							borrowInmonthFeeRateTwo), new BigDecimal(
							borrowOutmonthFeeRateTwo), new BigDecimal(
							borrowDayFeeRateTwo), new BigDecimal(
							borrowInmonthFeeRateThree), new BigDecimal(
							borrowOutmonthFeeRateThree), new BigDecimal(
							borrowDayFeeRateThree), new BigDecimal(
							borrowInmonthFeeRateFour), new BigDecimal(
							borrowOutmonthFeeRateFour), new BigDecimal(
							borrowDayFeeRateFour), -1, "");
			ret = Convert.strToLong(outParameters.get(0) + "", -1);
			if (ret > 0 && status == 4) {
				// 添加系统操作日志
				adminMap = adminDao.queryAdminById(conn, adminId);
				operationLogDao.addOperationLog(conn, "t_borrow", Convert
						.strToStr(adminMap.get("userName"), ""),
						IConstants.UPDATE, Convert.strToStr(adminMap
								.get("lastIP"), ""), 0, "满标复审通过", 2);
				// 提成奖励
				DataSet ds1 = MySQL.executeQuery(conn," select DISTINCT a.id as id,a.investor as userId,a.realAmount as realAmount,c.publisher as publisher from t_invest a left join t_repayment b on a.borrowId = b.borrowId  left join t_borrow c on a.borrowId = c.id where c.id ="
										+ id);
				ds1.tables.get(0).rows.genRowsMap();
				List<Map<String, Object>> list = ds1.tables.get(0).rows.rowsMap;
				for (Map<String, Object> map2 : list) {
					long uId = Convert.strToLong(map2.get("userId") + "", -1);
					long investId = Convert.strToLong(map2.get("id") + "", -1);
					Object obj = map2.get("realAmount");
					BigDecimal amounts = BigDecimal.ZERO;
					if (obj != null) {
						amounts = new BigDecimal(obj + "");
					}
					ret = awardService.updateMoneyNew(conn, uId, amounts,
							IConstants.MONEY_TYPE_1, investId);
				}

			}
			map.put("ret", ret + "");
			map.put("ret_desc", outParameters.get(1) + "");
			borrowUserMap = borrowManageDao.queryBorrowerById(conn, id);
			if(borrowUserMap != null){
				long userId = Convert.strToLong(borrowUserMap.get("publisher"), -1);
				userService.updateSign(conn, userId);//更换校验码
			}
			investorList = borrowManageDao.queryInvesterById(conn, id);
			if(investorList != null){
				for(Map<String, Object> investorMap : investorList){
					long userId = Convert.strToLong(investorMap.get("investor")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
				}
			}
			
			if (ret <= 0) {
				conn.rollback();
			} else {
				conn.commit();
			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
			//颁发活动奖励
			sendAward(id,adminId);
		}
		return map;
	}
	
	
	/**
	 * 颁发活动奖励
	 * @param conn
	 * @param bid_id
	 * @throws SQLException 
	 */
	public void sendAward(long bid_id,long adminId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			boolean isOpen =false /*sysparService.isOpen("OPEN","25")*/;
			if (!isOpen) {//活动未开启
				return ;
			}
			
			String startTime = "2015-09-25 10:00:00";
			String endTime = "2015-10-23 12:00:00";
			
			//查询所有的投标用户
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT SUM(t.`investAmount`) AS sumInvestAmount,t.`investor`,t.`deadline` FROM t_invest t WHERE t.`borrowId` = ").append(bid_id).append(" AND t.`investTime`>='").append(startTime).append("' AND t.`investTime`<='").append(endTime).append("' GROUP BY t.`investor`");
			DataSet ds = MySQL.executeQuery(conn, sql.toString());
			ds.tables.get(0).rows.genRowsMap();
			List<Map<String, Object>> investUsers = ds.tables.get(0).rows.rowsMap;
			for (Map<String, Object> map : investUsers) {
				int deadline = Convert.strToInt(map.get("deadline").toString(), 0);
				//Id
				long userid = Convert.strToLong(map.get("investor").toString(), -1l);
				//此标总投资额
				double sumInvestAmount = Convert.strToDouble(map.get("sumInvestAmount").toString(), 0);
				
				//是否新用户注册
				boolean hasNewUser =userService.hasNewUser(startTime, endTime, userid);
				
				double readPackge = 0;
				double proportion = 0.001;
				
				if (deadline==1) {
					proportion=proportion*2;
				}else {
					proportion=proportion*5;
				}
				
				readPackge = sumInvestAmount*proportion;
				
				/*if (!hasNewUser) {//老用户
					readPackge = proportion*sumInvestAmount;
				}*/
				
				FundManagementService fundManagementService = (FundManagementService) SpringUtil.getBean("fundManagementService");
				Map<String, String> user = userService.queryUserById(userid);
				fundManagementService.addBackR(conn, userid, adminId, 7, readPackge, "参与活动获得奖励", new Date(), "奖励充值", ServletUtils.getRemortIp(), user.get("username"), "参与活动获得奖励");
				userService.updateSign(conn,userid);
				/*//是否第一次投资
				sql.setLength(0);
				boolean hasOneInvest = false;
				sql.append("SELECT COUNT(id) AS cou FROM t_award_score t WHERE t.`userId` = ").append(userid).append("  AND t.`createTime` >='").append(startTime).append("'");
				ds = MySQL.executeQuery(conn, sql.toString());
				Map<String, String> inv = BeanMapUtils.dataSetToMap(ds);
				if (inv!=null) {
					int count = Convert.strToInt(inv.get("cou"), 0);
					hasOneInvest = count<=0;
				}
				
				//抽奖次数奖励
				int number = 0;
				double readPackge = 0;
				double proportion = 0.003;
				if (hasOneInvest && hasNewUser) {//新用户并且首投
					if (sumInvestAmount>=3000 && deadline>=3) {//是否符合活动规则
						if (sumInvestAmount>=3000 && sumInvestAmount<=5000) {
							number = 1;
						}else if (sumInvestAmount>5000 && sumInvestAmount<=10000) {
							number = 1;
						}else if (sumInvestAmount>10000 && sumInvestAmount<=50000) {
							number = 2;
						}else if (sumInvestAmount>50000 && sumInvestAmount<=100000) {
							number = 3;
						}else if (sumInvestAmount>100000 && sumInvestAmount<=300000) {
							number = 4;
						}else if (sumInvestAmount>300000) {
							number = 5;
						}else {
							number = 0;
						}
					}else{//否则直接送一次抽奖
						number = 1;
					}
					proportion = 0.001*deadline;
					//0.3%的红包金额奖励
					readPackge = AmountUtil.mathRound(proportion*sumInvestAmount);
				}else {//不是新用户，或者不是首投
					//直接看金额是否
					if (sumInvestAmount>=3000 && deadline>=3) {//是否符合活动规则
						if (sumInvestAmount>=3000 && sumInvestAmount<=5000) {
							number = 1;
						}else if (sumInvestAmount>5000 && sumInvestAmount<=10000) {
							number = 1;
						}else if (sumInvestAmount>10000 && sumInvestAmount<=50000) {
							number = 2;
						}else if (sumInvestAmount>50000 && sumInvestAmount<=100000) {
							number = 3;
						}else if (sumInvestAmount>100000 && sumInvestAmount<=300000) {
							number = 4;
						}else if (sumInvestAmount>300000) {
							number = 5;
						}else {
							number = 0;
						}
						proportion = 0.001*deadline;
						//0.3%的红包金额奖励
						readPackge = AmountUtil.mathRound(proportion*sumInvestAmount);
					}
				}
				
				if (readPackge>0) {
					userDao.addPresrent(readPackge, userid, conn);
				}
				//抽奖次数奖励
				if (number>0) {
					userDao.addScore(conn, number,3,6,userid);
				}*/
			}
			conn.commit();
		} catch (Exception e) {
			log.error("积分添加奖励发放失败");
			log.error(e);
			e.printStackTrace();
			conn.rollback();
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

	public long reBackBorrowFistAudit(long idLong, Long id, String basePath,
			String msg, String auditOpinion) throws Exception {
		basePath = Utility.filteSqlInfusion(basePath);
		msg = Utility.filteSqlInfusion(msg);
		auditOpinion = Utility.filteSqlInfusion(auditOpinion);
		
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> map_ret = new HashMap<String, String>();
		Map<String, String> adminMap = new HashMap<String, String>();
		Map<String, String> borrowUserMap = new HashMap<String, String>();
		List<Map<String, Object>> investorList = new ArrayList<Map<String, Object>>();
		try {
			Procedures.p_borrow_cancel(conn, ds, outParameterValues, idLong,
					id, 6, auditOpinion, basePath, -1, "");
			map_ret.put("out_ret", outParameterValues.get(0) + "");
			map_ret.put("out_desc", outParameterValues.get(1) + "");
			returnId = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (returnId <= 0) {
				conn.rollback();
				return Convert.strToLong(map_ret.get("out_ret"), -1);
			}
			
			borrowUserMap = borrowManageDao.queryBorrowerById(conn, idLong);
			if(borrowUserMap != null){
				long userId = Convert.strToLong(borrowUserMap.get("publisher"), -1);
				userService.updateSign(conn, userId);//更换校验码
			}
			investorList = borrowManageDao.queryInvesterById(conn, idLong);
			if(investorList != null){
				for(Map<String, Object> map : investorList){
					long userId = Convert.strToLong(map.get("investor")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
					map = null;
				}
			}
			// 添加操作日志
			adminMap = adminDao.queryAdminById(conn, id);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
					adminMap.get("userName"), ""), IConstants.UPDATE, Convert
					.strToStr(adminMap.get("lastIP"), ""), 0, "管理员撤销借款", 2);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return Convert.strToLong(map_ret.get("out_ret"), 1);
	}

	/**
	 * @MethodName: addCirculationRepay
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-7-23 下午04:32:03
	 * @Return:
	 * @Descb: 添加流转标还款记录
	 * @Throws:
	 */
	public long addCirculationRepay(long repayId, double amountDouble, Long id,
			String remark) throws Exception {
		Connection conn = MySQL.getConnection();
		long returnId = -1;
		try {
			returnId = repaymentRecordDao.addRepayMentRecord(conn, repayId,
					amountDouble, id, remark);
			if (returnId <= 0) {
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
		}
		return returnId;
	}

	public Map<String, String> queryBorrowInfo(long idLong) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowInfo(conn, idLong);
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
	 * 查询债权借款人
	 * queryTransferUser<br/>
	 * @author hjh
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public Map<String, String> queryTransferUser(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryTransferUser(conn, borrowId);
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
	
	public Map<String, String> queryBorrowInfo2(long idLong) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowInfo(conn, idLong);
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
	 * 查看借款协议中的内容
	 * 
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowMany(long borrowId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = investDao.queryBorrowMany(conn, borrowId);
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
	 * 根据借款id 和投资id 查询
	 * 
	 * @param borrowId
	 * @param invest_id
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryInvestMomey(long borrowId,
			long invest_id) throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = investDao.queryInvestMomey(conn, invest_id, borrowId);
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
	 * 查询所有投资人信息
	 * 
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryUsername(long borrowId, long invest_id)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = investDao.queryUsername(conn, borrowId, invest_id);
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
     * 查询所有投资人信息（不打码的）
     * 
     * @param borrowId
     * @return
     * @throws Exception
     * @throws DataException
     */
    public List<Map<String, Object>> queryUsername1(long borrowId, long invest_id)
            throws Exception {
        Connection conn = MySQL.getConnection();
        List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
        try {
            map = investDao.queryUsername1(conn, borrowId, invest_id);
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
	 * 根据借款查询借款应还的金额
	 * 
	 * @param conn
	 * @param borrowId
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowSumMomeny(long borrowId,
			long invest_id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = investDao.queryBorrowSumMomeny(conn, borrowId, invest_id);
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

	// 借款管理模块中，查询各个报表的总额
	public Map<String, String> queryBorrowTotalFistAuditDetail()
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalFistAuditDetail(conn);
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

	public Map<String, String> queryBorrowTotalWait() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalWait(conn);
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

	public Map<String, String> queryBorrowTotalTenderDetail() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalTenderDetail(conn);
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

	public Map<String, String> queryBorrowFlowMarkDetail() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowFlowMarkDetail(conn);
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
	 * queryRelated  查询相关材料
	 * @auth hejiahua
	 * @param id
	 * @param pageBean 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-10-11 下午3:20:20
	 * @since  1.0.0
	 */
	public void queryRelated(long id,PageBean pageBean) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
            dataPage(conn, pageBean, "v_t_related_materials", "*", " order by  id desc", " and  borrowId="+id);
        }
        catch (DataException e) {
            e.printStackTrace();
        }finally{
            conn.close();
        }
	}
	
	/**
	 * 保存相关材料
	 * addRelated
	 * @auth hejiahua
	 * @param paramMap
	 * @return 
	 * long
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-10-12 下午7:47:49
	 * @since  1.0.0
	 */
	public long addRelated(Map<String, String> paramMap) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result = -1L;
	    try {
            Dao.Tables.t_related_materials related_materials = new Dao().new Tables().new t_related_materials();
            related_materials._name.setValue(paramMap.get("name"));
            related_materials.borrowId.setValue(paramMap.get("id"));
            related_materials.description.setValue(paramMap.get("description"));
            related_materials.type.setValue(paramMap.get("type"));
            related_materials.imgPath.setValue(paramMap.get("imgPath"));
            related_materials.isa.setValue(paramMap.get("isa"));
            result = related_materials.insert(conn);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            log.error(e);
        }finally{
            conn.close();
        }
	    return result;
	}
	
	/**
     * 更新相关材料
     * addRelated
     * @auth hejiahua
     * @param paramMap
     * @return 
     * long
     * @throws SQLException 
     * @exception 
     * @date:2014-10-12 下午7:47:49
     * @since  1.0.0
     */
    public long updateRelated(Map<String, String> paramMap) throws SQLException{
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            Dao.Tables.t_related_materials related_materials = new Dao().new Tables().new t_related_materials();
            related_materials._name.setValue(paramMap.get("name"));
            related_materials.borrowId.setValue(paramMap.get("borrowId"));
            related_materials.description.setValue(paramMap.get("description"));
            related_materials.type.setValue(paramMap.get("type"));
            related_materials.imgPath.setValue(paramMap.get("imgPath"));
            related_materials.isa.setValue(paramMap.get("isa"));
            result = related_materials.update(conn, " id ="+paramMap.get("id"));
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            log.error(e);
        }finally{
            conn.close();
        }
        return result;
    }
    
    /**
     * 删除相关材料
     * addRelated
     * @auth hejiahua
     * @param paramMap
     * @return 
     * long
     * @throws SQLException 
     * @exception 
     * @date:2014-10-12 下午7:47:49
     * @since  1.0.0
     */
    public long deleteRelated(String id) throws SQLException{
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            Dao.Tables.t_related_materials related_materials = new Dao().new Tables().new t_related_materials();
            result = related_materials.delete(conn, " id in ("+id+")");
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            log.error(e);
        }finally{
            conn.close();
        }
        return result;
    }
    
	
	/**
	 * 查询相关材料
	 * queryRelated
	 * @auth hejiahua
	 * @param id
	 * @return
	 * @throws SQLException 
	 * Map<String,String>
	 * @exception 
	 * @date:2014-10-12 下午8:23:26
	 * @since  1.0.0
	 */
	public Map<String, String> queryRelated(long id) throws SQLException{
	    Map<String, String> paramMap = null;
	    Connection conn = MySQL.getConnection();
	    try {
            Dao.Tables.t_related_materials related_materials = new Dao().new Tables().new t_related_materials();
            DataSet ds = related_materials.open(conn, "*", " id ="+id, "", -1, -1);
            paramMap = BeanMapUtils.dataSetToMap(ds);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.close();
        }
	    return paramMap;
	}
	
	
	public Map<String, String> queryBorrowTotalFullScaleDetail()
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryBorrowTotalFullScaleDetail(conn);
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
	 * 查询借款申请列表
	 * queryBorrowAskList
	 * @auth hejiahua
	 * @param pageBean 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-10-23 上午8:38:38
	 * @since  1.0.0
	 */
	public void queryBorrowAskList(PageBean pageBean,long id,String contact_phone,String contact_name,String createTimeStart, String createTimeEnd,String borrowWay,String borrowerType,String hasMortgage,String hasDistribution) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
	        StringBuffer condition = new StringBuffer();
	        if (id>0) {
                condition.append(" and id="+id+"");
            }
	        if (StringUtils.isNotBlank(contact_phone)) {
                condition.append(" and contact_phone like '%"+Utility.filteSqlInfusion(contact_phone)+"%'");
            }
	        if (StringUtils.isNotBlank(contact_name)) {
                condition.append(" and contact_name  like '%"+Utility.filteSqlInfusion(contact_name)+"%'");
            }
	        if (StringUtils.isNotBlank(createTimeStart)) {
                condition.append(" and createTime  >='"+Utility.filteSqlInfusion(createTimeStart)+"'");
            }
	        if (StringUtils.isNotBlank(createTimeEnd)) {
                condition.append(" and createTime  <='"+Utility.filteSqlInfusion(createTimeEnd)+"'");
            }
	        if (StringUtils.isNotBlank(borrowerType)) {
                condition.append(" and borrowerType='"+Utility.filteSqlInfusion(borrowerType)+"'");
            }
	        if (StringUtils.isNotBlank(borrowWay)) {
                condition.append(" and borrowWay1='"+Utility.filteSqlInfusion(borrowWay)+"'");
            }
	        
	        if (StringUtils.isNotBlank(hasMortgage)) {
                condition.append(" and hasMortgage="+Utility.filteSqlInfusion(hasMortgage)+"");
            }
	        
	        if (StringUtils.isNotBlank(hasDistribution)) {
                condition.append(" and hasDistribution="+Utility.filteSqlInfusion(hasDistribution)+"");
            }
	        
            dataPage(conn, pageBean, "v_t_borrow_new", "*", "order by id desc", condition.toString());
            
            condition = null;
            
        }
        catch (Exception e) {
           e.printStackTrace();
           log.error(e);
        }finally{
            conn.close();
        }
	}
	/*
	 * 分配
	 */
	public long fenPei(long id,long userid) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    long result = -1L;
	    try {
            Dao.Tables.t_borrow_new tBorrow_new = new Dao().new Tables().new t_borrow_new();
            tBorrow_new.distributionPerson.setValue(userid);
            tBorrow_new.distributionDate.setValue(DateUtil.dateToString(new Date()));
            tBorrow_new.hasDistribution.setValue(0);//已分配
            result =  tBorrow_new.update(conn, " id = "+id);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	
	/*
	 * 查询员工是否已经添加
	 */
	public long queryEmployeeBindUser(long userId) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    long result = -1L;
	    try {
             DataSet ds  = Database.executeQuery(conn, "select count(userId) as c from t_employee where userId = "+userId);
            if (BeanMapUtils.dataSetToMap(ds)!=null) {
                return Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("c"), -1l);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	
	
	/**
	 * 添加员工
	 * addEmployee
	 * @auth hejiahua
	 * @param paramMap
	 * @return
	 * @throws SQLException 
	 * long
	 * @exception 
	 * @date:2014-11-6 下午5:31:33
	 * @since  1.0.0
	 */
	public long addEmployee(Map<String, String> paramMap) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    long result = -1L;
	    try {
            Dao.Tables.t_employee tEmployee = new Dao().new Tables().new t_employee();
            tEmployee.department.setValue(paramMap.get("department"));
            tEmployee.entrytime.setValue(paramMap.get("entrytime"));
            tEmployee.job.setValue(paramMap.get("job"));
            tEmployee.userId.setValue(paramMap.get("userId"));
            result = tEmployee.insert(conn);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	
	public InvestDao getInvestDao() {
		return investDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}

	public BorrowManageDao getBorrowManageDao() {
		return borrowManageDao;
	}

	public void setBorrowManageDao(BorrowManageDao borrowManageDao) {
		this.borrowManageDao = borrowManageDao;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public RepamentDao getRepamentDao() {
		return repamentDao;
	}

	public void setRepamentDao(RepamentDao repamentDao) {
		this.repamentDao = repamentDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public void setRepaymentRecordDao(RepaymentRecordDao repaymentRecordDao) {
		this.repaymentRecordDao = repaymentRecordDao;
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public PlatformCostService getPlatformCostService() {
		return platformCostService;
	}

	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public RepaymentRecordDao getRepaymentRecordDao() {
		return repaymentRecordDao;
	}

	public NoticeTaskDao getNoticeTaskDao() {
		return noticeTaskDao;
	}

	public void setNoticeTaskDao(NoticeTaskDao noticeTaskDao) {
		this.noticeTaskDao = noticeTaskDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public Map<String, String> queryAuthProtocol(long borrowId, long investId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowManageDao.queryAuthProtocol(conn,borrowId,investId);
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
	 * 新增验证是否有权限查看协议
	 * queryAuthProtocol
	 * @auth hejiahua
	 * @param borrowId
	 * @param investId
	 * @param userId
	 * @return
	 * @throws Exception 
	 * Map<String,String>
	 * @exception 
	 * @date:2014-11-3 下午7:47:54
	 * @since  1.0.0
	 */
	public Map<String, String> queryAuthProtocol(long borrowId, long investId,long userId) throws Exception {
        Connection conn = MySQL.getConnection();
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (investId==-1) {//如果等于-1，表示查询所有的借款人投资信息，此用户为借款人
                String sql = "select publisher from t_borrow where id = "+borrowId;
                DataSet ds =  Database.executeQuery(conn, sql);
                Map< String,  String> person =BeanMapUtils.dataSetToMap(ds);
                if (person==null || person.get("publisher")==null) {
                    return null;
                }else {
                    long publisher = Convert.strToLong(person.get("publisher"), -1);
                    if (publisher==-1) {
                        return null;
                    }else {
                        if (publisher!=userId) {
                            return null;
                        }
                    }
                }
            }
            map = borrowManageDao.queryAuthProtocol(conn,borrowId,investId);
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
	 * 查询员工信息
	 * queryEmployee
	 * @auth hejiahua
	 * @param pageBean
	 * @param username
	 * @param realName
	 * @param department
	 * @param phone
	 * @param hasWork
	 * @param createTimeStart
	 * @param createTimeEnd 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-6 下午6:21:53
	 * @since  1.0.0
	 */
	public void queryEmployee(PageBean pageBean,String username,String realName,String department,String phone,String hasWork,String createTimeStart,String createTimeEnd) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    StringBuffer condition = new StringBuffer();
	    condition.append( " and 1= 1");
	    if (StringUtils.isNotBlank(username)) {
            username = Utility.filteSqlInfusion(username);
            condition.append(" and username  like '%"+username+"%'");
        }
	    if (StringUtils.isNotBlank(realName)) {
	        realName = Utility.filteSqlInfusion(realName);
            condition.append(" and realName like  '%"+realName+"%'");
        }
	    if (StringUtils.isNotBlank(phone)) {
	        phone = Utility.filteSqlInfusion(phone);
            condition.append(" and cellPhone like '%"+phone+"%'");
        }
	    if (StringUtils.isNotBlank(department)) {
	        department = Utility.filteSqlInfusion(department);
            condition.append(" and department = '"+department+"'");
        }
	    if (StringUtils.isNotBlank(hasWork)) {
	        hasWork = Utility.filteSqlInfusion(hasWork);
            condition.append(" and hasWork = "+hasWork+"");
        }
	    if (StringUtils.isNotBlank(createTimeStart)) {
	        createTimeStart = Utility.filteSqlInfusion(createTimeStart);
            condition.append(" and createTime >= '"+createTimeStart+"'");
        }
	    if (StringUtils.isNotBlank(createTimeEnd)) {
	        createTimeEnd = Utility.filteSqlInfusion(createTimeEnd);
            condition.append(" and createTime <= '"+createTimeEnd+"'");
        }
	    try {
            dataPage(conn, pageBean, "v_t_employee", " *", " order by id desc ", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.close();
        }
	}
	
	/**
	 * 更新员工信息
	 * updateEmployee
	 * @auth hejiahua
	 * @param id
	 * @param department
	 * @param job
	 * @param entryTime
	 * @param hasWork
	 * @param leaveTime
	 * @return
	 * @throws SQLException 
	 * long
	 * @exception 
	 * @date:2014-11-7 下午5:26:27
	 * @since  1.0.0
	 */
	public long updateEmployee(long id,String department,String job,String entryTime,String hasWork,String leavetime) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    long result = -1l;
	    try {
            Dao.Tables.t_employee tEmployee = new Dao().new Tables().new t_employee();
            tEmployee.department.setValue(department);
            tEmployee.job.setValue(job);
            tEmployee.entrytime.setValue(entryTime);
            
            if ("1".equals(hasWork)) {//不离职
                tEmployee.leavetime.setValue("");
                tEmployee.hasWork.setValue(1);
            }else {
                tEmployee.leavetime.setValue(leavetime);
            }
            
            result = tEmployee.update(conn, " id ="+id);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
           e.printStackTrace();
           log.error(e);
           conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	/*
	 * id查询员工信息
	 */
	public Map<String, String> queryEmployeeById(long id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
            DataSet ds = Database.executeQuery(conn, "select * from v_t_employee where id = "+id);
            return BeanMapUtils.dataSetToMap(ds);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	    return null;
	}
	
	/**
	 * 离职
	 * leaveWork
	 * @auth hejiahua
	 * @param id
	 * @return 
	 * long
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-7 上午9:26:14
	 * @since  1.0.0
	 */
	public long leaveWork(long id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    long result = -1l;
	    try {
            Dao.Tables.t_employee tEmployee = new Dao().new Tables().new t_employee();
            tEmployee.hasWork.setValue(0);//离职
            tEmployee.leavetime.setValue(DateUtil.dateToString(new Date()));//离职时间
            result = tEmployee.update(conn, "id="+id );
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	/**
	 * @MethodName: queryBorrowAllDetailById
	 * @Param: BorrowManageService
	 * @Author: gang.lv
	 * @Date: 2013-3-10 下午10:24:35
	 * @Return:
	 * @Descb: 后台借款的借款详情
	 * @Throws:
	 */
	public int copyBorrowImage(long fromId,long toId)
			throws Exception {
		Connection conn = MySQL.getConnection();
//		Map<String, String> map = new HashMap<String, String>();
		try {
			borrowManageDao.copyBorrowImage(conn,fromId,toId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
		return 1;
	}
	
}
