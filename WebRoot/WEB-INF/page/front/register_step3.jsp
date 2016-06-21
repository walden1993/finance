<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html >
<html>
<head>
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
       <div class="tc mb30 reg_success" style="margin-left:340px;">
             <div class="fl reg_right"></div>
             <div class="fl ">恭喜您， 注册成功！<a href="login.mht" class="button w100">立即登录</a></div>
       </div>
       <div class="text-center"><span id="time" ></span>秒后，自动跳转到您的账户中心页面，如未跳转<a href="home.mht" class="red">点击此处</a></div>
       <s:if test="#request.paramMap.isMail==1">
          <div class="tc mb100"><span class="red">尊敬的用户</span>，平台给您的邮箱发送了一封验证邮件，请<a href="http://${request.paramMap.mail}" class="reg_blue mr5 ml5">进入</a>您的邮箱验证。</div>
       </s:if>
       
  </div>
</div>
</div>
<!-- 引用底部公共部分 -->     
<jsp:include page="/include/footer.jsp"></jsp:include>
<script type="text/javascript">
var i = 5;
window.onload = timeout();
function  timeout(){
	if(i==0)window.location.href="home.mht";
	$("#time").html(i);
	i--;
	window.setTimeout("timeout()","1000");
}
</script>
</body>
</html>
