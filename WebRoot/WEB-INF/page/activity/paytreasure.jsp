<%@page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set value="三好资本团圆中秋喜迎国庆" var="title" scope="request" />
<jsp:include page="/include/head.jsp"></jsp:include>
<style type="text/css">

.twoxxx {
	padding: 15px 30px;
	margin-bottom: 30px;
}

.number {
	color: #000;
	font-size: 45px;
}

.word {
	color: #000;
	font-weight: bold;
}

.ml45 {
	margin-left: 50px;
}

a.button {
	background: #de4a4a;
	color: #fff !important;
	display: inline-block;
	text-align: center;
	line-height: 25px;
	height: 27px;
	font-size: 14px;
	border-radius: 4px;
}

p {
	margin-bottom: 0px !important;
}
</style>
</head>
<body>
	<jsp:include page="/include/top.jsp"></jsp:include>

	<div class="wrap"
		style="line-height: 25px;background-color: #FFD2A8;">
		<div
			style=" display: block;background: url('activity/20151030/images/banner.jpg') 20% 0 no-repeat;height: 370px;"></div>
		<!-- <img alt="三好资本" src="activity/20150921/images/banner.png" > -->
		<div class="w950 mt10">
			<div class="twoxxx clearfix">
				<div class="fl">
					<img src="activity/20151030/images/NO.1.png" height="60" width="100" /><span class="word f25 orange">什么是涨薪宝？</span><a href="paytreasuredetail.mht" class="button w100 ml10">申请认购</a>
					<div class="ml45">
						<p class="f15 bold"><font color="#D9534F ">涨薪宝</font>是由平台与相关金融机构提供的理财产品，共同设计打造的一款活期理财产品，按日计息，随转随出。</p>
					</div>
				</div>
			</div>
			<div class="twoxxx clearfix">
				<div>
					<img src="activity/20151030/images/NO.2.png" height="60" width="100" /><span class="word f25 orange">涨薪宝的三大优势？</span>
					<div class="ml45">
						<div  class="clearfix" >
							<dd class="fl"><img src="activity/20151030/images/A.png" /></dd>
							<dd class="fl"><p class="f20 orange bold mt10">活期理财，随存随取</p><p class="mt10 bold"><span style=";font-family:微软雅黑;font-size:14px">赎回最快一日到账，存取灵活自由</span></p></dd>
							<dd class="fl ml20"><img src="activity/20151030/images/ka.png" style="max-width:80%" /></dd>
						</div>
						<div style="background-color: #FE8D2D;height: 5px;margin: 30px 0;" ></div>
						<div class="text-right clearfix">
							<dd class="fr"><img src="activity/20151030/images/B.png" /></dd>
							<dd class="fr"><p class="f20 orange bold mt10">超高的收益率</p><p class="mt10 bold"><span style=";font-family:微软雅黑;font-size:14px">年化收益<span class="money bold">5.10%</span>，是银行活期存款的15倍，是宝宝类产品的2倍</span></p></dd>
							<dd class=" mr20 fr"><img src="activity/20151030/images/zhu.png"  style="max-width: 70%"/></dd>
						</div>
						<div style="background-color: #FE8D2D;height: 5px;margin: 30px 0;" ></div>
						<div  class="clearfix" >
							<dd class="fl"><img src="activity/20151030/images/C.png" /></dd>
							<dd class="fl"><p class="f20 orange bold mt10">独有的安全策略</p><p class="mt10 bold"><span style=";font-family:微软雅黑;font-size:14px">由担保机构<span class="money bold">全额担保 </span>100%本息保障</span></p></dd>
							<dd class="fl ml20"><img src="activity/20151030/images/suo.png" style="max-width: 70%"/></dd>
						</div>
					</div>
				</div>

			</div>
			

			<div style="line-height: 25px;font-size: 15px;padding-bottom: 25px;padding-top: 10px;" class="ml45">
				<h4 class="bold">计息详细规则：</h4>
				<div >
					<p style="line-height: 1.75em;">
					    <span style="font-size: 14px;">您在当日15：00前转入涨薪宝的资金在第二个工作日进行份额确认并显示收益。</span>
					</p>
					<p style="line-height: 1.75em;">
					    <span style="color: rgb(255, 0, 0); font-size: 14px;">注：</span>
					</p>
					<p style="line-height: 1.75em;">
					    <span style="font-size: 14px;">1、15:00后转入的资金会顺延1个工作日确认。</span>
					</p>
					<p style="line-height: 1.75em;">
					    <span style="font-size: 14px;">2、双休日及国家法定假期间，不进行份额确认。</span>
					</p>
					<p style="line-height: 1.75em;">
					    <span style="font-family: 微软雅黑, 'Microsoft YaHei'; font-size: 14px;"></span><br>
					</p>
				</div>
			</div>

		</div>
	</div>

	<jsp:include page="/include/footer.jsp"></jsp:include>
	<script type="text/javascript">
	$(document).ready(function(){$("#test").addClass("hidden");})
	</script>
</body>
</html>
