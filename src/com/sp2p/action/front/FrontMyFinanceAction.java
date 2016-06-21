package com.sp2p.action.front;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.ServletUtils;
import com.shove.util.UtilDate;
import com.shove.web.util.DesSecurityUtil;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.AwardService;
import com.sp2p.service.BorrowService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.HomeInfoSettingService;
import com.sp2p.service.NewsAndMediaReportService;
import com.sp2p.service.PayTreasureService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.SysparService;
import com.sp2p.service.UserService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.service.admin.ShoveBorrowTypeService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.util.isKeywords;
import com.sun.xml.bind.v2.model.core.ID;

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的理财控制层
 */
public class FrontMyFinanceAction extends BaseFrontAction {

	public static Log log = LogFactory.getLog(FrontMyFinanceAction.class);
	private static final long serialVersionUID = 1L;

	private FinanceService financeService;
	private SelectedService selectedService;
	private Map<String, String> investDetailMap;
	private NewsAndMediaReportService newsService;
	//-add by C_J -- 标种类型  历史记录
	private  ShoveBorrowTypeService  shoveBorrowTypeService;
	private PublicModelService publicModelService;
	private SysparService sysparService;
	private UserService userService;
	
	private HomeInfoSettingService homeInfoSettingService;
	private PayTreasureService payTreasureService;
	
	
	
	
	
	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}

	public void setHomeInfoSettingService(
			HomeInfoSettingService homeInfoSettingService) {
		this.homeInfoSettingService = homeInfoSettingService;
	}

	//--
	//-add by houli
	private BorrowService borrowService;
	private BorrowManageService  borrowManageService;
	//--
	private List<Map<String, Object>> borrowMSGMap;
	
	private List<Map<String, Object>> borrowPurposeList ;
    private List<Map<String, Object>> borrowDeadlineList ;
    private List<Map<String, Object>> borrowAmountList ;
    private List<Map<String, Object>> linksList;
    private List<Map<String, Object>> meikuList;
    private List<Map<String, Object>> meikuInternetList;
    private List<Map<String, Object>> meikuSanhaoList;
    private List<Map<String, Object>> meikuStick;
    private List<Map<String, Object>> listsGGList;
    private List<Map<String, Object>> bannerList;
    	
    
    
	public void setSysparService(SysparService sysparService) {
        this.sysparService = sysparService;
    }

    /**
	 * @throws DataException
	 * @throws SQLException
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-4 上午11:16:54
	 * @Return: String
	 * @Descb: 我的理财初始化(此方法不用了)
	 * @Throws:
	 */
    @Deprecated
	public String financeInit() throws SQLException, DataException {
    	log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String mode = request.getString("m") == null ? "1" : request.getString("m");
		request().setAttribute("m", mode);
		String curPage = request.getString("curPage");
		if (StringUtils.isNotBlank(curPage)) {
			request().setAttribute("curPage", curPage);
		}
		
		// 初始化查询条件
		String title = request.getString("title");
		String paymentMode = request.getString("paymentMode");
		String purpose = request.getString("purpose");
		String raiseTerm = request.getString("raiseTerm");
		String reward = request.getString("reward");
		String arStart = request.getString("arStart");
		String arEnd = request.getString("arEnd");
		String type = request.getString("type");
		request().setAttribute("title", title);
		request().setAttribute("paymentMode", paymentMode);
		request().setAttribute("purpose", purpose);
		request().setAttribute("raiseTerm", raiseTerm);
		request().setAttribute("reward", reward);
		request().setAttribute("arStart", arStart);
		request().setAttribute("arEnd", arEnd);
		request().setAttribute("type", type);

		// 获取页面上需要的动态下拉列表

		request().setAttribute("borrowPurposeList", borrowPurposeList);
		request().setAttribute("borrowDeadlineList", borrowDeadlineList);
		request().setAttribute("borrowAmountList", borrowAmountList);
		return "success";
	}

	/**
	 * @MethodName: financeList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午08:44:15
	 * @Return:
	 * @Descb: 我的理财列表
	 * @Throws:
	 */
	public String financeList() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		/*// 前台显示列表类型
		String mode = request.getString("m");
		String title = request.getString("title");
		String paymentMode = request.getString("paymentMode");
		String purpose = request.getString("purpose");
		String deadline = request.getString("deadline");
		String reward = request.getString("reward");
		String arStart = request.getString("arStart");
		String arEnd = request.getString("arEnd");
		String type = request.getString("type");
		
		pageBean.setPageNum(request.getString("curPage"));
		
		pageBean.setPageSize(IConstants.PAGE_SIZE_10);
		String borrowWay = "";
		String borrowStatus = "";
		String borrowType = "";
		// 截取查询的类型，防止非常规操作
		if (StringUtils.isNotBlank(type)) {
			String[] types = type.split(",");
			if (types.length > 0) {
				for (int n = 0; n < types.length; n++) {
					// 是数字类型则添加到borrowType中
					if (StringUtils.isNumericSpace(types[n])) {
						borrowType += "," + types[n];
					}
				}
				if (StringUtils.isNotBlank(borrowType)) {
					borrowType = borrowType.substring(1, borrowType.length());
				}
			} else {
				if (StringUtils.isNumericSpace(type)) {
					borrowType = type;
				}
			}
		}
		if ("1".equals(mode)||"".equals(mode)) {
			// 全部借款列表,显示1 等待资料 2 正在招标中 3 已满标
			borrowStatus = "(2,3,4,5)";
			// 查询条件中的借款方式
			if (StringUtils.isNotBlank(borrowType)) {
				borrowWay = "(" + borrowType + ")";
			}
		} else if ("2".equals(mode)) {
			// 实地认证的借款
			borrowWay = "(" + IConstants.BORROW_TYPE_FIELD_VISIT + ")";
			borrowStatus = "(2,3,4,5)";
		} else if ("3".equals(mode)) {
			// 信用认证的借款
			borrowWay = "(" + IConstants.BORROW_TYPE_GENERAL + ")";
			borrowStatus = "(2,3,4,5)";
		} else if ("4".equals(mode)) {
			// 机构担保的借款
			borrowWay = "(" + IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE + ")";
			borrowStatus = "(2,3,4,5)";
		} else if ("5".equals(mode)) {
			// 最近成功的借款列表，显示4还款中 5 已还完
			borrowStatus = "(4,5)";
		}else{
			borrowStatus = "(2,3,4,5)";
		}
		financeService.queryAllBorrowByCondition(borrowStatus, borrowWay, title,
				paymentMode, purpose, deadline, reward, arStart, arEnd,
				IConstants.SORT_TYPE_DESC, pageBean);
		this.setRequestToParamMap();*/
		return SUCCESS;
	}
	
	public String financeInfo() throws Exception{
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        // 前台显示列表类型
        String mode = paramMap.get("m");
        String title = paramMap.get("title");
        String paymentMode = paramMap.get("paymentMode");
        String purpose = paramMap.get("purpose");
        String proStatus = paramMap.get("proStatus");
        String deadline = paramMap.get("deadline");
        String reward =paramMap.get("reward");
        String arStart = paramMap.get("arStart");
        String arEnd = paramMap.get("arEnd");
        String type = paramMap.get("type");
        pageBean.setPageNum(paramMap.get("curPage"));
        pageBean.setPageSize(8);
        String borrowWay = "";
        String borrowStatus = "";
        String borrowType = "";
        // 截取查询的类型，防止非常规操作
        if (StringUtils.isNotBlank(type)) {
            String[] types = type.split(",");
            if (types.length > 0) {
                for (int n = 0; n < types.length; n++) {
                    // 是数字类型则添加到borrowType中
                    if (StringUtils.isNumericSpace(types[n])) {
                        borrowType += "," + types[n];
                    }
                }
                if (StringUtils.isNotBlank(borrowType)) {
                    borrowType = borrowType.substring(1, borrowType.length());
                }
            } else {
                if (StringUtils.isNumericSpace(type)) {
                    borrowType = type;
                }
            }
        }
        if ("1".equals(mode)||"".equals(mode)) {
            // 全部借款列表,显示1 等待资料 2 正在招标中 3 已满标
            borrowStatus = "(2,3,4,5)";
            // 查询条件中的借款方式
            if (StringUtils.isNotBlank(borrowType)) {
                borrowWay = "(" + borrowType + ")";
            }
        } else if ("2".equals(mode)) {
            // 实地认证的借款
            borrowWay = "(" + IConstants.BORROW_TYPE_FIELD_VISIT + ")";
            borrowStatus = "(2,3,4,5)";
        } else if ("3".equals(mode)) {
            // 信用认证的借款
            borrowWay = "(" + IConstants.BORROW_TYPE_GENERAL + ")";
            borrowStatus = "(2,3,4,5)";
        } else if ("4".equals(mode)) {
            // 机构担保的借款
            borrowWay = "(" + IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE + ")";
            borrowStatus = "(2,3,4,5)";
        } else if ("5".equals(mode)) {
            // 最近成功的借款列表，显示4还款中 5 已还完
            borrowStatus = "(4,5)";
        }else{
            borrowStatus = "(2,3,4,5)";
        }
        financeService.queryAllBorrowByCondition(borrowStatus, borrowWay, title,
        		proStatus, purpose, deadline, reward, arStart, arEnd,
                IConstants.SORT_TYPE_DESC, pageBean);
        if (request("wap")!=null) {
			JSONObject jsonMap = new JSONObject();
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
			return null;
		}
        return SUCCESS;
	}
	
	
	/**
	 * @MethodName: experience
	 * @Param: FrontMyFinanceAction
	 * @Author: 李艳华
	 * @Date: 2013-3-18 上午09:29:33
	 * @Return:
	 * @Descb: 最新一期投资体验标的详情页面跳转
	 * @Throws:
	 */
	public String experience() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		User user = (User) session().getAttribute("user");
		
		long userId = -1;
		if(user != null){
			userId = user.getId();
		}
		
		//最新一期投资体验标的详情
		Map<String, String> lastestExperienceMap = financeService.queryLastestExperience();
		log.info(lastestExperienceMap.get("remainTime"));
		String id = lastestExperienceMap.get("id") == null ? ""
				: lastestExperienceMap.get("id");
		
		long idLong = Convert.strToLong(id, -1);
		
		Date endTime = DateUtil.strToDate(lastestExperienceMap.get("remainTimeEnd"));
		Date nowTime = new  Date();
		
		lastestExperienceMap.put("remainTime", String.valueOf((endTime.getTime()-nowTime.getTime())/1000));
		
		lastestExperienceMap.put("SurplusAmount", String.format("%.2f",(Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0)-Convert.strToDouble(lastestExperienceMap.get("hasInvestAmount"),0.0))));
		lastestExperienceMap.put("SurplusAmountsecend", String.format("%.2f",((Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0)-Convert.strToDouble(lastestExperienceMap.get("hasInvestAmount"),0.0))/10000)));
		
		lastestExperienceMap.put("borrowAmount", String.format("%.2f",(Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0))));
		lastestExperienceMap.put("borrowAmountsecend", String.format("%.2f",(Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0)/10000)));
		
		request().setAttribute("lastestExperienceMap", lastestExperienceMap);
		
		//用户拥有的体验券信息
		List<Map<String, Object>> experienceList = financeService
				.queryExperienceByid(userId,Convert.strToDouble(lastestExperienceMap.get("minTenderedSum"), 0.0),Convert.strToDouble(lastestExperienceMap.get("maxTenderedSum"),0.0));
		
		for (Map<String, Object> map : experienceList) {
            try {
                String disableDate = map.get("disableDate").toString();
                map.put("str", map.get("amount") +"            |       "+ disableDate.split(" ")[0]);
            }
            catch (Exception e) {
            }
        }
		
		request().setAttribute("experienceList", experienceList);
		
		//投资记录
		List<Map<String, Object>> investExperienceList = financeService
				.queryInvestExperienceByid(idLong);
		int investExperienceListCount = investExperienceList.size();
		request().setAttribute("investExperienceList", investExperienceList);
		request().setAttribute("investExperienceListCount", investExperienceListCount);
		
		
		//查询用户是否已投递此标
        List<Map<String, Object>> haslist = financeService .queryInvestExperienceByUser(idLong, userId);
        if (haslist!=null&&haslist.size()>0) {
            request().setAttribute("isInvest", "1");
        }
		
		return "success";
	}
	
	

    // 通过id查询某个体验标的详情
    public String experienceDetail() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		long userId = -1;
		if(user != null){
			userId = user.getId();
		}
		String borrowId = request.getString("id");
		int borrowid = Convert.strToInt(borrowId, -1);
		Map<String, String> lastestExperienceMap = financeService.queryExperienceDetail(borrowid);
		log.info(lastestExperienceMap.get("remainTime"));
		
		
		String id = lastestExperienceMap.get("id") == null ? ""
				: lastestExperienceMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		if (lastestExperienceMap == null ) {
                return "input";
        }
		
		lastestExperienceMap.put("SurplusAmount", String.format("%.2f",(Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0)-Convert.strToDouble(lastestExperienceMap.get("hasInvestAmount"),0.0))));
        lastestExperienceMap.put("SurplusAmountsecend", String.format("%.2f",((Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0)-Convert.strToDouble(lastestExperienceMap.get("hasInvestAmount"),0.0))/10000)));
        
        lastestExperienceMap.put("borrowAmount", String.format("%.2f",(Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0))));
        lastestExperienceMap.put("borrowAmountsecend", String.format("%.2f",(Convert.strToDouble(lastestExperienceMap.get("borrowAmount"),0.0)/10000)));

		request().setAttribute("lastestExperienceMap", lastestExperienceMap);
		
		//用户拥有的体验券信息
