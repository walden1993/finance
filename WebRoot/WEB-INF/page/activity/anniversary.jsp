<%@page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set value="双十一收获季,赚钱狂欢节" var="title"  scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
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
.banner{background:url(activity/20151104/images/banner.jpg) 50% no-repeat;height:347px;width:100%;display:block;}
.banner_time{padding:335px 0 0 0px;color:#fff;font-size:15px;text-align: center;}
.banner_time h3{color:#de4a4a !important;font-size: 25px;padding-top: 50px;font-weight: bold;}
.cont_bj{background:#fade7b;width:100%;padding:20px 0;}
.wrap{width:1071px; margin:0 auto; display:block;height:1272px;}
.zt_btn{ background:#ff4d4d;width:180px;line-height:40px;color:#fff;height:40px;display:block;-moz-border-radius:6px;      /* Gecko browsers */
    -webkit-border-radius:6px; text-align:center;font-size:20px;margin-top:20px;border-radius:6px;}
.zt_btn:hover{background:#ff6e00;color:#fff;}	
.zt_bottom_line{border-bottom:1px solid #992d2d;padding-bottom:20px;}	
.zt_explain{padding-top:40px;background:url(activity/20150811/images/button.png) 80% 80% no-repeat;}
.zt_explain p{line-height:30px;font-size:13px;}
.zt_explain span{color:#fbd501;}
.zt_explain h3{font-size:20px; margin-top:20px;}
.naving{display:block;padding:20px;}
.qzone_wb,.sina_wb,.qq_wb{ background:url(activity/20150413/old_activity/images/jan_wb.png) no-repeat;display:block;width:25px;height:25px;line-height:25px; float:left;margin:8px 2px 0 0; cursor:pointer;}
.naving span{color:#745586;font-size:13px;margin-top:8px;}
.naving a{margin-right: 20px;}
.qzone_wb{ background-position:0 0;}.sina_wb{ background-position:-28px 0;}.qq_wb{ background-position:-55px 0;}
.qzone_wb:hover{ background-position:0 -30px;}.sina_wb:hover{ background-position:-28px -30px;}.qq_wb:hover{ background-position:-55px -30px;}
.zt_list li{color:#745586; margin-bottom:5px;}
.zt_list li .info{ margin:35px 0 0 25px;}
.zt_list li .info h2{font-size:20px;font-weight:blod !important;}
.zt_list li .info p{margin:15px 0;font-size:13px;}
.zt_list li table{font-size:15px;}
</style>
<script type="text/javascript">var menuIndex = 8;</script>
</head>

<body>
<s:include value="/include/top.jsp"></s:include>
<div class="banner">
   <div class="tc banner_time"><h3>活动时间：2015年11月05日09:00——2015年11月27日12:00 </h3></div>
</div>
<div class="cont_bj">
  <div class="clearfix mt50">
       <ul class="zt_list">
         <li class="clearfix">
           <div class="w950">
	           <div>
		           <div class="info  fl " style="padding-top: 30px;"> 
		              <h2 style="color: #de4a4a ;" class="bold">活动一：新用户大实惠</h2>
		              <p class="bold f14">活动期间新用户成功注册送10元现金红包，投资（100或100以上）可直接提现。</p>
		           </div>
		           <div class=" fl"><a href="reg.mht"><img src="activity/20151104/images/redp.png" alt="三好资本"/></a></div>
	           </div>
	           <div class="bdsharebuttonbox info fl"><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a><a href="#" class="bds_more" data-cmd="more"></a></div>
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"值此三好资本平台三重喜庆之际即平台一周年庆、新版上线、交易额破亿，为感谢广大好粉对平台的大力支持和关注，特制定三重好礼送不停八月活动。","bdMini":"2","bdMiniList":false,"bdPic":"https://www.sanhaodai.com/activity/20150811/images/banner.png","bdStyle":"1","bdSize":"24"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"分享到：","viewSize":"24"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
	           <%-- <div class="naving">
		             <span class="fl">分享到：</span>
		             <a class="qzone_wb" href='http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=<%=application.getAttribute("basePath")%>activityIndex_running.mht?title=三重喜庆，好礼送不停！&summary=三重喜庆，好礼送不停' target="_blank"></a>
		             <a  class="sina_wb" href='http://v.t.sina.com.cn/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_running.mht?title=三重喜庆，好礼送不停' target="_blank"></a>
		             <a class="qq_wb" href='http://v.t.qq.com/share/share.php?url=<%=application.getAttribute("basePath")%>activityIndex_running.mht?title=三重喜庆，好礼送不停&pic=activity/20150811/images/banner.jpg' target="_blank"></a> 
		       </div> --%>
           </div>
         </li>
         <li class="clearfix">
           <div class="w950">
	           <div>
		           <div class="info  fl " style=""> 
		              <h2 style="color: #de4a4a ;" class="bold">活动二：分享有奖（金博会期间现场客户,微信扫描二维码分享）</h2>
		              <p class="bold f14">1、活动对象：新注册三好资本用户</p>
		              <p class="bold f14">2、活动期间关注三好资本订阅号，并分享活动文章至朋友圈（编辑一句话分享）;</p>
		              <p class="bold f14">即可获得抽奖机会，抽奖奖品如下(100%有奖)：</p>
		              <div class="ml30">
		              	  <div class="fl">
		              	  	 <p class="bold f14">一等奖：价值100元的奖品（3个）</p>
				              <p class="bold f14">二等奖：价值30-50元的奖品（15个）</p>
				              <p class="bold f14">三等奖：价值15-25元的奖品（82个）</p>
				              <p class="bold f14">四等奖：价值5-10元的奖品（600个）</p>
		              	  </div>
		              	  <div class=" fl"><img src="activity/20151104/images/awad.png" alt="三好资本"/></div>
		              </div>
		              <p class="bold f14 red">说明：中奖比例为3：15：82：600，送完即止。</p>
		           </div>
		           <div style="padding-top: 40px;"><img alt="三好资本" height="150" width="150" src="activity/20151104/images/code.png"></div>
	           </div>
           </div>
         </li>
         <li class="clearfix">
         	<div class="w950">
	           <div>
		           <div class="info  fl " style="padding-top: 30px;"> 
		              <h2 style="color: #de4a4a ;" class="bold">活动三：投资有礼</h2>
		              <p class="bold f14">1、投资三月、四月标累计五万以上有以下好礼赠送：</p>
		           </div>
	           </div>
	           <div  class="clearfix"></div>
	           <style>
	           .table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th, .table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td, .table-bordered>thead>tr>th{border-color: #745586  !important; border-width: 1px;}
	           </style>
	           <table class="table text-center table-bordered mt20" style="border-color: #E01616  !important;">
	           		<tbody>
	           			<tr>
	           				<td>投资金额(元)</td>
	           				<td>投资期限</td>
	           				<td>参考礼品</td>
	           			</tr>
	           			<tr>
	           				<td>≥50000</td>
	           				<td rowspan="9" style="vertical-align: middle;">三个月、四个月</td>
	           				<td>300元现金</td>
	           			</tr>
	           			<tr>
	           				<td>≥100000</td>
	           				<td>Misfit Shine 智能手环</td>
	           			</tr>
	           			<tr>
	           				<td>≥200000</td>
	           				<td>小米（MI）空气净化器</td>
	           			</tr>
	           			<tr>
	           				<td>≥300000</td>
	           				<td>小米（MI）净水器</td>
	           			</tr>
	           			<tr>
	           				<td>≥400000</td>
	           				<td>IPAD mini 3 16G</td>
	           			</tr>
	           			<tr>
	           				<td>≥500000</td>
	           				<td>Apple iPhone 6s A1688</td>
	           			</tr>
	           			<tr>
	           				<td>≥600000</td>
	           				<td>Apple iPhone 6s plus (A1699) 16G </td>
	           			</tr>
	           		</tbody>
	           </table>
	            <div class="info"> 
	              <p class="bold f14">2、投资五月、六月标及以上累计五万以上有以下好礼赠送：</p>
	              </div>
	           <div  class="clearfix"></div>
	           <table class="table text-center table-bordered mt20" style="border-color: #fff  !important;">
	           		<tbody>
	           			<tr>
	           				<td>投资金额(元)</td>
	           				<td>投资期限</td>
	           				<td>参考礼品</td>
	           			</tr>
	           			<tr>
	           				<td>≥50000</td>
	           				<td rowspan="9" style="vertical-align: middle;">5个月及以上</td>
	           				<td>Misfit Shine 智能手环</td>
	           			</tr>
	           			<tr>
	           				<td>≥100000</td>
	           				<td>小米（MI）空气净化器</td>
	           			</tr>
	           			<tr>
	           				<td>≥200000</td>
	           				<td>IPAD mini 3 16G </td>
	           			</tr>
	           			<tr>
	           				<td>≥300000</td>
	           				<td>Apple iPhone 6s A1688</td>
	           			</tr>
	           			<tr>
	           				<td>≥400000</td>
	           				<td>Apple iPhone 6s plus (A1699) 16G </td>
	           			</tr>
	           		</tbody>
	           </table>
           	</div>
         </li>
         
         <li class="clearfix">
           <div class="w950">
	           <div>
		           <div class="info  fl " style="padding-top: 30px;"> 
		              <h2 style="color: #de4a4a ;" class="bold">活动四：推荐拿红利</h2>
		              <p class="bold f14">老客户推荐好友注册投资，将获得好友投资收益的6%奖励（活动结束后七个工作日发放）。</p>
		           </div>
		           <div class=" fl"><img src="activity/20151104/images/profit.png" alt="三好资本"/></div>
	           </div>
           </div>
         </li>
       </ul>
       <div class="zt_explain w950">
           <h3>活动说明： </h3>
           <p >1、活动时间：2015年11月05日09:00——2015年11月27日12:00 </p>
			<p>2、三好资本账户以身份证号为唯一识别标识。</p>
			<p>3、实物奖品及现金奖励统一于活动结束后7个工作日内邮寄或发放到三好资本平台个人账户。其中：<br/>
   			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实物奖励(实物奖品颜色随机发放)由于实物奖励快递需要，达标用户请提交“平台注册用户名”<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以及真实“姓名、手 机、身份证、通讯地址” 至 &nbsp;<span style="color: red;">liyuqian@sanhaodai.com</span>。</p>
			<p>4、金博会期间折现按原价折现，其他时间按照奖品价格的8折折现。</p>
			<p>5、所获得的红包即时到账，需在活动期间内使用，逾期作废清零。</p>
			<p>6、本活动最终解释权归深圳市易付通金融信息技术有限公司所有。</p>
       </div>     
  </div>
</div>
<s:include value="/include/footer.jsp"></s:include>
<script type="text/javascript">
$(document).ready(function(){$("#test").addClass("hidden");})
</script>
</body>
</html>
