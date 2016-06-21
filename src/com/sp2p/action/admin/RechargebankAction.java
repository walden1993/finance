package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.util.JSONUtils;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.service.admin.RechargebankService;
/**
 * 线下充值银行编辑
 * @author Administrator
 *
 */
public class RechargebankAction extends BaseFrontAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(RechargebankAction.class);
	private RechargebankService rechargebankService;
	
	public String querybankeditInit(){
		return SUCCESS;
	}
	
	public String querybankeditInfo() throws Exception{
		rechargebankService.queryRechargebanklist(pageBean);
		return SUCCESS;
	}
	
	public String updateRechargeBankInit() throws Exception{
		long id = request.getLong("id", -1);
		paramMap = rechargebankService.queryrechargeBankById(id);
		return SUCCESS;
	}
	
	
	public String queryrechargeBank() throws Exception{
		Map<String,String> bankMap = new HashMap<String, String>();
		long id = Convert.strToLong(paramMap.get("id")+"", -1);
		bankMap = rechargebankService.queryrechargeBankById(id);
		JSONUtils.printObject(bankMap);
		return null;
	}
	
	
	
	
	/**
	 * 添加充值银行
	 * @return
	 * @throws SQLException
	 */
	public String addRechargeBankInit() throws SQLException{
		return SUCCESS;
	}
	/**
	 * 添加充值银行
	 * @return
	 * @throws Exception 
	 */
	public String addRechargeBankInfo() throws Exception{
		String bankname = paramMap.get("bankname")+"";
		String Account = paramMap.get("Account")+"";
		String accountbank = paramMap.get("accountbank")+"";
		String bankimage = paramMap.get("bankimage")+"";
		String accountname = paramMap.get("accountname")+"";
	    rechargebankService.addRechargeBankInit(bankname, Account, accountbank, bankimage,accountname);
	    return SUCCESS;
	}
	
	
	
	public String updateRechargeBankInfo() throws Exception{
		long id = Convert.strToLong(paramMap.get("id"), -1);
		String bankname = paramMap.get("bankname")+"";
		String Account = paramMap.get("Account")+"";
		String accountbank = paramMap.get("accountbank")+"";
		String bankimage = paramMap.get("bankimage")+"";
		String accountname = paramMap.get("accountname")+"";
		rechargebankService.updaterechargeBankById(id, bankname, Account, accountbank, bankimage,accountname);
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	public void setRechargebankService(RechargebankService rechargebankService) {
		this.rechargebankService = rechargebankService;
	}

	public RechargebankService getRechargebankService() {
		return rechargebankService;
	}
	
	

}
