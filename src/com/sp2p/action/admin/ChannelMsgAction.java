package com.sp2p.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.allinpay.ets.client.StringUtil;
import com.sp2p.action.front.BaseFrontAction;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Admin;
import com.sp2p.service.admin.ChannelMsgService;

/**
 * 广告管理 ，渠道管理
 * @author bao
 */
public class ChannelMsgAction extends BaseFrontAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(ChannelMsgAction.class);
	private ChannelMsgService channelMsgService ;
	
	public ChannelMsgService getChannelMsgService() {
		return channelMsgService;
	}



	public void setChannelMsgService(ChannelMsgService channelMsgService) {
		this.channelMsgService = channelMsgService;
	}



	/**
	 *功能：得到查询列表
	 * @return
	 */
	public String channelMsgList(){
		log.info("------------------------------channelMsgList.");
		pageBean.setPageSize(10);
		log.info("-----pageBean.pageNum="+pageBean.pageNum);
		String sqlId = "advert.channelMsgList";
		channelMsgService.commonQuery(pageBean, paramMap,sqlId);//channelMsgList(pageBean,paramMap);
		int pageNum = (int) (pageBean.getPageNum() - 1) * pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	
	
	public String channelMsgGotoAdd(){
		log.info("------------------------------channelMsgGotoAdd.");
		return SUCCESS;
	}
	/**
	 *功能：新增渠道
	 * @return
	 */
	public String channelMsgAdd(){
		log.info("------------------------------channelMsgAdd.");
		String ctype = paramMap.get("ctype");
		String channelName = paramMap.get("channelName");
		String channelDesc = paramMap.get("channelDesc");//渠道简称
		String sysCode = paramMap.get("sysCode");
		String channelCode = paramMap.get("channelCode");
		String remark = paramMap.get("remark");
		
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String adminName = admin.getUserName();
		Map <String, String>pmap = new HashMap<String, String>();
		pmap.put("ctype", ctype);
		pmap.put("channelName", channelName);
		pmap.put("channelDesc", channelDesc);
		pmap.put("sysCode", sysCode);
		pmap.put("channelCode", channelCode);
		pmap.put("remark", remark);
		pmap.put("adminName", adminName);
		
		channelMsgService.commonSave("advert.channelAdd",pmap);
		return SUCCESS;
	}
	
	
	/**
	 *功能：去新增页面--
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String gotoAdvertPage(){
		log.info("-----gotoAdvertPage-----");

		//查询渠道简称
		List<HashMap> list = channelMsgService.queryList("advert.queryChannelSimpleName", null);
		request().setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 *功能：保存广告页信息
	 * @return
	 */
	public String addAdvertPage(){
		log.info("------------------------------addAdvertPage.");
		String channelId = paramMap.get("channelId");//渠道简称
		String pageName = paramMap.get("pageName");
		
		Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
		String adminName = admin.getUserName();
		Map <String, String>pmap = new HashMap<String, String>();
		pmap.put("channelId", channelId);
		pmap.put("pageName", pageName);
		pmap.put("adminName", adminName);
		channelMsgService.commonSave("advert.addAdvertPage",pmap);
		return SUCCESS;
	}
	/**
	 *功能：广告页展示
	 * @return
	 */
	public String advertPageList() {
		log.info("------------------------------advertPageList.");
		pageBean.setPageSize(10);
		log.info("-----pageBean.pageNum=" + pageBean.pageNum);
		String sqlId = "advert.advertPageList";
		channelMsgService.commonQuery(pageBean, paramMap, sqlId);// channelMsgList(pageBean,paramMap);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}
	
	
	
	/**
	 *功能：跳转到编辑页
	 * @return
	 */
	public String gotoChannelPageEdit() {
		log.info("------------------------------gotoChannelPageEdit.");

		//查询渠道简称
		List<HashMap> list = channelMsgService.queryList("advert.queryChannelSimpleName", null);
		request().setAttribute("list", list);
		
		String sqlId = "advert.queryPageById";
		paramMap.put("id", request().getParameter("id"));
		Map retMap = (HashMap) channelMsgService.queryOne(sqlId, paramMap);// channelMsgList(pageBean,paramMap);
		request().setAttribute("retMap", retMap);
		return SUCCESS;
	}
	
	/**
	 *功能：跳转到编辑页
	 * @return
	 */
	public String editAdvertPage() {
		log.info("------------------------------editAdvertPage.");
		String sqlId = "advert.editAdvertPage";
		channelMsgService.commonUpdate(sqlId, paramMap);// channelMsgList(pageBean,paramMap);
		return SUCCESS;
	}
	
	
	/**
	 *功能：跳转到新增广告页
	 * @return
	 */
	public String advertInfoGotoAdd() {
		log.info("------------------------------editAdvertPage.");
//		String sqlId = "advert.editAdvertPage";
//		channelMsgService.commonUpdate(sqlId, paramMap);

		//查询渠道简称
		List<HashMap> channelList = channelMsgService.queryList("advert.queryChannelSimpleName", null);
		request().setAttribute("channelList", channelList);
		
		
		return SUCCESS;
	}
	/**
	 *功能：跳转到新增广告页
	 * @return
	 */
	public String queryPageByChannel() {
		log.info("------------------------------queryPageByChannel.");

		//查询渠道简称
		List<HashMap> pageList = channelMsgService.queryList("advert.queryPageByChannel", paramMap);
		request().setAttribute("pageList", pageList);
		return SUCCESS;
	}
	
	/**
	 *功能：保存广告信息
	 * @return
	 */
	public String pageInfoAdd(){
//		String channelId = paramMap.get("channelId");
//		String pageId = paramMap.get("pageId");//
//		String advertName = paramMap.get("advertName");
//		String pagePosition = paramMap.get("pagePosition");
//		String dispalyStyle = paramMap.get("dispalyStyle");
//		String advertStyle = paramMap.get("advertStyle");
//		String url = paramMap.get("url");
//		String infoName = paramMap.get("infoName");
//		String ifee = paramMap.get("ifee");//
//		String feeType = paramMap.get("feeType");//
//		String feeParam = paramMap.get("feeParam");
//		String feeParamCell = paramMap.get("feeParamCell");
//		String coStartTime = paramMap.get("coStartTime");//
//		String coEndTime = paramMap.get("coEndTime");//
//		String ieffect = paramMap.get("ieffect");//
//		String remark = paramMap.get("remark");
//		String createBy = paramMap.get("createBy");
		
		if (StringUtil.isEmpty(paramMap.get("pageId"))){
			paramMap.put("pageId", null);
		}

		if (StringUtil.isEmpty(paramMap.get("ifee"))){
			paramMap.put("ifee", null);
		}
		if (StringUtil.isEmpty(paramMap.get("feeType"))){
			paramMap.put("feeType", null);
		}
		if (StringUtil.isEmpty(paramMap.get("coStartTime"))){
			paramMap.put("coStartTime", null);
		}
		if (StringUtil.isEmpty(paramMap.get("coEndTime"))){
			paramMap.put("coEndTime", null);
		}
		if (StringUtil.isEmpty(paramMap.get("ieffect"))){
			paramMap.put("ieffect", null);
		}
		
		channelMsgService.commonSave("advert.pageInfoAdd", paramMap);
		
		return SUCCESS;
	}
	
}
