package com.sp2p.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.data.dao.Table;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.web.Utility;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_award_score;
import com.sp2p.database.Dao.Tables.t_orginfo;
import com.sp2p.database.Dao.Tables.t_user;
import com.sp2p.entity.User;
import com.sp2p.entity.Users;
import com.sp2p.util.DBReflectUtil;

public class UserDao {
	
	
	public Map<String, String> queryOauth(Connection conn,String openId) throws DataException{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM t_user_oauth t WHERE t.`openid` = '").append(openId).append("'");
		DataSet ds = MySQL.executeQuery(conn, sql.toString());
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public long insertOauth(Connection conn,Map<String, String> map){
		Dao.Tables.t_user_oauth tOauth = new Dao().new Tables().new t_user_oauth();
		tOauth.openid.setValue(map.get("openid"));
		tOauth.type_code.setValue(map.get("type_code"));
		tOauth.userId.setValue(map.get("userId"));
		tOauth.token_expirein.setValue(map.get("token_expirein"));
		tOauth.access_token.setValue(map.get("access_token"));
		return tOauth.insert(conn);
	}
	
	public long updateOauth(Connection conn,Map<String, String> map){
		Dao.Tables.t_user_oauth tOauth = new Dao().new Tables().new t_user_oauth();
		if (StringUtils.isNotBlank(map.get("userId"))) {
			tOauth.userId.setValue(map.get("userId"));
		}
		if (StringUtils.isNotBlank(map.get("token_expirein"))) {
			tOauth.token_expirein.setValue(map.get("token_expirein"));
		}
		if (StringUtils.isNotBlank(map.get("access_token"))) {
			tOauth.access_token.setValue(map.get("access_token"));
		}
		return tOauth.update(conn, " openid='"+map.get("openid")+"' ");
	}
	
	public Map<String, String> getUserInfoApp(Connection conn,long userId) throws DataException{
		DataSet ds = Database.executeQuery(conn, "SELECT CASE WHEN tu.dealpwd!=tu.password THEN 1 ELSE 0 END AS is_business_pwd, tu.`username` AS user_name,tu.`mobilePhone` AS mobile_tel,f_formatting_common(tp.`realName`,1,2) AS real_name,f_formatting_common(tp.`idNo`,3,14) AS id_no,CASE WHEN tp.`authCardName`>0  THEN 1 ELSE 0 END AS auth_status,tp.`personalHead` AS user_head FROM t_user tu,t_person tp WHERE tu.`id` = tp.`userId` AND tu.`id` = "+userId);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String, String> queryUserBankCard(Connection conn,long userId,long cardId) throws DataException{
		DataSet ds = Database.executeQuery(conn, "SELECT t.`cardNo` FROM t_bankcard t WHERE t.`userId` = "+userId+" AND t.`isDelete`=2 AND t.`cardStatus`=1 AND t.`id` = "+cardId);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	public Map<String, String> isAuthentication(Connection conn,long userId) throws DataException{
		DataSet ds = Database.executeQuery(conn, "SELECT authCardName FROM t_person t WHERE t.`userId` ="+userId);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	
	/**
	 * 检查TOKEN
	 * @param conn
	 * @param userId
	 * @param uuid
	 * @return
	 * @throws DataException
	 */
	public Map<String, String> checkToken(Connection conn,long userId,String uuid) throws DataException{
		DataSet ds = Database.executeQuery(conn, "SELECT * FROM t_user_token t WHERE t.`userid` = "+userId+" AND t.`token` = '"+StringEscapeUtils.escapeSql(uuid)+"'");
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 查询TOKEN
	 * @param conn
	 * @param userId
	 * @return
	 * @throws DataException
	 */
	public Map<String, String> queryToken(Connection conn,long userId) throws DataException{
		DataSet ds = Database.executeQuery(conn, "SELECT * FROM t_user_token t WHERE t.`userid` = "+userId+"");
		return BeanMapUtils.dataSetToMap(ds);
	}
	
	/**
	 * 用户添加赠送金额
	 * @param presrent
	 * @param conn
	 * @throws SQLException
	 */
	public long addPresrent(double presrent,long userId,Connection conn) throws SQLException{
		String command = "UPDATE t_user t SET t.`presrent` = t.`presrent`+"+presrent+"  where id =" + userId;
		return MySQL.executeNonQuery(conn, command.toString());
	}
	
	
	public Map<String, String> queryUserScore(Connection conn,long id) throws DataException{
		String command = "SELECT t.`sumScore` FROM t_user t WHERE t.`id` = "+id;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 添加积分	
	 * @param conn
	 * @param score 积分
	 * @param type  类型
	 * @param month 月份
	 * @param userId  用户ID
	 * @return
	 */
	public long addScore(Connection conn,int score,int type,int month,long userId){
		String sqlStr = "update t_user set sumScore = sumScore+"+score+" where id="+userId;
		MySQL.executeNonQuery(conn, sqlStr);
		
		Dao.Tables.t_award_score award = new Dao().new Tables().new t_award_score();
		award.createDate.setValue(new java.util.Date());
		award.createTime.setValue(new java.util.Date());
		award.score.setValue(score);
		award.stype.setValue(type);//1注册认证，2推荐，3投资，
		award.actType.setValue(month);//以后再改，先写死 1：12月份活动，3：3月份活动
		award.userId.setValue(userId);
		return award.insert(conn);
	}
	
	/**
	 * 添加TOKEN
	 * 
	 * @param conn
	 * @param userId
	 * @param uuid
	 * @param checktime
	 * @return
	 * @throws SQLException
	 */
	public Long addToken(Connection conn,Long userid,String uuid,String checktime) throws SQLException {
		Dao.Tables.t_user_token usertoken = new Dao().new Tables().new t_user_token();
		usertoken.userid.setValue(userid);
		usertoken.token.setValue(uuid);
		usertoken.checktime.setValue(checktime);
		Long a = usertoken.insert(conn);
		return a;
	}

	/**
	 * 添加用户
	 * 
	 * @param conn
	 * @param email
	 * @param userName
	 * @param password
	 * @param refferee
	 * @param lastDate
	 * @param lastIP
	 * @param dealpwd
	 * @param mobilePhone
	 * @param rating
	 * @param creditrating
	 * @param status
	 * @param vipcreatetime
	 * @param creditlimit
	 * @param authstep
	 * @param headImg
	 * @return
	 * @throws SQLException
	 */
	public Long addUser(Connection conn, String email, String userName,
			String password, String refferee, String lastDate, String lastIP,
			String dealpwd, String mobilePhone, Integer rating,
			Integer creditrating, Integer vipstatus, String vipcreatetime,
			Integer authstep, String headImg, Integer enable,
			Long servicePersonId,double creditLimit,Integer userType,String orgName) throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();

		user.email.setValue(email);
		user.username.setValue(userName);
		user.password.setValue(password);
		user.lastDate.setValue(lastDate);
		user.refferee.setValue(refferee);
		user.dealpwd.setValue(password);
		if (StringUtils.isNotBlank(lastIP)) {
			user.lastIP.setValue(lastIP);
		}
		
		user.creditLimit.setValue(creditLimit);
		user.usableCreditLimit.setValue(creditLimit);
		user.authStep.setValue(authstep);
		user.mobilePhone.setValue(mobilePhone);
		user.rating.setValue(rating);
		user.creditrating.setValue(creditrating);
		user.vipStatus.setValue(vipstatus);
		user.vipCreateTime.setValue(vipcreatetime);
		user.headImg.setValue(headImg);
		user.enable.setValue(enable);
		user.createTime.setValue(new Date());
		user.userType.setValue(userType);
		user.orgName.setValue(orgName);
		return user.insert(conn);
	}
	
	public Long addUser(Connection conn, Users userEntity) throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();

		user.email.setValue(userEntity.getEmail());
		user.username.setValue(userEntity.getUserName());
		user.password.setValue(userEntity.getPassword());
		user.lastDate.setValue(userEntity.getLastDate());
		user.refferee.setValue(userEntity.getRefferee());
		user.dealpwd.setValue(userEntity.getDealpwd());
		if (StringUtils.isNotBlank(userEntity.getLastIP())) {
			user.lastIP.setValue(userEntity.getLastIP());
		}
		
		user.creditLimit.setValue(userEntity.getCreaditLimit());
		user.usableCreditLimit.setValue(userEntity.getUsableCreateitLimit());
		user.authStep.setValue(userEntity.getAuthStep());
		user.mobilePhone.setValue(userEntity.getMobilePhone());
		user.rating.setValue(userEntity.getRating());
		user.creditrating.setValue(userEntity.getCreditrating());
		user.vipStatus.setValue(userEntity.getVipStatus());
		user.vipCreateTime.setValue(userEntity.getCreateTime());
		user.headImg.setValue(userEntity.getHeadImg());
		user.enable.setValue(userEntity.getEnable());
		user.createTime.setValue(userEntity.getCreateTime());
		user.userType.setValue(userEntity.getUserType());
		user.orgName.setValue(userEntity.getOrgName());
		Long aa=user.insert(conn);
		
		
 		return aa;
	}
	
	
	/**
	 * 企业必须上传资料
	 * 查询用户前台五大基本资料信息和显示详细图片的第一张
	 * 
	 * @param conn
	 * @param id
	 *            用户的id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryCompanyBasePicture(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tuser.username username, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql.append(" left join t_user tuser on tuser.id = tm.userId ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.userType=2 AND tmy.isMust=2 AND  tuser.id =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	/**
	 * 企业可选上传资料
	 * 查询用户前台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param conn
	 * @param id
	 *            用户的id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryCompanySelectPicture(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tuser.username username, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql.append(" left join t_user tuser on tuser.id = tm.userId ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.userType=2 AND tmy.isMust=1 AND  tuser.id =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 添加用户的企业资料
	 * 
	 * @return
	 */
	public Long addUserCompanyData(Connection conn,Long orgId, String organizationName,
			String address, Integer industory, Integer companyType, Integer companyScale,
			String foundDate, String legalPerson, String regNum,
			String organizationCode, String registeredCapital, String bankName,
			String accountName, String publicBankaccount, String website,
			String phone, String email,
			String linkmanName,  String linkmanMobile,
			String companyDescription, String headJpg,String source) throws SQLException {
		
		Dao.Tables.t_orginfo orginfo = new Dao().new Tables().new t_orginfo();
		orginfo.user_id.setValue(orgId);
		orginfo.organization_name.setValue(organizationName);
		orginfo.address.setValue(address);
		orginfo.industory.setValue(industory);
		orginfo.company_type.setValue(companyType);
		orginfo.company_scale.setValue(companyScale);
		orginfo.found_date.setValue(foundDate);
		orginfo.legal_person.setValue(legalPerson);
		orginfo.reg_num.setValue(regNum);
		orginfo.organization_code.setValue(organizationCode);
		orginfo.registered_capital.setValue(registeredCapital);
		orginfo.bank_name.setValue(bankName);
		orginfo.account_name.setValue(accountName);
		orginfo.public_bank_account.setValue(publicBankaccount);
		orginfo.website.setValue(website);
		orginfo.phone.setValue(phone);
		orginfo.email.setValue(email);
		orginfo.linkman_name.setValue(linkmanName);
		orginfo.linkman_mobile.setValue(linkmanMobile);
		orginfo.company_description.setValue(companyDescription);
		orginfo.head_jpg.setValue(headJpg);
		orginfo.create_time.setValue(new Date());
		orginfo.source.setValue(source);
		return orginfo.insert(conn);
	}
	
	/**
	 * 更新用户的企业信息
	 * 
	 * @param conn
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
	public Long updateUserCompanyData(Connection conn,Long Id, Long orgId, String organizationName,
			String address, Integer industory, Integer companyType, Integer companyScale,
			String foundDate, String legalPerson, String regNum,
			String organizationCode, String registeredCapital, String bankName,
			String accountName, String publicBankaccount, String website,
			String phone, String email,
			String linkmanName,  String linkmanMobile,
			String companyDescription, String headJpg) throws SQLException,
			DataException {
		 
			Dao.Tables.t_orginfo orginfo = new Dao().new Tables().new t_orginfo();
			
			
			orginfo.organization_name.setValue(organizationName);
			orginfo.address.setValue(address);
			orginfo.industory.setValue(industory);
			orginfo.company_type.setValue(companyType);
			orginfo.company_scale.setValue(companyScale);
			orginfo.found_date.setValue(foundDate);
			orginfo.legal_person.setValue(legalPerson);
			orginfo.reg_num.setValue(regNum);
			orginfo.organization_code.setValue(organizationCode);
			orginfo.registered_capital.setValue(registeredCapital);
			orginfo.bank_name.setValue(bankName);
			orginfo.account_name.setValue(accountName);
			orginfo.public_bank_account.setValue(publicBankaccount);
			orginfo.website.setValue(website);
			orginfo.phone.setValue(phone);
			orginfo.email.setValue(email);
			orginfo.linkman_name.setValue(linkmanName);
			orginfo.linkman_mobile.setValue(linkmanMobile);
			orginfo.company_description.setValue(companyDescription);
			orginfo.head_jpg.setValue(headJpg);
			orginfo.auditStatus.setValue(1);
			orginfo.update_time.setValue(new Date());
			
		    Long result = -1L;
		    
		    result=orginfo.update(conn, "id="+Id+" and user_id="+orgId);
		    
		    return result;
		
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
	public Map<String, String> queryCompayById(Connection conn, long orgId)
			throws SQLException, DataException {
		Dao.Tables.t_orginfo orginfo = new Dao().new Tables().new t_orginfo();
		DataSet dataSet = orginfo.open(conn, "*", " user_id=" + orgId, " id desc ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryEmailById(Connection conn,long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) AS cccc FROM `t_user` WHERE id="+id+" AND ( email IS NULL OR email='')");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		if(dataSet==null){
			return null;
		}
//		return null;
		sql= null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 初始化资料认证
	 * 
	 * @param conn
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public synchronized Long addMaterialsauth1(Connection conn, Long userId, long type)
			throws Exception {
		// 初始化资料认证
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		materialsauth.materAuthTypeId.setValue(type);// 默认为16种类型
		materialsauth.userId.setValue(userId);
		return materialsauth.insert(conn);
	}

	/**
	 * 统计有多少图片类型
	 * 
	 * @throws DataException
	 * @throws SQLException4
	 */
	public Map<String, String> querymaterialsauthtypeCount(Connection conn)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select COUNT(*) as cccc from t_materialsauthtype ");
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql= null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 添加用户的基本资料
	 * 
	 * @return
	 */
	public Long addUserBaseData(Connection conn, String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo) throws SQLException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.cellPhone.setValue(cellPhone);
		person.sex.setValue(sex);
		person.birthday.setValue(birthday);
		person.highestEdu.setValue(highestEdu);
		person.eduStartDay.setValue(eduStartDay);
		person.school.setValue(school);
		person.maritalStatus.setValue(maritalStatus);
		person.hasChild.setValue(hasChild);
		person.hasHourse.setValue(hasHourse);
		person.hasHousrseLoan.setValue(hasHousrseLoan);
		person.hasCar.setValue(hasCar);
		person.hasCarLoan.setValue(hasCarLoan);
		person.nativePlacePro.setValue(nativePlacePro);
		person.nativePlaceCity.setValue(nativePlaceCity);
		person.registedPlacePro.setValue(registedPlacePro);
		person.registedPlaceCity.setValue(registedPlaceCity);
		person.address.setValue(address);
		person.telephone.setValue(telephone);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.personalHead.setValue(personalHead);
		return person.insert(conn);
	}

	/**
	 * 添加图片
	 * 
	 * @param conn
	 * @param materAuthTypeId
	 * @param imgPath
	 * @param auditStatus
	 * @param userId
	 * @param authTime
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addImage(Connection conn, long materAuthTypeId, String imgPath,
			long userId) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
		materialsauth.materAuthTypeId.setValue(materAuthTypeId);
		materialsauth.imgPath.setValue(imgPath);

		Map<String, String> map = null;
		Map<String, String> Accmap = null;
		if (userId > 0) {
			DataSet matondataSet = materialsauth.open(conn, "",
					" 1 = 1 AND materAuthTypeId = " + materAuthTypeId
							+ " AND userId =" + userId
							+ " AND imgPath is not null", "", -1, -1);
			map = BeanMapUtils.dataSetToMap(matondataSet);
		}
		if (map != null && map.size() > 0) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(new Date());
			materialsauth.passTime.setValue(date);
			materialsauth.auditStatus.setValue(1);
			// 审核之后不可以修改
			return materialsauth.update(conn, "materAuthTypeId = "
					+ materAuthTypeId + " AND userId = " + userId);
		} else {
			// 如果更新的那么更新user表中的步骤状态值
			materialsauth.userId.setValue(userId);
			Dao.Tables.t_user user = new Dao().new Tables().new t_user();

			Accmap = queryPicturStatuCount(conn, userId);
			Integer alli = 0;
			if (Accmap != null && Accmap.size() > 0) {

				alli = Convert.strToInt(Accmap.get("ccc"), 0);
				if (alli >= 4) {
					user.authStep.setValue(5);
					user.update(conn, " id = " + userId);
				}
			}

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(new Date());
			materialsauth.passTime.setValue(date);
			materialsauth.auditStatus.setValue(1);
			return materialsauth.update(conn, "materAuthTypeId = "
					+ materAuthTypeId + " AND userId = " + userId);
		}

	}

	public Long updateUserauthod(Connection conn, Long userId) {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Map<String, String> Accmap = null;// 统计t_materialsauth用户类型
		try {
			Accmap = queryPicturStatuCount(conn, userId);
			Integer alli = 0;
			if (Accmap != null && Accmap.size() > 0) {
				alli = Convert.strToInt(Accmap.get("ccc"), 0);
				if (alli >= 5) {
					user.authStep.setValue(5);
					return user.update(conn, " id = " + userId);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		return 1L;
	}
	
	/**
	 * 重写updateUserauthod，添加用户类型
	 * @param conn
	 * @param userId
	 * @return
	 */
	public Long updateUserauthod(Connection conn, Long userId,int userType) {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Map<String, String> Accmap = null;// 统计t_materialsauth用户类型
		try {
			if(userType==1){
				Accmap = queryPicturStatuCount(conn, userId);
			}else if(userType==2){
				Accmap = queryByMaterialIsOk(conn, userId, "17,18,19");
			}
			
			Integer alli = 0;
			if (Accmap != null && Accmap.size() > 0) {
				alli = Convert.strToInt(Accmap.get("ccc"), 0);
				if(userType==1){
					if (alli >= 5) {
						user.authStep.setValue(5);
						return user.update(conn, " id = " + userId);
					}
				}else if(userType==2){
					if (alli >= 3) {
						user.authStep.setValue(5);
						return user.update(conn, " id = " + userId);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		return 1L;
	}
	/**
	 * 增加系统日志 (只用于这个类)
	 * @param conn
	 * @param operation_table
	 * @param operation_user
	 * @param operation_type
	 * @param operation_ip
	 * @param operation_money
	 * @param operation_remarks
	 * @param operation_around
	 * @return
	 * @throws SQLException
	 */
	 public  long  addOperationLog(Connection  conn,String operation_table,String operation_user,int  operation_type,String operation_ip,
			  double operation_money,String operation_remarks ,int operation_around) throws SQLException{
			Dao.Tables.t_operation_log t_opration_log  = new Dao().new Tables().new t_operation_log();
			t_opration_log.operation_table.setValue(operation_table);
			t_opration_log.operation_user.setValue(operation_user);
			t_opration_log.operation_type.setValue(operation_type);
			t_opration_log.operation_ip.setValue(operation_ip);
			t_opration_log.operation_money.setValue(operation_money);
			t_opration_log.operation_remarks.setValue(operation_remarks);
			t_opration_log.operation_around.setValue(operation_around);
			t_opration_log.operation_time.setValue(new Date());
			
			return  t_opration_log.insert(conn);
		}
	/**
	 * 更新用户的基本信息
	 * 
	 * @param conn
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
	public Long updateUserBaseData(Connection conn, String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo,String username,String lastIP) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		if(StringUtils.isNotBlank(realName)){
		person.realName.setValue(realName);
		}
        person.cellPhone.setValue(cellPhone);
        if(StringUtils.isNotBlank(sex)){	
		person.sex.setValue(sex);
        }
        if(StringUtils.isNotBlank(birthday)){
		person.birthday.setValue(birthday);
        }
		person.highestEdu.setValue(highestEdu);
        
        if(StringUtils.isNotBlank(eduStartDay)){
		person.eduStartDay.setValue(eduStartDay);
        }
		person.school.setValue(school);
		person.maritalStatus.setValue(maritalStatus);
		person.hasChild.setValue(hasChild);
		person.hasHourse.setValue(hasHourse);
		person.hasHousrseLoan.setValue(hasHousrseLoan);
		person.hasCar.setValue(hasCar);
		person.hasCarLoan.setValue(hasCarLoan);
		person.nativePlacePro.setValue(nativePlacePro);
		person.nativePlaceCity.setValue(nativePlaceCity);
		person.registedPlacePro.setValue(registedPlacePro);
		person.registedPlaceCity.setValue(registedPlaceCity);
		person.address.setValue(address);
		person.telephone.setValue(telephone);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.personalHead.setValue(personalHead);
		Long result = -1L;
		Map<String, String> map = null;
		try{
		DataSet PersondataSet = person.open(conn, "", "userId = " + userId, "",
				-1, -1);
		map = BeanMapUtils.dataSetToMap(PersondataSet);
		}catch (Exception e) {
		  e.printStackTrace();
		  return -1L;
		}
		if (map != null && map.size() > 0) {
			
		    String 	realNamestr = map.get("realName");	
            if(realNamestr!=null&&!realNamestr.equals("")){
            	result =  person.update(conn, "userId = " + userId);	
            	//添加操作日志
				result = addOperationLog(conn, "t_person", username, IConstants.UPDATE, lastIP, 0, "更新个人详细资料", 1);
				return  result;
            }else{
				// 如果更新的那么更新user表中的步骤状态值
		    	Dao.Tables.t_user  user = new Dao().new Tables().new t_user();
		    	
		    		user.authStep.setValue(2);// 2表示填写完基本认证
		    	
				result = user.update(conn, " id = " + userId);
				if (result > 0) {
					result =  person.update(conn, "userId = " + userId);// 如果user表更新成功
					 //添加操作日志
					result = addOperationLog(conn, "t_person", username, IConstants.INSERT, lastIP, 0, "填写个人详细资料", 1);
					return result;
				}
				return result;
		    }
			//return person.update(conn, "userId = " + userId);
		} else {
			// 如果更新的那么更新user表中的步骤状态值
			Dao.Tables.t_user user = new Dao().new Tables().new t_user();
			user.authStep.setValue(2);// 2表示填写完基本认证
			result = user.update(conn, " id = " + userId);
			if (result > 0) {
				result =  person.insert(conn);// 如果user表更新成功
				//添加操作日志
				result = addOperationLog(conn, "t_person", username, IConstants.UPDATE, lastIP, 0, "更新个人详细资料", 1);
				return result;
			}
			return result;
		}
	}
	/** 
	 *    投资人填写个人个信息，后属性状态
	 * @param conn
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
	public Long updateUserBaseWWc(Connection conn, String realName,
			String cellPhone, String sex, String birthday, String highestEdu,
			String eduStartDay, String school, String maritalStatus,
			String hasChild, String hasHourse, String hasHousrseLoan,
			String hasCar, String hasCarLoan, Long nativePlacePro,
			Long nativePlaceCity, Long registedPlacePro,
			Long registedPlaceCity, String address, String telephone,
			String personalHead, Long userId, String idNo) throws SQLException,
			DataException {
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		person.realName.setValue(realName);
		person.cellPhone.setValue(cellPhone);
		person.sex.setValue(sex);
		person.birthday.setValue(birthday);
		person.highestEdu.setValue(highestEdu);
		person.eduStartDay.setValue(eduStartDay);
		person.school.setValue(school);
		person.maritalStatus.setValue(maritalStatus);
		person.hasChild.setValue(hasChild);
		person.hasHourse.setValue(hasHourse);
		person.hasHousrseLoan.setValue(hasHousrseLoan);
		person.hasCar.setValue(hasCar);
		person.hasCarLoan.setValue(hasCarLoan);
		person.nativePlacePro.setValue(nativePlacePro);
		person.nativePlaceCity.setValue(nativePlaceCity);
		person.registedPlacePro.setValue(registedPlacePro);
		person.registedPlaceCity.setValue(registedPlaceCity);
		person.address.setValue(address);
		person.telephone.setValue(telephone);
		person.userId.setValue(userId);
		person.idNo.setValue(idNo);
		person.personalHead.setValue(personalHead);
		Long result = -1L;
		Map<String, String> map = null;
		try{
		DataSet PersondataSet = person.open(conn, "", "userId = " + userId, "",
				-1, -1);
		map = BeanMapUtils.dataSetToMap(PersondataSet);
		}catch (Exception e) {
		  e.printStackTrace();
		  return -1L;
		}
		if (map != null && map.size() > 0) {
			
		    String 	realNamestr = map.get("realName");	
            if(realNamestr!=null&&!realNamestr.equals("")){
            	return person.update(conn, "userId = " + userId);	
		    }else{
				// 如果更新的那么更新user表中的步骤状态值
		    	Dao.Tables.t_user  user = new Dao().new Tables().new t_user();
		    	user.authStep.setValue(1);// 2表示填写完基本认证
				result = user.update(conn, " id = " + userId);
				if (result > 0) {
					return person.update(conn, "userId = " + userId);// 如果user表更新成功
				}
				return result;
		    }
		} else {
			// 如果更新的那么更新user表中的步骤状态值
			Dao.Tables.t_user user = new Dao().new Tables().new t_user();
			user.authStep.setValue(1);// 2表示填写完基本认证
			result = user.update(conn, " id = " + userId);
			if (result > 0) {
				return person.insert(conn);// 如果user表更新成功
			}
			return result;
		}
	}
	/**
	 * 
	 * 后台用户基本资料审核
	 * @param conn  
	 * @param userId  用户id
	 * @param auditStatus 审核状态
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseDataCheck(Connection conn, Long userId,
			int auditStatus) throws SQLException, DataException {
		Long result = -1L;
		Integer personauditStatus = -1;
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();
		Map<String, String> Personmap = new HashMap<String, String>();
		try{
		DataSet personds = person.open(conn, "auditStatus", " userId = "
				+ userId, "", -1, -1);
		Personmap = BeanMapUtils.dataSetToMap(personds);
		}catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		Integer precreditrating = -1;// 原来的信用积分
		Map<String, String> map = new HashMap<String, String>();
		DataSet ds = null;
		Map<String, String> wormap = null;
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		try{
		ds = user.open(conn, "creditrating", " id = " + userId, "", -1,
				-1);
		map = BeanMapUtils.dataSetToMap(ds);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		if (map != null && map.size() > 0) {
			precreditrating = Convert.strToInt(map.get("creditrating"), -1);
		}
	
		
		if (auditStatus == 3){
			user.creditrating.setValue(10 + precreditrating);
			result = user.update(conn, " id = " + userId);// 更新用户的信用积分
			result = addserintegraldetail(conn, userId,10, 
					"用户基本资料审核",1, "用户基本资料审核", "增加");
			if(result<=0){
				return  -1L;
			}
		}else if (auditStatus == 2){
			user.creditrating.setValue(precreditrating - 10);
			result = user.update(conn, " id = " + userId);// 更新用户的信用积分
			result = addserintegraldetail(conn, userId, 10,
					"用户基本资料审核",1, "用户基本资料审核", "减少");
			if(result<=0){
				return  -1L;
			}
		}
			
		person.auditStatus.setValue(auditStatus);
		return person.update(conn, "userId = " + userId);
	}
	
	
	/**
	 * 
	 * 后台企业用户基本资料审核
	 * @param conn  
	 * @param userId  用户id
	 * @param auditStatus 审核状态
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserBaseDataCheck2(Connection conn, Long userId,
			int auditStatus) throws SQLException, DataException {
		Long result = -1L;
		Dao.Tables.t_orginfo orginfo = new Dao().new Tables().new t_orginfo();
		Map<String, String> Personmap = new HashMap<String, String>();
		try{
		DataSet personds = orginfo.open(conn, "auditStatus", " user_id = "
				+ userId, "", -1, -1);
		Personmap = BeanMapUtils.dataSetToMap(personds);
		}catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		Integer precreditrating = -1;// 原来的信用积分
		Map<String, String> map = new HashMap<String, String>();
		DataSet ds = null;
		Map<String, String> wormap = null;
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		try{
		ds = user.open(conn, "creditrating", " id = " + userId, "", -1,
				-1);
		map = BeanMapUtils.dataSetToMap(ds);
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		if (map != null && map.size() > 0) {
			precreditrating = Convert.strToInt(map.get("creditrating"), -1);
		}
		if (auditStatus == 3){
			user.creditrating.setValue(10 + precreditrating);
			result = user.update(conn, " id = " + userId);// 更新用户的信用积分
			result = addserintegraldetail(conn, userId,10, 
					"用户基本资料审核",1, "用户基本资料审核", "增加");
			if(result<=0){
				return  -1L;
			}
		}else if (auditStatus == 2){
			user.creditrating.setValue(precreditrating - 10);
			result = user.update(conn, " id = " + userId);// 更新用户的信用积分
			result = addserintegraldetail(conn, userId, 10,
					"用户基本资料审核",1, "用户基本资料审核", "减少");
			if(result<=0){
				return  -1L;
			}
		}
		orginfo.auditStatus.setValue(auditStatus);
		return orginfo.update(conn, "user_id = " + userId);
	}
	
	
	
	/**
	 * 向积分记录表添加记录
	 * @param conn
	 * @param userId
	 * @param score
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addserintegraldetail(Connection conn,Long userId,Integer score,String typeStr,Integer type,String remark,String changetype) throws SQLException, DataException{
		Dao.Tables.t_userintegraldetail  integraldetail = new Dao().new Tables().new t_userintegraldetail();
		integraldetail.changerecore.setValue(score);
		integraldetail.intergraltype.setValue(typeStr);
		integraldetail.remark.setValue(remark);
		integraldetail.changetype.setValue(changetype);//先设置成增加
		integraldetail.time.setValue(new Date());
		integraldetail.userid.setValue(userId);
		if(type==1){//信用积分
			integraldetail.type.setValue(1);
		}
		if(type==2){//vip积分
			integraldetail.type.setValue(2);
		}
		
		return  integraldetail.insert(conn);
	}
	
	

	/**
	 * 
	 * 添加用户的工作认证资料
	 * 
	 * @param conn
	 * @param orgName
	 * @param occStatus
	 * @param workPro
	 * @param workCity
	 * @param companyType
	 * @param companyLine
	 * @param companyScale
	 * @param job
	 * @param monthlyIncome
	 * @param workYear
	 * @param companyTel
	 * @param workEmail
	 * @param companyAddress
	 * @param directedName
	 * @param directedRelation
	 * @param directedTel
	 * @param otherName
	 * @param otherRelation
	 * @param otherTel
	 * @param moredName
	 * @param moredRelation
	 * @param moredTel
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Long addUserWorkData(Connection conn, String orgName,
			String occStatus, Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws SQLException {
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		workauth.orgName.setValue(orgName);
		workauth.occStatus.setValue(occStatus);
		workauth.workPro.setValue(workPro);
		workauth.workCity.setValue(workCity);
		workauth.companyType.setValue(companyType);
		workauth.companyLine.setValue(companyLine);
		workauth.companyScale.setValue(companyScale);
		workauth.job.setValue(job);
		workauth.monthlyIncome.setValue(monthlyIncome);
		workauth.workYear.setValue(workYear);
		workauth.companyTel.setValue(companyTel);
		workauth.workEmail.setValue(workEmail);
		workauth.companyAddress.setValue(companyAddress);
		workauth.directedName.setValue(directedName);
		workauth.directedRelation.setValue(directedRelation);
		workauth.directedTel.setValue(directedTel);
		workauth.otherName.setValue(otherName);
		workauth.otherRelation.setValue(otherName);
		workauth.otherTel.setValue(otherTel);
		workauth.moredName.setValue(moredName);
		workauth.moredRelation.setValue(moredRelation);
		workauth.moredTel.setValue(moredTel);
		workauth.userId.setValue(userId);
		return workauth.insert(conn);
	}

	/**
	 * 修改用户工作认证资料
	 * 
	 * @param conn
	 * @param orgName
	 * @param occStatus
	 * @param workPro
	 * @param workCity
	 * @param companyType
	 * @param companyLine
	 * @param companyScale
	 * @param job
	 * @param monthlyIncome
	 * @param workYear
	 * @param companyTel
	 * @param workEmail
	 * @param companyAddress
	 * @param directedName
	 * @param directedRelation
	 * @param directedTel
	 * @param otherName
	 * @param otherRelation
	 * @param otherTel
	 * @param moredName
	 * @param moredRelation
	 * @param moredTel
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateUserWorkData(Connection conn, String orgName,
			String occStatus, Long workPro, Long workCity, String companyType,
			String companyLine, String companyScale, String job,
			String monthlyIncome, String workYear, String companyTel,
			String workEmail, String companyAddress, String directedName,
			String directedRelation, String directedTel, String otherName,
			String otherRelation, String otherTel, String moredName,
			String moredRelation, String moredTel, Long userId)
			throws SQLException, DataException {
		Dao.Tables.t_workauth workauth = new Dao().new Tables().new t_workauth();
		workauth.orgName.setValue(orgName);
		workauth.occStatus.setValue(occStatus);
		workauth.workPro.setValue(workPro);
		workauth.workCity.setValue(workCity);
		workauth.companyType.setValue(companyType);
		workauth.companyLine.setValue(companyLine);
		workauth.companyScale.setValue(companyScale);
		workauth.job.setValue(job);
		workauth.monthlyIncome.setValue(monthlyIncome);
		workauth.workYear.setValue(workYear);
		workauth.companyTel.setValue(companyTel);
		workauth.workEmail.setValue(workEmail);
		workauth.companyAddress.setValue(companyAddress);
		workauth.directedName.setValue(directedName);
		workauth.directedRelation.setValue(directedRelation);
		workauth.directedTel.setValue(directedTel);
		workauth.otherName.setValue(otherName);
		workauth.otherRelation.setValue(otherRelation);
		workauth.otherTel.setValue(otherTel);
		workauth.moredName.setValue(moredName);
		workauth.moredRelation.setValue(moredRelation);
		workauth.moredTel.setValue(moredTel);
		DataSet PersondataSet = workauth.open(conn, "", "userId = " + userId,
				"", -1, -1);
		Map<String, String> map = new HashMap<String, String>();
		try{
		map = BeanMapUtils.dataSetToMap(PersondataSet);}
		catch (Exception e) {
			e.printStackTrace();
			return -1L;
		}
		if (map != null && map.size() > 0) {
			return workauth.update(conn, "userId = " + userId);
		} else {
			workauth.userId.setValue(userId);
			return workauth.insert(conn);

		}

	}

	/**
	 * 修改用户
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @param email
	 *            电子邮箱
	 * @param userName
	 *            用户名
	 * @param password
	 *            用户密码
	 * @param name
	 *            真实姓名
	 * @param gender
	 *            性别
	 * @param mobilePhone
	 *            手机号码
	 * @param qq
	 * @param provinceId
	 *            省Id
	 * @param cityId
	 *            城市id
	 * @param areaId
	 *            区/镇/县id
	 * @param postalcode
	 *            邮政编码
	 * @param headImg
	 *            头像
	 * @param status
	 *            邮箱是否验证通过 (0:未通过1:通过)
	 * @param balances
	 *            E币账户余额
	 * @param enable
	 *            是否禁用 1、启用 2、禁用
	 * @param rating
	 *            会员等级(1:普通会员2:铜牌会员3:银牌会员4:金牌会员)
	 * @param lastDate
	 *            最后登录时间
	 * @param lastIP
	 *            最后登录ip
	 * @throws SQLException
	 * @return Long
	 */
	public Long updateUser(Connection conn, Long id, String email,
			String userName, String password, String lastDate, String lastIP)
			throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (email != null) {
			user.email.setValue(email);
		}
		if (password != null) {
			password = com.shove.security.Encrypt.MD5(password.trim());
			user.password.setValue(password);
		}
		if (userName != null) {
			user.username.setValue(userName);
		}

		return user.update(conn, " id=" + id);
	}

	// 更新用户的认证状态
	public long updateUser(Connection conn, long userId,
			Map<String, String> userMap) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DBReflectUtil.mapToTableValue(user, userMap);

		return user.update(conn, "id=" + userId);
	}
	// 更新用户的申请密码保护状态 默认是1，表示还没有申请，2表示已经申请
	public Long updatePwdProState(Connection conn, Long userId)
			throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.isApplyPro.setValue(2);
		return user.update(conn, "id = " + userId);

	}

	// 更新用户的vip状态
	public Long updateUser(Connection conn, Long uerId, int authStep,
			int vipStatus, int servicePersonId, String content, String vipFee)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.authStep.setValue(authStep);
		user.kefuId.setValue(servicePersonId);
		user.content.setValue(content);
		user.vipCreateTime.setValue(new Date());
		user.vipStatus.setValue(2);// 修改vip状态 2为vip状态
		BigDecimal vipFeedecimal = new BigDecimal(vipFee);
		user.vipFee.setValue(vipFeedecimal);
		//----modify by houli 申请vip时，feeStatus=1 1为代扣  2为已扣
		//user.feeStatus.setValue(2);//为扣费用
		user.feeStatus.setValue(1);
		//---------------
		return user.update(conn, "id = " + uerId);
	}

	public int deleteUser(Connection conn, String userIds, String delimiter)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.delete(conn, "id=" + userIds);
		return 0;
	}

	/**
	 * 判断是否用户或者email重复
	 * 
	 * @param conn
	 * @param email
	 * @param userName
	 * @throws SQLException
	 * @throws DataException
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> isUserEmaiORUseName(Connection conn,
			String email, String userName) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		email=com.shove.web.Utility.filteSqlInfusion(email);
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		condition.append(" 1=1");
		if (StringUtils.isNotBlank(email)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName= '" + StringEscapeUtils.escapeSql(userName) + "'");
		}

		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	//重载判断唯一性 用户、email、企业用户名、电话号码
	public List<Map<String, Object>> isUserEmaiORUseName(Connection conn,
			String email, String userName,String orgName,String phone) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		email=com.shove.web.Utility.filteSqlInfusion(email);
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		condition.append(" 1=1");
		if (StringUtils.isNotBlank(email)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "'");
		}else if (StringUtils.isNotBlank(phone)){
			condition.append(" AND mobilePhone= '" + StringEscapeUtils.escapeSql(phone) + "'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName= '" + StringEscapeUtils.escapeSql(userName) + "'");
		}
		if (StringUtils.isNotBlank(orgName)) {
			condition.append(" AND orgName= '" + StringEscapeUtils.escapeSql(orgName) + "'");
		}
		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 判断t_person表中，电话号码唯一性
	 * @param conn
	 * @param phone
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> isExistPersonByPhone(Connection conn,
			String phone) throws SQLException, DataException {
		Dao.Tables.t_person user = new Dao().new Tables().new t_person();
		StringBuffer condition = new StringBuffer();
		condition.append(" 1=1");
		if (StringUtils.isNotBlank(phone)){
			condition.append(" AND cellPhone= '" + StringEscapeUtils.escapeSql(phone) + "'");
		}
		
		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	// ===============================================================
	/**
	 * 用户登录时候验证邮箱和用户名是否激活==========================
	 */
	public List<Map<String, Object>> isUEjihuo(Connection conn, String email,
			String userName) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		email=com.shove.web.Utility.filteSqlInfusion(email);
		condition.append(" 1=1");
		// 邮箱不空 用户名空
		if (StringUtils.isNotBlank(email) && !StringUtils.isNotBlank(userName)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "' AND enable = 2");
			// 邮箱和用户名都不空
		} else if (StringUtils.isNotBlank(email)
				&& StringUtils.isNotBlank(userName)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "'");
		}
		// 邮箱空 用户名不空
		if (!StringUtils.isNotBlank(email) && StringUtils.isNotBlank(userName)) {
			condition
					.append(" AND userName= '" + StringEscapeUtils.escapeSql(userName) + "' AND enable = 2");
		}

		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	// 没有检测enable状态
	public List<Map<String, Object>> isUEjihuo_(Connection conn, String email,
			String userName) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		StringBuffer condition = new StringBuffer();
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		email=com.shove.web.Utility.filteSqlInfusion(email);
		condition.append(" 1=1");
		if (StringUtils.isNotBlank(email)) {
			condition.append(" AND email= '" + StringEscapeUtils.escapeSql(email) + "'");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append(" AND userName= '" + StringEscapeUtils.escapeSql(userName) + "' or mobilePhone = '" + StringEscapeUtils.escapeSql(userName) + "'");
		}
		

