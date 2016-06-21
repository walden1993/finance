package com.sp2p.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;

public class ApproveDao {
	
	
	/**
	 * 忘记交易密码
	 * 
	 * @param conn
	 * @param username
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querytrancePassword(Connection conn, String email)
			throws SQLException, DataException {
		email=com.shove.web.Utility.filteSqlInfusion(email);
		StringBuffer condition = new StringBuffer();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "", " email = '" +StringEscapeUtils.escapeSql( email) + "'", "",
				-1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 修改用户交易密码
	 * 
	 * @param conn
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserTrancePassword(Connection conn, Long id, String dealpwd)
			throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if ("1".equals(IConstants.ENABLED_PASS)){
		    dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim());
		}else{
		    dealpwd = com.shove.security.Encrypt.MD5(dealpwd.trim()+IConstants.PASS_KEY);
		}
		try {
		    DataSet dataSet = user.open(conn, "*", " id=" + id, " id desc ", -1, -1);
		    Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		    String password = map.get("password");
            if (dealpwd.equals(password)) {//如果登录密码等于交易密码
                return 10L;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
		user.dealpwd.setValue(dealpwd);
		return user.update(conn, " id=" + id);
	}
	
	

}
