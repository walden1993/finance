<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include  file="/include/taglib.jsp"%>
<html>
	<head>
		<title>借款管理首页</title> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma"  content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" /> 
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords"  content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link href="../css/admin/admin_css.css" rel="stylesheet" type="text/css" />
		<link href="../css/admin/admin_custom_css.css" rel="stylesheet" type="text/css" />
	 </head>
	<body>
		<div id="right">
			<div style="padding: 15px 10px 0px 10px;">
				<div>
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="100" id="today"  class="main_alll_h2">
								<a href="queryVouchersInit.mht">代金券批次列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" id="today" height="28"   class="xxk_all_a">
								<a href="experienceInit.mht">代金券列表</a>
							</td>
							<td width="2">
								&nbsp;
							</td>
							<td width="100" class="white12">
							</td>
							<td>
								&nbsp;
							</td>
							</tr>
					</table>
				</div>
				<div  style="padding-right: 10px; padding-left: 10px; padding-bottom: 10px; width: 1160px; padding-top: 10px; background-color: #fff;">
					<table style="margin-bottom: 8px;" cellspacing="0" cellpadding="0"
						width="100%" border="0">
						<tbody>
						    <tr>
								<td class="f66" align="left" width="50%" height="36px">
									类型:
									<s:select id="type" list="#{-1:'-请选择-',0:'体验券',1:'现金券'}" ></s:select>&nbsp;&nbsp;
									状态:
									<s:select id="state" list="#{-1:'-请选择-',1:'正常',2:'冻结'}" ></s:select>&nbsp;&nbsp;
									申请人:
									<input id="investor"  value=""/>&nbsp;&nbsp;
									</td>
							</tr>
							<tr>
								<td class="f66" align="left" width="50%" height="36px">
									创建日期:
									<input id="timeStart" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									--
									<input id="timeEnd" class="Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:'readOnly'})"/>&nbsp;&nbsp;
									&nbsp;&nbsp;
									&nbsp;&nbsp;
									<input id="search" type="button" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;" />
									&nbsp;&nbsp;
									<input id="add" type="button" value="新增批次" />
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
<script type="text/javascript"  src="../script/jquery-2.0.js"></script>
<script type="text/javascript" src="../script/jquery.dispatch.js"></script>
<script  type="text/javascript" src="../css/admin/popom.js"></script>
<script  language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script  type="text/javascript">
	$(function(){
		initListInfo(param);
		$('#search').click(function(){
			param["pageBean.pageNum"] = 1;
			param["paramMap.investor"] = $("#investor").val();
			param["paramMap.type"] = $("#type").val();
			param["paramMap.state"] = $("#state").val();
			param["paramMap.timeStart"] = $("#timeStart").val();
			param["paramMap.timeEnd"] = $("#timeEnd").val();
			
			if($("#timeStart").val()> $("#timeEnd").val()){
				alert("起止日期不能小于截止日期");
				return;
			}
			
			initListInfo(param);
		});
		$("#add").click(function(){
			window.location.href='addVouchers.mht';
		});
	});		
	function initListInfo(praData){
		$.post("queryVouchersBaseInfo.mht",praData,initCallBack);
	}
	function initCallBack(data){
		$("#divList").html(data);
	}
</script>
</html>