		DataSet dataSet = user
				.open(conn, "*", condition.toString(), "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		condition = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 用户登录时候验证邮箱和用户名是否激活==========================
	 */
	// ======================================================
	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @throws SQLException
	 * @throws DataException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "*", " id=" + id, " id desc ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 根据用户name查询用户信息
	 * 
	 * @param conn
	 * @param userName
	 *            用户姓名
	 * @throws SQLException
	 * @throws DataException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserByUserName(Connection conn, String userName)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "*", " username = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or email = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or mobilePhone = '" + StringEscapeUtils.escapeSql(userName) + "'", " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 查询用户前台的图片信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserPictureStatus(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_user_frontpictur frontMeg = new Dao().new Views().new v_t_user_frontpictur();
		DataSet dataSet = frontMeg.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询用户前台五大基本资料信息和显示详细图片的第一张
	 * 
	 * @param conn
	 * @param id
	 *            用户的id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryBasePicture(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tuser.username username, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql.append(" left join t_user tuser on tuser.id = tm.userId ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.userType=1 AND tmy.isMust=2 AND  tuser.id =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询用户前台可选大基本资料信息和显示详细图片的第一张
	 * 
	 * @param conn
	 * @param id
	 *            用户的id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querySelectPicture(Connection conn, Long id)
			throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tm.userId as id, ");
		sql.append(" tuser.username username, ");
		sql.append(" tmy.`name` as tmyname, ");
		sql
				.append(" tm.auditStatus as auditStatus,tm.id as tmid,tmy.id as tmyid,vts.imagePath as imagePath ");
		sql.append(" from t_materialsauth tm  ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId  = tmy.id   ");
		sql.append(" left join t_user tuser on tuser.id = tm.userId ");
		sql
				.append(" left join v_t_user_showfirstpicture vts on vts.tmid = tm.id ");
		sql.append(" where tmy.userType=1 AND tmy.isMust=1 AND  tuser.id =  " + id);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询每一个证件类型下的明细
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryPerTyhpePicture(Connection conn,
			Long tmid) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" tmd.id as id,tmd.imagePath, ");
		sql
				.append(" tmd.auditStatus as auditStatus ,tmy.`name` as tmyname,tm.materAuthTypeId as materAuthTypeId,tmd.visiable as visiable  ");
		sql.append(" from t_materialimagedetal tmd ");
		sql
				.append(" left join t_materialsauth tm on  tmd.materialsauthid = tm.id ");
		sql
				.append(" left join t_materialsauthtype tmy on tm.materAuthTypeId = tmy.id ");
		sql.append(" where tmd.materialsauthid =  " + tmid);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		dataSet.tables.get(0).rows.genRowsMap();
		sql = null;
		return dataSet.tables.get(0).rows.rowsMap;
	}

	/**
	 * 查询图片类型
	 * 
	 * @param conn
	 * @param tmId
	 *            证件主表类型的id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPitcturTyep(Connection conn, Long tmId,
			Long userId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select tm.materAuthTypeId  from t_materialsauth tm  where tm.userId = "
						+ userId + " and tm.id = " + tmId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 计算用户的图片上传个数基本资料的上传个数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPicturStatuCount(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_user_verypictur verypictur = new Dao().new Views().new v_t_user_verypictur();
		DataSet dataSet = verypictur.open(conn, "", " id=" + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 根据用户名查用户id
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryIdByUser(Connection conn, String userName)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		DataSet dataSet = user.open(conn, "id", " username = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or email = '" + StringEscapeUtils.escapeSql(userName) + "'" +
				" or mobilePhone = '" + StringEscapeUtils.escapeSql(userName) + "'",
				"", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 查询图片id
	 * 
	 * @param conn
	 * @param tmId
	 *            证件主表类型的id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPitcturId(Connection conn, Long tmId,
			Long userId) throws SQLException, DataException {
		StringBuffer sql = new StringBuffer();
		sql
				.append("select tm.id as id from t_materialsauth tm  where tm.userId = "
						+ userId + " and tm.materAuthTypeId = " + tmId);
		DataSet dataSet = MySQL.executeQuery(conn, sql.toString());
		sql = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 忘记密码
	 * 
	 * @param conn
	 * @param username
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPassword(Connection conn, String email)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		email=com.shove.web.Utility.filteSqlInfusion(email);
		DataSet dataSet = user.open(conn, "", " email = '" + StringEscapeUtils.escapeSql(email) + "'", "",
				-1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 客服列表的内容
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> querykefylist(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_user_kefu kefu = new Dao().new Tables().new t_user_kefu();
		DataSet dataSet = kefu.open(conn, "", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 所有密码安全提问的内容
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAllQuestionList(Connection conn)
			throws SQLException, DataException {
		Dao.Tables.t_pwd_requestion question = new Dao().new Tables().new t_pwd_requestion();
		DataSet dataSet = question.open(conn, "", "", "", -1, -1);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	
	
	
	
	/**
	 * 用户基本信息
	 * 根据id查询个人信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPersonById(Connection conn, long id)
			throws SQLException, DataException {
	    Dao.Tables.t_person person = new Dao().new Tables().new t_person();
        DataSet dataSet = person.open(conn, "*", "userId = " + id, "", -1, -1);
        return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 企业基本信息
	 * 根据id查询企业信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryOrgById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_orginfo org = new Dao().new Tables().new t_orginfo();
		DataSet dataSet = org.open(conn, "", "user_id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	
	/**
	 * 用户基本信息
	 * 根据id查询企业信息
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryPersonById2(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_orginfo orginfo = new Dao().new Views().new v_t_orginfo();
		DataSet dataSet = orginfo.open(conn, "", "userId = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
		/**
	 * 根据t_userId查询企业用户详细信息 
	 * @param conn
	 * @param id t_userId
	 * @return 企业用户详细信息 
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryOrgInfoById(Connection conn, long id)
	throws SQLException, DataException {
		Dao.Tables.t_orginfo orginfo = new Dao().new Tables().new t_orginfo();
		DataSet dataSet = orginfo.open(conn, "", "user_id = " + id, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);

	}
	
	
	public int queryUserTypeById(Connection conn, long id)
	throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet dataSet = user.open(conn, "", "id = " + id, "", -1, -1);
		int userType=-1;
		if(BeanMapUtils.dataSetToMap(dataSet).size()>0){
		String userTypeStr=BeanMapUtils.dataSetToMap(dataSet).get("userType");
		userType=Integer.parseInt(userTypeStr);
		}
		return userType;

	}
	/**
	 * 查询用户所有密保答案
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryAnswerByUserId(Connection conn, long userId)
			throws SQLException, DataException {
		Dao.Tables.t_pwd_answer answer = new Dao().new Tables().new t_pwd_answer();
		DataSet dataSet = answer.open(conn, "", "userId = " + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 查询用户的工作状态值
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryWorkState(Connection conn, long id)
			throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"select COUNT(*) as 'total'  from t_materialsauth tm where tm.userId = "
								+ id
								+ " AND tm.auditStatus = 3  AND tm.materAuthTypeId > 5");
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 查询vip页面状态参数
	 * 
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryVipParamList(Connection conn, long id)
			throws SQLException, DataException {
		DataSet dataSet = MySQL
				.executeQuery(
						conn,
						"SELECT tuk.id as kfid, tuser.id as id ,tuser.username as username,tuser.email as email,tuser.vipStatus as vipStatus,tuser.usableSum as usableSum,tp.realName as realName,tuk.`name` as kefuname from t_user tuser LEFT join t_person tp on tuser.id = tp.userId left join t_user_kefu tuk on tuser.kefuId = tuk.id where tuser.id = "
								+ id);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**
	 * 该用户上传资料的类型的审核状态
	 * 
	 * @throws DataException
	 * @throws SQLException
	 */
	public Long updateUserPicturStatus(Connection conn, Long id,
			Long materAuthTypeId) throws SQLException, DataException {
		Dao.Tables.t_materialsauth materialsauth = new Dao().new Tables().new t_materialsauth();
			materialsauth.auditStatus.setValue(1);// 设置主表的状态值1 为等待审核
			materialsauth.passTime.setValue(new Date());
			return materialsauth.update(conn, " userId = " + id
					+ " AND materAuthTypeId = " + materAuthTypeId);
	}

	/**
	 * 增加用户的图片
	 * 
	 * @param conn
	 * @param id
	 * @param pasttime
	 * @param materAuthTypeId
	 * @param auditStatus
	 * @param uploadingTime
	 * @param imagePath
	 * @param materialsauthid
	 * @return
	 * @throws SQLException
	 */
	public synchronized Long  addUserImage(Connection conn, Integer auditStatus,
			String uploadingTime, String imagePath, Long materialsauthid,Integer visable)
			throws SQLException {
		Dao.Tables.t_materialimagedetal materialImagedetal = new Dao().new Tables().new t_materialimagedetal();
		materialImagedetal.auditStatus.setValue(auditStatus);
		materialImagedetal.imagePath.setValue(imagePath);
		materialImagedetal.visiable.setValue(visable);
		materialImagedetal.uploadingTime.setValue(new Date());
		materialImagedetal.materialsauthid.setValue(materialsauthid);
		return materialImagedetal.insert(conn);
	}
	
	
	public Map<String,String> queryByMaterialIsOk(Connection conn,long userId,String types) throws DataException{
		StringBuilder command = new StringBuilder();
		command.append("SELECT COUNT(*) as ccc FROM t_materialsauth WHERE userId="+userId+" AND materAuthTypeId IN ("+types+") AND auditStatus=1 ");
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		return map;
	}

	/**
	 * 重置图片的可见性 重置为不可见
	 * 
	 * @param conn
	 * @param tmid
	 * @return
	 * @throws SQLException
	 */
	public Long updatematerialImagedetalvisiable(Connection conn, Long tmid)
			throws SQLException {
		Dao.Tables.t_materialimagedetal materialImagedetal = new Dao().new Tables().new t_materialimagedetal();
		materialImagedetal.visiable.setValue(2);
		return materialImagedetal.update(conn, " materialsauthid = " + tmid);
	}

	/**
	 * 更新用户上传图片的可见性
	 * 
	 * @param conn
	 * @param tmdid
	 * @return
	 * @throws SQLException
	 */
	public Long updatevisiable(Connection conn, Long tmdid) throws SQLException {
		Dao.Tables.t_materialimagedetal Imagedetal = new Dao().new Tables().new t_materialimagedetal();
		Imagedetal.visiable.setValue(1);// 1为可见 2 为不可见
		return Imagedetal.update(conn, " id = " + tmdid);
	}

	/**
	 * 修改用户邮箱验证状态
	 * 
	 * @param conn
	 * @param id
	 *            用户编号
	 * @param status
	 *            邮箱是否验证通过 (0:未通过1:通过)
	 * @throws SQLException
	 * @return Long
	 */
	public Long updateUserEmailStatus(Connection conn, Long id, Integer status)
			throws SQLException {

		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (status != null) {
			// user.status.setValue(status);
		}
		return user.update(conn, " id=" + id);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param conn
	 * @param id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserPassword(Connection conn, Long id, String password)
			throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if ("1".equals(IConstants.ENABLED_PASS)){
			password = com.shove.security.Encrypt.MD5(password.trim());
		}else{
			password = com.shove.security.Encrypt.MD5(password.trim()+IConstants.PASS_KEY);
		}
		
		try {
            Map<String, String> map =  queryUserById(conn, id);
            String dealpwd = map.get("dealpwd");
            if (password.equals(dealpwd)) {//如果登录密码等于交易密码
                return 10L;
            }
        }
        catch (DataException e) {
            e.printStackTrace();
            return -1L;
        }
		
		user.password.setValue(password);
		return user.update(conn, " id=" + id);
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
	public Long updateEnable(Connection conn, Long id, Integer enable)
			throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if (enable != null) {
			user.enable.setValue(enable);
		}
		return user.update(conn, " id=" + id);

	}
	/**
	 * 
	 * 修改用户认证步骤
	 * @param conn
	 * @param id
	 * @param authStep
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updateAuthStepByid(Connection conn, Long id,int authStep)
		throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		long result=-1;
		if (authStep >1) {
			user.authStep.setValue(authStep);
			result=user.update(conn, " id=" + id);
		}
		return result;
	
	}

	/**
	 * 更新锁定用户状态
	 * 
	 * @param conn
	 * @param ids
	 * @param i
	 * @return
	 * @throws SQLException
	 */
	public Long updateLockedStatus(Connection conn, String ids, int enable)
			throws SQLException {
		ids=com.shove.web.Utility.filteSqlInfusion(ids);
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.enable.setValue(enable);
		if (enable == 1) {
			user.lockTime.setValue(null);
		} else {
			user.lockTime.setValue(new Date());
		}
		return user.update(conn, " id in (" +idSQL
				+ ")");
	}

	/**
	 * 更新锁定提现状态
	 * 
	 * @param conn
	 * @param ids
	 * @param cashStatus
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserCashStatus(Connection conn, String ids,
			String cashStatus) throws SQLException {
		ids=com.shove.web.Utility.filteSqlInfusion(ids);
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.cashStatus.setValue(StringEscapeUtils.escapeSql(cashStatus));
		return user.update(conn, " id in (" + idSQL
				+ ")");
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param conn
	 * @param userNames
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	public List<Map<String, Object>> queryUserIdByUserName(Connection conn,
			String userNames) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		userNames = userNames.replaceAll("’", "");
		String[] userName = userNames.split(",");
		StringBuilder newUserName = new StringBuilder();
		for (String name : userName) {
			newUserName.append("'");
			newUserName.append(name);
			newUserName.append("',");
		}
		int length = newUserName.length();
		if (length > 0) {
			newUserName.delete(length - 1, length);
		}
		if (StringUtils.isNotBlank(userNames)) {
			DataSet ds = user.open(conn, "id,username", " username in ("
					+ newUserName.toString() + ")", "", -1, -1); 
			ds.tables.get(0).rows.genRowsMap();
			return ds.tables.get(0).rows.rowsMap;
		}
		return new ArrayList<Map<String, Object>>();
	}

	public List<Map<String, Object>> queryUserAll(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet ds = user.open(conn, "userName,email ,password ", "", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	/**
	 * 判断用户是否为VIP
	 * @param conn
	 * @param userId
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public boolean isVip(Connection conn, long userId) throws SQLException, DataException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		DataSet ds = user.open(conn, "  vipStatus  ", " id="+userId, "", -1, -1);
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		boolean isVip = false;
		if(map != null){
			long vipStatus = Convert.strToLong(map.get("vipStatus"), -1);
			if(vipStatus == 2){
				isVip = true;
			}
		}
		return isVip;
	}
	
	/**
	 * @MethodName: updateInvestorSum
	 * @Param: FrontMyPaymentDao
	 * @Author: gang.lv
	 * @Date: 2013-4-26 下午05:01:09
	 * @Return:
	 * @Descb: 给用户添加可用资金
	 * @Throws:
	 */
	public long addUserUsableAmount(Connection conn, double amount, long userId)
			throws SQLException {
		long returnId = -1;
		String command = "update t_user set usableSum = usableSum + " + amount
				+ " where id =" + userId;
		returnId = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		return returnId;
	}
	
	
	public Map<String, String> queryUserByUserAndPwd(Connection conn,
			String userName, String pwd) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		pwd=com.shove.web.Utility.filteSqlInfusion(pwd);
		userName=com.shove.web.Utility.filteSqlInfusion(userName);
		DataSet ds = t_user.open(conn,
				"id,username,headImg,enable,vipStatus,email,authStep,usableSum,freezeSum,isLoginLimit ",
				" (email ='" + StringEscapeUtils.escapeSql(userName.trim())
						+ "' OR username='"
						+ StringEscapeUtils.escapeSql(userName.trim())
						+ "' or mobilePhone='"+ StringEscapeUtils.escapeSql(userName.trim())
						+"') AND password = '" + StringEscapeUtils.escapeSql(pwd)
						+ "' AND enable != 2", "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 * 更改登录密码
	 * @param conn
	 * @param id
	 * @param password
	 * @param type 1为登录密码，2为交易密码
	 * @return
	 * @throws SQLException
	 */
	public long updatePwd(Connection conn, Long id, String password,long type) throws SQLException {
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		if(type == 1){
			user.password.setValue(password);
		}else if(type == 2){
			user.dealpwd.setValue(password);
		}else{
			return 0;
		}
		
		return user.update(conn, " id=" + id);
	}
	
	public long queryUserIdByPhone(Connection conn, String cellPhone) throws SQLException, DataException {
		StringBuilder command = new StringBuilder();
		cellPhone=com.shove.web.Utility.filteSqlInfusion(cellPhone);
		command.append("select u.id from t_user u left JOIN t_person p on p.userId=u.id ");
		command.append(" LEFT JOIN t_phone_binding_info pb on pb.userId=u.id where u.mobilePhone = '");
		command.append(StringEscapeUtils.escapeSql(cellPhone));
		command.append("' or pb.mobilePhone='");
		command.append(StringEscapeUtils.escapeSql(cellPhone));
		command.append("' or p.cellPhone='");
		command.append(StringEscapeUtils.escapeSql(cellPhone));
		command.append("' ");
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		long userId = -1;
		if(map != null){
			userId = Convert.strToLong(map.get("id"), -1);
		}
		return userId;
	}
	public Map<String,String> queryUserBindphone(Connection conn,long userId) throws DataException{
		StringBuilder command = new StringBuilder();
		command.append("select u.id as userId ,u.mobilePhone as mobilePhone,p.cellPhone as cellPhone,pb.status as status,pb.mobilePhone as bindingPhone from t_user u left JOIN t_person p on p.userId=u.id ");
		command.append(" LEFT JOIN t_phone_binding_info pb on pb.userId=u.id where u.id = "+userId);
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		Map<String,String> map = BeanMapUtils.dataSetToMap(ds);
		return map;
	}
	
//	public List<Map<String, Object>> isPhoneExist(Connection conn,
//			String cellphone) throws SQLException, DataException {
//		
//		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
//		StringBuffer condition = new StringBuffer();
//		cellphone=com.shove.web.Utility.filteSqlInfusion(cellphone);
//		condition.append(" 1=1 ");
//		if (StringUtils.isNotBlank(cellphone)) {
//			condition.append("AND mobilePhone= '"+ StringEscapeUtils.escapeSql(cellphone.trim())+ "' or username= '"+ StringEscapeUtils.escapeSql(cellphone.trim())+ "' AND enable = 1 ");
//		}
//		DataSet dataSet = user
//				.open(conn, "*", condition.toString(), "", -1, -1);
//		dataSet.tables.get(0).rows.genRowsMap();
//		return dataSet.tables.get(0).rows.rowsMap;
//	}
	
    public List<Map<String, Object>> isPhoneExist(Connection conn, String cellphone) throws SQLException, DataException {
        Dao.Tables.t_person person = new Dao().new Tables().new t_person();
        DataSet dataSet = person.open(conn, "*", " cellPhone = '"+ StringEscapeUtils.escapeSql(cellphone.trim())+ "' ", "", -1, -1);
        dataSet.tables.get(0).rows.genRowsMap();
        return dataSet.tables.get(0).rows.rowsMap;
    }
	
//    public List<Map<String, Object>> isPhoneExist(Connection conn, 
//    String cellphone) throws SQLException, DataException { 
//    DataSet dataSet = MySQL.executeQuery(conn," select u.id,u.username from t_user u left join t_person p on u.id = p.userId where p.cellPhone = "  + StringEscapeUtils.escapeSql(cellphone.trim()) + "  and u.enable = 1"); 
//    dataSet.tables.get(0).rows.genRowsMap(); 
//    return dataSet.tables.get(0).rows.rowsMap; 
//    }
	
	// 通过手机更改用户手机号码
	public Long updatepasswordBycellphone(Connection conn, String cellphone,
			String password) throws SQLException {
		cellphone=com.shove.web.Utility.filteSqlInfusion(cellphone);
		password=com.shove.web.Utility.filteSqlInfusion(password);
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		user.password.setValue(com.shove.security.Encrypt.MD5(password.trim()+IConstants.PASS_KEY));
		return user.update(conn, " mobilePhone = " + StringEscapeUtils.escapeSql(cellphone.trim()));
	}
	
	public Map<String, String> queryUserAmount(Connection conn, Long userId)
	throws DataException, SQLException {
	//DataSet dataSet = MySQL.executeQuery(conn," select sum(usableSum+presrent) as usableSum from t_user where id = " + userId);
	DataSet dataSet = MySQL.executeQuery(conn," select usableSum as usableSum from t_user where id = " + userId);
	return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String, String> queryPresrent(Connection conn, Long userId)
			throws DataException, SQLException {
			DataSet dataSet = MySQL.executeQuery(conn," select presrent,usableSum  from t_user where id = " + userId);
			return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public long updatePresrent(Connection conn,long id,double money) throws SQLException{
		return MySQL.executeNonQuery(conn, " update t_user set t_user.presrent =t_user.presrent- "+money+" where id = "+id);
	}
	
	/**
	 * 更改邮箱byid
	 * @throws SQLException 
	 */
	public long updateEmalByid(Connection conn,long id,String email) throws SQLException{
		email=com.shove.web.Utility.filteSqlInfusion(email);
		return MySQL.executeNonQuery(conn, " update t_user set t_user.email = '"+StringEscapeUtils.escapeSql(email)+"' where id = "+id);
	}
	/**
	 * @MethodName: deducteUserUsableAmount
	 * @Param: UserDao
	 * @Author: gang.lv
	 * @Date: 2013-5-22 上午11:57:35
	 * @Return:
	 * @Descb: 扣除用户可用资金
	 * @Throws:
	 */
	public long deducteUserUsableAmount(Connection conn, double amount,
			long userId) throws SQLException {
		StringBuffer command = new StringBuffer();
		command.append("update t_user set usableSum = usableSum - " + amount
				+ " where id =" + userId);
		return MySQL.executeNonQuery(conn, command.toString());
	}
	/**
	 * @MethodName: queryUserAmountAfterHander
	 * @Param: BorrowManageDao
	 * @Author: gang.lv
	 * @Date: 2013-4-21 下午09:11:22
	 * @Return:
	 * @Descb: 查询用户 的资金
	 * @Throws:
	 */
	public Map<String, String> queryUserAmountAfterHander(Connection conn,
			long userId) throws SQLException, DataException {
		String command ="select a.usableSum,a.freezeSum,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) forPI "
		+" from t_user a left join t_invest b on a.id = b.investor where a.id="
		+ userId + " group by a.id";
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 激活账户
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public long updateUserActivate(Connection conn, long userId) throws SQLException{
		 Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		 t_user.enable.setValue(1);
		 return t_user.update(conn, " id = "+ userId);
	}


	public long setSign(Connection conn, long userId, String sign, String sign2) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.sign.setValue(sign);
		t_user.sign2.setValue(sign2);
		return t_user.update(conn, " id = "+ userId);
	}

	public Map<String, String> queryMaxIdFundById(Connection conn, long userId)  throws DataException, SQLException  {
		DataSet dataSet = MySQL.executeQuery(conn," select * from t_fundrecord where id = (select max(id) from t_fundrecord where userId = "+ userId+")" );
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public List<Map<String, Object>> queryVipUser(Connection conn) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet ds = t_user.open(conn, "id", "(vipStatus = 2 or vipStatus = 3) and feeStatus =1 and usableSum>vipFee", "", -1, -1);
		ds.tables.get(0).rows.genRowsMap();
		return ds.tables.get(0).rows.rowsMap;
	}
	/**
	 * 重置用户状态，即设置用户是否限制登录和设置用户错误登录次数
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long resetUserState(Connection conn,int loginErrorCount,int isLoginLimit,long userId) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.loginErrorCount.setValue(loginErrorCount);
		t_user.isLoginLimit.setValue(isLoginLimit);
		return t_user.update(conn, " id = "+ userId);
	}
	/**
	 * 设置用户登录错误次数和登陆时间
	 * @param conn
	 * @param loginErrorCount
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public long updateUserState(Connection conn,int loginErrorCount,String lastDate,long userId) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.loginErrorCount.setValue(loginErrorCount);
		//t_user.lastDate.setValue(lastDate);
		return t_user.update(conn, " id = "+ userId);
	}
	public long updateUserState1(Connection conn,int loginErrorCount,int isLoginLimit,String lastDate,long userId) throws SQLException {
		Dao.Tables.t_user  t_user = new Dao().new Tables().new t_user();
		t_user.loginErrorCount.setValue(loginErrorCount);
		t_user.lastDate.setValue(lastDate);
		t_user.isLoginLimit.setValue(isLoginLimit);
		return t_user.update(conn, " id = "+ userId);
	}
	
	public Map<String, String> queryUserByName(Connection conn, String name) throws DataException {
		StringBuilder command = new StringBuilder();
		command.append("select email from t_user where username = '");
		command.append(StringEscapeUtils.escapeSql(name));
		command.append("' or email = '");
		command.append(StringEscapeUtils.escapeSql(name));
		command.append("' or mobilePhone='");
		command.append(StringEscapeUtils.escapeSql(name));
		command.append("' ");
		DataSet ds = MySQL.executeQuery(conn, command.toString());
		return BeanMapUtils.dataSetToMap(ds);
	}

	
	/**
	 * 根据电话号码查询用户信息
	 * @param conn
	 * @param name
	 * @return
	 * @throws DataException
	 */
	public Map<String, String> queryUserByPhone(Connection conn, String phone) throws DataException {
		StringBuilder command = new StringBuilder();
		DataSet ds = MySQL.executeQuery(conn," select * from t_user where  mobilePhone= '"+ phone+"'" );
		return BeanMapUtils.dataSetToMap(ds);
	}
	
    public Map<String, String> queryUsernameById(Connection conn, Long userId)
    throws DataException, SQLException {
    DataSet dataSet = MySQL.executeQuery(conn," select username from t_user where id = " + userId);
    return BeanMapUtils.dataSetToMap(dataSet);
    }
    
    
    /**
	 * 根据用户id查询用户TOKEN信息
	 * 
	 * @param conn
	 * @param id
	 * @throws SQLException
	 * @throws DataException
	 * @return Map<String,String>
	 */
	public Map<String, String> queryUserTokenById(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Tables.t_user_token usertoken = new Dao().new Tables().new t_user_token();
		DataSet dataSet = usertoken.open(conn, "*", " userid=" + id, "", -1, -1);
		Map<String,String> map = BeanMapUtils.dataSetToMap(dataSet);
		return map;
	}
	
	/**
	 * 根据id更新token信息
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	public long updateUserToken(Connection conn, long userId ,String token ,String checktime) throws SQLException{
		 Dao.Tables.t_user_token  userToken = new Dao().new Tables().new t_user_token();
		 userToken.token.setValue(token);
		 userToken.checktime.setValue(checktime);
		 return userToken.update(conn, " userid = "+ userId);
	}
	
	/**
	 *功能：查询用户信息，包括银行卡
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserInfoById(Connection conn, long id)
			throws SQLException, DataException {
		String command ="SELECT c.*,d.authCardName FROM (SELECT a.*,b.cardNo FROM t_user a LEFT JOIN t_bankcard b ON a.id=b.userId WHERE a.id=" + id + ") c LEFT JOIN t_person d ON c.id=d.userId WHERE c.id=" + id;
			DataSet dataSet = MySQL.executeQuery(conn, command.toString());
			command = null;
			return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 更改手机号byid
	 * @throws SQLException 
	 */
	public long updateSmsByid(Connection conn,long id,String sms,int type) throws SQLException{
		sms=com.shove.web.Utility.filteSqlInfusion(sms);
		MySQL.executeNonQuery(conn, " update t_user set t_user.mobilePhone = '"+StringEscapeUtils.escapeSql(sms)+"' where id = "+id);
		
		//如果是个人用户，修改t_person
		if (type == 1){
			MySQL.executeNonQuery(conn, " update t_person set cellPhone = '"+StringEscapeUtils.escapeSql(sms)+"' where userId = "+id);
		} else {//如果是企业用户，修改t_orginfo
			MySQL.executeNonQuery(conn, " update t_orginfo set linkman_mobile = '"+StringEscapeUtils.escapeSql(sms)+"' where id = "+id);
		}
		
		
		return 2;
	}
	/**
	 * 更改手机号byid
	 * @throws SQLException 
	 */
	public long addSendLog(Connection conn,String phone,String sendType,Long waitTime) throws SQLException{
		if (null == waitTime){
			waitTime = 180L;
		}
		Dao.Tables.t_time_count sendCount = new Dao().new Tables().new t_time_count();
		sendCount.phoneMail.setValue(phone);
		sendCount.createTime.setValue(new Date());
		sendCount.bizType.setValue(sendType);
		sendCount.waitTime.setValue(waitTime);
		return sendCount.insert(conn);
	}
	
	/**
	 * 更改手机号byid
	 * @throws SQLException 
	 */
	public long addSendLog(Connection conn,String phone,String sendType,Long waitTime,String sendCode) throws SQLException{
		if (null == waitTime){
			waitTime = 180L;
		}
		Dao.Tables.t_time_count sendCount = new Dao().new Tables().new t_time_count();
		sendCount.phoneMail.setValue(phone);
		sendCount.createTime.setValue(new Date());
		sendCount.bizType.setValue(sendType);
		sendCount.waitTime.setValue(waitTime);
		sendCount.sendCode.setValue(sendCode);
		return sendCount.insert(conn);
	}
	
	/**
	 * 修改短信日志
	 * @throws SQLException 
	 */
	public long updateSendLog(Connection conn,String phone,String sendType,Long waitTime,String sendCode,long logid) throws SQLException{
		if (null == waitTime){
			waitTime = 180L;
		}
		Dao.Tables.t_time_count sendCount = new Dao().new Tables().new t_time_count();
		sendCount.phoneMail.setValue(phone);
		sendCount.createTime.setValue(new Date());
		sendCount.bizType.setValue(sendType);
		sendCount.waitTime.setValue(waitTime);
		sendCount.sendCode.setValue(sendCode);
		return sendCount.update(conn, " id = "+logid);
	}
	
	/**
	 *功能：查询发送时间是否过期
	 * @param conn
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * use querySendTime(Connection conn, String phoneMail,String sendCode)
	 */
	public Map<String, String> querySendTime(Connection conn, String phoneMail)
			throws SQLException, DataException {
		Dao.Tables.t_time_count sendCt = new Dao().new Tables().new t_time_count();
		phoneMail = com.shove.web.Utility.filteSqlInfusion(phoneMail);
		DataSet dataSet = sendCt.open(conn, " id,phoneMail, waitTime, MAX(createTime) newTime ", " phoneMail = '" + StringEscapeUtils.escapeSql(phoneMail) + "'", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 *功能：查询发送时间是否过期
	 * @param conn
	 * @param phoneMail  手机号码
	 * @param sendCode 验证码 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querySendTime(Connection conn, String phoneMail,String sendCode)
			throws SQLException, DataException {
		Dao.Tables.t_time_count sendCt = new Dao().new Tables().new t_time_count();
		phoneMail = com.shove.web.Utility.filteSqlInfusion(phoneMail);
		DataSet dataSet = sendCt.open(conn, "id,phoneMail, waitTime, MAX(createTime) newTime ", " phoneMail = '" + StringEscapeUtils.escapeSql(phoneMail) + "' and sendCode='"+sendCode+"'", "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 *功能：查询发送时间是否过期
	 * @param conn
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> querySmsOverTime(Connection conn)
			throws SQLException, DataException {
		String command ="SELECT a.introduce smsOverTime FROM t_selecttype a WHERE a.id=24";
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 插入UV IP PV信息
	 * @throws SQLException 
	 */
	public long addUvpLog(Connection conn,Integer pv,Integer uv,Integer ip) throws SQLException{
		Dao.Tables.t_puv_statis puv = new Dao().new Tables().new t_puv_statis();
		puv.ip.setValue(ip);
		puv.uv.setValue(uv);
		puv.pv.setValue(pv);
		puv.createDate.setValue(new Date());
		return puv.insert(conn);
	}
	
	/**
	 *功能：查询今天是否有记录
	 * @return
	 */
	public boolean queryUvpLog(Connection conn){
		DateFormat dft = new SimpleDateFormat(UtilDate.dtShort);
		String today = dft.format(new Date());
		String command ="SELECT * FROM t_puv_statis a WHERE a.createDate = '" + today+ "'";
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		try {
			Map a = BeanMapUtils.dataSetToMap(dataSet);
			if (null == a){
				return false;
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 *功能：更新当天记录
	 * @param conn
	 * @param pv
	 * @param uv
	 * @param ip
	 * @return
	 */
	public Long updateUvpLog(Connection conn,Integer pv,Integer uv,Integer ip){
		DateFormat dft = new SimpleDateFormat(UtilDate._dtShort);
		String today = dft.format(new Date());
		long ret = MySQL.executeNonQuery(conn, " update t_puv_statis set pv =pv+ "+pv+",ip=ip+"+ip+",uv=uv+"+uv+" where createDate = '"+today+"'");
		return ret;
	}
	
	
	/**
	 * 修改默认银行卡，普通用户
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updatedDefaultBankcard(Connection conn, Long id,int defaultBcard)
		throws SQLException, DataException {
		Dao.Tables.t_person user = new Dao().new Tables().new t_person();
		long result=-1;
		if (defaultBcard >1) {
			user.defaultBcard.setValue(defaultBcard);
			result=user.update(conn, " userId=" + id);
		}
		return result;
	
	}
	
	/**
	 * 修改默认银行卡，企业用户
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long updatedDefaultBankcard2(Connection conn, Long id,int defaultBcard)
		throws SQLException, DataException {
		Dao.Tables.t_orginfo user = new Dao().new Tables().new t_orginfo();
		long result=-1;
		if (defaultBcard >1) {
			user.defaultBcard.setValue(defaultBcard);
			result=user.update(conn, " user_id=" + id);
		}
		return result;
	
	}
	
	/**
	 * 保存客户来源
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long sourceStatisticSave(Connection conn, String mainSource,int value)
		throws SQLException, DataException {
		Dao.Tables.t_source_click source = new Dao().new Tables().new t_source_click();
		long result=-1;
		
		source.mainSource.setValue(mainSource);
		source.theDate.setValue(new Date());
		source.countIn.setValue(value);
		result = source.insert(conn);
		return result;
	}
	
	/**
	 * 保存客户来源
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long sourceStatisticUpdate(Connection conn, String mainSource,int value)
		throws SQLException, DataException {
		Dao.Tables.t_source_click source = new Dao().new Tables().new t_source_click();
		long result=-1;
		
		String command = "UPDATE t_source_click a SET a.`countIn`=a.`countIn`+"+value+" WHERE a.`mainSource`= '" + mainSource+"'";
		result = MySQL.executeNonQuery(conn, command.toString());
		command = null;
		
		return result;
	}
	
	//清除红包金额
	public void presrentToExpire(Connection conn,long id){
		String command = "UPDATE t_user t SET t.`expire` = t.`presrent` WHERE t.`id` = "+id;
		MySQL.executeNonQuery(conn, command.toString());
		command = "UPDATE t_user t SET t.`presrent` = 0.00 WHERE t.`id` = "+id;
		MySQL.executeNonQuery(conn, command);
	}
	
}
