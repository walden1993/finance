<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>我的投资</title>
    <link href="css/inside.css"  rel="stylesheet" type="text/css" />
    <link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
    <jsp:include page="html5meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
	<link rel="stylesheet" type="text/css" href="css/new_inside.css" />
	<link rel="stylesheet" type="text/css" href="css/user.css" />
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>	

<!--主题内容-->
<div class="mainwarp">
         <div class="uesr_border personal clearfix">
            <div class="pub_tb bottom clearfix red3">投资详情</div>
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
              <div class="nav_pub_tb table-responsive clearfix">项目信息</div>
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
                  
              <div class="nav_pub_tb table-responsive clearfix">投资信息</div>
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
                        <a href="protocol.mht?typeId=1&&borrowId=${abean.borrowId }&&investId=${abean.id }&&styles=1" target="_blank">查看协议</a>
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
<!--主题内容-->

</body>
<script type="text/javascript" src="js/jquery1.42.min.js"></script>
<script type="text/javascript" src="js/focus.js"></script>
<script type="text/javascript" src="js/js.js"></script>

<script>

$(document).ready(function(){
$("#con_one_2").find("li:gt(7)").slideToggle("slow");
$("#con_one_2").find(".flip2").click(function(){
	var isshow = false;
    if(isshow){
        isshow = false;
    }else{
        isshow = true;
    }
    $("#con_one_2").find("li:gt(7)").slideToggle("slow");
    if(isshow){
        $(".flip2").html("选择其他银行↑")
    }else{
        $(".flip2").html("选择其他银行↓")
    }
  });
  
$("#con_one_1").find("li:gt(7)").slideToggle("slow");
$("#con_one_1").find(".flip1").click(function(){
	var isshow = false;
    if(isshow){
        isshow = false;
    }else{
        isshow = true;
    }
    $("#con_one_1").find("li:gt(7)").slideToggle("slow");
    if(isshow){
        $(".flip1").html("选择其他银行↑")
    }else{
        $(".flip1").html("选择其他银行↓")
    }
  }); 
});
</script>
</html>
