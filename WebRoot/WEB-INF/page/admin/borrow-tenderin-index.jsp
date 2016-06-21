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
	</head>
	<body style="min-width: 1200px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
						<td width="100" height="28" class="xxk_all_a">
								<a href="borrowf.mht">待审核的借款</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" height="28" class="main_alll_h2">
								<a href="borrowTenderIn.mht">招标中的借款</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							  <td width="100" height="28" class="xxk_all_a">
								<a href="borrowFlowMark.mht">流标的借款</a>
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
									<input id="userName" value="" />&nbsp;&nbsp;&nbsp; 
									用户类型：
									<s:select id="userType" list="#{-1:'--请-选-择--',2:'企业',1:'个人'}" ></s:select>&nbsp;&nbsp; 
									借款类型：
									<s:select id="borrowWay" list="#{-1:'--请-选-择--',1:'净值借款',2:'秒还借款',3:'信用借款',4:'实地考察借款',5:'机构担保借款',7:'债权转让借款'}"></s:select>
									&nbsp;&nbsp; 
									<input id="search" type="button" value="&nbsp;&nbsp; 查 找&nbsp;&nbsp; " name="search" />
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
				praData["paramMap.userType"] = $("#userType").val();
				praData["paramMap.borrowWay"] = $("#borrowWay").val();
		 		$.dispatchPost("borrowTenderInList.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
</script>
</html>
