package com.sp2p.action.front;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.allinpay.ets.client.StringUtil;
import com.shove.Convert;
import com.shove.data.DataException;
import com.shove.vo.PageBean;
import com.shove.web.util.JSONUtils;
import com.sp2p.constants.IConstants;
import com.sp2p.entity.User;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.FinanceService;
import com.sp2p.service.HelpAndServicerService;
import com.sp2p.service.PublicModelService;
import com.sp2p.service.admin.BorrowManageService;
import com.sp2p.util.DateUtil;

/**
 * 信息管理模块
 * 
 * @author Administrator
 * 
 */
public class FrontMessageAction extends BaseFrontAction {

	private static final long serialVersionUID = 1L;
	public static Log log = LogFactory.getLog(FrontMessageAction.class);
	private BorrowManageService borrowManageService;
	private PublicModelService publicModelService;
	private FinanceService financeService;
	private AssignmentDebtService assignmentDebtService;
	private HelpAndServicerService callCenterService;
	private PublicModelService linksService;
	
	private List<Map<String, Object>> teamList;
	private Map<String,String> helpAnswer;
	public List<Map<String, Object>> getTeamList() {
		return teamList;
	}
	
	
	

    public void setLinksService(PublicModelService linksService) {
        this.linksService = linksService;
    }




    public Map<String, String> getHelpAnswer() {
        return helpAnswer;
    }



    public void setHelpAnswer(Map<String, String> helpAnswer) {
        this.helpAnswer = helpAnswer;
    }



    public HelpAndServicerService getCallCenterService() {
        return callCenterService;
    }



    public void setCallCenterService(HelpAndServicerService callCenterService) {
        this.callCenterService = callCenterService;
    }



    public PublicModelService getPublicModelService() {
		return publicModelService;
	}

	public void setPublicModelService(PublicModelService publicModelService) {
		this.publicModelService = publicModelService;
	}

	public void setTeamList(List<Map<String, Object>> teamList) {
		this.teamList = teamList;
	}

	public String initMessage() {
		return SUCCESS;
	}

	/**
	 * 根据信息类型查询信息详情
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String getMessageBytypeId() throws Exception {
		Integer typeId = request.getInt("msg", -1);
		int type = request.getInt("type", 0);
		request().setAttribute("type", type);
		paramMap = publicModelService.getMessageByTypeId(typeId);
		//友情链接
		pageBean.setPageSize(100);
	    ///	linksService.queryLinksPage(pageBean);
		publicModelService.queryLinksListPageBack(pageBean);
		
		if (request("wap")!=null) {
			JSONObject object = new JSONObject();
			object.put("pageBean", pageBean);
			object.put("paramMap", paramMap);
			JSONUtils.printObject(object);
		}
		
		if (typeId == 1) {
			return "jkxy"; // 借款协议
		}
		if (typeId == 24) {
			return "zqjkxy"; // 借款协议
		}
		if (typeId == 2) {
			return "gsjj"; // 公司简介
		} else if (typeId == 3) {
			return "ptyl"; // 平台原理
		} else if (typeId == 4) {
			return "gywm"; // 关于我们
		} else if (typeId == 5) {
			return "flzc"; // 法律政策
		} else if (typeId == 6) {
			return "zfsm"; // 资费说明
		} else if (typeId == 7) {
			request().setAttribute("typeId", "7");
			return "lxwm"; // 联系我们
		} else if (typeId == 8) {// 如何理财
			return "rhlc";
		} else if (typeId == 9) {// 本金保障
			return "bjbz";
		} else if (typeId == 15) {// 借款协议
			return "jkxy";
		} else if (typeId == 34) {// 媒体报道
			return "mtbd";
		} else if (typeId == 35) {// 官方公告
			return "gfgg";
		} else if (typeId == 99) {// <!-- 关于我们  99 -->
            return "gywm";
        } else if (typeId == 100) {// <!-- 网站公告  100 -->
            return "wzgg";
        } else if (typeId == 101) {//<!-- 平台咨询、互联网金融  101 -->
            return "ptzx";
        } else if (typeId == 102) {// <!-- 资讯详情  102 -->
            return "ptzx_detail";
        } else if (typeId == 103) {// <!-- 三好资本动态  103 -->
            return "shd_news";
        } else if (typeId == 104) {//<!-- 合作伙伴  104 -->
            return "hzhb";
        } else if (typeId == 105) {// <!-- 联系我们  105 -->
            return "lxwm";
        } else if (typeId == 106) {// <!-- 帮助中心  106 -->
            callCenterInit() ;
            String questionId = request.getString("questionId");
            if (!StringUtil.isEmpty(questionId)){
            	request().setAttribute("questionId", questionId);
            }
            return "bzzx";
        } else if (typeId == 107) {// <!-- 人才招聘  107 -->
            return "rczp";
        } else if (typeId == 108) {// <!-- 三好资本问答  108 -->
            return "askaw";
        } else if (typeId == 109) {// <!-- 团队介绍 109 -->
            return "tdjs";
        } else if (typeId == 110) {// <!-- 运营主体 109 -->
            return "yyzt";
        } else if (typeId == 111) {// <!-- 项目模式 109 -->
            return "xmms";
        } else {
			return "gywm";
		}

	}
	
	/**
	 *功能：招聘页面
	 * @return
	 */
	public String zhaopin(){
		return "rczp";
	}
	
