package com.sp2p.task;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.google.zxing.common.detector.MathUtils;
import com.sp2p.dao.UserDao;
import com.sp2p.dao.InvestDao;
import com.sp2p.constants.IAmountConstants;
import com.sp2p.constants.IFundConstants;
import com.sp2p.constants.IInformTemplateConstants;
import com.shove.Convert;
import com.shove.base.BaseService;
import com.shove.data.DataException;
import com.shove.data.DataSet;
import com.shove.data.dao.Database;
import com.shove.data.dao.MySQL;
import com.shove.util.BeanMapUtils;
import com.shove.util.UtilDate;
import com.shove.vo.PageBean;
import com.sp2p.constants.IConstants;
import com.sp2p.dao.AccountUsersDao;
import com.sp2p.dao.BorrowDao;
import com.sp2p.dao.FinanceDao;
import com.sp2p.dao.FrontMyPaymentDao;
import com.sp2p.dao.FundRecordDao;
import com.sp2p.dao.RepayMentDao;
import com.sp2p.dao.admin.BorrowManageDao;
import com.sp2p.database.Dao.Functions;
import com.sp2p.database.Dao.Procedures;
import com.sp2p.service.AssignmentDebtService;
import com.sp2p.service.PayTreasureService;
import com.sp2p.service.SelectedService;
import com.sp2p.service.UserIntegralService;
import com.sp2p.service.UserService;
import com.sp2p.util.AmountUtil;
import com.sp2p.util.DateUtil;
import com.sp2p.util.WebUtil;

/**
 * @ClassName: JobTaskService.java
 * @Author: gang.lv
 * @Date: 2013-4-11 上午11:14:41
 * @Copyright: 2013 www.emis.com Inc. All rights reserved.
 * @Version: V1.0.1
 * @Descrb: 定时任务处理服务
 */
public class JobTaskService extends BaseService {
	public static Log log = LogFactory.getLog(JobTaskService.class);

	private JobTaskDao jobTaskDao;
	private SelectedService selectedService;
	private UserService userService;
	private UserIntegralService userIntegralService;
	private FinanceDao financeDao;
	private AccountUsersDao   accountUsersDao;
	private BorrowManageDao  borrowManageDao;
	private InvestDao investDao;
	private BorrowDao  borrowDao;
	private RepayMentDao repayMentDao;
	private FrontMyPaymentDao frontpayDao;
	private FundRecordDao fundRecordDao;
	private UserDao userDao;
	private AssignmentDebtService assignmentDebtService;
	private PayTreasureService payTreasureService;
	
	
	
	

	public PayTreasureService getPayTreasureService() {
		return payTreasureService;
	}

	public void setPayTreasureService(PayTreasureService payTreasureService) {
		this.payTreasureService = payTreasureService;
	}

	public AccountUsersDao getAccountUsersDao() {
		return accountUsersDao;
	}

	public BorrowManageDao getBorrowManageDao() {
		return borrowManageDao;
	}

	public InvestDao getInvestDao() {
		return investDao;
	}

	public BorrowDao getBorrowDao() {
		return borrowDao;
	}

	public RepayMentDao getRepayMentDao() {
		return repayMentDao;
	}

	public FrontMyPaymentDao getFrontpayDao() {
		return frontpayDao;
	}

