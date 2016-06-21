package com.sp2p.action.admin;



import java.util.HashMap;
import java.util.Map;


import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.shove.Convert;
import com.shove.util.SqlInfusion;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.SEOConfigService;
import com.sp2p.util.PublicFunction;

public class SEOConfigAction extends BasePageAction {
	public static Log log = LogFactory.getLog(SEOConfigAction.class);
	
	private SEOConfigService SEOConfigService;

	public SEOConfigService getSEOConfigService() {
		return SEOConfigService;
	}

	public void setSEOConfigService(SEOConfigService configService) {
		SEOConfigService = configService;
	}

	/**
	 * 修改app版本号标准初始化
	 * @return
	 * @throws Exception 
	 */
	public String updateAppOptionsInit() throws Exception{
		Map<String, String> appMap = new HashMap<String, String>();
		String content = PublicFunction.GetOption("app_upcontent");
		String curNum = Convert.strToStr(PublicFunction.GetOption("app_version"), "");
		String appforce = Convert.strToStr(PublicFunction.GetOption("app_force"), "");
		String version_name = Convert.strToStr(PublicFunction.GetOption("app_version_name"), "");
		String app_url = Convert.strToStr(PublicFunction.GetOption("app_url"), "");
		appMap.put("content", content);
		appMap.put("curNum", curNum);
		appMap.put("appforce", appforce);
		appMap.put("url", app_url);
		appMap.put("version_name", version_name);
		request().setAttribute("appMap", appMap);
 		return SUCCESS;
	}

	/**
	 * 更新app版本号标准设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateAppOptions() throws Exception{
		JSONObject obj = new JSONObject();
		String curNum = SqlInfusion.FilteSqlInfusion(paramMap.get("curNum"));
		String content = SqlInfusion.FilteSqlInfusion(paramMap.get("content"));
		String appforce = SqlInfusion.FilteSqlInfusion(paramMap.get("appforce"));
		String appurl = SqlInfusion.FilteSqlInfusion(paramMap.get("appurl"));
		String version_name = SqlInfusion.FilteSqlInfusion(paramMap.get("version_name"));
		if(StringUtils.isBlank(content)){
			obj.put("msg","更新内容不能为空");
			JSONUtils.printObject(obj);
			return null;
		}
		if(StringUtils.isBlank(curNum)){
			obj.put("msg","版本号不能为空");
			JSONUtils.printObject(obj);
			return null;
		}
		Long result = -1L;
		result = SEOConfigService.updateOptions("app_version", curNum);
		if(result < 0){
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		}
		result = SEOConfigService.updateOptions("app_upcontent", content);
		if(result < 0){
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
			
		}
		result = SEOConfigService.updateOptions("app_force", appforce);
		if(result < 0){
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
			
		}
		result = SEOConfigService.updateOptions("app_url", appurl);
		if(result < 0){
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
			
		}
		result = SEOConfigService.updateOptions("app_version_name", version_name);
		if(result < 0){
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
			
		}
		obj.put("msg", "1");
		JSONUtils.printObject(obj);
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_seoconfig", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新移动端版本", 2);
		return null;
	}
	
	/**
	 * 更新SEO标准设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateSEOConfig() throws Exception{
		JSONObject obj = new JSONObject();
		String title = SqlInfusion.FilteSqlInfusion(paramMap.get("title"));
		String keywords = SqlInfusion.FilteSqlInfusion(paramMap.get("keywords"));
		String description = SqlInfusion.FilteSqlInfusion(paramMap.get("description"));
		String otherTags = SqlInfusion.FilteSqlInfusion(paramMap.get("otherTags"));
		Long result = -1L;
		result = SEOConfigService.updateSEOConfig( title, description, keywords,1,otherTags);
		if(result > 0){
			IConstants.SEO_TITLE = title;
			IConstants.SEO_KEYWORDS = keywords;
			IConstants.SEO_DESCRIPTION = description;
			IConstants.SEO_SITEMAP = 1;
			IConstants.SEO_OTHERTAGS = otherTags;
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
		}
		else{
			obj.put("msg",IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
		}
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		operationLogService.addOperationLog("t_seoconfig", admin.getUserName(),IConstants.UPDATE, admin.getLastIP(), 0, "更新ＳＥＯ标准设置", 2);
		return SUCCESS;
	}
	
	/**
	 * 查看SEO标准设置 
	 * @return
	 * @throws Exception 
	 */
	public String querySEOConfig() throws Exception{
		Map<String, String> seoMap = null;
		seoMap = SEOConfigService.querySEOConfig();
		request().setAttribute("seoMap", seoMap);
 		return SUCCESS;
	}
	
	
	/**
	 * 查询平台注册码设置参数信息
	 * @return
	 * @throws Exception 
	 */
	public String queryRegistCode() throws Exception{
		return SUCCESS;
	}
	
}
