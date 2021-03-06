package com.sp2p.action.front;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.service.PublicModelService;


/**
 * 前台友情链接
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class FrontLinksAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontLinksAction.class);
	private PublicModelService linksService;
	public PublicModelService getLinksService() {
		return linksService;
	}

	public void setLinksService(PublicModelService linksService) {
		this.linksService = linksService;
	}

	/**
	 * 查询下载资料列表
	 * @return String
	 * @throws Exception 
	 * @throws UnsupportedEncodingException
	 */
	public String frontQueryMediaReportdList() throws Exception{
		String pageNum = (String) (request.getString("curPage") == null ? ""
				: request.getString("curPage"));
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		if (IConstants.ISDEMO.equals("1")) {
			pageBean.setPageSize(100);
		}else{
			pageBean.setPageSize(IConstants.PAGE_SIZE_20);
		}
		
		
		linksService.queryLinksPage(pageBean);
		return SUCCESS;
	}
	
	/**
	 * 根据Id获取下载资料详情
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String frontQueryMediaReportById(){
		Long id = request.getLong("id",-1);
		try {
			
			Map<String, String> map=linksService.queryLinksInfoByid(id);
			JSONUtils.printObject(map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		
		return null;
	}
	
	



}