//		List<Map<String, Object>> experienceList = financeService
//				.queryExperienceByid(userId,Convert.strToDouble(lastestExperienceMap.get("minTenderedSum"), 0.0),Convert.strToDouble(lastestExperienceMap.get("maxTenderedSum"), 0.0));
//		request().setAttribute("experienceList", experienceList);
//		//投资记录
//		List<Map<String, Object>> investExperienceList = financeService
//				.queryInvestExperienceByid(idLong);
//		int investExperienceListCount = investExperienceList.size();
//		request().setAttribute("investExperienceList", investExperienceList);
//		request().setAttribute("investExperienceListCount", investExperienceListCount);
		//查询用户是否已投递此标
        List<Map<String, Object>> haslist = financeService .queryInvestExperienceByUser(idLong, userId);
        if (haslist!=null&&haslist.size()>0) {
            request().setAttribute("isInvest", "1");
        }
		return "success";
	}
	
    
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private  Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
    
	/**
	 * 立即体验
	 * experienceStart(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @auth hejiahua
	 * @return 
	 * String
	 * @throws Exception 
	 * @exception 
	 * @date:2014-8-26 上午10:59:23
	 * @since  1.0.0
	 */
	public String experienceStart() throws Exception{
	    log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
	    String idstr = paramMap.get("id");
	    String ticketNo = paramMap.get("ticketNo");
	    Object algorithm =  session().getAttribute("algorithm");
	    String code = paramMap.get("code");
	    if (StringUtils.isBlank(code)) {
	        JSONUtils.printStr("7");//请输入验证码
            return null;
        }
	    if (algorithm == null) {
            JSONUtils.printStr("8");//请重新获取验证码
            return null;
        }
	    if (!code.equals(algorithm.toString())) {
	        Map<String,Cookie> cookieMap = ReadCookieMap(request());
	        Cookie cookie = null;
	        if(cookieMap.containsKey("algorithm")){
	            cookie = (Cookie)cookieMap.get("algorithm");
	            cookie.setValue((Convert.strToInt(cookie.getValue(), 1)+1)+"");
	        }else {
                cookie = new Cookie("algorithm", "1");
            } 
	        cookie.setMaxAge(600);
            response().addCookie(cookie);
            JSONUtils.printStr("9");//验证码不正确
            return null;
        }else {
            Map<String,Cookie> cookieMap = ReadCookieMap(request());
            Cookie cookie = null;
            if(cookieMap.containsKey("algorithm")){
                cookie = (Cookie)cookieMap.get("algorithm");
                int value = Convert.strToInt(cookie.getValue(), 4);
                if (value>3) {
                    JSONUtils.printStr("10");//验证码不正确
                    return null;
                }
            }
        }
	    
	    
	    
	    User user = (User) session().getAttribute("user");
        if (user==null) {
            JSONUtils.printStr("6");//请登录
            return null;
        }
	    
	    int id=-2;
	    if (StringUtils.isBlank(idstr)) {
            JSONUtils.printStr("4");//参数有误，请重新执行。
            return null;
        }else {
            id = Convert.strToInt(idstr, -1);
        }
	    
	    if (StringUtils.isBlank(ticketNo)) {
            JSONUtils.printStr("5");//请选择体验券
            return null;
        }
	    
	   
	    
	    //每次投资只能使用一张体验券，每个用户每个体验标，只能投资一次
	    int result = financeService.validateTicket(id, ticketNo, user);
	    JSONUtils.printStr(result+"");
	    
	    return null;
	}
	

	/**
	 * @MethodName: financeLastestList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:29:33
	 * @Return:
	 * @Descb: 最新借款列表前10条记录
	 * @Throws:
	 */
	public String financeLastestList() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			List<Map<String, Object>> mapList = financeService
					.queryLastestBorrow();
			request().setAttribute("mapList", mapList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * @MethodName: investRank
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午11:24:23
	 * @Return:
	 * @Descb: 投资排名前20条记录
	 * @Throws:
	 */
	public String investRank() {
		List<Map<String, Object>>  rankList = new ArrayList<Map<String,Object>>(); 
		try {
			int number =Convert.strToInt( paramMap.get("number"),1);
				//当前年
			rankList = financeService.investRank(number,8);
			request().setAttribute("rankList", rankList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	//理财排行榜
	public String getRanking() throws Exception {
		String type = request("type");
		int typeint = Convert.strToInt(type, 0);
		List<Map<String, Object>> rankList = financeService.investRank(typeint,10);
		JSONObject object = new JSONObject();
		object.put("list", rankList);
		JSONUtils.printObject(object);
		return null;
	}
	
	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: index
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:46:12
	 * @Return:
	 * @Descb: 首页加载内容
	 * @Throws:
	 */
	public String index() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Map<String, String> totalRiskMap = financeService.queryTotalRisk();
		Map<String, String> currentRiskMap = financeService.queryCurrentRisk();
		
		request().setAttribute("totalRiskMap", totalRiskMap);
		request().setAttribute("currentRiskMap", currentRiskMap);
		
		String source = request().getParameter("source");
		if (null != source){
			sourceSave(source);
			request().setAttribute("source", source);
		}
		
		//涨薪宝
		Map<String, Object> map = payTreasureService.paytreasuredetail(getUserId());
		request().setAttribute("zxb", map);
		
		//行业优选标的
		financeService.queryBestBorrow(null, null, null,null, 
				null, null, null, null, null,
				IConstants.SORT_TYPE_DESC, pageBean);
		List<Map<String,Object>> pageBeanBest =pageBean.getPage();
		request().setAttribute("pageBeanBest",	pageBeanBest);
		
		//新手标
		Map<String, String> newBorrow = financeService.queryNewBorrow();
		request().setAttribute("newBorrow",	newBorrow);
		
		//标的信息列表
		financeService.queryBorrowByCondition(null, null, null,
				null, null, null, null, null, null,
				IConstants.SORT_TYPE_DESC, pageBean);
		List<Map<String,Object>> pageBeanList =pageBean.getPage();
		request().setAttribute("pageBeanList",	pageBeanList);
		pageBean.setPage(null);
		
		
        //最新借款列表
		/*List<Map<String, Object>> mapList = financeService.queryLastestBorrow();
		request().setAttribute("mapList", mapList);*/
		
		//最新一期投资体验标的详情
		//Map<String, String> lastestExperienceMap = financeService.queryLastestExperience();
		
		//图片滚动
		pageBean.setPageSize(5);
		///	linksService.queryBannerListPage(pageBean);
		publicModelService.queryBannerListPage(pageBean);
		bannerList =pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("bannerList", bannerList);
		
		if ("wap".equals(paramMap.get("wap"))) {//wap站点
            JSONObject wap = new JSONObject();
            wap.put("pageBeanBest", pageBeanBest);
            wap.put("bannerList", bannerList);
            wap.put("zxb", map);
            com.shove.util.JSONUtils.printObject(wap);
            return null;
        }
		
//		int amount_scale = System.Convert.ToInt32(lastestExperienceMap.amount_scale);
//		String amount_scale = lastestExperienceMap.get("amount_scale");
		//int amount_scale = Convert.strToInt(lastestExperienceMap.get("amount_scale"),-1);
//		int amount_scale =  Integer.parseInt(str_amount_scale);
		//request().setAttribute("lastestExperienceMap", lastestExperienceMap);
//		request().setAttribute("amount_scale", amount_scale);
		
		//排名前8条记录
		/*//全部
		List<Map<String, Object>> rankList = financeService.investRank(0,8);
		request().setAttribute("rankList",rankList);*/
		
		
		//公告
		List<Map<String,Object>> newsList = new ArrayList<Map<String,Object>>();
		pageBean.setPageSize(3);
		newsService.frontQueryNewsPage(pageBean,null);//普通公告
		newsList = pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("newsList", newsList);
//		//成功故事
//		List<Map<String,Object>> storyList = new ArrayList<Map<String,Object>>();
//		pageBean.setPageSize(2);
	/// successStoryService.querySuccessStoryPage(pageBean);
		/*publicModelService.querySuccessStoryPage(pageBean);
	    storyList = pageBean.getPage();
		pageBean.setPage(null);*/
		/*request().setAttribute("storyList", storyList);*/
		//友情链接,合作连接
		pageBean.setPageSize(100);
	///	linksService.queryLinksPage(pageBean);
		publicModelService.queryLinksListPageBack(pageBean);
		linksList =pageBean.getPage();
		pageBean.setPage(null);
//		request().setAttribute("linksList", linksList);
		request().setAttribute("linksList", linksList);
		//媒体报道取n条记录
		pageBean.setPageSize(3);
	///	mediaReportService.queryMediaReportPage(pageBean,1);
		newsService.queryMediaReportPage(pageBean,1);
		
		meikuList = pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("meikuStick", meikuList);
		
		//三好资本动态 取n条记录
		pageBean.setPageSize(3);
		newsService.queryMediaReportSanhaoPage(pageBean,1);
		meikuSanhaoList = pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("meikuSanhaoStick", meikuSanhaoList);
		
		//互联网金融 取n条记录
		pageBean.setPageSize(4);
		newsService.queryMediaReportInternetPage(pageBean,1,"2");
		meikuInternetList = pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("meikuInternetStick", meikuInternetList);
		
		
		//三好资本问答 取n条记录
		pageBean.setPageSize(4);
		newsService.queryMediaReportInternetPage(pageBean,1,"3");
		meikuInternetList = pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("sanhaodaiQA", meikuInternetList);
		
//		request().setAttribute("meikuList", meikuList);
		//投资广告
		pageBean.setPageSize(3);
///		linksService.queryLinksGGPage(pageBean);
		publicModelService.queryLinksGGPage(pageBean);
		listsGGList =pageBean.getPage();
		pageBean.setPage(null);
		request().setAttribute("listsGGList", listsGGList);
		//得到置顶的媒体报道
		pageBean.setPageSize(5);
//		pageBean.setPageSize(2);
///		mediaReportService.queryMediaReportPage(pageBean,2);
//		newsService.queryMediaReportPage(pageBean,2);
//		meikuStick = pageBean.getPage();
//		pageBean.setPage(null);
//		request().setAttribute("meikuStick", meikuStick);
		//得到用户对象
		User user = (User) session().getAttribute("user");
		if (user!=null) {
			paramMap = userService.queryUserById(user.getId());
			Map<String, String> safeMap = financeService.queryUserSafe(user.getId());
			request().setAttribute("safeMap", safeMap);
		}
		
		//得到排名
		/*String no_uid = null;
		try {no_uid = sysparService.querySysparBySql("SELECT t.`introduce` as no_uid FROM t_select t WHERE t.`typeId` = 20 AND t.`selectKey`=\"LCB\" ").get("no_uid").split("&")[0];
		} catch (Exception e) {no_uid="-1";}
		pageBean.setPageSize(10);
		financeService.queryInvestRanking(pageBean,no_uid);
		request().setAttribute("investRanking", pageBean.getPage());
		pageBean.setPage(null);*/
		
		//所有公告
		pageBean.setPageSize(5);
		newsService.frontQueryNewsPage(pageBean,null);
		request().setAttribute("notices", pageBean.getPage());
		
		
		return "success";
	}
	
	private static Map <String,Integer>souMap = new HashMap<String,Integer>();
	private static int stimes = 0;
	/**
	 *功能：保存来源到表中
	 * @param source
	 */
	private void sourceSave(String source){
		stimes ++;
		DateFormat dft = new SimpleDateFormat(UtilDate.dtShort);
		String today = dft.format(new Date());

		if (null == souMap.get(source)){
			souMap.put(source, 1);
		} else {
			souMap.put(source, souMap.get(source)+1);
		}
		
		//如果来源数大于10，或日期变了，则保存
		if (stimes >= 10 || !ptoday.equals(today)){
			//保存到表 start  TODO
			try {
				userService.sourceStatistic(souMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//完成保存 end
			
			//如果日期不对，则更新日期，并清理数据
			if (!ptoday.equals(today)){
                ptoday = today;
            }
			souMap = new HashMap<String,Integer>();
			stimes = 0;
		}
		
	}
	
	
	
	/*
	 * @MethodName: financeToolInit
	 * 
	 * @Param: FrontMyFinanceAction
	 * 
	 * @Author: gang.lv
	 * 
	 * @Date: 2013-3-4 下午01:30:25
	 * 
	 * @Return:理财工具箱
	 * 
	 * @Descb:
	 * 
	 * @Throws:
	 */
	public String financeToolInit() {
		return "success";
	}
	
	
	/**
	 * 投资体验说明书
	 * @throws Exception
	 * @MethodName: investment_experience_instruction
	 * @Param: UserAction
	 * @Author: 李艳华
	 * @Date: 2013-5-11 下午02:38:52
	 * @Return:
	 * @Descb:
	 * @Throws:
	 */
	public String investment_experience_instruction() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
//		paramMap = agreementService.getAgreementDetail(1, 1);
//		paramMap = agreementService.getMessageByTypeId(31);
		return SUCCESS;
	}
	
	

	/**
	 * @throws SQLException
	 * @throws DataException
	 * @MethodName: financeDetail
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:40:38
	 * @Return:
	 * @Descb: 理财中的借款明细
	 * @Throws:
	 */
	public String financeDetail()  throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		session().setAttribute("DEMO", IConstants.ISDEMO);//得到是不是演示版本
		
		String idStr = request.getString("id") == null ? "" : request.getString("id");
		String userType = null;
		if (!"".equals(idStr) && StringUtils.isNumericSpace(idStr)) {
			Long id = Convert.strToLong(idStr, -1);
			// 借款详细
			Map<String, String> borrowDetailMap = financeService.queryBorrowDetailById(id);
			
			if (borrowDetailMap == null)
			{
				return "404";
			}
			JSONObject object = null;
			if (StringUtils.isNotBlank(request("wap"))) {//wap站
	            object = new JSONObject();
	        }
			
			//hjh  担保机构和担保方式
		   String agent = borrowDetailMap.get("agent");
		   String agentWay = borrowDetailMap.get("agentWay");
		   Map<String, String> agentMap =	sysparService.querySysparChildById("selectName,introduce", Convert.strToInt(agent, -1));
		   Map<String, String> agentWayMap =  sysparService.querySysparChildById("selectName,introduce", Convert.strToInt(agentWay, -1));
		   if (agentMap!=null) {
		       borrowDetailMap.put("agent", agentMap.get("selectName"));
		       borrowDetailMap.put("introduce", agentMap.get("introduce"));
            }
		   if (agentWayMap!=null) {
		       borrowDetailMap.put("agentWay", agentWayMap.get("selectName"));
		       borrowDetailMap.put("agentWayintroduce", agentWayMap.get("introduce"));
           }
		   
			userType = borrowDetailMap.get("userType");
			if(!userType.equals(null) && "1".equals(userType)){//userType=1 进入个人用户处理
				//-- 7 - 9
				//查询借款信息得到借款时插入的平台收费标准
				Map<String,String> map = borrowManageService.queryBorrowInfo(id);
				if (map == null) {
					return "404";
				}
				borrowDetailMap.put("displayTime", map.get("displayTime"));
				//得到收费标准的json代码
				String feelog = Convert.strToStr(map.get("feelog"), "");
				Map<String,Double> feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
				//--end
				String nid_log = borrowDetailMap.get("nid_log");
				Map<String,String>  TypeLogMap = null;
				if (StringUtils.isNotBlank(nid_log)) {
					TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
					int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"),-1);
					request().setAttribute("subscribes",stauts );
					if (object!=null) {
                        object.put("subscribes", "stauts");
                    }
				}
				if (borrowDetailMap != null && borrowDetailMap.size() > 0) {
					double borrowSum = Convert.strToDouble(borrowDetailMap.get("borrowSum")+"", 0);
					double annualRate = Convert.strToDouble(borrowDetailMap.get("annualRate")+"", 0);
					int deadline = Convert.strToInt(borrowDetailMap.get("deadline")+"", 0);
					int paymentMode = Convert.strToInt(borrowDetailMap.get("paymentMode")+"", -1);
					int isDayThe = Convert.strToInt(borrowDetailMap.get("isDayThe")+"", 1);
					
					double investAmount = 10000;
					String earnAmount = "";
					if(borrowSum < investAmount){
						investAmount = borrowSum;
					}
					AmountUtil au = new AmountUtil();
					Map<String,String> earnMap = null;
					double costFee = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE)+"",0);
					if(paymentMode == 1){
						//按月等额还款
						earnMap = au.earnCalculateMonth(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2,costFee);
						earnAmount = earnMap.get("msg")+"";
					}else if(paymentMode == 2){
						//先息后本
						earnMap = au.earnCalculateSum(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2);
						earnAmount = earnMap.get("msg")+"";
					}else if(paymentMode == 3){
						//秒还
						earnMap = au.earnSecondsSum(investAmount, borrowSum, annualRate, deadline,0, 2);
						earnAmount = earnMap.get("msg")+"";
					} else if (paymentMode == 4) {
						// 一次性还款
						earnMap = au.earnCalculateOne(investAmount, borrowSum,
								annualRate, deadline, 0, isDayThe, 2, costFee);
						earnAmount = earnMap.get("msg") + "";
					}
					;
					session().setAttribute("realAmount", earnMap.get("realAmount"));
					session().setAttribute("totalInterest", earnMap.get("totalInterest"));
					if (object!=null) {
                        object.put("realAmount",  earnMap.get("realAmount"));
                        object.put("totalInterest",  earnMap.get("totalInterest"));
                    }
					//催收记录
					List<Map<String, Object>> collection = financeService.queryCollectionByid(id);
					if(collection != null && collection.size()>0)
						request().setAttribute("colSize", collection.size());
					request().setAttribute("earnAmount", earnAmount);
					if (object!=null) {
                        object.put("colSize",   collection.size());
                        object.put("earnAmount",   earnAmount);
                    }
					// 每次点击借款详情时新增浏览量
					financeService.addBrowseCount(id);
					request().setAttribute("borrowDetailMap", borrowDetailMap);
					if (object!=null) {
                        object.put("borrowDetailMap", borrowDetailMap);
                    }
					// 借款人资料
					Map<String, String> borrowUserMap = financeService
							.queryUserInfoById(id);
					request().setAttribute("borrowUserMap", borrowUserMap);
					if (object!=null) {
                        object.put("borrowUserMap", borrowUserMap);
                    }
					// 借款人认证资料
					List<Map<String, Object>> list = financeService
							.queryUserIdentifiedByid(id);
					request().setAttribute("list", list);
					if (object!=null) {
                        object.put("list", list);
                    }
					// 投资记录
					List<Map<String, Object>> investList = financeService
							.queryInvestByid(id);
					request().setAttribute("investList", investList);
					request().setAttribute("idStr", idStr);
					if (object!=null) {
                        object.put("investList", investList);
                        object.put("idStr", idStr);
                    }
					Map<String,String> borrowRecordMap = financeService.queryBorrowRecord(id);
					request().setAttribute("borrowRecordMap", borrowRecordMap);
					if (object!=null) {
                        object.put("borrowRecordMap", borrowRecordMap);
                    }
					//-----------add by houli
					String borrowWay = borrowDetailMap.get("borrowWay");
					String wStatus = judgeStatus(Convert.strToInt(borrowWay, -1),
							Convert.strToLong(borrowDetailMap.get("publisher"), -100));
					if(wStatus == null){
						request().setAttribute("wStatus", "");
						if (object!=null) {
	                        object.put("wStatus", "");
	                    }
					}else{
						request().setAttribute("wStatus", wStatus);
						if (object!=null) {
	                        object.put("wStatus", wStatus);
	                    }
					}
					// 借款显示类型，如果是流转标就跳转到流转标显示页面
					String cicuration = borrowDetailMap.get("borrowShow") + "";
					if (cicuration.equals("2")) {
						return "cicuration";
					}
				} else {
					return "404";
				}
			}else if(!userType.equals(null) && "2".equals(userType)){//userType=2 进入企业类型
				//查询借款信息得到借款时插入的平台收费标准
				Map<String,String> map = borrowManageService.queryBorrowInfo2(id);
				if (map == null) {
					return "404";
				}
				borrowDetailMap.put("displayTime", map.get("displayTime"));
				//得到收费标准的json代码
				String feelog = Convert.strToStr(map.get("feelog"), "");
				Map<String,Double> feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
				//--end
				String nid_log = borrowDetailMap.get("nid_log");
				Map<String,String>  TypeLogMap = null;
				if (StringUtils.isNotBlank(nid_log)) {
					TypeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
					int stauts = Convert.strToInt(TypeLogMap.get("subscribe_status"),-1);
					request().setAttribute("subscribes",stauts );
					if (object!=null) {
                        object.put("subscribes", stauts);
                    }
				}
				if (borrowDetailMap != null && borrowDetailMap.size() > 0) {
					double borrowSum = Convert.strToDouble(borrowDetailMap.get("borrowSum")+"", 0);
					double annualRate = Convert.strToDouble(borrowDetailMap.get("annualRate")+"", 0);
					int deadline = Convert.strToInt(borrowDetailMap.get("deadline")+"", 0);
					int paymentMode = Convert.strToInt(borrowDetailMap.get("paymentMode")+"", -1);
					int isDayThe = Convert.strToInt(borrowDetailMap.get("isDayThe")+"", 1);
					double investAmount = 10000;
					String earnAmount = "";
					if(borrowSum < investAmount){
						investAmount = borrowSum;
					}
					AmountUtil au = new AmountUtil();
					Map<String,String> earnMap = null;
					double costFee = Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE)+"",0);
					if(paymentMode == 1){
						//按月等额还款
						earnMap = au.earnCalculateMonth(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2,costFee);
						earnAmount = earnMap.get("msg")+"";
					}else if(paymentMode == 2){
						//先息后本
						earnMap = au.earnCalculateSum(investAmount, borrowSum, annualRate, deadline, 0, isDayThe, 2);
						earnAmount = earnMap.get("msg")+"";
					}else if(paymentMode == 3){
						//秒还
						earnMap = au.earnSecondsSum(investAmount, borrowSum, annualRate, deadline,0, 2);
						earnAmount = earnMap.get("msg")+"";
					} else if (paymentMode == 4) {
						// 一次性还款
						earnMap = au.earnCalculateOne(investAmount, borrowSum,
								annualRate, deadline, 0, isDayThe, 2, costFee);
						earnAmount = earnMap.get("msg") + "";
					}
					//----------add by houli 借款类型判断，前台借款详细信息中需要显示
					String borrowWay = borrowDetailMap.get("borrowWay");
					//催收记录
					List<Map<String, Object>> collection = financeService.queryCollectionByid(id);
					if(collection != null && collection.size()>0)
						request().setAttribute("colSize", collection.size());
					
					request().setAttribute("earnAmount", earnAmount);
					if (object!=null) {
                        object.put("colSize", collection.size());
                        object.put("earnAmount", earnAmount);
                    }
					// 每次点击借款详情时新增浏览量
					financeService.addBrowseCount(id);
					request().setAttribute("borrowDetailMap", borrowDetailMap);
					if (object!=null) {
                        object.put("borrowDetailMap", borrowDetailMap);
                    }
					// 融资资料
					Map<String, String> borrowUserMap = financeService
							.queryUserInfoById2(id);
					request().setAttribute("borrowUserMap", borrowUserMap);
					if (object!=null) {
                        object.put("borrowUserMap", borrowUserMap);
                    }
					// 通过id查询融资用户的名称
					Map<String, String> borrowUserOrgMap = financeService.queryUserInfoById3(id);
					String borrowOrgName = borrowUserOrgMap.get("username");
					request().setAttribute("borrowOrgName", borrowOrgName);
					if (object!=null) {
                        object.put("borrowOrgName", borrowOrgName);
                    }
					// 融资认证资料
					List<Map<String, Object>> list = financeService
							.queryUserIdentifiedByid2(id);
					request().setAttribute("list", list);
					if (object!=null) {
                        object.put("list", list);
                    }
					// 投资记录
					List<Map<String, Object>> investList = financeService
							.queryInvestByid(id);
					request().setAttribute("investList", investList);
					request().setAttribute("idStr", idStr);
					
					if (object!=null) {
                        object.put("investList", investList);
                        object.put("idStr", idStr);
                    }
					
					Map<String,String> borrowRecordMap = financeService.queryBorrowRecord(id);
					request().setAttribute("borrowRecordMap", borrowRecordMap);
					if (object!=null) {
                        object.put("borrowRecordMap", borrowRecordMap);
                    }
					//-----------add by houli
					String wStatus = judgeStatus(Convert.strToInt(borrowWay, -1),
							Convert.strToLong(borrowDetailMap.get("publisher"), -100));
					if(wStatus == null){
						request().setAttribute("wStatus", "");
						if (object!=null) {
	                        object.put("wStatus", "");
	                    }
					}else{
						request().setAttribute("wStatus", wStatus);
						if (object!=null) {
                            object.put("wStatus", wStatus);
                        }
					}
					// 借款显示类型，如果是流转标就跳转到流转标显示页面
					String cicuration = borrowDetailMap.get("borrowShow") + "";
					if (cicuration.equals("2")) {
						return "cicuration";
					}
				} else {
					return "404";
				}
			}else {//不为个人类型1,也不为企业类型2。返回404页面
				return "404";
			}
			
			//----------add by houli 借款类型判断，前台借款详细信息中需要显示
            String borrowWay = borrowDetailMap.get("borrowWay");
            if(borrowWay.equals(IConstants.BORROW_TYPE_NET_VALUE)){
                request().setAttribute("borrowWay", IConstants.BORROW_TYPE_NET_VALUE_STR);
                request().setAttribute("introduce", IConstants.BORROW_TYPE_NET_VALUE_INT);
                if (object!=null) {
                    object.put("borrowWay",   IConstants.BORROW_TYPE_NET_VALUE_STR);
                    object.put("introduce",   IConstants.BORROW_TYPE_NET_VALUE_INT);
                }
            }else if(borrowWay.equals(IConstants.BORROW_TYPE_SECONDS)){
                request().setAttribute("borrowWay", IConstants.BORROW_TYPE_SECONDS_STR);
                request().setAttribute("introduce", IConstants.BORROW_TYPE_SECONDS_INT);
                if (object!=null) {
                    object.put("borrowWay",   IConstants.BORROW_TYPE_SECONDS_STR);
                    object.put("introduce",   IConstants.BORROW_TYPE_SECONDS_INT);
                }
            }else if(borrowWay.equals(IConstants.BORROW_TYPE_GENERAL)){
                request().setAttribute("borrowWay", IConstants.BORROW_TYPE_GENERAL_STR);
                request().setAttribute("introduce", IConstants.BORROW_TYPE_GENERAL_INT);
                if (object!=null) {
                    object.put("borrowWay",   IConstants.BORROW_TYPE_GENERAL_STR);
                    object.put("introduce",   IConstants.BORROW_TYPE_GENERAL_INT);
                }
            }else if(borrowWay.equals(IConstants.BORROW_TYPE_FIELD_VISIT)){
                request().setAttribute("borrowWay", IConstants.BORROW_TYPE_FIELD_VISIT_STR);
                request().setAttribute("introduce", IConstants.BORROW_TYPE_FIELD_VISIT_INT);
                if (object!=null) {
                    object.put("borrowWay",   IConstants.BORROW_TYPE_FIELD_VISIT_STR);
                    object.put("introduce",   IConstants.BORROW_TYPE_FIELD_VISIT_INT);
                }
            }else if(borrowWay.equals(IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE)){
                request().setAttribute("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
                request().setAttribute("introduce", IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_INT);
                if (object!=null) {
                    object.put("borrowWay",   IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_STR);
                    object.put("introduce",   IConstants.BORROW_TYPE_INSTITUTION_GUARANTEE_INT);
                }
            }else if (borrowWay.equals(IConstants.BORROW_TYPE_INSTITUTION_FLOW)){//流转标
                request().setAttribute("borrowWay", IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
                request().setAttribute("introduce", IConstants.BORROW_TYPE_INSTITUTION_FLOW_INT);
                if (object!=null) {
                    object.put("borrowWay",   IConstants.BORROW_TYPE_INSTITUTION_FLOW_STR);
                    object.put("introduce",   IConstants.BORROW_TYPE_INSTITUTION_FLOW_INT);
                }
            }else if (borrowWay.equals(IConstants.BORROW_TYPE_TRANSFER)){//债权转让
                request().setAttribute("borrowWay", IConstants.BORROW_TYPE_TRANSFER_STR);
                request().setAttribute("introduce", IConstants.BORROW_TYPE_TRANSFER_INT);
                if (object!=null) {
                    object.put("borrowWay",   IConstants.BORROW_TYPE_TRANSFER_STR);
                    object.put("introduce",   IConstants.BORROW_TYPE_TRANSFER_INT);
                }
            }
			
			//查询出用户的可用金额hjh
	        User user = (User) session().getAttribute(IConstants.SESSION_USER);
	        if (user!=null) {
	            long uid = user.getId();
	            Map<String, String> usableSum = userService.queryUserAmount(uid);
	            if (usableSum!=null) {
	                request().setAttribute("usableSum",usableSum.get("usableSum"));
	                request().setAttribute("presrent", usableSum.get("presrent"));
	                if (object!=null) {
	                    object.put("usableSum",   usableSum.get("usableSum"));
	                    object.put("presrent",   usableSum.get("presrent"));
	                }
	            }
	        }
	        
	        //查询用户的相关材料 hjh
	        int isa = -1;//是否打码   0 打码 1 不打码
	        //查询用户是否投了此标  如果投了  则显示非打码的图片，否则显示打码图片
	        if (user==null) {
                isa = 0;
            }else {
                int size = financeService.queryInvestByidAndInvestor(id, user.getId());
                if (size>0) {//如果大于0.表示此用户投了此标，显示打码的
                    isa = 1;
                }else {
                    isa = 0;
                }
            }
	        double investAmount =  Convert.strToDouble(borrowDetailMap.get("investAmount").replace(",", ""), 0);
            double minTenderedSum =  Convert.strToDouble(borrowDetailMap.get("minTenderedSum"), 0);
            if(investAmount <minTenderedSum ){
                borrowDetailMap.put("minTenderedSum", investAmount+"");
            }
	        
			if (minTenderedSum >= investAmount ){
				borrowDetailMap.put("minInvestCal", "" + investAmount);
			} else {
				borrowDetailMap.put("minInvestCal", "" + minTenderedSum);
			}
            
	        List<Map<String, Object>> related =  financeService.queryRelated(id, isa);
	        request().setAttribute("related", related);
	        
	        if (object!=null) {
                object.put("related",   related);
            }
	        
	        // 借款人还款记录  hjh
	        List<Map<String, Object>> list = financeService
	                .queryRePayByid(id);
	        request().setAttribute("repayList", list);
	        
	        if (object!=null) {
                object.put("repayList",   list);
            }
	        
	        //查询可投的金额倍数
	        Map<String, String> jebs = sysparService.querySysparChild("selectValue", "JEBS", "", -1, -1);
			if (jebs!=null && StringUtils.isNotBlank(jebs.get("selectValue"))) {
                request().setAttribute("jebs", jebs.get("selectValue"));
                if (object!=null) {
                    object.put("jebs",   jebs.get("selectValue"));
                }
            }else {
                request().setAttribute("jebs", 100);//默认100
                if (object!=null) {
                    object.put("jebs",   100);
                }
            }
			
			if (StringUtils.isNotBlank(request("wap"))) {//wap站
	            JSONUtils.printObject(object);
	            return null;
	        }
			
    		} else {
    			return "404";
    		}
		
		
		
		if(!userType.equals(null) && "1".equals(userType)){
			return "success1";
		}else if(!userType.equals(null) && "2".equals(userType)){
			return "success2";
		}else {
			return "404";
		}
	}
	/**
	 * 点击查看详情的时候，判断某标的的状态
	 * @param tInt
	 * @return
	 * @throws Exception 
	 */
	private String  judgeStatus(int tInt,Long userId) throws Exception{
		Map<String,String> map=userService.queryUserById(userId);
		int userType=Integer.parseInt(map.get("userType"));
		if(tInt < 3){//秒还、净值标的
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if(aa < 0){
				return "waitBorrow";
				
			}
		}else{//其它借款
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if(aa < 0){
				return "waitBorrow";
			}else{
				Long bb = borrowService.queryBaseFiveApprove(userId,userType);
				if(bb < 0){
					return "waitBorrow";
				}
			}
		}
		return null;
	}
	
	/**
	 * 债权转让借款详情
	 * @return
	 * @throws DataException 
	 * @throws SQLException 
	 */
	public String queryDebtBorrowDetail()  throws Exception{
		return financeDetail();
	}

	/**
	 * @MethodName: financeAudit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:26:02
	 * @Return:
	 * @Descb: 借款人认证资料
	 * @Throws:
	 */
	public String financeAudit() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 借款企业认证资料
		List<Map<String, Object>> list = financeService
			.queryUserIdentifiedByid(idLong);
		request().setAttribute("auditList", list);
		
		return "success";
	}
	
	
	
	/**
	 * @MethodName: financeAudit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:26:02
	 * @Return:
	 * @Descb: 借款企业认证资料
	 * @Throws:
	 */
	public String financeAudit1() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 借款企业认证资料
		List<Map<String, Object>> list = financeService
			.queryUserIdentifiedByid2(idLong);
		request().setAttribute("auditList", list);
		
		return "success";
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @MethodName: financeRepay
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:27:02
	 * @Return:
	 * @Descb: 借款人还款记录
	 * @Throws:
	 */
	public String financeRepay() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 借款人还款记录
		List<Map<String, Object>> list = financeService
				.queryRePayByid(idLong);
		request().setAttribute("repayList", list);
		return "success";
	}

	
	/**   
	 * @MethodName: financeCollection  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:29:12
	 * @Return:    
	 * @Descb: 借款人催款记录
	 * @Throws:
	*/
	public String financeCollection() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		// 借款人催款记录
		List<Map<String, Object>> list = financeService
				.queryCollectionByid(idLong);
		request().setAttribute("collectionList", list);
		return "success";
	}

	/**
	 * 投资理财初始化Wap
	 * financeInvestInitWap
	 * @auth hejiahua
	 * @return
	 * @throws Exception 
	 * String
	 * @exception 
	 * @date:2015-1-4 下午4:04:13
	 * @since  1.0.0
	 */
	public String financeInvestInitWap() throws Exception {
		log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		Thread.sleep(100);
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = request.getString("id") == null ? "" : request.getString("id");
		if(!StringUtils.isNumericSpace(id)){
		    obj.put("msg", IConstants.ACTOIN_ILLEGAL);
            JSONUtils.printObject(obj);
            return null;
		}
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			// 非法操作直接返回
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}
		Map<String, String> investMaps = financeService.getInvestStatus(idLong);
		String nid_log= "";
		if(investMaps!=null && investMaps.size()>0){
			 nid_log = Convert.strToStr(investMaps.get("nid_log"),"");
			 Map<String,String>  typeLogMap = null;
				if (StringUtils.isNotBlank(nid_log)) {
					typeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
					int stauts = Convert.strToInt(typeLogMap.get("subscribe_status"),-1);
					obj.put("subscribes",stauts);
					obj.put("investMaps", investMaps);
				}
		}
		if (investMaps != null && investMaps.size() > 0) {
			String hasPWD = investMaps.get("hasPWD") == null?"-1":investMaps.get("hasPWD");
			investDetailMap = financeService.queryBorrowInvest(idLong);
			double residue =  Convert.strToDouble(investDetailMap.get("residue"), 0);
			double minTenderedSum =  Convert.strToDouble(investDetailMap.get("minTenderedSum"), 0);
			if(residue <minTenderedSum ){
			    obj.put("minTenderedSum",residue);
			}else{
			    obj.put("minTenderedSum",minTenderedSum);
			}
			String userId = investDetailMap.get("userId") == null ? ""
					: investDetailMap.get("userId");
			if (userId.equals(user.getId().toString())) {
				// 不满足投标条件,返回
				obj.put("msg", "不能投标自己发布的借款");
				JSONUtils.printObject(obj);
				return null;
			}
			session().setAttribute("investStatus","ok");
			Map<String, String> userMap = financeService.queryUserMonney(user
					.getId());
			obj.put("userMap",userMap);
			Map<String, String> userMap2 = userService.queryUserAmount(user
					.getId());
			obj.put("userMap",userMap);
			obj.put("userMap2",userMap2);
			obj.put("hasPWD",hasPWD);
			//查询可投的金额倍数
            Map<String, String> jebs = sysparService.querySysparChild("selectValue", "JEBS", "", -1, -1);
            if (jebs!=null && StringUtils.isNotBlank(jebs.get("selectValue"))) {
                obj.put("jebs",jebs.get("selectValue"));
            }else {
                obj.put("jebs",100);//默认100
            }
		} else {
			// 不满足投标条件,返回
			obj.put("msg", "该借款投标状态已失效");
			JSONUtils.printObject(obj);
			return null;
		}
		obj.put("investDetailMap", investDetailMap);
		JSONUtils.printObject(obj);
		return null;
	}

	   /**
     * @MethodName: financeInvestInit
     * @Param: FrontMyFinanceAction
     * @Author: gang.lv
     * @Date: 2013-3-20 上午08:20:57
     * @Return:
     * @Descb: 理财投标初始化
     * @Throws:
     */
    public String financeInvestInit() throws Exception {
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        Thread.sleep(100);
        User user = (User) session().getAttribute("user");
        Map<String, String> m = new HashMap<String, String>();
        m = userService.queryUserById(user.getId());
        
        String orgName = null;
        orgName = m.get("orgName");
        String userType = null;
        userType = m.get("userType");
        System.out.println("----------------------:"+userType);
        System.out.println("----------------------:"+orgName);
        
        //hjh
        request().setAttribute("borrowAmount", request.getInt("borrowAmount",0));
        
//      if (null != m) {
//          int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
//          if (isApplyPro == 1) {
//              getOut()
//                      .print(
//                              "<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
//              getOut().flush();
//          }
//      }
        
        JSONObject obj = new JSONObject();
        String id = request.getString("id") == null ? "" : request.getString("id");
        if(!StringUtils.isNumericSpace(id)){
            return INPUT;
        }
        long idLong = Convert.strToLong(id, -1);
        if (idLong == -1) {
            // 非法操作直接返回
            obj.put("msg", IConstants.ACTOIN_ILLEGAL);
            JSONUtils.printObject(obj);
            return null;
        }
        Map<String, String> investMaps = financeService.getInvestStatus(idLong);
        String nid_log= "";
        if(investMaps!=null && investMaps.size()>0){
             nid_log = Convert.strToStr(investMaps.get("nid_log"),"");
             Map<String,String>  typeLogMap = null;
                if (StringUtils.isNotBlank(nid_log)) {
                    typeLogMap = shoveBorrowTypeService.queryBorrowTypeLogByNid(nid_log.trim());
                    int stauts = Convert.strToInt(typeLogMap.get("subscribe_status"),-1);
                    request().setAttribute("subscribes",stauts );
                    request().setAttribute("investMaps",investMaps );
                }
        }
        
        if (investMaps != null && investMaps.size() > 0) {
            String hasPWD = investMaps.get("hasPWD") == null?"-1":investMaps.get("hasPWD");
            investDetailMap = financeService.queryBorrowInvest(idLong);
            double residue =  Convert.strToDouble(investDetailMap.get("residue"), 0);
            double minTenderedSum =  Convert.strToDouble(investDetailMap.get("minTenderedSum"), 0);
            if(residue <minTenderedSum ){
                request().setAttribute("minTenderedSum",residue);
            }else{
                request().setAttribute("minTenderedSum",minTenderedSum);
            }
            String userId = investDetailMap.get("userId") == null ? ""
                    : investDetailMap.get("userId");
            if (userId.equals(user.getId().toString())) {
                // 不满足投标条件,返回
                obj.put("msg", "不能投标自己发布的借款");
                JSONUtils.printObject(obj);
                return null;
            }
            session().setAttribute("investStatus","ok");
            Map<String, String> userMap = financeService.queryUserMonney(user
                    .getId());
            Map<String, String> userMap2 = userService.queryUserAmount(user.getId());
            request().setAttribute("userMap", userMap);
            request().setAttribute("userMap2", userMap2);
            session().setAttribute("hasPWD", hasPWD);
            
            //查询可投的金额倍数
            Map<String, String> jebs = sysparService.querySysparChild("selectValue", "JEBS", "", -1, -1);
            if (jebs!=null && StringUtils.isNotBlank(jebs.get("selectValue"))) {
                request().setAttribute("jebs", jebs.get("selectValue"));
            }else {
                request().setAttribute("jebs", 100);//默认100
            }
            
            if(!investDetailMap.get("userType").equals(null) && "1".equals(investDetailMap.get("userType"))){
                
                return "success";
                
            }else if(!investDetailMap.get("userType").equals(null) && "2".equals(investDetailMap.get("userType"))){
                
                return "success-org";
                
            }else
                
            System.out.println("----------------------:"+investDetailMap.get("userType"));
        } else {
            // 不满足投标条件,返回
            obj.put("msg", "该借款投标状态已失效");
            JSONUtils.printObject(obj);
            return null;
        }
        return null;
    }
    
	/**   
	 * @throws Exception 
	 * @MethodName: financeInvestLoad  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-5 下午05:04:52
	 * @Return:    
	 * @Descb: 输入密码后的投标
	 * @Throws:
	*/
	public String financeInvestLoad() throws Exception{
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id")==null?"":paramMap.get("id");
		
		String investPWD = paramMap.get("investPWD")==null?"":paramMap.get("investPWD");
		long idLong = Convert.strToLong(id, -1);
    
		if (idLong == -1) {
			// 非法操作直接返回
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}
		
		if ("".equals(investPWD)) {
			this.addFieldError("paramMap['investPWD']", "请输入投标密码");
			return "input";						
		}
		Map<String, String> investPWDMap = financeService.getInvestPWD(idLong,investPWD);
		if (investPWDMap == null || investPWDMap.size() ==0) {
			this.addFieldError("paramMap['investPWD']", "投标密码错误");
			return "input";						
		}
		// 判断是否进行了资料审核
		Object object = session().getAttribute("investStatus");
		if (object == null) {
			return null;
		}
		Map<String, String> investMaps = financeService.getInvestStatus(idLong);
		if (investMaps != null && investMaps.size() > 0) {
			investDetailMap = financeService.queryBorrowInvest(idLong);

			String userId = investDetailMap.get("userId") == null ? ""
					: investDetailMap.get("userId");
			if (userId.equals(user.getId().toString())) {
				// 不满足投标条件,返回
				obj.put("msg", "不能投标自己发布的借款");
				JSONUtils.printObject(obj);
				return null;
			}
			Map<String, String> userMap = financeService.queryUserMonney(user
					.getId());
			request().setAttribute("userMap", userMap);
		} else {
			// 不满足投标条件,返回
			obj.put("msg", "该借款投标状态已失效");
			JSONUtils.printObject(obj);
			return null;
		}
		return "success";
	}
	/**   
	 * @MethodName: financeInvest  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-3-30 下午03:53:34
	 * @Return:    
	 * @Descb: 投标借款
	 * @Throws:
	*/
	public String financeInvest() throws Exception {
			log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		    User user = (User) session().getAttribute("user");
		    
		    Map<String, String> m = new HashMap<String, String>();
			m = userService.queryUserById(user.getId());
			JSONObject obj = new JSONObject();
			 
			if (null != m) {
				if (m.get("password").equals(m.get("dealpwd"))) {
				    obj.put("msg", "*为了您的账户安全，交易密码不能与登录密码相同，点击这里<a href=\"securityCent.mht?isOn=2\" class=\"red\">修改交易密码</a>! ");
				    obj.put("updatePwd", "updatePwd");
				    JSONUtils.printObject(obj);
					return null;
				}
//				int isApplyPro = Convert.strToInt(m.get("isApplyPro"), 1);
//				if (isApplyPro == 1) {
//					getOut()
//							.print(
//									"<script>alert('*您的账号还没有申请密保，为了您的帐号安全，请您申请密保！! ');window.location.href='queryQuestion.do';</script>");
//					return null;
//				}
			}

		   
		    boolean re = userService.checkSign(user.getId());
			if(!re){
				request().getSession().removeAttribute("user");
				request().getSession().invalidate();
				 obj.put("msg", "*您的账号出现异常，请速与管理员联系! ");
                 JSONUtils.printObject(obj);
				return null;
			}
			String id = paramMap.get("id") == null?"":paramMap.get("id");
			long idLong = Convert.strToLong(id, -1);
			String amount = paramMap.get("amount") == null?"":paramMap.get("amount");
			double amountDouble = Convert.strToDouble(amount, 0);
			String hasPWD = ""+session().getAttribute("hasPWD");
		    String investPWD = paramMap.get("investPWD") == null?"":paramMap.get("investPWD");
		    int status =Convert.strToInt( paramMap.get("subscribes"),2);//是否开启认购模式
		    
		    String dealPWD = paramMap.get("dealPWD")==null?"":paramMap.get("dealPWD");
		    if (StringUtils.isBlank(dealPWD)) {
		        obj.put("msg", "请输入交易密码");
                JSONUtils.printObject(obj);
                return null;
				//this.addFieldError("paramMap['dealPWD']", "请输入交易密码");
				//return "input";
			}
		    
		    String oldPass = com.shove.security.Encrypt.MD5(dealPWD
					+ IConstants.PASS_KEY);//输入的交易密码MD5加密
		    if(!oldPass.equals(user.getDealpwd())){
		        obj.put("msg", "交易密码错误");
                JSONUtils.printObject(obj);
                return null;
		    	//this.addFieldError("paramMap['dealPWD']", "交易密码错误");
				//return "input";	
		    }

		    if("1".equals(hasPWD)){
		    	Map<String, String> investPWDMap = financeService.getInvestPWD(idLong,investPWD);
				if (investPWDMap == null || investPWDMap.size() ==0) {
					if(status == 1){
						obj.put("msg", "投标密码错误");
						JSONUtils.printObject(obj);
						return null;
					}
					obj.put("msg", "投标密码错误");
                    JSONUtils.printObject(obj);
                    return null;
					//this.addFieldError("paramMap['investPWD']", "投标密码错误");
					//return "input";						
				}
		    }
		    
		    investDetailMap = financeService.queryBorrowInvest(idLong);
            
            if (investDetailMap!=null ) {
                String displayTime = investDetailMap.get("displayTime");
                String nowTime = DateUtil.dateToString(new Date());
                if (StringUtils.isNotBlank(displayTime)&&displayTime.compareTo(nowTime)>0) {
                    obj.put("msg", "此标的尚未到投标时间");
                    JSONUtils.printObject(obj);
                    return null;
                }
            }
		    
		    int num =0;
		    if(status ==1){
		    	double smallestFlowUnit = Convert.strToDouble(paramMap.get("smallestFlowUnit"), 0.0);
		    	if (smallestFlowUnit==0) {
		    		obj.put("msg", "操作失败");
					JSONUtils.printObject(obj);
					return null;
				}
		    	String result = Convert.strToStr(paramMap.get("result"),"");
		    	if(StringUtils.isBlank(result)){
		    		obj.put("msg", "请输入购买的份数");
					JSONUtils.printObject(obj);
					return null;
		    	}
		    	boolean b=result.matches("[0-9]*");
		    	if(!b){
		    		obj.put("msg", "请正确输入购买的份数");
					JSONUtils.printObject(obj);
					return null;
		    	} 
		    	
		    	String userId = investDetailMap.get("userId") == null ? "": investDetailMap.get("userId");
		    	if (userId.equals(user.getId().toString())) {
		    	obj.put("msg", "不能投标自己发布的借款");
		    	JSONUtils.printObject(obj);
		    	return null;
		    	    }
		    	 num = Integer.parseInt(result);
		    	if (num<1) {
		    		obj.put("msg", "请正确输入购买的份数");
					JSONUtils.printObject(obj);
					return null;
				}
		    	amountDouble = num * smallestFlowUnit;
		    }
		    Map<String,String> result = financeService.addBorrowInvest(idLong, user
					.getId(),"", amountDouble,getBasePath(),user.getUserName(),status,num);
		    String resultMSG = result.get("ret_desc");
		    userService.updateSign(user.getId());//更换校验码
		    if("".equals(resultMSG)){
		    	obj.put("msg", 1);
		    }else{
		    	obj.put("msg", result.get("ret_desc")+"");
		    }
			
			JSONUtils.printObject(obj);
			
			return null;
	}

	/**
	 * @MethodName: borrowMSGInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:08:51
	 * @Return:
	 * @Descb: 借款留言初始化
	 * @Throws:
	 */
	public String borrowMSGInit() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String pageNum = paramMap.get("curPage");
		if (StringUtils.isNotBlank(pageNum)) {
			pageBean.setPageNum(pageNum);
		}
		pageBean.setPageSize(IConstants.PAGE_SIZE_6);
		if (idLong == -1) {
			return "404";
		}
		financeService.queryBorrowMSGBord(idLong, pageBean);
		request().setAttribute("id", id);
		return "success";
	}

	/**
	 * @throws IOException
	 * @throws SQLException
	 * @throws DataException 
	 * @MethodName: addBorrowMSG
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:09:06
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public String addBorrowMSG() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("msg_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String msgContent = paramMap.get("msg") == null ? "" : paramMap
				.get("msg");
		if(isKeywords.isKeywordsOnDB(msgContent)){
			this.addFieldError("paramMap['code']", "留言内容含有关键字，不能留言");
			return "input";
		}
		long returnId = -1;
		returnId = financeService.addBorrowMSG(idLong, user.getId(), msgContent);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @throws IOException
	 * @throws DataException
	 * @MethodName: focusOnBorrow
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:06:16
	 * @Return:
	 * @Descb: 我关注的借款
	 * @Throws:
	 */
	public String focusOnBorrow() throws Exception,
			DataException {
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		long returnId = -1L;
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}

		Map<String, String> map = financeService.hasFocusOn(idLong, user
				.getId(), IConstants.FOCUSON_BORROW);
		if (map != null && map.size() > 0) {
			obj.put("msg", "您已关注过该借款");
			JSONUtils.printObject(obj);
			return null;
		}

		returnId = financeService.addFocusOn(idLong, user.getId(),
				IConstants.FOCUSON_BORROW);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "关注成功!");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @throws IOException
	 * @throws DataException
	 * @MethodName: focusOnUser
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 上午09:07:20
	 * @Return:
	 * @Descb: 我关注的用户
	 * @Throws:
	 */
	public String focusOnUser() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		long returnId = -1L;
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		if (idLong == -1) {
			obj.put("msg", IConstants.ACTOIN_ILLEGAL);
			JSONUtils.printObject(obj);
			return null;
		}

		Map<String, String> map = financeService.hasFocusOn(idLong, user
				.getId(), IConstants.FOCUSON_USER);
		if (map != null && map.size() > 0) {
			obj.put("msg", "您已关注过该用户");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = financeService.addFocusOn(idLong, user.getId(),
				IConstants.FOCUSON_USER);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			obj.put("msg", "关注成功!");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @MethodName: mailInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:31
	 * @Return:
	 * @Descb: 发送站内信初始化
	 * @Throws:
	 */
	public String mailInit() {
		String id = request.getString("id");
		String userName = request.getString("username");
		request().setAttribute("id", id);
		request().setAttribute("userName", userName);
		return "success";
	}

	/**
	 * @MethodName: reportInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:48
	 * @Return:
	 * @Descb: 举报用户初始化
	 * @Throws:
	 */
	public String reportInit() {
		String id = request.getString("id");
		String userName = request.getString("username");
		request().setAttribute("id", id);
		request().setAttribute("userName", userName);
		return "success";
	}

	public String mailAdd() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		
		JSONObject obj = new JSONObject();
		

		String code = (String) session().getAttribute("code_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long reciver = Convert.strToLong(id, -1);
		String title = paramMap.get("title") == null ? "" : paramMap
				.get("title");
		String content = paramMap.get("content") == null ? "" : paramMap
				.get("content");
		long returnId = -1;
		Integer enable=user.getEnable();
		if(enable==3){
			obj.put("msg", "8");
			JSONUtils.printObject(obj);
			return null;
		}
		returnId = financeService.addUserMail(reciver, user.getId(), title,
				content, IConstants.MALL_TYPE_COMMON);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	/**
	 * @throws DataException 
	 * @MethodName: reportAdd
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:16:11
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public String reportAdd() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String code = (String) session().getAttribute("code_checkCode");
		String _code = paramMap.get("code") == null ? "" : paramMap.get("code");
		if (!code.equals(_code)) {
			this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
			return "input";
		}
		String id = paramMap.get("id") == null ? "" : paramMap.get("id");
		long reporter = Convert.strToLong(id, -1);
		String title = paramMap.get("title") == null ? "" : paramMap
				.get("title");
		String content = paramMap.get("content") == null ? "" : paramMap
				.get("content");
		long returnId = -1;
		returnId = financeService.addUserReport(reporter, user.getId(), title,
				content);
		if (returnId <= 0) {
			obj.put("msg", IConstants.ACTION_FAILURE);
			JSONUtils.printObject(obj);
			return null;
		} else {
			// 添加成功返回值
			obj.put("msg", "1");
			JSONUtils.printObject(obj);
			return null;
		}
	}

	
	/**   
	 * @MethodName: showImg  
	 * @Param: FrontMyFinanceAction  
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:24:03
	 * @Return:    
	 * @Descb: 查看图片
	 * @Throws:
	*/
	public String showImg() throws Exception{
		String typeId = request.getString("typeId") == null?"":request.getString("typeId");
		String userId = request.getString("userId") == null?"":request.getString("userId");
		long typeIdLong = Convert.strToLong(typeId, -1);
		long userIdLong = Convert.strToLong(userId, -1);
		List<Map<String,Object>> imgList = financeService.queryUserImageByid(typeIdLong, userIdLong);
		request().setAttribute("imgList", imgList);
		return "success";
	}

	/**
	 * 跳转流转标购买 页面
	 * @throws Exception 
	 */
	public String subscribeinit() throws Exception{
		long borrowid = request.getLong("borrowid", -1);
		try {
			Map<String, String> borrowDetailMap = financeService.queryBorrowDetailById(borrowid);
			request().setAttribute("borrowDetailMap",borrowDetailMap);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
		return SUCCESS;
	}
	

	/**
	 * @throws SQLException
	 * @throws DataException 
	 * @MethodName: subscribe
	 * @Param: FrontMyBorrowAction
	 * @Author: gang.lv
	 * @Date: 2013-5-20 下午08:22:15
	 * @Return:
	 * @Descb: 认购流转标
	 * @Throws:
	 */
	@SuppressWarnings("unchecked")
	public String subscribe() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		User user = (User) session().getAttribute("user");
		JSONObject obj = new JSONObject();
		String id = paramMap.get("id");
		long idLong = Convert.strToLong(id, -1);
		String result = paramMap.get("result");
		int resultLong = Convert.strToInt(result, -1);
		
		if (idLong == -1) {
			obj.put("msg", "无效认购标的");
			JSONUtils.printObject(obj);
			return null;
		}
		if (resultLong == -1) {
			obj.put("msg", "非法的认购份数");
			JSONUtils.printObject(obj);
			return null;
		} else if (resultLong <= 0) {
			obj.put("msg", "请输入正确的认购份数");
			JSONUtils.printObject(obj);
			return null;
		}
		
		
		String resultStr = financeService.subscribeSubmit(idLong, resultLong,
				user.getId(), getBasePath(), user.getUserName(),getPlatformCost());
		userService.updateSign(user.getId());//更换校验码
		obj.put("msg", resultStr);
		JSONUtils.printObject(obj);
		return null;
	}
	
	
	
	public Map<String, String> getInvestDetailMap() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		String id = request.getString("id") == null ? "" : request.getString("id");
		long idLong = Convert.strToLong(id, -1);
		if (investDetailMap == null) {
			investDetailMap = financeService.queryBorrowInvest(idLong);
		}
		return investDetailMap;
	}
	
	
	
	
	
	
	/**
	 * acitvity_2015_6:2015年6月10日 活动<br/>
	 * @author he
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public void acitvity_2015_6() throws Exception{
		//查询用户活动期间的投资额度
		User user = (User) session().getAttribute(IConstants.SESSION_USER);
		
		String startTime = "2015-06-17 20:00:00";
		String endTime = "2015-07-14 18:00:00";
		
		//期数
		String termId = "56";
		
		//投资额度
		double sumInvestAmount = 0;
		
		String termIds = " (56,57,58,59,60,61,62) ";
		
		if (null!=user) {
			//投资额度
			Map<String, String> investAmountMap = borrowService.queryActivityInvest(startTime, endTime, user.getId());
			
			//抽奖次数
			Map<String, String> scoreMap = userService.queryUserScore(user.getId());
			request().setAttribute("scoreMap", scoreMap);

			if (null!=investAmountMap) {
				
				sumInvestAmount = Convert.strToDouble(investAmountMap.get("investAmount"), 0);
				
				//是否符合活动规则
				if (sumInvestAmount>=3000 && sumInvestAmount<=5000) {
					termId = "57";
				}else if (sumInvestAmount>5000 && sumInvestAmount<=10000) {
					termId = "58";
				}else if (sumInvestAmount>10000 && sumInvestAmount<=50000) {
					termId = "59";
				}else if (sumInvestAmount>50000 && sumInvestAmount<=100000) {
					termId = "60";
				}else if (sumInvestAmount>100000 && sumInvestAmount<=300000) {
					termId = "61";
				}else if (sumInvestAmount>300000) {
					termId = "62";
				}else {
					termId = "56";
				}
				
			}else {
				termId = "56";
			}
			
			
			request().setAttribute("sumInvestAmount", sumInvestAmount);
			
			
			//我的抽奖明细
			Connection conn = MySQL.getConnection();
			String sql = "SELECT tap.`awardName`,DATE_FORMAT( t.`createTime`,'%Y-%c-%d %H:%i:%s') AS createTime,(CASE t.`sendAward`  WHEN   0 THEN '未派奖' ELSE '已派奖' END ) AS sendAwardStr FROM t_award_user t,t_award_param tap,t_award_info tai WHERE tai.`id` = t.`awardInfoId` AND tap.`id` = t.`paramId` AND t.`userId` = "+user.getId()+" AND t.`termId` IN " +termIds+ "AND tai.`isUsable` = 1 AND tai.`status` = 1 ORDER BY createTime DESC";
			DataSet ds = Database.executeQuery(conn, sql);
			ds.tables.get(0).rows.genRowsMap();
			request().setAttribute("myAwardDetail",ds.tables.get(0).rows.rowsMap);
			conn.close();
			
		}
		
		// 抽奖人排名
		Connection conn = MySQL.getConnection();
		String sql  = "SELECT date_format( a.`createTime`,'%Y-%c-%d %H:%i:%s') as createTime,b.`awardName`,f_formatting_username(c.`username`) username FROM t_award_user a,t_award_param b,t_user c WHERE a.`userId`=c.id AND a.`paramId`=b.`id` AND b.id !=1 and a.termId in "+termIds+" ORDER BY a.createTime DESC LIMIT 0,30 ";
		DataSet ds =  Database.executeQuery(conn, sql);
		BeanMapUtils.dataSetToMap(ds);
		ds.tables.get(0).rows.genRowsMap();
        List list = ds.tables.get(0).rows.rowsMap;
        conn.close();
        request().setAttribute("list", list);
		
		//物品列表
		List<Map<String, String>> awardInfoList = borrowService.queryAwardInfo(termId);
		request().setAttribute("awardInfoList", awardInfoList);
		
		//期数加密传递
		DesSecurityUtil util = new DesSecurityUtil(IConstants.PASS_KEY);
		request().setAttribute("termId", util.encrypt(termId));
		util = null;
		
	}
	
	/**
	 *功能：开始抽奖
	 * @return
	 * @throws Exception 
	 */
	public String awardStart() throws Exception{
		log.info("className:" + this.getClass().getName() + ";methodName:"
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		JSONObject json = new JSONObject();
		User user = (User) session("user");
		if (null == user){
			log.info("evil_opt");
			return null;
		}
		
		//日期过期了，果断返回  
		try {
			SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
			Date edate = sf.parse("2015-07-28 18:00:00");
			
			if (new Date().after(edate)){
				log.info("evil_opt,timeout.over.");
				return null;
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		
		//String termId = request().getParameter("termId");
		//String termId = paramMap.get("termId");
		String termId = paramMap.get("termId");
		DesSecurityUtil util = new DesSecurityUtil(IConstants.PASS_KEY);
		termId = util.decrypt(termId);
		
		Long userId = user.getId();
		
		/*//判断是那个规则的抽奖，对应不同的期数
		long hasaward = homeInfoSettingService.queryHasAward(termId, userId);
		if (hasaward>0) {
			termId = "4";
		}*/
		
		try {
			Map m = homeInfoSettingService.awardStart(termId,userId,2);
			try {
				if (null != m.get("error")){
					json.put("msg", m.get("error"));
				} else {
					json.put("success", "success");
					//json.put("awardId", m.get("awardId"));
					json.put("retAwardName", m.get("retAwardName"));
					json.put("paramId", m.get("paramId"));
				}
					/*	
					//如果用户中奖了，判断是否自动派奖，如果是自动派奖，则直接发放到用户账户的赠送金额。赠送金额可以抵用投标金额
					try {
						int paramId = Convert.strToInt(m.get("awardId").toString(), -1);
						int ternId = Convert.strToInt(termId, -1);
						long awar_user_id = Convert.strToLong(m.get("awar_user_id").toString(), -1);
						Map<String, String> realMoneyMap =  homeInfoSettingService.queryRealMoney(paramId, ternId);
						if (realMoneyMap!=null) {
							double realMoney = Convert.strToDouble(realMoneyMap.get("realMoney"), 0.0);
							long result = userService.addPresrent(realMoney, userId,awar_user_id);
							if (result>0) {
								operationLogService.addOperationLog("t_user", user.getUserName(), IConstants.UPDATE, ServletUtils.getIpAddress(request()), realMoney, "用户获取赠送金额成功", 1);
							}else {
								operationLogService.addOperationLog("t_user", user.getUserName(), IConstants.UPDATE, ServletUtils.getIpAddress(request()), realMoney, "用户获取赠送金额失败", 1);
							}
						}
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
				}*/
				JSONUtils.printObject(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			json.put("msg", "系统繁忙，请稍后再试...");
			JSONUtils.printObject(json);
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/*
	 * 活动专题
	 */
	public String activityIndex() throws Exception{log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
	  acitvity_2015_6();
	
	//int type = Convert.strToInt(request.getString("award"), 3);
//	if (type==1) {
//	  //当前登录用户
        /*User user = (User) session().getAttribute(IConstants.SESSION_USER);
           
            if (userId>0) {
                DesSecurityUtil des = new DesSecurityUtil();
                String userI = des.encrypt(userId.toString());
                String uri=getPath();
                request().setAttribute("url", uri);
                request().setAttribute("userId", userI);
                //查询用户的有效邀请人数
                List<Map<String, String>> myvalidList = financeService.queryMyAndFriendValid(0);
                request().setAttribute("myvalidList", myvalidList);
                if (null != user){
                	 Long userId = user.getId();
                	 List l = financeService.queryMyFriendValid(userId);
                	 Map m = (Map) l.get(0);
                	 request().setAttribute("myCt", m.get("myCt"));
                } else {
                	request().setAttribute("myCt", null);
                }
                
                
                if (myvalidList!=null && myvalidList.size()>0) {
                    Map<String, String>  myvalid = myvalidList.get(0);
                    request().setAttribute("myvalid", myvalid);
                }else {
                    request().setAttribute("myvalid", null);
                }
              查询用户的有效邀请人数
                List<Map<String, String>> myinvestList = financeService.queryMyAndFriendInvest(userId);
                if (myinvestList!=null && myinvestList.size()>0) {
                    Map<String, String>  myinvest = myinvestList.get(0);
                    request().setAttribute("myinvest", myinvest);
                }else {
                    request().setAttribute("myinvest", null);
                }
            }
        //有效邀请人数前十
        List<Map<String, String>>  valid = financeService.queryMyAndFriendValid(-1);
        //我和好友累计投资前十
        List<Map<String, String>> invest = financeService.queryMyAndFriendInvest(-1);
        request().setAttribute("valid", valid);
        request().setAttribute("invest", invest);
    }
	    
	    if (type==2) {//有效邀请人数
	        Connection conn = MySQL.getConnection();
	        try {
	            if (user!=null) {
	                String sql  = "SELECT COUNT(t.id) as amount from t_user t,t_recommend_user tru where t.id = tru.userId  and t.createTime>'2014-12-09 00:00:00' and t.createTime<'2015-01-01 00:00:00' and  EXISTS (SELECT ti.investor from t_invest ti where ti.investor = t.id and  ti.investTime>'2014-12-09 00:00:00' and ti.investTime<'2015-01-01 00:00:00') and tru.recommendUserId = "+user.getId();
	                DataSet ds =  Database.executeQuery(conn, sql);
	                Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
	                sql ="SELECT k.recommendUserId as a,m.`username` as b ,n.`realName` as c,k.mAmt as d,FORMAT(k.hraward,2) as e FROM (SELECT h.recommendUserId ,SUM(h.mAmt) mAmt,SUM(h.hraward)hraward FROM (SELECT j.investor,j.recommendUserId,j.mAmt, SUM(j.hraward) hraward FROM ( SELECT i.*,CASE WHEN i.deadline <3 THEN mAmt * 0.0008 * i.deadline WHEN i.deadline <6 THEN mAmt * 0.001 * i.deadline  WHEN i.deadline <12 THEN mAmt * 0.0012 * i.deadline   END hraward FROM (  SELECT b.`investor`,SUM(b.`investAmount`) mAmt,e.`recommendUserId`,h.`id` borrowId,h.`deadline` FROM t_invest b,t_recommend_user e,t_borrow h   WHERE b.investor=e.userId AND h.id=b.`borrowId` AND b.`investTime` > '2014-12-09 00:00:00 ' AND b.`investTime` < '2015-01-01 00:00:00' AND NOT EXISTS(SELECT 1 FROM t_employee cc WHERE cc.`userId`=e.recommendUserId) GROUP BY b.`investor` ,h.`deadline` ) i)j  GROUP BY j.investor,j.recommendUserId ) h GROUP BY h.recommendUserId)k,t_user m,t_person n WHERE k.recommendUserId=m.id AND n.`userId`=m.`id` and m.`id`="+user.getId();
	                ds =  Database.executeQuery(conn, sql);
                    Map<String, String> map2 = BeanMapUtils.dataSetToMap(ds);
	                request().setAttribute("map", map);
	                request().setAttribute("map2", map2);
                }else {
                    request().setAttribute("map", null);
                    request().setAttribute("map2", null);
                }
	            
            }
            catch (Exception e) {
                e.printStackTrace();
                request().setAttribute("map", null);
                request().setAttribute("map2", null);
            }finally{
                conn.close();
            }
	    }
	    if (type==3) {// 抽奖
	    	Connection conn = MySQL.getConnection();
	    	try {
	    		//所有抽奖人数
				String sql  = "SELECT COUNT(1) awardAll FROM t_award_user WHERE `paramId`!=1 ";
				DataSet ds =  Database.executeQuery(conn, sql);
				Map<String, String> map = BeanMapUtils.dataSetToMap(ds);
				request().setAttribute("awardAll", map.get("awardAll"));
				
				// 抽奖人排名
	    		String sql  = "SELECT date_format( a.`createTime`,'%Y-%c-%d %H:%i:%s') as createTime,b.`awardName`,f_formatting_username(c.`username`) username FROM t_award_user a,t_award_param b,t_user c WHERE a.`userId`=c.id AND a.`paramId`=b.`id` AND b.id !=1 and a.termId=2 ORDER BY a.createTime DESC LIMIT 0,20 ";
	    		DataSet ds =  Database.executeQuery(conn, sql);
				BeanMapUtils.dataSetToMap(ds);
				ds.tables.get(0).rows.genRowsMap();
	            List list = ds.tables.get(0).rows.rowsMap;
	            request().setAttribute("list", list);
	            
	            //红包剩余多少个SELECT a.awardNo FROM t_award_term a WHERE a.`id` =46;//1
	            sql  = "SELECT a.awardNo FROM t_award_term a WHERE a.`id` =2";
				ds =  Database.executeQuery(conn, sql);
				Map<String, String> map2 = BeanMapUtils.dataSetToMap(ds);
				request().setAttribute("awardNoLeast", map2.get("awardNo"));
				
				//抽奖表ID
				request().setAttribute("termId", 52);
	            if (null != user){
	            	//查询用户好币数。
	            	sql  = "SELECT a.`sumScore` FROM t_user a WHERE a.id="+user.getId();
					ds =  Database.executeQuery(conn, sql);
					Map<String, String> map1 = BeanMapUtils.dataSetToMap(ds);
					request().setAttribute("sumScore", map1.get("sumScore"));
					
					//用户可以抽奖次数
					int awardTimes = Integer.parseInt(map1.get("sumScore"))/1000;
					request().setAttribute("awardTimes", awardTimes);
					
					//我的抽奖明细
					sql = "SELECT date_format( t.`createTime`,'%Y-%c-%d %H:%i:%s') as createTime,(CASE t.`sendAward`  WHEN   0 THEN '未派奖' ELSE '已派奖' END ) AS sendAwardStr,t.`sendAward`, v.`awardName`,t.`id`   FROM t_award_user t,t_user tu,v_t_award_tern_list v WHERE  t.`userId` = tu.`id` AND t.`awardInfoId` = v.`id` AND tu.`id` = "+user.getId()+" AND t.`termId` = 2  AND v.`status` =  1 order by id desc";
					ds = Database.executeQuery(conn, sql);
					ds.tables.get(0).rows.genRowsMap();
					request().setAttribute("myAwardDetail",ds.tables.get(0).rows.rowsMap);
	            }
	            
	            
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
                conn.close();
            }
	    }*/
	    
	    return SUCCESS;
	}
   public static int pv = 0;
    public static int uv = 0;
    public static int ipc = 0;
    public static Map<String,Integer> uMap = new HashMap<String,Integer>();
    public static Map<String,Integer> iMap = new HashMap<String,Integer>();
    private DateFormat dft = new SimpleDateFormat(UtilDate.dtShort);
    private String ptoday = dft.format(new Date());
	/**
     *功能：PV,UV统计
     * @return
     */
    public String pvuvStatistic(){
        DateFormat dft = new SimpleDateFormat(UtilDate.dtShort);
        String today = dft.format(new Date());
//      log.info("ptoday:" + ptoday + "----" + today);
        //  3->100  
        if (!ptoday.equals(today) || pv %50 == 0){
            ptoday = dft.format(new Date());
            try {
                //保存进数据库
                userService.addUvpLog( pv, uv, ipc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            if (!ptoday.equals(today)){
                uMap = new HashMap<String,Integer>();
                iMap = new HashMap<String,Integer>();
                ptoday = today;
            }
            pv = 0;
            uv = 0;
            ipc = 0;
        }
        pv++;
        dft.format(new Date());
        String ip = request().getRemoteAddr();
        String mockClientIp = paramMap.get("mockClientIp");
//      log.info("----mockClientIp:" + mockClientIp);
//      log.info("----RemoteAddr:" + ip);
        if (null == mockClientIp){
            mockClientIp = ip;
            log.info("=====mockClientIp_null");
            if(null == mockClientIp){
                log.info("=====ip_null");
                return null;
            }
            
        }
        Integer counts = uMap.get(mockClientIp);
        if (null == counts){
            uMap.put(mockClientIp, 1);
            uv ++;
        } else {
            uMap.put(mockClientIp, counts + 1);
        }
        Integer icounts = iMap.get(ip);
        if (null == icounts){
            iMap.put(ip, 1);
            ipc ++;
        } else {
            iMap.put(ip, icounts + 1);
        }
//      if (pv % 3 == 0){
//          System.out.println("--uMap:" + uMap);
//          System.out.println("--iMap:" + iMap);
//      }
        try {
            JSONUtils.printStr("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	public static void main(String[] args) {
		int a = 4123;
		System.out.println(a/1000*1000);
	}
	
	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public NewsAndMediaReportService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsAndMediaReportService newsService) {
		this.newsService = newsService;
	}

	

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		 borrowPurposeList = selectedService
			.borrowPurpose();

		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		  borrowDeadlineList = selectedService
			.borrowDeadline();

		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getBorrowAmountList() throws Exception {log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
		 borrowAmountList = selectedService.borrowAmountRange();
		return borrowAmountList;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
	}

	public void setBorrowMSGMap(List<Map<String, Object>> borrowMSGMap) {
		this.borrowMSGMap = borrowMSGMap;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public void setInvestDetailMap(Map<String, String> investDetailMap) {
		this.investDetailMap = investDetailMap;
	}


	public void setBorrowPurposeList(List<Map<String, Object>> borrowPurposeList) {
		this.borrowPurposeList = borrowPurposeList;
	}

	public void setBorrowDeadlineList(List<Map<String, Object>> borrowDeadlineList) {
		this.borrowDeadlineList = borrowDeadlineList;
	}

	public void setBorrowAmountList(List<Map<String, Object>> borrowAmountList) {
		this.borrowAmountList = borrowAmountList;
	}

//	public void setLinksService(LinksService linksService) {
//		this.linksService = linksService;
//	}
//	public LinksService getLinksService() {
//		return linksService;
//	}

	public List<Map<String, Object>> getLinksList() {
		return linksList;
	}

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

	public void setLinksList(List<Map<String, Object>> linksList) {
		this.linksList = linksList;
	}

//	public void setMediaReportService(MediaReportService mediaReportService) {
//		this.mediaReportService = mediaReportService;
//	}

	public List<Map<String, Object>> getMeikuList() {
		return meikuList;
	}

	public void setMeikuList(List<Map<String, Object>> meikuList) {
		this.meikuList = meikuList;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Map<String, Object>> getMeikuStick() {
		return meikuStick;
	}

	public void setMeikuStick(List<Map<String, Object>> meikuStick) {
		this.meikuStick = meikuStick;
	}

	public List<Map<String, Object>> getListsGGList() {
		return listsGGList;
	}

	public void setListsGGList(List<Map<String, Object>> listsGGList) {
		this.listsGGList = listsGGList;
	}

	public List<Map<String, Object>> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<Map<String, Object>> bannerList) {
		this.bannerList = bannerList;
	}

	/**
     * 生成数字验证码
     * 
     * @return
     * @throws IOException
     */
    public String imageCodeAlgorithm() throws IOException {
        // 在内存中创建图象
        int width =85, height = 20;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.getGraphics();
        // 生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(230, 255));
        g.fillRect(0, 0, 100, 25);
        // 设定字体
        g.setFont(new Font("Arial", Font.CENTER_BASELINE | Font.ITALIC, 18));
        // 产生0条干扰线，
        g.drawLine(0, 0, 0, 0);
        // 取随机产生的认证码(4位数字)
        String sRand = "";
        String [] maths = new String[5];
        maths[0] = random.nextInt(50)+"";
        maths[2] = random.nextInt(50)+"";
        maths[1] =Integer.parseInt(maths[0])<Integer.parseInt(maths[2])?"+":"-";
        maths[3] ="=";
        maths[4] ="?";
        String rand ="";
        for (int i = 0; i < maths.length; i++) {
            rand = maths[i];
            sRand += rand;
        }
        // 将认证码显示到图象中
        g.setColor(getRandColor(100, 150));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
        g.drawString(sRand+"   ", 1, 16);
          for(int i=0;i<(random.nextInt(5)+5);i++){  
                g.setColor(new Color(random.nextInt(255)+1,random.nextInt(255)+1,random.nextInt(255)+1));  
                g.drawLine(random.nextInt(100),random.nextInt(30),random.nextInt(100),random.nextInt(30));  
        }
         int result = 99;//最大为98
         if ("+".equals(maths[1])) {
            result = Integer.parseInt(maths[0])+Integer.parseInt(maths[2]);
         }else {
             result = Integer.parseInt(maths[0])-Integer.parseInt(maths[2]);
        }
        // 将验证码存入页面KEY值的SESSION里面
        session().setAttribute("algorithm", result);
        // 图象生效
        g.dispose();
        ServletOutputStream responseOutputStream = response().getOutputStream();
        // 输出图象到页面
        ImageIO.write(image, "JPEG", responseOutputStream);
        // 以下关闭输入流！
        responseOutputStream.flush();
        responseOutputStream.close();
        maths = null;
        // 获得页面key值
        return null;
    }
    /**
     * 给定范围获得随机颜色
     * 
     * @param fc
     * @param bc
     * @return
     */
    Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
