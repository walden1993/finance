<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>平台收费管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../script/jquery-2.0.js"></script>
		<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
		<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				
				param["pageBean.pageNum"] = 1;
				param["type"] = '${type}';
				initListInfo(param);
				$("#bt_search").click(function(){
					param["pageBean.pageNum"] = 1;
					initListInfo(param);
				});
					
			});
			
			function initListInfo(praData){
		 		$.post("showPlatformCostList.mht",param,initCallBack);
		 	}
		 	
		 	function initCallBack(data){
		 	  
				$("#dataInfo").html(data);
			}
			
			
		</script>
	</head>
	<body style="min-width: 1000px">
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<s:if test="#request.type=='' ||#request.type==1 ">
								<td width="100" height="28" class="main_alll_h2">
							</s:if>
							<s:else>
								<td width="100" height="28" class="xxk_all_a">
							</s:else>
							<a href="showPlatformCostInit.mht?typess=1">奖励费用管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<%--
							
							<s:if test="#request.type==2 ">
							<td width="100" height="28"  class="main_alll_h2">
						   </s:if>
						   <s:else>
								<td width="100" height="28"  class="xxk_all_a">
						  </s:else>
								<a href="showPlatformCostInit.mht?typess=2">借款收费管理</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							
							--%>
							<s:if test="#request.type==3 ">
								<td width="100" height="28" class="main_alll_h2">
							</s:if>
							<s:else>
								<td width="100" height="28" class="xxk_all_a">
							</s:else>
							<a href="showPlatformCostInit.mht?typess=3">其他收费管理</a>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
					<div
						style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: auto; padding-top: 10px; background-color: #fff;">
						<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tbody>
								<tr>
									<td class="f66" align="left" width="50%" height="36px">
				
									</td>
								</tr>
							</tbody>
						</table>
						<span id="dataInfo"> <img src="../images/admin/load.gif" class="load" alt="加载中..." /></span>
					</div>
				</div>
			</div>
	</body>
</html>
