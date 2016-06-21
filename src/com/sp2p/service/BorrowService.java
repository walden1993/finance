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
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.sp2p.constants.IConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.RechargeDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Functions;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.util.DateUtil;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.SMSUtil;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.shovesoft.SMS;

/**
 * @ClassName: FinanceService.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:14:21
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 借款业务处理层
 */
public class BorrowService extends BaseService {

	public static Log log = LogFactory.getLog(BorrowService.class);

	private BorrowDao borrowDao;
	private RechargeDao rechargeDao;
	private FundRecordDao fundRecordDao;
	private SelectedService selectedService;
	private BorrowManageService borrowManageService;
	private AccountUsersDao accountUsersDao;
	private UserDao userDao;
	private OperationLogDao operationLogDao;
	private SysparService sysparService;
	
	
	
	public void setSysparService(SysparService sysparService) {
		this.sysparService = sysparService;
	}

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	/**
	 * @param i
	 * @throws DataException
	 * @MethodName: addBorrow
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-7 下午05:02:31
	 * @Return:
	 * @Descb: 添加借款业务处理
	 * @Throws:
	 */
	public Map<String,String> addBorrow(String title, String imgPath, int borrowWay,
			int purpose, int deadLine, int paymentMode, double amount,
			double annualRate, double minTenderedSum, double maxTenderedSum,
			int raiseTerm, int excitationType, double sum, String detail,
			int excitationMode, String investPWD, int hasPWD, String remoteIP,
			long publisher, double fee, int daythe, String basePath,
			String username, double smallestFlowUnit, int circulationNumber,
			int hasCirculationNumber, int subscribe_status, String nid_log,
			double frozen_margin, String json, String jsonState)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		title = Utility.filteSqlInfusion(title);
		imgPath = Utility.filteSqlInfusion(imgPath);
		detail = Utility.filteSqlInfusion(detail);
		investPWD = Utility.filteSqlInfusion(investPWD);
		remoteIP = Utility.filteSqlInfusion(remoteIP);
		basePath = Utility.filteSqlInfusion(basePath);
		username = Utility.filteSqlInfusion(username);
		nid_log = Utility.filteSqlInfusion(nid_log);
		json = Utility.filteSqlInfusion(json);
		jsonState = Utility.filteSqlInfusion(jsonState);
		
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> maps = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			Procedures.p_borrow_initialization(conn, ds, outParameterValues,
					publisher, title, imgPath, borrowWay, "", deadLine,
					paymentMode, new BigDecimal(amount), new BigDecimal(
							annualRate), new BigDecimal(minTenderedSum),
					new BigDecimal(maxTenderedSum), new BigDecimal(raiseTerm),
					detail, 1, publisher, excitationType, new BigDecimal(sum),
					excitationMode, purpose, hasPWD, investPWD, new Date(),
					remoteIP, daythe, new BigDecimal(smallestFlowUnit),
					circulationNumber, nid_log, new BigDecimal(frozen_margin),
					"", basePath, new BigDecimal(fee), json, jsonState, "", "",
					"", "", "", 1, 0, "");
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			maps.put("ret", result + "");
			maps.put("ret_desc", outParameterValues.get(1) + "");
			if (result <= 0) {
				conn.rollback();
			}else{
				userMap = userDao.queryUserById(conn, publisher);
				operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
				userMap.get("username"), ""), IConstants.INSERT, Convert
				.strToStr(userMap.get("lastIP"), ""), amount, "用户发布借款", 1);
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

		return maps;
	}
	
	/**
	 * 借款申请添加
	 * addNewBorrow
	 * @auth hejiahua
	 * @param contact_name
	 * @param contact_phone
	 * @param sex
	 * @param age
	 * @param borrowerType
	 * @param borrowWay
	 * @param hasMortgage
	 * @param borrowAmount
	 * @param borrowLine
	 * @param describe
	 * @return 
	 * long
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-10-28 上午8:21:45
	 * @since  1.0.0
	 */
	public long addNewBorrow(String contact_name,String contact_phone,String sex, String age,String borrowerType,String borrowWay, String hasMortgage ,String borrowAmount,String borrowLine, String describe ) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    long result  = -1L;
	    try {
            Dao.Tables.t_borrow_new tBorrow_new = new Dao().new Tables().new t_borrow_new();
            tBorrow_new.contact_name.setValue(contact_name);
            tBorrow_new.contact_phone.setValue(contact_phone);
            tBorrow_new.borrowAmount.setValue(borrowAmount);
            tBorrow_new.sex.setValue(sex);
            tBorrow_new.age.setValue(age);
            tBorrow_new.borrowerType.setValue(borrowerType);
            tBorrow_new.borrowWay.setValue(borrowWay);
            tBorrow_new.hasMortgage.setValue(hasMortgage);
            tBorrow_new.borrowLine.setValue(borrowLine);
            tBorrow_new.describe.setValue(describe);
            tBorrow_new.createTime.setValue(DateUtil.dateToString(new Date()));
            result = tBorrow_new.insert(conn);
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
     * @param i
     * @throws DataException
     * @MethodName: addBorrow
     * @Param: BorrowService
     * @Author: hejiahua
     * @Date: 2014-10-09 14:48:30
     * @Return:
     * @Descb: 添加借款业务处理（重写）,新增担保方式
     * @Throws:
     */
    public Map<String,String> addBorrow(String agent,int agentWay,String title, String imgPath, int borrowWay,
            int purpose, int deadLine, int paymentMode, double amount,
            double annualRate, double minTenderedSum, double maxTenderedSum,
            int raiseTerm, int excitationType, double sum, String detail,
            int excitationMode, String investPWD, int hasPWD, String remoteIP,
            long publisher, double fee, int daythe, String basePath,
            String username, double smallestFlowUnit, int circulationNumber,
            int hasCirculationNumber, int subscribe_status, String nid_log,
            double frozen_margin, String json, String jsonState)
            throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        title = Utility.filteSqlInfusion(title);
        imgPath = Utility.filteSqlInfusion(imgPath);
        detail = Utility.filteSqlInfusion(detail);
        investPWD = Utility.filteSqlInfusion(investPWD);
        remoteIP = Utility.filteSqlInfusion(remoteIP);
        basePath = Utility.filteSqlInfusion(basePath);
        username = Utility.filteSqlInfusion(username);
        nid_log = Utility.filteSqlInfusion(nid_log);
        json = Utility.filteSqlInfusion(json);
        jsonState = Utility.filteSqlInfusion(jsonState);
        
        Connection conn = MySQL.getConnection();
        DataSet ds = new DataSet();
        List<Object> outParameterValues = new ArrayList<Object>();
        Map<String, String> maps = new HashMap<String, String>();
        Map<String, String> userMap = new HashMap<String, String>();
        Long result = -1L;
        try {
            Procedures.p_borrow_initialization(conn, ds, outParameterValues,agentWay,
                    publisher, title, imgPath, borrowWay, "", deadLine,
                    paymentMode, new BigDecimal(amount), new BigDecimal(
                            annualRate), new BigDecimal(minTenderedSum),
                    new BigDecimal(maxTenderedSum), new BigDecimal(raiseTerm),
                    detail, 1, publisher, excitationType, new BigDecimal(sum),
                    excitationMode, purpose, hasPWD, investPWD, new Date(),
                    remoteIP, daythe, new BigDecimal(smallestFlowUnit),
                    circulationNumber, nid_log, new BigDecimal(frozen_margin),
                    "", basePath, new BigDecimal(fee), json, jsonState,agent, "",
                    "", "", "", 1, 0, "");
            result = Convert.strToLong(outParameterValues.get(0) + "", -1);
            maps.put("ret", result + "");
            maps.put("ret_desc", outParameterValues.get(1) + "");
            if (result <= 0) {
                conn.rollback();
            }else{
                userMap = userDao.queryUserById(conn, publisher);
                operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
                userMap.get("username"), ""), IConstants.INSERT, Convert
                .strToStr(userMap.get("lastIP"), ""), amount, "用户发布借款", 1);
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

        return maps;
    }
	

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryBorrowConcernAppByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:45:15
	 * @Return:
	 * @Descb: app关注的借款列表查询
	 * @Throws:
	 */
	public void queryBorrowConcernAppByCondition(String title,
			String publishTimeStart, String publishTimeEnd, long userId,
			PageBean pageBean, String deadline, String borrowWay)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		title = Utility.filteSqlInfusion(title);
		publishTimeStart = Utility.filteSqlInfusion(publishTimeStart);
		publishTimeEnd = Utility.filteSqlInfusion(publishTimeEnd);
		deadline = Utility.filteSqlInfusion(deadline);
		borrowWay = Utility.filteSqlInfusion(borrowWay);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >'"
					+ StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ "'");
		}
		if (StringUtils.isNotBlank(deadline)) {
			condition.append(" and deadline ='"
					+ StringEscapeUtils.escapeSql(deadline.trim()) + "'");
		}
		if (StringUtils.isNotBlank(borrowWay)) {
			condition.append(" and borrowWay ='"
					+ StringEscapeUtils.escapeSql(borrowWay.trim()) + "'");
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <'"
					+ StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + "'");
		}
		condition.append(" and userId =" + userId);
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_concern", resultFeilds,
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryCreditingByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午05:13:17
	 * @Return:
	 * @Descb: 根据条件查询信用申请信息
	 * @Throws:
	 */
	public void queryCreditingByCondition(long userId, PageBean pageBean)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String resultFeilds = " id,creditingName,applyAmount,applyDetail,status";
		StringBuffer condition = new StringBuffer();
		condition.append(" and applyer =" + userId);
		Connection conn = MySQL.getConnection();

		try {
			dataPage(conn, pageBean, " v_t_crediting_list", resultFeilds,
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryCreditingApply
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午07:53:11
	 * @Return:
	 * @Descb: 查询能够再次申请信用额度的记录
	 * @Throws:
	 */
	public Map<String, String> queryCreditingApply(long userId)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditingApply(conn, userId);
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
	 * @MethodName: addCrediting
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-8 下午04:29:23
	 * @Return:
	 * @Descb: 添加信用申请
	 * @Throws:
	 */
	public Long addCrediting(double applyAmount, String applyDetail, long userId)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = borrowDao.addCrediting(conn, applyAmount, applyDetail,
					userId);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_crediting", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "发布额度申请", 1);
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
	 * @MethodName: queryBorrowTypeNetValueCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-9 下午01:00:07
	 * @Return:
	 * @Descb: 查询用户的净值借款条件记录
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTypeNetValueCondition(long userId,
			double borrowSum) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		double amount = 0;
		// 待收金额
		double forpaySum = 0;
		// 待还金额
		double forRepaySum = 0;
		// 可用金额
		double usableSum = 0;

		try {
			// 待收借款
			Map<String, String> forpayBorrowMap = borrowDao.queryForpayBorrow(
					conn, userId);
			if (forpayBorrowMap == null) {
				forpayBorrowMap = new HashMap<String, String>();
			}
			forpaySum = Convert.strToDouble(forpayBorrowMap.get("forpaySum")
					+ "", 0);
			// 待还借款
			Map<String, String> forRePaySumMap = borrowDao.queryForRepayBorrow(
					conn, userId);
			if (forRePaySumMap == null) {
				forRePaySumMap = new HashMap<String, String>();
			}
			forRepaySum = Convert.strToDouble(forRePaySumMap.get("forRepaySum")
					+ "", 0);
			// 用户可用金额
			Map<String, String> userAmountMap = borrowDao.queryUserAmount(conn,
					userId);

			if (userAmountMap == null) {
				userAmountMap = new HashMap<String, String>();
			}
			usableSum = Convert.strToDouble(
					userAmountMap.get("usableSum") + "", 0);
			amount = usableSum + forpaySum - forRepaySum;
			// 净值金额大于10000才可以发布
			if (amount >= 10000) {
				if (borrowSum > 0) {
					// 发布借款的上限额
					amount = amount * 0.7;
					if (borrowSum <= amount) {
						map.put("result", "1");
					} else {
						map.put("result", "0");
					}
				} else {
					map.put("result", "1");
				}
			} else {
				map.put("result", "0");
			}
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
	 * @MethodName: queryBorrowTypeSecondsCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-9 下午01:01:16
	 * @Return:
	 * @Descb: 查询用户的秒还借款条件记录
	 * @Throws:
	 */
	public Map<String, String> queryBorrowTypeSecondsCondition(
			double borrowAmount, double borrowAnnualRate, long userId,
			Map<String, Object> platformCostMap, double frozenMargin)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowTypeSecondsCondition(conn, borrowAmount,
					borrowAnnualRate, userId, platformCostMap, frozenMargin);

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
	 * 查询用户可以资金是否小于保障金额
	 * 
	 * @param frozen
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryBorrowFinMoney(double frozen, long userId)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryBorrowFinMoney(conn, frozen, userId);
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
	 * 
	 * refreshBorrowTicketTime
	 * 刷新体验标时间
	 * @auth hejiahua
	 * @return 
	 * Long
	 * @exception 
	 * @date:2014-8-26 下午2:40:58
	 * @since  1.0.0
	 */
	public void refreshBorrowTrtialTime() throws Exception {
	    log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection conn = MySQL.getConnection();
        int result = 0;
        // 借款id
        long id = -1;
        // 用户id
        SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
        String date = "";
        //Map<String, String> maxTime = null;
        Map<String, String> minTime = null;
        try {
            List<Map<String, Object>> borrowList = borrowDao.queryTrialBorrow(conn);
            List<Object> outParameterValues = new ArrayList<Object>();
            DataSet ds = new DataSet();
            for (Map<String, Object> borrowMap : borrowList) {
                date = sf.format(new Date());
                id = Convert.strToLong(borrowMap.get("id") + "", -1);
                // 当前时间小于剩余结束时间,剩余开始时间为当前时间
                //maxTime = borrowDao.queryMaxTimeTrial(conn, id, date);
                //if (maxTime != null && maxTime.size() > 0) {
                   // borrowDao.updateTimeTrial(conn, id, date);
              //  }
                // 当前时间大于剩余结束时间,剩余开始时间为剩余结束时间
                minTime = borrowDao.queryMinTimeTrail(conn, id, date);
                if (minTime != null && minTime.size() > 0) {
                        // 更新借款为满标 
                        //borrowDao.updateBorrowTrtialState(conn, id,IConstants.BORROW_STATUS_1);
                        Procedures.p_trial_update_expired(conn, ds, outParameterValues, id, 0  , "");
                        result = Convert.strToInt(outParameterValues.get(0).toString(), 0);
                        if (result!=-1) {
                            conn.rollback();
                        }else {
                            conn.commit();
                        }
                }
            }
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
	 * @MethodName: refreshBorrowTime
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-17 上午10:58:33
	 * @Return:
	 * @Descb: 刷新借款时间
	 * @Throws:
	 */
	public Long refreshBorrowTime() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		// 借款id
		long id = -1;
		// 用户id
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		String date = "";
		Map<String, String> maxTime = null;
		Map<String, String> minTime = null;
		Map<String, String> borrowStatus = null;
		try {
			List<Map<String, Object>> borrowList = borrowDao.queryBorrow(conn);
			for (Map<String, Object> borrowMap : borrowList) {
				date = sf.format(new Date());
				id = Convert.strToLong(borrowMap.get("id") + "", -1);
				// 当前时间小于剩余结束时间,剩余开始时间为当前时间
				maxTime = borrowDao.queryMaxTime(conn, id, date);
				if (maxTime != null && maxTime.size() > 0) {
					borrowDao.updateTime(conn, id, date);
				}
				// 当前时间大于剩余结束时间,剩余开始时间为剩余结束时间
				minTime = borrowDao.queryMinTime(conn, id, date);
				if (minTime != null && minTime.size() > 0) {
					// 借款总额等于投资总额,则为满标,否则流标
					borrowStatus = borrowDao.queryBorrowState(conn, id);
					if (borrowStatus != null && borrowStatus.size() > 0) {
						// 更新借款为满标 满标sorts 为 5
						borrowDao.updateBorrowState(conn, id,IConstants.BORROW_STATUS_3, 5);
					} else {
						// 更新借款为流标
						//borrowManageService.reBackBorrow(id, -1, IConstants.WEB_URL);
						log.info("标的过期处理......................start");
						String sql = "SELECT t.`introduce` FROM t_select t WHERE t.`typeId` = 18 AND t.`selectKey` = \"LBMSG\"";
						Map<String, String> mobile = sysparService.querySysparBySql(sql);
						if (mobile!=null && mobile.size()>0) {
							String mobileString = mobile.get("introduce");
							if (StringUtils.isNotBlank(mobileString)) {
								String [] mobiles = mobileString.split("&")[0].split(",");
								for (int i = 0; i < mobiles.length; i++) {
									SMSUtil.sendSMS("", "", "标的已过期起，请及时添加借款时间,标的ID["+id+"]", mobiles[i], "");
								}
							}
						}
						log.info("标的过期处理...........................end");
					}
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

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryBorrowConcernByCondition
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午11:45:15
	 * @Return:
	 * @Descb: 关注的借款列表查询
	 * @Throws:
	 */
	public void queryBorrowConcernByCondition(String title,
			String publishTimeStart, String publishTimeEnd, long userId,
			PageBean pageBean) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		title = Utility.filteSqlInfusion(title);
		publishTimeStart = Utility.filteSqlInfusion(publishTimeStart);
		publishTimeEnd = Utility.filteSqlInfusion(publishTimeEnd);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(title)) {
			condition.append(" and borrowTitle  LIKE CONCAT('%','"
					+ StringEscapeUtils.escapeSql(title.trim()) + "','%')");
		}
		if (StringUtils.isNotBlank(publishTimeStart)) {
			condition.append(" and publishTime >='"
					+ StringEscapeUtils.escapeSql(publishTimeStart.trim())
					+ " 00:00:00'");//添加时间戳  liyanhua
		}
		if (StringUtils.isNotBlank(publishTimeEnd)) {
			condition.append(" and publishTime <='"
					+ StringEscapeUtils.escapeSql(publishTimeEnd.trim()) + " 23:59:59'");//添加时间戳  liyanhua 方便查询当天记录
		}
		condition.append(" and userId =" + userId);
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_concern", resultFeilds,
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @throws DataException
	 * @MethodName: delBorrowConcern
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-19 上午12:24:56
	 * @Return:
	 * @Descb: 删除关注的借款
	 * @Throws:
	 */
	public void delBorrowConcern(long idLong, Long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = borrowDao.delBorrowConcern(conn, idLong, userId);
			if (result < 0) {
				conn.rollback();
			} else {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_concern", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.DELETE, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "删除关注的借款", 1);
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
	}

	/**
	 * @MethodName: queryCreditLimit
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-3-25 下午10:09:55
	 * @Return:
	 * @Descb: 查询可用信用额度
	 * @Throws:
	 */
	public Map<String, String> queryCreditLimit(double amountDouble, Long id)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditLimit(conn, amountDouble, id);
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
	 * houli 查询是否有未满标审核的借款标的
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryBorrowStatus(Long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> lists;
		int count = 0;
		try {
			lists = borrowDao.queryBorrowStatus(conn, userId);
			
			if (lists == null || lists.size() <= 0) {// 如果没有该用户的借款信息，那么该用户可以发布借款
				return 1L;
			} else {
				for (Map<String, Object> map : lists) {
					int status = Convert.strToInt(map.get("borrowStatus").toString(), -1);
					if (status > 3) {// 如果该用户的借款标的已经满标审核通过
						count++;
					}
				}
				//return count == lists.size() ? 1L : -1L;
				return 1l;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * add by houli
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryBaseApprove(Long userId, int status) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = borrowDao.queryBaseApprove(conn, userId,
					status);
			
			if (map == null || map.size() <= 0) {
				return -1L;
			}
			return 1L;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long queryBaseFiveApprove(Long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = borrowDao.queryBaseFiveApprove(conn,
					userId);
			if (map == null || map.size() <= 0) {
				return -1L;
				
			}
			int status = Convert.strToInt(map.get("auditStatus"), -1);
			if (status < 15) {
				return -1L;
			}
			return 1L;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	public Long queryBaseFiveApprove(Long userId,int userType) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = borrowDao.queryBaseFiveApprove(conn,
					userId,userType);
			if (map == null || map.size() <= 0) {
				return -1L;
				
			}
			int status = Convert.strToInt(map.get("auditStatus"), -1);
			if(userType==1){//判断用户五项基本认证资料是否认证成功
				if (status < 15) {
					return -1L;
				}
			}else if(userType==2){//判断企业三项基本认证资料是否认证成功
				if (status < 9) {
					return -1L;
				}
			}
			return 1L;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: addCirculationBorrow
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-5-17 下午05:06:07
	 * @Return:
	 * @Descb: 添加流转标借款
	 * @Throws:
	 */
	public long addCirculationBorrow(String title, String imgPath,
			int borrowWay, int purposeInt, int deadLineInt, int paymentMode,
			double amountDouble, double annualRateDouble, String remoteIP,
			int circulationNumber, double smallestFlowUnitDouble, Long id,
			String businessDetail, String assets, String moneyPurposes,
			int dayThe, String basePath, String userName,
			int excitationTypeInt, double sumInt, String json,
			String jsonState, String nid, String agent, String counterAgent,
			double fee) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		title = Utility.filteSqlInfusion(title);
		imgPath = Utility.filteSqlInfusion(imgPath);
		remoteIP = Utility.filteSqlInfusion(remoteIP);
		businessDetail = Utility.filteSqlInfusion(businessDetail);
		assets = Utility.filteSqlInfusion(assets);
		moneyPurposes = Utility.filteSqlInfusion(moneyPurposes);
		basePath = Utility.filteSqlInfusion(basePath);
		userName = Utility.filteSqlInfusion(userName);
		json = Utility.filteSqlInfusion(json);
		jsonState = Utility.filteSqlInfusion(jsonState);
		nid = Utility.filteSqlInfusion(nid);
		agent = Utility.filteSqlInfusion(agent);
		counterAgent = Utility.filteSqlInfusion(counterAgent);
		
		Connection conn = MySQL.getConnection();
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		Map<String, String> maps = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			Procedures.p_borrow_initialization(conn, ds, outParameterValues,
					id, title, imgPath, borrowWay, "", deadLineInt,
					paymentMode, new BigDecimal(amountDouble), new BigDecimal(
							annualRateDouble), new BigDecimal(-1),
					new BigDecimal(-1), new BigDecimal(deadLineInt), "", 1, id,
					excitationTypeInt, new BigDecimal(sumInt), 1, purposeInt,
					-1, "", new Date(), remoteIP, dayThe, new BigDecimal(
							smallestFlowUnitDouble), circulationNumber, nid,
					new BigDecimal(fee), "", basePath, new BigDecimal(fee),
					json, jsonState, agent, counterAgent, businessDetail,
					assets, moneyPurposes, 2, 0, "");
			
			result = Convert.strToLong(outParameterValues.get(0) + "", -1);
			maps.put("ret", result + "");
			maps.put("ret_desc", outParameterValues.get(1) + "");
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			// 添加操作日志
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_borrow", Convert.strToStr(
					userMap.get("username"), ""), IConstants.INSERT, Convert
					.strToStr(userMap.get("lastIP"), ""), amountDouble, "用户发布借款", 1);
			
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

	public RechargeDao getRechargeDao() {
		return rechargeDao;
	}

	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	/**
	 * @MethodName: queryCurrentCreditLimet
	 * @Param: BorrowService
	 * @Author: gang.lv
	 * @Date: 2013-5-11 下午04:47:31
	 * @Return:
	 * @Descb: 查询当前可用信用额度
	 * @Throws:
	 */
	public Map<String, String> queryCurrentCreditLimet(Long id)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = borrowDao.queryCreditLimit(conn, id);
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
	 * 
	 * queryActivityInvest:查询用户在活动期间内的投资额 <br/>
	 *
	 * @author he
	 * @param startTime  开始时间
	 * @param endTime   结束时间
	 * @param id  用户id
	 * @return  查询结果
	 * @throws SQLException 
	 * @since JDK 1.6
	 */
	public Map<String, String> queryActivityInvest(String startTime,String endTime,long id) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return borrowDao.queryActivityInvest(conn, startTime, endTime, id);
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	/**
	 * 
	 * queryAwardInfo:查询该期的奖品信息，展示在前台. <br/>
	 *
	 * @author he
	 * @param ternId 活动期数ID
	 * @return 返回奖品信息列表
	 * @throws SQLException 
	 * @since JDK 1.6
	 */
	public List<Map<String, String>> queryAwardInfo(String termId) throws SQLException{
		Connection conn = MySQL.getConnection();
		List<Map<String, String>> awardInfoList = null;
		try {
			awardInfoList = borrowDao.queryAwardInfo(conn, termId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return awardInfoList;
	}
	
	/**
	 * @throws SQLException 
	 * 
	 * @Title: saveTransfer 
	 * @Description: 保存债权人信息
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 * @throws
	 */
	public void saveTransfer(String name,String code) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			long result = borrowDao.saveTransfer(conn,name,code);
			if (result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	
	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
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
}
