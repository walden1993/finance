package com.sp2p.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;

import com.google.zxing.Result;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_award_score;

public class MyHomeInfoSettingDao {
	/**
	 * 该用户密码
	 * @param conn
	 * @param userId
	 * @param password
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public Long updateUserPassword(Connection conn,long userId,String password,String type) throws SQLException{
		Dao.Tables.t_user tuser = new Dao().new Tables().new t_user();
		if ("1".equals(IConstants.ENABLED_PASS)){
			password = com.shove.security.Encrypt.MD5(password.trim());
		}else{
			password = com.shove.security.Encrypt.MD5(password.trim()+IConstants.PASS_KEY);
		}
		if(type.endsWith("login")){
			tuser.password.setValue(password);//修改登录密码
		}else{//修改交易密码
			tuser.dealpwd.setValue(password);
		}
		return tuser.update(conn, " id=" + userId);
	}
	
	/**
	 * 删除提现银行卡信息
	 * @param conn
	 * @param bankId
	 * @return
	 * @throws SQLException
	 */
	public Long deleteBankInfo(Connection conn,long bankId) throws SQLException{
		Dao.Tables.t_bankcard tbank = new Dao().new Tables().new t_bankcard();
		return tbank.delete(conn, " id="+bankId);
	}
	
	//删除已绑定的银行卡信息
	public Long deleteMyBankInfo(Connection conn,long bankId,long userId) throws SQLException{
		Dao.Tables.t_bankcard tbank = new Dao().new Tables().new t_bankcard();
		tbank.isDelete.setValue(1);
		return tbank.update(conn, " id="+bankId+" and userId="+userId);
	}
	
