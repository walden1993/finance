package com.sp2p.action.front;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.NewsAndMediaReportService;
import com.sp2p.service.SysparService;

/**
 * 前台媒体报道
 * 
 * @author Administrator
 * 
 */
public class FrontMediaReportdAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontMediaReportdAction.class);
	private NewsAndMediaReportService mediaReportService;
	private SysparService sysparService;
	
	
	
	public void setSysparService(SysparService sysparService) {
        this.sysparService = sysparService;
    }

    public NewsAndMediaReportService getMediaReportService() {
		return mediaReportService;
	}

	public void setMediaReportService(
			NewsAndMediaReportService mediaReportService) {
		this.mediaReportService = mediaReportService;
	}

	/**
	 * 初始化下载数据
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQueryMediaReportdInit() throws SQLException,
			DataException {
		return SUCCESS;
	}

	/**
	 * 查询下载资料列表
	 * 
	 * @return String
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String frontQueryMediaReportdList() throws Exception {
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		int newsType = request.getInt("newsType", 0);
		request().setAttribute("newsType", newsType);
		
		String label = request.getString("label");
		
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(10);
		if(newsType != 0){
			mediaReportService.queryMediaReportPage1(pageBean,newsType,label);
		}else{
			mediaReportService.queryMediaReportPage(pageBean);
		}
		List<Map<String, Object>> lists = pageBean.getPage();

		// 截取内容字段 houli
		String content = "";
		if (lists != null) {
			for (Map<String, Object> map : lists) {
				String publishTime=map.get("publishTime").toString().trim();
				int index=publishTime.lastIndexOf(".");
				publishTime=publishTime.substring(0, index);
				map.put("publishTime", publishTime);
//				content = map.get("content").toString().replaceAll("(<(/*[a-zA-Z]+)>)|(&nbsp;)",""); 
				content = map.get("content").toString().replaceAll("(<[^>]*>)|(&nbsp;)","");
				content = content.replaceAll("\\s", "");
				if (content.length() > 60) {
					content = content.substring(0, 60);
					content = content + "...";
				}
				map.put("content", content);
			}
		}
		if (request("wap")!=null) {
			JSONObject wap = new JSONObject();
			wap.put("page", pageBean.getPage());
			JSONUtils.printObject(wap);
			return null;
		}
		return SUCCESS;
	}

	/**
	 * 根据Id获取下载资料详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frontQueryMediaReportById() throws Exception {
		Long id = request.getLong("id", -1);
		try {
			Map<String, String> map = null;
			map = mediaReportService.getMediaReportById(id);
			request().setAttribute("map", map);
			//JSONUtils.printObject(map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 根据Id获取媒体报道
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frontMedialinkId() throws Exception {
		Long id = request.getLong("id", -1);
		try {
			paramMap = mediaReportService.getMediaReportById(id);
			//修改浏览次数
			mediaReportService.updateMediaReportVisits(id);
			Map<String, String> premap = null;
			Map<String, String> aftermap = null;
			premap=mediaReportService.frontReportPreById(id);
			aftermap=mediaReportService.frontReportAfterById(id);
			
			if (request("wap")!=null) {
				JSONObject wap = new JSONObject();
				wap.put("paramMap", paramMap);
				wap.put("premap", premap);
				wap.put("aftermap", aftermap);
				JSONUtils.printObject(wap);
				return null;
			}
			
			request().setAttribute("premap", premap);
			request().setAttribute("aftermap", aftermap);
			
			//查询标签
			if (StringUtils.isNotBlank(paramMap.get("label"))) {
			     List<Map<String,Object>> labels =  sysparService.querySysparAllChild("id,selectName", "typeId=16 and id in ("+paramMap.get("label")+")", "id "+IConstants.SORT_TYPE_ASC, -1, -1);
	             request().setAttribute("labels",labels);
			}else {
			    request().setAttribute("labels",null);
			    request().setAttribute("relation", null);
            }
			 //相关阅读 数据来源，相同资讯类型下，与当前文章标签词匹配的文章
            List<Map<String, Object>>  relation = mediaReportService.queryRelationNews(id, paramMap.get("label"), paramMap.get("newsType"));
            request().setAttribute("relation", relation);
			//热门推荐  据来源为： 该类资讯[咨询分为[三好资本动态]和[互联网金融]2类]下，前5篇置顶文章，其中不包含当前打开的文章
			List<Map<String, Object>> hotnews =  mediaReportService.queryHotNews(id, paramMap.get("newsType"));
			request().setAttribute("hotnews", hotnews);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
     *功能：获取文章列表
	 * @return
	 */
	public String articleList(){
		try {
			pageBean.setPageSize(50);
			mediaReportService.queryArticlePage(pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	/**
	 * 根据Id获取媒体报道
	 * 
	 * @return
	 * @throws Exception
	 */
	public String frontArticleId() throws Exception {
		Long id = request.getLong("id", -1);
		try {
			paramMap = mediaReportService.queryArticleById(id);
			//修改浏览次数
//			mediaReportService.updateMediaReportVisits(id);
			Map<String, String> premap = null;
			Map<String, String> aftermap = null;
			premap=mediaReportService.frontReportPreById(id);
			aftermap=mediaReportService.frontReportAfterById(id);
			request().setAttribute("premap", premap);
			request().setAttribute("aftermap", aftermap);
			
			//查询标签
			if (StringUtils.isNotBlank(paramMap.get("label"))) {
			     List<Map<String,Object>> labels =  sysparService.querySysparAllChild("id,selectName", "typeId=16 and id in ("+paramMap.get("label")+")", "id "+IConstants.SORT_TYPE_ASC, -1, -1);
	             request().setAttribute("labels",labels);
			}else {
			    request().setAttribute("labels",null);
			    request().setAttribute("relation", null);
            }
			 //相关阅读 数据来源，相同资讯类型下，与当前文章标签词匹配的文章
            List<Map<String, Object>>  relation = mediaReportService.queryRelationNews(id, paramMap.get("label"), paramMap.get("newsType"));
            request().setAttribute("relation", relation);
			//热门推荐  据来源为： 该类资讯[咨询分为[三好资本动态]和[互联网金融]2类]下，前5篇置顶文章，其中不包含当前打开的文章
			List<Map<String, Object>> hotnews =  mediaReportService.queryHotNews(id, paramMap.get("newsType"));
			request().setAttribute("hotnews", hotnews);
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}
}
