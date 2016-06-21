package com.sp2p.action.front;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Operator;
import com.sp2p.entity.User;
import com.sp2p.service.FinanceService;
import com.sp2p.service.MyHomeService;
import com.sp2p.service.NewsAndMediaReportService;
import com.sp2p.service.PayTreasureService;
import com.sp2p.service.RechargeService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserService;
import com.sp2p.util.DateUtil;
import com.sp2p.util.SpringUtil;

/**
 * @ClassName: FrontMyHomeAction.java
 * @Author: gang.lv
 * @Date: 2013-3-13 下午10:21:30
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的主页控制层
 */
public class FrontMyHomeAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyHomeAction.class);
	private static final long serialVersionUID = 1L;

	private MyHomeService myHomeService;
	private SelectedService selectedService;
	private List<Map<String, Object>> borrowDeadlineList;
	private Map<String, String> automaticBidMap;
	private List<Operator> checkList;
	private FinanceService financeService;
	private RechargeService rechargeService;
	private NewsAndMediaReportService newsAndMediaReportService;
	private UserService userService;
	private PayTreasureService payTreasureService;
	
	
	
	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}

	/**
	 * 上传个人头像
	 * 
	 */
	public String pastpicture() {
		return SUCCESS;
	}
	
	/**
	 * @throws Exception
	 * @MethodName: homeInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午08:53:19
	 * @Return:
	 * @Descb: 我的主页初始化
	 * @Throws:
	 */
	public String homeInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		//个人信息
		Map<String, String> homeMap = myHomeService.queryHomeInfo(user.getId());
		request().setAttribute("homeMap", homeMap);
		//金额信息
		Map<String, String> accmountStatisMap = myHomeService
				.queryAccountStatisInfo(user.getId());
		request().setAttribute("accmountStatisMap", accmountStatisMap);
		
		//涨薪宝收益
		
		
		/*Map<String, String> repayMap = myHomeService.queryRepaymentByOwner(user
				.getId());
		request().setAttribute("repayMap", repayMap);*/
		//推荐投资
		/*try {
			financeService.queryBorrowRecomment(null, null, null,
					null, null, null, null, null, null,
					IConstants.SORT_TYPE_DESC, pageBean);
		} catch (Exception e) {
			log.error("..............error....");
			e.printStackTrace();
		}
		if (null == pageBean){
			log.debug("---------------0.0--null == pageBean---------------------");
		}
		if (null != pageBean.getPage()){
			List<Map<String,Object>> pageBeanList =pageBean.getPage();
			log.debug("=======1===============recommentList=" + pageBeanList);
			request().setAttribute("recommentList",	pageBeanList);
			pageBean.setPage(null);
		} else {
			log.debug("---------------0.0--null == queryBorrowRecomment---------------------");
		}*/
		
		
		//安全等级
		Map<String, String> safeMap = financeService.queryUserSafe(user.getId());
		request().setAttribute("safeMap", safeMap);
		
		// 公告
		/*List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
		pageBean.setPageSize(5);
		// newsService.frontQueryNewsPage(pageBean);
		newsAndMediaReportService.frontQueryNewsPage(pageBean,null);
		if (null != pageBean.getPage()){
			newsList = pageBean.getPage();
			pageBean.setPage(null);
			request().setAttribute("newsList", newsList);
		}*/
		
		//查询用户的其它信息
		Map <String, String> userMap = userService.queryUserInfoById(user.getId());
		if(userMap.get("authCardName")==null||userMap.get("authCardName")==""){
			userMap.put("authCardName", "1");
		}
		request().setAttribute("userMap", userMap);
		if (null == session().getAttribute("authCardNameFlag")){
			session().setAttribute("authCardNameFlag", "1");
		}
		
		//取总投资，总借款
		Map <String, String> m = financeService.queryInvestAmount(user.getId());
		DecimalFormat formatter1=new DecimalFormat("#.00");
		if (null != m.get("investSum") && !"".equals(m.get("investSum"))){
			String a  = formatter1.format(Double.parseDouble((String)m.get("investSum"))); 
			m.put("investSum", a);
		}
		request().setAttribute("moneySum", m);
		
		//nextRepay
		/*Map nextm = financeService.queryNextRepay(user.getId());
		if (null != nextm){
			request().setAttribute("nextRepayMap", nextm);
		}*/
		//还次还钱时间
		/*Map nextd = financeService.queryNextDebt(user.getId());
		if (null != nextd){
			request().setAttribute("nextDevtMap", nextd);
		}*/
		
		return "success";
	}
	
	/**
	 * wap站首页
	 * homeWapInit
	 * @auth hejiahua
	 * @return
	 * @throws Exception 
	 * String
	 * @exception 
	 * @date:2014-12-30 下午3:30:53
	 * @since  1.0.0
	 */
    public String homeWapInit() throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        User user = (User) session().getAttribute("user");
        Map<String, String> accmountStatisMap = myHomeService.queryAccountStatisInfo(user.getId());
        accmountStatisMap.put("username", user.getUserName());
        JSONUtils.printObject(accmountStatisMap);
        return null;
    }
	

	/**
	 * @MethodName: homeBorrowPublishInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午08:53:29
	 * @Return:
	 * @Descb: 已经发布的借款初始化
	 * @Throws:
	 */
	public String homeBorrowPublishInit() {
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: loanStatisInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:57:19
	 * @Return:
	 * @Descb: 借款统计
	 * @Throws:
	 */
	public String loanStatisInit() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		Map<String, String> loanStatisMap = myHomeService.queryLoanStatis(user
				.getId());
		request().setAttribute("loanStatisMap", loanStatisMap);
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeStatisInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午02:57:31
	 * @Return:
	 * @Descb: 理财统计
	 * @Throws:
	 */
	public String financeStatisInit() throws SQLException, DataException {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		Map<String, String> financeStatisMap = myHomeService
				.queryFinanceStatis(user.getId());
		request().setAttribute("financeStatisMap", financeStatisMap);
		if (checkMobile()) {
			return "successmobile";
		}
		return "success";
	}
	
	/**
	 * @Author: 李艳华
	 * @Date: 2014-8-23
	 * @Descb: 我的投资体验信息
	 */
	public String financeExperience() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId = -1;
		if(user != null){
			userId = user.getId();
		}
		pageBean.setPageNum(paramMap.get("curPage"));
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		//我的体验记录列表
		myHomeService.queryMyExperienceByid(userId,pageBean);
		
		//我的体验投资总额和总收益
		Map<String, String> mapSum = myHomeService
		.queryMyExperienceSumByid(userId);
		request().setAttribute("myExperienceList", pageBean.getPage());
		request().setAttribute("mapSum", mapSum);
		return "success";
	}
	
	/**
	 * @Author: 李艳华
	 * @Date: 2014-8-23
	 * @Descb: 我的投资体验信息搜索
	 */
	public String searchExperience() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());		
		User user = (User) session().getAttribute("user");
		long userId = -1;
		if(user != null){
			userId = user.getId();
		}
		
		String statusStr = paramMap.get("status");
		
		int status = Convert.strToInt(statusStr, -1);
		
		pageBean.setPageNum(request.getString("curPage"));
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		//我的体验记录列表
		myHomeService.queryMyExperienceByStatus(userId,status,pageBean);
		
		//我的体验投资总额和总收益
		Map<String, String> mapSum = myHomeService
		.queryMyExperienceSumByid(userId);
		request().setAttribute("myExperienceList", pageBean.getPage());
		request().setAttribute("mapSum", mapSum);
		return "success";
	}
	/**
	 * @Author: 李艳华
	 * @Date: 2014-8-24
	 * @Return:
	 * @Descb: 激活体验券
	 * @Throws:
	 */
	public String activateTicket() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
