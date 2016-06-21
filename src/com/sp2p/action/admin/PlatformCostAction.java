package com.sp2p.action.admin;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.service.admin.PlatformCostService;

/**
 * 平台收费标准
 * @author C_J
 *
 */
public class PlatformCostAction  extends BasePageAction {

	private static final long serialVersionUID = 1L;
	
	public static Log log = LogFactory.getLog(PlatformCostAction.class);
	
	private PlatformCostService platformCostService;

	
	/**
	 *  平台收费初始化
	 * @return
	 * @throws Exception
	 */
	public String showPlatformCostInit() throws Exception {
		int type = request.getInt("typess", 1);
		request().setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 所有平台收费情况
	 * @return
	 * @throws Exception
	 */
	public String showPlatformCostList() throws Exception {
		int type = request.getInt("type", -1);
		platformCostService.queryPlatformCostAll(pageBean,type);//分页显示
		int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);

		return SUCCESS;
	}
  
	/**
	 * 修改初始化
	 * @return
	 * @throws Exception
	 */
	public String updatePlatformCostbyIdInit() throws Exception {
		request().setAttribute("id", request.getString("id"));
		return SUCCESS;
	}
	
	
	/**
	 * 根据ID修改平台收费情况
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String updatePlatformCost() throws Exception {
	  int id=request.getInt("id",-1);
	  double costFee=request.getDouble("costFee", -1);
	  int   costMode=request.getInt("costMode", -1);
	  if(costMode==1)
	  {
		  if(!(costFee>=0 && costFee <=100))
		  {
			  JSONUtils.printStr("3");
			  return null;
		  }
	  }else
	  {
		  if(costFee<0)
		  {
			  JSONUtils.printStr("4");
			  return null;
		  }
	  }
	  Long result= platformCostService.updatePlatformCostById(costFee, id,getPlatformCost());
	  if(result<0){
			JSONUtils.printStr("1");
			return null;
		}else
		{
			JSONUtils.printStr("2");
			
			return null;
		}	
		
	}
	
	
	 /**
	  * 修改 隐藏显示
	  * @return
	  */
	public String updateShow_view() {
		int id= request.getInt("id",-1);
		int show_view = request.getInt("show_view", -1);
		long result = -1L;
		try {
			result = platformCostService.updateShow_view(id, show_view);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		 if  (result > 0) 
			 return INPUT;
		 
		 return SUCCESS;
	}
	/**
	 * 根据ID查询平台收费情况
	 * @return
	 * @throws Exception
	 */
	public String updatePlatformCostbyIdInfo() throws Exception {
		 int id=request.getInt("paramMap.id",-1);
		 Map<String, String> map=platformCostService.queryPlatformCostById(id);
		 request().setAttribute("maps", map);
		return SUCCESS;
	}


	public void setPlatformCostService(PlatformCostService platformCostService) {
		this.platformCostService = platformCostService;
	}
	
}
