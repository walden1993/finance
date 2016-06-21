package com.sp2p.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.constants.IConstants;
import com.sp2p.dao.MyHomeInfoSettingDao;
import com.sp2p.dao.PayTreasureDao;
import com.sp2p.dao.PersonDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.RelationDao;
import com.sp2p.dao.admin.ShoveBorrowAmountTypeDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_payinvest;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.dao.MySQL;

/**
 * 手机号码注册
 * 
 * @author lw
 * 
 */
public class CellPhoneService extends BaseService {
	private UserDao userDao;
	private RelationDao relationDao;
	private PersonDao personDao;
	private ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao;
	private MyHomeInfoSettingDao myHomeInfoSettingDao;
	
	PayTreasureDao payTreasureDao;
	
	

	public void setPayTreasureDao(PayTreasureDao payTreasureDao) {
		this.payTreasureDao = payTreasureDao;
	}

	public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
		return myHomeInfoSettingDao;
	}

	public void setMyHomeInfoSettingDao(MyHomeInfoSettingDao myHomeInfoSettingDao) {
		this.myHomeInfoSettingDao = myHomeInfoSettingDao;
	}

	public static Log log = LogFactory.getLog(CellPhoneService.class);

	/**
	 * 用户注册
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long usercellRegister(String cellphone, String userName,
			String password, String refferee, Map<String, Object> userMap,
			int typeLen,Integer userType,String orgName) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		String email = null;
		String dealpwd = null; // 交易密码
		String mobilePhone = cellphone;
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao
				.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		long result = 1L;
		boolean flag = false;
		try {
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit,userType,orgName);

			// 将手机号码同步到t_person表中
			if(userId<0){
				return -1L;
			}
			
			if(userType==1){
				personDao.addPerson(conn, null, cellphone, null, null,
						null, null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, userId, null, null, -1,
						null, userMap ==null || userMap.get("source")==null ?null:(String)userMap.get("source"));
			}
			

			if (result <= 0) {
				return -1L;
			}
			//绑定手机
			myHomeInfoSettingDao.addBindingMobile(conn,
					cellphone, userId, IConstants.PHONE_BINDING_ON,//修改为已绑定
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				try {
					result = userDao.addMaterialsauth1(conn, userId, i);
					if (result <= 0) {
						return -1L;
					}
				} catch (Exception e) {
					return -1L;
				}
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
			}
			//初始化张新宝账户
			Map<String, String> payInvest = new HashMap<String, String>();
			payInvest.put("userid", userId+"");
			payInvest.put("amount", "0");
			payTreasureDao.addPayInvest(conn, payInvest);
			
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return userId;
	}
	
	public Long usercellRegister2(Map<String, Object> userMap,Map m) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		String cellphone=(String) m.get("mobilePhone"); 
		String userName=(String) m.get("userName"); 
		String password=(String) m.get("password");  
		String refferee=(String) m.get("refferee"); 
//		Map<String, Object> userMap,
		int typeLen=(Integer) m.get("typelen"); 
		Integer userType=(Integer) m.get("userType"); 
		String orgName=(String) m.get("orgName"); 
		
		String email = null;
		String dealpwd = null; // 交易密码
		String mobilePhone = cellphone;
		Integer rating = 0;// 网站积分
		Integer creditrating = 0;// 信用积分
		String lastIP = "";
		String lastDate = null;// 最后登录时间
		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
		// DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String vipcreatetime = null;// VIP创建时间
		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
		String headImg = "";// 用户头型
		Integer enable = 1; // 是否禁用 1、启用 2、禁用
		// 测试--跳过验证
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 得到信息额度类型
		Map<String, String> map = shoveBorrowAmountTypeDao
				.queryBorrowAmountByNid(conn, "credit");
		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
		if (IConstants.ISDEMO.equals("1")) {
			authstep = 1;
			enable = 1;
			vipstatus = 2;
			vipcreatetime = df.format(new Date());
			creditlimit = creditLimit;
		}
		Long servicePersonId = null;
		long userId = -1L;
		long result = 1L;
		boolean flag = false;
		try {
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit,userType,orgName);

			// 将手机号码同步到t_person表中
			if(userId<0){
				return -1L;
			}
			
			if(userType==1){
				personDao.addPerson(conn, null, cellphone, null, null,
						null, null, null, null, null, null, null, null, null, null,
						null, null, null, null, null, userId, null, null, -1,
						null,(String)m.get("source"));
			}
			

			if (result <= 0) {
				return -1L;
			}
			//绑定手机
			myHomeInfoSettingDao.addBindingMobile(conn,
					cellphone, userId, IConstants.PHONE_BINDING_ON,//修改为已绑定
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				try {
					result = userDao.addMaterialsauth1(conn, userId, i);
					if (result <= 0) {
						return -1L;
					}
				} catch (Exception e) {
					return -1L;
				}
			}
			if (userMap != null) {
				relationDao.addRelation(conn, userId, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
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

		return userId;
	}
	/**
     * 用户注册(后台手动添加)
     * 
     * @param email
     * @param username
     * @param password
     * @param refferee
     * @return
     * @throws SQLException
     * @throws DataException
     */
    public Long usercellRegister(String cellphone, String userName,
            String password, String refferee, Map<String, Object> userMap,
            int typeLen,Integer userType,String orgName,String idNo,String realName) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        Connection conn = MySQL.getConnection();

        String email = null;
        String dealpwd = null; // 交易密码
        String mobilePhone = cellphone;
        Integer rating = 0;// 网站积分
        Integer creditrating = 0;// 信用积分
        String lastIP = "";
        String lastDate = null;// 最后登录时间
        Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
        // DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String vipcreatetime = null;// VIP创建时间
        double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
        Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
        String headImg = "";// 用户头型
        Integer enable = 1; // 是否禁用 1、启用 2、禁用
        // 测试--跳过验证
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 得到信息额度类型
        Map<String, String> map = shoveBorrowAmountTypeDao
                .queryBorrowAmountByNid(conn, "credit");
        double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
        if (IConstants.ISDEMO.equals("1")) {
            authstep = 1;
            enable = 1;
            vipstatus = 2;
            vipcreatetime = df.format(new Date());
            creditlimit = creditLimit;
        }
        Long servicePersonId = null;
        long userId = -1L;
        long result = 1L;
        boolean flag = false;
        try {
            userId = userDao.addUser(conn, email, userName, password, refferee,
                    lastDate, lastIP, dealpwd, mobilePhone, rating,
                    creditrating, vipstatus, vipcreatetime, authstep, headImg,
                    enable, servicePersonId, creditLimit,userType,orgName);

            // 将手机号码同步到t_person表中
            if(userId<0){
                return -1L;
            }
            
            if(userType==1){
            	String source = "";
            	if (null != userMap){
            		source = (String)userMap.get("source");
            	}
            	
                personDao.addPerson(conn, realName , cellphone, null, null,
                        null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, userId, null, idNo , -1,
                        null,source);
            }
            

            if (result <= 0) {
                return -1L;
            }
            //绑定手机
            myHomeInfoSettingDao.addBindingMobile(conn,
                    cellphone, userId, IConstants.PHONE_BINDING_ON,//修改注册成功手机为已绑定
                    "手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
            // 初始化验证资料
            for (long i = 1; i <= typeLen; i++) {
                try {
                    result = userDao.addMaterialsauth1(conn, userId, i);
                    if (result <= 0) {
                        return -1L;
                    }
                } catch (Exception e) {
                    return -1L;
                }
            }
            if (userMap != null) {
                relationDao.addRelation(conn, userId, (Long) userMap
                        .get("parentId"), (Integer) userMap.get("level"), 1);
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

        return userId;
    }
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	// 通过用户手机号码更改用户登录密码

	public Long updatepasswordBycellphone(String cellphone, String password)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Long resultId = -1L;

		try {
			resultId = userDao.updatepasswordBycellphone(conn, cellphone,
					password);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return resultId;
	}

	/**
	 * 根据手机号码查询
	 * 
	 * @param cellphone
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryCellPhone(String cellphone)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = personDao.querCellPhone(conn, cellphone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();

			throw e;
		} finally {
			conn.close();
		}

		return map;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public void setShoveBorrowAmountTypeDao(
			ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao) {
		this.shoveBorrowAmountTypeDao = shoveBorrowAmountTypeDao;
	}

}
