package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RechargeDao;
import com.sp2p.dao.RechargeDetailDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.FIManageDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.pay.llpay.client.vo.PayDataBean;
import com.sp2p.pay.llpay.client.vo.UserCardInfo;
import com.sp2p.util.DateUtil;

public class RechargeService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);

	private RechargeDao rechargeDao;
	private FIManageDao fiManageDao;
	private AccountUsersDao accountUsersDao;
	private UserDao userDao;
	private FundRecordDao fundRecordDao;
	private OperationLogDao operationLogDao;
	private RechargeDetailDao rechargeDetailDao;
	private FinanceDao financeDao;
	
    private SelectedService selectedService;

	public SelectedService getSelectedService() {
	return selectedService;
	}
	
	public void setSelectedService(SelectedService selectedService) {
	this.selectedService = selectedService;
}

	public RechargeDetailDao getRechargeDetailDao() {
		return rechargeDetailDao;
	}

	public void setRechargeDetailDao(RechargeDetailDao rechargeDetailDao) {
		this.rechargeDetailDao = rechargeDetailDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public List<Map<String, Object>> withdrawLoad(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return rechargeDao.withdrawLoad(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 重载查找账户信息
	 * @param userId
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> withdrawLoad(long userId,Integer userType) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			if(userType==2){
				return rechargeDao.withdrawLoadOrg(conn, userId, -1, -1);
			}else{
				return rechargeDao.withdrawLoad(conn, userId, -1, -1);
			}
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public void queryWithdrawList(PageBean pageBean, long userId,
			double endSum, String startTime) throws Exception {
		startTime = Utility.filteSqlInfusion(startTime);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		condition.append(" and userId=");
		condition.append(userId);
		if (endSum > 0) {
			condition.append(" and `sum`<=");
			condition.append(endSum);
		}
		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" and applyTime>='");
			condition.append(startTime);
			condition.append("'");
		}
		try {
			dataPage(conn, pageBean, "(SELECT a.*,b.`bankName`,RIGHT(a.acount, 4) acount4 FROM t_withdraw a,t_bankcard b WHERE a.`bankId`=b.id )a", "*",
					" order by applyTime desc ", condition.toString());// return
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 后台线下充值审核
	 * 
	 * @param pageBean
	 * @param userId
	 * @throws Exception
	 * @throws DataException
	 */
	public void queryRechareDetil(PageBean pageBean, String userName,
			String realName,String orgName,String userType) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		orgName = Utility.filteSqlInfusion(orgName);
		userType = Utility.filteSqlInfusion(userType);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" and rechargeType = 4 ");
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username like  '%"
					+ StringEscapeUtils.escapeSql(userName) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName like  '%"
					+ StringEscapeUtils.escapeSql(realName) + "%'");
		}
		if (StringUtils.isNotBlank(orgName)) {
			condition.append(" and orgName like  '%"
					+ StringEscapeUtils.escapeSql(orgName) + "%'");
		}
		if (StringUtils.isNotBlank(userType) && !"-1".equals(userType)) {
			condition.append(" and userType = '"
					+ StringEscapeUtils.escapeSql(userType) + "'");
		}
		try {
			dataPage(conn, pageBean, "v_t_recharge_detail", "*",
					" order by rechargeTime desc ", condition.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryupdateRechargeDetailById(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return rechargeDetailDao
					.queryupdateRechargeDetailById(conn, userId);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	

	public void queryFundrecordList(PageBean pageBean, long userId,
			String startTime, String endTime, String momeyType, String cycle)
			throws Exception, DataException {
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		momeyType = Utility.filteSqlInfusion(momeyType);
		
		Connection conn = MySQL.getConnection();
		StringBuffer conditon = new StringBuffer();
		conditon.append(" and userId=" + userId);
		if (StringUtils.isNotBlank(endTime)) {
			conditon.append(" and recordTime <='"
					+ StringEscapeUtils.escapeSql(endTime) + " 23:59:59'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			conditon.append(" and recordTime >= '"
					+ StringEscapeUtils.escapeSql(startTime) + " 00:00:00'");
		}
		
		if (StringUtils.isNotBlank(cycle)) {
			conditon.append(" and recordTime >= '" + StringEscapeUtils.escapeSql(cycle) + "'");
		}
		
		if (StringUtils.isNotBlank(momeyType)) {
			if(momeyType.equals("--请选择--")){
				conditon.append("");
			}else{
				conditon.append(" and fundMode like '%"
						+ StringEscapeUtils.escapeSql(momeyType) + "%'");
			}
		}
		try {
			dataPage(conn, pageBean, " v_t_fundrecord_index ", " * ",
					" order by id desc, recordTime desc ", conditon.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void queryFundrecordList3days(PageBean pageBean, long userId,
			String startTime, String endTime, String rechargeType)
			throws Exception, DataException {
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		rechargeType = Utility.filteSqlInfusion(rechargeType);
		
		Connection conn = MySQL.getConnection();
		StringBuffer conditon = new StringBuffer();
		conditon.append(" and userId=" + userId);
		if (StringUtils.isNotBlank(endTime)) {
			conditon.append(" and rechargeTime <='"
					+ StringEscapeUtils.escapeSql(endTime) + " 23:59:59'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			conditon.append(" and rechargeTime >= '"
					+ StringEscapeUtils.escapeSql(startTime) + " 00:00:00'");
		}
		if (StringUtils.isNotBlank(rechargeType)) {
			if(rechargeType.equals("")){
				conditon.append("");
			}else{
				conditon.append(" and rechargeType in (5,7,8,9) ");//9 移动充值
			}
		}
		try {
			dataPage(conn, pageBean, " v_t_user_rechargedetails_list ", " id,userId,CASE  WHEN result = 0 THEN '失败' WHEN result = 1 THEN '成功' END result,CASE  WHEN rechargeType = 5 THEN '手工充值' WHEN rechargeType = 9 THEN '移动充值' WHEN rechargeType = 7 THEN '奖励充值' ELSE '在线充值' END rechargeType,rechargeMoney,rechargeTime ",
					" order by id desc", conditon.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	

	public Map<String, String> queryFundrecordSum(long userId,
			String startTime, String endTime, String momeyType,String cycle)
			throws Exception, DataException {
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		momeyType = Utility.filteSqlInfusion(momeyType);
		
		Connection conn = MySQL.getConnection();
		StringBuffer conditon = new StringBuffer();
		conditon
				.append("SELECT round(ifnull(sum(income),0),2) as SumincomeSum,round(ifnull(sum(usableSum),0),2) as  SumusableSum, round(ifnull(sum(spending),0),2) as SumspendingSum  FROM v_t_fundrecord_index where 1=1 ");
		conditon.append(" and userId=" + userId);
		if (StringUtils.isNotBlank(endTime)) {
			conditon.append(" and recordTime<='"
					+ StringEscapeUtils.escapeSql(endTime) + "'");
		}
		if (StringUtils.isNotBlank(startTime)) {
			conditon.append(" and recordTime >= '"
					+ StringEscapeUtils.escapeSql(startTime) + "'");
		}
		if (StringUtils.isNotBlank(cycle)) {
			conditon.append(" and recordTime >= '" + StringEscapeUtils.escapeSql(cycle) + "'");
		}
		if (StringUtils.isNotBlank(momeyType)) {
			if(momeyType.equals("--请选择--")){
				conditon.append("");
			}else{
				conditon.append(" and fundMode like '%"
						+ StringEscapeUtils.escapeSql(momeyType) + "%'");
			}
		}
		try {
			DataSet dataSet = MySQL.executeQuery(conn, conditon.toString());
			return BeanMapUtils.dataSetToMap(dataSet);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询最新资金流信息
	 * 
	 * @param pageBean
	 * @param userId
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> queryFundrecordSum(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return rechargeDao.queryFundrecordSum(conn, userId, -1, -1);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
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
	 * @throws DataException
	 */
	public Long updateFundrecord(long userId, double money, int type)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = rechargeDao.updateFundrecord(conn, userId, money, type);

			Map<String, String> dueinMap = fiManageDao.queryDueInSum(conn,
					userId);

			// 往资金记录表里面插入申请提现冻结资金记录 2013-04-27
			long result2 = fundRecordDao.addFundRecord(conn, userId, "取消提现",
					money, Convert.strToDouble(dueinMap.get("usableSum"), 0),
					Convert.strToDouble(dueinMap.get("freezeSum"), 0d), Convert
							.strToDouble(dueinMap.get("forPI"), 0d), -1,
					"取消提现解冻", money, 0.0, -1, -1, 51, 0.0);
			if (result <= 0 || result2 <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 删除提现信息
	 * 
	 * @param userId
	 * @param wid
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> deleteWithdraw(long userId, long wid)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long ret = -1;
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_amount_withdraw_cancel(conn, ds, outParameterValues,
					userId, wid, "", "", -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret < 0) {
				conn.rollback();
			} else {
				// 得到用户信息
				userMap = userDao.queryUserById(conn, userId);
				// 增加系统操作日志
				operationLogDao.addOperationLog(conn, "t_withdraw", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.DELETE, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "取消提现", 1);
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
	 * 添加充值记录信息
	 * 
	 * @param userId
	 * @param money
	 * @param rechargeType
	 * @param bankName
	 * @param poundage
	 * @param account
	 * @param rechargeTime
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public long addRechargeInfo(Long userId, float money, int rechargeType,
			String bankName, float poundage, float account, int status)
			throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = rechargeDao.addRechargeInfo(conn, userId, money,
					rechargeType, bankName, poundage, account, status);
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

	public Map<String, String> addRecharge(Map<String, String> paramMap,
			int repaytype) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = addPrrechar(Convert.strToLong(paramMap.get("userId"), -1L),
					repaytype, paramMap.get("bankCode"), Convert.strToDouble(paramMap
							.get("rechargeMoney"), -1), 0);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return map;
	}
	
	/**
	 * 更新部分信息
	 * @Title: updateRecharge
	 * @Description:
	 * @param paramMap
	 * @param id
	 * @throws Exception
	 * void
	 * @author he
	 * @date 2015-2-3
	 */
	public void updateRechargeDetail(Map<String, String> paramMap,
			int id) throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			rechargeDao.updateRechargeDetail(conn, id, paramMap);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	public Map<String, String> getRechargeDetail(long id) throws Exception,
			DataException {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = rechargeDao.getRechargeDetail(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return result;
	}

	public Map<String, String> queryRechargeInTime(long userId, String startTime)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = rechargeDao.queryRechargeInTime(conn, userId, startTime);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	public Map<String, String> queryTradeInTime(long userId, String startTime)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		try {
			result = rechargeDao.queryTradeInTime(conn, userId, startTime);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}

	private String getRechargeNumber() {
		String rechargeNumber = DateUtil.YYYYMMDDMMHHSSSSS.format(new Date());
		Random rand = new Random();
		long num = rand.nextInt(1000);
		while (num < 100 && num >= 1000) {
			num = rand.nextInt(1000);
		}
		return rechargeNumber + num;
	}

	public void queryRechargeInfo(PageBean pageBean, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		String condition = " and userId=" + userId;
		try {
			dataPage(conn, pageBean, "t_recharge_detail", "*",
					"  order by id desc ", condition);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public int userPay(Map<String, Object> paramMap) throws Exception {
		int returnId = -1;

		try {
			log.info("1-");
			String[] extraCommonParam = (String[]) paramMap
					.get("extraCommonParam");
			BigDecimal total_fee = (BigDecimal) paramMap.get("total_fee");
			String paynumber = (String) paramMap.get("paynumber");
			String paybank = (String) paramMap.get("paybank");
			String notify_timeStr = (String) paramMap.get("notify_time");
			String buyer_email = (String) paramMap.get("buyer_email");
			Long orderId = Convert.strToLong(extraCommonParam[0], -1);// 获得订单编号
			if (orderId < 0) {
				return -2;// 订单编号错误
			}
			Long userId = Convert.strToLong(extraCommonParam[1], -1);// 获得用户编号
			if (userId < 0) {
				return -3;// 用户编号错误
			}
			log.info("2-");

			Date notify_time = DateUtil.strToDate(notify_timeStr);
			log.info("3-");

			returnId = userPayIn(orderId, userId, total_fee, paynumber,
					paybank, buyer_email, notify_time);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return returnId;// 成功!
	}
	
	/**
	 * 移动充值
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public synchronized int userPay(PayDataBean bean) throws Exception {
		try {
			log.info("1-");
			String[] extraCommonParam = bean.getNo_order().split("_");
			BigDecimal total_fee = new BigDecimal(bean.getMoney_order());
			String paynumber = extraCommonParam[1];
			Long orderId = Convert.strToLong(extraCommonParam[1], -1);// 获得订单编号
			if (orderId < 0) {
				return -2;// 订单编号错误
			}
			Long userId = Convert.strToLong(extraCommonParam[2], -1);// 获得用户编号
			if (userId < 0) {
				return -3;// 用户编号错误
			}
			log.info("2-");

			Connection conn = MySQL.getConnection();
			int ret = -1;
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			try {
				Procedures.p_useraddmoney(conn, ds, outParameterValues, userId,
						total_fee.doubleValue(), paynumber, "移动充值", -1, "");
				ret = Convert.strToInt(outParameterValues.get(0) + "", -1);
				if (ret < 0) {
					conn.rollback();
				}else {//充值流水
					rechargeDao.addRechargeNumber(conn, bean);
					log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~recharge~~~~~~~~~~~~~success");
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

			return ret;

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}
	
	
	private int userPayIn(Long orderId, long userId, BigDecimal total_fee,
			String paynumber, String paybank, String buyer_email,
			Date notify_time) throws Exception {
		Connection conn = MySQL.getConnection();
		int ret = -1;
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_useraddmoney(conn, ds, outParameterValues, userId,
					total_fee.doubleValue(), paynumber, "网上充值", -1, "");
			ret = Convert.strToInt(outParameterValues.get(0) + "", -1);
			if (ret < 0) {
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

		return ret;
	}

	public Map<String, String> queryLastRecharge(Long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return rechargeDao.queryLastRecharge(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryFund(Long userId) throws Exception,
			DataException {
		Connection conn = Database.getConnection();
		try {
			return rechargeDao.queryFund(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	public long addWithdraw(long userId, double money, String username,
			String cellPhone, String cardNo, long bankId, String ipAddress,
			double sum) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		// 计算是否提现金额有在15天内没有投标
		String startTime = getNeedTime(new Date());// 获得该时间的前15天的日期
		if (startTime == null) {
			return result;
		}
		double addPoundage = 0.0;
		if (money > IConstants.WITHDRAW_MAX) {
			// 首先超出的部分进行手续费的计算，然后未超出的部分看是否在15天内提取并且没有进行投标的
			double overMoney = money - IConstants.WITHDRAW_MAX;
			addPoundage = Float.parseFloat(String.format("%.4f", overMoney
					* IConstants.WITHDRAW_POUNDAGE));
		}

		try {

			// 获得十五天内用户成功充值的总额
			Map<String, String> rechargeMap = rechargeDao.queryRechargeInTime(
					conn, userId, startTime);
			double rechargeIn15 = 0;
			if (rechargeMap != null) {
				rechargeIn15 = Convert.strToDouble(rechargeMap
						.get("rechargeMoney"), 0);
			}

			Map<String, String> map = userDao.queryUserById(conn, userId);
			double usableSum = 0;// 用户的可用余额
			if (map != null) {
				usableSum = Convert.strToDouble(map.get("usableSum"), 0);
			}

			// 15天以外的金额
			double rechargeOut15 = usableSum - rechargeIn15;
			if (rechargeOut15 < 0) {// 客户提取了一部分钱，使得可用余额小于充值的余额，所以将值置为0
				rechargeOut15 = 0;
			}

			if (money <= rechargeOut15) {// 15以后提取金额，直接提取，不扣除手续费
				result = rechargeDao.addWithdraw(conn, userId, username,
						cellPhone, cardNo, sum, 0
								* IConstants.WITHDRAW_POUNDAGE + addPoundage,
						IConstants.WITHDRAW_CHECK, bankId, ipAddress);
				// 添加操作日志
				operationLogDao.addOperationLog(conn, "t_withdraw", Convert
						.strToStr(map.get("username"), ""), IConstants.INSERT,
						Convert.strToStr(map.get("lastIP"), ""), sum,
						"申请提现,冻结金额", 1);
			} else {
				// 查看15天以内成功获得的还款
				Map<String, String> tradeMap = rechargeDao.queryTradeInTime(
						conn, userId, startTime);
				double tradeIn15 = 0;// 0;
				if (tradeMap != null) {
					tradeIn15 = Convert.strToDouble(tradeMap.get("hasPI"), 0);
				}

				// 提取金额不用收手续费
				if (money <= (rechargeOut15 + tradeIn15)) {
					result = rechargeDao.addWithdraw(conn, userId, username,
							cellPhone, cardNo, sum, 0
									* IConstants.WITHDRAW_POUNDAGE
									+ addPoundage, IConstants.WITHDRAW_CHECK,
							bankId, ipAddress);
					// 添加操作日志
					operationLogDao.addOperationLog(conn, "t_withdraw", Convert
							.strToStr(map.get("username"), ""),
							IConstants.INSERT, Convert.strToStr(map
									.get("lastIP"), ""), sum, "申请提现,冻结金额", 1);
				} else {
					double need_poundageMoney = money - rechargeOut15
							- tradeIn15;
					// 需要支付的手续费
					float poundage = Float.parseFloat(String.format("%.4f",
							need_poundageMoney * IConstants.WITHDRAW_POUNDAGE));
					result = rechargeDao.addWithdraw(conn, userId, username,
							cellPhone, cardNo, sum, poundage + addPoundage,
							IConstants.WITHDRAW_CHECK, bankId, ipAddress);
					// 添加操作日志
					operationLogDao.addOperationLog(conn, "t_withdraw", Convert
							.strToStr(map.get("username"), ""),
							IConstants.INSERT, Convert.strToStr(map
									.get("lastIP"), ""), sum, "申请提现,冻结金额", 1);
				}

				if (result > 0) {
					// 插入提现记录之后，则用户表里面的可用余额减少，冻结余额增加

					rechargeDao.updateFundrecord(conn, userId, sum, 0);
					Map<String, String> dueinMap = fiManageDao.queryDueInSum(
							conn, userId);

					// 往资金记录表里面插入申请提现冻结资金记录 2013-04-27
					fundRecordDao.addFundRecord(conn, userId,
							IFundConstants.FREEZE_WITHDRAW, sum, Convert
									.strToDouble(dueinMap.get("usableSum"), 0),
							Convert.strToDouble(dueinMap.get("freezeSum"), 0d),
							Convert.strToDouble(dueinMap.get("forPI"), 0d), -1,
							"", 0.0, sum, -1, -1, 553, 0.0);
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

	public static String getNeedTime(Date currDate) {
		try {
			String currDateStr = DateUtil.YYYY_MM_DD.format(currDate);
			currDate = DateUtil.YYYY_MM_DD.parse(currDateStr);
			long currTime = currDate.parse(currDate.toString());
			long delTime = IConstants.WITHDRAW_TIME * 24 * 60 * 60 * 1000;
			long needTime = currTime - delTime;
			Date needDate = new Date();
			needDate.setTime(needTime);
			String needDateStr = DateUtil.YYYY_MM_DD.format(needDate);
			return needDateStr;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询资金记录（修改晓风软件字体在视图中修改）
	 * 
	 * @param pageBean
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 * @throws DataException
	 */
	public void queryFundrecordsList(PageBean pageBean, long userId,
			String startTime, String endTime) throws Exception, DataException {
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();

		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" and recordTime>='");
			condition.append(startTime);
			condition.append("' ");
		}

		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and recordTime<='");
			condition.append(endTime);
			condition.append("' ");
		}

		condition.append(" and userId=");
		condition.append(userId);
		try {
			dataPage(conn, pageBean, "v_t_fundrecord_index", "income,spending,id,userId,fundMode,handleSum,usableSum,freezeSum,dueinSum,sum,recordTime,dueoutSum",
					" order by id desc, recordTime desc ", condition.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 查询资金记录（修改晓风软件字体在视图中修改）
	 * 
	 * @param pageBean
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 * @throws DataException
	 */
	public void queryFundrecordsListApp(PageBean pageBean, long userId,
			String startTime, String endTime) throws Exception, DataException {
		startTime = Utility.filteSqlInfusion(startTime);
		endTime = Utility.filteSqlInfusion(endTime);
		
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();

		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" and recordTime>='");
			condition.append(startTime);
			condition.append("' ");
		}

		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and recordTime<='");
			condition.append(endTime);
			condition.append("' ");
		}

		condition.append(" and userId=");
		condition.append(userId);
		try {
			dataPage(conn, pageBean, "v_t_fundrecord_index", "income as income_fund,spending as pay_fund,fundMode as type,usableSum as operation_available_balance,recordTime as operation_time",
					" order by id desc, recordTime desc ", condition.toString());
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 添加充值明细 (存储过程)
	 * 
	 * @param userid
	 * @param rechargeType
	 * @param bankName
	 * @param rechargeMoney
	 * @param cost
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> addPrrechar(long userid, int rechargeType,
			String bankName, double rechargeMoney, double cost)
			throws Exception {
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_getnewpaynumber(conn, ds, outParameterValues, userid,
					rechargeType, bankName, rechargeMoney, cost, -1, "");
			System.out.println(outParameterValues);
			map.put("result", outParameterValues.get(0) + "");
			map.put("description", outParameterValues.get(1) + "");
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
	 * 充值
	 * 
	 * @param userid
	 * @param rechargeType
	 * @param bankName
	 * @param rechargeMoney
	 * @param cost
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Map<String, String> addUseraddmoney(long userid, double momeny,
			String in_paynumber, String in_remarks) throws Exception,
			DataException {
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_useraddmoney(conn, ds, outParameterValues, userid,
					momeny, in_paynumber, in_remarks, -1, "");
			map.put("result", outParameterValues.get(0) + "");
			map.put("description", outParameterValues.get(1) + "");
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
	 * 查询所有资金流水中所有类型
	 * 
	 * @return
	 * @throws DataException
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryTypeFund() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			mapList = rechargeDao.queryTypeFund(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return mapList;
	}

	public synchronized Map<String, String> withdrawApply(long userId, double moneyD,
			String dealpwd, long bankId, String type, String ipAddress)
			throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		String date = sf.format(new Date());
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		Map<String, String> map = new HashMap<String, String>();
		List<Object> outParameterValues = new ArrayList<Object>();
		try {
			Procedures.p_amount_withdraw(conn, ds, outParameterValues, userId,
					dealpwd, new BigDecimal(moneyD), bankId, type, ipAddress,
					date, -1, "");
			map.put("ret", outParameterValues.get(0) + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			ds = null;
			sf = null;
			date = null;
			outParameterValues = null;
		}

		return map;
	}

	/**
	 * 线下充值
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public long addRechargeoutline(Map<String, String> paramMap)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			Map<String, String> rechargeDetailMap = new HashMap<String, String>();
			rechargeDetailMap.put("rechargeMoney", paramMap
					.get("rechargeMoney"));
			// rechargeDetailMap.put("rechargeId", 0+"");
			rechargeDetailMap.put("result", paramMap.get("result"));
			rechargeDetailMap.put("rechargeType", paramMap.get("rechargeType"));
			rechargeDetailMap.put("bankName", paramMap.get("bankName"));
			rechargeDetailMap.put("rechargeNumber", paramMap
					.get("rechargeNumber"));
			rechargeDetailMap.put("userId", paramMap.get("userId"));
			rechargeDetailMap.put("rechargeTime", paramMap.get("rechargeTime"));
			rechargeDetailMap.put("remark", paramMap.get("remark"));
			// rechargeDetailMap.put("ipAddress", paramMap.get("ipAddress"));
			result = rechargeDao.addRechargeDetail(conn, rechargeDetailMap);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
    public long updateRechargeDetailStatusById(long id, long userId,
            long resul, double rechargeMoney, String award) throws Exception {
        Connection conn = null;
        Map<String, String> updateMap = new HashMap<String, String>();
        long result = -1;
        try {
            conn = MySQL.getConnection();
            updateMap.put("award", award);
            updateMap.put("result", resul + "");
            updateMap.put("rechargeMoney", rechargeMoney + "");
            // 查看充值明细表中的状态
            Map<String, String> rechargeStatusMap = rechargeDetailDao
                    .queryupdateRechargeDetailById(conn, id);
            String ststus = rechargeStatusMap.get("result");
            if (!"3".equals(ststus)) {
                return -1l;
            }
            // 更新充值明细表中的状态
            result = rechargeDetailDao.updateRechargeDetail_(conn, id,
                    updateMap);
            if (resul == 1 && result > 0) {
                result = userDao.addUserUsableAmount(conn, rechargeMoney,
                        userId);
                // 查询投资后的账户金额
                Map<String, String> userAmountMap = financeDao
                        .queryUserAmountAfterHander(conn, userId);
                if (userAmountMap == null) {
                    userAmountMap = new HashMap<String, String>();
                }
                double usableSum = Convert.strToDouble(userAmountMap
                        .get("usableSum")
                        + "", 0);
                double freezeSum = Convert.strToDouble(userAmountMap
                        .get("freezeSum")
                        + "", 0);
                double forPI = Convert.strToDouble(userAmountMap.get("forPI")
                        + "", 0);
                double oninvest = Convert.strToDouble(userAmountMap
                        .get("oninvest")
                        + "", 0);
                // 添加资金流动记录
                result = fundRecordDao.addFundRecord(conn, userId, "线下充值成功",
                        rechargeMoney, usableSum, freezeSum, forPI, -1,
                        "线下充值成功", rechargeMoney, 0.0, -1, -1, 1, 0.0);

                // 增加线下充值奖励
                if (!"".equals(award)) {
                    double awarda = Convert.strToDouble(award + "", 0);
                    if (awarda != 0) {
                        double awarddouble = rechargeMoney * awarda * 0.01;
                        result = userDao.addUserUsableAmount(conn, awarddouble,
                                userId);
                        // 查询投资后的账户金额
                        userAmountMap = financeDao.queryUserAmountAfterHander(
                                conn, userId);
                        if (userAmountMap == null) {
                            userAmountMap = new HashMap<String, String>();
                        }
                        usableSum = Convert.strToDouble(userAmountMap
                                .get("usableSum")
                                + "", 0);
                        freezeSum = Convert.strToDouble(userAmountMap
                                .get("freezeSum")
                                + "", 0);
                        forPI = Convert.strToDouble(userAmountMap.get("forPI")
                                + "", 0);
                        oninvest = Convert.strToDouble(userAmountMap
                                .get("oninvest")
                                + "", 0);
                        // 添加资金流动记录
                        result = fundRecordDao.addFundRecord(conn, userId,
                                "线下充值奖励", awarddouble, usableSum, freezeSum,
                                forPI, -1, "线下充值奖励", awarddouble, 0.0, -1, -1,
                                1, 0.0);
                    }
                }
                
                Map<String, String> userMap = userDao.queryUsernameById(conn,
                        userId);

                if (userMap != null) {
                    // 发送通知，通过通知模板
                    Map<String, Object> informTemplateMap = (Map<String, Object>) ContextLoader
                            .getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);

                    Map<String, String> noticeMap = new HashMap<String, String>();
                    // 消息模版
                    // 站内信
                    String informTemplate = informTemplateMap.get(
                            IInformTemplateConstants.RECHARGE_SUCCESS)
                            .toString();
                    informTemplate = informTemplate.replace("date", DateUtil
                            .dateToString((new Date())));
                    informTemplate = informTemplate.replace("money",
                            new BigDecimal(rechargeMoney) + "");
                    noticeMap.put("mail", informTemplate);

                    // 邮件
                    String e_informTemplate = informTemplateMap.get(
                            IInformTemplateConstants.E_RECHARGE_SUCCESS)
                            .toString();

                    e_informTemplate = e_informTemplate.replace("date",
                            DateUtil.dateToString((new Date())));
                    e_informTemplate = e_informTemplate.replace("money",
                            new BigDecimal(rechargeMoney) + "");
                    noticeMap.put("email", e_informTemplate);

                    // 短信
                    String s_informTemplate = informTemplateMap.get(
                            IInformTemplateConstants.S_RECHARGE_SUCCESS)
                            .toString();

                    s_informTemplate = s_informTemplate.replace("userName",
                            userMap.get("username"));
                    s_informTemplate = s_informTemplate.replace("date",
                            DateUtil.dateToString((new Date())));
                    s_informTemplate = s_informTemplate.replace("money",
                            new BigDecimal(rechargeMoney) + "");
                    noticeMap.put("note", e_informTemplate);

                    selectedService.sendNoticeMSG(conn, userId, "充值成功",
                            noticeMap, IConstants.NOTICE_MODE_5);
                }

            }
            if (result > 0) {
                
                conn.commit();
            } else {
                conn.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();

            throw e;
        } finally {
            conn.close();
        }
        return result;

    }

	
	
	public void queryWithdrawListApp(PageBean pageBean, long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		condition.append(" and userId =");
		condition.append(userId);
		try {
			dataPage(conn, pageBean, "(SELECT t.id as withdraw_id,t.`status` AS withdraw_status,date_format(t.`applyTime`,'%Y-%c-%d %H:%i:%s') AS application_date,t.`sum` AS withdraw_amount,t.`poundage` AS withdraw_poundage,b.`bankName` AS withdraw_bank,RIGHT(t.acount, 4) as last_four_no FROM t_withdraw t,t_bankcard b WHERE t.`bankId`=b.id and t.userId="+userId+" ) a", "*",
					" order by application_date desc ", "");// return
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 自动计算手续费
	 * withdrawCount
	 * @auth hejiahua
	 * @return 
	 * Map<String,String>
	 * @throws SQLException 
	 * @exception 
	 * @date:2015-1-22 下午4:24:01
	 * @since  1.0.0
	 */
	public Map<String, String> withdrawCount(long userId,double money) throws SQLException{
        Connection conn = MySQL.getConnection();
        DataSet ds = new DataSet();
        Map<String, String> map = new HashMap<String, String>();
        List<Object> outParameterValues = new ArrayList<Object>();
        String date = DateUtil.dateToString(new Date());
        try {
            Procedures.p_amount_withdraw_count(conn, ds, outParameterValues, userId,new BigDecimal(money),date,-1,"");
            map.put("ret", outParameterValues.get(0) + "");
            map.put("ret_desc", outParameterValues.get(1) + "");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            conn.close();
            conn = null;
            ds = null;
            outParameterValues = null;
        }

        return map;
    }
	
	
	/**
	 * @Title: queryCardByProtocol
	 * @Description:通过卡协议查询用户信息
	 * @param no_agree
	 * @return
	 * long
	 * @author he
	 * @throws SQLException 
	 * @date 2015-2-2
	 */
	public long queryCardByProtocol(String no_agree,String user_id) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = rechargeDao.queryCardByProtocol(conn, no_agree, user_id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally{
			conn.close();
		}
		
		return result;
		
	}
	
	public List<Map<String, String>> queryCardByCardNo(String cardNo,String user_id) throws SQLException{
		Connection conn = MySQL.getConnection();
		List<Map<String, String>> cardlist =null;
		try {
			cardlist = rechargeDao.queryCardByCardNo(conn, cardNo, user_id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally{
			conn.close();
		}
		return cardlist;
	}
	
	/**
	 * @Title: addCardProtocol
	 * @Description:添加用户卡信息
	 * @param info
	 * @return
	 * long
	 * @author he
	 * @throws SQLException 
	 * @date 2015-2-2
	 */
	public void addCardProtocol(UserCardInfo info,String user_id) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			long result = rechargeDao.addCardProtocol(conn, info, user_id);
			if (result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}finally{
			conn.close();
		}
	}
	
	
	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	public RechargeDao getRechargeDao() {
		return rechargeDao;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public FIManageDao getFiManageDao() {
		return fiManageDao;
	}

	public void setFiManageDao(FIManageDao fiManageDao) {
		this.fiManageDao = fiManageDao;
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

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}

}
