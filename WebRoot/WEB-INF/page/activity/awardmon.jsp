<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>【三好资本】迎端午，送豪礼！粽情粽意，一惠千三收益额外加，二惠千元大奖任你拿</title>

<style type="text/css">
/*top_bar*/
html, body, div, p, h1, h2, h3, h4, h5, h6, blockquote,ol, ul, li, dl, dt, dd, form, fieldset, legend, button, input, textarea, pre, code, th, td{margin:0; padding:0;font-family:"Microsoft Yahei";font-family:"微软雅黑";}img{border:none;color:#804633;}
img,a{border:none;}
.body_content{background: #fff4de}
ul, ol, li{list-style:none;}
a{color:#86533d; text-decoration:none;}
a:hover{color:#d01c1c;}
button, input, select, textarea{color:#333; }
.clearfix:after {content:"\200B";display:block;height:0;clear:both;}
.clearfix {*zoom:1;}
.clear{clear:both;line-height:0;}
.fr{float:right;}.fl{float:left;}.red{color:#ff000a;}.tc{ text-align:center;}.f16{ font-size:16px;}.f20{ font-size:20px;}.f24{ font-size:20px;}.f30{ font-size:30px;}
.mr5{ margin-right:5px;}.mt40{ margin-top:40px;}.mt15{margin-top:15px;}.mt60{margin-top:60px;}.mb20{margin-bottom:30px;}
.pt30{padding-top:40px;}.pb30{padding-bottom:40px;}.pt5{padding-top:5px;}.pb20{padding-bottom:9px;}
.jan_red{color:#dc3250;}

.jan_banner{ display:block;width:100%;height:553px;}
.w1010{width:1010px; margin:0 auto;}
.jan_ny{padding:400px 0 0 380px;} 
.jan_h1{font-size:24px;color:#fff4de;}
.jan_h1 span{ background:#ef6565;padding:0px 6px; margin:0 3px;font-size:32px;}
.jan_content{background:#fff4de;width:100%;}
.jan_gird_one{background:#fff4de;padding:20px;width:994px;margin:30px auto 0 auto;border:3px #e5363c dashed;}
.jan_lipin{padding:10px 20px;}
.jan_lipin li{line-height:50px;float:left;font-size:22px;}
.jan_lipin li p{line-height:24px;}
.jan_lipin li b{color:#d01c1c;font-size:30px;}
.jan_lipin li i{float:left;}
.jan_icn02{ background:url(activity/20150112/images/jan_04.png) no-repeat; display:block;width:34px;height:35px; margin:5px 5px 0 0;}
.jan_icn01{ background:url(activity/20150112/images/jan_05.png) no-repeat; display:block;width:33px;height:25px; margin:13px 5px 0 0;}
.list1{margin-left:140px;}
.list2{margin-left:150px;}
.jan_yaoqing,.jan_guize{margin-top:40px;}
.w1002{width:1080px; margin:0 auto}.w1023{width:1023px; margin:10px auto 0 auto}
/*抽奖*/
.jan_wrap{padding-top:30px;}
.jan_top{}
.jan_top .get{border:1px solid #edca84; background:url(activity/20150112/images/jan_bj.jpg) repeat-x;height:147px;}
.jan_top .get li{float:left; display:block; background:url(activity/20150112/images/jan_line.jpg) no-repeat right top;padding:20px 0px 20px 5px; width:200px;}
.jan_top .get li .icon{width:26px; margin-right:3px;}
.jan_top .get li .ny_font{width:165px;font-size:13px;}
.btn{background:#e62248; cursor:pointer;text-decoration:none;border-bottom:2px solid #bd424b; display:block; clear:both;width:165px; text-align:center; margin:0 auto;color:#fff; line-height:42px;height:42px;font-size:18px;}
.btn:hover{background:#e62248;border-bottom:none;line-height:44px;height:44px;text-decoration:none;display:block; clear:both;width:165px;color:#fff;}
/*转盘*/
.turnplate{ background:url(activity/20150112/images/jan_22.jpg) repeat;width:522px;height:430px;}
#lottery{width:574px;height:584px;padding:10px;}
#lottery table td{width:133px;height:133px;text-align:center;vertical-align:middle;font-size:24px;color:#333;font-index:-999;}
#lottery table td a{width:326px;height:89px;display:block;text-decoration:none;}
#lottery table td a img{padding:5px 0 0 5px;}
#lottery table td p{font-size:16px;}
#lottery table td.active{background:#ff5e62;}
.bjone{ background:#ffe6b3;} .bjtwo{background:#ffbe3d;}.bjthree{ background:#f8efde;}
/*排名*/
.jan_winning{width:294px;}.jan_winning .tp{width:402px;}
.txtMarquee-top1{ overflow:hidden; position:relative;}
.txtMarquee-top1 .hd{ overflow:hidden;  height:60px; background:#f4f4f4; padding:0 10px;}
.txtMarquee-top1 .bd{width:400px;height:330px;border:1px solid #eecc98; border-top:none;}
.txtMarquee-top1 .infoList li{ padding:5px auto; height:40px; margin-top:3px;font-size:14px;border-bottom:1px dashed #dbceb3;}
.txtMarquee-top1 .infoList li div{float:left; text-align:center;}
.txtMarquee-top1 .infoList li{ line-height:40px;}
.txtMarquee-top1 .infoList li .date{ float:right; color:#999;  }
.txtMarquee-top1 .bd .title{line-height:50px; height:50px;}
.txtMarquee-top1 .bd .title span{float:left;font-size:20px; text-align:center; color:#da262b; font-weight:bold;}
.txtMarquee-top1 .bd .name1{width:140px;display:block;}
.txtMarquee-top1 .bd .award1{width:60px;display:block;}
.txtMarquee-top1 .bd .time1{width:200px;display:block;}

.jan_tit h1{font-size:36px;color:#c51318;}.jan_tit i{margin-right:8px;}
.jan_yaoqing .cont{border-top:none;margin: 30px auto;}
.jan_yaoqing .cont .around{padding:20px 0px 20px 15px; font-family:"宋体";font-size:15px;}
.jan_yaoqing .cont .around li{width:228px; float:left; line-height:18px;color:#804633; margin:8px 8px;}
.jan_yaoqing .cont .around li img{float:left;}
.jan_yaoqing .cont .around li .one{width:26px; float:left; margin-right:3px;}.jan_yaoqing .cont .around li .two{width:190px;float:left;}
.jan_yaoqing .cont .around li .font{height:60px;} .jan_yaoqing .cont .around li .pic{ margin-top:18px;}
.jan_guize .cont{border:1px solid #eecc98;margin:30px auto; background:url(activity/20150112/images/jan_50.png) no-repeat bottom right;}
.jan_rule{padding:20px;width:770px;}
.jan_rule li{font-size:16px; font-family:"微软雅黑";color:#000; line-height:32px;}
.jan_rule li .blod{font-weight:bold;color:#000;font-size:16px;}
.jan_rule li .number{ text-align:center;color:#ffffff; margin:7px 15px 0 0;float:left; background:url(activity/20150112/images/jan_34.png) no-repeat 0 0px; width:20px;height:20px; line-height:20px; font-size:16px; display:block;}
.jan_rule li .info{margin:0px 0 0 35px;}.jan_rule li .info p{ line-height:35px; font-family:"微软雅黑";color:#000;}
.naving{ background:url(activity/20150112/images/jan_28.png) no-repeat; display:block;width:142px;height:212px;padding-left:70px;}
.qzone_wb,.sina_wb,.qq_wb{ background:url(activity/20150112/images/jan_wb.png) no-repeat;display:block;width:25px;height:25px;line-height:25px; float:left;margin:150px 2px 0 0; cursor:pointer;}
.qzone_wb{ background-position:0 0;}.sina_wb{ background-position:-28px 0;}.qq_wb{ background-position:-55px 0;}
.qzone_wb:hover{ background-position:0 -30px;}.sina_wb:hover{ background-position:-28px -30px;}.qq_wb:hover{ background-position:-55px -30px;}
 .float_block{ position:fixed; right:50%; margin-right:520px; top:0px;_position: absolute; _top:expression(150px+((e=document.documentElement.scrollTop)?e:document.body.scrollTop)+'px');}
  
.popup_bg{ background:#000; width:100%; height:100%; position:fixed; top:0px; left:0px; z-index:9999; filter:alpha(opacity=30);-moz-opacity:.30;opacity:0.3; _position:absolute; _height:2000px;}
.popup_tips{ width:477px;background:#fffef2;z-index:99999;position: fixed;left:50%;top:50%;_position: absolute; _top:expression(200+((e=document.documentElement.scrollTop)?e:document.body.scrollTop)+'px');z-index:100000;
}
.popup_tips .topbj{ background:url(images/jan_top_24.png) no-repeat;width:477px;height:23px; margin-top:-23px;}
.popup_tips .topbj01{ background:url(images/jan_47.png) no-repeat;width:345px;height:23px; margin-top:-23px;}
.popup_tips .popup_close{border-bottom:1px solid #f9aaaf;text-align:left;padding:15px 10px;}
.popup_tips .popup_close a{float:right;width:33px; height:32px; line-height:32px;display:inline-block; background:url(activity/20150112/images/close.png) no-repeat; text-indent:20000px; overflow:hidden; margin:5px 5px 0 0;}
.popup_tips .con{ padding:20px;}
.popup_tips .con table tr td{ text-align:center;font-size:15px; line-height:30px;border-bottom:1px dashed #ddd;}
.popup_tips .popup_btn{ margin:10px 0 20px 0; text-align:center; margin-left:-17px;}
.popup_tips .popup_btn a{ width:80px; height:30px; display:inline-block; line-height:30px; text-align:center; color:#fff; margin:0 5px; font-size:14px;}
.popup_tips .popup_btn .popup_btn1{ background:#a0a0a0;font-size:16px;}
table{max-width:100%;background-color:fff4de;border-collapse:collapse;border-spacing:0;}
.table{width:100%;margin-bottom:20px;}.table th,.table td{background:#fff4de;padding:8px;line-height:20px;vertical-align:top;border-top:1px solid #eecc98;}
.table th{font-weight:bold;}
.table thead th{vertical-align:bottom;}
.table caption+thead tr:first-child th,.table caption+thead tr:first-child td,.table colgroup+thead tr:first-child th,.table colgroup+thead tr:first-child td,.table thead:first-child tr:first-child th,.table thead:first-child tr:first-child td{border-top:0;}
.table tbody+tbody{border-top:2px solid #eecc98;}
.table .table{background-color:#fff4de;}
.table-condensed th,.table-condensed td{padding:4px 5px;}
.table-bordered{border:1px solid #eecc98;border-collapse:separate;*border-collapse:collapse;border-left:0;-webkit-border-radius:4px;-moz-border-radius:4px;border-radius:4px;}.table-bordered th,.table-bordered td{border-left:1px solid #eecc98;}
.table-bordered caption+thead tr:first-child th,.table-bordered caption+tbody tr:first-child th,.table-bordered caption+tbody tr:first-child td,.table-bordered colgroup+thead tr:first-child th,.table-bordered colgroup+tbody tr:first-child th,.table-bordered colgroup+tbody tr:first-child td,.table-bordered thead:first-child tr:first-child th,.table-bordered tbody:first-child tr:first-child th,.table-bordered tbody:first-child tr:first-child td{border-top:0;}
.table-bordered thead:first-child tr:first-child>th:first-child,.table-bordered tbody:first-child tr:first-child>td:first-child,.table-bordered tbody:first-child tr:first-child>th:first-child{-webkit-border-top-left-radius:4px;-moz-border-radius-topleft:4px;border-top-left-radius:4px;}
.table-bordered thead:first-child tr:first-child>th:last-child,.table-bordered tbody:first-child tr:first-child>td:last-child,.table-bordered tbody:first-child tr:first-child>th:last-child{-webkit-border-top-right-radius:4px;-moz-border-radius-topright:4px;border-top-right-radius:4px;}
.table-bordered thead:last-child tr:last-child>th:first-child,.table-bordered tbody:last-child tr:last-child>td:first-child,.table-bordered tbody:last-child tr:last-child>th:first-child,.table-bordered tfoot:last-child tr:last-child>td:first-child,.table-bordered tfoot:last-child tr:last-child>th:first-child{-webkit-border-bottom-left-radius:4px;-moz-border-radius-bottomleft:4px;border-bottom-left-radius:4px;}
.table-bordered thead:last-child tr:last-child>th:last-child,.table-bordered tbody:last-child tr:last-child>td:last-child,.table-bordered tbody:last-child tr:last-child>th:last-child,.table-bordered tfoot:last-child tr:last-child>td:last-child,.table-bordered tfoot:last-child tr:last-child>th:last-child{-webkit-border-bottom-right-radius:4px;-moz-border-radius-bottomright:4px;border-bottom-right-radius:4px;}
.table-bordered tfoot+tbody:last-child tr:last-child td:first-child{-webkit-border-bottom-left-radius:0;-moz-border-radius-bottomleft:0;border-bottom-left-radius:0;}
.table-bordered tfoot+tbody:last-child tr:last-child td:last-child{-webkit-border-bottom-right-radius:0;-moz-border-radius-bottomright:0;border-bottom-right-radius:0;}
.table-bordered caption+thead tr:first-child th:first-child,.table-bordered caption+tbody tr:first-child td:first-child,.table-bordered colgroup+thead tr:first-child th:first-child,.table-bordered colgroup+tbody tr:first-child td:first-child{-webkit-border-top-left-radius:4px;-moz-border-radius-topleft:4px;border-top-left-radius:4px;}
.table-bordered caption+thead tr:first-child th:last-child,.table-bordered caption+tbody tr:first-child td:last-child,.table-bordered colgroup+thead tr:first-child th:last-child,.table-bordered colgroup+tbody tr:first-child td:last-child{-webkit-border-top-right-radius:4px;-moz-border-radius-topright:4px;border-top-right-radius:4px;}
.table-striped tbody>tr:nth-child(odd)>td,.table-striped tbody>tr:nth-child(odd)>th{background-color:#fff4de;}
.table-hover tbody tr:hover>td,.table-hover tbody tr:hover>th{background-color:#fff4de;}
table td[class*="span"],table th[class*="span"],.row-fluid table td[class*="span"],.row-fluid table th[class*="span"]{display:table-cell;float:none;margin-left:0;}
.table td.span1,.table th.span1{float:none;width:44px;margin-left:0;}
.table td.span2,.table th.span2{float:none;width:124px;margin-left:0;}
.table td.span3,.table th.span3{float:none;width:204px;margin-left:0;}
.table td.span4,.table th.span4{float:none;width:284px;margin-left:0;}
.table td.span5,.table th.span5{float:none;width:364px;margin-left:0;}
.table td.span6,.table th.span6{float:none;width:444px;margin-left:0;}
.table td.span7,.table th.span7{float:none;width:524px;margin-left:0;}
.table td.span8,.table th.span8{float:none;width:604px;margin-left:0;}
.table td.span9,.table th.span9{float:none;width:684px;margin-left:0;}
.table td.span10,.table th.span10{float:none;width:764px;margin-left:0;}
.table td.span11,.table th.span11{float:none;width:844px;margin-left:0;}
.table td.span12,.table th.span12{float:none;width:924px;margin-left:0;}
.table tbody tr.success>td{background-color:#fff4de;}
.table tbody tr.error>td{background-color:#fff4de;}
.table tbody tr.warning>td{background-color:#fff4de;}
.table tbody tr.info>td{background-color:#fff4de;}
.table-hover tbody tr.success:hover>td{background-color:#d0e9c6;}
.table-hover tbody tr.error:hover>td{background-color:#ebcccc;}
.table-hover tbody tr.warning:hover>td{background-color:#faf2cc;}
.table-hover tbody tr.info:hover>td{background-color:#c4e3f3;}
thead {
  display: table-header-group;
  vertical-align: middle;
  border-color: inherit;
}
</style>
<script>

	function AutoResizeImage(maxWidth, maxHeight, objImg) {
		maxWidth = screen.width - 18;
		var img = new Image();
		img.src = objImg.src;
		var hRatio;
		var wRatio;
		var Ratio = 1;
		var w = img.width;
		var h = img.height;
		wRatio = maxWidth / w;
		hRatio = maxHeight / h;
		if (maxWidth == 0 && maxHeight == 0) {
			Ratio = 1;
		} else if (maxWidth == 0) {//
			if (hRatio < 1)
				Ratio = hRatio;
		} else if (maxHeight == 0) {
			if (wRatio < 1)
				Ratio = wRatio;
		} else if (wRatio < 1 || hRatio < 1) {
			Ratio = (wRatio <= hRatio ? wRatio : hRatio);
		}
		if (Ratio < 1) {
			w = w * Ratio;
			h = h * Ratio;
		}
		objImg.height = h;
		objImg.width = w;
	}
</script>
</head>

<body>

<s:include value="/include/top.jsp"></s:include>
<div class="body_content">
<!--我的奖品弹窗-->
<div id="bgpop" class="popup_bg" style="display:none;"></div>
<div id="bgtips" class="popup_tips" style="width:477px; margin:-120px 0 0 -238px;display:none;" >
    <div class="topbj"><img src="activity/20150112/images/jan_top_24.png" width="477" height="23" /></div>
	<div class="popup_close"><b class="f20 jan_red">我的奖品</b> <a href="#" onclick="refreshme2();">关闭</a></div>
    <div class="con"  style="height:170px;overflow-x:hidden;overflow-y:scroll">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0"   >
              <tr style="font-size:16px; font-weight:bold;">
                <td>序号</td>
                <td>奖品</td>
                <td>中奖时间</td>
                <td>发放状态</td>
              </tr>
              <s:iterator  var="d"  value="#request.myAwardDetail"  status="s">
              <tr>
                <td>${s.index+1 }</td>
                <td>${d.awardName}</td>
                <td>${d.createTime} </td>
                <td>${d.sendAwardStr}</td>
              </tr>
              </s:iterator>
            </table>    
    </div>
    <div class="popup_btn pb10 pt10 clear"><a href="#" onclick="refreshme2();" class="popup_btn1">关闭</a></div>
</div>
<!--end 我的奖品弹窗 -->

<!-- 抽奖成功start -->
<div id="sbg" class="popup_bg" style="display:none;"></div>
<div id="stips" class="popup_tips " style="width:345px; margin:-120px 0 0 -125px;display:none;" >
    <div class="topbj01"><img src="activity/20150112/images/jan_47.png"/></div>
	<div class="popup_close" style="border-bottom:none;"><a href="#" onclick="refreshme()" >关闭</a></div>
    <div class="con">
    	<p class="tc"><span id="fuyou">恭喜您${user.userName}！</span></p>
        <p class="tc" id="pmonId">抽中<b class="jan_red"><span id="tmonId">[谢谢参与，红包还在包...]</span></b></p>
    </div>
    <div class="popup_btn pb10 pt10 clear">
        <!-- input type="button" value="再次抽奖" class="popup_btn" onclick="awardAgain();"/>  -->
        <a href="#" onclick="refreshme()" class="popup_btn1">关闭</a>
    </div>
</div>
<!-- 抽奖成功end -->




<div class="float_block"><a href="#activityrules"><img src="activity/20150112/images/jan_02.png" width="80" height="173" /></a></div>
<img src="activity/20150112/images/banner_1.jpg" onload="javascript:AutoResizeImage(1000,0,this)"/>
 <!--  <div class="w1010">
     <div class="jan_ny"> </div>
   </div>   -->
<div class="jan_content">
  <!--抽奖次数-->
  <div class="jan_gird_one">
     <!--未登录-->
     <s:if test="#session.user==null">
         <div class="tc f30 dl_btn" style="display:block;">请您<a href="login.mht" class="jan_red">登录</a>后抽奖</div>
     </s:if>
     <s:else>
     <ul class="jan_lipin clearfix">
         <li class="list1">
           <i class="jan_icn01"></i> 我剩余抽奖次数：<b>${scoreMap.sumScore }</b>
         </li>
         <li class="list2"><i class="jan_icn02"></i><a href="#" onclick="myAward();">我的奖品</a> </li>
     </ul>
     </s:else>
  </div>
   
  <!--抽奖--> 
  <div class="w1002 jan_wrap clearfix">
    <!--左侧-->
    <div class="fl clearfix">
      
      <div class="turnplate">
         <div id="lottery">
            <table border="0" cellpadding="0" cellspacing="1">
                <tr>
                    <td class="lottery-unit lottery-unit-0 bjone" data="${awardInfoList[0].id}"><img src="${awardInfoList[0].picAddr}"></td>
                    <td class="lottery-unit lottery-unit-1 bjtwo" data="${awardInfoList[1].id}"><img src="${awardInfoList[1].picAddr}"></td>
                    <td class="lottery-unit lottery-unit-2 bjone" data="${awardInfoList[2].id}"><img src="${awardInfoList[2].picAddr}"></td>
                </tr>
                <tr>
                   <td class="lottery-unit lottery-unit-7 bjtwo" data="${awardInfoList[3].id}"><img src="${awardInfoList[3].picAddr}"></td>
                    <td  class="bjthree"><a  herf="#"  style="cursor: pointer;background: url(activity/20150112/images/jan_45_2.png) no-repeat;width: 166px;  height: 133px"></a>
                        <p style="display:block;"></p></td>
                    <td class="lottery-unit lottery-unit-3 bjtwo" data="${awardInfoList[4].id}"><img src="${awardInfoList[4].picAddr}"></td>
                </tr>
                <tr>
                    <td class="lottery-unit lottery-unit-6 bjone" data="${awardInfoList[5].id}"><img src="${awardInfoList[5].picAddr}"></td>
                    <td class="lottery-unit lottery-unit-5 bjtwo" data="${awardInfoList[6].id}"><img src="${awardInfoList[6].picAddr}"></td>
                    <td class="lottery-unit lottery-unit-4 bjone" data="${awardInfoList[7].id}"><img src="${awardInfoList[7].picAddr}"></td>
                </tr>
            </table>
        </div>
      </div>
    </div>
    
    <!--右侧-->
    <div class="fr winning">
      <div class="tp" style="text-align: center;"><img src="activity/20150112/images/jan_12.png"/></div>
      <div class="txtMarquee-top1">
            <div><img src="activity/20150112/images/jan_17.png" width="402" height="33" /></div>
			<div class="bd clearfix">  
                <div class="title clearfix">
                  <span class="name1">用户名</span>
                  <span class="award1">奖品</span>
                  <span class="time1">时间</span>
                </div>
				<ul class="infoList">
				
				<s:iterator var="bean" value="#request.list" status="sta">
                   
                   <li>
                       <div class="name1"> ${bean.username} </div>
                       <div class="award1">${bean.awardName}</div>
                       <div class="time1"><p>${bean.createTime}</p><!-- p>${bean.createTime}</p> --></div>  
                    </li>
                </s:iterator>   
				
				</ul>
			</div>
	  </div>
    </div>  
  </div>
  
  <!--邀请好友,赚好币-->  
  <div class="jan_yaoqing clearfix">
      <div class="w1002 jan_tit"><a name="invitefriends" id="#invitefriends"><i class="fl"><img src="activity/20150112/images/jan_08.png" /></i><h1 style="font-size: 35px;">奖励说明</h1></a></div>
      <div class="cont w1002 clearfix">
        <table class="table table-bordered">
        	<thead align="center">
        		<tr align="center">
        			<th>用户群体</th>
        			<th>单个标累计投资金额(x)</th>
        			<th>投资期限(y)</th>
        			<th>奖励红包</th>
        			<th>对应抽奖次数</th>
        			<th>抽奖红包（元）</th>
        		</tr>
        	</thead>
        	<tbody>
        		<tr>
        			<td align="center">新用户</td>
        			<td>首次投资，无金额限制</td>
        			<td align="center">y=1个月</td>
        			<td rowspan="7"style="width:80px;text-align: center;padding-top: 95px;">按月额外奖励0.1%（例如:3月标加奖为0.3%,6月标加奖为0.6%）</td>
        			<td align="center">1次</td>
        			<td>2，5，10，20，30，50，100，150</td>
        		</tr>
        		<tr>
        			<td rowspan="7"style=" text-align: center;padding-top: 95px;">新、老用户</td>
        			<td>3000≤x≤5000元</td>
        			<td align="center">y≥3个月</td>
        			<td align="center">1次</td>
        			<td>2，5，10，15，20，30，50，100</td>
        		</tr>
        		<tr >
        			<td>5001≤x≤10000元</td>
        			<td align="center">y≥3个月</td>
        			<td align="center">1次</td>
        			<td>5，10，15，20，30，50，100，200</td>
        		</tr>
        		<tr>
        			<td>10001≤x≤50000元</td>
        			<td align="center">y≥3个月</td>
        			<td align="center">2次</td>
        			<td>5，10，15，20，30，50，100，300</td>
        		</tr>
        		<tr >
        			<td>50001≤x≤100000元</td>
        			<td align="center">y≥3个月</td>
        			<td align="center">3次</td>
        			<td>5，10，15，20，30，50，100，500</td>
        		</tr>
        		<tr>
        			<td>100001≤x≤300000元</td>
        			<td align="center">y≥3个月</td>
        			<td align="center">4次</td>
        			<td>10，20，30，50，100，200，500，1000 </td>
        		</tr>
        		<tr>
        			<td>300000以上</td>
        			<td align="center">y≥3个月</td>
        			<td align="center">5次</td>
        			<td>20，30，50，100，200，500，800，1000 </td>
        		</tr>
        	</tbody>
        </table>
      </div>
  </div>
   
  <!--活动规则--> 
  <div class="jan_guize clearfix">
      <div class="w1002 jan_tit"><a name="activityrules" id="#activityrules"><i class="fl pt5"><img src="activity/20150112/images/jan_21.png"/></i><h1>活动规则</h1></div>
      <div class="cont w1002 clearfix">
          <ul class="jan_rule">
             <li>
                <div class="blod"><span class="number">1</span>活动时间：2015-06-17 20:00:00至2015-07-14 18:00:00</div>
             </li>
             <li>
                <div class="blod"><span class="number">2</span>活动规则：</div>
                <div class="info">
                  <p>• 新用户首次投资，将享受此次活动基础上，可不受投标金额上的限制即可获额外的红包奖励+幸运抽奖机会1次；（红包及抽奖次数均在满标后发放并参与抽奖）。
                  </p>
                  <p>• 新老用户根据投资额度和投资期限即可获得额外的红包奖励和对应的抽奖次数；（红包及抽奖次数均在满标后发放并参与抽奖）。
                  </p>
                  <p>• 活动期间，按单个标的累计投资金额来计算对应的活动奖励，每个标的都可参与一次活动。</p>
                  <p>•活动期间获得奖励（红包和抽奖次数）均在标的满标后发放，红包可全额投标，需投标后才可提现。</p>
                  <p>• 每次抽奖需消耗一次抽奖机会，每天抽奖次数不限。</p>
                  <p>• 所有获得的活动奖励需在活动期限（2015-06-17 20:00:00至2015-07-14 18:00:00）内使用，逾期无效清零。活动作弊者，一经发现，扣除所有奖励；</p>
                </div>
             </li>
             <li>
                <div class="blod"><span class="number">3</span>本活动最终解释权归深圳市易付通金融信息技术有限公司所有。</div>
             </li>
          </ul>
      </div>
  </div> 
</div>
</div>
<s:include value="/include/footer.jsp"></s:include>
<script type="text/javascript" src="js/jquery.SuperSlide.2.1.js"></script>

<script type="text/javascript">
  $(".txtMarquee-top1").slide({mainCell:".bd ul",autoPlay:true,effect:"topMarquee",vis:6,interTime:50});
</script> 
<script type="text/javascript">
var lottery={
	index:-1,
	count:0,
	timer:0,	
	speed:20,	
	times:0,	
	cycle:50,	
	prize:-1,	
	init:function(id){
		if ($("#"+id).find(".lottery-unit").length>0) {
			$lottery = $("#"+id);
			$units = $lottery.find(".lottery-unit");
			this.obj = $lottery;
			this.count = $units.length;
			$lottery.find(".lottery-unit-"+this.index).addClass("active");
		};
	},
	roll:function(){
		var index = this.index;
		var count = this.count;
		var lottery = this.obj;
		$(lottery).find(".lottery-unit-"+index).removeClass("active");
		index += 1;
		if (index>count-1) {
			index = 0;
		};
		$(lottery).find(".lottery-unit-"+index).addClass("active");
		this.index=index;
		return false;
	},
	stop:function(index){
		this.prize=index;
		return false;
	}
};
var prizeName = "";
function roll(){
	lottery.times += 1;
	lottery.roll();
	if (lottery.times > lottery.cycle+10 && lottery.prize==$(".lottery-unit-"+lottery.index).attr("data")) {
		clearTimeout(lottery.timer);
		lottery.prize=-1;
		lottery.times=0;
		click=false;
		$("#sbg").show();//="none";
		$("#stips").show();
		//alert("恭喜您，获得了:【"+prizeName+"】"); // TODO
		//$("#tmonId").html("【"+prizeName+"】");
		//抽中<b class="jan_red"><span id="tmonId">[谢谢参与，红包还在包]</span></b>红包
		if (prizeName == "谢谢参与"){
			$("#fuyou").html(" ");
			$("#pmonId").html("<b class='jan_red'><span>谢谢参与，红包还在包 <img src='activity/20150112/images/ing.gif'/></span></b>");
		} else {
			$("#pmonId").html("抽中<b class='jan_red'><span>【"+prizeName+"】</span></b>红包!");
		}
		
		//window.location.reload();
	}else{
		if (lottery.times<lottery.cycle) {
			lottery.speed -= 10;
		}else if(lottery.times==lottery.cycle) {
			var index = Math.random()*(lottery.count)|0;
		}else{
			if (lottery.times > lottery.cycle+10 && ((lottery.prize==0 && lottery.index==7) || lottery.prize==lottery.index+1)) {
				lottery.speed += 110;
			}else{
				lottery.speed += 20;
			}
		}
		if (lottery.speed<40) {
			lottery.speed=40;
		};
		//console.log(lottery.times+'^^^^^^'+lottery.speed+'^^^^^^^'+lottery.prize);
		lottery.timer = setTimeout(roll,lottery.speed);
	}
	return false;
}
var click=false;

$(function(){
	lottery.init('lottery');
	$("#lottery a").click(function(){
		if(!'${user.id}'){
			$("#stips").show();
            $("#stips .con").html("<font color = 'red'>您尚未登录，请登录再试</font>");
            return;
		}
		
		if('${scoreMap.sumScore}'<1){
			$("#stips").show();
            $("#stips .con").html("<font color = 'red'>您没有抽奖机会，请查看如何获取抽奖次数。</font>");
			return;
		}
		if (click) {
			return false;
		}else{
			var param = {};
			param["paramMap.termId"] = '${termId}';
			$.dispatchPost("awardStart.mht",param,initCallBack2);
			return false;
		}
        return;
	});
});
/*
function awardfor(){
	param["paramMap.termId"] = 46;
	$.dispatchPost("awardStart.mht",param,initCallBack2);
}*/

function initCallBack2(data){
	/* var reta = data.awardId;
	if (reta == 1){
		lottery.prize = 3;
	}else if (reta == 2){
		lottery.prize = 5;
	}else if (reta == 3){
		lottery.prize = 2;
	}else if (reta == 4){
		lottery.prize = 8;
	}else if (reta == 12){
		lottery.prize = 0;
	}else if (reta == 6){
		lottery.prize = 6;
	}else if (reta == 13){
		lottery.prize = 4;
	}else if (reta == 14){
		lottery.prize = 7;
	}else if (reta == 11){
		lottery.prize = 9;
	}else if (reta == 15){
		lottery.prize = 1;
	}
	prizeName = data.retAwardName;
	lottery.speed=100;
	roll();
	click=true;
	return false; */
	
	if(data.error){
		alert(data.error);
		window.location.reload();
	}
	
	lottery.prize = data.paramId;
	prizeName = data.retAwardName;
	lottery.speed=100;
	roll();
	click=true;
	return false;
}

function refreshme(){
	$("#sbg").hide();
	$("#stips").hide();
	window.location.reload();
}

function awardAgain(){
	$("#sbg").hide();
	$("#stips").hide();
	
	var param = {};
	param["paramMap.termId"] = '${termId}';
	$.dispatchPost("awardStart.mht",param,initCallBack2);
}

function myAward(){
	$("#bgpop").show();
	$("#bgtips").show();
}
function refreshme2(){
	$("#bgpop").hide();
	$("#bgtips").hide();
//	window.location.reload();
}

</script>
</body>
</html>
