package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.sp2p.database.Dao;

public class UserManageDao {
   
	/**
	 * 用户基本信息里面的查看用户的基本信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryUserManageInnerMsg(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_usermanage_baseinfoinner baseinfoinner = new Dao().new Views().new v_t_usermanage_baseinfoinner();

		DataSet dataSet = baseinfoinner.open(conn, "", " id = " + id, "",
				-1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 企业基本信息里面的查看企业的基本信息
	 * @param conn
	 * @param id
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> queryOrgManageInnerMsg(Connection conn, long id)
			throws SQLException, DataException {
		Dao.Views.v_t_orginfo baseinfoinner = new Dao().new Views().new v_t_orginfo();

		DataSet dataSet = baseinfoinner.open(conn, "", " userId = " + id, "",
				-1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
  /**
   * 弹出框显示信息初始化
   * @param conn
   * @param userId 用户id
   * @return
   * @throws SQLException
   * @throws DataException
   */
	public Map<String,String> queryUserManageaddInteral(Connection conn,Long userId) throws SQLException, DataException{
		DataSet dataSet = MySQL.executeQuery(conn, "select  tuser.id,tuser.username as username,tuser.creditrating as creditrating,tuser.rating as rating ,tp.realName as realName  from t_user tuser left join t_person tp on tuser.id = tp.userId where tuser.id = "+userId);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> queryUserInfo(Connection conn,long userId) throws SQLException, DataException{
		DataSet dataSet = MySQL.executeQuery(conn, "select tuser.id as id,tuser.username as username,tuser.creditrating as creditrating,tuser.rating as rating ,tuser.createTime as createTime,tuser.orgName orgName,tuser.userType userType,tp.realName as realName,tp.qq as qq,tuser.email as email,tuser.lastIP as lastIP,tp.cellPhone as cellPhone,tp.idNo AS idNo from t_user tuser left join t_person tp on tuser.id = tp.userId where tuser.id = "+userId);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Long updateUserqq(Connection conn,Long userId,String qq) throws SQLException{
		Dao.Tables.t_person person = new Dao().new Tables().new t_person();		
		person.qq.setValue(qq);
		return  person.update(conn, " userId = "+userId);
	}
	
	public Long updateUserInfo(Connection conn,Map map) throws SQLException{
//		String retUserName = (String) map.get("retUserName");
		String loginPwdHid = (String) map.get("loginPwdHid");
		String tranPwdHid = (String) map.get("tranPwdHid");
		Long userId = (Long) map.get("userId");
		String retId = (String) map.get("retId");
//		Dao.Tables.t_person person = new Dao().new Tables().new t_person();		
//		person.qq.setValue(qq);
//		return  person.update(conn, " userId = "+userId);
		
		//保存推荐人信息
		if (!StringUtil.isEmpty(retId)){
			Dao.Tables.t_recommend_user retu =  new Dao().new Tables().new t_recommend_user();
			retu.userId.setValue(userId);
			retu.recommendUserId.setValue(map.get("retId"));
			//retu.delete(conn, " userId="+userId);
			retu.insert(conn);
		}
		
		//修改密码
		if (!StringUtil.isEmpty(loginPwdHid) || !StringUtil.isEmpty(tranPwdHid)){
			Dao.Tables.t_user user =  new Dao().new Tables().new t_user();
			if (!StringUtil.isEmpty(loginPwdHid)){
				user.password.setValue(loginPwdHid);
			}
			if (!StringUtil.isEmpty(tranPwdHid)){
				user.dealpwd.setValue(tranPwdHid);
			}
			user.update(conn, " id=" + userId);
		}
		return 1L;
	}

	/**
	 *  * 向user表插入数据
	 * @param conn
	 * @param userId
	 * @param score
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addUserManageaddInteral(Connection conn,Long userId,Integer score,Integer type) throws SQLException, DataException{
		Dao.Tables.t_user user = new Dao().new Tables().new t_user();
		Map<String,String> userMap = null;
		DataSet dataSet = user.open(conn, "creditrating,rating", " id = "+userId, "", -1, -1);
		userMap = BeanMapUtils.dataSetToMap(dataSet);
		Integer precreditrating = null;
		Integer prerating = null;
		if(userMap!=null&&userMap.size()>0){
			precreditrating = Convert.strToInt(userMap.get("creditrating"), -1) ;
			prerating = Convert.strToInt(userMap.get("rating"), -1) ;
			if(precreditrating!=-1&&type==1){
				user.creditrating.setValue(precreditrating+score);
			     return  user.update(conn, " id = "+userId);
			}
			if(prerating!=-1&&type==2){
				user.rating.setValue(prerating+score);
				return  user.update(conn, " id = "+userId);
			}
		}
		return -1L;
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
	 * add by houli 查询用户资金信息
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryUserCashInfo(Connection conn,Long userId) throws SQLException, DataException{
		String sqlStr = "SELECT (usableSum+freezeSum) as totalSum,usableSum from t_user where id="+userId;
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 向代金券批次表添加记录
	 * @throws SQLException
	 * @throws DataException
	 */
	public Long addVouchersInfo(Connection conn,String title,Integer type,String amount,Integer number, String timeStart,
			String timeEnd, String applicant,String departments,String operation) throws SQLException, DataException{
		Dao.Tables.t_trial_batch  integraldetail = new Dao().new Tables().new t_trial_batch();
		integraldetail.title.setValue(title);
		integraldetail.type.setValue(type);
		integraldetail.amount.setValue(amount);
		integraldetail.qty.setValue(number);
		integraldetail.enableDate.setValue(timeStart);
		integraldetail.disableDate.setValue(timeEnd);
		integraldetail.applyBy.setValue(applicant);
		integraldetail.applyDept.setValue(departments);
		integraldetail.createBy.setValue(operation);
		integraldetail.createTime.setValue(new Date());
		return  integraldetail.insert(conn);
	}
	
	/**
	 *功能：修改真名，身份证号，且实名认证通过
	 * @param conn
	 * @param map
	 * @return
	 * @throws SQLException
	 */
	public Long updatePersonAuth(Connection conn,Map <String,Object>map) throws SQLException{
		String realName = (String) map.get("realName");
		Long userId = (Long) map.get("userId");
		Dao.Tables.t_person user =  new Dao().new Tables().new t_person();
		user.realName.setValue(realName);
		user.idNo.setValue(map.get("idNo"));
		if (!StringUtil.isEmpty(realName) && !StringUtil.isEmpty((String)map.get("idNo"))){
			user.authCardName.setValue(0);
		} else {
			user.authCardName.setValue(1);
		}
		user.update(conn, " userId=" + userId);
		return 1L;
	}
}
