<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title><%=IConstants.SEO_TITLE %></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta name="keywords" content="<%=IConstants.SEO_KEYWORDS%>">
    <meta name="description" content="<%=IConstants.SEO_DESCRIPTION %>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%=IConstants.SEO_OTHERTAGS %>
<style type="text/css">
/*top_bar*/
html, body, div, p, h1, h2, h3, h4, h5, h6, blockquote,ol, ul, li, dl, dt, dd, form, fieldset, legend, button, input, textarea, pre, code, th, td{margin:0; padding:0;font-family:"Microsoft Yahei";font-family:"微软雅黑";}img{border:none;}
ul, ol, li{list-style:none;}
button, input, select, textarea{color:#333; font-family:"Microsoft Yahei";}
a{outline:none;text-decoration:none;color:#333;}
a:active{noOutline:expression(this.onFocus=this.blur());}
:focus{outline:0;}
a:hover{color:#ff6666;text-decoration:none;}
.clearfix:after {content:"\200B";display:block;height:0;clear:both;}
.clearfix {*zoom:1;}
.clear{clear:both;line-height:0;}
.fr{float:right;}.fl{float:left;}.red{color:#ff000a;}.tc{ text-align:center;}.f24{ font-size:20px;}
.mr5{ margin-right:5px;}.mt40{ margin-top:40px;}.mt10{margin-top:10px;}.mt60{margin-top:60px;}.mb20{margin-bottom:30px;}.mt20{margin-top:20px;}

.dec_banner{background:url(activity/award20141208/images/dec_banner.jpg) no-repeat top center;width:2000xp;height:513px;}
.big_one_bj{background:#fff9d6;padding:20px 0 ;border-bottom:1px solid #fff;}
.big_two_bj{background:#ffd954;padding:20px 0 ;border-bottom:1px solid #fff;}
.one_content{ background:url(activity/award20141208/images/dec_pic01.jpg) no-repeat top center;width:1000px; margin:0 auto;height:429px;}
.one_content img{margin:300px 0 0 20px;}
.two_content{width:1000px; margin:0 auto;}
.two_content .btn_tc{ margin:20px 0 0 300px;}
.big_three_bj{ background:#fea200;padding:20px 0 ;border-bottom:1px solid #fff;}
.big_three_bj .two_content h1{font-size:30px; padding:0 0 30px 10px; display:block;color:#fff;}
.dec_notice{float:left; margin:0 0 15px 10px;}
.dec_notice li{float:left;width:990px;line-height:35px;color:#fff;font-size:16px;}
.dec_notice li span{background:url(activity/award20141208/images/icon_bk.png) no-repeat center;font-size:16px; font-family:Arial, Helvetica, sans-serif;width:21px;height:21px;color:#fe8f00;display:block;text-align:center;float:left;line-height:21px;font-weight:bold; margin:7px 10px 0 0;}
.big_four_bj{ background:#fffcf6;padding:20px 0 ;border-bottom:1px solid #fff;} 
.grey_color{color:#333;}
.w1000{width:1000px; margin:0 auto;}
.dec_btn02{padding:397px 0 0 638px;}
.big_five_bj{ background:#fff;padding:20px 0 ;border-bottom:1px solid #fff;}
.w382{width:475px; margin-left:10px;}h2{color:#d0211c; margin-bottom:8px;}
.ranking{background:#fffbe8;border:1px solid #fae6c0;}
.ranking li{ line-height:42px;height:42px;border-bottom:1px dashed #99978b;float:left;width:100%;}
.ranking li div{float:left; margin:0 8px; text-align:center;}.list_one{width:60px;}.list_two{width:140px;}.list_three{width:150px;}
.ranking li.tit{ background:url(activity/award20141208/images/nov_37.png) repeat-x top left; width:100%;height:52px; line-height:52px;color:#fff;font-size:18px;border-bottom:1px solid #fae5bd;}
.ranking li .nub{display:block; border-radius:8px;background:#cfc4aa;height:30px;width:30px; line-height:30px;margin:6px 0 0 18px;color:#fff;}
.ranking li .nub_one{background:#ff1800;}.ranking li .nub_two{background:#f0c400;}.ranking li .nub_three{background:#ffa188;}
.population{ margin-top:15px;font-size:18px;}
.dec_packet{background:url(activity/award20141208/images/dec_pic02.jpg) no-repeat;width:1000px;height:85px;}
.dec_packet span{float:right; line-height:45px;background:#fff;color:#333;padding:0 20px; margin-top:15px;}
.dec_packet span b{color:#F00;font-size:18px;}
.six_content{background:url(activity/award20141208/images/dec_pic04.jpg) no-repeat;width:1000px;height:525px;margin:10px auto;}
.get_money{width:500px;float:right;margin:120px 80px 0 0;}
.get_money .dec_tit{color:#fff;font-size:16px;float:right; display:block;clear:both; text-align:center; line-height:90px;}
.get_money .dec_tit b{font-size:24px;}
.get_money .dec_tit b a{color:#fff;}
</style>
</head>
<jsp:include page="/include/top.jsp"></jsp:include> 
<body>
<div class="dec_banner">
  <div class="w1000"><a href="#002"><img src="activity/award20141208/images/dec_btn02.png" width="150" height="23" class="dec_btn02"/></a></div>
</div>

<div class="big_one_bj clearfix">
  <div class="one_content"><a href="reg.mht"  target="_blank"><img src="activity/award20141208/images/dec_btn.gif" width="441" height="77" /></a></div>
</div>

<div class="big_two_bj clearfix">
  <div class="two_content">
    <div class="dec_packet"><span>我获得的红包奖励：<s:if test="#session.user != null"><b><s:if test="#request.map==null">0元</s:if><s:else>${map.amount*2}元</s:else></b></s:if><s:else><b><a href="login.mht"  target="_blank" class="red">未登录</a></b></s:else></span></div>
    <img src="activity/award20141208/images/dec_pic03.jpg" width="1000" height="305" class="mt10"/>
    <a href="#001"><img src="activity/award20141208/images/dec_btn01.gif" width="441" height="96" class="btn_tc"/></a>
  </div>
</div>  

<div class="big_one_bj clearfix">
  <div class="six_content">
      <div class="get_money clearfix">
          <p class="dec_tit">我获得的推荐奖励总额<br/><b><s:if test="#session.user != null"><s:if test="#map2!=null">￥${map2.e }</s:if><s:else>￥0</s:else></s:if><s:else><a href="login.mht"  target="_blank" >未登录</a></s:else></b></p>     
       </div>
  </div>
</div> 

<div class="big_three_bj clearfix">
  <div class="two_content">
     <a name="002" id="#002"><h1>活动说明</h1></a>
     <ul class="dec_notice">
        <li><span>1</span>活动时间：2014年12月9日至2014年12月31日</li>
        <li><span>2</span>平台会员注册的角色分为：会员A，会员B（A推荐）；</li>
        <li><span>3</span>奖励只支持一级奖励：A推荐B成功注册并投标，奖励A 2元/个，奖励上不封顶；</li>
        <li><span>4</span><b>推荐注册的会员必须是在活动期间注册的有效会员，有效会员是指成功注册并投标（不包含体验标）的会员；</b></li>
        <li><span>5</span>推荐好友注册奖励是系统自动根据注册时填写的推荐人统计，若未填写，该奖励不予计算；</li>
        <li><span>6</span>平台会在活动期结束后的5个工作日内，将奖金直接发放到推荐人的平台账户上；</li>
        <li><span>7</span>被推荐人投资期不满一个月不予奖励；</li>
        <li><span>8</span><b  class="red">活动期间用户参与的投资不能进行债权转让。</b></li>
        <li><span>9</span>活动期间体验劵数量有限，每个新用户注册只能领取一张体验劵，先注册先得，送完即止；</li>
        <li><span>10</span>体验劵使用有效期截止日期为2014年12月31日；</li>
        <li><span>11</span>内部员工不得参与该活动。</li>
        <li><span>12</span>在法律允许的范畴内，本活动最终解释权归深圳市易付通金融信息技术有限公司 所有。</li>
     </ul>
  </div>
</div> 

<div class="big_four_bj clearfix">
  <a name="001" id="#001"><div class="two_content">
    <h1 class="grey_color">如何邀请好友</h1>
    <img src="activity/award20141208/images/dec_pic05.jpg" width="1000" height="383" class="mt20"/> 
  </div></a>
</div>

<jsp:include page="/include/footer.jsp"></jsp:include>
</body>
</html>
