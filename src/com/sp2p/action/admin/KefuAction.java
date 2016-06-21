package com.sp2p.action.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp2p.service.HelpAndServicerService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.web.action.BasePageAction;

/**
 * 客服管理
 * @author zhongchuiqing
 *
 */
@SuppressWarnings("unchecked")
public class KefuAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(KefuAction.class);
	
	private HelpAndServicerService kefusService;
		
	/**
	 * 初始化分页查询客服信息列表
	 * @return
	 */
	public String queryKefuListInit(){
		return SUCCESS;
	}
	
	/**
	 * 分页查询客服信息列表
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String queryKefuListPage()throws Exception{
		try {
			kefusService.queryKefuPage(pageBean);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	/**
	 * 查看客服下的投资详情初始化
	 * @return
	 * @throws Exception 
	 */
	public String queryKefuOfInvestorInit() throws Exception{
    	Long kefuid=request.getLong("id", -1);
			 try {
				paramMap=kefusService.getTeamById(kefuid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return SUCCESS;
	}
	/**
	 * 查看客服下的投资详情
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryKefuOfInvestorInfo() throws Exception{
		Long kefuid=Convert.strToLong(paramMap.get("kefuid")+"", -1); 
		kefusService.queryKefuOfInvest(pageBean,kefuid);
		return SUCCESS;
	}
	
	
	/**
	 * 查看客服分配初始化
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryKefuForInit() throws Exception{
		Long kefuid=request.getLong("id", -1);
    	paramMap=kefusService.getTeamById(kefuid);
    	request().setAttribute("flag", "a");
		return SUCCESS;
	}
	/**
	 * 查看客服下的分配
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryKefuForInfo() throws Exception{
		Long kefuid=Convert.strToLong(paramMap.get("kefuid")+"", -1);
		request().setAttribute("fid", kefuid);
		String isF = paramMap.get("unfor")==null?"-1":paramMap.get("unfor")+"";
	    int flag = 3;
		if("1".equals(isF)){
			flag = 1;
		}else if("2".equals(isF)){
			flag = 2;
		}else if("3".equals(isF)){
			flag = 3;
		}else{
			flag = 3;
		}
		String kefuname = paramMap.get("kefuname");
		String username = paramMap.get("username");
		List<Map<String, Object>> kefulist = kefulist = kefusService.querykefulist();
		request().setAttribute("kefulist", kefulist);
		request().setAttribute("kefuname", kefuname);
		request().setAttribute("flag", flag);
		kefusService.queryKefuForInfo(pageBean,kefuname,username,flag);
		return SUCCESS;
	}
	/**
	 * 分配添加客服
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException 
	 */
	public String addUserForkefubyId() throws Exception{
		
		String ids = paramMap.get("ids")+"";
		String kefuId = paramMap.get("kid")+"";
		long result = -1;
		result =	kefusService.updateUserKefu(ids,kefuId);
		if(result>0){
			JSONUtils.printStr("1");
			return null;	
		}
		
		return null;
	}
	
	
	/**
	 * 选择客服
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 */
	public String addUserForkefu() throws Exception{
		String kefuid = paramMap.get("selectid")+"";
		String userid = paramMap.get("userId")+"";
		long result = -1;
		result =	kefusService.updateUserKefu_(Convert.strToLong(kefuid, -1),Convert.strToLong(userid, -1));
		if(result>0){
			JSONUtils.printStr("1");
			return null;	
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 添加客服信息初始化
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String addKefuInit()throws SQLException,DataException{
		return SUCCESS;
	}
	
	/**
	 * 添加客服信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String addKefu()throws Exception{
		String kefuName=paramMap.get("name");		
		String imgPath=paramMap.get("kefuImage");
		String qq=paramMap.get("QQ");
		String remark=paramMap.get("remark");
		@SuppressWarnings("unused")
		String message="添加失败";
        Long result=-1L;
		try {
			kefusService.addKefu(kefuName, imgPath, qq, remark);
			if(result>0){
				message="添加成功";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	

	
	/**
	 * 更新初始化，根据Id获取客服信息详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateKefuInit()throws Exception{
	    	Long id=request.getLong("id", -1);
		try {
			 paramMap=kefusService.getTeamById(id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 更新客服信息
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateKefu()throws Exception{
		Long id=Convert.strToLong(paramMap.get("id"), -1L);
		String kefuName=paramMap.get("name");		
		String imgPath=paramMap.get("kefuImage");
		String qq=paramMap.get("QQ");
		String remark=paramMap.get("remark");
		@SuppressWarnings("unused")
		String message="更新失败";
		
		try {
			
			
			long result =kefusService.updateKefu(id, kefuName, imgPath, qq, remark);
			if (result > 0) {
				message = "更新成功";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
		
	}
	
	
	/**
	* 删除团队介绍数据
	* @throws DataException
	* @throws SQLException
	* @return String
	*/
	public String deleteKefu() throws Exception{
		String dynamicIds = request.getString("id");
		String[] newsids = dynamicIds.split(",");
		if (newsids.length > 0) {
			long tempId = 0;
			for (String str : newsids) {
				tempId = Convert.strToLong(str, -1);
				if(tempId == -1){
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		
		try {
			kefusService.deleteKefu(dynamicIds, ",");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return SUCCESS;
	}

	public HelpAndServicerService getKefusService() {
		return kefusService;
	}

	public void setKefusService(HelpAndServicerService kefusService) {
		this.kefusService = kefusService;
	}
	
	

}
