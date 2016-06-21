package com.sp2p.action.admin;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.action.BasePageAction;
import com.sp2p.service.PublicModelService;

/**
 * 客服中心帮助问题处理
 * 
 * @author li.hou
 * 
 */
@SuppressWarnings("unchecked")
public class LinksAction extends BasePageAction {
	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(LinksAction.class);

	private PublicModelService linksService;

	public PublicModelService getLinksService() {
		return linksService;
	}

	public void setLinksService(PublicModelService linksService) {
		this.linksService = linksService;
	}

	/**
	 * 添加帮助中心数据初始化
	 * 
	 * @return String
	 * @throws DataException
	 * @throws SQLException
	 * @throws
	 */
	public String addLinksInit() throws Exception {
		// 获得数据库中最大的序列号，不用用户自己填写
		Map<String, String> map = linksService.getLinkMaxSerial();
		long val = Convert.strToLong(map.get("max(serialCount)"), 0) + 1;
		paramMap.put("serialCount", val + "");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paramMap.put("publishTime", sf.format(new Date()));
		return SUCCESS;
	}

	/**
	 * 添加帮助问题数据
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String addLinks() throws Exception {//		
		String companyName = paramMap.get("companyName"); // 公司名称
		String companyImg = paramMap.get("companyImg");// 图片
		String companyURL = paramMap.get("companyURL");// 公司网址
		int serialCount = Convert.strToInt(paramMap.get("serialCount"), 1); // 序号(链接Id)
		//2014-10-21 09:06:28 hjh 新增类型、描述
		int type  = Convert.strToInt(paramMap.get("type"), -1);//类型  1友情链接  5合作机构
		String description = paramMap.get("description");//描述
		String dateTime = paramMap.get("publishTime");
		@SuppressWarnings("unused")
		String messageInfo = "添加失败";
		try {
			long result = linksService.addLinks(companyName, companyImg,
					companyURL, serialCount, dateTime,type,description);
			if (result > 0) {
				messageInfo = "添加成功";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	
	/**
	 * 修改初始化
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateLinksInit() throws Exception {
		long typeId = request.getLong("commonId",-1);
		try {
			paramMap = linksService.queryLinksInfoByid(typeId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		request().setAttribute("commonId", typeId);
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return String
	 * @throws DataException
	 * @throws SQLException
	 * @throws Exception
	 */
	public String updateLinks() throws Exception {
		String companyName = Convert
				.strToStr(paramMap.get("companyName"), null); // 公司名称
		String companyImg = Convert.strToStr(paramMap.get("companyImg"), null);// 图片
		String companyURL = Convert.strToStr(paramMap.get("companyURL"), null);// 公司网址
		int id = Convert.strToInt(paramMap.get("id"), -1); // 序号(链接Id)
		int serialCount = Convert.strToInt(paramMap.get("serialCount"), 1);
		String dateTime = Convert.strToStr(paramMap.get("publishTime"), null);
		//2014-10-21 09:06:28 hjh 新增类型、描述
        int type  = Convert.strToInt(paramMap.get("type"), -1);//类型  1友情链接  5合作机构
        String description = paramMap.get("description");//描述
		@SuppressWarnings("unused")
		String messageInfo = "修改失败";
		try {
			long result = linksService.updateLinks(id,companyName, companyImg,
					companyURL, serialCount, dateTime,type,description);
			if (result > 0) {
				messageInfo = "修改成功";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String queryLinksListPageInit() throws DataException, SQLException {

		return SUCCESS;
	}

	public String queryLinksListPage() throws Exception {
		try {
			linksService.queryLinksPage(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 后台查询：友情链接,合作机构列表
	 * @return
	 * @throws Exception
	 */
	public String queryLinksListPageBack() throws Exception {
		try {
			linksService.queryLinksListPageBack(pageBean);
			int pageNum = (int) (pageBean.getPageNum() - 1)
					* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	/**
	 * 删除
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String deleteLinks() throws Exception {
		String linksIds = request.getString("commonId");
		String[] allIds = linksIds.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		linksService.deleteLinkss(linksIds);
		return SUCCESS;
	}

	/**
	 * 首页滚动图片
	 * 
	 * @return
	 * @author Administrator
	 */
	public String queryIndexRollImgInit() {
		return SUCCESS;
	}

	public String queryIndexRollImgInfo() throws Exception {
		linksService.queryIndexRollImgPage(pageBean);
		int pageNum = (int) (pageBean.getPageNum() - 1)
				* pageBean.getPageSize();
		request().setAttribute("pageNum", pageNum);
		return SUCCESS;
	}

	public String deleteIndexRollImg() throws Exception {
		String linksIds = request.getString("commonId");
		String[] allIds = linksIds.split(",");// 进行全选删除的时候获得多个id值
		if (allIds.length > 0) {
			long tempId = 0;
			for (String str : allIds) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}
		linksService.deleteIndexRollImg(linksIds);
		return SUCCESS;
	}

	public String updateIndexRollImgInfo() throws Exception {
		String companyImg = Convert.strToStr(paramMap.get("companyImg"), null);// 图片
		int ordershort = Convert.strToInt(paramMap.get("ordershort"), 1); // 序号(链接Id)
		int id = Convert.strToInt(paramMap.get("id"), -1); // 序号(链接Id)
        int serialCount = Convert.strToInt(paramMap.get("serialCount"), 1);
		String dateTime = Convert.strToStr(paramMap.get("publishTime"), null);
		String companyURL = paramMap.get("companyURL");
		@SuppressWarnings("unused")
		String messageInfo = "修改失败";
		try {
			long result = linksService.updateIndexRollImg(id,companyImg,
					serialCount, dateTime,companyURL, ordershort);
			if (result > 0) {
				messageInfo = "修改成功";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	public String updateIndexRollImgInit() throws Exception {
		long typeId = request.getLong("commonId",-1);
		try {
			paramMap = linksService.queryLinksInfoByids(typeId);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		request().setAttribute("commonId", typeId);
		return SUCCESS;
	}

	public String addIndexRollImgInit() throws Exception {
		// 获得数据库中最大的序列号，不用用户自己填写
		Map<String, String> map = linksService.getMaxIndexRollImgSerial();
		long val = Convert.strToLong(map.get("max(serialCount)"), 0) + 1;
		paramMap.put("serialCount", val + "");
		return SUCCESS;
	}

	public String addIndexRollImgInfo() throws Exception {//		
		String companyImg = paramMap.get("companyImg");// 图片
		int serialCount = Convert.strToInt(paramMap.get("serialCount"), 1); // 序号(链接Id)
		int ordershort = Convert.strToInt(paramMap.get("ordershort"), 1); // 序号(链接Id)
		String dateTime = paramMap.get("publishTime");
		int cardStatus = Convert.strToInt(paramMap.get("cardStatus"), -1);// 图片作用，类型
		String companyURL = paramMap.get("companyURL");
		@SuppressWarnings("unused")
		String messageInfo = "添加失败";
		try {
			long result = linksService.addIndexRollImg(companyImg, serialCount,
					dateTime, companyURL,ordershort, cardStatus);
			if (result > 0) {
				messageInfo = "添加成功";
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
}
