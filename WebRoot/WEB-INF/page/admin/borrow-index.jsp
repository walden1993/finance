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
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../common/date/calendar.css"/>
		<script type="text/javascript" src="../common/date/calendar.js"></script>
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
	</head>
	<body style="min-width: 1200px">
		<div id="right">
			<div style="padding: 15px 0 10px 10px">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" height="28" class="main_alll_h2">
								<a href="javascript:void(0);">所有的借款</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 0px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户名:
									<input id="userName" value="" />&nbsp;&nbsp;
									真实姓名:
									<input id="realName" value="" />&nbsp;&nbsp;
									企业全称:
									<input id="orgName" value="" />&nbsp;&nbsp;
									是否新手标：
									<s:select id="isNew" list="#{'':'--请-选-择--',0:'否',1:'是'}"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									用户类型：
									<s:select id="userType" list="#{-1:'--请-选-择--',1:'个人',2:'企业'}"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									标的类型：
									<s:select id="borrowWay" list="#{-1:'--请-选-择--',1:'净值借款',2:'秒还借款',3:'信用借款',4:'实地考察借款',5:'机构担保借款',6:'流转标借款',7:'债权转让借款'}"></s:select>&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									借款状态：
									<s:select id="borrowStatus" list="#{-1:'--请-选-择--',1:'初审中',2:'招标中',3:'满标',4:'还款中',5:'已还完',6:'流标'}"></s:select>&nbsp;&nbsp;&nbsp;&nbsp;
									<input id="search" type="button" value="查找" name="search" />
								</td>
							</tr>
						</tbody>
					</table>
		             <span id="divList"><img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					<div>
	</div>
</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript">
			$(function(){
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"] = 1;
				   initListInfo(param);
				});				
			});			
			function initListInfo(praData){
				praData["paramMap.userName"] = $("#userName").val();
				praData["paramMap.borrowStatus"] = $("#borrowStatus").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
				praData["paramMap.realName"] = $("#realName").val();
				praData["paramMap.orgName"] = $("#orgName").val();
				praData["paramMap.userType"] = $("#userType").val();
				praData["paramMap.isNew"] = $("#isNew").val();
		 		$.dispatchPost("borrowAllList.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
