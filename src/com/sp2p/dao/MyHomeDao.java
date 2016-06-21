package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.sp2p.constants.IConstants;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_award_moth;
import com.sp2p.database.Dao.Views.v_t_trial_ticket;

public class MyHomeDao {


	/**
	 * @MethodName: queryBorrowRepaymentById
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:51:39
	 * @Return:
	 * @Descb: 查询投资人回收中借款的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowForpayById(Connection conn,
			long id, long userId,long iid) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.repayPeriod as  repayPeriod ,DATE_FORMAT(a. repayDate,'%Y-%m-%d')  as  repayDate,round(a.recivedPrincipal,2) AS  forpayPrincipal , " );
		command.append("round(a.recivedInterest,2) AS  forpayInterest , round(a.principalBalance,2) AS  principalBalance , round(a.iManageFee,2)   AS  manage , " );
		command.append("a.isLate as  isLate ,a.lateDay as  lateDay ,round(a.recivedFI,2) AS  forFI ,round((a.recivedInterest -a.iManageFee+a.recivedFI ),2) AS earn , c.borrowTitle as borrowTitle ," );
		command.append("d.username as username,a.isWebRepay as isWebRepay, a.repayStatus AS repayStatus from t_invest_repayment a LEFT JOIN " );
		command.append("t_repayment b on a.repayId=b.id LEFT JOIN t_borrow c on b.borrowId=c.id LEFT JOIN t_user d on c.publisher=d.id");
		command.append(" where a.invest_id="+iid+" and a.owner ="+userId+" AND a.isWebRepay=1 ORDER BY a.repayDate ");
        
		
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryBorrowRepaymentById
	 * @Param: MyHomeDao
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:51:39
	 * @Return:
	 * @Descb: 查询投资人回收中借款的还款详情
	 * @Throws:
	 */
	public List<Map<String, Object>> queryBorrowForpayByIdApp(Connection conn,
			long id, long userId,long iid) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.repayPeriod as  pre_trade_period ,DATE_FORMAT(a. repayDate,'%Y-%m-%d')  as  repayment_date,round(a.recivedPrincipal,2) AS  principal, " );
		command.append("round(a.recivedInterest,2) AS  interest, " );
		//command.append("round(a.recivedFI,2) AS  forFI ,round((a.recivedInterest -a.iManageFee+a.recivedFI ),2) AS earn  ," );
		command.append(" a.repayStatus AS pre_status from t_invest_repayment a LEFT JOIN " );
		command.append("t_repayment b on a.repayId=b.id LEFT JOIN t_borrow c on b.borrowId=c.id LEFT JOIN t_user d on c.publisher=d.id");
		command.append(" where a.invest_id="+iid+" and a.owner ="+userId+" AND a.isWebRepay=1 ORDER BY a.repayDate ");
        
		
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * @MethodName: queryInvestExperienceByid
	 * @Param: FinanceDao
	 * @Author: 李艳华
	 * @Date: 
	 * @Return:
	 * @Descb: 根据ID查询我的体验记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryMyExperienceByid(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_trial_ticket myExperienceRecord = new Dao().new Views().new v_t_trial_ticket();
		DataSet dataSet = myExperienceRecord.open(conn, "*", " userId=" + id , " useTime asc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	//根据ID查询我的体验记录总的投资金额和总的投资收益
	public Map<String, String> queryMyExperienceSumByid(Connection conn, long userId)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select sum(amount) as amount, sum(userHasRepaymentAmount) as userHasRepaymentAmount");
		command.append(" from v_t_trial_ticket where userId=" + userId + " and userInterestAmount > 0" );
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
}
	
	
	
	
	public List<Map<String, Object>> queryMyTicketByid(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_trial_ticket MyTicket = new Dao().new Views().new v_t_trial_ticket();
		DataSet dataSet = MyTicket.open(conn, "*", "userId=" + id," useTime asc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	//查询体验券号是否存在
	public Map<String, String> queryMyTicketByticketNo(Connection conn, String ticketNo)
			throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append(" select * from v_t_trial_ticket");
		command.append(" where ticketNo='" + ticketNo + "'");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	//update我的体验券信息
	public Long updateMyTicket(Connection conn,long userId,String ticketNo)
			throws SQLException, DataException {
		Dao.Tables.t_trial_ticket myTicket = new Dao().new Tables().new t_trial_ticket();
		myTicket.userId.setValue(userId);
		return myTicket.update(conn, " ticketNo = '" + ticketNo + "'");
	}
	
	/**   
	 * @MethodName: queryBorrowHaspayById  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:56:47
	 * @Return:    
	 * @Descb: 查询已回收的借款还款详情
	 * @Throws:
	*/
	public List<Map<String, Object>> queryBorrowHaspayById(Connection conn,
			long idLong, long userId,long iid) throws SQLException, DataException {
			StringBuffer command = new StringBuffer();

				//modify by 2013-06-06
				command.append("SELECT a.repayPeriod as  repayPeriod ,a.repayDate as  repayDate  ,");
				command.append("round(a.hasPrincipal,2) AS  forpayPrincipal , round(a.hasInterest,2) AS  forpayInterest , round(a.principalBalance,2) AS  principalBalance , " );
				command.append(" round(a.iManageFee,2) AS  manage , a.isLate as  isLate ,a.lateDay as  lateDay ,round(a.recivedFI,2) AS  forFI , " );
				command.append("round((a.hasInterest -a.iManageFee+a.recivedFI ),2) AS  earn ,d.username as  username ,a.isWebRepay as  isWebRepay  " );command.append("from t_invest_repayment a LEFT JOIN t_repayment b on a.repayId=b.id LEFT JOIN t_borrow c on b.borrowId=c.id LEFT JOIN t_user d " );
				command.append("on c.publisher=d.id  " );
				command.append("where a.invest_id="+iid+" and a. owner ="+userId+" and (a.repayStatus=2 OR a.isWebRepay=2) " );
				command.append("ORDER BY a.repayDate ");
				//----end
        DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		command = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}


	
	/**   
	 * @MethodName: queryAutomaticBid  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:20
	 * @Return:    
	 * @Descb: 查询用户的自动投标设置
	 * @Throws:
	*/
	public Map<String, String> queryAutomaticBid(Connection conn, Long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.usableSum,b.* FROM t_user a LEFT JOIN t_automaticbid b ON b.userId = a.id");
		command.append(" where a.id = "+id + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	//查询本月回收中的借款
	public Map<String, String> queryBorrowRecycleByCondition1(Connection conn, Long id,String starttime,String endtime) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT * FROM (SELECT COUNT(*) counts,a.investor,a.borrowId FROM t_invest a LEFT JOIN t_repayment b ON a.borrowId = b.borrowId LEFT JOIN t_borrow c ON b.borrowId = c.id");
		command.append(" LEFT JOIN t_user d ON a.investor = d.id WHERE b.repayStatus = 1 AND a.investor ="+id);
		command.append("  AND b.repayDate>='"+starttime+"'AND b.repayDate <='"+endtime+"' GROUP BY a.investor) t GROUP BY t.investor");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @MethodName: automaticBidSet  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:29:20
	 * @Return:    
	 * @Descb: 修改用户自动投标状态
	 * @Throws:
	*/
	public Long automaticBidSet(Connection conn, long status,long userId) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		if(status == 1){
			t_automaticbid.bidStatus.setValue(2);
			t_automaticbid.bidSetTime.setValue(new Date());
		}else{
			t_automaticbid.bidStatus.setValue(1);						
		}
		return t_automaticbid.update(conn, " userId = "+userId);
	}
	
	
	/**   
	 * @MethodName: queryAutomaticBid  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午05:06:47
	 * @Return:    
	 * @Descb: 查询用户是否已经创建自动投标内容
	 * @Throws:
	*/
	public Map<String,String> hasAutomaticBid(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append(" select id from t_automaticbid");
		command.append(" where userId = "+userId + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @throws SQLException 
	 * @MethodName: automaticBidAdd  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:57:42
	 * @Return:    
	 * @Descb: 添加自动投标内容
	 * @Throws:
	*/
	public Long automaticBidAdd(Connection conn, Double bidAmount,
			Double rateStart, Double rateEnd, Double deadlineStart,
			Double deadlineEnd, Double creditStart, Double creditEnd,
			Double remandAmount, Long id,String borrowWay) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidAmount.setValue(bidAmount);
		t_automaticbid.rateStart.setValue(rateStart);
		t_automaticbid.rateEnd.setValue(rateEnd);
		t_automaticbid.deadlineStart.setValue(deadlineStart);
		t_automaticbid.deadlineEnd.setValue(deadlineEnd);
		t_automaticbid.creditStart.setValue(creditStart);
		t_automaticbid.creditEnd.setValue(creditEnd);
		t_automaticbid.remandAmount.setValue(remandAmount);
		t_automaticbid.userId.setValue(id);
		t_automaticbid.borrowWay.setValue(borrowWay);
		return t_automaticbid.insert(conn);
	}
	
	/**   
	 * @throws SQLException 
	 * @MethodName: automaticBidUpdate  
	 * @Param: MyHomeDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:57:28
	 * @Return:    
	 * @Descb: 更新自动投标内容
	 * @Throws:
	*/
	public Long automaticBidUpdate(Connection conn, Double bidAmount,
			Double rateStart, Double rateEnd, Double deadlineStart,
			Double deadlineEnd, Double creditStart, Double creditEnd,
			Double remandAmount, Long id,String borrowWay) throws SQLException {
		Dao.Tables.t_automaticbid t_automaticbid = new Dao().new Tables().new t_automaticbid();
		t_automaticbid.bidAmount.setValue(bidAmount);
		t_automaticbid.rateStart.setValue(rateStart);
		t_automaticbid.rateEnd.setValue(rateEnd);
		t_automaticbid.deadlineStart.setValue(deadlineStart);
		t_automaticbid.deadlineEnd.setValue(deadlineEnd);
		t_automaticbid.creditStart.setValue(creditStart);
		t_automaticbid.creditEnd.setValue(creditEnd);
		t_automaticbid.remandAmount.setValue(remandAmount);
		t_automaticbid.borrowWay.setValue(borrowWay);
		return t_automaticbid.update(conn, " userId = "+id);
	}
	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryRepaymentByOwner  
	 * @Param: MyHomeDao  
	 * @Author: 
	 * @Date: 
	 * @Return:    
	 * @Descb: 查询最近还款日及金额
	 * @Throws:
	*/
	
	public Map<String, String> queryRepaymentByOwner(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append("SELECT DATE_FORMAT(tr.repayDate ,'%Y-%m-%d') AS minRepayDate,SUM(recivedPrincipal + recivedInterest) AS totalSum FROM t_invest_repayment tr ");
		command.append("WHERE tr.repayDate IN (SELECT MIN(repayDate) FROM t_invest_repayment ");
		command.append("WHERE owner = "+userId+" AND DATE_FORMAT(repayDate ,'%Y-%m-%d') >= DATE_FORMAT(NOW() ,'%Y-%m-%d') AND repayStatus = 1) ");
		command.append("AND tr.`owner` = "+userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	public Map<String, String> allMyInvest(Connection conn, long userId)
	throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT COUNT(1) investNo, ifnull(ROUND(SUM(a.investAmount),2),0)investAllSum FROM t_invest a WHERE a.investor=").append(userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
}
