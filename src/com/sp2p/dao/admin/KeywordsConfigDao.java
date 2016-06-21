package com.sp2p.dao.admin;


import java.sql.Connection;
import java.sql.SQLException;
import com.shove.data.DataException;
import com.sp2p.database.Dao;

/**
 * 关键字管理
 * @author Jing.X
 *
 */
public class KeywordsConfigDao {
	/**
	 * 修改
	 * @param conn
	 * @param id
	 * @param isuse
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long updateKeywordsState(Connection conn, int id, int isuse) throws SQLException, DataException {
		Dao.Tables.t_keywords keywords = new Dao().new Tables().new t_keywords();
		keywords.isuse.setValue(isuse);
		return keywords.update(conn, " id = " + id);
	}
	
	/**
	 * 添加
	 * @param conn
	 * @param word
	 * @param isuse
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addKeywords(Connection conn, String words, int isuse) throws SQLException, DataException {
		Dao.Tables.t_keywords keywords = new Dao().new Tables().new t_keywords();
		keywords.keyword.setValue(words);
		keywords.isuse.setValue(isuse);
		return keywords.insert(conn);
	}
	
}
