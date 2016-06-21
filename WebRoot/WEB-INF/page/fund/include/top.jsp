<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>

<div class="top_wrap">
<div class="w1180">

<div class="tools_bar">
<s:if test="#session.user == null">
<span><a href="login.mht" >登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="reg.mht" >注册</a></span>
</s:if>

<span><a class="qq" href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes" target="_blank">在线咨询</a></span><span><a href="queryAboutInfo.mht?msg=106" target="_blank">帮助中心</a></span><span><a href="#" id="mobilelink" target="_blank">手机版</a><div class="mobilelink_pop"><img alt="三好资本apk二维码下载" src="images/index/mobilelink.jpg" /></div></span>
<s:if test="#session.user != null"><span><a href="logout.mht">安全退出</a></span></s:if></div>


<div class="wel_text">您好 <s:if test="#session.user != null"><a href="home.mht">${user.userName}</a></s:if>，欢迎来到三好资本！</div>


</div>
</div>

<div class="top_nav">
<div class="w1180">
<div class="logo_box"><a href="index.mht"><img alt="" src="images/index/sanhao_logo.png"></a></div>
<ul>
<li><a href="index.mht">首页</a></li>
<li><a href="public_funds.mht">公募基金</a></li>
<li><a href="private_finance.mht">高端理财</a></li>
<li><a href="bidlist.mht">稳健理财</a></li>
<li><a href="paytreasuredetail.mht">现金管理</a></li>
<li><a href="queryAboutInfo.mht?msg=99">关于我们</a></li>
</ul>
</div>
</div>

