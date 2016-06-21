<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>五月奔跑季  “速度”看你的</title>
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
.banner{background:url(activity/20150413/old_activity/images/banner.jpg) top center;height:467px;width:100%;display:block;}
.banner_top_bj{ background:url(activity/20150413/old_activity/images/banner-top.png) repeat center top; width:100%;height:9px;}
.banner_time{padding:430px 0 0 30px;color:#fff;font-size:18px; margin-left:-100px;}
.cont_bj{background:#05ab92 url(activity/20150413/old_activity/images/cont_bj.jpg) no-repeat top center;width:100%;padding:20px 0;}
.wrap{background:url(activity/20150413/old_activity/images/bj.png) no-repeat top center;width:1071px; margin:0 auto; display:block;height:1272px;}
.zt_btn{ background:#ff4d4d;width:180px;line-height:40px;color:#fff;height:40px;display:block;-moz-border-radius:6px;      /* Gecko browsers */
    -webkit-border-radius:6px; text-align:center;font-size:20px;margin-top:20px;border-radius:6px;}
.zt_btn:hover{background:#ff6e00;color:#fff;}	
.zt_bottom_line{border-bottom:1px solid #992d2d;padding-bottom:20px;}	
.zt_explain{padding-top:40px;}
.zt_explain p{line-height:38px;color:#fff;font-size:16px; margin-top:20px;padding:0 168px;}
.zt_explain span{color:#fbd501;}
.zt_explain h3{color:#fff; text-align:center;font-size:30px; margin-top:20px;}
.naving{ background:url(activity/20150413/old_activity/images/jan_28.png) no-repeat; display:block;padding:40px; margin-left:420px;}
.qzone_wb,.sina_wb,.qq_wb{ background:url(activity/20150413/old_activity/images/jan_wb.png) no-repeat;display:block;width:25px;height:25px;line-height:25px; float:left;margin:8px 2px 0 0; cursor:pointer;}
.naving span{color:#008b76;font-size:18px;margin-top:8px;}
.qzone_wb{ background-position:0 0;}.sina_wb{ background-position:-28px 0;}.qq_wb{ background-position:-55px 0;}
.qzone_wb:hover{ background-position:0 -30px;}.sina_wb:hover{ background-position:-28px -30px;}.qq_wb:hover{ background-position:-55px -30px;}
.zt_list{padding:40px 0 0 120px;}
.zt_list li{color:#008b76; margin-bottom:50px;}
.zt_list li div{float:left;}
.zt_list li .info{ margin:15px 0 0 25px;}
.zt_list li .info h2{font-size:30px;font-weight:blod;}
.zt_list li .info p{margin:15px 0;font-size:18px;}
</style>
</head>

<body>
<s:include value="/include/top.jsp"></s:include>
<div class="banner">
   <div class="banner_top_bj"></div>
   <div class="tc banner_time">活动时间：2015年4月13日20:00至2015年5月31日18:00</div>
</div>
<div class="cont_bj">
  <div class="wrap clearfix">
       <div class="naving">
               <span class="fl">分享到：</span>
             <a class="qzone_wb" href='http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=<%=application.getAttribute("basePath")%>activityIndex_running.mht?title=四月奔跑季  “速度”看你的 三好资本理财平台！&summary=四月红包季  “速度”看你的 三好资本理财平台' target="_blank"></a>
             <a  class="sina_wb" href='http://v.t.sina.com.cn/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_running.mht?title=四月奔跑季  “速度”看你的 三好资本理财平台' target="_blank"></a>
             <a class="qq_wb" href='http://v.t.qq.com/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_running.mht?title=四月奔跑季  “速度”看你的 三好资本理财平台&pic=activity/20150112/images/jan_01.jpg' target="_blank"></a> 
       </div>
       <ul class="zt_list">
         <li class="clearfix">
           <div><img src="activity/20150413/old_activity/images/pic01.png" width="168" height="168" /></div>
           <div class="info"> 
              <h2>捷足先登奖</h2>
              <p>每个项目第一个投资成功且投资额10000元及以上用户，可获得10元现金</p>
              <a href="bidlist.mht" target="_blank" class="zt_btn">立即投标</a>
           </div>
         </li>
         <li class="clearfix">
           <div><img src="activity/20150413/old_activity/images/pic02.png" width="168" height="168" /></div>
           <div class="info"> 
              <h2>完美收官奖</h2>
              <p>每个项目最后一个投资成功，可获得10元现金</p>
              <a href="bidlist.mht" target="_blank" class="zt_btn">立即投标</a>
           </div>
         </li>
         <li class="clearfix">
           <div class="fl"><img src="activity/20150413/old_activity/images/pic03.png" width="168" height="168" /></div>
           <div class="info"> 
              <h2>财富至尊奖</h2>
              <p>每个项目累计投资额最高者，可获得30元现金</p>
              <a href="bidlist.mht" target="_blank" class="zt_btn">立即投标</a>
           </div>
         </li>
       </ul>
       <div class="zt_explain">
           <h3>活动说明</h3>
           <p>1、活动时间：2015年4月13日20:00至2015年5月31日18:00
<br/><span>2、活动三大奖奖励金额将会在活动期结束后的5个工作日内，直接发放到用户的平台账户上；</span>
<br/>3、财富至尊奖如累计投资额最高者额度相同，则按完成投资时间顺序，先完成者获得奖励；
<br/>4、本活动最终解释权归深圳市易付通金融信息技术有限公司所有。

</p>
       </div>     
  </div>
</div>
<s:include value="/include/footer.jsp"></s:include>
</body>
</html>