//		User user = (User) session().getAttribute("user");
//		long userId = -1;
//		if(user != null){
//			userId = user.getId();
//		}
		//我的体验记录
//		List<Map<String, Object>> myExperienceList = myHomeService
//		.queryMyExperienceByid(userId);
//		User user = (User) session().getAttribute("user");
//		Map<String, String> financeStatisMap = myHomeService
//				.queryFinanceStatis(user.getId());
//		request().setAttribute("financeStatisMap", financeStatisMap);
		return "success";
	}
	
	/**
	 * @throws Exception 
	 * @MethodName: financeExperience
	 * @Param: FrontMyHomeAction
	 * @Author: 李艳华
	 * @Date: 2014-8-23
	 * @Return:
	 * @Descb: 我的体验券信息
	 * @Throws:
	 */
	public String experienceTicket() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId = -1;
		if(user != null){
			userId = user.getId();
		}
		
		pageBean.setPageNum(request.getString("curPage"));
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		//我的体验券信息
//		pageBean.setPageNum(request.getString("curPage"));
		myHomeService.queryMyTicketByid(userId,pageBean);
		
		if (pageBean.getPage()!=null&&pageBean.getPage().size()>0) {
			for (int i = 0; i < pageBean.getPage().size(); i++) {
				Map<String, Object> map = (Map<String, Object>) pageBean.getPage().get(i);
				Date enableTime = DateUtil.strToDate(map.get("disableDate").toString()==null?"2014-8-29 09:13:32":map.get("disableDate").toString());
				Date nowTime = new Date();
				long isShixiao = nowTime.getTime()-enableTime.getTime();
				if (Long.valueOf(isShixiao)>0 && map.get("ticketStatus").equals(3)) {
					map.put("isShixiao", "1");
				}
			}
		}
		
		
		request().setAttribute("myTicketList", pageBean.getPage());
		
		
		//我的体验投资总额和总收益
		Map<String, String> mapSum = myHomeService
		.queryMyExperienceSumByid(userId);
		request().setAttribute("mapSum", mapSum);
		return "success";
	}
	
	public String searchTicket() throws Exception{
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        
        User user = (User) session().getAttribute("user");
        long userId = -1;
        if(user != null){
            userId = user.getId();
        }
        pageBean.setPageNum(request.getString("curPage"));
        pageBean.setPageSize(IConstants.PAGE_SIZE_10);
        String ticketNo = paramMap.get("ticketNo");
        String status = paramMap.get("ticketStatus");
        
        int stu = Convert.strToInt(status, -1);
        
        if (StringUtils.isBlank(ticketNo)) {
            ticketNo="";
        }
        //我的体验券信息
        myHomeService.queryMyTicketByTicketNo(userId,ticketNo,stu,pageBean);
        request().setAttribute("myTicketList", pageBean.getPage());

        //我的体验投资总额和总收益
         Map<String, String> mapSum = myHomeService
         .queryMyExperienceSumByid(userId);
         request().setAttribute("mapSum", mapSum); 
        return "success";
    }
	
	
	// 判断用户体验券是否有用
	public String queryTicketNo() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId = -1;
		if(user != null){
			userId = user.getId();
		}
		String ticketNo = paramMap.get("ticketNo") == null ? "1" : paramMap.get("ticketNo");
		//我的体验券信息
		Map<String, String> myTicketList = myHomeService
		.queryMyTicketBytichetNo(ticketNo);
		
		request().setAttribute("myTicketList", myTicketList);
		try {
			if (myTicketList == null) {
				JSONUtils.printStr("1");
				return null;
			}else{
				int ticketStatus = Convert.strToInt(myTicketList.get("ticketStatus"), -1);
				int batchStatus = Convert.strToInt(myTicketList.get("batchStatus"), -1);
				int id = Convert.strToInt(myTicketList.get("userId"), -1);
				int bindingTime = Convert.strToInt(myTicketList.get("bindingTime"), -1);
				int disableDate = Convert.strToInt(myTicketList.get("disableDate"), -1);
				if(ticketStatus==1||batchStatus==2){
					JSONUtils.printStr("1");
					return null;
				}
				if(id != -1 && id != userId){
					JSONUtils.printStr("2");
					return null;
				}
//				if(bindingTime >= disableDate){
//					JSONUtils.printStr("3");
//					return null;
//				}
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	//激活体验券
	public String activate() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String ticketNo = paramMap.get("ticketNo") == null ? "1" : paramMap.get("ticketNo");
		User user = (User) session().getAttribute("user");
		int userId = 0;
		if(user != null){
			userId = new Long(user.getId()).intValue();
		}
		//更新我的体验券信息
//		long result =  myHomeService.updateMyTicket(userId, ticketNo);
//		Map<String,String> msgMap = new HashMap<String,String>();
//		if(result<0){
//			msgMap.put("msg", "激活失败");
//			JSONUtils.printObject(msgMap);
//			return null;
//		}
//		msgMap.put("msg", "1");
//		JSONUtils.printObject(msgMap);
		
		//JSONObject obj = new JSONObject();
		Map<String,String> result = myHomeService.updateMyTicket(userId, ticketNo);
	    //String resultMSG = result.get("ret_desc");
	    //String resultCode = result.get("ret");
		//userService.updateSign(user.getId());//更换校验码
	   // if("-1".contains(resultCode)){
	    //	obj.put("msg", 1);
	    //}else{
	    //	obj.put("msg", resultMSG);
	   // } 
		JSONUtils.printObject(result);
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowBackAcount
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-4-2 上午09:12:22
	 * @Return:
	 * @Descb: 查询借款回账
	 * @Throws:
	 */
	public String homeBorrowBackAcount() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String title = paramMap.get("title") == null ? "" : paramMap
				.get("title");
		String publishTimeStart = paramMap.get("publishTimeStart") == null ? ""
				: paramMap.get("publishTimeStart");
		if (StringUtils.isNotBlank(publishTimeStart)) {
			publishTimeStart = publishTimeStart + " 00:00:00";
		}
		String publishTimeEnd = paramMap.get("publishTimeEnd") == null ? ""
				: paramMap.get("publishTimeEnd");
		if (StringUtils.isNotBlank(publishTimeStart)) {
			publishTimeEnd = publishTimeEnd + " 23:59:59";
		}
		Map<String, String> map = myHomeService.queryBackAcountStatis(user
				.getId(), publishTimeStart, publishTimeEnd, title);
		String allForPIOneMonth = map.get("allForPIOneMonth") == null ? "0"
				: map.get("allForPIOneMonth");
		String allForPIThreeMonth = map.get("allForPIThreeMonth") == null ? "0"
				: map.get("allForPIThreeMonth");
		String allForPIYear = map.get("allForPIYear") == null ? "0" : map
				.get("allForPIYear");
		String allForPI = map.get("allForPI") == null ? "0" : map
				.get("allForPI");
		obj.put("allForPIOneMonth", allForPIOneMonth);
		obj.put("allForPIThreeMonth", allForPIThreeMonth);
		obj.put("allForPIYear", allForPIYear);
		obj.put("allForPI", allForPI);
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowInvestList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:40:13
	 * @Return:
	 * @Descb: 投资借款列表
	 * @Throws:
	 */
	public String homeBorrowInvestList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");

		String queryType = (null == request().getParameter("queryType") ? "1" : request().getParameter("queryType") );
		request().setAttribute("queryType", queryType);
		pageBean.setPageNum(request.getString("curPage"));
		pageBean.setPageSize(10);

//		String type = "1";
		String borrowStatus = "";
//		if ("1".equals(type)) {
//			borrowStatus = //IConstants.BORROW_STATUS_4 + 
//			"" + IConstants.BORROW_STATUS_5;
//		} else if ("2".equals(type)) {
//			borrowStatus = "" + IConstants.BORROW_STATUS_2;
//		}
		String title = request.getString("title");
		String publishTimeStart = request.getString("publishTimeStart");
		String publishTimeEnd = request.getString("publishTimeEnd");
		myHomeService.queryBorrowInvestByCondition3(title, publishTimeStart,
				publishTimeEnd, borrowStatus, user.getId(), pageBean);
		
		
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowInvestList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:40:13
	 * @Return:
	 * @Descb: 投资借款列表
	 * @Throws:
	 */
	public String homeBorrowTenderInList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");

		pageBean.setPageNum(request.getString("curPage"));
		pageBean.setPageSize(10);
		String borrowStatus = IConstants.BORROW_STATUS_2 + "";

		String title = request.getString("title");
		String publishTimeStart = request.getString("publishTimeStart");
		String publishTimeEnd = request.getString("publishTimeEnd");
		myHomeService.queryBorrowInvestByCondition(title, publishTimeStart,
				publishTimeEnd, borrowStatus, user.getId(), pageBean);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowRecycleList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:41:47
	 * @Return:
	 * @Descb: 待回收借款列表
	 * @Throws:
	 */
	public String homeBorrowRecycleList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String queryType = (null == request().getParameter("queryType") ? "1" : request().getParameter("queryType") );
		request().setAttribute("queryType", queryType);
		String id = request().getParameter("id");
		
		User user = (User) session().getAttribute("user");
		pageBean.setPageNum(request.getString("curPage"));
		String title = request.getString("title");
		String borrowStatus = "(1,2,3,4,5,6)";
		if ("2".equals(queryType)){
			borrowStatus = "2";
		} else if ("3".equals(queryType)){
			borrowStatus = "4";
		} else if ("4".equals(queryType)){
			borrowStatus = "5";
		} else if ("5".equals(queryType)){
			borrowStatus = "6";
		} else {
			borrowStatus = "(1,2,3,4,5,6)";
		}
		
		String startTime = request().getParameter("publishTimeStart");
		String endTime = request().getParameter("publishTimeEnd");
		
		String t = request.getString("t")==""?null:request.getString("t");
		if("1".equals(t)){
			myHomeService.queryBorrowRecycleByCondition1(title, user.getId(),
					pageBean);
		}else{//投资记录，进这里
			myHomeService.queryBorrowRecycleByCondition(title, user.getId(),
					pageBean,borrowStatus, startTime,  endTime,id);
			List list = pageBean.getPage();
			if (null == list){
				list = new ArrayList();
			}
			
			//hjh 2015-02-05 13:36:20  添加wap
			if (StringUtils.isNotBlank(request("wap"))) {
				try {
					JSONObject wap = new JSONObject();
					wap.put("pageBean", pageBean);
					JSONUtils.printObject(wap);
				} catch (Exception e) {
					log.error(e);
				}
				return null;
			}
			
			//封装数据，生成投资编号，日期格式处理等
			for (int i = 0; i < list.size(); i++){
				//年份后2位+借款id 补全到3位+月份+当前借款第几位投资数 补全到3位+日   T15 028 01 001 23
				try {
					StringBuffer seq = new StringBuffer("T"); 
					Map map = (Map) list.get(i);
					Date publishTime = (Date) map.get("publishTime");
					Long borrowId = (Long) map.get("borrowId");
					Long minInvestId = (Long) map.get("minInvestId");
					Long investId = (Long) map.get("id");
					Date investTime = (Date) map.get("investTime");
					SimpleDateFormat fat = new SimpleDateFormat(UtilDate.simple);
					map.put("investTime", fat.format(investTime));
					SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
					String strDate = formatter.format(publishTime);
					String year = strDate.substring(0,2);
					String month = strDate.substring(3,5);
					String dayth = strDate.substring(6,8);
					seq.append(year).append(formatData(""+borrowId)).append(month)
					.append(formatData(""+(investId - minInvestId + 1))).append(dayth);
					map.put("seq", seq.toString());
					
					//如果点击的是详情链接，则查看还款记录
					if (!StringUtil.isEmpty(id)){
						request().setAttribute("abean", map);
						//查询回款详情
						List<Map<String, Object>> listMap = myHomeService.queryBorrowForpayById(0L, user.getId(), (Long)map.get("id"));
						request().setAttribute("listMap", listMap);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		this.setRequestToParamMap();
		Map amap = myHomeService.allMyInvest(user.getId());
		session().setAttribute("investNo", amap.get("investNo"));
		session().setAttribute("investAllSum", amap.get("investAllSum"));
		if (StringUtil.isEmpty(id)){
			if (checkMobile()) {
				return "successmobile";
			}else {
				return "success";
			}
		} else {
			if (checkMobile()) {
				return "detailmobile";
			}else {
				return "detail";
			}
		}
		
	}

	public static String formatData(String str){
		if (str.length() < 3){
			str = "00"+str;
		}
		str = str.substring(str.length() - 3);
		return str;
	}
	
	/**
	 * @throws Exception
	 * @MethodName: homeBorrowRecycledList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午01:40:27
	 * @Return:
	 * @Descb: 已回收的借款
	 * @Throws:
	 */
	public String homeBorrowRecycledList() throws Exception {
		User user = (User) session().getAttribute("user");
		String queryType = (null == request().getParameter("queryType") ? "1" : request().getParameter("queryType") );
		request().setAttribute("queryType", queryType);
		
		pageBean.setPageNum(request.getString("curPage"));
		String title = request.getString("title");
		myHomeService.queryBorrowRecycledByCondition(title, user.getId(),
				pageBean);
		this.setRequestToParamMap();
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowForpayDetail
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:53:03
	 * @Return:
	 * @Descb: 查询投资人回收中借款还款详情
	 * @Throws:
	 */
	public String homeBorrowForpayDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		String id = request.getString("id") == null ? "" : request.getString("id");
		// add by houli
		String iid = request.getString("iid") == null ? "" : request.getString("iid");
		long idLong = Convert.strToLong(id, -1);
		long iidLong = Convert.strToLong(iid, -1);
		List<Map<String, Object>> listMap = myHomeService
				.queryBorrowForpayById(idLong, user.getId(), iidLong);
		/*
		 * DecimalFormat df_two = new DecimalFormat("#0.00"); //-----add by
		 * houli 2013-04-25将数字进行格式化，保留两位小数 for(Map<String,Object> map : listMap
		 * ){
		 * 
		 * map.put("forpayPrincipal", df_two.format(
		 * map.get("forpayPrincipal"))); map.put("forpayInterest",
		 * df_two.format( map.get("forpayInterest")));
		 * map.put("principalBalance", df_two.format(
		 * map.get("principalBalance"))); map.put("manage", df_two.format(
		 * map.get("manage"))); map.put("earn", String.format("%.2f",
		 * map.get("earn"))); } //
		 */request().setAttribute("listMap", listMap);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowHaspayDetail
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-27 下午06:57:20
	 * @Return:
	 * @Descb: 查询投资人已回收借款还款详情
	 * @Throws:
	 */
	public String homeBorrowHaspayDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		String id = request.getString("id") == null ? "" : request.getString("id");
		String iid = request.getString("iid") == null ? "" : request.getString("iid");

		long idLong = Convert.strToLong(id, -1);
		long iidLong = Convert.strToLong(iid, -1);
		List<Map<String, Object>> listMap = myHomeService
				.queryBorrowHaspayById(idLong, user.getId(), iidLong);
		request().setAttribute("listMap", listMap);
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowBackAcountList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-26 下午08:43:24
	 * @Return:
	 * @Descb: 借款回账查询列表
	 * @Throws:
	 */
	public String homeBorrowBackAcountList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");

		Map<String, String> backAcountStatisMap = myHomeService
				.queryBackAcountStatis(user.getId(), "", "", "");
		request().setAttribute("backAcountStatisMap", backAcountStatisMap);
		// 回账类型
		request().setAttribute("type", "5");

		pageBean.setPageNum(request.getString("curPage"));
		String title = request.getString("title");
		String publishTimeStart = request.getString("publishTimeStart");
		String publishTimeEnd = request.getString("publishTimeEnd");
		myHomeService.queryBorrowBackAcountByCondition(title, publishTimeStart,
				publishTimeEnd, user.getId(), pageBean);

		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowPublishList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午09:03:01
	 * @Return:
	 * @Descb: 审核中的借款
	 * @Throws:
	 */
	public String homeBorrowAuditList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String borrowStatus = IConstants.BORROW_STATUS_1 + ","
				+ IConstants.BORROW_STATUS_3;
		return queryBrrowPublishList(borrowStatus);
	}

	/**
	 * @throws Exception
	 * @MethodName: homeBorrowPublishList
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午09:03:01
	 * @Return:
	 * @Descb: 已经发布的借款列表
	 * @Throws:
	 */
	public String homeBorrowingList() throws Exception {
		String borrowStatus = "" + IConstants.BORROW_STATUS_2;
		return queryBrrowPublishList(borrowStatus);
	}

	private String queryBrrowPublishList(String borrowStatus) throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		pageBean.setPageNum(request.getString("curPage"));
		pageBean.setPageSize(1);
		String title = request.getString("title");
		String publishTimeStart = request.getString("publishTimeStart");
		String publishTimeEnd = request.getString("publishTimeEnd");
		myHomeService.queryBorrowFinishByCondition(title, publishTimeStart,
				publishTimeEnd, borrowStatus, user.getId(), pageBean);
		this.setRequestToParamMap();
		return SUCCESS;
	}

	/**
	 * @MethodName: automaticBidInit
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午03:09:53
	 * @Return:
	 * @Descb: 查询用户自动投标设置
	 * @Throws:
	 */
	public String automaticBidInit() throws SQLException, DataException {
		if (checkMobile()) {
			return "successmobile";
		}
		return "success";
	}

	/**
	 * @throws Exception
	 * @MethodName: automaticBidSet
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午04:33:53
	 * @Return:
	 * @Descb: 自动投标设置
	 * @Throws:
	 */
	public String automaticBidSet() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String bidStatus = paramMap.get("s") == null ? "1" : paramMap.get("s");
		long bidStatusLong = Convert.strToLong(bidStatus, 1);
		JSONObject obj = new JSONObject();
		User user = (User) session().getAttribute("user");
		long returnId = -1;
		returnId = myHomeService.automaticBidSet(bidStatusLong, user.getId());

		if (returnId <= 0) {
			obj.put("msg", "未保存自动投标设置");
		} else {
			obj.put("msg", 1);
		}
		JSONUtils.printObject(obj);
		return null;
	}

	/**
	 * @throws Exception
	 * @MethodName: automaticBidModify
	 * @Param: FrontMyHomeAction
	 * @Author: gang.lv
	 * @Date: 2013-3-28 下午05:04:24
	 * @Return:
	 * @Descb: 修改自动投标内容
	 * @Throws:
	 */
	public String automaticBidModify() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject obj = new JSONObject();
		User user = (User) session().getAttribute("user");
		long returnId = -1;

		double usableSum = Convert.strToDouble(paramMap.get("usableSum"), 0);
		String bidAmount = paramMap.get("bidAmount") == null ? "" : paramMap
				.get("bidAmount");
		String rateStart = paramMap.get("rateStart") == null ? "" : paramMap
				.get("rateStart");
		String rateEnd = paramMap.get("rateEnd") == null ? "" : paramMap
				.get("rateEnd");
		String deadlineStart = paramMap.get("deadlineStart") == null ? ""
				: paramMap.get("deadlineStart");
		String deadlineEnd = paramMap.get("deadlineEnd") == null ? ""
				: paramMap.get("deadlineEnd");
		String creditStart = paramMap.get("creditStart") == null ? ""
				: paramMap.get("creditStart");
		String creditEnd = paramMap.get("creditEnd") == null ? "" : paramMap
				.get("creditEnd");
		String remandAmount = paramMap.get("remandAmount") == null ? ""
				: paramMap.get("remandAmount");
		String borrowWay = paramMap.get("borrowWay") == null ? "" : paramMap
				.get("borrowWay");
		Double bidAmountDouble = Convert.strToDouble(bidAmount, 0);
		Double rateStartDouble = Convert.strToDouble(rateStart, 0);
		Double rateEndDouble = Convert.strToDouble(rateEnd, 0);
		Double deadlineStartDouble = Convert.strToDouble(deadlineStart, 0);
		Double deadlineEndDouble = Convert.strToDouble(deadlineEnd, 0);
		Double creditStartDouble = Convert.strToDouble(creditStart, 0);
		Double creditEndDouble = Convert.strToDouble(creditEnd, 0);
		Double remandAmountDouble = Convert.strToDouble(remandAmount, 0);
		
		if (StringUtils.isNotBlank(bidAmount) && bidAmount.indexOf(".")!=-1) {
		    obj.put("msg", "每次投标金额只能为整数");
            JSONUtils.printObject(obj);
            return null;
        }
		
		if (StringUtils.isBlank(bidAmount)) {
			obj.put("msg", "每次投标金额不可为空");
			JSONUtils.printObject(obj);
			return null;
		} else if (bidAmountDouble == 0) {
			obj.put("msg", "每次投标金额格式错误");
			JSONUtils.printObject(obj);
			return null;
		} else if (bidAmountDouble < 50) {
			obj.put("msg", "每次投标金额不能低于50元");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(rateStart)) {
			obj.put("msg", "利率范围开始不可为空");
			JSONUtils.printObject(obj);
			return null;
		} else if (rateStartDouble == 0) {
			obj.put("msg", "利率范围开始格式错误");
			JSONUtils.printObject(obj);
			return null;
		} else if (rateStartDouble < 0.1 || rateStartDouble > 24) {
			obj.put("msg", "利率范围0.1%~24%");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(rateEnd)) {
			obj.put("msg", "利率范围结束不可为空");
			JSONUtils.printObject(obj);
			return null;
		} else if (rateEndDouble == 0) {
			obj.put("msg", "利率范围结束格式错误");
			JSONUtils.printObject(obj);
			return null;
		} else if (rateEndDouble < 0.1 || rateEndDouble > 24) {
			obj.put("msg", "利率范围0.1%~24%");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(remandAmount)) {
			obj.put("msg", "账户保留金额不可为空");
			JSONUtils.printObject(obj);
			return null;
		} else if (remandAmountDouble == 0) {
			obj.put("msg", "账户保留金额格式错误");
			JSONUtils.printObject(obj);
			return null;
		}
		if (StringUtils.isBlank(borrowWay)) {
			obj.put("msg", "请勾选借款类型");
			JSONUtils.printObject(obj);
			return null;
		}
//		if (bidAmountDouble > usableSum - remandAmountDouble) {
//			obj.put("msg", "投标金额不能大于(可用余额 - 保底金额)");
//			JSONUtils.printObject(obj);
//			return null;
//		}
		returnId = myHomeService.automaticBidModify(bidAmountDouble,
				rateStartDouble, rateEndDouble, deadlineStartDouble,
				deadlineEndDouble, creditStartDouble, creditEndDouble,
				remandAmountDouble, user.getId(), borrowWay);

		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
		} else {
			obj.put("msg", IConstants.ACTION_SUCCESS);
		}
		JSONUtils.printObject(obj);
		return null;

	}

	public MyHomeService getMyHomeService() {
		return myHomeService;
	}

	public void setMyHomeService(MyHomeService myHomeService) {
		this.myHomeService = myHomeService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {
		if (borrowDeadlineList == null) {
			// 借款期限列表
			borrowDeadlineList = selectedService.borrowDeadline();
		}
		return borrowDeadlineList;
	}

	public Map<String, String> getAutomaticBidMap() throws Exception {
		if (automaticBidMap == null) {
			// 自动投标设置
			User user = (User) session().getAttribute("user");
			automaticBidMap = myHomeService.queryAutomaticBid(user.getId());
			if (automaticBidMap != null) {
			    automaticBidMap.put("bidAmount", Double.valueOf(automaticBidMap.get("bidAmount")).intValue()+"");
				// 设置ckBoxList的选中值
				String borrowWay = automaticBidMap.get("borrowWay");
				if (StringUtils.isNotBlank(borrowWay)) {
				    checkList = new ArrayList<Operator>();
				    String[] ckList = borrowWay.split(",");
	                if (ckList.length > 0) {
	                    for (String ck : ckList) {
	                        checkList.add(new Operator(ck, ""));
	                    }
	                }
                }else {
                    checkList=null;
                }
			}
		}
		return automaticBidMap;
	}
	
	/**
	 * 功能：查找三天内的充值记录
	 * @return
	 * @throws Exception 
	 * @throws DataException 
	 */
	public String money3Days() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		Date etoday = new Date();
		Date stoday = new Date(etoday.getTime() - 3 * 24 * 60 * 60 * 1000);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String endTime = formatter.format(etoday);
		String startTime = formatter.format(stoday);
		String momeyType = "3天内的充值";
		pageBean.setPageSize(5);
		pageBean.setPageNum(request.getString("pageBean.pageNum")==null ? 1:request.getString("pageBean.pageNum"));

		//充值记录
		rechargeService.queryFundrecordList3days(pageBean, user.getId(), startTime,
				endTime, momeyType);
		if (null != pageBean.getPage()){
			List<Map<String,Object>> pageBeanList2 =pageBean.getPage();
			 if (pageBeanList2!=null&&pageBeanList2.size()>0) {
                for (Map<String, Object> itemMap : pageBeanList2) {
                    if (itemMap.get("rechargeType").equals("在线充值") || itemMap.get("rechargeType").equals("移动充值")) {
                        DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                        String orderid = format.format(itemMap.get("rechargeTime"))+"_"+itemMap.get("id").toString()+"_"+itemMap.get("userId").toString();
                        itemMap.put("id", orderid);
                    }
                }
            }
			
			log.info("=======2====================" + pageBeanList2.size());
			int pageNum = (int) (pageBean.getPageNum() - 1)* pageBean.getPageSize();
			request().setAttribute("pageNum", pageNum);
//			request().setAttribute("investMoneyList",	pageBeanList2);
			//pageBean.setPage(null);
		} else {
			log.debug("---------------0.0--null == queryFundrecordList---------------------");
		}
		log.info("=======2=========SUCCESS===========");
		return SUCCESS;
	}
	
	/**
	 *功能：投资详情
	 * @return
	 */
	public String homeBorrowInvestDetail(){
		
		return SUCCESS;
	}
	
	/**
	 * 展示可用余额
	 * queryUsableSum:首页头部如果不展示的可以删除 <br/>
	 *
	 * @author he
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public String queryUsableSum() throws Exception{
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		if (user!=null) {//登录首页显示可用余额
			Map<String, String> map = userService.queryUserById(user.getId());
			JSONUtils.printStr(map.get("usableSum"));
		}
		return null;
	}
	
	public String getHuiKuanCalendar() throws SQLException, IOException{
		long userId = getUserId();
		String date = request("date");
		//查询日历
		List<Map<String, String>> dateList = financeService.queryNextRepayList(userId, date);
		StringBuffer sb = new StringBuffer();
		String dates ="";
		if (dateList!=null && dateList.size()>0) {
			for (Map<String, String> map : dateList) {
				sb.append(map.get("nextRepay")).append(",");
			}
			dates = sb.substring(0, sb.lastIndexOf(","));
		}
		//查询总计
		Map<String, String> total = financeService.queryNextRepayTotal(userId, date);
		//记录
		List<Map<String, String>> result = financeService.queryNextRepayDetail(userId, date);
		JSONObject attributes = new JSONObject();
		attributes.put("dates", dates);
		attributes.put("total", total.get("total"));
		attributes.put("result", result);
		JSONUtils.printObject(attributes);
		return null;
	}
	
	/**
	 * 
	 * queryEamilSum:站内信
	 *
	 * @author Administrator
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public String queryEamilSum() throws Exception{
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		if (user!=null) {
			//查询用户未读站内信
			Map<String, String> tmail = financeService.queryTmail(user.getId());
			JSONUtils.printStr(tmail.get("cou"));
		}
		return null;
	}
	
	public void removeSessionFlag(){
		session().setAttribute("authCardNameFlag", "0");
	}

	public List<Operator> getCheckList() {
		return checkList;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public RechargeService getRechargeService() {
		return rechargeService;
	}

	public void setRechargeService(RechargeService rechargeService) {
		this.rechargeService = rechargeService;
	}

	public NewsAndMediaReportService getNewsAndMediaReportService() {
		return newsAndMediaReportService;
	}

	public void setNewsAndMediaReportService(
			NewsAndMediaReportService newsAndMediaReportService) {
		this.newsAndMediaReportService = newsAndMediaReportService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
