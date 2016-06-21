package com.sp2p.service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.SMSUtil;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.SearchBasicInfoDao;
import com.sp2p.database.Dao;
import com.sp2p.service.FinanceService;
import com.sp2p.service.admin.GetMailMsgOnUpService;
import com.sp2p.service.admin.SMSInterfaceService;
import com.sp2p.util.DateUtil;

public class SendMessageService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);
	private SearchBasicInfoDao searchBasicDao;
	private ThreadPoolTaskExecutor taskExecutor;
	private JavaMailSenderImpl senderImpl;
	private AccountUsersDao accountUsersDao;
	private SMSInterfaceService sMsService;
	private FundRecordDao fundRecordDao;
	private UserService userService;
	private String from;

	private void init() {
		String host = IConstants.MAIL_HOST;
		String username = IConstants.MAIL_USERNAME;
		String password = IConstants.MAIL_PASSWORD;
		this.from = IConstants.MAIL_FROM;

		senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(host);
		senderImpl.setUsername(username);
		senderImpl.setPassword(password);

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
	}

	public SendMessageService() {
		init();
	}

	/**
	 * 邮件发送
	 * 
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long emailSend(String title, String content, Long receiver)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = searchBasicDao
					.getUserById(conn, receiver);
			if (map != null) {
				String userName = map.get("username") == null ? "" : map
						.get("username");
				String email = map.get("email") == null ? "" : map.get("email");
				// 发送通知邮件
				sendRegisterVerificationEmail(userName, email, content);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return -1L;
	}

	public Long emailSend(Connection conn, String title, String content,
			Long receiver) throws Exception {
		Map<String, String> map = searchBasicDao.getUserById(conn, receiver);
		if (map != null) {
			String userName = map.get("username") == null ? "" : map
					.get("username");
			String email = map.get("email") == null ? "" : map.get("email");
			// 发送通知邮件
			sendRegisterVerificationEmail(userName, email, content);
		}
		return -1L;
	}

	private void sendRegisterVerificationEmailMsg(String userName,
			String userEmail, String content) throws MessagingException {
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");

		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(IConstants.PRO_GLOBLE_NAME+"通知!");

		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的<span>");
		sb.append(userName);
		sb.append("</span>:</H4>");
		sb.append("<BR/>");
		sb.append("<DIV>" + content + "</DIV>");
		sb.append("<BR/>");
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendRegisterVerificationEmail(final String userName,
			final String userEmail, final String content) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					getMailSet();
					sendRegisterVerificationEmailMsg(userName, userEmail,
							content);
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		});
	}

	// ------

	public Long emailSendTemplate(String title, String content, Long receiver)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = searchBasicDao
					.getUserById(conn, receiver);
			if (map != null) {
				String userName = map.get("username") == null ? "" : map
						.get("username");
				String email = map.get("email") == null ? "" : map.get("email");
				// 发送通知邮件
				sendEmail(userName, email, content, title);
			}
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return -1L;
	}

	public Long emailSendTemplate(Connection conn, String title,
			String content, Long receiver) throws Exception {
		Map<String, String> map = searchBasicDao.getUserById(conn, receiver);
		if (map != null) {
			String userName = map.get("username") == null ? "" : map
					.get("username");
			String email = map.get("email") == null ? "" : map.get("email");
			// 发送通知邮件
			sendEmail(userName, email, content, title);
		}
		return -1L;
	}

	private void sendEmailMsg(String userName, String userEmail,
			String content, String title) throws MessagingException {
		MimeMessage msg = senderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");

		helper.setFrom(from);
		helper.setTo(userEmail);
		helper.setSubject(IConstants.PRO_GLOBLE_NAME+"通知!");

		StringBuffer sb = new StringBuffer();
		sb.append("<HTML><BODY style='border-width:0px'>");
		sb.append("<H4 style='font-weight:normal;font-size:14px'>尊敬的"+userName+"<span>");
		sb.append(userName);
		sb.append("客户，以下是您的相关业务报告：</span>:</H4>");
		sb.append("<BR/>");
		sb.append("<DIV> "
						+ "<table width='50%' border='1' cellspacing='0' cellpadding='0'>"
						+ "<tr><td align='center'>标题</td><td align='center'>"
						+ title + "</td></tr>"
						+ "<tr><td align='center'>日期</td><td align='center'>"
						+ DateUtil.dateToString(new Date()) + "</td></tr>"
						+ "<tr><td align='center'>内容</td><td align='center'>"
						+ content + "</td></tr>" + "</table></DIV>");
		sb.append("<BR/>");
		sb.append("</BODY></HTML>");
		helper.setText(sb.toString(), true);
		senderImpl.send(msg);
		try {
			getMailMsgOnUpService.saveMailCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendEmail(final String userName, final String userEmail,
			final String content, final String title) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					getMailSet();
					sendEmailMsg(userName, userEmail, content, title);
				} catch (MessagingException e) {
					log.error(e);
					e.printStackTrace();
				}
			}
		});
	}

	// ----

	/**
	 * 站内信发送
	 * 
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long mailSend(String title, String content, Long receiver)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			Map<String, String> map = new HashMap<String, String>();
			/**
			 * 添加t_mail表
			 */
			map.put("mailTitle", title);
			map.put("mailContent", content);
			map.put("sendTime", DateUtil.dateToString(new Date()));
			map.put("sender", String.valueOf(-1));
			map.put("reciver", String.valueOf(receiver));
			map.put("mailType", String.valueOf(IConstants.MALL_TYPE_SYS));
			map.put("mailStatus", String.valueOf(IConstants.MAIL_UN_READ));
			map.put("mailMode", String.valueOf(IConstants.MAIL_SYS_));
			result = searchBasicDao.addMail(conn, map);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	public Long mailSend(Connection conn, String title, String content,
			Long receiver, Long borrowId) throws Exception {
		Long result = -1L;
		try {
			Map<String, String> map = new HashMap<String, String>();
			/**
			 * 添加t_mail表
			 */
			map.put("mailTitle", title);
			map.put("mailContent", content);
			map.put("sendTime", DateUtil.dateToString(new Date()));
			map.put("sender", String.valueOf(-1));
			map.put("reciver", String.valueOf(receiver));
			map.put("mailType", String.valueOf(IConstants.MALL_TYPE_SYS));
			map.put("mailStatus", String.valueOf(IConstants.MAIL_UN_READ));
			map.put("mailMode", String.valueOf(IConstants.MAIL_SYS_));
			map.put("borrowId", String.valueOf(borrowId));
			result = searchBasicDao.addMail(conn, map);
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			result = -1L;

			throw e;
		}
		return result;
	}

	/**
	 * 短信通知 如果用户帐户没有余额，则不发送。如果有，则扣除0.1元/条。同时向资金记录表中添加记录。
	 * 
	 * @throws SQLException
	 * @throws DataException
	 * @throws UnsupportedEncodingException
	 */
	public long noteSend(String content, Long userId) throws Exception,
			UnsupportedEncodingException {
		Connection conn = MySQL.getConnection();
		try {
			int id = IConstants.PHONE_IMP;
			// 判断用户的帐号上是否有可用余额,没有就不发送短信
			Map<String, String> userUsableSum = searchBasicDao
					.queryUserUsableSum(conn, userId);
			if (userUsableSum != null) {
				Map<String, String> map = searchBasicDao.getUserMobile(conn,
						userId);
				Map<String, String> smsMap = searchBasicDao
						.getSMSById(conn, id);
				Map<String, String> sms = sMsService.getSMSById(1);
				String phone = "";
				if (map != null && map.size() > 0) {
					phone = map.get("mobilePhone");
					if (phone.length() != 11)
						return -1L;
					String retCode = SMSUtil.sendSMS(sms.get("Account"), sms
							.get("Password"), content, phone, null);
					long ret = retCode.equals("Sucess") ? 1L : -1L;
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return -1L;
	}

	public Long noteSend(Connection conn, String content, Long userId)
			throws Exception {
		long returnId = -1;
		int id = IConstants.PHONE_IMP;
		// 判断用户的帐号上是否有可用余额,没有就不发送短信
		Map<String, String> userUsableSum = searchBasicDao.queryUserUsableSum(
				conn, userId);
		if (userUsableSum != null) {
			Map<String, String> map = searchBasicDao
					.getUserMobile(conn, userId);
			Map<String, String> smsMap = searchBasicDao.getSMSById(conn, id);
			String phone = "";
			if (map != null && map.size() > 0) {
				phone = map.get("mobilePhone");
				if (phone.length() != 11)
					return -1L;
				// 发送短信
				String retCode = "0";
				try {
					retCode = SMSUtil.sendSMS(smsMap.get("Account"), smsMap
							.get("Password"), content, phone, null);
				} catch (Exception e2) {
					log.error("SMSUtil_sendSMS_error:", e2);
					e2.printStackTrace();
				}
				long ret = retCode.equals("Sucess") ? 1L : -1L;
				// 发送成功扣除短信服务费
				if (ret == 1) {
					// 查询用户金额信息
					Map<String, String> userAmount = new HashMap<String, String>();;
					try {
						// 扣除短信服务费
						returnId = searchBasicDao.deductedNoteFee(conn, userId);
						userAmount = searchBasicDao.queryUserAmount(conn, userId);
					} catch (Exception e1) {
						log.error("deductedNoteFee_error...", e1);
						e1.printStackTrace();
					}
					double usableSum = Convert.strToDouble(userAmount
							.get("usableSum")
							+ "", 0);
					double freezeSum = Convert.strToDouble(userAmount
							.get("freezeSum")
							+ "", 0);
					double dueinSum = Convert.strToDouble(userAmount
							.get("dueinSum")
							+ "", 0);
					
					Double smsFee = 0.1;
					try
					{
						Dao.Tables.t_platform_cost tpc = new Dao().new Tables().new t_platform_cost();
						DataSet  ds = tpc.open(conn, " costFee ", "alias='smsFee' and costMode='2'", "id", -1, -1);
						ds.tables.get(0).rows.genRowsMap();
						smsFee = Convert.strToDouble(ds.tables.get(0).rows.get(0).get("costFee").toString(), 0);
					}
					catch (Exception e)
					{
						log.error("query_costFee_error:", e);
						smsFee=0.1;
					}
					
					// 添加资金流动记录
					try {
						returnId = fundRecordDao.addFundRecord(conn, userId,
								"扣除短信服务费", smsFee, usableSum, freezeSum, dueinSum, -1,
								"短信提醒服务费", 0.0, smsFee, -1, -1, 801, 0.0);
					} catch (Exception e) {
						log.error("fundRecordDao_addFundRecord_error:", e);
						e.printStackTrace();
					}
					userService.updateSign(conn, userId);//更换校验码
				}
			}
		}
		return returnId;
	}

	private void getMailSet() {
		this.from = IConstants.MAIL_FROM;
		senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(IConstants.MAIL_HOST.trim());
		senderImpl.setUsername(IConstants.MAIL_USERNAME.trim());
		senderImpl.setPassword(IConstants.MAIL_PASSWORD.trim());
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.timeout", "25000");

		senderImpl.setJavaMailProperties(javaMailProperties);
	}

	public SearchBasicInfoDao getSearchBasicDao() {
		return searchBasicDao;
	}

	public void setSearchBasicDao(SearchBasicInfoDao searchBasicDao) {
		this.searchBasicDao = searchBasicDao;
	}

	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public SMSInterfaceService getSMsService() {
		return sMsService;
	}

	public void setSMsService(SMSInterfaceService msService) {
		sMsService = msService;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setSenderImpl(JavaMailSenderImpl senderImpl) {
		this.senderImpl = senderImpl;
	}

	public void setsMsService(SMSInterfaceService sMsService) {
		this.sMsService = sMsService;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	private GetMailMsgOnUpService getMailMsgOnUpService;
	public GetMailMsgOnUpService getGetMailMsgOnUpService() {
		return getMailMsgOnUpService;
	}
	public void setGetMailMsgOnUpService(GetMailMsgOnUpService getMailMsgOnUpService) {
		this.getMailMsgOnUpService = getMailMsgOnUpService;
	}
	
	
}
