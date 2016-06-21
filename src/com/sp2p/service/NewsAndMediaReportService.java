package com.sp2p.service;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.ConnectionManager;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.ExcelUtils;
import com.shove.vo.Files;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.dao.admin.MediaReportDao;
import com.sp2p.dao.admin.NewsDao;
import com.sp2p.database.Dao;
import com.sp2p.entity.AwardParamVo;

/**
 * 网站公告 和 媒体报道Service
 */
public class NewsAndMediaReportService extends BaseService {
	public static Log log = LogFactory.getLog(NewsAndMediaReportService.class);

	private NewsDao newsDao;

	private MediaReportDao mediaReportDao;

	private ConnectionManager connectionManager;
	
	
	private UserService userService;
	
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public NewsDao getNewsDao() {
		return newsDao;
	}

	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public MediaReportDao getMediaReportDao() {
		return mediaReportDao;
	}

	public void setMediaReportDao(MediaReportDao mediaReportDao) {
		this.mediaReportDao = mediaReportDao;
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	/**
	 * 添加网站公告信息
	 * 
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param userId
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addNews(Integer sort, String title, String content,
			Long userId, String visits, String publishTime,String newsType)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = newsDao.addNews(conn, sort, title, content, publishTime,
					userId, visits,newsType);
			
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
	 * 修改网站公告信息
	 * 
	 * @param id
	 * @param sort
	 * @param title
	 * @param content
	 * @param publishTime
	 * @param userId
	 * @param visits
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateNews(Long id, Integer sort, String title, String content,
			Long userId, Integer visits, String publishTime,String newsType)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = newsDao.updateNews(conn, id, sort, title, content,
					publishTime, userId, visits,newsType);
			
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
	 * 删除网站公告 信息
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteNews(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = newsDao.deleteNews(conn, id);
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
	 * 删除网站公告介绍（团度介绍）的数据
	 * 
	 * @param commonIds
	 *            id拼接字符串
	 * @param delimiter
	 *            分割符
	 * @throws DataException
	 * @throws SQLException
	 * @return int
	 */
	public int deleteNews(String commonIds, String delimiter)
			throws Exception {
		Connection conn = MySQL.getConnection();
		int result = -1;
		try {
			result = newsDao.deleteNews(conn, commonIds, delimiter);
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
	 * 根据Id获取团队信息详情
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getNewsById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = newsDao.getNewsById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		
		return map;
	}

	public void queryNewsPage(PageBean<Map<String, Object>> pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_news_list", "*",
					"order by  publishTime  desc ", "AND state=1");

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 查询公告
	 * @param pageBean
	 * @param newStype 公告类型：0普通，1服务，2过期 ,null查询所有
	 * @throws Exception
	 */
	public void frontQueryNewsPage(PageBean<Map<String, Object>> pageBean,String newsType)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			// modify by houli 按照后台排序顺序显示
			String command = "";
			if (newsType!=null) {
				command = " and newsType="+newsType+"  ";
			}
			dataPage(conn, pageBean, "v_t_news_list", "*",
					command+"  order by publishTime desc ", " AND state=1 ");
		} catch (Exception e) {

			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * add by houli 查找表里最大的排列序号
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getMaxSerial() throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return newsDao.getMaxSerial(conn);
		} catch (Exception e) {

			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * add by houli 排序处理
	 * 
	 * @param ids
	 * @return
	 * @throws SQLException
	 */
	public Long updateNewsIndex(String ids) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = newsDao.updateNewsIndex(conn, ids);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
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
	 * add by houli 查看sortid是否存在
	 * 
	 * @param sortId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long isExistSortId(int sortId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = newsDao.isExistSortId(conn, sortId);
			if (map == null || map.get("sort") == null)
				return 1L;
			else
				return -1L;

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long isExistToUpdate(int sortId, int originalSortId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = newsDao.isExistToUpdate(conn, sortId,
					originalSortId);
			if (map == null || map.get("sort") == null)
				return 1L;
			else
				return -1L;

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	// 以上是网站公告的模块（增删改）
	// 以下是媒体报道的模块
	/**
	 * 添加媒体报道信息
	 * 
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addMediaReport(Integer sort, int newsType, String title, String source,
			String url, String imgPath, String content, String publishTime,
			int state, int stick,String label) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = mediaReportDao.addReport(conn, sort, newsType, title, source, url,
					imgPath, content, publishTime, state, stick,label);
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
	 * 更新媒体报道信息
	 * 
	 * @param id
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateMediaReport(Long id, Integer sort, String title,
			String source, String url, String imgPath, String content,
			String publishTime, int state, int stick,String label,String newsType) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = mediaReportDao.updateReport(conn, id, sort, title, source,
					url, imgPath, content, publishTime, state, stick,label,newsType);
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
     * 更新媒体报道信息浏览次数
     * 
     * @param id
     * @return
     * @throws SQLException
     * @throws DataException
     */
    public Long updateMediaReportVisits(Long id) throws Exception {
        Connection conn = MySQL.getConnection();
        Long result = 0L;
        try {
            result = Database.executeNonQuery(conn, " update t_mediareport set visits = visits+1" 
                    +" where id=" + id);
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
	 * 删除媒体报道信息
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long deleteMediaReprot(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = mediaReportDao.deleteReport(conn, id);
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
	 * 删除媒体报道的数据
	 * 
	 * @param commonIds
	 *            id拼接字符串
	 * @param delimiter
	 *            分割符
	 * @throws DataException
	 * @throws SQLException
	 * @return int
	 */
	public int deleteMediaReport(String commonIds, String delimiter)
			throws Exception {
		Connection conn = MySQL.getConnection();
		int result = -1;
		try {
			result = mediaReportDao.deleteReport(conn, commonIds, delimiter);
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
	 * 根据Id获取媒体报道详情
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getMediaReportById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = mediaReportDao.getReportById(conn, id);
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
	 * 媒体报道分页
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMediaReportPage(PageBean<Map<String, Object>> pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append("  and state =2 ");
//		condition.append("  and newsType =1 ");
		try {
			dataPage(conn, pageBean, " t_mediareport  ", " * ",
					" order by id desc ", condition + "");//stick desc,sort desc
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void queryMediaReportPage1(PageBean<Map<String, Object>> pageBean,int newsType)
			throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		// condition.append("  and state =2 ");
		if(newsType == 1){
			condition.append("  and newsType =1 ");
		}
		if(newsType == 2){
			condition.append("  and newsType =2 ");
		}
		try {
			dataPage(conn, pageBean, " t_mediareport  ", " * ",
					" order by id desc ", condition + "");// stick desc,sort
															// desc
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void queryMediaReportPage1(PageBean<Map<String, Object>> pageBean,int newsType,String label)
            throws Exception {
        Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
         condition.append("  and state =2 ");
        if(newsType == 1){
            condition.append("  and newsType =1 ");
        } else if(newsType == 2){
            condition.append("  and newsType =2 ");
        } else if(newsType == 3){
            condition.append("  and newsType =3 ");
        }
        
        if (StringUtils.isNotBlank(label)) {
             condition.append(" and label like '%"+Utility.filteSqlInfusion(label)+"%'");
        }
        
        try {
            dataPage(conn, pageBean, " t_mediareport  ", " * ",
                    " order by id desc ", condition + "");// stick desc,sort
                                                            // desc
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            throw e;
        } finally {
            conn.close();
        }
    }
	

	/**
	 * 查询媒体报道置顶数据（查询是否顶置）
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMediaReportPage(PageBean<Map<String, Object>> pageBean,
			int stick) throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		if (stick == 2) {
			condition.append("  and stick = 2  and state =2 ");
		}
		if (stick == 1) {
			condition.append("  and stick = 1  and state =2 ");
		}
		if (stick != 1 && stick != 2) {
			condition.append(" ");
		}
		try {
			dataPage(conn, pageBean, " t_mediareport  ", " * ",
					" order by stick desc,id desc ", condition + "");//sort desc 
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void queryMediaReportPage(PageBean<Map<String, Object>> pageBean,
	                                 int stick,String content,String startTime,String endTime,String newsType,String artitle) throws Exception {
	                             Connection conn = MySQL.getConnection();
	                             StringBuffer condition = new StringBuffer();
	                             if (stick == 2) {
	                                 condition.append("  and stick = 2  and state =2 ");
	                             }
	                             if (stick == 1) {
	                                 condition.append("  and stick = 1  and state =2 ");
	                             }
	                             if (stick != 1 && stick != 2) {
	                                 condition.append(" ");
	                             }
	                             
	                             if (StringUtils.isNotBlank(content)) {
                                    condition.append(" and (content like '%"+content+"%') ");
                                }
	                             if (StringUtils.isNotBlank(artitle)) {
	                                    condition.append(" and (title like '%"+artitle+"%') ");
	                                }
	                             if (StringUtils.isNotBlank(startTime)) {
	                                    condition.append(" and publishTime>'"+startTime+"'");
	                              }
	                             if (StringUtils.isNotBlank(endTime)) {
	                                 condition.append(" and publishTime<'"+endTime+"'");
	                              }
	                             
	                             if (StringUtils.isNotBlank(newsType)) {
	                                 condition.append(" and newsType="+newsType+"");
                                }
	                             
	                             try {
	                                 dataPage(conn, pageBean, " t_mediareport  ", " * ",
	                                         " order by stick desc,id desc ", condition + "");//sort desc 
	                             } catch (Exception e) {
	                                 log.error(e);
	                                 e.printStackTrace();
	                                 throw e;
	                             } finally {
	                                 conn.close();
	                             }
	                         }
	
	//查询三好资本平台动态信息
	public void queryMediaReportSanhaoPage(PageBean<Map<String, Object>> pageBean,
			int stick) throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
//		condition.append("  and state =2 ");
		condition.append("  and newsType = 1 ");
		if (stick == 2) {
			condition.append("  and stick = 2  ");
		}
		if (stick == 1) {
			condition.append("  and stick = 1  ");
		}
		if (stick != 1 && stick != 2) {
			condition.append(" ");
		}
		try {
			dataPage(conn, pageBean, " t_mediareport  ", " * ",
					" order by stick desc,id desc ", condition + "");//sort desc 
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	//查询互联网金融动态信息
	/**
	 * 查询动态
	 * queryMediaReportInternetPage:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author Administrator
	 * @param pageBean
	 * @param stick
	 * @param type 类别  1.三好资本动态 2.互联网金融  3.三好资本问答
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public void queryMediaReportInternetPage(PageBean<Map<String, Object>> pageBean,
			int stick,String type) throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();

		if (stick == 2) {
			condition.append("  and stick = 2  ");
		}
		if (stick == 1) {
			condition.append("  and stick = 1  ");
		}
		if (type != null) {
			condition.append("  and newsType = ").append(type);
		}
		if (stick != 1 && stick != 2) {
			condition.append(" ");
		}
		try {
			dataPage(conn, pageBean, " t_mediareport  ", " * ",
					" order by stick desc,id desc ", condition + "");//sort desc 
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 修改媒体报道顶置状态，是否顶置
	 * 
	 * @param id
	 * @param stick
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long updateReportStick(int id, int stick) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = mediaReportDao.updateReportStick(conn, id, stick);
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
	 * 查询首页滚动图片（页面上显示）是前台还是后台？？？
	 * 
	 * @return
	 * @author Administrator
	 */
	public List<Map<String, Object>> queryIndexRollImg() throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = mediaReportDao.queryIndexRollImg(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		
		if (list.size() == 0) {
			return null;
		}
		return list;
	}
	
	
	public Map<String, String> queryRecruitmentInfo() throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = mediaReportDao.queryRecruitmentInfo(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	public Map<String, String> getNewsPreById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = newsDao.getNewsPreById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		
		return map;
	}

	public Map<String, String> getNewsByAfterId(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = newsDao.getNewsByAfterId(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
		
		return map;
	}
	public Map<String, String> frontReportPreById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = mediaReportDao.frontReportPreById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}
	
	public Map<String, String> frontReportAfterById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = mediaReportDao.frontReportAfterById(conn, id);
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
	 * 查询相关阅读
	 * queryRelationNews
	 * @auth hejiahua
	 * @param id
	 * @param label
	 * @param newsType 
	 * void
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-10-22 下午2:58:28
	 * @since  1.0.0
	 */
	public List<Map<String, Object>> queryRelationNews(long id,String label,String newsType) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
            DataSet ds =  Database.executeQuery(conn, "select * from t_mediareport t where t.id!="+id+"  and  t.label  like '%"+label+"%' and  t.newsType="+newsType+" order by id desc  limit 0,5");
            ds.tables.get(0).rows.genRowsMap();
            if (ds.tables.get(0).rows.rowsMap==null || ds.tables.get(0).rows.rowsMap.size()<1) {
                ds =  Database.executeQuery(conn, "select * from t_mediareport t where t.id!="+id+"  and  t.newsType="+newsType+" order by id desc   limit 0,5");
                ds.tables.get(0).rows.genRowsMap();
            }
            return ds.tables.get(0).rows.rowsMap;
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
	 * 抽奖管理列表
	 * queryAwardManagerInfo
	 * @throws SQLException 
	 * @auth hejiahua 
	 * void
	 * @exception 
	 * @date:2015-1-9 上午8:53:52
	 * @since  1.0.0
	 */
	public void queryAwardManagerInfo(PageBean pageBean,String dateStart ,String dateEnd) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
	        StringBuffer condition = new StringBuffer();
	        if (StringUtils.isNotBlank(dateStart)) {
                condition.append(" and createTime >= '"+dateStart+"'" );
            }
	        if (StringUtils.isNotBlank(dateEnd)) {
	            condition.append(" and createTime <= '"+dateEnd+"'" );
            }
            dataPage(conn, pageBean, "t_award_term", "*", " order by id desc",condition.toString());
        }
        catch (Exception e) {
           e.printStackTrace();
           log.error(e);
        }finally{
            conn.close();
        }
	}
	
	
	
	/**
	 * 查询热门推荐
	 * queryHotNews
	 * @auth hejiahua
	 * @param id
	 * @param newsType
	 * @return 
	 * List<Map<String,Object>>
	 * @throws SQLException 
	 * @exception 
	 * @date:2014-10-22 下午3:03:56
	 * @since  1.0.0
	 */
	public List<Map<String,Object>> queryHotNews(long id,String newsType) throws SQLException{
	    Connection conn = MySQL.getConnection();
        try {
            DataSet ds =  Database.executeQuery(conn, "select * from t_mediareport t where t.id!="+id+" and stick =2 and state=2  and  t.newsType="+newsType+" order by publishTime desc limit 0,5");
            ds.tables.get(0).rows.genRowsMap();
            return ds.tables.get(0).rows.rowsMap;
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
	 * 添加抽奖基本参数
	 * 
	 */
	public Long addAwardParam(Map <String,String>map)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = newsDao.addAwardParam(conn, map);
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
	 * 添加某一期的奖品
	 * addAwardInfo
	 * @auth hejiahua
	 * @param map
	 * @return 
	 * Long
	 * @throws SQLException 
	 * @exception 
	 * @date:2015-1-9 下午5:28:04
	 * @since  1.0.0
	 */
	public Long addAwardInfo(Map<String, String> map) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result = -1L;
	    try {
            
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	    return result;
	}
	
	/**
	 * 查询所有的奖品列表
	 * queryAwardParam
	 * @auth hejiahua
	 * @return 
	 * List<Map<String,String>>
	 * @throws SQLException 
	 * @exception 
	 * @date:2015-1-9 下午5:30:43
	 * @since  1.0.0
	 */
	public List<Map<String,String>>   queryAwardParam() throws SQLException{
	    Connection conn = MySQL.getConnection();
	    List<Map<String, String>> list = null;
	    try {
	        list = newsDao.queryAwardParam(conn);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	    return list;
	}
	
	/**
     * 查询所有的抽奖期数
     * queryAwardParam
     * @auth hejiahua
     * @return 
     * List<Map<String,String>>
     * @throws SQLException 
     * @exception 
     * @date:2015-1-9 下午5:30:43
     * @since  1.0.0
     */
    public List<Map<String,String>>   queryAward() throws SQLException{
        Connection conn = MySQL.getConnection();
        List<Map<String, String>> list = null;
        try {
            list = newsDao.queryAward(conn);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
        return list;
    }
	
	/**
	 * 
	 * queryRelationNews
	 * @auth hejiahua
	 * @param id
	 * @param label
	 * @param newsType 
	 * void
	 * @throws Exception 
	 * @exception 
	 * @date:2014-10-22 下午2:58:28
	 * @since  1.0.0
	 */
	public  void listAwardParam(String termId,PageBean<Map<String, Object>> pageBean) throws Exception{
	    Connection conn = MySQL.getConnection();
	    StringBuffer sb = new StringBuffer("(SELECT b.id,b.`awardName` ,1 selOpt, isUsable, a.`byOrder`,paramId FROM t_award_info a,t_award_param b WHERE a.`termId`=")
	    .append(termId) 
	    .append(" AND a.`paramId`=b.`id`")
	    .append("UNION ALL ")
	    .append("SELECT a.`id`, a.awardName,0 selOpt, 0 isUsable,0, a.`id`  FROM t_award_param a WHERE NOT EXISTS (")
	    .append("SELECT 1 FROM t_award_info b WHERE b.`paramId` =a.`id` AND termId=" + termId)
	    .append("))c");
		try {
			dataPage(conn, pageBean, sb.toString(), "*",
					" ORDER BY selOpt DESC,paramId ASC ", " ");

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	
	/**
	 * 添加抽奖期数
	 */
	public Long addAwardTerm(Map <String,String>map,int strutsAction) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = newsDao.addAwardTerm(conn, map,strutsAction);
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
	 * 某一期的抽奖
	 * queryAwardById
	 * @auth hejiahua
	 * @param id
	 * @return
	 * @throws SQLException 
	 * Map<String,String>
	 * @exception 
	 * @date:2015-1-9 下午2:14:10
	 * @since  1.0.0
	 */
	public Map<String, String> queryAwardById(int id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Map<String, String> map = null;
	    try {
	        map = newsDao.queryAwardById(conn, id);
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
	 * 某一期的抽奖明细包含奖品信息
	 * queryAwardInfoById
	 * @auth hejiahua
	 * @param id
	 * @return
	 * @throws SQLException 
	 * Map<String,String>
	 * @exception 
	 * @date:2015-1-9 下午2:42:41
	 * @since  1.0.0
	 */
    public List<Map<String, String>> queryAwardInfoById(int id) throws SQLException{
        Connection conn = MySQL.getConnection();
        List<Map<String, String>> list = null;
        try {
            list = newsDao.queryAwardInfoById(conn, id);
        }
        catch (Exception e) {
           e.printStackTrace();
           log.error(e);
        }finally{
            conn.close();
        }
        return list;
    }
	
	/**
	 * 添加抽奖信息
	 */
	public Long addAwardInfo(List <AwardParamVo> list) throws Exception {
		log.info("--service.addAwardInfo---");
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			//result = newsDao.addAwardTerm(conn, map);
			for (int i = 0; i<list.size(); i++){
				AwardParamVo vo = (AwardParamVo) list.get(i);
				newsDao.addAwardInfo(conn,vo);
			}
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
	 * 保存每一期的奖品信息
	 * saveAwardInfo
	 * @auth hejiahua
	 * @param map
	 * @return
	 * @throws SQLException 
	 * long
	 * @exception 
	 * @date:2015-1-9 下午7:31:02
	 * @since  1.0.0
	 */
	public long saveAwardInfo(Map<String, String> map) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result = -1L;
	    try {
	        result =  newsDao.saveAwardInfo(conn, map);
	        if(result>0) conn.commit();
	        else conn.rollback();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	//删除
	public long awardParamDelete(int id) throws SQLException{
	    Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            result =  newsDao.awardParamDelete(conn, id);
            if(result>0) conn.commit();
            else conn.rollback();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            conn.rollback();
        }finally{
            conn.close();
        }
        return result;
	}
	
	/**
	 * 抽奖明细
	 * queryAwardParamUserList
	 * @auth hejiahua
	 * @param pageBean
	 * @param params
	 * @throws SQLException 
	 * void
	 * @exception 
	 * @date:2015-1-9 下午8:24:42
	 * @since  1.0.0
	 */
	public void queryAwardParamUserList(PageBean pageBean,Map<String, String> params) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    try {
	        StringBuffer condition = new StringBuffer();
	        condition.append(" and t.`userId` = tu.`id` AND t.`awardInfoId` = v.`id` ");
	        if (StringUtils.isNotBlank(params.get("termNo"))) {
                condition.append(" AND v.`termNo` = "+params.get("termNo"));
            }
	        if (StringUtils.isNotBlank(params.get("username"))) {
                condition.append(" AND (  tu.`username` LIKE '%"+params.get("username")+"%'  OR tu.`email` LIKE '%"+params.get("username")+"%'  OR tu.`mobilePhone` LIKE '%"+params.get("username")+"%' )  ");
            }
            if (StringUtils.isNotBlank(params.get("status"))) {
                condition.append(" AND v.`status` = "+params.get("status"));
            }
            if (StringUtils.isNotBlank(params.get("paramId"))) {
                condition.append(" AND t.`paramId` = "+params.get("paramId"));
            }
            if (StringUtils.isNotBlank(params.get("sendAward"))) {
                condition.append(" AND  t.`sendAward` =  "+params.get("sendAward"));
            }
            if (StringUtils.isNotBlank(params.get("startTime"))) {
                condition.append("  AND t.`createTime`>='"+params.get("startTime")+"'");
            }
            if (StringUtils.isNotBlank(params.get("endTime"))) {
                condition.append("  AND t.`createTime`<='"+params.get("endTime")+"'");
            }
            dataPage(conn, pageBean, " t_award_user t,t_user tu,v_t_award_tern_list v ", "  t.`createTime`,tu.`username`,(CASE t.`sendAward`  WHEN   0 THEN '未派奖' ELSE '已派奖' END ) AS sendAwardStr,t.`sendAward`,CONCAT(\"第\",CAST(v.`termNo` AS CHAR),\"期\") AS termNo,v.`status`, (CASE  v.`status` WHEN   0 THEN \"未中奖\" ELSE \"中奖\" END ) AS statusStr, v.`awardName`,t.`id`  ", "order by  termNo DESC,createTime DESC ",condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	}
	
	/**
	 * 奖品列表
	 * queryParamDetail
	 * @auth hejiahua
	 * @param pageBean
	 * @param name
	 * @throws SQLException 
	 * void
	 * @exception 
	 * @date:2015-1-12 下午2:31:35
	 * @since  1.0.0
	 */
	public void queryParamDetail(PageBean pageBean,String name) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    StringBuffer condition = new StringBuffer();
	    try {
	        if (StringUtils.isNotBlank(name)) {
                condition.append(" and awardName like '%"+name+"%'");
            }
            dataPage(conn, pageBean, "t_award_param", "*", " order by id desc ", condition.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }finally{
            conn.close();
        }
	}
	/**
     * 奖品Id查询
     * queryParamDetailById
     * @auth hejiahua
     * @param id
     * @throws SQLException 
     * void
     * @exception 
     * @date:2015-1-12 下午2:31:35
     * @since  1.0.0
     */
	public Map<String, String> queryParamDetailById(long id) throws SQLException{
        Connection conn = MySQL.getConnection();
        Map<String, String> map = null;
        try {
           DataSet ds =  Database.executeQuery(conn, " SELECT * FROM t_award_param where   id = "+id);
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
	 * 更新、插入、删除奖品
	 * editParamDetail
	 * @auth hejiahua
	 * @param params
	 * @param strutsAction
	 * @return
	 * @throws SQLException 
	 * long
	 * @exception 
	 * @date:2015-1-12 下午2:41:21
	 * @since  1.0.0
	 */
	public long editParamDetail(Map<String, String> params,int strutsAction) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result = -1L;
	    try {
	        result = newsDao.editParamDetail(conn, params, strutsAction);
	        if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            conn.rollback();
        }finally{
            conn.close();
        }
        return result;
	}
	
	/**
	 * 派奖
	 * sendAward
	 * @auth hejiahua
	 * @param id
	 * @return
	 * @throws SQLException 
	 * long
	 * @exception 
	 * @date:2015-1-14 上午9:43:21
	 * @since  1.0.0
	 */
	public long sendAward(long id) throws SQLException{
	    Connection conn = MySQL.getConnection();
	    Long result = -1L;
	    try {
            Dao.Tables.t_award_user t = new Dao().new Tables().new t_award_user();
            t.sendAward.setValue(1);
            result = t.update(conn, "id="+id);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            conn.rollback();
        }finally{
            conn.close();
        }
	    return result;
	}
	
	/**
	 * 添加小文章信息
	 * 
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addArticleList(Integer sort, int newsType, String title, String source,
			String url, String imgPath, String content, String publishTime,
			int state, int stick,String label) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = mediaReportDao.addArticleList(conn, sort, newsType, title, source, url,
					imgPath, content, publishTime, state, stick,label);
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
	
	
	public void queryArticlePage(PageBean<Map<String, Object>> pageBean,
			int stick, String content, String startTime, String endTime,
			String newsType) throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		if (stick == 2) {
			condition.append("  and stick = 2  and state =2 ");
		}
		if (stick == 1) {
			condition.append("  and stick = 1  and state =2 ");
		}
		if (stick != 1 && stick != 2) {
			condition.append(" ");
		}

		if (StringUtils.isNotBlank(content)) {
			condition.append(" and (title like '%" + content
					+ "%'  or content like '%" + content + "%') ");
		}
		if (StringUtils.isNotBlank(startTime)) {
			condition.append(" and publishTime>'" + startTime + "'");
		}
		if (StringUtils.isNotBlank(endTime)) {
			condition.append(" and publishTime<'" + endTime + "'");
		}

		if (StringUtils.isNotBlank(newsType)) {
			condition.append(" and newsType=" + newsType + "");
		}

		try {
			dataPage(conn, pageBean, " t_articleList  ", " * ",
					" order by stick desc,id desc ", condition + "");// sort
																		// desc
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 根据Id获取媒体报道详情
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryArticleById(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = null;
		try {
			map = mediaReportDao.queryArticleById(conn, id);
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
	 * 更新媒体报道信息
	 * 
	 * @param id
	 * @param sort
	 * @param userName
	 * @param imgPath
	 * @param intro
	 * @param publishTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateArticle(Long id, Integer sort, String title,
			String source, String url, String imgPath, String content,
			String publishTime, int state, int stick,String label,String newsType) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = 0L;
		try {
			result = mediaReportDao.updateArticle(conn, id, sort, title, source,
					url, imgPath, content, publishTime, state, stick,label,newsType);
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
	 * 删除媒体报道的数据
	 * 
	 * @param commonIds
	 *            id拼接字符串
	 * @param delimiter
	 *            分割符
	 * @throws DataException
	 * @throws SQLException
	 * @return int
	 */
	public long deleteArticle(String commonIds, String delimiter)
			throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = mediaReportDao.deleteArticle(conn, commonIds, delimiter);
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
	 * 媒体报道分页
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryArticlePage(PageBean<Map<String, Object>> pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		condition.append("  and state =2 ");
		try {
			dataPage(conn, pageBean, " t_articleList  ", " * ",
					" order by id desc ", condition + "");//stick desc,sort desc
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 清空过期奖品
	 * @param files
	 * @throws Exception 
	 */
	public List<Map<String, Object>> clearAward(Files files,String type,long adminId,String addIP) throws Exception{
		if (StringUtils.isNotBlank(type) && files!=null) {
			Connection conn = MySQL.getConnection();
			try {
				List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
				int type_int = Integer.parseInt(type);
				String [][] data = ExcelUtils.getData(files.getFiles(), 1);
				for (int i = 0; i < data.length; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					String str_id = data[i][0];//id
					long upresult = userService.clearAward(conn, str_id, Convert.strToDouble(data[i][1], 0), type_int, adminId, addIP);
					if (upresult>0) {
						conn.commit();
					}else {
						map.put("id", str_id);
						map.put("money", data[i][1]);
						map.put("result", "error");
						resultList.add(map);
						map = null;
						conn.rollback();
					}
				}
				return resultList;
			} catch (Exception e) {
				log.error(e);
			}finally{
				conn.close();
			}
		}
		return null;
	}
	
}
