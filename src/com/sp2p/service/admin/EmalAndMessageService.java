package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.zxing.Result;
import com.sp2p.dao.admin.EmalAndMessageDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables.t_select;
import com.sp2p.database.Dao.Tables.t_selecttype;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.Utility;

public class EmalAndMessageService extends BaseService {
	public static Log log = LogFactory.getLog(EmalAndMessageService.class);

	private EmalAndMessageDao emalAndMessageDao;

	public void setEmalAndMessageDao(EmalAndMessageDao emalAndMessageDao) {
		this.emalAndMessageDao = emalAndMessageDao;
	}

	/**
	 * 插入邮件设置表
	 * 
	 * @param mailaddress
	 * @param mailpassword
	 * @param sendmail
	 * @param sendname
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUserWorkData(String mailaddress, String mailpassword,
			String sendmail, String sendname, String port, String host)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addMailSet(conn, mailaddress,
					mailpassword, sendmail, sendname, port, host);
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
	 * 添加或者修改短信设置
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @param url
	 * @param enable
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMessageSet(Long id, String username, String password,
			String url, Integer enable) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addMessageSet(conn, id, username,
					password, url, enable);
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
	public Long updateMessageSet(int id,String mailaddress,
			String mailpassword, String sendmail, String sendname,String port,String host) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addMailSetu(conn,id,mailaddress,
					mailpassword, sendmail, sendname,port,host);
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

	public Long addTarage(String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addTarage(conn, name);
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
	 * 增加担保方式
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addDan(String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addDan(conn, name);
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
	 * 增加反担保机构
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long addInver(String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addInver(conn, name);
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
	 * 添加系统头像
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long addSysImage(String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addSysImage(conn, name);
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

	public Long updateTage(Long id, String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.updateTage(conn, id, name);
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
	 * 修改担保机构
	 * 
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long updateAccount(Long id, String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.updateAccount(conn, id, name);
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
	 * 更改系统头像
	 * 
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long updateSysImage(Long id, String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.updateSysImage(conn, id, name);
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
	 * 修改投标金额
	 * 
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long updateInvers(Long id, String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.updateAccount(conn, id, name);
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
	 * 修改借款期限
	 * 
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long updateDeadline(Long id, String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.updateDeadline(conn, id, name);
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
	 * 更新某类型的系统参数
	 * updateSysparChild(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @param id
	 * @param selectName
	 * @param selectValue
	 * @param deleted
	 * @return
	 * @throws SQLException 
	 * Long
	 * @exception 
	 * @date:2014-9-2 下午6:10:40
	 * @since  1.0.0
	 */
	public Long updateSysparChild(String id,String selectName,String selectValue,String deleted,String introduce) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result =-1L;
	    try {
            result = emalAndMessageDao.updateSysparChild(conn, Utility.filteSqlInfusion(id), Utility.filteSqlInfusion(selectName), Utility.filteSqlInfusion(selectValue), Utility.filteSqlInfusion(deleted), Utility.filteSqlInfusion(introduce));
            conn.commit();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	public Long deleteTage(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.deleteTage(conn, id);
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
	 * 删除反担保fangsh
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long deleteacc(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.deleteacc(conn, id);
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
	 * 删除系统参数类型
	 * @throws SQLException 
	 */
	public Long deleteSyspar(String id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result = -1L;
	    try {
	        result =emalAndMessageDao.deleteSyspar(conn, id);
	        conn.commit();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();
           }
	    finally{
	        conn.close();
	    }
	    return result;
	}
	/**
     * 删除某类型系统参数
     * @throws SQLException 
     */
    public Long deleteSysparChild(String id) throws SQLException{
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            result =emalAndMessageDao.deleteSysparChild(conn, id);
            conn.commit();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();
           }
        finally{
            conn.close();
        }
        return result;
    }
	/**
	 * 删除系统头像
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long deletSImage(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.deletSImage(conn, id);
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
	 * 查询信息设置
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws Exception
	 */
	public Map<String, String> queryMessageSet(long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = emalAndMessageDao.queryMessageSet(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		}finally {
			conn.close();
		}
		return map;
	}

	/**
	 * 得到当前最大的ID
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryMailsetMaxId() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = emalAndMessageDao.queryMailsetMaxId(conn);
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

	public Map<String, String> queryMailSet(long id) throws DataException,
			Exception {
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = emalAndMessageDao.queryMailSet(conn, id);
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
	 *功能：删除指定邮件
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws Exception
	 */
	public Map<String, String> deleteMail(long id) throws DataException,
			Exception {
		Connection conn = MySQL.getConnection();

//		Map<String, String> map = new HashMap<String, String>();
		try {
			emalAndMessageDao.deleteMail(conn, id);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return null;
	}
	/**
	 * 查询借款目的内容
	 * 
	 * @param pageBean
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> querySelectInfo(
			PageBean<Map<String, Object>> pageBean) throws Exception {
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND ts.typeId = 1 AND ts.deleted = 1");
		/*
		 * if(userId!=null&&userId>0){ condition.append(" AND id = "+userId); }
		 */
		// =============================
		StringBuffer sqlresult = new StringBuffer();
		sqlresult.append(" ts.id as 'id' , ");
		sqlresult.append(" ts.selectName as 'name' ");
		// ==========================
		StringBuffer sql = new StringBuffer();
		sql.append(" t_select ts ");
		// ================================
		try {
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(),
					" order by id ", condition.toString());
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
	 * 查询反担保方式
	 * 
	 * @param pageBean
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryIversInof(
			PageBean<Map<String, Object>> pageBean) throws Exception {
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND ts.typeId = 3");
		StringBuffer sql = new StringBuffer();
		sql.append(" t_select ts ");
		try {
			dataPage(conn, pageBean, sql.toString(), " * ", " order by id ",
					condition.toString());
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
	 * 查询系统头像
	 * 
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> querySysImageInfo(
			PageBean<Map<String, Object>> pageBean) throws Exception {
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND ts.enable = 1 ");
		/*
		 * if(userId!=null&&userId>0){ condition.append(" AND id = "+userId); }
		 */
		// =============================
		StringBuffer sqlresult = new StringBuffer();
		sqlresult.append(" ts.id as id , ");
		sqlresult.append(" ts.imagePath as imagePath ");
		// ==========================
		StringBuffer sql = new StringBuffer();
		sql.append(" t_sysimages ts ");
		// ================================
		try {
			dataPage(conn, pageBean, sql.toString(), sqlresult.toString(),
					" order by id ", condition.toString());
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
	 * 查询机构担保列表
	 * 
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAccountInfo(
			PageBean<Map<String, Object>> pageBean) throws Exception {
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND typeId = 2 ");
		try {
			dataPage(conn, pageBean, " t_select ", " * ", " order by id ",
					condition.toString());
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
	 * 修改
	 * 
	 * @param type
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long updateSelectType(int type, long id) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = emalAndMessageDao.updateSelectType(conn, type, id);
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
	 * 查询所有担保机构
	 * 
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryinstitution() throws Exception {
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		try {
			map = emalAndMessageDao.queryinstitution(conn);
			conn.commit();
		} catch (Exception e) {
			log.equals(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	/**
	 * 查询所有反担保方式
	 * 
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryguarantee() throws Exception {

		List<Map<String, Object>> map = null;

		Connection conn = MySQL.getConnection();
		try {
			map = emalAndMessageDao.queryguarantee(conn);
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
	 * 查询借款期限列表
	 * 
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryDeadlineInfo(
			PageBean<Map<String, Object>> pageBean) throws Exception {
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND typeId = 4 ");
		try {
			dataPage(conn, pageBean, " t_select ", " * ",
					" order by selectValue ", condition.toString());
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
	 * 查询金额范围列表
	 * 
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMomeyInfo(
			PageBean<Map<String, Object>> pageBean) throws Exception {
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append(" AND typeId = 5 ");
		try {
			dataPage(conn, pageBean, " t_select ", " * ",
					" order by selectValue ", condition.toString());
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
	 * 修改金额范围 和借款期限
	 * 
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long updateMoney(Long id, String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.updateMoney(conn, id, name);
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
	 * 增加借款期限和金额范围
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long addDeadline(String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addDeadline(conn, name);
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
	 * 增加借款期限和金额范围
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 * @throws DataException
	 */
	public Long addMoney(String name) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = emalAndMessageDao.addMomey(conn, name);
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
	 * 添加系统参数类别
	 * addSyspar
	 * @auth hejiahua
	 * @param typeName
	 * @return
	 * @throws SQLException 
	 * Long
	 * @exception 
	 * @date:2014-9-2 上午8:35:08
	 * @since  1.0.0
	 */
	public Long addSyspar(String typeName) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result = -1L;
	    try {
	        result = this.emalAndMessageDao.addSyspar(conn, typeName);
	        conn.commit();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	/**
     * 添加系统参数类别
     * addSyspar
     * @auth hejiahua
     * @param typeName
     * @return
     * @throws SQLException 
     * Long
     * @exception 
     * @date:2014-9-2 上午8:35:08
     * @since  1.0.0
     */
    public Long addSyspar(String typeName,String introduce) throws SQLException{
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            result = this.emalAndMessageDao.addSyspar(conn, typeName,introduce);
            conn.commit();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();
        }finally{
            conn.close();
        }
        return result;
    }
	
	/**
	 * 查询系统参数类型
	 * querySysparInfo(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @param pageBean
	 * @throws SQLException 
	 * void
	 * @exception 
	 * @date:2014-9-2 下午3:06:45
	 * @since  1.0.0
	 */
	public void querySysparInfo(PageBean pageBean) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
            dataPage(conn, pageBean, " t_selecttype ", " * ", " order by id desc ", "");
        }
        catch (Exception e) {
             log.error(e);
             e.printStackTrace();
        }finally{
            conn.close();
        }
	}
	
	/**
     * 查询系统参数类型
     * querySysparInfo(这里用一句话描述这个方法的作用)
     * (这里描述这个方法适用条件 – 可选)
     * @auth hejiahua
     * @param pageBean
     * @throws SQLException 
     * void
     * @exception 
     * @date:2014-9-2 下午3:06:45
     * @since  1.0.0
     */
    public void querySysparInfo(PageBean pageBean,String typeId) throws SQLException{
        Connection conn = MySQL.getConnection();
        try {
            dataPage(conn, pageBean, " t_selecttype ", " * ", " order by id desc ", " and id="+typeId);
        }
        catch (Exception e) {
             log.error(e);
             e.printStackTrace();
        }finally{
            conn.close();
        }
    }
	
	/**
	 * 查询某一类别系统参数
	 * querySyspar(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @param typeId
	 * @param pageBean
	 * @throws SQLException 
	 * void
	 * @exception 
	 * @date:2014-9-2 下午3:07:05
	 * @since  1.0.0
	 */
    public void querySyspar(String typeId,PageBean pageBean) throws SQLException {
            Connection conn = MySQL.getConnection();
            try {
                dataPage(conn, pageBean, "t_select", "*", " order by id desc    ", "  and  typeId ="+Convert.strToInt(typeId, 0) +" ");
            }
            catch (Exception e) {
               log.error(e);
               e.printStackTrace();
            }finally{
                conn.close();
            }
    }
    
    
    public int querySysparByKey(String selectKey) throws SQLException {
        Connection conn = MySQL.getConnection();
        try {
            Dao.Tables.t_select select = new Dao().new Tables().new t_select();
            DataSet ds =  select.open(conn, "*", " selectKey ='"+Utility.filteSqlInfusion(selectKey)+"' ", "", -1, -1);
            Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
            if (map==null ) {
                return 0;
            }else{
                return map.size();
            }
        }
        catch (Exception e) {
           log.error(e);
           e.printStackTrace();
        }finally{
            conn.close();
        }
        return 0;
}
    /**
     * 新增某一类别的系统参数
     * addSysparChild(这里用一句话描述这个方法的作用)
     * 此方法不做唯一判断。
     * @auth hejiahua
     * @param typeId
     * @param selectValue
     * @param selectName
     * @param deleted
     * @return
     * @throws SQLException 
     * Long
     * @exception 
     * @date:2014-9-2 下午3:08:18
     * @since  1.0.0
     */
    public Long addSysparChild(String typeId,String selectValue, String selectName,String selectKey,String deleted,String introduce) throws SQLException {
       Connection conn = MySQL.getConnection();
       Long result = -1L;
       try {
           Dao.Tables.t_select select = new Dao().new Tables().new t_select();
           //select.open(conn, "*", "", "", 0, -1);
           select.deleted.setValue(deleted);
           select.selectName.setValue(selectName);
           select.selectValue.setValue(selectValue);
           select.selectKey.setValue(selectKey);
           select.typeId.setValue(typeId);
           select.introduce.setValue(introduce);
           result = select.insert(conn);
           conn.commit();
       }
       catch (Exception e) {
           log.error(e);
           e.printStackTrace();
           conn.rollback();
       }finally{
           conn.close();
       }
       return result;
   }
    
    /**
     * 更新系统参数类型
     * updateSyspar(这里用一句话描述这个方法的作用)
     * (这里描述这个方法适用条件 – 可选)
     * @auth hejiahua
     * @param id
     * @param typeName
     * @return 
     * Long
     * @throws SQLException 
     * @exception 
     * @date:2014-9-2 下午3:21:10
     * @since  1.0.0
     */
    public Long updateSyspar(String id,String typeName) throws SQLException{
            Connection conn = MySQL.getConnection();
            Long result = -1L;
            try {
                Dao.Tables.t_selecttype selecttype = new Dao().new Tables().new t_selecttype();
                selecttype._name.setValue(typeName);
                result = selecttype.update(conn, " id ="+id);
                conn.commit();
            }
            catch (Exception e) {
                log.error(e);
                e.printStackTrace();
                conn.rollback();
            }finally{
                conn.close();
            }
            return result;
    }
    public Long updateSyspar(String id,String typeName,String  introduce) throws SQLException{
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            Dao.Tables.t_selecttype selecttype = new Dao().new Tables().new t_selecttype();
            selecttype._name.setValue(typeName);
            selecttype._introduce.setValue(introduce);
            result = selecttype.update(conn, " id ="+id);
            conn.commit();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            conn.rollback();
        }finally{
            conn.close();
        }
        return result;
    }
    
    /**
     *功能：查找email_List
     * @param pageBean
     * @return
     * @throws SQLException
     */
    public void emailAndMessagelist(PageBean<Map<String, Object>> pageBean) throws SQLException{
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " t_mailset ", " * ", " order by id ", "");
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
    
    /**
     *功能：查找paramlist
     * @param pageBean
     * @return
     * @throws SQLException
     */
    public void queryParamlist(PageBean<Map<String, Object>> pageBean) throws SQLException{
		List<Map<String, Object>> map = null;
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, " t_param ", " * ", " order by id ", "");
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
    
    /**
     *功能：查找email_List
     * @param pageBean
     * @return
     * @throws SQLException
     */
    public void updateParamById(Map map) {
    	String bakValue = map.get("bakValue") == null ? "":(String) map.get("bakValue");
    	String bizValue = map.get("bizValue") == null ? "":(String) map.get("bizValue");
    	String dateValue = map.get("dateValue") == null ? "":(String) map.get("dateValue");
    	String feeValue = map.get("feeValue") == null ? "":(String) map.get("feeValue");
    	String iValue = map.get("iValue") == null ? "":(String) map.get("iValue");
    	String remark = map.get("remark") == null ? "":(String) map.get("remark");
    	String id = (String) map.get("id");
    	
    	StringBuffer command = new StringBuffer();
    	
		command.append("UPDATE t_param a SET a.bakValue='")
		.append(bakValue)
		.append("',a.bizValue='")
		.append(bizValue)
		.append("',");
		if (StringUtils.isNotEmpty(dateValue)){
			command.append("a.dateValue='")
			.append(dateValue)
			.append("',");
		}
		if (StringUtils.isNotEmpty(feeValue)){
			command.append("a.feeValue=")
			.append(feeValue)
			.append("',");
		}
		if (StringUtils.isNotEmpty(iValue)){
			command.append("a.iValue=")
			.append(iValue)
			.append("',");
		}
		command.append("a.remark='")
		.append(remark)
		.append("' WHERE a.id=").append(id);
		log.info("sql:" + command.toString());
		Connection conn = MySQL.getConnection();
		try {
			
			MySQL.executeNonQuery(conn, command.toString());
			command = null;
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
}
