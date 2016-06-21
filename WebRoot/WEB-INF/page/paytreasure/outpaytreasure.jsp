<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<c:set value="涨薪宝转出" scope="request" var="title"></c:set>
<jsp:include page="/include/head.jsp"></jsp:include>
<style type="text/css">
</style>
<script type="text/javascript">
	var menuIndex = 4;
</script>
<style type="text/css">
.q{font-weight: bold;}
.a{color: #DE4A4A;}
	.footer_btn{
position:fixed; bottom:0px;line-height:23px; z-index:9999; opacity:90;
filter:alpha(opacity=100); _bottom:auto; _width:100%; _position:absolute;
_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-
(parseInt(this.currentStyle.marginTop, 10)||0)-(parseInt(this.currentStyle.marginBottom, 10)||0)));
}
	
	</style>
</head>
<body>
	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>
	<div class="wrap  mb20">
  <div class="w950 wdzh">
  	<ol class="breadcrumb mb0 bg-none">
	  <li><a href="/">首页 </a></li>
	  <li><a href="myPayTreasure.mht">我的涨薪宝</a></li>
	  <li class="active">转出</li>
 	 </ol>
      <!--  div class="box"> -->
         <!-- 引用我的帐号主页左边栏 -->
         <%@include file="/include/left.jsp" %>
      <!-- /div -->
    
	<div class="r_main fr w750 mb">
		<div class="pub_tb bottom">
		<h3 style="border:none; "><a href="myPayTreasure.mht" style="color:#a6a6a6;">我的涨薪宝</a></h3>
        <h3>转出</h3>
        <h3 style="border:none; "><a href="paytreasuredetail.mht" style="color:#a6a6a6;">转入</a></h3>
        </div>
        
         <div class="uesr_border personal">
             <div class="mt30">
             	<table class="text-center w">
	             	<tr height="30px;">
	             		<td>涨薪宝总金额：<s:property default="0.00" value="#request.myPayInvest.investamount" />元，三好资本余额：${usableSum.usableSum}元</td>
	             	</tr>
	             	<tr height="50px;">
	             		<td>转出金额：<input type="text" id="borrowAmount" class="input w200" /></td>
	             	</tr>
	             	<tr height="50px;">
	             		<td>交易密码：<input type="password" id="dealPWD" class="input w200" /></td>
	             	</tr>
	             	<tr height="40px;">
	             		<td><button id="invest" class="button w140">确认转出</button></td>
	             	</tr>
	             	<tr height="40px;">
	             		<td>(注意：涨薪宝金额转出资金会转出在三好资本余额中,提现参考三好资本提现规则)</td>
	             	</tr>
            	 </table>
             </div>
             <div>
             	<div class="mb10" style="border-bottom: 1px solid #ccc;line-height: 40px;height: 40px;"></div>
             	<h3>常见问题</h3>
             	<hr/>
             	<p class="q">Q：转出资金流向哪里了？</p>
             	<p class="a">A：涨薪宝转出资金会转出在三好资本余额中。</p>
             	<p class="q">Q：何提取涨薪宝余额？</p>
             	<p class="a">A：转出后在三好资本余额中申请提取。</p>
             	<p class="q">Q：转出部分当日有收益吗？</p>
             	<p class="a">A：转出的部分，当天是没有收益的。</p>
             </div>
         </div>
        </div>
	
  </div>
</div>
	
	<!-- 引用底部公共部分 -->
	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
		//displayDetail(6,9);
		$(function(){
			$("#invest").click(function(){
				var amount = $("#borrowAmount").val();
				var dealpwd = $("#dealPWD").val();
				param["paramMap.amount"] = amount;
				param["paramMap.dealpassword"] = dealpwd;
				if(isBlank(amount)){show("请输入转出金额");return;}
				if(isBlank(dealpwd)){show("请输入交易密码");return;}
				$.dispatchPost("outPayTreasure.mht",param,function(data){
					if(data==1){
						show("请输入转出金额");
					}else if(data==2){
						show("您输入的金额有误，请重新输入");
					}else if(data==12){
						show("您输入的金额小数太多，请重新输入，最多输入两位小数");
					}else if(data==3){
						show("请输入交易密码");
					}else if(data==4){
						show("请重新登录，<a href='login.mht' class='red'>立即登录</a>");
					}else if(data==5){
						show("交易密码不正确");
					}else if(data==6){
						show("您的转出金额超过您的总余额，请重新输入");
					}else if(data==7){
						show("转出失败，请重新操作");
					}else if(data==8){
						show("转出成功",function(){window.location.reload();});
					}
				})
			});
		})	
	</script>
</body>
</html>

