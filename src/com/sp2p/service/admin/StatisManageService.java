package com.sp2p.service.admin;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.allinpay.ets.client.StringUtil;
import com.mysql.jdbc.Util;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.base.CommonService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.admin.StatisManageDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.util.DateUtil;

/**
 * @ClassName: AfterCreditManageService.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:18:35
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 统计管理业务处理层
 */
public class StatisManageService extends CommonService {

	public static Log log = LogFactory.getLog(StatisManageService.class);

	private StatisManageDao statisManageDao;

	public StatisManageDao getStatisManageDao() {
		return statisManageDao;
	}

	public void setStatisManageDao(StatisManageDao statisManageDao) {
		this.statisManageDao = statisManageDao;
	}

	/**
	 * @throws DataException
	 * @MethodName: queryLoginStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午09:49:43
	 * @Return:
	 * @Descb: 查询登录统计
	 * @Throws:
	 */
	public void queryLoginStatisByCondition(String userName, String realName,
			String timeStart, String timeEnd,int countEnd,String ltimeStart,String ltimeEnd,String mobilePhone, int countInt,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realname  like '%"
					+ StringEscapeUtils.escapeSql(realName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and lastDate >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and lastDate <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		boolean logFlag = false;//是按 登陆时间 统计
		boolean sFlag = false;
		boolean eFlag = false;
		if (StringUtils.isNotBlank(ltimeStart)) {
//			condition.append(" and lastDate >= '"
//					+ StringEscapeUtils.escapeSql(ltimeStart) + "'");
			logFlag = true;
			sFlag = true;
		}
		if (StringUtils.isNotBlank(ltimeEnd)) {
//			condition.append(" and lastDate <= '"
//					+ StringEscapeUtils.escapeSql(ltimeEnd) + "'");
			logFlag = true;
			eFlag = true;
		}
		if (StringUtils.isNotBlank(mobilePhone)){
			condition.append(" and mobilePhone = '"
					+ StringEscapeUtils.escapeSql(mobilePhone) + "'");
		}
		if (countInt != -1) {
			condition.append(" and loginCount > " + countInt);
		}
		if (countEnd != -1) {
			condition.append(" and loginCount < " + countEnd);
		}
		if (logFlag){
			condition.append(" AND EXISTS (SELECT operation_user FROM t_operation_log  WHERE operation_user = username ");
			if (sFlag){
				condition.append(" AND operation_time > '")
				.append(ltimeStart).append("'");
			}
			if(eFlag){
				condition.append(" AND operation_time < '")
				.append(ltimeEnd).append("'");
			}
			
			condition.append(" AND operation_remarks='用户登录')");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_login_statis", resultFeilds, " ",
					condition.toString());
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
	 * @MethodName: queryInvestStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午09:33:22
	 * @Return:
	 * @Descb: 查询投资统计列表
	 * @Throws:
	 */
	public PageBean queryInvestStatisByCondition(Map pmap,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		
		String bTitle = (String) pmap.get("bTitle");
		String investor = (String) pmap.get("investor");// =(String) pmap.get("");
		String timeStart=(String) pmap.get("timeStart");
		String timeEnd=(String) pmap.get("timeEnd");
		int borrowWayInt=(Integer) pmap.get("borrowWayInt");
		int isAutoBidInt =(Integer) pmap.get("isAutoBidInt");
		String borrowStatus=(String) pmap.get("borrowStatus");
		int groupInt=(Integer) pmap.get("groupInt");
		String recommendName=(String) pmap.get("recommendName");
		String deadlineType=(String) pmap.get("deadlineType");
		String fromLine=(String) pmap.get("fromLine");
		String toLine=(String) pmap.get("toLine");
		String realName=(String) pmap.get("realName");
		
		
		//新增合计
		PageBean<Map<String, Object>> pageBean1 = new PageBean<Map<String,Object>>();
		bTitle = Utility.filteSqlInfusion(bTitle);
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		borrowStatus = Utility.filteSqlInfusion(borrowStatus);
		Connection conn = MySQL.getConnection();
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(bTitle)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(bTitle.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			timeEnd += " 23:59:59";
			condition.append(" and investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (borrowWayInt != -1) {
			condition.append(" and borrowWay = " + borrowWayInt);
		}
		if (isAutoBidInt != -1) {
			condition.append(" and isAutoBid = " + isAutoBidInt);
		}
		if (StringUtils.isNotBlank(borrowStatus) && !"-1".equals(borrowStatus)) {
			condition.append(" and borrowStatus in "
					+ StringEscapeUtils.escapeSql(borrowStatus));
		}
		if (groupInt != -1) {
			condition.append(" and groupid = " + groupInt);
		}
		if (StringUtils.isNotEmpty(recommendName)){//如果推荐人不为空 
			DataSet ds =  Database.executeQuery(conn, "SELECT id FROM t_user a WHERE a.username ='"+recommendName.trim()+"'");
			Map umap = BeanMapUtils.dataSetToMap(ds);
			if (null != umap && null != umap.get("id")){
				String redId = (String) umap.get("id");
				condition.append(" and recommendUserId = " + redId);
			}
		}
		
		if (StringUtils.isNotEmpty(deadlineType)){//期限单位:天，月 isDayThe
			condition.append(" and isDayThe = " + (deadlineType.equals("2")?"'日'":"'月'"));
		}
		if (StringUtils.isNotEmpty(fromLine)){//借款期限
			condition.append(" and deadline >= " + fromLine);
		}
		if (StringUtils.isNotEmpty(toLine)){//借款期限
			condition.append(" and deadline <= " + toLine);
		}
		if (StringUtils.isNotEmpty(realName)){//借款期限
			condition.append(" and realName = '" + realName+"' or organization_name='"+ realName+"'");
		}
		
		
		try {
			dataPage(conn, pageBean, " v_t_invest_statis ", resultFeilds, " ",
					condition.toString());

			
			dataPage(conn, pageBean1, " v_t_invest_statis ", " sum(realAmount) sumAmount ", " ",
					condition.toString());
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		return pageBean1;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryInvestStatisRankByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午10:35:39
	 * @Return:
	 * @Descb: 查询投资排名列表
	 * @Throws:
	 */
	public void queryInvestStatisRankByCondition(Map map,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		String investor = (String) map.get("");
		String timeStart=(String) map.get(""); 
		String timeEnd=(String) map.get(""); 
		String deadlines=(String) map.get("deadlines"); 
		String deadlinee=(String) map.get("deadlinee"); 
//		int groupInt=(String) map.get("");
//		investor = Utility.filteSqlInfusion(investor);
//		timeStart = Utility.filteSqlInfusion(timeStart);
//		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
//		String resultFeilds = " a.*,b.realSum ";
//		StringBuffer tables = new StringBuffer("t_invest");
//		tables
//				.append(" v_t_invest_rank a left join (select investor,sum(realAmount) realSum from v_t_invest_rank where 1=1 ");
//		if (StringUtils.isNotBlank(investor)) {
//			condition.append(" and a.investor like '%"
//					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
//			tables.append(" and investor like '%"
//					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
//		}
//		if (StringUtils.isNotBlank(timeStart)) {
//			condition.append(" and a.investTime >= '"
//					+ StringEscapeUtils.escapeSql(timeStart) + "'");
//			tables.append(" and investTime >= '"
//					+ StringEscapeUtils.escapeSql(timeStart) + "'");
//		}
//		if (StringUtils.isNotBlank(timeEnd)) {
//			condition.append(" and a.investTime <= '"
//					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
//			tables.append(" and investTime <= '"
//					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
//		}
//		if (groupInt != -1) {
//			condition.append(" and a.groupId = " + groupInt);
//			tables.append(" and groupId = " + groupInt);
//		}
//		tables.append(" group by investor) b on a.investor = b.investor ");
		
//		log.info("-----sql:" + tables.toString());//TODO  querydepartmentFund
		/*SELECT SUM(a.investAmount)investSum, a.investor FROM t_invest a 
		WHERE 
		a.investTime > '2015-03-01' AND 
		a.deadline = 1
		GROUP BY a.investor
		*/
//		sql.append("SELECT SUM(a.investAmount)investSum, a.investor FROM t_invest a WHERE 1=1 ");
//		if (StringUtils.isNotBlank(timeStart)) {
//			sql.append(" and investTime >= '").append(timeStart).append("'");
//		}
//		if (StringUtils.isNotBlank(timeEnd)) {
//			sql.append(" and investTime <= '").append(timeEnd).append("'");
//		}
//		if (StringUtils.isNotBlank(deadlines)) {
//			sql.append(" and deadline >= ").append(deadlines);
//		}
//		if (StringUtils.isNotBlank(deadlinee)) {
//			sql.append(" and deadline <= ").append(deadlinee);
//		}
//		sql.append(" GROUP BY investor LIMIT 0,10");
//		
//		Connection conn = MySQL.getConnection();
		try {
//			dataPage(conn, pageBean, "t_invest", " * ",
//					" order by realAmount desc ", sql
//							.toString());
			
//			DataSet ds =  Database.executeQuery(conn, sql.toString());
//            ds.tables.get(0).rows.genRowsMap();
//            List list = (List<Map<String, Object>>) ds.tables.get(0).rows.rowsMap;
//            pageBean.setPage(list);
			
			
			String sqlId = "advert.queryInvestRank";
			commonQuery(pageBean, map,sqlId);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
//			conn.close();
		}
	}

	/**
	 * @MethodName: queryBorrowStatisByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午10:23:49
	 * @Return:
	 * @Descb: 借款管理费统计
	 * @Throws:
	 */
	public void queryBorrowStatisByCondition(String borrowTitle,
			String borrower, String timeStart, String timeEnd,
			int borrowWayInt, PageBean<Map<String, Object>> pageBean)
			throws Exception {
		borrowTitle = Utility.filteSqlInfusion(borrowTitle);
		borrower = Utility.filteSqlInfusion(borrower);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(borrowTitle)) {
			condition.append(" and  borrowTitle  like '%"
					+ StringEscapeUtils.escapeSql(borrowTitle.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(borrower)) {
			condition.append(" and borrower  like '%"
					+ StringEscapeUtils.escapeSql(borrower.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and auditTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and auditTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (borrowWayInt != -1) {
			condition.append(" and borrowWay = " + borrowWayInt);
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " v_t_borrow_statis ", resultFeilds, " ",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * @MethodName: queryBorrowStatisAmount
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 上午11:00:40
	 * @Return:
	 * @Descb: 查询借款统计总计
	 * @Throws:
	 */
	public Map<String, String> queryBorrowStatisAmount(String borrowTitle,
			String borrower, String timeStart, String timeEnd, int borrowWayInt)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = statisManageDao.queryBorrowStatisAmount(conn, borrowTitle,
					borrower, timeStart, timeEnd, borrowWayInt);
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
	 * @MethodName: queryborrowStatisInterestByCondition
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:16:20
	 * @Return:
	 * @Descb: 投标借款管理费统计
	 * @Throws:
	 */
	public void queryborrowStatisInterestByCondition(String investor,
			String timeStart, String timeEnd,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		// String resultFeilds =
		// " a.id,a.investor,a.realName,round(b.manageFI,2) as manageFI,round(b.hasPI,2) as hasPI,round(b.manageFee,2) as manageFee, round(b.hasInterest,2) as hasInterest, round(b.forInterest,2) as forInterest,round(b.forPI,2) as forPI";
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables.append(" v_t_new_invest_statis a ");
		// tables
		// .append(" v_t_invest_interest_statis a left join (select investor,sum(manageFI) manageFI,sum(hasPI) hasPI,sum(manageFee ) manageFee ,");
		// tables
		// .append("sum(hasInterest) hasInterest,sum(forInterest) forInterest,sum(forPI) forPI   from v_t_invest_interest_statis where 1=1 ");
		if (StringUtils.isNotBlank(investor)) {
			condition.append(" and a.investor like '%"
					+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
			// tables.append(" and investor like '%"
			// + StringEscapeUtils.escapeSql(investor.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			condition.append(" and a.investTime >= '"
					+ StringEscapeUtils.escapeSql(timeStart) + "'");
			// tables.append(" and investTime >= '"
			// + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			condition.append(" and a.investTime <= '"
					+ StringEscapeUtils.escapeSql(timeEnd) + "'");
			// tables.append(" and investTime <= '"
			// + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		// tables.append(" group by investor) b on a.investor = b.investor");
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds, "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// ........................................................................................................
	/**
	 * 查询用户组统计
	 * 
	 * @param groupName
	 *            查询条件 组名
	 * @param pageBean
	 *            分页
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryborrowStatisUserGroupByCondition(String groupName,
			PageBean<Map<String, Object>> pageBean) throws Exception {
		groupName = Utility.filteSqlInfusion(groupName);
		
		String resultFeilds = " * ";
		StringBuffer condition = new StringBuffer();
		StringBuffer tables = new StringBuffer();
		tables
				.append(" ( select ifnull(sum(a.totalSum),0) totalSum,ifnull(sum(a.usableSum),0) usableSum,ifnull(sum(a.freezeSum),0) freezeSum,round(ifnull(sum(b.forPI),0),2) forPI,round(ifnull(sum(b.forInterest),0),2) forInterest,ifnull(sum(c.manageFee),0) manageFee,");
		tables
				.append(" ifnull(sum(d.vipFee),0) vipFee,ifnull(sum(e.hasPI),0) hasPI,ifnull(sum(f.realAmount),0) realAmount,g.groupId,h.groupName from");
		tables
				.append(" v_t_group_user_amount a left join v_t_group_for_amount b on a.userId = b.userId left join v_t_group_managefee c on a.userId = c.userId left join v_t_group_vip d ");
		tables
				.append(" on a.userId = d.userId left join v_t_has_amount e on a.userId=e.userId left join  (select sum(realAmount) realAmount,userId from v_t_invest_amount group by userId) f");
		tables
				.append(" on a.userId = f.userId left join t_group_user g on a.userId = g.userId left join t_group h on g.groupId =h.id");
		tables
				.append(" where groupId is not null group by g.groupId,h.groupName) t");
		if (StringUtils.isNotBlank(groupName)) {
			condition.append(" and t.groupName ='"
					+ StringEscapeUtils.escapeSql(groupName.trim()) + "'");
		}
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, tables.toString(), resultFeilds,
					" order by groupId desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	// ......................................................................

	/**
	 * @MethodName: queryFinanceStatis
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:17:37
	 * @Return:
	 * @Descb: 查询投资统计
	 * @Throws:
	 */
	public Map<String, String> queryFinanceEarnStatis(String timeStart,
			String timeEnd) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getFinanceEarnStatis(conn, ds, outParameterValues,
					timeStart, timeEnd);
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
	 * @MethodName: queryWebStatis
	 * @Param: StatisManageService
	 * @Author: gang.lv
	 * @Date: 2013-4-6 下午03:18:16
	 * @Return:
	 * @Descb: 查询网站统计
	 * @Throws:
	 */
	public Map<String, String> queryWebStatis() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.pr_getWebStatis(conn, ds, outParameterValues, -1);
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
	
	public void changeNumToStr(PageBean<Map<String, Object>> pageBean) {
		List<Map<String, Object>> list = pageBean.getPage();
		if (list != null) {
			for (Map<String, Object> map : list) {
//				if (Convert.strToStr(map.get("borrowWay") + "", "").equals(
//						IConstants.BORROW_TYPE_NET_VALUE)) {
//					map.put("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
//				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
//						.equals(IConstants.BORROW_TYPE_SECONDS)) {
//					map.put("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
//				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
//						.equals(IConstants.BORROW_TYPE_GENERAL)) {
//					map.put("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
//				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
//						.equals(IConstants.BORROW_TYPE_FIELD_VISIT)) {
//					map
//							.put("borrowWay",
//									IConstants.BORROW_TYPE_FIELD_VISIT_STR);
//				} else if (Convert.strToStr(map.get("borrowWay") + "", "")
//						.equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)) {
//					map.put("borrowWay",
//							IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
//				}
//
//				if (Convert.strToStr(map.get("isAutoBid") + "", "").equals("1")) {
//					map.put("isAutoBid", "否");
//				} else if (Convert.strToStr(map.get("isAutoBid") + "", "")
//						.equals("2")) {
//					map.put("isAutoBid", "是");
//				}
//
//				if (Convert.strToStr(map.get("borrowStatus") + "", "").equals(
//						"4")) {
//					map.put("borrowStatus", "是");
//				} else if (Convert.strToStr(map.get("borrowStatus") + "", "")
//						.equals("5")) {
//					map.put("borrowStatus", "是");
//				} else {
//					map.put("borrowStatus", "否");
//				}
				if (Convert.strToStr(map.get("isEmployee") + "", "").equals("0")) {
					map.put("isEmployee", "非员工");
				} else if (Convert.strToStr(map.get("isEmployee") + "", "").equals("1")) {
					map.put("isEmployee", "员工");
				}

			}
		}
	}
	
	/**
	 * 员工资金统计
	 * queryemployeeFund
	 * @auth hejiahua
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param department
	 * @param phone
	 * @param acountBalanceTime
	 * @param investtimeStart
	 * @param investTimeEnd
	 * @param rechargetimeStart
	 * @param rechargeTimeEnd
	 * @throws SQLException 
	 * void
	 * @exception 
	 * @date:2014-11-10 下午6:58:36
	 * @since  1.0.0
	 */
	public void queryemployeeFund(PageBean pageBean,String userName,String realName,String department,String phone,String acountBalanceTime,String investtimeStart,String investTimeEnd,String rechargetimeStart,String rechargeTimeEnd) throws SQLException{
	    Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        condition.append( " and 1= 1");
        if (StringUtils.isNotBlank(userName)) {
            userName = Utility.filteSqlInfusion(userName);
            condition.append(" and username  like '%"+userName+"%'");
        }
        if (StringUtils.isNotBlank(realName)) {
            realName = Utility.filteSqlInfusion(realName);
            condition.append(" and realName like '%"+realName+"%'");
        }
        if (StringUtils.isNotBlank(phone)) {
            phone = Utility.filteSqlInfusion(phone);
            condition.append(" and cellPhone like '%"+phone+"%'");
        }
        if (StringUtils.isNotBlank(department)) {
            department = Utility.filteSqlInfusion(department);
            condition.append(" and department = '"+department+"'");
        }
        
        String  acoumtMoney="(SELECT SUM(tf.`usableSum` + tf.`freezeSum` + tf.`dueinSum` + tf.`dueoutSum`) FROM t_fundrecord tf WHERE tf.`userId` = vt.userId AND tf.recordTime <= '"+DateUtil.dateToString(new Date())+"' GROUP BY id ORDER BY id DESC LIMIT 0,1) AS acoumtMoney";
        String rechargeMoney="(SELECT SUM(trd.rechargeMoney) FROM t_recharge_detail trd WHERE trd.userId = vt.userId AND trd.result = 1    AND trd.rechargeTime <= '"+DateUtil.dateToString(new Date())+"' ) AS rechargeMoney";
        String investMoney="(SELECT SUM(a.recivedPrincipal)  FROM t_invest a WHERE a.investor = vt.userId  and a.investTime<='"+DateUtil.dateToString(new Date())+"') as investMoney" ;
        
        //余额时间点
        if (StringUtils.isNotBlank(acountBalanceTime)) {
            acoumtMoney= "(SELECT SUM(tf.`usableSum` + tf.`freezeSum` + tf.`dueinSum` + tf.`dueoutSum`) FROM t_fundrecord tf WHERE tf.`userId` = vt.userId AND tf.recordTime <= '"+acountBalanceTime+"' GROUP BY id ORDER BY id DESC LIMIT 0,1) AS acoumtMoney";
        }
        //充值
        if (StringUtils.isNotBlank(rechargetimeStart) && StringUtils.isNotBlank(rechargeTimeEnd)) {
            rechargeMoney ="(SELECT SUM(trd.rechargeMoney) FROM t_recharge_detail trd WHERE trd.userId = vt.userId AND trd.result = 1 and trd.rechargeTime >= '"+rechargetimeStart+"'    AND trd.rechargeTime <= '"+rechargeTimeEnd+"' ) AS rechargeMoney";
        }
        //投资
        if (StringUtils.isNotBlank(investtimeStart) && StringUtils.isNotBlank(investTimeEnd)) {
            investMoney ="(SELECT SUM(a.recivedPrincipal)  FROM t_invest a WHERE a.investor = vt.userId and a.investTime>='"+investtimeStart+"' and a.investTime<='"+investTimeEnd+"') as investMoney";
        }
        
        condition.append(" and vt.hasWork1=1 ");//默认查询不离职的
        
        try {
            dataPage(conn, pageBean, "v_t_employee vt", " id, username,realname,selectName,job,createTime,userId,"+acoumtMoney+","+rechargeMoney+","+investMoney+" ", " order by id desc ", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.close();
        }
	}
	
	/**
	 * 部门资金统计
	 * querydepartmentFund
	 * @auth hejiahua
	 * @param department
	 * @param acountBalanceTime
	 * @param investtimeStart
	 * @param investTimeEnd
	 * @param rechargetimeStart
	 * @param rechargeTimeEnd
	 * @return
	 * @throws SQLException 
	 * List<Map<String,Object>>
	 * @exception 
	 * @date:2014-11-10 下午7:23:26
	 * @since  1.0.0
	 */
	public List<Map<String,Object>>   querydepartmentFund(String department,String acountBalanceTime,String investtimeStart,String investTimeEnd,String rechargetimeStart,String rechargeTimeEnd) throws SQLException{
	    Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(department)) {
            department = Utility.filteSqlInfusion(department);
            condition.append(" and department = '"+department+"'");
        }
        
        String  acoumtMoney="(SELECT SUM(tf.`usableSum` + tf.`freezeSum` + tf.`dueinSum` + tf.`dueoutSum`) FROM t_fundrecord tf WHERE tf.`userId` = vt.userId AND tf.recordTime <= '"+DateUtil.dateToString(new Date())+"' GROUP BY id ORDER BY id DESC LIMIT 0,1) AS acoumtMoney";
        String rechargeMoney="(SELECT SUM(trd.rechargeMoney) FROM t_recharge_detail trd WHERE trd.userId = vt.userId AND trd.result = 1    AND trd.rechargeTime <= '"+DateUtil.dateToString(new Date())+"' ) AS rechargeMoney";
        String investMoney="(SELECT SUM(a.recivedPrincipal)  FROM t_invest a WHERE a.investor = vt.userId  and a.investTime<='"+DateUtil.dateToString(new Date())+"') as investMoney" ;

        
        //余额时间点
        if (StringUtils.isNotBlank(acountBalanceTime)) {
            acoumtMoney= "(SELECT SUM(tf.`usableSum` + tf.`freezeSum` + tf.`dueinSum` + tf.`dueoutSum`) FROM t_fundrecord tf WHERE tf.`userId` = vt.userId AND tf.recordTime <= '"+acountBalanceTime+"' GROUP BY id ORDER BY id DESC LIMIT 0,1) AS acoumtMoney";
        }
        //充值
        if (StringUtils.isNotBlank(rechargetimeStart) && StringUtils.isNotBlank(rechargeTimeEnd)) {
            rechargeMoney ="(SELECT SUM(trd.rechargeMoney) FROM t_recharge_detail trd WHERE trd.userId = vt.userId AND trd.result = 1 and trd.rechargeTime >= '"+rechargetimeStart+"'    AND trd.rechargeTime <= '"+rechargeTimeEnd+"' ) AS rechargeMoney";
        }
        //投资
        if (StringUtils.isNotBlank(investtimeStart) && StringUtils.isNotBlank(investTimeEnd)) {
            investMoney ="(SELECT SUM(a.recivedPrincipal)  FROM t_invest a WHERE a.investor = vt.userId and a.investTime>='"+investtimeStart+"' and a.investTime<='"+investTimeEnd+"') as investMoney";
        }
        try {
            DataSet ds =  Database.executeQuery(conn, String.format("SELECT v.selectName as a ,count(v.selectName) as e, SUM(v.acoumtMoney) as b,SUM(v.rechargeMoney) as c,SUM(v.investMoney) as d FROM (SELECT department,selectName, %s,%s,%s  FROM  v_t_employee vt where  vt.hasWork1=1 ) v where 1=1 "+condition.toString()+" GROUP BY v.selectName", acoumtMoney,rechargeMoney,investMoney));
            ds.tables.get(0).rows.genRowsMap();
            return (List<Map<String, Object>>) ds.tables.get(0).rows.rowsMap;
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
	 * 员工推荐明细
	 * queryemployeeRefInfo
	 * @auth hejiahua
	 * @param pageBean
	 * @param username
	 * @param department
	 * @param hasRecharge
	 * @param hasInvest
	 * @param createtimeStart
	 * @param createTimeEnd
	 * @throws SQLException 
	 * void
	 * @exception 
	 * @date:2014-11-11 上午9:56:12
	 * @since  1.0.0
	 */
	public void queryemployeeRefInfo(PageBean pageBean,String username,String department,String hasRecharge,String hasInvest,String createtimeStart,String createTimeEnd) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    
	    StringBuffer condition = new StringBuffer();
	    if (StringUtils.isNotBlank(username)) {
            username = Utility.filteSqlInfusion(username);
            condition.append("  and  tr.recommendUserName like '%"+username+"%'");
        }
	    if (StringUtils.isNotBlank(department)) {
	        department = Utility.filteSqlInfusion(department);
	        condition.append(" and  tr.department ='"+department+"'");
        }
	    if (StringUtils.isNotBlank(hasRecharge)) {
	        hasRecharge = Utility.filteSqlInfusion(hasRecharge);
	        condition.append(" and  tr.hasRecharge ='"+hasRecharge+"'");
        }
	    if (StringUtils.isNotBlank(hasInvest)) {
	        hasInvest = Utility.filteSqlInfusion(hasInvest);
	        condition.append(" and  tr.hasInvest ='"+hasInvest+"'");
        }
	    if (StringUtils.isNotBlank(createtimeStart)) {
	        createtimeStart = Utility.filteSqlInfusion(createtimeStart);
	        condition.append(" and  tr.createTime >='"+createtimeStart+"'");
        }
	    if (StringUtils.isNotBlank(createTimeEnd)) {
	        createTimeEnd = Utility.filteSqlInfusion(createTimeEnd);
	        condition.append(" and  tr.createTime <='"+createTimeEnd+"'");
        }
	    
	    String table =" (SELECT   tu.username,  (   CASE   WHEN tu.`userType` = 1    THEN    (SELECT    tp.realName   FROM    t_person tp   WHERE tp.userId = tu.`id`)    ELSE tu.`orgName`   END ) AS realname, tu.`createTime`,(  CASE    WHEN     (SELECT     COUNT(id) AS counts    FROM   t_recharge_detail trd    WHERE trd.`userId` = tu.`id`     AND trd.`result` = 1) > 0    THEN '是'   ELSE '否'   END  ) AS hasRecharge ,(CASE WHEN (SELECT COUNT(id) AS counts FROM t_invest ti WHERE ti.`investor` = tu.`id`)>0 THEN '是'    ELSE '否'   END ) AS hasInvest,vte.`username` AS recommendUserName,vte.`realName` AS recommendUserRealName,vte.`department`,vte.`selectName` FROM  v_t_recommendfriend_list v, t_user tu,v_t_employee vte WHERE v.`userId` = tu.`id` AND v.`recommendUserId` = vte.`userId` and vte.`hasWork1`=1) tr";
	    try {
            dataPage(conn, pageBean,table, "*", " order by tr.createTime desc ", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	    
	}
	
	/**
	 * 员工推荐活跃度统计
	 * queryemployeeLivInfo
	 * @auth hejiahua
	 * @param pageBean
	 * @param username
	 * @param realname
	 * @param department
	 * @param logintimeStart
	 * @param loginTimeEnd
	 * @param logincount 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-11 下午2:54:53
	 * @since  1.0.0
	 */
	public void queryemployeeLivInfo(PageBean pageBean,String username,String realname,String department,String logintimeStart,String loginTimeEnd,String logincount) throws SQLException{
	    Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(username)) {
            username = Utility.filteSqlInfusion(username);
            condition.append("  and  t3.username like '%"+username+"%'");
        }
        if (StringUtils.isNotBlank(realname)) {
            realname = Utility.filteSqlInfusion(realname);
            condition.append("  and  t3.realName like '%"+realname+"%'");
        }
        if (StringUtils.isNotBlank(department)) {
            department = Utility.filteSqlInfusion(department);
            condition.append(" and  t3.department ='"+department+"'");
        }
        if (StringUtils.isNotBlank(logintimeStart)) {
            logintimeStart = Utility.filteSqlInfusion(logintimeStart);
        }else {
            logintimeStart="2013-01-01 00:00:00";
        }
        if (StringUtils.isNotBlank(loginTimeEnd)) {
            loginTimeEnd = Utility.filteSqlInfusion(loginTimeEnd);
        }else {
            loginTimeEnd=DateUtil.dateToString(new Date());
        }
        if (StringUtils.isNotBlank(logincount)) {
            logincount = Utility.filteSqlInfusion(logincount);
        }else {
            logincount = "10";
        }
	    
	    String table ="(SELECT t.username,t.realName,t.selectName,t.department,t.rcdcount,t.rechargecount,CONCAT(cast((case when t.rcdcount>0 then FORMAT(t.rechargecount/t.rcdcount,2)*100 else 0 end) as char),'%') as rechargeActive,t.investcount,CONCAT(cast((case when t.rcdcount>0 then FORMAT(t.investcount/t.rcdcount,2)*100 else 0 END) as char),'%') as investActive,(t.rcdcount-t.rechargecount) as staticscount,CONCAT(cast((case when t.rcdcount >0 then FORMAT((t.rcdcount-t.rechargecount)/t.rcdcount,2)*100 else 0 end) as char),'%') as statics,t.logincount,CONCAT(cast((case when t.rcdcount>0 then FORMAT(t.logincount/t.rcdcount,2)*100 else 0 end) as char),'%') as loginActive  from (SELECT vte.userId,vte.username,vte.realName,vte.selectName,vte.department,(SELECT COUNT(tru.userId) FROM t_recommend_user tru WHERE tru.recommendUserId = vte.userId ) AS rcdcount,f_recharge_count(vte.userId) as rechargecount,f_invest_count(vte.userId) as investcount,f_login_count('"+logintimeStart+"','"+loginTimeEnd+"',vte.userId,"+logincount+") as logincount FROM v_t_employee vte WHERE vte.hasWork1 = 1) t) t3";
	
	    try {
            dataPage(conn, pageBean, table, "*", "order by t3.loginActive desc", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	}
	/**
	 * 部门推荐活跃度统计
	 * querydepartmentLivInfo
	 * @auth hejiahua
	 * @param pageBean
	 * @param department
	 * @param logintimeStart
	 * @param loginTimeEnd
	 * @param logincount 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-11 下午7:00:48
	 * @since  1.0.0
	 */
	public void querydepartmentLivInfo(PageBean pageBean,String department,String logintimeStart,String loginTimeEnd,String logincount) throws SQLException{
	    Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(department)) {
            department = Utility.filteSqlInfusion(department);
            condition.append(" and  t3.department ='"+department+"'");
        }
        if (StringUtils.isNotBlank(logintimeStart)) {
            logintimeStart = Utility.filteSqlInfusion(logintimeStart);
        }else {
            logintimeStart="2013-01-01 00:00:00";
        }
        if (StringUtils.isNotBlank(loginTimeEnd)) {
            loginTimeEnd = Utility.filteSqlInfusion(loginTimeEnd);
        }else {
            loginTimeEnd=DateUtil.dateToString(new Date());
        }
        if (StringUtils.isNotBlank(logincount)) {
            logincount = Utility.filteSqlInfusion(logincount);
        }else {
            logincount = "10";
        }
        String table ="(SELECT t4.selectName,t4.department,COUNT(t4.selectName) AS countnumber,SUM(t4.rcdcount) AS rcdcount,SUM(t4.rechargecount) AS rechargecount,CONCAT(cast((CASE WHEN SUM(t4.rcdcount) > 0 THEN FORMAT(SUM(t4.rechargecount) / SUM(t4.rcdcount),2) * 100  ELSE  0  END ) as char),'%') AS rechargeActive,SUM(t4.investcount) AS investcount, CONCAT(cast( (CASE WHEN SUM(t4.investcount) > 0 THEN  FORMAT( SUM(t4.investcount) / SUM(t4.rcdcount),2) * 100 ELSE  0 END ) as char),'%' ) AS investActive,SUM(t4.staticscount) AS staticscount,CONCAT(cast( (CASE  WHEN SUM(t4.rcdcount) > 0 THEN  FORMAT( (SUM(t4.rcdcount) - SUM(t4.rechargecount)) / SUM(t4.rcdcount),2) * 100 ELSE  0  END) as char),'%') AS statics,SUM(t4.logincount) AS logincount, CONCAT(cast((CASE  WHEN SUM(t4.rcdcount) > 0 THEN  FORMAT(SUM(t4.logincount) / SUM(t4.rcdcount), 2)*100  ELSE 0  END ) as char),'%') AS loginActive FROM(SELECT  t.username,t.realName,t.selectName,t.department, t.rcdcount, t.rechargecount,t.investcount,(t.rcdcount - t.rechargecount) AS staticscount,t.logincount FROM  (SELECT vte.userId,vte.username,vte.realName,vte.selectName,vte.department, (SELECT COUNT(tru.userId) FROM  t_recommend_user tru  WHERE  tru.recommendUserId = vte.userId) AS rcdcount,f_recharge_count (vte.userId) AS rechargecount, f_invest_count (vte.userId) AS investcount, f_login_count ('"+logintimeStart+"','"+loginTimeEnd+"',vte.userId, "+logincount+" ) AS logincount FROM  v_t_employee vte  WHERE   vte.hasWork1 = 1) t) t4  GROUP BY t4.selectName,t4.department) t3";
        try {
            dataPage(conn, pageBean, table, "*", "order by t3.loginActive desc", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	}
	
	/**
     * 员工绩效统计
     * queryemployeePerformanceInfo
     * @auth hejiahua
     * @param username
     * @param realname
     * @param pageBean
     * @param department
     * @param timeStart 
     * void
     * @throws SQLException 
     * @exception 
     * @date:2014-11-11 下午7:00:48
     * @since  1.0.0
     */
    public void queryemployeePerformanceInfo(PageBean pageBean,String username,String realname,String department,String timeStart,String investStartTime,String investEndTime) throws SQLException{
        Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(username)) {
            username = Utility.filteSqlInfusion(username);
            condition.append("  and  t3.username like '%"+username+"%'");
        }
        if (StringUtils.isNotBlank(realname)) {
            realname = Utility.filteSqlInfusion(realname);
            condition.append("  and  t3.realName like '%"+realname+"%'");
        }
        if (StringUtils.isNotBlank(department)) {
            department = Utility.filteSqlInfusion(department);
            condition.append(" and  t3.department ='"+department+"'");
        }
        if (StringUtils.isNotBlank(timeStart)) {
            timeStart = Utility.filteSqlInfusion(timeStart);
        }else {
            timeStart=DateUtil.dateToString(new Date());
        }
        
        if (StringUtils.isBlank(investEndTime)) {
            investStartTime = "2013-01-01 00:00:00";
            investEndTime = DateUtil.dateToString(new Date());
        }else {
            investStartTime = Utility.filteSqlInfusion(investStartTime);
            investEndTime = Utility.filteSqlInfusion(investEndTime);
        }
        
        String timestart1 = DateUtil.dateToString(new Date());
        
        String table ="(SELECT vte.createTime, vte.username,vte.realName,vte.department,vte.selectName, f_my_freeAmount(vte.userId, '"+timestart1+"') as myFreeAmount, f_my_amount(vte.userId,'"+timeStart+"') as myAmount,f_my_sumAmount(vte.userId,'"+timestart1+"') as mySumAmount,(SELECT SUM(ti.investAmount) FROM t_invest ti,t_borrow b WHERE ti.borrowId=b.id  and ti.investor = vte.userId   and  ti.investTime>='"+investStartTime+"' and ti.investTime<='"+investEndTime+"' and   b.borrowStatus>=2 AND b.borrowStatus<=5  ) as myInvestAmount,f_friend_amount(vte.userId,'"+timeStart+"') as friendAmount,f_friend_sumAmount(vte.userId,'"+timestart1+"') as friendSumAmount,f_friend_freezeAmount (vte.userId, '"+timestart1+"') AS friendFreezeAmount,f_friend_investAmount(vte.userId,'"+investStartTime+"','"+investEndTime+"') as friendInvestAmount,ac.rcdcount,ac.rechargecount,ac.rechargeActive,ac.investcount,ac.investActive,ac.staticscount,ac.statics from v_t_employee vte,(SELECT t.username,t.realName,t.selectName,t.department,t.rcdcount,(case when t.rcdcount>0 then t.rechargecount else 0 end) as rechargecount,CONCAT(cast((case when t.rcdcount>0 then FORMAT(t.rechargecount/t.rcdcount,2)*100 else 0 end) as char),'%') as rechargeActive,(case when t.rcdcount>0 then t.investcount else 0 end) as investcount,CONCAT(cast((case when t.rcdcount>0 then FORMAT(t.investcount/t.rcdcount,2)*100 else 0 END) as char),'%') as investActive,(case when t.rcdcount>0 then (t.rcdcount - t.rechargecount) else 0 end) as staticscount,CONCAT(CAST((case when t.rcdcount >0 then FORMAT((t.rcdcount-t.rechargecount)/t.rcdcount,2)*100 else 0 end) AS CHAR),'%') as statics  from (SELECT vte.userId,vte.username,vte.realName,vte.selectName,vte.department,(SELECT COUNT(tru.userId) FROM t_recommend_user tru,t_user t WHERE tru.recommendUserId = vte.userId and tru.userId = t.id and t.createTime>='"+investStartTime+"'  and  t.createTime<='"+investEndTime+"' ) AS rcdcount,f_recharge_count(vte.userId,'"+investStartTime+"','"+investEndTime+"') as rechargecount,f_invest_count(vte.userId,'"+investStartTime+"','"+investEndTime+"') as investcount FROM v_t_employee vte WHERE vte.hasWork1 = 1) t) ac where ac.username = vte.username and vte.hasWork1 = 1) t3";
        try {
            dataPage(conn, pageBean, table, "*", "order by t3.createTime ", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
    }
	
	
	/**
	 *功能：用户统计
	 * @param map
	 * @param pageBean
	 * @throws Exception 
	 */
	public void userstaticsList(Map <String, String> map,PageBean<Map<String, Object>> pageBean) throws Exception{
		//按条件生成SQL语句
		Map <String,String>sqlMap = generateSql(map);
		Connection conn = MySQL.getConnection();
		try {
			//System.out.println(sqlMap.get("tableSql").toString()+" limit "+ pageBean.getPageNum() +","+pageBean.getPageSize());
			//查询记录
			//List list = statisManageDao.queryUserInfo(conn, sqlMap.get("tableSql").toString()+" limit "+ pageBean.getPageNum() +","+pageBean.getPageSize());
			Map<String, String> all = statisManageDao.queryUserInfo(conn, sqlMap.get("allSql").toString());
			map.putAll(all);
			
			dataPage(conn, pageBean, " ("+sqlMap.get("tableSql").toString()+") tab", "*", "", "");
			//封装pageBean
			//pageBean.setTotalNum(list.size());
			//int lastd = (int)(pageBean.getPageSize()*(pageBean.pageNum-1) + pageBean.getPageSize()) > list.size() ? list.size() : (int)(pageBean.getPageSize()*(pageBean.pageNum-1) + pageBean.getPageSize());
			//pageBean.setPage(list.subList((int)(pageBean.getPageSize()*(pageBean.pageNum-1)), lastd));
			/*for (int i = 0; i < list1.size(); i++){
				Map m = (Map) list1.get(i);
				BigDecimal rechargeMoney = (BigDecimal) m.get("rechargeMoney");
				BigDecimal investAmt = (BigDecimal) m.get("investAmt");
				BigDecimal usableSum = (BigDecimal) m.get("usableSum");
				if (null == rechargeMoney){
					rechargeMoney = new BigDecimal(0);
					investAmt = new BigDecimal(0);
				}
				map.put("rechargeMoney", rechargeMoney.toString());
				map.put("investAmt", investAmt.toString());
				map.put("usableSum", usableSum==null ? "0" : usableSum.toString());
			}*/
			
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 *功能：根据条件封装SQL语句
	 * @param map
	 * @return
	 */
	private Map<String,String> generateSql(Map<String, String> map) {
		String userName = map.get("userName");
		String realName = map.get("realName");
		String timeStart = map.get("timeStart");
		String timeEnd = map.get("timeEnd");
		String isEmployee = map.get("isEmployee");
		String ltimeStart = map.get("ltimeStart");
		String ltimeEnd = map.get("ltimeEnd");
		String rtimeStart = map.get("rtimeStart");
		String rtimeEnd = map.get("rtimeEnd");
		String mobilePhone = map.get("mobilePhone");
		String usableAmts = map.get("usableAmts");
		String usableAmte = map.get("usableAmte");
		String usableTimeStart = map.get("usableTimeStart");
		
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		isEmployee = Utility.filteSqlInfusion(isEmployee);
		ltimeStart = Utility.filteSqlInfusion(ltimeStart);
		ltimeEnd = Utility.filteSqlInfusion(ltimeEnd);
		mobilePhone = Utility.filteSqlInfusion(mobilePhone);
		
		StringBuffer tableSql = new StringBuffer();
		StringBuffer allSql = new StringBuffer();
		StringBuffer condition = new StringBuffer("");
		
		allSql.append("SELECT ifnull(SUM(trd1.rechargeMoney),0) as rechargeMoney, ifnull(SUM(ti1.investAmount),0) as investAmt,ifnull(SUM(tu.usableSum),0) usableSum FROM   t_user tu ");
				
		tableSql.append("SELECT tu.username,tp1.realName,tu.mobilePhone,tu.email,ifnull(te1.hasWork,0)  as isEmployee  ,tu.createTime,ti1.investAmount as investAmt,trd1.rechargeMoney,tu.usableSum,(SELECT t.`usableSum`  FROM t_fundrecord t WHERE t.`userId` = tu.id AND t.`id` = (SELECT MAX(id) FROM t_fundrecord tf WHERE tf.`userId` = tu.id AND tf.`recordTime`<='"+(StringUtils.isBlank(usableTimeStart)?DateUtil.dateToString(new Date()):usableTimeStart)+"')) as  usableSumTime,(SELECT  tp1.`realName` redName  FROM t_recommend_user tru, t_user tu1, t_person tp1   WHERE tru.`recommendUserId` = tu1.`id`   AND tp1.`userId` = tu1.`id` AND tru.`userId` = tu.id) as redName FROM   t_user tu ");

		//真实姓名
		if (StringUtils.isNotBlank(realName)) {
			condition.append("  inner JOIN (SELECT tp.`userId`,tp.`realName` FROM t_person tp WHERE  tp.`realName` LIKE '%").append(realName).append("%') tp1  ON   tu.`id` = tp1.`userId`   ");
		}else {
			condition.append("  LEFT JOIN t_person tp1  ON tu.`id` = tp1.`userId`");
		}
		
		//充值
		if (StringUtils.isNotBlank(ltimeStart)||StringUtils.isNotBlank(ltimeEnd)) {
			condition.append("  inner JOIN (SELECT SUM(trd.`rechargeMoney`) AS rechargeMoney,trd.`userId` FROM t_recharge_detail trd WHERE trd.`result` = 1 ");
			if (StringUtils.isNotBlank(ltimeStart)) {
				condition.append(" AND trd.`rechargeTime`>='").append(ltimeStart).append("' ");
			}
			if (StringUtils.isNotBlank(ltimeEnd)) {
				condition.append(" AND trd.`rechargeTime`<='").append(ltimeEnd).append("' ");
			}
			condition.append(" GROUP BY trd.`userId`) trd1    ON trd1.`userId` = tu.`id` ");
		}else {
			condition.append("  LEFT JOIN (SELECT SUM(trd.`rechargeMoney`) AS rechargeMoney,trd.`userId` FROM t_recharge_detail trd WHERE trd.`result` = 1  GROUP BY trd.`userId`) trd1  ON trd1.`userId` = tu.`id` ");
		}
		
		//投资
		if (StringUtils.isNotBlank(timeStart)||StringUtils.isNotBlank(timeEnd)) {
			condition.append(" inner JOIN  (SELECT   SUM(ti.`investAmount`) AS investAmount, ti.`investor`  FROM t_invest ti WHERE 1=1 ");
			if (StringUtils.isNotBlank(timeStart)) {
				condition.append(" and ti.`investTime`>='").append(timeStart).append("' ");
			}
			if (StringUtils.isNotBlank(timeEnd)) {
				condition.append(" AND ti.`investTime`<='").append(timeEnd).append("' ");
			}
			condition.append(" GROUP BY ti.`investor`) ti1  ON ti1.`investor` = tu.`id`   ");
		}else {
			condition.append(" LEFT JOIN   (SELECT  SUM(ti.`investAmount`) AS investAmount,  ti.`investor`  FROM  t_invest ti GROUP BY ti.`investor`) ti1   ON ti1.`investor` = tu.`id` ");
		}
		
		//推荐人
		//condition.append("  LEFT JOIN (SELECT tru.`userId`,tp1.`realName` redName FROM t_recommend_user tru,t_user tu1,t_person tp1 WHERE tru.`recommendUserId` = tu1.`id` AND tp1.`userId` = tu1.`id`) tru1 ON tru1.`userId` = tu.`id` ");
		
		/*//可用余额时间点
		if (StringUtils.isNotBlank(usableTimeStart)) {
			condition.append("  LEFT JOIN (SELECT t.`usableSum` as usableSumTime,t.`userId` FROM t_fundrecord t WHERE t.id EXISTS (SELECT MAX(id) FROM t_fundrecord tf WHERE tf.`recordTime`>'"+usableTimeStart+"' group by userId)) tff ON tff.UserID = tu.id ");
		}else {
			condition.append("  LEFT JOIN (SELECT t.`usableSum` as usableSumTime,t.`userId` FROM t_fundrecord t WHERE t.id EXISTS (SELECT MAX(id) FROM t_fundrecord tf WHERE tf.`recordTime`>'"+DateUtil.dateToString(new Date())+"'  group by userId )) tff ON tff.UserID = tu.id ");
		}*/
		
		
		condition.append(" LEFT JOIN t_employee te1 ON te1.`userId` = tu.`id` where 1 = 1 ");
		
		if (StringUtils.isNotBlank(mobilePhone)){
			condition.append(" and tu.`mobilePhone` like '%"
					+ StringEscapeUtils.escapeSql(mobilePhone) + "%'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username  like '%"
					+ StringEscapeUtils.escapeSql(userName.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(usableAmts)) {
			condition.append(" and tu.usableSum >="
					+ StringEscapeUtils.escapeSql(usableAmts.trim()) + " ");
		}
		if (StringUtils.isNotBlank(usableAmte)) {
			condition.append(" and tu.usableSum <="
					+ StringEscapeUtils.escapeSql(usableAmte.trim()) + " ");
		}
		if (StringUtils.isNotBlank(rtimeStart)) {
			condition.append(" and tu.createTime  >= '"
					+ StringEscapeUtils.escapeSql(rtimeStart) + "' ");
		}
		if (StringUtils.isNotBlank(rtimeEnd)) {
			condition.append(" and tu.createTime  <= '"
					+ StringEscapeUtils.escapeSql(rtimeEnd) + "' ");
		} 
		
		if (StringUtils.isNotBlank(map.get("userType"))) {
			condition.append(" and tu.userType  = '"
					+ StringEscapeUtils.escapeSql(map.get("userType")) + "' ");
		} 
		
		if (StringUtils.isNotBlank(isEmployee)){
			if ("1".equals(isEmployee)){
				condition.append("  AND EXISTS (SELECT 1 FROM t_employee te WHERE te.`hasWork` = 1 AND te.`userId` = tu.`id` )");
			}else {
				condition.append("  AND not EXISTS (SELECT 1 FROM t_employee te WHERE te.`hasWork` = 1 AND te.`userId` = tu.`id` )");
			} 
		}
		
		
		condition.append(" ORDER BY tu.id DESC ");
		
		tableSql.append(condition);
		allSql.append(condition);
		
		log.info("----sql:"+tableSql);
		log.info("----allSql:"+allSql);
		Map <String,String>retMap = new HashMap<String,String>();
		retMap.put("tableSql", tableSql.toString());
		retMap.put("allSql", allSql.toString());
		return retMap;
	}
	
	/**
	 * 借款统计列表
	 * queryborrowInfoDeatil
	 * @auth hejiahua
	 * @param pageBean
	 * @param borrowTitle
	 * @param username
	 * @param borrowWay
	 * @param timeStart
	 * @param timeEnd
	 * @param createTimeStart
	 * @param createTimeEnd 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-11-14 下午3:09:43
	 * @since  1.0.0
	 */
	public Map<String, String> queryborrowInfoDeatil(PageBean pageBean,String borrowTitle,String username,String borrowWay,String timeStart,String timeEnd,String createTimeStart,String createTimeEnd) throws SQLException{
	    StringBuffer condition = new StringBuffer();
	    if (StringUtils.isNotBlank(borrowTitle)) {
	        borrowTitle = Utility.filteSqlInfusion(borrowTitle);
	        condition.append(" and tb.a2 like '%"+borrowTitle+"%'");
        }
	    
	    if (StringUtils.isNotBlank(username)) {
	        username = Utility.filteSqlInfusion(username);
	        condition.append(" and tb.a3 like '%"+username+"%'");
        }
	    
	    if (StringUtils.isNotBlank(borrowWay)) {
	        borrowWay = Utility.filteSqlInfusion(borrowWay);
	        condition.append(" and tb.a6  ="+borrowWay+"");
        }
	    
	    if (StringUtils.isNotBlank(timeStart)) {
	        timeStart = Utility.filteSqlInfusion(timeStart);
	        condition.append(" and tb.a11 >= '"+timeStart+"'");
        }
	    
	    if (StringUtils.isNotBlank(timeEnd)) {
	        timeEnd = Utility.filteSqlInfusion(timeEnd);
	        condition.append(" and tb.a11 <='"+timeEnd+"'");
        }
	    
	    if (StringUtils.isNotBlank(createTimeStart)) {
	        createTimeStart = Utility.filteSqlInfusion(createTimeStart);
	        condition.append(" and tb.a10 >= '"+createTimeStart+"'");
        }
	    
	    if (StringUtils.isNotBlank(createTimeEnd)) {
	        createTimeEnd = Utility.filteSqlInfusion(createTimeEnd);
	        condition.append(" and tb.a10 <='"+createTimeEnd+"'");
        }
	    Map<String, String> map = null;
	    String table = " (SELECT tb.id AS a1,tb.borrowTitle AS a2,tu.username AS a3, CASE WHEN tu.userType = 1 THEN tp.realName ELSE tu.orgName END AS a4, case tb.borrowWay when 1 then '净值借款' when 2 then  '秒还借款' when 3 then '普通借款' when  4 then '实地考察借款' when 5 then '机构担保借款' when 6 then '流转标' else '' end as a5,tb.borrowWay as a6, tb.borrowAmount AS a7,tb.annualRate AS a8, CASE WHEN tb.isDayThe > 1 THEN CONCAT(CONVERT (tb.deadline, CHAR),'天') ELSE CONCAT(CONVERT (tb.deadline, CHAR),'月') END AS a9, tb.publishTime AS a10, tb.auditTime AS a11, DATE_FORMAT((CASE WHEN tb.isDayThe > 1 THEN DATE_ADD(tb.auditTime, INTERVAL tb.deadline DAY) ELSE DATE_ADD(tb.auditTime,INTERVAL tb.deadline MONTH) END),'%Y-%m-%d')  AS a12, tb.manageFee AS a13,(tb.borrowAmount - tb.manageFee) AS a14,(tbl.stillTotalSum - tb.borrowAmount) AS a15, (SELECT sum(trb.stillPrincipal) FROM t_repayment trb WHERE trb.borrowId = tb.id AND trb.repayStatus = 2 ) AS a16,(SELECT sum(trb.stillInterest) FROM t_repayment trb WHERE trb.borrowId = tb.id AND trb.repayStatus = 2 ) AS a17,(SELECT sum(trb.stillPrincipal) FROM t_repayment trb WHERE trb.borrowId = tb.id AND trb.repayStatus = 1) AS a18, (SELECT sum(trb.stillInterest) FROM t_repayment trb WHERE trb.borrowId = tb.id AND trb.repayStatus = 1) AS a19,tb.auditTime as a20 FROM t_borrow tb LEFT JOIN t_user tu ON tu.id = tb.publisher LEFT JOIN t_person tp ON tp.userId = tb.publisher LEFT JOIN t_borrow_success_list tbl ON tbl.borrowId = tb.id WHERE tb.borrowStatus > 3 AND tb.borrowStatus < 6) tb ";
	    Connection conn = MySQL.getConnection();
	    log.info("-----condition:" + condition.toString());
	   try {
	       dataPage(conn, pageBean, table, "*", " order by tb.a1 desc", condition.toString());
	       log.info("select sum(tb.a5) as sumAmount,sum(tb.13) as sumInterest from "+table+" where 1= 1   "+condition.toString());
	       DataSet ds =  Database.executeQuery(conn, "select sum(tb.a7) as a,sum(tb.a15) as b from "+table+" where 1= 1   "+condition.toString());
	       map = BeanMapUtils.dataSetToMap(ds); 
	       
	   }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	    return map;
	}
	
	/**
	 * 还款信息统计
	 * queryinvestInfoDetail
	 * @auth hejiahua
	 * @param pageBean
	 * @param borrowTitle
	 * @param username
	 * @param borrowWay
	 * @param timeStart
	 * @param timeEnd
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param investTimeStart
	 * @param investTimeEnd
	 * @return
	 * @throws SQLException 
	 * Map<String,String>
	 * @exception 
	 * @date:2014-11-14 下午4:57:46
	 * @since  1.0.0
	 */
	public Map<String, String> queryinvestInfoDetail(PageBean pageBean,String borrowTitle,String username,String borrowWay,String timeStart,String timeEnd,String createTimeStart,String createTimeEnd,String investTimeStart,String investTimeEnd,String reffere) throws SQLException{
	    StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(borrowTitle)) {
            borrowTitle = Utility.filteSqlInfusion(borrowTitle);
            condition.append(" and tb2.a1 like '%"+borrowTitle+"%'");
        }
        
        if (StringUtils.isNotBlank(username)) {
            username = Utility.filteSqlInfusion(username);
            condition.append(" and tb2.a2 like '%"+username+"%'");
        }
        
        if (StringUtils.isNotBlank(borrowWay)) {
            borrowWay = Utility.filteSqlInfusion(borrowWay);
            condition.append(" and tb2.a66  ="+borrowWay+"");
        }
        
        if (StringUtils.isNotBlank(timeStart)) {
            timeStart = Utility.filteSqlInfusion(timeStart);
            condition.append(" and tb2.a9 >= '"+timeStart+" 00:00:00'");
        }
        
        if (StringUtils.isNotBlank(timeEnd)) {
            timeEnd = Utility.filteSqlInfusion(timeEnd);
            condition.append(" and tb2.a9 <='"+timeEnd+" 23:59:59'");
        }
        
        if (StringUtils.isNotBlank(createTimeStart)) {
            createTimeStart = Utility.filteSqlInfusion(createTimeStart);
            condition.append(" and tb2.a8 >= '"+createTimeStart+" 00:00:00'");
        }
        
        if (StringUtils.isNotBlank(createTimeEnd)) {
            createTimeEnd = Utility.filteSqlInfusion(createTimeEnd);
            condition.append(" and tb2.a8 <='"+createTimeEnd+" 23:59:59'");
        }
        
        if (StringUtils.isNotBlank(investTimeStart)) {
            investTimeStart = Utility.filteSqlInfusion(investTimeStart);
            condition.append(" and tb2.a11 >= '"+investTimeStart+" 00:00:00'");
        }
        
        if (StringUtils.isNotBlank(investTimeEnd)) {
            investTimeEnd = Utility.filteSqlInfusion(investTimeEnd);
            condition.append(" and tb2.a11 <='"+investTimeEnd+" 23:59:59 '");
        }
        
        if (StringUtils.isNotBlank(reffere)) {
			condition.append(" and tb2.reffere like '%").append(reffere).append("%'");
		}
        
        Map<String, String> map = null;
        String table = "(SELECT (SELECT tp.`realName` FROM t_recommend_user tru,t_person tp WHERE tru.`userId` = tu.id AND tru.`recommendUserId` = tp.`userId`) AS reffere,tb.borrowTitle AS a1,tu.username AS a2,CASE WHEN tu.userType = 1 THEN   tp.realName ELSE    tu.orgName END AS a3, tb.borrowAmount AS a4, tb.annualRate AS a5,tb.borrowWay as a66, case tb.borrowWay when 1 then '净值借款' when 2 then  '秒还借款' when 3 then '普通借款' when  4 then '实地考察借款' when 5 then '机构担保借款' when 6 then '流转标' else '' end as a6,cast( (case when tb.isDayThe = 1 then CONCAT(tb.deadline,'月') else CONCAT(tb.deadline,'天') end)  as char)as a7, tb.publishTime AS a8, tb.auditTime AS a9, vtl.repayDate AS a11,    (CASE WHEN tb.isDayThe > 1 THEN    DATE_ADD(    tb.auditTime,   INTERVAL tb.deadline DAY  ) ELSE  DATE_ADD(    tb.auditTime,    INTERVAL tb.deadline MONTH   ) END ) as a10,vtl.repayPeriod AS a12,(vtl.stillPrincipal+vtl.stillInterest) as a13, vtl.stillPrincipal AS a14, vtl.stillInterest AS a15 FROM t_repayment vtl LEFT JOIN t_borrow tb ON tb.id = vtl.borrowId LEFT JOIN t_user tu ON tu.id = tb.publisher LEFT JOIN t_person tp ON tp.userId = tb.publisher) tb2";
        Connection conn = MySQL.getConnection();
       try {
           dataPage(conn, pageBean, table, "*", " order by tb2.a1 desc,tb2.a10 desc ", condition.toString());
           DataSet ds =  Database.executeQuery(conn, "select sum(tb2.a13) as a,sum(tb2.a14) as b ,sum(tb2.a15) as c from "+table+" where 1= 1  "+condition.toString());
           map = BeanMapUtils.dataSetToMap(ds);
       }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
        return map;
	}
	
	/**
	 *功能：统计报表
	 * @param smonth
	 * @return
	 * @throws Exception 
	 */
	public Map allUserStatics() throws Exception{
		log.info("----service.allUserStatics----");
		Map map = new HashMap();
		Connection conn = MySQL.getConnection();
//		DataSet ds =  Database.executeQuery(conn, "select sum(tb2.a13) as a,sum(tb2.a14) as b ,sum(tb2.a15) as c from "+table+" where 1= 1  "+condition.toString());
//        map = BeanMapUtils.dataSetToMap(ds);
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_alluser_static(conn, ds, outParameterValues);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		log.info("----map;"+map);
		
		return map;
	}
	/**
	 *功能：按月统计报表
	 * @param smonth
	 * @return
	 * @throws Exception 
	 */
	public Map monthUserStatics(String month) throws Exception{
		log.info("----monthUserStatics----"+month);
		Map map = new HashMap();
		Connection conn = MySQL.getConnection();
//		DataSet ds =  Database.executeQuery(conn, "select sum(tb2.a13) as a,sum(tb2.a14) as b ,sum(tb2.a15) as c from "+table+" where 1= 1  "+condition.toString());
//        map = BeanMapUtils.dataSetToMap(ds);
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_monthuser_static(conn, ds, outParameterValues,month);
			ds.tables.get(0).rows.genRowsMap();
			map = BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
		log.info("----map;"+map);
		
		return map;
	}
	/**
	 *功能：注册奖励
	 * @param map
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public Map regeditStatics(Map <String, String> map,PageBean pageBean) throws SQLException{
		String userName = map.get("userName");
		String realName = map.get("realName");
		String timeStart = map.get("memberTimes");
		String timeEnd = map.get("memberTimee");
		
		String sversion = map.get("sversion");
		String isEmployee = map.get("isEmployee");
		String awardFrom = map.get("awardFrom");
		String awardTo = map.get("awardTo");
		
        Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(userName)) {
        	userName = Utility.filteSqlInfusion(userName);
            condition.append(" and t1.username like '%"+userName+"%'");
        }
        if (StringUtils.isNotBlank(realName)) {
        	realName = Utility.filteSqlInfusion(realName);
            condition.append(" and t1.realName like '%"+realName+"%'");
        }
        StringBuffer timeCondition = new StringBuffer("");//注册时间条件
        StringBuffer itimeCondition = new StringBuffer("");//投资时间条件 
        if (StringUtils.isNotBlank(timeStart)) {
            timeStart = Utility.filteSqlInfusion(timeStart);
            timeCondition.append(" and a.createTime >='").append(timeStart).append("' ");
            itimeCondition.append(" and f.investTime >='").append(timeStart).append("' ");
        }
        if (StringUtils.isNotBlank(timeEnd)) {
        	timeEnd = Utility.filteSqlInfusion(timeEnd);
        	timeCondition.append(" and a.createTime <='").append(timeEnd).append("' ");
        	itimeCondition.append(" and f.investTime <='").append(timeEnd).append("' ");
        }
        String employeeFilter = "";//如果是内部员工，只查表中存在的
        if (StringUtils.isNotBlank(isEmployee) && isEmployee.equals("1")) {
        	employeeFilter = " AND EXISTS(SELECT 1 FROM t_employee f WHERE f.userId=d.ret1)";
        } else if (StringUtils.isNotBlank(isEmployee) && isEmployee.equals("0")) {
        	employeeFilter = " AND NOT EXISTS(SELECT 1 FROM t_employee f WHERE f.userId=d.ret1)";
        } 
        if (StringUtils.isNotBlank(isEmployee) && isEmployee.equals("1")) {
        	condition.append( " and t1.isEmployee is not null");
        } else if (StringUtils.isNotBlank(isEmployee) && isEmployee.equals("0")) {
        	condition.append( " and t1.isEmployee is null");
        } 
        
//        StringBuffer condition = new StringBuffer("where 1=1");
        if (StringUtils.isNotBlank(awardFrom)) {
        	awardFrom = Utility.filteSqlInfusion(awardFrom);
        	condition.append(" and t1.sumAll >= ").append(awardFrom);
        }
        if (StringUtils.isNotBlank(awardTo)) {
        	awardTo = Utility.filteSqlInfusion(awardTo);
        	condition.append(" and t1.sumAll <= ").append(awardTo);
        }
        
        String firstMoney = "10.0";
        String secondMoney = "2.0";
        
        String outMon = "2.0";
        
        
        //String table ="(SELECT vte.createTime, vte.username,vte.realName,vte.department,vte.selectName, f_my_freeAmount(vte.userId, '"+timestart1+"') as myFreeAmount, f_my_amount(vte.userId,'"+timeStart+"') as myAmount,f_my_sumAmount(vte.userId,'"+timestart1+"') as mySumAmount,(SELECT SUM(ti.recivedPrincipal) FROM t_invest ti,t_borrow b WHERE ti.borrowId=b.id  and ti.investor = vte.userId   and  ti.investTime>='"+investStartTime+"' and ti.investTime<='"+investEndTime+"') as myInvestAmount,f_friend_amount(vte.userId,'"+timeStart+"') as friendAmount,f_friend_sumAmount(vte.userId,'"+timestart1+"') as friendSumAmount,f_friend_freezeAmount (vte.userId, '"+timestart1+"') AS friendFreezeAmount,f_friend_investAmount(vte.userId,'"+investStartTime+"','"+investEndTime+"') as friendInvestAmount,ac.rcdcount,ac.rechargecount,ac.rechargeActive,ac.investcount,ac.investActive,ac.staticscount,ac.statics from v_t_employee vte,(SELECT t.username,t.realName,t.selectName,t.department,t.rcdcount,(case when t.rcdcount>0 then t.rechargecount else 0 end) as rechargecount,CONCAT(cast((case when t.rcdcount>0 then FORMAT(t.rechargecount/t.rcdcount,2)*100 else 0 end) as char),'%') as rechargeActive,(case when t.rcdcount>0 then t.investcount else 0 end) as investcount,CONCAT(cast((case when t.rcdcount>0 then FORMAT(t.investcount/t.rcdcount,2)*100 else 0 END) as char),'%') as investActive,(case when t.rcdcount>0 then (t.rcdcount - t.rechargecount) else 0 end) as staticscount,CONCAT(CAST((case when t.rcdcount >0 then FORMAT((t.rcdcount-t.rechargecount)/t.rcdcount,2)*100 else 0 end) AS CHAR),'%') as statics  from (SELECT vte.userId,vte.username,vte.realName,vte.selectName,vte.department,(SELECT COUNT(tru.userId) FROM t_recommend_user tru,t_user t WHERE tru.recommendUserId = vte.userId and tru.userId = t.id and t.createTime>='"+investStartTime+"'  and  t.createTime<='"+investEndTime+"' ) AS rcdcount,f_recharge_count(vte.userId) as rechargecount,f_invest_count(vte.userId) as investcount FROM v_t_employee vte WHERE vte.hasWork1 = 1) t) ac where ac.username = vte.username and vte.hasWork1 = 1) t3";
        StringBuffer sbtable = new StringBuffer();
        if (sversion.equals("v1")){
        	sbtable.append("( SELECT * FROM ( SELECT d.ret1,h.userName,d.level1,d.level1*").append(firstMoney)
            .append(" fmon,d.level2,d.level2*").append(secondMoney).append(" smon,")
            .append("(SELECT  job FROM t_employee f WHERE  f.userId=d.ret1 and f.hasWork=1) isEmployee, e.realName,d.level2*").append(secondMoney).append(" + d.level1*").append(firstMoney).append(" sumAll FROM ")
            .append("( ")
            .append("SELECT c.ret1, ")
            .append("MAX(IF(mylevel = 1,ct,0)) AS level1, ")
            .append("MAX(IF(mylevel = 2,ct,0)) AS level2 ")
            .append("FROM ( ")
            .append("SELECT recommendUserId ret1,COUNT(recommendUserId) ct, 1 mylevel  FROM ")
            .append("( ")
            .append("SELECT a.username, a.id,b.recommendUserId FROM t_user a,t_recommend_user b WHERE ")
            .append("EXISTS (SELECT f.investor FROM t_invest f WHERE f.investor = a.id ").append(itimeCondition).append(") ")
            .append("AND b.userId=a.id ")
            .append(timeCondition)
            .append(") g GROUP BY recommendUserId ")
            .append("UNION ALL ")
            .append("SELECT aa.recommendUserId id,  COUNT(aa.recommendUserId)ct, 2 mylevel ")//--
            .append("FROM t_recommend_user aa ,t_recommend_user bb WHERE aa.userId = bb.recommendUserId ")
            .append("AND EXISTS(SELECT 1 FROM t_invest f WHERE f.investor = bb.userId ")
            .append(itimeCondition).append(")")//and a.createTime >'2014-09-01' AND a.createTime < '2014-11-12' 
            .append("AND EXISTS(SELECT 1 FROM t_user a WHERE a.id=bb.userId  ")
            .append(timeCondition).append(") ")
            .append("GROUP BY aa.recommendUserId ")
            .append(" )c GROUP BY c.ret1 ")
            .append(")d ,t_person e,t_user h WHERE d.ret1 = e.userId AND d.ret1=h.id").append(employeeFilter).append(" ) g ")
            //.append(awardCon)
            .append(" ) t1 ");
        } else {
        	sbtable.append("(SELECT * FROM ( ")
        	.append("SELECT d.ret1,h.userName, d.ct level1,e.realName,fmon sumAll, ")
        	.append("(SELECT  job FROM t_employee f WHERE  f.userId=d.ret1 and f.hasWork=1) isEmployee ")
        	.append("FROM ( SELECT c.ret1,ct,c.ct*").append(outMon).append(" fmon FROM ")
        	.append("( SELECT recommendUserId ret1,COUNT(recommendUserId) ct FROM ")
        	.append("( SELECT  a.id,b.recommendUserId FROM t_user a,t_recommend_user b ")
        	.append("WHERE EXISTS (SELECT f.investor FROM t_invest f WHERE f.investor = a.id ").append(itimeCondition).append(")")
        	.append("AND b.userId=a.id ").append(timeCondition)
        	.append(") g  GROUP BY recommendUserId ")
        	.append(")c GROUP BY c.ret1 ")
        	.append(")d ,t_person e ,t_user h ")
        	.append("WHERE d.ret1 = e.userId AND h.id=d.ret1 ) g WHERE 1=1  ) t1 ");
        }
        log.info(sbtable);
        
        try {
            dataPage(conn, pageBean, sbtable.toString(), " * ", " order by t1.ret1 ", condition.toString());
            
            //奖励总计
            DataSet ds =  Database.executeQuery(conn, "select SUM(sumAll) sumAward from "+sbtable.toString()+" where 1= 1  "+condition.toString());
            map = BeanMapUtils.dataSetToMap(ds);
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
		return map;
	}
	
	
	/**
	 *功能：投资奖励
	 * @param map
	 * @param pageBean
	 * @return
	 * @throws SQLException
	 */
	public Map retInvestAwardList(Map <String, String> map,PageBean pageBean) throws SQLException{
		String userName = map.get("userName");
		String realName = map.get("realName");
		String timeStart = map.get("memberTimes");
		String timeEnd = map.get("memberTimee");
		
		String sversion = map.get("sversion");
		String isEmployee = map.get("isEmployee");
		String awardFrom = map.get("awardFrom");
		String awardTo = map.get("awardTo");
		
        Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        if (StringUtils.isNotBlank(userName)) {
        	userName = Utility.filteSqlInfusion(userName);
            condition.append(" and t1.username like '%"+userName+"%'");
        }
        if (StringUtils.isNotBlank(realName)) {
        	realName = Utility.filteSqlInfusion(realName);
            condition.append(" and t1.realName like '%"+realName+"%'");
        }
        StringBuffer itimeCondition = new StringBuffer("");//投资时间条件 
        if (StringUtils.isNotBlank(timeStart)) {
            timeStart = Utility.filteSqlInfusion(timeStart);
            itimeCondition.append(" and a.investTime >='").append(timeStart).append("' ");
        }
        if (StringUtils.isNotBlank(timeEnd)) {
        	timeEnd = Utility.filteSqlInfusion(timeEnd);
        	itimeCondition.append(" and a.investTime <='").append(timeEnd).append("' ");
        }
        if (StringUtils.isNotBlank(isEmployee) && isEmployee.equals("1")) {
        	condition.append( " and t1.isEmployee is not null");
        } else if (StringUtils.isNotBlank(isEmployee) && isEmployee.equals("0")) {
        	condition.append( " and t1.isEmployee is null");
        } 
        
//        StringBuffer awardCon = new StringBuffer("where 1=1");
        if (StringUtils.isNotBlank(awardFrom)) {
        	awardFrom = Utility.filteSqlInfusion(awardFrom);
        	condition.append(" and t1.sumAll >= ").append(awardFrom);
        }
        if (StringUtils.isNotBlank(awardTo)) {
        	awardTo = Utility.filteSqlInfusion(awardTo);
        	condition.append(" and t1.sumAll <= ").append(awardTo);
        }
        
        StringBuffer sbtable = new StringBuffer();
        if (sversion.equals("v1")){//内部版
        	sbtable.append("(SELECT recommendUserId, k.username,h.realName, ")
        	.append("SUM(IF(mylevel = 1,investAmount,0)) AS investAmount1, ")
        	.append("SUM(IF(mylevel = 2,investAmount,0)) AS investAmount2, ")
        	.append("SUM(IF(mylevel = 1,money1,0)) AS mylevel1, ")
        	.append("SUM(IF(mylevel = 2,money1,0)) AS mylevel2, ")
        	.append("SUM(IF(mylevel = 1,money1,0)) + SUM(IF(mylevel = 2,money1,0)) sumAll, ")
        	.append("(SELECT job FROM t_employee WHERE userId = recommendUserId and hasWork=1) isEmployee ")
        	.append("FROM ( ")
        	.append("SELECT d.recommendUserId,c.*, ")
        	.append("CASE WHEN deadline<3 THEN (c.investAmount*0.01) *deadline/12 ")
        	.append("WHEN deadline<6 THEN (c.investAmount*0.012) *deadline/12 ")
        	.append("WHEN deadline<12 THEN (c.investAmount*0.016) *deadline/12 ")
        	.append("WHEN deadline<24 THEN (c.investAmount*0.02) *deadline/12 ")
        	.append("WHEN deadline>=24 THEN (c.investAmount*0.025) *deadline/12 ")
        	.append("END money1 ,1 mylevel ")
        	.append("FROM ")
        	.append("(SELECT  a.investAmount,a.deadline,a.investor, b.annualRate FROM t_invest a,t_borrow b WHERE  b.id= a.borrowId ")
        	.append(itimeCondition)
        	.append(" AND b.borrowStatus IN (2,3,4,5) ")
        	.append(") c ,t_recommend_user d ")
        	.append("WHERE c.investor = d.userId ")
        	.append("UNION ALL  ")
        	.append("SELECT f.recommendUserId, e.investAmount,e.deadline,e.investor,e.annualRate,  e.money1*0.2 money1,2 mylevel FROM ")
        	.append("( SELECT d.recommendUserId,c.*, ")
        	.append("CASE WHEN deadline<3 THEN (c.investAmount*0.01) *deadline/12 ")
        	.append("WHEN deadline<6 THEN (c.investAmount*0.012) *deadline/12 ")
        	.append("WHEN deadline<12 THEN (c.investAmount*0.016) *deadline/12 ")
        	.append("WHEN deadline<24 THEN (c.investAmount*0.02) *deadline/12 ")
        	.append("WHEN deadline>=24 THEN (c.investAmount*0.025) *deadline/12 ")
        	.append("END money1   FROM ")
        	.append("(SELECT  a.investAmount,a.deadline,a.investor, b.annualRate FROM t_invest a,t_borrow b WHERE b.id= a.borrowId ").append(itimeCondition)
        	.append(" AND b.borrowStatus IN (2,3,4,5) ")
        	.append(") c ,t_recommend_user d ")
        	.append("WHERE c.investor = d.userId ")
        	.append(")e,t_recommend_user f ")
        	.append("WHERE e.recommendUserId = f.userid ")
        	.append(") g,t_person h,t_user k WHERE h.userId=g.recommendUserId AND k.id=g.recommendUserId GROUP BY g.recommendUserId")
        	.append(") t1");
        }else{//外部版
        	sbtable.append("( SELECT recommendUserId, k.username,h.realName, SUM(investAmount) investAmount, SUM(money1) sumAll,  ")
        	.append("(SELECT job FROM t_employee WHERE userId = recommendUserId and hasWork=1) isEmployee ")
        	.append("FROM (")
        	.append("SELECT d.recommendUserId,c.*, ")
        	.append("CASE WHEN deadline<3 THEN (c.investAmount*0.0008) *deadline ")
        	.append("WHEN deadline<6 THEN (c.investAmount*0.001) *deadline ")
        	.append("WHEN deadline<12 THEN (c.investAmount*0.012) *deadline ")
        	.append("END money1 FROM ")
        	.append("(SELECT a.investAmount,a.deadline,a.investor, b.annualRate FROM t_invest a,t_borrow b ,t_user c ")
        	.append("WHERE c.id=a.investor AND b.id= a.borrowId AND b.borrowStatus IN (2,3,4,5) ")
        	.append("AND  DATE_ADD(c.createTime,INTERVAL 6 MONTH) > a.investTime ")
        	//.append("AND a.investTime>'2014-09-25' AND a.investTime < '2014-11-15' ")
        	.append(itimeCondition)
        	.append(") c ,t_recommend_user d ")
        	.append("WHERE c.investor = d.userId ")
        	.append(") g,t_person h,t_user k WHERE h.userId=g.recommendUserId AND k.id=g.recommendUserId GROUP BY g.recommendUserId")
        	.append(") t1");
        }
        
        log.info("investSQL:"+sbtable);
        
        try {
            dataPage(conn, pageBean, sbtable.toString(), " * ", " order by t1.recommendUserId ", condition.toString());
            
            //奖励总计
            DataSet ds =  Database.executeQuery(conn, "select SUM(sumAll) sumAward from "+sbtable.toString()+" where 1= 1  "+condition.toString());
            map = BeanMapUtils.dataSetToMap(ds);
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
		return map;
	}
	private static final int times = 5;
	/**
	 *功能：按月统计报表
	 * @param smonth
	 * @return
	 * @throws Exception 
	 */
	public Map alldayStaticsList(PageBean pagebean, Map<String, String> param) throws Exception{
		log.info("----service.alldayStaticsList----"+param);
		// 
		String month = param.get("monthsel");//"201412";//
		String dateStart = param.get("dateStart");//"2014-12-12";//
		String dateEnd = param.get("dateEnd");//"2014-12-13";//
		String pvs = param.get("pvs");
		String pve = param.get("pve");
		String uvs = param.get("uvs");
		String uve = param.get("uve");
		int days = 0;
		
		Map map = new HashMap();
		Connection conn = MySQL.getConnection();
		try {
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date s = sf.parse(dateStart);
				Date e = sf.parse(dateEnd);
				days = (int)((e.getTime() - s.getTime())/(24*60*60*1000)) + 1;
				log.info("---------days:" + days);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			List a = new ArrayList();
			int loops = days % times == 0 ? (days / times) : (days / times + 1);
			for (int i = 0 ; i < loops; i++){
				DataSet ds = new DataSet();
				List<Object> outParameterValues = new ArrayList<Object>();
				int vd = days > times ? times : days;
				days = days - times;
				log.info("---------dateStart=" + dateStart +"---vd:"+vd);
				Procedures.p_allday_static(conn, ds, outParameterValues,month,dateStart, dateEnd,pvs,pve,uvs,uve,vd);
				ds.tables.get(0).rows.genRowsMap();
	            a.addAll((List<Map<String, Object>>) ds.tables.get(0).rows.rowsMap);
	            dateStart = nextDate(dateStart);
			}
			
			//IP、UV、新注册用户数、新用户充值人数、有效客户数、新用户充值金额、当日充值金额、投标金额；
			//ip ,uv,todayRegNum todayregRecharge effectReg newUserRecharge todayregMoney allInvest
			
			int ipSum=0;
			int uvSum=0;
			int todayRegNumSum=0;
			int todayregRechargeSum=0;
			int effectRegSum=0;
			BigDecimal newUserRechargeSum = new BigDecimal("0");
			BigDecimal todayregMoneySum = new BigDecimal("0");
			BigDecimal allInvestSum = new BigDecimal("0");
			BigDecimal allRechargeSum = new BigDecimal("0");
			
			for (int i=0; i< a.size(); i++){
				Map record = (Map) a.get(i);
				String ipstr = "" + record.get("ip");
				String uvstr = "" + record.get("uv");
				
				Integer ip = 0;;
				try {
					ip = Integer.parseInt(ipstr);
					ipSum += ip;
				} catch (Exception e) {
					ip = 0;
				}
				
				Integer uv = 0;;
				try {
					uv = Integer.parseInt(uvstr);
					uvSum += uv;
				} catch (Exception e) {
					uv = 0;
				}
				
				
				//record.get("ip") ==null?0:(Integer) record.get("ip");
//				Integer uv = record.get("uv")==null?0:(Integer) record.get("uv");
				Long todayRegNum = (Long) record.get("todayRegNum")==null?0:(Long) record.get("todayRegNum");
				Long todayregRecharge = (Long) record.get("todayregRecharge")==null?0:(Long) record.get("todayregRecharge");
				Long effectReg = (Long) record.get("effectReg")==null?0:(Long) record.get("effectReg");
				BigDecimal newUserRecharge = (BigDecimal) record.get("newUserRecharge")==null?new BigDecimal(0):(BigDecimal) record.get("newUserRecharge");
				BigDecimal todayregMoney = (BigDecimal) record.get("todayregMoney")==null?new BigDecimal(0):(BigDecimal) record.get("todayregMoney");
				BigDecimal allInvest = (BigDecimal) record.get("allInvest")==null?new BigDecimal(0):(BigDecimal) record.get("allInvest");
				BigDecimal allRecharge = (BigDecimal) record.get("allWithdraw")==null?new BigDecimal(0):(BigDecimal) record.get("allWithdraw");
				
//				uvSum += uv;
				todayRegNumSum += todayRegNum;
				todayregRechargeSum += todayregRecharge;
				effectRegSum += effectReg;
				newUserRechargeSum = newUserRechargeSum.add(newUserRecharge);
				todayregMoneySum = todayregMoneySum.add(todayregMoney);
				allInvestSum = allInvestSum.add(allInvest);
				allRechargeSum = allRechargeSum.add(allRecharge);
			}
			Map <String,Object>retMap = new HashMap<String,Object>();
			retMap.put("ipSum", ipSum);
			retMap.put("uvSum", uvSum);
			retMap.put("todayRegNumSum", todayRegNumSum);
			retMap.put("todayregRechargeSum", todayregRechargeSum);
			retMap.put("effectRegSum", effectRegSum);
			retMap.put("newUserRechargeSum", newUserRechargeSum);
			retMap.put("todayregMoneySum",todayregMoneySum );
			retMap.put("allInvestSum", allInvestSum);
			retMap.put("allRechargeSum", allRechargeSum);
			
			pagebean.setTotalNum(a.size() );
			int lastd = (int)(pagebean.getPageSize()*(pagebean.pageNum-1) + pagebean.getPageSize()) > a.size() ? a.size() : (int)(pagebean.getPageSize()*(pagebean.pageNum-1) + pagebean.getPageSize());
            pagebean.setPage(a.subList((int)(pagebean.getPageSize()*(pagebean.pageNum-1)), lastd));
            
            log.info("-----list size:" + a.size());
            return retMap;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public static String nextDate(String dateStart) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
		    Date myDate = formatter.parse(dateStart);
		    Calendar c = Calendar.getInstance();
		    c.setTime(myDate);
		    c.add(Calendar.DATE, times);
		    myDate = c.getTime();
		    dateStart = formatter.format(myDate);
		    return dateStart;
		} catch (ParseException e1) {
		    e1.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 推荐有效客户投资明细
	 * @param pageBean
	 * @param id
	 * @param level
	 * @throws SQLException 
	 */
	public void recommendAwardDetialInfo(PageBean pageBean,String id,String level,String startInvestTime,String endInvestTime) throws SQLException{
		StringBuffer sql = new StringBuffer();
		// AND ti.`investTime` >= '2014-01-25 13:53:47' AND ti.`investTime`<='2015-05-25 13:54:01'
		Connection conn = MySQL.getConnection();
		if ("1".equals(level)) {
			sql.append("(SELECT tb.id, ti.`investAmount`, tu.`username`, tb.`borrowTitle`,  tp.`realName`,  (SELECT  tu.`username`  FROM  t_user tu  WHERE tu.id = tb.`publisher`) AS publisher, f_borrow_type (tb.`borrowWay`) AS borrowWay, ti.`investTime`, CASE  WHEN tb.`isDayThe` = 2   THEN '天'   WHEN tb.`isDayThe` = 1  THEN '月'  END AS isDayThe, tb.`deadline`  FROM t_invest ti  INNER JOIN   (SELECT    t.`id`    FROM  t_user t,  t_recommend_user tru  WHERE tru.`recommendUserId` = "+id+"   AND tru.`userId` = t.`id`) t   ON t.id = ti.`investor`  INNER JOIN t_borrow tb   ON ti.`borrowId` = tb.`id`   INNER JOIN t_user tu   ON tu.id = ti.`investor`  LEFT JOIN t_person tp  ON tp.`userId` = tu.`id` WHERE tb.`borrowStatus`!=6) tb");
		}else if("2".equals(level)){
			sql.append("(SELECT tb.id, ti.`investAmount`, tu.`username`, tb.`borrowTitle`, tp.`realName`, (SELECT    tu.`username`  FROM   t_user tu  WHERE tu.id = tb.`publisher`) AS publisher, f_borrow_type (tb.`borrowWay`) AS borrowWay, ti.`investTime`, CASE   WHEN tb.`isDayThe` = 2    THEN '天'     WHEN tb.`isDayThe` = 1     THEN '月'   END AS isDayThe,  tb.`deadline` FROM t_invest ti  INNER JOIN (SELECT  tu2.`id`, tu2.`username`, t2.`recommendUserId`,  t2.recommend_username FROM t_user tu2  INNER JOIN   (SELECT   tru2.`userId`,  tru2.`recommendUserId`,  ti.username AS recommend_username   FROM  t_recommend_user tru2   INNER JOIN  (SELECT   tu.`id`,  tu.`username`    FROM   t_user tu   INNER JOIN   (SELECT   tru.`userId`   FROM  t_recommend_user tru   WHERE tru.`recommendUserId` = "+id+") t      ON t.userId = tu.`id`) ti     ON ti.id = tru2.`recommendUserId`) t2    ON tu2.`id` = t2.userId   ) tuser2 ON tuser2.id = ti.investor INNER JOIN t_borrow tb     ON ti.`borrowId` = tb.`id`   INNER JOIN t_user tu   ON tu.id = ti.`investor`  LEFT JOIN t_person tp  ON tp.`userId` = tu.`id` WHERE tb.`borrowStatus`!=6) tb ");
		}
		String where = "";
		if (StringUtils.isNotBlank(startInvestTime) && StringUtils.isNotBlank(endInvestTime)) {
			where = " and tb.`investTime` >= '"+startInvestTime+"' AND tb.`investTime`<='"+endInvestTime+"'";
		}
		try {
			dataPage(conn, pageBean, sql.toString(), "*", " order by id desc", where);
		} catch (Exception e) {
			log.error(e);
		}finally{
			conn.close();
		}
	}
	
}
