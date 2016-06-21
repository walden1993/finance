<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<c:set value="投资详情" var="title" scope="request" />
    <jsp:include page="/include/head.jsp"></jsp:include>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
</head>

<body>
<jsp:include page="/include/top.jsp"></jsp:include>	

<!--主题内容-->
<div class="mainwarp">
  <div class="w950 clearfix">
  <ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="home.mht">我的账户</a></li>
	  <li><a href="/homeBorrowRecycleList.mht">投资记录</a></li>
	  <li class="active">投资详情</li>
  </ol>
       <!--右侧导航-->
       <%@include file="/include/left.jsp" %>
       
       <!--左侧内容-->
       <div class="fr w750 clearfix">
         <div class="uesr_border personal clearfix">
            <div class="pub_tb bottom clearfix red3"><h3>投资详情</h3></div>
            <div class="clearfix gird20_title">
               <span class="fl">投资编号：  ${abean.seq }</span>
               <span class="fr">状态：  <span class="red">
               <s:if test="#request.abean.borrowStatus ==1">等待资料</s:if>
		        <s:elseif test="#request.abean.borrowStatus ==2"> 招标中</s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus ==3 ">已满标 </s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus ==4 ">还款中 </s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus ==5 "> 已还完 </s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus == 6 ">失败</s:elseif>
		    	<s:else>${abean.borrowStatus}</s:else>
		    	</span></span>
            </div>
            
            <div class="gird20_title">
              <div class="nav_pub_tb clearfix">项目信息</div>
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_d">
                      <tr class="blod">                                                                    
                        <td>项目名称</td>
                        <td>借款总金额</td>
                        <td>年化利率</td>
                        <td>借款期限</td>
                        <td>还款方式</td>
                        <td>状态</td>
                      </tr>
                      <tr>
                        <td><a href="financeDetail.mht?id=${abean.borrowId}" target="_blank">${abean.borrowTitle }</a></td>
                        <td>${abean.borrowAmount }</td>
                        <td>${abean.annualRate }%</td>
                        <td>${abean.deadline }个月</td>
                        <td><s:if test="#request.abean.paymentMode == 1">按月分期还款</s:if>
		        <s:elseif test="#request.abean.paymentMode == 2">按月付息,到期还本</s:elseif>
		        <s:elseif test="#request.abean.paymentMode == 3">秒还</s:elseif>
		        <s:elseif test="#request.abean.paymentMode == 4">一次性还款</s:elseif></td>
                        <td>
                        <s:if test="#request.abean.borrowStatus ==1">等待资料</s:if>
		        <s:elseif test="#request.abean.borrowStatus ==2"> 招标中</s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus ==3 ">已满标 </s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus ==4 ">还款中 </s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus ==5 "> 已还完 </s:elseif>
		    	<s:elseif test="#request.abean.borrowStatus == 6 ">失败</s:elseif>
		    	<s:else>${abean.borrowStatus}</s:else>
		    			</td>
                      </tr>
                  </table> 
                  
              <div class="nav_pub_tb clearfix">投资信息</div>
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_d">
                      <tr class="blod">                                                                    
                        <td>投资金额</td>
                        <td>总收益</td>
                        <td>借款到期时间</td>
                        <td>投资时间</td>
                        <td>状态</td>
                      </tr>
                      <tr>
                        <td>${abean.investAmount }</td>
                        <td>${abean.recievedInterest }</td>
                        <td>${abean.endTime }</td>
                        <td>${abean.investTime }</td>
                        <td>
                        <s:if test="#request.abean.borrowStatus >=4">
                        	<s:if test="#request.abean.borrowWay ==7"><a class="fancybox.ajax" id="protocol" href="protocol.mht?typeId=2&&borrowId=${abean.borrowId }&&investId=${abean.id }&&styles=1" target="_blank">查看协议</a></s:if>
                        	<s:else><a class="fancybox.ajax" id="protocol" href="protocol.mht?typeId=1&&borrowId=${abean.borrowId }&&investId=${abean.id }&&styles=1" target="_blank">查看协议</a></s:else>
                        </s:if>
                        <s:else>-- </s:else>
                        </td>
                      </tr>
                  </table>
              <s:if test="#request.abean.borrowStatus ==4 || #request.abean.borrowStatus ==5">
              <div class="nav_pub_tb clearfix">回款信息</div>
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_d">
                      <tr class="blod">                                                                    
                        <td>期数</td>
                        <td>还款时间</td>
                        <td>应收本金</td>
                        <td>应收利息</td>
                        <td>利息管理费</td>
                        <td>逾期罚息</td>
                        <td>状态</td>
                      </tr>
                      
                     <s:iterator value="#request.listMap" var="bean">
					   <tr>
					   <td align="center">${bean.repayPeriod}</td>
					   <td align="center">${bean.repayDate}</td>
					   <td align="center">${bean.forpayPrincipal}</td>
					   <td align="center">${bean.forpayInterest}</td>
					   <td align="center">${bean.manage}</td>
					   <td align="center"><s:if test="#bean.forFI ==0">--</s:if><s:else>${bean.forFI}</s:else></td>
					   <td align="center"><s:if test="#bean.repayStatus ==1">未偿还</s:if><s:else>已偿还</s:else></td>
					   
				       </tr>
				     </s:iterator>
                      
                      
                  </table>   
             </s:if>
           </div>                   
         </div>
        </div>
     
   </div>
</div>
<!--主题内容-->

<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
//displayDetail(1,2);
$("#protocol").fancybox({
		fitToView	: false,
		width		: '70%',
		height		: '90%',
		autoSize	: false,
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none'
	});
</script>
</body>
</html>
