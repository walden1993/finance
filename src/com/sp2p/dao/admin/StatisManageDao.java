package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;

/**
 * @ClassName: AfterCreditManageDao.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:16:48
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 统计管理数据处理层
 */
public class StatisManageDao {

	public Map<String, String> queryBorrowStatisAmount(Connection conn,
			String borrowTitle, String borrower, String timeStart,
			String timeEnd, int borrowWayInt) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		borrowTitle=com.shove.web.Utility.filteSqlInfusion(borrowTitle);
		borrower=com.shove.web.Utility.filteSqlInfusion(borrower);
		timeEnd=com.shove.web.Utility.filteSqlInfusion(timeEnd);
		timeStart=com.shove.web.Utility.filteSqlInfusion(timeStart);
		command.append("SELECT SUM(manageFee) amount FROM v_t_borrow_statis where 1=1");
		if (StringUtils.isNotBlank(borrowTitle)) {
			command.append(" and  borrowTitle  like '%"+StringEscapeUtils.escapeSql(borrowTitle.trim())+"%' ");
		}
		if (StringUtils.isNotBlank(borrower)) {
			command.append(" and borrower  like '%"+ StringEscapeUtils.escapeSql(borrower.trim()) + "%'");
		}
		if (StringUtils.isNotBlank(timeStart)) {
			command.append(" and auditTime >= '" + StringEscapeUtils.escapeSql(timeStart) + "'");
		}
		if (StringUtils.isNotBlank(timeEnd)) {
			command.append(" and auditTime <= '" + StringEscapeUtils.escapeSql(timeEnd) + "'");
		}
		if (borrowWayInt != -1) {
			command.append(" and borrowWay = " + borrowWayInt);
		}
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryUserInfo(Connection conn, String sql) throws DataException{
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	

}
