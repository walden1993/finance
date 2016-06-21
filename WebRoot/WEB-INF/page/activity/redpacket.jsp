<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>五月红包季 百元红包任性抢</title>
<style type="text/css">
/*top_bar*/
html, body, div, p, h1, h2, h3, h4, h5, h6, blockquote,ol, ul, li, dl, dt, dd, form, fieldset, legend, button, input, textarea, pre, code, th, td{margin:0; padding:0;font-family:"Microsoft Yahei";font-family:"微软雅黑";}img{border:none;}
ul, ol, li{list-style:none;}
button, input, select, textarea{color:#333; font-family:"Microsoft Yahei";}
a{outline:none;text-decoration:none;color:#fe492a;}
a:active{noOutline:expression(this.onFocus=this.blur());}
:focus{outline:0;}
a:hover{color:#ff6666;text-decoration:none;}
.clearfix:after {content:"\200B";display:block;height:0;clear:both;}
.clearfix {*zoom:1;}
.clear{clear:both;line-height:0;}
.fr{float:right;}.fl{float:left;}.red{color:#ff000a;}.tc{ text-align:center;}.f24{ font-size:20px;}
.mr5{ margin-right:5px;}.mt40{ margin-top:40px;}.mt10{margin-top:10px;}.mt60{margin-top:60px;}.mb20{margin-bottom:30px;}.mt20{margin-top:20px;}.ml50{ margin-left:50px;}.ml20{ margin-left:20px;}
.ml40{ margin-left:10px;}

.w1070{width:1000px;margin:0 auto 20px auto;}
.banner{background: url(activity/20150413/new_activity/images/banner.jpg) top center;height:461px;width:100%;display:block;}
.banner_top_bj{ background:url(activity/20150413/new_activity/images/banner-top.png) repeat center top; width:100%;height:9px;}
.banner_time{padding:340px 0 0 0;color:#fe4344;font-size:18px; margin-left:-100px;}
.cont_bj{background:#ba1f20 url(activity/20150413/new_activity/images/pic04.jpg) no-repeat center center;width:100%;padding:20px 0;}
.zt_info{color:#fff; padding-top:45px;}
.zt_info h3,.zt_explain h3{font-size:26px;}
.zt_info p{ margin-top:30px;font-size:22px; line-height:45px;}
.zt_info span{color:#faef92;font-size:26px;}
.zt_info i{font-size:18px;}
.zt_info .zt_btn{ background:#8f1112; border-bottom:2px solid #6d1008;width:242px;line-height:50px;color:#fff;height:50px;display:block;-moz-border-radius: 15px;      /* Gecko browsers */
    -webkit-border-radius: 15px; text-align:center;font-size:20px;margin-top:20px;border-radius:20px; }
.zt_bottom_line{border-bottom:1px solid #992d2d;padding-bottom:20px;}	

.zt_explain{padding-top:20px;}
.zt_explain p{line-height:32px;color:#fff;font-size:16px; margin-top:20px;}
.zt_explain h3{color:#faef92;}
.naving{ background:url(activity/20150413/new_activity/images/jan_28.png) no-repeat; display:block;margin-left:380px;}
.qzone_wb,.sina_wb,.qq_wb{ background:url(activity/20150413/new_activity/images/jan_wb.png) no-repeat;display:block;width:25px;height:25px;line-height:25px; float:left;margin:8px 2px 0 0; cursor:pointer;}
.naving span{color:#fff;font-size:18px;margin-top:8px;}
.qzone_wb{ background-position:0 0;}.sina_wb{ background-position:-28px 0;}.qq_wb{ background-position:-55px 0;}
.qzone_wb:hover{ background-position:0 -30px;}.sina_wb:hover{ background-position:-28px -30px;}.qq_wb:hover{ background-position:-55px -30px;}
</style>
</head>

<body>
<s:include value="/include/top.jsp"></s:include>
<div class="banner">
   <div class="banner_top_bj"></div>
   <div class="tc banner_time">活动时间：2015年4月13日18:00至2015年5月31日18:00</div>
</div>
<div class="cont_bj">
   <div class="w1070 clearfix zt_bottom_line">
     <div class="fl ml20"><img src="activity/20150413/new_activity/images/pic01.jpg"/></div>
     <div class="fl ml50 zt_info">
       <div class="clearfix">
           <h3 class="fl">新手任务</h3>
           <div class="naving">
                 <span class="fl">分享到：</span>
             	<a class="qzone_wb" href='http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=<%=application.getAttribute("basePath")%>activityIndex_redpacket.mht?title=四月红包季 百元红包任性抢 三好资本理财平台！&summary=四月奔跑季  百元红包任性抢 三好资本理财平台' target="_blank"></a>
             	<a  class="sina_wb" href='http://v.t.sina.com.cn/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_redpacket.mht?title=四月红包季 百元红包任性抢 三好资本理财平台' target="_blank"></a>
             	<a class="qq_wb" href='http://v.t.qq.com/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_redpacket.mht?title=四月红包季 百元红包任性抢 三好资本理财平台' target="_blank"></a> 
           </div>
       </div>
       <p>新用户成功注册送<span>20元</span>红包<br/>
投资再送<span>30元</span>红包<br/>
红包金额按投资额的<span>5‰</span>进行使用<i></i></p>
       <a href="reg.mht" target="_blank" class="zt_btn">立即注册</a>
     </div>
   </div>
  <div class="w1070 clearfix zt_bottom_line pt20">
     <div class="fl zt_info ml20">
        <div class="clearfix">
           <h3 class="fl">推荐任务</h3>
           <div class="naving f">
                 <span class="fl">分享到：</span>
             	<a class="qzone_wb" href='http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=<%=application.getAttribute("basePath")%>activityIndex_redpacket.mht?title=四月红包季 百元红包任性抢 三好资本理财平台！&summary=四月奔跑季  百元红包任性抢 三好资本理财平台' target="_blank"></a>
             	<a  class="sina_wb" href='http://v.t.sina.com.cn/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_redpacket.mht?title=四月红包季 百元红包任性抢 三好资本理财平台' target="_blank"></a>
             	<a class="qq_wb" href='http://v.t.qq.com/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_redpacket.mht?title=四月红包季 百元红包任性抢 三好资本理财平台' target="_blank"></a> 
           </div>
       </div>
       <p>每推荐注册有效客户1人<br/>
推荐人可获得<span>10元</span>红包<br/>
红包金额按投资额的<span>5‰</span>进行使用<br/>
<i></i></p>
       <a href="reg.mht" target="_blank" class="zt_btn">立即注册</a>
     </div>
     <div class="fl ml50"><img src="activity/20150413/new_activity/images/pic02.jpg"/></div>
   	<div class="fl zt_info ml20">
   	<p>邀请方式：
	<br/>方式一：进入注册页面，让您的好友在推荐人一栏填写您的用户名即可。
	<br/>方式二：登录您的账户，在账户中心的会员设置>邀请好友中，将您的邀请链接复制给您的好友即可。
	<br/>有效客户：推荐好友注册成功并投资即可成为有效客户。</p>
   	</div>
   </div>
  <div class="w1070 clearfix">
     <div class="fl ml40"><img src="activity/20150413/new_activity/images/pic03.jpg" /></div>
     <div class="fl zt_explain ml40">
       <h3>活动说明</h3>
       <p>1、活动时间：2015年4月13日20:00至2015年5月31日18:00<br/>
2、红包按投资额的5‰进行使用，以所获得的红包奖励金额作为封顶;<br/>
3、新手任务红包奖励仅限活动期间内有效，逾期作废，请您及时使用；<br/>
4、活动奖励金额将会直接发放到用户的平台账户上；<br/>
5、奖励金额可在我的账户中查看(赠送金额)；<br/>
6、本活动最终解释权归深圳市易付通金融信息技术有限公司所有。
</p>
     </div>
   </div>
</div>
<s:include value="/include/footer.jsp"></s:include>
</body>
</html>
