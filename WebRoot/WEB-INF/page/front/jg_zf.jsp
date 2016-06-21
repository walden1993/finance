<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="资费说明" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
<script type="text/javascript">var menuIndex = 5;</script>
</head>

<body>
<!-- 引用头部公共部分 -->
<jsp:include page="/include/top.jsp"></jsp:include>

<!--内容区域-->
<div class="wrap">
  <div class="w950">
	  <ol class="breadcrumb mb0 bg-none">
		  <li><a href="/">首页 </a></li>
		  <li><a href="queryAboutInfo.mht?msg=99">关于我们 </a></li>
		  <li>资费说明</li>
	  </ol>
  	<jsp:include page="/WEB-INF/page/helpcenter/aboutme_left.jsp"></jsp:include>
    <div class="r_main fr w750 mb">
    	<div class="pub_tb bottom"><h3>资费说明</h3></div><div class="clearfix"></div>
    	<div class="about_context p15 bg-white pb20 mb20">
		<table border="0" class="table table-bordered " style="text-align: center;" cellpadding="0" cellspacing="0">
		    <tbody>
		        <tr>
		            <td colspan="2">类型</td>
		            <td style="width: 200px;" colspan="2">费用标准</td>
		        </tr>
		        <tr>
		            <td colspan="2">注册会员</td>
		            <td style="color:#fb5555" colspan="2">免费</td>
		        </tr>
		        <tr>
		            <td colspan="2">投资管理费</td>
		            <td colspan="2">收取利息的10%的费用，<font style="color:#fb5555">现平台推广期免费</font></td>
		        </tr>
		        <tr>
		            <td colspan="2">充值</td>
		            <td colspan="2" style="color:#fb5555">免费</td>
		        </tr>
		        <tr>
		            <td colspan="2">实名认证</td>
		            <td colspan="2" style="color:#fb5555">免费</td>
		        </tr>
		        <tr>
		            <td colspan="2">账户管理</td>
		            <td  colspan="2" style="color:#fb5555">免费</td>
		        </tr>
		        <tr>
		            <td colspan="2">VIP</td>
		            <td colspan="2" style="color:#fb5555">免费</td>
		        </tr>
		        <tr>
		            <td colspan="2">短信通知</td>
		            <td colspan="2" style="color:#fb5555">免费</td>
		        </tr>
		        <tr>
		            <td rowspan="2">提现</td>
		            <td rowspan="2">T+1个工作日到账</td>
		            <td>15天内</td>
		            <td style="text-align:left">凡是在三好资本充值未投标的用户，15天以内提现收取本金<font color="red">0.5%</font>，最低<font color="red">3</font>元。</td>
		        </tr>
		        <tr>
		            <td>15天外</td>
		             <td style="color:#fb5555">免费</td>
		        </tr>
		        <%-- <tr>
		            <td rowspan="2">快速提现<br>（14:30前提现，当日到账；14：30后提现，T+1个工作日到账）</td>
		            <td style="text-align:left">投资后的回收资金（利息、本金、奖金）</td>
		            <td>0.20% （最低3元/笔）</td>
		        </tr>
		        <tr>
		            <td style="width:287px;text-align:left">未投资资金</td>
		            <td>0.60% （最低6元/笔）</td>
		        </tr>
		        <tr>
		            <td rowspan="2">实时提现<br>（实时到账，限工商银行和招商银行）</td>
		            <td style="text-align:left">投资后的回收资金（利息、本金、奖金）</td>
		            <td>0.20% （最低3元/笔）</td>
		        </tr>
		        <tr>
		            <td style="text-align:left">未投资资金</td>
		            <td>0.60% （最低6元/笔）</td>
		        </tr>
		        <tr>
		            <td rowspan="3">站内转让</td>
		            <td colspan="2" style="width:287px;">债权持有天数满60天（包括60天）</td>
		            <td style="color:#fb5555">免费</td>
		        </tr>
		        <tr>
		            <td colspan="2">债权持有天数不足60天，收取本金的</td>
		            <td>0.50%</td>
		        </tr>
		        <tr>
		            <td colspan="2">转让价格高于投资本金的，收取超出部分的</td>
		            <td>20%</td>
		        </tr> --%>
		    </tbody>
		</table>
    	</div>
    </div>
  </div>
</div>
<div class="clearfix"></div>
<!--footer-->
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	$(".html_left a:eq(4)").addClass("hover");

})
</script>

</body>
</html>
