package com.sp2p.dao.admin;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_mailset;
import com.sp2p.database.Dao.Tables.t_messageset;
import com.sp2p.database.Dao.Tables.t_select;

public class GetMailMsgOnUpDao {
	/**
	 * 从t_mailset表读取配置数据
	 */
	public Map getMailSet(Connection conn)
			throws SQLException, DataException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String time = formatter.format(new Date());
		StringBuffer command = new StringBuffer("SELECT a.*, IFNULL(b.count,-1) ecount,b.sendDate FROM t_mailset a LEFT JOIN  t_mail_statistic b ON a.mailaddress = b.mailaddress AND b.sendDate='")
		.append(time).append("'")
		.append(" WHERE NOT EXISTS (SELECT c.count,c.sendDate FROM t_mail_statistic c WHERE c.mailaddress = a.mailaddress AND c.sendDate='");
		command.append(time).append("'").append(" AND c.count >= ").append(IConstants.MAIL_AMOUNT).append(") ORDER BY a.id");
		
		try {
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			dataSet.tables.get(0).rows.genRowsMap();
			List <Map>list = dataSet.tables.get(0).rows.rowsMap;
			Map m = list.get(0);
			//往统计表中插入数据
			if ((Long)m.get("ecount") == -1L){
				m.put("mailsetId", m.get("id"));
				saveMailStatis(conn, m);
				conn.commit();
			}
			if ((Long)m.get("ecount") == -1L){
				m.put("ecount", 0L);
			}
			
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *功能：发送邮件统计
	 * @param conn
	 * @param map
	 * @return
	 */
	public Long saveMailCount(Connection conn){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String time = formatter.format(new Date());
		StringBuffer sb = new StringBuffer();
		sb.append(" mailaddress=").append("'");
		sb.append(IConstants.MAIL_USERNAME).append("'");
		sb.append(" and sendDate=");
		sb.append("'").append(time).append("'");
		Dao.Tables.t_mail_statistic messageset = new Dao().new Tables().new t_mail_statistic();
		DataSet dataSet = messageset.open(conn, "*", sb.toString(), "", -1, -1);
		
		try {
			Map m = BeanMapUtils.dataSetToMap(dataSet);
			//如果查出了数据，则更新
			if (null != m){
				if (IConstants.MAIL_COUNT < IConstants.MAIL_AMOUNT){
					IConstants.MAIL_COUNT++;
					messageset.count.setValue(IConstants.MAIL_COUNT);
					messageset.sendDate.setValue(time);
					messageset.updateTime.setValue(new java.util.Date());
					messageset.update(conn, "mailaddress='"+IConstants.MAIL_USERNAME+"' and sendDate='" + time+"'");
					
				} else {//如果发送超过最大时，换一个邮件地址  
					try {
						Map p = getMailSet(conn);
						IConstants.MAIL_HOST = p.get("host") + "";
						IConstants.MAIL_USERNAME = p.get("mailaddress") + "";
						IConstants.MAIL_PASSWORD = p.get("mailpassword") + "";
						IConstants.MAIL_FROM = p.get("sendmail") + "";
						IConstants.MAIL_COUNT = 1;//Integer.parseInt((Long)p.get("ecount")+"");
						IConstants.NICK = javax.mail.internet.MimeUtility
						.encodeText((String)p.get("sendname"))
						+ "";
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				
				return 1L; 
			} else {//如果没有查到数据，则插入 
				messageset.mailaddress.setValue(IConstants.MAIL_USERNAME);
				messageset.count.setValue(1);
				messageset.sendDate.setValue(time);
				messageset.updateTime.setValue(new java.util.Date());
				IConstants.MAIL_COUNT = 1;
				return messageset.insert(conn);
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		return 1L;
	}
	
	private Long saveMailStatis(Connection conn,Map map){
		Dao.Tables.t_mail_statistic messageset = new Dao().new Tables().new t_mail_statistic();
		messageset.mailaddress.setValue(map.get("mailaddress"));
		messageset.count.setValue(0);//(Long)map.get("ecount")==0 ? 1: map.get("ecount")
		messageset.mailsetId.setValue(map.get("mailsetId"));
		messageset.sendDate.setValue(new Date());
		messageset.updateTime.setValue(new Date());
		return messageset.insert(conn);
	}
	
}
