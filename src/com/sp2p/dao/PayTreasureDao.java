/**
 * Project Name:sp2p_web
 * File Name:PayTreasureDao.java
 * Package Name:com.sp2p.dao
 * Date:2015-9-28下午2:42:57
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.sp2p.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables.t_payinvest;
import com.sp2p.util.DateUtil;

/**
 * ClassName:PayTreasureDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015-9-28 下午2:42:57 <br/>
 * @author   hjh
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class PayTreasureDao {
	
	/**
	 * @throws DataException 
	 * 收益临时
	 * @Title: payprofittemp 
	 * @param @param userid
	 * @param @return 设定文件 
	 * @return Map<String,Object> 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public Map<String, String> queryPayProfitTemp(Connection conn,long userid) throws DataException{
		String command = "SELECT * FROM t_payprofittemp t where t.userid="+userid;
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 更新或者新增收益临时记录
	 * @Title: updateOrAddPayProfitTemp 
	 * @param @param conn
	 * @param @param map
	 * @param @param isupdate
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public long updateOrAddPayProfitTemp(Connection conn,Map<String, String> map,boolean isupdate){
		Dao.Tables.t_payprofittemp tPayprofittemp = new Dao().new Tables().new t_payprofittemp();
		tPayprofittemp.time.setValue(map.get("time"));
		tPayprofittemp.amount.setValue(map.get("amount"));
		tPayprofittemp.profit.setValue(map.get("profit"));
		tPayprofittemp.userid.setValue(map.get("userid"));
		tPayprofittemp.rate.setValue(map.get("rate"));
		tPayprofittemp.payprofit.setValue(map.get("payprofit"));
		tPayprofittemp.bearingid.setValue(map.get("bearingid"));
		if (isupdate) {
			tPayprofittemp.intamount.setValue(0);
			tPayprofittemp.outamount.setValue(0);
			return tPayprofittemp.update(conn, " userid="+map.get("userid"));
		}else {
			return tPayprofittemp.insert(conn);
		}
	}
	
	
	/**
	 * @throws DataException 
	 * 查询涨薪宝信息
	 * @Title: querypaytreasure 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @return 设定文件 
	 * @return Map<String,String> 返回类型 
	 * @author hjh
	 * @throws
	 */
	public Map<String, String> querypaytreasure(Connection conn,String userid) throws DataException{
		String command = "";
		if (StringUtils.isNotBlank(userid)) {
			command = "SELECT * FROM t_paytreasure where t.userid="+userid+" limit 0,1";
		}else {
			command = "SELECT * FROM t_paytreasure limit 0,1";
		}
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 新增转入
	 * @Title: intoPayTreasure 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param conn
	 * @param @param paramMap
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public long addPayInvest(Connection conn,Map<String, String> paramMap){
		Dao.Tables.t_payinvest tPayinvest = new Dao(). new Tables(). new t_payinvest();
		tPayinvest.userid.setValue(paramMap.get("userid"));
		tPayinvest.investamount.setValue(paramMap.get("amount"));
		tPayinvest.allprofit.setValue(0);
		return tPayinvest.insert(conn);
	}
	
	public long updateAllProfit(Connection conn,double profit,long userid){
		String command = "UPDATE t_payinvest t SET t.`allprofit` = t.`allprofit`+"+profit+" WHERE t.`userid` = "+userid;
		return MySQL.executeNonQuery(conn, command);
	}
	
	
	public long addPayProfit(Connection conn, long userid,double profitamount,double investamount,String time){
		Dao.Tables.t_payprofit tPayprofit = new Dao().new Tables().new t_payprofit();
		tPayprofit.userid.setValue(userid);
		tPayprofit.profitamount.setValue(profitamount);
		tPayprofit.investamount.setValue(investamount);
		tPayprofit.time.setValue(time);
		return tPayprofit.insert(conn);
	}
	
	public Map<String, String> queryMyPayInvest(Connection conn,long userId) throws DataException{
		String command = "SELECT allprofit,investamount FROM t_payinvest t WHERE t.`userid` = "+userId;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryMyPayInvestApp(Connection conn,long userId) throws DataException{
		String command = "SELECT ifnull(t.`allprofit`,0.00) AS zxb_accum_income ,ifnull(t.`investamount`,0.00) AS zxb_accum,ifnull((SELECT t.`profit`   FROM t_payprofittemp t WHERE t.`userid` ="+userId+"),0.00) AS zxb_yesterday_income FROM t_payinvest t WHERE t.`userid` = "+userId;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String, Object>> queryPayInvest(Connection conn) throws DataException{
		String command = "SELECT * FROM t_payinvest t ";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 更新转让金额
	 * @Title: updatePayTreasure 
	 * @param @param conn
	 * @param @param paramMap
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 * @throws
	 */
	public long updatePayInvest(Connection conn,Map<String, String> paramMap,String symbol){
		String command = "UPDATE t_payinvest t SET t.`investamount` = t.`investamount`"+symbol+paramMap.get("amount")+" WHERE t.`userid` = "+paramMap.get("userid");
		return MySQL.executeNonQuery(conn, command);
	}
	
	/**
	 * 更新转让金额
	 * @Title: updatePayTreasure 
	 * @param @param conn
	 * @param @param paramMap
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 * @throws
	 */
	public long updatePayTreasure(Connection conn,String amount,String symbol){
		//String command = "UPDATE t_paytreasure t SET t.`acount` = t.`acount`"+symbol+ amount/*" WHERE t.`userid` = "+paramMap.get("userid")*/;
		//return MySQL.executeNonQuery(conn, command);
		return 1L;
	}
	
	/**
	 * 增加历史记录
	 * @Title: addPayTreasureH 
	 * @param @param conn
	 * @param @param paramMap 设定文件 
	 * @return void 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public long addPayInvestH(Connection conn,Map<String, String> paramMap){
		Dao.Tables.t_payinvesth tPayinvesth = new Dao() . new Tables(). new t_payinvesth();
		tPayinvesth.handamount.setValue(paramMap.get("amount"));
		tPayinvesth.time.setValue(DateUtil.dateToString(new Date()));
		tPayinvesth.userid.setValue(paramMap.get("userid"));
		tPayinvesth.type.setValue(paramMap.get("type"));
		tPayinvesth.oldamount.setValue(paramMap.get("oldamount"));
		if (StringUtils.isNotBlank(paramMap.get("operationType"))) {//操作类型
			tPayinvesth.operationType.setValue(paramMap.get("operationType"));
		}
		return tPayinvesth.insert(conn);
 	}
	
	/**
	 * 删除以计算的收益
	 * @Title: deletePayInvestH 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param conn
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public long deletePayInvestH(Connection conn,long userId){
		Dao.Tables.t_payprofittemp t_payprofittemp = new Dao() . new Tables(). new t_payprofittemp();
		return t_payprofittemp.delete(conn, " userid ="+userId);
	}
	
	/**
	 * 查询用户转入信息
	 * @Title: querypayinvest 
	 * @param @param conn
	 * @param @param userid
	 * @param @return
	 * @param @throws DataException 设定文件 
	 * @return Map<String,String> 返回类型 
	 * @author hjh
	 * @throws
	 */
	public Map<String, String> querypayinvest(Connection conn,long userid) throws DataException{
		String command = "SELECT * FROM t_payinvest t WHERE t.`userid` ="+userid;
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String,  String> yesterdayProfit(Connection conn, long userId) throws DataException{
		//String command = "SELECT * FROM  t_payprofit t WHERE t.`userid` = "+userId+" AND t.`time`>=CONCAT(DATE_SUB(CURRENT_DATE(), INTERVAL 1 DAY),\" 00:00:00\") AND t.`time`<=CONCAT(CURRENT_DATE(),\" 00:00:00\") LIMIT 0 ,1";
		//String command = "SELECT IFNULL(t.profitamount,0) as zxbprofit FROM t_payprofit t WHERE t.`userid` = "+userId+" AND  t.`time`>=CONCAT(CURRENT_DATE(),\" 00:00:00\") AND t.`time`<=CONCAT(CURRENT_DATE(),\" 24:00:00\") LIMIT 0 ,1";
		String command = "SELECT t.`profit` as zxbprofit FROM t_payprofittemp t WHERE t.`userid` ="+userId;
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String,  String> querySetting(Connection conn, long userId) throws DataException{
		String command = "SELECT * FROM t_paysetting t WHERE t.`userId` ="+userId;
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public long initSetting(Connection conn, long userId) throws DataException{
		Dao.Tables.t_paysetting tPaysetting = new Dao().new Tables().new t_paysetting();
		tPaysetting.open.setValue("0");
		tPaysetting.userId.setValue(userId);
		return tPaysetting.insert(conn);
	}
	
	public long saveSetting(Connection conn, Map<String, String> paramMap,boolean update){
		Dao.Tables.t_paysetting tPaysetting = new Dao().new Tables().new t_paysetting();
		tPaysetting.open.setValue(paramMap.get("open"));
		if ("0".equals(paramMap.get("open"))) {
			tPaysetting.minBalance.setValue(paramMap.get("minBalance"));
		}
		if (update) {
			return tPaysetting.update(conn, "userId = "+paramMap.get("userId"));
		}else {
			tPaysetting.userId.setValue(paramMap.get("userId"));
			return tPaysetting.insert(conn);
		}
	}
	
	/**
	 * 是否还有允许自动转让的用户
	 * @Title: allowAutoIntoPay 
	 * @param @param conn
	 * @param @param userId
	 * @param @return
	 * @param @throws DataException 设定文件 
	 * @return boolean 返回类型 
	 * @author hjh
	 * @throws
	 */
	public boolean allowAutoIntoPay(Connection conn) throws DataException{
		String command = "SELECT  COUNT(tu.id) AS cou  FROM t_user tu,t_paysetting tps WHERE tu.`id` = tps.`userId` AND tps.`open` = 0 AND tu.`usableSum`>tps.`minBalance`";
		DataSet ds = MySQL.executeQuery(conn, command);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		if (map!=null&&map.size()>0) {
			int cou = Convert.strToInt(map.get("cou"), 0);
			return cou>0;
		}else {
			return false;
		}
	}
	
	/*
	 * 涨薪宝当前总额跟发放收益
	 */
	public Map<String, String> querySumPayInvest(Connection conn) throws DataException{
		String command = "SELECT SUM(t.`investamount`) AS amount,SUM(t.`allprofit`) AS profit,(SELECT SUM(th.handamount) FROM t_payinvesth th WHERE th.type=1) AS inamount,(SELECT SUM(th.handamount) FROM t_payinvesth th WHERE th.type=2) AS outamount FROM t_payinvest  t";
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 获取1000条可以自动转入的用户
	 * @Title: queryAllowPayUser 
	 * @param @param conn
	 * @param @return 设定文件 
	 * @return List<Map<String,Object>> 返回类型 
	 * @author hjh
	 * @throws
	 */
	public List<Map<String, Object>> queryAllowPayUser(Connection conn){
		String command = "SELECT tu.`id` ,tu.`usableSum`,tu.`username`,tu.`lastIP`,tps.`minBalance` FROM t_user tu,t_paysetting tps WHERE tu.`id` = tps.`userId` AND tps.`open` = 0 AND tu.`usableSum`>tps.`minBalance` LIMIT 0,1000";
		DataSet ds = MySQL.executeQuery(conn, command);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
}

