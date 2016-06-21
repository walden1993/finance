/**
 * Project Name:sp2p_web
 * File Name:PayTreasureService.java
 * Package Name:com.sp2p.service
 * Date:2015-9-28下午2:38:59
 * Copyright (c) 2015
 *
*/

package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.shove.base.BaseService;
import com.sp2p.dao.PayTreasureDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.util.AmountUtil;

/**
 * 涨薪宝服务
 * ClassName:PayTreasureService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015-9-28 下午2:38:59 <br/>
 * @author   hjh
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class PayTreasureService extends BaseService{
	public static Log log =  LogFactory.getLog(PayTreasureService.class);
	PayTreasureDao payTreasureDao;
	UserService userService;
	
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public PayTreasureDao getPayTreasureDao() {
		return payTreasureDao;
	}

	public void setPayTreasureDao(PayTreasureDao payTreasureDao) {
		this.payTreasureDao = payTreasureDao;
	}
	
	
	/**
	 * 涨薪宝详情
	 * @Title: paytreasuredetail 
	 * @param @param userid
	 * @param @return
	 * @param @throws DataException
	 * @param @throws SQLException 设定文件 
	 * @return Map<String,Object> 返回类型 
	 * @author hjh
	 */
	public Map<String, Object> paytreasuredetail(long userid) throws DataException, SQLException{
		Connection conn = MySQL.getConnection();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, String> paytreasure = payTreasureDao.querypaytreasure(conn,null);
			Map<String, String> payinvest = payTreasureDao.querypayinvest(conn, userid);
			map.put("paytreasure",paytreasure);
			map.put("payinvest", payinvest);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return map;
	}
	
	
	/**
	 * 转入金额
	 * @Title: intoPayTreasure 
	 * @param @param map
	 * @param @return
	 * @param @throws SQLException 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 
	 */
	public long intoPayTreasure(Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = publicIntoPay(paramMap, conn);
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			result = -1L;
		} finally {
			conn.close();
		}
		return result;
	}

	public long publicIntoPay(Map<String, String> paramMap, Connection conn)
			throws DataException, Exception, SQLException {
		long result;
		long userid = Convert.strToLong(paramMap.get("userid"), -1l);
		Map<String, String> payinvest = payTreasureDao.querypayinvest(conn, userid);
		String oldAmount = "0";
		if (payinvest!=null && payinvest.size()>0) {//已经有过投资了
			result = payTreasureDao.updatePayInvest(conn, paramMap,"+");
			oldAmount = payinvest.get("investamount");
		}else {//第一次
			result = payTreasureDao.addPayInvest(conn, paramMap);
		}
		if (result>0) {//历史记录
			paramMap.put("oldamount", oldAmount);
			//历史表增加记录
			paramMap.put("type", "1");//转入
			result = payTreasureDao.addPayInvestH(conn, paramMap);
		}
		
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		
		//转入
		Procedures.p_t_payoutamount(conn, ds, outParameterValues, userid,0, Convert.strToDouble(paramMap.get("amount"), 0));
		
		if (result>0) {
			result = payTreasureDao.updatePayTreasure(conn, paramMap.get("amount"), "+");
			if (result>0) {
				//对账户资金进行扣除
				double usableSum = Convert.strToDouble(paramMap.get("amount"), 0.0);
				String username = paramMap.get("username");
				String ip = paramMap.get("ip");
				String remark="可用余额转入涨薪宝，转入金额["+usableSum+"]元。";
				result = userService.deductionUsableSum(conn,userid, remark, usableSum, username, ip);
				if (result>0) {
					//更新用户资金校验
					userService.updateSign(conn, userid);
					conn.commit();
				}else {
					conn.rollback();
				}
			}else {
				conn.rollback();
			}
		}else {
			conn.rollback();
		}
		return result;
	}
	
	/**
	 * 转出金额
	 * @Title: intoPayTreasure 
	 * @param @param map
	 * @param @return
	 * @param @throws SQLException 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 
	 */
	public  long outPayTreasure(Map<String, String> paramMap,boolean allOut) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			long userid = Convert.strToLong(paramMap.get("userid"), -1l);
			Map<String, String> payinvest = payTreasureDao.querypayinvest(conn, userid);
			if (payinvest!=null) {//已经有过投资了
				result = payTreasureDao.updatePayInvest(conn, paramMap,"-");
				if (result>0) {
					String oldAmount = payinvest.get("investamount");
					paramMap.put("oldamount", oldAmount);
					//历史表增加记录
					paramMap.put("type", "2");//转出
					result = payTreasureDao.addPayInvestH(conn, paramMap);
				}
				DataSet ds = new DataSet();
				List<Object> outParameterValues = new ArrayList<Object>();
				//转出
				Procedures.p_t_payoutamount(conn, ds, outParameterValues, userid, Convert.strToDouble(paramMap.get("amount"), 0),0);
			}
			if (result>0) {
				result = payTreasureDao.updatePayTreasure(conn, paramMap.get("amount"), "-");
				if (result>0) {
					//对账户资金进行增加
					double usableSum = Convert.strToDouble(paramMap.get("amount"), 0.0);
					String username = paramMap.get("username");
					String ip = paramMap.get("ip");
					String remark="涨薪宝转入可用余额，转入金额["+usableSum+"]元。";
					result = userService.rechargeUsableSum(conn,10,userid, remark, usableSum, username,"涨薪宝转出", ip);
					if (result>0) {
						if (allOut) {//全部转出
							//已过期
							//payTreasureDao.deletePayInvestH(conn, userid);
						}
						//更新用户资金校验
						userService.updateSign(conn, userid);
						conn.commit();
					}else {
						conn.rollback();
					}
				}else {
					conn.rollback();
				}
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			conn.rollback();
			log.error(e);
			e.printStackTrace();
			result = -1L;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	  SQLException 
	 * 查询我的涨薪宝总额
	 * @Title: queryMyPayInvest 
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return Map<String,String> 返回类型 
	 * @author hjh
	 
	 */
	public Map<String, String> queryMyPayInvest(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return payTreasureDao.queryMyPayInvest(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	  SQLException 
	 * 查询我的涨薪宝总额
	 * @Title: queryMyPayInvest 
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return Map<String,String> 返回类型 
	 * @author hjh
	 
	 */
	public Map<String, String> queryMyPayInvestApp(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return payTreasureDao.queryMyPayInvestApp(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	 * 我的转入转出记录（非分页）
	 * @Title: payInvestRecordNotPage 
	 * @param @param userId
	 * @param @param startTime
	 * @param @param endTime
	 * @param @param type
	 * @param @return
	 * @param @throws SQLException 设定文件 
	 * @return Map<String,String> 返回类型 
	 * @author hjh
	 * @throws
	 */
	public Map<String, String> payInvestRecordNotPage(long userId,String startTime,String endTime,String type) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			
			StringBuffer condition = new StringBuffer();
			condition.append(" SELECT SUM(`handamount`)  AS handamount  FROM t_payinvesth  WHERE   userid = ").append(userId);
			if (StringUtils.isNotBlank(startTime)) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(startTime)).append("' ");
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition.append(" and time <'").append(Utility.filteSqlInfusion(endTime)).append("' ");
			}
			if (StringUtils.isNotBlank(type)) {
				condition.append(" and type =").append(type);
			}
			DataSet ds = MySQL.executeQuery(conn, condition.toString());
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	
	/**
	 * 我的转入转出记录
	 * @Title: payInvestRecord 
	 * @param @param pageBean
	 * @param @param userId
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 
	 */
	public void payInvestRecordApp(PageBean pageBean,long userId,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			
			StringBuffer condition = new StringBuffer();
			condition.append(" and userid = ").append(userId);
			if (StringUtils.isNotBlank(paramMap.get("publishTimeStart"))) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeStart"))).append(" 00:00:00 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("publishTimeEnd"))) {
				condition.append(" and time <='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeEnd"))).append(" 23:59:59 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("type"))) {
				condition.append(" and type =").append(paramMap.get("type"));
			}
			
			dataPage(conn, pageBean, " t_payinvesth t", " t.`type`,t.`operationType` AS operation_type,t.`handamount` AS in_out_amount,case  when t.`type`=1 then (t.`oldamount`+t.`handamount`) else (t.`oldamount`-t.`handamount`) end AS zxb_accum,t.`time` AS operation_time ", " order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 我的转入转出记录
	 * @Title: payInvestRecord 
	 * @param @param pageBean
	 * @param @param userId
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 
	 */
	public void payInvestRecord(PageBean pageBean,long userId,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			
			StringBuffer condition = new StringBuffer();
			condition.append(" and userid = ").append(userId);
			if (StringUtils.isNotBlank(paramMap.get("publishTimeStart"))) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeStart"))).append(" 00:00:00 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("publishTimeEnd"))) {
				condition.append(" and time <='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeEnd"))).append(" 23:59:59 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("type"))) {
				condition.append(" and type =").append(paramMap.get("type"));
			}
			
			dataPage(conn, pageBean, "t_payinvesth", "*", " order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 我的收益记录
	 * @Title: profitRecord 
	 * @param @param pageBean
	 * @param @param userId
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 
	 */
	public void profitRecord(PageBean pageBean,long userId,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			condition.append(" and userid = ").append(userId).append(" and profitamount>0 ");
			if (StringUtils.isNotBlank(paramMap.get("publishTimeStart"))) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeStart"))).append(" 00:00:00 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("publishTimeEnd"))) {
				condition.append(" and time <='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeEnd"))).append(" 23:59:59 ' ");
			}
			dataPage(conn, pageBean, "t_payprofit", "*", " order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 我的收益记录
	 * @Title: profitRecord 
	 * @param @param pageBean
	 * @param @param userId
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 
	 */
	public void profitRecordApp(PageBean pageBean,long userId,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			condition.append(" and userid = ").append(userId).append(" and t.`profitamount`>0 ");
			if (StringUtils.isNotBlank(paramMap.get("publishTimeStart"))) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeStart"))).append(" 00:00:00 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("publishTimeEnd"))) {
				condition.append(" and time <='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeEnd"))).append(" 23:59:59 ' ");
			}
			dataPage(conn, pageBean, "t_payprofit t", "  t.`profitamount` AS in_amount,t.`time` AS in_time  ", " order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 我的转入转出记录
	 * @Title: payInvestRecord 
	 * @param @param pageBean
	 * @param @param userId
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 
	 */
	public List<Map<String, Object>> payInvestRecord(PageBean pageBean,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			
			StringBuffer condition = new StringBuffer();
			if (StringUtils.isNotBlank(paramMap.get("publishTimeStart"))) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeStart"))).append(" 00:00:00 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("publishTimeEnd"))) {
				condition.append(" and time <='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeEnd"))).append(" 23:59:59 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("type"))) {
				condition.append(" and type =").append(paramMap.get("type"));
			}
			if (StringUtils.isNotBlank(paramMap.get("username"))) {
				condition.append(" and username like '%").append(paramMap.get("username")).append("%'");
			}
			if (StringUtils.isNotBlank(paramMap.get("realName"))) {
				condition.append(" and realName like '%").append(paramMap.get("realName")).append("%'");
			}
			dataPage(conn, pageBean, "(SELECT t.*,tp.`realName`,tp.`cellPhone`,tu.`username` FROM t_payinvesth t ,t_person tp,t_user tu WHERE t.`userid` = tp.`userId` AND t.`userid` = tu.`id`) tb", "*", " order by id desc", condition.toString());
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT SUM(t.handamount) as handamount ,t.`type` FROM t_payinvesth t ,t_person tp,t_user tu WHERE t.`userid` = tp.`userId` AND t.`userid` = tu.`id` ");
			sql.append(condition);
			sql.append(" GROUP BY t.`type` order by t.type ");
			return sqlToList(conn, sql.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	 * 我的收益记录
	 * @Title: profitRecord 
	 * @param @param pageBean
	 * @param @param userId
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 
	 */
	public Map<String, String> profitRecord(PageBean pageBean,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			if (StringUtils.isNotBlank(paramMap.get("publishTimeStart"))) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeStart"))).append(" 00:00:00 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("publishTimeEnd"))) {
				condition.append(" and time <='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeEnd"))).append(" 23:59:59 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("username"))) {
				condition.append(" and username like '%").append(paramMap.get("username")).append("%'");
			}
			if (StringUtils.isNotBlank(paramMap.get("realName"))) {
				condition.append(" and realName like '%").append(paramMap.get("realName")).append("%'");
			}
			
			dataPage(conn, pageBean, "(SELECT t.*,tp.`realName`,tp.`cellPhone`,tu.`username` FROM t_payprofit t ,t_person tp,t_user tu WHERE t.`userid` = tp.`userId` AND t.`userid` = tu.`id`) tb", "*", " order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return null;
	}
	
	/**
	 * 昨日收益
	 * @Title: yesterdayProfit 
	 * @param @param userId
	 * @param @return
	 * @param @throws SQLException 设定文件 
	 * @return Map<String,String> 返回类型 
	 * @author Administrator
	 
	 */
	public Map<String, String> yesterdayProfit(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return payTreasureDao.yesterdayProfit(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	 * 初始化设置
	 * @Title: defaultSetting 
	 * @param @param userId
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author Administrator
	 
	 */
	public void defaultSetting(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			payTreasureDao.initSetting(conn, userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	  SQLException 
	 * 将所有已经勾选自动转入协议的用户，执行自动转入
	 * @Title: autoIntoPayTreasure 
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 
	 */
	public void autoIntoPayTreasure() throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			//得到可以转入的用户
			List<Map<String, Object>> users = payTreasureDao.queryAllowPayUser(conn);
			for (Map<String, Object> map : users) {
				long userId = MapUtils.getLongValue(map, "id");
				double usableSum = MapUtils.getDoubleValue(map, "usableSum");
				double minBalance = MapUtils.getDoubleValue(map, "minBalance");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userid", userId+"");
				paramMap.put("amount", AmountUtil.mathRound(usableSum-minBalance)+"");
				paramMap.put("username", map.get("username").toString());
				paramMap.put("ip", map.get("lastIP").toString());
				paramMap.put("operationType", "1");
				publicIntoPay(paramMap, conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 发工资转入涨薪宝
	 * @Title: autoIntoPayTreasure 
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @param operationType 1自动，2手动，3定时
	 * @author hjh
	 
	 */
	public long autoIntoPayTreasureOne(long userId,double usableSum,String username,String ip,String operationType) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			//得到可以转入的用户
			paramMap.put("userid", userId+"");
			paramMap.put("amount", usableSum+"");
			paramMap.put("username", username);
			paramMap.put("ip", ip);
			paramMap.put("operationType",operationType);
			result = publicIntoPay(paramMap, conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return result;
	}
	
	public boolean allowAutoIntoPay() throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return payTreasureDao.allowAutoIntoPay(conn);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			conn.close();
		}
		return false;
	}
	
	/**
	 * 查询涨薪宝设置
	 * @Title: querySetting 
	 * @param @param userId
	 * @param @return
	 * @param @throws SQLException 设定文件 
	 * @return Map<String,String> 返回类型 
	 * @author Administrator
	 
	 */
	public Map<String, String> querySetting(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return payTreasureDao.querySetting(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	  SQLException 
	 * 保存涨薪宝设置
	 * @Title: saveSetting 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param paramMap
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 
	 */
	public long saveSetting(Map<String, String> paramMap,long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			Map<String, String> updateMap = querySetting(userId);
			boolean update = updateMap!=null&&updateMap.size()>0;
			result = payTreasureDao.saveSetting(conn, paramMap, update);
			if (result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询所有的当前转入金额
	 * @Title: queryAllPayInvest 
	 * @param @param pageBean
	 * @param @param paramMap
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author hjh
	 */
	public void queryAllPayInvest(PageBean pageBean,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			if (StringUtils.isNotBlank(paramMap.get("username"))) {
				condition.append(" and username like '%").append(paramMap.get("username")).append("%' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("realName"))) {
				condition.append(" and realName like '%").append(paramMap.get("realName")).append("%' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("cellPhone"))) {
				condition.append(" and cellPhone like '%").append(paramMap.get("cellPhone")).append("%' ");
			}
			
			dataPage(conn, pageBean, "v_payinvest_user", "*", "", condition.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			conn.close();
		}
	}
	
	//统计涨薪宝总额跟发放收益
	public Map<String, String> querySumPayInvest() throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return payTreasureDao.querySumPayInvest(conn);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	 * 涨薪宝每日记录
	 * @Title: paydailyrecordList 
	 * @param @param pageBean
	 * @param @param paramMap
	 * @param @throws SQLException 设定文件 
	 * @return void 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public void paydailyrecordList(PageBean pageBean,Map<String, String> paramMap) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			StringBuffer condition = new StringBuffer();
			if (StringUtils.isNotBlank(paramMap.get("publishTimeStart"))) {
				condition.append(" and time >='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeStart"))).append(" 00:00:00 ' ");
			}
			if (StringUtils.isNotBlank(paramMap.get("publishTimeEnd"))) {
				condition.append(" and time <='").append(Utility.filteSqlInfusion(paramMap.get("publishTimeEnd"))).append(" 23:59:59 ' ");
			}
			dataPage(conn, pageBean, "t_paydailyrecord", "*", " order by id desc", condition.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} finally {
			conn.close();
		}
	}
}

