package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.allinpay.ets.client.StringUtil;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.web.Utility;
import com.sp2p.dao.SysparDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_risk_detail;
import com.sp2p.database.Dao.Tables.t_user;
/**
 * 注意：使用系统参数的时候，下拉框的值必须是参数表中的selectKey。否则会出现数据冲突的情况
 * @author hejiahua
 *
 */
public class SysparService {
    private SysparDao sysparDao;
    private static Log log = LogFactory.getLog(SysparService.class);
    
    public SysparDao getSysparDao() {
        return sysparDao;
    }

    public void setSysparDao(SysparDao sysparDao) {
        this.sysparDao = sysparDao;
    }
    /**
     * 查询系统参数
     * querySysparChild
     * @auth hejiahua
     * @param map
     * @param fieldList
     * @param condition
     * @param order
     * @param limitCount
     * @param limitStart
     * @throws SQLException 
     * void
     * @exception 
     * @date:2014-9-3 上午9:01:40
     * @since  1.0.0
     */
    public Map<String, String>  querySysparChild(String fieldList,String selectKey,String order,int limitCount,int limitStart) throws SQLException {
        Connection conn = MySQL.getConnection();
        try {
            return  sysparDao.querySysparChild(conn,fieldList,Utility.filteSqlInfusion(selectKey),order,limitCount,limitStart);
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return null;
    }
    /**
     * id查询子参数
     * querySysparChildById
     * @auth hejiahua
     * @param fieldList
     * @param id
     * @return
     * @throws SQLException 
     * Map<String,String>
     * @exception 
     * @date:2014-10-9 下午4:42:18
     * @since  1.0.0
     */
    public Map<String, String>  querySysparChildById(String fieldList,int id) throws SQLException {
        Connection conn = MySQL.getConnection();
        try {
            return  sysparDao.querySysparChildById(conn,fieldList,id);
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return null;
    }
    
    /**
     * 查询某个类型的所有子参数
     * querySysparAllChild
     * @auth hejiahua
     * @param fieldList
     * @param condition
     * @param order
     * @param limitCount
     * @param limitStart
     * @return 
     * List<Map<String,Object>>
     * @throws SQLException 
     * @exception 
     * @date:2014-10-9 下午2:19:12
     * @since  1.0.0
     */
    public List<Map<String , Object>>  querySysparAllChild(String fieldList,String condition,String order,int limitCount,int limitStart) throws SQLException{
        Connection conn = MySQL.getConnection();
        try {
            return  sysparDao.querySysparAllChild(conn,fieldList,condition,order,limitCount,limitStart);
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return null;
    }
    
    /**
     * 自定义查询SQL
     * querySysparBySql
     * @auth hejiahua
     * @param sql
     * @return
     * @throws SQLException 
     * Map<String,String>
     * @exception 
     * @date:2014-12-22 上午9:45:12
     * @since  1.0.0
     */
    public Map<String, String> querySysparBySql(String sql) throws SQLException{
        Connection conn = MySQL.getConnection();
        Map<String, String> map = null;
        try {
            DataSet ds =  Database.executeQuery(conn, sql); 
            map = BeanMapUtils.dataSetToMap(ds);
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally{
            conn.close();
        }
        return map;
    }
    
    /**
     * 系统参数是否开启
     * @param selectKey
     * @param typeId
     * @return
     * @throws SQLException
     */
    public boolean isOpen(String selectKey,String typeId) throws SQLException{
    	Map<String, String> result =  querySysparByParams(typeId, selectKey, true);
    	if (result == null) {
    		return false;
		}else {
			return true;
		}
    }
    
    public static void main(String[] args) throws SQLException {
		System.out.println(querySysparByParams("25", "OPEN", true));;
	}
    
    /**
     * 查询系统参数的子参数
     * @param typeId  系统参数ID(建议传入)
     * @param selectKey  子参数的Key(必填参数)
     * @param isOpen  是否开启(自选参数)
     * @param objects  查询的参数值</br>
     * objects={id  编号,
     * typeId   列表类型id,
     * selectKey   参数码，唯一。用于查询,
     * selectValue   下拉值,
     * selectName   下拉名称,
     * deleted  默认1 启用 2 未启用,
     * introduce  介绍或备注
     * }
     * @return
     * @throws SQLException
     */
    public static Map<String,String> querySysparByParams(String typeId,String selectKey,boolean isOpen,Object ... objects) throws SQLException{
    	Connection conn = MySQL.getConnection();
    	Map<String, String> map = null;
    	try {
			StringBuffer sql = new StringBuffer();
			if (objects==null || objects.length==0) {
				sql.append("select * from t_select where 1=1 ");
			}else {
				sql.append("select ");
				for (int i = 0; i < objects.length; i++) {
					sql.append(objects[i]);
					if (i!=objects.length-1) {
						sql.append(",");
					}
				}
				sql.append(" from t_select where 1=1 ");
			}
			if (StringUtils.isNotBlank(typeId)) {
				sql.append(" and typeId = ").append(typeId);
			}
			if (StringUtils.isNotBlank(selectKey)) {
				sql.append(" and selectKey = '").append(selectKey).append("'");
			}
			if (isOpen) {
				sql.append(" and deleted = 1 ");
			}else {
				sql.append(" and  deleted = 2 ");
			}
			System.out.println(sql.toString());
			DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
			map = BeanMapUtils.dataSetToMap(dataSet);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
    	return map;
    }
    
    /**
	 *功能：可否参加注册抽奖
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getAwardJudge(String id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Map<String,  String> map  = null;
	    try {
            map = sysparDao.getAwardJudge(conn, id);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.info(e);
        }finally{
            conn.close();
        }
	    return map;
	}
	
	/**
	 *功能：可否参加注册抽奖
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public void addUserSore(Long userId) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
            sysparDao.addUserSore(conn, userId);
    		String sqlStr = "update t_user set sumScore = sumScore+1000 where id="+userId;
    		MySQL.executeNonQuery(conn, sqlStr);
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.info(e);
        }finally{
            conn.close();
        }
	}
	
	
}
