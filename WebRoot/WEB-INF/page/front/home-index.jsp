<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  	<c:set value="账户中心" var="title"  scope="request" />
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link rel="stylesheet" href="css/usercenter.css" type="text/css"  media="screen"/>
    <link rel="stylesheet" href="framework/poshytip-1.2/src/tip-twitter/tip-twitter.css" type="text/css"  media="screen"/>
  </head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>
<div class="wrap mb20">
<div class="w950 clearfix">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li class="active">账户总览</li>
  </ol>
  <div class="center_top">
      <div class="center_photo fl">
         <a href="pastpicture.mht" id="uploadHeadImg" class="fancybox.ajax"><shove:img defaulImg="images/ueser_03.jpg"  src="${homeMap.personalHead}${homeMap.head_jpg }"  width="108" height="108"></shove:img></a> 
      </div>
      <!--center_top_mes start-->
      <div class="center_top_mes fl">
        <div class="center_top_mes1">
		  <font class="center_top_user fl mr20 color6A6A6A">您好，${homeMap.username}</font>
          <a href="javascript:;" class="fl" id="changeUser"></a>
          <span class="fl">绑定手机：<shove:utils text="${userMap.mobilePhone}" start="3" end="6" /></span>
            
		 <!--<div class="dj fl"></div>-->

        </div>
        <div class="center_top_mes2 mt5 clearfix">
            
          <div class="center_top_mes2_l fl ">安全等级：<span class="text-danger">${safeMap.words}</span>&nbsp;&nbsp;
              
             <s:if test="%{#request.safeMap.number!=100}"> <a href="securityCent.mht">提升</a></s:if>
              
          </div>
          <div class="center_top_mes2_r fl pl10">
                 <s:if test="#session.user.userType==1">
                    <a href="securityCent.mht?isOn=3" <s:if test="%{#request.userMap.authCardName == 0}"> class="blue_ico01" </s:if> <s:else> class="gray_ico01" </s:else> title="实名认证"></a>
                 </s:if>
                 
                 <a href="securityCent.mht?isOn=4"  <s:if test="%{#request.userMap.phone != ''}"> class="blue_ico02" </s:if> <s:else> class="gray_ico02" </s:else> 
						       title="绑定手机"></a>
                 <a href="securityCent.mht?isOn=5" <s:if test="%{#request.userMap.email != ''}"> class="blue_ico03" </s:if> <s:else> class="gray_ico03" </s:else> 
                  			   title="绑定邮箱"></a>
                 <a href="mybank.mht" <s:if test="%{#request.userMap.cardNo != ''}"> class="blue_ico04" </s:if> <s:else> class="gray_ico04" </s:else> 
                 			   title="绑定银行卡"></a>
                 <a href="bevip.mht" <s:if test="%{#request.userMap.vipStatus == 2}"> class="blue_ico05" </s:if> <s:else> class="gray_ico05" </s:else>  
                 				title="申请VIP"></a>
                 				
                 <a href="queryAboutInfo.mht?msg=106&js=1"   class="a0a0a0 " style="text-decoration: underline;" target="_blank">查看名词解释</a>
            <%--  福利 <div id="welfare"><a class="center_fl fl" style="margin-top:-10px; margin-left:10px;" target="_blank" href="/activity/welfare"></a></div>--%>
          </div>
        </div>
      </div>
      <!--center_top_mes end-->
      
      <!--center_top_money start-->
      <div class="center_top_money fr">
        <div class="fl">
          <table cellpadding="0" cellspacing="0" class="center_top_money_table" width="380">
            <tbody><tr height="28">
              <td class="">账户总额<em title="账户总额：可用余额  + 待收本金  + 待收利息 + 冻结金额  + 红包金额  + 涨薪宝余额" class="helplist"></em>：<span class="f15 bold" style="color: #DE4A4A;"><s:if test="#request.accmountStatisMap.accountSum !=''">${accmountStatisMap.accountSum}</s:if><s:else>0</s:else></span>元</td>
              <td class="">可用余额：<span class="f15 bold" style="color: #CF3A3B;"><s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if><s:else>0</s:else></span>元</td>
            </tr>
            <tr height="28">
              <td class="color6A6A6A">待收本金：<s:if test="#request.accmountStatisMap.forPayPrincipal !=''">${accmountStatisMap.forPayPrincipal}</s:if><s:else>0</s:else> 元</td>
              <td class="color6A6A6A">待收利息：<s:if test="#request.accmountStatisMap.forPayInterest !=''">${accmountStatisMap.forPayInterest}</s:if><s:else>0</s:else> 元</td>
            </tr>
            <tr height="28">
              <td  class="color6A6A6A">冻结金额：<s:if test="#request.accmountStatisMap.freezeAmount !=''">${accmountStatisMap.freezeAmount}</s:if><s:else>0</s:else> 元</td>
              <td class="color6A6A6A">红包金额：${accmountStatisMap.userPresrent}元</td>
            </tr>
            <tr height="28">
            	<td class="color6A6A6A">涨薪宝余额：<span class="f15 " style="color: #CF3A3B;">${accmountStatisMap.zxbacount}</span>元</td>
              	<td class="color6A6A6A">涨薪宝昨日收益：<s:if test="#request.accmountStatisMap.zxbprofit!=null && #request.accmountStatisMap.zxbprofit>0"><span class="f15 " style="color: #CF3A3B;">${accmountStatisMap.zxbprofit}</span>元</s:if><s:else>暂无收益</s:else></td>
            </tr>
          </tbody></table>
        </div>
        <div class="fr w100" style="line-height:3">
          <a href="rechargeInit.mht" class="button w100 mt10" style="height: 25px;line-height: 25px;">充值</a>
          <a href="withdrawLoad.mht" class="button w100 mt10" style="height: 25px;line-height: 25px;">提现</a>
          <a href="myPayTreasure.mht" class="blue w100 mt10" style="height: 25px;line-height: 25px;background-image: url('images/hot.gif');background-repeat: no-repeat;background-position: right;padding-right: 30px;">进入涨薪宝</a>
        </div>
      </div>
      <!--center_top_money end-->
    </div>
  <div  class="clearfix"></div>
  <div class="main_center mt10">
     
     <!-- 引用我的帐号主页左边栏 -->
     <%@include file="/include/left.jsp" %>
     
     
     <div class="center_right fr w750">
		  <div class="tj f14">
			  <div class="fl w050 bdr tz"><i></i>累计投资<em title="累计投资：还款中  + 已还完的投资项目总额 " class="helplist"></em>：<span><s:if test="#request.moneySum.investSum !='' && #request.moneySum.investSum > 0" >${moneySum.investSum}</s:if><s:else>0</s:else></span>元</div>
			  <div class="fr w050 sy"><i></i>累计收益<em title="累计收益：已到账的投资项目利息总额 " class="helplist"></em>：<span>${accmountStatisMap.earnSum}</span>元</div>
		  </div>
		  
		  <div class="clearfix ShdSocialStatus">
				<div class="navTabStyle clearfix">
					<div class="paymentsNav bt1 beforeOneTab fl" id="repaymentDate">
						<div class="isNoClick currSocialStatus">
							<label class="dataListed"><i></i>本月回款日历</label>
						</div>
					</div>
					<div class="fr" style="padding: 5px;border: 1px solid #DE4A4A;border-radius: 3px;"><a href="calctool.mht"><img src="images/calculator.png" title="三好资本" /><span class="pl5 pr10">收益计算器</span></a></div>
				</div>
				<ul class="clearfix foreshowList" id="foreshowList" style="display: none;">
					<li class="foreshowListTitle"><span class="span1">回款日期</span><span class="span2">项目</span><span class="span3">回款金额</span></li>
				</ul>
				<div class="clearfix dateContainer" id="dateContainer">
					<div class="dateWdate clearfix" id="dateWdate"></div>
					<div class="systemTime" id="systemTime"></div>
					<div class="repaymentList">
						<div class="repaymentTimeTotal"></div>
						<ul class="repaymentUl" id="repaymentUl">
							<li class="th"><span class="repaymentSpan1">项目</span><span class="repaymentSpan2">回款金额</span></li>
						</ul>
					</div>
				</div>
			</div>
			
			
			<s:set name="counts" value="#request.pageNum"/>
			
			<div class="clearfix ShdSocialStatus mt15">
				<div class="navTabStyle clearfix">
					<div class="paymentsNav bt1 beforeOneTab" id="repaymentDate">
						<div class="isNoClick currSocialStatus">
							<a href="queryFundrecordInit.mht"><label class="dataListed"><i></i>资金记录</label></a>
						</div>
					</div>
				</div>
				
				<div  id="moneyDataInfo" class="dateContainer  pr10 pl10 pt0 pb0" >
					
				</div>
			</div>
			
        <%--  <div id="moneyDataInfo" class="uesr_border recommend clearfix"> 
             <div class="pub_tb bottom"><i></i>最近三天充值记录</div>
             <div class="tab_b">
                 <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="leve_titbj">                                                    
                        <td width="31%">充值单号</td>
                        <td width="21%">充值金额(元)</td>
                        <td width="13%">充值类型</td>
                        <td width="27%">充值时间</td>
                        <td width="8%">状态</td>
                      </tr>
                      
                      <s:if test="#request.investMoneyList.size>0">
     					<s:iterator value="pageBean.page" var="finance" >
	     				  <tr>
	                        <td><s:property value="#finance.id" default="0"/> </td>
	                        <td><s:property value="#finance.rechargeMoney" default="0"/> </td>
	                        <td><s:property value="#finance.rechargeType" default="充值"/> </td>
	                        <td><s:date name="#finance.rechargeTime" format="yyyy-MM-dd HH:mm:ss"/> </td>
	                        <td> <s:property value="#finance.result" default="成功"/> </td>
	                        </tr>
                      </s:iterator>
       				</s:if>
       				
                    </table>
              </div>
         </div>
     </div> --%>
     
    <%-- <div class="fr w765 hidden">
          <div class="personal clearfix">
             <div class="pic w100 fl">
               <p class="uesr_border">
               <shove:img defaulImg="images/ueser_03.jpg" src="${homeMap.personalHead}${homeMap.head_jpg }"  width="108" height="108"></shove:img></p>
               <p class="tc mt5"><a href="#"  onclick="pastpictur()">更换头像</a></p>
             </div>
             <div class="w340 ml20 fl">
                <div class="f20">欢迎您，<span class="pink">${homeMap.username}</span></div>
                <div class="dengji"><span class="fl">安全等级：</span><div class="progress_bg"><div class="progress" style="width:75%;"></div></div> 75%</div>
                <div class="mt30">
                 
                 <s:if test="#session.user.userType==1">
                    <a href="securityCent.mht?isOn=3" <s:if test="%{#request.userMap.authCardName == 0}"> class="blue_ico01" </s:if> <s:else> class="gray_ico01" </s:else> title="实名认证"></a>
                 </s:if>
                 
                 <a href="securityCent.mht?isOn=4"  <s:if test="%{#request.userMap.phone != ''}"> class="blue_ico02" </s:if> <s:else> class="gray_ico02" </s:else> 
						       title="绑定手机"></a>
                 <a href="securityCent.mht?isOn=5" <s:if test="%{#request.userMap.email != ''}"> class="blue_ico03" </s:if> <s:else> class="gray_ico03" </s:else> 
                  			   title="绑定邮箱"></a>
                 <a href="mybank.mht" <s:if test="%{#request.userMap.cardNo != ''}"> class="blue_ico04" </s:if> <s:else> class="gray_ico04" </s:else> 
                 			   title="绑定银行卡"></a>
                 <a href="bevip.mht" <s:if test="%{#request.userMap.vipStatus == 2}"> class="blue_ico05" </s:if> <s:else> class="gray_ico05" </s:else>  
                 				title="申请VIP"></a>
                 <a href="mailNoticeInit.mht" class="blue_ico06" title="站内信"><i class="uesr_border"><s:if test="#request.homeMap.unReadCount !=''">${homeMap.unReadCount}</s:if><s:else>0</s:else></i></a>
                </div>
             </div>
             <div class="button mt20">
               <a href="rechargeInit.mht" class="ui_btn01 usebtn">充值</a><a href="withdrawLoad.mht" class="ui_btn02 usebtn">提现</a>
             </div>
         </div>
         <div class="user_notice clearfix">
             <div class="s_icon"><img src="images/notice.gif" width="28" height="28" /></div>
             <div class="txtScroll-top">
                <div class="bd">
                    <ul class="infoList">
                    <s:if test="#request.newsList.size>0">
     					<s:iterator value="#request.newsList" var="finance" >
                        <li><a href="frontNewsDetails.mht?id=${finance.id }"><span class="lbt"><s:property value="#finance.title" default="..."/></span><span class="ldt"><s:date name="#finance.publishTime" format="MM-dd"/></span></a></li>	
                        </s:iterator>
                    </s:if>
                    </ul>
                </div>
             </div>
             <div class="fr f16"><a href="queryAboutInfo.mht?msg=100" >更多公告>></a></div>
        </div>
        
        <div class="uesr_border recommend clearfix"> 
             <div class="pub_tb bottom clearfix">
                 <span class="fl"><i></i>账户信息</span>
                 <div class="fr f16"><a href="queryFundrecordInit.mht" class="blue"><span class="balance_img"></span>查看资金记录</a></div>
              </div>   
             <ul class="mt20 mine_order clearfix">
                <li>
                   <div class="listone"><b>账户总额</b></div>
                   <div class="listwo"><s:if test="#request.accmountStatisMap.accountSum !=''">${accmountStatisMap.accountSum}</s:if><s:else>0</s:else><span class="f14">元</span></div>
                </li>
                <li class="img01"></li>
                <li>
                   <div class="listone">可用余额</div>
                   <div class="listhree"><s:if test="#request.accmountStatisMap.usableAmount !=''">${accmountStatisMap.usableAmount}</s:if><s:else>0</s:else><span class="f14">元</span></div>
                </li>
                <li class="img02"></li>
                <li>
                   <div class="listone">冻结金额</div>
                   <div class="listhree"><s:if test="#request.accmountStatisMap.freezeAmount !=''">${accmountStatisMap.freezeAmount}</s:if><s:else>0</s:else><span class="f14">元</span></div>
                </li>
                <li class="img02"></li>
                <li>
                   <div class="listone clearfix">
                     <span class="fl ml20">待收本金</span>
                     <div class="tooltip in_login mt5">
                       <ul>
                           <div class="index_one_name"><a href="#" title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>当前账户内所有待回收的投资的本金总和(不含体验标投资金额)</div>
                           </li>
                       </ul>
                    </div>
                   </div>
                   <div class="listhree"><s:if test="#request.accmountStatisMap.forPayPrincipal !=''">${accmountStatisMap.forPayPrincipal}</s:if><s:else>0</s:else><span class="f14">元</span></div>
                </li>
                <li class="img02"></li>
                <li>
                   <div class="listone clearfix">
                      <span class="fl">待收利息</span>
                      <div class="tooltip in_login mt5">
                       <ul>
                           <div class="index_one_name"><a href="#" title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>当前账户内所有待回收的投资按到期正常还款所计算的预期投资收益总和，含平台服务费和投资体验收益</div>
                           </li>
                       </ul>
                    </div>
                   </div>
                   <div class="listhree"><s:if test="#request.accmountStatisMap.forPayInterest !=''">${accmountStatisMap.forPayInterest}</s:if><s:else>0</s:else><span class="f14">元</span></div>
                </li>
                <li class="img02"></li>
                <li>
                   <div class="listone clearfix">
                      <span class="fl">红包金额</span>
                      <div class="tooltip in_login mt5">
                       <ul>
                           <div class="index_one_name"><a href="#" title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>通过参与活动获得，使用方式参考该期活动规则，活动时间内未使用的红包金额将变成过期金额</div>
                           </li>
                       </ul>
                    </div>
                   </div>
                   <div class="listhree"><s:if test="#request.accmountStatisMap.userPresrent !=''">${accmountStatisMap.userPresrent}</s:if><s:else>0</s:else><span class="f14">元</span></div>
                </li>
             </ul>
             
             <p class="clear"></p>
             <div class="mine_rate clearfix">
                  <div class="ml300 f16">
                      <span class="img03 fl"></span>
                      <span class="fl">已赚利息</span>
                      <div class="tooltip in_login mt5">
                             <ul>
                                 <div class="index_one_name"><a href="#" title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                                 <li>
                                  <div class="sj"></div>
                                  <div>已收投资收益+已收投资体验收益总和</div>
                                 </li>
                             </ul>
                       </div>
                       <span class="fl ml5">:</span>
                       <span class="fl"><s:if test="#request.accmountStatisMap.earnSum !=''">${accmountStatisMap.earnSum}</s:if><s:else>0.00</s:else>元</span>
                    </div>   
             </div>
             <s:if test="#request.accmountStatisMap.userExpire>0">
             <div class="listone clearfix f14 mt15">
                      <span class="fl">过期金额</span>
                      <div class="tooltip in_login">
                       <ul>
                           <div class="index_one_name"><a href="#" title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>过期金额将不能使用，请及时使用红包金额</div>
                           </li>
                       </ul>
                    </div>
                    <div class="listhree fl ml10">: <s:if test="#request.accmountStatisMap.userPresrent !=''">${accmountStatisMap.userExpire}</s:if><s:else>0</s:else><span class="f14">元</span></div>
                   </div>                  
             </s:if>
         </div>
        
        
         <div class="uesr_border recommend clearfix">
            <div class="pub_tb clearfix uesdashed" ><span class="fl"><i></i>投资情况</span><span class="fr f15">
            <s:if test="#request.nextRepayMap.nextRepay != null">最近回款：<a href="homeBorrowRecycleList.mht" class="red">${nextRepayMap.nextRepay}  </a> </s:if></span></div>
            
            <div class="tab_c">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                  <tr>
                    <td width="40%">
                     <span class="fl" title="当前账户内所有待回收的投资的本金总和(不含体验标投资金额)">待收本金</span> 
                      <div class="tooltip in_login">
                       <ul>
                           <div class="index_one_name"><a href="javascript:void(0)"  title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>当前账户内所有待回收的投资的本金总和(不含体验标投资金额)</div>
                           </li>
                       </ul>
                    </div>
                     <span class="fl ml5">:</span> 
                     <span class="fl ml5"><b><s:if test="#request.accmountStatisMap.forPayPrincipal !=''">${accmountStatisMap.forPayPrincipal}</s:if><s:else>0</s:else></b>元</span> 
                    </td>
                    <td rowspan="2" class="f20" width="40%">总投资：<s:if test="#request.moneySum.investSum !='' && #request.moneySum.investSum > 0" >${moneySum.investSum}</s:if><s:else>0</s:else> 元</td>
                    <td rowspan="2" width="20%"><a href="bidlist.mht" class="ui_btn01 usebtn">去投资</a></td>
                  </tr>
                  <tr>
                    <td width="40%">
                     <span class="fl" title="当前账户内所有待回收的投资按到期正常还款所计算的预期投资收益总和，含平台服务费和投资体验收益">待收利息</span> 
                      <div class="tooltip in_login">
                       <ul>
                           <div class="index_one_name"><a href="javascript:void(0)" title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>当前账户内所有待回收的投资按到期正常还款所计算的预期投资收益总和，含平台服务费和投资体验收益</div>
                           </li>
                       </ul>
                    </div>
                     <span class="fl ml5">:</span> 
                     <span class="fl ml5"><b><s:if test="#request.accmountStatisMap.forPayInterest !=''">${accmountStatisMap.forPayInterest}</s:if><s:else>0</s:else></b>元</span> 
                     </td>
                  </tr>                 
             </table>
            </div>
            
			<div class="pub_tb mt10 clearfix uesdashed"><span class="fl"><i></i>借款情况 
        		</span>
        		<s:if test="#request.nextDevtMap.repayDate != null"><span class="fr f15">最近还款：<a href="queryAllDetails.mht" class="red">${nextDevtMap.repayDate}</a> </span></s:if>
        		
           </div>
            <div class="tab_c">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                  <tr>
                    <td width="40%">
                     <span class="fl" title="所有需要偿还的借款本金总和">待还本金 </span> 
                      <div class="tooltip in_login">
                       <ul>
                           <div class="index_one_name"><a href="javascript:void(0)"  title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>所有需要偿还的借款本金总和</div>
                           
                       </ul>
                    </div>
                     <span class="fl ml5">:</span> 
                     <span class="fl ml5"><b><s:if test="#request.accmountStatisMap.forRePayPrincipal !=''">${accmountStatisMap.forRePayPrincipal}</s:if><s:else>0</s:else></b>元</span> 
                    </td>
                    <td rowspan="2" class="f20" width="40%">总借款：<s:if test="#request.moneySum.borrowAmount !=''">${moneySum.borrowAmount}</s:if><s:else>0</s:else> 元</td>
                    <td rowspan="2" width="20%"><a href="queryMyPayingBorrowList.mht" class="ui_btn01 usebtn">去还款</a></td>
                  </tr>
                  <tr>
                    <td width="40%">
                     <span class="fl" title="所有需要偿还的借款利息总和">待还利息</span> 
                      <div class="tooltip in_login">
                       <ul>
                           <div class="index_one_name"><a a href="javascript:void(0)" title="" class="f14"><img src="images/quest_icon.jpg" width="18" height="18" /></a></div>
                           <li>
                            <div class="sj"></div>
                            <div>所有需要偿还的借款利息总和</div>
                           
                       </ul>
                    </div>
                     <span class="fl ml5">:</span> 
                     <span class="fl ml5"><b><s:if test="#request.accmountStatisMap.forRePayInterest !=''">${accmountStatisMap.forRePayInterest}</s:if><s:else>0</s:else></b>元</span> 
                     </td>
                  </tr>                
             </table>
            </div>
            
         </div>
         <div class="uesr_border recommend clearfix"> 
             <div class="pub_tb bottom"><i></i>推荐投资</div>
             	<div class="tab_a">
             		<table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="leve_titbj">                                                                    
                        <td width="38%">贷款标题</td>
                        <td width="14%">年利率</td>
                        <td width="16%">金额</td>
                        <td width="17%">期限</td>
                        <td width="15%">&nbsp;</td>
                      </tr>
                      
                      <s:if test="#request.recommentList.size>0">
     					<s:iterator value="#request.recommentList" var="finance" status="st">
	     				  <tr <s:if test="#st.even == true"> class="tablistgrey"</s:if>>
	                        <td>
	                        <span >
	                        <s:if test="#finance.borrowWay ==1">
								<span class="xin">净</span>
								</s:if>
								<s:elseif test="#finance.borrowWay ==2">
									<span  class="xin">秒</span>
								</s:elseif>
								<s:elseif test="#finance.borrowWay ==3">
								</s:elseif>
								<s:elseif test="#finance.borrowWay ==4">
									<span class="xin">实地</span>
								</s:elseif>
								<s:elseif test="#finance.borrowWay ==5">
									<span class="xin">保</span>
								</s:elseif>
								<s:else>
								</s:else>
	                        </span>
	                        <a href="financeDetail.mht?id=${finance.id}" target="_blank" >
		                      <s:property value="#finance.borrowTitle" default="..."/>
	                        </a>
	                        </td>
	                        <td><s:property value="#finance.annualRate" default="0"/><span class="f12">%</span></td>
	                        <td><s:property value="#finance.borrowAmount" default="0"/><span class="f12"></span></td>
	                        <td><s:property value="#finance.deadline" default="0"/><span class="f12">个月</span></td>
	                        <td> 
		                        <s:if test="%{#finance.borrowStatus == 1}">
					             <div class="usebtn ui_btn03">初审中</div>
						        </s:if>
						         <s:elseif test="%{#finance.borrowStatus == 2}">
						           
						             <s:if test="%{#finance.borrowShow == 2}"><a href="financeInvestInit.mht?id=${finance.id}"  class="usebtn ui_btn03">立即认购</a></s:if>
						         	  <s:else><a href="javascript:void(0)"  onclick="financeInvestInit('${finance.id}')"  class="usebtn ui_btn03">立即投标</a></s:else>				    
						        </s:elseif>
						        <s:elseif test="%{#finance.borrowStatus == 3}">
						            <div class="usebtn ui_btn03">复审中</div>&nbsp;
						        </s:elseif>
						        <s:elseif test="%{#finance.borrowStatus == 4}">
						            <div class="usebtn ui_btn03">
						            <s:if test="%{#finance.borrowShow == 2}">回购中</s:if>
						          	<s:else>已满标</s:else>
						          	</div>&nbsp;
						        </s:elseif>
						        <s:elseif test="%{#finance.borrowStatus == 5}">
						           <div class="butn03">已还完</div>&nbsp;
						        </s:elseif>
						        <s:else>
						           <div class="butn03">流标</div>&nbsp;
						        </s:else>
	                        </td>
	                      </tr>
     					
     					
     					</s:iterator>
       				</s:if>
       				<s:else>
			           <tr><td colspan="5" align="left"> &nbsp;&nbsp; 暂无推荐</td></tr>&nbsp;
			        </s:else>
                   </table>
              </div>
         </div>    
       </div>
    
    </div> --%>  
    
    
  </div>
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript" src="script/index.js"></script>
<script src="My97DatePicker/WdatePicker.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/account.js"></script>
<script type="text/javascript" src="framework/poshytip-1.2/src/jquery.poshytip.min.js"></script>
<script>
var result =0 ;
$(function(){
	//提示语
	$('.helplist').poshytip({
		className: 'tip-twitter',
		alignTo: 'target',
		alignX: 'inner-left',
		offsetX: -60,
		offsetY: 10
	});
	
	//displayDetail(0,1);
	
    //样式选中
    var param = {};
	 param["pageBean.pageNum"] = 1;
	 param['paramMap.pageNum'] = 1;
	 initListInfo(param);
	 
	  $("#uploadHeadImg").fancybox({
		maxWidth	: 800,
		maxHeight	: 600,
		fitToView	: false,
		width		: '40%',
		height		: '70%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	});
});


</script>
<script>
    function financeInvestInit(id){
    	$.post("financeInvestInit.mht","id="+id,function(data){
    		if(data.msg==null){
    			window.location.href="financeInvestInit.mht?id="+id;
    		}else{
    			alert(data.msg);
    		}
    	})
    }
    
	function initListInfo(praData){
		$.dispatchPost("queryFundrecordList.mht",praData,initCallBack);
	}
	
	<%-- function initListInfo(param){
 		$.dispatchPost("money3Days.mht",param,initCallBack);
 	} --%>
 	
 	function initCallBack(data){
		$("#moneyDataInfo").html(data);
		$(".pageDivClass").addClass("hidden");
	}
</script>
</body>
</html>
