<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>涨薪宝</title>
<link href="css/inside.css"  rel="stylesheet" type="text/css" />
<link id="skin" rel="stylesheet" href="css/jbox/Gray/jbox.css" />
<link href="css/common.css"  rel="stylesheet" type="text/css" />
<link href="css/user.css"  rel="stylesheet" type="text/css" />
   <link rel="stylesheet" type="text/css"
	href="script/bootstrap/css/bootstrap.min.css">
	<style type="text/css">
	.footer_btn{
position:fixed; bottom:0px;line-height:23px; z-index:9999; opacity:90;
filter:alpha(opacity=100); _bottom:auto; _width:100%; _position:absolute;
_top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-
(parseInt(this.currentStyle.marginTop, 10)||0)-(parseInt(this.currentStyle.marginBottom, 10)||0)));
}
.button1{border-bottom: 1px solid #eee; background-color:#fff;line-height:50px;}
.active{border-bottom: 2px solid red;}
	html { overflow:hidden ; }
	</style>
</head>

<body >
<!-- 引用头部公共部分 -->
<jsp:include page="header.jsp"></jsp:include>	
<div class="r_main">
     <div class="uesr_border personal p0">
            <div class="pub_tb bottom mt10 pl10">涨薪宝-<span class="money">金秋1号</span></div>
            <div>
            	<div class="text-center p20">
            		<div class="w050 fl">
            			<p>每万份收益</p>
	            		<p class="  bold "><span class="money f30">${map.paytreasure.payprofit}</span>元</p>
	            		<p>年化收益率：${map.paytreasure.rate}%</p>
            		</div>
            		<div class="w050 fr pb20">
	            		<s:if test="#session.user==null"><p class="f20 bold">尚未登录，请<a href="www/index.html#/user/login">登录</a></p></s:if>
	            		<s:else>
	            			<p>昨日收益(元)</p>
	            			<s:if test="#request.map.zxbprofit.zxbprofit!=null"><p class="f30 bold">${map.zxbprofit.zxbprofit}</p></s:if>
	            			<s:else><p class="f30 bold">暂无收益</p></s:else>
	            			<a href="profitRecordInit.mht">收益记录</a>
	            		</s:else>
            		</div>
            	</div>
            	<div class="clear"></div>
            	<div>
            		<div  style="padding: 10px 0;"><button onclick="detail()" id="detail1" class="button1 w049 active">项目详情</button><button onclick="procotol()" id="procotol1" class="button1 w050 fr">投资协议</button></div>
            		<div class="content" id="detail">
            		<p>
    <br/>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">1.项目介绍</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">涨薪宝是由平台与相关金融机构提供的理财产品，共同设计打造的一款活期理财产品，按日计息，随转随出。</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;"></span>
</p>
<hr/>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">2.投资范围</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">货币基金、ETF 基金、固定收益类信托计划、基金公司资管计划、契约式私募投资基金等稳定收益投资。</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;"></span>
</p>
<hr/>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">3.预期年化收益率</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">年化 5.10%</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;"></span>
</p>
<hr/>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">4.认购总额</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">2000 万</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;"></span>
</p>
<hr/>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">5.转入规则</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">1000 元起</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;"></span>
</p>
<hr/>
<style>
									.profit {
										    margin: 10px 0;
										    max-width: 1000px;
										    table-layout: auto;
										    border: 1px solid #d6d6d6;
										    border-collapse: collapse;
										    text-align: left;
										}
									.profit tr th{
									    font-size: 14px;
									    border: 1px solid #d6d6d6;
									    padding: 8px;
									    min-width: 60px;
									    vertical-align: middle;
									    text-align: left;
									}
									.profit td{line-height: 30px;padding:0 10px; }
									.profit thead {
									    background-color: #A1B5C7;
									    color: #fff;
									    font-size: 14px;
									    border: 1px solid #d6d6d6;
									}
									.profit span {
									    font-size: 14px;
									}
									</style>
									 <span >涨薪宝转入后，收益首次显示时间如下：（节假日顺延）</span>
<table class="profit" border="1" cellpadding="1" cellspacing="1" style="width: 100%;margin: 10px 0; max-width: 1000px;table-layout: auto; border: 1px solid #d6d6d6; border-collapse: collapse; text-align: left;"> 
 <thead> 
  <tr> 
   <th scope="col"><span style="line-height: 20.796875px;">转入时间</span></th> 
   <th scope="col"><span style="line-height: 20.796875px;">确认份额</span></th> 
   <th scope="col"><span style="line-height: 20.796875px;">首次发放收益时间</span></th> 
  </tr> 
 </thead> 
 <tbody> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周四15:00（含15:00）～周五15:00</span></td> 
   <td><span style="line-height: 20.796875px;">下周一</span></td> 
   <td><span style="line-height: 20.796875px;">下周二</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周五15:00（含15:00）～下周一15:00</span></td> 
   <td><span style="line-height: 20.796875px;">下周二</span></td> 
   <td><span style="line-height: 20.796875px;">下周三</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周一15:00（含15:00）～周二15:00</span></td> 
   <td><span style="line-height: 20.796875px;">周三</span></td> 
   <td><span style="line-height: 20.796875px;">周四</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周二15:00（含15:00）～周三15:00</span></td> 
   <td><span style="line-height: 20.796875px;">周四</span></td> 
   <td><span style="line-height: 20.796875px;">周五</span></td> 
  </tr> 
  <tr> 
   <td><span style="line-height: 20.796875px;">周三15:00（含15:00）～周四15:00</span></td> 
   <td><span style="line-height: 20.796875px;">周五</span></td> 
   <td><span style="line-height: 20.796875px;">周六</span></td> 
  </tr> 
 </tbody> 
</table>
<hr/>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">7.费用</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;">转入、转出 0%</span>
</p>
<p>
    <span style="font-family: 微软雅黑, &#39;Microsoft YaHei&#39;; font-size: 14px;"><br/></span>
</p>
            		</div>
            		<div id="procotol" style="display: none;">
            		
            		</div>
            	</div>
            </div>
 	 </div>
 	 <div class="clear"></div>
 	 <div class="footer_btn w " ><a  href="intoPayTreasureMobile.mht?mobile=f98c59c3-ab9e-43f2-b2d1-2703d1e01850" class="btn btn-danger h40 w050">转入</a><a  href="outPayTreasureInit.mht?mobile=f98c59c3-ab9e-43f2-b2d1-2703d1e01850" class="btn btn-danger  h40 w050">转出</a></div>
</div>
<!-- 引用底部公共部分 -->     
<script>
function detail(){
	$("#procotol").hide();
	$("#detail").show();
	$("#detail1").addClass("active");
	$("#procotol1").removeClass("active");
}
function procotol(){
	$("#detail1").removeClass("active");
	$("#procotol1").addClass("active");
	$("#procotol").show();
	$("#detail").hide();
	$.dispatchPost("payTreasureProtocol.mht",null,function(data){
		$("#procotol").html(data);
	})
}


</script>

</body>
</html>
