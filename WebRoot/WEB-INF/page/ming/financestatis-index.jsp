<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>投资统计</title>
    <jsp:include page="html5meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
</head>
<body>
<!-- 引用头部公共部分 -->
<jsp:include page="header.jsp"></jsp:include>	
<div class="nymain">
  <div class="wdzh">
    <div class="r_main">
      <div class="box">
       <div class="boxmain2">
      <div class="biaoge table-responsive" style="margin-top:0px;">
          <table class="table">
  <tr>
    <th colspan="3">回报统计</th>
    </tr>
  <tr>
    <td align="center">已赚利息</td>
    <td align="center">奖励收入总额</td>
    <td align="center">已赚逾期罚息</td>
    </tr>
  <tr>
    <td align="center">￥<s:if test="#request.financeStatisMap.earnInterest !=''">${financeStatisMap.earnInterest}</s:if><s:else>0</s:else></td>
    <td align="center">￥<s:if test="#request.financeStatisMap.rewardIncome !=''">${financeStatisMap.rewardIncome}</s:if><s:else>0</s:else></td>
    <td align="center">￥<s:if test="#request.financeStatisMap.hasFI !=''">${financeStatisMap.hasFI}</s:if><s:else>0</s:else></td>
    </tr>
          </table>

          </div>
          <div class="biaoge table-responsive">
          <table  class="table">
  <tr>
    <th colspan="6">个人理财统计</th>
    </tr>
  <tr>
    <td align="center">总借出金额</td>
    <td align="center">总借出笔数</td>
    <td align="center">已回收本息</td>
    </tr>
  <tr>
    <td align="center">￥<s:if test="#request.financeStatisMap.investAmount !=''">${financeStatisMap.investAmount}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.financeStatisMap.investCount !=''">${financeStatisMap.investCount}</s:if><s:else>0</s:else></td>
    <td align="center">￥<s:if test="#request.financeStatisMap.hasPI !=''">${financeStatisMap.hasPI}</s:if><s:else>0</s:else></td>
  </tr>
    <tr>
    <td align="center">已回收笔数</td>
    <td align="center">待回收本息</td>
    <td align="center">待回收笔数</td>
    </tr>
    <tr>
    <td align="center"><s:if test="#request.financeStatisMap.hasCount !=''">${financeStatisMap.hasCount}</s:if><s:else>0</s:else></td>
    <td align="center">￥<s:if test="#request.financeStatisMap.forPI !=''">${financeStatisMap.forPI}</s:if><s:else>0</s:else></td>
    <td align="center"><s:if test="#request.financeStatisMap.forCount !=''">${financeStatisMap.forCount}</s:if><s:else>0</s:else></td>
    </tr>
          </table>

          </div>
       </div>
</div>
    </div>
  </div>
</div>
<!-- 引用底部公共部分 -->     
  <script>
    $(function(){
		$("#invStatisNew").addClass('on');
          //$("#zh_hover").attr('class','nav_first');
	 	  //$("#zh_hover div").removeClass('none');
    	  //$('#li_12').addClass('on');
        });
  
    </script>
</body>
</html>
