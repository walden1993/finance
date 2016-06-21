package com.shove.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.shove.vo.PageBean;
/**
 * 通用业务类，实现常见的增删改查
 * @author bao
 *
 */
public class CommonService extends BaseService {
	private SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}


	/**
	 *功能：通用查询功能，返回list 
	 * @param pageBean
	 * @param pmap
	 * @return
	 */
	public List queryList(String sqlId,Map pmap){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			List list = sqlSession.selectList(sqlId, pmap);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return null;
	}
	
	/**
	 *功能：保存功能
	 * @param map
	 */
	public void commonSave(String sqlId,Map <String,String>map){
//		TransactionFactory transactionFactory = new JdbcTransactionFactory();  
		SqlSession sqlSession = sqlSessionFactory.openSession();
//		Transaction newTransaction = transactionFactory.newTransaction(sqlSession.getConnection());  
		try {//advert.channelAdd
			sqlSession.insert(sqlId, map);
//			newTransaction.commit();
		} /*catch (SQLException e) {
			e.printStackTrace();
		} */catch (Exception e) {
			e.printStackTrace();
		} finally{
//			try {
//				newTransaction.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			sqlSession.close();
		}
	}
	
	/**
	 *功能：通用查询功能，返回分页
	 * @param pageBean
	 * @param pmap
	 * @param sqlId
	 * @return
	 */
	public List commonQuery(PageBean pageBean, Map pmap, String sqlId) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
			Map map = new HashMap();
			map.putAll(pmap);
			map.put("count", "1");
			List list = sqlSession.selectList(sqlId,map);
			Map cmap = (Map) list.get(0);
			Long ctLong = (Long)cmap.get("ct");
			int ct = new Long(ctLong).intValue();
			if (ct > 0){
				pageBean.setTotalNum(ct);
				map.remove("count");
				map.put("detail", "1");
				map.put("limit", "1");
				map.put("start", (pageBean.pageNum-1) * pageBean.getPageSize());
				map.put("pageSize", pageBean.getPageSize());
				list = sqlSession.selectList(sqlId,map);
				pageBean.setPage(list);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return null;
	}
	
	/**
	 *功能：通用查询功能，返回一条记录Map 
	 * @param pageBean
	 * @param pmap
	 * @return
	 */
	public Object queryOne(String sqlId,Map pmap){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
			Object obj = sqlSession.selectOne(sqlId, pmap);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return null;
	}
	
	/**
	 *功能：通用修改功能
	 * @param map
	 */
	public int commonUpdate(String sqlId,Map <String,String>map){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try { 
			return sqlSession.update(sqlId, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return 0;
	}
	
	/**
	 *功能：通用删除功能
	 * @param map
	 */
	public int commonDelete(String sqlId,Map <String,String>map){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try { 
			return sqlSession.delete(sqlId, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			sqlSession.close();
		}
		return 0;
	}
}
