package com.sp2p.service.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.dao.UserIntegralDao;
import com.sp2p.dao.admin.UserManageDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_trial_ticket;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;

/**
 * 后台用户管理
 * 
 * @author lw
 * 
 */
public class UserManageServic extends BaseService {
	public static Log log = LogFactory.getLog(UserManageServic.class);
	private UserManageDao userManageDao;
	private UserIntegralDao userIntegralDao;

	public UserIntegralDao getUserIntegralDao() {
		return userIntegralDao;
	}

	public void setUserIntegralDao(UserIntegralDao userIntegralDao) {
		this.userIntegralDao = userIntegralDao;
	}

	private List<Map<String, Object>> paymentMode;
//	private List<Map<String, Object>> deadline;

	public void setPaymentMode(List<Map<String, Object>> paymentMode) {
		this.paymentMode = paymentMode;
	}

//	public void setDeadline(List<Map<String, Object>> deadline) {
//		this.deadline = deadline;
//	}

	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}

	/**
	 * 用户基本信息管理
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserManageBaseInfo(PageBean<Map<String, Object>> pageBean,
			String userName, String realName,String orgName,String userType) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		orgName = Utility.filteSqlInfusion(orgName);
		userType = Utility.filteSqlInfusion(userType);
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(userName)) {
				condition.append(" and username  like '%"
						+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(realName)) {
				condition.append(" and realName like '%"
						+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(orgName)) {
				condition.append(" and orgName like '%"
						+ StringEscapeUtils.escapeSql(orgName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(userType) &&  !"-1".equals(userType)) {
				condition.append(" and userType = '"
						+ StringEscapeUtils.escapeSql(userType.trim())+"'");
			}

			dataPage(conn, pageBean, "v_t_usermanage_baseinfo", "*",
					" order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 用户基本信息管理
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserManageBaseInfo(PageBean<Map<String, Object>> pageBean)
			throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, "v_t_usermanage_baseinfo", "*", "", "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	// 用户基本信息列表查看
	public void queryUserManageInfo(PageBean<Map<String, Object>> pageBean,
			String userName, String realName,String orgName,String userType) throws Exception {
		userName = Utility.filteSqlInfusion(userName);
		realName = Utility.filteSqlInfusion(realName);
		orgName = Utility.filteSqlInfusion(orgName);
		userType = Utility.filteSqlInfusion(userType);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(userName)) {
				condition.append(" and username  like '%"
						+ StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(realName)) {
				condition.append(" and realName like '%"
						+ StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(orgName)) {
				condition.append(" and orgName like '%"
						+ StringEscapeUtils.escapeSql(orgName.trim()) + "%' ");
			}
			if (StringUtils.isNotBlank(userType) &&  !"-1".equals(userType)) {
				condition.append(" and userType = '"
						+ StringEscapeUtils.escapeSql(userType.trim())+"'");
			}
			dataPage(conn, pageBean, "v_t_usermanage_info", "*",
					" order by id desc", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * * queryUserManageInfo(重写)
	 * @auth hejiahua
	 * @param pageBean
	 * @param userName
	 * @param realName
	 * @param orgName
	 * @param userType
	 * @param createTime
	 * @param lastLoginTime
	 * @param refereName
	 * @throws Exception 
	 * void
	 * @exception 
	 * @date:2014-10-15 上午9:43:18
	 * @since  1.0.0
	 */
    public void queryUserManageInfo(PageBean<Map<String, Object>> pageBean,
            String userName, String realName,String orgName,String userType,String createTimeStart,String lastLoginTimeStart,
            String createTimeEnd,String lastLoginTimeEnd,String refereName,String sms) throws Exception {
        userName = Utility.filteSqlInfusion(userName);
        realName = Utility.filteSqlInfusion(realName);
        orgName = Utility.filteSqlInfusion(orgName);
        userType = Utility.filteSqlInfusion(userType);
        createTimeEnd = Utility.filteSqlInfusion(createTimeEnd);
        createTimeStart = Utility.filteSqlInfusion(createTimeStart);
        lastLoginTimeEnd = Utility.filteSqlInfusion(lastLoginTimeEnd);
        lastLoginTimeStart = Utility.filteSqlInfusion(lastLoginTimeStart);
        refereName = Utility.filteSqlInfusion(refereName);
        sms = Utility.filteSqlInfusion(sms);
        
        Connection conn = MySQL.getConnection();
        StringBuffer condition = new StringBuffer();
        try {
            if (StringUtils.isNotBlank(userName)) {
                condition.append(" and username  like '%"
                        + StringEscapeUtils.escapeSql(userName.trim()) + "%' ");
            }
            if (StringUtils.isNotBlank(realName)) {
                condition.append(" and realName like '%"
                        + StringEscapeUtils.escapeSql(realName.trim()) + "%' ");
            }
            if (StringUtils.isNotBlank(orgName)) {
                condition.append(" and orgName like '%"
                        + StringEscapeUtils.escapeSql(orgName.trim()) + "%' ");
            }
            if (StringUtils.isNotBlank(userType) &&  !"-1".equals(userType)) {
                condition.append(" and userType = '"
                        + StringEscapeUtils.escapeSql(userType.trim())+"'");
            }
            if (StringUtils.isNotBlank(sms)) {
                condition.append(" and cellPhone ='"
                        + StringEscapeUtils.escapeSql(sms.trim()) + "' ");
            }
            
            
            if (StringUtils.isNotBlank(createTimeStart)) {
                condition.append(" and createTime >='"
                        + StringEscapeUtils.escapeSql(createTimeStart.trim()) + "' ");
            }
            
            if (StringUtils.isNotBlank(createTimeEnd)) {
                condition.append(" and createTime <='"
                        + StringEscapeUtils.escapeSql(createTimeEnd.trim()) + "' ");
            }
            
            if (StringUtils.isNotBlank(lastLoginTimeStart)) {
                condition.append(" and lastDate  >='"
                        + StringEscapeUtils.escapeSql(lastLoginTimeStart.trim()) + "' ");
            }
            
            if (StringUtils.isNotBlank(lastLoginTimeEnd)) {
                condition.append(" and lastDate   <='"
                        + StringEscapeUtils.escapeSql(lastLoginTimeEnd.trim()) + "' ");
            }
            
            if (StringUtils.isNotBlank(refereName)) {
                condition.append(" and refferee like '%"
                        + StringEscapeUtils.escapeSql(refereName.trim()) + "%' ");
            }
           
            
            dataPage(conn, pageBean, "v_t_usermanage_info_1", "*",
                    " order by id desc", condition.toString());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();

            throw e;
        } finally {
            conn.close();
        }
    }
	

	/**
	 * 用户积分管理
	 * 
	 * @param pageBean
	 * @param username
	 * @param viprecode
	 * @param creditcode
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUserManageintegralinfo(
			PageBean<Map<String, Object>> pageBean, String username,
			int viprecode, int creditcode, String userType) throws Exception {
		username = Utility.filteSqlInfusion(username);
		userType = Utility.filteSqlInfusion(userType);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(username)) {
			condition.append(" and  username  like '%"
					+ StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) &&  !"-1".equals(userType)) {
			condition.append(" and userType = '"
					+ StringEscapeUtils.escapeSql(userType.trim())+"'");
		}
		StringBuffer ordercondition = new StringBuffer();
		if (viprecode != -1 && viprecode == 1) {
			ordercondition.append(" ORDER BY   rating ");
		}
		if (viprecode != -1 && viprecode == 2) {
			ordercondition.append(" ORDER BY  rating  DESC");
		}
		if (creditcode != -1 && creditcode == 1 && viprecode == -1) {
			ordercondition.append(" ORDER BY   creditrating ");
		}
		if (creditcode != -1 && creditcode == 2 && viprecode == -1) {
			ordercondition.append("  ORDER BY   creditrating  DESC");// 大到小
		}
		if (creditcode != -1 && creditcode == 1 && viprecode != -1) {
			ordercondition.append("  , creditrating ");
		}
		if (creditcode != -1 && creditcode == 2 && viprecode != -1) {// 大到小
			ordercondition.append("  , creditrating DESC ");
		}

		try {
			dataPage(conn, pageBean, "v_t_usermanage_integralinfo", "*",
					ordercondition.toString(), condition.toString());
			// dataPage(conn, pageBean, "v_t_usermanage_integralinfo", "*", "",
			// "");
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * vip记录表
	 * 
	 * @param pageBean
	 * @param username
	 * @param apptime
	 * @param lasttime
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryUservipRecoderinfo(PageBean<Map<String, Object>> pageBean,
			String username,String userType, String apptime, String lasttime,String appendtime,String lastendtime) throws Exception {
		username = Utility.filteSqlInfusion(username);
		userType = Utility.filteSqlInfusion(userType);
		apptime = Utility.filteSqlInfusion(apptime);
		lasttime = Utility.filteSqlInfusion(lasttime);
		appendtime = Utility.filteSqlInfusion(appendtime);
		lastendtime = Utility.filteSqlInfusion(lastendtime);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(username)) {
			condition.append(" and  username  like '%"
					+ StringEscapeUtils.escapeSql(username.trim()) + "%' ");
		}
		if (StringUtils.isNotBlank(userType) &&  !"-1".equals(userType)) {
			condition.append(" and userType = '"
					+ StringEscapeUtils.escapeSql(userType.trim())+"'");
		}
		if (StringUtils.isNotBlank(apptime)) {
			condition.append(" and vipCreateTime >= '"
					+ StringEscapeUtils.escapeSql(apptime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(appendtime)) {
			condition.append(" and vipCreateTime <= '"
					+ StringEscapeUtils.escapeSql(appendtime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(lasttime)) {
			condition.append(" and vip >= '"
					+ StringEscapeUtils.escapeSql(lasttime.trim()) + "'");
		}
		if (StringUtils.isNotBlank(lastendtime)) {
			condition.append(" and vip <= '"
					+ StringEscapeUtils.escapeSql(lastendtime.trim()) + "'");
		}
		try {
			dataPage(conn, pageBean, "v_t_usermanage_viprecordinfo", "*", "",
					condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 用户基本信息里面的查看用户的基本信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryUserManageInnerMsg(Long userId)
			throws Exception {
		Map<String, String> map = null;

		Connection conn = MySQL.getConnection();
		;
		try {
			map = userManageDao.queryUserManageInnerMsg(conn, userId);

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
	 * 企业基本信息里面的查看企业的基本信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryOrgManageInnerMsg(Long userId)
			throws Exception {
		Map<String, String> map = null;

		Connection conn = MySQL.getConnection();
		;
		try {
			map = userManageDao.queryOrgManageInnerMsg(conn, userId);

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
	 * 弹出框显示信息初始化
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */

	public Map<String, String> queryUserManageaddInteral(Long userId)
			throws Exception {
		Map<String, String> map = null;

		Connection conn = MySQL.getConnection();
		;
		try {
			map = userManageDao.queryUserManageaddInteral(conn, userId);

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
	 * 查询用户信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryUserInfo(long userId) throws Exception {
		Connection conn = MySQL.getConnection();
		;
		try {
			return userManageDao.queryUserInfo(conn, userId);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			
			conn.close();
		}
	}

	public long updateUserqq(long id, String qq) throws Exception {
		long result = -1L;
		Connection conn = MySQL.getConnection();
		try {
			result = userManageDao.updateUserqq(conn, id, qq);
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
	 * 
	 *功能：修改用户密码，增加推荐人
	 * @param id
	 * @param qq
	 * @return
	 * @throws Exception
	 */
	public long updateUserInfo(Map map) throws Exception {
//		String retUserName = (String) map.get("retUserName");
//		String loginPwdHid = (String) map.get("loginPwdHid");
//		String tranPwdHid = (String) map.get("tranPwdHid");
//		int userId = (Integer) map.get("userId");
		long result = -1L;
		Connection conn = MySQL.getConnection();
		try {
			result = userManageDao.updateUserInfo(conn, map);
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
	 * 跳转到会员分明细info
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public void userintegralcreditinfo(PageBean<Map<String, Object>> pageBean,
			Long userid, Integer type) throws Exception {
		Connection conn = MySQL.getConnection();
		;
		StringBuffer condition = new StringBuffer();
		try {
			if (userid != -1L) {
				condition.append(" AND id = " + userid + " AND type = " + type);
				dataPage(conn, pageBean, "v_t_usermanage_integralinner", "*",
						"", condition.toString());
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询用投资管理
	 * 
	 * @param pageBean
	 * @param userid
	 * @throws SQLException
	 */
	public void queryUserManageInvest(PageBean<Map<String, Object>> pageBean,
			Long userid, String createtimeStart, String createtimeEnd)
			throws Exception {
		createtimeStart = Utility.filteSqlInfusion(createtimeStart);
		createtimeEnd = Utility.filteSqlInfusion(createtimeEnd);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (userid != -1L) {
				condition.append(" AND investor = " + userid);
			}
			if (StringUtils.isNotBlank(createtimeStart)) {
				condition.append(" and investTime >'"
						+ StringEscapeUtils.escapeSql(createtimeStart.trim())
						+ "'");
			}
			if (StringUtils.isNotBlank(createtimeEnd)) {
				condition.append(" and investTime <'"
						+ StringEscapeUtils.escapeSql(createtimeEnd.trim())
						+ "'");
			}
			dataPage(conn, pageBean, "v_t_usermanage_invest", "*", "",
					condition.toString());

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

	}

	/**
	 * 查询用户积分详情
	 * 
	 * @param userId
	 * @param score
	 * @param type
	 * @param typeStr
	 * @param remark
	 * @param time
	 * @param changetype
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addIntervalDelt(Long userId, Integer score, Integer type,
			String typeStr, String remark, String changetype)
			throws Exception {
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		Long result1 = -1L;
		try {
			// 向t_user表增加积分
			result = userManageDao.addUserManageaddInteral(conn, userId, score,
					type);
			if (result < 0) {
				conn.rollback();
				return -1L;
			}

			if (type == 1) { // 向积分明细添加信用积分明细
				result1 = userManageDao.addserintegraldetail(conn, userId,
						score, typeStr, type, remark, changetype);
			}// 向积分明细添加会员积分明细
			else {
				Map<String, String> map = userIntegralDao.queryUserIntegral2(
						conn, userId, 2, typeStr);
				if (map == null|| StringUtils.isBlank(map.get("minId"))) {
					result1 = userManageDao.addserintegraldetail(conn, userId,
							score, typeStr, type, remark, changetype);
				} else {

					long changerecore = Convert.strToInt((String) map
							.get("changerecore"), 1);
					long minId = Convert.strToInt(map.get("minId"), 1);
					result1 = userIntegralDao.updateUserIntegral(conn,
							changerecore, score, minId);
				}
			}
			if (result1 < 0) {
				conn.rollback();
				return -1L;
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			
			throw e;
		}finally{
			conn.close();
		}
		return result;
	}

	/**
	 * 查询用户资金信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserCashInfo(Long userId)
			throws Exception {
		Connection conn = MySQL.getConnection();
		;
		try {
			return userManageDao.queryUserCashInfo(conn, userId);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			
			throw e;
		}finally{
			conn.close();
		}
	}

	public void changeFigure(PageBean<Map<String, Object>> pageBean) {
		List<Map<String, Object>> ll = pageBean.getPage();
		if (ll != null && ll.size() > 0) {// result rechargeType 中文显示
			for (Map<String, Object> mp : ll) {
				if (mp.get("paymentMode") != null) {
					String typeId = mp.get("paymentMode").toString();
					for (Map<String, Object> cc : this.getpaymentMode()) {
						if (cc.get("typeId").toString().equals(typeId)) {
							mp.put("paymentMode", cc.get("typeValue"));
							break;
						}
					}
				}
				if (mp.get("deadline") != null && mp.get("isDayThe") != null) {
					if (mp.get("isDayThe").equals(1)) {
						mp.put("deadline", mp.get("deadline") + "个月");
					} else
						mp.put("deadline", mp.get("deadline") + "天");

				}
			}
		}
	}

	public List<Map<String, Object>> getpaymentMode() {
		if (paymentMode == null) {
			paymentMode = new ArrayList<Map<String, Object>>();
			Map<String, Object> mp = null;
			mp = new HashMap<String, Object>();
			mp.put("typeId", 1);
			mp.put("typeValue", " 按月等额本息还款");
			paymentMode.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 2);
			mp.put("typeValue", "按先息后本还款");
			paymentMode.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 3);
			mp.put("typeValue", "秒还");
			paymentMode.add(mp);

			mp = new HashMap<String, Object>();
			mp.put("typeId", 4);
			mp.put("typeValue", "一次性还款");
			paymentMode.add(mp);

		}
		return paymentMode;
	}
	
	public void changeNumToStr1(PageBean<Map<String, Object>> pageBean){
		List<Map<String, Object>> list = pageBean.getPage();
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		for (Map<String, Object> map : list) {
			
			if (map.get("userType").toString().equals("1")) {
				map.put("userType", "个人");
			} else if (map.get("userType").toString().equals("2")) {
				map.put("userType", "企业");
			}
			
			if (map.get("realName") == null || map.get("realName") == "") {
				map.put("realName", "---");
			}
			if (map.get("orgName") == null || map.get("orgName") == "") {
				map.put("orgName", "---");
			}
			
		}
	}
	//查询代金券批次列表
	public void queryVouchersInfo(PageBean<Map<String, Object>> pageBean,
			String type, String state,String investor,String timeStart,String timeEnd) throws Exception {
		type = Utility.filteSqlInfusion(type);
		state = Utility.filteSqlInfusion(state);
		investor = Utility.filteSqlInfusion(investor);
		timeStart = Utility.filteSqlInfusion(timeStart);
		timeEnd = Utility.filteSqlInfusion(timeEnd);
		
		Connection conn = MySQL.getConnection();
		StringBuffer condition = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(type) &&  !"-1".equals(type)) {
				condition.append(" and type  = '"
						+ StringEscapeUtils.escapeSql(type.trim()) + "' ");
			}
			if (StringUtils.isNotBlank(state) &&  !"-1".equals(state)) {
				condition.append(" and status = '"
						+ StringEscapeUtils.escapeSql(state.trim()) + "' ");
			}
			if (StringUtils.isNotBlank(investor)) {
				condition.append(" and applyBy like '%"
						+ StringEscapeUtils.escapeSql(investor.trim()) + "%'");
			}
			if (StringUtils.isNotBlank(timeStart) && StringUtils.isNotBlank(timeEnd) && timeStart.equals(timeEnd)) {
			    condition.append(" AND  createTime  > '"
                        + StringEscapeUtils.escapeSql(timeStart+" 00:00:00") + "'");
                    condition.append(" AND  createTime  < '"
                            + StringEscapeUtils.escapeSql(timeEnd+" 23:59:59") + "'");
            }else {
                if (StringUtils.isNotBlank(timeStart)) {
                    condition.append(" AND  createTime  >= '"
                            + StringEscapeUtils.escapeSql(timeStart) + "'");
                }
                if (StringUtils.isNotBlank(timeEnd)) {
                    condition.append(" AND  createTime  <= '"
                            + StringEscapeUtils.escapeSql(timeEnd) + "'");
                }
            }
			dataPage(conn, pageBean, "t_trial_batch", "*",
					" order by id desc ", condition.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}
	}
	//下载模版
	public void downExperienceModel(int batchId,PageBean pageBean) throws SQLException   {
        Connection conn = MySQL.getConnection();
        try {
            dataPage(conn, pageBean, "t_trial_ticket", "ticketNo,userId", "order by id desc", "  and  batchId ="+batchId+" and  status =0 ");
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            conn.close();
        }
    }
	
	//查询未绑定的数量
	public long queryTicketUnbind(int batchId) throws SQLException{
	    Long unbind  =-1L;
	    Connection conn = MySQL.getConnection();
	   try {
	       Dao.Tables.t_trial_ticket ticket = new Dao().new Tables().new t_trial_ticket();
	      
	        unbind = ticket.getCount(conn, "   batchId = "+batchId+"  and  status =0");//查询出未绑定的数量
    }
    catch (Exception e) {
       e.printStackTrace();
       log.error(e);
    }finally{
        conn.close();
    }
        
        return unbind;
	}
	
	//查询代金券列表
		public void queryExperienceInfo(PageBean<Map<String, Object>> pageBean,
				String batch,String number,String name,String state,String project,String timeStart,String timeEnd) throws Exception {
			batch = Utility.filteSqlInfusion(batch);
			number = Utility.filteSqlInfusion(number);
			name = Utility.filteSqlInfusion(name);
			state = Utility.filteSqlInfusion(state);
			project = Utility.filteSqlInfusion(project);
			timeStart = Utility.filteSqlInfusion(timeStart);
			timeEnd = Utility.filteSqlInfusion(timeEnd);
			
			Connection conn = MySQL.getConnection();
			StringBuffer condition = new StringBuffer();
			try {
				if (StringUtils.isNotBlank(batch)) {
					condition.append(" and batchId  = '"
							+ StringEscapeUtils.escapeSql(batch.trim()) + "' ");
				}
				if (StringUtils.isNotBlank(number)) {
					condition.append(" and ticketNo  like '%"+ StringEscapeUtils.escapeSql(number.trim()) + "%' ");
				}
				if (StringUtils.isNotBlank(name)) {
					condition.append(" and username like '%"
							+ StringEscapeUtils.escapeSql(name.trim()) + "%' ");
				}
				if (StringUtils.isNotBlank(state) &&  !"-1".equals(state)) {
				    condition.append(" and ticketStatus = '"
                            + StringEscapeUtils.escapeSql(state.trim()) + "' ");
                }
				if (StringUtils.isNotBlank(project)) {
					condition.append(" and borrowTitle like '%"
							+ StringEscapeUtils.escapeSql(project.trim()) + "%' ");
				}
				if (StringUtils.isNotBlank(timeStart)) {
					condition.append(" AND  bindingTime  >= '"
							+ StringEscapeUtils.escapeSql(timeStart) + "'");
				}
				if (StringUtils.isNotBlank(timeEnd)) {
					condition.append(" AND  bindingTime  <= '"
							+ StringEscapeUtils.escapeSql(timeEnd) + "'");
				}
				dataPage(conn, pageBean, "v_t_trial_ticket", "*",
						" order by bindingTime desc,ticketId desc ", condition.toString());
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();

				throw e;
			} finally {
				conn.close();
			}
		}
		
		/**
		 * 批量绑定体验券
		 * bindExperienceMany(这里用一句话描述这个方法的作用)
		 * (这里描述这个方法适用条件 – 可选)
		 * @auth hejiahua
		 * @param data
		 * @return
		 * @throws SQLException 
		 * List<Map<String,String>>
		 * @exception 
		 * @date:2014-9-8 上午1:42:46
		 * @since  1.0.0
		 */
		public List<Map<String, Object>>  bindExperienceMany(String [][] data) throws SQLException{
		    //string[0][0] = {userid,ticketNo};
		    List<Map<String, Object>>  error = new ArrayList<Map<String,Object>>();
		    try {
                for (int i = 0; i < data.length; i++) {//从标题列之后开始
                    String userId = data[i][1];//用户id
                    String ticketNo = data[i][0];//体验券号
                    Connection conn = MySQL.getConnection();
                    try {
                        Dao.Tables.t_user user = new Dao().new Tables().new t_user();
                        DataSet dataSet = user.open(conn, "id", " username = '" + StringEscapeUtils.escapeSql(userId) + "' " , " ", -1, -1);
                        Map<String, String>  ids = BeanMapUtils.dataSetToMap(dataSet);
                        userId = ids.get("id");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }finally{
                        conn.close();
                    }
                    if (StringUtils.isBlank(userId) || StringUtils.isBlank(ticketNo)) {
                        continue;
                    }
                    Map<String, Object> map = updateMyTicket(userId, ticketNo);
                    map.put("userId", userId);
                    map.put("ticketNo", ticketNo);
                    error.add(map);
                }
            }
            catch (Exception e) {
                log.error(e);
                e.printStackTrace();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("ret", "exception");
                map.put("ret_desc",e.getMessage());
                error.add(map);
            }
		    return error;
		}
		
		
		/**
		 * 查询体验券
		 * queryTicketById(这里用一句话描述这个方法的作用)
		 * (这里描述这个方法适用条件 – 可选)
		 * @auth hejiahua
		 * @param id
		 * @return
		 * @throws SQLException 
		 * Map<String,String>
		 * @exception 
		 * @date:2014-9-7 上午12:48:37
		 * @since  1.0.0
		 */
		public Map<String, String> queryTicketById(Long id) throws SQLException{
		    Connection conn = MySQL.getConnection();
		    try {
                DataSet ds =  Database.executeQuery(conn, "SELECT ticketId,ticketNo,amount  FROM v_t_trial_ticket where ticketId="+id);
                return BeanMapUtils.dataSetToMap(ds);
            }
            catch (Exception e) {
                log.error(e);
                e.printStackTrace();
            }finally{
                conn.close();
            }
		    return null;
		}
		
		  /**
	     * 激活我的体验券信息
	     * updateMyTicket
	     * @auth hejiahua
	     * @param userId
	     * @param ticketNo
	     * @return
	     * @throws Exception 
	     * Map<String,String>
	     * @exception 
	     * @date:2014-9-7 上午3:29:34
	     * @since  1.0.0
	     */
	    public Map<String, Object> updateMyTicket(String userId, String ticketNo) throws Exception {
	        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
	        ticketNo = Utility.filteSqlInfusion(ticketNo);
	        Connection conn = MySQL.getConnection();
	        long ret = -1;
	        DataSet ds = new DataSet();
	        List<Object> outParameterValues = new ArrayList<Object>();
	        Map<String, Object> map = new HashMap<String, Object>();
	        try {
                int userid =Convert.strToInt(userId, -1);
	           /* if (StringUtils.isNumeric(userId)) {
	                userid = Convert.strToInt(userId, -1);
                }else {*/
                    //这个id查询不到这个用户，就是用用户名
                    //Dao.Tables.t_user user = new Dao().new Tables().new t_user();
                    //DataSet dataSet = user.open(conn, "id", " username = '" + StringEscapeUtils.escapeSql(userId) + "' " , " ", -1, -1);
                    //Map<String, String>  ids = BeanMapUtils.dataSetToMap(dataSet);
                    //userid = Convert.strToInt(ids.get("id"), -1);
                //}
	            
	            Procedures.p_trial_ticket_binding(conn, ds, outParameterValues,
	                                              userid, ticketNo, 0, "");
	            ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
	            map.put("ret", ret + "");
	            map.put("ret_desc", outParameterValues.get(1) + "");
	            if (ret <= 0) {
	                conn.rollback();
	            }
	            conn.commit();
	        } catch (Exception e) {
	            log.error(e);
	            e.printStackTrace();
	            conn.rollback();
	        } finally {
	            conn.close();
	        }
	        return map;
	    }
		
		/**
		 * 新增代金券批次
		 * 
		 * @return
		 * @throws SQLException
		 * @throws DataException
		 */
		public Long addVouchersInfo(String title,Integer type,String amount,Integer number, String timeStart,
				String timeEnd, String applicant,String departments,String operation)
				throws Exception {
			Connection conn = MySQL.getConnection();
			;
			try {
				return userManageDao.addVouchersInfo(conn, title, type, amount, number, timeStart, timeEnd, applicant, departments, operation);
			}catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				
				throw e;
			}finally{
				conn.close();
			}
		}
		
		public Map<String, String> queryTrialGenerateTicket(int batchId) throws Exception{
			Connection conn = MySQL.getConnection();
			long ret = -1;
			Map<String, String> map = new HashMap<String, String>();
			try {
				DataSet ds = new DataSet();
				List<Object> outParameterValues = new ArrayList<Object>();
				Procedures.p_trial_generate_ticket(conn, ds, outParameterValues, batchId ,0,"");
				ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
				map.put("ret", ret + "");
				map.put("ret_desc", outParameterValues.get(1) + "");
				if (ret <= 0) {
					conn.rollback();
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
			return map;
		}
		
		/**
		 * 对体验券批次进行解冻或冻结
		 * experienceUp(这里用一句话描述这个方法的作用)
		 * (这里描述这个方法适用条件 – 可选)
		 * @auth hejiahua
		 * @param id
		 * @param stauts
		 * @return
		 * @throws SQLException 
		 * Long
		 * @exception 
		 * @date:2014-9-4 下午4:45:50
		 * @since  1.0.0
		 */
		public Long experienceUp(Long id,int status) throws SQLException{
		    Connection conn = MySQL.getConnection();
		    Long result = -1L;
		    try {
                    Dao.Tables.t_trial_batch batch = new Dao().new Tables().new t_trial_batch();
                    batch.status.setValue(status);
                    result = batch.update(conn, "id="+id);
                    conn.commit();
            }
            catch (Exception e) {
               log.error(e);
               e.printStackTrace();
               conn.rollback();
            }finally{
                conn.close();
            }
		    return result;
		}
		/**
         * 对体验券进行解冻或冻结
         * experienceUp(这里用一句话描述这个方法的作用)
         * (这里描述这个方法适用条件 – 可选)
         * @auth hejiahua
         * @param id
         * @param stauts
         * @return
         * @throws SQLException 
         * Long
         * @exception 
         * @date:2014-9-4 下午4:45:50
         * @since  1.0.0
         */
		public Long experienceUp(Long id,int status,String isBatch) throws SQLException{
            Connection conn = MySQL.getConnection();
            Long result = -1L;
            try {
                    Dao.Tables.t_trial_ticket ticket = new Dao().new Tables().new t_trial_ticket();
                    if(status==0)//如果是解冻。先判断这个体验券是否已经被绑定了。如果是绑定了。要改成绑定--3
                    {
                        Map<String, String> map = BeanMapUtils.dataSetToMap(ticket.open(conn, "userId", "  id="+id, "", -1, -1));
                        if (map.get("userId")!=null  && !map.get("userId").equals("")) {
                            status = 3;
                            conn.close();
                            conn = MySQL.getConnection();
                        }
                    }
                    ticket.status.setValue(status);
                    result = ticket.update(conn, "id="+id);
                    conn.commit();
            }
            catch (Exception e) {
               log.error(e);
               e.printStackTrace();
               conn.rollback();
            }finally{
                conn.close();
            }
            return result;
        }
		
		
	/**
	 *功能：修改真名，身份证号，且实名认证通过
	 * @param id
	 * @param qq
	 * @return
	 * @throws Exception
	 */
	public long updatePersonAuth(Map <String,Object>map) throws Exception {
		long result = -1L;
		Connection conn = MySQL.getConnection();
		try {
			result = userManageDao.updatePersonAuth(conn, map);
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
		
}
