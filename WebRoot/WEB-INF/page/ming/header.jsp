<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="html5meta.jsp"></jsp:include>
<!--必要样式-->
<link type="text/css" href="ming/css/style.css" rel="stylesheet">
<link href="ming/css/component.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/bootstrap/js/jquery-1.9.1.min.js"></script>
</head>

<body>
	<header class="header">
	
		<a href="<%=basePath%>www/index.html#/tab/dash" class="logo hidden"><img
			src="images/logo.png" alt="三好资本">
		</a>
		 <a href="http://sc.chinaz.com/" class="search hidden"><span>目的地搜索</span>
		</a> 
		<a href="<%=basePath%>www/index.html#/tab/account" class="user-icon"><span>用户中心</span>
		</a>
		<span class="user-title">
		<script type="text/javascript">
			var a = $("title").text();
			document.write(a);
		</script>
		</span>
		<div id="dl-menu" class="dl-menuwrapper">
			<button id="dl-menu-button">Open Menu</button>
			<ul class="dl-menu">
				<li><a href="<%=basePath%>www/index.html#/tab/dash">资金管理</a>
					<ul class="dl-submenu">
						<li class="dl-back"><a href="#">返回上一级</a>
						</li>
						<li><a href="queryFundrecordInit.mht">资金记录</a>
						</li>
						<li><a href="rechargeInit.mht">充值</a>
						</li>
						<li><a href="withdrawLoad.mht">提现</a>
						</li>
					</ul>
				</li>
				
				<li><a href="<%=basePath%>www/index.html#/tab/dash">投资管理</a>
					<ul class="dl-submenu">
						<li class="dl-back"><a href="#">返回上一级</a>
						</li>
						<li><a href="homeBorrowRecycleList.mht">我的投资</a>
						</li>
						<li><a href="automaticBidInit.mht">自动投标</a>
						</li>
						<li><a href="financeStatisInit.mht">投资统计</a>
						</li>
					</ul>
				</li>
				
				<li><a href="<%=basePath%>www/index.html#/tab/dash">涨薪宝</a>
					<ul class="dl-submenu">
						<li class="dl-back"><a href="#">返回上一级</a>
						</li>
						<li><a href="paytreasuredetail.mht">详情</a>
						</li>
						<li><a href="intoPayTreasureMobile.mht">转入</a>
						</li>
						<li><a href="outPayTreasureInit.mht">转出</a>
						</li>
						<li><a href="profitRecordInit.mht">收益记录</a>
						</li>
					</ul>
				</li>
				
				<li><a href="<%=basePath%>www/index.html#/tab/dash">会员设置</a>
					<ul class="dl-submenu">
						<li class="dl-back"><a href="#">返回上一级</a>
						</li>
						<li><a href="mybank.mht">银行卡设置</a>
						</li>
						<li><a href="securityCent.mht">安全中心</a>
						</li>
						<li><a href="friendManagerInit.mht">邀请好友</a>
						</li>
					</ul>
				</li>
					
				<li class="hidden"><a href="<%=basePath%>www/index.html#/tab/about">关于我们</a>
				</li>
				<li class="hidden"><a href="Line">关于我们</a>
					<ul class="dl-submenu">
						<li class="dl-back"><a href="#">返回上一级</a>
						</li>
						<li><a href="http://sc.chinaz.com/">线路</a>
						</li>
						<li><a href="http://sc.chinaz.com/">签证</a>
						</li>
						<li><a href="http://sc.chinaz.com/">门票</a>
						</li>
					</ul></li>
			</ul>
		</div>
	</header>

	<script type="text/javascript" src="script/jquery.dispatch.js"></script>
	<script type="text/javascript" src="ming/js/modernizr.custom.js"></script>
	<script type="text/javascript" src="ming/js/jquery.dlmenu.js"></script>
	<script type="text/javascript" src="script/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('#dl-menu').dlmenu();
		});
	</script>
</body>
</html>
