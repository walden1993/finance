package com.sp2p.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataRow;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.vo.PageBean;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.BeVipDao;
import com.sp2p.dao.MyHomeInfoSettingDao;
import com.sp2p.dao.OperationLogDao;
import com.sp2p.dao.PersonDao;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.admin.RelationDao;
import com.sp2p.dao.admin.ShoveBorrowAmountTypeDao;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.entity.LoginVerify;
import com.sp2p.entity.User;
import com.sp2p.entity.Users;
import com.sp2p.service.admin.FundManagementService;
import com.sp2p.service.admin.SendmsgService;
import com.sp2p.util.DateUtil;

public class UserService extends BaseService {

	public static Log log = LogFactory.getLog(UserService.class);

	private UserDao userDao;
	private RelationDao relationDao;
	private SendmsgService sendmsgService;
	private BeVipDao beVipDao;
	private OperationLogDao operationLogDao;
	private MyHomeInfoSettingDao myHomeInfoSettingDao;
	private ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao;
	private PersonDao personDao;
	private FundManagementService fundManagementService;
	
	

	public void setFundManagementService(FundManagementService fundManagementService) {
		this.fundManagementService = fundManagementService;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	
	public MyHomeInfoSettingDao getMyHomeInfoSettingDao() {
		return myHomeInfoSettingDao;
	}

	public void setMyHomeInfoSettingDao(
			MyHomeInfoSettingDao myHomeInfoSettingDao) {
		this.myHomeInfoSettingDao = myHomeInfoSettingDao;
	}

	public void setBeVipDao(BeVipDao beVipDao) {
		this.beVipDao = beVipDao;
	}

	public void setSendmsgService(SendmsgService sendmsgService) {
		this.sendmsgService = sendmsgService;
	}

	/**
	 * 添加用户
	 * 
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 *            推荐人
	 * @param lastDate
	 * @param lastIP
	 * @param dealpwd
	 *            交易密码
	 * @param mobilePhone
	 * @param rating
	 *            网站积分
	 * @param creditrating
	 * @param vipstatus
	 * @param vipcreatetime
	 * @param creditlimit
	 * @param authstep
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUser(String email, String userName, String password,
			String refferee, String lastDate, String lastIP, String dealpwd,
			String mobilePhone, Integer rating, Integer creditrating,
			Integer vipstatus, String vipcreatetime, Integer creditlimit,
			Integer authstep, String headImg, Integer enable,
			Long servicePersonId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long userId = -1L;
		try {
			// 得到信息额度类型
			Map<String, String> map = shoveBorrowAmountTypeDao
					.queryBorrowAmountByNid(conn, "credit");
			double creditLimit = Convert.strToDouble(map.get("creditLimit"), 0);
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit,null,null);

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
	 * 根据id查询用户是否已经绑定邮箱
	 * @param id
	 * @return
	 */
	public Map<String, String> queryEamilById(long id) throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		userDao=new UserDao();
		try {
			map=userDao.queryEmailById(conn, id);
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
	 * 根据用户id查询企业信息
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @throws SQLException
	 * @throws DataException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryCompayById( long orgId)
			throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map=userDao.queryCompayById(conn, orgId);
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
	 * 添加赠送金额
	 * @param presrent 中奖实际金额
	 * @param userId 用户ID
	 * @param awar_user_id 用户中奖ID
	 * @return
	 * @throws SQLException
	 */
	public long addPresrent(double presrent,long userId,long awar_user_id) throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result =  userDao.addPresrent(presrent, userId, conn);
			if (result>0) {
				/*Dao.Tables.t_award_user t = new Dao().new Tables().new t_award_user();
	            t.sendAward.setValue(1);
	            result = t.update(conn, "id="+awar_user_id);*/
				if (result>0) {
					conn.commit();
				}else {
					conn.rollback();
				}
				//还需要把用户的奖品中奖状态改为已派发
			}else {
				conn.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}finally{
			conn.close();
		}
		return result;
	}
	
	

	/**
	 * 添加认证图片
	 * 
	 * @param materAuthTypeId
	 * @param imgPath
	 * @param auditStatus
	 * @param userId
	 * @param authTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addImage(long materAuthTypeId, String imgPath, long userId)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long ImageId = -1L;
		try {
			ImageId = userDao.addImage(conn, materAuthTypeId, imgPath, userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return ImageId;
	}
	
	/**
	 * 检查TOKEN
	 * @param userId
	 * @param uuid
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> checkToken(Long userId,String uuid) throws SQLException{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = userDao.checkToken(conn, userId, uuid);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally{
			conn.close();
		}
		return null;
	}
	
	/**
	 * 查询TOKEN
	 * @param userId
	 * @param uuid
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> queryToken(Long userId) throws SQLException{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = userDao.queryToken(conn, userId);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}finally{
			conn.close();
		}
		return null;
	}
	
	/**
	 * 添加TOKEN
	 * 
	 * @param uuid
	 * @param userId
	 * @param checktime
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addToken(Long userid,String uuid,String checktime)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long TokenId = -1L;
		try {
			TokenId = userDao.addToken(conn, userid, uuid, checktime);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return TokenId;
	}

	/**
	 * 用户注册(存储过程处理)
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
	public Long userRegister1(String email, String userName,String orgName ,String password,
			String refferee, Map<String, Object> userMap, int typeLen,
			String mobilePhone) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		int demo = Convert.strToInt(IConstants.ISDEMO, 2);

		Long ret = -1l;
		try {
			int userType=1;//判断是否是企业用户0-不是,1-是。默认为0
			if(orgName.length()!=0){
				userType=2;
			}
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_user_register(conn, ds, outParameterValues, email,
					userName, password, refferee, demo, orgName,userType,-1, "");
			
			//Procedures.p_user_register(conn, ds, outParameterValues, email,
				//	userName, password, refferee, demo, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);

			if (ret <= 0) {
				return ret;
			}
			if (userMap != null) {
				relationDao.addRelation(conn, ret, (Long) userMap
						.get("parentId"), (Integer) userMap.get("level"), 1);
			}
			
//			if(userType==1){
//				 personDao.addPerson(conn, null, null, null, null,
//						null, null, null, null, null, null, null, null, null, null,
//						null, null, null, null, null, ret, null, null, -1,
//						null);
//			}
 			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}

		return ret;
	}
	
	/**
	 * 更新用户企业信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserCompanyData(Long Id,Long orgId, String organizationName,
			String address, Integer industory, Integer companyType, Integer companyScale,
			String foundDate, String legalPerson, String regNum,
			String organizationCode, String registeredCapital, String bankName,
			String accountName, String publicBankaccount, String website,
			String phone, String email,
			String linkmanName,  String linkmanMobile,
			String companyDescription, String headJpg)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> user = new HashMap<String, String>();
		long personId = -1L;
		try {
			personId = userDao.updateUserCompanyData(conn, Id, orgId, organizationName, 
					address, industory, companyType, companyScale, foundDate, legalPerson,
					regNum, organizationCode, registeredCapital, bankName, accountName,
					publicBankaccount, website, phone, email, linkmanName, linkmanMobile,
					companyDescription, headJpg);
			if (personId <= 0) {
				//添加用户绑定信息
				myHomeInfoSettingDao=new MyHomeInfoSettingDao(); 
				conn.rollback();
				return -1L;
			}else{
//				(Connection conn,String mobile,long userId,int status,String content,int type,String oldPhone)
				myHomeInfoSettingDao.addBindingMobile(conn,linkmanMobile,orgId,2,"填写企业基本资料时绑定手机",1,null);
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

		return personId;

	}
	
	/**
	 * 查询用户前台五大基本资料信息和显示详细图片的第一张
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryCompanyBasePicture(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryCompanyBasePicture(conn, id);
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
	 * 查询用户前台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryCompanySelectPicture(long id)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryCompanySelectPicture(conn, id);
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
	 * 用户注册
	 * 
	 * @param email
	 * @param username
	 * @param password
	 * @param refferee
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws DataException
	 */
//	public Long userRegister(String email, String userName, String password,
//			String refferee, Map<String, Object> userMap, int typeLen,
//			String mobilePhone) throws Exception {
//		Connection conn = MySQL.getConnection();
//		String dealpwd = null; // 交易密码
//		Integer rating = 0;// 网站积分
//		Integer creditrating = 0;// 信用积分
//		String lastIP = "";
//		String lastDate = null;// 最后登录时间
//		Integer vipstatus = 1;// VIP会员状态 1是非vip 2是vip
//		String vipcreatetime = null;// VIP创建时间
//		double creditlimit = 0;// 信用额度 如果是vip 那么初始creditlimit = 3000；
//		Integer authstep = 1;// 认证步骤(默认是1 个人详细信息 2 工作认证 3上传
//		String headImg = "";// 用户头型
//		// 系统给予默认头型
//		Integer enable = 2; // 是否禁用 1、启用 2、禁用
//		// 测试--跳过验证
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		// 得到信息额度类型
//		Map<String, String> map = shoveBorrowAmountTypeDao
//				.queryBorrowAmountByNid(conn, "credit");
//		double creditLimit = Convert.strToDouble(map.get("init_credit"), 0);
//		if (IConstants.ISDEMO.equals("1")) {
//			authstep = 1;
//			enable = 1;
//			vipstatus = 2;
//			vipcreatetime = df.format(new Date());
//			creditlimit = creditLimit;
//		}
//		Long servicePersonId = null;
//		long userId = -1L;
//
//		try {
//
//			userId = userDao.addUser(conn, email, userName, password, refferee,
//					lastDate, lastIP, dealpwd, mobilePhone, rating,
//					creditrating, vipstatus, vipcreatetime, authstep, headImg,
//					enable, servicePersonId, creditLimit);
//			if (userId <= 0) {
//				return -1L;
//			}
//			// 初始化验证资料
//			for (long i = 1; i <= typeLen; i++) {
//				userDao.addMaterialsauth1(conn, userId, i);
//			}
//			if (userMap != null) {
//				relationDao.addRelation(conn, userId, (Long) userMap
//						.get("parentId"), (Integer) userMap.get("level"), 1);
//			}
//			conn.commit();
//		} catch (Exception e) {
//			log.error(e);
//			e.printStackTrace();
//			conn.rollback();
//
//			throw e;
//		} finally {
//			conn.close();
//		}
//
//		return userId;
//	}

//	public static void main(String[] args) {
//		System.out.println(new Date());
//	}
	
	/**
	 * 
	 * 
	 * @param user 用户
	 * @param king 注册种类与类型 1-邮件注册个人用 2-邮件注册企业用户 3-电话注册个人用户 4-电话注册企业用户
	 * @return 成功-用户id  失败-错误代码
	 * @throws Exception
	 */
	public Long userRegister(Users user,int king) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		//初始化用户默认数据
		user.setVipStatus(1);
		user.setAuthStep(1);
		user.setCreateTime(new Date());
		user.setCashStatus(1);
		user.setIsFirstVip(1);
		//双重判断是邮件注册还是手机注册,并把没有注册的值设为null
		if(user.getEmail().length()>0){
			if(king==1){
				user.setUserType(0);
				user.setMobilePhone(null);
				user.setOrgName(null);
			}else if(king==2){
				user.setUserType(1);
				user.setMobilePhone(null);
			}else{
				return -1L;
			}
		}else{
			if(king==3){
				user.setUserType(0);
				user.setEmail(null);
				user.setOrgName(null);
			}else if(king==4){
				user.setUserType(1);
				user.setEmail(null);
			}else{
				return -1L;
			}
		}
		
		Connection conn = MySQL.getConnection();//建立数据库连接
		
		long userId = -1L; //dao返回的用户id

		try {

			userId = userDao.addUser(conn, user);//调用dao添加用户函数
			if (userId <= 0) {
				return -1L;
			}else{
				
			}
			conn.commit();//提交事务
		} catch (Exception e) {
			log.error(e); //记录错误日志
			e.printStackTrace();
			conn.rollback();//回滚事务
			throw e;
		} finally {
			conn.close();//关闭数据库连接
		}

		return userId;
	}
	
	
	
	
	/**
	 * 手机用户注册
	 * 
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param userMap
	 * @param typeLen
	 * @param mobilePhone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long userAppRegister(String email, String userName, String password,
			String refferee, Map<String, Object> userMap, int typeLen,
			String mobilePhone) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		String dealpwd = null; // 交易密码
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
		// 系统给予默认头型
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

		try {
			userId = userDao.addUser(conn, email, userName, password, refferee,
					lastDate, lastIP, dealpwd, mobilePhone, rating,
					creditrating, vipstatus, vipcreatetime, authstep, headImg,
					enable, servicePersonId, creditLimit,null,null);
			if (userId <= 0) {
				return -1L;
			}
			personDao.addPerson(conn, null, mobilePhone, null, null,
					null, null, null, null, null, null, null, null, null, null,
					null, null, null, null, null, userId, null, null, null,
					null,null);
			myHomeInfoSettingDao.addBindingMobile(conn,
					mobilePhone, userId, IConstants.PHONE_BINDING_CHECK,
					"手机注册绑定手机", IConstants.INSERT_BASE_TYPE, null);
			// 初始化验证资料
			for (long i = 1; i <= typeLen; i++) {
				userDao.addMaterialsauth1(conn, userId, i);
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
	 * 投资人填写个人信息
	 * 
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param registedPlaceCity
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public long updateUserBaseTT(String realName, String cellPhone, String sex,
			String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild,
			String hasHourse, String hasHousrseLoan, String hasCar,
			String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address,
			String telephone, String personalHead, Long userId, String idNo)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userDao.updateUserBaseWWc(conn, realName, cellPhone,
					sex, birthday, highestEdu, eduStartDay, school,
					maritalStatus, hasChild, hasHourse, hasHousrseLoan, hasCar,
					hasCarLoan, nativePlacePro, nativePlaceCity,
					registedPlacePro, registedPlaceCity, address, telephone,
					personalHead, userId, idNo);
			// add by houli
			// add by houli 如果个人信息填写成功，将填写的手机号码同步到T_PHONE_BINDING_INFO表中
			Map<String, String> p_map = myHomeInfoSettingDao
					.queryBindingInfoByUserId(conn, userId, -1, -1);
			Long result1 = -1L;
			if (p_map == null || p_map.size() <= 0) {// 如果没有记录则插入手机绑定数据，否则进行更新
				result1 = myHomeInfoSettingDao.addBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_CHECK,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			} else {
				result1 = myHomeInfoSettingDao.updateBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_CHECK,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			}
			if (result1 <= 0) {
				conn.rollback();
				return -1L;
			}
			// end
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 存储过程添加或更新用户基本资料
	 * 
	 * @param realName
	 * @param cellPhone
	 * @param sex
	 * @param birthday
	 * @param highestEdu
	 * @param eduStartDay
	 * @param school
	 * @param maritalStatus
	 * @param hasChild
	 * @param hasHourse
	 * @param hasHousrseLoan
	 * @param hasCar
	 * @param hasCarLoan
	 * @param nativePlacePro
	 * @param nativePlaceCity
	 * @param registedPlacePro
	 * @param registedPlaceCity
	 * @param address
	 * @param telephone
	 * @param personalHead
	 * @param userId
	 * @param idNo
	 * @param num
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> updateUserBaseData1(String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo, String num)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		conn.setAutoCommit(false);// 后面添加这么一句即可,设置无法自动提交,手动提交
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		Map<String, String> user = new HashMap<String, String>();
		user = userDao.queryUserById(conn, userId);
		String userName = Convert.strToStr(user.get("username"), "");
		String lastip = Convert.strToStr(user.get("lastIP"), "");
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_userInfo_update(conn, ds, outParameterValues,
					realName, cellPhone, sex, birthday, highestEdu,
					eduStartDay, school, maritalStatus, hasChild, hasHourse,
					hasHousrseLoan, hasCar, hasCarLoan, nativePlacePro,
					nativePlaceCity, registedPlacePro, registedPlaceCity,
					address, telephone, personalHead, userId, idNo, userName,
					lastip, num, -1, "");
			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");
			if (ret < 0) {
				conn.rollback();
				return map;
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
	 * 更新用户基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseData(String realName, String cellPhone,
			String sex, String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild,
			String hasHourse, String hasHousrseLoan, String hasCar,
			String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address,
			String telephone, String personalHead, Long userId, String idNo)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> user = new HashMap<String, String>();
		long personId = -1L;
		try {
			user = userDao.queryUserById(conn, userId);
			personId = userDao.updateUserBaseData(conn, realName, cellPhone,
					sex, birthday, highestEdu, eduStartDay, school,
					maritalStatus, hasChild, hasHourse, hasHousrseLoan, hasCar,
					hasCarLoan, nativePlacePro, nativePlaceCity,
					registedPlacePro, registedPlaceCity, address, telephone,
					personalHead, userId, idNo, Convert.strToStr(user
							.get("username"), ""), Convert.strToStr(user
							.get("lastIP"), ""));
			if (personId <= 0) {
				conn.rollback();
				return -1L;
			}

			// add by houli
			// add by houli 如果个人信息填写成功，将填写的手机号码同步到T_PHONE_BINDING_INFO表中
			Map<String, String> p_map = myHomeInfoSettingDao
					.queryBindingInfoByUserId(conn, userId, -1, -1);
			Long result1 = -1L;
			if (p_map == null || p_map.size() <= 0) {// 如果没有记录则插入手机绑定数据，否则进行更新
				result1 = myHomeInfoSettingDao.addBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_CHECK,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE, null);
			} else {
				result1 = myHomeInfoSettingDao.updateBindingMobile(conn,
						cellPhone, userId, IConstants.PHONE_BINDING_CHECK,
						"个人信息资料填写申请手机绑定", IConstants.INSERT_BASE_TYPE,null);
			}
			if (result1 <= 0) {
				conn.rollback();
				return -1L;
			}
			// end
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;

	}
	
	/**
	 * 更新用户企业信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUserCompanyData(Long orgId, String organizationName,
			String address, Integer industory, Integer companyType, Integer companyScale,
			String foundDate, String legalPerson, String regNum,
			String organizationCode, String registeredCapital, String bankName,
			String accountName, String publicBankaccount, String website,
			String phone, String email,
			String linkmanName,  String linkmanMobile,
			String companyDescription, String headJpg)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		
		long personId = -1L;
		try {
			
			personId = userDao.addUserCompanyData(conn, orgId, organizationName, address, industory,
					companyType, companyScale, foundDate, legalPerson, regNum, organizationCode,
					registeredCapital, bankName, accountName, publicBankaccount, website, phone,
					email, linkmanName, linkmanMobile, companyDescription, headJpg,"");
			if (personId <= 0) {
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

		return personId;

	}
	/**
	 * 更新用户企业信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUserCompanyData2(Map map)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Long orgId = (Long) map.get("orgId");
		String organizationName = (String) map.get("organizationName");
		String address = (String) map.get("address");
		Integer industory = (Integer) map.get("industory");
		Integer companyType = (Integer) map.get("companyType"); 
		Integer companyScale = (Integer) map.get("companyScale");
		String foundDate = (String) map.get("foundDate");
		String legalPerson = (String) map.get("legalPerson"); 
		String regNum = (String) map.get("regNum");
		String organizationCode = (String) map.get("organizationCode"); 
		String registeredCapital = (String) map.get("registeredCapital");
		String bankName = (String) map.get("bankName");
		String accountName = (String) map.get("accountName");
		String publicBankaccount = (String) map.get("publicBankaccount");
		String website = (String) map.get("website");
		String phone = (String) map.get("phone");
		String email = (String) map.get("email");
		String linkmanName = (String) map.get("linkmanName"); 
		String linkmanMobile = (String) map.get("linkmanMobile");
		String companyDescription = (String) map.get("companyDescription");
		String headJpg = (String) map.get("headJpg");
		String source = (String) map.get("source");
		
		long personId = -1L;
		try {
			
			personId = userDao.addUserCompanyData(conn, orgId, organizationName, address, industory,
					companyType, companyScale, foundDate, legalPerson, regNum, organizationCode,
					registeredCapital, bankName, accountName, publicBankaccount, website, phone,
					email, linkmanName, linkmanMobile, companyDescription, headJpg,source);
			if (personId <= 0) {
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

		return personId;

	}

	/**
	 * 审核用户基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseDataCheck(long userId, int auditStatus,
			Long adminId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		StringBuffer msg = new StringBuffer();
		long personId = -1L;
		try {
			personId = userDao.updateUserBaseDataCheck(conn, userId,
					auditStatus);// 更新用户的工作信息认证审核状态

			if (personId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				int phoneStatus = 2;// 默认审核中
				if (auditStatus == 2) {// 失败
					phoneStatus = 4;// bangding 失败
				} else if (auditStatus == 3) {
					phoneStatus = 1;// 审核成功
				}
				beVipDao.updatePhoneBanding(conn, userId, phoneStatus);
				// 更新用户绑定手机状态
				personId = beVipDao.updatePhoneBanding(conn, userId,
						phoneStatus);
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				} else {

					// 发站内信
					String m = "";
					if (auditStatus == 2) {
						m = "不通过";
					} else if (auditStatus == 3) {
						m = "通过";
					}
					msg.append("您的基本信息审核状况:");
					msg.append(m);
					// 发站内信
					personId = sendmsgService.sendCheckMail(userId,
							" 基本信息审核通知", msg.toString(), 2, adminId);// 2管理员信件
					if (personId <= 0) {
						conn.rollback();
						return -1L;
					}
				}
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

		return personId;
	}
	
	/**
	 * 审核企业用户基础资料
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseDataCheck2(long userId, int auditStatus,
			Long adminId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		StringBuffer msg = new StringBuffer();
		long personId = -1L;
		try {
			personId = userDao.updateUserBaseDataCheck2(conn, userId,
					auditStatus);// 更新用户的工作信息认证审核状态

			if (personId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				int phoneStatus = 2;// 默认审核中
				if (auditStatus == 2) {// 失败
					phoneStatus = 4;// bangding 失败
				} else if (auditStatus == 3) {
					phoneStatus = 1;// 审核成功
				}
				/*
				 * 企业用户手机绑定需求待确认
				 * 
				beVipDao.updatePhoneBanding(conn, userId, phoneStatus);
				// 更新用户绑定手机状态
				personId = beVipDao.updatePhoneBanding(conn, userId,
						phoneStatus);
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				} else {     */
					// 发站内信
					String m = "";
					if (auditStatus == 2) {
						m = "不通过";
					} else if (auditStatus == 3) {
						m = "通过";
					}
					msg.append("您的基本信息审核状况:");
					msg.append(m);
					// 发站内信
					personId = sendmsgService.sendCheckMail(userId,
							" 基本信息审核通知", msg.toString(), 2, adminId);// 2管理员信件
					if (personId <= 0) {
						conn.rollback();
						return -1L;
					}
				}
