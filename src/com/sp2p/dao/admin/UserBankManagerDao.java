package com.sp2p.dao.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.util.BeanMapUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;

public class UserBankManagerDao {

	public Map<String,String> queryBankCardInfos(Connection conn,Long bankId,int limitStart,int limitCount) 
	    throws SQLException, DataException{
		Dao.Views.t_bankcard_lists t_info = new Dao().new Views().new t_bankcard_lists();
		DataSet dataSet = t_info.open(conn, "", " id=" + bankId, "",limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
	}
	
	/**
	 * 更新银行卡审核数据信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long updateBankInfo(Connection conn,Long checkUserId,Long bankId,String remark,Integer result) throws SQLException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		t_info.remark.setValue(remark);
		t_info.checkUser.setValue(checkUserId);
		t_info.cardStatus.setValue(result);
		t_info.checkTime.setValue(new Date());
		return t_info.update(conn, " id="+bankId);
    }
	
	   /**
     * 更新银行卡审核数据信息
     * @param conn
     * @return
     * @throws SQLException
     */
    public Long updateBankInfo(Connection conn,Long checkUserId,Long bankId,String remark,Integer result,int provinceId,int cityId) throws SQLException{
        Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
        t_info.remark.setValue(remark);
        t_info.checkUser.setValue(checkUserId);
        t_info.cardStatus.setValue(result);
        t_info.checkTime.setValue(new Date());
        t_info.province.setValue(provinceId);
        t_info.city.setValue(cityId);
        return t_info.update(conn, " id="+bankId);
    }
	
    /**
	 * 更新银行卡审核数据信息
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public Long updateBankInfoForPay(Connection conn,Map map) throws SQLException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		t_info.bankName.setValue(map.get("bankName"));
		t_info.branchBankName.setValue(map.get("branchBankName"));
		return t_info.update(conn, " id="+map.get("id"));
    }
    
	/**
	 * 更新银行卡变更信息
	 * @param conn
	 * @param checkUserId
	 * @param bankId
	 * @param remark
	 * @param result
	 * @return
	 * @throws SQLException 
	 */
	public Long updateModifyBankInfo(Connection conn,Long checkUserId,Long bankId,
			String remark,Integer result,String bankName
			,String branchBankName,String bankCardNo,String date,boolean success) throws SQLException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		t_info.remark.setValue(remark);
		t_info.checkUser.setValue(checkUserId);
		t_info.cardStatus.setValue(result);
		t_info.checkTime.setValue(new Date());
		
		if(success){//审核成功
			//将修改后的银行卡信息变为现在的银行卡信息
			t_info.bankName.setValue(bankName);
			t_info.branchBankName.setValue(branchBankName);
			t_info.cardNo.setValue(bankCardNo);
			t_info.commitTime.setValue(date);
			
			//将现在的银行卡信息进行删除
			t_info.modifiedBankName.setValue("");
			t_info.modifiedBranchBankName.setValue("");
			t_info.modifiedCardNo.setValue("");
			t_info.modifiedCardStatus.setValue(null);
			t_info.modifiedTime.setValue(null);
		}else{//审核失败
			t_info.modifiedCardStatus.setValue(IConstants.BANK_FAIL);//修改的状态失败
			t_info.cardStatus.setValue(IConstants.BANK_SUCCESS);//以前绑定的银行卡的状态为成功
		}
		
		return t_info.update(conn, " id="+bankId);
	}
	
	public Map<String,String> queryOneBankInfo(Connection conn,Long bankId,int limitStart,int limitCount) 
	   throws SQLException, DataException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		DataSet dataSet = t_info.open(conn, "", " id=" + bankId, "",limitStart, limitCount);
		return BeanMapUtils.dataSetToMap(dataSet);
		
	}
	
	public Long updateChangeBankInfo(Connection conn,Long bankId,String bankName,String subBankName,String bankCard,
			int status,Date date,boolean modify)
	    throws SQLException{
		Dao.Tables.t_bankcard t_info = new Dao().new Tables().new t_bankcard();
		t_info.modifiedBankName.setValue(bankName);
		t_info.modifiedBranchBankName.setValue(subBankName);
		t_info.modifiedCardNo.setValue(bankCard);
		t_info.modifiedTime.setValue(date);
		if(modify){//申请银行卡变更
			t_info.modifiedCardStatus.setValue(status);
		}else{//取消银行卡变更
			t_info.cardStatus.setValue(status);
			t_info.modifiedCardStatus.setValue(null);
		}
		return t_info.update(conn, " id="+bankId);
    }
}
