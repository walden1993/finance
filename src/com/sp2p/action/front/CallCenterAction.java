package com.sp2p.action.front;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.service.HelpAndServicerService;

public class CallCenterAction extends BaseFrontAction {
	
	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;	
	private HelpAndServicerService callCenterService;
	
	private Map<String,String> helpAnswer;
	private String question;
	
	

	public HelpAndServicerService getCallCenterService() {
		return callCenterService;
	}

	public void setCallCenterService(HelpAndServicerService callCenterService) {
		this.callCenterService = callCenterService;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setHelpAnswer(Map<String, String> helpAnswer) {
		this.helpAnswer = helpAnswer;
	}

	public String callCenterInit() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNum = request.getString("curPage");
		String cid = request.getString("cid") == null?"1":request.getString("cid");
		request().setAttribute("cid", cid);
		
		long typeId = Long.parseLong(cid);
		try{	
			//查询左边菜单
			pageBean = new PageBean();
			callCenterService.queryHelpTypes(pageBean);
			List<Map<String, Object>> types = pageBean.getPage();
			
			Map<String,String> values = null;
			String curDes = null;
			if(request.getString("type")!= null){
				values = callCenterService.getDescribeById(typeId);
				curDes = values.get("helpDescribe");//获得帮助类型描述
			}
			
			//右边问题
			
			pageBean = new PageBean();
			pageBean.setPageSize(4);
			if(StringUtils.isNotBlank(pageNum)){
				pageBean.setPageNum(pageNum);
			}
			
			callCenterService.queryHelpQuestions(pageBean,typeId);
			List<Map<String, Object>> questions = pageBean.getPage();
			if(StringUtils.isBlank(curDes)){
				curDes = "新手入门";
			}
			List<Map<String, Object>> lists;//客服列表
			lists = callCenterService.queryKefuLimit4();
			request().setAttribute("types", types);
			request().setAttribute("curDes", curDes);
			request().setAttribute("typeId", typeId);
			request().setAttribute("lists", lists);
			
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 显示客服列表
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public String callKfsInit() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try{
			List<Map<String,Object>> lists;
			//查询左边菜单
			pageBean = new PageBean();
			callCenterService.queryHelpTypes(pageBean);
			List<Map<String, Object>> types = pageBean.getPage();
			lists = callCenterService.queryKefuList();
			request().setAttribute("types", types);
			request().setAttribute("lists", lists);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String queryCallCenterInit() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNum = request.getString("curPage");
		long typeId = Long.parseLong(request.getString("cid"));
		//前台显示列表类型
		if(StringUtils.isNotBlank(pageNum)){
			pageBean.setPageNum(pageNum);
		}
		
		pageBean.setPageSize(5);
		try{
			callCenterService.queryAllAnswerLists(IConstants.SORT_TYPE_DESC, pageBean,typeId);
			request().setAttribute("cid", typeId);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	public String callcenterSearch() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String pageNum = paramMap.get("curPage");
		String queryString = paramMap.get("queryString");
		//前台显示列表类型
		if(StringUtils.isNotBlank(pageNum)){
			pageBean.setPageNum(pageNum);
		}
		if (StringUtils.isBlank(queryString)) {
			pageBean.setPage(null);
			return SUCCESS;
		}
		pageBean.setPageSize(5);
		try{
			callCenterService.queryCallcenterSearch(IConstants.SORT_TYPE_DESC, pageBean,queryString);
			
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
	
	
	public String queryCallCenterAnswer() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = Convert.strToStr(request.getString("id"), "");
		
		//添加浏览量
		try{
			List<Map<String,Object>> list = callCenterService.queryAnswerByTypeId(Convert.strToLong(id, -1));
			request().setAttribute("list", list);
		}catch(SQLException e){
			log.error(e);
			e.printStackTrace();
			throw e;
		}
		return SUCCESS;
	}
}
