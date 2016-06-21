package com.sp2p.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.omg.CORBA.portable.Streamable;

import com.allinpay.ets.client.StringUtil;
import com.sp2p.constants.IAmountConstants;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Field;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.shove.web.util.ServletUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.MyHomeInfoSettingDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.UserDao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.entity.User;
import com.sp2p.service.admin.FundManagementService;

/**
 * @ClassName: CallCenterService.java
 * @Author: li.hou
 * @Descrb: 我的帐户 个人设置
 */
public class HomeInfoSettingService extends BaseService {

	public static Log log = LogFactory.getLog(FinanceService.class);
	private MyHomeInfoSettingDao myHomeInfoSettingDao;
	private FundRecordDao fundRecordDao;
	private OperationLogDao operationLogDao;
	private UserDao userDao;

	private SelectedService selectedService;
	private FundManagementService fundManagementService;
	private OperationLogService operationLogService;
	private UserService userService;
	
	

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Long updateUserPassword(long userId, String password, String type)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		Map<String, String> userMap = new HashMap<String, String>();
		try {
			userMap = userDao.queryUserById(conn, userId);
			result = myHomeInfoSettingDao.updateUserPassword(conn, userId,
					password, type);
			if (type.endsWith("login")) {
				// 添加系统操作日志
				result = operationLogDao.addOperationLog(conn, "t_user",
						Convert.strToStr(userMap.get("username"), ""),
						IConstants.UPDATE, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "修改会员登陆密码", 1);
			} else {
				// 添加系统操作日志
				result = operationLogDao.addOperationLog(conn, "t_user",
						Convert.strToStr(userMap.get("username"), ""),
						IConstants.UPDATE, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "修改会员交易密码", 1);
			}

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
	 * 根据id获得交易密码
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> getDealPwd(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.getDealPwd(conn, userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> queryCardStatus(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBankInfoCardStauts(conn, userId,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	//查询某个账号是否已经被该用户绑定过了
	public Map<String, String> queryBankCardisBanding(long userId,String cardNo)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBankCardisBanding(conn, userId, cardNo,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 * 添加银行卡
	 * addBankCardInfo
	 * @auth hejiahua
	 * @param userId
	 * @param cardUserName
	 * @param bankName
	 * @param subBankName
	 * @param bankCard
	 * @param cardStatus
	 * @param city 市
	 * @param province 省
	 * @return
	 * @throws Exception 
	 * Long
	 * @exception 
	 * @date:2014-12-4 下午1:29:40
	 * @since  1.0.0
	 */
    public Long addBankCardInfo(long userId, String cardUserName,
            String bankName, String subBankName, String bankCard, int cardStatus,int city,int province)
            throws Exception {
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            result = myHomeInfoSettingDao.addBankCardInfo(conn, userId,
                    cardUserName, bankName, subBankName, bankCard, cardStatus,city,province);

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
	 * 添加银行卡
	 * addBankCardInfo
	 * @auth hejiahua
	 * @param userId
	 * @param cardUserName
	 * @param bankName
	 * @param subBankName
	 * @param bankCard
	 * @param cardStatus
	 * @param card_Bank_en 英文简称
	 * @param card_type 卡类型
	 * @return
	 * @throws Exception 
	 * Long
	 * @exception 
	 * @date:2014-12-4 下午1:29:40
	 * @since  1.0.0
	 */
    public Long addBankCardInfoApp(long userId, String cardUserName,
            String bankName, String subBankName, String bankCard, int cardStatus,String card_Bank_en,String card_type,int city,int province,String vc_branchbank,String card_mobile)
            throws Exception {
        Connection conn = MySQL.getConnection();
        Long result = -1L;
        try {
            result = myHomeInfoSettingDao.addBankCardInfoApp(conn, userId,
                    cardUserName, bankName, subBankName, bankCard, cardStatus,card_Bank_en,card_type,city,province,vc_branchbank,card_mobile);

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


	public Long addBankCardInfo(long userId, String cardUserName,
			String bankName, String subBankName, String bankCard, int cardStatus)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addBankCardInfo(conn, userId,
					cardUserName, bankName, subBankName, bankCard, cardStatus);

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
	
	public List<Map<String, Object>> queryBankInfoList(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		
		try {
			return myHomeInfoSettingDao.queryBankInfoList(conn, userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public List<Map<String, Object>> queryBankInfoListApp(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		
		try {
			return myHomeInfoSettingDao.queryBankInfoListApp(conn, userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	//卡bin查询
	public Map<String, String> queryBankBinInfoList(String card_bin)
			throws Exception {
		Connection conn = MySQL.getConnection();
		
		try {
			return myHomeInfoSettingDao.queryBankBinInfoList(conn, card_bin, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	//查询共有多少成功绑定的银行卡
	public List<Map<String, Object>> queryBankInfoListCount(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBankInfoListCount(conn, userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> querySuccessBankInfoList(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		
		try {
			return myHomeInfoSettingDao.querySuccessBankInfoList(conn, userId,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public List<Map<String, Object>> querySuccessBankInfoListApp(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		
		try {
			return myHomeInfoSettingDao.querySuccessBankInfoListApp(conn, userId,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public long deleteBankInfo(long bankId) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result =  myHomeInfoSettingDao.deleteBankInfo(conn, bankId);
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
	 * 是否默认基金银行卡
	 * @param bankId
	 * @param userId
	 * @return true 是， false 否
	 * @throws Exception 
	 */
	public boolean isFundDefault(long bankId,long userId) throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			return  myHomeInfoSettingDao.isFundDefault(conn, bankId,userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	//删除已绑定的银行卡信息
	public long deleteMyBankInfo(long bankId,long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			//关联基金银行卡不能删除
			if (myHomeInfoSettingDao.isFundDefault(conn, bankId, userId)) {
				return result;
			}
			result =  myHomeInfoSettingDao.deleteMyBankInfo(conn, bankId,userId);
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

	public Map<String, String> getRealNameByUserId(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.getUserRealName(conn, userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public Long addBindingMobile(String mobile, long userId, int status,
			String content, String oldPhone) throws Exception {
		Connection conn = MySQL.getConnection();
		
		Long result = -1L;
		try {
			//Map<String, String> p_map = 
			myHomeInfoSettingDao.
			updateBindingMobile(conn, mobile, userId, IConstants.PHONE_BINDING_CANCEL, content, 1, null);
			//.queryBindingInfoByUserId(conn, userId, -1, -1);
//			Long result1 = -1L;
//			if (p_map == null || p_map.size() <= 0) {// 如果没有记录则插入手机绑定数据，否则进行更新
//				result1 = myHomeInfoSettingDao.addBindingMobile(conn,
//						mobile, userId, IConstants.PHONE_BINDING_ON,
//						"申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
//			} else {
//				result1 = myHomeInfoSettingDao.updateBindingMobile(conn,
//						mobile, userId, IConstants.PHONE_BINDING_ON,
//						"申请更换手机", IConstants.INSERT_BASE_TYPE, null);
//			}
			result = myHomeInfoSettingDao.addBindingMobile(conn,
					mobile, userId, IConstants.PHONE_BINDING_ON,
					"申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
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
	 * 查询绑定的手机号码的状态（手机号码是唯一的）
	 * 
	 * @param mobile
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingMobile(String mobile)
			throws Exception {
		Connection conn = MySQL.getConnection();
		
		try {
			return myHomeInfoSettingDao.queryBindingMoble(conn, mobile, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查看手机绑定表里面是否有该用户的手机绑定信息
	 * 
	 * @param mobile
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingMobileUserInfo(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingInfoByUserId(conn, userId,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询某用户的手机绑定信息
	 * 
	 * @param userId
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingInfoByUserId(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		
		try {
			return myHomeInfoSettingDao.queryBindingInfoByUserId(conn, userId,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> querySucessBindingInfoByUserId(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.querySucessBindingInfoByUserId(conn,
					userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public List<Map<String, Object>> queryBindingsByUserId(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingsByUserId(conn, userId, -1,
					-1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public long addNotes(long userId, boolean message, boolean mail,
			boolean notes) throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addNotes(conn, userId, message, mail,
					notes);
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
	 * 添加通知设置
	 * 
	 * @param userId
	 * @param messageReceive
	 * @param messageDeposit
	 * @param messageBorrow
	 * @param messageRecharge
	 * @param messageChange
	 * @param mailReceive
	 * @param mailDeposit
	 * @param mailBorrow
	 * @param mailRecharge
	 * @param mailChange
	 * @param noteReceive
	 * @param noteDeposit
	 * @param noteBorrow
	 * @param noteRecharge
	 * @param noteChange
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addNotesSetting(long userId, boolean messageReceive,
			boolean messageDeposit, boolean messageBorrow,
			boolean messageRecharge, boolean messageChange,
			boolean mailReceive, boolean mailDeposit, boolean mailBorrow,
			boolean mailRecharge, boolean mailChange, boolean noteReceive,
			boolean noteDeposit, boolean noteBorrow, boolean noteRecharge,
			boolean noteChange) throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addNotesSetting(conn, userId,
					messageReceive, messageDeposit, messageBorrow,
					messageRecharge, messageChange, mailReceive, mailDeposit,
					mailBorrow, mailRecharge, mailChange, noteReceive,
					noteDeposit, noteBorrow, noteRecharge, noteChange);
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
	 * 获取通知设置总类型
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryNotesList(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryNotes(conn, userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 获得某种通知设置下面的详细通知设置
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryNotesSettingList(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryNotesSetting(conn, userId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 加载站内信信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryMailList(long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryMailList(conn, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 添加邮件信息
	 * 
	 * @param sendUserId
	 * @param receiverUserId
	 * @param title
	 * @param content
	 * @param mailStatus
	 * @param mailType
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long addMail(long sendUserId, long receiverUserId, String title,
			String content, int mailStatus, int mailType) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addMail(conn, sendUserId,
					receiverUserId, title, content, mailStatus, mailType);
			if (result <= 0) {
				conn.rollback();
				return -1L;
			} else {
				userMap = userDao.queryUserById(conn, sendUserId);
				// 添加操作日志
				operationLogDao.addOperationLog(conn, "t_mail", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.INSERT, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "发送站内信", 1);
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
	 * 添加邮件信息
	 * 
	 * @param sendUserId
	 * @param receiverUserId
	 * @param title
	 * @param content
	 * @param mailStatus
	 * @param mailType
	 * @return
	 * @throws SQLException
	 */
	public long addMail(long sendUserId, long receiverUserId, String title,
			String content, int mailStatus, Integer mailMode, int mailType)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.addMail(conn, sendUserId,
					receiverUserId, title, content, mailStatus, mailMode,
					mailType);
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
	 * 获得收件箱信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryReceiveMails(PageBean pageBean, long userId, int mailType,
			String type, int mailStatus) throws Exception {
		type = Utility.filteSqlInfusion(type);
		
		Connection conn = MySQL.getConnection();
		String condition = "";
		if (type.equalsIgnoreCase("sys")) {
			condition = " and reciver='" + userId + "' and mailType="
					+ mailType + "  and mailMode=" + IConstants.MAIL_SYS_;
		} else {
			condition = " and reciver='" + userId + "' and mailType="
					+ mailType + "  and mailMode=" + IConstants.MAIL_MODE;
		}
		if (mailStatus > 0) {
			condition += " and mailStatus=" + mailStatus;
		}
		try {
			dataPage(conn, pageBean, "t_mail", "*", " order by sendTime desc ",
					condition);
		} catch (Exception e) {
			log.error(e);
			
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 获得发件箱消息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public void querySendMails(PageBean pageBean, long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		String condition = " and sender=" + userId;
		try {
			dataPage(conn, pageBean, "t_mail", "*", " order by sendTime desc ",
					condition);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public long deleteMails(String ids, long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long result = -1L;
		try {
			result = myHomeInfoSettingDao.deleteMail(conn, ids);
			if (result > 0) {
				userMap = userDao.queryUserById(conn, userId);
				operationLogDao.addOperationLog(conn, "t_mail", Convert
						.strToStr(userMap.get("username"), ""),
						IConstants.DELETE, Convert.strToStr(userMap
								.get("lastIP"), ""), 0, "删除站内信", 1);
			}
			
			conn.commit();
			
			return result;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询邮件信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryEmailDetailById(long mailId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryEmailDetailById(conn, mailId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	public long updateMails(String ids, int type) throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.updateMail(conn, ids, type);
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
	 * 查询邮件信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryEmailById(long mailId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryEmailById(conn, mailId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
/**
 * 得到用户设置的所有密保问题和答案
 * @param userId
 * @return
 * @throws Exception
 */
	public Map<String, String> queryUserALLAnswer(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryUserALLAnswer(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}
	/**
	 *  保存用户密保答案
	 * @param userId
	 * @param questionOne
	 * @param questionTwo
	 * @param questionThree
	 * @param answerOne
	 * @param answerTwo
	 * @param answerThree
	 * @return
	 * @throws Exception
	 */
	public Long savePwdAnswer(long userId,String questionOne,String questionTwo,String questionThree,
			String answerOne,String answerTwo,String answerThree) throws Exception{
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.savePwdAnswer( conn, userId, questionOne, questionTwo, questionThree,
					 answerOne, answerTwo, answerThree);
			Map<String, String> user = userDao.queryUserById(conn, userId);
			operationLogDao.addOperationLog(conn, "t_pwd_answer", Convert.strToStr(user.get("username"), ""), IConstants.INSERT, 
					Convert.strToStr(user.get("lastIP"), ""), 0, "添加安全问题", 1);
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
	 * 用户修改密保答案
	 * @param conn
	 * @param userId
	 * @param questionOne
	 * @param questionTwo
	 * @param questionThree
	 * @param answerOne
	 * @param answerTwo
	 * @param answerThree
	 * @return
	 * @throws SQLException
	 */
	public Long updatePwdAnswer(long userId,String questionOne,String questionTwo,String questionThree,
			String answerOne,String answerTwo,String answerThree) throws Exception{
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = myHomeInfoSettingDao.updatePwdAnswer( conn, userId, questionOne, questionTwo, questionThree,
					 answerOne, answerTwo, answerThree);
			Map<String, String> user = userDao.queryUserById(conn, userId);
			operationLogDao.addOperationLog(conn, "t_pwd_answer", Convert.strToStr(user.get("username"), ""), IConstants.UPDATE, 
					Convert.strToStr(user.get("lastIP"), ""), 0, "修改安全问题", 1);
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
	public Map<String, String> queryBankInfoById(long bankId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBankInfoById(conn, bankId, -1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 修改个人头像的时候判断是否填写过个人信息
	 * 
	 * @return
	 */
	public Map<String, String> queryHeadImg(String username) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeInfoSettingDao.queryHeadImg(conn, username);
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
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: updatePersonImg
	 * @Param: HomeInfoSettingService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:03:11
	 * @Return:
	 * @Descb: 修改个人头像
	 * @Throws:
	 */
	public long updatePersonImg(String imgPath, Long id) throws Exception {
		Connection conn = MySQL.getConnection();

		Long result = -1L;
		try {
			Map<String, String> personMap = myHomeInfoSettingDao.queryPerson(
					conn, id);
			if (personMap != null) {
				result = myHomeInfoSettingDao
						.updatePersonImg(conn, imgPath, id);
			} else {
				result = myHomeInfoSettingDao.addPersonImg(conn, imgPath, id);
			}
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

	public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
		return myHomeInfoSettingDao;
	}

	public void setMyHomeInfoSettingDao(
			MyHomeInfoSettingDao myHomeInfoSettingDao) {
		this.myHomeInfoSettingDao = myHomeInfoSettingDao;
	}

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: queryRenewalVIP
	 * @Param: HomeInfoSettingService
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:38:26
	 * @Return:
	 * @Descb: 查询用户的续费情况
	 * @Throws:
	 */
	public Map<String, String> queryRenewalVIP(Long id) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = myHomeInfoSettingDao.queryRenewalVIP(conn, id);
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
	 * @MethodName: renewalVIPSubmit
	 * @Param: HomeInfoSettingService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 上午10:50:33
	 * @Return:
	 * @Descb: vip续费提交
	 * @Throws:
	 */
	public Map<String, String> renewalVIPSubmit(Long id,
			Map<String, Object> platformCostMap) throws Exception {
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		long returnId = -1;
		String result = "";
		Map<String, String> noticeMap = new HashMap<String, String>();
		String username = "";
		try {
			// 查询VIP会费
			double vipFee = Convert.strToDouble(platformCostMap
					.get(IAmountConstants.VIP_FEE_RATE)
					+ "", 0);
			// 查询用户是否为VIP
			Map<String, String> isVIPMap = myHomeInfoSettingDao.isVIP(conn, id);
			if (isVIPMap != null && isVIPMap.size() > 0) {
				// 查询VIP会员是否需要续费
				Map<String, String> renewalMap = myHomeInfoSettingDao
						.isRenewal(conn, id);
				if (renewalMap != null && renewalMap.size() > 0) {
					// 查询用户可用金额是否够VIP续费
					Map<String, String> userAmount = myHomeInfoSettingDao
							.isForVIPFee(conn, id, vipFee);
					if (userAmount != null && userAmount.size() > 0) {
						username = userAmount.get("username") + "";
						// 扣除VIP会费
						myHomeInfoSettingDao.decucatedVIPFee(conn, id, vipFee);
						// 添加VIP会费扣除记录
						myHomeInfoSettingDao.addVIPFeeRecord(conn, id, vipFee);
						// 查询扣除VIP会费后的账户金额
						Map<String, String> userSumMap = myHomeInfoSettingDao
								.queryUserAmountAfterHander(conn, id);
						if (userSumMap == null) {
							userSumMap = new HashMap<String, String>();
						}
						double usableSum = Convert.strToDouble(userSumMap
								.get("usableSum")
								+ "", 0);
						double freezeSum = Convert.strToDouble(userSumMap
								.get("freezeSum")
								+ "", 0);
						double forPI = Convert.strToDouble(userSumMap
								.get("forPI")
								+ "", 0);
						// 添加资金流动记录
						returnId = fundRecordDao.addFundRecord(conn, id,
								"VIP会员续费成功", vipFee, usableSum, freezeSum,
								forPI, -1, "扣除vip会员费", 0.0, vipFee, -1, -1,
								803, 0.0);
						// 消息模版
						// 站内信
						noticeMap.put("mail", "您的VIP会员续费成功,本次扣除VIP会费：￥"
								+ vipFee + "元");
						// 邮件
						noticeMap.put("email", "您的VIP会员续费成功,本次扣除VIP会费：￥"
								+ vipFee + "元");
						// 短信
						noticeMap.put("note", "尊敬的" + username
								+ ":\n    您的VIP会员续费成功,本次扣除VIP会费：￥" + vipFee
								+ "元");

						if (returnId < 1) {
							returnId = -1;
						} else {
							// 给用户发送通知
							selectedService.sendNoticeMSG(conn, id,
									"VIP会员续费成功", noticeMap,
									IConstants.NOTICE_MODE_5);
						}
						result = "1";
					} else {
						result = "您的账户可用余额不足,请充值";
					}
				} else {
					result = "您的VIP会员暂未过期,无需续费";
				}
			} else {
				result = "您还不是VIP会员,请先申请VIP会员";
			}
			if (returnId < 0) {
				conn.rollback();
			}
			
			conn.commit();
			
			map.put("result", result);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		}
		finally {
			conn.close();
		}
		
		return map;
	}

	public Long getConcernList(long userId, String receiver)
			throws Exception {
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = myHomeInfoSettingDao.getConcernList(conn, userId, receiver,
					-1, -1);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	/**
	 * 查询用户资金状态
	 * 
	 * @param userId
	 * @param cashStatus
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long queryUserCashStatus(Long userId, int cashStatus)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = myHomeInfoSettingDao.queryUserCashStatus(
					conn, userId, cashStatus);
			if (map == null || map.size() <= 0) {// 用户提现权限没有被禁用
				return 1L;
			}
			return -1L;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 
	 * @param pageBean
	 * @param userId
	 * @param type
	 *            1为查询发件箱 2为查询收件箱
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryMailList(PageBean pageBean, long userId, int type)
			throws Exception {

		Connection conn = MySQL.getConnection();
		String condition = "";
		if (type == 1) {
			condition = " and sender=" + userId;
		} else if (type == 2) {
			condition = " and reciver=" + userId;
		}

		try {
			dataPage(conn, pageBean, "v_t_mail_user", "*",
					" order by sendTime desc ", condition);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 查看用户当期是否参见过抽奖
	 * @param ternId
	 * @return
	 * @throws SQLException 
	 */
	public long queryHasAward(String ternId,long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = myHomeInfoSettingDao.queryHasAward(conn, ternId,userId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询奖品的实际金额
	 * @param paramId
	 * @param ternId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String>  queryRealMoney(int paramId,int ternId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryRealMoney(conn, paramId, ternId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			return null;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 功能：开始抽奖
	 * @param pageBean
	 * @param userId
	 * @param type 抽奖类型
	 */
	public Map<String,String> awardStart(String termId,long userId, int stype) throws Exception {
		Map<String,String> retMap = new HashMap<String, String>();
		Connection conn = MySQL.getConnection();
		
		Map<String,String> smap = myHomeInfoSettingDao.queryAwardSore(conn, userId,stype);
    	String score = (String) smap.get("sumScore");
    	if (!StringUtil.isEmpty(score)){
    		if ( Integer.parseInt(score) < 1){
    			log.info("-----error..........");
    			retMap.put("error", "抽奖次数不够了");
    			return retMap;//好币不够
    		}
    	}
		
		//得到该期的所有奖品
    	List<Map<String, Object>> infoList = myHomeInfoSettingDao.queryAwardInfo(conn, termId);
    	
		    try {
				String startTime = "2015-06-17 20:00:00";
				String endTime = "2015-07-14 18:00:00";
		    	String award_index = "";//奖品
		    	String paramId = "";
		    	String awardName = "";
		    	double realMoney = 0;
		    	
		    	//查询用户当前活动累计的投资额度
		    	StringBuffer sql = new StringBuffer();
		    	sql.append("SELECT SUM(t.`investAmount`) AS sumInvestAmount FROM t_invest t,t_borrow tb WHERE t.`borrowId` = tb.`id` AND tb.`borrowStatus` >=3 AND tb.`borrowStatus`<=5 AND t.`investor` = ").append(userId).append(" AND t.`deadline`>=3 AND t.`investTime`>='").append(startTime).append("' AND t.`investTime`<='").append(endTime).append("'");
		    	DataSet ds = MySQL.executeQuery(conn, sql.toString());
		    	Map<String, String> sumInvestMap = BeanMapUtils.dataSetToMap(ds);
		    	double sumInvestAmount = Convert.strToDouble(sumInvestMap.get("sumInvestAmount"), 0);
		    	
		    	String termIds = " (56,57,58,59,60,61,62) ";
		    	
		    	//查看用户活动期间已经获得的奖励
		    	sql.setLength(0);
		    	sql.append("SELECT SUM(tai.`realMoney`) as sumRealMoney FROM t_award_user tau,t_award_info tai WHERE tau.`awardInfoId` = tai.`id`  AND tau.`termId` IN ").append(termIds).append("  AND tau.`userId` = ").append(userId);
		    	ds = MySQL.executeQuery(conn, sql.toString());
		    	Map<String, String> sumRealMoneyMap = BeanMapUtils.dataSetToMap(ds);
		    	double sumRealMoney = Convert.strToDouble(sumRealMoneyMap.get("sumRealMoney"), 0);
		    	
		    	
		    	//用户在活动期间获得的剩余总抽奖次数
		    	sql.setLength(0);
		    	//sql.append("SELECT sum(tac.`score`) as sumScore FROM t_award_score tac WHERE tac.`createTime`>='").append(startTime).append("' AND tac.`createTime`<='").append(endTime).append("' AND tac.`userId`= ").append(userId);
		    	sql.append("SELECT t.`sumScore` FROM t_user t WHERE t.`id` = ").append(userId);
		    	ds = MySQL.executeQuery(conn, sql.toString());
		    	Map<String, String> sumScoreMap = BeanMapUtils.dataSetToMap(ds);
		    	int sumScore = Convert.strToInt(sumScoreMap.get("sumScore"), 0);
		    	
		    	//用户在活动期间获得的总抽奖次数
		    	sql.setLength(0);
		    	sql.append("SELECT sum(tac.`score`) as sumScore FROM t_award_score tac WHERE tac.`createTime`>='").append(startTime).append("' AND tac.`createTime`<='").append(endTime).append("' AND tac.`userId`= ").append(userId);
		    	ds = MySQL.executeQuery(conn, sql.toString());
		    	sumScoreMap = BeanMapUtils.dataSetToMap(ds);
		    	int allSumScore = Convert.strToInt(sumScoreMap.get("sumScore"), 0);
		    	
		    	//第一次抽奖
		    	boolean isOne = allSumScore == sumScore;
		    	
		    	if (isOne) {
		    		double maxMoney = (sumInvestAmount*0.0015-sumRealMoney)/sumScore;
		    		for (int i = 0; i < infoList.size(); i++) {
		    			Map<String, Object> awardInfo = infoList.get(i);
		    			realMoney = Convert.strToDouble(awardInfo.get("realMoney").toString(), 0);
		    			if (realMoney>=maxMoney) {
		    				award_index = awardInfo.get("id").toString();
							paramId = awardInfo.get("paramId").toString();
							awardName = awardInfo.get("awardName").toString();
			    			break;
						}
					}
		    		
				}else {
					//随机在当期进行抽奖，如果奖励大于0.15%，重新抽，一直抽到小于为止
			    	int i = 0;
			    	for (;;i++) {
			    		int random = (int)(Math.random()*(infoList.size()-1));
				    	Map<String, Object> awardInfo = infoList.get(random);
				    	realMoney = Convert.strToDouble(awardInfo.get("realMoney").toString(), 0);
				    	
				    	if (realMoney>=((sumInvestAmount*0.0015-sumRealMoney)/sumScore)) {
				    		if (i>=100) {
				    			awardInfo = infoList.get(0);
					    		award_index = awardInfo.get("id").toString();
								paramId = awardInfo.get("paramId").toString();
								awardName = awardInfo.get("awardName").toString();
				    			break;
								//throw new Exception("系统繁忙，请稍后再试...");
							}
							continue;
						}else {
							award_index = awardInfo.get("id").toString();
							paramId = awardInfo.get("paramId").toString();
							awardName = awardInfo.get("awardName").toString();
							break;
						}
					}	
				}
		    	
		    		
		        
		    	//返回奖品信息
		    	retMap.put("paramId", paramId);
		    	retMap.put("retAwardName", awardName);
		    	retMap.put("infocId",award_index+"");
		    	
				/*List <Double>rateList = new ArrayList<Double>();
				List <Integer>pramIdList = new ArrayList<Integer>();
				List <Integer>infocList = new ArrayList<Integer>();
				List <String>awardNameList = new ArrayList<String>();
				List <Integer>hitTimeList = new ArrayList<Integer>();
				int awardallNo = 0;
				
				//查询抽奖信息
				List infoList = myHomeInfoSettingDao.queryAwardInfo(conn, termId);
				double sum = 0.0D;
		    	int awardSize = infoList.size();
		    	for (int i = 0; i < awardSize; i++){
		    		
		    		Map map = (HashMap) infoList.get(i);
		    		Integer paramId = (Integer) map.get("paramId");
		    		BigDecimal hitRate = (BigDecimal) map.get("hitRate");
		    		String awardName = (String) map.get("awardName");
		    		Integer infoId = (Integer) map.get("id");
		    		Integer hitTime = (Integer) map.get("hitTime");
		    		//hitTimeList
		    		
		    		sum += hitRate.doubleValue();
		    		rateList.add(sum);
		    		pramIdList.add(paramId);
		    		infocList.add(infoId);
		    		awardNameList.add(awardName);
		    		hitTimeList.add(hitTime);
		    		if (i == 0){
		    			awardallNo = (Integer) map.get("awardNo");	
		    		}
		    	}
		    	//log.info("----------rateList:"+rateList);
		    	
		    	if (awardallNo < 1){
		    		log.info("-----error..........");
	    			retMap.put("error", "红包送完了。");
	    			return retMap;//红包送完了。
		    	}
		    	
		    	//生成随机数
		    	double a = Math.random() * 100;
				int rai = (int) (a);//[0,99]
				log.info("----------------------- rai:"+rai);
		    	//开始抽奖
		    	for (int i = 0; i < rateList.size(); i++){
		    		Double awardNo = rateList.get(i);
		    		if (rai < awardNo){
		    			log.info("您中的奖品是：" + awardNameList.get(i));
		    			retAwardName = awardNameList.get(i);
				    	awardId = pramIdList.get(i);
				    	
				    	//查询
		    			Map asm = myHomeInfoSettingDao.queryTodaySum(conn, awardId);
		    			String todaySum = (String) asm.get("sumpid");
		    			if (StringUtil.isEmpty(todaySum)){
		    				todaySum = "0";
		    			}
		    			//如果超出今日总量则，谢谢参与
				    	if (Integer.parseInt(todaySum) >= hitTimeList.get(i)){
				    		log.info("==============================超过上限，不给，谢谢参与");
				    		infocId = 1;
				    		retAwardName="谢谢参与";
					    	retMap.put("retAwardName", "谢谢参与");
					    	retMap.put("awardId", 1);
					    	awardId = 1;
				    	} else {
				    		infocId = infocList.get(i);
					    	retMap.put("retAwardName", retAwardName);
					    	retMap.put("awardId", awardId);
				    	}
		    			break;
		    		}
		    	}
		    	//抽奖意外了，则谢谢参与
		    	if (null == retMap.get("retAwardName")){
		    		log.info("-----rate.error");
		    		retMap.put("retAwardName", "谢谢参与");
			    	retMap.put("awardId", 1);
			    	awardId = 1;
			    	infocId = 1;
		    	}
		    	
		    	//超过50元、则谢谢参与
		    	if (myHomeInfoSettingDao.queryPresrent(conn, userId,"(3,4)")>0) {
		    		log.info("-----rate.error");
		    		retMap.put("retAwardName", "谢谢参与");
			    	retMap.put("awardId", 1);
			    	awardId = 1;
			    	infocId = 1;
				}
		    	
		    	Map m = new HashMap();
		    	m.put("userId", userId);
		    	m.put("termId", termId);
		    	m.put("paramId", awardId);
		    	m.put("infocId", infocId);
		    	m.put("score", 1000);
		    	
		    	
		    	//抽奖完成，扣分,插入数据到抽奖表
		    	long reuslt = myHomeInfoSettingDao.updateAwardScore(conn, m);
		    	retMap.put("awar_user_id", reuslt);
	            conn.commit();*/
		    	
		    	
		    	Map<String,Object> m = new HashMap<String, Object>();
		    	m.put("userId", userId);
		    	m.put("termId", termId);
		    	m.put("paramId", paramId);
		    	m.put("infocId", award_index);
		    	m.put("score", 1);
		    	m.put("sendAward", 1);//已经派奖
		    	//抽奖完成，扣分,插入数据到抽奖表
		    	long result = myHomeInfoSettingDao.updateAwardScore(conn, m);
		    	if (result>0) {
		    		
		    		Map<String, String> userMap = userService.queryUserById(conn,userId);
		    		
		    		String ip = ServletUtils.getRemortIp();
		    		
		    		result = userDao.addPresrent(realMoney, userId, conn);
		    		
					operationLogService.addOperationLog(conn,"t_user", userMap.get("username"), IConstants.UPDATE, ip, realMoney, result<=0?"用户获取赠送金额失败":"用户获取赠送金额成功", 1);
		    		
		    		//发放奖励直接到用户的可用余额中
		    		/*result = fundManagementService.addBackR(conn,userId, -1L,
	                        7, realMoney, "参加活动抽奖获得奖励", new Date(),
	                        IConstants.FUNDMODE_RECHARGE_AUTO, ip, userMap
	                                .get("username"), "参加活动抽奖获得奖励");
	                if (result > 0) {
	                    result = userService.updateSign(conn,userId);//更换校验码
	                    if (result>0) {
	                    	operationLogService.addOperationLog(conn,"activity",
	    	                        "程序自动", IConstants.INSERT, ip,
	    	                        realMoney, "手动充值,对象" + userMap.get("username"), 2);
	                    	
	                    	retMap.put("awar_user_id", result+"");
							conn.commit();
						}else {
							conn.rollback();
						}
	                }*/
					
					if (result > 0) {
						conn.commit();
					}else {
						conn.rollback();
					}
				}else {
					conn.rollback();
				}
	        }
	        catch (Exception e) {
	        	conn.rollback();
                log.info(e);
	            e.printStackTrace();
	            throw new Exception("系统繁忙，请稍候再试...");
	        }finally{
	            conn.close();
	        }
		
		return retMap;
	}
	
	/**
	 *功能： 添加抽奖记录
	 * @param conn
	 * @param termId
	 * @return
	 * @throws DataException
	 */
	public Long addAwardUser(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			long result = myHomeInfoSettingDao.addAwardUser(conn, userId);
			if (result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		return userId;
	}
	
	/**
	 * 
	 * addTrealName:添加信息到实名认证表
	 * @author hjh
	 * @param name
	 * @param cardNo
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public Long addTrealName(String name,String cardNo) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result =-1L;
		try {
			result = myHomeInfoSettingDao.addTrealName(conn, name,cardNo);
			if (result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		return result;
	}
	
	/**
	 * 
	 * queryTrealName:查询实名认证表
	 * @author hjh
	 * @param name
	 * @param cardNo
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public Long queryTrealName(String name,String cardNo) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result =-1L;
		try {
			result = myHomeInfoSettingDao.queryTrealName(conn, name,cardNo);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return result;
	}
	
	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
