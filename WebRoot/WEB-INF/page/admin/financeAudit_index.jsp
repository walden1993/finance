	<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<html>
	<head>
		<title>理财师管理首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link id="skin" rel="stylesheet" href="../css/jbox/Gray/jbox.css" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today" class="main_alll_h2">
								理财师申请审核
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
				<div style="padding-right: 10px; padding-left: 10px; padding-bottom: 0px; width: 1120px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 0px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									申请时间:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									用户名：
									<input id="timeEnd"  type="text"/>&nbsp;&nbsp;
									状态
									<select id="appStatus">
									  <option value="">全部</option>
									  <option value="1" selected>申请中</option>
									  <option value="0">非申请中</option>
									</select>
									&nbsp;&nbsp;
									<input id="search" type="button" value="查&nbsp;&nbsp;找" name="search" />
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
<script type="text/javascript" src="../script/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../css/admin/popom.js"></script>
<script type="text/javascript">
			$(function(){
				$("#divList").html("");
				initListInfo(param);
				$('#search').click(function(){
				   param["pageBean.pageNum"]=1;
				   initListInfo(param);
				});		
				
			});	
			
			function initListInfo(praData){
				praData["paramMap.timeStart"] = $("#timeStart").val();
				praData["paramMap.timeEnd"] = $("#timeEnd").val();
				praData["paramMap.appStatus"] = $("#appStatus").val();
		 		$.dispatchPost("queryFinanceAudit.mht",praData,initCallBack);
		 	}
		 	function initCallBack(data){
				$("#divList").html(data);
			}
			function addRisk(){
   			   var url="addRiskInit.mht";
   			   ShowIframe("手动添加风险保障金",url,500,300);
   			}
   			function deductedRisk(){
   			   var url="deductedRiskInit.mht";
   			   ShowIframe("手动扣除风险保障金",url,500,300);
   			}
   			function closeReresh(){
   			  window.location.href="queryRiskInit.mht";
   			  ClosePop();
   			}
   			function close(){
   			  ClosePop();
   			}
</script>
</html>
