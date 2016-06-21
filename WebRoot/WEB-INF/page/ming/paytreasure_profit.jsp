<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收益记录</title>
<link href="css/inside.css"  rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/common.css"  rel="stylesheet" type="text/css" />
<link href="css/user.css"  rel="stylesheet" type="text/css" />
   <link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
	<style type="text/css">
.tab_ec{margin:0 auto;padding-top:20px;line-height:24px;color:#666;}
.tab_ec a{color:#00aaeb;}.tab_ec a:hover{color:#41bdec;}
.tab_ec table{border:1px solid #dadada; background:#fff; line-height:24px;}
.tab_ec .leve_titbj{background:#f8f8f8; color:#333;}
.tab_ec table tr,.tab_ec table tr{border-bottom:1px solid #dadada;text-align:center;}
.tab_ec table tr td{border-right:1px solid #dadada;}.tab_ec table tr td{padding:5px 5px;}
.tab_d{line-height:30px;color:#666;margin:0px auto 20px auto;width:100%;}
.tab_d a{color:#00aaeb;}.tab_c a:hover{color:#41bdec;}
.tab_d .leve_titbj{background:#f8f8f8; color:#333; font-size:16px;}
.tab_d .blod{font-weight:bold;}
.tab_d tr,.tab_d tr{border-bottom:1px solid #dadada;text-align:center;}
.tab_d tr td{padding:5px 0px;}.tab_d tr td{padding:5px 5px;}
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
		<div class="text-center mt20">
			<p class="f25 bold">累计收益(元)</p>
			<p class="f25 bold"><s:property default="0.00" value="#request.mypaytreasure.allprofit" /></p>
		</div>
		<div id="info"></div>
 	 </div>
</div>
<script>

 	$.dispatchPost("profitRecord.mht",param,function(data){
         		$("#info").html(data);
    });
 
</script>
</body>
</html>