//			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return personId;
	}


	/**
	 * 该用户上传资料的类型的审核状态
	 * 
	 * @param id
	 * @param materAuthTypeId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserPicturStatus(Long id, Long materAuthTypeId)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		try {
			personId = userDao
					.updateUserPicturStatus(conn, id, materAuthTypeId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 增加用户的图片
	 * 
	 * @param auditStatus
	 * @param uploadingTime
	 * @param imagePath
	 * @param materialsauthid
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUserImage(Integer auditStatus, String uploadingTime,
			List<Long> lists, List<String> imgListsy, List<String> imgListsn,
			Long materialsauthid, Long id, Long materAuthTypeId, Long tmid,
			int addCount) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long personId = -1L;
		Long userId = -1L;
		Long userIdauthd = -1L;
		try {
			// 将用户选择可见的设置为可见
			// if(lists.size()>0&&lists!=null){
			personId = userDao.updatematerialImagedetalvisiable(conn,
					materialsauthid);// 将重置图片的可见性 设置为不可见
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			// 插于图片为可见的
			for (String vimg : imgListsy) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 1);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}

			}
			// 插于图片为不可见的
			for (String vimg : imgListsn) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 2);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}// --------modify by houli 如果未新添加上传图片，那么不修改总审核状态
			if (addCount > 0) {
				userId = userDao.updateUserPicturStatus(conn, id,
						materAuthTypeId);// 更新用户总证件状态
				if (userId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			userId = userDao.updateUserPicturStatus(conn, id, materAuthTypeId);// 更新用户总证件状态
			if (userId <= 0) {
				conn.rollback();
				return -1L;
			}
			// 更新user authod表中的状态
			userIdauthd = userDao.updateUserauthod(conn, id);// 当5大基本资料上传完成后更新用户的认证步骤
			if (userIdauthd <= 0) {
				conn.rollback();
				return -1L;
			}
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_materialimagedetal",
					Convert.strToStr(userMap.get("username"), ""),
					IConstants.UPDATE, Convert.strToStr(userMap.get("lastIP"),
							""), 0, "上传图片", 1);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 重写addUserImage方法，添加用户类型参数
	 * @param auditStatus
	 * @param uploadingTime
	 * @param lists
	 * @param imgListsy
	 * @param imgListsn
	 * @param materialsauthid
	 * @param id
	 * @param materAuthTypeId
	 * @param tmid
	 * @param addCount
	 * @return
	 * @throws Exception
	 */
	public Long addUserImage(Integer auditStatus, String uploadingTime,
			List<Long> lists, List<String> imgListsy, List<String> imgListsn,
			Long materialsauthid, Long id, Long materAuthTypeId, Long tmid,
			int addCount,int userType) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long personId = -1L;
		Long userId = -1L;
		Long userIdauthd = -1L;
		try {
			// 将用户选择可见的设置为可见
			// if(lists.size()>0&&lists!=null){
			personId = userDao.updatematerialImagedetalvisiable(conn,
					materialsauthid);// 将重置图片的可见性 设置为不可见
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			// 插于图片为可见的
			for (String vimg : imgListsy) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 1);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}

			}
			// 插于图片为不可见的
			for (String vimg : imgListsn) {// 遍历集合
				personId = userDao.addUserImage(conn, auditStatus,
						uploadingTime, vimg, materialsauthid, 2);// t_materialImagedetal添加图片
				// 1 为可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
			}// --------modify by houli 如果未新添加上传图片，那么不修改总审核状态
			if (addCount > 0) {
				userId = userDao.updateUserPicturStatus(conn, id,
						materAuthTypeId);// 更新用户总证件状态
				if (userId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			userId = userDao.updateUserPicturStatus(conn, id, materAuthTypeId);// 更新用户总证件状态
			if (userId <= 0) {
				conn.rollback();
				return -1L;
			}
			
			
			// 更新user authod表中的状态
			userIdauthd = userDao.updateUserauthod(conn, id,userType);// 当5大基本资料上传完成后更新用户的认证步骤
			if (userIdauthd <= 0) {
				conn.rollback();
				return -1L;
			}
			userMap = userDao.queryUserById(conn, id);
			operationLogDao.addOperationLog(conn, "t_materialimagedetal",
					Convert.strToStr(userMap.get("username"), ""),
					IConstants.UPDATE, Convert.strToStr(userMap.get("lastIP"),
							""), 0, "上传图片", 1);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}
	
	
	/**
	 * 更新用户上传图片的可见性
	 * 
	 * @param tmdid
	 *            //主图片类型下的图片明细id
	 * @param tmid
	 *            //主图片类型id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updatevisiable(Long tmid, List<Long> lists) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			long personId = -1L;
			Long resetvisable = -1L;

			resetvisable = userDao.updatematerialImagedetalvisiable(conn, tmid);// 将重置图片的可见性
			// 重置为不可见
			if (resetvisable <= 0) {
				conn.rollback();
				return -1L;
			}
			for (Long list : lists) {
				personId = userDao.updatevisiable(conn, list);// 根据传来的id集合重新设置哪个可见
				if (personId <= 0) {
					conn.rollback();
					return -1L;
				}
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

		return -1L;
	}

	/**
	 * 添加用户的基础资料
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Long userBaseData(String realName, String cellPhone, String sex,
			String birthday, String highestEdu, String eduStartDay,
			String school, String maritalStatus, String hasChild,
			String hasHourse, String hasHousrseLoan, String hasCar,
			String hasCarLoan, Long nativePlacePro, Long nativePlaceCity,
			Long registedPlacePro, Long registedPlaceCity, String address,
			String telephone, String personalHead, Long userId, String idNo)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long personId = -1L;
		long uId = -1L;
		try {
			personId = userDao.addUserBaseData(conn, realName, cellPhone, sex,
					birthday, highestEdu, eduStartDay, school, maritalStatus,
					hasChild, hasHourse, hasHousrseLoan, hasCar, hasCarLoan,
					nativePlacePro, nativePlaceCity, registedPlacePro,
					registedPlaceCity, address, telephone, personalHead,
					userId, idNo);

			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return personId;
	}

	/**
	 * 添加用户工作认证信息
	 * 
	 * @throws SQLException
	 */
	public Long addUserWorkData(String orgName, String occStatus, Long workPro,
			Long workCity, String companyType, String companyLine,
			String companyScale, String job, String monthlyIncome,
			String workYear, String companyTel, String workEmail,
			String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long workDataId = -1L;
		try {
			workDataId = userDao.addUserWorkData(conn, orgName, occStatus,
					workPro, workCity, companyType, companyLine, companyScale,
					job, monthlyIncome, workYear, companyTel, workEmail,
					companyAddress, directedName, directedRelation,
					directedTel, otherName, otherRelation, otherTel, moredName,
					moredRelation, moredTel, userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return workDataId;
	}

	/**
	 * 修改用户工作认证信息
	 * 
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> updateUserWorkData1(String orgName,
			String occStatus, Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId,
			Integer vipStatus, Integer newutostept) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
//		long workDataId = -1L;
		Map<String, String> map = new HashMap<String, String>();
		Long ret = -1l;
		try {
			userMap = userDao.queryUserById(conn, userId);
			String lastip = Convert.strToStr(userMap.get("lastIP"), "");

			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_userWorkInfo_update(conn, ds, outParameterValues,
					orgName, occStatus, workPro, workCity, companyType,
					companyLine, companyScale, job, monthlyIncome, workYear,
					companyTel, workEmail, companyAddress, directedName,
					directedRelation, directedTel, otherName, otherRelation,
					otherTel, moredName, moredRelation, moredTel, userId,
					vipStatus, newutostept, lastip, -1, "");

			ret = Convert.strToLong(outParameterValues.get(0) + "", -1);
			map.put("ret", ret + "");
			map.put("ret_desc", outParameterValues.get(1) + "");

			if (ret <= 0) {
				conn.rollback();
				return map;
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

	public Long updateUserWorkData(String orgName, String occStatus,
			Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId,
			Integer vipStatus, Integer newutostept) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		long workDataId = -1L;
		try {
			workDataId = userDao.updateUserWorkData(conn, orgName, occStatus,
					workPro, workCity, companyType, companyLine, companyScale,
					job, monthlyIncome, workYear, companyTel, workEmail,
					companyAddress, directedName, directedRelation,
					directedTel, otherName, otherRelation, otherTel, moredName,
					moredRelation, moredTel, userId);

			userMap = userDao.queryUserById(conn, userId);
			workDataId = operationLogDao.addOperationLog(conn, "t_workauth",
					Convert.strToStr(userMap.get("username"), ""),
					IConstants.INSERT, Convert.strToStr(userMap.get("lastIP"),
							""), 0.00, "填写工作认证信息", 1);
			if (workDataId <= 0) {
				conn.rollback();
				return -1L;
			}

			// 跟新用户的认证步骤
			if (newutostept == 2) {
				workDataId = beVipDao.updateUserAustep(conn, userId, 3);// 3为填写完了工作信息的认证步骤
				if (workDataId <= 0) {
					conn.rollback();
					return -1L;
				}
			}
			if (vipStatus != 1) {// 如果此时用户的vip状态为会员 那么要更新user的认证步骤
				if (newutostept <= 3) {
					workDataId = beVipDao.updateUserAustep(conn, userId, 4);// 4为填写完了vip的认证步骤
					if (workDataId <= 0) {
						conn.rollback();
						return -1L;
					}
				}
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

		return workDataId;
	}

	/**
	 * 判断重复登录
	 * 
	 * @param email
	 * @param userName
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long isExistEmailORUserName(String email, String userName)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUserEmaiORUseName(conn, email, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}
	
	/**
	 * 重载判断重复邮箱，用户名，企业全称，手机
	 * 
	 * @param email
	 * @param userName
	 * @param orgName
	 * @param phone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long isExistEmailORUserName(String email, String userName,String orgName,String phone)
	throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUserEmaiORUseName(conn, email, userName,orgName,phone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
 			conn.close();
		}
		return list.size() <= 0 ? 0L : 1L;
		}
	
	/**
	 * 验证t_person表中电话号码唯一性
	 * @param email
	 * @param userName
	 * @param orgName
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Long isExistPersonByPhone(String phone) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isExistPersonByPhone(conn, phone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
 			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
		}
	// =====================================
	/**
	 * 用户登录时候验证邮箱和用户名是否激活
	 */
	public Long isUEjihuo(String email, String userName) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUEjihuo(conn, email, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	/**
	 * 用户登录时候验证邮箱和用户名是否激活
	 */
	public Long isUEjihuo_(String email, String userName) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isUEjihuo_(conn, email, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	// =========================================
	private SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	private DataSourceTransactionManager transactionManager;
	
	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(
			DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * 用户登录处理
	 * 
	 * 
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws SQLException
	 * @throws DataException
	 */
	public User userLogin1(String userName, String password, String lastIP,
			String lastTime) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
			Connection conn = MySQL.getConnection();
			password = StringEscapeUtils.escapeSql(password.trim());
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}

			return commonlogin(userName, password, lastIP, lastTime, conn);}
	/**
	 * 用户登录处理
	 * 
	 * 
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws SQLException
	 * @throws DataException
	 */
	public User userLogin1(String userName, String password, String lastIP,
			String lastTime,String dm) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
			Connection conn = MySQL.getConnection();
			password = StringEscapeUtils.escapeSql(password.trim());
			return commonlogin(userName, password, lastIP, lastTime, conn);
	}

	private User commonlogin(String userName, String password, String lastIP,
			String lastTime, Connection conn) throws SQLException, Exception {
		User user = null;
		Long userid = -1L;
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();

			Map<String, String> umap = new HashMap<String, String>();
			umap = userDao.queryUserByUserName(conn, userName);
			if (null == umap) {
				Long puserid = queryUserIdByPhone(userName);//手机号登录，还要去t_person或binding表查询
				if(puserid > 0){
					umap=userDao.queryUserById(conn, puserid);
				}else{
					return null;
				}
			}
			int id = Convert.strToInt(umap.get("id"), -1);
			String lastLoginTime = Convert.strToStr(umap.get("lastDate"), null);
			SimpleDateFormat simple = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			if (null != lastLoginTime) {
				Date loginTime = simple.parse(lastLoginTime);
				if ((new Date().getTime() - loginTime.getTime()) > 3 * 60 * 60 * 1000) {
					// 取消用户限制登录将isLoginLimit设置为1和loginErrorCount设置为0
					userDao.resetUserState(conn, 0, 1, id);
				}
			}
			umap = userDao.queryUserById(conn, id);
			int isLoginLimit = Convert.strToInt(umap.get("isLoginLimit"), 1);
			int loginErrorCount = Convert.strToInt(umap.get("loginErrorCount"),
					0);
			String umappassword = Convert.strToStr(umap.get("password"),
					null);
			if(!password.equals(umappassword)){
				if (loginErrorCount == 2) {
					userDao.resetUserState(conn, 3, 1, id);
					conn.commit();
					user = new User();
					user.setLoginErrorCount(3);
					user.setPassword(umappassword);
					return user;
				}
				
				if (loginErrorCount == 3) {
					userDao.resetUserState(conn, 4, 1, id);
					conn.commit();
					user = new User();
					user.setLoginErrorCount(4);
					user.setPassword(umappassword);
					return user;
				}
				
				if (loginErrorCount == 4) {
					// 设置用户限制登录，设置loginErrorCount为0
					userDao.resetUserState(conn, 0, 2, id);
					conn.commit();
					user = new User();
					user.setIsLoginLimit(2);
					return user;
				}
			}
			//用户被限制3小时登陆
			if (isLoginLimit == 2) {
				user = new User();
				user.setIsLoginLimit(2);
				return user;
			}
			
			
			Procedures.p_user_login(conn, ds, outParameterValues, userName,
					password, lastIP, -1, "");
			userid = Convert.strToLong(outParameterValues.get(0) + "", -1);
			if (userid <= 0) {
				if (userid == -4) {
					int count = loginErrorCount + 1;
					// 更新用户错误登录次数和登陆时间
					userDao.updateUserState(conn, count, lastTime, id);
					conn.commit();
				}
				if (userid == -5) {
					user = new User();
					user.setEnable(2);
					return user;
				} 				
				return null;
			}
			userDao.resetUserState(conn, 0, 1, userid);
			Map<String, String> usermap = new HashMap<String, String>();
			usermap = userDao.queryUserById(conn, userid);

			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();
			DataSet dataSet = sessionuser.open(conn, "", " id=" + userid, "",
					-1, -1);
			Map<String, String> sessionmap = new HashMap<String, String>();
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);
			if (sessionmap != null && sessionmap.size() > 0) {
				user = new User();
				user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"),
						-1));
				user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
				user.setPassword(Convert.strToStr(sessionmap.get("password"),
						null));
				user.setDealpwd(Convert.strToStr(sessionmap.get("dealpwd"),
						null));
				user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
				user.setRealName(Convert.strToStr(sessionmap.get("realName"),
						null));
				user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"),
						null));
				user.setUserName(Convert.strToStr(sessionmap.get("username"),
						null));
				user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"),
						-1));
				user.setHeadImage(Convert
						.strToStr(usermap.get("headImg"), null));
				user.setEnable(Convert.strToInt(usermap.get("enable"), -1));
				user.setPersonalHead(Convert.strToStr(sessionmap
						.get("personalHead"), null));
				user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));
				user.setCreditLimit(Convert.strToStr(sessionmap
						.get("usableCreditLimit"), null));
				
				user.setUserType(Convert.strToInt(sessionmap.get("userType"), -1));
				user.setOrgName(Convert.strToStr(sessionmap.get("orgName"), null));
				user.setOrgheadimg(Convert.strToStr(sessionmap.get("orgheadimg"), null));

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
		return user;
	}
	/**
	 * 用户登录处理
	 * 
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名称
	 * @param password
	 *            用户密码
	 * @param lastIP
	 *            最后登录ip
	 * @param lastTime
	 *            最后登录时间
	 * @param loginType
	 *            登录类型，1用户名或邮箱登录，
	 * @return User
	 * @throws SQLException
	 * @throws DataException
	 */
	public User userLogin(String userName, String password, String lastIP,
			String lastTime) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		User user = null;
		try {
			password = StringEscapeUtils.escapeSql(password.trim());
			if ("1".equals(IConstants.ENABLED_PASS)) {
				password = com.shove.security.Encrypt.MD5(password.trim());
			} else {
				password = com.shove.security.Encrypt.MD5(password.trim()
						+ IConstants.PASS_KEY);
			}
			Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
			DataSet ds = t_user
					.open(
							conn,
							"id,username,headImg,enable,vipStatus,email,authStep,lastIP ",
							"(email ='"
									+ StringEscapeUtils.escapeSql(userName
											.trim())
									+ "' OR username='"
									+ StringEscapeUtils.escapeSql(userName
											.trim())
									+ "' OR mobilePhone='"
									+ StringEscapeUtils.escapeSql(userName
											.trim()) + "') AND password = '"
									+ password + "' AND enable != 2", "", -1,
							-1);
			int returnId = ds.tables.get(0).rows.getCount();
			if (returnId <= 0) {
				return null;
			} else {
				user = new User();
				DataRow dr = ds.tables.get(0).rows.get(0);

				// ======
				Map<String, String> sessionmap = new HashMap<String, String>();

				Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

				DataSet dataSet = sessionuser.open(conn, "", " id="
						+ (Long) (dr.get("id") == null ? -1l : dr.get("id")),
						"", -1, -1);
				sessionmap = BeanMapUtils.dataSetToMap(dataSet);

				if (sessionmap != null && sessionmap.size() > 0) {
					user = new User();
					user.setAuthStep(Convert.strToInt(sessionmap
							.get("authStep"), -1));
					user.setEmail(Convert.strToStr(sessionmap.get("email"),
							null));
					user.setPassword(Convert.strToStr(sessionmap
							.get("password"), null));
					user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
					user.setRealName(Convert.strToStr(sessionmap
							.get("realName"), null));
					user.setKefuname(Convert.strToStr(sessionmap
							.get("kefuname"), null));
					user.setUserName(Convert.strToStr(sessionmap
							.get("username"), null));
					user.setVipStatus(Convert.strToInt(sessionmap
							.get("vipStatus"), -1));
					user.setHeadImage((String) (dr.get("headImg") == null ? ""
							: dr.get("headImg")));
					user.setEnable((Integer) (dr.get("enable") == null ? -1
							: dr.get("enable")));
					user.setPersonalHead(Convert.strToStr(sessionmap
							.get("personalHead"), null));
					user.setKefuid(Convert
							.strToInt(sessionmap.get("tukid"), -1));
					user.setCreditLimit(Convert.strToStr(sessionmap
							.get("usableCreditLimit"), null));
				}
				if (StringUtils.isNotBlank(lastIP)) {
					t_user.lastDate.setValue(lastTime);
					t_user.lastIP.setValue(lastIP);
					t_user.update(conn, " id=" + user.getId());
				}

			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return user;
	}

	/**
	 * 虚拟用户登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public User userVirtualLogin(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		User user = null;

		try {
			Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
			DataSet ds = t_user
					.open(
							conn,
							"id  ,username ,headImg  ,enable  ,vipStatus  ,email ,authStep  ",
							" id=" + id, "", -1, -1);
			int returnId = ds.tables.get(0).rows.getCount();
			if (returnId <= 0) {
				return null;
			} else {
				user = new User();
				DataRow dr = ds.tables.get(0).rows.get(0);

				// ======
				Map<String, String> sessionmap = new HashMap<String, String>();

				Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

				DataSet dataSet = sessionuser.open(conn, "", " id ="
						+ (dr.get("id") == null ? -1l : dr.get("id")), "", -1,
						-1);
				sessionmap = BeanMapUtils.dataSetToMap(dataSet);

				if (sessionmap != null && sessionmap.size() > 0) {
					user = new User();
					user.setAuthStep(Convert.strToInt(sessionmap
							.get("authStep"), -1));
					user.setEmail(Convert.strToStr(sessionmap.get("email"),
							null));
					user.setPassword(Convert.strToStr(sessionmap
							.get("password"), null));
					user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
					user.setRealName(Convert.strToStr(sessionmap
							.get("realName"), null));
					user.setKefuname(Convert.strToStr(sessionmap
							.get("kefuname"), null));
					user.setUserName(Convert.strToStr(sessionmap
							.get("username"), null));
					user.setVipStatus(Convert.strToInt(sessionmap
							.get("vipStatus"), -1));
					user.setHeadImage((String) (dr.get("headImg") == null ? ""
							: dr.get("headImg")));
					user.setEnable(Convert.strToInt(dr.get("enable") + "", -1));
					user.setPersonalHead(Convert.strToStr(sessionmap
							.get("personalHead"), null));
					user.setKefuid(Convert
							.strToInt(sessionmap.get("tukid"), -1));
					user.setCreditLimit(Convert.strToStr(sessionmap
							.get("usableCreditLimit"), null));
				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return user;
	}

	/**
	 * @MethodName: loginCountReFresh
	 * @Param: UserService
	 * @Author: gang.lv
	 * @Date: 2013-4-4 上午10:34:45
	 * @Return:
	 * @Descb: 刷新登录计数
	 * @Throws:
	 */
	public void loginCountReFresh(long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (userId > -1) {
			Connection conn = MySQL.getConnection();
			try {
				MySQL.executeNonQuery(conn,
						" update t_user set loginCount = loginCount + 1 where id="
								+ userId);
				conn.commit();
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				conn.rollback();

				throw e;
			} finally {
				conn.close();
			}
		}
	}

	// =================登陆中初始化LoginVerify
	public LoginVerify getLoginVerify(Long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		LoginVerify loginVerify = null;
		Map<String, String> spmap = new HashMap<String, String>();
		Map<String, String> vpmap = new HashMap<String, String>();
		try {
			Dao.Views.v_t_user_loginsession_user sessond = new Dao().new Views().new v_t_user_loginsession_user();
			Dao.Views.v_t_login_session_verify verify = new Dao().new Views().new v_t_login_session_verify();
			DataSet sdataSet = sessond.open(conn, "", " id=" + userId, "", -1,
					-1);
			spmap = BeanMapUtils.dataSetToMap(sdataSet);
			DataSet vdataSet = verify.open(conn, "", " id=" + userId, "", -1,
					-1);
			vpmap = BeanMapUtils.dataSetToMap(vdataSet);
			if (spmap != null && spmap.size() > 0 && vpmap != null
					&& vpmap.size() > 0) {
				loginVerify = new LoginVerify();
				loginVerify.setJbStatus(Convert.strToInt(spmap
						.get("tpauditStatus"), -1));
				loginVerify.setAllworkjbStatus(Convert.strToInt(spmap
						.get("twsum"), -1));
				loginVerify.setIdentyVerifyStatus(Convert.strToInt(vpmap
						.get("identyauditStatus"), -1));
				loginVerify.setWorkVerifyStatus(Convert.strToInt(vpmap
						.get("workauditStatus"), -1));
				loginVerify.setAddressVerifyStatus(Convert.strToInt(vpmap
						.get("addryauditStatus"), -1));
				loginVerify.setResponseVerifyStatus(Convert.strToInt(vpmap
						.get("responsauditStatus"), -1));
				loginVerify.setIncomeVerifyStatus(Convert.strToInt(vpmap
						.get("incomeauditStatus"), -1));
				loginVerify.setFangchanVerifyStatus(Convert.strToInt(vpmap
						.get("fcyauditStatus"), -1));
				loginVerify.setGcVerifyStatus(Convert.strToInt(vpmap
						.get("gcauditStatus"), -1));
				loginVerify.setJhVerifyStatus(Convert.strToInt(vpmap
						.get("jhauditStatus"), -1));
				loginVerify.setXlVerifyStatus(Convert.strToInt(vpmap
						.get("xlauditStatus"), -1));
				loginVerify.setJsVerifyStatus(Convert.strToInt(vpmap
						.get("jsauditStatus"), -1));
				loginVerify.setSjVerifyStatus(Convert.strToInt(vpmap
						.get("sjauditStatus"), -1));
				loginVerify.setWbVerifyStatus(Convert.strToInt(vpmap
						.get("wbauditStatus"), -1));
				loginVerify.setSpVerifyStatus(Convert.strToInt(vpmap
						.get("spauditStatus"), -1));
				loginVerify.setXcVerifyStatus(Convert.strToInt(vpmap
						.get("xcauditStatus"), -1));
				loginVerify.setDbVerifyStatus(Convert.strToInt(vpmap
						.get("dbauditStatus"), -1));
				loginVerify.setDyVerifyStatus(Convert.strToInt(vpmap
						.get("dyauditStatus"), -1));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return loginVerify;
	}

	public User jumpToWorkData(Long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		// 更新user表中的认证状态
		User user = null;
		Connection conn = MySQL.getConnection();
		Map<String, String> sessionmap = new HashMap<String, String>();
		try {
			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

			DataSet dataSet = sessionuser.open(conn, "", " id=" + userId, "",
					-1, -1);
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);

			if (sessionmap != null && sessionmap.size() > 0) {
				user = new User();
				user.setAuthStep(Convert.strToInt(sessionmap.get("authStep"),
						-1));
				user.setEmail(Convert.strToStr(sessionmap.get("email"), null));
				user.setPassword(Convert.strToStr(sessionmap.get("password"),
						null));
				user.setId(Convert.strToLong(sessionmap.get("id"), -1L));
				user.setRealName(Convert.strToStr(sessionmap.get("realName"),
						null));
				user.setKefuname(Convert.strToStr(sessionmap.get("kefuname"),
						null));
				user.setUserName(Convert.strToStr(sessionmap.get("username"),
						null));
				user.setVipStatus(Convert.strToInt(sessionmap.get("vipStatus"),
						-1));
				user.setKefuid(Convert.strToInt(sessionmap.get("tukid"), -1));

			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return user;

		// ===============================================================================

	}

	// 查询用户的最新状态
	public Map<String, String> querynewStatus(Long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> sessionmap = null;
		try {
			Dao.Views.v_t_user_loginsession_user sessionuser = new Dao().new Views().new v_t_user_loginsession_user();

			DataSet dataSet = sessionuser.open(conn, "", " id=" + userId, "",
					-1, -1);
			sessionmap = BeanMapUtils.dataSetToMap(dataSet);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}

		return sessionmap;

		// ===============================================================================
	}

	/**
	 * 更新申请vip时候的步骤和状态
	 * 
	 * @param userId
	 * @param authStep
	 *            认证步骤
	 * @param vipStatus
	 *            会员状态
	 * @return User实体
	 * @throws Exception
	 */
	public Long updataUserVipStatus(Long userId, int authStep, int vipStatus,
			int servicePersonId, String content, String vipFee, String username)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		StringBuffer msg = new StringBuffer();
		long uId = -1L;
		Connection conn = MySQL.getConnection();
		try {
			uId = userDao.updateUser(conn, userId, authStep, vipStatus,
					servicePersonId, content, vipFee);
			if (uId <= 0) {
				conn.rollback();
				return -1L;
			} else {
				// 发送站内信
				msg.append("尊敬的" + username + ",你申请vip成功");
				// 发站内信
				uId = sendmsgService.sendCheckMail(userId, " 申请vip审核通知", msg
						.toString(), 2, -1);// 2管理员信件 -1 后台管理员
				if (uId <= 0) {
					conn.rollback();
					return -1L;
				}
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
		return uId;
	}

	public Long frontVerificationEmial(Long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;

		user.enable.setValue(1);
		try {
			retut = user.update(conn, " id=" + userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}
		return retut;
	}

	/**
	 * 处理前台用户修改头像
	 * 
	 * @param userId
	 *            用户id
	 * @param headImg
	 *            图片地址
	 * @return Long
	 * @throws SQLException
	 */
	public Long frontUpdateUserHeadImg(Long userId, String headImg)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;
		try {
			user.headImg.setValue(headImg);
			retut = user.update(conn, " id=" + userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return retut;
	}

	/**
	 * 处理前台用户修改密码
	 * 
	 * @param userId
	 *            用户id
	 * @param password
	 *            新密码
	 * @return Long
	 * @throws SQLException
	 */
	public Long frontUpdatePassword(Long userId, String password)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Long retut = -1L;
		try {
			user.password.setValue(password);
			retut = user.update(conn, " id=" + userId);
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();

			throw e;
		} finally {
			conn.close();
		}

		return retut;
	}

	/**
	 * 修改用户邮箱验证状态
	 * 
	 * @Title: updateUserEmailStatus
	 * @param id
	 *            用户ID
	 * @param status
	 *            标志邮箱是否验证通过 (0:未通过1:通过)
	 * @throws SQLException
	 * @return Long
	 */
	public Long updateUserEmailStatus(Long id, Integer status) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long userId = -1;

		try {
			userId = userDao.updateUserEmailStatus(conn, id, status);
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
	 * 修改用户密码
	 * 
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserPassword(Long id, String password) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long userId = -1;

		try {
			userId = userDao.updateUserPassword(conn, id, password);
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
	 * 根据用户id查询用户信息
	 * 
	 * @param id
	 * @throws DataException
	 * @throws SQLException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserById(conn, id);
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
	 * 根据用户id查询用户信息
	 * 
	 * @param id
	 * @throws DataException
	 * @throws SQLException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(Connection conn,long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} 
		return map;
	}
	
	
	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param id
	 * @throws DataException
	 * @throws SQLException
	 * @return Map<String,String>
	 */
	public User queryUserDetailById(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = null;
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserById(conn, id);
			
			if (map != null && map.size() > 0) {
				user = new User();
				user.setAuthStep(Convert.strToInt(map.get("authStep"),
						-1));
				//user.setBalances(BigDecimal.valueOf(Long.valueOf(map.get("balances").toString())));
				user.setCreditLimit(Convert.strToStr(map.get("creditLimit"),
						null));
				user.setDealpwd(Convert.strToStr(map.get("dealpwd"),
						null));
				user.setEmail(Convert.strToStr(map.get("email"), null));
				user.setEnable(Convert.strToInt(map.get("enable"),
						-1));
				user.setEncodeP(Convert.strToStr(map.get("encodeP"), null));
				user.setHeadImage(Convert.strToStr(map.get("headImage"), null));
				user.setId(Convert.strToLong(map.get("id"), -1L));
				user.setImgHead(Convert.strToStr(map.get("imgHead"), null));
				user.setIsApplyPro(Convert.strToInt(map.get("isApplyPro"), -1));
				user.setIsLoginLimit(Convert.strToInt(map.get("isLoginLimit"), -1));
				user.setKefuid(Convert.strToInt(map.get("tukid"), -1));
				user.setKefuname(Convert.strToStr(map.get("kefuname"),
						null));
				user.setLastIP(Convert.strToStr(map.get("lastIP"),
						null));
				user.setLastTime(Convert.strToDate(map.get("lastTime"), null));
				user.setLoginErrorCount(Convert.strToInt(map.get("loginErrorCount"), -1));
				user.setOrgName(Convert.strToStr(map.get("orgName"),
						null));
				user.setPassword(Convert.strToStr(map.get("password"),
						null));
				user.setPersonalHead(Convert.strToStr(map.get("personalHead"),
						null));
				user.setRealName(Convert.strToStr(map.get("realName"),
						null));
				user.setServicePerson(Convert.strToStr(map.get("servicePerson"),
						null));
				user.setUserName(Convert.strToStr(map.get("username"),
						null));
				user.setUserType(Convert.strToInt(map.get("userType"), -1));
				user.setVipStatus(Convert.strToInt(map.get("vipStatus"),
						-1));
				user.setVirtual(Convert.strToInt(map.get("virtual"), -1));
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return user;
	}
	

	/**
	 * 查询用户的五大基本资料的计数
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryPicturStatuCount(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPicturStatuCount(conn, id);
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
	 * 查询前台上传图片的图片状态
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryUserPictureStatus(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserPictureStatus(conn, id);
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
	 * 查询用户前台五大基本资料信息和显示详细图片的第一张
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryBasePicture(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryBasePicture(conn, id);
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
	 * 查询用户前台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> querySelectPicture(long id)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.querySelectPicture(conn, id);
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
	 * 查询每一个证件的上传类型的图片数据显示
	 * 
	 * @param tmid
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryPerTyhpePicture(Long tmid)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.queryPerTyhpePicture(conn, tmid);
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
	 * 查询图片类型
	 * 
	 * @param userId
	 * @param tmid
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */

	public Map<String, String> queryPitcturTyep(Long userId, long tmid)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPitcturTyep(conn, tmid, userId);
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
	 * 查询图片id
	 * 
	 * @param userId
	 * @param tmid
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */

	public Map<String, String> queryPitcturId(Long userId, long tmid)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPitcturId(conn, tmid, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	//根据用户名查询用户明细
	public Map<String, String> queryIdByUser(String userName) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryIdByUser(conn, userName);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}

	public Map<String, String> queryPassword(String email) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPassword(conn, email);
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
	 * 查询客服列表
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> querykefylist() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		try {
			map = userDao.querykefylist(conn);
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
	 * 所有密码安全提问的内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryAllQuestionList() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.queryAllQuestionList(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list;
	}

	/**
	 * 用户基本信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPersonById(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPersonById(conn, id);
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
	 * 企业基本信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryOrgById(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryOrgById(conn, id);
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
	 * 用户基本信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryPersonById2(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryPersonById2(conn, id);
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
	 * llz
	 * 企业用户详细详细
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryOrgInfoById(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryOrgInfoById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	public int queryUserTypeById(long id) throws Exception{
		Connection conn = MySQL.getConnection();
		int userType=-1;
		try {
			
			userType = userDao.queryUserTypeById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		return userType;
	}
	
	/**
	 * 查询用户所有密保答案
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryAnswerByUserId(long userId)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryAnswerByUserId(conn, userId);
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
	 * 查询vip页面状态参数
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryVipParamList(long id) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryVipParamList(conn, id);
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
	 * add by houli 查找用户资金管理信息
	 * 
	 * @param userDao
	 */
	public void queryUserFundInfo(PageBean<Map<String, Object>> pageBean,
			String userName) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		userName = Utility.filteSqlInfusion(userName);

		Connection conn = MySQL.getConnection();
		// 手机变更状态为空
		String command = " ";
		if (userName != null) {
			command += " and username like '%"
					+ StringEscapeUtils.escapeSql(userName) + "%' ";
		}
		try {
			dataPage(conn, pageBean, "v_t_user_fund_lists", "*", "", command);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 查询黑名单用户
	 * 
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryBlacklistUser(PageBean<Map<String, Object>> pageBean,
			String userName) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		userName = Utility.filteSqlInfusion(userName);

		Connection conn = MySQL.getConnection();
		// 手机变更状态为空
		StringBuffer command = new StringBuffer();
		command.append("and enable=3");
		if (StringUtils.isNotBlank(userName) && !userName.equals("")) {
			command.append(" and username like '%");
			command.append(StringEscapeUtils.escapeSql(userName));
			command.append("%'");
		}

		try {
			dataPage(conn, pageBean, "v_blacklist_list", "*", "", command
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 更新黑名单用户
	 * 
	 * @param conn
	 * @param id
	 * @param enable
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateEnable(Long id, Integer enable) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateEnable(conn, id, enable);
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
	 * 修改用户认证状态
	 * @param id
	 * @param authStep
	 * @return
	 * @throws Exception
	 */
	public Long updateAuthStepByid(Long id,int authStep) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateAuthStepByid(conn, id, authStep);
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
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 查询锁定用户或未锁定用户
	 * 
	 * @param userName
	 *            用户名
	 * @param realName
	 *            真实名字
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param lockType
	 *            判断是否锁定，1为未锁定，2为锁定
	 * @param pageBean
	 * @throws SQLException
	 * @throws DataException
	 */
	public void queryLockUsers(String userName, String realName, String userType, String orgName,
			String startTime, String endTime, int lockType, PageBean pageBean)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" and username like '%");
			condition.append(StringEscapeUtils.escapeSql(userName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append(" and realName like '%");
			condition.append(StringEscapeUtils.escapeSql(realName));
			condition.append("%' ");
		}
		if (StringUtils.isNotBlank(userType) &&  !"-1".equals(userType)) {
			condition.append(" and userType = '");
			condition.append(StringEscapeUtils.escapeSql(userType));
			condition.append("' ");
		}
		if (StringUtils.isNotBlank(orgName)) {
			condition.append(" and orgName like '%");
			condition.append(StringEscapeUtils.escapeSql(orgName));
			condition.append("%' ");
		}
		if (lockType == 1) {// 启用
			if (StringUtils.isNotBlank(startTime)) {
				condition.append(" and createTime >= '");
				condition.append(StringEscapeUtils.escapeSql(startTime));
				condition.append("' ");
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition.append(" and createTime <= '");
				condition.append(StringEscapeUtils.escapeSql(endTime));
				condition.append("' ");
			}
			condition.append(" and enable=1 ");
		} else if (lockType == 2) { // 锁定
			if (StringUtils.isNotBlank(startTime)) {
				condition.append(" and lockTime >= '");
				condition.append(StringEscapeUtils.escapeSql(startTime));
				condition.append("' ");
			}
			if (StringUtils.isNotBlank(endTime)) {
				condition.append(" and lockTime <= '");
				condition.append(StringEscapeUtils.escapeSql(endTime));
				condition.append("' ");
			}
			condition.append(" and enable=2 ");
		}

		try {
			dataPage(conn, pageBean, "v_t_user_lock", "*", "", condition
					.toString());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	public Map<String, String> querymaterialsauthtypeCount() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.querymaterialsauthtypeCount(conn);
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
	 * 更新锁定用户状态
	 * 
	 * @param ids
	 * @param enable
	 * @return
	 * @throws SQLException
	 */
	public long updateLockedStatus(String ids, int enable) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			result = userDao.updateLockedStatus(conn, ids, enable);

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
	 * add by houli 查看用户是否已经绑定了手机号码
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public Map<String, String> queryBindingMobleUserInfo(Long userId)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queryBindingMobleUserInfo(conn, userId,
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
	 * 查询所有用户
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryUserAll() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());

		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> result = null;
		try {
			result = userDao.queryUserAll(conn);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;

	}

	public long queryUserIdByPhone(String cellPhone) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = userDao.queryUserIdByPhone(conn, cellPhone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public long updateLoginPwd(Long userId, String password) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = userDao.updatePwd(conn, userId, password, 1);
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

	public Long updateDealPwd(long userId, String password) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long result = -1;
		try {
			result = userDao.updatePwd(conn, userId, password, 2);
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
	 * 查询用户信息
	 * 
	 * @param userName
	 *            邮箱号，手机号，用户名
	 * @param pwd
	 *            密码
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserByUserAndPwd(String userName, String pwd)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> result = null;
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastTime = simple.format(new Date());
		Map<String, String> umap = new HashMap<String, String>();
		umap = userDao.queryUserByUserName(conn, userName);
		try {
			if (null == umap) {
				log.info("null == umap,return...");
				return null;
			}
			int id = Convert.strToInt(umap.get("id"), -1);
			String lastLoginTime = Convert.strToStr(umap.get("lastDate"), null);
			if (null != lastLoginTime) {
				Date loginTime = simple.parse(lastLoginTime);
				if (new Date().getTime() - loginTime.getTime() > 3 * 60 * 60 * 1000) {
					// 取消用户限制登录将isLoginLimit设置为1和loginErrorCount设置为0
					userDao.resetUserState(conn, 0, 1, id);
				}
			}
			umap = userDao.queryUserByUserName(conn, userName);
			int isLoginLimit = Convert.strToInt(umap.get("isLoginLimit"), 1);
			int loginErrorCount = Convert.strToInt(umap.get("loginErrorCount"),
					0);
			if (isLoginLimit == 2 || loginErrorCount == 3) {
				// 设置用户限制登录，设置loginErrorCount为0
				userDao.resetUserState(conn, 0, 2, id);
				conn.commit();
				result = new HashMap<String, String>();
				result.put("isLoginLimit", "2");
				return result;
			}
			result = userDao.queryUserByUserAndPwd(conn, userName, pwd);
			if (null == result) {
				int count = loginErrorCount + 1;
				// 更新用户错误登录次数和最后登陆时间
				if (count == 3) {
					// 设置用户限制登录，重置loginErrorCount为0
					userDao.updateUserState1(conn, 0,2, lastTime, id);
				} else {
					userDao.updateUserState1(conn, count,1, lastTime, id);
				}
			} else {
				userDao.updateUserState1(conn, 0,1, lastTime, id);
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	/**
	 * 查询用户可投金额
	 * queryUserAmount
	 * @auth hejiahua
	 * @param userId
	 * @return
	 * @throws Exception 
	 * Map<String,String>
	 * @exception 
	 * @date:2014-10-9 上午8:12:03
	 * @since  1.0.0
	 */
	public Map<String, String> queryUserAmount(Long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			return userDao.queryUserAmount(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/*
	 * 红包金额跟可用余额
	 */
	public Map<String, String> queryPresent (Long userId) throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			return userDao.queryPresrent(conn, userId);
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * 手机查询
	 * 
	 * @param cellphone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long isPhoneExist(String cellphone) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = userDao.isPhoneExist(conn, cellphone);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return list.size() <= 0 ? -1L : 1L;
	}

	public long updateEmalByid(long id, String email) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			result = userDao.updateEmalByid(conn, id, email);
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
	
	public long updateIsLoginLimit(long id) throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        long result = -1;
        Connection conn = MySQL.getConnection();
        try {
            Dao.Tables.t_user user = new Dao().new Tables().new t_user();
            user.isLoginLimit.setValue(1);//解除限制
            result = user.update(conn, " id ="+id);
            if (result>0) {
                conn.commit();
            }else {
                conn.rollback();
            }
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
	 * 更新用户的申请密码保护状态 默认是1，表示还没有申请，2表示已经申请
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long updatePwdProState(Long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			result = userDao.updatePwdProState(conn, userId);
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

	public Map<String, String> queEmailUser(Long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			return myHomeInfoSettingDao.queEmailUser(conn, userId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 激活账户
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long updateUserActivate(long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.updateUserActivate(conn, userId);
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
	 * 更换校验码1：通过Action调用
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long updateSign(long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Map<String, String> fundMap = new HashMap<String, String>();
		try {
			userMap = userDao.queryUserById(conn, userId);
			if (userMap == null) {
				return -1;
			} else {
				String id = Convert.strToStr(userMap.get("id"), "0");
				String usableSum = Convert.strToStr(userMap.get("usableSum"),
						"0");
				String freezeSum = Convert.strToStr(userMap.get("freezeSum"),
						"0");
				String dueinSum = Convert
						.strToStr(userMap.get("dueinSum"), "0");
				String dueoutSum = Convert.strToStr(userMap.get("dueoutSum"),
						"0");
				fundMap = userDao.queryMaxIdFundById(conn, userId);
				if (fundMap == null) {
					if ("1".equals(IConstants.ENABLED_PASS)) {
						String sign = com.shove.security.Encrypt.MD5(id
								+ usableSum + freezeSum + dueinSum + dueoutSum);
						String sign2 = com.shove.security.Encrypt
								.MD5("0" + id + "0" + "0" + "0" + "0" + "0"
										+ "0" + "0" + "0");
						userDao.setSign(conn, userId, sign, sign2);
					} else {
						String sign = com.shove.security.Encrypt.MD5(id
								+ usableSum + freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						String sign2 = com.shove.security.Encrypt.MD5("0" + id
								+ "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0"
								+ IConstants.PASS_KEY);
						userDao.setSign(conn, userId, sign, sign2);
					}
				} else {
					String f_id = Convert.strToStr(fundMap.get("id"), "0");
					String f_userId = Convert.strToStr(fundMap.get("userId"),
							"0");
					String f_handleSum = Convert.strToStr(fundMap
							.get("handleSum"), "0");
					String f_usableSum = Convert.strToStr(fundMap
							.get("usableSum"), "0");
					String f_freezeSum = Convert.strToStr(fundMap
							.get("freezeSum"), "0");
					String f_dueinSum = Convert.strToStr(fundMap
							.get("dueinSum"), "0");
					String f_dueoutSum = Convert.strToStr(fundMap
							.get("dueoutSum"), "0");
					String f_cost = Convert.strToStr(fundMap.get("cost"), "0");
					String f_income = Convert.strToStr(fundMap.get("income"),
							"0");
					String f_spending = Convert.strToStr(fundMap
							.get("spending"), "0");
					if ("1".equals(IConstants.ENABLED_PASS)) {
						String sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum);
						String sign2 = com.shove.security.Encrypt.MD5(f_id
								+ f_userId + f_handleSum + f_usableSum
								+ f_freezeSum + f_dueinSum + f_dueoutSum
								+ f_cost + f_income + f_spending);
						userDao.setSign(conn, userId, sign, sign2);
					} else {
						String sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						String sign2 = com.shove.security.Encrypt.MD5(f_id
								+ f_userId + f_handleSum + f_usableSum
								+ f_freezeSum + f_dueinSum + f_dueoutSum
								+ f_cost + f_income + f_spending
								+ IConstants.PASS_KEY);
						userDao.setSign(conn, userId, sign, sign2);
					}
				}
			}
			log.info("-------------" + userMap.get("username")
					+ "：更换校验码-----------");
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
	 * 更换校验码1：通过Action调用
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public long updateSign(Connection conn, long userId) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> userMap = new HashMap<String, String>();
		Map<String, String> fundMap = new HashMap<String, String>();
		userMap = userDao.queryUserById(conn, userId);
		if (userMap == null) {
			return -1;
		} else {
			String id = Convert.strToStr(userMap.get("id"), "0");
			String usableSum = Convert.strToStr(userMap.get("usableSum"), "0");
			String freezeSum = Convert.strToStr(userMap.get("freezeSum"), "0");
			String dueinSum = Convert.strToStr(userMap.get("dueinSum"), "0");
			String dueoutSum = Convert.strToStr(userMap.get("dueoutSum"), "0");
			fundMap = userDao.queryMaxIdFundById(conn, userId);
			if (fundMap == null) {
				if ("1".equals(IConstants.ENABLED_PASS)) {
					String sign = com.shove.security.Encrypt.MD5(id + usableSum
							+ freezeSum + dueinSum + dueoutSum);
					String sign2 = com.shove.security.Encrypt.MD5("0" + id
							+ "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0");
					userDao.setSign(conn, userId, sign, sign2);
				} else {
					String sign = com.shove.security.Encrypt.MD5(id + usableSum
							+ freezeSum + dueinSum + dueoutSum
							+ IConstants.PASS_KEY);
					String sign2 = com.shove.security.Encrypt.MD5("0" + id
							+ "0" + "0" + "0" + "0" + "0" + "0" + "0" + "0"
							+ IConstants.PASS_KEY);
					userDao.setSign(conn, userId, sign, sign2);
				}
			} else {
				String f_id = Convert.strToStr(fundMap.get("id"), "0");
				String f_userId = Convert.strToStr(fundMap.get("userId"), "0");
				String f_handleSum = Convert.strToStr(fundMap.get("handleSum"),
						"0");
				String f_usableSum = Convert.strToStr(fundMap.get("usableSum"),
						"0");
				String f_freezeSum = Convert.strToStr(fundMap.get("freezeSum"),
						"0");
				String f_dueinSum = Convert.strToStr(fundMap.get("dueinSum"),
						"0");
				String f_dueoutSum = Convert.strToStr(fundMap.get("dueoutSum"),
						"0");
				String f_cost = Convert.strToStr(fundMap.get("cost"), "0");
				String f_income = Convert.strToStr(fundMap.get("income"), "0");
				String f_spending = Convert.strToStr(fundMap.get("spending"),
						"0");
				if ("1".equals(IConstants.ENABLED_PASS)) {
					String sign = com.shove.security.Encrypt.MD5(f_userId
							+ usableSum + freezeSum + dueinSum + dueoutSum);
					String sign2 = com.shove.security.Encrypt.MD5(f_id
							+ f_userId + f_handleSum + f_usableSum
							+ f_freezeSum + f_dueinSum + f_dueoutSum + f_cost
							+ f_income + f_spending);
					userDao.setSign(conn, userId, sign, sign2);
				} else {
					String sign = com.shove.security.Encrypt.MD5(f_userId
							+ usableSum + freezeSum + dueinSum + dueoutSum
							+ IConstants.PASS_KEY);
					String sign2 = com.shove.security.Encrypt.MD5(f_id
							+ f_userId + f_handleSum + f_usableSum
							+ f_freezeSum + f_dueinSum + f_dueoutSum + f_cost
							+ f_income + f_spending + IConstants.PASS_KEY);
					userDao.setSign(conn, userId, sign, sign2);
				}
			}
		}
		log.info("-------------" + userMap.get("username")
				+ "：更换校验码-----------");
		return userId;
	}

	public void setRelationDao(RelationDao relationDao) {
		this.relationDao = relationDao;
	}

	public void setShoveBorrowAmountTypeDao(
			ShoveBorrowAmountTypeDao shoveBorrowAmountTypeDao) {
		this.shoveBorrowAmountTypeDao = shoveBorrowAmountTypeDao;
	}

	public OperationLogDao getOperationLogDao() {
		return operationLogDao;
	}

	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	public boolean checkSign(Long userId) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> userMap = new HashMap<String, String>();
		Map<String, String> fundMap = new HashMap<String, String>();
		String sign = "";
		String sign2 = "";
		try {
			userMap = userDao.queryUserById(conn, userId);
			if (userMap == null) {
				return false;
			} else {
				String id = Convert.strToStr(userMap.get("id"), "0");
				String usableSum = Convert.strToStr(userMap.get("usableSum"),
						"0");
				String freezeSum = Convert.strToStr(userMap.get("freezeSum"),
						"0");
				String dueinSum = Convert
						.strToStr(userMap.get("dueinSum"), "0");
				String dueoutSum = Convert.strToStr(userMap.get("dueoutSum"),
						"0");
				fundMap = userDao.queryMaxIdFundById(conn, userId);
				if (fundMap == null) {
					if ("1".equals(IConstants.ENABLED_PASS)) {
						sign = com.shove.security.Encrypt.MD5(id + usableSum
								+ freezeSum + dueinSum + dueoutSum);
						sign2 = com.shove.security.Encrypt.MD5("0" + id + "0"
								+ "0" + "0" + "0" + "0" + "0" + "0" + "0");
					} else {
						sign = com.shove.security.Encrypt.MD5(id + usableSum
								+ freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						sign2 = com.shove.security.Encrypt.MD5("0" + id + "0"
								+ "0" + "0" + "0" + "0" + "0" + "0" + "0"
								+ IConstants.PASS_KEY);
					}
				} else {
					String f_id = Convert.strToStr(fundMap.get("id"), "0");
					String f_userId = Convert.strToStr(fundMap.get("userId"),
							"0");
					String f_handleSum = Convert.strToStr(fundMap
							.get("handleSum"), "0");
					String f_usableSum = Convert.strToStr(fundMap
							.get("usableSum"), "0");
					String f_freezeSum = Convert.strToStr(fundMap
							.get("freezeSum"), "0");
					String f_dueinSum = Convert.strToStr(fundMap
							.get("dueinSum"), "0");
					String f_dueoutSum = Convert.strToStr(fundMap
							.get("dueoutSum"), "0");
					String f_cost = Convert.strToStr(fundMap.get("cost"), "0");
					String f_income = Convert.strToStr(fundMap.get("income"),
							"0");
					String f_spending = Convert.strToStr(fundMap
							.get("spending"), "0");
					if ("1".equals(IConstants.ENABLED_PASS)) {
						sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum);
						sign2 = com.shove.security.Encrypt.MD5(f_id + f_userId
								+ f_handleSum + f_usableSum + f_freezeSum
								+ f_dueinSum + f_dueoutSum + f_cost + f_income
								+ f_spending);
					} else {
						sign = com.shove.security.Encrypt.MD5(f_userId
								+ usableSum + freezeSum + dueinSum + dueoutSum
								+ IConstants.PASS_KEY);
						sign2 = com.shove.security.Encrypt.MD5(f_id + f_userId
								+ f_handleSum + f_usableSum + f_freezeSum
								+ f_dueinSum + f_dueoutSum + f_cost + f_income
								+ f_spending + IConstants.PASS_KEY);
					}
				}
				if (userMap.get("sign").equals(sign)
						&& userMap.get("sign2").equals(sign2)) {
					return true;
				}
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
		return false;
	}

	public Map<String, String> queryUserBindphone(long userId)
			throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> result = userDao.queryUserBindphone(conn, userId);
			return result;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}finally{
			conn.close();
		}
		
	}
	/**
	 * 根据电话号码查询用户信息
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryUserByPhone(String phone) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = userDao.queryUserByPhone(conn, phone);
			return map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public Map<String, String> queryUserByName(String email) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		try {
			Map<String, String> map = userDao.queryUserByName(conn, email);
			return map;
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void queryUserByNickName(String name,PageBean pageBean) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
    Connection conn = MySQL.getConnection();
    try {
        StringBuilder command = new StringBuilder();
        command.append("   and ( username like '%");
        command.append(StringEscapeUtils.escapeSql(name));
        command.append("%' or email like '%");
        command.append(StringEscapeUtils.escapeSql(name));
        command.append("%' or mobilePhone like '%");
        command.append(StringEscapeUtils.escapeSql(name));
        command.append("%'  )");
        dataPage(conn, pageBean, " t_user ", " id,username ", " order by id desc", command.toString());
    } catch (Exception e) {
        log.error(e);
        e.printStackTrace();
        throw e;
    } finally {
        conn.close();
    }
}
	
	/**
	 * 后台查询员工绑定前台用户
	 * queryUserByUsername
	 * @auth hejiahua
	 * @param username
	 * @return
	 * @throws Exception 
	 * Map<String,String>
	 * @exception 
	 * @date:2014-11-6 下午4:10:41
	 * @since  1.0.0
	 */
	public Map<String, String> queryUserByUsername(String username) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
            Connection conn = MySQL.getConnection();
            Map<String, String>  map = null;
            try {
                StringBuilder command = new StringBuilder();
                command.append(" select    id,username,email,cellPhone,realName,userType  from  v_t_usermanage_info  where  username = '");
                command.append(StringEscapeUtils.escapeSql(username));
                command.append("' or email = '");
                command.append(StringEscapeUtils.escapeSql(username));
                command.append("' or cellPhone = '");
                command.append(StringEscapeUtils.escapeSql(username));
                command.append("' and userType=1 ");
                DataSet ds = Database.executeQuery(conn, command.toString());
                map = BeanMapUtils.dataSetToMap(ds);
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
                throw e;
            } finally {
                conn.close();
            }
            return map;
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
	
	/**
	 * 根据用户id查询用户Token信息
	 * 
	 * @param id
	 * @throws DataException
	 * @throws SQLException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserTokenById(long id) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserTokenById(conn, id);
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
	 * 更新TOKEN信息
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long updateUserToken(long userId,String token,String checktime) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.updateUserToken(conn, userId ,token ,checktime);
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
	 * 分页存储过程
	 * @param pageBean
	 * @param table 表名
	 * @param field 字段名
	 * @param order 排序
	 * @param condition 条件
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public  List<Map<String, Object>> getDbData(String table, String field, String order, String condition, int pageSize, int pageNo) throws SQLException, DataException, IOException{
			DataSet ds = new DataSet();
			Connection conn = MySQL.getConnection();
			List<Object> outParameterValues = new ArrayList<Object>();
			try
			{
			    if (condition.contains("#")) {//因为单引号存在问题。所以传过来的用#代替，然后在替换
                    condition =  condition.replace("#", "'");
                }
			    System.out.println(condition);
				Procedures.pr_page(conn, ds, outParameterValues, table, field, pageSize, pageNo, order, condition, pageSize);
				ds.tables.get(0).rows.genRowsMap();
				return ds.tables.get(0).rows.rowsMap;
			}
			catch(Exception ex)
			{				
			}
			finally
			{
				conn.close();
			}
			
			return null;
	}
	
	/**
	 * 验证字段在数据库是否存在
	 * validationIdNoAndRealName(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @param field  字段名
	 * @param fieldValue 字段值
	 * @param table 表名
	 * @return
	 * @throws SQLException 
	 * Long
	 * @exception 
	 * @date:2014-9-15 下午2:22:49
	 * @since  1.0.0
	 */
	public Long validationIdNoAndRealName(String field,String fieldValue,String table) throws SQLException{
	    log.info("className+"+this.getClass().getName()+" ---------method------"+Thread.currentThread().getStackTrace()[1].getMethodName());
	    Connection conn = MySQL.getConnection();
	    long count = -1;
	    try {
            DataSet ds =  Database.executeQuery(conn, " select count(1) as c  from `"+table+"` where "+field+" = '"+Utility.filteSqlInfusion(fieldValue)+"'");
            count = Convert.strToLong(BeanMapUtils.dataSetToMap(ds).get("c"), -1);
            return count;
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }finally{
            conn.close();
        }
	    return count;
	}
	public  List<Map<String, Object>> queryUserManageBnormalInfo() throws SQLException{
	    Connection conn = MySQL.getConnection();
	    List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
	    try {
	       DataSet ds =  Database.executeQuery(conn, "select * from t_user");
	       ds.tables.get(0).rows.genRowsMap();
	       List<Map<String, Object>> list1= ds.tables.get(0).rows.rowsMap;
	       for (Map<String, Object> map : list1) {
            long userId = Convert.strToLong(map.get("id").toString(), -1);
            if (!checkSign(userId)) {//异常了
                lists.add(map);
            }
        }
        }
        catch (Exception e) {
        }finally{
            conn.close();
        }
	    return lists;
	}
	
	/**
	 *功能：根据用户ID查询用户信息，包括银行卡
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> queryUserInfoById(long id) throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.queryUserInfoById(conn, id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
			conn.close();
		}
		return map;
	}
	
	
	public long updateSmsByid(long id, String sms,int type) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			result = userDao.updateSmsByid(conn, id, sms,type);
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
	 *功能：添加短信\邮件记录
	 * @param sms
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public long addSendLog(String phone,String sendType,Long waitTime) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			
			//userDao 查找超时时间
			Map m = userDao.querySmsOverTime(conn);
			String smsOverTime = (String) m.get("smsOverTime");
			try {
				waitTime = Long.parseLong(smsOverTime);
			} catch (Exception e) {
				waitTime = 600L;
			}
			result = userDao.addSendLog(conn, phone,sendType,waitTime);
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
	 *功能：添加短信\邮件记录
	 * @param sms
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public long addSendLog(String phone,String sendType,Long waitTime,String sendCode) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			
			//userDao 查找超时时间
			Map m = userDao.querySmsOverTime(conn);
			String smsOverTime = (String) m.get("smsOverTime");
			try {
				waitTime = Long.parseLong(smsOverTime);
			} catch (Exception e) {
				waitTime = 600L;
			}
			
			Map<String, String> map= userDao.querySendTime(conn,phone);
			if (map!=null) {
				long id = MapUtils.getLongValue(map, "id",-1L);
				if (id!=-1L) {
					result = userDao.updateSendLog(conn, phone,sendType,waitTime,sendCode,id);
				}else {
					result = userDao.addSendLog(conn, phone,sendType,waitTime,sendCode);
				}
			}else {
				result = userDao.addSendLog(conn, phone,sendType,waitTime,sendCode);
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
	 *功能：使短信\邮件记录过期
	 * @param sms
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public long overSendLog(String phone,String sendType,String sendCode) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			Long waitTime = 0L;
			Map<String, String> map= userDao.querySendTime(conn,phone, sendCode);
			if (map!=null) {
				long id = MapUtils.getLongValue(map, "id",-1L);
				if (id!=-1L) {
					result = userDao.updateSendLog(conn, phone,sendType,waitTime,sendCode,id);
				}else {
					result = userDao.addSendLog(conn, phone,sendType,waitTime,sendCode);
				}
			}else {
				result = userDao.addSendLog(conn, phone,sendType,waitTime,sendCode);
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
	 *功能：查询发送时间是否过期
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Boolean querySendTime(String emailSms) {
		log.info(";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.querySendTime(conn, emailSms);
			if(null == map){
				return true;
			}
			Date date = new Date();
			Long nowt = date.getTime();
			String dbDate = map.get("newTime");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date iDate = df.parse(dbDate);
			Long waitTime = Long.parseLong(map.get("waitTime"));
			if (nowt - iDate.getTime() > waitTime*1000){
				return false;
			}
			
		} catch (Exception e) {
			log.error(e);
			return true;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	/**
	 *功能：查询发送时间是否过期
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Boolean querySendTime(String emailSms,String sendCode) {
		log.info(";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();

		Map<String, String> map = new HashMap<String, String>();
		try {
			map = userDao.querySendTime(conn, emailSms,sendCode);
			if(null == map){
				return true;
			}
			Date date = new Date();
			Long nowt = date.getTime();
			String dbDate = map.get("newTime");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date iDate = df.parse(dbDate);
			Long waitTime = Long.parseLong(map.get("waitTime"))*1000;
			Long now = nowt - iDate.getTime();
			return now >waitTime;
		} catch (Exception e) {
			log.error(e);
			return true;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *功能：添加短信\邮件记录
	 * @param sms
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public long addUvpLog(Integer pv,Integer uv,Integer ip) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		long result = -1;
		Connection conn = MySQL.getConnection();
		try {
			
			boolean a = userDao.queryUvpLog(conn);
			//如果有当天的数据，则相加
			if (true == a){
				result = userDao.updateUvpLog(conn, pv,uv,ip);
			} else {//如果没有当天的数据，则插入
				result = userDao.addUvpLog(conn, pv,uv,ip);	
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
	 * 修改用户认证状态
	 * @param id
	 * @param authStep
	 * @return
	 * @throws Exception
	 */
	public Long updatedDefaultBankcard(Long id,int userType,int defaultBcard) throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Connection conn = MySQL.getConnection();
		Long result = -1L;
		try {
			if (userType == 2){
				result = userDao.updatedDefaultBankcard2(conn, id, defaultBcard);
			} else{
				result = userDao.updatedDefaultBankcard(conn, id, defaultBcard);
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
	 * 来源统计
	 * @param id
	 * @param authStep
	 * @return
	 * @throws Exception
	 */
	public Long sourceStatistic(Map <String, Integer> map) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		Long result = -1L;
		try {
			StringBuffer sous = new StringBuffer("");
			Connection conn = MySQL.getConnection();
			//拼接SQL条件 ，
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				sous.append("'").append(entry.getKey()).append("'").append(",");
			}
			sous.append("'-99'");
			
			try {
				//查找表中是否存在这些推广数据
	            DataSet ds =  Database.executeQuery(conn, "SELECT mainSource,countIn FROM t_source_click a WHERE a.mainSource IN ("+sous.toString()+") AND a.theDate = DATE_FORMAT(NOW(),'%Y-%m-%d')");
	            ds.tables.get(0).rows.genRowsMap();
	            List <HashMap>list = ds.tables.get(0).rows.rowsMap;
	            Map tabMap = new HashMap();
	            
	            //将已经存在的，封装成Map
	            for (Map m : list){
	            	tabMap.put(m.get("mainSource"), m.get("countIn"));
	            }
	            
	            //逻辑处理，保存或更新
	            for (Map.Entry<String, Integer> entry : map.entrySet()) {
					//如果在表中找不到，则保存
					if (null == tabMap.get(entry.getKey())){
						userDao.sourceStatisticSave(conn, entry.getKey(),entry.getValue());
					} else {//如果在表中找到了，则更新
						userDao.sourceStatisticUpdate(conn, entry.getKey(),entry.getValue());
					}
				}
	            sous = null;
	            conn.commit();
	        } catch (Exception e) {
	            log.error(e);
	            e.printStackTrace();
	        }finally{
	            conn.close();
	        }
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		} finally {
		}
		return result;
	}
	

	/**
	 * @throws SQLException 
	 * 
	 */
	public void visitCustomList(Map map, PageBean pageBean) throws SQLException{
		String userName = (String) map.get("userName");
		String regTimes = (String) map.get("regTimes");
		String regTimee = (String) map.get("regTimee");
//		String investAmts = (String) map.get("investAmts");
//		String investAmte = (String) map.get("investAmte");
		String linkNos = (String) map.get("linkNos");
		String linkNoe = (String) map.get("linkNoe");
		String usableAmts = (String) map.get("usableAmts");
		String usableAmte = (String) map.get("usableAmte");
		
		StringBuffer condition = new StringBuffer("");
		if (StringUtils.isNotEmpty(linkNos)){
			condition.append(" and visitNo >=").append(linkNos);
		}
		if (StringUtils.isNotEmpty(linkNoe)){
			condition.append(" and visitNo <=").append(linkNoe);
		}
//		if (StringUtils.isNotEmpty(investAmts)){
//			condition.append(" and investSum >=").append(investAmts);
//		}
//		if (StringUtils.isNotEmpty(investAmte)){
//			condition.append(" and investSum <=").append(investAmte);
//		}
		
		StringBuffer command = new StringBuffer("");
		command.append("(SELECT c.id,c.username,c.mobilePhone,c.usableSum,c.lastDate,c.createTime,c.realName,c.sex,d.recommendUserId,d.redName,")//,g.investTime,g.investAmount
		//.append("(SELECT SUM(i.investAmount) FROM t_invest i WHERE i.investor=c.id)investSum,IFNULL(j.visitNo,0) visitNo FROM ")
		.append("IFNULL(j.visitNo,0) visitNo FROM ")
		.append("(SELECT a.id,a.username,a.mobilePhone,a.usableSum,a.lastDate,a.createTime,b.realName ,b.sex ")
		.append("FROM t_user a,t_person b ")
		.append("WHERE a.id=b.userId ");
		
		//当用户名不为空时
		if (StringUtils.isNotEmpty(userName)){
			command.append("AND a.username='").append(userName).append("'");
		}
		//当注册开始时间不为空时
		if (StringUtils.isNotEmpty(regTimes)){
			command.append("AND a.createTime>='").append(regTimes).append("'");
		}
		//当注册结束时间不为空时
		if (StringUtils.isNotEmpty(regTimee)){
			command.append("AND a.createTime<='").append(regTimee).append("'");
		}
		//当可用金额不为空时
		if (StringUtils.isNotEmpty(usableAmts)){
			command.append("AND a.usableSum >= '").append(usableAmts).append("'");
		}
		if (StringUtils.isNotEmpty(usableAmte)){
			command.append("AND a.usableSum <= '").append(usableAmte).append("'");
		}
		
		command.append(")c LEFT JOIN (SELECT e.userId,e.recommendUserId,f.realName redName FROM t_recommend_user e,t_person f WHERE e.recommendUserId=f.userId)d ON c.id = d.userId ") 
//		.append("LEFT JOIN (")
//		.append("SELECT h.investTime,h.investAmount,h.investor FROM t_invest h WHERE (h.investor,h.investTime) IN(")
//		.append("SELECT f.investor, MAX(f.investTime)investTime FROM t_invest f GROUP BY f.investor ")
//		.append(")")
//		.append(") g ON g.investor=c.id ")
        .append("LEFT JOIN (SELECT COUNT(id) visitNo,userId FROM t_custom_visit k GROUP BY k.userId) j ON j.userId=c.id)t");
		log.info("---sql:" + command.toString());
		Connection conn = MySQL.getConnection();
		try {
			dataPage(conn, pageBean, command.toString(), " * ", " order by id desc", condition.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		System.out.println("getTotalNum:" + pageBean.getTotalNum());
		
		/*SELECT c.id,c.username,c.usableSum,c.createTime,c.realName,d.recommendUserId,d.redName,g.investTime,g.investAmount FROM 
(SELECT a.id,a.username,a.usableSum,a.createTime,b.realName 
FROM t_user a,t_person b 
WHERE a.id=b.userId 
)c LEFT JOIN (SELECT e.userId,e.recommendUserId,f.realName redName FROM  t_recommend_user e,t_person f WHERE e.recommendUserId=f.userId)d ON c.id = d.userId  
LEFT JOIN (
SELECT h.investTime,h.investAmount,h.investor FROM t_invest h WHERE (h.investor,h.investTime) IN(
SELECT f.investor, MAX(f.investTime)investTime FROM t_invest f GROUP BY f.investor 
)
) g ON g.investor=c.id;*/
	}
	
	
	public List queryVisitCustom(String id){
		List <HashMap>list = null;
		Connection conn = MySQL.getConnection();
        try {
        	StringBuffer sb = new StringBuffer("SELECT a.id,a.createTime,a.createdBy,");
        	sb.append("CASE WHEN a.linkType=1 THEN '电话' WHEN a.linkType=2 THEN 'QQ' WHEN a.linkType=3 THEN '邮件' END  linkType,")
        	.append("CASE WHEN a.visitType=1 THEN '有效' WHEN a.visitType=0 THEN '无效' END  visitType,")
        	.append("CASE WHEN a.knowType=1 THEN '朋友推荐' WHEN a.knowType=2 THEN '广告' WHEN a.knowType=3 THEN '自行寻找' WHEN a.knowType=5 THEN '网络' WHEN a.knowType=6 THEN '微信'  WHEN a.knowType=4 THEN '现场活动' END  knowType,")
        	.append("a.content ")
        	.append("FROM t_custom_visit a WHERE a.userId =")
        	.append(id);
        	
    		DataSet ds =  Database.executeQuery(conn, sb.toString());
            ds.tables.get(0).rows.genRowsMap();
            list = ds.tables.get(0).rows.rowsMap;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i<list.size(); i++){
            	Map map = list.get(i);
            	Date createTime = (Date) map.get("createTime");
            	String sTime = df.format(createTime);
            	map.put("createTime", sTime);
            	
            	String content = (String) map.get("content");
            	if (StringUtils.isNotEmpty(content) && content.length() > 20){
            		content = content.substring(0, 20) + "...";
            		map.put("content", content);
            	}
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	 *功能：保存回访记录
	 * @param map
	 */
	public void saveVisitCustom(Map map){
		String linkType = (String) map.get("linkType");
		String visitType = (String)map.get("visitType");
		String knowType = (String)map.get("knowType");
		String userId = (String)map.get("userId");
		String content = (String)map.get("content");
		String createdBy = (String)map.get("createdBy");
		String nextTime = (String)map.get("nextTime");
		Dao.Tables.t_custom_visit custom = new Dao().new Tables().new t_custom_visit();
		Connection conn = MySQL.getConnection();
		custom.linkType.setValue(linkType);
		custom.visitType.setValue(visitType);
		if (StringUtils.isNotEmpty(knowType)){
			custom.knowType.setValue(knowType);
		}
		custom.userId.setValue(userId);
		custom.content.setValue(content);
		custom.createdBy.setValue(createdBy);
		
		custom.createTime.setValue(new Date());
		custom.updateTime.setValue(new Date());
		if (StringUtils.isNotEmpty(nextTime)){
			custom.nextTime.setValue(nextTime);	
		}
		
		try {
			custom.insert(conn);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * 
	 *功能：根据ID查询回访记录
	 * @param map
	 */
	public Map queryCustomById(String id){
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds =  Database.executeQuery(conn, "select * from t_custom_visit a where a.id="+id);
			conn.commit();
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	/**
	 * 
	 *功能：根据ID查询回访记录
	 * @param map
	 */
	public Map queryCustomById2(String id){
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds =  Database.executeQuery(conn, "SELECT a.*,b.username FROM t_custom_visit a,t_user b WHERE a.userId=b.id AND a.id="+id);
			conn.commit();
			return BeanMapUtils.dataSetToMap(ds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	/**
	 * 
	 *功能：修改回访记录
	 * @param map
	 */
	public void editVisitCustom(Map map){
		String linkType = (String) map.get("linkType");
		String visitType = (String)map.get("visitType");
		String knowType = (String)map.get("knowType");
		String userId = (String)map.get("userId");
		String content = (String)map.get("content");
		String nextTime = (String)map.get("nextTime");
		String id = (String)map.get("id");
		Dao.Tables.t_custom_visit custom = new Dao().new Tables().new t_custom_visit();
		Connection conn = MySQL.getConnection();
		custom.linkType.setValue(linkType);
		custom.visitType.setValue(visitType);
//		custom.knowType.setValue(knowType);
		if (StringUtils.isNotEmpty(knowType)){
			custom.knowType.setValue(knowType);
		}
		custom.userId.setValue(userId);
		custom.content.setValue(content);
		custom.updateTime.setValue(new Date());
		if (StringUtils.isNotEmpty(nextTime)){
			custom.nextTime.setValue(nextTime);
		}
		try {
			custom.update(conn, "id="+id);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 
	 *功能：添加回访记录
	 * @param map
	 * @throws Exception 
	 */
	public void saveVisitCustom(String [][] strs,String createdBy) throws Exception{
		for (int i = 0; i < strs.length; i++) {
			String userId = strs[i][1];
			if (StringUtils.isBlank(userId)) {
				String username = strs[i][0];
				if (StringUtils.isNotBlank(username)) {
					Map<String, String> userMap =  queryIdByUser(username);
					if (userMap!=null && userMap.size()>0) {
						userId = userMap.get("id");
					}else {
						break;
					}
				}else {
					break;
				}
			}
			//1电话,2QQ,3邮件,4短信
			String linkType = strs[i][5];
			if (StringUtils.isNotBlank(linkType)) {
				if (linkType.equals("电话")) {
					linkType = "1";
				}
				if (linkType.equals("QQ")) {
					linkType = "2";	
				}
				if (linkType.equals("邮件")) {
					linkType = "3";
				}
				if (linkType.equals("短信")) {
					linkType = "4";
				}
			}
			//沟通类型1有效 0无效
			String visitType =strs[i][6];
			if (StringUtils.isNotBlank(visitType)) {
				if (visitType.equals("有效")) {
					visitType="1";
				}
				if (visitType.equals("无效")) {
					visitType="0";		
				}
			}
			//客户来源1朋友推荐,2广告,3现场活动
			String knowType =strs[i][7];
			if (StringUtils.isNotBlank(knowType)) {
				if (knowType.equals("朋友推荐")) {
					knowType="1";
				}
				else if (knowType.equals("广告")) {
					knowType="2";		
				}
				else if (knowType.equals("现场活动")) {
					knowType="4";
				}else if (knowType.equals("微信")) {
					knowType="6";
				}else if (knowType.equals("网络")) {
					knowType="5";
				}else{
					knowType="3";
				}
			}
			String content = strs[i][8];
			String nextTime = strs[i][10];
			Dao.Tables.t_custom_visit custom = new Dao().new Tables().new t_custom_visit();
			Connection conn = MySQL.getConnection();
			custom.linkType.setValue(linkType);
			custom.visitType.setValue(visitType);
			if (StringUtils.isNotEmpty(knowType)){
				custom.knowType.setValue(knowType);
			}
			custom.userId.setValue(userId);
			custom.content.setValue(content);
			custom.createdBy.setValue(createdBy);
			
			custom.createTime.setValue(new Date());
			custom.updateTime.setValue(new Date());
			if (StringUtils.isNotEmpty(nextTime)){
				custom.nextTime.setValue(DateUtil.strToDate(nextTime));	
			}else {
				custom.nextTime.setValue(new Date());	
			}
			try {
				custom.insert(conn);
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将红包金额转成过期金额
	 * @throws SQLException 
	 * 
	 */
	public void presrentToExpire(long id) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			userDao.presrentToExpire(conn,id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 扣费
	 * @Title: deductionUsableSum 
	 * @param @param userId
	 * @param @param remark
	 * @param @param usableSum
	 * @param @param username
	 * @param @param ip
	 * @param @return
	 * @param @throws Exception 设定文件 
	 * @return long 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public long deductionUsableSum(Connection conn,long userId,String remark,double usableSum,String username,String ip) throws Exception{
		return fundManagementService.addBackW(conn,userId, -1L, 11, usableSum, "涨薪宝转入", new Date(),"涨薪宝转入", ip, username, remark);
	}
	
	/**
	 * 充值
	 * @Title: rechargeUsableSum 
	 * @param @param userId
	 * @param @param remark
	 * @param @param usableSum
	 * @param @param username
	 * @param @param ip
	 * @param @return
	 * @param @throws Exception 设定文件 
	 * @return long 返回类型 
	 * @author Administrator
	 * @throws
	 */
	public long rechargeUsableSum(Connection conn,int type,long userId,String remark,double usableSum,String username,String fundMode,String ip) throws Exception{
		return fundManagementService.addBackR(conn,userId, -1L,type, usableSum, "涨薪宝转出", new Date(), fundMode, ip, username, remark);
	}
	
	/**
	 * 清空中奖过期处理
	 * @param conn
	 * @param str_id
	 * @param str_money
	 * @param type_int
	 * @return
	 * @throws Exception 
	 */
	public long clearAward(Connection conn,String id,double money,int type_int,long adminId,String addIP) throws Exception{
		long result = -1L;
		String command = "";
		long userId = Convert.strToLong(id, -1L);
		
		Map<String, String> userMap =  queryUserById(userId);
		if (userMap==null || userMap.size()==0) {
			return result;
		}
		
		String usableSumString = userMap.get("usableSum");
		
		switch (type_int) {
			case 0://清空全部，不能使用
				/*boolean isUsableSum = false;
				command = "UPDATE t_user t SET t.`expire` = t.`usableSum` + t.`expire` WHERE t.`id` = "+userId;
				result = MySQL.executeNonQuery(conn, command);//可用
				isUsableSum = result>0;
				command = "UPDATE t_user t SET t.`expire` = t.`presrent` + t.`expire` WHERE t.`id` = "+userId;
				result = MySQL.executeNonQuery(conn, command);//红包
				command = "UPDATE t_user t SET t.`usableSum` = 0.00,t.`presrent` = 0.00 WHERE t.`id` = "+userId;
				result = MySQL.executeNonQuery(conn, command);
				if (isUsableSum) {
					fundManagementService.addBackW(userId, adminId, IConstants.WITHDRAW, money, "活动", new Date(), IConstants.FUNDMODE_WITHDRAW_HANDLE, addIP, userMap
							.get("username"), "活动奖励金额过期");
					//更改验证码，资金记录
					updateSign(conn,userId);
				}*/
				break;
			case 1://清空可用余额
				if (money<=0) {
					/*command = "UPDATE t_user t SET t.`expire` = t.`usableSum` + t.`expire` WHERE t.`id` = "+userId;
					result = MySQL.executeNonQuery(conn, command);
					command = "UPDATE t_user t SET t.`usableSum` = 0.00 WHERE t.`id` = "+userId;
					result = MySQL.executeNonQuery(conn, command);*/
				}else {
					double usableSum = Convert.strToDouble(usableSumString, 0);
					if (usableSum<=0) {
						money = 0;
					}else if (usableSum<money) {
						money = usableSum;
					}
					fundManagementService.addBackW(userId, adminId, IConstants.WITHDRAW, money, "活动", new Date(), IConstants.FUNDMODE_WITHDRAW_HANDLE, addIP, userMap
							.get("username"), "活动奖励金额过期");
					command = "UPDATE t_user t SET t.`expire` = "+money+" + t.`expire` WHERE t.`id` = "+userId +"";
					result = MySQL.executeNonQuery(conn, command);
				}
				updateSign(conn, userId);
				break;
			case 2://清空红包金额
				if (money<=0) {
					command = "UPDATE t_user t SET t.`expire` = t.`presrent` + t.`expire` WHERE t.`id` = "+userId;
					result = MySQL.executeNonQuery(conn, command);
					command = "UPDATE t_user t SET t.`presrent` = 0.00 WHERE t.`id` = "+userId;
					result = MySQL.executeNonQuery(conn, command);
				}else {
					command = "UPDATE t_user t SET t.`expire` = "+money+" + t.`expire`,t.`presrent` = t.`presrent` - "+money+" WHERE t.`id` = "+userId+" and t.`presrent`>="+money;
					result = MySQL.executeNonQuery(conn, command);
				}
				break;
			default:
				break;
		}
		return result;
	}
	
	/**
	 * queryUserScore:查询用户积分. <br/>
	 *
	 * @author he
	 * @param id 用户ID
	 * @return
	 * @throws SQLException 
	 * @since JDK 1.6
	 */
	public Map<String, String> queryUserScore(long id) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return userDao.queryUserScore(conn,id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	 * 检查是否新用户
	 * hasNewUser: <br/>
	 *
	 * @author he
	 * @param startTime
	 * @param endTime
	 * @param id
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public boolean hasNewUser(String startTime,String endTime,long id) throws DataException, SQLException{
		Connection conn = MySQL.getConnection();
		boolean hasNewUser = false;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT count(id) as cou FROM t_user t WHERE t.`createTime` >='").append(startTime).append("' AND t.`createTime`<='").append(endTime).append("' and id = ").append(id);
			DataSet ds = MySQL.executeQuery(conn, sql.toString());
			Map<String, String> user = BeanMapUtils.dataSetToMap(ds);
			if (user!=null) {
				int count = Convert.strToInt(user.get("cou"), 0);
				hasNewUser = count>0;
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
		
		return hasNewUser;
	}
	
	/**
	 * queryOauth: 查询第三方帐号是否存在<br/>
	 * @author he
	 * @param openId
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public Map<String, String> queryOauth(String openId) throws Exception{
		Connection conn = MySQL.getConnection();
		try {
			return userDao.queryOauth(conn, openId);
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
	}
	
	/**
	 * insertOauth:插入第三方帐号信息. <br/>
	 *
	 * @author he
	 * @param map
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public long insertOauth(Map<String, String> map) throws Exception{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result =  userDao.insertOauth(conn, map);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	public Map<String, String> getUserInfoApp(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		try {
			return  userDao.getUserInfoApp(conn, userId);
		} catch (Exception e) {
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}
	
	/**
	 * updateOauth:更新第三方帐号信息. <br/>
	 *
	 * @author he
	 * @param map
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public long updateOauth(Map<String, String> map) throws Exception{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			result = userDao.updateOauth(conn, map);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
		return result;
	}
	
	
	/**
	 * 根据用户ID跟银行卡编号查询银行卡信息
	 * @param uid
	 * @param cardId
	 * @return
	 * @throws SQLException 
	 */
	public Map<String, String> queryUserBankCard(long uid,long cardId) throws SQLException{
		Connection conn = MySQL.getConnection();
		Map<String, String> bank = new HashMap<String, String>();
		try {
			bank = userDao.queryUserBankCard(conn,uid,cardId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return bank;
	}
	
	/**
	 * 查询是否实名
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public boolean isAuthentication(long userId) throws SQLException{
		Connection conn = MySQL.getConnection();
		boolean isAuthentication = false;
		try {
			Map<String, String> map= userDao.isAuthentication(conn,userId);
			if (map==null || map.isEmpty()) {
				return false;
			}else {
				int authCardName = MapUtils.getIntValue(map, "authCardName",1);
				if (authCardName==0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return isAuthentication;
	}
}