	public FundRecordDao getFundRecordDao() {
		return fundRecordDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public AssignmentDebtService getAssignmentDebtService() {
		return assignmentDebtService;
	}

	public void setRepayMentDao(RepayMentDao repayMentDao) {
		this.repayMentDao = repayMentDao;
	}

	public void setFundRecordDao(FundRecordDao fundRecordDao) {
		this.fundRecordDao = fundRecordDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAssignmentDebtService(AssignmentDebtService assignmentDebtService) {
		this.assignmentDebtService = assignmentDebtService;
	}

	public void setFrontpayDao(FrontMyPaymentDao frontpayDao) {
		this.frontpayDao = frontpayDao;
	}

	public void setInvestDao(InvestDao investDao) {
		this.investDao = investDao;
	}
	
	public void setBorrowDao(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}

	public SelectedService getSelectedService() {
		return selectedService;
	}

	public void setAccountUsersDao(AccountUsersDao accountUsersDao) {
		this.accountUsersDao = accountUsersDao;
	}

	public void setBorrowManageDao(BorrowManageDao borrowManageDao) {
		this.borrowManageDao = borrowManageDao;
	}

	public void setSelectedService(SelectedService selectedService) {
		this.selectedService = selectedService;
	}

	public JobTaskDao getJobTaskDao() {
		return jobTaskDao;
	}

	public void setJobTaskDao(JobTaskDao jobTaskDao) {
		this.jobTaskDao = jobTaskDao;
	}

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserIntegralService getUserIntegralService() {
		return userIntegralService;
	}

	public void setUserIntegralService(UserIntegralService userIntegralService) {
		this.userIntegralService = userIntegralService;
	}

	/**
	 * VIP会费处理
	 * @throws Exception 
	 */
	public void autoDeductedVIPFee() throws Exception{
		Connection  conn = MySQL.getConnection();
		DataSet  ds = new DataSet();
		List<Object>  outParameterValues = new ArrayList<Object>();
		List<Map<String, Object>> VipUserList = new ArrayList<Map<String, Object>>();
		try { 
			Procedures.p_auto_pastvipfee(conn, ds, outParameterValues, 0, "");
			//查询出没有扣会员费的会员
			VipUserList = userDao.queryVipUser(conn);
			Procedures.p_auto_firstvip(conn, ds, outParameterValues, 0, "");
			if(VipUserList != null){
				for(Map<String, Object> map : VipUserList){
					long userId = Convert.strToLong(map.get("id")+"", -1);
					userService.updateSign(conn, userId);//更换校验码
					map = null;
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		}finally{
			conn.close();
		}
		
		
	}
	/**
	 * add by houli 当用户首次成为vip并且有会费余额，则立即进行会费扣除（用户申请会员时引用）
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public Long beToVip(Long userId,Map<String,Object> platformCostMap) throws SQLException{
		Connection conn = Database.getConnection();
		Map<String,String> noticeMap = new HashMap<String, String>();
		Map<String,String> map = null;
		Map<String,String> vipFeeMap = null;
		Map<String,String> userAmountMap = null;
		Map<String,String> userSumMap = null;
		String username = "";
		//模板
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try {
			map = jobTaskDao.queryFirstVipById(conn,userId);
			if(map == null || map.size() <=0){//用户不是首次成为vip
				return -1L;
			}
	    	double vipFee = Convert.strToDouble(platformCostMap
    				.get(IAmountConstants.VIP_FEE_RATE)+ "", 0);
        	//如果代扣的VIP用户账户有钱就进行VIP会费扣除
        	userAmountMap = jobTaskDao.queryUserHasVipFee(conn,userId,vipFee);
        	if(userAmountMap != null && userAmountMap.size() > 0 && vipFee > 0){
        		username = userAmountMap.get("userAmountMap")+"";
        		//扣除VIP会费
        		jobTaskDao.deductedUserVipFee(conn,userId,vipFee); 
        		//添加VIP会费扣除记录
        		jobTaskDao.addVipFeeRecord(conn,userId,vipFee);
        		//查询投资后的账户金额
        		userSumMap = financeDao.queryUserAmountAfterHander(conn,userId);
				if(userSumMap == null){userSumMap = new HashMap<String,String>();}
				double usableSum = Convert.strToDouble(userSumMap.get("usableSum")+"", 0);
				double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")+"", 0);
				double forPI = Convert.strToDouble(userSumMap.get("forPI")+"", 0);
				//添加资金流动记录
				fundRecordDao.addFundRecord(conn, userId,"VIP会员续费",vipFee,usableSum,freezeSum,forPI,-1,"扣除VIP会员费",0.0,vipFee,-1,-1,804,0.0);
				
				//站内信
				String informTemplate = informTemplateMap.get(IInformTemplateConstants.VIP_SUCCESS_XU)+"";
				informTemplate = informTemplate.replace("vipFee", vipFee+"");
				//短信
				String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_VIP_SUCCESS_XU)+"";
				s_informTemplate = s_informTemplate.replace("username", username);
				s_informTemplate = s_informTemplate.replace("vipFee", vipFee+"");
				//邮件
				String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_VIP_SUCCESS_XU)+"";
				e_informTemplate = e_informTemplate.replace("vipFee", vipFee+"");
				 noticeMap.put("mail",informTemplate);//站内信
				 noticeMap.put("note", s_informTemplate);//短信
				 noticeMap.put("email",e_informTemplate);//邮件
        		//发送通知
            	selectedService.sendNoticeMSG(conn, userId, "VIP会员成功续费", noticeMap, IConstants.NOTICE_MODE_5);
            	userService.updateSign(conn, userId);//更换校验码
        	}
        	conn.commit();
        	return 1L;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
		    conn = null;
			noticeMap = null;
			map = null;
			vipFeeMap = null;
			userAmountMap = null;
			userSumMap = null;
			System.gc();
		}
		return -1L;
	}

	/**
	 * @MethodName: pr_inviteFriendsReward
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午01:29:55
	 * @Return:
	 * @Descb: 好友奖励发放
	 * @Throws:
	 */
	public void inviteFriendsReward() throws SQLException, DataException {
	Connection conn = Database.getConnection();
		Map<String,String> userSumMap = null;
		Map<String, String> riskMap = null;
		Map<String,String> noticeMap = new HashMap<String, String>();
		List<Map<String,Object>> friendsRewardList = null;
		Map<String,String> fmap = null;
		long id = -1;
		//奖励金额
		double money = 0;
		//用户Id
		long userId = -1;
		String username = "";
		//模板
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		
		try {
			//清除异常数据(奖励的用户为小于1的用户)
			jobTaskDao.clearExceptionDate(conn);
			//处理好友奖励
			friendsRewardList = jobTaskDao.queryFriendsReward(conn);
			for(Map<String,Object> vipMap:friendsRewardList){
				id = Convert.strToLong(vipMap.get("id")+"", -1);
				money = Convert.strToLong(vipMap.get("money")+"", -1);
				userId = Convert.strToLong(vipMap.get("userId")+"", -1);
				username = vipMap.get("username")+"";
				
				//查询该条奖励对应的用户是否交了VIP会费
	         	  List<Map<String,Object>> map = jobTaskDao.queryFriendsvip2(conn,id);
	         	  //若交了会费推荐人进行奖励
	         	  if(map!=null&&map.size()>0){
	         		  
	         	// 查询风险保障金余额
         	   riskMap = jobTaskDao.queryRiskBalance(conn);
         	   if(riskMap == null){riskMap = new HashMap<String, String>();}
         	   double riskBalance = Convert.strToDouble(riskMap
						.get("riskBalance")+ "", 0);
         	   //扣除风险保障金
         	   jobTaskDao.spendingRiskAmount(conn, riskBalance, money, userId, -1, "好友邀请奖励");
         	   //更新已奖励状态为已奖励
         	   jobTaskDao.updateRewardStatus(conn,id,userId);
         	   //邀请奖励发给邀请人
         	   jobTaskDao.addUserAmount(conn,userId,money);
			   //查询投资后的账户金额
				userSumMap = financeDao.queryUserAmountAfterHander(conn,userId);
				if(userSumMap == null){userSumMap = new HashMap<String,String>();}
				double usableSum = Convert.strToDouble(userSumMap.get("usableSum")+"", 0);
				double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")+"", 0);
				double forPI = Convert.strToDouble(userSumMap.get("forPI")+"", 0);
				//发送通知
				//--------------add by houli
				fmap = jobTaskDao.queryFriendInfo(conn,id,userId);
				String friendName = "";
				if(fmap != null){
					friendName = fmap.get("username");
				}
				//添加资金流动记录
				fundRecordDao.addFundRecord(conn, userId,"好友邀请奖励",money,usableSum,freezeSum,forPI,-1,"您邀请的用户<a href='userMeg.do?id="+userId+"' target='_blank'>【"+friendName+"】</a>已成为VIP会员,在此奖励￥"+money+"元,再接再厉!",money,0.0,-1,-1,251,0.0);
        		
				//------------------/
				//模板通知
				//站内信
				 String	informTemplate = informTemplateMap.get(IInformTemplateConstants.GOOD_INVITATION)+"";
				 informTemplate  = informTemplate.replace("friendName", friendName);
				 informTemplate  = informTemplate.replace("money", money+"");
				 //短信
				 String	s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_GOOD_INVITATION)+"";
				 s_informTemplate  = s_informTemplate.replace("friendName", friendName);
				 s_informTemplate  = s_informTemplate.replace("username", username);
				 s_informTemplate  = s_informTemplate.replace("money", money+"");
				 //邮件
				 String	e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_GOOD_INVITATION)+"";
				 e_informTemplate  = e_informTemplate.replace("friendName", friendName);
				 e_informTemplate  = e_informTemplate.replace("money", money+"");
				 noticeMap.put("mail",informTemplate); //站内信
			     noticeMap.put("email",e_informTemplate);//邮件
				 noticeMap.put("note", s_informTemplate);//短信
            	 selectedService.sendNoticeMSG(conn, userId, "好友邀请奖励", noticeMap, IConstants.NOTICE_MODE_5);
            	 
         		 }//回收对象
	         	 userService.updateSign(conn,userId);//更换校验码
				 vipMap = null;
         	  
			}
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}  finally {
			conn.close();
		    conn = null;
			userSumMap = null;
			riskMap = null;
			noticeMap = null;
			friendsRewardList = null;
			fmap = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: updateOverDueRepayment
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-11 下午02:42:50
	 * @Return:
	 * @Descb: 更新逾期的还款
	 * @Throws:
	 */
	public void updateOverDueRepayment() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		DecimalFormat df= new DecimalFormat("#0.00");
		List<Map<String,Object>> overDueRepaymentList = null;
		String date = sf.format(new Date());
		long borrowId = -1L;
		long id = -1;
		//应还本金
		double stillPrincipal = 0;
		//应还利息
		double stillInterest = 0;
		// 逾期罚息
		double lateFee = 0;
		//执行天数
		int executeDay = 0;
		//逾期天数
		int overdueDay = 0;
		//借款方式
		int borrowWay = 0;
		//执行时间
		String executeTime ="";
		//-- 7 - 9
		//查询借款信息得到借款时插入的平台收费标准
		Map<String,String> map = new HashMap<String, String>();
		String feelog = "";
		Map<String,Double> feeMap = new HashMap<String, Double>();
		//得到收费标准的说明信息
		String feestate = "";
		Map<String,String> feestateMap = new HashMap<String, String>();
		//--end
		double overdueFeeRate = 0;
		try {
			overDueRepaymentList = jobTaskDao.queryOverDueRepayment(conn,date);
			for(Map<String,Object> overDueMap:overDueRepaymentList){
				id = Convert.strToLong(overDueMap.get("id")+"", -1);
				borrowId = Convert.strToLong(overDueMap.get("borrowId")+"", 0);
				stillPrincipal = Convert.strToDouble(overDueMap.get("stillPrincipal")+"", 0);
				stillInterest = Convert.strToDouble(overDueMap.get("stillInterest")+"", 0);
				executeTime = overDueMap.get("executeTime")+"";
				
				overdueDay = Convert.strToInt(overDueMap.get("overdueDay")+"", 0);
				map = borrowManageDao.queBorrowInfo(conn, borrowId);
				if(map!=null){
					//得到收费标准的json代码
					feelog = Convert.strToStr(map.get("feelog"), "");
					feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
			
					overdueFeeRate =Convert.strToDouble(  feeMap.get(IAmountConstants.OVERDUE_FEE_RATE)+"",0);
					borrowWay = Convert.strToInt(map.get("borrowWay"), 0);
				}else{
					overdueFeeRate = 0;
				}
				//不符合条件的情况，将逾期天数重置为0
				if(overdueDay < 0){
					overdueDay = 0;
				}
				if (borrowWay ==6) {//流转标不计算罚息
					overdueDay = 0;
				}
				// 计算罚息=本息
				lateFee = (stillPrincipal + stillInterest)*overdueDay* overdueFeeRate;
				lateFee = Double.valueOf(df.format(lateFee));
				// 更新逾期还款
				if(overdueDay >0){
					jobTaskDao.updateOverDueRepayment(conn, id, lateFee,overdueDay,date);
				}
				// 回收对象
				overDueMap = null;
				map = null;
				feeMap = null;
				feestateMap = null;
				//提交任务
				conn.commit();
			}
		}catch(Exception e){
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
		    sf = null;
			df= null;
			overDueRepaymentList = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: autoDeductedXLFee
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-12 下午05:05:24
	 * @Return:
	 * @Descb: 自动扣除学历认证费用
	 * @Throws:
	 */
	public void autoDeductedXLFee() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		Map<String,String> userSumMap = null;
		Map<String,String> noticeMap = new HashMap<String, String>();
		List<Map<String,Object>> xlFeeList = null;
		//扣费id
		long costId = -1;
		//用户Id
		long userId = -1;
		//学历认证费用
		double freeEducation = 0;
		//用户可用金额
		double useableAmount = 0;
		String username = "";
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try {
			xlFeeList = jobTaskDao.queryDeductedXLFee(conn);
			for(Map<String,Object> xlFeeMap:xlFeeList){
				costId = Convert.strToLong(xlFeeMap.get("id")+"", -1);
				userId = Convert.strToLong(xlFeeMap.get("userId")+"", 0);
				freeEducation = Convert.strToDouble(xlFeeMap.get("freeEducation")+"", 0);
				username = xlFeeMap.get("username")+"";
				useableAmount = Convert.strToDouble(xlFeeMap.get("usableSum")+"", 0);
				//如果扣费费用和用户有钱就进行扣费处理
				if(freeEducation > 0 && useableAmount > freeEducation){
			        //更新费用扣除状态
				    jobTaskDao.updateXLFeeStatus(conn,costId);	
				    //扣除学历认证费用				
				    jobTaskDao.deductedXLFee(conn,userId,freeEducation);
				    //查询投资后的账户金额
				    userSumMap = financeDao.queryUserAmountAfterHander(conn,userId);
				    if(userSumMap == null){userSumMap = new HashMap<String,String>();}
				    double usableSum = Convert.strToDouble(userSumMap.get("usableSum")+"", 0);
				    double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")+"", 0);
				    double forPI = Convert.strToDouble(userSumMap.get("forPI")+"", 0);
				    //添加资金流动记录
				    fundRecordDao.addFundRecord(conn, userId,"学历认证费",freeEducation,usableSum,freezeSum,forPI,-1,"管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元",0.0,freeEducation,-1,-1,802,0.0);
				    
				    //消息模版  学历认证成功 
				    //站内信
				    String	informTemplate = informTemplateMap.get(IInformTemplateConstants.APPROVE_EDU_SUCCESS)+"";
					 informTemplate  = informTemplate.replace("freeEducation", freeEducation+"");
					 //短信
					 String	s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_APPROVE_EDU_SUCCESS)+"";
					 s_informTemplate  = s_informTemplate.replace("username", username);
					 s_informTemplate  = s_informTemplate.replace("freeEducation", freeEducation+"");
					//邮件
					 String	e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_APPROVE_EDU_SUCCESS)+"";
					 e_informTemplate  = e_informTemplate.replace("freeEducation", freeEducation+"");
					 //站内信
					 noticeMap.put("mail",informTemplate);
					 //邮件
					 noticeMap.put("email",e_informTemplate);
					 //短信
					 noticeMap.put("note",s_informTemplate);
					/* //站内信
				    noticeMap.put("mail", "管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元");
				    //邮件
				    noticeMap.put("email","管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元");
				    //短信
				    noticeMap.put("note",  "尊敬的"+username+":\n    管理员以对您的学历进行了审核，本次产生的费用为：￥"+freeEducation+"元");
*/
				    //发送通知
				    selectedService.sendNoticeMSG(conn, userId, "学历认证费", noticeMap, IConstants.NOTICE_MODE_5);
				    userService.updateSign(conn, userId);//更换校验码
				    //回收对象
				    xlFeeMap = null;
				}
			}
			
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			userSumMap = null;
			noticeMap = null;
			xlFeeList = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: autoBid
	 * @Param: JobTaskService
	 * @Author: gang.lv
	 * @Date: 2013-4-14 下午11:50:51
	 * @Return:
	 * @Descb: 自动投标
	 * @Throws:
	 */
	public void autoBid() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		List<Map<String, Object>> biderList = jobTaskDao.queryAllBider(conn);
		List<Map<String, Object>> userList = null;
		Map<String, String> userParam = null;
		Map<String, String> bider = null;
		Map<String, String> userAmount = null;
		Map<String, String> borrowOwer = null;
		List<Map<String, Object>> userBiderList = null;
		Map<String,String> borrowInfoMap = null;
		Map<String,String> userAmountMap = null;
		Map<String,String> Usermap = null;
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Map<String,String> noticeMap = new HashMap<String, String>();
		Map<String,String> hasInvestMap = null;
		Map<String, String> borrowMap = null;
		// 利率开始
		double rateStart = 0;
		// 利率结束
		double rateEnd = 0;
		// 借款期限开始
		int deadLineStart = 0;
		// 借款期限结束
		int deadLineEnd = 0;
		// 信用等级开始
		int creditStart = 0;
		// 信用等级结束
		int creditEnd = 0;
		// 用户Id
		long userId = -1;
		// 借款id
		int borrowId = -1;
		// 投标金额
		double bidAmount = 0;
		// 保留金额
		double remandAmount = 0;
		// 借款金额
		double borrowAmount = 0;
		// 已投资金额
		double hasAmount = 0;
		// 可用金额
		double usableAmount = 0;
		//借款方式数组字符串
		String borrowWay = "";
		//借款方式
		String way= "";
		//是否为天标
		int isDayThe = 1;
		//月利率
		double monthRate =0;
		//发布者
		long publisher = -1;
		//借款期限
		int deadline = 0;
		//借款标题
		String borrowTitle = "";
		int score = 0;
		Integer preScore = null;
		long returnId = -1;
		String username = "";
		String basePath = WebUtil.getWebPath();
		try {
			// 遍历所有的符合条件进度低于95%的招标中的借款
			for (Map<String, Object> biderMap : biderList) {
				borrowId = Convert.strToInt(biderMap.get("id") + "", -1);
				borrowAmount = Convert.strToDouble(biderMap.get("borrowAmount")+ "", 0);
				hasAmount = Convert.strToDouble(biderMap.get("hasInvestAmount")+ "", 0);
				way = biderMap.get("borrowWay")+"";
				isDayThe = Convert.strToInt(biderMap.get("isDayThe") + "", -1);
				// 查询符合自动投标的用户
				userList = jobTaskDao.queryAutoBidUser(conn);
				// 所有符合条件的用户排队对每条借款进行自动投标
				for (Map<String, Object> userMap : userList) {
					userId = Convert.strToInt(userMap.get("userId") + "", -1);
					// 如果该借款是发布者的标,则发布者不能投标,用户自动排队到后面
					borrowOwer = jobTaskDao.queryBorrowOwer(conn, borrowId,userId);
					if (borrowOwer != null) {
						// 是发布者的标,退出本次投标,用户自动排队到后面
						jobTaskDao.updateUserAutoBidTime(conn, userId);
					} else {
						userParam = jobTaskDao.queryUserBidParam(conn, userId);
						// 当用户设置了自动投标参数
						if (userParam != null) {
							userBiderList = jobTaskDao.queryUserBider(conn,borrowId, userId);
							// 用户已经投标的标的不能再自动投标
							if (userBiderList != null && userBiderList.size() > 0) {
								// 已经投标,用户自动排队到后面
								jobTaskDao.updateUserAutoBidTime(conn, userId);
							} else {
								rateStart = Convert.strToDouble(userParam.get("rateStart")+ "", 0);
								rateEnd = Convert.strToDouble(userParam.get("rateEnd")+ "", 0);
								deadLineStart = Convert.strToInt(userParam.get("deadlineStart")+ "", 0);
								deadLineEnd = Convert.strToInt(userParam.get("deadlineEnd")+ "", 0);
								creditStart = Convert.strToInt(userParam.get("creditStart")+ "", 0);
								creditEnd = Convert.strToInt(userParam.get("creditEnd")+ "", 0);
								bidAmount = Convert.strToDouble(userParam.get("bidAmount")+ "", 0);
								remandAmount = Convert.strToDouble(userParam.get("remandAmount")+ "", 0);
								borrowWay = userParam.get("borrowWay");
								//用户设置的借款类型
								if(borrowWay.contains(way)){
									// 根据用户投标参数获取投标的标的
									bider = jobTaskDao.queryBiderByParam(conn,
											rateStart, rateEnd, deadLineStart,
											deadLineEnd, creditStart, creditEnd,
											borrowId,isDayThe);
									// 找到了符合自动投标的标的
									if (bider != null) {
										// 计算投标金额
										bidAmount = calculateBidAmount(bidAmount,borrowAmount, hasAmount);
										if (bidAmount > 0) {
											// 查询用户可用余额是否足够
											userAmount = jobTaskDao.queryUserAmount(conn,remandAmount, userId);
											if (userAmount != null) {
												// 获取用户减掉预留金额后的可用金额
												usableAmount = Convert.strToDouble(userAmount.get("usableSum")+ "", 0);
												if (usableAmount >= bidAmount&& usableAmount > 0) {
													// 查询借款的状态,借款未达到95%且处于招标中
													borrowMap = jobTaskDao.queryBorrow(conn,borrowId);
													if (borrowMap != null) {
														borrowAmount = Convert.strToDouble(borrowMap.get("borrowAmount")+ "",0);
														hasAmount = Convert.strToDouble(borrowMap.get("hasInvestAmount")+ "",0);
														bidAmount = calculateBidAmount(bidAmount,borrowAmount,hasAmount);
														// 满足投标条件,进行扣费处理
														//#查询借款的基础信息
														borrowInfoMap = jobTaskDao.queryBorrowInfo(conn,borrowId);
														if(borrowInfoMap !=null){
															monthRate = Convert.strToDouble(borrowInfoMap.get("monthRate")+"", 0);
															deadline = Convert.strToInt(borrowInfoMap.get("deadline")+"", 0);
															borrowTitle = borrowInfoMap.get("borrowTitle")+"";
															publisher = Convert.strToLong(borrowInfoMap.get("publisher")+"", 0);
															int version = Convert.strToInt(borrowInfoMap.get("version") + "",0);
//															notice.append("您于["+sf.format(new Date())+"]自动投标了借款[["+borrowTitle+"]],投资金额为：￥"+bidAmount);
//															   notice.append("元");
															Map<String,Object> informTemplateMap = (Map<String, Object>)
															ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
												            //站内信
															String informTemplate = informTemplateMap.get(IInformTemplateConstants.TENDER)+"";
															informTemplate = informTemplate.replace("title", borrowTitle+"");
															informTemplate = informTemplate.replace("[voluntarily]", "自动");
															informTemplate = informTemplate.replace("date",sf.format(new Date()));
															informTemplate = informTemplate.replace("investAmount", bidAmount+"");
															//短信
															String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_TENDER)+"";
															s_informTemplate = s_informTemplate.replace("username", username);
															s_informTemplate = s_informTemplate.replace("title", borrowTitle+"");
															s_informTemplate = s_informTemplate.replace("[voluntarily]", "自动");
															s_informTemplate = s_informTemplate.replace("date",sf.format(new Date()));
															s_informTemplate = s_informTemplate.replace("investAmount", bidAmount+"");
															//邮件
															String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_TENDER)+"";
															e_informTemplate = e_informTemplate.replace("title", borrowTitle+"");
															e_informTemplate = e_informTemplate.replace("[voluntarily]", "自动");
															e_informTemplate = e_informTemplate.replace("date",sf.format(new Date()));
															e_informTemplate = e_informTemplate.replace("investAmount", bidAmount+"");
															
															 noticeMap.put("mail", informTemplate);//站内信
															 noticeMap.put("email",e_informTemplate);//邮件
															 noticeMap.put("note", s_informTemplate);//短信
															//消息模版voluntarily
													         /*  //站内信
													           noticeMap.put("mail", "["+sf.format(new Date())+"] 您自动投标了借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+" target=_blank>"+borrowTitle+"</a>],冻结投标金额：￥"+bidAmount+"元");
													           //邮件
													           noticeMap.put("email", "["+sf.format(new Date())+"] 您自动投标了借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>],冻结投标金额：￥"+bidAmount+"元");
													           //短信
													           noticeMap.put("note",  "尊敬的"+username+":\n    ["+sf.format(new Date())+"] 您自动投标了借款["+borrowTitle+"],冻结投标金额：￥"+bidAmount+"元");
*/
													           // 更新借款信息中的已投资总额和数量
																returnId = financeDao.updateBorrowStatus(conn,
																		bidAmount,0, borrowId,version);
																if(returnId > 0){
													               //查询已投资总额是否小于等于借款总额，否则是无效投标
															       hasInvestMap = financeDao.queryHasInvest(conn,borrowId);
															       if(hasInvestMap !=null && hasInvestMap.size() > 0){
																      if(deadline!=0){
																	     score = Integer.parseInt(new java.text.DecimalFormat("#0").format((bidAmount/100)*deadline));//小数部分四舍五入
																      }
																      Usermap = userService.queryUserById(userId);
																      preScore = Convert.strToInt(Usermap.get("rating"),-1);//查找用户的之前的信用积分
																      if(preScore!=-1&&deadline!=0&&score!=0){
																	     //添加投标所得积分
																	     userIntegralService.UpdateFinnceRating(userId, score, preScore);
																      }
																      //添加投资记录
																      returnId = financeDao.addInvest(conn,bidAmount,bidAmount,monthRate,userId,userId,borrowId,deadline,2);
																      //投资人投资成功资金冻结
																      returnId = financeDao.freezeUserAmount(conn,bidAmount,userId);
																      //投标状态已经达到满标条件,更新为满标
																      returnId = financeDao.updateFullBorrow(conn, borrowId);
																      //添加用户动态
																      String cotent = "自动投标了借款<a href='"+basePath+"/financeDetail.do?id="+borrowId+"' target='_blank'>"
																           +borrowTitle+"</a>";
																      returnId = financeDao.addUserDynamic(conn,userId,cotent);
																      //查询投资后的账户金额
																      userAmountMap = financeDao.queryUserAmountAfterHander(conn,userId);
																      if(userAmountMap == null){userAmountMap = new HashMap<String,String>();}
																      double usableSum = Convert.strToDouble(userAmountMap.get("usableSum")+"", 0);
																      double freezeSum = Convert.strToDouble(userAmountMap.get("freezeSum")+"", 0);
																      double forPI = Convert.strToDouble(userAmountMap.get("forPI")+"", 0);
																      //添加资金流动记录
																      returnId = fundRecordDao.addFundRecord(conn, userId,"投标金额冻结",bidAmount,usableSum,freezeSum,forPI,publisher,"投标"+"<a href='"+basePath+"/financeDetail.do?id="+borrowId+"' target='_blank'>",
																    		  0.0,bidAmount,borrowId,-1,654,0.0);
																       
																        //更新用户自动投标的标的记录
																      returnId = financeDao.addAutoBidRecord(conn,userId,borrowId);
																      if(returnId > 0){
																	     //发送通知
																		selectedService.sendNoticeMSG(conn,userId,"理财投资报告",noticeMap,IConstants.NOTICE_MODE_5);
																      }
															     }else{
																   returnId = -1;
															     }
															}else{
																returnId = -1;
															}
														}
														// 投标完成,用户自动排队到后面
														jobTaskDao.updateUserAutoBidTime(conn,userId);
													} else {
														// 借款状态已改变,投标失败,用户自动排队到后面
														jobTaskDao.updateUserAutoBidTime(
																		conn,
																		userId);
													}
												} else {
													// 用户的可用余额不够投标，投标失败,用户自动排队到后面
													jobTaskDao.updateUserAutoBidTime(
																	conn, userId);
												}
											} else {
												// 用户的可用余额不足，投标失败,用户自动排队到后面
												jobTaskDao.updateUserAutoBidTime(
														conn, userId);
											}
										} else {
											// 投标金额为0,投标失败,用户自动排队到后面
											jobTaskDao.updateUserAutoBidTime(conn,
													userId);
										}
									} else {
										// 没有找到符合用的标的,用户自动排队到后面
										jobTaskDao.updateUserAutoBidTime(conn,
												userId);
									}
								}else{
									// 不符合设置的借款类型,用户自动排队到后面
									jobTaskDao.updateUserAutoBidTime(
													conn,
													userId);
								}
								
							}
						}
					}
					//回收对象
					userMap = null;
					if (returnId < 0) {
						conn.rollback();
					}
					userService.updateSign(conn, userId);//更换校验码
				}
				//回收对象
				biderMap = null;
			}
			if (returnId < 0) {
				conn.rollback();
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
			biderList = null;
			userList = null;
			userParam = null;
			bider = null;
			userAmount = null;
			borrowOwer = null;
			userBiderList = null;
			borrowInfoMap = null;
			userAmountMap = null;
			Usermap = null;
			sf = null;
			noticeMap = null;
			hasInvestMap = null;
			borrowMap = null;
			System.gc();
		}
	}

	/**
	 * @MethodName: calculateBidAmount
	 * @Param: JobTaskDao
	 * @Author: gang.lv
	 * @Date: 2013-4-15 上午10:34:39
	 * @Return:
	 * @Descb: 计算最后投标金额,(扣除保留金额)
	 * @Throws:
	 */
	private double calculateBidAmount(double bidAmount, double borrowAmount,
			double hasAmount) {
		double maxBidAmount = borrowAmount * 0.2;
		double schedule = hasAmount / borrowAmount;
		double invesAmount = 0;
		if (schedule < 0.9500) {
			while (bidAmount > maxBidAmount) {
				bidAmount = bidAmount - 50;
			}

			do {
				invesAmount = hasAmount + bidAmount;
				schedule = invesAmount / borrowAmount;
				if (schedule > 0.9500) {
					bidAmount = bidAmount - 50;
				}
			} while (schedule > 0.9500);
		}
		return bidAmount;
	}
	/**   
	 * @MethodName: updateOverDueInvestRepayment  
	 * @Param: JobTaskService  
	 * @Author: gang.lv
	 * @Date: 2013-6-2 下午10:25:20
	 * @Return:    
	 * @Descb: 更新逾期投资还款记录
	 * @Throws:
	*/
	public void updateOverDueInvestRepayment() throws SQLException, DataException {
		Connection conn = Database.getConnection();
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate._dtShort);
		DecimalFormat df= new DecimalFormat("#0.00");
		List<Map<String,Object>> overDueRepaymentList = null;
		String date = sf.format(UtilDate.getYesterDay());
		long id = -1;
		//应还本金
		double stillPrincipal = 0;
		//应还利息
		double stillInterest = 0;
		//逾期罚息
		double lateFee = 0;
		long repayId = 0;
		int lateDay= 0;
		int isLate= 0 ;
		int borrowWay= 0;
		//-- 7 - 9
		//查询借款信息得到借款时插入的平台收费标准
		Map<String,String> map = null;
		String feelog = "";
		Map<String,Double> feeMap = null;
		//得到收费标准的说明信息
		String feestate = "";
		Map<String,String> feestateMap = null;
		//--end
		double overdueFeeRate =0;
		try {
			overDueRepaymentList = jobTaskDao.queryOverDueInvestRepayment(conn,date);
			for(Map<String,Object> overDueMap:overDueRepaymentList){
				long borrowId = Convert.strToLong(overDueMap.get("borrowId")+"", 0);
				map = borrowManageDao.queryBorrowInfo(conn, borrowId);
			 	//得到收费标准的json代码
				feelog = Convert.strToStr(map.get("feelog"), "");
				feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
				feestate = Convert.strToStr(map.get("feestate"), "");
				feestateMap = (Map<String,String>)JSONObject.toBean(JSONObject.fromObject(feestate),HashMap.class);
				overdueFeeRate = Convert.strToDouble( feeMap.get(IAmountConstants.OVERDUE_FEE_RATE)+"",0);
					
			    //计算罚息
				id = Convert.strToLong(overDueMap.get("id")+"", -1);
				repayId = Convert.strToLong(overDueMap.get("repayId")+"", -1);
				lateDay = Convert.strToInt(overDueMap.get("lateDay")+"", 0);
				isLate = Convert.strToInt(overDueMap.get("isLate")+"", 1);
				stillPrincipal = Convert.strToDouble(overDueMap.get("recivedPrincipal")+"", 0);
				stillInterest = Convert.strToDouble(overDueMap.get("recivedInterest")+"", 0);
				lateFee = (stillPrincipal+stillInterest)*overdueFeeRate*lateDay*0.5;
			    lateFee = Double.valueOf(df.format(lateFee));
			    borrowWay = Convert.strToInt(overDueMap.get("borrowWay")+"", 0);
			    //更新逾期还款
			    // 6 为流转标 不处理
			    if ( borrowWay !=6) { 
			    	jobTaskDao.updateOverDueInvestRepayment(conn,id,repayId,lateFee,lateDay,isLate);
				}
			    //回收对象
			    overDueMap = null;
				map = null;
				feeMap = null;
				feestateMap = null;
			}
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.equals(e);
			conn.rollback();
		} finally {
			conn.close();
			conn = null;
		    sf = null;
			df= null;
			overDueRepaymentList = null;
			System.gc();
		}
	}
	
	/**   
	 * @throws SQLException 
	 * @throws DataException 
	 * @throws SQLException 
	 * @MethodName: AutomaticPayment  
	 * @Param: JobTaskService  
	 * @Author: gang.lv
	 * @Date: 2013-5-21 下午11:36:05
	 * @Return:    
	 * @Descb: 自动还款
	 * @Throws:
	*/
	@SuppressWarnings("unchecked")
	public void automaticPayment() throws SQLException{
		double investFeeRate = 0;
		//处理流转标状态为认购中或回购中的借款
		Connection conn = MySQL.getConnection();
		List<Map<String,Object>> circulationList = null;
		List<Map<String,Object>> investList=null;
		Map<String,String> noticeMap = new HashMap<String, String>();
		DecimalFormat df = new DecimalFormat("#0.00");
		SimpleDateFormat sf = new SimpleDateFormat(UtilDate.simple);
		Map<String,String> userSumMap = null;
		//借款人
		long borrower = -1;
		//投资人
		long investor = -1;
		//借款id
		long borrowId = -1;
		//投资id
		long investId = -1;
		//返回值
		long returnId = -1;
		//应收本金
		double recivedPrincipal =0;
		//应收利息
		double recievedInterest = 0;
		//应收本息
		double recivedPI = 0;
		//借款标题
		String borrowTitle = "";
		//实得还款金额
		double hasSum = 0;
		//投资管理费
		double mFee = 0;
		//投资人名称
		String username = "";
		//回购期数
		int hasRepoNumber = 0;
		//最小流转单位
		double smallestFlowUnit =0;
		//实际投资金额
		double realAmount = 0;
		int repayId = -1;
		long oriInvestor =  -1;  //原始投资人
		String basePath = WebUtil.getWebPath();
		//查询借款信息得到借款时插入的平台收费标准
		String feelog = "";
		Map<String,Double> feeMap = null;
		Map<String,String> flowMap = null;
		//模板
		Map<String,Object> informTemplateMap = (Map<String, Object>)
		ContextLoader.getCurrentWebApplicationContext().getServletContext().getAttribute(IInformTemplateConstants.INFORM_TEMPLATE_APPLICATION);
		try{
			//查询状态为认购中或回购中的借款或者借款人还完的借款
			circulationList = borrowDao.queryCirculationBorrow(conn);
			for(Map<String,Object> circulationMap:circulationList){
								borrowId = Convert.strToLong(circulationMap.get("id")+"", -1);
								borrowTitle = circulationMap.get("borrowTitle")+"";
								borrower = Convert.strToLong(circulationMap.get("publisher")+"", -1);
								smallestFlowUnit = Convert.strToDouble(circulationMap.get("smallestFlowUnit")+"", 0);
								repayId = Convert.strToInt(circulationMap.get("repayId")+"", -1);
								//得到收费标准的json代码
								feelog= circulationMap.get("feelog")+"";
								feeMap = (Map<String,Double>)JSONObject.toBean(JSONObject.fromObject(feelog),HashMap.class);
								investFeeRate= Convert.strToDouble(feeMap.get(IAmountConstants.INVEST_FEE_RATE)+"",0);
								//查询流转标借款待收款投资人
								investList = investDao.queryInvestorByBorrowId(conn, borrowId);
								for(Map<String,Object> investMap:investList){
									investId = Convert.strToLong(investMap.get("id")+"", -1);
									investor = Convert.strToLong(investMap.get("investor")+"", -1);
									oriInvestor = Convert.strToLong(investMap.get("oriInvestor")+"", -1);
									username = investMap.get("username")+"";
									realAmount = Convert.strToDouble(investMap.get("realAmount")+"", 0);
									recivedPrincipal = Convert.strToDouble(investMap.get("recivedPrincipal")+"", 0);
									recievedInterest = Convert.strToDouble(investMap.get("recievedInterest")+"", 0);
									recivedPI = recivedPrincipal+recievedInterest;
									mFee = recievedInterest*investFeeRate;
						            mFee = Double.valueOf(df.format(mFee));
						            hasSum = recivedPI - mFee;
						            //查询流转标还款记录ID
						            flowMap = investDao.queryFlowMap(conn, investId);
						            if(flowMap==null){  //为空则该条投资没有还
						            	//更新流转标还款状态为已还款
										returnId = investDao.updateInvestRepayStatus(conn,investId);
										//更新t_invest_repayment的状态为已还款
										returnId =  investDao.updateInvestRepaymentStatus(conn, investId);
										//更新流转标投资 辅助表
										returnId = investDao.addFlowRepayment(conn, investId);
										//更新t_invest借款人已收本息
										returnId = investDao.updateInvesthasPrincipalAndhasInterest(conn, investId, recivedPrincipal, recievedInterest);
										//更新invest
										investDao.updateInvestRepayment(conn, repayId, investId, investor, 0, oriInvestor, 2);
										if(returnId >0){
											//更新借款已回购份数
											hasRepoNumber = (int) (realAmount/smallestFlowUnit);
											borrowDao.updateHasRepoNumber(conn,borrowId,hasRepoNumber);
											String informTemplate = informTemplateMap.get(IInformTemplateConstants.RECOVER_ADVANCE_SUCCESS)+"";
											informTemplate = informTemplate.replace("title", borrowTitle+"");
											informTemplate = informTemplate.replace("[repayPeriod]", "1/1");
											informTemplate = informTemplate.replace("[paymentModeStr]", "");
											informTemplate = informTemplate.replace("[recivedSum]", recivedPI+"");
											informTemplate = informTemplate.replace("[hasP]", recivedPrincipal+"");
											informTemplate = informTemplate.replace("[hasI]", recievedInterest+"");
											informTemplate = informTemplate.replace("[mFee]", mFee+"");
											informTemplate = informTemplate.replace("[msFee]",df.format(hasSum)+"");
											informTemplate = informTemplate.replace("[hasLFI]", "0");
											informTemplate = informTemplate.replace("[paymentModeStr]","还款方式：[一次性还款]<br/>");
											//短信
											String s_informTemplate = informTemplateMap.get(IInformTemplateConstants.S_RECOVER_ADVANCE_SUCCESS)+"";
											s_informTemplate = s_informTemplate.replace("username", username);
											s_informTemplate = s_informTemplate.replace("title", borrowTitle+"");
											s_informTemplate = s_informTemplate.replace("[repayPeriod]", "1/1");
											s_informTemplate = s_informTemplate.replace("[paymentModeStr]", "");
											s_informTemplate = s_informTemplate.replace("[recivedSum]", recivedPI+"");
											s_informTemplate = s_informTemplate.replace("[hasP]", recivedPrincipal+"");
											s_informTemplate = s_informTemplate.replace("[hasI]", recievedInterest+"");
											s_informTemplate = s_informTemplate.replace("[mFee]", mFee+"");
											s_informTemplate = s_informTemplate.replace("[msFee]", df.format(hasSum)+"");
											s_informTemplate = s_informTemplate.replace("[paymentModeStr]","还款方式：[一次性还款]<br/>");
											s_informTemplate = s_informTemplate.replace("[hasLFI]", "0");
											//邮件
											String e_informTemplate = informTemplateMap.get(IInformTemplateConstants.E_RECOVER_ADVANCE_SUCCESS)+"";
											e_informTemplate = e_informTemplate.replace("title", borrowTitle+"");
											e_informTemplate = e_informTemplate.replace("[repayPeriod]", "1/1");
											e_informTemplate = e_informTemplate.replace("[paymentModeStr]", "");
											e_informTemplate = e_informTemplate.replace("[recivedSum]", recivedPI+"");
											e_informTemplate = e_informTemplate.replace("[hasP]", recivedPrincipal+"");
											e_informTemplate = e_informTemplate.replace("[hasI]", recievedInterest+"");
											e_informTemplate = e_informTemplate.replace("[mFee]", mFee+"");
											e_informTemplate = e_informTemplate.replace("[msFee]", df.format(hasSum)+"");
											e_informTemplate = e_informTemplate.replace("[paymentModeStr]","还款方式：[一次性还款]<br/>");
											e_informTemplate = e_informTemplate.replace("[hasLFI]","0");
											
											 noticeMap.put("mail", informTemplate);//站内信
											 noticeMap.put("email",e_informTemplate);//邮件
											 noticeMap.put("note", s_informTemplate);//短信
											//消息模版
								            //站内信
								          /*  noticeMap.put("mail", "您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>],已经完成.<br/>"+
								                  "本期应得总额：￥"+recivedPI+",其中本金部分为："+recivedPrincipal+"元,利息部分："+recievedInterest+"元<br/>扣除投资管理费：￥"+mFee+"元"+"<br/>实得总额：￥"+hasSum+"元");
								            //邮件
								            noticeMap.put("email","您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>],已经完成.<br/>"+
								                    "本期应得总额：￥"+recivedPI+",其中本金部分为："+recivedPrincipal+"元,利息部分："+recievedInterest+"元<br/>扣除投资管理费：￥"+mFee+"元"+"<br/>实得总额：￥"+hasSum+"元");
								            //短信
								            noticeMap.put("note",  "尊敬的"+username+":\n    ["+sf.format(new Date())+"] 您投资的借款["+borrowTitle+"]已经完成.\n"+
								            		"本期应得总额：￥"+recivedPI+",其中本金部分为："+recivedPrincipal+"元,利息部分："+recievedInterest+"元\n扣除投资管理费：￥"+mFee+"元"+"<br/>实得总额：￥"+hasSum+"元");
								            	*/
								             // 查询风险保障金余额
											 Map<String, String> riskMap = frontpayDao.queryRiskBalance(conn);
											 double riskBalance = Convert.strToDouble(riskMap.get("riskBalance")+ "", 0);
											 //投资手续费累加到风险保障金
											 returnId=frontpayDao.addRiskAmount(conn, riskBalance,mFee, investor, -1,"投资管理费累加风险保障金");
											 //关闭自动投标
											 returnId=frontpayDao.closeAutoBid(conn,investor);
											 //投资人帐号资金收入
											 returnId=userDao.addUserUsableAmount(conn,recivedPI,investor);
											 // 查询投资后的账户金额
											 userSumMap = userDao.queryUserAmountAfterHander(conn, investor);
											if(userSumMap == null){userSumMap = new HashMap<String,String>();}
											double usableSum = Convert.strToDouble(userSumMap.get("usableSum") + "", 0);
											double freezeSum = Convert.strToDouble(userSumMap.get("freezeSum") + "", 0);
											double forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
											// 添加资金流动记录
											returnId = fundRecordDao.addFundRecord(conn, investor, "用户还款资金收入",
													recivedPI, usableSum, freezeSum, forPI, borrower,"您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>]",recivedPI,0.0,borrowId,repayId,151,0.0);
										
											// 投资人扣除投资管理费
											returnId = userDao.deducteUserUsableAmount(conn, mFee,investor);
											// 查询投资后的账户金额
											userSumMap = userDao.queryUserAmountAfterHander(conn, investor);
											usableSum = Convert.strToDouble(userSumMap.get("usableSum")
															+ "", 0);
											freezeSum = Convert.strToDouble(userSumMap.get("freezeSum")
															+ "", 0);
											forPI = Convert.strToDouble(userSumMap.get("forPI") + "", 0);
												// 添加资金流动记录
											returnId = fundRecordDao.addFundRecord(conn, investor,"投资收款扣除管理费", mFee, usableSum, freezeSum, forPI,-1,"您投资的借款[<a href="+basePath+"/financeDetail.do?id="+borrowId+">"+borrowTitle+"</a>]",0.0,mFee,borrowId,repayId,651,0.0);
											if(returnId > 0){
												 //给投资人发送消息
												 selectedService.sendNoticeMSG(conn, investor, "用户还款资金收入报告", noticeMap, IConstants.NOTICE_MODE_1);
												 //自动结束提前还款正在竞拍中的债权
												 assignmentDebtService.preRepayment(conn,repayId);
											}
											
									}else{
										returnId = -1;
									}
									//回收对象
									investMap = null;
									if(returnId <=0){
										conn.rollback();
									}
									
								}
								userService.updateSign(conn, investId);//更换校验码
							}
				userService.updateSign(conn, borrower);//更换校验码
				//回收对象
				circulationMap = null;
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			conn.rollback();
		}finally{
			conn.close();
			circulationList = null;
			investList=null;
			noticeMap = null;
			df = null;
			sf = null;
			userSumMap = null;
			df = null;
            System.gc();
		}
	}
	
	/**
	 * 发送短信 模板
	 * @throws Exception 
	 */
	public void sendtoTemple() throws Exception{
		Connection conn = MySQL.getConnection();
		Map<String,String>  noticeMap = new HashMap<String, String>();
		List<Map<String,Object>> tmplList = new ArrayList<Map<String,Object>>();
		PageBean<Map<String,Object>>  pagebean = new PageBean<Map<String,Object>>();
		long investor = -1;
		String title = "";
		int id = 0;
		try {
			pagebean.setPageSize(150);
			jobTaskDao.queryTmpleAll(conn,pagebean);
			tmplList = pagebean.getPage();
			if (tmplList!=null) {
				for (Map<String, Object> map : pagebean.getPage()) {
					investor = Convert.strToLong(map.get("user_id")+"",-1L);
					title = Convert.strToStr(map.get("sendtitle")+"", "");
					id = Convert.strToInt(map.get("id")+"", -1);
					noticeMap.put("email", Convert.strToStr(map.get("email_info")+"",""));
					noticeMap.put("mail", Convert.strToStr(map.get("mail_info")+"",""));
					noticeMap.put("note", Convert.strToStr(map.get("sms_info")+"",""));
					noticeMap.put("operate_id", Convert.strToStr(map.get("operate_id")+"",""));
					selectedService.sendNoticeMSG(conn, investor,
							title, noticeMap, Convert.strToStr(map.get("s_nid")+"",""));
					
					Functions.f_send_temple(conn,id);
					map = null;
				}
			}
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} catch (DataException e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
			noticeMap = null;
			pagebean = null;
			tmplList = null;
			System.gc();
		}
	}

	public void autoBidProTask() throws Exception {
		checkSign();
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_autobid_task(conn, ds, outParameterValues, IConstants.WEB_URL, -1, "");
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	public void autoBidPro() throws Exception {
		Connection conn = MySQL.getConnection();
		try {
			DataSet ds = new DataSet();
			List<Object> outParameterValues = new ArrayList<Object>();
			Procedures.p_autobid(conn, ds, outParameterValues, IConstants.WEB_URL, -1, "");
			changeSign(conn);//更换校验码方法
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}
	
	private void checkSign() throws Exception {
		Connection conn = MySQL.getConnection();
		long userId = -1;
		try {
			List<Map<String,Object>> autoBidTaskList = new ArrayList<Map<String,Object>>();
			autoBidTaskList = jobTaskDao.queryAutoBidTask(conn);
			if (autoBidTaskList!=null) {
				for (Map<String, Object> map : autoBidTaskList) {
					userId = Convert.strToLong(map.get("userId")+"",-1L);
					boolean re = userService.checkSign(userId);//验证校验码
					if(!re){
						JobTaskDao.updateAuto(conn, userId);
					}
					map = null;
				}
			}
			conn.commit();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}

	/**
	 * 自动投标时修改校验码
	 * @throws Exception
	 */
	private void changeSign(Connection conn) throws Exception{
		long userId = -1;
		List<Map<String,Object>> autoBidTaskList = new ArrayList<Map<String,Object>>();
		autoBidTaskList = jobTaskDao.queryAutoBidTask(conn);
		if (autoBidTaskList!=null) {
			for (Map<String, Object> map : autoBidTaskList) {
				userId = Convert.strToLong(map.get("userId")+"",-1L);
				userService.updateSign(conn, userId);//更换校验码
				map = null;
			}
		}
	}
	
	/**
	 * @throws SQLException 
	 * @throws DataException 
	 * 涨薪宝收益发放
	 * @Title: payTreasure 
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 * @throws
	 */
	public long payTreasureWeeked() throws DataException, SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			//涨薪宝信息
			Map<String, String> payTreasure = payTreasureService.getPayTreasureDao().querypaytreasure(conn, null);
			
			//投资人信息
			List<Map<String, Object>> payInvester = payTreasureService.getPayTreasureDao().queryPayInvest(conn);
			String time = DateUtil.dateToString(new Date());//时间
			
			if (payTreasure==null || payInvester == null) {
				return result;
			}
			
			for (Map<String, Object> map : payInvester) {
				long userId = Convert.strToLong(map.get("userid").toString(), -1L);//用户
				//String allprofit = map.get("allprofit");//总收益
				log.info(map.get("investamount"));
				double investamount = Convert.strToDouble(map.get("investamount").toString(), 0);//总金额
				//发利息
				Map<String, String> profittemp = payTreasureService.getPayTreasureDao().queryPayProfitTemp(conn, userId);
				

				if (profittemp!=null && profittemp.size()>0) {//有利息
					double profit = MapUtils.getDoubleValue(profittemp, "profit");//上次计算的收益
					//核算收益是否正确。中途是否有转入转出的过程,当前金额大于等于上次计算收益金额，直接发放收益，核算正确。否则重新计算收益，根据当前的金额发放
					double lastAmount = MapUtils.getDoubleValue(profittemp, "amount");//上次计算收益时的金额
					Date date = new Date();
					//周六跟周日如果或者未开启份额确认，有转出，需要重新计算
					String confirm = payTreasure.get("confirm");
					if (DateUtil.getWeekDayString(date)==1||DateUtil.getWeekDayString(date)==7 || "1".equals(confirm)) {
						if (investamount <lastAmount) {
							profit = calcProfit(profittemp.get("bearingid"), MapUtils.getDoubleValue(profittemp, "rate"),  MapUtils.getDoubleValue(profittemp, "payprofit"), investamount);
						}
					}
					//更新总收益
					result = payTreasureService.getPayTreasureDao().updateAllProfit(conn, profit, userId);
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userid", userId+"");
					paramMap.put("amount", profit+"");
					if (result>0) {
						result = payTreasureService.getPayTreasureDao().addPayProfit(conn, userId, profit, investamount, time);
						if (result>0) {
							//将收益放入总金额
							result = payTreasureService.getPayTreasureDao().updatePayInvest(conn, paramMap, "+");
							paramMap = null;
							if (result<=0) {
								conn.rollback();
								result = -1L;
							}else {
								conn.commit();
							}
						}else {
							conn.rollback();
							result = -1L;
						}
					}else {
						conn.rollback();
						result = -1L;
					}
					//总金额等于当前金额跟利息之和
					investamount+= profit;
				}else {
					result = 1L;//因为没有上次的收益，所以直接算执行成功。
				}
			
				
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}		
		
		return result;
	}
	
	/**
	 * 涨薪宝自动转入
	 * @Title: payTreasureInto 
	 * @param @return
	 * @param @throws SQLException 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 * @throws
	 */
	public void payTreasureInto() throws SQLException{
		try {
			boolean have = payTreasureService.allowAutoIntoPay();
			while (have) {
				payTreasureService.autoIntoPayTreasure();
				have = payTreasureService.allowAutoIntoPay();
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} 
	}
	
	/**
	 * @throws SQLException 
	 * 涨薪宝每日记录
	 * @Title: payTreasureDailyRecord 
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author he
	 * @throws
	 */
	public long payTreasureDailyRecord() throws SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			List<Object> outParameterValues = new ArrayList<Object>();
			DataSet ds = new DataSet();
			Procedures.p_paydailyrecord(conn, ds, outParameterValues);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return result;
	}
	public static void main(String[] args) {
		Date now = new Date();
		String startBeforeTwoDay = "";//开始
		String  endBeforeOneDay="";//截至
		if (DateUtil.getWeekDayString(now)==2) {//如果是周一，周末周日也要去除
			//查询出用户在上一个工作日15点后到当前时间为止的转入跟转出金额
			startBeforeTwoDay = DateUtil.dateToString(DateUtil.dateAddDay(now, -3));
		}else {//只需要延迟一天
			//查询出用户在上一个工作日15点后到当前时间为止的转入跟转出金额
			startBeforeTwoDay = DateUtil.dateToString(DateUtil.dateAddDay(now, -1));
		}
		endBeforeOneDay = DateUtil.dateToString(now);
		System.out.println(startBeforeTwoDay);
		System.out.println(endBeforeOneDay);
	}
	/**
	 * @throws SQLException 
	 * @throws DataException 
	 * 涨薪宝确定份额
	 * @Title: payTreasure 
	 * @param @return 设定文件 
	 * @return long 返回类型 
	 * @author hjh
	 * @throws
	 */
	public long payTreasure() throws DataException, SQLException{
		Connection conn = MySQL.getConnection();
		long result = -1L;
		try {
			//涨薪宝信息
			Map<String, String> payTreasure = payTreasureService.getPayTreasureDao().querypaytreasure(conn, null);
			//投资人信息
			List<Map<String, Object>> payInvester = payTreasureService.getPayTreasureDao().queryPayInvest(conn);
			
			if (payTreasure==null || payInvester==null) {
				return result;
			}else {
				//未开启份额确认
				String confirm = payTreasure.get("confirm");
				if ("1".equals(confirm)) {
					return result;
				}
			}
			String time = DateUtil.dateToString(new Date());//时间
			
			//计息方式
			String type = payTreasure.get("bearingid");
			double rate =MapUtils.getDoubleValue(payTreasure, "rate");//利率
			double payProfit = MapUtils.getDoubleValue(payTreasure, "payprofit");//万份收益
			
			for (Map<String, Object> map : payInvester) {
				long userId = Convert.strToLong(map.get("userid").toString(), -1L);//用户
				//String allprofit = map.get("allprofit");//总收益
				log.info(map.get("investamount"));
				double investamount = Convert.strToDouble(map.get("investamount").toString(), 0);//总金额
				//上次确定的收益
				Map<String, String> profittemp = payTreasureService.getPayTreasureDao().queryPayProfitTemp(conn, userId);
				if (investamount==0) {//没有转入金额的用户忽略
					continue;
				}else{
					
					double outamount = 0;
					//double intamount = 0;
					String lastTime = null;
					if (profittemp!=null&&profittemp.size()>0) {//修复针对多次转入转出的漏洞
						outamount = profittemp==null?0:Convert.strToDouble(profittemp.get("outamount"), 0.0);
						//intamount = profittemp==null?0:Convert.strToDouble(profittemp.get("intamount"), 0.0);
						lastTime = profittemp.get("time");
					}
					
					Date now = new Date();
					String startBeforeTwoDay = "";//开始
					String  endBeforeOneDay="";//截至
					
					//查询出用户在上一个工作日15点后到当前时间为止的转入跟转出金额
					if (StringUtils.isNotBlank(lastTime)) {
						startBeforeTwoDay = lastTime;
					}else {
						if (DateUtil.getWeekDayString(now)==2) {//如果是周一，周末周日也要去除
							startBeforeTwoDay = DateUtil.dateToString(DateUtil.dateAddDay(now, -3));
						}else {//只需要延迟一天
							startBeforeTwoDay = DateUtil.dateToString(DateUtil.dateAddDay(now, -1));
						}
					}
					endBeforeOneDay = DateUtil.dateToString(now);
					//转入
					Map<String, String> intMoney = payTreasureService.payInvestRecordNotPage(userId, startBeforeTwoDay, endBeforeOneDay, "1");
					//转出
					Map<String, String> outMoney = payTreasureService.payInvestRecordNotPage(userId, startBeforeTwoDay, endBeforeOneDay, "2");
					double dIntMoney = intMoney==null?0:Convert.strToDouble(intMoney.get("handamount"), 0.0);
					double dOutMoney = outMoney==null?0:Convert.strToDouble(outMoney.get("handamount"), 0.0);
					
					if (StringUtils.isNotBlank(lastTime)) {//非第一次确定份额
						if (dIntMoney-dOutMoney>0) {//15点后转入的不计算收益
							//确定份额总金额 = 当前总金额 - (首次转出的金额  -(总的转入-总的转出))
							investamount = investamount - (dIntMoney-dOutMoney) + outamount;
						}else {
							//确定份额总金额 = 当前总金额 - (首次转出的金额  -(总的转入-总的转出))
							investamount = investamount + (outamount - (dIntMoney-dOutMoney));
						}
					}else {//首次确定份额
						investamount = investamount - (dIntMoney-dOutMoney);
					}
					
					//计算利息
					double profit = calcProfit(type, rate, payProfit, investamount);//收益
					if (profit>=0) {//收益大于等于0计算
						boolean isupdate = false;
						if (profittemp!=null&&profittemp.size()>0) {
							isupdate = true;
						}else {
							profittemp = new HashMap<String, String>();
							profittemp.put("userid", userId+"");
						}
						profittemp.put("profit", profit+"");
						profittemp.put("amount", investamount+"");
						profittemp.put("bearingid", type);
						profittemp.put("rate", rate+"");
						profittemp.put("payprofit", payProfit+"");
						profittemp.put("time", time);
						result = payTreasureService.getPayTreasureDao().updateOrAddPayProfitTemp(conn, profittemp,isupdate);
						if (result>0) {
							conn.commit();
						}else {
							conn.rollback();
						}
					}
					profittemp = null;
				}
				
			}
			
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}		
		
		return result;
	}
	
	//计算收益
	public double calcProfit(String type,double rate,double payProfit,double amount) throws Exception{
		double profit = 0;
		if (StringUtils.isBlank(type)) {
			throw new Exception("收益计算方式有误");
		}
		if ("1".equals(type)) {//万份收益
			profit = amount/10000*payProfit;
		}else if("2".equals(type)){//年利率计算
			profit = rate/(365*100)*amount;
		}
		return AmountUtil.mathRound(profit);
	}
	
	
	/*public  void autoRelaseLimit() throws Exception{
		Connection conn = Database.getConnection();
		try{
			
			List<Map<String, String>> limitList = jobTaskDao.queryAllLimitUser(conn);
			for (Map<String, String> userMap : limitList){
				String lastDate= userMap.get("lastDate");
				int id=Convert.strToInt(userMap.get("id"), -1);
				SimpleDateFormat simple =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date  d=simple.parse(lastDate);
				if(new Date().getTime()-d.getTime()>3*60*60*1000){
					//取消用户限制登录将isLoginLimit设置为1和loginErrorCount设置为0
					//resetUserState(Connection conn,int loginErrorCount,int isLoginLimit,long userId)
					userDao.resetUserState( conn,0,1, id);
					 conn.commit();
				}
			}
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			conn.close();
		}
	}*/
		
}
