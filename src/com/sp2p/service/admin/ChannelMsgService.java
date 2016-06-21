package com.sp2p.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.shove.base.BaseService;
import com.shove.base.CommonService;
import com.shove.vo.PageBean;

/**
 * @ClassName: ChannelMsgService.java
 * @Author: gang.lv
 * @Date: 2013-3-19 上午10:18:35
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 统计管理业务处理层
 */
public class ChannelMsgService extends CommonService {

	public static Log log = LogFactory.getLog(ChannelMsgService.class);

	/**
	 *功能：新增渠道
	 * @param map
	 */
	private void channelMsgAdd(Map <String,String>map){
//		TransactionFactory transactionFactory = new JdbcTransactionFactory();  
		SqlSession sqlSession = getSqlSessionFactory().openSession();
//		Transaction newTransaction = transactionFactory.newTransaction(sqlSession.getConnection());  
		try {
			sqlSession.insert("advert.channelAdd", map);
//			newTransaction.commit();
		} /*catch (SQLException e) {
			e.printStackTrace();
		} */catch (Exception e) {
			log.info("error,");
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
	
	
	
	
	
}
