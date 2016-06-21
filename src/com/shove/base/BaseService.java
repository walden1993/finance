package com.shove.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;

import com.shove.config.DataBaseConfig;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.sp2p.database.Dao.Procedures;

/**
 * 基类
 *
 */
public class BaseService {
	
	public static ConnectionManager connectionManager;

	public static DataBaseConfig dateManager;
	
	/**
	 * 通用的获取单个查询方法
	 * @Description: TODO
	 * @param @param conn
	 * @param @param sql
	 * @param @return   
	 * @return Map<String,String>  
	 * @author hjh
	 * @date 2016-2-23
	 */
	public Map<String, String> sqlToMap(Connection conn,String sql){
		DataSet ds = new DataSet();
		ds = MySQL.executeQuery(conn, sql);
		try {
			return BeanMapUtils.dataSetToMap(ds);
		} catch (DataException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通用的获取集合查询方法
	 * @Description: TODO
	 * @param @param conn
	 * @param @param sql
	 * @param @return   
	 * @return Map<String,String>  
	 * @author hjh
	 * @date 2016-2-23
	 */
	public List<Map<String, Object>> sqlToList(Connection conn,String sql){
		DataSet ds = new DataSet();
		ds = MySQL.executeQuery(conn, sql);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 分页存储过程
	 * @param pageBean
	 * @param table 表名 例如：(select t.a,t.b from t) table  或者  t
	 * @param field 字段名 例如： a,b,c
	 * @param order 排序 例如：order by id desc 或者  order by id asc
	 * @param where 条件 例如： and t.a = 1  等等
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public void dataPage(Connection conn, PageBean<Map<String, Object>> pageBean, String table, String field, String order, String condition) throws SQLException, DataException{
		DataSet ds = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		long curPage =  pageBean.getPageNum();
		Procedures.pr_page(conn, ds, outParameterValues, table, field, pageBean.getPageSize(), curPage, order, condition, 0);
		long count = (Long) outParameterValues.get(0);
		boolean result = pageBean.setTotalNum(count);
		if(result){
			ds.tables.get(0).rows.genRowsMap();
			pageBean.setPage(ds.tables.get(0).rows.rowsMap);
		}
	}
//	public List commonQuery(PageBean pageBean, Map pmap,SqlSessionFactory sqlSessionFactory,String sqlId) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		Map map = new HashMap();
//		map.putAll(pmap);
//		map.put("count", "1");
//		List list = sqlSession.selectList(sqlId,map);
//		Map cmap = (Map) list.get(0);
//		Long ctLong = (Long)cmap.get("ct");
//		int ct = new Long(ctLong).intValue();
//		if (ct > 0){
//			pageBean.setTotalNum(ct);
//			map.remove("count");
//			map.put("detail", "1");
//			map.put("limit", "1");
//			map.put("start", (pageBean.pageNum-1) * pageBean.getPageSize());
//			map.put("pageSize", pageBean.getPageSize());
//			list = sqlSession.selectList(sqlId,map);
//			pageBean.setPage(list);
//			
//			sqlSession.close();
//		}
//		return list;
//	}
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public static DataBaseConfig getDateManager() {
		return dateManager;
	}

	public static void setDateManager(DataBaseConfig dateManager) {
		BaseService.dateManager = dateManager;
	}

	public Map<String,Object> getInformTemplate(){
		return (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
	}
	public Map<String, Object> getPlatformCost(){
		//获取平台收费
		Map<String,Object> platformCostMap = (Map<String, Object>) ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IAmountConstants.FEE_APPLICATION);
        if(platformCostMap == null)
        	platformCostMap = new HashMap<String, Object>();
		return platformCostMap;
	}
	
}
