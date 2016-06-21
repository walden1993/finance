package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.Field;
import com.shove.data.dao.MySQL;
import com.shove.data.dao.View;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.web.action.BaseAction;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Views;
import com.sp2p.database.Dao.Tables.t_award_info;
import com.sp2p.database.Dao.Tables.t_award_param;
import com.sp2p.entity.AwardParamVo;
import com.sp2p.util.DateUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 网站公告
 * 
 * @author Administrator
 * 
 */
public class NewsDao {

	/**
	 * 添加网站公告信息
	 * 
	 * @param conn
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addNews(Connection conn, Integer sort, String title,
			String content, String publishTime, Long userId, String visits,String newsType)
			throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		news.sort.setValue(sort);
		news.title.setValue(title);
		news.content.setValue(content);
		news.publishTime.setValue(publishTime);
		news.userId.setValue(userId);
		news.visits.setValue(visits);
		news.newsType.setValue(newsType);
		return news.insert(conn);

	}

	/**
	 * 删除网站公告
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteNews(Connection conn, Long id) throws SQLException,
			DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		news.state.setValue(2);
		return news.update(conn, "id=" + id);
	}

	/**
	 * 删除网站公告
	 * 
	 * @param conn
	 * @param ids
	 *            id字符串拼接
	 * @param delimiter
	 *            拼接符号
	 * @return long
	 * @throws DataException
	 * @throws SQLException
	 */
	public int deleteNews(Connection conn, String commonIds, String delimiter)
			throws SQLException, DataException {
		DataSet dataSet = new DataSet();
		List<Object> outParameterValues = new ArrayList<Object>();
		return Dao.Procedures.p_deleteNews(conn, dataSet, outParameterValues,
				commonIds, delimiter);
	}

