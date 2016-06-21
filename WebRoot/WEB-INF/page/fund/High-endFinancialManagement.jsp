<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>高端理财</title>
		<link rel="stylesheet" href="css/public.css" type="text/css"/>
		<link rel="stylesheet" href="css/hefm.css" type="text/css"/>
		<script type="text/javascript" src="js/doT.js" ></script>
		<script type="text/javascript" src="js/jquery.min.js" ></script>
	</head>
	<body><jsp:include page="/WEB-INF/page/fund/include/top.jsp"/>
		<div class="main-content">
			<div class="top-banner"></div>
			<div id="center-wrap" class="center-wrap">
				<script type="text/template" id="list-tpl">
					{{~ it : value : index}}
					<div id="floatDiv" class="floatDiv" style="top: {{=value.top}};left: {{=value.left}};">
					</div>
					<div id="floatDivBg" class="floatDivBg" style="top: {{=value.topt}};left: {{=value.leftt}};">
								<div class="content">
									<div class="top">
										<p class="font-size-26">
											合格投资人认定书
										</p>
										<p class="font-size-13 public">
											根据《私募投资基金监督管理暂行办法》第四章第十四条规定："私募基金管理人、私募基金销售机构不得向合格投资者之外的单位和个人募集资金，不得通过报刊、电台、电视、互联网等公众传播媒体或者讲座、报告会、分析会和布告、传单、手机短信、微信、博客和电子邮件等方式，向不特定对象宣传推介。"
											华融金服谨遵《私募投资基金监督管理暂行办法》之规定，只向特定投资者展示私募基金产品信息，不构成任何投资推介建议。
											阁下如有意了解私募投资基金且满足《私募投资基金监督管理暂行办法》关于"合规投资者"标准之规定，即具备相应风险识别能力和风险承担能力，投资于单只私募基金的金额不低于100 万元，且个人金融类资产不低于300万元或者最近三年个人年均收入不低于50万元人民币。请阁下详细阅读本提示，并确认为合格投资者，方可进入页面获取私募基金相关展示信息。
										</p>
									</div>
									<div class="bottom">
										<span onclick="hideDiv();" class="btn">
											我知道了
										</span>
										<p class="checkBoxBtn">
											<input type="checkbox" />&nbsp;记住选择，不再提示
										</p>
									</div>
								</div>
							</div>
					{{~}}
				</script>
				<div class="center-center">
					<ul class="main-ul">
						<li>
							<div class="li-top">
								<div class="rigthBanner">
									<input type="text" placeholder="姓名" class="inputText"/>
									<input type="text" placeholder="电话" class="inputText"/>
									<span  class="btn">马 上 咨 询</span>
								</div>
							</div>
						</li>
						<li>
							<div class="li-buttom-left">
								<img style="width: 679px;height:240px;" src="images/hefm/list_project_pic01.jpg" />
							</div>
							<div class="li-buttom-rigth">
								<div class="desc-title">
									<p style="font-size: 24px;">伦顿1号私募证券投资基金</p>
								</div>
								<div class="desc-content">
									<p style="font-size: 16px;color: #42413e;"><span class="desc">投资门槛</span><span class="desc">目标规模</span></p>
									<p style="font-size: 20px;"><span class="color-red desc">100.00万</span><span class="color-red desc">10000.00万</span></p>
								</div>
								<div class="desc-content" style="margin-top: 20px;">
									<p style="font-size: 16px;olor: #42413e;"><span class="desc">存续期限</span><span class="desc">投资方向</span></p>
									<p><span class="desc" style="font-size: 20px;">1&nbsp;年</span><span class=" desc" style="font-size: 20px;">证券投资</span></p>
								</div>
							</div>
						</li>
						<li>
							<div class="li-buttom-left">
								<img style="width: 679px;height:240px;" src="images/hefm/list_project_pic02.jpg" />
							</div>
							<div class="li-buttom-rigth">
								<div class="desc-title">
									<p style="font-size: 24px;">伦顿2号私募证券投资基金</p>
								</div>	
								<div class="desc-content">
									<p style="font-size: 16px;color: #42413e;"><span class="desc">投资门槛</span><span class="desc">目标规模</span></p>
									<p style="font-size: 20px;"><span class="color-red desc">100.00万</span><span class="color-red desc">10000.00万</span></p>
								</div>
								<div class="desc-content" style="margin-top: 20px;">
									<p style="font-size: 16px;color: #42413e;"><span class="desc">存续期限</span><span class="desc">投资方向</span></p>
									<p><span class="desc" style="font-size: 20px;">1&nbsp;年</span><span class="desc" style="font-size: 20px;">证券投资</span></p>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div style="width: 100%;height: 200px;"></div>
		</div>
		<jsp:include page="/WEB-INF/page/fund/include/footer.jsp"/>
	</body>
	<script type="text/javascript">
		window.onload = function() { 
			var perWid = document.body.scrollWidth;
			var perHei = $(window).height();
			var top = (perHei-680)/2;
			var left = (perWid - 781)/2;
			var topt = top+10;
			var leftt = left+10;
			var _dot = doT.template($("#list-tpl").html());
			var renderArray = {"left":left+"px","top":top+"px","leftt":leftt+"px","topt":topt+"px"};
			var flag = localStorage.getItem("floatDivHideFlag");
			if(flag !='T'){
				$("#center-wrap").append(_dot([renderArray]));
			}
		}
		function hideDiv(){
			$("#floatDiv").hide();
			$("#floatDivBg").hide();
			localStorage.setItem("floatDivHideFlag","T");
		}
	</script>
</html>
