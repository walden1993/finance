package com.sp2p.service.admin;



import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.base.BaseService;
import com.shove.data.dao.MySQL;
import com.shove.vo.PageBean;
import com.sp2p.dao.admin.KeywordsConfigDao;


public class KeywordsConfigService extends BaseService {
	public static Log log = LogFactory.getLog(SendSMSService.class);

	private KeywordsConfigDao keywordsConfigDao;

	public KeywordsConfigDao getKeywordsConfigDao() {
		return keywordsConfigDao;
	}

	public void setKeywordsConfigDao(KeywordsConfigDao keywordsConfigDao) {
		this.keywordsConfigDao = keywordsConfigDao;
	}

	/**
	 * 添加一个关键字
	 * @param words
	 * @param isuse
	 * @return
	 * @throws Exception
	 */
	public long addKeywords(String words, int isuse) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = keywordsConfigDao.addKeywords(conn, words, isuse);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 修改关键字使用状态
	 * @param id
	 * @param isuse
	 * @return
	 * @throws Exception
	 */
	public long updateKeywordsState(int id, int isuse) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = keywordsConfigDao.updateKeywordsState(conn, id, isuse);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 分页查询所有
	 * @param pageBean
	 * @param words
	 * @param isuse
	 * @throws Exception
	 */
	public void queryKeywords(PageBean pageBean, String words, int isuse) throws Exception {
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		if(!words.equals("") && words != null) {
			condition.append(" and keyword like '%"+ words +"%'");
		}
		if(isuse != -1) {
			condition.append(" and isuse = " + isuse);
		}System.out.println(condition);
		try {
			dataPage(conn, pageBean, "t_keywords", " * ", " ",
					condition.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
		}
	}

}
