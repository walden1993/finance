<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>转入</title>
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
	             		<td>三好资本余额：${usableSum.usableSum}元 ，<a href="rechargeInit.mht">我要充值</a></td>
	             	</tr>
	             	<tr height="50px;">
	             		<td>转入金额：<input type="text" id="borrowAmount" class="input w200" /></td>
	             	</tr>
	             	<tr height="50px;">
	             		<td>交易密码：<input type="password" id="dealPWD" class="input w200" /></td>
	             	</tr>
	             	<tr height="40px;">
	             		<td><button id="invest" class="button w140">确认</button></td>
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
				var proctol = $("#proctol").val();
				param["paramMap.amount"] = amount;
				param["paramMap.dealpassword"] = dealpwd;
				//if(${map.paytreasure.isopen}==1){alert("尚未开始，敬请期待");return;}
				if(isBlank(amount)){alert("请输入投资金额");return;}
				if(isBlank(dealpwd)){alert("请输入交易密码");return;}
				$.dispatchPost("intoPayTreasure.mht",param,function(data){
					if(data==1){
						alert("请输入投资金额");
					}if(data==11){
						alert("尚未开始，敬请期待");
					}else if(data==2){
						alert("您输入的金额有误，请重新输入");
					}else if(data==12){
						alert("起购金额为1000元,请重新输入");
					}else if(data==13){
						show("只能输入两位小数，请重新输入");
					}else if(data==3){
						alert("请输入交易密码");
					}else if(data==4){
						alert("登录失效，请重新登录");
					}else if(data==5){
						alert("交易密码不正确");
					}else if(data==6){
						alert("您的投资金额超过可用余额，请重新输入");
					}else if(data==7){
						alert("投资失败，请重新操作");
					}else if(data==8){
						alert("投资成功，请进入您的账户中心查看您的涨薪宝明细");
						window.location.reload();
					}
				})
			});
});
</script>

</body>
</html>
