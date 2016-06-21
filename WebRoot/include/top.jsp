<%@ page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="taglib.jsp" %>

<!-- <span  id="nrie"> </span>
<div style="position:fixed;left:0%;top:55%; z-index:80" id="test">
<div style="position:absolute; z-index:80px; left:150px; top:0px;"><button onclick="document.getElementById('test').style.display='none';" style="border:none; background:#fff;"><img src="images/x_gb.png"/></button></div>
<div class=""><a href="activityIndex_national.mht"><img src="activity/20150921/images/left2.png" usemap="#Map" height="250" width="180"/></a></div>
</div> -->

<!--头部-->
<div class="header">
      <div class="head_top w950" >
    <div class="left">
		<div class="tel"><span>客服热线：<font color="#dcdcdc" class="bold800">${sitemap.servicePhone }</font></span></div>
    <div class="fl "><span class="pr10">|</span>关注我们</div>
    <div class="weibo mr10 ml10" data-id="weibo"> <img src="images/weibo.png" height="15px;" />
          <div id="weibo" class="pop_con" style="width: 160px; display:  none;">
        <div class="content">
              <p>点击关注新浪微博： </p>
              <p><a href="http://weibo.com/u/5251411790" target="_blank">@三好资本</a></p>
            </div>
        <s><i></i></s> </div>
        </div>
    <div class="weixin mr10" data-id="weixin"> <img src="images/weixin.png"  height="15px;" />
          <div id="weixin" class="pop_con" style="width: 200px; height:90px; display: none;">
        <div class="content">
              <div class="fl"><img src="images/wxgz.jpg" width="70" height="70"></div>
              <div class="xinxi">
            <p><span>三好资本微信号：</span></p>
            <p style="color:#de4a4a">三好资本</p>
          </div>
            </div>
        <s><i></i></s></div>
        </div>
    <div class="fl"><span class="pr10">|</span><font color="#dcdcdc"><a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2198380644&amp;site=qq&amp;menu=yes" target="_blank" style="color: #dcdcdc;">在线咨询</a></font></div>
    <div class="qq mr10 ml10" data-id="qq"> <img src="images/qq.png"  height="15px;" />
        <div id="qq" class="pop_con" style="width: 180px; height: 55px; display: none;">
        <div class="content">
              <div class="center pl10 pr10">
            <p><a href="http://wpa.qq.com/msgrd?v=3&amp;uin=2198380644&amp;site=qq&amp;menu=yes" target="_blank" class="red_qq">在线咨询</a></p>
          </div>
            </div>
        <s><i></i></s></div>
        </div>
       <s:if test="#session.user != null"><div class="fl"> <span class="pr10">|</span><a href="queryAboutInfo.mht?msg=106" style="color: #dcdcdc;border:1px solid #DDD;padding:0px 1px">帮助中心</a></span> </div></s:if>
    </div>
    <div class="right ">
    	  <s:if test="#session.user == null">
          	<div class="r mr10" style="padding-top: 1px;"><span class="reg"><a href="reg.mht" >注册</a></span>|<span class="login"><a href="login.mht" >登录</a></span>|<span><a href="queryAboutInfo.mht?msg=106" style="color: #dcdcdc;border:1px solid #DDD;padding:0 1px">帮助中心</a></span></div>
          </s:if>
          <s:else>
          	<div class="r mr10">
	          	<div class="r haslogin">
	          	<ul>
	          	<li class="ico1"><a href="home.mht">${user.userName}</a>
				</li>
				<li class="ico2">
				<a href="mailNoticeInit.mht" id="mEmail">
				</a></li>
				<li class="ico3"><a href="logout.mht">退出</a></li></ul>
				</div>
          	</div>
          </s:else>
          <%-- <div class="phone mr10"><a href="activityIndex_app_zt.mht">移动客户端</a></div> --%>
          <div class="line mr10"></div>
        </div>
  </div>
    </div>
<div class="menu">
    <div class="x_menu w950">
          <div class="logo mt10"><a href="<%=application.getAttribute("basePath")%>"><img src="images/index/sanhao_logo.png" alt="三好资本" title="三好资本"  height="55" /></a></div>
          <div class="x_sidenav fr">
         <ul class="clearfix">
              <li>
            <div class="main_nav text-center"><a href="index.mht">首&nbsp;&nbsp;页</a></div>
          </li>
              <li>
            <div class="main_nav"><a href="public_funds.mht">公募基金</a></div>
          </li>
              <li class="">
            <div class="main_nav"><a href="private_finance.mht">高端理财</a></div>
          </li>
          <li>
            <div class="main_nav"><a href="bidlist.mht">稳健理财</a></div>
          </li>
          <li>
            <div class="main_nav"><a href="bidlist.mht">现金管理<b class="rit_sj"></b></a></div>
          </li>          
              <li class="side">
            <div class="main_navlist"><a href="queryAboutInfo.mht?msg=99">关于我们<b class="rit_sj"></b></a></div>
            <!-- <div class="sub_nav">
                  <div class="top_sj"></div>
                  <ul>
                <li><a href="queryAboutInfo.mht?msg=100">网站公告</a></li>
                <li><a href="queryAboutInfo.mht?msg=101">平台资讯</a></li>
                <li><a href="queryAboutInfo.mht?msg=104">合作伙伴</a></li>
                <li><a href="queryAboutInfo.mht?msg=105">联系我们</a></li>
                <li><a href="queryAboutInfo.mht?msg=106">帮助中心</a></li>
                <li><a href="queryAboutInfo.mht?msg=107">人才招聘</a></li>
              </ul>
                </div> -->
          </li>
        </ul>
      </div>
        </div>
  </div>
<noscript>
	<style type="text/css">
	.ie-noscript-warning{background: red;color:white;text-align: center;}
	</style>
     <div class="ie-noscript-warning">您的浏览器禁用了脚本，请<a href="">查看这里</a>来启用脚本!或者<a href="/?noscript=1">继续访问</a>
     </div>
</noscript>
<%--[if lt IE 8]>
<script>window.location.href  = 'activityIndex_updateBowers.mht';</script>
<![endif]--%>