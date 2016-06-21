<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>转出</title>
<link href="css/inside.css"  rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/common.css"  rel="stylesheet" type="text/css" />
<link href="css/user.css"  rel="stylesheet" type="text/css" />
   <link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
	<style type="text/css">
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
<jsp:include page="header.jsp"></jsp:include>	
<div class="r_main">
     <div class="uesr_border personal p0">
       <div class="mt30">
             	<table class="text-center w">
	             	<tr height="30px;">
	             		<td>涨薪宝总金额：<s:property default="0.00" value="#request.myPayInvest.investamount" />元<br/></td>
	             	</tr>
	             	<tr height="50px;">
	             		<td>转出金额：<input type="text" id="borrowAmount" class="input w200" /></td>
	             	</tr>
	             	<tr height="50px;">
	             		<td>交易密码：<input type="password" id="dealPWD" class="input w200" /></td>
	             	</tr>
	             	<tr height="40px;">
	             		<td><button id="invest" class="button w140">确认</button></td>
	             	</tr>
	             	<tr height="40px;">
	             		<td class="red">(注意：涨薪宝金额转出资金会转出在三好资本余额中)</td>
	             	</tr>
            	 </table>
             </div>
 	 </div>
</div>
<!-- 引用底部公共部分 -->     
<script>
	$(function(){
			$("#invest").click(function(){
				var amount = $("#borrowAmount").val();
				var dealpwd = $("#dealPWD").val();
				param["paramMap.amount"] = amount;
				param["paramMap.dealpassword"] = dealpwd;
				if(isBlank(amount)){alert("请输入转出金额");return;}
				if(isBlank(dealpwd)){alert("请输入交易密码");return;}
				$.dispatchPost("outPayTreasure.mht",param,function(data){
					if(data==1){
						alert("请输入转出金额");
					}else if(data==2){
						alert("您输入的金额有误，请重新输入");
					}else if(data==12){
						alert("您输入的金额小数太多，请重新输入，最多输入两位小数");
					}else if(data==3){
						alert("请输入交易密码");
					}else if(data==4){
						alert("登录失效，请重新登录。");
					}else if(data==5){
						alert("交易密码不正确");
					}else if(data==6){
						alert("您的转出金额超过您的总余额，请重新输入");
					}else if(data==7){
						alert("转出失败，请重新操作");
					}else if(data==8){
						alert("转出成功");
						window.location.reload();
					}
				})
			});
		})	

</script>

</body>
</html>