	//查询是否默认基金银行卡
	public boolean isFundDefault(Connection conn,long bankId,long userId) throws SQLException, DataException{
		Dao.Tables.t_bankcard t_bankcard = new Dao().new Tables().new t_bankcard();
		String condition = " fund_default = 1 and userId="+userId;
		DataSet dataSet = t_bankcard.open(conn, "userId", condition, " ", -1, -1);
		 Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		if (map==null || map.isEmpty()) {
			return false;
		}else {
			long _userId = MapUtils.getLongValue(map, "userId",-1L);
			if (_userId>0) {
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * 根据用户id获得交易密码
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String, String> getDealPwd(Connection conn,long userId,int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Tables.t_user tuser = new Dao().new Tables().new t_user();
		DataSet dataSet = tuser.open(conn, "dealpwd", " id="+userId, "", limitStart, limitStart);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 添加提现银行卡信息
	 * @param conn
	 * @param userId
	 * @param cardUserName
	 * @param bankName
	 * @param subBankName
	 * @param bankCard
	 * @param cardStatus
	 * @return
	 * @throws SQLException
	 */
	public Long addBankCardInfo(Connection conn,long userId,String cardUserName,String bankName,String subBankName,
			String bankCard,int cardStatus,int city,int province) throws SQLException{
		Dao.Tables.t_bankcard t_bank = new Dao().new Tables().new t_bankcard();
		t_bank.userId.setValue(userId);
		t_bank.cardUserName.setValue(cardUserName);
		t_bank.bankName.setValue(bankName);
		t_bank.branchBankName.setValue(subBankName);
		t_bank.cardNo.setValue(bankCard);
		t_bank.cardStatus.setValue(cardStatus);
		t_bank.commitTime.setValue(new Date());//提交时间
		t_bank.city.setValue(city);
		t_bank.province.setValue(province);
		return t_bank.insert(conn);
	}
	
	/**
	 * 添加提现银行卡信息
	 * @param conn
	 * @param userId
	 * @param cardUserName
	 * @param bankName
	 * @param subBankName
	 * @param bankCard
	 * @param cardStatus
	 * @return
	 * @throws SQLException
	 */
	public Long addBankCardInfoApp(Connection conn,long userId,String cardUserName,String bankName,String subBankName,
			String bankCard,int cardStatus,String card_Bank_en,String card_type,int city,int province,String vc_branchbank,String card_mobile) throws SQLException{
		Dao.Tables.t_bankcard t_bank = new Dao().new Tables().new t_bankcard();
		t_bank.userId.setValue(userId);
		t_bank.cardUserName.setValue(cardUserName);
		t_bank.bankName.setValue(bankName);
		t_bank.branchBankName.setValue(subBankName);
		t_bank.cardNo.setValue(bankCard);
		t_bank.cardStatus.setValue(cardStatus);
		t_bank.commitTime.setValue(new Date());//提交时间
		t_bank.cardMode.setValue(card_type);
		t_bank.card_Bank_en.setValue(card_Bank_en);
		t_bank.city.setValue(city);
		t_bank.province.setValue(province);
		t_bank.vc_branchbank.setValue(vc_branchbank);
		t_bank.card_mobile.setValue(card_mobile);
		return t_bank.insert(conn);
	}

    public Long addBankCardInfo(Connection conn,long userId,String cardUserName,String bankName,String subBankName,
                                String bankCard,int cardStatus) throws SQLException{
                            Dao.Tables.t_bankcard t_bank = new Dao().new Tables().new t_bankcard();
                            t_bank.userId.setValue(userId);
                            t_bank.cardUserName.setValue(cardUserName);
                            t_bank.bankName.setValue(bankName);
                            t_bank.branchBankName.setValue(subBankName);
                            t_bank.cardNo.setValue(bankCard);
                            t_bank.cardStatus.setValue(cardStatus);
                            t_bank.commitTime.setValue(new Date());//提交时间
                            return t_bank.insert(conn);
                        }	
	
	/**
	 * 添加手机绑定信息
	 * @param conn
	 * @param mobile
	 * @return
	 * @throws SQLException 
	 */
	public Long addBindingMobile(Connection conn,String mobile,long userId,int status,String content,int type,String oldPhone) throws SQLException{
		Dao.Tables.t_phone_binding_info tp = new Dao().new Tables().new t_phone_binding_info();
		tp.mobilePhone.setValue(mobile);
		tp.userId.setValue(userId);
		tp.status.setValue(status);
		tp.reason.setValue(content);//手机绑定原因
		tp.requsetTime.setValue(new Date());//手机绑定时间
		tp.oldPhone.setValue(oldPhone);
		//add by lw
		tp.type.setValue(type);//区分是填写基础资料插入的还是填写手机变更时候插入 
		//end
		return tp.insert(conn);
	}
	
	public Long updateBindingMobile(Connection conn,String mobile,long userId,int status,String content,int type,String oldPhone)throws SQLException{
		Dao.Tables.t_phone_binding_info tp = new Dao().new Tables().new t_phone_binding_info();
		tp.mobilePhone.setValue(mobile);
		tp.status.setValue(status);
		tp.reason.setValue(content);//手机绑定原因
		tp.requsetTime.setValue(new Date());//手机绑定时间
		tp.oldPhone.setValue(oldPhone);
		//add by lw
		tp.type.setValue(type);//区分是填写基础资料插入的还是填写手机变更时候插入 
		//end
		return tp.update(conn, " userId = "+userId);
	}
	
	/**
	 * 查询绑定的手机信息
	 * @param conn
	 * @param mobile
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBindingMoble(Connection conn,String mobile,int limitStart,int limitCount) 
	    throws SQLException, DataException{
		Dao.Tables.t_user t_phone_binding_info = new Dao().new Tables().new t_user();
		mobile=com.shove.web.Utility.filteSqlInfusion(mobile);
		String condition = " mobilePhone='"+StringEscapeUtils.escapeSql(mobile)+"'";
		DataSet dataSet = t_phone_binding_info.open(conn, " id ", condition, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public Map<String,String> queryBindingMobleUserInfo(Connection conn,long userId,int limitStart,int limitCount) 
	    throws SQLException, DataException{
		Dao.Tables.t_phone_binding_info t_phone_binding_info = new Dao().new Tables().new t_phone_binding_info();
		
		String condition = " userId="+userId;
		DataSet dataSet = t_phone_binding_info.open(conn, "userId,mobilePhone", condition, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
    }
	
	/**
	 * 如果该用户已经绑定了一个手机号码，则不允许再继续绑定手机号码，需要更换手机号码
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBindingInfoByUserId(Connection conn,long userId,int limitStart,int limitCount) 
    	throws SQLException, DataException{
		Dao.Tables.t_phone_binding_info t_phone_binding_info = new Dao().new Tables().new t_phone_binding_info();
		
		String condition = " userId='"+userId+"'";
		DataSet dataSet = t_phone_binding_info.open(conn, "*", condition, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
   }
	/**
	 * 查看在手机绑定表中是否存在此信息
	 * @param conn
	 * @param userId
	 * @param status
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
//	public Map<String,String> querySucessBindingInfoByUserId(Connection conn,long userId,int status,int limitStart,int limitCount) 
//		throws SQLException, DataException{
//		Dao.Tables.t_phone_binding_info t_phone_binding_info = new Dao().new Tables().new t_phone_binding_info();
//		
//		String condition = " userId="+userId+" and status = "+status;
//		DataSet dataSet = t_phone_binding_info.open(conn, "*", condition, " ", limitStart, limitCount);
//		return BeanMapUtils.dataSetToMap(dataSet);
//	}
	/**
	 * 查看在手机绑定表中是否存在此信息
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> querySucessBindingInfoByUserId(Connection conn,long userId,int limitStart,int limitCount) 
	throws SQLException, DataException{
	Dao.Tables.t_phone_binding_info t_phone_binding_info = new Dao().new Tables().new t_phone_binding_info();	
	String condition = " userId="+userId;
	DataSet dataSet = t_phone_binding_info.open(conn, "*", condition, " ", limitStart, limitCount);
	return BeanMapUtils.dataSetToMap(dataSet);
}
	
	public List<Map<String,Object>> queryBindingsByUserId(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		String condition = " userId='"+userId+"'";
//		Dao.Tables.t_phone_binding_info t_phone_binding_info = new Dao().new Tables().new t_phone_binding_info();
//		DataSet dataSet = t_phone_binding_info.open(conn, "*", condition, " ", limitStart, limitCount);
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		DataSet dataSet = t_person.open(conn, "*", condition, " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }
	
	/**
	 * 获得银行卡信息
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryBankInfoList(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
		DataSet dataSet = t_bankCard.open(conn, "   card_Bank_en,cardMode,cardUserName ,bankName,branchBankName,cardNo,modifiedCardStatus,id,cardStatus,remark   ", " userId='"+userId+"' and `isDelete` = 2", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 获得银行卡信息App
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryBankInfoListApp(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
		DataSet dataSet = t_bankCard.open(conn, "   card_Bank_en,cardMode as card_type ,bankName as card_bank_name,cardNo as card_number,id as card_id,cardStatus as card_status  ", " userId='"+userId+"' and `isDelete` = 2", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 获得银行卡bin信息
	 * @param conn
	 * @param card_bin
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBankBinInfoList(Connection conn,String card_bin,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_bankcard_bin t_bankCardBin = new Dao().new Tables().new t_bankcard_bin();
		DataSet dataSet = t_bankCardBin.open(conn, "    `banknam` AS bank_name,`bankcode` AS card_bank_en,`crdtype` AS card_type  ", " `bindata`='"+card_bin+"' AND `crdtype` = 0", " ", 0, 1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	//查询已经成功绑定的银行卡
	public List<Map<String,Object>> queryBankInfoListCount(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
		DataSet dataSet = t_bankCard.open(conn, "   cardUserName ,bankName,branchBankName,cardNo,modifiedCardStatus,id,cardStatus   ", " userId='"+userId+"' and cardStatus = 1 and isDelete = 2", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> querySuccessBankInfoList(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
		DataSet dataSet = t_bankCard.open(conn, "*", " userId='"+userId+"' and cardStatus=1 and isDelete = 2", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	public List<Map<String,Object>> querySuccessBankInfoListApp(Connection conn,long userId,int limitStart,int limitCount)
			throws SQLException, DataException {
			Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
			DataSet dataSet = t_bankCard.open(conn, " card_Bank_en,cardMode as card_type ,bankName as card_bank_name,cardNo as card_number,id as card_id,cardStatus as card_status  ", " userId='"+userId+"' and cardStatus=1 and `isDelete` = 2", " ", limitStart, limitCount);
			dataSet.tables.get(0).rows.genRowsMap();
			return dataSet.tables.get(0).rows.rowsMap;
		}
	
	/**
	 * 查询绑定的银行卡信息，如果某用户已经绑定两张银行卡，那么就不能再绑定银行卡信息了
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBankInfoCardStauts(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
		//查询绑定的银行卡信息。cardStatus=1为绑定状态
		String condition = " userId='"+userId+"' and cardStatus in (1,2) and isDelete=2 ";
//		String condition = " userId='"+userId+"'";
		DataSet dataSet = t_bankCard.open(conn, "count(*)", condition, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	//查询某个账号是否已经被该用户绑定过了
	public Map<String,String> queryBankCardisBanding(Connection conn,long userId,String cardNo,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
		//String condition = " userId='" + userId + "' and cardNo = " + cardNo;
		String condition = "  cardNo = '" + cardNo+"' and isDelete=2  group by isDelete ";//hjh  改
		DataSet dataSet = t_bankCard.open(conn, "  userId,count(*)  ", condition, " ", limitStart, limitCount);
		Map <String,String>m = BeanMapUtils.dataSetToMap(dataSet);
		if (null == m){
			return new HashMap(){{put("count(*)","0");}};
		}
		return m;
		 
	}
	
	/**
	 * 根据银行卡id查找银行卡号
	 * @param conn
	 * @param bankId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryBankInfoById(Connection conn,long bankId,int limitStart,int limitCount) 
	   throws SQLException, DataException{
		Dao.Tables.t_bankcard t_bankCard = new Dao().new Tables().new t_bankcard();
		String condition = " id='"+bankId+"'";
		DataSet dataSet = t_bankCard.open(conn, "cardNo", condition, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 根据用户id获得用户的真实姓名
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> getUserRealName(Connection conn,long userId,int limitStart,int limitCount) throws SQLException, DataException{
		Dao.Views.t_user_person_info tperson = new Dao().new Views().new t_user_person_info();
		DataSet dataSet = tperson.open(conn, "*", " userId="+userId, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public long addNotes(Connection conn,long userId,boolean message,boolean mail,boolean notes) throws SQLException, DataException{
		long result = -1L;
		Dao.Tables.t_notice tnotice = new Dao().new Tables().new t_notice();
		tnotice.userId.setValue(userId);
		if(message){
			tnotice.mailNoticeEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.mailNoticeEnable.setValue(IConstants.NOTICE_OFF);
		}
		
		if(mail){
			tnotice.emailNoticeEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.emailNoticeEnable.setValue(IConstants.NOTICE_OFF);
		}
		
		if(notes){
			tnotice.noteNoticeEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.noteNoticeEnable.setValue(IConstants.NOTICE_OFF);
		}
		Map<String,String> map = this.queryNoteInfo(conn, userId);
		//如果没有数据，则插入
		if(map == null){
			result = tnotice.insert(conn);
		}else{//否则进行更新
			result = tnotice.update(conn, " userId="+userId);
		}
		return result;
	}
	
	public long addNotesSetting(Connection conn,long userId,boolean messageReceive,boolean messageDeposit,boolean messageBorrow,
			boolean messageRecharge,boolean messageChange,boolean mailReceive,boolean mailDeposit,
			boolean mailBorrow,boolean mailRecharge,boolean mailChange,boolean noteReceive,
			boolean noteDeposit,boolean noteBorrow,boolean noteRecharge,boolean noteChange) throws SQLException, DataException{
		long result = -1L;
		Dao.Tables.t_noticecon tnotice = new Dao().new Tables().new t_noticecon();
		//通知方式(1 邮件 2 站内信 3 短信) 状态(1 关闭  2 开启)
		int nt1 = IConstants.MAIL_NOTICE;//Convert.strToInt(HomeInfoSettingAction.informWay[1], -1);
		tnotice.noticeMode.setValue(nt1);
		tnotice.userId.setValue(userId);
		//添加站内信信息
		addToNotice(tnotice,messageReceive,messageDeposit,messageBorrow,messageRecharge,messageChange);
		Map<String,String> map = this.queryNoticeSettingInfo(conn, nt1, userId);
		long result1 = -1L;
		if(map == null){//如果没有数据 则进行插入，否则进行更新
			result1 = tnotice.insert(conn);
		}else{
			result1 = tnotice.update(conn, " userId="+userId+"  and noticeMode="+nt1);
		}
		//邮件
		tnotice = new Dao().new Tables().new t_noticecon();
		int nt2 = IConstants.EMAIL_NOTICE;//Convert.strToInt(HomeInfoSettingAction.informWay[0], -1);
		tnotice.noticeMode.setValue(nt2);
		tnotice.userId.setValue(userId);
		addToNotice(tnotice,mailReceive,mailDeposit,mailBorrow,mailRecharge,mailChange);
		map = this.queryNoticeSettingInfo(conn, nt2, userId);
		long result2 = -1L;
		if(map == null){//如果没有数据 则进行插入，否则进行更新
			result2 = tnotice.insert(conn);
		}else{
			result2 = tnotice.update(conn, " userId="+userId+"  and noticeMode="+nt2);
		}
		
		//短信
		tnotice = new Dao().new Tables().new t_noticecon();
		int nt3 = IConstants.NOTE_NOTICE;//Convert.strToInt(HomeInfoSettingAction.informWay[2], -1);
		tnotice.noticeMode.setValue(nt3);
		tnotice.userId.setValue(userId);
		addToNotice(tnotice,noteReceive,noteDeposit,noteBorrow,noteRecharge,noteChange);
		map = this.queryNoticeSettingInfo(conn, nt3, userId);
		long result3 = -1L;
		if(map == null){//如果没有数据 则进行插入，否则进行更新
			result3 = tnotice.insert(conn);
		}else{
			result3 = tnotice.update(conn, " userId="+userId+"  and noticeMode="+nt3);
		}
		
		if(result1 > 0 && result2 >0 && result3 >0)//三条数据都插入成功
			result  = 1L;
		map= null;
		return result;
	}
	
	private void addToNotice(Dao.Tables.t_noticecon tnotice,boolean reciveRepayEnable,boolean showSucEnable,boolean loanSucEnable,
			boolean rechargeSucEnable,boolean capitalChangeEnable) throws SQLException{
		if(reciveRepayEnable){//2 为开启
			tnotice.reciveRepayEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.reciveRepayEnable.setValue(IConstants.NOTICE_OFF);
		}
		
		if(showSucEnable){//2 为开启
			tnotice.showSucEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.showSucEnable.setValue(IConstants.NOTICE_OFF);
		}
		
		if(loanSucEnable){//2 为开启
			tnotice.loanSucEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.loanSucEnable.setValue(IConstants.NOTICE_OFF);
		}
		
		if(rechargeSucEnable){//2 为开启
			tnotice.rechargeSucEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.rechargeSucEnable.setValue(IConstants.NOTICE_OFF);
		}
		
		if(capitalChangeEnable){//2 为开启
			tnotice.capitalChangeEnable.setValue(IConstants.NOTICE_ON);
		}else{
			tnotice.capitalChangeEnable.setValue(IConstants.NOTICE_OFF);
		}
	}
	
	/**
	 * 查询对应的通知设置，总类型的设置
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryNotes(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_notice t_notice = new Dao().new Tables().new t_notice();
		String condition = " userId='"+userId+"'";
		DataSet dataSet = t_notice.open(conn, "*", condition, " ", limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 * 查询通知设置总类型下面的具体设置
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryNotesSetting(Connection conn,long userId,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_noticecon t_noticecon = new Dao().new Tables().new t_noticecon();
		DataSet dataSet = t_noticecon.open(conn, "*", " userId='"+userId+"'", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 查询站内信信息
	 * @param conn
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryMailList(Connection conn,int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		DataSet dataSet = t_mail.open(conn, "*", "", " ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
	}
	
	/**
	 * 添加邮件信息
	 * @param conn
	 * @param sendUserId
	 * @param receiverUserId
	 * @param title
	 * @param content
	 * @param mailStatus
	 * @param mailType
	 * @return
	 * @throws SQLException
	 */
	public Long addMail(Connection conn,long sendUserId,long receiverUserId,String title,String content,int mailStatus,
			int mailType) throws SQLException{
		Long result = -1L;
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		t_mail.sender.setValue(sendUserId);
		t_mail.reciver.setValue(receiverUserId);
		t_mail.mailTitle.setValue(title);
		t_mail.mailContent.setValue(content);
		t_mail.sendTime.setValue(new Date());
		System.out.println(new Date());
		t_mail.mailStatus.setValue(mailStatus);
		t_mail.mailType.setValue(mailType);
		t_mail.mailMode.setValue(IConstants.MAIL_MODE);
		result = t_mail.insert(conn);
		return result;
	}
	
	
	
	/**
	 * 添加邮件信息
	 * @param conn
	 * @param sendUserId
	 * @param receiverUserId
	 * @param title
	 * @param content
	 * @param mailStatus
	 * @param mailType
	 * @return
	 * @throws SQLException
	 */
	public Long addMail(Connection conn,long sendUserId,long receiverUserId,String title,String content,int mailStatus,Integer mailMode,
			int mailType) throws SQLException{
		Long result = -1L;
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		t_mail.sender.setValue(sendUserId);
		t_mail.reciver.setValue(receiverUserId);
		t_mail.mailTitle.setValue(title);
		t_mail.mailContent.setValue(content);
		t_mail.sendTime.setValue(new Date());
		t_mail.mailStatus.setValue(mailStatus);
		t_mail.mailType.setValue(mailType);
		t_mail.mailMode.setValue(mailMode);
		result = t_mail.insert(conn);
		return result;
	}
	
	private Map<String,String> queryNoteInfo(Connection conn,long userId) throws SQLException, DataException{
		Dao.Tables.t_notice t_notice = new Dao().new Tables().new t_notice();
		DataSet dataSet = t_notice.open(conn, "userId", " userId="+userId, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	private Map<String,String> queryNoticeSettingInfo(Connection conn,int noticeMode,long userId) throws SQLException, DataException{
		Dao.Tables.t_noticecon t_noticecon = new Dao().new Tables().new t_noticecon();
		DataSet dataSet = t_noticecon.open(conn, "userId", " userId="+userId+" and noticeMode="+noticeMode, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 获得收件箱信息(一般信息的收件箱跟系统信息的收件箱)
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> queryReceiveMails(Connection conn,long userId,int mailType,
			int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		DataSet dataSet = t_mail.open(conn, "*", " reciver='"+userId+"' and mailType="+mailType
				+"  and mailMode="+IConstants.MAIL_MODE,
				" ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }
	
	/**
	 * 获得发件箱信息
	 * @param conn
	 * @param userId
	 * @param limitStart
	 * @param limitCount
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public List<Map<String,Object>> querySendMails(Connection conn,long userId,
			int limitStart,int limitCount)
		throws SQLException, DataException {
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		DataSet dataSet = t_mail.open(conn, "*", " sender='"+userId
				+"'  and mailMode="+IConstants.MAIL_MODE, 
				" ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }
	
	//删除发送邮件信息
	public Long deleteMail(Connection conn,String ids) throws SQLException{
		ids=com.shove.web.Utility.filteSqlInfusion(ids);
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		return t_mail.delete(conn, " id in("+idSQL+")");
	}
	
	//更新邮件信息 站内信状态(1 默认未读 2 删除 3 已读)
	public Long updateMail(Connection conn,String ids,int type) throws SQLException{
		String idStr = StringEscapeUtils.escapeSql("'"+ids+"'");
		String idSQL = "-2";
		idStr = idStr.replaceAll("'", "");
		String [] array = idStr.split(",");
		for(int n=0;n<=array.length-1;n++){
		   idSQL += ","+array[n];
		}
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		t_mail.mailStatus.setValue(type);
		return t_mail.update(conn, " id in("+idSQL+")");
	}
	
	/**
	 * 根据邮件id 查询邮件信息
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public Map<String,String> queryEmailById(Connection conn,long mailId) throws SQLException, DataException{
		Dao.Tables.t_mail t_mail = new Dao().new Tables().new t_mail();
		DataSet dataSet = t_mail.open(conn, "*", " id="+mailId, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**
	 * 得到用户设置的所有密保问题和答案
	 * @param conn
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public Map<String,String> queryUserALLAnswer(Connection conn,long userId) throws SQLException, DataException{
		Dao.Tables.t_pwd_answer answer = new Dao().new Tables().new t_pwd_answer();
		DataSet dataSet = answer.open(conn, "*", " userId="+userId, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 保存用户密保答案
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
	public Long savePwdAnswer(Connection conn,long userId,String questionOne,String questionTwo,String questionThree,
			String answerOne,String answerTwo,String answerThree) throws SQLException{
		Dao.Tables.t_pwd_answer answer = new Dao().new Tables().new t_pwd_answer();
		answer.userId.setValue(userId);
		answer.answerOne.setValue(answerOne);
		answer.answerTwo.setValue(answerTwo);
		answer.answerThree.setValue(answerThree);
		answer.questionOne.setValue(questionOne);
		answer.questionTwo.setValue(questionTwo);
		answer.questionThree.setValue(questionThree);
		return answer.insert(conn);
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
	public Long updatePwdAnswer(Connection conn,long userId,String questionOne,String questionTwo,String questionThree,
			String answerOne,String answerTwo,String answerThree) throws SQLException {
		Dao.Tables.t_pwd_answer answer = new Dao().new Tables().new t_pwd_answer();
		answer.answerOne.setValue(answerOne);
		answer.answerTwo.setValue(answerTwo);
		answer.answerThree.setValue(answerThree);
		answer.questionOne.setValue(questionOne);
		answer.questionTwo.setValue(questionTwo);
		answer.questionThree.setValue(questionThree);
		return answer.update(conn, " userId = " +userId);
	}
	
	/**
	 * 修改个人头像的时候判断是否填写过个人信息
	 * @return
	 */
	public Map<String,String> queryHeadImg(Connection conn,String username) throws Exception {
		StringBuffer command = new StringBuffer();
		username=com.shove.web.Utility.filteSqlInfusion(username);
		command.append(" SELECT * FROM t_person ");
		command.append(" WHERE realName = '"+ StringEscapeUtils.escapeSql(username )+"' limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**   
	 * @throws SQLException 
	 * @MethodName: updatePersonImg  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:04:48
	 * @Return:    
	 * @Descb: 修改个人头像
	 * @Throws:
	*/
	public Long updatePersonImg(Connection conn,String imgPath, Long id) throws SQLException {
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		t_person.personalHead.setValue(imgPath);
		return t_person.update(conn, " userId = " +id);
	}

	
	/**   
	 * @MethodName: queryRenewalVIP  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午08:47:34
	 * @Return:    
	 * @Descb: 查询会员VIP信息
	 * @Throws:
	*/
	public Map<String, String> queryRenewalVIP(Connection conn, Long id) throws SQLException, DataException {
		StringBuffer command = new StringBuffer();
		command.append("SELECT a.vipStatus,a.username,b.realName,a.email,DATE_ADD(a.vipCreateTime, INTERVAL 1 YEAR) AS vipCreateTime");
		command.append(" FROM t_user a LEFT JOIN t_person b ON b.userId=a.id");
		command.append(" where a.id = "+id + " limit 0,1");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	public List<Map<String,Object>> getConcernList(Connection conn,long userId,String receiver,
			int limitStart,int limitCount)
		throws SQLException, DataException {
		receiver=com.shove.web.Utility.filteSqlInfusion(receiver);
//		Dao.Views.v_t_user_concern_lists views = new Dao().new Views().new v_t_user_concern_lists();
//		DataSet dataSet = views.open(conn, "*", "  userId= "+userId+" and username='"+StringEscapeUtils.escapeSql(receiver)+"'", 
//				" ", limitStart, limitCount);
		Dao.Views.v_t_recommendfriend_list views = new Dao().new Views().new v_t_recommendfriend_list();
		DataSet dataSet = views.open(conn, "*", "  recommendUserId= "+userId+" and username='"+StringEscapeUtils.escapeSql(receiver)+"'", 
				" ", limitStart, limitCount);
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
    }

	
	/**   
	 * @MethodName: queryPerson  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-22 下午10:48:56
	 * @Return:    
	 * @Descb: 查询个人基本信息
	 * @Throws:
	*/
	public Map<String, String> queryPerson(Connection conn, Long id) throws SQLException, DataException {
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		DataSet dataSet = t_person.open(conn, " id ", " userId="+id, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	/**   
	 * @MethodName: addPersonImg  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-4-22 下午10:50:39
	 * @Return:    
	 * @Descb: 添加个人头像
	 * @Throws:
	*/
	public Long addPersonImg(Connection conn, String imgPath,long id) throws SQLException {
		Dao.Tables.t_person t_person = new Dao().new Tables().new t_person();
		t_person.userId.setValue(id);
		t_person.personalHead.setValue(imgPath);
		return t_person.insert(conn);
	}
	
	
	public Map<String, String> queryUserCashStatus(Connection conn, Long userId,int cashStatus) throws SQLException, DataException {
		
		String sqlStr = "SELECT cashStatus FROM t_group a " +
				"left JOIN  t_group_user b on a.id=b.groupId where userId="+userId+" and cashStatus="+cashStatus;
		
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		sqlStr = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	/**   
	 * @MethodName: isRenewal  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 上午10:37:58
	 * @Return:    
	 * @Descb: 查询用户是否为VIP
	 * @Throws:
	*/
	public Map<String, String> isVIP(Connection conn, Long id) throws SQLException, DataException {
		String sqlStr = "select id from t_user where id="+id+" and vipStatus !=1";
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		sqlStr = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: isRenewal  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 上午10:54:25
	 * @Return:    
	 * @Descb: 查询用户是否需要续费
	 * @Throws:
	*/
	public Map<String, String> isRenewal(Connection conn, Long id) throws SQLException, DataException {
		String sqlStr = "select id from t_user where id="+id+" and vipStatus = 3";
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		sqlStr = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: isForVIPFee  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 上午10:58:08
	 * @Return:    
	 * @Descb: 查询用户可用金额是否够支付会费
	 * @Throws:
	*/
	public Map<String, String> isForVIPFee(Connection conn, Long id,double vipFee) throws SQLException, DataException {
		String sqlStr = "select usableSum,username from t_user where id="+id+" and usableSum > "+vipFee;
		DataSet dataSet = MySQL.executeQuery(conn, sqlStr);
		sqlStr = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	
	/**   
	 * @MethodName: decucatedVIPFee  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 上午11:03:53
	 * @Return:    
	 * @Descb: 扣除VIP会费
	 * @Throws:
	*/
	public long decucatedVIPFee(Connection conn, Long id, double vipFee) throws SQLException {
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		String date = sf.format(new Date());
		String sqlStr = "update t_user set usableSum = usableSum - "+vipFee+",feeStatus=2,vipStatus=2,vipCreateTime = '"+date+"' where id ="+id;
		long result=MySQL.executeNonQuery(conn, sqlStr);
		sf=null;
		date=null;
		date=null;
		return result;
	}

	
	/**   
	 * @throws SQLException 
	 * @MethodName: addVIPFeeRecord  
	 * @Param: MyHomeInfoSettingDao  
	 * @Author: gang.lv
	 * @Date: 2013-5-4 上午11:05:52
	 * @Return:    
	 * @Descb: 添加VIP会费记录
	 * @Throws:
	*/
	public long addVIPFeeRecord(Connection conn, Long id, double vipFee) throws SQLException {
		Dao.Tables.t_vipsum vipsum = new Dao().new Tables().new t_vipsum();
		vipsum.vipFee.setValue(vipFee);
		vipsum.userId.setValue(id);
		return vipsum.insert(conn);
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
		StringBuffer command = new StringBuffer();
		command.append("select a.usableSum,a.freezeSum,sum(b.recivedPrincipal+b.recievedInterest-b.hasPrincipal-b.hasInterest) forPI");
		command.append(" from t_user a left join t_invest b on a.id = b.investor where a.id="+userId+" group by a.id");
		DataSet dataSet = MySQL.executeQuery(conn, command.toString());
		command = null;
		return BeanMapUtils.dataSetToMap(dataSet);
	}

	public Map<String,String> queryEmailDetailById(Connection conn,long mailId) throws SQLException, DataException{
		Dao.Views.v_t_mail_user_detail v_t_mail_user_detail = new Dao().new Views().new v_t_mail_user_detail();
		DataSet dataSet = v_t_mail_user_detail.open(conn, "*", " id="+mailId, " ", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	public Map<String, String> queEmailUser(Connection conn, Long userId) throws SQLException, DataException {
		Dao.Tables.t_user t_user = new Dao().new Tables().new t_user();
		DataSet dataSet = t_user.open(conn, " username ", " id=" + userId, "", -1, -1);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	
	/**
	 *功能：查询用户积分
	 * @param conn
	 * @param userId
	 * @return
	 * @throws DataException
	 */
	public Map<String, String> queryAwardSore(Connection conn, Long userId,int stype)
			throws DataException {
		Dao.Tables.t_user select = new Dao().new Tables().new t_user();
		DataSet ds = select
				.open(conn, "sumScore ", " id = " + userId , "", -1, -1);
		return BeanMapUtils.dataSetToMap(ds);
	}

	/**
	 *功能：查询活动信息
	 * @param conn
	 * @param termId
	 * @return
	 * @throws DataException
	 */
	public List<Map<String, Object>> queryAwardInfo(Connection conn, String termId) throws DataException {
		String command = "SELECT a.*,b.`awardName`,b.`picAddr`,c.`awardNo` FROM t_award_info a,t_award_param b,t_award_term c WHERE a.`paramId`=b.id AND a.`isUsable`=1 AND c.id=a.`termId` AND a.`termId`="+termId +"  ORDER BY realMoney ";
		DataSet dataSet = MySQL.executeQuery(conn, command);
		command = null;
		dataSet.tables.get(0).rows.genRowsMap();
		return dataSet.tables.get(0).rows.rowsMap;
		
	}
	
	public long queryHasAward(Connection conn, String termId,long userId) throws DataException {
		String command = "SELECT count(id) as usercount FROM t_award_user t WHERE t.`termId` = "+termId+" AND t.`userId`="+userId;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		if (map!=null) {
			long usercount = Convert.strToLong(map.get("usercount"), -1);
			if (usercount>0) {
				return usercount;
			}else {
				command ="SELECT COUNT(id) as usercount FROM t_user t WHERE t.`id` = "+userId+" AND t.`createTime` <='2015-03-13 20:00:00'";
				dataSet = MySQL.executeQuery(conn, command);
				map = BeanMapUtils.dataSetToMap(dataSet);
				if (map!=null) {
					usercount = Convert.strToLong(map.get("usercount"), -1);
					return usercount;
				}
			}
			
			return usercount;
		}else {
			return -1L;
		}
	}
	
	public long queryPresrent(Connection conn,long userId,String ternId) throws DataException {
		String command = "SELECT SUM(IFNULL(ti.`realMoney`,0.00)) AS sumRealMoney FROM t_award_user tu ,t_award_info ti WHERE tu.`awardInfoId` = ti.`id` AND ti.`status` = 1 AND tu.`userId` = "+userId+" AND tu.`termId` IN "+ternId;
		DataSet dataSet = MySQL.executeQuery(conn, command);
		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		if (map!=null) {
			long usercount = Convert.strToDouble(map.get("sumRealMoney"), -1)>20?1L:-1L;
			return usercount;
		}else {
			return -1L;
		}
	}
	
	
	/**
	 *功能： 更新积分
	 * @param conn
	 * @param termId
	 * @return
	 * @throws DataException
	 */
	public Long updateAwardScore(Connection conn, Map map) throws DataException {
		//更新总好币
		Long userId = (Long) map.get("userId");
		String sqlStr = "update t_user set sumScore = sumScore-1 where id="+userId;
		long a = MySQL.executeNonQuery(conn, sqlStr);
		if (!((map.get("paramId")).toString()+"").equals("1")){
			sqlStr = "UPDATE t_award_term a SET a.`awardNo` = a.`awardNo` -1 WHERE a.id="+map.get("termId");
			MySQL.executeNonQuery(conn, sqlStr);
		}
		
		//插入好币扣除记录
		Dao.Tables.t_award_user vipsum = new Dao().new Tables().new t_award_user();
		vipsum.userId.setValue(userId);
		vipsum.termId.setValue(map.get("termId"));
		vipsum.paramId.setValue(map.get("paramId"));
		vipsum.score.setValue(map.get("score"));
		vipsum.awardInfoId.setValue(map.get("infocId"));
		vipsum.createTime.setValue(new Date());
		vipsum.sendAward.setValue(map.get("sendAward"));
		
		return vipsum.insert(conn);
		//return a;
		
	}
	
	
	public Long addAwardUser(Connection conn, long userId) throws DataException {
		
		DataSet dataSet = MySQL.executeQuery(conn, "SELECT introduce FROM t_select t WHERE t.`typeId` = 25 AND t.`selectKey` = 'AID'");
		Map<String, String> map = BeanMapUtils.dataSetToMap(dataSet);
		Map<String, String> json = JSONUtils.toHashMap(map.get("introduce"));
		//插入好币扣除记录
		Dao.Tables.t_award_user vipsum = new Dao().new Tables().new t_award_user();
		vipsum.userId.setValue(userId);
		vipsum.termId.setValue(json.get("termId"));
		vipsum.paramId.setValue(json.get("paramId"));
		vipsum.score.setValue("0");
		vipsum.awardInfoId.setValue(json.get("awardInfoId"));
		vipsum.createTime.setValue(new Date());
		vipsum.sendAward.setValue(1);
		return vipsum.insert(conn);
		//return a;
		
	}
	
	public Long addTrealName(Connection conn, String name,String cardNo) throws DataException {
		Dao.Tables.t_realname tRealname = new Dao(). new Tables(). new t_realname();
		tRealname.cardNo.setValue(cardNo);
		tRealname.realName.setValue(name);
		return tRealname.insert(conn);
	}
	
	public Long queryTrealName(Connection conn, String name,String cardNo) throws DataException {
		String command = "SELECT count(id) as cou FROM t_realname t WHERE t.`cardNo`='"+cardNo+"' AND t.`realName`='"+name+"';";
		DataSet ds = MySQL.executeQuery(conn, command);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		if (map!=null) {
			return Convert.strToLong(map.get("cou"), 0L);
		}
		return 0L;
	}
	
	/**
	 *功能：查询活动信息
	 * @param conn
	 * @param termId
	 * @return
	 * @throws DataException
	 */
	public Map<String, String> queryTodaySum(Connection conn, int paramId) throws DataException {
		String command = "SELECT COUNT(a.id) sumpid FROM t_award_user a WHERE a.`createTime`>DATE_FORMAT(NOW(),'%Y-%m-%d') AND a.`paramId`="+paramId;
		DataSet ds = MySQL.executeQuery(conn, command);
		command = null;
		return BeanMapUtils.dataSetToMap(ds);
		
	}
	
	/**
	 * 查询奖品的实际金额
	 * @param conn
	 * @param paramId  奖品ID
	 * @param ternId   期数
	 * @return
	 * @throws DataException 
	 */
	public Map<String, String> queryRealMoney(Connection conn,int paramId,int ternId) throws DataException{
		String command = "SELECT t.`realMoney` FROM t_award_info t WHERE t.`paramId` = "+paramId+" AND t.`termId` = "+ternId;
		DataSet ds = MySQL.executeQuery(conn, command);
		return BeanMapUtils.dataSetToMap(ds);
	}
	
}
