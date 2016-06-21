<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>列表页</title>
		<link rel="stylesheet" href="css/public.css" type="text/css"/>
		<link rel="stylesheet" href="css/hefm.css" type="text/css"/>
		<script type="text/javascript" src="js/jquery.min.js" ></script>
	</head>
	<body><jsp:include page="/WEB-INF/page/fund/include/top.jsp"/>
		<div class="listpage-main-content">
			<div class="top-banner"></div>
			<div class="center-wrap">
				<div class="center-center">
					<ul class="ul-table" style="border-right: none;">
						<li>
							<span class="hot quartercircle-hot">
								<span style="font-size: 32px;">热门</span>
							</span>
							<div class="info">
								<span class="desc1">近三个月涨幅</span>
								<span class="desc2">25.33%</span>
								<span class="desc3">平安大华保本混合</span>
								<span class="desc4">控制风险实现保本增值</span>
							</div>
						</li><li>
							<span class=" hot quartercircle-good">
								<span style="font-size: 32px;">优选</span>
							</span>
							<div class="info">
								<span class="desc1">近一年涨幅</span>
								<span class="desc2">83.98%</span>
								<span class="desc3">平安大华策略先锋混合</span>
								<span class="desc4">主投新兴产业及价值低估股票</span>
							</div>
						</li><li>
							<span class="hot quartercircle-hot">
								<span style="font-size: 32px;">热门</span>
							</span>
							<div class="info">
								<span class="desc1">近一年涨幅</span>
								<span class="desc2">53.93%</span>
								<span class="desc3">平安大华行业先锋混合</span>
								<span class="desc4">把握不同行业变化趋势分享投资机会</span>
							</div>
						</li><li style="border-right: solid 1px #F2E7C9;width: 294px;">
							<span class="hot quartercircle-good">
								<span style="font-size: 32px;">优选</span>
							</span>
							<div class="info">
								<span class="desc1">近三个月涨幅</span>
								<span class="desc2">19.40%</span>
								<span class="desc3">平安大华新鑫先锋A</span>
								<span class="desc4">投资灵活，追求长期收益</span>
							</div>
						</li>
					</ul>
					<div class="center-banner">
							<div name="banner-title" style="margin-left: 5px;" class="div-activity">智能优选</div>
							<div name="banner-title">全部基金</div>
							<div style="float: right;width: 320px;text-align: left;">
								<input type="text" class="textInput"/>
								<span class="btn-block btn-img">搜 索</span>
							</div>
					</div>
					<div style="width: 100%;height: 100%;">
						<ul class="listpage-ul-table">
								<li>
									<ul class="listpage-ul-tr">
										<li style="border-bottom: solid 1px #A8ABAE;padding-left: 20px;width: 125px;">
											基金类型
										</li><li style="text-align: center;width: 100px;">
											不限
										</li><li>
											债券型
										</li><li>
											股票
										</li><li>
											混合型
										</li>
									</ul>
								</li><li>
									<ul class="listpage-ul-tr">
										<li style="border-bottom: solid 1px #A8ABAE;padding-left: 20px;width: 125px;color: black;">
											业绩-近一年收益
										</li><li style="text-align: center;width: 100px;">
											不限
										</li><li>
											0.00%~7.00%
										</li><li>
											7.00%~9.00%
										</li><li>
											9.00%~11.00%
										</li><li>
											11.00%以上
										</li>
									</ul>
								</li><li>
									<ul class="listpage-ul-tr">
										<li style="border-bottom: solid 1px #A8ABAE;padding-left: 20px;width: 125px;color: black;">
											持续性-排名领先
										</li><li style="text-align: center;width: 100px;">
											不限
										</li><li>
											近一年排名前1/4
										</li><li>
											连续两年排名前1/3
										</li><li>
											连续三年排名前1/2
										</li>
									</ul>
								</li><li style="border-bottom: none;">
									<ul class="listpage-ul-tr">
										<li style="padding-left: 20px;width: 125px;color: black;border-bottom: none;">
											风险等级
										</li><li style="text-align: center;width: 100px;border-bottom: none;">
											不限
										</li><li style="border-bottom: none;">
											中低风险
										</li><li style="border-bottom: none;">
											中风险
										</li><li style="border-bottom: none;">
											中高风险
										</li><li style="border-bottom: none;">
											高风险
										</li>
										<br/>
									</ul>
								</li>
							</ul>
							<ul class="order-desc">
								<li style="padding-left: 20px;width: 15%;">排序</li><li>
									默认排序
								</li><li>
									近一周
								</li><li>
									近三个月
								</li><li>
									近一年
								</li><li>
									<input type="checkbox"/> 0申购
								</li><li>
									<input type="checkbox"/> 5星评级
								</li>
							</ul>
					</div>
					<div id="banner-title-0" style="display: block;height: 100%;" class="banner-title-detail">
							<ul>
							<div class="listpage-list-ul-table" style="border-top: solid 1px #E1E4E9;margin-top: 20px;">
								<div class="left">
									<span class="hot quartercircle-topLeft">
										<span style="font-size: 20px;height: 60px;width: 65px;line-height: 40px;text-align: center;">优选</span>
									</span>
									<div class="info">
										<span class="desc1">平安大华保本混合700004</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">25.33%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">25.33%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">36.30%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：控制风险实现保本增值</p>
									</div>
								</div>
								<div class="rigth">
									<a href="public_funds_detail.mht"><span class="btn-activity">立刻投资</span></a>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<span class="hot quartercircle-topLeft">
										<span style="font-size: 20px;height: 60px;width: 65px;line-height: 40px;text-align: center;">优选</span>
									</span>
									<div class="info">
										<span class="desc1">平安大华策略先锋混合700003</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">83.98%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">78.29%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">105.03%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
										<!--<p class="font-size-16" style="margin-top: 5px;">0.08% <del>0.80%</del> <label class="color-red">1折</label></p>-->
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：主投新兴产业及价值低估股票</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<div class="info">
										<span class="desc1">平安大华行业先锋混合700001</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">50.21%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">57.90%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">108.10%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：把握不同行业变化趋势分享投资机会</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<div class="info">
										<span class="desc1">平安大华新鑫先锋A000739</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">19.80%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">20.30%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">40.10%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：投资灵活，追求长期收益</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<div class="info">
										<span class="desc1">平安大华新鑫先锋C001515</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-green font-size-16" style="margin-top: 5px;">-32.88%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-green font-size-16" style="margin-top: 5px;">-31.92%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-green font-size-16" style="margin-top: 5px;">-15.39%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：投资灵活，控制风险，无申购费率</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
						</ul>
					</div>
					<div id="banner-title-1" style="height: 100%;" class="banner-title-detail">
							<ul>
							<div class="listpage-list-ul-table" style="border-top: solid 1px #E1E4E9;margin-top: 20px;">
								<div class="left">
									<span class="hot quartercircle-topLeft">
										<span style="font-size: 20px;height: 60px;width: 65px;line-height: 40px;text-align: center;">优选</span>
									</span>
									<div class="info">
										<span class="desc1">平安大华保本混合700004</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">25.33%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">25.33%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">36.30%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：控制风险实现保本增值</p>
									</div>
								</div>
								<div class="rigth">
									<a href="public_funds_detail.mht"><span class="btn-activity">立刻投资</span></a>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<span class="hot quartercircle-topLeft">
										<span style="font-size: 20px;height: 60px;width: 65px;line-height: 40px;text-align: center;">优选</span>
									</span>
									<div class="info">
										<span class="desc1">平安大华策略先锋混合700003</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">83.98%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">78.29%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">105.03%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
										<!--<p class="font-size-16" style="margin-top: 5px;">0.08% <del>0.80%</del> <label class="color-red">1折</label></p>-->
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：主投新兴产业及价值低估股票</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<div class="info">
										<span class="desc1">平安大华行业先锋混合700001</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">50.21%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">57.90%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">108.10%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：把握不同行业变化趋势分享投资机会</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<div class="info">
										<span class="desc1">平安大华新鑫先锋A000739</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">19.80%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">20.30%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-red font-size-16" style="margin-top: 5px;">40.10%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：投资灵活，追求长期收益</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
							<div class="listpage-list-ul-table">
								<div class="left">
									<div class="info">
										<span class="desc1">平安大华新鑫先锋C001515</span>
									</div>
								</div>
								<div class="center">
									<div class="line"></div>
									<span class="list" style="padding-left: 15px;">
										<p class="font-size-14">近一周收益</p>
										<p class="color-green font-size-16" style="margin-top: 5px;">-32.88%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近三个月收益</p>
										<p class="color-green font-size-16" style="margin-top: 5px;">-31.92%</p>
									</span>
									<span class="list">
										<p class="font-size-14">近一年收益</p>
										<p class="color-green font-size-16" style="margin-top: 5px;">-15.39%</p>
									</span>
									<span class="list">
										<p class="font-size-14">类型</p>
										<p class="font-size-16" style="margin-top: 5px;">混合型</p>
									</span>
									<span class="list" style="width: 130px;">
										<p class="font-size-14">申购费率</p>
										<p class="font-size-16" style="margin-top: 5px;">1.5%</p>
									</span>
									<div style="padding:10px 0 0 15px;color: #a4a7a8;">
										<p class="font-size-12">点评：投资灵活，控制风险，无申购费率</p>
									</div>
								</div>
								<div class="rigth">
									<span class="btn">立刻预约</span>
								</div>
							</div>
						</ul>
					</div>
					<div class="page">
							<label style="margin-left: 20px;">共 10 页 </label> <label style="margin: 0 20px 0 10px;">共 100 条记录 </label>
							<a class="firstPage pageBasic" href="#">&nbsp;</a>
							<a class="page_prev pageBasic" href="#">&nbsp;</a>
							<a class="pageBasic-acivity" href="#">1</a>
							<a class="pageBasic" href="#">2</a>
							<a class="pageBasic" href="#">3</a>
							<a class="page_next pageBasic" href="#">&nbsp;</a>
							<a class="endPage pageBasic" href="#">&nbsp;</a>
					</div>
				</div>
				<div style="width: 100%;height: 50px;"></div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/page/fund/include/footer.jsp"/>
		<script type="text/javascript">
			$(function(){
				$("div[name=banner-title]").on("click",function(){
					var index = $("div[name=banner-title]").index($(this));
					$("#banner-title-"+index).show();
					$(this).addClass("div-activity");
					$(this).siblings().each(function(inx,obj){
						if($(obj).attr("name")=="banner-title"){
							$(this).removeClass("div-activity");
							var hideid = $("div[name=banner-title]").index($(obj));
							$("#banner-title-"+hideid).hide();
						}
					})
				})
				$("span[class=btn]").on("click",function(){
					location.href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes";
				})
			})
		</script>
	</body>
</html>
