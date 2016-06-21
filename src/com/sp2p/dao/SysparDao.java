package com.sp2p.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_award_score;
import com.sp2p.database.Dao.Tables.t_selecttype;

/**
 * 系统参数dao
 * 
 * @author hejiahua
 * 
 */
public class SysparDao {

	public Map<String, String> querySysparChild(Connection conn,
			String fieldList, String selectKey, String order, int limitCount,
			int limitStart) throws DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		DataSet ds = new DataSet();
		ds = select.open(conn, fieldList, "selectKey='" + selectKey + "'",
				order, limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(ds);
	}

	public Map<String, String> querySysparChildById(Connection conn,
			String fieldList, int id) throws DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		DataSet ds = new DataSet();
		ds = select.open(conn, fieldList, "id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	public List<Map<String, Object>> querySysparAllChild(Connection conn,
			String fieldList, String condition, String order, int limitCount,
			int limitStart) throws DataException {
		Dao.Tables.t_select select = new Dao().new Tables().new t_select();
		DataSet ds = new DataSet();
		ds = select.open(conn, fieldList, condition, order, limitStart,
				limitCount);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}

	/**
	 *功能：可否参加注册抽奖
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 */
	public Map<String, String> getAwardJudge(Connection conn, String id)
			throws DataException {
		Dao.Tables.t_selecttype select = new Dao().new Tables().new t_selecttype();
		DataSet ds = select
				.open(conn, " introduce ", " id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 *功能：加抽奖好币
	 * @param conn
	 * @param id
	 * @return
	 * @throws DataException
	 */
	public Long addUserSore(Connection conn, Long userId)
			throws DataException {
		Dao.Tables.t_award_score award = new Dao().new Tables().new t_award_score();
		award.createDate.setValue(new java.util.Date());
		award.createTime.setValue(new java.util.Date());
		award.score.setValue(1000);
		award.stype.setValue(1);//1注册认证，2推荐，3投资，
		award.actType.setValue(3);//以后再改，先写死 1：12月份活动，3：3月份活动
		award.userId.setValue(userId);
		return award.insert(conn);
	}
	
	
}
