<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%-- <link href="css/common.css"  rel="stylesheet" type="text/css" />
<link href="css/user.css"  rel="stylesheet" type="text/css" />

<style type="text/css">
.titleNavn {
background:#f3485a;
color:#FFF;
}
</style> --%>

<div class="pageLeft">
<div class="accountLeftTitle clearfix"><label>我的账户<i></i></label></div>
	<ul class="clearfix accountLeftNav m0"> <%--accouontCurr1 curr --%>
		<li><a href="home.mht" class="accouontIcon1"><i></i>账户总览</a></li>
		<li><a href="homeBorrowRecycleList.mht" class="accouontIcon2 "><i></i>投资管理</a></li>
		<li><a href="rechargeInit.mht" class="accouontIcon4 "><i></i>充值提现</a></li>
		<li><a href="securityCent.mht" class="accouontIcon7 "><i></i>安全中心</a></li>
		<%--<li><a href="/profile/debtProjectList.shtml" class="accouontIcon3 "><i></i>债权转让</a></li>
		<li><a href="/profile/queryMyRedRatePackage.shtml" class="accouontIcon5 "><i></i>我的红包</a></li>
		<li><a href="/profile/mypoints.shtml" class="accouontIcon8 "><i></i>我的积分</a></li> --%>
		<li><a href="mailNoticeInit.mht" class="accouontIcon6 "><i></i>消息中心</a></li>
		<li><a href="friendManagerInit.mht" class="accouontIcon8 "><i></i>友情邀请</a></li>
		<li><a href="myPayTreasure.mht" class="accouontIcon9 "><i></i>涨薪宝<img src="images/hot.gif" class="ml10"/></a></li>
		<li><a href="myFund.mht" class="accouontIcon7 "><i></i>基金</a></li>
		<li><a href="companyEmployeeInit.mht" class="accouontIcon7 "><i></i>员工管理</a></li>
		<%-- <li><a href="companyEmployeeInit.mht" class="accouontIcon7 "><i></i>员工管理</a></li> --%>
	</ul>
</div>
<%--
  <!--右侧导航-->
       <div class="uesr_sidebar fl w220">
         <h3 id="shdHead" class="curr"><a href="home.mht" class="gird_icon01 user_ico">我的三好资本</a></h3>
         <div class="nav">
                <ul>
                    <li>
                        <h2><a href="javascript:void(0)" name="dv1" id="myMoneyMsg" onclick="//displayDetail('0');"><i class="gird_icon02 user_ico"></i>资金管理</a></h2>
                        <div class="navList" >
                            <a href="queryFundrecordInit.mht" id="fundRecordNew"><i></i>资金记录</a>
                            <a href="rechargeInit.mht" id="addMoneyNew"><i></i>充值</a>
                            <a href="withdrawLoad.mht" id="cashNew"><i></i>提现</a>
                        </div>
                    </li>
                    <li>
                        <h2 ><a href="javascript:void(0)" name="dv1" id="myInvestMsg" onclick="//displayDetail('1');"><i class="gird_icon03 user_ico"></i>投资管理</a></h2>
                        <div class="navList">
                            <a href="homeBorrowRecycleList.mht" id="myInvestNew"><i></i>我的投资</a> 
                            <a href="financeExperience.mht" id="investExpeNew"><i></i>投资体验</a>
                            <a href="automaticBidInit.mht" id="autoInvestNew"><i></i>自动投标</a>
                            <a href="financeStatisInit.mht" id="invStatisNew"><i></i>投资统计</a>
                        </div>
                    </li>
                    <li>
                        <h2 ><a href="javascript:void(0)" name="dv1" id="myBorrowMsg" onclick="//displayDetail('2');"><i class="gird_icon04 user_ico"></i>借款管理</a></h2>
                        <div class="navList">
                            <a href="queryMySuccessBorrowList.mht" id="borrowMgrNew"><i></i>还款管理</a>
                            <a href="homeBorrowAuditList.mht" id="publishBorrowNew"><i></i>已发布的借款</a>
                            <a href="loanStatisInit.mht" id="borrowStatisNew"><i></i>借款统计</a>
                        </div>
                    </li>
                    <li>
                        <h2 ><a href="javascript:void(0)" name="dv1" id="memberMsg" onclick="//displayDetail('3');"><i class="gird_icon05 user_ico"></i>会员设置</a></h2>
                        <div class="navList">
                            <a href="owerInformationInit.mht" id="homeNew"><i></i>会员详细信息</a>
                            <a href="userrrjc.mht" id="certifyNew"><i></i>认证信息</a>
                            <a href="securityCent.mht" id="securCenterNew"><i></i>安全中心 </a>
                            <a href="mybank.mht" id="bankCardNew"><i></i>银行卡设置</a>
                            <a href="friendManagerInit.mht" id="friendMgrNew"><i></i>邀请好友</a>
                            <!-- a href="financialManager.mht" id="financialManager"><i></i>理财师申请</a> -->
                        </div>
                    </li>
                    <li>
                        <h2><a href="javascript:void(0)" name="dv1" id="messageCer" onclick="//displayDetail('4');"><i class="gird_icon06 user_ico"></i>消息中心</a></h2>
                        <div class="navList">
                            <a href="mailNoticeInit.mht" id="siteMsg"><i></i>站内信</a>
                            <a href="szform.mht" id="noteSetup"><i></i>通知设置</a>
                        </div>
                    </li>
                </ul>
          </div>
       </div>  --%>
 
<script type="text/javascript">
<%--菜单动态样式 index 菜单索引  css 样式索引--%>
function displayDetail(index,css){
	var addClass = "accouontCurr"+css+" curr";
	$(".pageLeft a:eq("+index+")").addClass(addClass);
}

</script>