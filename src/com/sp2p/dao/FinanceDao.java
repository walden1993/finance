package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import sun.util.logging.resources.logging;

import com.shove.data.DataException;
import com.shove.data.DataRow;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.security.Encrypt;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: FinanceDao.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:15:29
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 理财数据处理层
 */
public class FinanceDao {

	/**
	 * @MethodName: queryBorrowList
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-4 上午11:13:28
	 * @Return: Map<String, String>
	 * @Descb: 查询借款列表
	 * @Throws: SQLException, DataException
	 */
	public List<Map<String, Object>> queryBorrowList(Connection conn,
			int borrowMode, int limitStart, int limitCount)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_list borrowList = new Dao().new Views().new v_t_borrow_list();
		DataSet dataSet = borrowList.open(conn, "*", "", "", limitStart,
				limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: queryBorrowDetailById
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午06:04:15
	 * @Return:
	 * @Descb: 根据ID查询借款的详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetailById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_detail borrowDetail = new Dao().new Views().new v_t_borrow_detail();
		DataSet dataSet = borrowDetail.open(conn, " * ", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * @MethodName: queryBorrowDetailById
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-6 下午06:04:15
	 * @Return:
	 * @Descb: 根据ID查询借款的详情
	 * @Throws:
	 */
	public Map<String, String> queryBorrowDetailByIdApp(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_detail borrowDetail = new Dao().new Views().new v_t_borrow_detail();
		DataSet dataSet = borrowDetail.open(conn, "  DISTINCT id, id AS object_id, investAmount1 AS available_amount,schedules AS financing_progress, paymentMode AS repayment_method, deadline AS trade_period,borrowWay AS object_type,borrowStatus AS object_status,borrowAmount1 AS borrowing_balance,annualRate AS annual_interest_rate,borrowInfo as project_detail,auditOpinion as risk_information", " id=" + id, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
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
	public Map<String, String> queryUserInfoById(Connection conn, long id)
			throws SQLException, DataException {
		
		Dao.Views.v_t_borrow_user_info v_t_borrow_user_info = new Dao().new Views().new v_t_borrow_user_info();
		DataSet dataSet = v_t_borrow_user_info.open(conn, "id , userId ,f_formatting_username( username) as username , username as username_2 ,vipStatus ,rating , personalHead ,address , credit , creditrating , createTime ,DATE_FORMAT(lastDate,'%Y-%m-%d') as lastDate  , creditLimit ,  vip , age ,maritalStatus ,  workPro , workCity , companyLine , companyScale , job ,school ,highestEdu ,eduStartDay ,  hasHourse , hasCar ,  hasHousrseLoan , hasCarLoan , auditperson ,  auditwork ,nativePlaceCity,nativePlacePro,nativePlace,sex", " id=" + id,
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
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
	public Map<String, String> queryUserInfoByIdApp(Connection conn, long id)
			throws SQLException, DataException {
		
		Dao.Views.v_t_borrow_user_info v_t_borrow_user_info = new Dao().new Views().new v_t_borrow_user_info();
		DataSet dataSet = v_t_borrow_user_info.open(conn, "hasCarLoan AS has_car_loan,hasHousrseLoan as has_housrse_loan,hasCar as has_car,hasHourse as has_hourse,eduStartDay AS edu_start_day,highestEdu as highest_edu,school,job,companyScale as  company_scale,companyLine as company_line,sex,age,maritalStatus as marital_status,workCity as work_city", " id=" + id,
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * @MethodName: queryUserInfoById2
	 * @Param: FinanceDao
	 * @Author: L.X.Z
	 * @Date: 
	 * @Return:
	 * @Descb: 根据ID查询融资信息发布者信息
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoById2(Connection conn, long id)
			throws SQLException, DataException {
		
		Dao.Views.v_t_borrow_org_info v_t_borrow_org_info = new Dao().new Views().new v_t_borrow_org_info();
		DataSet dataSet = v_t_borrow_org_info.open(conn, " *,f_formatting_username( username) as username1,f_formatting_username( legalPerson) as legalPerson1,f_formatting_username( address) as address1,f_formatting_username( phone) as phone1 ,f_formatting_username( regNum) as regNum1 ", " id=" + id,
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * @MethodName: queryUserInfoById2
	 * @Param: FinanceDao
	 * @Author: L.Y.H
	 * @Date: 
	 * @Return:
	 * @Descb: 通过id查询融资用户
	 * @Throws:
	 */
	public Map<String, String> queryUserInfoById3(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_org_info v_t_borrow_org_info = new Dao().new Views().new v_t_borrow_org_info();
		DataSet dataSet = v_t_borrow_org_info.open(conn, "*", " id=" + id, " id desc ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
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
	public List<Map<String, Object>> queryUserIdentifiedByid(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materialsauth user_materialsauth = new Dao().new Views().new v_t_borrow_user_materialsauth();
		DataSet dataSet = user_materialsauth.open(conn, " * ,count(DISTINCT materAuthTypeId)  as testnum ", " auditStatus = 3 and id=" + id+" group by materAuthTypeId ", " ",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryUserIdentifiedByidApp(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materialsauth user_materialsauth = new Dao().new Views().new v_t_borrow_user_materialsauth();
		DataSet dataSet = user_materialsauth.open(conn, " auditStatus as audit_status,passTime as pass_time,name as audit_name", " auditStatus = 3 and id=" + id+" group by materAuthTypeId ", " ",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * @MethodName: queryUserIdentifiedByid2
	 * @Param: FinanceDao
	 * @Author: L.X.Z
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询企业用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByid2(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_org_materialsauth org_materialsauth = new Dao().new Views().new v_t_borrow_org_materialsauth();
		DataSet dataSet = org_materialsauth.open(conn, "   * ,count(DISTINCT materAuthTypeId)  as testnum  ", " auditStatus = 3 and id=" + id+" group by materAuthTypeId ", " ",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
	 * @MethodName: queryUserIdentifiedByid2
	 * @Param: FinanceDao
	 * @Author: L.X.Z
	 * @Date: 2013-3-6 下午08:00:04
	 * @Return:
	 * @Descb: 根据ID查询企业用户认证信息
	 * @Throws:
	 */
	public List<Map<String, Object>> queryUserIdentifiedByid2App(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_org_materialsauth org_materialsauth = new Dao().new Views().new v_t_borrow_org_materialsauth();
		DataSet dataSet = org_materialsauth.open(conn, "  auditStatus as audit_status,passTime as pass_time,name as audit_name  ", " auditStatus = 3 and id=" + id+" group by materAuthTypeId ", " ",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryRePayByid(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_repayment v_t_borrow_repayment = new Dao().new Views().new v_t_borrow_repayment();
		DataSet dataSet = v_t_borrow_repayment.open(conn, " * ", " borrowId=" + id, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryRePayByidApp(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_repayment v_t_borrow_repayment = new Dao().new Views().new v_t_borrow_repayment();
		DataSet dataSet = v_t_borrow_repayment.open(conn, " repayStatus AS status, stillPI AS to_repay,repayDate AS repayment_date,hasPI AS has_interest ", " borrowId=" + id, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryCollectionByid(Connection conn,
			long id) throws SQLException, DataException {
		Dao.Views.v_t_borrow_collection collection = new Dao().new Views().new v_t_borrow_collection();
		DataSet dataSet = collection.open(conn, " * ", " borrowId=" + id, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryInvestByid(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_investrecord investrecord = new Dao().new Views().new v_t_borrow_investrecord();
		DataSet dataSet = investrecord.open(conn, "  id ,borrowId , f_formatting_username(username) as username , investAmount , investTime , investor , creditedStatus  ", " borrowId=" + id,
				//" id desc", -1, -1);//modify by houli 按照时间的正序排
				" investTime desc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryInvestByidApp(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_investrecord investrecord = new Dao().new Views().new v_t_borrow_investrecord();
		DataSet dataSet = investrecord.open(conn, " f_formatting_username(username) as bidder, investAmount as bid_amount, investTime as bidding_time", " borrowId=" + id,
				//" id desc", -1, -1);//modify by houli 按照时间的正序排
				" investTime desc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	/**
     * @MethodName: queryInvestByidAndinvestor
     * @Param: FinanceDao
     * @Author: hejiahua
     * @Date: 2014-10-12 21:14:55
     * @Return:
     * @Descb: 根据ID和用户ID查询投资记录
     * @Throws:
     */
    public List<Map<String, Object>> queryInvestByidAndInvestor(Connection conn, long id,long investor)
            throws SQLException, DataException {
        Dao.Views.v_t_borrow_investrecord investrecord = new Dao().new Views().new v_t_borrow_investrecord();
        DataSet dataSet = investrecord.open(conn, "  id ,borrowId , f_formatting_username(username) as username , investAmount , investTime , investor , creditedStatus  ", " borrowId=" + id +" and investor="+investor,
                //" id desc", -1, -1);//modify by houli 按照时间的正序排
                " investTime asc", -1, -1);
        dataSet.tables.get(0).rows.genRowsMap();
        return dataSet.tables.get(0).rows.rowsMap;
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
	public List<Map<String, Object>> queryInvestExperienceByid(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_trial_ticket investExperienceRecord = new Dao().new Views().new v_t_trial_ticket();
		DataSet dataSet = investExperienceRecord.open(conn, "  id , userId , amount , useTime , username  ", " id=" + id +" and ticketStatus = 2 and batchStatus = 1",
				//" id desc", -1, -1);//modify by houli 按照时间的正序排
				" useTime asc", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 验证用户是否体验了此标
	 * queryInvestExperienceByUser(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @param conn
	 * @param id
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException 
	 * List<Map<String,Object>>
	 * @exception 
	 * @date:2014-8-28 下午7:03:59
	 * @since  1.0.0
	 */
	public List<Map<String, Object>> queryInvestExperienceByUser(Connection conn, long id,long userId)
            throws SQLException, DataException {
        Dao.Views.v_t_trial_ticket investExperienceRecord = new Dao().new Views().new v_t_trial_ticket();
        DataSet dataSet = investExperienceRecord.open(conn, "  id , userId , amount , useTime , username  ", " id=" + id +" and userId="+userId,
                //" id desc", -1, -1);//modify by houli 按照时间的正序排
                " useTime asc", -1, -1);
        dataSet.tables.get(0).rows.genRowsMap();
        return dataSet.tables.get(0).rows.rowsMap;
    }
	
	
	/**
	 * @MethodName: queryExperienceByid
	 * @Param: FinanceDao
	 * @Author: 李艳华
	 * @Date: 2014-8-22
	 * @Return:
	 * @Descb: 根据ID查询体验券记录
	 * @Throws:
	 */
	public List<Map<String, Object>> queryExperienceByid(Connection conn, long id,double startMoney,double endMoney)
			throws SQLException, DataException {
		Dao.Views.v_t_trial_ticket trial_ticket = new Dao().new Views().new v_t_trial_ticket();
		System.out.println(" userId="+id+" and batchStatus != 2 and ticketStatus = 3  and disableDate>= '"+DateUtil.dateToString(new Date()) +"'  and  amount>="+startMoney +" and  amount<="+(endMoney<=0?1000000000:endMoney)+" ");
		DataSet dataSet = trial_ticket.open(conn, " * ", " userId="+id+" and batchStatus != 2 and ticketStatus = 3  and disableDate>= '"+DateUtil.dateToString(new Date()) +"'  and  amount>="+startMoney +" and  amount<="+(endMoney<=0?"10000000000000":endMoney)+" ", "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * @MethodName: getInvestStatus
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午06:47:23
	 * @Return:
	 * @Descb: 获取借款投标的状态,条件是正在招标中
	 * @Throws:
	 */
	public Map<String, String> getInvestStatus(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow_invest = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow_invest.open(conn, " id,hasPWD ,nid_log,hasCirculationNumber,circulationNumber,smallestFlowUnit ",
				" borrowStatus =2 and id=" + id, " id desc", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryBorrowInvest
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-11 下午06:52:43
	 * @Return:
	 * @Descb: 根据ID获取借款投标中的借款内容
	 * @Throws:
	 */
	public Map<String, String> queryBorrowInvest(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_invest v_t_borrow_invest = new Dao().new Views().new v_t_borrow_invest();
		DataSet dataSet = v_t_borrow_invest.open(conn, " * ", " id=" + id, "",
				0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: queryUserMonney
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午08:47:41
	 * @Return:
	 * @Descb: 查询用户的金额
	 * @Throws:
	 */
	public Map<String, String> queryUserMonney(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet dataSet = t_user.open(conn,
				" (usableSum+freezeSum) AS totalSum,usableSum ", " id="
						+ userId, "", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: addBrowseCount
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:51:36
	 * @Return:
	 * @Descb: 更新浏览量
	 * @Throws:
	 */
	public long addBrowseCount(Connection conn, Long id) throws SQLException {
		long returnId = -1L;
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		StringBuffer condition = new StringBuffer();
		condition.append("UPDATE t_borrow  SET remainTimeStart = '"
				+ sf.format(new Date()));
		condition.append("' , visitors = visitors+1 WHERE  id =" + id);
		returnId = MySQL.executeNonQuery(conn, condition.toString());
		condition=null;
		return returnId;
	}

	/**
	 * @MethodName: valideInvest
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:06:58
	 * @Return:
	 * @Descb: 验证投资人是否符合本次投标
	 * @Throws:
	 */
	public String valideInvest(Connection conn, long id, long userId,
			double amount) throws SQLException, DataException {
		int returnId = 0;
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();

		DataSet investAmountDataSet = t_borrow.open(conn, " id ", " id=" + id
				+ " and (borrowAmount-hasInvestAmount) >=" + amount, "", 0, 1);
		returnId = investAmountDataSet.tables.get(0).rows.getCount();
		if (returnId == 0) {
			return "您的投标金额超过本轮剩余投标金额";
		} else {
			DataSet maxTenderedSumDataSet = t_borrow.open(conn,
					" maxTenderedSum ", " id=" + id, "", 0, 1);
			returnId = maxTenderedSumDataSet.tables.get(0).rows.getCount();
			DataRow dr = maxTenderedSumDataSet.tables.get(0).rows.get(0);
			BigDecimal maxTenderedSum = (BigDecimal) dr.get("maxTenderedSum");
			if (maxTenderedSum != null) {
				if (returnId == 0) {
					return "您的投标金额超过本轮最多投标金额";
				} else {
					DataSet usableSumDataSet = t_user.open(conn, " id ", " id="
							+ userId + " and usableSum >" + amount, "", 0, 1);
					returnId = usableSumDataSet.tables.get(0).rows.getCount();
					if (returnId == 0) {
						return "您的可用余额不够进行本轮投标";
					}
				}
			} else {
				DataSet usableSumDataSet = t_user.open(conn, " id ", " id="
						+ userId + " and usableSum >" + amount, "", 0, 1);
				returnId = usableSumDataSet.tables.get(0).rows.getCount();
				if (returnId == 0) {
					return "您的可用余额不够进行本轮投标";
				}
			}
		}
		return "";
	}

	/**
	 * @MethodName: valideTradePassWord
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午04:07:23
	 * @Return:
	 * @Descb: 验证交易密码
	 * @Throws:
	 */
	public String valideTradePassWord(Connection conn, long userId, String pwd)
			throws SQLException, DataException {
		String passWord = Encrypt.MD5(pwd.trim());
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		passWord=com.shove.web.Utility.filteSqlInfusion(passWord);
		DataSet dataSet = t_user.open(conn, " id ", " id=" + userId
				+ " and dealpwd ='" + StringEscapeUtils.escapeSql(passWord) + "'", "", 0, 1);
		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		if (map == null || map.size() == 0) {
			return "交易密码错误";
		}
		return "";
	}

	/**
	 * @MethodName: addBorrowInvest
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午05:46:28
	 * @Return:
	 * @Descb: 添加借款投资
	 * @Throws:
	 */
	public Long addBorrowInvest(Connection conn, long id, long userId,
			double borrowSum, double annualRate,double deadlineDouble) throws SQLException {
		long returnId = -1;
		// 添加投资记录
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(borrowSum);
		t_invest.realAmount.setValue(borrowSum);
		t_invest.monthRate.setValue(annualRate/12);
		t_invest.investor.setValue(userId);
		t_invest.oriInvestor.setValue(userId);
		t_invest.investTime.setValue(new Date());
		t_invest.borrowId.setValue(id);
		t_invest.deadline.setValue(deadlineDouble);
		returnId = t_invest.insert(conn);
		// 更新借款信息中的已投资总额和数量
		returnId = MySQL.executeNonQuery(conn,
				" update t_borrow set hasInvestAmount = hasInvestAmount+"
						+ borrowSum + ",investNum=investNum+1" + " where id = "
						+ id);
		// 更新投资人的资金信息
		returnId = MySQL.executeNonQuery(conn,
				" update t_user set usableSum = usableSum-" + borrowSum
						+ ",freezeSum=freezeSum+" + borrowSum + " where id = "
						+ userId);
		return returnId;
	}

	/**
	 * @MethodName: isFullSale
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-17 上午12:44:07
	 * @Return:
	 * @Descb: 判断是否符合满标的条件投资金额已经达到借款金额
	 * @Throws:
	 */
	public Map<String, String> isFullSale(Connection conn, long id)
			throws SQLException, DataException {
		StringBuffer condition = new StringBuffer();
		condition.append(" borrowStatus =" + IConstants.BORROW_STATUS_2);
		condition.append(" and borrowAmount = hasInvestAmount");
		condition.append(" and remainTimeStart < remainTimeEnd");
		condition.append(" and id=" + id);
		Dao.Tables.t_borrow t_borrow_invest = new Dao().new Tables().new t_borrow();
		DataSet dataSet = t_borrow_invest.open(conn, " id ", condition
				.toString(), " id desc", -1, -1);
		condition = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: updateBorrowFullSale
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-17 上午12:57:53
	 * @Return:
	 * @Descb: 更新借款的状态为满标
	 * @Throws:
	 */
	public long updateBorrowFullSale(Connection conn, long id,int sorts)
			throws SQLException, DataException {
		long returnId = -1L;
		StringBuffer condition = new StringBuffer();
		condition.append("update t_borrow set ");
		condition.append(" sort = "+sorts);
		condition.append(", borrowStatus =" + IConstants.BORROW_STATUS_3);
		condition.append(",remainTimeStart= remainTimeEnd");
		condition.append(" where id =" + id);
		returnId = MySQL.executeNonQuery(conn, condition.toString());
		condition = null;
		return returnId;
	}

	/**
	 * @MethodName: addBorrowMSG
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-13 上午08:15:42
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public Long addBorrowMSG(Connection conn, long id, long userId,
			String msgContent) throws SQLException {
		Dao.Tables.t_msgboard t_msgboard = new Dao().new Tables().new t_msgboard();
		t_msgboard.msgContent.setValue(msgContent);
		t_msgboard.modeId.setValue(id);
		// 借款留言类型
		t_msgboard.msgboardType.setValue(1);
		t_msgboard.msger.setValue(userId);
		t_msgboard.msgTime.setValue(new Date());
		return t_msgboard.insert(conn);
	}

	/**
	 * @MethodName: addFocusOn
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午08:58:05
	 * @Return:
	 * @Descb: 我的关注
	 * @Throws:
	 */
	public Long addFocusOn(Connection conn, long id, long userId, int moduleType)
			throws SQLException {
		Dao.Tables.t_concern t_concern = new Dao().new Tables().new t_concern();
		t_concern.moduleId.setValue(id);
		t_concern.userId.setValue(userId);
		t_concern.moduleType.setValue(moduleType);
		return t_concern.insert(conn);
	}

	/**
	 * @MethodName: hasFocusOn
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午11:02:03
	 * @Return:
	 * @Descb: 查询用户是否已经有关注
	 * @Throws:
	 */
	public Map<String, String> hasFocusOn(Connection conn, long id,
			long userId, int moduleType) throws SQLException, DataException {
		String condition = "moduleId = " + id + " and userId = " + userId
				+ " and moduleType=" + moduleType;
		Dao.Tables.t_concern t_concern = new Dao().new Tables().new t_concern();
		DataSet dataSet = t_concern.open(conn, " id ", condition, "", 0, 1);
		condition =  null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * @MethodName: addUserMail
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午09:59:59
	 * @Return:
	 * @Descb: 添加用户站内信
	 * @Throws:
	 */
	public Long addUserMail(Connection conn, long reciver, Long userId,
			String title, String content, int mailType) throws SQLException {
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		t_mail.reciver.setValue(reciver);
		t_mail.sender.setValue(userId);
		t_mail.mailTitle.setValue(title);
		t_mail.mailContent.setValue(content);
		t_mail.mailType.setValue(mailType);
		t_mail.mailMode.setValue(1);
		t_mail.sendTime.setValue(new Date());
		return t_mail.insert(conn);
	}

	/**
	 * @MethodName: addUserReport
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:13:31
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public Long addUserReport(Connection conn, long reporter, Long userId,
			String title, String content) throws SQLException {
		Dao.Tables.t_report t_report = new Dao().new Tables().new t_report();
		t_report.reporter.setValue(reporter);
		t_report.user.setValue(userId);
		t_report.reportTitle.setValue(title);
		t_report.reportContent.setValue(content);
		t_report.reportTime.setValue(new Date());
		return t_report.insert(conn);
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryLastBorrow
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:25:24
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public List<Map<String, Object>> queryLastestBorrow(Connection conn)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_index v_t_borrow_index = new Dao().new Views().new v_t_borrow_index();
		DataSet dataSet = v_t_borrow_index.open(conn, " * ", " sorts != 0 and borrowStatus in (2,3,4,5) ", " sorts desc , schedules asc  ", 0,
				12);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryLastBorrow
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:25:24
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public List<Map<String, Object>> queryExperienceBorrow(Connection conn,long userId)
			throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materauth_img user_materialsauth_img = new Dao().new Views().new v_t_borrow_user_materauth_img();
		DataSet dataSet = user_materialsauth_img.open(conn, " * ", " userId="+userId, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: queryLastestExperience
	 * @Param: FinanceDao
	 * @Author: 李艳华
	 * @Date: 2014-8-21 上午10:52:00
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public Map<String, String> queryLastestExperience(Connection conn)
			throws SQLException, DataException {
//		StringBuffer command = new StringBuffer();
//		command.append("select * from v_t_trial_borrow order by id limit 1");
//		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		
		Dao.Views.v_t_trial_borrow view = new Dao().new Views().new v_t_trial_borrow();
		DataSet dataSet = view.open(conn, " * ", "" , "   id desc  ", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	public Map<String, String> queryExperienceDetail(Connection conn, int borrowid)
			throws SQLException, DataException {
		Dao.Views.v_t_trial_borrow view = new Dao().new Views().new v_t_trial_borrow();
		DataSet dataSet = view.open(conn, " * ", " id="+borrowid, "",
				-1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: investRank  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:10:04
	 * @Return:    
	 * @Descb: 投资排名前8条记录
	 * @Throws:
	*/
	public List<Map<String, Object>> investRank(Connection conn,String starTime,String endTime)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUM(t.borrowSum) AS borrowSum,f_formatting_username(b.username) as username,f_rating(b.rating) AS rating FROM(");
		sql.append(" SELECT SUM(realAmount) AS borrowSum,investor,investTime FROM t_invest ");
		starTime=com.shove.web.Utility.filteSqlInfusion(starTime);
		endTime=com.shove.web.Utility.filteSqlInfusion(endTime);
		if (StringUtils.isNotBlank(starTime)) {
			sql.append(" where investTime >= '"+StringEscapeUtils.escapeSql(starTime)+"'") ;
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append("  and investTime <='"+StringEscapeUtils.escapeSql(endTime)+"'") ;
		}
		sql.append(" GROUP BY investor");
		sql.append(" UNION ALL SELECT SUM(realAmount) AS borrowSum,investor,investTime FROM t_invest_history ");
		if (StringUtils.isNotBlank(starTime)) {
			sql.append(" where investTime >= '"+StringEscapeUtils.escapeSql(starTime)+"'") ;
		}
		if (StringUtils.isNotBlank(endTime)) {
			sql.append("  and investTime <='"+StringEscapeUtils.escapeSql(endTime)+"'") ;
		}
		sql.append("GROUP BY investor");
		sql.append(" )t LEFT JOIN t_user b ON t.investor = b.id GROUP BY t.investor ORDER BY borrowSum DESC LIMIT 0,8");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**   
	 * @MethodName: queryTotalRisk  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:33:42
	 * @Return:    
	 * @Descb: 查询风险保障金总额
	 * @Throws:
	*/
	public Map<String,String> queryTotalRisk(Connection conn) throws SQLException, DataException{
		DataSet dataSet = MySQL.executeQuery(conn, "SELECT ((SUM(riskInCome)-SUM(riskSpending))) AS total FROM t_risk_detail limit 0,1");
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @MethodName: queryCurrentRisk  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:33:46
	 * @Return:    
	 * @Descb: 查询当日风险保障金收支金额
	 * @Throws:
	*/
	public Map<String,String> queryCurrentRisk(Connection conn) throws SQLException, DataException{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(new Date());
		Dao.Tables.t_risk_detail t_risk_detail = new Dao().new Tables().new t_risk_detail();
		DataSet dataSet = t_risk_detail.open(conn, " sum(riskInCome) as riskInCome,sum(riskSpending) as riskSpending ", " riskDate= '" +date+"'",
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	

	
	/**   
	 * @MethodName: getInvestPWD  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:38:27
	 * @Return:    
	 * @Descb: 获取投标密码是否正确
	 * @Throws:
	*/
	public Map<String, String> getInvestPWD(Connection conn,long idLong, String investPWD) throws SQLException, DataException {
		Dao.Tables.t_borrow t_borrow = new Dao().new Tables().new t_borrow();
		investPWD=com.shove.web.Utility.filteSqlInfusion(investPWD);
		System.out.println("-------------------id:"+idLong+"------------investPWD:"+investPWD);
		DataSet dataSet = t_borrow.open(conn, " id ", " id="+idLong+" and investPWD='"+StringEscapeUtils.escapeSql(investPWD.trim())+"'",
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @MethodName: getInvestPWD  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:38:27
	 * @Return:    
	 * @Descb: 获取交易密码是否正确
	 * @Throws:
	*/
	public Map<String, String> getDealPWD(Connection conn,long idLong, String dealPWD) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		dealPWD=com.shove.web.Utility.filteSqlInfusion(dealPWD);
		DataSet dataSet = t_user.open(conn, " id ", " id="+idLong+" and dealpwd='"+StringEscapeUtils.escapeSql(dealPWD.trim())+"'",
				"", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查找投资人信息
	 * add by houli
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryInvestorById(Connection conn,long investorId,int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		DataSet dataSet = t_invest.open(conn, " investor ", 
				" investor="+investorId,"",0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: queryInvestIdByFlag  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午05:38:44
	 * @Return:    
	 * @Descb: 查询投资的id
	 * @Throws:
	*/
	public Map<String, String> queryInvestIdByFlag(Connection conn, String flag) throws SQLException, DataException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		DataSet dataSet = t_invest.open(conn, " id ", 
				" flag="+flag,"",0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryUserImageByid  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:21:25
	 * @Return:    
	 * @Descb: 查询用户认证通过的图片
	 * @Throws:
	*/
	public List<Map<String, Object>> queryUserImageByid(Connection conn,
			long typeId, long userId) throws SQLException, DataException {
		Dao.Views.v_t_borrow_user_materauth_img user_materialsauth_img = new Dao().new Views().new v_t_borrow_user_materauth_img();
		DataSet dataSet = user_materialsauth_img.open(conn, " * ", " materAuthTypeId=" + typeId+" and userId="+userId, "",
				-1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**   
	 * @MethodName: queBorrowInfo  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午01:56:10
	 * @Return:    
	 * @Descb: 查询借款信息
	 * @Throws:
	*/
	public Map<String, String> queBorrowInfo(Connection conn, long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append(" select a.borrowWay as borrowWay,a.auditTime as auditTime, a.excitationType as excitationType,a.excitationSum as excitationSum,a.circulationNumber as circulationNumber, a.version,a.annualRate,(a.annualRate/12) monthRate");
		command.append(",a.borrowAmount,a.deadline,a.borrowTitle,a.publisher,a.isDayThe,b.username as borrowerName from");
		command.append(" t_borrow a left join t_user b on a.publisher=b.id where a.id="+ id);
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(ds);
	}
	

	/**   
	 * @MethodName: addInvest  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午02:04:59
	 * @Return:    
	 * @Descb: 添加投资记录
	 * @Throws:
	*/
	public long addInvest(Connection conn, double investAmount,
			double realAmount, double monthRate, long investor,long oriInvestor, long id, int deadline,int isAutoBid) throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(investAmount);
		t_invest.realAmount.setValue(realAmount);
		t_invest.monthRate.setValue(monthRate);
		t_invest.investor.setValue(investor);
		t_invest.oriInvestor.setValue(oriInvestor);
		t_invest.borrowId.setValue(id);
		t_invest.deadline.setValue(deadline);
		t_invest.investTime.setValue(new Date());
		t_invest.isAutoBid.setValue(isAutoBid);
		return t_invest.insert(conn);
	}
	
	/**
	 * @MethodName: updateBorrowStatus
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午04:47:06
	 * @Return:
	 * @Descb: 更新借款状态
	 * @Throws:
	 */
	public long updateBorrowStatus(Connection conn, double investAmount,
			 long copies,long id,int version) throws SQLException {
		long returnId = -1;
		StringBuffer command = new StringBuffer();
		command.append("UPDATE t_borrow SET hasInvestAmount = hasInvestAmount+ "+investAmount);
		command.append(",hasCirculationNumber=hasCirculationNumber+"+copies);
		command.append(",investNum=investNum+1,version=version+1 WHERE id =" + id);
		command.append(" and hasInvestAmount <borrowAmount and version="+version);
		returnId = MySQL.executeNonQuery(conn, command.toString());
		return returnId;
	}
	
	

	/**   
	 * @MethodName: freezeUserAmount  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午04:48:11
	 * @Return:    
	 * @Descb: 投资人投资成功资金冻结
	 * @Throws:
	*/
	public long freezeUserAmount(Connection conn, double investAmount,
			long userId) throws SQLException {
		long returnId = -1;
		StringBuffer command = new StringBuffer();
		command.append("UPDATE t_user SET usableSum = usableSum-"+investAmount+", freezeSum=freezeSum+"+investAmount+" WHERE id ="+userId);
		returnId = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		return returnId;
	}
	
	
	/**   
	 * @MethodName: updateFullBorrow  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午04:51:11
	 * @Return:    
	 * @Descb: 更新满标的借款
	 * @Throws:
	*/
	public long updateFullBorrow(Connection conn,long id) throws SQLException{
		long returnId = -1;
		StringBuffer command = new StringBuffer();
		command.append("update t_borrow set borrowStatus =3,sort = 5,remainTimeStart= remainTimeEnd where borrowAmount=hasInvestAmount AND borrowStatus = 2 AND id = "+id);
		returnId = MySQL.executeNonQuery(conn, command.toString());
		command = null; 
		return returnId;
	}

	
	/**   
	 * @MethodName: queryUserAmountAfterHander  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午05:06:05
	 * @Return:    
	 * @Descb: 查询用户操作后的资金记录
	 * @Throws:
	*/
	public Map<String, String> queryUserAmountAfterHander(Connection conn,
			long userId) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select ifnull(a.usableSum,0) usableSum,ifnull(a.freezeSum,0) freezeSum,ifnull(sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest),0.0) forPI ,a.lastIP as lastIP ");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id="+userId+" group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询待还金额
	 */
	public Map<String,String> queryUserRepayMount(Connection conn,long userId) throws SQLException, DataException{
		StringBuffer command = new StringBuffer();
		command.append("select ifnull(sum((a.stillPrincipal+a.stillInterest-a.hasPI+a.lateFI-a.hasFI)),0) as forpaySum ");
		command.append("from t_repayment a left join t_borrow b on a.borrowId = b.id where a.repayStatus = 1 and b.publisher= "+userId+" GROUP BY b.publisher");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**   
	 * @MethodName: addUserDynamic  
	 * @Param: BorrowDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-21 上午10:28:50
	 * @Return:    
	 * @Descb: 添加用户动态
	 * @Throws:
	*/
	public long addUserDynamic(Connection conn, long userId, String url) throws SQLException {
		Dao.Tables.t_user_recorelist t_user_recorelist = new Dao().new Tables().new t_user_recorelist();
		t_user_recorelist.userId.setValue(userId);
		t_user_recorelist.url.setValue(url);
		t_user_recorelist.time.setValue(new Date());
		return t_user_recorelist.insert(conn);
	}

	/**   
	 * @MethodName: updateFullScall  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:01:50
	 * @Return:    
	 * @Descb: 更新满标的状态
	 * @Throws:
	*/
	public void updateFullScall(Connection conn, long id) throws SQLException {
        StringBuffer command = new StringBuffer();
        command.append("update t_borrow set borrowStatus =3,remainTimeStart= remainTimeEnd where borrowAmount=hasInvestAmount AND borrowStatus = 2 and id ="+id);
        MySQL.executeNonQuery(conn, command.toString());
        command = null;
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryBorrowTenderIn  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:03:40
	 * @Return:    
	 * @Descb: 查询招标中的借款
	 * @Throws:
	*/
	public Map<String, String> queryBorrowTenderIn(Connection conn, long id) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id,version,smallestFlowUnit,(circulationNumber-hasCirculationNumber) as remainCirculationNumber FROM t_borrow WHERE "
				+ "borrowAmount>hasInvestAmount AND borrowStatus = 2 AND id ="
				+ id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryInvestAmount  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:06:31
	 * @Return:    
	 * @Descb: 本轮剩余投标金额
	 * @Throws:
	*/
	public Map<String, String> queryInvestAmount(Connection conn, long id,double investAmount) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE (borrowAmount-hasInvestAmount) >="+investAmount+"  AND id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryMinTenderedSum  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:19:15
	 * @Return:    
	 * @Descb: 查询最小投标金额
	 * @Throws:
	*/
	public Map<String, String> queryMinTenderedSum(Connection conn, long id,
			double investAmount) throws SQLException, DataException {  
	    StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE minTenderedSum > (borrowAmount-hasInvestAmount) and id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryUserUsableSum  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:19:25
	 * @Return:    
	 * @Descb: 查询用户可用金额
	 * @Throws:
	*/
	public Map<String, String> queryUserUsableSum(Connection conn, long userId,double investAmount) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id,usableSum  FROM t_user WHERE usableSum < "+investAmount+" and id ="+userId);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryMaxTenderedSum  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:24:07
	 * @Return:    
	 * @Descb: 查询最大投标金额
	 * @Throws:
	*/
	public Map<String, String> queryMaxTenderedSum(Connection conn, long id,
			double investAmount) throws DataException, SQLException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE maxTenderedSum < "+investAmount+" and maxTenderedSum is not null and id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: queryMinTenderedSumMaps  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-25 上午10:26:49
	 * @Return:    
	 * @Descb: 查询本轮最小投标金额
	 * @Throws:
	*/
	public Map<String, String> queryMinTenderedSumMaps(Connection conn,
			long id, double investAmount) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT id FROM t_borrow WHERE minTenderedSum > "+investAmount+" and minTenderedSum is not null and id ="+id);
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: addAutoBidRecord  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-27 上午11:47:20
	 * @Return:    
	 * @Descb: 添加自动投标用户投标记录
	 * @Throws:
	*/
	public long addAutoBidRecord(Connection conn, long userId, int borrowId) throws SQLException {
		Dao.Tables.t_automaticbid_user t_automaticbid_user = new Dao().new Tables().new t_automaticbid_user();
		t_automaticbid_user.userId.setValue(userId);
		t_automaticbid_user.borrowId.setValue(borrowId);
		return t_automaticbid_user.insert(conn);
	}

	/**   
	 * @MethodName: queryHasInvest  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-14 上午10:54:46
	 * @Return:    
	 * @Descb: 查询已投资总额是否小于等于借款总额
	 * @Throws:
	*/
	public Map<String, String> queryHasInvest(Connection conn, long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("select id from t_borrow where hasInvestAmount <=borrowAmount and id ="+id);
		DataSet dataSet = Database.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**   
	 * @MethodName: reBackBorrowStatus  
	 * @Param: FinanceDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-14 下午04:56:11
	 * @Return:    
	 * @Descb: 回退借款信息中的已投资总额和数量
	 * @Throws:
	*/
	public long reBackBorrowStatus(Connection conn, double bidAmount,
			int borrowId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("UPDATE t_borrow SET hasInvestAmount = hasInvestAmount- "+bidAmount+",investNum=investNum-1 WHERE id ="+borrowId);
		long result= Database.executeNonQuery(conn, command.toString());
		command = null;
		return result;
	}
	
	/**
	 * @MethodName: updateRepo
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午09:05:09
	 * @Return:
	 * @Descb: 更新回购中的状态
	 * @Throws:
	 */
	public void updateRepo(Connection conn, Long id) throws SQLException {
		String command = "update t_borrow set borrowStatus =4 where borrowAmount=hasInvestAmount AND borrowStatus = 2 and id ="
						+ id;
		MySQL.executeNonQuery(conn, command.toString());
		command = null;
	}

	public long addCirculatioinInvest(Connection conn, double investAmount,
			double realAmount, double monthRate, long investor,
			long oriInvestor, long id, int deadline,
			int circulationForpayStatus, double circulationInterest,Date repayDate,
			double excutation,String reason,int isAutoBid)
			throws SQLException {
		Dao.Tables.t_invest t_invest = new Dao().new Tables().new t_invest();
		t_invest.investAmount.setValue(investAmount);
		t_invest.realAmount.setValue(realAmount);
		t_invest.monthRate.setValue(monthRate);
		t_invest.investor.setValue(investor);
		t_invest.oriInvestor.setValue(oriInvestor);
		t_invest.borrowId.setValue(id);
		t_invest.deadline.setValue(deadline);
		t_invest.investTime.setValue(new Date());
		if(repayDate !=null){
			t_invest.repayDate.setValue(repayDate);			
		}
		t_invest.circulationForpayStatus.setValue(circulationForpayStatus);
		t_invest.circulationInterest.setValue(circulationInterest);
		t_invest.recivedPrincipal.setValue(investAmount);
		t_invest.recievedInterest.setValue(circulationInterest);
		t_invest.reward.setValue(excutation);
		t_invest.reason.setValue(reason);
		t_invest.isAutoBid.setValue(isAutoBid);
		
		return t_invest.insert(conn);
	}
	/**
	 * @throws SQLException
	 * @MethodName: addUserAmount
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午11:04:23
	 * @Return:
	 * @Descb: 添加用户金额
	 * @Throws:
	 */
	public long addUserAmount(Connection conn, double investAmount, long userId)
			throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum =usableSum+" + investAmount
				+ " where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}
	/**
	 * @MethodName: deductedUserAmount
	 * @Param: FinanceDao
	 * @Author: gang.lv
	 * @Date: 2013-5-21 上午11:04:14
	 * @Return:
	 * @Descb: 扣除用户金额
	 * @Throws:
	 */
	public long deductedUserAmount(Connection conn, double investAmount,
			long userId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum =usableSum-" + investAmount
				+ " where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}
		
	public Map<String, String> queryNewBorrow(Connection conn) throws DataException{
		String command = "SELECT *, `f_formatAmount`((`a`.`borrowAmount` - `a`.`hasInvestAmount`))  AS `remainAmount`, `f_injectPoint`(((`a`.`hasInvestAmount` / `a`.`borrowAmount`) * 100))  AS `schedules` FROM t_borrow a WHERE a.`id` = (SELECT MAX(id) FROM t_borrow t WHERE t.`isNew` = 1) ;";
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String, String> queryTmail(Connection conn,long userId) throws DataException{
		String command = "SELECT COUNT(id) as cou FROM t_mail t WHERE t.`mailStatus` = 1 AND t.`reciver` = "+userId;
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
}
