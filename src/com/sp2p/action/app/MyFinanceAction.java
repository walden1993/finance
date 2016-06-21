package com.sp2p.action.app;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.Response;
import com.sp2p.entity.User;
import com.sp2p.service.BorrowService;
import com.sp2p.service.FinanceService;
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

/**
 * @ClassName: FrontMyFinanceAction.java
 * @Author: gang.lv
 * @Date: 2013-3-4 上午11:16:33
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 我的理财控制层
 */
public class MyFinanceAction extends BaseAppAction {

	public static Log log = LogFactory.getLog(MyFinanceAction.class);
	private static final long serialVersionUID = 1L;

	private FinanceService financeService;
	private SelectedService selectedService;
	private Map<String, String> investDetailMap;
	// private NewsService newsService;
	private NewsAndMediaReportService newsAndMediaReportService;
	private PublicModelService publicModelService;
	// private SuccessStoryService successStoryService;
	// -add by C_J -- 标种类型 历史记录
	private ShoveBorrowTypeService shoveBorrowTypeService;
	// private LinksService linksService;
	// private MediaReportService mediaReportService;
	// --
	// -add by houli
	private BorrowService borrowService;
	private UserService userService;
	private BorrowManageService borrowManageService;
	private SysparService sysparService;
	private PayTreasureService payTreasureService;
	
	
	

	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}

	public void setSysparService(SysparService sysparService) {
		this.sysparService = sysparService;
	}

	private List<Map<String, Object>> borrowPurposeList;
	private List<Map<String, Object>> borrowDeadlineList;
	private List<Map<String, Object>> borrowAmountList;
	private List<Map<String, Object>> linksList;
	private List<Map<String, Object>> meikuList;

	/**
	 * @throws Exception 
	 * @MethodName: financeList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-4 下午08:44:15
	 * @Return:
	 * @Descb: 

	 * @Throws:
	 */
	public String financeList() throws Exception {
		// 前台显示列表类型
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Response response = new Response();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			
			String starting_page = infoMap.get("starting_page") ;
			String page_number = infoMap.get("page_number");
			if (NumberUtils.isNumber(page_number)) {
				pageBean.setPageSize(Integer.valueOf(page_number));
			}
			if (NumberUtils.isNumber(starting_page)) {
				pageBean.setPageNum(starting_page);
			}
			
			String mode = infoMap.get("mode") ;
			String title = infoMap.get("title");
			String paymentMode = infoMap.get("paymentMode");
			String purpose = infoMap.get("purpose");
			String deadline = infoMap.get("deadline");
			String reward = infoMap.get("reward");
			String arStart = infoMap.get("arStart");
			String arEnd = infoMap.get("arEnd");
			String type = infoMap.get("type");
			//pageBean.setPageNum(infoMap.get("curPage"));
			
			//pageBean.setPageSize(IConstants.PAGE_SIZE_10);
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
			if ("1".equals(mode)) {
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
			}else if("6".equals(mode)){
				//正在招标中的借款
				borrowStatus = "(2)";
				borrowWay = "(1,2,3,5)";
			}
			else{
				borrowStatus = "(2,3,4,5)";
			}
			financeService.queryBorrowByConditionApp(borrowStatus, borrowWay, title,
					paymentMode, purpose, deadline, reward, arStart, arEnd,
					IConstants.SORT_TYPE_DESC, pageBean);
			System.out.println(pageBean.getPage());
			borrowPurposeList = selectedService
			.borrowPurpose();
			borrowAmountList = selectedService
			.borrowAmountRange();
			//jsonMap.put("borrowPurposeList", borrowPurposeList);
//			jsonMap.put("borrowDeadlineList", borrowDeadlineList);
			//jsonMap.put("borrowAmountList", borrowAmountList);
			//jsonMap.put("pageBean", pageBean);
			//jsonMap.put("error", "-1");
			//jsonMap.put("msg", "查询成功");
			jsonMap.put("pro_list", pageBean.getPage());
			response.success(jsonMap);
			JSONUtils.printObject(response);
		} catch (IOException e) {
			//paramMap.put("error", "1");
			//paramMap.put("msg", "未知异常");
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			log.error(e);
		}
		return null;
	}
	// /**
	// * @throws IOException
	// * @MethodName: financeList
	// * @Param: FrontMyFinanceAction
	// * @Author: gang.lv
	// * @Date: 2013-3-4 下午08:44:15
	// * @Return:
	// * @Descb: 正在招标中的借款
	// * @Throws:
	// */
	// public String financeBorrowingList() throws SQLException, DataException,
	// IOException {
	// // 前台显示列表类型
	// Map<String, Object> jsonMap = new HashMap<String, Object>();
	// try {
	// Map<String, String> infoMap = this.getAppInfoMap();
	// String title = infoMap.get("title");
	// String paymentMode = infoMap.get("paymentMode");
	// String purpose = infoMap.get("purpose");
	// String deadline = infoMap.get("deadline");
	// String reward = infoMap.get("reward");
	// String arStart = infoMap.get("arStart");
	// String arEnd = infoMap.get("arEnd");
	// String type = infoMap.get("type");
	// pageBean.setPageNum(infoMap.get("curPage"));
	//			
	// pageBean.setPageSize(IConstants.PAGE_SIZE_10);
	// String borrowWay = "";
	// String borrowStatus = "";
	// String borrowType = "";
	// // 截取查询的类型，防止非常规操作
	// if (StringUtils.isNotBlank(type)) {
	// String[] types = type.split(",");
	// if (types.length > 0) {
	// for (int n = 0; n < types.length; n++) {
	// // 是数字类型则添加到borrowType中
	// if (StringUtils.isNumericSpace(types[n])) {
	// borrowType += "," + types[n];
	// }
	// }
	// if (StringUtils.isNotBlank(borrowType)) {
	// borrowType = borrowType.substring(1, borrowType.length());
	// }
	// } else {
	// if (StringUtils.isNumericSpace(type)) {
	// borrowType = type;
	// }
	// }
	// }
	// // 全部借款列表,显示1 等待资料 2 正在招标中 3 已满标
	// borrowStatus = "(1,2,3,4,5)";
	// // 查询条件中的借款方式
	// if (StringUtils.isNotBlank(borrowType)) {
	// borrowWay = "(" + borrowType + ")";
	// }
	// financeService.queryBorrowByCondition(borrowStatus, borrowWay, title,
	// paymentMode, purpose, deadline, reward, arStart, arEnd,
	// IConstants.SORT_TYPE_DESC, pageBean);
	//	
	// // this.setRequestToParamMap();
	// jsonMap.put("borrowPurposeList", borrowPurposeList);
	// jsonMap.put("pageBean", pageBean);
	// jsonMap.put("error", "-1");
	// jsonMap.put("msg", "查询成功");
	// JSONUtils.printObject(jsonMap);
	// } catch (IOException e) {
	// paramMap.put("error", "1");
	// paramMap.put("msg", "未知异常");
	// JSONUtils.printObject(paramMap);
	// log.error(e);
	// }
	// return null;
	// }

	/**
	 * @MethodName: financeLastestList
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 上午09:29:33
	 * @Return:
	 * @Descb: 最新借款列表前10条记录
	 * @Throws:
	 */
	public String financeLastestList() throws SQLException, DataException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> mapList = financeService
					.queryLastestBorrow();
			request().setAttribute("mapList", mapList);
			jsonMap.put("mapList", mapList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> rankList = new ArrayList<Map<String, Object>>();
			Map<String, String> infoMap = this.getAppInfoMap();
			String num = infoMap.get("number");
			if (StringUtils.isBlank(num)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "请选择查询的方式");
				JSONUtils.printObject(jsonMap);
			}
			int number = Convert.strToInt(num, 1);
			if (number == 1) {
				// 当前年
				rankList = financeService.investRank(1, 8);
			} else {
				// 当月
				rankList = financeService.investRank(3, 8);
			}

			// request().setAttribute("rankList", rankList);
			jsonMap.put("rankList", rankList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: index
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-18 下午01:46:12
	 * @Return:
	 * @Descb: 首页加载内容
	 * @Throws:
	 */
	public String index() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> totalRiskMap = financeService.queryTotalRisk();
			Map<String, String> currentRiskMap = financeService
					.queryCurrentRisk();
			jsonMap.put("totalRiskMap", totalRiskMap);
			jsonMap.put("currentRiskMap", currentRiskMap);
			// 最新借款列表
			List<Map<String, Object>> mapList = financeService
					.queryLastestBorrow();
			// request().setAttribute("mapList", mapList);
			jsonMap.put("mapList", mapList);
			// 排名前8条记录
			// 当前年投标统计
			// 投标排名查询
			// 当年
			List<Map<String, Object>> rankList = financeService
					.investRank(1, 8);
			// request().setAttribute("rankList",rankList);
			jsonMap.put("rankList", rankList);
			// 公告
			List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
			pageBean.setPageSize(5);
			// newsService.frontQueryNewsPage(pageBean);
			newsAndMediaReportService.frontQueryNewsPage(pageBean,null);
			newsList = pageBean.getPage();
			pageBean.setPage(null);
			// request().setAttribute("newsList", newsList);
			jsonMap.put("newsList", newsList);
			// 成功故事
			List<Map<String, Object>> storyList = new ArrayList<Map<String, Object>>();
			pageBean.setPageSize(2);
			// successStoryService.querySuccessStoryPage(pageBean);
			publicModelService.querySuccessStoryPage(pageBean);
			storyList = pageBean.getPage();
			pageBean.setPage(null);
			// request().setAttribute("storyList", storyList);
			jsonMap.put("storyList", storyList);
			// 友情链接
			if (IConstants.ISDEMO.equals("1")) {
				pageBean.setPageSize(7);
			} else {
				pageBean.setPageSize(IConstants.PAGE_SIZE_20);
			}
			// linksService.queryLinksPage(pageBean);
			publicModelService.queryLinksPage(pageBean);
			linksList = pageBean.getPage();
			pageBean.setPage(null);
			// request().setAttribute("linksList", linksList);
			jsonMap.put("linksList", linksList);
			// 媒体报道 取 2条记录
			pageBean.setPageSize(2);
			// mediaReportService.queryMediaReportPage(pageBean);
			newsAndMediaReportService.queryMediaReportPage(pageBean);
			meikuList = pageBean.getPage();
			pageBean.setPage(null);
			// request().setAttribute("meikuList", meikuList);
			jsonMap.put("meikuList", meikuList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
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
	 * @throws SQLException
	 * @throws DataException
	 * @throws IOException
	 * @MethodName: financeDetail
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-5 下午03:40:38
	 * @Return:
	 * @Descb: 理财中的借款明细
	 * @Throws:
	 */
	public String financeDetail() throws SQLException, DataException,
			IOException {
		Map<String, Object> object = new HashMap<String, Object>();
		Response response = new Response();
		try {
			Map<String, String> infoMap = this.getAppInfoMap();
			String idStr = infoMap.get("object_id");
			
			String phone = infoMap.get("mobile_tel");
			
			if (StringUtils.isBlank(idStr)) {
				response.failure("借款ID不能为空");
				JSONUtils.printObject(response);
				return null;
			}
			
			String userType = null;
			
			if (!"".equals(idStr) && StringUtils.isNumericSpace(idStr)) {
				Long id = Convert.strToLong(idStr, -1);
				// 借款详细
				Map<String, String> jsonborrowDetailMap = financeService.queryBorrowDetailByIdApp(id);
				Map<String, String> borrowDetailMap = financeService.queryBorrowDetailById(id);
				if (borrowDetailMap == null)
				{
					response.failure("标的信息不存在");
					JSONUtils.printObject(response);
					return null;
				}
				//hjh  担保机构和担保方式
			   String agent = borrowDetailMap.get("agent");
			   String agentWay = borrowDetailMap.get("agentWay");
			   Map<String, String> agentMap =	sysparService.querySysparChildById("selectName,introduce", Convert.strToInt(agent, -1));
			   Map<String, String> agentWayMap =  sysparService.querySysparChildById("selectName,introduce", Convert.strToInt(agentWay, -1));
			   if (agentMap!=null) {
			       jsonborrowDetailMap.put("guarantee_party", agentMap.get("selectName"));
			       //jsonborrowDetailMap.put("guarantee_way_dis", agentMap.get("introduce"));
	            }
			   if (agentWayMap!=null) {
			       jsonborrowDetailMap.put("guarantee_way", agentWayMap.get("selectName"));
			       //jsonborrowDetailMap.put("guarantee_party_dis", agentWayMap.get("introduce"));
	           }
				userType = borrowDetailMap.get("userType");
				if(!userType.equals(null) && "1".equals(userType)){//userType=1 进入个人用户处理
					//-- 7 - 9
					//查询借款信息得到借款时插入的平台收费标准
					Map<String,String> map = borrowManageService.queryBorrowInfo(id);
					if (map == null) {
						response.failure("标的信息不存在");
						JSONUtils.printObject(response);
						return null;
					}
					borrowDetailMap.put("displayTime", map.get("displayTime"));
					
						// 每次点击借款详情时新增浏览量
						financeService.addBrowseCount(id);
						
						// 借款人资料
						Map<String, String> borrowUserMap = financeService
								.queryUserInfoByIdApp(id);
						object.putAll(borrowUserMap);
						// 借款人认证资料
						List<Map<String, Object>> list = financeService
								.queryUserIdentifiedByidApp(id);
						if (object!=null) {
	                        object.put("audit_info_list", list);
	                    }
						
					}else if(!userType.equals(null) && "2".equals(userType)){//userType=2 进入企业类型
					
						// 每次点击借款详情时新增浏览量
						financeService.addBrowseCount(id);
						
						
						// 融资认证资料
						List<Map<String, Object>> list = financeService
								.queryUserIdentifiedByid2App(id);
						if (object!=null) {
	                        object.put("audit_info_list", list);
	                    }
						
				}else {//不为个人类型1,也不为企业类型2。返回404页面
					response.failure("标的信息不存在");
					JSONUtils.printObject(response);
					return null;
				}
				
				// 投资记录
				List<Map<String, Object>> investList = financeService
						.queryInvestByidApp(id);
                    object.put("investment_list", investList);
				
		        // 借款人还款记录  hjh
		        List<Map<String, Object>> list = financeService
		                .queryRePayByidApp(Convert.strToLong(idStr, -1L));
	            object.put("repayment_plan", list);
	            
	            object.putAll(jsonborrowDetailMap);
	            object.put("debtor_type", userType);
				response.success(object);
	            JSONUtils.printObject(response);
	            return null;
			}
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 点击查看详情的时候，判断某标的的状态
	 * 
	 * @param tInt
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 */
	private String judgeStatus(int tInt, Long userId) throws Exception {
		if (tInt < 3) {// 秒还、净值标的
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if (aa < 0) {
				return "waitBorrow";

			}
		} else {// 其它借款
			Long aa = borrowService.queryBaseApprove(userId, 3);
			if (aa < 0) {
				return "waitBorrow";
			} else {
				Long bb = borrowService.queryBaseFiveApprove(userId);
				if (bb < 0) {
					return "waitBorrow";
				}
			}
		}
		return null;
	}

	/**
	 * 债权转让借款详情
	 * 
	 * @return
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String queryDebtBorrowDetail() throws SQLException, DataException,
			IOException {
		return financeDetail();
	}

	/**
	 * @throws IOException
	 * @MethodName: financeAudit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:26:02
	 * @Return:
	 * @Descb: 借款人认证资料
	 * @Throws:
	 */
	public String financeAudit() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {

			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款人ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			// 借款人认证资料
			List<Map<String, Object>> list = financeService
					.queryUserIdentifiedByid(idLong);
			request().setAttribute("auditList", list);
			jsonMap.put("auditList", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws DataException
	 * @throws SQLException
	 * @throws IOException
	 * @MethodName: financeRepay
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:27:02
	 * @Return:
	 * @Descb: 借款人还款记录
	 * @Throws:
	 */
	public String financeRepay() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			// 借款人还款记录
			List<Map<String, Object>> list = financeService
					.queryRePayByid(idLong);
			request().setAttribute("repayList", list);
			jsonMap.put("repayList", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @MethodName: financeCollection
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-20 上午08:29:12
	 * @Return:
	 * @Descb: 借款人催款记录
	 * @Throws:
	 */
	public String financeCollection() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			// 借款人催款记录
			List<Map<String, Object>> list = financeService
					.queryCollectionByid(idLong);
			// request().setAttribute("collectionList", list);
			jsonMap.put("collectionList", list);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查询成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}

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
		Map<String, String> jsonMap = new HashMap<String, String>();
		User user = null;
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId != -1) {
				user = userService.jumpToWorkData(userId);
			}
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			if (idLong == -1) {
				// 非法操作直接返回
				jsonMap.put("error", "2");
				jsonMap.put("msg", IConstants.ACTOIN_ILLEGAL);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 获取用户认证进行的步骤
			if (user.getAuthStep() == 1) {
				// 个人信息认证步骤
				return "querBaseData";
			} else if (user.getAuthStep() == 2) {
				// 工作信息认证步骤
				return "querWorkData";
			} else if (user.getAuthStep() == 3) {
				// VIP申请认证步骤
				return "quervipData";
			} else if (user.getAuthStep() == 4) {
				// 上传资料认证步骤
				return "userPassData";
			}
			Map<String, String> investMap = financeService
					.getInvestStatus(idLong);
			String nid_log = "";
			if (investMap != null && investMap.size() > 0) {
				nid_log = Convert.strToStr(investMap.get("nid_log"), "");
				Map<String, String> typeLogMap = null;
				if (StringUtils.isNotBlank(nid_log)) {
					typeLogMap = shoveBorrowTypeService
							.queryBorrowTypeLogByNid(nid_log.trim());
					int stauts = Convert.strToInt(typeLogMap
							.get("subscribe_status"), -1);
					request().setAttribute("subscribes", stauts);
					request().setAttribute("investMap", investMap);
				}
				String hasPWD = investMap.get("hasPWD") == null ? "-1"
						: investMap.get("hasPWD");
				investDetailMap = financeService.queryBorrowInvest(idLong);

				String userid = investDetailMap.get("userId") == null ? ""
						: investDetailMap.get("userId");
				if (userid.equals(user.getId().toString())) {
					// 不满足投标条件,返回
					jsonMap.put("error", "3");
					jsonMap.put("msg", "不能投标自己发布的借款");
					JSONUtils.printObject(jsonMap);
					return null;
				}

				session().setAttribute("investStatus", "ok");
				Map<String, String> userMap = financeService
						.queryUserMonney(user.getId());
				request().setAttribute("userMap", userMap);
				session().setAttribute("hasPWD", hasPWD);

				jsonMap.put("error", "-1");
				jsonMap.put("msg", "初始化成功");
				JSONUtils.printObject(jsonMap);
				// if(!"-1".equals(hasPWD)){
				// request().setAttribute("id", id);
				// return "pwdBorrow";
				// }
			} else {
				// 不满足投标条件,返回
				jsonMap.put("error", "4");
				jsonMap.put("msg", "该借款投标状态已失效");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "5");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
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
	public String financeInvestLoad() throws Exception {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			User user = null;
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId != -1) {
				user = userService.jumpToWorkData(userId);
			}
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String investPWD = appInfoMap.get("investPWD");
			if (StringUtils.isBlank(investPWD)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "密码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);

			if (idLong == -1) {
				// 非法操作直接返回
				jsonMap.put("error", "3");
				jsonMap.put("msg", IConstants.ACTOIN_ILLEGAL);
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> investPWDMap = financeService.getInvestPWD(
					idLong, investPWD);
			if (investPWDMap == null || investPWDMap.size() == 0) {
				// this.addFieldError("paramMap['investPWD']", "投标密码错误");
				jsonMap.put("error", "4");
				jsonMap.put("msg", "投标密码错误");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			// 判断是否进行了资料审核
			Object object = session().getAttribute("investStatus");
			if (object == null) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "投标资料未审核通过");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			Map<String, String> investMap = financeService
					.getInvestStatus(idLong);
			if (investMap != null && investMap.size() > 0) {
				investDetailMap = financeService.queryBorrowInvest(idLong);

				String userid = investDetailMap.get("userId") == null ? ""
						: investDetailMap.get("userId");
				if (userid.equals(user.getId().toString())) {
					// 不满足投标条件,返回
					jsonMap.put("error", "6");
					jsonMap.put("msg", "不能投标自己发布的借款");
					JSONUtils.printObject(jsonMap);
					return null;
				}
				Map<String, String> userMap = financeService
						.queryUserMonney(user.getId());
				jsonMap.put("userMap", userMap);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "投标初始化成功");
				JSONUtils.printObject(jsonMap);
			} else {
				// 不满足投标条件,返回
				jsonMap.put("error", "7");
				jsonMap.put("msg", "该借款投标状态已失效");
				JSONUtils.printObject(jsonMap);
				return null;
			}
		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: financeInvest
	 * @Param: FrontMyFinanceAction
	 * @Author: l.x.z
	 * @Return:
	 * @Descb: 投标
	 * @Throws:
	 */
	public String financeInvest() throws Exception {
		Response response = new Response();
		try {
			log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
			Map<String, String> appInfoMap = getAppInfoMap();
			String mobile_tel = appInfoMap.get("mobile_tel");
			if (StringUtils.isBlank(mobile_tel)) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
			long userId = getUserId(mobile_tel, userService);
			if (userId==-1L) {
				response.failure("请重新登陆");
				JSONUtils.printObject(response);
				return null;
			}
		    
		    Map<String, String> m = new HashMap<String, String>();
			m = userService.queryUserById(userId);
			JSONObject obj = new JSONObject();
			 
			if (null != m) {
				if (m.get("password").equals(m.get("dealpwd"))) {
				    response.failure("*为了您的账户安全，交易密码不能与登录密码相同");
					JSONUtils.printObject(response);
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

		   
		    boolean re = userService.checkSign(userId);
			if(!re){
				 response.failure("*您的账号出现异常，请速与管理员联系!");
				 JSONUtils.printObject(response);
				return null;
			}
			String id = appInfoMap.get("object_id") == null?"":appInfoMap.get("object_id");
			long idLong = Convert.strToLong(id, -1);
			String amount = appInfoMap.get("investment_amount") == null?"":appInfoMap.get("investment_amount");
			double amountDouble = Convert.strToDouble(amount, 0);
			String hasPWD = "";//是否有密码
		    String investPWD = "";//投标密码
		    int status =2;//是否开启认购模式（不开启）
		    
		    String dealPWD = appInfoMap.get("business_pwd")==null?"":appInfoMap.get("business_pwd");
		    if (StringUtils.isBlank(dealPWD)) {
		        response.failure("*请输入交易密码");
				 JSONUtils.printObject(response);
	            return null;
				//this.addFieldError("paramMap['dealPWD']", "请输入交易密码");
				//return "input";
			}
		    
		    String oldPass = com.shove.security.Encrypt.MD5(dealPWD
					+ IConstants.PASS_KEY);//输入的交易密码MD5加密
		    if(!oldPass.equals(m.get("dealpwd"))){
		        response.failure("*交易密码错误");
				 JSONUtils.printObject(response);
	            return null;
		    	//this.addFieldError("paramMap['dealPWD']", "交易密码错误");
				//return "input";	
		    }

		    if("1".equals(hasPWD)){
		    	Map<String, String> investPWDMap = financeService.getInvestPWD(idLong,investPWD);
				if (investPWDMap == null || investPWDMap.size() ==0) {
					if(status == 1){
						response.failure("投标密码错误");
						 JSONUtils.printObject(response);
						return null;
					}
					response.failure("投标密码错误");
					 JSONUtils.printObject(response);
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
	                response.failure("此标的尚未到投标时间");
					 JSONUtils.printObject(response);
	                return null;
	            }
	        }
		    
		    int num =0;
		    if(status ==1){
		    	double smallestFlowUnit = Convert.strToDouble(paramMap.get("smallestFlowUnit"), 0.0);
		    	if (smallestFlowUnit==0) {
		    		response.failure("操作失败");
					 JSONUtils.printObject(response);
					return null;
				}
		    	String result = Convert.strToStr(paramMap.get("result"),"");
		    	if(StringUtils.isBlank(result)){
		    		response.failure("请输入购买的份数");
					 JSONUtils.printObject(response);
					return null;
		    	}
		    	boolean b=result.matches("[0-9]*");
		    	if(!b){
		    		response.failure("请正确输入购买的份数");
					 JSONUtils.printObject(response);
					return null;
		    	} 
		    	
		    	String userIds = investDetailMap.get("userId") == null ? "": investDetailMap.get("userId");
		    	if (userIds.equals(String.valueOf(userId))) {
		    	response.failure("不能投标自己发布的借款");
				 JSONUtils.printObject(response);
		    	return null;
		    	    }
		    	 num = Integer.parseInt(result);
		    	if (num<1) {
		    		response.failure("请正确输入购买的份数");
					 JSONUtils.printObject(response);
					return null;
				}
		    	amountDouble = num * smallestFlowUnit;
		    }
		    Map<String,String> result = financeService.addBorrowInvest(idLong, userId,"", amountDouble,getBasePath(),m.get("username"),status,num);
		    String resultMSG = result.get("ret_desc");
		    userService.updateSign(userId);//更换校验码
		    if("".equals(resultMSG)){
				Map<String, String> mypaytreasure = payTreasureService.queryMyPayInvest(userId);
				Map<String, String> usableSum= userService.queryPresent(userId);
				mypaytreasure.putAll(usableSum);
				appInfoMap.clear();
				appInfoMap.put("zxb_accum", MapUtils.getString(mypaytreasure, "investamount","0.00"));
				appInfoMap.put("available_balance", MapUtils.getString(usableSum, "usableSum","0.00"));
		    	response.success(appInfoMap);
		    	JSONUtils.printObject(response);
				return null;
		    }else{
		    	response.failure(result.get("ret_desc"));
				 JSONUtils.printObject(response);
				return null;
		    }
			
		} catch (Exception e) {
			response.failure("服务器异常");
			JSONUtils.printObject(response);
			return null;
		}
	}

	/**
	 * @throws IOException
	 * @MethodName: borrowMSGInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:08:51
	 * @Return:
	 * @Descb: 借款留言初始化
	 * @Throws:
	 */
	public String borrowMSGInit() throws SQLException, DataException,
			IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			String pageNum = appInfoMap.get("curPage");
			if (StringUtils.isNotBlank(pageNum)) {
				pageBean.setPageNum(pageNum);
			}
			pageBean.setPageSize(IConstants.PAGE_SIZE_6);
			if (idLong == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			financeService.queryBorrowMSGBord(idLong, pageBean);
			// request().setAttribute("id", id);
			jsonMap.put("id", id);
			jsonMap.put("pageBean", pageBean);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "初始化成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "9");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @throws IOException
	 * @throws SQLException
	 * @MethodName: addBorrowMSG
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-12 下午11:09:06
	 * @Return:
	 * @Descb: 添加借款留言
	 * @Throws:
	 */
	public String addBorrowMSG() throws IOException, SQLException {

		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			long userId = Convert.strToLong(appInfoMap.get("userid"), -1);
			if (userId == -1) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户不存在");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			String msgContent = appInfoMap.get("msg");
			if (StringUtils.isBlank(msgContent)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "留言内容不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if(isKeywords.isKeywordsOnDB(msgContent)){
				jsonMap.put("error", "4");
				jsonMap.put("msg", "留言内容含有关键字，不能留言");
				JSONUtils.printObject(jsonMap);
			}
			long returnId = -1;
			returnId = financeService.addBorrowMSG(idLong, userId, msgContent);
			if (returnId <= 0) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", IConstants.ACTION_FAILURE);
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加成功返回值
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "添加成功");
				JSONUtils.printObject(jsonMap);

			}
		} catch (Exception e) {
			jsonMap.put("error", "6");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
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
	// public String focusOnBorrow() throws SQLException, IOException,
	// DataException {
	// Map<String, String> jsonMap = new HashMap<String, String>();
	// try{
	//			
	// Map<String, String> appInfoMap = getAppInfoMap();
	// User user = session().getAttribute("user");
	// String id = appInfoMap.get("id");
	// if(StringUtils.isBlank(id)){
	// jsonMap.put("error", "1");
	// jsonMap.put("msg", "借款ID不能为空");
	// JSONUtils.printObject(jsonMap);
	// return null;
	// }
	// long returnId = -1L;
	// long idLong = Convert.strToLong(id, -1);
	// Map<String, String> map = financeService.hasFocusOn(idLong, user
	// .getId(), IConstants.FOCUSON_BORROW);
	// if (map != null && map.size() > 0) {
	// jsonMap.put("error", "2");
	// jsonMap.put("msg", "您已关注过该借款");
	// JSONUtils.printObject(jsonMap);
	// return null;
	// }
	//	
	// returnId = financeService.addFocusOn(idLong, user.getId(),
	// IConstants.FOCUSON_BORROW);
	// if (returnId <= 0) {
	// jsonMap.put("error", "3");
	// jsonMap.put("msg", IConstants.ACTION_FAILURE);
	// JSONUtils.printObject(jsonMap);
	// return null;
	// } else {
	// jsonMap.put("error", "-1");
	// jsonMap.put("msg", "关注成功");
	// JSONUtils.printObject(jsonMap);
	// }
	// }catch (Exception e) {
	// jsonMap.put("error", "6");
	// jsonMap.put("msg", "未知异常");
	// JSONUtils.printObject(jsonMap);
	// log.error(e);
	// e.printStackTrace();
	// }
	// return null;
	// }

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
	// public String focusOnUser() throws SQLException, IOException,
	// DataException {
	// Map<String, String> jsonMap = new HashMap<String, String>();
	// try {
	// Map<String, String> appInfoMap = getAppInfoMap();
	// User user = session().getAttribute("user");
	// String id = appInfoMap.get("id");
	// if(StringUtils.isBlank(id)){
	// jsonMap.put("error", "1");
	// jsonMap.put("msg", "用户ID不能为空");
	// JSONUtils.printObject(jsonMap);
	// return null;
	// }
	// long returnId = -1L;
	// long idLong = Convert.strToLong(id, -1);
	// Map<String, String> map = financeService.hasFocusOn(idLong, user
	// .getId(), IConstants.FOCUSON_USER);
	// if (map != null && map.size() > 0) {
	// jsonMap.put("error", "2");
	// jsonMap.put("msg", "您已关注过该用户");
	// JSONUtils.printObject(jsonMap);
	// return null;
	// }
	// returnId = financeService.addFocusOn(idLong, user.getId(),
	// IConstants.FOCUSON_USER);
	// if (returnId <= 0) {
	// jsonMap.put("error", "2");
	// jsonMap.put("msg", IConstants.ACTION_FAILURE);
	// JSONUtils.printObject(jsonMap);
	// return null;
	// } else {
	// jsonMap.put("error", "2");
	// jsonMap.put("msg", "关注成功!");
	// JSONUtils.printObject(jsonMap);
	// }
	// } catch (Exception e) {
	// jsonMap.put("error", "6");
	// jsonMap.put("msg", "未知异常");
	// JSONUtils.printObject(jsonMap);
	// log.error(e);
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * @throws IOException
	 * @MethodName: mailInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:31
	 * @Return:
	 * @Descb: 发送站内信初始化
	 * @Throws:
	 */
	public String mailInit() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String userName = appInfoMap.get("username");
			if (StringUtils.isBlank(userName)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户名不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			request().setAttribute("id", id);
			request().setAttribute("userName", userName);
			jsonMap.put("id", id);
			jsonMap.put("userName", userName);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "初始化成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @MethodName: reportInit
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午06:23:48
	 * @Return:
	 * @Descb: 举报用户初始化
	 * @Throws:
	 */
	public String reportInit() throws IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String userName = appInfoMap.get("username");
			if (StringUtils.isBlank(userName)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户名不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			request().setAttribute("id", id);
			request().setAttribute("userName", userName);
			jsonMap.put("id", id);
			jsonMap.put("userName", userName);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "初始化成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Descb: 发送邮件
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public String mailAdd() throws IOException, SQLException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		User user = null;
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId != -1) {
				user = userService.jumpToWorkData(userId);
			}

			String code = (String) session().getAttribute("code_checkCode");
			Map<String, String> appInfoMap = getAppInfoMap();
			String _code = appInfoMap.get("code");
			if (StringUtils.isBlank(_code)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!code.equals(_code)) {
				this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
				jsonMap.put("error", "2");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String id = appInfoMap.get("id");
			if (!code.equals(id)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long reciver = Convert.strToLong(id, -1);
			String title = appInfoMap.get("title");
			if (!code.equals(title)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "标题不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String content = appInfoMap.get("content");
			if (!code.equals(content)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "内容不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long returnId = -1;
			Integer enable = user.getEnable();
			if (enable == 3) {
				jsonMap.put("error", "6");
				jsonMap.put("msg", "用户被禁用");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			returnId = financeService.addUserMail(reciver, user.getId(), title,
					content, IConstants.MALL_TYPE_COMMON);
			if (returnId <= 0) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", IConstants.ACTION_FAILURE);
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加成功返回值
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "添加成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @MethodName: reportAdd
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-3-16 下午10:16:11
	 * @Return:
	 * @Descb: 添加用户举报
	 * @Throws:
	 */
	public String reportAdd() throws SQLException, IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		User user = null;
		try {
			Map<String, String> authMap = getAppAuthMap();
			long userId = Convert.strToLong(authMap.get("uid"), -1);
			if (userId != -1) {
				user = userService.jumpToWorkData(userId);
			}
			String code = (String) session().getAttribute("code_checkCode");
			Map<String, String> appInfoMap = getAppInfoMap();
			String _code = appInfoMap.get("code");
			if (StringUtils.isBlank(_code)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "验证码不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			if (!code.equals(_code)) {
				this.addFieldError("paramMap['code']", IConstants.CODE_FAULS);
				jsonMap.put("error", "2");
				jsonMap.put("msg", "验证码不正确");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String id = appInfoMap.get("id");
			if (!code.equals(id)) {
				jsonMap.put("error", "3");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long reporter = Convert.strToLong(id, -1);
			String title = appInfoMap.get("title");
			if (!code.equals(id)) {
				jsonMap.put("error", "4");
				jsonMap.put("msg", "标题不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String content = appInfoMap.get("content");
			if (!code.equals(content)) {
				jsonMap.put("error", "5");
				jsonMap.put("msg", "内容不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long returnId = -1;
			returnId = financeService.addUserReport(reporter, user.getId(),
					title, content);
			if (returnId <= 0) {
				jsonMap.put("error", "7");
				jsonMap.put("msg", IConstants.ACTION_FAILURE);
				JSONUtils.printObject(jsonMap);
				return null;
			} else {
				// 添加成功返回值
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "举报成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "8");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @throws IOException
	 * @MethodName: showImg
	 * @Param: FrontMyFinanceAction
	 * @Author: gang.lv
	 * @Date: 2013-4-16 上午11:24:03
	 * @Return:
	 * @Descb: 查看图片
	 * @Throws:
	 */
	public String showImg() throws SQLException, DataException, IOException {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			Map<String, String> appAuthMap = getAppAuthMap();
			String typeId = appInfoMap.get("typeId");
			if (StringUtils.isBlank(typeId)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "类型ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			String userId = appAuthMap.get("userId");
			if (StringUtils.isBlank(userId)) {
				jsonMap.put("error", "2");
				jsonMap.put("msg", "用户ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long typeIdLong = Convert.strToLong(typeId, -1);
			long userIdLong = Convert.strToLong(userId, -1);
			List<Map<String, Object>> imgList = financeService
					.queryUserImageByid(typeIdLong, userIdLong);
			// request().setAttribute("imgList", imgList);
			jsonMap.put("imgList", imgList);
			jsonMap.put("error", "-1");
			jsonMap.put("msg", "查看成功");
			JSONUtils.printObject(jsonMap);
		} catch (Exception e) {
			jsonMap.put("error", "3");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, String> getInvestDetailMap() throws SQLException,
			IOException {
		Map<String, String> jsonMap = new HashMap<String, String>();
		try {
			Map<String, String> appInfoMap = getAppInfoMap();
			String id = appInfoMap.get("id");
			if (StringUtils.isBlank(id)) {
				jsonMap.put("error", "1");
				jsonMap.put("msg", "借款ID不能为空");
				JSONUtils.printObject(jsonMap);
				return null;
			}
			long idLong = Convert.strToLong(id, -1);
			if (investDetailMap == null) {
				investDetailMap = financeService.queryBorrowInvest(idLong);
				jsonMap.put("error", "-1");
				jsonMap.put("msg", "查询成功");
				JSONUtils.printObject(jsonMap);
			}
		} catch (Exception e) {
			jsonMap.put("error", "2");
			jsonMap.put("msg", "未知异常");
			JSONUtils.printObject(jsonMap);
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * app首页滚动图
	 */
	public String getHomeImg() throws Exception{
		Response response = new Response();
	   try {
		    Map<String, Object> json = new HashMap<String, Object>();
		    pageBean.setPageSize(4);
		    publicModelService.queryAppImgPage(pageBean);
		    json.put("banner_list", pageBean.getPage());
		    response.success(json);
		} catch (Exception e) {
			e.printStackTrace();
			response.failure("服务器异常");
		}
	    JSONUtils.printObject(response);
	    return null;
	}
	
	/*
	 * 推荐
	 */
	public String recommandBorrowMethod() throws IOException, SQLException{
        JSONUtils.printObject(publicModelService.queryRecommandBorrow());
        return null;
	}
	
	/*
	 *  借款方信息
	 */
	public String borrowInfoInverstor() throws Exception{
	    Map<String, String> appInfoMap = getAppInfoMap();
	    long id = Convert.strToLong(appInfoMap.get("id"), -1);
	    String type = appInfoMap.get("type");
	    Map<String, String> borrowUserMap = null;
	    if ("1".equals(type)) {
	        // 借款人资料
	        borrowUserMap = financeService
	                 .queryUserInfoById(id);
        }
	    if ("2".equals(type)) {
	        borrowUserMap = financeService
	                .queryUserInfoById2(id);
        }
	    Map<String, Object> jsonMap = new HashMap<String, Object>();
	    jsonMap.put("dbList", borrowUserMap);
        JSONUtils.printObject(jsonMap);
        return null;
	}
	
	/*
	 * 投资记录
	 */
	public String inversthistory() throws Exception{
	    Map<String, String> appInfoMap = getAppInfoMap();
        long id = Convert.strToLong(appInfoMap.get("id"), -1);
	    // 投资记录
        List<Map<String, Object>> investList = financeService
                .queryInvestByid(id);
       JSONUtils.printArray(investList);
        return null;
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

	// public NewsService getNewsService() {
	// return newsService;
	// }
	//
	// public void setNewsService(NewsService newsService) {
	// this.newsService = newsService;
	// }

	// public SuccessStoryService getSuccessStoryService() {
	// return successStoryService;
	// }
	//
	// public void setSuccessStoryService(SuccessStoryService
	// successStoryService) {
	// this.successStoryService = successStoryService;
	// }
	    
	   public String queryProtocol() throws Exception{
	       Map<String, String> info = getAppInfoMap();
	        int typeId =  request.getInt("typeId", -1);
	        long userId =Convert.strToLong(info.get("uid"), -2);
	        List<Map<String, Object>> investMaps = null;
	        Map<String, String> map = new HashMap<String, String>();
	        Map<String, String> mapContent = new HashMap<String, String>();
	        Map<String, String> Content = new HashMap<String, String>();
	        Map<String, String> sumMap = null;
	        List<Map<String, Object>> user_invest_map = null;
	        List<Map<String, Object>> borrow_map = null;
	        // 得到session 对象
	        long borrowId = request.getLong("borrowId", -1);
	        long invest_id = request.getLong("investId", -1);
	        long styles = request.getLong("styles", -1);
	        //校验用户是否有权限查看协议
	        Map<String,String> authProtocolMap = borrowManageService.queryAuthProtocol(borrowId,invest_id);
	        if(authProtocolMap == null){
	            getOut().print("<script>alert('对不起！您没有查看此协议的权限。');window.history.go(-1);</script>");
	            return null;
	        }
	        if (typeId == 15 || typeId == 1) {
	            
	            sumMap = borrowManageService.queryBorrowSumMomeny(borrowId,
	                    invest_id);
	            map = borrowManageService.queryBorrowMany(borrowId);
	            investMaps = borrowManageService.queryInvestMomey(borrowId,
	                    invest_id);
	            user_invest_map = borrowManageService.queryUsername(borrowId,
	                    invest_id);
	            // 得到还款记录
	            borrow_map = financeService.queryRepayment(borrowId);
	            // 替换设定的参数值
	            // 得到借款协议
	            mapContent = publicModelService.getMessageByTypeId(1);
	            Content = publicModelService.getMessageByTypeId(18);
	            String map_cont = mapContent.get("content")+"";
	            if(null != map_cont){
	                map_cont = map_cont.replace("[corporation]", IConstants.PRO_GLOBLE_NAME+"");//平台名称
	                 Map<String, String> modelValue =  publicModelService.modelValue(borrowId);
	                    if (modelValue==null) {
	                        map_cont = map_cont.replace("[name]","无");//银行户名
	                        map_cont = map_cont.replace("[bankName]","无");//银行开户行
	                        map_cont = map_cont.replace("[bankNo]", "无");//银行账号
	                        map_cont = map_cont.replace("[guarantee]","深圳华融融资担保有限公司");//担保公司
	                    }else {
	                        long publisher = Convert.strToLong(modelValue.get("publisher"), -1);
	                        if (publisher==userId) {
	                            map_cont = map_cont.replace("[name]",modelValue.get("cardUserName"));//银行户名
	                            map_cont = map_cont.replace("[bankName]", modelValue.get("bankName"));//银行开户行
	                            map_cont = map_cont.replace("[bankNo]", modelValue.get("cardNo"));//银行账号
	                        }else {
	                            map_cont = map_cont.replace("[name]",modelValue.get("cardUserName"));//银行户名
	                            map_cont = map_cont.replace("[bankName]",  com.shove.util.StringUtils.formatStr(modelValue.get("bankName"), 1));//银行开户行
	                            map_cont = map_cont.replace("[bankNo]", com.shove.util.StringUtils.formatStr(modelValue.get("cardNo"), 4));//银行账号
	                        }
	                        map_cont = map_cont.replace("[guarantee]", (StringUtils.isBlank(modelValue.get("selectName"))?"深圳华融融资担保有限公司":modelValue.get("selectName")));//担保公司
	                    }
	                    if (map!=null) {
	                        long publisher = Convert.strToLong(modelValue.get("publisher"), -1);
	                        if (publisher==userId) {
	                            if (StringUtils.isBlank(map.get("realName")) && StringUtils.isBlank(map.get("orgName")) ) {
	                                map_cont = map_cont.replace("[realName]", "无");//真实姓名
	                            }else {
	                                map_cont = map_cont.replace("[realName]", map.get("orgName")+map.get("realName"));//真实姓名
	                            }
	                            map_cont = map_cont.replace("[account]",map.get("username") );//账号
	                        }else {
	                            if (StringUtils.isBlank(map.get("realName")) && StringUtils.isBlank(map.get("orgName")) ) {
	                                map_cont = map_cont.replace("[realName]", "无");//真实姓名
	                            }else {
	                                map_cont = map_cont.replace("[realName]", com.shove.util.StringUtils.formatStr(map.get("orgName")+map.get("realName"), 1));//真实姓名
	                            }
	                            map_cont = map_cont.replace("[account]",com.shove.util.StringUtils.formatStr(map.get("username"), 1) );//账号
	                        }
	                    }
	            }
	            mapContent.put("content", map_cont);

	            String cont_cont = Content.get("content")+"";
	            if(null != cont_cont){
	                cont_cont = cont_cont.replace("[corporation]", IConstants.PRO_GLOBLE_NAME+"");
	            }
	            Content.put("content", cont_cont);
	            request().setAttribute("mapContent", mapContent);
	            request().setAttribute("investMaps", investMaps);
	            request().setAttribute("contentMap", Content);
	            request().setAttribute("map", map);
	            request().setAttribute("sumMap", sumMap);
	            request().setAttribute("styles", styles);
	            request().setAttribute("borrow_map", borrow_map);
	            request().setAttribute("user_invest_map", user_invest_map);
	            return "jkxy";
	        }
	       
	        return null;
	    }
	
	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
	}

	public List<Map<String, Object>> getBorrowPurposeList() throws Exception {
		borrowPurposeList = selectedService.borrowPurpose();

		return borrowPurposeList;
	}

	public List<Map<String, Object>> getBorrowDeadlineList() throws Exception {
		borrowDeadlineList = selectedService.borrowDeadline();

		return borrowDeadlineList;
	}

	public List<Map<String, Object>> getBorrowAmountList() throws Exception {
		borrowAmountList = selectedService.borrowAmountRange();
		return borrowAmountList;
	}

	public void setShoveBorrowTypeService(
			ShoveBorrowTypeService shoveBorrowTypeService) {
		this.shoveBorrowTypeService = shoveBorrowTypeService;
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

	public void setBorrowDeadlineList(
			List<Map<String, Object>> borrowDeadlineList) {
		this.borrowDeadlineList = borrowDeadlineList;
	}

	public void setBorrowAmountList(List<Map<String, Object>> borrowAmountList) {
		this.borrowAmountList = borrowAmountList;
	}

	// public void setLinksService(LinksService linksService) {
	// this.linksService = linksService;
	// }

	public List<Map<String, Object>> getLinksList() {
		return linksList;
	}

	public void setLinksList(List<Map<String, Object>> linksList) {
		this.linksList = linksList;
	}

	// public void setMediaReportService(MediaReportService mediaReportService)
	// {
	// this.mediaReportService = mediaReportService;
	// }

	public List<Map<String, Object>> getMeikuList() {
		return meikuList;
	}

	public void setMeikuList(List<Map<String, Object>> meikuList) {
		this.meikuList = meikuList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public NewsAndMediaReportService getNewsAndMediaReportService() {
		return newsAndMediaReportService;
	}

	public void setNewsAndMediaReportService(
			NewsAndMediaReportService newsAndMediaReportService) {
		this.newsAndMediaReportService = newsAndMediaReportService;
	}

	public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

}
