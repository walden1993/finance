<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

<!DOCTYPE HTML>
<html lang="zh-cn">
  <head>
    <title>集成登录</title>
	<jsp:include page="/include/head.jsp"></jsp:include><!-- 引用公共head -->
	<link rel="stylesheet" type="text/css" href="css/register.css">

  </head>
  
  <body>
  	<!-- 引用头部公共部分 -->
	<jsp:include page="/include/top.jsp"></jsp:include>	
    <div class="ny_content">
<div class="reg_matter clearfix">
  <%-- <div class="step_three clearfix"></div> --%>
  <div class="w950 pt30 f14">
       <div class="text-center"><s:actionerror/>，<span id="time" ></span>秒后，自动跳转到登录页面，如未跳转<a href="login.mht" class="red">点击此处</a></div>
  </div>
</div>
</div>
    <!-- 引用底部公共部分 -->     
	<jsp:include page="/include/footer.jsp"></jsp:include>
    <script type="text/javascript">
	var i = 5;
	window.onload = timeout();
	function  timeout(){
		if(i==0)window.location.href="login.mht";
		$("#time").html(i);
		i--;
		window.setTimeout("timeout()","1000");
	}
	</script>
  </body>
  
</html>
