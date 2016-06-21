package com.sp2p.action.admin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp2p.constants.IConstants;
import com.sp2p.service.HelpAndServicerService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.JSONUtils;
import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;

/**
 * 客服中心帮助问题类型处理
 * @author li.hou
 *
 */
@SuppressWarnings("unchecked")
public class HelpCategoryAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(HelpCategoryAction.class);
	
	private HelpAndServicerService callCenterService;
	private List<Map<String,Object>> typeList;
	
	public HelpAndServicerService getCallCenterService() {
		return callCenterService;
	}

	public void setCallCenterService(HelpAndServicerService callCenterService) {
		this.callCenterService = callCenterService;
	}

	public String queryHelpCategoryListPage() throws SQLException, DataException{
		request().setAttribute("typeList", typeList);
		return SUCCESS;
		
	}
	
	/**
	 * 添加帮助问题分类(初始化)
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String addHelpCategoryInit() throws SQLException, DataException{
		return SUCCESS;
	}
	
	/**
	 * 添加帮助问题分类
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @throws AdminHelpMessageException 
	 */
	public String addHelpCategory() throws Exception{
		
		String helpCategoryName = Convert.strToStr(paramMap.get("name").toString().trim(), null);
		try {
			callCenterService.addCategory(helpCategoryName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改分类(初始化)
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String updateHelpCategoryInit() throws Exception{
		Long typeId = request.getLong("typeId", -1);
		paramMap = callCenterService.queryCategoryById(typeId);
		return SUCCESS;
	}
	
	/**
	 * 修改分类
	 * @return
	 * @throws SQLException 
	 * @throws DataException 
	 */
	public String updateHelpCategory() throws Exception {
		Long typeId = Convert.strToLong(paramMap.get("id"), -1);
		String helpDescribe = Convert.strToStr(paramMap.get("helpDescribe"), null);
		
		try {
			callCenterService.updateCategory(typeId, helpDescribe);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 删除分类
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String deleteHelpCategory() throws Exception{
		Long id = request.getLong("typeId",-1);
		callCenterService.deleteCategory(id);
		//删除此分类下的分页数据
		CacheManager.clearStartsWithAll(IConstants.CACHE_BZZX_PAGE_+id+"_");
		CacheManager.clearStartsWithAll(IConstants.CACHE_BZZX_INFO_);
		return SUCCESS;
	}
	
	public String updateCategoryIndex() throws Exception{
		String ids = request.getString("ids");
		
		try{
			Long result = callCenterService.updateCategoryIndex(ids);
		
			if(result <= 0){
				JSONUtils.printStr("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return null;
	}

	public List<Map<String, Object>> getTypeList() throws Exception {
		if(typeList == null)
			typeList = callCenterService.queryHelpTypeList();//查询帮助类型列表
		return typeList;
	}

	public void setTypeList(List<Map<String, Object>> typeList) {
		this.typeList = typeList;
	}

}
