package com.shove.web.action;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.shove.vo.PageBean;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.service.OperationLogService;
import com.sp2p.util.WebUtil;


/**
 * @author 杨程
 * @date:Oct 15, 2010 8:32:16 AM
 * @category
 * @version :
 */
public class BasePageAction<T> extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Pattern regex = Pattern.compile("^([1-9]{1}[0-9]{0,})$");//验证每页显示的条数
	public PageBean<T> pageBean;//存放分页信息
	
	//PrintWriter打印输出
	private PrintWriter out;

	protected  OperationLogService operationLogService;	
	
	
	
	public BasePageAction() {		
		this.pageBean = new PageBean<T>(10);
	}

	public PageBean<T> getPageBean() {
		String curPage = request.getString("curPage");//当前页
		String pageSize = request.getString("pageSize");//每页的条数
		
		if(StringUtils.isNotBlank(curPage)){
			pageBean.setPageNum(curPage);
		}
		
		if(StringUtils.isNotBlank(pageSize) && regex.matcher(pageSize).matches()){
			pageBean.setPageSize(Integer.parseInt(pageSize));
		}
		return pageBean;
	}

	public void setPageBean(PageBean<T> pageBean) {
		this.pageBean = pageBean;
	}
	
	public PrintWriter getOut() throws Exception {
		response().setCharacterEncoding("UTF-8");
		response().setContentType("text/html; charset=UTF-8");
		out = response().getWriter();
		return out;
	}
	
	protected void sendHtml(String html) throws Exception{
		PrintWriter out = getOut();
		out.println(html);
		out.flush();
		out.close();
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPlatformCost(){
		//获取平台收费
		Map<String,Object> platformCostMap = (Map<String, Object>) application().getAttribute(IAmountConstants.FEE_APPLICATION);
        if(platformCostMap == null)
        	platformCostMap = new HashMap<String, Object>();
		return platformCostMap;
	}
	
	/**
	 * 将request中的参数设置到paramMap中去
	 */
	protected void setRequestToParamMap(){
		Enumeration<String> keyNames = request().getParameterNames();
		while(keyNames.hasMoreElements()){
			String attrName = keyNames.nextElement();
			paramMap.put(attrName, request(attrName));
		}
	}
	
	/**
	 * 初始化操作，这个action不做任何处理的可以直接使用
	 * @return
	 */
	public String init(){
		return SUCCESS;
	}
	
	public String getBasePath(){
		return WebUtil.getWebPath();
	}

	public void setOperationLogService(OperationLogService operationLogService) {
		this.operationLogService = operationLogService;
	}

	public OperationLogService getOperationLogService() {
		return operationLogService;
	}
}