	/**
	 * 更新网站公告
	 * 
	 * @param conn
	 * @param id
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param publisher
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateNews(Connection conn, Long id, Integer sort,
			String title, String content, String publishTime, Long userId,
			Integer visits,String newsType) throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		if (sort!=null) {
			news.sort.setValue(sort);
		}
		
		if (StringUtils.isNotBlank(title)) {
			news.title.setValue(title);
		}
		
		if (StringUtils.isNotBlank(content)) {
			news.content.setValue(content);
		}
		
		if (StringUtils.isNotBlank(publishTime)) {
			news.publishTime.setValue(publishTime);
		}
		
		if (userId!=null) {
			news.userId.setValue(userId);
		}
		
		if (visits!=null) {
			news.visits.setValue(visits);
		}
		if (newsType!=null) {
			news.newsType.setValue(newsType);
		}
		return news.update(conn, "id=" + id);

	}
/**
 * 通过新闻Id获取新闻公告
 * @param conn
 * @param id
 * @return
 * @throws SQLException
 * @throws DataException
 */
	public Map<String, String> getNewsById(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer command=new StringBuffer();
		command.append("select ta.userName as username,tn.title,tn.id as id,tn.publishTime as publishTime, tn.visits as visits,tn.content as content,ifnull(tn.newsType,0) as newsType from t_news tn LEFT JOIN" +
		" t_admin ta on tn.userId=ta.id where tn.id=" + id);
		DataSet dataSet=MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryNewsList(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, "*", "state=1", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * add by houli 查找表里最大的排列序号
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getMaxSerial(Connection conn) throws SQLException, DataException{
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, "max(sort) as sortId", "",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * add by houli 排序处理
	 * @param conn
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public Long updateNewsIndex(Connection conn,String ids) throws SQLException{
		ids=com.shove.web.Utility.filteSqlInfusion(ids);
		String[] transIds = StringEscapeUtils.escapeSql(ids).split(",");
		Long result = 0L;
		Long re = -1L;
		long tempId = 0;
		if (transIds.length > 0) {
			for(int i=0;i<transIds.length;i++){
				tempId = 0;re = -1L;//重新赋值
				//看是否是正规的int值
				tempId = Convert.strToLong(transIds[i], -1);
				if(tempId != -1){
					re = MySQL.executeNonQuery(conn,
							"update t_news set sort="+i+" where id="+tempId);
					if(re > 0)
						result += 1;
				}
			}
		}
		return result==(transIds.length-1)?1L:-1L;		
	}
	
	/**
	 * add by houli 判断sort是否存在
	 * @param conn
	 * @param sortId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> isExistSortId(Connection conn,int sortId) throws SQLException, DataException{
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, " sort", " sort="+sortId,
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * add by houli 判断修改后的sort是否存在
	 * @param conn
	 * @param sortId
	 * @param originalSortId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> isExistToUpdate(Connection conn,int sortId,int originalSortId) throws SQLException, DataException{
		String command = "SELECT id,sort from (select id,sort from t_news " +
				" where sort != "+originalSortId+" ) b where sort="+sortId;
		DataSet dataSet = MySQL.executeQuery(conn, command); 
		command=null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> getNewsPreById(Connection conn, Long id) throws DataException, SQLException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, "*", " id=(select min(id) from t_news where id > " + id+")" + " AND state=1",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String, String> getNewsByAfterId(Connection conn, Long id) throws SQLException, DataException {
		Dao.Tables.t_news news = new Dao().new Tables().new t_news();
		DataSet dataSet = news.open(conn, "*", " id=(select max(id) from t_news where id < " + id+")" + " AND state=1",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 添加奖品
	 * @param conn
	 */
	public Long addAwardParam(Connection conn,Map <String,String>map)throws SQLException,DataException{
		Dao.Tables.t_award_param award=new Dao().new Tables().new t_award_param();
		award.awardName.setValue(map.get("awardName"));
		award.picAddr.setValue(map.get("picAddr"));
		award.remark.setValue(map.get("remark"));
		return award.insert(conn);
	}
	
	/**
	 * 添加抽奖期数
	 * @param conn
	 */
	public Long addAwardTerm(Connection conn,Map <String,String>map,int strutsAction)throws SQLException{
		Dao.Tables.t_award_term award = new Dao().new Tables().new t_award_term();
		award.luckTheme.setValue(map.get("luckTheme"));
		award.termNo.setValue(map.get("termNo"));
		award.startTime.setValue(map.get("timeStart"));
		award.endTime.setValue(map.get("timeEnd"));
		award.createTime.setValue(DateUtil.dateToString(new Date()));
		award.isActive.setValue(1);
		award.awardDesc.setValue(map.get("awardDesc"));
		if (strutsAction==1) {//新增
		    award.awardNo.setValue(map.get("awardNo"));
		    return award.insert(conn);
        }else {//修改
            return award.update(conn, " id = "+map.get("id"));
        }
	}
	
	/**
	 * 添加抽奖信息
	 * @param conn
	 */
	public Long addAwardInfo(Connection conn,AwardParamVo vo)throws SQLException,DataException{
		System.out.println("-------------addAwardInfo-------------------------------");
		try {
			Dao.Tables.t_award_info award = new Dao().new Tables().new t_award_info();
			award.byOrder.setValue(vo.getByOrder());
			award.hitRate.setValue(vo.getHitRate());
			award.paramId.setValue(vo.getParamId());
			award.isUsable.setValue(Integer.parseInt(vo.getIsUsable()));
			award.termId.setValue(vo.getTermId());
			award.createTime.setValue(new java.util.Date());
			
			award.insert(conn);
//			DataSet dataSet = award.open(conn, " id ", " luckTheme='"+map.get("luckTheme") + "' and termNo=" + map.get("termNo"),
//					"", -1, -1);
//			Map mm = BeanMapUtils.dataSetToMap(dataSet);
//			return Long.parseLong((String)mm.get("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	//查询单个一期的东西
	public Map<String,String> queryAwardById(Connection conn,int id) throws DataException{
	    DataSet ds = Database.executeQuery(conn, "SELECT * FROM t_award_term t WHERE t.`id` = "+id);
	    return BeanMapUtils.dataSetToMap(ds);
	} 
	//查询单个一期的奖品信息
    public List<Map<String, String>> queryAwardInfoById(Connection conn,int id) throws DataException{
        DataSet ds =Database.executeQuery(conn, "SELECT * FROM v_t_award_tern_list v WHERE v.`termId` = "+id);
        ds.tables.get(0).rows.genRowsMap();
        return ds.tables.get(0).rows.rowsMap;
    } 
    //查询所有的奖品信息
    public List<Map<String, String>> queryAwardParam(Connection conn) throws DataException{
        DataSet ds =Database.executeQuery(conn, "SELECT * FROM t_award_param t");
        ds.tables.get(0).rows.genRowsMap();
        return ds.tables.get(0).rows.rowsMap;
    } 
    //查询所有的抽奖期数
    public List<Map<String, String>> queryAward(Connection conn) throws DataException{
        DataSet ds =Database.executeQuery(conn, "SELECT t.`termNo`,CONCAT(\"第\",CAST(t.`termNo` AS CHAR),\"期\") AS termNoStr FROM t_award_term t ");
        ds.tables.get(0).rows.genRowsMap();
        return ds.tables.get(0).rows.rowsMap;
    } 
  //保存某一期的奖品信息
    public long saveAwardInfo(Connection conn,Map<String, String> map) throws DataException{
        Dao.Tables.t_award_info t = new Dao().new Tables(). new t_award_info();
        t.paramId.setValue(map.get("paramId"));
        t.termId.setValue(map.get("termId"));
        t.isUsable.setValue(map.get("isUsable"));
        t.hitRate.setValue(map.get("hitRate"));
        t.byOrder.setValue(map.get("byOrder"));
        t.status.setValue(map.get("status"));
        t.hitTime.setValue(map.get("hitTime"));
        t.realMoney.setValue(StringUtils.isBlank(map.get("realMoney"))?0:map.get("realMoney"));
        t.createTime.setValue(DateUtil.dateToString(new Date()));
        return t.insert(conn);
    } 
    
    //删除某一期的奖品
    public long awardParamDelete(Connection conn,int id){
        Dao.Tables.t_award_info t = new Dao().new Tables(). new t_award_info();
        return t.delete(conn, " id ="+id);
    }
    
    //更新或插入奖品
    public long editParamDetail(Connection conn,Map<String, String> map,int strutsAction) throws DataException{
        Dao.Tables.t_award_param t = new Dao().new Tables(). new t_award_param();
        t.awardName.setValue(map.get("awardName"));
        t.picAddr.setValue(map.get("picAddr"));
        t.remark.setValue(map.get("remark"));
        if (strutsAction==BaseAction.ADD) {
            return t.insert(conn);
        }
        if (strutsAction==BaseAction.EDIT) {
            return t.update(conn, " id = "+map.get("id"));
        }
        if (strutsAction == BaseAction.DELETE) {
            return t.delete(conn, " id = "+map.get("id"));
        }
        return -1L;
    } 
    
}
