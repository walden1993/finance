<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@include file="/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="yes" name="apple-touch-fullscreen">
        <meta name="data-spm" content="a215s">
        <meta content="telephone=no,email=no" name="format-detection">
        <meta content="fullscreen=yes,preventMove=no" name="ML-Config">
        <meta name="viewport" content="width=device-width,initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
		<title>三好资本</title>
		<link rel="stylesheet" href="css/public.css" type="text/css"/>
		<link rel="stylesheet" href="css/hefm.css" type="text/css"/>
		<script type="text/javascript" src="js/jquery.min.js" ></script>
		<script type="text/javascript" src="js/jscharts.js" ></script>
	</head>
	<body><jsp:include page="/WEB-INF/page/fund/include/top.jsp"/>
		<div class="pe-main-wrap">
			<div class="center-center">
				<div class="path">公募基金&nbsp;&nbsp;<label></label>&gt;<label>&nbsp;&nbsp;项目详情</label> </div>
				<div class="trend">
					<div class="left">
						<div class="title">累计净值走势</div>
						<div name="month-trend" class="month-trend" style="background-color: #FFFFFF;color: #000;">1个月</div>
						<div name="month-trend" class="month-trend">3个月</div>
						<div name="month-trend" class="month-trend">1年</div>
						<div name="month-trend" class="month-trend">今年以来</div>
						<div id="month-trend-0" class="map" style="display: block;">
							<p style="height: 40px;line-height: 40px;padding-left: 15px;">最近一个月涨幅<span style="font-size: 18px;" class="color-red">25.33%</span></p>
							<img src="images/hefm/zsup.png" width="378px" height="207px"/>
						</div>
						<div id="month-trend-1" class="map">
							<p style="height: 40px;line-height: 40px;padding-left: 15px;">最近三个月涨幅<span style="font-size: 18px;" class="color-red">25.45%</span></p>
							<img src="images/hefm/zsup.png" width="378px" height="207px"/>
						</div>
						<div id="month-trend-2" class="map">
							<p style="height: 40px;line-height: 40px;padding-left: 15px;">最近一年涨幅<span style="font-size: 18px;" class="color-green">-13.24%</span></p>
							<img src="images/hefm/zsup.png" width="378px" height="207px"/>
						</div>
						<div id="month-trend-3" class="map">
							<p style="height: 40px;line-height: 40px;padding-left: 15px;">今年以来涨幅<span style="font-size: 18px;" class="color-red">25.58%</span></p>
							<img src="images/hefm/zsup.png" width="378px" height="207px"/>
						</div>
					</div>
					<div class="center"></div>
					<div class="rigth">
						<div class="title">
							<span style="display: inline-block; font-size: 24px; color:#000;">平安大华保本混合</span>
							<a class="invest_btn" href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank"><span class="btn-consult">咨 询</span></a>
							<p><label style="font-size: 16px;color: #42413e;">基金代码：700004</label></p>
						</div>
						<div class="content">
							<ul class="ul-table-fund">
								<li>
									<div class="detail">
										<span style="font-size: 14px;color: #707273;">
											最新净值 2016-05-24
										</span>
										<p class="color-red" style="margin-top: 8px;">
											1.175
										</p>
									</div>
								</li>
								<li>
									<div class="detail">
										<span style="font-size: 14px;color: #707273;">
											净值日增长率
										</span>
										<p class="color-green" style="margin-top: 8px;">
											-1.261%
										</p>
									</div>
								</li>
								<li>
									<div class="detail" style="border: none;">
										<span style="font-size: 14px;color: #707273;">
											申购汇率
										</span>
										<p class="color-red" style="margin-top: 8px;">
											1.50%
											<!--0.60% <del style="color: grey;">1.50%</del> 4.00折-->
										</p>
									</div>
								</li>
							</ul>
							<div class="desc" >
								<span class="cycle"></span>
							          <label style="font-size: 14px;margin-right: 10px;">当前阶段：开放申购&nbsp;开放赎回</label>
							    <span class="cycle"></span>
							          <label style="font-size: 14px;margin-right: 10px;">风险等级：低等风险</label>
							    <span class="cycle"></span>
									<label style="font-size: 14px;margin-right: 10px;">投资人条件：风险承受能力“保守型”</label>
							</div>
							<div style="margin-left: 10px;line-height: 50px;">
								<label>账号余额：</label><label>登陆可见</label> 
							</div>
							<div style="margin-left: 10px;">
								<input type="text"   style="width:200px;height: 48px;border: solid 1px #E2A942;font-size: 18px;"/>
								<!-- <a class="invest_btn" href="http://wpa.qq.com/msgrd?v=3&uin=2198380644&site=qq&menu=yes"   target="_blank"><span class="btn-investment">立即预约</span></a></a>-->
								<a class="invest_btn" href="trade.mht?type=1"   target="_blank"><span class="btn-investment">立即购买</span></a></a>
							</div>
							<div style="margin-left: 10px;line-height: 30px;padding-top: 5px">
								<label>单次投资限额：无限额</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>单次投资限额：无限额</label>
								<p><label>支付方式：账户余额</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>先投资后代扣</label></p>
							</div>
						</div>
					</div>	
				</div>
				<div class="center-banner">
					<div name="banner-title" style="margin-left: 5px;" class="div-activity">基金详情</div>
					<div name="banner-title">基金经理</div>
					<div name="banner-title">历史数据</div>
					<div name="banner-title">投资组合</div>
					<div name="banner-title">基金公告</div>
				</div>
				<div id="banner-title-0" style="display: block;" class="banner-title-detail">
					<div class="personName">
						<span class="name">
							平安大华保本混合型证券投资基金
						</span>
					</div>
					<div  class="personInfo" style="width:38%;border-bottom: none;font-size: 14px;">
						<p><span class="fund-detail-left">基金代码：</span><span class="fund-detail-right">700004</span></p>
						<p><span class="fund-detail-left">基金公司：</span><span class="fund-detail-right">平安大华基金</span></p>
						<p><span class="fund-detail-left">基金类型：</span><span class="fund-detail-right">混合型</span></p>
						<!--<p><span class="fund-detail-left">收费方式：</span><span class="fund-detail-right">前段</span></p>-->
						<p><span class="fund-detail-left">赎回到账时间：</span><span class="fund-detail-right">3~4个工作日</span></p>
						<p><span class="fund-detail-left">托管人名称：</span><span class="fund-detail-right">中国建设银行股份有限公司</span></p>
						<p><span class="fund-detail-left">成立时间：</span><span class="fund-detail-right">2012-09-11</span></p>
						<p><span class="fund-detail-left">默认分红方式：</span><span class="fund-detail-right">现金分红</span></p>
						<p><span class="fund-detail-left">基金规模：</span><span class="fund-detail-right">开放式基金</span></p>
 						<div class="fund-disclaimer">
							<p style="font-size: 16px;">平台免责声明：</p>
							<p style="font-size: 14px;line-height: 26px;">
								三好资本作为交易服务平台进行信息发布，不对任何投资人及/或任何交易提供任何担保，
								无论是明示、 默示或法定的。陆金所平台提供的各种信息及资料仅供参考，投资人应该
								具独立判断做出决策。投资人据此进行投资交易的，产生的投资风险有投资人自行承担，
								三好资本不承担任何责任。
							</p>
						</div>
					</div>
					<div class="personInfoList">
						<ul class="ul-table">
							<li style="background-color: #DFE9F5;padding-left: 10px;font-size: 16px;">
								申购汇率</li><li style="border-bottom: solid 1px #DFE9F5 ;">
								<ul class="ul-tr">
									<li class="column-with-1">申购金额（元）</li><li>申购费率</li><li>折扣</li><li>折后费率</li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">申购金额<50万元</li><li><font color="red">1.20%</font></li><li>--</li><li><font color="red">1.20%</font></li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">50万元 ≤ 申购金额 ＜200万元</li><li><font color="red">0.80%</font></li><li>--</li><li><font color="red">0.80%</font></li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">200万元 ≤ 申购金额＜500万元</li><li><font color="red">0.40%</font></li><li>--</li><li><font color="red">0.40%</font></li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">申购金额 ≥500万元</li><li><font color="red">每笔1000元</font></li><li>--</li><li><font color="red">每笔1000元</font></li>
								</ul>
							</li>
						</ul>
						<ul class="ul-table" style="margin-top: 20px;border: solid 1px #F4EDDB;">
							<li style="background-color: #F4EDDB;padding-left: 10px;font-size: 16px;">
								赎回汇率</li><li style="border-bottom: solid 1px #F4EDDB ;">
								<ul class="ul-tr">
									<li class="column-with-1">持有时间</li><li>赎回费率</li><li>折扣</li><li>折后费率</li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">持持有期限<1年</li><li><font color="red">2.00%</font></li><li>--</li><li><font color="red">2.00%</font></li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">1年 ≤ 持有期限 ＜2年</li><li><font color="red">1.50%</font></li><li>--</li><li><font color="red">1.50%</font></li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">2年 ≤持有期限＜3年</li><li><font color="red">1.00%</font></li><li>--</li><li><font color="red">1.00%</font></li>
								</ul>
							</li><li>
								<ul class="ul-tr">
									<li class="column-with-1">持有期限 ≥3年</li><li><font color="red">不收取</font></li><li>--</li><li><font color="red">不收取</font></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<div id="banner-title-1"  class="banner-title-detail">
					<div class="personName">
						<span class="name">
							孙健
						</span>
					</div>
					<div  class="personInfo" style="width:38%;font-size: 14px;">
						<img src="images/hefm/defaultHeadPhoto.png" height="190px" width="180px" />
						<p><span class="detail">性别：</span><span class="detail">男</span></p>
						<p><span class="detail">学历：</span><span class="detail">硕士</span></p>
						<p><span class="detail">上任日期：</span><span class="detail">2011年</span></p>
					</div>
					<div class="personInfoList">
							<p >孙健，基金经理。自2001年起，先后在湘财证券资产管理部、中国太平人寿保险公司投资部/太平资产管理公司从事投资工作，2006年起，分别在摩根士丹利华鑫基金公司、银华基金公司担任基金经理。2011年9月加入平安基金，任投资研究部固定收益研究员，现担任平安大华保本混合型证券投资基金基金经理、平安大华添利债券型证券投资基金基金经理、平安大华日增利货币市场基金基金经理、平安大华鑫安混合型证券投资基金基金经理、平安大华智慧中国灵活配置混合型证券投资基金基金经理、平安大华鑫享混合型证券投资基金基金经理、平安大华安心混合型证券投资基金基金经理、平安大华安享混合型证券投资基金基金经理、平安大华安盈保本混合型证券投资基金基金经理</p>
					</div>
				</div>
				<div id="banner-title-2" class="banner-title-detail">
					<div style="padding: 20px; 15px 0 15px">
							<ul class="history-ul-top-table">
								<li style="background-color: #DFE9F5;padding-left: 10px;font-size: 16px;">
									基金涨幅
								</li><li style="border-bottom: solid 1px #DFE9F5;"><ul class="history-ul-tr">
									<li style="width: 20%;">涨幅走势比较</li><li>
											近一周
										</li><li>
											近一月
										</li><li>
											近三月
										</li><li>
											近一年
										</li><li>
											近两年
										</li><li>
											今年以来
										</li>
									</ul>
								</li><li><ul class="history-ul-tr">
										<li style="width: 20%;">阶段涨幅</li><li>
											<font class="color-green">-0.20%</font>
										</li><li>
											<font class="color-green">-0.40%</font>
										</li><li>
											<font class="color-green">-0.20%</font>
										</li><li>
											<font class="color-green">-13.24%</font>
										</li><li>
											<font class="color-red">＋ 15.09%</font>
										</li><li>
											<font class="color-red">＋0.20%</font>
										</li>
									</ul>
								</li><li><ul class="history-ul-tr">
										<li style="width: 20%;">同类平均涨幅</li><li>
											<font class="color-red">＋0.05%</font>
										</li><li>
											<font class="color-red">＋0.23%</font>
										</li><li>
											<font class="color-red">＋0.68%</font>
										</li><li>
											<font class="color-red">＋2.94%</font>
										</li><li>
											<font class="color-red">＋6.99%</font>
										</li><li>
											<font class="color-red">＋1.08%</font>
										</li>
									</ul>
								</li><!--<li><ul class="history-ul-tr">
										<li style="width: 20%;">同类排名</li><li>
											<font color="gray">105/175</font>
										</li><li>
											<font color="gray">146/175</font>
										</li><li>
											<font color="gray">122/172</font>
										</li><li>
											<font color="gray">24/158</font>
										</li><li>
											<font color="gray">30/141</font>
										</li><li>
											<font color="gray">113/172</font>
										</li>
									</ul>
								</li><li><ul class="history-ul-tr">
										<li style="width: 20%;">中证全债</li><li>
											<font class="color-green">-0.25%</font>
										</li><li>
											<font class="color-green">-0.06%</font>
										</li><li>
											<font class="color-red">0.25%</font>
										</li><li>
											<font class="color-red">10.84%</font>
										</li><li>
											<font class="color-red">37.21%</font>
										</li><li>
											<font class="color-red">0.50%</font>
										</li>
									</ul>
								</li>-->
						</ul>
						<ul class="history-ul-left-table">
							<li style="background-color: #F4EDDB;padding-left: 10px;font-size: 16px;">
								历史净值
							</li><li style="border-bottom: solid 1px #F4EDDB;"><ul class="history-ul-tr">
								<li>净值日期</li><li>
										单位净值
									</li><li>
										累计净值
									</li><li>
										日增长率
									</li>
								</ul>
							</li><li><ul class="history-ul-tr">
									<li>
										<font>2016-05-24</font>
									</li><li>
										<font class="color-red">1.006</font>
									</li><li>
										<font class="color-red">1.255</font>
									</li><li>
										<font>0.00%</font>
									</li>
								</ul>
							</li><li><ul class="history-ul-tr">
									<li>
										<font>2016-05-23</font>
									</li><li>
										<font class="color-red">1.006</font>
									</li><li>
										<font class="color-red">1.255</font>
									</li><li>
										<font>0.00%</font>
									</li>
								</ul>
							</li><li><ul class="history-ul-tr">
									<li>
										<font>2016-05-20</font>
									</li><li>
										<font class="color-red">1.006</font>
									</li><li>
										<font class="color-red">1.255</font>
									</li><li>
										<font>0.00%</font>
									</li>
								</ul>
							</li>
						</ul>
						<!--<ul class="history-ul-left-table" style="float: right;">
							<li style="background-color: #F4EDDB;padding-left: 10px;font-size: 16px;">
								基金评级
							</li><li style="border-bottom: solid 1px #F4EDDB;"><ul class="history-ul-tr history-ul-tr-width-30">
								<li>评级机构</li><li>
										评级日期
									</li><li>
										评级
									</li>
								</ul>
							</li><li><ul class="history-ul-tr history-ul-tr-width-30">
									<li>
										<font>海通评级</font>
									</li><li>
										<font >2016-01</font>
									</li><li>
										<span class="color-red" style="font-size:15px;letter-spacing: 2px;">★★★</span>
									</li>
								</ul>
							</li><li><ul class="history-ul-tr history-ul-tr-width-30">
									<li>
										<font>上证评级</font>
									</li><li>
										<font >2016-03</font>
									</li><li>
										<span class="color-red" style="font-size:15px;letter-spacing: 2px;">★★★</span>
									</li>
								</ul>
							</li>
							<li><ul class="history-ul-tr history-ul-tr-width-30">
									<li>
										<font>海通评级</font>
									</li><li>
										<font >2016-04</font>
									</li><li>
										<span class="color-red" style="font-size:15px;letter-spacing: 2px;">★★★</span>
									</li>
								</ul>
							</li>
						</ul>-->
					</div>
				</div>
				<div id="banner-title-3" class="banner-title-detail">
					<div style="padding: 20px; 20px 0 15px">
						<ul class="history-ul-left-table" style="margin-top: 0;">
							<li style="background-color: #F4EDDB;padding-left: 10px;font-size: 16px;">
								资产配置 <span class="title-right font-size-14">更新时间2016-03-31</span>
							</li><li style="border-bottom: solid 1px #F4EDDB;"><ul class="history-ul-tr history-ul-tr-width-45">
								<li>资产</li><li>
										占比
									</li>
								</ul>
							</li><li><ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										股票
									</li><li>
										4.50%
									</li>
								</ul>
							</li><li><ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										债券
									</li><li>
										67.41%
									</li>
								</ul>
							</li><li><ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										现金
									</li><li>
										0.00%
									</li>
								</ul>
							</li><li><ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										其他
									</li><li>
										28.09%
									</li>
								</ul>
							</li>
						</ul>
						<ul class="history-ul-left-table" style="float: right;margin-top: 0;">
							<li style="background-color: #F4EDDB;padding-left: 10px;font-size: 16px;">
								行业配置<span class="title-right font-size-14">更新时间2016-03-31</span>
							</li><li style="border-bottom: solid 1px #F4EDDB;"><ul class="history-ul-tr history-ul-tr-width-45">
								<li>行业</li><li>
										占比
									</li>
								</ul>
							</li><li><ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										<font>采矿业</font>
									</li><li>
										<font >0.10%</font>
									</li>
								</ul>
							</li><li>
								<ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										<font>制造业</font>
									</li><li>
										<font >0.37%</font>
									</li>
								</ul>
							</li><li>
								<ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										<font>建筑业</font>
									</li><li>
										<font >1.73%</font>
									</li>
								</ul>
							</li><li>
								<ul class="history-ul-tr history-ul-tr-width-45">
									<li>
										<font>租赁和商务服务业</font>
									</li><li>
										<font >1.49%</font>
									</li>
								</ul>
							</li>
						</ul>
						<ul class="history-ul-top-table" style="margin-top: 50px;">
								<li style="background-color: #DFE9F5;padding-left: 10px;font-size: 16px;">
									持股配置<span class="title-right font-size-14">更新时间2016-03-31</span>
								</li><li style="border-bottom: solid 1px #DFE9F5;"><ul class="history-ul-tr history-ul-tr-width-20">
									<li>债券名称</li><li>
											债券代码
										</li><li>
											债券市值（万元）
										</li><li>
											债券市值占比
										</li>
									</ul>
								</li><li><ul class="history-ul-tr history-ul-tr-width-20">
										<li>11佛山公控债</li><li>
											<font>11030700001GKZ</font>
										</li><li>
											<font color="red">33,552,000.00(元)</font>
										</li><li>
											<font >5.85%</font>
										</li>
									</ul>
								</li><li><ul class="history-ul-tr history-ul-tr-width-20">
										<li>15均胜电子MTN001</li><li>
											<font>15030300001YJS</font>
										</li><li>
											<font color="red">31,227,000.00(元)</font>
										</li><li>
											<font >5.45%</font>
										</li>
									</ul>
								</li><li><ul class="history-ul-tr history-ul-tr-width-20">
										<li>11凯迪MTN1</li><li>
											<font>11030700001KDM</font>
										</li><li>
											<font color="red">30,432,000.00(元)</font>
										</li><li>
											<font >5.31%</font>
										</li>
									</ul>
								</li><li><ul class="history-ul-tr history-ul-tr-width-20">
										<li>13吉利MTN1</li><li>
											<font>13030300001JLP</font>
										</li><li>
											<font color="red">30,288,000.00(元)</font>
										</li><li>
											<font >5.28%</font>
										</li>
									</ul>
								</li><li><ul class="history-ul-tr history-ul-tr-width-20">
										<li>15三花SCP003</li><li>
											<font>15030073003HVR</font>
										</li><li>
											<font color="red">30,165,000.00(元)</font>
										</li><li>
											<font >5.26%</font>
										</li>
									</ul>
								</li>
						</ul>
					</div>
				</div>
				<div id="banner-title-4" class="banner-title-detail">
					<div id="infoFileList">
						<ul class="jjgg_list" style="padding-top: 20px;">
							<li><span>2015-09-30<a target="_blank" href="/main/a/20150930/26173.shtml" class="download"></a></span><a target="_blank" href="/main/a/20150930/26173.shtml" class="list_name">平安大华财富宝货币市场基金招募说明书(更新)摘要 2015年第2期</a></li><li>
								<span>2015-09-30<a target="_blank" href="/main/a/20150930/26172.shtml" class="download"></a></span><a target="_blank" href="/main/a/20150930/26172.shtml" class="list_name">平安大华财富宝货币市场基金招募说明书(更新) 2015年第2期</a></li><li>
								<span>2014-08-15<a target="_blank" href="/main/a/20140815/25191.shtml" class="download"></a></span><a target="_blank" href="/main/a/20140815/25191.shtml" class="list_name">平安大华财富宝货币市场基金份额发售公告</a></li><li>
								<span>2014-08-15<a target="_blank" href="/main/a/20140815/25190.shtml" class="download"></a></span><a target="_blank" href="/main/a/20140815/25190.shtml" class="list_name">平安大华财富宝货币市场基金基金合同</a></li>
								<li><span>2014-08-15<a target="_blank" href="/main/a/20140815/25189.shtml" class="download"></a></span><a target="_blank" href="/main/a/20140815/25189.shtml" class="list_name">平安大华财富宝货币市场基金基金合同摘要</a></li>
						</ul>
						<div class="pagebox"><span>总共<em class="orgcol">7</em>条记录</span><a href="javascript:void(0);" class="page01 on" data-pagenum="1">1</a><a href="javascript:void(0);" class="page01" data-pagenum="2">2</a><a class="page02" href="javascript:void(0);" id="next_page">下一页</a><span style="margin: 0 5px;">共<em class="orgcol" id="pageCount">2</em>页</span>跳到<input id="page_jump_txt" type="text" class="page_inp">页<a class="page02" href="javascript:void(0);" id="pageJump">确定</a></div>
					</div>
				</div>
				<div class="recommend">
					<div class="recommend-title">
						<span>为你推荐</span>
					</div>
					<ul class="recommend-list">
						<li><div class="title">
							平安大华保本混合 <span class="type">混合型</span>
							</div>
							<div class="increase">
								<span>进一年涨幅</span>
								<p>36.30%</p>
							</div>
							<div class="desc">
								<div class="info" style="width: 50%;border-right: solid 1px #C4C4C4;">100.00起</div>
								<div class="info" style="width: 45%;">0元申购</div>
							</div>
						</li><li>
							<div class="title">
								平安大华策略先锋混合 <span class="type">混合型</span>
							</div>
							<div class="increase">
								<span>进一年涨幅</span>
								<p>105.03%</p>
							</div>
							<div class="desc">
								<div class="info" style="width: 50%;border-right: solid 1px #C4C4C4;">100.00起</div>
								<div class="info" style="width: 45%;">0元申购</div>
							</div>
						</li><li>
							<div class="title">平安大华行业先锋混合<span class="type">混合型</span></div>
							<div class="increase">
								<span>进一年涨幅</span>
								<p>108.10%</p>
							</div>
							<div class="desc">
								<div class="info" style="width: 50%;border-right: solid 1px #C4C4C4;">100.00起</div>
								<div class="info" style="width: 45%;">0元申购</div>
							</div>
						</li><li>
							<div class="title">平安大华新鑫先锋A<span class="type">混合型</span></div>
							<div class="increase">
								<span>进一年涨幅</span>
								<p>40.10%</p>
							</div>
							<div class="desc">
								<div class="info" style="width: 50%;border-right: solid 1px #C4C4C4;">100.00起</div>
								<div class="info" style="width: 45%;">0元申购</div>
							</div>
						</li>
					</ul>
					<div style="width: 100%;height: 7px;"></div>
				</div>
				<div style="width: 100%;height: 50px;"></div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/page/fund/include/footer.jsp"/>
		<script type="text/javascript">
			$(function(){
				$("div[class=month-trend]").on("click",function(){
					$(this).css("background-color","white");
					var index = $("div[name=month-trend]").index($(this));
					$("#month-trend-"+index).show();
					$(this).siblings().each(function(inx,obj){
						if($(obj).attr("class")=="month-trend"){
							$(this).css("background-color","#C4C4C4");
							var hideid = $("div[name=month-trend]").index($(obj));
							$("#month-trend-"+hideid).hide();
						}
					})
				})
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
			})
		</script>
	</body>
</html>