	public String queryCallCenterAnswer() throws Exception{
        log.info("className:" + this.getClass().getName() + ";methodName:" + Thread.currentThread().getStackTrace()[1].getMethodName());
        String id = Convert.strToStr(request.getString("id"), "");
        
        //添加浏览量
        try{
            helpAnswer = callCenterService.queryAnswer(Convert.strToLong(id, -1));
            callCenterService.updateQuestionBrowse(Convert.strToLong(id, -1));
        }catch(SQLException e){
            log.error(e);
            e.printStackTrace();
            throw e;
        }
        getOut().print(helpAnswer.get("anwer"));//获得对应问题的回答值
        return null;
    }
	
	public void callCenterInit() throws Exception{
        String pageNum = request.getString("curPage");
        String cid = request.getString("cid") == null?"1":request.getString("cid");
        request().setAttribute("cid", cid);
        
        long typeId = Convert.strToLong(cid, 1);
        try{    
            //查询左边菜单
            callCenterService.queryHelpTypes(pageBean);
            List<Map<String, Object>> types = pageBean.getPage();
            
            Map<String,String> values = null;
            String curDes = null;
            if(request.getString("type")!= null){
                values = callCenterService.getDescribeById(typeId);
                curDes = values.get("helpDescribe");//获得帮助类型描述
            }
            
            //右边问题
            
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
    }
	
	/*
	 * 借款协议
	 */
	public String queryProtocol() throws Exception{
		Integer typeId = request.getInt("typeId", -1);//协议类型
		List<Map<String, Object>> investMaps = null;
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> mapContent = new HashMap<String, String>();
		Map<String, String> Content = new HashMap<String, String>();
		Map<String, String> sumMap = null;
		List<Map<String, Object>> user_invest_map = null;
		List<Map<String, Object>> borrow_map = null;
		// 得到session 对象
		long borrowId = request.getLong("borrowId", -1);//标的ID
		long invest_id = request.getLong("investId", -1);//投资ID
		long styles = request.getLong("styles", -1);//样式
		User user = (User)session().getAttribute("user");
		long userId= user.getId();
		//校验用户是否有权限查看协议
		Map<String,String> authProtocolMap = borrowManageService.queryAuthProtocol(borrowId,invest_id,userId);

		if (typeId == 15 || typeId == 1) {
		    if(authProtocolMap == null){
	            getOut().print("<script>alert('对不起！您没有查看此协议的权限。');window.history.go(-1);</script>");
	            return null;
	        }
		    long investor = Convert.strToLong(authProtocolMap.get("investor"), -1);
	        long borrower = Convert.strToLong(authProtocolMap.get("publisher"), -1);
	        long oriInvestor = Convert.strToLong(authProtocolMap.get("oriInvestor"), -1);
	        if(userId == investor && userId == borrower && userId == oriInvestor){
	            return "404";
	        }
			sumMap = borrowManageService.queryBorrowSumMomeny(borrowId,
					invest_id);
			map = borrowManageService.queryBorrowMany(borrowId);//包含借款方信息
			if (map!=null &&!StringUtils.isBlank(map.get("borrowAmount"))) {
                String borrowAmount = com.shove.util.StringUtils.digitUppercase(Convert.strToDouble(map.get("borrowAmount"), 0));
                map.put("borrowAmount", borrowAmount);
            }
			
			investMaps = borrowManageService.queryInvestMomey(borrowId,invest_id);
			user_invest_map = borrowManageService.queryUsername1(borrowId,invest_id);//投资用户信息
			// 得到还款记录
			borrow_map = financeService.queryRepayment(borrowId);
			// 替换设定的参数值
			// 得到借款协议
			mapContent = publicModelService.getMessageByTypeId(1);
			Content = publicModelService.getMessageByTypeId(18);//协议内容
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
				cont_cont = cont_cont.replace("[corporation]", IConstants.PRO_GLOBLE_NAME+"");//平台名称
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
		if (typeId == 24) {// 债权转让协议
			paramMap = publicModelService.getMessageByTypeId(typeId);
			long aid = request.getLong("aid", -1);
			map = assignmentDebtService.queryDebtUserName(aid);
			if (map != null) {
				String cont_cont = Convert.strToStr(paramMap.get("content"), "");
				if(null != cont_cont){
					cont_cont = cont_cont.replace("[ppusername]", IConstants.PRO_GLOBLE_NAME+"");
					cont_cont = cont_cont.replace("[pusername]", Convert
						.strToStr(map.get("dbusername"), "未填写"));
					cont_cont = cont_cont.replace("[yusername]", Convert
						.strToStr(map.get("realName"), ""));
					cont_cont = cont_cont.replace("[endtime]", Convert
						.strToStr(map.get("auctionEndTime"), ""));
				}
				paramMap.put("content", cont_cont);
				// 得到借款Id
				borrowId = Convert.strToLong(map.get("borrowId"), -1);
				// 得到投资Id
				invest_id = Convert.strToLong(map.get("investId"), -1);
				sumMap = borrowManageService.queryBorrowSumMomeny(borrowId,invest_id);
				mapContent = borrowManageService.queryBorrowInfo(borrowId);
				SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dd.parse(mapContent.get("auditTime"));
				// 得到借款期限
				int isdayThe = Convert.strToInt(mapContent.get("isDayThe"),0);
				int deadline = Convert.strToInt(mapContent.get("deadline"),0);
				if (isdayThe == 1) {
					date = DateUtil.dateAddMonth(date, deadline);
				} else {
					date = DateUtil.dateAddDay(date, deadline);
				}
				String stime = dd.format(date);
				mapContent.put("auditTime", stime);
				request().setAttribute("map", map);
				request().setAttribute("sumMap", sumMap);
				request().setAttribute("mapContent", mapContent);
			}
			return "zqjkxy";
		}
		//债权转让协议 2015-08-20 15:26:41  hjh
		if (typeId==2) {
		    if(authProtocolMap == null){
	            getOut().print("<script>alert('对不起！您没有查看此协议的权限。');window.history.go(-1);</script>");
	            return null;
	        }
		    long investor = Convert.strToLong(authProtocolMap.get("investor"), -1);
	        long borrower = Convert.strToLong(authProtocolMap.get("publisher"), -1);
	        long oriInvestor = Convert.strToLong(authProtocolMap.get("oriInvestor"), -1);
	        if(userId == investor && userId == borrower && userId == oriInvestor){
	            return "404";
	        }
			sumMap = borrowManageService.queryBorrowSumMomeny(borrowId,
					invest_id);
			map = borrowManageService.queryBorrowMany(borrowId);//包含借款方信息
			if (map!=null &&!StringUtils.isBlank(map.get("borrowAmount"))) {
                String borrowAmount = com.shove.util.StringUtils.digitUppercase(Convert.strToDouble(map.get("borrowAmount"), 0));
                map.put("borrowAmount", borrowAmount);
            }
			
			investMaps = borrowManageService.queryInvestMomey(borrowId,invest_id);
			user_invest_map = borrowManageService.queryUsername1(borrowId,invest_id);//投资用户信息
			// 得到还款记录
			borrow_map = financeService.queryRepayment(borrowId);
			
			//得到债权借款人实际信息
			Map<String, String> transferUser = borrowManageService.queryTransferUser(borrowId);
			request().setAttribute("transferUser", transferUser);
			
			request().setAttribute("mapContent", mapContent);
			request().setAttribute("investMaps", investMaps);
			request().setAttribute("contentMap", Content);
			request().setAttribute("map", map);
			request().setAttribute("sumMap", sumMap);
			request().setAttribute("styles", styles);
			request().setAttribute("borrow_map", borrow_map);
			request().setAttribute("user_invest_map", user_invest_map);
			return "zqjkxy";
		}
		
		return null;
	}
	/**
	 * 根据信息类型查询信息详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String qureygetMessageBytypeId() {
		Integer typeId = request.getInt("typeId", -1);
		try {
			Map<String, String> map = null;
			map = publicModelService.getMessageByTypeId(typeId);
			JSONUtils.printObject(map);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 分页查询团队介绍信息
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DataException
	 */
	public String listTeamPage() throws SQLException, DataException {
		try {
			teamList = publicModelService.findListTeam();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public BorrowManageService getBorrowManageService() {
		return borrowManageService;
	}

	public void setBorrowManageService(BorrowManageService borrowManageService) {
		this.borrowManageService = borrowManageService;
	}

	public FinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setAssignmentDebtService(
			AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}
	
}
