<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet"
			type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body style="min-width: 1200px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" class="main_alll_h2">
								<a href="javascript:void(0);">今日到期</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="tomorrow" class="xxk_all_a">
								<a href="javascript:void(0);">明天到期</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="afterday" class="xxk_all_a">
								<a href="javascript:void(0);">后天到期</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div
					style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用&nbsp;户&nbsp;名:
									<input id="userName" value="" maxlength="20" />
									&nbsp;&nbsp; 真实姓名:
									<input id="realName" value="" maxlength="20" />
									&nbsp;&nbsp; 企业全称:
									<input id="orgName" value="" maxlength="20" />
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									借款标题:
									<input id="titles" value="" maxlength="50" />&nbsp;&nbsp;
									用户类型：
									<s:select id="userType" list="#{-1:'-请选择-',2:'企业',1:'个人'}" ></s:select>&nbsp;&nbsp;			
									 借款类型：
									<s:select id="borrowWay"
										list="#{-1:'--请选择--',1:'净值借款',2:'秒还借款',3:'信用借款',4:'实地考察借款',5:'机构担保借款',7:'债权转让借款'}"></s:select>
									&nbsp;&nbsp; 还款状态：
									<s:select id="status" list="#{-1:'--请选择--',1:'未偿还',2:'已偿还'}"></s:select>&nbsp;&nbsp;
									<input id="search" type="button" value="&nbsp;&nbsp;查 找&nbsp;&nbsp;" name="search" />
									<input id="excel" type="button" value="导出Excel" name="excel" />
								</td>
							</tr>
						</tbody>
					</table>
					<span id="divList"><img src="../images/admin/load.gif"
							class="load" alt="加载中..." />
					</span>
					<div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="../script/jquery-2.0.js"></script>
	<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
	<script type="text/javascript" src="../css/admin/popom.js"></script>
	<script type="text/javascript">
	$(function() {
		initListInfo(param);
		$('#search').click(function() {
			param["pageBean.pageNum"] = 1;
			initListInfo(param);
		});

		$("#excel")
				.click(
						function() {
							$("#excel").attr("disabled", true);
							if ($("#today").hasClass("main_alll_h2")) {
								window.location.href = encodeURI(encodeURI("exportlastRepayMent.mht?type="
										+ "&&userName="+ $("#userName").val()
										+ "&&realName="+ $("#realName").val()
										+ "&&status="+ $("#status").val()
										+ "&&orgName="+ $("#orgName").val()
										+ "&&userType="+ $("#userType").val()
										+ "&&borrowWay="+ $("#borrowWay").val()
										+ "&&titles="+ $("#titles").val()));
								setTimeout("test()", 3000);
								return;
							}
							if ($("#tomorrow").hasClass("main_alll_h2")) {
								window.location.href = encodeURI(encodeURI("exportlastRepayMent.mht?type=1"
										+ "&&userName="+ $("#userName").val()
										+ "&&realName="+ $("#realName").val()
										+ "&&status="+ $("#status").val()
										+ "&&orgName="+ $("#orgName").val()
										+ "&&userType="+ $("#userType").val()
										+ "&&borrowWay="+ $("#borrowWay").val()
										+ "&&titles="+ $("#titles").val()));
								setTimeout("test()", 3000);
								return;
							}
							if ($("#afterday").hasClass("main_alll_h2")) {
								window.location.href = encodeURI(encodeURI("exportlastRepayMent.mht?type=2"
										+ "&&userName="+ $("#userName").val()
										+ "&&realName="+ $("#realName").val()
										+ "&&status="+ $("#status").val()
										+ "&&orgName="+ $("#orgName").val()
										+ "&&userType="+ $("#userType").val()
										+ "&&borrowWay="+ $("#borrowWay").val()
										+ "&&titles="+ $("#titles").val()));
								setTimeout("test()", 3000);
								return;
							}
						});

		$('#today').click(function() {
			$('#today').addClass('main_alll_h2').removeClass('xxk_all_a');
			$('#tomorrow').addClass('xxk_all_a').removeClass('main_alll_h2');
			$('#afterday').addClass('xxk_all_a').removeClass('main_alll_h2');
			clearComponent();
			param['paramMap.type'] = '';
			initListInfo(param);
		});
		$('#tomorrow').click(function() {
			$('#today').addClass('xxk_all_a').removeClass('main_alll_h2');
			$('#tomorrow').addClass('main_alll_h2').removeClass('xxk_all_a');
			$('#afterday').addClass('xxk_all_a').removeClass('main_alll_h2');
			clearComponent();
			param['paramMap.type'] = '1';
			initListInfo(param);
		});
		$('#afterday').click(function() {
			$('#today').addClass('xxk_all_a').removeClass('main_alll_h2');
			$('#tomorrow').addClass('xxk_all_a').removeClass('main_alll_h2');
			$('#afterday').addClass('main_alll_h2').removeClass('xxk_all_a');
			clearComponent();
			param['paramMap.type'] = '2';
			initListInfo(param);
		});

	});
	function initListInfo(praData) {
		praData["paramMap.userName"] = $("#userName").val();
		praData["paramMap.realName"] = $("#realName").val();
		praData["paramMap.borrowWay"] = $("#borrowWay").val();
		praData["paramMap.title"] = $("#titles").val();
		praData["paramMap.status"] = $("#status").val();
		praData["paramMap.orgName"] = $("#orgName").val();
		praData["paramMap.userType"] = $("#userType").val();
		$.dispatchPost("lastRepayMentList.mht", praData, initCallBack);
	}
	function initCallBack(data) {
		$("#divList").html(data);
	}
	function clearComponent() {
		$("#userName").val('');
		$("#realName").val('');
		$("#borrowWay").val('');
		$("#titles").val('');
		$("#status").val('');
		$("#userType").val('');
		$("#orgName").val('');
	}
	function test() {
		$("#excel").attr("disabled", false);
	}
	function noticedetail(id) {
		var url = "repaymentNoticeInit.mht?id=" + id;
		ShowIframe("客服沟通", url, 600, 500);
	}
</script>
</html>
