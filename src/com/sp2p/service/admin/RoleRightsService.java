package com.sp2p.service.admin;import java.sql.Connection;import java.sql.SQLException;import java.util.ArrayList;import java.util.List;import java.util.Map;import org.apache.commons.lang.StringUtils;import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;import com.shove.base.BaseService;import com.shove.data.DataException;import com.shove.data.DataSet;import com.shove.data.dao.MySQL;import com.sp2p.constants.IConstants;import com.sp2p.dao.admin.RoleRightsDao;import com.sp2p.database.Dao;import com.sp2p.database.Dao.Tables;import com.sp2p.database.Dao.Tables.bt_rights;public class RoleRightsService extends BaseService {    public static Log log = LogFactory.getLog(RoleRightsService.class);    private RoleRightsDao roleRightsDao;    /**     * 根据角色编号查询角色权限信息     *      * @param roleId     *            角色编号     * @return     * @throws DataException     * @throws SQLException     */    public List<Map<String, Object>> queryRoleRightByRoleId(long roleId)            throws Exception {        Connection conn = MySQL.getConnection();        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();        try {            list = roleRightsDao.queryRoleRightByRoleId(conn, roleId);        }        catch (Exception e) {            log.error(e);            e.printStackTrace();            throw e;        }        finally {            conn.close();        }        return list;    }    /**     * 根据角色编号查询角色权限信息     *      * @param roleId     *            角色编号     * @return     * @throws Exception     * @throws Exception     */    public String queryRoleRightsIdByRoleId(long roleId) throws Exception,            Exception {        Connection conn = MySQL.getConnection();        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();        try {            list = roleRightsDao.queryRoleRightsIdByRoleId(conn, roleId);        }        catch (Exception e) {            log.error(e);            e.printStackTrace();            throw e;        }        finally {            conn.close();        }        String[] rightIds = new String[list.size()];        int i = 0;        for (Map<String, Object> map : list) {            rightIds[i] = map.get("rightsId").toString();            i++;        }        return StringUtils.join(rightIds, ",");    }    /**     * 根据角色编号查询管理员角色权限信息     *      * @param roleId     *            角色编号     * @return     * @throws Exception     * @throws Exception     */    public List<Map<String, Object>> queryAdminRoleRightMenu(long roleId)            throws Exception {        Connection conn = MySQL.getConnection();        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();        try {            list = roleRightsDao.queryAdminRoleRightMenu(conn, roleId);        }        catch (Exception e) {            log.error(e);            e.printStackTrace();            throw e;        }        finally {            conn.close();        }        return list;    }    /**     * 根据角色编号查询管理员角色权限信息     *      * @param roleId     *            角色编号     * @param rightsId     *            功能编号     * @return     * @throws Exception     * @throws Exception     */    public List<Map<String, Object>> queryAdminRoleRightMenu(long roleId,                                                             String rightsId)            throws Exception {        Connection conn = MySQL.getConnection();        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();        try {            list = roleRightsDao.queryAdminRoleRightMenu(conn, roleId, rightsId);        }        catch (Exception e) {            log.error(e);            e.printStackTrace();            throw e;        }        finally {            conn.close();        }        return list;    }    /**     * 查询角色是否有权限操作该路径     *      * @param roleId     *            角色编号     * @return 返回ture表示有权限，否则没有权限     * @throws Exception     * @throws Exception     */    public boolean queryAdminRoleIsHaveRights(long roleId, String url)            throws Exception {        Connection conn = MySQL.getConnection();        boolean reslut = false;        try {            reslut = roleRightsDao.queryAdminRoleIsHaveRights(conn, roleId, url);        }        catch (Exception e) {            log.error(e);            e.printStackTrace();            throw e;        }        finally {            conn.close();        }        return reslut;    }    /**     * 查询所有的功能列表 queryAllRights(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)     *      * @throws SQLException     * @auth hejiahua void     * @exception     * @date:2014-9-26 上午10:06:15     * @since 1.0.0     */    public List<Map<String, Object>> queryAllRights() throws SQLException {        Connection conn = MySQL.getConnection();        try {            Dao.Tables.bt_rights rights = new Dao().new Tables().new bt_rights();            DataSet dataSet = rights.open(conn,                                          "",                                          " ",                                          " id " + IConstants.SORT_TYPE_DESC,                                          -1,                                          -1);            dataSet.tables.get(0).rows.genRowsMap();            return dataSet.tables.get(0).rows.rowsMap;        }        catch (Exception e) {            e.printStackTrace();            log.error(e);        }        finally {            conn.close();        }        return null;    }    /**     * 添加功能 addRights(这里用一句话描述这个方法的作用)     *      * @auth hejiahua     * @param map     * @return     * @throws SQLException     *             long     * @exception     * @date:2014-9-26 上午11:31:20     * @since 1.0.0     */    public long addRights(Map<String, String> map) throws SQLException {        Connection conn = MySQL.getConnection();        long result = -1;        try {            Dao.Tables.bt_rights rights = new Dao().new Tables().new bt_rights();            rights._name.setValue(map.get("name"));            rights.action.setValue(map.get("action"));            rights.summary.setValue(map.get("summary"));            rights.indexs.setValue(map.get("indexs"));            rights.isIntercept.setValue(map.get("isIntercept"));            rights.isLog.setValue(map.get("isLog"));            rights.isQuery.setValue(map.get("isQuery"));            rights.parentID.setValue(map.get("parentID"));            rights.description.setValue(map.get("description"));            result = rights.insert(conn);            if (result<0) {                conn.rollback();            }else {                conn.commit();            }        }        catch (Exception e) {            e.printStackTrace();            conn.rollback();            log.error(e);        }        finally {            conn.close();        }        return result;    }        /**     * 添加权限     * addRoles     * @auth hejiahua     * @param rights     * @return     * @throws SQLException      * long     * @exception      * @date:2014-10-22 下午8:42:45     * @since  1.0.0     */    public long addRoles(long rights) throws SQLException {        Connection conn = MySQL.getConnection();        long result = -1;        try {            Dao.Tables.t_role_rights role = new Dao().new Tables().new t_role_rights();            role.rightsId.setValue(rights);            role.roleId.setValue(-1);            result = role.insert(conn);            if (result<0) {                conn.rollback();            }else {                conn.commit();            }        }        catch (Exception e) {            e.printStackTrace();            conn.rollback();            log.error(e);        }        finally {            conn.close();        }        return result;    }    public void setRoleRightsDao(RoleRightsDao roleRightsDao) {        this.roleRightsDao = roleRightsDao;    }}