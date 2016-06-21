package com.sp2p.action.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.allinpay.ets.client.StringUtil;
import com.sp2p.action.front.FrontMyPaymentAction;
import com.sp2p.constants.IConstants;
import com.sp2p.database.Dao;
import com.sp2p.database.Dao.Tables;
import com.sp2p.database.Dao.Tables.t_award_user;
import com.sp2p.entity.Admin;
import com.sp2p.entity.AwardParamVo;
import com.sp2p.service.NewsAndMediaReportService;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.JSONUtils;
import com.shove.util.ServletUtils;
import com.shove.util.UtilDate;
import com.shove.vo.Files;
import com.shove.web.CacheManager;
import com.shove.web.action.BasePageAction;
import com.shove.web.util.ExcelUtils;

/**
 * 网站公告Action
 * 
 * @author zhongchuiqing
 * 
 */
@SuppressWarnings("unchecked")
public class NewsAction extends BasePageAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(NewsAction.class);

	private NewsAndMediaReportService newsService;
	
	private Files files;//要批量上传的文件
	
	

	public Files getFiles() {
		return files;
	}

	public void setFiles(Files files) {
		this.files = files;
	}

	public NewsAndMediaReportService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsAndMediaReportService newsService) {
		this.newsService = newsService;
	}

	/**
	 * 初始化分页查询网站公告列表
	 * 
	 * @return
	 */
	public String queryNewsListInit() {
		return SUCCESS;
	}

	/**
	 * 分页查询网站公告列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryNewsListPage() throws Exception {
		try {
			newsService.queryNewsPage(pageBean);
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
	 * 招贤纳士
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String queryRecruitmentInfoInit() {
		return SUCCESS;
	}
	
	public String queryRecruitmentInfo() throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map = newsService.queryRecruitmentInfo();
            request().setAttribute("map", map.get("content"));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 添加网站公告信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addNews() throws Exception {
		Admin user = (Admin) session().getAttribute("admin");
		Integer sort = Convert.strToInt(paramMap.get("sort"), 1);
		String title = paramMap.get("title");
		String content = paramMap.get("content");
		String newsType = paramMap.get("newsType");
		Long userId = -1L;
		if (user != null) {
			userId = user.getId();
		}
		String visits = paramMap.get("visits");
		String publishTime = paramMap.get("publishTime");
		@SuppressWarnings("unused")
		String message = "添加失败";
		Long result = -1L;
		try {
			result = newsService.addNews(sort, title, content, userId, visits,
					publishTime,newsType);
			if (result > 0) {
				message = "添加成功";
				// 清空分页，列表数据
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INDEX);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_WZDT);
				CacheManager.clearStartsWithAll(IConstants.CACHE_WZGG_PAGE_);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * 添加网站公告信息初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addNewsInit() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publishTime = format.format(new Date());
		paramMap.put("publishTime", publishTime);

		// -----add by houli 给出默认序列号值
		Map<String, String> map = newsService.getMaxSerial();
		if (map == null || map.get("sortId") == null) {
			paramMap.put("sort", String.valueOf(1));
		} else {
			int sortId = Convert.strToInt(map.get("sortId"), 1);
			paramMap.put("sort", String.valueOf(sortId + 1));
		}
		// 新添加的公告信息浏览量默认为0
		paramMap.put("visits", String.valueOf(0));
		// -----------

		return SUCCESS;
	}

	/**
	 * 更新初始化，根据Id获取网站公告信息详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateNewsInit() throws Exception {
		Long id = request.getLong("id", -1);
		try {
			paramMap = newsService.getNewsById(id);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;
	}

	/**
	 * 预览（在添加或更新中）
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String PreviewNews() throws SQLException, DataException,
			UnsupportedEncodingException {
		Map<String, String> map = new HashMap<String, String>();
		Admin user = (Admin) session().getAttribute("admin");
		String userName = null;
		if (user != null) {
			userName = user.getUserName();
		}
		Long id = request.getLong("id", -1);
		String sort = request().getParameter("sort");
		String title = request().getParameter("title");
		String content = request().getParameter("content");
		String visits = request().getParameter("visits");
		String publishTime = request().getParameter("publishTime");
		/*title = URLDecoder.decode(title, "UTF-8");
		content = URLDecoder.decode(content, "UTF-8");
		visits = URLDecoder.decode(content, "UTF-8");
		publishTime = URLDecoder.decode(publishTime, "UTF-8");*/
		if(id!=-1){
			
			try {
				map = newsService.getNewsById(id);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			request().setAttribute("newsPreview", map);
			return SUCCESS;
		}else{

			map.put("userName", userName);
			map.put("sort", sort);
			map.put("title", title);
			map.put("content", content);
			map.put("visits", visits);
			map.put("publishTime", publishTime);
			request().setAttribute("newsPreview", map);
			return SUCCESS;
		}
		
		
	}

	/**
	 * 更新网站公告信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateNews() throws Exception {
		Admin user = (Admin) session().getAttribute("admin");
		Long id = Convert.strToLong(paramMap.get("id"), 0);
		Integer sort = Convert.strToInt(paramMap.get("sort"), 1);
		String title = paramMap.get("title");
		String content = paramMap.get("content");
		String newsType = paramMap.get("newsType");
		Long userId = -1L;
		if (user != null) {
			userId = user.getId();
		}

		Integer visits = Convert.strToInt(paramMap.get("visits"), -1);
		String publishTime = paramMap.get("publishTime");
		@SuppressWarnings("unused")
		String message = "更新失败";

		try {

			long result = newsService.updateNews(id, sort, title, content,
					userId, visits, publishTime,newsType);
			if (result > 0) {
				message = "更新成功";
				// 清空分页，列表数据，当前明细
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INDEX);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_WZDT);
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INFO_ + id);
				CacheManager.clearStartsWithAll(IConstants.CACHE_WZGG_PAGE_);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			throw e;
		}

		return SUCCESS;

	}

	/**
	 * 删除网站公告数据
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String deleteNews() throws Exception {
		String dynamicIds = request.getString("id");
		String[] newsids = dynamicIds.split(",");
		if (newsids.length > 0) {
			long tempId = 0;
			for (String str : newsids) {
				tempId = Convert.strToLong(str, -1);
				if (tempId == -1) {
					return INPUT;
				}
			}
		} else {
			return INPUT;
		}

		try {
			newsService.deleteNews(dynamicIds, ",");
			// 清空分页，列表数据，当前明细
			CacheManager.clearByKey(IConstants.CACHE_WZGG_INDEX);
			CacheManager.clearByKey(IConstants.CACHE_WZGG_WZDT);
			CacheManager.clearStartsWithAll(IConstants.CACHE_WZGG_PAGE_);
			for (String id : newsids) {
				CacheManager.clearByKey(IConstants.CACHE_WZGG_INFO_ + id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}

	/**
	 * add by houli 判断所填写的sort是否是唯一的
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isExistSortId() throws Exception {
		int id = request.getInt("sort", -1);
		try {
			Long result = newsService.isExistSortId(id);

			if (result <= 0) {
				JSONUtils.printStr("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public String isExistToUpdate() throws Exception {
		int id = Convert.strToInt(paramMap.get("sortId"), -1);
		int originalId = Convert.strToInt(paramMap.get("originalId"), -1);
		try {
			Long result = newsService.isExistToUpdate(id, originalId);

			if (result <= 0) {
				JSONUtils.printStr("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	/**
	 *功能：点击“抽奖管理”
	 * @return
	 * @throws Exception
	 */
	public String awardManage() throws Exception {
		return SUCCESS;
	}
	
	/**
     *功能：点击“抽奖管理” 查询列表
     * @return
	 * @throws SQLException 
     * @throws Exception
     */
	public String awardManageInfo() throws SQLException{
	    
	    String dateStart = paramMap.get("dateStart");
	    String dateEnd = paramMap.get("dateEnd");
	    newsService.queryAwardManagerInfo(pageBean,dateStart,dateEnd);
	    return SUCCESS;
	}
	
	/**
	 *功能：点击设置
	 * @return
	 * @throws Exception
	 */
	public String addAwardTerm() throws Exception {
	    int type = Convert.strToInt(request("type"), 1);
	    paramMap.put("awardsize", "0");//奖品个数
	    if (type == EDIT) {//修改
            int id = Convert.strToInt(request("id"), -1);
            Map<String, String> map =  newsService.queryAwardById(id);
            List<Map<String, String>> list =  newsService.queryAwardInfoById(id);
            int size = list==null?0:list.size();
            if (map==null) {
                return null;
            }else {
                paramMap.putAll(map);
                paramMap.put("awardsize", size+"");//奖品个数
            }
        }
	    setStrutsAction(type);
		return SUCCESS;
	}
	
	public String listAwardParamInit(){
	    request().setAttribute("id", request("id"));
	    return SUCCESS;
	}
	/**
	 * 奖品列表
	 */
	public String listAwardParam() throws Exception {
		int termId = Convert.strToInt(request("id"), -1);
		List<Map<String, String>> list =  newsService.queryAwardInfoById(termId);
		request().setAttribute("id", termId);
		request().setAttribute("list", list);
		return SUCCESS;
	}
	
	/**
	 *功能：点击新增（参数表）
	 * @return
	 * @throws Exception
	 */
	public String gotoAddAwardParam() throws Exception {
	    long id = Convert.strToLong(request("id"), -1L);
	    if (id==-1L) {
            strutsAction = ADD;
        }else {
            strutsAction = EDIT;
            paramMap = newsService.queryParamDetailById(id);
        }
		return SUCCESS;
	}
	/**
	 *功能：新增（参数表）
	 * @return
	 * @throws Exception
	 */
	public String addAwardParam() throws Exception {
		log.info("----addAwardParam---");
		Admin user = (Admin) session().getAttribute("admin");
		newsService.addAwardParam(paramMap);
		return SUCCESS;
	}
	
	/**
	 * 添加抽奖期数
	 * addAwardTermMain
	 * @auth hejiahua
	 * @return
	 * @throws Exception 
	 * String
	 * @exception 
	 * @date:2015-1-9 上午11:25:46
	 * @since  1.0.0
	 */
	public String addAwardTermMain() throws Exception {
		log.info("-addAwardTermMain---paramMap:"+paramMap);
		JSONObject obj = new JSONObject();
		if (strutsAction == ADD) {//增加
		    Long termId = newsService.addAwardTerm(paramMap,strutsAction);
	        obj.put("termId", termId);
        }else if(strutsAction == EDIT) {//修改
            Long termId = newsService.addAwardTerm(paramMap,strutsAction);
            obj.put("termId", termId);
        }
		JSONUtils.printObject(obj);
		return null;
	}
	
	/**
	 *功能：保存抽奖信息
	 * @return
	 * @throws Exception
	 */
	public String saveAwardInfo() throws Exception {
	    long result = newsService.saveAwardInfo(paramMap);
	    JSONUtils.printStr(result+"");
	    return null;
	}
	
	//添加某一期的奖品
	public String addAwardInfoInit() throws SQLException{
	    request().setAttribute("id", request("id"));
	    request().setAttribute("awardParams", newsService.queryAwardParam());
	    return SUCCESS;
	}
	
	//删除某一期的奖品
	public String awardParamDelete() throws SQLException{
	    int id = Convert.strToInt(request("id"), -1);
	    if (id!=-1) {
	        newsService.awardParamDelete(id);
        }
	    return null;
	}
	
	//初始化抽奖明细
	public String awardParamUserInit() throws SQLException{
	    request().setAttribute("termId", request("termId"));
	    request().setAttribute("awardParams", newsService.queryAwardParam());
	    request().setAttribute("award", newsService.queryAward());
	    return SUCCESS;
	}
	
	//抽奖明细列表
	public String awardParamUserList() throws SQLException{
	    newsService.queryAwardParamUserList(pageBean, paramMap);
	    return SUCCESS;
	}
	
	//导出抽奖明细
	public String exportAwardParamUserList(){
        pageBean.pageNum = 1;
        pageBean.setPageSize(100000);
        try {
            newsService.queryAwardParamUserList(pageBean, paramMap);
            if (pageBean.getPage() == null) {
                getOut()
                        .print(
                                "<script>alert(' 导出记录条数不能为空! ');window.history.go(-1);</script>");
                return null;
            }
            if (pageBean.getPage().size() > IConstants.EXCEL_MAX) {
                getOut()
                        .print(
                                "<script>alert(' 导出记录条数不能大于50000条 ');window.history.go(-1);</script>");
                return null;
            }
            HSSFWorkbook wb = ExcelUtils.exportExcel("抽奖明细",
                    pageBean.getPage(), new String[] { "期数", "用户名", "中奖状态",
                            "奖品名称", "抽奖时间", "派奖状态"}, new String[] {
                            "termNo", "username", "statusStr", "awardName", "createTime", "sendAwardStr" });
            this.export(wb, new Date().getTime() + ".xls");
            Admin admin = (Admin) session().getAttribute(
                    IConstants.SESSION_ADMIN);
            operationLogService.addOperationLog(
                    "", admin.getUserName(),
                    IConstants.EXCEL, admin.getLastIP(), 0, "导出抽奖明细列表", 2);
        } catch (SQLException e) {

            e.printStackTrace();
        } catch (DataException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public String awardParamDetailInit(){
	    return SUCCESS;
	}
	//查询
	public String awardParamDetail() throws SQLException{
	    String name = paramMap.get("name");
	    newsService.queryParamDetail(pageBean, name);
	    return SUCCESS;
	}
	//删除奖品
	public String awardParamDetailDelete() throws SQLException, IOException{
	    long result = newsService.editParamDetail(paramMap, DELETE);
	    if (result>-1) {
            JSONUtils.printStr("1");
        }else {
            JSONUtils.printStr("-1");
        }
	    return null;
	}
	//更新奖品 or 新增奖品
	public String awardParamEdit() throws SQLException, IOException{
	    long result = newsService.editParamDetail(paramMap, strutsAction);
	    if (result>-1) {
            JSONUtils.printStr("1");
        }else {
            JSONUtils.printStr("-1");
        }
	    return null;
	}
	
	//派奖
	public String sendAward () throws SQLException{
	    String commonId = request("commonId");
	    if (StringUtils.isNotBlank(commonId)) {
            if (commonId.contains(",")) {
                String [] ids = commonId.split(",");
                for (int i = 0; i < ids.length; i++) {
                    long id = Convert.strToLong(ids[i], -1L);
                    newsService.sendAward(id);
                }
            }else {
                long id = Convert.strToLong(commonId, -1L);
                newsService.sendAward(id);
            }
        }
	    return null;
	}
	
	public String downModelClearAward() throws IOException{
		HSSFWorkbook book = ExcelUtils.exportExcel("模版",new ArrayList<Map<String,Object>>(), new String[]{"用户ID","金额(不填写则全部清空)"}, null);
    	this.export(book, new Date().getTime()+".xls");
		return null;
	}
	
	//清空奖品
	public String clearAwardMethod() throws Exception{
	    if (files==null) {
            addActionError("请选择文件");
            return INPUT;
        }
	    String filename = files.getFilesFileName().substring(files.getFilesFileName().lastIndexOf("."));
	    if (!".xls".equalsIgnoreCase(filename)) {
	        addActionError("文件格式不正确，请重新选择文件");
            return INPUT;
        }
	    try {
	    	String addIP = ServletUtils.getRemortIp();
	    	Admin admin = (Admin) session().getAttribute(IConstants.SESSION_ADMIN);
			Long adminId = admin.getId();
	    	List<Map<String, Object>> result = newsService.clearAward(files,paramMap.get("type"), adminId, addIP);
	    	if (result!=null && result.size()>0) {
	    		HSSFWorkbook book = ExcelUtils.exportExcel("批量处理结果",result, new String[]{"用户ID","金额","结果"}   , new String[]{"id","money","result"});
		    	this.export(book, new Date().getTime()+".xls");
			}
	    	getOut().print("<script type='text/javascript'>alert('操作成功！');window.location.href='awardManage.do';</script>");
            return null;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            log.error(e);
            e.printStackTrace();
            getOut().print("<script type='text/javascript'>alert('操作失败！');</script>");
            return null;
        }
	    return SUCCESS;
	}
	
	//手动添加红包
	public String addHandAward() throws SQLException, DataException{
		Connection conn = MySQL.getConnection();
		//插入好币扣除记录
		Dao.Tables.t_award_user vipsum = new Dao().new Tables().new t_award_user();
		vipsum.userId.setValue(paramMap.get("userId"));
		vipsum.termId.setValue(paramMap.get("termId"));
		vipsum.paramId.setValue(paramMap.get("paramId"));
		vipsum.score.setValue("0");
		vipsum.awardInfoId.setValue(paramMap.get("awardInfoId"));
		vipsum.createTime.setValue(new Date());
		vipsum.sendAward.setValue(1);
		long result = vipsum.insert(conn);
		
		String command = "SELECT IFNULL(realMoney,0) AS money FROM t_award_info t WHERE t.`id` = "+paramMap.get("awardInfoId");
		DataSet ds = MySQL.executeQuery(conn, command);
		Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
		String presrent = "0";
		if (map!=null) {
			presrent = map.get("money");
		}
		
		command = "UPDATE t_user t SET t.`presrent` = t.`presrent`+"+presrent+"  where id =" + paramMap.get("userId");
		MySQL.executeNonQuery(conn, command.toString());
		conn.commit();
		conn.close();
		com.shove.JSONUtils.printStr(""+result);
		return null;
	}
}
