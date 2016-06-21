<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>三好资本国庆专题</title>
<style type="text/css">
html, body, div, p, h1, h2, h3, h4, h5, h6, blockquote,ol, ul, li, dl, dt, dd, form, fieldset, legend, button, input, textarea, pre, code, th, td{margin:0; padding:0;font-family:'Microsoft Yahei';font-family:'微软雅黑';}
ul, ol, li{list-style:none;}
button, input, select, textarea{color:#333; font-family:"Microsoft Yahei";}
a{outline:none;text-decoration:none;color:#333;}
a:active{noOutline:expression(this.onFocus=this.blur());}
:focus{outline:0;}
a:hover{color:#ff6666;text-decoration:none;}
img{border:none;}
.fr{float:right;}.fl{float:left;}.red{color:#ff000a;}.tc{ text-align:center;}.f24{ font-size:24px;}
.w1002{width:1003px; margin:0 auto;}
.mr5{ margin-right:5px;}.mt40{ margin-top:40px;}.mt10{margin-top:10px;}
.top_bar{font-size:15px;width:100%;height:40px; line-height:40px; border-bottom:1px #ececec solid;background:#fbfbfb;width:100%;position:fixed;left:0;top:0;_position: absolute; _top:expression(0+((e=document.documentElement.scrollTop)?e:document.body.scrollTop)+'px');z-index:1000;}
.top_bar .nav li{float:left;color:#a7a7a7;width:80px; margin-left:10px;}
.index_demo ul .index_one_name{float:right;height:32px;width:90px;} 
.index_demo ul .index_one_name a{float:left;padding:0;font-size:15px;}
.index_demo ul .index_one_name a.f14{font-size:15px;}
.index_demo ul .index_one_name a .tips{height:32px;color:#ff6666; display:block;white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; overflow:hidden; text-align:right;}
.index_demo ul .index_one_name a i{background:url("images/activity/index_icon_pic.png") no-repeat -84px -42px;height:32px;display:block;width:12px; }
.index_demo ul{position:relative;padding:0;margin:0;z-index:2000;*z-index:2000}
.index_demo ul li{position:absolute;background:#fff;left:-30px;*left:-15px;visibility:hidden;position:absolute;top:0px;width:0;border-top:#f4f4f4 1px solid;margin-top:30px;}/*显示li的内容的定位*/
.index_demo ul:hover li,.demo ul a:hover li{visibility:visible;}/*鼠标经过ul时，显示li的内容*/
.index_demo ul li a{display:block;width:85px;padding-left:8px;background:#fff;line-height:30px;height:30px;border:1px solid #f4f4f4;border-top:none;}
.index_demo ul li a:hover{color:#fff;background:#ff6666;}
.banner{ background: url(images/activity/gq_01.jpg) no-repeat top center;width:100%; display:block; height:552px;}
.containt{background:#ffe29c; width:100%;height:auto;padding-bottom:25px;}
.containt img{padding-top:32px;}
.float_block{ position:fixed; left:50%; margin-left:520px; top:150px;_position: absolute; _top:expression(150px+((e=document.documentElement.scrollTop)?e:document.body.scrollTop)+'px');}
.explain{background:#fff;padding:20px 0 20px 0; margin-top:30px;}
.explain .tit img{margin:0; padding:0;}
.explain .info{padding:40px 20px 0px 20px;}
.explain .info p{ line-height:30px;color:#333;}
.notice{font-size:18px; margin-top:20px;padding-bottom:25px;}
.notice .title{background:url(images/activity/gq_07.jpg) no-repeat; display:block;width:54px;height:55px;float:left; text-align:center;color:#fff;float:left; margin-right:15px; line-height:24px;padding-top:2px;}
.footer{margin-top:25px;padding-bottom:25px;line-height:30px;}
</style>

</head>

<body>
<div class="float_block"><img src="images/activity/gq_07.png" border="0" usemap="#Map" />
  <map name="Map" id="Map"><area shape="rect" coords="-22,28,100,65" href="#001" />
  <area shape="rect" coords="-1,66,100,100" href="#002" />
    <area shape="rect" coords="-1,100,100,135" href="#003" />
    <area shape="rect" coords="-4,136,100,169" href="#004" />
  <area shape="rect" coords="0,281,99,326" href="#top"/>
  </map>
</div>
<!--顶部条-->
<div class="top_bar">
	<div class="w1002 clearfix">
    	<div class="fl">
          <s:if test="#session.user !=null">
    		<input type="hidden" id="islogin" value="ok"/>
          	<!--登录后-->
       	  	<div class="dl">您好！<span class="red mr5">${user.userName}</span>欢迎回家！<a href="javascript:void(0);" class="ml10" onclick="logout()">退出</a></div>
       	</s:if>
		<s:else>
       	  	<!--未登录-->
          	<div class="dl"><a href="login.mht" class="mr10">登录</a>&nbsp;&nbsp;<a href="reg.mht">注册</a></div>
        </s:else>
        </div>
        <div class="fr side_menu">
        	<ul class="clearfix nav">
              <li><a href="index.mht">首&nbsp;页</a></li>
              <li>
                <div class="index_demo">
                   <ul>
				       <div class="index_one_name"><a href="home.mht" title="" class="f14"><span class="fl tips">我的账户</span><i class="fl"></i></a></div>
				       <li><a href="rechargeInit.mht">充值</a><a href="withdrawLoad.mht">提现</a><a href="financeStatisInit.mht">收益查看</a><a href="homeBorrowInvestList.mht">交易记录</a><a href="logout.mht">安全退出</a></li>
				   </ul>
                </div>
              </li>
			  <li><a href="callcenter.mht?type=true&cid=1">新手入门</a></li>
              <li><a href="callcenter.mht">帮助中心</a></li>
              <!--<li><a href="javascript:;">手机版</a></li>-->
            </ul>
        </div>
  </div>
</div>
<!--顶部条-->

<div class="banner">
  <div class="w1002" style="height:552px; display:block;"><div style="padding:500px 0 0 400px;"><a href="#006" style="bottom:0;"><img src="images/activity/btn.jpg" width="262" height="62" /></a></div></div>
</div>

<div class="containt">
    <div class="w1002">
      <div><a name="001" id="#001" ><img src="images/activity/gq_pic01.jpg" usemap="#Map2" border="0"/>
          <map name="Map2" id="Map2">
            <area shape="rect" coords="460,460,912,564" href="javascript:void(0);" onclick="dianji()" />
          </map>
      </a></div>
      <div><a name="002" id="#002"><img src="images/activity/gq_pic02.jpg" usemap="#Map3" border="0"/>
          <map name="Map3" id="Map3">
            <area shape="rect" coords="320,450,700,520" href="#005" />
          </map>
      </a></div>
      <div><a name="003" id="#003"><img src="images/activity/gq_pic03.jpg" usemap="#Map4" border="0"/>
          <map name="Map4" id="Map4">
            <area shape="rect" coords="404,300,743,402" href="bidlist.mht" />
          </map>
      </a></div>
      <div><a name="004" id="#004"><img src="images/activity/gq_pic04.jpg" usemap="#Map5" border="0"/>
          <map name="Map5" id="Map5">
            <area shape="rect" coords="801,26,989,110" href="bidlist.mht" />
          </map>
      </a></div>
      <div class="explain" id="006" >
          <div class="tit"><img src="images/activity/gq_pic08.jpg"/></div>
          <div class="info"><p>1、本次活动的时间为：2014年9月29日~2014年10月30日；</p>
<p>2、注册送体验券：用户成功注册后，系统会自动给新用户绑定一张1000元体验券，用户登录后可以到【会员中心】-【投资管理】-【投资体验】-【我的体验券】页面查看；</p>
<p>3、体验券仅可投资体验标的；</p>
<p>4、邀请有效会员的定义：被邀请的好友，成功注册并成功投标，其中所投标的不能为体验标的；</p>
<p>5、好人缘奖、好理财奖会在活动结束后5个工作日内，公布中奖名单，届时一并发放奖励金额，奖金会直接打入中奖者在平台的账户；</p>
<p>6、活动结束，新用户注册，将不再赠送体验券；</p>
<p>7、本次活动体验券数量有限，每个新用户只能领取一张体验券，先注册先得，送完即止；</p>
<p>8、体验券使用有效期截止2014年10月30日；</p>
<p>9、在法律允可的范畴内，本活动最终解释权归深圳市易付通投资管理有限公司所有。</p>
<div class="notice"><span class="title">特别<br/>申明</span><span class="red">如果本次活动的参与者，采用某些非正常的手段，扰乱本次推荐活动，一经核实，三好资本保留拒绝发放奖励的权利。</span></div>
      </div>
      </div>
      
      <div class="explain">
          <a name="005" id="005"><div class="tit"><img src="images/activity/gq_pic09.jpg" style="padding-top:30px;" /></div></a>
          <div class="info"> 
              <p class="f24 mt10">1、登录后，在平台左上角，记下自己的用户名</p>
              <p class="tc"><img src="images/activity/gq_pic05.jpg" width="855" height="475" /></p>
              <p class="f24">2、将您的【用户名】发给好友，请他注册时填写</p>
              <p class="tc"><img src="images/activity/gq_pic06.jpg"/></p>
          </div>
      </div>
      </div>
    </div>
<div class="tc footer">
<a href="queryAboutInfo.mht?msg=4">公司介绍</a>&nbsp;|&nbsp;<a href="queryAboutInfo.mht?msg=7">联系我们</a>&nbsp;|&nbsp;<a href="callcenter.mht">帮助中心</a>&nbsp;|&nbsp;<a href="capitalEnsure.mht">本金保障</a><p>版权所有 © 2013 深圳市易付通投资管理有限公司<a href="http://www.sanhaodai.com">www.sanhaodai.com</a> 华融金融集团备案号： 粤ICP备14016299号保障</p></div>
<script type="text/javascript" src="script/jquery-2.0.js"></script>
<script type="text/javascript" src="script/jquery.dispatch.js"></script>
<script type="text/javascript" src="script/nav-zh.js"></script>
<script type="text/javascript" src="script/jquery.zclip.min.js"></script>
<script>

function dianji(){
	var session = '<%=session.getAttribute("user")%>';
	if (session == 'null'){
		window.location.href='reg.mht';
		return ;
	}else{
		alert("您已登录，请先安全退出！")
		return;
	}
}

function logout(){
	$.dispatchPost("logout.mht?",param,function(data){
		window.location.reload();
     });
	
}

</script>


	



</body>
</html>
