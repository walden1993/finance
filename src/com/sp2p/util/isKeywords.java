package com.sp2p.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
 

import com.shove.data.DataSet;
import com.shove.data.dao.MySQL; 
import com.shove.util.BeanMapUtils;
import com.shove.util.SqlInfusion;
import com.sp2p.database.Dao;

public class isKeywords {

	 
	
	/**
	 * 判断是否存在关键字
	 * @param 输入值
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean isKeywordsOnDB(String str) throws SQLException
	{
		str = str.replaceAll(" ", "");
		Connection conn = MySQL.getConnection();
		boolean isKeyword = false;//是否是关键字
		try {
			Dao.Tables.t_keywords list =  new Dao().new Tables().new t_keywords();
			DataSet dataSet = list.open(conn, "sum(INSTR('"+SqlInfusion.FilteSqlInfusion(str)+"',keyword)) as keywordscount", "isuse = 1", "", -1, -1);
			
			if (dataSet == null)
			{
				return false;
			}
			
			if (dataSet.tables.get(0).rows.getCount() < 1)
			{
				isKeyword = false;
			}
			
			
			Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
			Long keywordscount = com.shove.Convert.strToLong(map.get("keywordscount"), 0);
			if (keywordscount > 0 )
			{
				isKeyword = true;
			}
			else
			{
				isKeyword = false;
			}
 
			
		} catch (Exception e) {
			conn.rollback();
			return false;
		}finally{
			conn.close();
		}
		
		 
		return isKeyword;
	}
}
